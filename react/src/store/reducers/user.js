import * as actionTypes from '../actions/actionTypes';
import { updateObject } from '../utility';

const initialState = {
    auth: false,
    id: "",
    role: "",
    loading: false,
    username: ""
};

const reducer = (state=initialState, action) => {
    switch(action.type){
        case actionTypes.PAGE_RELOAD:
            return updateObject(state, {auth: action.auth, id: action.id, role: action.role, username: action.username})
        case actionTypes.USER_SIGNUP_SUCCESS:
            return updateObject(state, {auth: true, id: action.id, role: "USER", loading: false, username: action.username});
        case actionTypes.USER_LOGIN_SUCCESS:
            return updateObject(state, {auth: true, id: action.id, role: action.role, loading: false, username: action.username});
        case actionTypes.STARTLOADING:
            return updateObject(state, {loading: true});
        case actionTypes.ENDLOADING:
            return updateObject(state, {loading: false});
        default:
            return state;
    }
}
 
export default reducer;