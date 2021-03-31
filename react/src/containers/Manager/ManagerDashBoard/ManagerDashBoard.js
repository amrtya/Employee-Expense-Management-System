import React, { Component } from 'react';
import SingleExpense from './SingleExpense/SingleExpense';
import {connect} from 'react-redux';
import classes from './ManagerDashBoard.module.css';

class ManagerDashBoard extends Component {
    state = {  }
    render() {
        return (
            <div className={classes.Expense}>
                <div className={classes.expenseLeft}>
                    {this.props.vouchers.map(voucher => (
                        <SingleExpense billN={voucher.billNumber}
                            amt={voucher.billCost}
                            date={voucher.datedOn}
                            key={'_' + Math.random().toString(36).substr(2, 9)}
                            voucherID={voucher.expenseId} />
                    ))}
                </div>
            </div>
        );
    }
}

const mapStatetoProps = state => {
    return {
        vouchers: state.manager.vouchers
    }
}
 
export default connect(mapStatetoProps)(ManagerDashBoard);