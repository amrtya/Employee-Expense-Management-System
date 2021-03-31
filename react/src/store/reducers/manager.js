import * as actionTypes from '../actions/actionTypes';
import { updateObject } from '../utility';

const initialState = {
    vouchers: []
};

const reducer = (state=initialState, action) => {
    switch(action.type){
        case actionTypes.GET_ALL_VOUCHER:
            return updateObject(state, {
                vouchers: action.vouchers
            })
        case actionTypes.DEL_VOUCHER:
            return updateObject(state, {
                vouchers: state.vouchers.filter(voucher => voucher.expenseId!==action.expenseID)
            })
        default:
            return state;
    }
}
 
export default reducer;