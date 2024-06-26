import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormulaOneItem } from '../../models/formula-one-item';
import { EntryFeeStatus } from '../../models/entry-fee-status';

import { ButtonModule } from 'primeng/button';
import { MessagesModule } from 'primeng/messages';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { TooltipModule } from 'primeng/tooltip';

import { FormulaOneService } from '../../services/formula-one.service';
import { Subject, takeUntil } from 'rxjs';
import { Router } from '@angular/router';

import { Store } from '@ngrx/store';
import { addFormulaOneItem } from '../../store/formula-one-actions';
import { AuthenticationService } from '../../services/authentication.service';
import { ErrorModel } from '../../models/error-model';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [
    CommonModule,

    ButtonModule,
    MessagesModule,
    TableModule,
    ToastModule,
    TooltipModule,
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
})
export class MainComponent implements OnInit, OnDestroy {
  private authenticationService = inject(AuthenticationService);
  private formulaOneService = inject(FormulaOneService);
  private messageService = inject(MessageService);

  private store = inject(Store);
  private router = inject(Router);

  private destroy = new Subject<void>();

  teams: FormulaOneItem[] = [];

  ngOnInit(): void {
    this.getTeams();
  }

  getTeams(): void {
    this.formulaOneService
      .getTeams()
      .pipe(takeUntil(this.destroy))
      .subscribe(value => (this.teams = value));
  }

  isPaid(item: FormulaOneItem): boolean {
    return item.entryFeeStatus === EntryFeeStatus.PAID;
  }

  create(): void {
    this.router.navigateByUrl('add-new-team');
  }

  updateTeam(item: FormulaOneItem): void {
    this.store.dispatch(addFormulaOneItem(item));
    this.router.navigateByUrl('update-team');
  }

  deleteTeam(item: FormulaOneItem): void {
    this.formulaOneService
      .deleteTeam(item.id!)
      .pipe(takeUntil(this.destroy))
      .subscribe({
        next: () => {
          this.getTeams();
        },
        error: (err: ErrorModel) => {
          this.showError(err);
        },
      });
  }

  isUserLoggedIn(): boolean {
    return this.authenticationService.isUserLoggedIn();
  }

  ngOnDestroy(): void {
    this.destroy.next();
    this.destroy.complete();
  }

  private showError(err: ErrorModel): void {
    if (err?.status && err.status == 401) {
      this.messageService.add({
        severity: 'error',
        summary: 'Login has expired',
        detail: `Please logout and login again.`,
      });
      return;
    }
    this.messageService.add({
      severity: 'error',
      summary: 'Delete was unsuccessful',
      detail: err.error?.message ? err.error.message : err.message,
    });
  }
}
