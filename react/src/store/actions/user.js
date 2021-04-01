import * as actionTypes from './actionTypes';
import axios from 'axios';
import * as actions from './index';
import {toast} from 'react-toastify';

const signUpSuccess = (id) => {
    localStorage.setItem('auth', true);
    localStorage.setItem('id', id);
    localStorage.setItem('role', "USER");
    return {
        type: actionTypes.USER_SIGNUP_SUCCESS,
        id: id
    }
}

const signupstart = () => {
    return {
        type: actionTypes.STARTLOADING
    }
}
const endloading = () => {
    return {
        type: actionTypes.ENDLOADING
    }
}

export const onSignUp = (signupdata) => {
    return dispatch => {
        dispatch(signupstart());
        axios.post('http://localhost:8080/signup', signupdata)
            .then(response => {
                console.log(response.data);
                if(response.data.responseType === "SUCCESS"){
                    dispatch(signUpSuccess(response.data.result.userId));
                    toast.success(response.data.message);
                }else{
                    toast.error(response.data.message);
                }
            }).catch(err => {
                dispatch(endloading());
                console.log(err);
                toast.error("Unknown Error occured.")
            })
    }
}

export const onPageReload = (auth, id, role) => {
    return {
        type: actionTypes.PAGE_RELOAD,
        auth: auth,
        id: id,
        role: role
    }
}

const loginSuccess = (id, role) => {
    localStorage.setItem('auth', true);
    localStorage.setItem('id', id);
    localStorage.setItem('role', role);
    return {
        type: actionTypes.USER_LOGIN_SUCCESS,
        id: id,
        role: role
    }
}
export const onLogin = (logindata) => {
    return dispatch => {
        dispatch(signupstart());
        axios.post('http://localhost:8080/login', logindata)
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    dispatch(loginSuccess(response.data.result.userId, response.data.result.role));
                    toast.success(response.data.message);
                }else{
                    toast.error(response.data.message);
                }
            }).catch(err => {
                dispatch(endloading());
                console.log(err);
                toast.error("Unknown Error occured.")
            })
    }
}

export const userUpdateVoucher = (uid, eid, data, image) => {
    return dispatch => {
        axios.put("http://localhost:8080/expense/"+eid, data, {headers: {user_id: uid}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    dispatch(actions.uploadImage(eid, uid, image));
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