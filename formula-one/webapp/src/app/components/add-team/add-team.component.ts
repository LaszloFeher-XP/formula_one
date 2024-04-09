import { CommonModule, DatePipe } from '@angular/common';
import { Component, OnDestroy, forwardRef } from '@angular/core';
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
import { Subject, takeUntil, timer } from 'rxjs';
import { ErrorModel } from '../../models/error-model';
import { MessageService } from 'primeng/api';
import { Store } from '@ngrx/store';
import { MessageModule } from 'primeng/message';
import { removeFormulaOneItem } from '../../store/formula-one-actions';

@Component({
  selector: 'app-add-team',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,    
    ReactiveFormsModule,    
    DatePipe,

    ButtonModule,
    CardModule,
    InputNumberModule,
    InputTextModule,
    InputSwitchModule, 
    MessageModule,
    TableModule,
    ToastModule
  ],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputNumberModule),
      multi: true,      
    },
    DatePipe
  ],
  templateUrl: './add-team.component.html',
  styleUrl: './add-team.component.scss'
})
  
export class AddTeamComponent implements  OnDestroy{
  private destroy: Subject<void> = new Subject<void>();
  readonly START_YEAR: number = 1950;
  readonly END_YEAR: number = 1950;
  formGroup: FormGroup;
  storeItem = this.store.select('formulaOneItem');
  editMode: boolean = false;
  isSaving: boolean = false;
  id: string =  '';

  constructor(
    private datePipe: DatePipe, 
    private formulaOneService: FormulaOneService,
    private messageService : MessageService,
    private store : Store<{formulaOneItem: FormulaOneItem}>,
    private router: Router,

  ) {
    this.END_YEAR = +(this.datePipe.transform(new Date(), 'yyyy')!);
    this.formGroup = new FormGroup({
      name: new FormControl('', Validators.required),
      foundationYear: new FormControl(1950, [Validators.required, Validators.min(this.START_YEAR), Validators.max(2024)]),
      championships: new FormControl(0, [Validators.required, Validators.min(0), Validators.max(100)]),
      entryFeeStatus: new FormControl(false, Validators.required),
    })    
    this.storeItem.pipe(takeUntil(this.destroy)).subscribe((value: FormulaOneItem) => {
      this.checkUpdateMode(value);
      if (value && value.id) {
        this.fillExistingDataIntoForm(value);
        this.editMode = true;
      }
    });
  }

  fillExistingDataIntoForm(value: FormulaOneItem): void{
    this.formGroup.controls['name'].setValue(value.name);
    this.formGroup.controls['foundationYear'].setValue(value.foundationYear);
    this.formGroup.controls['championships'].setValue(value.championships);
    this.formGroup.controls['entryFeeStatus'].setValue(value.entryFeeStatus === EntryFeeStatus.PAID);
    this.id = value.id!;
  }

  checkUpdateMode(value: FormulaOneItem): void{    
    if (this.router.url === '/update-team' && !value?.id) {
      this.router.navigateByUrl('/');
   }
  }

  back(): void{
    this.store.dispatch(removeFormulaOneItem());
    this.router.navigateByUrl('/');
  }

  save(): void {
    const item: FormulaOneItem = {
      name: this.formGroup.controls['name'].value,
      foundationYear: this.formGroup.controls['foundationYear'].value,
      championships: this.formGroup.controls['championships'].value,
      entryFeeStatus: this.formGroup.controls['entryFeeStatus'].value ? EntryFeeStatus.PAID : EntryFeeStatus.NOT_PAID,
    }
    if (this.editMode) {      
      this.doUpdate(item);
      return;
    }
    this.doSave(item);
  }

  doSave(item: FormulaOneItem): void {
    this.isSaving = true;
    this.formulaOneService.addTeam(item)
    .pipe(takeUntil(this.destroy))
      .subscribe({        
        next: (value: FormulaOneItem) => {
          this.checkAdded(value);
          this.finalize();
        },
        error: (err: ErrorModel) => {
          this.showError(err);
        },
      });
  }

  doUpdate(item: FormulaOneItem): void {    
    item.id = this.id;
    this.formulaOneService.updateTeam(item)
    .pipe(takeUntil(this.destroy))
      .subscribe({        
        next: (value: FormulaOneItem) => {
          this.checkUpdated(value);
          this.finalize();
        },
        error: (err: ErrorModel) => {
          this.showError(err);
        },
      });
  }

  get headerLabel(): string{
    return this.editMode ? 'Update' : 'Add New';
  }

  get saveLabel(): string{
    return this.editMode ? 'Update' : 'Save';
  }

  get buttonDisabled(): boolean{
    return this.editMode
      ? (this.formGroup.pristine || !this.formGroup.valid || this.isSaving)
      : (!this.formGroup.valid || this.isSaving);
  }

  nameHasError(): boolean{
    return this.formGroup.controls['name'].dirty && this.formGroup.controls['name'].hasError('required');
  }

  ngOnDestroy(): void {
    this.destroy.next();
    this.destroy.complete();
  }
  
  private showError(err: ErrorModel): void{
    this.isSaving = false;
    if (err?.status && err.status == 401) {
      this.messageService.add({ severity: 'error', summary: 'Login has expired', detail: `Please logout and login again.` });
      return;
    }
    this.messageService.add({
      severity: 'error',
      summary: 'Unsuccessful action',
      detail: err.error?.message ? err.error.message : err.message
    });
  }

  private checkAdded(value: FormulaOneItem): void{
    if (value && value.id) {
      this.messageService.add({ severity: 'success', summary: 'Successful creation', detail: `${value.name} has been created.` });
    }
  }

  private checkUpdated(value: FormulaOneItem): void{
    if (value && value.id) {
      this.messageService.add({ severity: 'success', summary: 'Update successful', detail: `${value.name} has been modified.` });
    }
  }

  private finalize(): void{
    timer(1000).subscribe({
      next: () => {
        this.isSaving = false;
        this.formGroup.reset();
        this.store.dispatch(removeFormulaOneItem());
        this.router.navigateByUrl('/');
      },      
    })    
  }
}
