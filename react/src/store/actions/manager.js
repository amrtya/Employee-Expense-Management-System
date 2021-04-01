import * as actionTypes from './actionTypes';
import axios from 'axios';
import { toast } from '../../../node_modules/react-toastify';

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
                    toast.error(response.data.message);
                }
                
            }).catch(err => {
                console.log(err);
                toast.error("Unknown Error occured.")
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
                    toast.success(response.data.message);
                }else{
                    toast.error(response.data.message);
                }
            }).catch(err => {
                console.log(err);
                toast.error("Unknown Error occured.")
            })
    }
}

const uploadImage = (eid, uid, image, mid) => {
    return dispatch => {
        let data = new FormData();
        data.append("receipt_image", image);

        axios.post('http://localhost:8080/expense/upload/'+eid, data, {headers: {user_id: uid}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    dispatch(getAllVouchers(mid));
                }else{
                    toast.error(response.data.message);
                }
            }).catch(err => {
                console.log(err);
                // toast.error("Unknown Error occured.")
            })
    }
}

export const managerUpdateVoucher = (mid, data, image) => {
    return dispatch => {
        axios.put("http://localhost:8080/manager/expense/"+data.expenseId, data, {headers: {manager_id: mid}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    //dispatch(getAllVouchers(mid));
                    dispatch(uploadImage(data.expenseId, data.claimedBy.userId, image, mid));
                    toast.success(response.data.message);
                }else{
                    toast.error(response.data.message);
                }
            }).catch(err => {
                console.log(err);
                toast.error("Unknown Error occured.")
            })
    }
}