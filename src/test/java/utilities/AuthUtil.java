package utilities;

import endpoints.EndPoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static utilities.ConfigReader.*;

public class AuthUtil {

    public static String fetchToken() {
        String reqBody = """
                {
                    "userLoginEmailId":"%s",
                    "password":"%s"
                }
                """;

        String body = String.format(reqBody, getAdminEmail(), getAdminPassword());

        // 2. Build the request fluently
        RequestSpecification req = given()
                .baseUri(getBaseUrl())
                .contentType(ContentType.JSON)
                .body(body);

        Response res = req.post(EndPoints.USER_SIGN_IN.getEndpoint());

        return res.jsonPath().getString("token");
    }

    public static RequestSpecification buildLoginRequest(String email, String password, String uri) {
        String reqBody = """
                {
                    "userLoginEmailId":"%s",
                    "password":"%s"
                }
                """;

        String body = String.format(reqBody, email, password);
        return given()
                .baseUri(uri)
                .contentType(ContentType.JSON)
                .body(body);
    }
}
