import classes from './DashBoard.module.css';
import React from 'react';

const userDashboard = () => {
    return (
        <div className={classes.DashBoard}>
            <h1>DASHBOARD</h1>
            <div className={classes.details}>
                <div className={classes.detailsCard}>
                    <p>Total Expense</p>
                    <p>4300</p>
                </div>
                <div className={classes.detailsCard}>
                    <p>Pending Expense</p>
                    <p>1300</p>
                </div>
                <div className={classes.detailsCard}>
                    <p>Approved Expense</p>
                    <p>3000</p>
                </div>
            </div>
            <div className={classes.details}>
                <div className={classes.detailsCard}>
                    <p>Total Employee</p>
                    <p>570</p>
                </div>
            </div>
        </div>
    );
}
 
export default userDashboard;