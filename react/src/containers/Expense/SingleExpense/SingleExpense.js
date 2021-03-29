import React from 'react';

import classes from './SingleExpense.module.css';

const SingleExpense = (props) => {
    return (
        <div className={classes.SingleExpense}>
            <img alt="profile" src="https://t4.ftcdn.net/jpg/03/46/93/61/360_F_346936114_RaxE6OQogebgAWTalE1myseY1Hbb5qPM.jpg" />
            <p>{props.id}</p>
            <p>{props.date}</p>
            <p>{props.amt}</p>
            <i className="fa fa-edit"></i>
        </div>
    );
}
 
export default SingleExpense;