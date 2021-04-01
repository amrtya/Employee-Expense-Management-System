import React, {Component} from 'react';

import classes from './SingleExpense.module.css';
import {connect} from 'react-redux';
import * as actions from '../../../../store/actions/index';
import * as actionTypes from '../../../../store/actions/actionTypes';

class SingleExpense extends Component {
    deleteVoucherHandler = (event) => {
        event.stopPropagation();
        this.props.onDelVoucher(this.props.id, this.props.voucherID);
    }

    updateVoucherHandler = (event) => {
        event.stopPropagation();
        const vid = this.props.voucherID;
        const sV = this.props.vouchers.filter(voucher => voucher.expenseId===vid)[0];
        this.props.onManagerVoucherUpdate(sV);
        this.props.onEdit();
        this.props.updateV(sV);
    }
    render() {
        let colr = {backgroundColor: "white"};
        if(this.props.stat === "REIMBURSED"){
            colr = {backgroundColor: "lightgreen"}
        } 
        return (
            <div className={classes.SingleExpense} style={colr} onClick={this.props.clik}>
                <img alt="profile" src="https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg" />
                <p>{this.props.billN}</p>
                <p>{this.props.date}</p>
                <p>₹ {this.props.amt}</p>
                <i className="fa fa-edit" onClick={this.updateVoucherHandler}></i>
                <i className="fa fa-trash" onClick={(event) => this.deleteVoucherHandler(event)}></i>
            </div>
        );
    }
}

const mapStatetoProps = state => {
    return {
        id: state.user.id,
        vouchers: state.manager.vouchers
    }
}

const mapDispatchtoProps = dispatch => {
    return {
        onDelVoucher: (mid, eid) => dispatch(actions.deleteVoucher(mid, eid)),
        onEdit: () => dispatch({type: actionTypes.UPDATE_EDIT}),
        onManagerVoucherUpdate: (data) => dispatch({type: actionTypes.MANAGER_UPDATE_VOUCHER, data:data})
    }
}
 
export default connect(mapStatetoProps, mapDispatchtoProps)(SingleExpense);