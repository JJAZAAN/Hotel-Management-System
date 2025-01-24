<h3 align="center">Hotel Management System</h3

<p align="center">
    A hotel management system that facilitates efficient interactions between clients, staff, and admins. It features separate functionalities for each user type and offers robust session management and user-friendly interfaces.
    <br />
    <br />
    <p align="center">
    <a href="https://github.com/github_username/repo_name/issues/new?labels=bug&template=bug-report---.md" align=>Report Bug</a>
    &middot;
    <a href="https://github.com/github_username/repo_name/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
    </p>
</p>

<details>
  <summary>Table of Contents</summary>
  <ul>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#client-side">Client Side</a></li>
          <li><a href="#staff">Staff</a></li>
          <ul>
              <li><a href="#reception-page">Reception Page</a></li>
              <li><a href="#search-functionality">Search Functionality</a></li>
          </ul>
          <li><a href="#admin">Admin</a></li>
          <ul>
              <li><a href="#manage-employees">Manage Employees</a></li>
              <li><a href="#manage-rooms">Manage Rooms</a></li>
          </ul>
      </ul>
      <li><a href="#cool-features">Cool Features</a></li>
  </ul>
</details>


  <!-- ABOUT THE PROJECT -->
## About The Project

## Features

### Client Side
<p>
    <ul>
      <li>Signup & Login: </li>
        Clients can register and log in with mandatory fields (email, password, etc.) and email verification.
    </ul>
    <ul>
      <li>Room Booking:: </li>
         Rooms are displayed based on the client's specified check-in and check-out dates. Only available rooms are shown.
    </ul>
    <ul>
      <li>Update Profile: </li>
         Clients can update account settings like password or email.
    </ul>
    <ul>
      <li>Booking History: </li>
        Clients can view their previous bookings, providing a detailed history.
    </ul>
</p>

### Staff
#### Reception Page

<p>
    Staff can:
    <ul>
      <li>Check users in and out.</li>
      <li>Create new client accounts and book rooms for them. </li>
    </ul>
</p>

#### Search Functionality
<p>
    Staff can search for rooms, bookings, and customers with advanced filters:
    <ul>
      <li>Search Room: Bed Type, Room Type, Booked or Not</li>
        <li>Serach Bookings: Name of Booking, Room Number, Check In Date, Check Out Date, Active Booking</li> 
        <li>Search Customer: Name, Email, Address</li>
    </ul>
</p>

### Admin
#### Manage Employees:
<p>
    Admins can:
     <ul>   
         <li>Add employees</li>
         <li>When an employee is added, account is automatically created with randomly generated passwords containing uppercase, lowercase letters, and numbers.</li>
         <li>Update employee details. </li>
     </ul>
</p>
    
#### Manage Rooms:
<p>
    Admins can
    <ul>
        <li>Add Rooms</li>
        <li>Update Room details</li>
    </ul>
</p>
    

## Cool Features
Email Verification: Email confirmation is required during signup to validate accounts.
<br>
<br>
Random Password: Generator Randomly generated passwords containing uppercase, lowercase letters, and numbers.
<br>
<br>
User Session Management Tracks if a user is logged in. Stores room details temporarily if a booking is initiated before login. After login, the system retrieves the stored details to continue the booking process seamlessly.
Allows users to view their previous booking history.
                                          


