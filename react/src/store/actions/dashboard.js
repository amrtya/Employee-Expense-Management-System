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
        console.log("http://localhost:8080/dashboard/"+month);
        console.log(uid);
        axios.get("http://localhost:8080/dashboard/"+month, {headers: {user_id: uid}})
            .then(response => {
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