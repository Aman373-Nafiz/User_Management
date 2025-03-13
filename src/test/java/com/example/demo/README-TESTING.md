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
                    └── service
                        └── UserServiceTest.java
```

## Common Errors and Troubleshooting

### NullPointerException in Tests

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

### Test Data Setup

When setting up test data for parent-child relationships:

1. Create mock parent objects in the `@BeforeEach` method
2. Create child objects in the specific test methods
3. Set up the mock repository behavior to return the expected objects

Example:
```java
@BeforeEach
void setUp() {
    mockParent = new ParentModel();
    mockParent.setId(1L);
    mockParent.setFirstName("John");
    mockParent.setLastName("Doe");
}

@Test
public void testCreateChild() {
    ChildModel child = new ChildModel();
    child.setFirstName("Alice");
    
    when(userRepository.findById(1L)).thenReturn(Optional.of(mockParent));
    when(userRepository.save(any(ChildModel.class))).thenReturn(child);
    
    // Test service method
}
```

## Best Practices

1. Mock all external dependencies (repositories, services)
2. Test each method independently
3. Use descriptive test method names
4. Verify expected behavior with assertions
5. Use `verify()` to confirm that repository methods are called as expected

## Additional Resources

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)