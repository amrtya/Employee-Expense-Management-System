import React from 'react';
import { Link } from 'react-router-dom';

import classes from './LandingPage.module.css';

const LandingPage = () => {
    return (
        <div>
            <div className={classes.header}>
                <h1>Expense Manager</h1>
            </div>
            <div className={classes.LandingPage}>
                <div className={classes.card}>
                    <h2>LOGIN</h2>
                    <p>If you have an account created click below to log in</p>
                    <Link to="/login"><button type="button" className={classes.login}>LogIn</button></Link>
                </div>
                <div className={classes.card}>
                    <h2>SIGNUP</h2>
                    <p>If you don't have an account use the below button to create one</p>
                    <Link to="/signup"><button type="button" className={classes.signup}>SignUp</button></Link>
                </div>
            </div>
        </div>
    );
}
 
export default LandingPage;