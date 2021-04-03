import React, { Component } from 'react';
import ManagerDashBoard from './ManagerDashBoard/ManagerDashBoard';
import ManagerHeader from './ManagerHeader/ManagerHeader';
import * as actions from '../../store/actions/index';
import {connect} from 'react-redux';

class Admin extends Component {
    componentDidMount(){
        this.props.getAllVouchers(this.props.id);
    }
    render() { 
        return (
            <div>
                <ManagerHeader />
                <ManagerDashBoard />
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
        getAllVouchers: (mid) => dispatch(actions.getAllVouchers(mid))
    }
}
 
export default connect(mapStatetoProps, mapDispatchtoProps)(Admin);