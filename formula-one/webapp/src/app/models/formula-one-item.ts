import { EntryFeeStatus } from './entry-fee-status';

export interface FormulaOneItem {
  id?: string;
  name: string;
  foundationYear: string;
  championships: number;
  entryFeeStatus: EntryFeeStatus;
}
