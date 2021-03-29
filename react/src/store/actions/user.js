import * as actionTypes from './actionTypes';
import axios from 'axios';

const signUpSuccess = (email) => {
    localStorage.setItem('auth', true);
    localStorage.setItem('email', email);
    localStorage.setItem('role', "USER");
    return {
        type: actionTypes.USER_SIGNUP_SUCCESS,
        email: email
    }
}

export const onSignUp = (signupdata) => {
    return dispatch => {
        axios.post('http://localhost:8080/signup', signupdata)
            .then(response => {
                console.log(response);
                dispatch(signUpSuccess(signupdata.email));
            }).catch(err => {
                console.log(err);
                dispatch(signUpSuccess(signupdata.email));
            })
    }
}

export const onPageReload = (auth, email, role) => {
    return {
        type: actionTypes.PAGE_RELOAD,
        auth: auth,
        email: email,
        role: role
    }
}

const loginSuccess = (email, role) => {
    localStorage.setItem('auth', true);
    localStorage.setItem('email', email);
    localStorage.setItem('role', role);
    return {
        type: actionTypes.USER_LOGIN_SUCCESS,
        email: email,
        role: role
    }
}
export const onLogin = (logindata) => {
    return dispatch => {
        axios.post('http://localhost:8080/login', logindata)
            .then(response => {
                console.log(response);
                dispatch(loginSuccess(logindata.email, "USER"));  //get the role from response
            }).catch(err => {
                console.log(err);
                dispatch(loginSuccess(logindata.email, "USER"));
            })
    }
}