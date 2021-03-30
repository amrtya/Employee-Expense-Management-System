import * as actionTypes from '../actions/actionTypes';
import { updateObject } from '../utility';

const initialState = {
    auth: false,
    id: "",
    role: ""
};

const reducer = (state=initialState, action) => {
    switch(action.type){
        case actionTypes.PAGE_RELOAD:
            return updateObject(state, {auth: action.auth, id: action.id, role: action.role})
        case actionTypes.USER_SIGNUP_SUCCESS:
            return updateObject(state, {auth: true, id: action.id, role: "USER"});
        case actionTypes.USER_LOGIN_SUCCESS:
            return updateObject(state, {auth: true, id: action.id, role: action.role});
        default:
            return state;
    }
}
 
export default reducer;