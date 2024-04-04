import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;

public class CrudAPITest {

    @Test
    public void testCreateUser() {
        String requestBody = "{\"name\": \"vera\", \"username\": \"vera@icloud.com\", \"password\": \"123\"}";

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("http://localhost:9097/api/v1/crud/users")
                .then()
                .assertThat()
                .statusCode(200); // Замените 200 на ожидаемый код ответа
    }
}

