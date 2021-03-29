import React, { Component } from 'react';
//import LandingPage from '../../components/LandingPage/LandingPage';
import { Switch, Route } from 'react-router-dom';
import Login from '../Login/Login';
import Signup from '../Signup/Signup';
import User from '../../components/User/User';
import Admin from '../Admin/Admin';

class Layout extends Component {
    state = {
        authenticated: false
    }
    updateAuthentication = () => {
        this.setState(prevState => {
            return {
                authenticated: true
            }
        });
    }
    componentDidMount(){
        const newState = localStorage.getItem('auth');
        if (localStorage.getItem('auth')) {
            this.setState({
                authenticated: newState
            });
        }else{
            this.setState({
                authenticated: false
            });
        }
    }
    render() {
        let newMain = <Login updateAuth={() => this.updateAuthentication()} />;
        if(this.state.authenticated){
            newMain = <User />;
        }
        return (
            <div>
                <Switch>
                    <Route path="/admin" component={Admin} />
                    <Route path="/add-expense" render={() => newMain} />
                    <Route path="/signup" component={Signup} />
                    <Route path="/" render={() => newMain} />
                </Switch>
            </div>
        );
    }
}
 
export default Layout;