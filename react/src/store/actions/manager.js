import * as actionTypes from './actionTypes';
import axios from 'axios';

const getVoucherInStore = (vouchers) => {
    return {
        type: actionTypes.GET_ALL_VOUCHER,
        vouchers: vouchers
    }
}

export const getAllVouchers = (managerID) => {
    return dispatch => {
        axios.get('http://localhost:8080/manager', {headers: {manager_id: managerID}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    dispatch(getVoucherInStore(response.data.results));
                }else{
                    alert(response.data.message);
                }
                
            }).catch(err => {
                console.log(err);
            })
    }
}

const delVoucher = (expenseID) => {
    return {
        type: actionTypes.DEL_VOUCHER,
        expenseID: expenseID
    }
}

export const deleteVoucher = (managerID, expenseID) => {
    return dispatch => {
        axios.delete("http://localhost:8080/manager/expense/"+expenseID, {headers: {manager_id: managerID}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    dispatch(delVoucher(expenseID));
                }else{
                    alert(response.data.message);
                }
            }).catch(err => {
                console.log(err);
            })
    }
}

export const managerUpdateVoucher = (mid, data) => {
    return dispatch => {
        console.log("http://localhost:8080/manager/expense/"+data.expenseId);
        console.log(mid);
        axios.put("http://localhost:8080/manager/expense/"+data.expenseId, data, {headers: {manager_id: mid}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    dispatch(getAllVouchers(mid));
                }else{
                    alert(response.data.message);
                }
            }).catch(err => {
                console.log(err);
            })
    }
}