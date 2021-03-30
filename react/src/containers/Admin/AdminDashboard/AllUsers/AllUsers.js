import React from 'react';
import classes from './AllUsers.module.css';
import SingleUser from './SingleUser/SingleUser';
import {connect} from 'react-redux';

const AllUsers = (props) => {
    return (
        <div>
            <div className={classes.tableHeader}>
                <p>Sl.No.</p>
                <p>Name</p>
                <p>Email</p>
                <p>Role</p>
                <p>Option</p>
            </div>
            <div className={classes.divider}></div>
            <div className={classes.tableUsers}>
                {props.users.map(user => {
                    return (
                        user.username.includes(props.query)?
                        (<SingleUser count="1" 
                        name={user.username} email={user.email}
                        key={user.userId} role={user.role}
                        id={user.userId} />):null
                    )
                })}
            </div>
        </div>
    );
}

const mapStatetoProps = state => {
    return {
        users: state.admin.users
    }
}
 
export default connect(mapStatetoProps)(AllUsers);