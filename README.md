# Project Name: Clear Solutions

This project is a task of the Clear Solutions

## Getting Started

To start the project in your development environment, follow these steps:

### Prerequisites

- Java Development Kit (JDK) installed (preferably JDK 17 or later)
- Apache Maven installed
- IDE (Integrated Development Environment) of your choice (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. Clone the repository:

    ```bash
    https://github.com/arturhasparian/Clear-Solutions.git
    ```

2. Navigate to the project directory:

    ```bash
    cd Clear-Solutions
    ```

### Running the Project


1. Build the project using Maven:

    ```bash
    mvn clean install
    ```
   or
   ```bash
   ./mvnw clean install
    ```

2. Run the project:

    ```bash
    mvn spring-boot:run
   ```
   or
   ```bash
   ./mvnw spring-boot:run
   ```
3. Click this URL http://localhost:8889/swagger-ui/index.html

## Task Description

1. It has the following fields:
   1.1. Email (required). Add validation against email pattern
   1.2. First name (required)
   1.3. Last name (required)
   1.4. Birth date (required). Value must be earlier than current date
   1.5. Address (optional)
   1.6. Phone number (optional)
2. It has the following functionality:
   2.1. Create user. It allows to register users who are more than [18] years old. The value [18] should be taken from properties file.
   2.2. Update one/some user fields
   2.3. Update all user fields
   2.4. Delete user
   2.5. Search for users by birth date range. Add the validation which checks that “From” is less than “To”.  Should return a list of objects
3. Code is covered by unit tests using Spring
4. Code has error handling for REST
5. API responses are in JSON format
6. Use of database is not necessary. The data persistence layer is not required.
7. Any version of Spring Boot. Java version of your choice
8. You can use Spring Initializer utility to create the project: Spring Initializr
