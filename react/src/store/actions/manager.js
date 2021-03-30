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
                    console.log(response.data.message);
                }
                
            }).catch(err => {
                console.log(err);
            })
    }
}