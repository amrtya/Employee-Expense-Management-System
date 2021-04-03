import React, { Component } from 'react';
import classes from './Signup.module.css';
import {Link} from 'react-router-dom';
import { connect } from 'react-redux';
import * as actions from '../../store/actions/index';
import md5 from 'crypto-js/md5';
import { validateEmail, validateMobileNumber } from '../../store/validators/validators';

class Signup extends Component {
    state = {
        email: {
            value: "",
            valid: true
        },
        username: "",
        mobileNumber: {
            value: "",
            valid: true
        },
        password: "",
        conPassword: "",
        role: "USER",
        active: true,
        valid: false
    }
    checkValidity = (currState) => {
        let isValid = true;
        isValid = isValid && currState.email.valid;
        isValid = isValid && currState.username!=="";
        isValid = isValid && currState.mobileNumber.valid;
        isValid = isValid && currState.password!=="";
        isValid = isValid && currState.password===currState.conPassword;
        this.setState({valid: isValid});
    }
    updateEmail = (event) => {
        const newState = {
            ...this.state,
            email: {
                ...this.state.email,
                value: event.target.value,
                valid: validateEmail(event.target.value)
            }
        }
        this.setState(newState);
        this.checkValidity(newState);
    }
    updateUsername = (event) => {
        const newState = {
            ...this.state,
            username: event.target.value
        }
        this.setState(newState);
        this.checkValidity(newState);
    }
    updateMobileNumber = (event) => {
        const newState = {
            ...this.state,
            mobileNumber: {
                ...this.state.mobileNumber,
                value: event.target.value,
                valid: validateMobileNumber(event.target.value)
            }
        }
        this.setState(newState);
        this.checkValidity(newState);
    }
    updatePassword = (event) => {
        const newState = {
            ...this.state,
            password: event.target.value
        }
        this.setState(newState);
        this.checkValidity(newState);
    }
    updateConpassword = (event) => {
        const newState = {
            ...this.state,
            conPassword: event.target.value
        }
        this.setState(newState);
        this.checkValidity(newState);
    }
    signUpHandler = () => {
        const signupdata = {
            email: this.state.email.value,
            password: md5(this.state.password).toString(),
            username: this.state.username,
            mobileNumber: this.state.mobileNumber.value,
            active: true,
            role: "USER"
        }
        this.props.onSignUp(signupdata);
    }
    render() { 
        return (
            <div>
                <div className={classes.header}>
                    <h1>Expense Manager</h1>
                </div>
                <div className={classes.Signup}>
                    <div className={classes.signupcard}>
                        <h1>SignUp</h1>
                        <input type="email" placeholder="Enter Email"
                                onChange={this.updateEmail}
                                value={this.state.email.value}
                                className={this.state.email.valid ? classes.normal : classes.red} />
                        <input type="text" placeholder="Enter username"
                                onChange={this.updateUsername}
                                value={this.state.username} />
                        <input type="tel" placeholder="Enter Mobile Number"
                                onChange={this.updateMobileNumber}
                                value={this.state.mobileNumber.value}
                                className={this.state.mobileNumber.valid ? classes.normal : classes.red} />
                        <input type="password" placeholder="Password"
                                onChange={this.updatePassword}
                                value={this.state.password} />
                        <input type="password" placeholder="Confirm Password"
                                onChange={this.updateConpassword}
                                value={this.state.conPassword} />
                        <Link to="/">
                            <button disabled={!this.state.valid} type="button"
                                    onClick={this.signUpHandler}>Sign Up</button>
                        </Link>
                        <p>Already a User?<Link to="/">Log In</Link></p>
                    </div>
                </div>
            </div>
        );
    }
}

const mapDispatchtoProps = dispatch => {
    return {
        onSignUp: (signupdata) => dispatch(actions.onSignUp(signupdata))
    }
}
 
export default connect(null, mapDispatchtoProps)(Signup);