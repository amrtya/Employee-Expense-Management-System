import React, { Component } from 'react';
import AdminDashboard from './AdminDashboard/AdminDashboard';
import AdminHeader from './AdminHeader/AdminHeader';

class Admin extends Component {
    state = {

    }
    render() { 
        return (
            <div>
                <AdminHeader />
                <AdminDashboard />
            </div>
        );
    }
}
 
export default Admin;