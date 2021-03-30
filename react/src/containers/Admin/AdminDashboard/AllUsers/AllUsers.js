import React from 'react';
import classes from './AllUsers.module.css';
import SingleUser from './SingleUser/SingleUser';

const users = [
    {
        count: 1,
        name: "Pratik",
        email: "pratik@gmail.com"
    },{
        count: 1,
        name: "Pratak",
        email: "pratik@gmail.com"
    },{
        count: 1,
        name: "Ptatik",
        email: "pratik@gmail.com"
    },{
        count: 1,
        name: "NONE",
        email: "pratik@gmail.com"
    },
]

const AllUsers = (props) => {
    return (
        <div>
            <div className={classes.tableHeader}>
                <p>Sl.No.</p>
                <p>Name</p>
                <p>Email</p>
                <p>Option</p>
            </div>
            <div className={classes.divider}></div>
            <div className={classes.tableUsers}>
                {users.map(user => {
                    return (
                        user.name.includes(props.query)?<SingleUser count={user.count} name={user.name} email={user.email} />:null
                    )
                })}
                {/* <SingleUser count="1" name="Pratik" email="pratik@gmail.com" />
                <SingleUser count="1" name="Pratiksha" email="pratik@gmail.com" />
                <SingleUser count="1" name="Ptatik" email="pratik@gmail.com" />
                <SingleUser count="1" name="Sourav" email="pratik@gmail.com" /> */}
            </div>
        </div>
    );
}
 
export default AllUsers;