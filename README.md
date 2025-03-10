# Demo Project for Spring Boot

## Project Overview
This project is a Spring Boot-based application designed to demonstrate the usage of Spring Boot features like JPA, RESTful APIs, and MySQL database integration. The application exposes endpoints for creating and managing user-related data, including Parent and Child entities.

## Technologies Used
- **Java 17**
- **Spring Boot 3.4.3**
- **Spring Data JPA**
- **MySQL Database**
- **Lombok**
- **JUnit for testing**

## Setup Instructions

### Prerequisites
Before you start, make sure you have the following installed:
- **Java 17** or later
- **Maven** to build the project
- **MySQL Database** running locally or remotely

### Clone the Repository
Clone the repository to your local machine using Git:

```bash
git clone https://github.com/your-username/demo.git


# User Management API

## Configure Database (Optional)
By default, the application uses an H2 in-memory database. If you want to use a different database, update the `application.properties` file in `src/main/resources/`:

```properties
# H2 Database configuration (default)
spring.datasource.url=jdbc:h2:mem:userdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Build the Application
```bash
mvn clean install
```

## Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`. If you've enabled the H2 console, you can access it at `http://localhost:8080/h2-console`.

## API Endpoints
| Method | URL | Description | Request Body |
|--------|-----|-------------|--------------|
| POST | `/users/parent` | Create a new parent user | `ParentModel` JSON |
| POST | `/users/child/{parentId}` | Create a new child user | `ChildModel` JSON |
| PUT | `/users/{id}` | Update an existing user | `UserModel` JSON |
| DELETE | `/users/{id}` | Delete a user | None |
| GET | `/users/` | Get all users | None |

## Complete API Usage Guide

### Create a Parent User
**Request:**
```bash
curl -X POST http://localhost:8080/users/parent \  
  -H "Content-Type: application/json" \  
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zip": "10001"
  }'
```

**Response:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "street": "123 Main St",
  "city": "New York",
  "state": "NY",
  "zip": "10001",
  "children": []
}
```

### Create a Child User
**Request:**
```bash
curl -X POST http://localhost:8080/users/child/1 \  
  -H "Content-Type: application/json" \  
  -d '{
    "firstName": "Jane",
    "lastName": "Doe"
  }'
```

**Response:**
```json
{
  "id": 2,
  "firstName": "Jane",
  "lastName": "Doe",
  "parent": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe"
  }
}
```

### Update a Parent User
**Request:**
```bash
PUT http://localhost:8080/users/1 \  
  -H "Content-Type: application/json" \  
  -d '{
    "firstName": "John",
    "lastName": "Smith",
    "street": "456 Oak Ave",
    "city": "Boston",
    "state": "MA",
    "zip": "02108"
  }'
```

**Response:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Smith",
  "street": "456 Oak Ave",
  "city


### Get All Users
#### Request:
```bash
curl -X GET http://localhost:8080/users/
```
#### Response:
```json
[
  {
    "id": 1,
    "name": "John",
    "age": 45
  },
  {
    "id": 2,
    "name": "Alice",
    "age": 10
  }
]
```
