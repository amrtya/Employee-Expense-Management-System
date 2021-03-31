import classes from './Expense.module.css';
import React, { Component } from 'react';
import * as actions from '../../store/actions/index';
import { connect } from 'react-redux';
import SingleExpense from './SingleExpense/SingleExpense';
import Modal from '../UI/Modal/Modal';
import { thisExpression } from '../../../node_modules/@babel/types';
import BackDrop from '../UI/BackDrop/BackDrop';

class AddExpense extends Component {
    state = {
        expenseID: null,
        status: null,
        date: "",
        eid: "",
        amount: "",
        description: "",
        valid: false,
        formState: true,     //true: add voucher || false: edit voucher
        modal: false,
        modalID: null
    }
    clearForm = () => {
        this.setState({
            date: "",
            eid: "",
            amount: "",
            description: "",
            valid: false,
            formState: true,
            expenseID: null,
            status: null,
            modal: false,
            modalID: null
        })
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
        this.clearForm();
        this.props.onAddVoucher(expenseData, this.props.userID);
    }
    componentDidMount(){
        this.props.getVouchers(this.props.userID);
    }
    editVoucherHandler = (event, voucherID) => {
        event.stopPropagation();
        const newVoucher = this.props.vouchers.filter(voucher => 
                                voucher.expenseId===voucherID)[0];
        this.setState({
            expenseID: voucherID,
            status: newVoucher.status,
            date: newVoucher.datedOn,
            eid: newVoucher.billNumber,
            amount: newVoucher.billCost,
            description: newVoucher.remark,
            valid: true,
            formState: false
        })
    }
    updateVoucherHandler = () => {
        const data = {
            billCost: this.state.amount,
            billNumber: this.state.eid,
            datedOn: this.state.date,
            remark: this.state.description,
            status: this.state.status
        }
        const expID = this.state.expenseID;
        this.clearForm();
        this.props.updateVoucher(this.props.userID, expID, data);
    }
    clickHandler = () => {
        if(this.state.formState){
            this.addVoucherHandler();
        }else{
            this.updateVoucherHandler();
        }
    }
    updateModal = (expenseId) => {
        this.setState({modal: true, modalID: expenseId});
    }
    modalClose = () => {
        this.setState({modal: false, modalID: null});
    }
    render() {
        let modalView = {display: "none"};
        if(this.state.modal && this.state.modalID){
            modalView = {display: "flex"};
        }
        return (
            <div className={classes.Expense}>
                {this.state.modalID ? <Modal style={modalView} close={this.modalClose} vid={this.state.modalID} />:null}
                {this.state.modalID ? <BackDrop clicked={this.modalClose} />:null}
                <div className={classes.expenseLeft}>
                    {this.props.vouchers.map(voucher => (
                        <SingleExpense id={voucher.billNumber}
                            amt={voucher.billCost}
                            date={voucher.datedOn}
                            key={'_' + Math.random().toString(36).substr(2, 9)}
                            stat={voucher.status}
                            clicked={(event) => this.editVoucherHandler(event, voucher.expenseId)}
                            clik={() => this.updateModal(voucher.expenseId)} />
                    ))}
                </div>
                <div className={classes.expenseRight}>
                    <div className={classes.addExpense}>
                        <h2>Add Expense</h2>
                        <img alt="profile" src="https://t4.ftcdn.net/jpg/03/46/93/61/360_F_346936114_RaxE6OQogebgAWTalE1myseY1Hbb5qPM.jpg" />
                        <input type="date" placeholder="Select Date"
                                onChange={this.onDateChange}
                                value={this.state.date} />
                        <input type="text" placeholder="Bill Number"
                                onChange={this.onIdChange}
                                value={this.state.eid} />
                        <input type="text" placeholder="Bill Cost"
                                onChange={this.onAmountChange}
                                value={this.state.amount} />
                        <input type="text" placeholder="Remarks"
                                onChange={this.onDescChange}
                                value={this.state.description} />
                        <button type="button" disabled={!this.state.valid}
                                onClick={this.clickHandler}>Submit</button>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStatetoProps = state => {
    return {
        vouchers: state.voucher.vouchers,
        userID: state.user.id
    }
}

const mapDispatchtoProps = dispatch => {
    return {
        getVouchers: (userID) => dispatch(actions.getVoucher(userID)),
        onAddVoucher: (voucherData, userID) => dispatch(actions.addVoucher(voucherData, userID)),
        updateVoucher: (uid, eid, data) => dispatch(actions.userUpdateVoucher(uid, eid, data))
    }
}
 
export default connect(mapStatetoProps, mapDispatchtoProps)(AddExpense);