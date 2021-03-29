import React, {Component} from 'react';
import { NavLink } from 'react-router-dom';

import classes from './Header.module.css';

class userHeader extends Component {
    delAuth = () => {
        localStorage.clear();
        window.location.href = "/";
    }
    render() {
        return (
            <div className={classes.Header}>
                <div className={classes.HeaderLeft}>
                    <h2>Expense Manager</h2>
                </div>
                <div className={classes.HeaderRight}>
                    <NavLink to="/" 
                        style={{textDecoration: "none", color: "white",
                                padding: "0px 10px",
                                fontSize: "22px",
                                cursor: "pointer",
                                margin: "auto 5px"
                        }}>
                            <p>Home</p>
                    </NavLink>
                    <NavLink to="/add-expense" 
                                style={{textDecoration: "none", color: "white",
                                padding: "0px 10px",
                                fontSize: "22px",
                                cursor: "pointer",
                                margin: "auto 5px"
                        }}>
                            <p>Add Expense</p>
                    </NavLink>
                    <p onClick={this.delAuth}>Logout</p>
                </div>
            </div>
        );
    }
}
 
export default userHeader;