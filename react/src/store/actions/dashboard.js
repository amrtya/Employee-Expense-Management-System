import * as actionTypes from './actionTypes';
import axios from 'axios';
import * as actions from './index';

const getDetails = (data) => {
    return {
        type: actionTypes.GET_MONTH_DETAILS,
        data: data
    }
}

export const getMonthDetails = (month, uid) => {
    return dispatch => {
        axios.get("http://localhost:8080/expense/dashboard/"+month, {headers: {user_id: uid}})
            .then(response => {
                console.log(response.data);
                if(response.data.responseType === "SUCCESS"){
                    dispatch(getDetails(response.data.result));
                }else{
                    alert(response.data.message);
                }
            }).catch(err => {
                console.log(err);
            })
    }
}