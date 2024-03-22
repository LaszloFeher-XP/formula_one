import { createReducer, on } from "@ngrx/store";
import { addFormulaOneItem, removeFormulaOneItem } from "./formula-one-actions";

export const initalItem = {};

export const formulaOneItemReducer = createReducer(
    initalItem,
    on(addFormulaOneItem, (state, props) => {
        return state = props;
    }),
    on(removeFormulaOneItem, (state) => {
        return state = {};
    })
)