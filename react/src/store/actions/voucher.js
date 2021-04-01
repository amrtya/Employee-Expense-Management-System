import * as actionTypes from './actionTypes';
import axios from 'axios';
import { toast } from '../../../node_modules/react-toastify';

const addVoucherSuccess = (id, voucherData) => {
    return {
        type: actionTypes.ADD_VOUCHER,
        id: id,
        voucherData: voucherData
    }
}

export const uploadImage = (eid, uid, image) => {
    return dispatch => {
        axios.post('http://localhost:8080/expense/upload/'+eid, image, {headers: {user_id: uid}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    //dispatch(addVoucherSuccess(response.data.result.expenseId, voucherData));
                    dispatch(getVoucher(uid));
                }else{
                    toast.error(response.data.message);
                }
            }).catch(err => {
                console.log(err);
            })
    }
}

export const addVoucher = (voucherData, userID, image) => {
    return dispatch => {
        axios.post('http://localhost:8080/expense', voucherData, {headers: {user_id: userID}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    //dispatch(addVoucherSuccess(response.data.result.expenseId, voucherData));
                    dispatch(uploadImage(response.data.result.expenseId, userID, image));
                    toast.success(response.data.message);
                }else{
                    toast.error(response.data.message);
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
                    toast.error(response.data.message);
                }
                
            }).catch(err => {
                console.log(err);
            })
    }
}