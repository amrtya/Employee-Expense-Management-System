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
        axios.get('/manager', {headers: {managerId: managerID}})
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
        axios.delete("/manager/expense/"+expenseID, {headers: {managerId: managerID}})
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

        axios.post('/manager/expense/upload/'+eid, data, {headers: {managerId: mid}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    dispatch(getAllVouchers(mid));
                }else{
                    dispatch(getAllVouchers(mid));
                    toast.error(response.data.message);
                }
            }).catch(err => {
                dispatch(getAllVouchers(mid));
                console.log(err);
                // toast.error("Unknown Error occured.")
            })
    }
}

export const managerUpdateVoucher = (mid, data, image) => {
    return dispatch => {
        axios.put("/manager/expense/"+data.expenseId, data, {headers: {managerId: mid}})
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