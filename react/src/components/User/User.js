import React from 'react';
import UserDashboard from './DashBoard/DashBoard';
import UserHeader from './Header/Header';
import {Switch, Route} from 'react-router-dom';
import AddExpense from '../../containers/Expense/Expense';

const User = () => {
    return (
        <div>
            <UserHeader />
            <Switch>
                <Route path="/add-expense" component={AddExpense} />
                <Route path="/" component={UserDashboard} />
            </Switch>
        </div>
    );
}
 
export default User;