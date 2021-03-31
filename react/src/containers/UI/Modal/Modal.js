import React, { Component } from 'react';
import classes from './Modal.module.css';
import {connect} from 'react-redux';

class Modal extends Component {
    render() {
        let voucher = null;
        if(this.props.role === "USER"){
            voucher = this.props.userVouchers.filter(voucher => voucher.expenseId===this.props.vid)[0];
        }else{
            voucher = this.props.managerVouchers.filter(voucher => voucher.expenseId===this.props.vid)[0];
        }
        return (
            <div style={this.props.style} className={classes.Modal}>
                <h2>Bill Number: {voucher.billNumber}</h2>
                <p>Bill Cost: {voucher.billCost}</p>
                <p>Dated on: {voucher.datedOn}</p>
                <p>Status: {voucher.status}</p>
                <p>Remarks: {voucher.remark}</p>
                <button type="button" onClick={this.props.close}>Close</button>
            </div>
        );
    }
}

const mapStatetoProps = state => {
    return {
        userVouchers: state.voucher.vouchers,
        managerVouchers: state.manager.vouchers,
        role: state.user.role
    }
}
 
export default connect(mapStatetoProps)(Modal);