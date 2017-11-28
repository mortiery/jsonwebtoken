import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {AuthTokenService} from './auth-token.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private authTokenService: AuthTokenService
  ) {}

  public intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const tokenForHeader = this.authTokenService.getIdTokenForHeader();
    if (tokenForHeader !== null) {
      const copiedRequest = request.clone({headers: request.headers.set('Authorization', tokenForHeader)});
      return next.handle(copiedRequest);
    } else {
      return next.handle(request);
    }
  }
}
