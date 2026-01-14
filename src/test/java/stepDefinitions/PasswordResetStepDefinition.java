package stepDefinitions;

import context.HttpContext;
import endpoints.EndPoints;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import utilities.ConfigReader;

import static io.restassured.RestAssured.given;
import static utilities.ConfigReader.*;

@Slf4j
public class PasswordResetStepDefinition {

    private HttpContext ctx;

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
    public void admin_sets_authorization_for_reset_password(String string) {
        String token = AuthUtil.fetchToken();
        RequestSpecification req = given()
                .baseUri(getBaseUrl())
                .header("Authorization", String.format("Bearer %s", token))
                .contentType(ContentType.JSON);

        ctx.setRequest(req);
    }

    @Given("Admin creates reset password request with {string} email and {string} password")
    public void admin_creates_reset_password_request_with_email_and_password(String string, String string2) {
        String template = """
                {
                    "userLoginEmailId":"%s",
                    "password":"%s"
                }
                """;
        requestBody = String.format(template, getAdminEmail(), getAdminPassword());
    }

    @When("Admin calls {string} method with {string} endpoint for reset password")
    public void admin_calls_method_with_endpoint_for_reset_password(String string, String string2) {
        ctx.setResponse(ctx.getRequest().body(requestBody).post(EndPoints.USER_PASSWORD_RESET.getEndpoint()));
    }

    @Then("Admin receives {int} for reset password")
    public void admin_receives_for_reset_password(Integer status) {
        log.info(ctx.getResponse().statusLine());
        Assertions.assertThat(ctx.getResponse().getStatusCode()).isEqualTo(status);
    }

}
