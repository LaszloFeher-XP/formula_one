import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FormulaOneItem } from '../models/formula-one-item';
import { DeleteResponse } from '../models/delete-response';

@Injectable({
  providedIn: 'root',
})
export class FormulaOneService {
  private readonly API: string = 'http://localhost:8080/api/f1';

  constructor(private readonly http: HttpClient) {}

  getTeams(): Observable<FormulaOneItem[]> {
    return this.http.get<FormulaOneItem[]>(this.API);
  }

  addTeam(item: FormulaOneItem): Observable<FormulaOneItem> {
    return this.http.post<FormulaOneItem>(`${this.API}/team`, item);
  }

  updateTeam(item: FormulaOneItem): Observable<FormulaOneItem> {
    return this.http.put<FormulaOneItem>(`${this.API}/team`, item);
  }

  deleteTeam(id: string): Observable<DeleteResponse> {
    return this.http.delete<DeleteResponse>(`${this.API}/team/${id}`);
  }
}
