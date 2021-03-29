import React, { Component } from 'react';

import classes from './AddUser.module.css';

class AddUser extends Component {
    state = {

    }
    render() { 
        return (
            <div className={classes.AddUser}>
                <h2>Add/Edit Details</h2>
                <input className={classes.AddUserInput} type="text" placeholder="Name" />
                <input className={classes.AddUserInput} type="email" placeholder="Email" />
                <input className={classes.AddUserInput} type="tel" placeholder="Mobile Number" />
                <select name="role">
                    <option value="null">Select Role...</option>
                    <option value="user">User</option>
                    <option value="manager">Manager</option>
                    <option value="admin">Admin</option>
                </select>
                <span className={classes.radioGroup}>
                    Active? 
                    <input type="radio" name="active" value="yes" />Yes
                    <input type="radio" name="active" value="no" />No
                </span>
                <span>
                    <button type="button">Update</button>
                    <button type="button">Clear</button>
                </span>
            </div>
        );
    }
}
 
export default AddUser;