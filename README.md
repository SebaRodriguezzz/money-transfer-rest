# Money Transfer Rest API
This project is a RESTful API built with Spring and Spring Boot, primarily designed for managing money transfers between users.

Users can securely register new accounts and authenticate through a robust login system. The API employs password encoding to ensure that user credentials are stored and processed securely.

Money transfers between user accounts are straightforward and facilitated by the API. The system handles transactions efficiently, allowing users to transfer funds seamlessly while ensuring data integrity. Users can access their transaction history and view detailed information about both sent and received transactions.

Basic exception handling is implemented to maintain stability and manage any errors that may occur during operations. The API is designed to handle unexpected issues gracefully, providing a reliable user experience and minimizing disruptions.

Unit tests have been implemented using JUnit and Mockito to verify the functionality and reliability of the API.
### Endpoints
* **POST /register**: Register new user account.
* **POST /login**: Authenticate user credentials.
* **POST /users/transfer**: Initiate money transfers between users.
* **GET /users/transactions**: Retrieve transaction history for a user.
* **GET /users/transactions?type=sent**: Retrieve sent transactions for a user.
* **GET /users/transactions?type=received**: Retrieve received transactions for a user.
* **POST /logout**: Log out from user account.

### Technologies Used
* **Java**
* **Spring**
* **Spring Boot**
* **Spring Security**
* **Spring Data JPA**
* **Hibernate**
* **H2 Database**
* **Maven**
* **JUnit**
* **Mockito**

