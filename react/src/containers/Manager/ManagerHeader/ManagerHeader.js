import React from 'react';

import classes from './ManagerHeader.module.css';

const delAuth = () => {
    localStorage.clear();
    window.location.href = "/";
}

const ManagerHeader = () => {
    return (
        <div className={classes.Header}>
            <div className={classes.HeaderLeft}>
                <h2>Expense Manager</h2>
            </div>
            <div className={classes.HeaderRight}>
                <p>Home</p>
                <p onClick={delAuth}>Logout</p>
            </div>
        </div>
    );
}
 
export default ManagerHeader;