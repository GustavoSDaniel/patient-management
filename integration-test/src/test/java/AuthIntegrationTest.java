import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest {

    @BeforeAll
    static void setUp() {

        RestAssured.baseURI = "http://localhost:9002/api/v1/auth";
    }

    @Test
    public void shouldReturnOKWithValidToken() {

        String loginPayLoad = """
                    {
                      "email": "testuser@test.com",
                      "password": "password123"
                
                    }
                """;

        Response response = given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("/login") //Chama apenas o endpoint específico do método
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .response();

        System.out.printf("Generated Token: " + response.jsonPath().getString("token"));

    }

    @Test
    public void shouldReturnUnauthorizedOnInvalidLogin() {

        String loginPayLoad = """
                    {
                      "email": "invalid_user@test.com",
                      "password": "wrongpassword"
                
                    }
                """;

            given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("/login") //Chama apenas o endpoint específico do método
                .then()
                .statusCode(401);

    }
}
