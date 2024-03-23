import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'
  private readonly API: string = 'http://localhost:8080/api/auth';
  private _username: string='';
  private _password: string='';

  constructor(
    private http: HttpClient
  ) { }

  authenticationService(username: string, password: string): Observable<void>  {
    const basicAuthHeaderString = this.createBasicAuthHeader(username, password);
    const headers = new HttpHeaders({
      Authorization: basicAuthHeaderString
    })
    return this.http.get(this.API, { headers })
      .pipe(map(() => {
        this._username = username;
        this._password = password;
        this.registerSuccessfulLogin(username);
      }));    
  }

  createBasicAuthHeader(username: string, password: string): string {
    const basicAuthHeaderString = 'Basic ' + window.btoa(`${username}:${password}`);
    return basicAuthHeaderString;
  }

  registerSuccessfulLogin(username: string): void {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, username);
  }

  logout(): void {
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    this._username = '';
    this._password = '';
  }

  isUserLoggedIn(): boolean {
    const user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    if (user === null) {
      return false;
    }
    return true;
  }

  get username(): string{
    return this._username
  }

  get password(): string{
    return this._password;
  }
}