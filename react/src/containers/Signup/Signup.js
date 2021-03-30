import React, { Component } from 'react';
import classes from './Signup.module.css';
import {Link} from 'react-router-dom';
import { connect } from 'react-redux';
import * as actions from '../../store/actions/index';

class Signup extends Component {
    state = {
        email: "",
        username: "",
        mobileNumber: "",
        password: "",
        conPassword: "",
        role: "USER",
        active: true,
        valid: false
    }
    checkValidity = (email, username, mobileNumber, password, conPassword) => {
        let isValid = true;
        isValid = isValid && email!=="";
        isValid = isValid && username!=="";
        isValid = isValid && mobileNumber.length===10;
        isValid = isValid && password!=="";
        isValid = isValid && password===conPassword;
        this.setState({valid: isValid});
    }
    updateEmail = (event) => {
        const val = event.target.value;
        this.setState({email: val});
        this.checkValidity(val, this.state.username, this.state.mobileNumber, this.state.password, this.state.conPassword);
    }
    updateUsername = (event) => {
        const val = event.target.value;
        this.setState({username: val});
        this.checkValidity(this.state.email, val, this.state.mobileNumber, this.state.password, this.state.conPassword);
    }
    updateMobileNumber = (event) => {
        const val = event.target.value;
        this.setState({mobileNumber: val});
        this.checkValidity(this.state.email, this.state.username, val, this.state.password, this.state.conPassword);
    }
    updatePassword = (event) => {
        const val = event.target.value;
        this.setState({password: val});
        this.checkValidity(this.state.email, this.state.username, this.state.mobileNumber, val, this.state.conPassword);
    }
    updateConpassword = (event) => {
        const val = event.target.value;
        this.setState({conPassword: val});
        this.checkValidity(this.state.email, this.state.username, this.state.mobileNumber, this.state.password, val);
    }
    signUpHandler = () => {
        const signupdata = {
            email: this.state.email,
            password: this.state.password,
            username: this.state.username,
            mobileNumber: this.state.mobileNumber,
            active: true,
            role: "ADMIN"
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
                                value={this.state.email} />
                        <input type="text" placeholder="Enter username"
                                onChange={this.updateUsername}
                                value={this.state.username} />
                        <input type="tel" placeholder="Enter Mobile Number"
                                onChange={this.updateMobileNumber}
                                value={this.state.mobileNumber} />
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