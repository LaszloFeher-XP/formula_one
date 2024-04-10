import { createAction, props } from '@ngrx/store';
import { FormulaOneItem } from '../models/formula-one-item';

export const addFormulaOneItem = createAction(
  '[Item] Add team',
  props<FormulaOneItem>()
);
export const removeFormulaOneItem = createAction('[Item] Remove team');
