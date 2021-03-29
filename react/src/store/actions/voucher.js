import * as actionTypes from './actionTypes';
import axios from 'axios';

const addVoucherSuccess = (id, voucherData) => {
    return {
        type: actionTypes.ADD_VOUCHER,
        id: id,
        voucherData: voucherData
    }
}

export const addVoucher = (voucherData) => {
    return dispatch => {
        axios.post('http://localhost:8080/expense', voucherData)
            .then(response => {
                dispatch(addVoucherSuccess(12345, voucherData)); //get 12345 id from response body
            }).catch(err => {
                console.log(err);
                dispatch(addVoucherSuccess(12345, voucherData));
            })
    }
}