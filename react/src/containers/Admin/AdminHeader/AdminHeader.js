import React from 'react';

import classes from './AdminHeader.module.css';

const AdminHeader = () => {
    return (
        <div className={classes.Header}>
            <div className={classes.HeaderLeft}>
                <h2>Expense Manager</h2>
            </div>
            <div className={classes.HeaderRight}>
                <p>Home</p>
                <p>Logout</p>
            </div>
        </div>
    );
}
 
export default AdminHeader;