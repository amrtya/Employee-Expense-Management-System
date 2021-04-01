import React from 'react';
import classes from './AllUsers.module.css';
import SingleUser from './SingleUser/SingleUser';
import {connect} from 'react-redux';

const AllUsers = (props) => {
    const newUsers = props.users.filter(user => user.userId!==props.id);
    return (
        <div className={classes.AllUser}>
            <div className={classes.tableHeader}>
                <p>Sl.No.</p>
                <p>Name</p>
                <p>Email</p>
                <p>Role</p>
                <p>Option</p>
            </div>
            <div className={classes.divider}></div>
            <div className={classes.tableUsers}>
                {newUsers.map((user, index) => {
                    return (
                        user.username.includes(props.query)?
                        (<SingleUser count={index+1} 
                        name={user.username} email={user.email}
                        key={user.userId} role={user.role}
                        id={user.userId}
                        clicked={props.clicked} />):null
                    )
                })}
            </div>
        </div>
    );
}

const mapStatetoProps = state => {
    return {
        users: state.admin.users,
        id: state.user.id
    }
}
 
export default connect(mapStatetoProps)(AllUsers);