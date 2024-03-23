import { inject } from "@angular/core";
import { AuthenticationService } from "../../services/authentication.service";
import { CanActivateFn, Router, UrlTree } from "@angular/router";
import { Observable } from "rxjs";

export const TeamGuard: CanActivateFn = ():
  Observable<boolean | UrlTree> 
  | Promise<boolean | UrlTree> 
  | boolean 
  | UrlTree => {
  return inject(AuthenticationService).isUserLoggedIn()
    ? true
    : inject(Router).createUrlTree(['/']);
};