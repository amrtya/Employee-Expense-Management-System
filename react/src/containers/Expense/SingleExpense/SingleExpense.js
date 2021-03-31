import React from 'react';

import classes from './SingleExpense.module.css';


const SingleExpense = (props) => {
    let colr = {backgroundColor: "white"};
    if(props.stat === "REIMBURSED"){
        colr = {backgroundColor: "lightgreen"}
    } 
    return (
        <div className={classes.SingleExpense} style={colr} onClick={props.clik}>
            <img alt="profile" src="https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg" />
            <p>{props.id}</p>
            <p>{props.date}</p>
            <p>₹ {props.amt}</p>
            <i className="fa fa-edit" onClick={(event) => props.clicked(event)}></i>
        </div>
    );
}
 
export default SingleExpense;