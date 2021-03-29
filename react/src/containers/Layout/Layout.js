import React, { Component } from 'react';
//import LandingPage from '../../components/LandingPage/LandingPage';
import { Switch, Route } from 'react-router-dom';
import Login from '../Login/Login';
import Signup from '../Signup/Signup';
import User from '../../components/User/User';
import Admin from '../Admin/Admin';
import {connect} from 'react-redux';
import * as actions from '../../store/actions/index';

class Layout extends Component {
    componentDidMount(){
        if (localStorage.getItem('auth')) {
            const auth = localStorage.getItem('auth');
            const email = localStorage.getItem('email');
            const role = localStorage.getItem('role');
            this.props.pageReload(auth, email, role);
        }else{
            const auth = false;
            const email = "";
            const role = "";
            this.props.pageReload(auth, email, role);
        }
    }
    render() {
        let newMain = <Login />;
        if(this.props.auth){
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

const mapStatetoProps = state => {
    return {
        auth: state.user.auth
    }
}

const mapDispatchtoProps = dispatch => {
    return {
        pageReload: (auth, email, role) => dispatch(actions.onPageReload(auth, email, role))
    }
}
 
export default connect(mapStatetoProps, mapDispatchtoProps)(Layout);