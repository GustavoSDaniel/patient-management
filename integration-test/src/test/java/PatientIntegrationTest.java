import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PatientIntegrationTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:8098"; // API Gateway pq ele indica os caminhos
    }

    @Test
    public void shouldReturnPatientsWithValidaToken() {

        String loginPayLoad = """
                    {
                      "email": "testuser@test.com",
                      "password": "password123"
                
                    }
                """;

        String token = given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("/api/v1/auth/login") //Chama apenas o endpoint específico do método
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");


        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/v1/patients")
                .then()
                .statusCode(200)
                .body("content", notNullValue());  // procure pelo campo content no JSON da resposta, que é o campo  retornado por um objeto Page

    }
}
