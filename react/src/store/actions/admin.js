import * as actionTypes from './actionTypes';
import axios from 'axios';

const storeUsers = (users) => {
    return {
        type: actionTypes.GET_ALL_USERS,
        users: users
    }
}

export const getUsers = (adminID) => {
    return dispatch => {
        axios.get("http://localhost:8080/admin", {headers: {admin_id: adminID}})
            .then(response => {
                if(response.data.responseType==="SUCCESS"){
                    dispatch(storeUsers(response.data.results))
                }else{
                    console.log(response.data.message);
                }
            }).catch(err => {
                console.log(err);
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
        axios.delete("http://localhost:8080/admin/user/"+userID, {headers: {admin_id: adminID}})
            .then(response => {
                dispatch(delUser(userID));
                console.log(response.data.message);
            }).catch(err => {
                console.log(err);
            })
    }
}

export const updateUser = (adminID, userID, userData) => {
    return dispatch => {
        axios.put("http://localhost:8080/admin/user/"+userID, userData, {headers: {admin_id: adminID}})
            .then(response => {
                dispatch(getUsers(adminID));
                console.log(response.data.message);
            }).catch(err => {
                console.log(err);
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
        axios.post("http://localhost:8080/admin/user", userData, {headers: {admin_id: adminID}})
            .then(response => {
                console.log(response);
                if(response.data.responseType === "SUCCESS"){
                    const newUser = {
                        ...userData,
                        userId: response.data.result.userId
                    };
                    dispatch(addUsertoStore(newUser));
                }else{
                    alert(response.data.message);
                }
            }).catch(err => {
                console.log(err);
            })
    }
}