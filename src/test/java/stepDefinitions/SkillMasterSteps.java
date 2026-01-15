package stepDefinitions;

import endpoints.EndPoints;
import io.cucumber.java.an.E;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class SkillMasterSteps {
    @Given("Admin sets Authorization to Bearer Token to create skill")
public void admin_sets_authorization_to_bearer_token_to_create_skill() {
    endPoint = EndPoints.CREATE_NEWSKILL.getEndpoint();

}
    @Given("Admin creates request with valid data in request body for create skill")
    public void admin_creates_request_with_valid_data_in_request_body_for_create_skill() {
        skillRequest.prepareCreateSkillRequest();
        String token=context.getToken();
        context.setRequest(given().spec(requestSpecFactory.withAuth(token)));
    }
    @When("Admin sends HTTPS Request with data from row {string}")
    public void admin_sends_https_request_with_data_from_row(String scenarioName) throws Exception {
        skillRequest.createSkillFromExcelRow(scenarioName);

    }
    @Then("the response body should be equal to Expected Status message.")
    public void the_response_body_should_be_equal_to_expected_status_message() {
        Response response = context.getResponse();
        Map<String,String> row = context.getRowData();
        int expectedStatus = Integer.parseInt(row.get("ExpectedStatusCode"));
        int actStatusCode= response.getStatusCode();

        ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
//ResponseValidator.validateContentType(response.getContentType(), row.get("ContentType"));
        System.out.println("Status for Skill:"+response.getStatusCode());
        System.out.println("Status for Skill:"+response.getStatusLine());
        System.out.println("body: "+response.asString());

        if (expectedStatus == 201 && actStatusCode == 201) {
            String skillID = response.jsonPath().getString("skill.skillId");
            context.setSkillId(skillID); // Setting userid for chaining

        }
        System.out.println("Status for UserID:"+context.getSkillId());




    }
}
