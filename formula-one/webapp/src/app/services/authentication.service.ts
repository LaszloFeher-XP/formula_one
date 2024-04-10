import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  USER_NAME_SESSION = 'authenticatedUser';
  private readonly API: string = 'http://localhost:8080/api/auth';
  private _user: User = new User();

  constructor(private http: HttpClient) {}

  authenticationService(username: string, password: string): Observable<void> {
    const basicAuthHeaderString = this.createBasicAuthHeader(
      username,
      password
    );
    const headers = new HttpHeaders({
      Authorization: basicAuthHeaderString,
    });
    return this.http.get(this.API, { headers }).pipe(
      map(() => {
        this._user.username = username;
        this._user.password = password;
        this.registerSuccessfulLogin(username);
      })
    );
  }

  createBasicAuthHeader(username: string, password: string): string {
    const basicAuthHeaderString =
      'Basic ' + window.btoa(`${username}:${password}`);
    return basicAuthHeaderString;
  }

  registerSuccessfulLogin(username: string): void {
    sessionStorage.setItem(this.USER_NAME_SESSION, username);
  }

  logout(): void {
    sessionStorage.removeItem(this.USER_NAME_SESSION);
    this._user.username = '';
    this._user.password = '';
  }

  isUserLoggedIn(): boolean {
    const user = sessionStorage.getItem(this.USER_NAME_SESSION);
    if (user === null) {
      return false;
    }
    return true;
  }

  get user(): User {
    return this._user;
  }

  get hasUserData(): boolean {
    return (
      this._user &&
      this.user.username !== undefined &&
      this.user.password !== undefined
    );
  }
}
