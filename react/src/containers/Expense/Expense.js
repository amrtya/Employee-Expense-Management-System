import classes from './Expense.module.css';
import React, { Component } from 'react';
import * as actions from '../../store/actions/index';
import { connect } from 'react-redux';
import SingleExpense from './SingleExpense/SingleExpense';
import Modal from '../UI/Modal/Modal';
import BackDrop from '../UI/BackDrop/BackDrop';
import  { Redirect } from 'react-router-dom';
import { validateBillCost, validateBillNumber } from '../../store/validators/validators';

class AddExpense extends Component {
    state = {
        expenseID: null,
        status: null,
        date: "",
        eid: {
            value: "",
            valid: true
        },
        amount: {
            value: "",
            valid: true
        },
        description: "",
        image: null,
        imageName: "",
        valid: false,
        formState: true,     //true: add voucher || false: edit voucher
        modal: false,
        modalID: null
    }
    clearForm = () => {
        this.setState({
            date: "",
            eid: {
                value: "",
                valid: true
            },
            amount: {
                value: "",
                valid: true
            },
            description: "",
            image: null,
            imageName: "",
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
        isValid = isValid && eid.valid;
        isValid = isValid && amount.valid;
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
        this.setState({
            ...this.state,
            eid: {
                value: val,
                valid: validateBillNumber(val)
            }
        });
        this.checkValidity(this.state.date, {
            value: val,
            valid: validateBillNumber(val)
        }, this.state.amount, this.state.description);
    }
    onAmountChange = (event) => {
        const val = event.target.value;
        this.setState({
            ...this.state,
            amount: {
                ...this.state.amount,
                value: val,
                valid: validateBillCost(val)
            }
        });
        this.checkValidity(this.state.date, this.state.eid, {
            value: val,
            valid: validateBillCost(val)
        }, this.state.description);
    }
    onDescChange = (event) => {
        const val = event.target.value;
        this.setState({description: val});
        this.checkValidity(this.state.date, this.state.eid, this.state.amount, val);
    }
    onImageChange = (event) => {
        this.setState({image: event.target.files[0], imageName: event.target.value});
    }
    addVoucherHandler = () => {
        const expenseData = {
            billNumber: this.state.eid.value,
            billCost: this.state.amount.value,
            datedOn: this.state.date,
            status: "NOT_REIMBURSED",
            remark: this.state.description
        }
        this.clearForm();
        this.props.onAddVoucher(expenseData, this.props.userID, this.state.image);
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
            eid: {
                value: newVoucher.billNumber,
                valid: true
            },
            amount: {
                value: newVoucher.billCost,
                valid: true
            },
            description: newVoucher.remark,
            image: newVoucher.receiptImage,
            valid: true,
            formState: false
        })
    }
    updateVoucherHandler = () => {
        const data = {
            billCost: this.state.amount.value,
            billNumber: this.state.eid.value,
            datedOn: this.state.date,
            remark: this.state.description,
            status: this.state.status
        }
        const expID = this.state.expenseID;
        this.props.updateVoucher(this.props.userID, expID, data, this.state.image);
        this.clearForm();
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
        if(!this.props.auth || this.props.role!=="USER"){
            return <Redirect to='/signup' />;
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
                        <h2> {this.state.formState? "Add": "Edit"} Expense</h2>
                        <img alt="profile" src="https://t4.ftcdn.net/jpg/03/46/93/61/360_F_346936114_RaxE6OQogebgAWTalE1myseY1Hbb5qPM.jpg" />
                        <input type="date" placeholder="Select Date"
                                onChange={this.onDateChange}
                                value={this.state.date} />
                        <input type="text" placeholder="Bill Number"
                                onChange={this.onIdChange}
                                value={this.state.eid.value}
                                className={this.state.eid.valid ? classes.normal : classes.red} />
                        <input type="text" placeholder="Bill Cost"
                                onChange={this.onAmountChange}
                                value={this.state.amount.value}
                                className={this.state.amount.valid ? classes.normal : classes.red} />
                        <input type="text" placeholder="Remarks"
                                onChange={this.onDescChange}
                                value={this.state.description} />
                        <input type="file" style={{border: "none"}}
                                onChange={this.onImageChange}
                                value={this.state.imageName} />
                        <div>
                            <button type="button" disabled={!this.state.valid}
                                className={classes.update} 
                                onClick={this.clickHandler}>{this.state.formState? "Add": "Update"}
                            </button>
                            <button type="button" className={classes.cancel} 
                                onClick={this.clearForm}>Cancel</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStatetoProps = state => {
    return {
        vouchers: state.voucher.vouchers,
        userID: state.user.id,
        role: state.user.role,
        auth: state.user.auth
    }
}

const mapDispatchtoProps = dispatch => {
    return {
        getVouchers: (userID) => dispatch(actions.getVoucher(userID)),
        onAddVoucher: (voucherData, userID, img) => dispatch(actions.addVoucher(voucherData, userID, img)),
        updateVoucher: (uid, eid, data, image) => dispatch(actions.userUpdateVoucher(uid, eid, data, image))
    }
}
 
export default connect(mapStatetoProps, mapDispatchtoProps)(AddExpense);