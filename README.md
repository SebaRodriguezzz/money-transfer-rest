# Money Transfer Rest API
This RESTful API, created using Spring and Spring Boot, is a mock implementation for user authentication and money transfers between users. It employs an H2 database for minimalistic data storage and includes exception handling.

### Key Features
#### Registration
Enables new user registration, securely storing user information.
#### User Authentication
Offers secure login functionality using encrypted credentials.
#### Money Transfer Functionality
Allows for simple money transfer operations between user accounts.
#### Database Integration
Utilizes an H2 in-memory database for storing user-related information.
#### Exception Handling
Implements basic exception handling to maintain stability during user transactions.

### Endpoints
* **POST /register**: Register new user account.
* **POST /login**: Authenticate user credentials.
* **POST /users/transfer**: Initiate money transfers between users.
* **GET /users/transactions**: Retrieve transaction history for a user.
* **GET /users/transactions?type=sent**: Retrieve sent transactions for a user.
* **GET /users/transactions?type=received**: Retrieve received transactions for a user.
* **POST /logout**: Log out from user account.

### Technologies Used
* **Spring**: Lightweight framework for building Java applications.
* **Spring Boot**: Simplifies the setup and development of Spring applications.
* **Spring Security**: Authentication and access-control framework for Spring applications.
* **Spring Data JPA**: Simplifies the implementation of JPA-based repositories.
* **Hibernate**: ORM framework for mapping Java objects to relational database tables.
* **H2 Database**: Lightweight, in-memory database for data storage.
* **Maven**: Build automation tool for Java projects.