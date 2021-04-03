import * as actionTypes from './actionTypes';
import axios from 'axios';
import {toast} from 'react-toastify';

const storeUsers = (users) => {
    return {
        type: actionTypes.GET_ALL_USERS,
        users: users
    }
}

export const getUsers = (adminID) => {
    return dispatch => {
        axios.get("/admin", {headers: {adminId: adminID}})
            .then(response => {
                if(response.data.responseType==="SUCCESS"){
                    dispatch(storeUsers(response.data.results));
                }else{
                    toast.error(response.data.message);
                }
            }).catch(err => {
                console.log(err);
                toast.error("Unknown Error occured.")
            })
    }
}

const delUser = (userID) => {
    return {
        type: actionTypes.DELETE_USER,
        id: userID
    }
}

export const deleteUser = (adminID, userID) => {
    return dispatch => {
        axios.delete("/admin/user/"+userID, {headers: {adminId: adminID}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    dispatch(delUser(userID));
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

export const updateUser = (adminID, userID, userData) => {
    return dispatch => {
        axios.put("/admin/user/"+userID, userData, {headers: {adminId: adminID}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    dispatch(getUsers(adminID));
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

const addUsertoStore = (userData) => {
    return {
        type: actionTypes.ADD_USER,
        userData: userData
    }
}

export const addUser = (adminID, userData) => {
    return dispatch => {
        axios.post("/admin/user", userData, {headers: {adminId: adminID}})
            .then(response => {
                if(response.data.responseType === "SUCCESS"){
                    const newUser = {
                        ...userData,
                        userId: response.data.result.userId
                    };
                    dispatch(addUsertoStore(newUser));
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