import {Base64} from 'js-base64';
import {JwtBody} from './data/jwt-body';

export class AuthTokenService {
  private idToken: JwtBody;
  private refreshToken: JwtBody;

  public initializeTokensFromStorage() {
    this.idToken = this.parseJwtBody(localStorage.getItem('id_token'));
    this.refreshToken = this.parseJwtBody(localStorage.getItem('refresh_token'));
  }

  public getRawToken(tokenType: AuthTokenType): string {
    if (tokenType === AuthTokenType.ID_TOKEN) {
      return localStorage.getItem('id_token');
    } else {
      return localStorage.getItem('refresh_token');
    }
  }

  public setIdTokenFromRawData(rawData: string): void {
    this.idToken = this.parseJwtBody(rawData);
    localStorage.setItem('id_token', rawData);
  }

  public setRefreshTokenFromRawData(rawData: string): void {
    this.refreshToken = this.parseJwtBody(rawData);
    localStorage.setItem('refresh_token', rawData);
  }

  public deleteTokens() {
    localStorage.removeItem('id_token');
    localStorage.removeItem('refresh_token');
    this.idToken = null;
    this.refreshToken = null;
  }

  public tokenNotExpired(tokenType: AuthTokenType): boolean {
    const token = this.getToken(tokenType);

    if (token == null) {
      return false;
    } else if (Date.now() >= token.exp * 1000) {
      return false;
    }
    return true;
  }

  public getTokenRefreshTimeout(tokenType: AuthTokenType): number {
    const token = this.getToken(tokenType);
    const expirationTime = token.exp;
    return expirationTime * 1000 - new Date().getTime();
  }

  public isRefreshableAtTimeout(refreshTimeout: number): boolean {
    return (new Date().getTime() + refreshTimeout) / 1000 < this.refreshToken.exp;
  }

  public getIdTokenForHeader(): string {
    if (localStorage.getItem('id_token') !== null) {
      return 'Bearer ' + localStorage.getItem('id_token');
    }
    return null;
  }

  public getLoggedInUserName(): string {
    return this.idToken.usr;
  }

  public loggedInUserHasRole(role: string): boolean {
    return this.idToken.rls.indexOf('ROLE_' + role) !== -1;
  }

  private getToken(tokenType: AuthTokenType): JwtBody {
    if (tokenType === AuthTokenType.ID_TOKEN) {
      return this.idToken;
    } else {
      return this.refreshToken;
    }
  }

  private parseJwtBody(encodedToken: string): JwtBody {
    if (encodedToken == null) {
      return null;
    }
    const encodedTokenBody: string = encodedToken.split('\.')[1];
    return JSON.parse(Base64.decode(encodedTokenBody));
  }
}

export enum AuthTokenType {
  ID_TOKEN,
  REFRESH_TOKEN
}
