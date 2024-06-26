import {
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationService } from './authentication.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authenticationService: AuthenticationService) {}

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (
      this.authenticationService.isUserLoggedIn() &&
      req.url.indexOf('auth') === -1
    ) {
      const authReq = req.clone({
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          Authorization: `Basic ${window.btoa(this.authenticationService.user.username + ':' + this.authenticationService.user.password)}`,
        }),
      });
      return next.handle(authReq);
    } else {
      return next.handle(req);
    }
  }
}
