import * as actionTypes from '../actions/actionTypes';
import { updateObject } from '../utility';

const initialState = {
    vouchers: [],
    editValid: "none",
    singleVoucher: null
};

const reducer = (state=initialState, action) => {
    switch(action.type){
        case actionTypes.GET_ALL_VOUCHER:
            return updateObject(state, {
                vouchers: action.vouchers,
                singleVoucher: null,
                editValid: "none"
            })
        case actionTypes.DEL_VOUCHER:
            return updateObject(state, {
                vouchers: state.vouchers.filter(voucher => voucher.expenseId!==action.expenseID)
            })
        case actionTypes.UPDATE_EDIT:
            return updateObject(state, {
                editValid: "block"
            })
        case actionTypes.CLOSE_EDIT:
            return updateObject(state, {
                editValid: "none"
            })
        case actionTypes.MANAGER_UPDATE_VOUCHER:
            return updateObject(state, {
                singleVoucher: action.data
            })
        default:
            return state;
    }
}
 
export default reducer;