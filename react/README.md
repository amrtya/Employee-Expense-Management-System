<h1 align="center">Employee Expense Manager</h1>
<img src="https://github.com/iamneowise/employeeexpensemgmtapp-react-FS_Team_159/blob/main/Images/User%20Dashboard.png?raw=true" />

<h1 align="center">Objective of Project</h1>
<p align="center">The aim is to build an Expense Management System that simplifies the employee expense-reimbursement process by automating it through a web app. The web app targets to reduce the manual work behind the process and making the process as error prone as possible.
</p>

<h1 align="center">Technology Stack</h1>
<p align="center">
  <img src="https://img.shields.io/badge/react%20-%2300D9FF.svg?&style=for-the-badge&logo=react&logoColor=white" />&nbsp;&nbsp;&nbsp;
  <img src="https://img.shields.io/badge/javascript%20-%231572B6.svg?&style=for-the-badge&logo=javascript&logoColor=yellow" />&nbsp;&nbsp;
  <img src="https://img.shields.io/badge/mysql%20-%231572B6.svg?&style=for-the-badge&logo=mysql&logoColor=white" />&nbsp;&nbsp;
  <img src="https://img.shields.io/badge/git%20-%231572B6.svg?&style=for-the-badge&logo=git&logoColor=white" />&nbsp;&nbsp;
  <img src="https://img.shields.io/badge/spring%20-%231572B6.svg?&style=for-the-badge&logo=spring&logoColor=white" />&nbsp;&nbsp;
</p>

<h1 align="center">FrontEnd Docs</h1>
<p>Ways to get in to the platform are
  <ul>
    <li>Login</li>
    <li>SignUp</li>
    <li>User/Manager created by admin</li>
  </ul>
</p>

<p>The expense management web app is built for the usage by three classes of employees
  <ul>
    <li>Users</li>
    <li>Managers</li>
    <li>Admin</li>
  </ul>
</p>

<h1>Authorities of an User</h1>
<h2>DashBoard</h2>
<p>In the dashboard, an user can check their expenses segregated into approved and non-approved according to the month they choose.</p>

<h2>Add Expense Page <strong>[/add-expense]</strong></h2>
<p>In the add expense page an user can add an expense, while it also displays all the previous expenses added by that user. The ones which are already approved/reimbursed are marked in green</p>

<p>Clicking on each of the expenses will open a separate modal displaying all the details of that particular expense</p>

<p>For every expense added the user can edit the details as well at a later point of time using the edit icon on the corresponding expense card</p>

<h3>Each expense includes</h3>
<ul>
  <li>Date of the expense</li>
  <li>Bill Number of the expense</li>
  <li>Cost/Amount of the expense</li>
  <li>Remarks/description regarding the expense</li>
  <li>Receipt image for the expense (optional)</li>
</ul>

<h1>Authorities of a Manager</h1>
<h2>Home</h2>
<p>The home page of a manager displays all the expenses added by all the users on the platform. The user who added a particular expense can also be checked from the modal displaying the details of the expense.</p>
<p>A manager can edit any expense added by any of the users. Although a manager can edit any of the details, the most crucial one is that the manager can mark whether or not an expense was approved/reimbursed. In such cases the updated details are stored for that expense.</p>

<p>Also a manager has the authority to delete an expense from  the platform completely using the delete icon on the corresponding expense card</p>

<p>No other pages are accessible to the manager. In case they try to navigate, they will be redirected to the home page.</p>

<h1>Authorities of an Admin</h1>
<h2>Home</h2>
<p>The admin has the authorities to add or delete user to the platform and also update the details of an user</p>
<p>The details that can be added or updated regarding a user/manager are:</p>
<ul>
  <li>UserName</li>
  <li>Email</li>
  <li>Password</li>
  <li>Mobile Number</li>
  <li>Role - User/Manager/Admin</li>
  <li>Active - Yes/No</li>
</ul>

<p>The admin can mark whether an user is active or not. Inactive users are not allowed to perform any functionality on the platform except loggin in to their account</p>

<p>The admin can also promote or demote the role of an employee on the platform among user, manager, and admin.</p>

<p>Also an admin can delete any user from the platform</p>
