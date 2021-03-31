import * as actionTypes from '../actions/actionTypes';

import {updateObject} from '../utility';

const initialState = {
    totalExpense: "",
    pendingExpense: "",
    approvedExpense: ""
}

const reducer = (state=initialState, action) => {
    switch(action.type){
        case actionTypes.GET_MONTH_DETAILS:
            return updateObject(state, {
                totalExpense: action.data.totalExpense,
                pendingExpense: action.data.pendingExpense,
                approvedExpense: action.data.approvedExpense
            })
        default:
            return state;
    }
}
 
export default reducer;