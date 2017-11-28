import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {Subject} from 'rxjs/Subject';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {AuthTokenService, AuthTokenType} from './auth-token.service';

@Injectable()
export class AuthService {
  private static readonly REFRESH_TOLERANCE: number = 5 * 60 * 1000;

  loginState: Subject<boolean> = new ReplaySubject<boolean>(1);
  private refreshTimeout: any;

  constructor(
    private httpClient: HttpClient,
    private router: Router,
    private tokenService: AuthTokenService
  ) {
    this.tokenService.initializeTokensFromStorage();
    this.determineCurrentLoginState();
    this.setUpRefresh();
  }

  private determineCurrentLoginState() {
    if (this.tokenService.tokenNotExpired(AuthTokenType.ID_TOKEN)) {
      this.loginState.next(true);
    } else {
      this.loginState.next(false);
    }
  }

  private setUpRefresh() {
    if (!this.tokenService.tokenNotExpired(AuthTokenType.REFRESH_TOKEN)) {
      return;
    }

    const refreshTimeout = this.tokenService.getTokenRefreshTimeout(AuthTokenType.ID_TOKEN) - AuthService.REFRESH_TOLERANCE;

    if (refreshTimeout >= 0 && this.tokenService.isRefreshableAtTimeout(refreshTimeout)) {
      console.log('Scheduling token refresh in ' + refreshTimeout + ' ms');
      this.refreshTimeout = setTimeout(this.refresh, refreshTimeout);
    } else {
      console.log('Token (possibly) timed out, immediately refreshing...');
      this.refresh();
    }
  }

  login(username: string, password: string): void {
    this.httpClient.post('/auth/login',
      {username : username, password : password}).subscribe(
      (loginPayload) => {

        this.tokenService.setIdTokenFromRawData(loginPayload['id_token']);
        this.tokenService.setRefreshTokenFromRawData(loginPayload['refresh_token']);
        this.loginState.next(true);
        this.setUpRefresh();
        this.router.navigate(['/']);
      }
    );
  }

  logout(): void {
    this.loginState.next(false);
    this.tokenService.deleteTokens();
    clearTimeout(this.refreshTimeout);
    this.router.navigate(['/']);
  }

  public refresh = (): void  => {
    if (!this.tokenService.tokenNotExpired(AuthTokenType.REFRESH_TOKEN)) {
      return;
    }

    console.log('Refreshing...');
    this.httpClient.post('/auth/refresh',
      {'refresh_token' : this.tokenService.getRawToken(AuthTokenType.REFRESH_TOKEN)}).subscribe(
      (refreshPayload) => {
        this.tokenService.setIdTokenFromRawData(refreshPayload['id_token']);
        this.loginState.next(true);
        this.setUpRefresh();
      },
      (error) => {
        console.error('Could not refresh token' + error);
        this.logout();
      }
    );
  };



}

