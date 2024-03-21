import { HttpClient, HttpHeaders, HttpParamsOptions } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, lastValueFrom } from 'rxjs';
import { FormulaOneItem } from '../models/formula-one-item';

@Injectable({
  providedIn: 'root'
})
export class FormulaOneService {

  private readonly API: string = 'http://localhost:8080/api/f1';
  private readonly HEADERS: HttpHeaders = new HttpHeaders()
    .set('Content-Type', 'application/json-patch+json');

  constructor(
    private readonly http: HttpClient
  ) { }

  getTeams(): Observable<FormulaOneItem[]> {
    return this.http.get<FormulaOneItem[]>(this.API, { headers: this.HEADERS });
  }

  updateTeam(item: FormulaOneItem): Observable<FormulaOneItem> {
    return this.http.post<FormulaOneItem>(`${this.API}/${item.id}`, item);
  }

  async deleteTeam(id:string): Promise<void>{    
    return lastValueFrom(this.http.delete<void>(`${this.API}/${id}`));
  }

  addTeam(item: FormulaOneItem): Observable<FormulaOneItem>{
    return this.http.post<FormulaOneItem>(this.API, item);
  }

}
