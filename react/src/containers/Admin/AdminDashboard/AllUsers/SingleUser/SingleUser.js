import React from 'react';

import classes from './SingleUser.module.css';

const SingleUser = (props) => {
    return (
        <div className={classes.SingleUser}>
            <p>{props.count}</p>
            <p>{props.name}</p>
            <p>{props.email}</p>
            <p>
                <i className="fa fa-edit"></i>
                <i className="fa fa-trash"></i>
            </p>
        </div>
    );
}
 
export default SingleUser;