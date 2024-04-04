import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthAPITest {

    @Test
    public void testRegistration() {
        String requestBody = "{\"username\": \"verka\", \"email\": \"vera@icloud.com\", \"password\": \"123\"}";

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("http://localhost:9099/api/v1/auth/register")
                .then()
                .assertThat()
                .statusCode(200);
    }
    @Test
    public void testAuthentication() {
        String requestBody = "{\"username\": \"verka\", \"password\": \"123\"}";

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("http://localhost:9099/api/v1/auth/login")
                .then()
                .assertThat()
                .statusCode(200);
    }
}
