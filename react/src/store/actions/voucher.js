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

export const uploadImage = (eid, uid, image, voucherData=null) => {
    return dispatch => {
        let data = new FormData();
        data.append("receipt_image", image);

        axios.post('/expense/upload/'+eid, data, {headers: {user_id: uid}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    if(voucherData){
                        dispatch(addVoucherSuccess(eid, voucherData));
                    }else{
                        dispatch(getVoucher(uid));
                    }
                }else{
                    if(voucherData){
                        dispatch(addVoucherSuccess(eid, voucherData));
                    }else{
                        dispatch(getVoucher(uid));
                    }
                    toast.error(response.data.message);
                }
            }).catch(err => {
                if(voucherData){
                    dispatch(addVoucherSuccess(eid, voucherData));
                }else{
                    dispatch(getVoucher(uid));
                }
                console.log(err);
                // toast.error("Unknown Error occured.")
            })
    }
}

export const addVoucher = (voucherData, userID, image) => {
    return dispatch => {
        axios.post('/expense', voucherData, {headers: {user_id: userID}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    dispatch(uploadImage(response.data.result.expenseId, userID, image, voucherData));
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

const getVoucherInStore = (vouchers) => {
    return {
        type: actionTypes.GET_VOUCHER,
        vouchers: vouchers
    }
}

export const getVoucher = (userID) => {
    return dispatch => {
        axios.get('/expense', {headers: {user_id: userID}})
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