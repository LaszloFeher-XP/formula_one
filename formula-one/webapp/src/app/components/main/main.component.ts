import { Component } from '@angular/core';
import { CommonModule } from "@angular/common";
import { FormulaOneItem } from '../../models/formula-one-item';
import { FormulaOneService } from '../../services/formula-one.service';

import { TableModule } from 'primeng/table';
import { EntityFeeStatus } from '../../models/entity-fee-status';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [
    CommonModule,
    TableModule
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss'
})
export class MainComponent {
  teams: FormulaOneItem[] = []

  constructor(
    private formulaOneService: FormulaOneService,
  ) {}

  ngOnInit(): void {
    this.getTeams();
  }

  getTeams(): void {
    this.formulaOneService.getTeams().subscribe(value => this.teams = value);
  }

  isPaid(item: FormulaOneItem): boolean{
    return item.entryFeeStatus === EntityFeeStatus.PAID;
  }
}
