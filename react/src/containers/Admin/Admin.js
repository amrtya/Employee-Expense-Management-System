import React, { Component } from 'react';
import AdminDashboard from './AdminDashboard/AdminDashboard';
import AdminHeader from './AdminHeader/AdminHeader';
import * as actions from '../../store/actions/index';
import {connect} from 'react-redux';
import 'react-toastify/dist/ReactToastify.css';

class Admin extends Component {
    state = {
        check: false
    }
    componentDidMount(){
        this.props.getUsers(this.props.id);
    }
    render() { 
        return (
            <div>
                <AdminHeader />
                <AdminDashboard />
            </div>
        );
    }
}

const mapStatetoProps = state => {
    return {
        id: state.user.id
    }
}

const mapDispatchtoProps = dispatch => {
    return {
        getUsers: (adminID) => dispatch(actions.getUsers(adminID))
    }
}
 
export default connect(mapStatetoProps, mapDispatchtoProps)(Admin);