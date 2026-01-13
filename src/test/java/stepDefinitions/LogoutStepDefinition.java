package stepDefinitions;

import static endpoints.EndPoints.*;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import context.HttpContext;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import utilities.ConfigReader;

@Slf4j
public class LogoutStepDefinition {
    private final HttpContext context;

    public LogoutStepDefinition(HttpContext context) {
        this.context = context;
    }

    // --- HELPER METHOD: Reduces duplication in @Given steps ---
    private void setAuthenticatedRequest(String email, String password) {
        // 1. Prepare the JSON body
        String reqBody = """
                {
                    "userLoginEmailId":"%s",
                    "password":"%s"
                }
                """.formatted(email, password);

        // 2. Execute the POST request immediately to get the token
        Response res = given()
                .baseUri(ConfigReader.getBaseUrl())
                .contentType(ContentType.JSON)
                .body(reqBody)
                .when()
                .post(USER_SIGN_IN.getEndpoint());

        // 3. Extract the token (Assuming the JSON key is "token")
        String token = res.jsonPath().getString("token");

//        // 4. Store both the request spec and the token in your context
//        context.setToken(token);

        // Pre-configure the next request with the Bearer token already attached
        RequestSpecification authenticatedReq = given()
                .baseUri(ConfigReader.getBaseUrl())
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON);

        context.setRequest(authenticatedReq);
    }

    @Given("Admin sets No Auth for logout")
    public void admin_sets_no_auth() {
        // 1. Build the specification
        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getBaseUrl())
                .setContentType(ContentType.JSON)
                .build();

        // 2. CRITICAL: Pass it to the context so other steps can see it
        context.setRequest(given().spec(req));
    }

    @Given("Admin sets authorization to bearer Token with old token")
    public void admin_sets_authorization_to_bearer_token_with_old_token() {
        // 1. Define an "old" or invalid token string
        String oldToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleHBpcmVkIn0.old_signature";

        // 2. Build the request with the "Authorization" header
        RequestSpecification req = RestAssured.given()
                .baseUri(ConfigReader.getBaseUrl())
                .header("Authorization", "Bearer " + oldToken)
                .contentType(ContentType.JSON);

        // 3. Save it to your context so the @When step can access it
        context.setRequest(req);
    }

    @Given("Admin sets authorization to bearer Token with token")
    public void admin_sets_authorization_to_bearer_token_with_token() {
        setAuthenticatedRequest(ConfigReader.getAdminEmail(), ConfigReader.getAdminPassword());
    }

    @Given("Admin creates request for logout")
    public void admin_creates_request_for_logout() {
        //
    }

    @When("Admin calls {string} method with {string} for logout")
    public void admin_calls_logout_method(String method, String type) {
        RequestSpecification req = context.getRequest();
        String endpoint = USER_LOGOUT.getEndpoint();

        // 1. Handle Endpoint/BaseURI Logic based on 'type'
        switch (type.toLowerCase()) {
            case "invalid endpoint" -> endpoint = INVALID.getEndpoint();
            case "invalid base url" -> req.baseUri(ConfigReader.getBaseUrl()+"abc");
            // default case is "valid endpoint", which uses USER_LOGOUT.getEndpoint()
        }

        // 2. Perform dynamic HTTP request
        // request(method, path) allows you to use the String parameter directly
        Response res = req.when().request(method.toUpperCase(), endpoint);

        // 3. Save to context
        context.setResponse(res);
    }

    @Then("Admin receives {int} for logout")
    public void admin_receives_for_logout(Integer http_status) {
        log.info("Received Response : {}", context.getResponse().getStatusLine());
        assertThat(context.getResponse().getStatusCode()).isEqualTo(http_status);
    }
}