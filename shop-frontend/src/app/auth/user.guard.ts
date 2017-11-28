import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {AuthTokenService} from './auth-token.service';

@Injectable()
export class UserGuard implements CanActivate {

  constructor(
    private authTokenService: AuthTokenService
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean>|boolean {
    return this.authTokenService.loggedInUserHasRole('USER');
  }

}
