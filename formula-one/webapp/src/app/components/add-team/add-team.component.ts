import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, forwardRef } from '@angular/core';
import { FormControl, FormGroup, FormsModule, NG_VALUE_ACCESSOR, ReactiveFormsModule, Validators } from '@angular/forms';

import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { InputSwitchModule } from 'primeng/inputswitch';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { Router } from '@angular/router';
import { FormulaOneService } from '../../services/formula-one.service';
import { FormulaOneItem } from '../../models/formula-one-item';
import { EntryFeeStatus } from '../../models/entry-fee-status';
import { Subject, takeUntil } from 'rxjs';
import { ErrorModel } from '../../models/error-model';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-add-team',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,    
    ReactiveFormsModule,    

    ButtonModule,
    CardModule,
    InputNumberModule,
    InputTextModule,
    InputSwitchModule, 
    TableModule,
    ToastModule
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputNumberModule),
      multi: true,
    }
  ],
  templateUrl: './add-team.component.html',
  styleUrl: './add-team.component.scss'
})
export class AddTeamComponent implements OnInit, OnDestroy{
  private destroy = new Subject<void>();
  formGroup: FormGroup;

  constructor(
    private formulaOneService: FormulaOneService,
    private messageService : MessageService,
    private router: Router
  ) {
    this.formGroup = new FormGroup({
      name: new FormControl('', Validators.required),
      foundationYear: new FormControl('', [Validators.required, Validators.min(1900)]),
      championships: new FormControl('', Validators.required),
      entryFeeStatus: new FormControl(false, Validators.required),
    })
  }

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  back(): void{
    this.router.navigateByUrl('/');
  }

  statusChange($event: any): void{
    console.log($event);
  }

  save(): void {
    const item: FormulaOneItem = {
      name: this.formGroup.controls['name'].value,
      foundationYear: this.formGroup.controls['foundationYear'].value,
      championships: this.formGroup.controls['championships'].value,
      entryFeeStatus: this.formGroup.controls['entryFeeStatus'].value ? EntryFeeStatus.PAID : EntryFeeStatus.NOT_PAID,
    }
    this.formulaOneService.addTeam(item)
      .pipe(takeUntil(this.destroy))
      .subscribe({
        next: (value: FormulaOneItem) => {
          this.checkUploads(value);
          this.formGroup.reset();
          this.router.navigateByUrl('/');
        },
        error: (err: ErrorModel) => {
          this.showError(err);
        },
      });
  }

  
  ngOnDestroy(): void {
    this.destroy.next();
    this.destroy.complete();
  }
  
  private showError(err: ErrorModel): void{
    this.messageService.add({
      severity: 'error',
      summary: 'Sikertelen feltöltés',
      detail: err.error?.message ? err.error.message : err.message
    });
  }

  private checkUploads(value: FormulaOneItem): void{
    if (value && value.id) {
      this.messageService.add({ severity: 'success', summary: 'Sikeres feltöltés', detail: `${value.name} csapat hozzáadva.` });
    }
  }
}
