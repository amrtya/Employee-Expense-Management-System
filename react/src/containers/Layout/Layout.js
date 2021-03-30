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
        if (localStorage.getItem('id')) {
            const auth = localStorage.getItem('auth');
            const id = localStorage.getItem('id');
            const role = localStorage.getItem('role');
            this.props.pageReload(auth, id, role);
        }else{
            const auth = false;
            const id = "";
            const role = "";
            this.props.pageReload(auth, id, role);
        }
    }
    render() {
        let newMain = <Login />;
        if(this.props.auth){
            newMain = <User />;
        }
        if(this.props.role === "ADMIN"){
            newMain = <Admin />
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
        auth: state.user.auth,
        role: state.user.role
    }
}

const mapDispatchtoProps = dispatch => {
    return {
        pageReload: (auth, id, role) => dispatch(actions.onPageReload(auth, id, role))
    }
}
 
export default connect(mapStatetoProps, mapDispatchtoProps)(Layout);