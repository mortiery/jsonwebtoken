import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {AuthService} from '../auth/auth.service';
import {AuthTokenService} from '../auth/auth-token.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styles: ['']
})
export class MenuComponent implements OnInit, OnDestroy {
  private loginSubscription: Subscription;
  loggedIn = false;
  userHasUserRole = false;
  userHasAdminRole = false;

  constructor(
    private authService: AuthService,
    private authTokenService: AuthTokenService
  ) { }

  ngOnInit() {
    this.loginSubscription = this.authService.loginState.subscribe((loggedIn) => {
      this.loginStateChanged(loggedIn);
    });
  }

  private loginStateChanged(loggedIn) {
    this.loggedIn = loggedIn;
    if (loggedIn) {
      this.userHasUserRole = this.authTokenService.loggedInUserHasRole('USER');
      this.userHasAdminRole = this.authTokenService.loggedInUserHasRole('ADMIN');
    } else {
      this.userHasUserRole = false;
      this.userHasAdminRole = false;
    }
  }

  getLoggedInUserName(): string {
    return this.authTokenService.getLoggedInUserName();
  }

  onLogout(): void {
    this.authService.logout();
  }

  ngOnDestroy(): void {
    this.loginSubscription.unsubscribe();
  }

}
