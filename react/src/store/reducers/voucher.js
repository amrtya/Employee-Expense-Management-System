import * as actionTypes from '../actions/actionTypes';
import { updateObject } from '../utility';

const initialState = {
    vouchers: []
};

const reducer = (state=initialState, action) => {
    switch(action.type){
        case actionTypes.GET_VOUCHER:
            return updateObject(state, {
                vouchers: action.vouchers
            });
        case actionTypes.ADD_VOUCHER:
            const newVoucher = updateObject(action.voucherData, {expenseID: action.id});
            return updateObject(state, {
                vouchers: state.vouchers.concat(newVoucher)
            });
        default:
            return state;
    }
}
 
export default reducer;