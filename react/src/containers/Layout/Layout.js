import React, { Component } from 'react';
//import LandingPage from '../../components/LandingPage/LandingPage';
import { Switch, Route } from 'react-router-dom';
import Login from '../Login/Login';
import Signup from '../Signup/Signup';
import User from '../../components/User/User';

class Layout extends Component {
    state = {
        authenticated: true
    }
    render() {
        let main = Login;
        if(this.state.authenticated){
            main = User;
        }
        return (
            <div>
                <Switch>
                    <Route path="/add-expense" component={main} />
                    <Route path="/signup" exact component={Signup} />
                    <Route path="/" exact component={main} />
                </Switch>
            </div>
        );
    }
}
 
export default Layout;