import classes from './Expense.module.css';
import React, { Component } from 'react';

class AddExpense extends Component {
    state = {

    }
    render() { 
        return (
            <div className={classes.Expense}>
                <div className={classes.expenseLeft}>
                    <div className={classes.expenseCard}>
                        <img alt="profile" src="https://t4.ftcdn.net/jpg/03/46/93/61/360_F_346936114_RaxE6OQogebgAWTalE1myseY1Hbb5qPM.jpg" />
                        <p>User 1</p>
                        <p>Date</p>
                        <p>Amount</p>
                        <i class="fa fa-edit"></i>
                    </div>
                    <div className={classes.expenseCard}>
                        <img alt="profile" src="https://t4.ftcdn.net/jpg/03/46/93/61/360_F_346936114_RaxE6OQogebgAWTalE1myseY1Hbb5qPM.jpg" />
                        <p>User 1</p>
                        <p>Date</p>
                        <p>Amount</p>
                        <i className="fa fa-edit"></i>
                    </div>
                    <div className={classes.expenseCard}>
                        <img alt="profile" src="https://t4.ftcdn.net/jpg/03/46/93/61/360_F_346936114_RaxE6OQogebgAWTalE1myseY1Hbb5qPM.jpg" />
                        <p>User 1</p>
                        <p>Date</p>
                        <p>Amount</p>
                        <i class="fa fa-edit"></i>
                    </div>
                </div>
                <div className={classes.expenseRight}>
                    <div className={classes.addExpense}>
                        <h2>Add Expense</h2>
                        <img alt="profile" src="https://t4.ftcdn.net/jpg/03/46/93/61/360_F_346936114_RaxE6OQogebgAWTalE1myseY1Hbb5qPM.jpg" />
                        <input type="date" placeholder="Select Date" />
                        <input type="text" placeholder="Emp ID" />
                        <input type="text" placeholder="Amount" />
                        <input type="text" placeholder="Description" />
                        <button type="button">Submit</button>
                    </div>
                </div>
            </div>
        );
    }
}
 
export default AddExpense;