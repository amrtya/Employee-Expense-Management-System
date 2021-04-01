import React, { Component } from 'react';
import classes from './Modal.module.css';
import {connect} from 'react-redux';

class Modal extends Component {
    state = {
        email: null,
        username: null
    }
    render() {
        let voucher = null;
        if(this.props.role === "USER"){
            voucher = this.props.userVouchers.filter(voucher => voucher.expenseId===this.props.vid)[0];
            if(this.state.email){
                this.setState({email: null, username: null});
            }
        }else{
            voucher = this.props.managerVouchers.filter(voucher => voucher.expenseId===this.props.vid)[0];
            if(!this.state.email){
                this.setState({email: voucher.claimedBy.email, username: voucher.claimedBy.username});
            }
        }
        let colr = classes.red;
        if(voucher.status === "REIMBURSED"){
            colr = classes.green;
        }
        return (
            <div className={classes.outer}>
                {voucher.receiptImage ? 
                    <div className={classes.modalImage}>
                        <h2>Receipt Image</h2>
                        <img alt="receipt" src={`data:image/png;base64,${voucher.receiptImage}`}/>
                    </div>: null}
                
                <div style={this.props.style} className={classes.Modal}>
                    <h2>Bill Number: {voucher.billNumber}</h2>
                    {this.state.email ? <p className={classes.modalp}>{this.state.email}</p> : null}
                    {this.state.username ? <p className={classes.modalp}>{this.state.username}</p> : null}
                    {/* {voucher.receiptImage ? <img alt="receipt" src={`data:image/png;base64,${voucher.receiptImage}`}/>: null} */}
                    <div className={classes.modalGroup}>
                        <div className={classes.modalField}>
                            <p>Bill Cost</p>
                            <p>{voucher.billCost}</p>
                        </div>
                        <div className={classes.modalField}>
                            <p>Dated On</p>
                            <p>{voucher.datedOn}</p>
                        </div>
                    </div>
                    <div className={classes.modalGroup}>
                        <div className={classes.modalField + " " + colr}>
                            <p>Status</p>
                            <p>{voucher.status}</p>
                        </div>
                        <div className={classes.modalField}>
                            <p>Remarks</p>
                            <p>{voucher.remark}</p>
                        </div>
                    </div>
                    <button type="button" onClick={this.props.close}>Close</button>
                </div>
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