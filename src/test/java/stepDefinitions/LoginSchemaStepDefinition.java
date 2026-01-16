package stepDefinitions;

import context.HttpContext;
import endpoints.EndPoints;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import utilities.AuthUtil;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utilities.ConfigReader.*;

@Slf4j
public class LoginSchemaStepDefinition {
    private final HttpContext ctx;

    public LoginSchemaStepDefinition(HttpContext ctx) {
        this.ctx = ctx;
    }

    @When("I make a {string} request to the {string} API")
    public void i_make_a_request_to_the_api(String method, String endpointName) {
        // 1. Resolve the Endpoint URL from your Enum or Config
        String path = switch (endpointName.toUpperCase()) {
            case "LOGIN" -> EndPoints.USER_SIGN_IN.getEndpoint();
            case "RESET_PASSWORD" -> EndPoints.USER_PASSWORD_RESET.getEndpoint();
            default -> throw new IllegalArgumentException("Unknown endpoint: " + endpointName);
        };

        // 2. Build the Request Specification
        // We assume AuthUtil sets the BaseURI, Body, and Content-Type
        RequestSpecification req = switch (endpointName.toUpperCase()) {
            case "LOGIN" -> AuthUtil.buildLoginRequest(getAdminEmail(), getAdminPassword(), getBaseUrl());
            default -> ctx.getRequest(); // Fallback to existing request in context
        };

        // 3. Execute the request dynamically (GET, POST, PUT, DELETE, etc.)
        var response = req.when()
                .request(method.toUpperCase(), path);

        // 4. Save to context for the @Then steps
        ctx.setResponse(response);
    }

    @Then("the response body should match the {string} schema")
    public void the_response_body_should_match_the_json_schema(String schemaFileName) {
        log.info("Validating response against schema: {}", schemaFileName);
        String file = String.format("schemas.user-login/%s",schemaFileName);
        // Perform the validation
        ctx.getResponse()
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(schemaFileName));
    }
}
