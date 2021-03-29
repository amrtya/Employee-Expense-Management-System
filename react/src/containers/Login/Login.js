import React, { Component } from 'react';
import classes from './Login.module.css';
import {Link} from 'react-router-dom';

class Login extends Component {
    state = {
        email: "",
        password: "",
        valid: false
    }
    checkValidity = (email, password) => {
        let isValid = true;
        isValid = isValid && email!=="";
        isValid = isValid && password!=="";
        this.setState({valid: isValid});
    }
    updateEmail = (event) => {
        const val = event.target.value;
        this.setState({email: val});
        this.checkValidity(val, this.state.password);
    }
    updatePassword = (event) => {
        const val = event.target.value;
        this.setState({password: val});
        this.checkValidity(this.state.email, val);
    }
    storeData = () => {
        this.props.updateAuth();
        localStorage.setItem('document',JSON.stringify(this.state));
        localStorage.setItem('auth', true);
    }
    componentDidMount(){
        const newState = JSON.parse(localStorage.getItem('document'));
        if (localStorage.getItem('document')) {
            this.setState({
                email: newState.email,
                password: newState.password,
                valid: newState.valid
            })
        }else{
            this.setState({
                email: "",
                password: "",
                valid: false
            })
        }
    }
    render() { 
        return (
            <div>
                <div className={classes.header}>
                    <h1>Expense Manager</h1>
                </div>
                <div className={classes.Login}>
                    <div className={classes.logincard}>
                        <h1>LogIn</h1>
                        <input type="email" placeholder="Enter Email"
                                onChange={this.updateEmail}
                                value={this.state.email} />
                        <input type="password" placeholder="Enter Password"
                                onChange={this.updatePassword}
                                value={this.state.password} />
                        <button disabled={!this.state.valid} type="button"
                            onClick={this.storeData}>Log In</button>
                        <p>New User? <Link to="/signup">Sign Up</Link></p>
                    </div>
                </div>
            </div>
        );
    }
}
 
export default Login;