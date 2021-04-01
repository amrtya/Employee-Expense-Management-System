import React, {Component} from 'react';

import classes from './SingleUser.module.css';
import * as actions from '../../../../../store/actions/index';
import {connect} from 'react-redux';
import * as actionTypes from '../../../../../store/actions/actionTypes';


class SingleUser extends Component {
    updateUser = (user_id) => {
        this.props.clicked();
        const sUser = this.props.users.filter(user => user.userId===user_id)[0];
        this.props.updateAdminEntry(sUser);
    }
    render() { 
        return (
            <div className={classes.SingleUser}>
                <p>{this.props.count}</p>
                <p>{this.props.name}</p>
                <p>{this.props.email}</p>
                <p>{this.props.role}</p>
                <p>
                    <i className="fa fa-edit" onClick={() => this.updateUser(this.props.id)}></i>
                    <i className="fa fa-trash" onClick={() => this.props.deleteUser(this.props.userID, this.props.id)}></i>
                </p>
            </div>
        );
    }
}

const mapStatetoProps = state => {
    return {
        userID: state.user.id,
        users: state.admin.users
    }
}

const mapDispatchtoProps = dispatch => {
    return {
        deleteUser: (adminID, userID) => dispatch(actions.deleteUser(adminID, userID)),
        updateAdminEntry: (det) => dispatch({type: actionTypes.UPDATE_ADMIN_ENTRY, det: det})
    }
}
 
export default connect(mapStatetoProps, mapDispatchtoProps)(SingleUser);