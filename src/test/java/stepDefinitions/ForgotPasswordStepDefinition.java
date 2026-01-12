package stepDefinitions;

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
public class ForgotPasswordStepDefinition {
    private static final String BASE_URI = "https://lms-hackathon-nov-2025-8dd40899c026.herokuapp.com/lms";
    private final HttpContext context;

    public ForgotPasswordStepDefinition(HttpContext context) {
        this.context = context;
    }

    // --- HELPER METHOD: Reduces duplication in @Given steps ---
    private void buildRequest(String email, String uri) {
        String reqBody = """
                {
                    "userLoginEmailId":"%s"
                }
                """;

        String body = String.format(reqBody, email);

        // 2. Build the request fluently
        RequestSpecification req = given()
                .baseUri(uri)
                .contentType(ContentType.JSON)
                .body(body);

        context.setRequest(req);
    }

    // Background Steps
    @Given("Admin sets No Auth for forgot-password")
    public void fp_admin_sets_no_auth() {
        // Write code here that turns the phrase above into concrete actions
        var req = new RequestSpecBuilder().setBaseUri(ConfigReader.getBaseUrl()).setContentType(ContentType.JSON)
                .build();
    }

    // --- GIVEN STEPS ---

    @Given("Admin creates request for forgot-password with valid credentials")
    public void admin_creates_request_for_forgot_password_with_valid_credentials() {
        buildRequest("team2@gmail.com", BASE_URI);
    }

    @When("Admin calls Post Https method for forgot-password with valid endpoint")
    public void admin_calls_post_https_method_for_forgot_password_with_valid_endpoint() {
        context.setResponse(context.getRequest().post("/login/forgotpassword/confirmEmail"));
    }

    @Then("^Admin receives for forgot-password (\\d+)(?:.*)$")
    public void admin_receives_status(int http_status) {
        log.info("Received HTTP status {}", context.getResponse().getStatusCode());
        assertThat(context.getResponse().getStatusCode()).isEqualTo(http_status);
    }

    @When("Admin calls Post Https method for forgot-password with invalid endpoint")
    public void admin_calls_post_https_method_for_forgot_password_with_invalid_endpoint() {
        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
        // TODO
    }
    @Then("Admin does not receive any status for forgot-password")
    public void admin_does_not_receive_any_status_for_forgot_password() {
        // TODO
    }

    @Given("Admin creates request for forgot-password with special characters in admin email")
    public void admin_creates_request_for_forgot_password_with_special_characters_in_admin_email() {
        buildRequest("@@@team2@gmail.com", BASE_URI);
    }

    @Given("Admin creates request for forgot-password with invalid admin email")
    public void admin_creates_request_for_forgot_password_with_invalid_admin_email() {
        buildRequest("team2@gmail.abc", BASE_URI);
    }

    @Given("Admin creates request for forgot-password with Null body")
    public void admin_creates_request_for_forgot_password_with_null_body() {
        context.setRequest(given().baseUri(BASE_URI).contentType(ContentType.JSON).body(""));
    }

    @When("Admin calls Post Https method for forgot-password with invalid content type")
    public void admin_calls_post_https_method_for_forgot_password_with_invalid_content_type() {
        context.setResponse(context.getRequest().contentType(ContentType.XML).post("/login/forgotpassword/confirmEmail"));
    }

}
/**/
