package com.itm.space.backendresources.integration;

import com.itm.space.backendresources.*;
import static io.restassured.RestAssured.*;
import io.restassured.http.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.*;
import java.util.*;

public class UserControllerIT extends BaseIntegrationTest {

    private final String TOKEN = getBearerToken();

    private static final String ID = "5cfec3a0-5298-40e7-88d8-0e6f1d668166";

    private static final String BODY_WITH_GOOD_REQUEST =
            """
                    {
                        "username": "test_user",
                        "email": "test_user@mail.ru",
                        "password": "test_user",
                        "firstName": "Test_user",
                        "lastName": "Test_userTest_user"
                    }
                    """;

    private static final String BODY_WITH_BAD_REQUEST =
            """
                    {
                        "username": "test_user",
                        "email": "test_user",
                        "password": "test_user",
                        "firstName": "Test_user",
                        "lastName": "Test_userTest_user"
                    }
                    """;

    @Test
    void hello_givenAuthenticatedUser_whenGetApiUsersHello_shouldReturnStatusOk_andString() {
        given()
                .header("Authorization", TOKEN)
                .when()
                .get("api/users/hello")
                .then()
                .assertThat()
                .statusCode(is(200))
                .body(is(notNullValue()));
    }

    @Test
    void create_givenAuthenticatedUser_whenPostApiUsers_shouldReturnStatusOk() {
        given()
                .header("Authorization", TOKEN)
                .contentType(ContentType.JSON)
                .body(BODY_WITH_GOOD_REQUEST)
                .when()
                .post("/api/users")
                .then()
                .assertThat()
                .statusCode(is(200));
    }

    @Test
    void create_givenAuthenticatedUser_whenPostApiUsers_shouldReturnBedRequest() {
        given()
                .header("Authorization", TOKEN)
                .contentType(ContentType.JSON)
                .body(BODY_WITH_BAD_REQUEST)
                .when()
                .post("/api/users")
                .then()
                .assertThat()
                .body("email", equalTo("Email should be valid"))
                .statusCode(is(400));
    }

    @Test
    void getUserById_givenAuthenticatedUser_whenGetApiUsers_shouldReturnCorrectUser_andStatusOk() {
        given()
                .header("Authorization", TOKEN)
                .when()
                .get("/api/users/" + ID)
                .then()
                .body("firstName", equalTo("User"))
                .body("lastName", equalTo("UserUser"))
                .body("email", equalTo("user@mail.ru"))
                .body("roles", equalTo(List.of("MODERATOR", "default-roles-itm")))
                .body("groups", equalTo(List.of("Moderators")))
                .statusCode(is(200));
    }

    @Test
    void getUserById_givenAuthenticatedUser_whenGetApiUsers_shouldReturnStatus404() {
        given()
                .header("Authorization", TOKEN)
                .when()
                .get("/api/users/" + " ")
                .then()
                .statusCode(is(404));
    }
}