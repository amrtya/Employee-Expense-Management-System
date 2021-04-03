import * as actionTypes from './actionTypes';
import axios from 'axios';
// import * as actions from './index';
import {toast} from 'react-toastify';

const getDetails = (data) => {
    return {
        type: actionTypes.GET_MONTH_DETAILS,
        data: data
    }
}

export const getMonthDetails = (month, uid) => {
    return dispatch => {
        axios.get("/expense/dashboard/"+month, {headers: {userId: uid}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    dispatch(getDetails(response.data.result));
                }else{
                    toast.error(response.data.message);
                }
            }).catch(err => {
                console.log(err);
                toast.error("Unknown Error occured.")
            })
    }
}