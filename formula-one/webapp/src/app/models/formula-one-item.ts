import { EntityFeeStatus } from './entity-fee-status';

export interface FormulaOneItem{
  id:string;
  name:string;
  foundationYear:string;
  championships:number;
  entryFeeStatus: EntityFeeStatus;  
}