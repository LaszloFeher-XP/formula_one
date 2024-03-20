import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FormulaOneItem } from '../models/formula-one-item';

@Injectable({
  providedIn: 'root'
})
export class FormulaOneService {

  private readonly API: string = 'http://localhost:8080/api/f1';

  constructor(
    private readonly http: HttpClient
  ) { }

  getTeams(): Observable<FormulaOneItem[]> {
    return this.http.get<FormulaOneItem[]>(this.API);
  }

}
