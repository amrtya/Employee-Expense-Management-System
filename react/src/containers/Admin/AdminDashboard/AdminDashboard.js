import classes from './AdminDashboard.module.css';
import React, { Component } from 'react';

class AdminDashboard extends Component {
    state = {

    }
    render() { 
        return (
            <div className={classes.dashBoard}>
                <div className={classes.dashHead}>
                    <input type="text" placeholder="Type here to search" />
                    <button type="button">Search</button>
                </div>
                <div>
                    
                </div>
            </div>
        );
    }
}
 
export default AdminDashboard;