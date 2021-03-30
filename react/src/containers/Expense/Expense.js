import classes from './Expense.module.css';
import React, { Component } from 'react';
import * as actions from '../../store/actions/index';
import { connect } from 'react-redux';
import SingleExpense from './SingleExpense/SingleExpense';

class AddExpense extends Component {
    state = {
        date: "",
        eid: "",
        amount: "",
        description: "",
        valid: false
    }
    checkValidity = (date, eid, amount, desc) => {
        let isValid = true;
        isValid = isValid && date!=="";
        isValid = isValid && eid!=="";
        isValid = isValid && amount!=="";
        isValid = isValid && desc!=="";
        this.setState({valid: isValid});
    }
    onDateChange = (event) => {
        const val = event.target.value;
        this.setState({date: val});
        this.checkValidity(val, this.state.eid, this.state.amount, this.state.description);
    }
    onIdChange = (event) => {
        const val = event.target.value;
        this.setState({eid: val});
        this.checkValidity(this.state.date, val, this.state.amount, this.state.description);
    }
    onAmountChange = (event) => {
        const val = event.target.value;
        this.setState({amount: val});
        this.checkValidity(this.state.date, this.state.eid, val, this.state.description);
    }
    onDescChange = (event) => {
        const val = event.target.value;
        this.setState({description: val});
        this.checkValidity(this.state.date, this.state.eid, this.state.amount, val);
    }
    addVoucherHandler = () => {
        const expenseData = {
            billNumber: this.state.eid,
            billCost: this.state.amount,
            datedOn: this.state.date,
            status: "NOT_REIMBURSED",
            remark: this.state.description
        }
        this.setState({
            date: "",
            eid: "",
            amount: "",
            description: "",
            valid: false
        })
        this.props.onAddVoucher(expenseData);
    }
    render() { 
        return (
            <div className={classes.Expense}>
                <div className={classes.expenseLeft}>
                    {this.props.vouchers.map(voucher => (
                        <SingleExpense id={voucher.billNumber}
                            amt={voucher.billCost}
                            date={voucher.datedOn}
                            key={voucher.expenseID} />
                    ))}
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                    <SingleExpense id="1234" amt="500" date="18-05-2000" />
                </div>
                <div className={classes.expenseRight}>
                    <div className={classes.addExpense}>
                        <h2>Add Expense</h2>
                        <img alt="profile" src="https://t4.ftcdn.net/jpg/03/46/93/61/360_F_346936114_RaxE6OQogebgAWTalE1myseY1Hbb5qPM.jpg" />
                        <input type="date" placeholder="Select Date"
                                onChange={this.onDateChange}
                                value={this.state.date} />
                        <input type="text" placeholder="Expense ID"
                                onChange={this.onIdChange}
                                value={this.state.eid} />
                        <input type="text" placeholder="Amount"
                                onChange={this.onAmountChange}
                                value={this.state.amount} />
                        <input type="text" placeholder="Description"
                                onChange={this.onDescChange}
                                value={this.state.description} />
                        <button type="button" disabled={!this.state.valid}
                                onClick={this.addVoucherHandler}>Submit</button>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStatetoProps = state => {
    return {
        vouchers: state.voucher.vouchers
    }
}

const mapDispatchtoProps = dispatch => {
    return {
        onAddVoucher: (voucherData) => dispatch(actions.addVoucher(voucherData))
    }
}
 
export default connect(mapStatetoProps, mapDispatchtoProps)(AddExpense);