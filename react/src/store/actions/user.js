import * as actionTypes from './actionTypes';
import axios from 'axios';
import * as actions from './index';
import {toast} from 'react-toastify';

const signUpSuccess = (id, username) => {
    localStorage.setItem('auth', true);
    localStorage.setItem('id', id);
    localStorage.setItem('role', "USER");
    localStorage.setItem('username', username);
    return {
        type: actionTypes.USER_SIGNUP_SUCCESS,
        id: id,
        username: username
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
        axios.post('/signup', signupdata)
            .then(response => {
                console.log(response.data);
                if(response.data.responseType === "SUCCESS"){
                    dispatch(signUpSuccess(response.data.result.userId, response.data.result.username));
                    toast.success(response.data.message);
                }else{
                    dispatch(endloading());
                    toast.error(response.data.message);
                }
            }).catch(err => {
                dispatch(endloading());
                console.log(err);
                toast.error("Unknown Error occured.")
            })
    }
}

export const onPageReload = (auth, id, role, username) => {
    return {
        type: actionTypes.PAGE_RELOAD,
        auth: auth,
        id: id,
        role: role,
        username: username
    }
}

const loginSuccess = (id, role, username) => {
    localStorage.setItem('auth', true);
    localStorage.setItem('id', id);
    localStorage.setItem('role', role);
    localStorage.setItem('username', username)
    return {
        type: actionTypes.USER_LOGIN_SUCCESS,
        id: id,
        role: role,
        username: username
    }
}
export const onLogin = (logindata) => {
    return dispatch => {
        dispatch(signupstart());
        axios.post('/login', logindata)
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    dispatch(loginSuccess(response.data.result.userId, response.data.result.role, response.data.result.username));
                    toast.success(response.data.message);
                }else{
                    dispatch(endloading());
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
        axios.put("/expense/"+eid, data, {headers: {userId: uid}})
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