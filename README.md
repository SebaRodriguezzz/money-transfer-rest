# Money Transfer Rest Application
This basic RESTful application, created using Spring and Spring Boot, is a mock implementation for user authentication and money transfers between users. It employs an H2 database for minimalistic data storage and includes exception handling.

### Key Features
#### User Authentication
Provides a basic login feature using predefined credentials.
#### Money Transfer Functionality
Allows for simple money transfer operations between user accounts.
#### Database Integration
Utilizes an H2 in-memory database for storing user-related information.
#### Exception Handling
Implements basic exception handling to maintain stability during user transactions.

### Endpoints
* **POST /login**: Authenticate users.
* **POST /transfer**: Initiate mock money transfers between users.

### Technologies Used
* **Spring**: Lightweight framework for building Java applications.
* **Spring Boot**: Simplifies the setup and development of Spring applications.
* **H2 Database**: Lightweight, in-memory database for data storage.
* **Maven**: Build automation tool for Java projects.