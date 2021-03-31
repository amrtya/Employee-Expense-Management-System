import classes from './DashBoard.module.css';
import React, {Component} from 'react';
import * as actions from '../../../store/actions/index';
import {connect} from 'react-redux';

class userDashboard extends Component {
    state = {
        month: ""
    }
    componentDidMount(){
        const d = new Date();
        let months = new Array();
        months[0] = "JANUARY";
        months[1] = "FEBRUARY";
        months[2] = "MARCH";
        months[3] = "APRIL";
        months[4] = "MAY";
        months[5] = "JUNE";
        months[6] = "JULY";
        months[7] = "AUGUST";
        months[8] = "SEPTEMBER";
        months[9] = "OCTOBER";
        months[10] = "NOVEMBER";
        months[11] = "DECEMBER";
        const month = months[d.getMonth()];
        this.setState({month: month});
        this.props.getMonthDetails(month, this.props.id);
    }
    monthChangeHandler = (event) => {
        this.setState({month: event.target.value});
        this.props.getMonthDetails(event.target.value, this.props.id);
    }
    render() {
        return (
            <div className={classes.DashBoard}>
                <div className={classes.monthHeader}>
                    <h1 style={{color: "white", fontSize: "36px"}}>DASHBOARD</h1>
                    <select name="month"
                        value={this.state.month}
                        onChange={this.monthChangeHandler}>
                        <option value="JANUARY">January</option>
                        <option value="FEBRUARY">February</option>
                        <option value="MARCH">March</option>
                        <option value="APRIL">April</option>
                        <option value="MAY">May</option>
                        <option value="JUNE">June</option>
                        <option value="JULY">July</option>
                        <option value="AUGUST">August</option>
                        <option value="SEPTEMBER">September</option>
                        <option value="OCTOBER">October</option>
                        <option value="NOVEMBER">November</option>
                        <option value="DECEMBER">December</option>
                    </select>
                </div>
                <div className={classes.details}>
                    <div className={classes.detailsCard}>
                        <p>Total Expense</p>
                        <p>{this.props.total}</p>
                    </div>
                    <div className={classes.detailsCard}>
                        <p>Pending Expense</p>
                        <p>{this.props.pending}</p>
                    </div>
                    <div className={classes.detailsCard}>
                        <p>Approved Expense</p>
                        <p>{this.props.approved}</p>
                    </div>
                </div>
                <div className={classes.details}>
                    <div className={classes.detailsCard}>
                        <p>Number of Expenses</p>
                        <p>{this.props.num}</p>
                    </div>
                </div>
            </div>
        );
    }
}

const mapStatetoProps = state => {
    return {
        id: state.user.id,
        total: state.dashboard.totalExpense,
        approved: state.dashboard.approvedExpense,
        pending: state.dashboard.pendingExpense,
        num: state.dashboard.numberofExpenses
    }
}

const mapDispatchtoProps = dispatch => {
    return {
        getMonthDetails: (month, uid) => dispatch(actions.getMonthDetails(month, uid))
    }
}
 
export default connect(mapStatetoProps, mapDispatchtoProps)(userDashboard);