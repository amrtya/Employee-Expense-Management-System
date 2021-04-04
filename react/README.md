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
<h2>Elements: </h2>
<h3><strong>DashBoard:</strong> Only accessible to user(not for manager or admin)</h3>
<ul>
  <li><strong>Month Input: </strong>User can change the input of the month, the expense details of which they need.</li>
  <li><strong>Total Expense: </strong>Shows the summation of all the expenses done in the selected month for the corresponsding user</li>
  <li><strong>Approved Expense: </strong>Shows the summation of all the approved/reimbursed expenses from the selected month for the corresponsding user</li>
  <li><strong>Pending Expense: </strong>Shows the summation of all the non-reimbursed expenses from the selected month for the corresponsding user</li>
  <li><strong>Number of Expenses: </strong>Shows the total number of expenses from the selected month for the corresponsding user</li>
</ul>

<h3><strong>Add Expense: [User: /add-expense][Manager: /]</strong></h3>
<p>The add-expense page consists of two major sections</p>
<ul>
  <li>
    <strong>All Expenses(User)</strong>
    <p>The all expenses section consists of all the previous expenses added by the user in the form cards. Each of them consists of:</p>
    <ul>
      <li>Bill Number: The receipt number added by the user</li>
      <li>Bill Cost: The cost of the expense that is to be reimbursed</li>
      <li>Date of expense: The date the expense was made</li>
      <li>Edit Icon: This icon lets a user edit their previously added expense details</li>
      <li>Modal: On clicking the cards it displays a modal that shows all the details regarding the expense along with the receipt image and status of the expense</li>
    </ul>
  </li><br>
  <li>
    <strong>All Expenses(Manager)</strong>
    <p>The all expenses section for the manager is quite similar to that of the user except for the fact that the manager can view, update or delete any expense added by of the users on the platform</p>
  </li><br>
  <li>
    <strong>Add Expense Form(User)</strong>
    <p>The add expense form takes the follwing details from the user regarding the expense: </p>
    <ul>
      <li>Bill Number: The receipt number added by the user</li>
      <li>Bill Cost: The cost of the expense that is to be reimbursed</li>
      <li>Date of expense: The date the expense was made</li>
      <li>Remarks: A short description regarding the expense that the user is adding</li>
      <li>Receipt Image: An image of the receipt for the expense to show the genuinity of the expense</li>
    </ul>
  </li><br>
  <li>
    <strong>Edit Expense Form(Manager)</strong>
    <p>The details updated by the manager for an expense is same as that of user with one extra field: </p>
    <ul>
      <li>Status: REIMBURSED | NOT_REIMBURSED</li>
    </ul>
  </li><br>
</ul>

<h3><strong>Homepage of Admin: </strong></h3>
<p>The home page of the admin has the following: </p>
<ul>
  <li><strong>Search Bar: </strong>The search bar allows the admin to filter out users by their username</li><br>
  <li><strong>All Users Section: </strong>The All users section displays all the users on the platform in the form of single elements. Each of the elements consists of: 
    <ul>
      <li>Sl No: To keep track of the number of users on the platform</li>
      <li>Username: Username of the user(filter parameter)</li>
      <li>Email: Mail ID of the respective user</li>
      <li>Role: Role of the user on the platform [USER | MANAGER | ADMIN]</li>
      <li>Edit icong: To edit the details of a particular user (can update all details except the password)</li>
      <li>Delete icon: To delete the details of an user from the platform completely</li>
    </ul>
  </li><br>
  <li><strong>Add/Update user form: </strong>This form can both add a new user or update the details of an user. On pressing edit icon on any user the form gets filled with the details of that corresponding user and the admin can update them accordingly.
    <ul>
      <li>Username</li>
      <li>Email ID</li>
      <li>Password (not available when updating)</li>
      <li>Mobile Number</li>
      <li>Active[Yes | No]: This denotes whether an user is active or not on the platform. An inactive user has no authority on the platform. They will be banned from all activities.</li>
      <li>Role [USER | MANAGER | ADMIN]: Denotes the role of the user on the platform</li>
    </ul>
  </li>
</ul>



<h2>Ways to get in to the platform are: </h2>
  <ul>
    <li>Login</li>
    <li>SignUp</li>
    <li>User/Manager created by admin</li>
  </ul>

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
