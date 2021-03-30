import React, { Component } from 'react';
import SingleExpense from './SingleExpense/SingleExpense';
import {connect} from 'react-redux';
import classes from './ManagerDashBoard.module.css';

class ManagerDashBoard extends Component {
    state = {  }
    render() { 
        return (
            <div>
                <div className={classes.expenseLeft}>
                    {this.props.vouchers.map(voucher => (
                        <SingleExpense id={voucher.billNumber}
                            amt={voucher.billCost}
                            date={voucher.datedOn}
                            key={voucher.expenseID} />
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