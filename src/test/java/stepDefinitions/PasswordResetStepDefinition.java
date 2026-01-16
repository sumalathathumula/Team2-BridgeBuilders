package stepDefinitions;

import context.HttpContext;
import endpoints.EndPoints;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import utilities.AuthUtil;

import static endpoints.EndPoints.USER_PASSWORD_RESET;
import static io.restassured.RestAssured.given;
import static utilities.ConfigReader.*;

@Slf4j
public class PasswordResetStepDefinition {

    private final HttpContext ctx;
    private String requestBody;

    public PasswordResetStepDefinition(HttpContext context) {
        this.ctx = context;
    }

    @Builder
    static class ResetRequest {
        String password;
        String userLoginEmailId;
    }

    // --- GIVEN STEPS ---

    @Given("Admin sets {string} authorization for reset password")
    public void admin_sets_authorization_for_reset_password(String auth_type) {
        // Initialize base request
        RequestSpecification req = given()
                .baseUri(getBaseUrl())
                .contentType(ContentType.JSON);

        // Add Token if authorization is required
        if (!auth_type.equalsIgnoreCase("no")) {
            String token = AuthUtil.fetchToken();
            req.header("Authorization", "Bearer " + token);
        }

        ctx.setRequest(req);
    }

    @Given("Admin creates reset password request with {string} email and {string} password")
    public void admin_creates_reset_password_request_with_email_and_password(String email_state, String password_state) {
        String email = switch (email_state.toLowerCase()) {
            case "valid" -> getAdminEmail();
            case "new" -> "adc@gmail.com";
            case "invalid" -> "invalid_email_format";
            default -> "123";
        };

        String password = switch (password_state.toLowerCase()) {
            case "old" -> getAdminPassword();
            case "new" -> "Abcd@123";
            case "special_char" -> "~!@#$%^&&&";
            default -> getAdminPassword();
        };

        String template = """
                {
                    "userLoginEmailId":"%s",
                    "password":"%s"
                }
                """;
        requestBody = String.format(template, email, password);
    }

    // --- WHEN STEPS ---

    /**
     * Handles the Scenario Outline logic for dynamic Methods and Endpoints
     */
    @When("Admin calls {string} method with {string} endpoint for reset password")
    public void admin_calls_method_with_endpoint_for_reset_password(String method, String endpoint_type) {
        String endpoint = endpoint_type.equalsIgnoreCase("valid")
                ? USER_PASSWORD_RESET.getEndpoint()
                : EndPoints.INVALID.getEndpoint();

        var res = ctx.getRequest()
                .body(requestBody)
                .when()
                .request(method.toUpperCase(), endpoint);

        ctx.setResponse(res);
    }

    /**
     * Specifically handles the @Reset-Negative-BaseURL scenario
     */
    @When("Admin calls Post Https method for reset password with invalid base URI")
    public void admin_calls_post_with_invalid_base_uri() {
        RequestSpecification req = ctx.getRequest();

        // Pointing to a valid host with an invalid path extension to trigger a 404/400
        String invalidBase = getBaseUrl() + "/invalid-root-path";
        req.baseUri(invalidBase);

        var res = req.body(requestBody)
                .when()
                .post(USER_PASSWORD_RESET.getEndpoint());

        ctx.setResponse(res);
    }

    /**
     * Specifically handles the @Reset-Negative-ContentType scenario
     */
    @When("Admin calls Post Https method for reset password with invalid content type")
    public void admin_calls_post_with_invalid_content_type() {
        var res = ctx.getRequest()
                .contentType(ContentType.XML) // Overriding JSON with XML
                .body(requestBody)
                .when()
                .post(USER_PASSWORD_RESET.getEndpoint());

        ctx.setResponse(res);
    }

    // --- THEN STEPS ---

    @Then("Admin receives {int} for reset password")
    public void admin_receives_for_reset_password(Integer status) {
        log.info("Response Status Line: {}", ctx.getResponse().statusLine());
        log.info("Response Body: \n{}", ctx.getResponse().asPrettyString());

        Assertions.assertThat(ctx.getResponse().getStatusCode())
                .withFailMessage("Expected status %d but got %d", status, ctx.getResponse().getStatusCode())
                .isEqualTo(status);
    }
}