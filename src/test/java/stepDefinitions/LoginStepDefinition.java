package stepDefinitions;

import static endpoints.EndPoints.INVALID;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import context.HttpContext;
import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import utilities.ConfigReader;

@Slf4j
public class LoginStepDefinition {
    private static final String BASE_URI = "https://lms-hackathon-nov-2025-8dd40899c026.herokuapp.com/lms";
    private final HttpContext context;

    public LoginStepDefinition(HttpContext context) {
        this.context = context;
    }

    // --- HELPER METHOD: Reduces duplication in @Given steps ---
    private void buildRequest(String email, String password, String uri) {
        String reqBody = """
                {
                    "userLoginEmailId":"%s",
                    "password":"%s"
                }
                """;

        String body = String.format(reqBody, email, password);

        // 2. Build the request fluently
        RequestSpecification req = given()
                .baseUri(uri)
                .contentType(ContentType.JSON)
                .body(body);

        context.setRequest(req);
    }

    // Background Steps
    @Given("Admin sets No Auth")
    public void admin_sets_no_auth() {
        // Write code here that turns the phrase above into concrete actions
        var req = new RequestSpecBuilder().setBaseUri(ConfigReader.getBaseUrl()).setContentType(ContentType.JSON)
                .build();
    }

    // --- GIVEN STEPS ---

    @Given("Admin creates request with valid credentials for login")
    public void admin_creates_request_with_valid_credentials_for_user_mgmt() {
        buildRequest("team2@gmail.com", "ApiHackathon2@2", BASE_URI);
    }

    @Given("Admin creates request with invalid admin email for login")
    public void admin_creates_request_with_invalid_email() {
        buildRequest("team2@gmail.com", "ApiHackathon2@2", BASE_URI);
    }

    @Given("Admin creates request with special characters in admin email for login")
    public void admin_creates_request_with_special_characters_in_admin_email() {
        buildRequest("@@@team2@gmail.com", "ApiHackathon2@2", BASE_URI);
    }

    @Given("Admin creates request with special characters in password for login")
    public void admin_creates_request_with_special_characters_in_password() {
        buildRequest("team2@gmail.com", "ApiHackathon2@2!@#$", BASE_URI);
    }

    @Given("Admin creates request with numbers in email for login")
    public void admin_creates_request_with_numbers_in_email() {
        buildRequest("12345@gmail.com", "ApiHackathon2@2", BASE_URI);
    }

    @Given("Admin creates request with numbers in password for login")
    public void admin_creates_request_with_numbers_in_password() {
        buildRequest("team2@gmail.com", "12345678", BASE_URI);
    }

    @Given("Admin creates request with Null password for login")
    public void admin_creates_request_with_null_password() {
        // Omitting password field via logic or manual string
        context.setRequest(given().baseUri(BASE_URI).contentType(ContentType.JSON).body("{\"userLoginEmailId\":\"team2@gmail.com\"}"));
    }

    @Given("Admin creates request with Null email for login")
    public void admin_creates_request_with_null_email() {
        context.setRequest(given().baseUri(BASE_URI).contentType(ContentType.JSON).body("{\"password\":\"ApiHackathon2@2\"}"));
    }

    @Given("Admin creates request with Null body for login")
    public void admin_creates_request_with_null_body() {
        buildRequest(null, null, BASE_URI);
    }

    // --- WHEN STEPS ---

    @When("Admin calls Post Https method with valid endpoint for login")
    public void admin_calls_post_https_method_with_valid_endpoint() {
        context.setResponse(context.getRequest().post("/login"));
    }

    @When("Admin calls GET HTTPS method with post endpoint for login")
    public void admin_call_get_https_method_with_post_endpoint() {
        context.setResponse(context.getRequest().get("/login"));
    }

    @When("Admin calls Post Https method with {string} for login")
    public void admin_calls_post_with_path(String path) {
        // Generic approach for invalid endpoints
        context.setResponse(context.getRequest().post(path));
    }

    @When("Admin calls Post Https method with invalid content type for login")
    public void admin_calls_post_https_method_with_invalid_content_type() {
        context.setResponse(context.getRequest().contentType(ContentType.XML).post("/login"));
    }


    @When("Admin calls Post Https method with invalid base URI for login")
    public void admin_calls_post_https_method_with_invalid_base_uri() {
        RequestSpecification req = context.getRequest();

        // We use the REAL host but add a 'bad' segment to the URI
        // This triggers a server response instead of a network crash
        String invalidBase = "https://lms-hackathon-nov-2025-8dd40899c026.herokuapp.com/!!invalid!!";

        req.baseUri(invalidBase);

        // Execute the request
        var res = req.when().post("/login");

        // Save response - now res.getStatusCode() will be 400 or 404 instead of throwing an Exception
        context.setResponse(res);
    }

    @When("Admin calls Post Https method with invalid endpoint for login")
    public void admin_calls_post_https_method_with_invalid_endpoint() {
        // 1. Retrieve the request from context
        RequestSpecification req = context.getRequest();

        // 2. Use the path defined in your Enum: EndPoints.INVALID.getEndpoint()
        String invalidPath = INVALID.getEndpoint();

        // 3. Execute the POST request
        var res = req.when().post(invalidPath);

        // 4. Save response to context for assertion
        context.setResponse(res);

        log.info("Request sent to invalid endpoint: {}", invalidPath);
    }

    @Then("Admin receives {int} for Login")
    public void admin_receives_status(int http_status) {
        log.info("Received HTTP status {}", context.getResponse().getStatusCode());
        assertThat(context.getResponse().getStatusCode()).isEqualTo(http_status);
    }

    @Then("Admin receives {int} created with auto generated token for login")
    public void admin_receives_created_with_auto_generated_token_for_user_mgmt(Integer http_status) {
        log.info("Received HTTP status {}", context.getResponse().getStatusCode());

        assertThat(context.getResponse().getStatusCode()).isEqualTo(http_status);
    }

    @Then("^Admin does not receive any status")
    public void admin_receives_invalid() {
        log.info("No response is received");
    }
}