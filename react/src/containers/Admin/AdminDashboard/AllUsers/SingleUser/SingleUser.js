import React from 'react';

import classes from './SingleUser.module.css';

const SingleUser = (props) => {
    return (
        <div className={classes.SingleUser}>
            <p>{props.count}</p>
            <p>{props.name}</p>
            <p>{props.email}</p>
            <p>
                <i class="fa fa-edit"></i>
                <i class="fa fa-trash"></i>
            </p>
        </div>
    );
}
 
export default SingleUser;