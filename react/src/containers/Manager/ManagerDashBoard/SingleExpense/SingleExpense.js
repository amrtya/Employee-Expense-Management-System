import React, {Component} from 'react';

import classes from './SingleExpense.module.css';
import {connect} from 'react-redux';
import * as actions from '../../../../store/actions/index';

class SingleExpense extends Component {
    deleteVoucherHandler = () => {
        this.props.onDelVoucher(this.props.id, this.props.voucherID);
    }
    render() { 
        return (
            <div className={classes.SingleExpense}>
                <img alt="profile" src="https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg" />
                <p>{this.props.billN}</p>
                <p>{this.props.date}</p>
                <p>₹ {this.props.amt}</p>
                <i className="fa fa-edit"></i>
                <i className="fa fa-trash" onClick={this.deleteVoucherHandler}></i>
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
        onDelVoucher: (mid, eid) => dispatch(actions.deleteVoucher(mid, eid))
    }
}
 
export default connect(mapStatetoProps, mapDispatchtoProps)(SingleExpense);