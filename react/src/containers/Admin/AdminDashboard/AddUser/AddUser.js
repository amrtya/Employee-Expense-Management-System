import React, { Component } from 'react';

import classes from './AddUser.module.css';
import * as actions from '../../../../store/actions/index';
import {connect} from 'react-redux';
import * as actionTypes from '../../../../store/actions/actionTypes';
import { toast } from "react-toastify";

class AddUser extends Component {
    state = {
        // username: "",
        // email: "",
        // mobileNumber: "",
        // password: "",
        // role: "",
        // active: ""
        addState: true      //false denotes updateState
        // valid: false
    }
    // updateEmail = (event) => {
    //     const val = event.target.value;
    //     this.setState({email: val});
    // }
    // updateUsername = (event) => {
    //     const val = event.target.value;
    //     this.setState({username: val});
    // }
    // updateMobileNumber = (event) => {
    //     const val = event.target.value;
    //     this.setState({mobileNumber: val});
    // }
    // updatePassword = (event) => {
    //     const val = event.target.value;
    //     this.setState({password: val});
    // }
    // updateRole = (event) => {
    //     const val = event.target.value;
    //     this.props.user.role = val;
    // }
    clearForm = () => {
        this.props.updateEmail("");
        this.props.updateMobileNumber("");
        this.props.updateRole("");
        this.props.updateUserName("");
        this.props.updateActive("yes");
        this.props.clicked();
    }
    checkValidity = () => {
        let isValid = true;
        isValid = isValid && this.props.user.username!=="";
        isValid = isValid && this.props.user.email!=="";
        isValid = isValid && this.props.user.role!=="";
        if(this.props.formState){
            isValid = isValid && this.props.user.password!=="";
        }
        isValid = isValid && this.props.user.username!=="";
        return isValid;
    }
    updateUser = () => {
        let act = true;
        if(this.props.user.active === "no"){
            act = false;
        }
        if(this.checkValidity()){
            this.props.updateUser(this.props.id, this.props.user.id, {
                username: this.props.user.username,
                email: this.props.user.email,
                mobileNumber: this.props.user.mobileNumber,
                role: this.props.user.role,
                password: this.props.user.password,
                active: act
            });
            this.clearForm();
        }else{
            toast.error("One or more fields are empty. Fill them and try again")
        }
    }
    addUser = () => {
        let act = true;
        if(this.props.user.active === "no"){
            act = false;
        }
        if(this.checkValidity()){
            this.props.addUser(this.props.id, {
                username: this.props.user.username,
                email: this.props.user.email,
                mobileNumber: this.props.user.mobileNumber,
                role: this.props.user.role,
                active: act,
                password: this.props.user.password
            });
            this.clearForm();
        }else{
            toast.error("One or more fields are empty. Fill them and try again")
        }
    }
    clickHandler = () => {
        if(this.props.formState){
            this.addUser();
        }else{
            this.updateUser();
        }
    }
    render() {
        return (
            <div className={classes.AddUser}>
                <h2>Add/Edit Details</h2>
                <input className={classes.AddUserInput} 
                    type="text" placeholder="Name"
                    value={this.props.user.username}
                    onChange={(event) => this.props.updateUserName(event.target.value)} />
                <input className={classes.AddUserInput} 
                    type="email" placeholder="Email"
                    value={this.props.user.email}
                    onChange={(event) => this.props.updateEmail(event.target.value)} />
                {this.props.formState? <input className={classes.AddUserInput} 
                    type="password" placeholder="Password"
                    value={this.props.user.password}
                    onChange={(event) => this.props.updatePassword(event.target.value)} /> : null}
                <input className={classes.AddUserInput} 
                    type="tel" placeholder="Mobile Number"
                    value={this.props.user.mobileNumber}
                    onChange={(event) => this.props.updateMobileNumber(event.target.value)} />
                <select name="role" 
                    value={this.props.user.role}
                    onChange={(event) => this.props.updateRole(event.target.value)} >
                    <option value="USER" selected={true}>User</option>
                    <option value="MANAGER">Manager</option>
                    <option value="ADMIN">Admin</option>
                </select>
                <span className={classes.radioGroup}>
                    Active? 
                    <input type="radio" name="active" value="yes" checked={this.props.user.active==="yes"}
                        onChange={(event) => this.props.updateActive(event.target.value)}  />Yes
                    <input type="radio" name="active" value="no" checked={this.props.user.active==="no"}
                        onChange={(event) => this.props.updateActive(event.target.value)} />No
                </span>
                <span>
                    <button type="button" onClick={this.clickHandler}>{this.props.formState?"Add":"Update"}</button>
                    <button type="button" onClick={this.clearForm}>Clear</button>
                </span>
            </div>
        );
    }
}

const mapStatetoProps = state => {
    return {
        id: state.user.id,
        user: state.admin.singleUser
    }
}

const mapDispatchtoProps = dispatch => {
    return {
        updateEmail: (email) => dispatch({type: actionTypes.UPDATE_EMAIL, email: email}),
        updateUserName: (username) => dispatch({type: actionTypes.UPDATE_USERNAME, username: username}),
        updateMobileNumber: (mn) => dispatch({type: actionTypes.UPDATE_MOBILE, mobileNumber: mn}),
        updateRole: (role) => dispatch({type: actionTypes.UPDATE_ROLE, role: role}),
        updatePassword: (password) => dispatch({type: actionTypes.UPDATE_PASSWORD, password: password}),
        updateActive: (active) => dispatch({type: actionTypes.UPDATE_ACTIVE, active: active}),
        updateUser: (aid, uid, data) => dispatch(actions.updateUser(aid, uid, data)),
        addUser: (aid, data) => dispatch(actions.addUser(aid, data))
    }
}
 
export default connect(mapStatetoProps, mapDispatchtoProps)(AddUser);