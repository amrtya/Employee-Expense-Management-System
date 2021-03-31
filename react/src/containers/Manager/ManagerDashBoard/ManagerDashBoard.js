import React, { Component } from 'react';
import SingleExpense from './SingleExpense/SingleExpense';
import {connect} from 'react-redux';
import classes from './ManagerDashBoard.module.css';
import * as actions from '../../../store/actions/index';

class ManagerDashBoard extends Component {
    state = {
        // date: "",
        billNumber: "",
        billCost: "",
        remarks: "",
        status: ""
    }
    // onDateChange = (event) => {
    //     this.setState({date: event.target.value});
    // }
    onBillChange = (event) => {
        this.setState({billNumber: event.target.value});
    }
    onCostChange = (event) => {
        this.setState({billCost: event.target.value});
    }
    onRemarksChange = (event) => {
        this.setState({remarks: event.target.value});
    }
    onStatusChange = (event) => {
        this.setState({status: event.target.value});
    }
    updateState = (data) => {
        this.setState({
            // data: data.datedOn,
            billNumber: data.billNumber,
            billCost: data.billCost,
            remarks: data.remark,
            status: data.status
        })
    }
    updateExpense = () => {
        const newVoucher = {
            ...this.props.singleVoucher,
            billCost: this.state.billCost,
            billNumber: this.state.billNumber,
            remark: this.state.remarks,
            status: this.state.status
        }
        this.props.updateVoucher(this.props.id, newVoucher);
    }
    render() {
        return (
            <div className={classes.Expense}>
                <div className={classes.expenseLeft}>
                    {this.props.vouchers.map(voucher => (
                        <SingleExpense billN={voucher.billNumber}
                            amt={voucher.billCost}
                            date={voucher.datedOn}
                            key={'_' + Math.random().toString(36).substr(2, 9)}
                            voucherID={voucher.expenseId}
                            updateV={this.updateState} />
                    ))}
                </div>
                <div className={classes.expenseRight} style={{display: this.props.editValid}}>
                    <div className={classes.addExpense}>
                        <h2>Edit Expense</h2>
                        <img alt="profile" src="https://t4.ftcdn.net/jpg/03/46/93/61/360_F_346936114_RaxE6OQogebgAWTalE1myseY1Hbb5qPM.jpg" />
                        {/* <input type="date" placeholder="Select Date"
                                onChange={this.onDateChange}
                                value={this.state.date} /> */}
                        <input type="text" placeholder="Bill Number"
                                onChange={this.onBillChange}
                                value={this.state.billNumber} />
                        <input type="text" placeholder="Bill Cost"
                                onChange={this.onCostChange}
                                value={this.state.billCost} />
                        <select name="status" 
                            value={this.state.status}
                            onChange={this.onStatusChange} >
                            <option value="NOT_REIMBURSED">NOT_REIMBURSED</option>
                            <option value="REIMBURSED">REIMBURSED</option>
                        </select>
                        <input type="text" placeholder="Remarks"
                                onChange={this.onRemarksChange}
                                value={this.state.remarks} />
                        <button type="button" onClick={this.updateExpense}>Update</button>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStatetoProps = state => {
    return {
        id: state.user.id,
        vouchers: state.manager.vouchers,
        editValid: state.manager.editValid,
        singleVoucher: state.manager.singleVoucher
    }
}

const mapDispatchtoProps = dispatch => {
    return {
        updateVoucher: (mid, data) => dispatch(actions.managerUpdateVoucher(mid, data))
    }
}
 
export default connect(mapStatetoProps, mapDispatchtoProps)(ManagerDashBoard);