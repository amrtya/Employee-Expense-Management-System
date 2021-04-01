import * as actionTypes from '../actions/actionTypes';
import { updateObject } from '../utility';

const initialState = {
    users: [],
    singleUser: {
        id: "",
        username: "",
        email: "",
        mobileNumber: "",
        password: "",
        role: "USER",
        active: "yes"
    }
};

const reducer = (state=initialState, action) => {
    switch(action.type){
        case actionTypes.GET_ALL_USERS:
            return updateObject(state, {
                users: action.users
            })
        case actionTypes.DELETE_USER:
            return updateObject(state, {
                users: state.users.filter(user => user.userId!==action.id)
            })
        case actionTypes.ADD_USER:
            return updateObject(state, {
                users: state.users.concat(action.userData)
            })

        case actionTypes.UPDATE_ADMIN_ENTRY:
            let act = "yes";
            if(!action.det.active){
                act = "no";
            }
            return updateObject(state, {
                singleUser: {
                    ...state.singleUser,
                    id: action.det.userId,
                    email: action.det.email,
                    username: action.det.username,
                    mobileNumber: action.det.mobileNumber,
                    password: action.det.password,
                    role: action.det.role,
                    active: act
                }
            })
        case actionTypes.UPDATE_EMAIL:
            return updateObject(state, {
                singleUser: {
                    ...state.singleUser,
                    email: action.email
                }
            })
        case actionTypes.UPDATE_USERNAME:
            return updateObject(state, {
                singleUser: {
                    ...state.singleUser,
                    username: action.username
                }
            })
        case actionTypes.UPDATE_MOBILE:
            return updateObject(state, {
                singleUser: {
                    ...state.singleUser,
                    mobileNumber: action.mobileNumber
                }
            })
        case actionTypes.UPDATE_PASSWORD:
            return updateObject(state, {
                singleUser: {
                    ...state.singleUser,
                    password: action.password 
                }
            })
        case actionTypes.UPDATE_ROLE:
            return updateObject(state, {
                singleUser: {
                    ...state.singleUser,
                    role: action.role
                }
            })
        case actionTypes.UPDATE_ACTIVE:
            return updateObject(state, {
                singleUser: {
                    ...state.singleUser,
                    active: action.active
                }
            })
        default:
            return state;
    }
}
 
export default reducer;