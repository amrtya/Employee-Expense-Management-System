import classes from './AdminDashboard.module.css';
import React, { Component } from 'react';
import AllUsers from './AllUsers/AllUsers';
import AddUser from './AddUser/AddUser';

class AdminDashboard extends Component {
    state = {
        search: "",
        addState: true
    }
    onSearchUpdate = (event) => {
        const val = event.target.value;
        this.setState({search: val});
    }
    formUpdateState = () => {
        this.setState({addState: false});
    }
    formAddState = () => {
        this.setState({addState: true});
    }
    render() { 
        return (
            <div className={classes.dashBoard}>
                <div className={classes.dashHead}>
                    <input type="text" placeholder="Type here to search"
                            onChange={this.onSearchUpdate}
                            value={this.state.search} />
                    {/* <button type="button">Search</button> */}
                </div>
                <div className={classes.dashTail}>
                    <div className={classes.usertable}><AllUsers clicked={this.formUpdateState} query={this.state.search} /></div>
                    <AddUser clicked={this.formAddState} formState={this.state.addState} />
                </div>
            </div>
        );
    }
}
 
export default AdminDashboard;