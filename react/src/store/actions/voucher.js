import * as actionTypes from './actionTypes';
import axios from 'axios';

const addVoucherSuccess = (id, voucherData) => {
    return {
        type: actionTypes.ADD_VOUCHER,
        id: id,
        voucherData: voucherData
    }
}

export const addVoucher = (voucherData, userID) => {
    return dispatch => {
        axios.post('http://localhost:8080/expense', voucherData, {headers: {user_id: userID}})
            .then(response => {
                console.log(response.data, userID);
                if(response.data.responseType === "SUCCESS"){
                    dispatch(addVoucherSuccess(response.data.result.expenseId, voucherData));
                }else{
                    console.log(response.data.message);
                }
            }).catch(err => {
                console.log(err);
            })
    }
}

const getVoucherInStore = (vouchers) => {
    return {
        type: actionTypes.GET_VOUCHER,
        vouchers: vouchers
    }
}

export const getVoucher = (userID) => {
    return dispatch => {
        axios.get('http://localhost:8080/expense', {headers: {user_id: userID}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    dispatch(getVoucherInStore(response.data.results));
                }else{
                    console.log(response.data.message);
                }
                
            }).catch(err => {
                console.log(err);
            })
    }
}