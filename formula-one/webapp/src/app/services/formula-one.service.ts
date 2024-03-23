import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FormulaOneItem } from '../models/formula-one-item';
import { AuthenticationService } from './authentication.service';
import { DeleteResponse } from '../models/delete-response';

@Injectable({
  providedIn: 'root'
})
export class FormulaOneService {

  private readonly API: string = 'http://localhost:8080/api/f1';

  constructor(
    private readonly authenticationService: AuthenticationService,
    private readonly http: HttpClient,
  ) { }

  getTeams(): Observable<FormulaOneItem[]> {
    return this.http.get<FormulaOneItem[]>(this.API);
  }

  addTeam(item: FormulaOneItem): Observable<FormulaOneItem>{
    return this.http.post<FormulaOneItem>(`${this.API}/team`, item, {headers: this.header});
  }

  updateTeam(item: FormulaOneItem): Observable<FormulaOneItem>{
    return this.http.put<FormulaOneItem>(`${this.API}/team`, item, {headers: this.header});
  }

  deleteTeam(id: string): Observable<DeleteResponse> {
    return this.http.delete<DeleteResponse>(`${this.API}/team/${id}`, {headers: this.header});
  }

  get header(): HttpHeaders {
    const headers = new HttpHeaders({
      Authorization: this.basicAuthHeaderString
    })
    return headers;
  }

  get basicAuthHeaderString(): string {
    const basicAuthHeaderString = 'Basic ' + window.btoa(`${this.authenticationService.username}:${this.authenticationService.password}`);
    return basicAuthHeaderString;
  }
}
