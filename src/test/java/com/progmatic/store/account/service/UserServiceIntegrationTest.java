package com.progmatic.store.account.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.progmatic.store.account.constants.UserConstants;
import com.progmatic.store.account.init.Application;
import com.progmatic.store.account.response.ErrorResponse;
import com.progmatic.store.account.response.UserListResponse;
import com.progmatic.store.account.response.UserResponse;
import com.progmatic.store.account.response.ValidationErrorResponse;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("IntegrationTest")
public class UserServiceIntegrationTest {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/userdb";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "admin";

    private static final String PATH_TO_SQL_SCRIPTS = Path.of(System.getProperty("user.dir"), "data", "scripts", "sql").toAbsolutePath().toString();

    private static ConfigurableApplicationContext applicationContext;

    private static Connection connection;

    @BeforeAll
    public static void init() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
        applicationContext = SpringApplication.run(Application.class);
    }

    @AfterAll
    public static void destroy() throws SQLException {
        applicationContext.close();
        if (connection != null) connection.close();
    }

    @Test
    public void testGetAllUsersWithoutUserData() {
        String scriptPath = getEmptyUserDataScript();
        assertDoesNotThrow(() -> readSqlScript(scriptPath));
        RestTemplate restTemplate = new RestTemplate();
        UserListResponse response = restTemplate.getForObject("http://localhost:8080/user-service/api/v0/users", UserListResponse.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(HttpStatus.OK.getReasonPhrase(), response.getStatus());
        assertEquals("0 users found.", response.getMessage());
        assertNotNull(response.getPayload());
        assertTrue(response.getPayload().isEmpty());
    }

    @Test
    public void testGetAllUsersWithUserData() {
        String scriptPath = getFilledUserDataScript();
        assertDoesNotThrow(() -> readSqlScript(scriptPath));
        RestTemplate restTemplate = new RestTemplate();
        UserListResponse response = restTemplate.getForObject("http://localhost:8080/user-service/api/v0/users", UserListResponse.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(HttpStatus.OK.getReasonPhrase(), response.getStatus());
        assertEquals("15 users found.", response.getMessage());
        assertNotNull(response.getPayload());
        assertEquals(15, response.getPayload().size());
    }

    @Test
    public void testGetUserWithoutUserData() {
        String scriptPath = getEmptyUserDataScript();
        assertDoesNotThrow(() -> readSqlScript(scriptPath));
        RestTemplate restTemplate = new RestTemplate();
        RestClientResponseException exception = assertThrows(RestClientResponseException.class, () -> restTemplate.getForObject("http://localhost:8080/user-service/api/v0/user/user1@gmail.com", ErrorResponse.class));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        ErrorResponse response = exception.getResponseBodyAs(ErrorResponse.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), response.getStatus());
        assertEquals(String.format(UserConstants.USER_NOT_FOUND, "user1@gmail.com"), response.getMessage());
        assertEquals("/user-service/api/v0/user/user1@gmail.com", response.getPath());
    }

    @Test
    public void testGetUserWithUserData() {
        String scriptPath = getFilledUserDataScript();
        assertDoesNotThrow(() -> readSqlScript(scriptPath));
        RestTemplate restTemplate = new RestTemplate();
        UserResponse response = restTemplate.getForObject("http://localhost:8080/user-service/api/v0/user/user1@gmail.com", UserResponse.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(HttpStatus.OK.getReasonPhrase(), response.getStatus());
        assertNotNull(response.getPayload());
        assertEquals("user1@gmail.com", response.getPayload().getEmailId());
        assertEquals("User1@123456", response.getPayload().getPassword());
        assertEquals("User One", response.getPayload().getFullName());
        assertEquals("user1", response.getPayload().getUsername());
        assertNotNull(response.getPayload().getId());
    }

    @Test
    public void testGetUserWithoutUserData_InvalidEmail() {
        String scriptPath = getEmptyUserDataScript();
        assertDoesNotThrow(() -> readSqlScript(scriptPath));
        RestTemplate restTemplate = new RestTemplate();
        RestClientResponseException exception = assertThrows(RestClientResponseException.class, () -> restTemplate.getForObject("http://localhost:8080/user-service/api/v0/user/user.com", ValidationErrorResponse.class));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        ValidationErrorResponse response = exception.getResponseBodyAs(ValidationErrorResponse.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), response.getStatus());
        assertTrue(response.getViolatedConstraints().containsKey("getUser.arg0"));
        assertEquals("Enter a valid email address.", response.getViolatedConstraints().get("getUser.arg0"));
        assertEquals("User validations failed.", response.getMessage());
        assertEquals("/user-service/api/v0/user/user.com", response.getPath());
    }

    @Test
    public void testGetUserWithUserData_InvalidEmail() {
        String scriptPath = getFilledUserDataScript();
        assertDoesNotThrow(() -> readSqlScript(scriptPath));
        RestTemplate restTemplate = new RestTemplate();
        RestClientResponseException exception = assertThrows(RestClientResponseException.class, () -> restTemplate.getForObject("http://localhost:8080/user-service/api/v0/user/user.com", ValidationErrorResponse.class));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        ValidationErrorResponse response = exception.getResponseBodyAs(ValidationErrorResponse.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), response.getStatus());
        assertTrue(response.getViolatedConstraints().containsKey("getUser.arg0"));
        assertEquals("Enter a valid email address.", response.getViolatedConstraints().get("getUser.arg0"));
        assertEquals("User validations failed.", response.getMessage());
        assertEquals("/user-service/api/v0/user/user.com", response.getPath());
    }

    private void readSqlScript(String filePath) throws IOException, SQLException {
        StringBuilder sqlScript = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sqlScript.append(line);
            }
        }
        String[] queries = sqlScript.toString().split(";");
        try (Statement statement = connection.createStatement()) {
            for (String query : queries) {
                query = query.trim();
                statement.execute(query);
            }
        }
    }

    private static String getEmptyUserDataScript() {
        return Path.of(PATH_TO_SQL_SCRIPTS, "empty-user-data.sql").toAbsolutePath().toString();
    }

    private static String getFilledUserDataScript() {
        return Path.of(PATH_TO_SQL_SCRIPTS, "add-user-data.sql").toAbsolutePath().toString();
    }
}
