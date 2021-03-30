import classes from './AdminDashboard.module.css';
import React, { Component } from 'react';
import AllUsers from './AllUsers/AllUsers';
import AddUser from './AddUser/AddUser';

class AdminDashboard extends Component {
    state = {
        search: ""
    }
    onSearchUpdate = (event) => {
        const val = event.target.value;
        this.setState({search: val});
    }
    render() { 
        return (
            <div className={classes.dashBoard}>
                <div className={classes.dashHead}>
                    <input type="text" placeholder="Type here to search"
                            onChange={this.onSearchUpdate}
                            value={this.state.search} />
                    <button type="button">Search</button>
                </div>
                <div className={classes.dashTail}>
                    <div className={classes.usertable}><AllUsers query={this.state.search} /></div>
                    <AddUser />
                </div>
            </div>
        );
    }
}
 
export default AdminDashboard;