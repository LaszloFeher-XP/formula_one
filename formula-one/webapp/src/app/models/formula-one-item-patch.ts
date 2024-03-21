import { EntryFeeStatus } from './entry-fee-status';

export interface FormulaOneItemPatch{
  id:string;
  name?:string;
  foundationYear?:string;
  championships?:number;
  entryFeeStatus?: EntryFeeStatus;  
}