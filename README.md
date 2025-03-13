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

# Testing Documentation

This document provides instructions on how to run unit tests for the project and troubleshoot common issues.

## Running Unit Tests

### Using Maven

To run all tests using Maven:

```bash
mvn test
```

To run a specific test class:

```bash
mvn test -Dtest=UserServiceTest
```

Or for controller tests:

```bash
mvn test -Dtest=UserControllerTest
```

### Using an IDE

#### IntelliJ IDEA

1. Right-click on the test class or test folder
2. Select "Run 'TestClassName'" or "Run 'Tests in directory'"
3. View test results in the run window

#### Eclipse

1. Right-click on the test class or test folder
2. Select "Run As" > "JUnit Test"
3. View test results in the JUnit tab

## Project Structure

The test structure follows the application structure:

```
src
├── main
│   └── java
│       └── com
│           └── example
│               └── demo
│                   ├── controller
│                   │   └── UserController.java
│                   ├── model
│                   │   ├── UserModel.java
│                   │   ├── ParentModel.java
│                   │   └── ChildModel.java
│                   ├── repository
│                   │   └── UserRepository.java
│                   └── service
│                       └── UserService.java
└── test
    └── java
        └── com
            └── example
                └── demo
                    ├── controller
                    │   └── UserControllerTest.java
                    └── service
                        └── UserServiceTest.java
```

## Service Layer Testing

### Common Errors and Troubleshooting

If you encounter a `NullPointerException` like:

```
java.lang.NullPointerException: Cannot invoke "com.example.demo.repository.UserRepository.findById(Object)" because "this.userRepository" is null
```

Check the following:

1. Ensure you're using the correct mock repositories in your test class:
   - Your service uses `UserRepository`, so your test should mock `UserRepository`
   - If you're mocking `ParentRepository` or `ChildRepository` but your service uses `UserRepository`, you'll get this error

2. Make sure your test class is properly set up:

   ```java
   @ExtendWith(MockitoExtension.class)
   public class UserServiceTest {
       @Mock
       private UserRepository userRepository;

       @InjectMocks
       private UserService userService;
   }
   ```

3. If using `@ExtendWith(MockitoExtension.class)`, you don't need to call `MockitoAnnotations.openMocks(this)` in your `@BeforeEach` method

## Controller Layer Testing

### Setup

Controller tests use Spring's `MockMvc` to simulate HTTP requests without starting a full server. Your test setup should look like:

```java
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
}
```

### Best Practices for Controller Testing

1. Initialize `MockMvc` in a `@BeforeEach` method to avoid repetition
2. Use `ObjectMapper` to convert objects to JSON
3. Mock the service layer responses
4. Test HTTP status codes and response content
5. Verify that service methods are called with correct parameters

### Common Controller Test Examples

#### Testing POST Endpoints

```java
@Test
void testCreateParent() throws Exception {
    ParentModel parent = new ParentModel();
    parent.setFirstName("John");
    parent.setLastName("Doe");

    when(userService.createParent(any(ParentModel.class))).thenReturn(parent);

    mockMvc.perform(post("/users/parent")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(parent)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("John"));

    verify(userService, times(1)).createParent(any(ParentModel.class));
}
```

#### Testing GET Endpoints

```java
@Test
void testGetAllUsers() throws Exception {
    ParentModel parent = new ParentModel();
    parent.setFirstName("John");

    when(userService.getAllUsers()).thenReturn(Arrays.asList(parent));

    mockMvc.perform(get("/users/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].firstName").value("John"));

    verify(userService, times(1)).getAllUsers();
}
```

#### Testing PUT/DELETE Endpoints

```java
@Test
void testUpdateUser() throws Exception {
    ParentModel parent = new ParentModel();
    parent.setId(1L);
    parent.setFirstName("Updated");

    when(userService.updateUser(eq(1L), any(ParentModel.class))).thenReturn(parent);

    mockMvc.perform(put("/users/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(parent)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Updated"));
}

@Test
void testDeleteUser() throws Exception {
    doNothing().when(userService).deleteUser(1L);

    mockMvc.perform(delete("/users/1"))
            .andExpect(status().isOk());

    verify(userService, times(1)).deleteUser(1L);
}
```

## Integration Testing

For full integration tests with a running application context:

```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        // Setup test data
    }

    @Test
    void testCreateParentIntegration() throws Exception {
        // Test with real repository
    }
}
```

## Additional Resources

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [MockMvc Documentation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html)
