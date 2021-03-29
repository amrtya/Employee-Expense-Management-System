import React from 'react';
import classes from './AllUsers.module.css';
import SingleUser from './SingleUser/SingleUser';

const AllUsers = () => {
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
                <SingleUser count="1" name="Pratik" email="pratik@gmail.com" />
                <SingleUser count="1" name="Pratik" email="pratik@gmail.com" />
                <SingleUser count="1" name="Pratik" email="pratik@gmail.com" />
                <SingleUser count="1" name="Pratik" email="pratik@gmail.com" />
                <SingleUser count="1" name="Pratik" email="pratik@gmail.com" />
                <SingleUser count="1" name="Pratik" email="pratik@gmail.com" />
                <SingleUser count="1" name="Pratik" email="pratik@gmail.com" />
                <SingleUser count="1" name="Pratik" email="pratik@gmail.com" />
                <SingleUser count="1" name="Pratik" email="pratik@gmail.com" />
                <SingleUser count="1" name="Pratik" email="pratik@gmail.com" />
            </div>
        </div>
    );
}
 
export default AllUsers;