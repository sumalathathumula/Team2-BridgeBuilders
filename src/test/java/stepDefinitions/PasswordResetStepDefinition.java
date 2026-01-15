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

    @Given("Admin sets {string} authorization for reset password")
    public void admin_sets_authorization_for_reset_password(String auth_type) {
        // 1. Initialize the base request with common settings
        RequestSpecification req = given()
                .baseUri(getBaseUrl())
                .contentType(ContentType.JSON);

        // 2. Add the Bearer Token only if 'no' is not specified
        if (!auth_type.equalsIgnoreCase("no")) {
            String token = AuthUtil.fetchToken();
            req.header("Authorization", "Bearer " + token);
        }

        // 3. Save the prepared request to the context once
        ctx.setRequest(req);
    }

    @Given("Admin creates reset password request with {string} email and {string} password")
    public void admin_creates_reset_password_request_with_email_and_password(String email_state, String password_state) {
        String email = switch (email_state.toLowerCase()) {
            case "valid" -> getAdminEmail();
            case "new" -> "adc@gmail.com";
            default -> "123";
        };
        String password = switch (password_state.toLowerCase()) {
            case "old" -> getAdminPassword();
//            case "old" -> "Abcd@123";
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

    @When("Admin calls {string} method with {string} endpoint for reset password")
    public void admin_calls_method_with_endpoint_for_reset_password(String method, String endpoint_type) {
        // 1. Resolve endpoint dynamically
        String endpoint = endpoint_type.equalsIgnoreCase("valid")
                ? EndPoints.USER_PASSWORD_RESET.getEndpoint()
                : EndPoints.INVALID.getEndpoint();

        // 2. Execute the request dynamically based on the HTTP verb provided in Gherkin
        var res = ctx.getRequest()
                .body(requestBody)
                .when()
                .request(method.toUpperCase(), endpoint);

        // 3. Save response to context for validation
        ctx.setResponse(res);
    }

    @Then("Admin receives {int} for reset password")
    public void admin_receives_for_reset_password(Integer status) {
        log.info(ctx.getResponse().statusLine());
        log.info(ctx.getResponse().asPrettyString());
        Assertions.assertThat(ctx.getResponse().getStatusCode()).isEqualTo(status);
    }

}
