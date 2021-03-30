import * as actionTypes from './actionTypes';
import axios from 'axios';

const signUpSuccess = (id) => {
    localStorage.setItem('auth', true);
    localStorage.setItem('id', id);
    localStorage.setItem('role', "USER");
    return {
        type: actionTypes.USER_SIGNUP_SUCCESS,
        id: id
    }
}

export const onSignUp = (signupdata) => {
    return dispatch => {
        axios.post('http://localhost:8080/signup', signupdata)
            .then(response => {
                console.log(response.data);
                if(response.data.responseType === "SUCCESS"){
                    dispatch(signUpSuccess(response.data.result.userId));
                }else{
                    console.log(response.data.message);
                }
            }).catch(err => {
                console.log(err);
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
        axios.post('http://localhost:8080/login', logindata)
            .then(response => {
                console.log(response.data);
                if(response.data.responseType === "SUCCESS"){
                    dispatch(loginSuccess(response.data.result.userId, response.data.result.role));
                }else{
                    console.log(response.data.message);
                }
            }).catch(err => {
                console.log(err);
            })
    }
}