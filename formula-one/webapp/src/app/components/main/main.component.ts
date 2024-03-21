import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from "@angular/common";

import { FormulaOneItem } from '../../models/formula-one-item';
import { EntryFeeStatus } from '../../models/entry-fee-status';

import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { TooltipModule } from 'primeng/tooltip';

import { FormulaOneService } from '../../services/formula-one.service';
import { Subject, takeUntil } from 'rxjs';
import { Router } from '@angular/router';

import { Store, StoreModule } from '@ngrx/store';
import { formulaOneItemReducer } from '../store/formula-one-reducers';
import { addFormulaOneItem } from '../store/formula-one-actions';
@Component({
  selector: 'app-main',
  standalone: true,
  imports: [
    CommonModule,

    ButtonModule,
    TableModule,
    TooltipModule
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss'
})
export class MainComponent implements OnInit, OnDestroy {
  private destroy = new Subject<void>();

  teams: FormulaOneItem[] = []

  constructor(
    private formulaOneService: FormulaOneService,
    private store: Store,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getTeams();
  }

  getTeams(): void {
    this.formulaOneService.getTeams().pipe(takeUntil(this.destroy)).subscribe(value => this.teams = value);
  }

  isPaid(item: FormulaOneItem): boolean{
    return item.entryFeeStatus === EntryFeeStatus.PAID;
  }

  create(): void{
    this.router.navigateByUrl('add-new-team');
  }

  updateTeam(item: FormulaOneItem): void{
    this.store.dispatch(addFormulaOneItem(item));
    this.router.navigateByUrl('update-team');
  }

  async deleteTeam(item: FormulaOneItem): Promise<void>{
    await this.formulaOneService.deleteTeam(item.id!);    
    this.getTeams();
  }

  ngOnDestroy(): void {
    this.destroy.next();
    this.destroy.complete();
  }
}
