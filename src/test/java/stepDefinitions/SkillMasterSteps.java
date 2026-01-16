package stepDefinitions;
import APIRequests.SkillMasterRequest;
import context.ScenarioContext;
import endpoints.EndPoints;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.testng.CucumberPropertiesProvider;
import io.restassured.response.Response;
import utilities.*;

import java.util.Map;
import java.util.List;
import java.util.StringJoiner;


import static io.cucumber.java.Scenario.*;
import static io.restassured.RestAssured.given;
import static utilities.ExcelReader.*;



public class SkillMasterSteps {
    private final ScenarioContext context = ScenarioContext.getInstance();
    private final SkillMasterRequest skillRequest = new SkillMasterRequest();
    private String endPoint;

    @Given("Admin sets Authorization to Bearer Token to create skill")
public void admin_sets_authorization_to_bearer_token_to_create_skill() {
    endPoint = EndPoints.CREATE_NEWSKILL.getEndpoint();

}
    @Given("Admin creates request with valid data in request body for create skill")
    public void admin_creates_request_with_valid_data_in_request_body_for_create_skill() {
        skillRequest.prepareCreateSkillMasterRequest();
        String token = context.getToken();
        context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));

    }
    @When("Admin sends HTTPS Request with data from row {string}")
    public void admin_sends_https_request_with_data_from_row(String scenarioName) throws Exception {
        //SkillMasterRequest skillRequest = new SkillMasterRequest();
        skillRequest.createSkillFromExcelRow(scenarioName);

    }
    @Then("the response body should be equal to Expected Status message.")
    public void the_response_body_should_be_equal_to_expected_status_message() {
        Response response = context.getResponse();
        Map<String, String> row = context.getRowData();
        int expectedStatus = Integer.parseInt(row.get("ExpectedStatusCode"));
        int actStatusCode= response.getStatusCode();
       ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
     //  ResponseValidator.validateContentType(response.getContentType(), row.get("ContentType"));
        System.out.println("Status for Skill:"+response.getStatusCode());
        System.out.println("Status for Skill:"+response.getStatusLine());
        System.out.println("body: "+response.asString());

        if (expectedStatus == 201 && actStatusCode == 201) {
            String skillID = response.jsonPath().getString("skill.skillId");
            context.setSkillId(skillID);

        }
        System.out.println("Status for SkillID:"+context.getSkillId());




    }
    //GETALLSKILLMASTER
    @Given("Admin creates GET Request for the LMS API")
    public void admin_creates_get_request_for_the_lms_api() {
        endPoint = EndPoints.GETALL_SKILLMASTER.getEndpoint();
        String token= context.getToken();
        context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
    }

    @When("Admin sends HTTPS Request with Valid Endpoint")
    public void admin_sends_https_request_with_valid_endpoint() {
        Response response = context.getRequest().when().get(endPoint);
        context.setResponse(response);
    }

    @Then("Admin receives {int} with OK message")
    public void admin_receives_with_ok_message(Integer expStatusCode) {
        int actStatusCode = context.getResponse().getStatusCode();
        LoggerLoad.info("actStatusCode : "+actStatusCode);
        ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
    }

    @Given("Admin creates GET Request for  LMS API")
    public void admin_creates_get_request_for_lms_api() {
        endPoint = EndPoints.GETALL_SKILLMASTERINVALID.getEndpoint();
        String token= context.getToken();
        context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
    }

    @When("Admin sends HTTPS Request with InValid Endpoint")
    public void admin_sends_https_request_with_in_valid_endpoint() {
        Response response = context.getRequest().when().get(endPoint);
        context.setResponse(response);
    }

    @Then("Admin receives {int} with Not Found message")
    public void admin_receives_with_not_found_message(Integer expStatusCode) {
        int actStatusCode = context.getResponse().getStatusCode();
        LoggerLoad.info("actStatusCode : "+actStatusCode);
        ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
    }
//GETSKILLBYSKILLMASTERNAME
    @Given("Admin creates GET Request for the LMS API endpoint with valid skill master name")
    public void admin_creates_get_request_for_the_lms_api_endpoint_with_valid_skill_master_name() {
        endPoint = EndPoints.GETSKILL_BYSKILLMASTERNAME.getEndpoint();
        String token= context.getToken();

        context.setRequest(
                given()
                        .filter((req, res, ctx) -> {
                            System.out.println("Final Request URI: " + req.getURI());
                            return ctx.next(req, res);
                        })
                        .pathParam("skillMasterName", context.getSkillName())
                        .spec(RequestSpecFactory.withAuth(token))
        );
    }

    @When("Admin sends HTTPS Request with valid endpoint as skill master name")
    public void admin_sends_https_request_with_valid_endpoint_as_skill_master_name() {
        Response response = context.getRequest().when().get(endPoint);
        context.setResponse(response);
    }

    @Then("Admin receives {int} Status with OK message")
    public void admin_receives_status_with_ok_message(Integer expStatusCode) {
        int actStatusCode = context.getResponse().getStatusCode();
        LoggerLoad.info("actStatusCode : "+actStatusCode);
        ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
    }

    //GETSKILLBYSKILLMASTERNAMEINVALID
    @Given("Admin creates GET Request for  LMS API with invalid skill master name")
    public void admin_creates_get_request_for_lms_api_with_invalid_skill_master_name() {
        endPoint = EndPoints.GETSKILL_BYSKILLMASTERNAMEINVALID.getEndpoint();
        String token= context.getToken();

        context.setRequest(
                given()
                        .filter((req, res, ctx) -> {
                            System.out.println("Final Request URI: " + req.getURI());
                            return ctx.next(req, res);
                        })
                        .pathParam("skillMasterName", context.getSkillName())
                        .spec(RequestSpecFactory.withAuth(token))
        );
    }

    @When("Admin sends HTTPS Request with InValid SkillMasterName")
    public void admin_sends_https_request_with_in_valid_skill_master_name() {
        Response response = context.getRequest().when().get(endPoint);
        context.setResponse(response);

    }

    @Then("Admin receives  the {int} with Not found  message")
    public void admin_receives_the_with_not_found_message(Integer expStatusCode) {
        int actStatusCode = context.getResponse().getStatusCode();
        LoggerLoad.info("actStatusCode : "+actStatusCode);
        ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
    }
//
////DELETESKILLBYSKILLID
//@Given("Admin creates  DELETE Request for the LMS API endpoint")
//public void admin_creates_delete_request_for_the_lms_api_endpoint() {
//    switch (Scenario.toLowerCase()) {
//
//        case "validskillid":
//            context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
//            endPoint = EndPoints.DELETESKILL_BYSKILLID.getEndpoint();
//            break;
//
//        case "invalidendpoint":
//            context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
//            endPoint = EndPoints.DELETESKILL_BYSKILLIDINVALID.getEndpoint() + context.getSkillId("SKILL");
//            break;
//
//        case "invalidskillid":
//            context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
//            endPoint = EndPoints.DELETESKILL_BYSKILLID.getEndpoint() + 922;
//            break;
//
//
//        default:
//            throw new IllegalArgumentException("Unsupported scenario type: " + scenario);
//    }
//}
//
//
//
//@When("Admin sends HTTPS Request from row {string}")
//public void admin_sends_https_request_from_row(String scenarioName) {
//    Response response = context.getRequest().when().delete(endPoint);
//    context.setResponse(response);
//    LoggerLoad.info("DELETE Skill request sent for scenario: " + scenarioName);
//}
//
//@Then("the response body should be equal to {string} and {string}.")
//public void the_response_body_should_be_equal_to_and(String string, String string2) {
//    Response response = context.getRequest().when().get(endPoint);
//    context.setResponse(response);
//    }
//
//}
//
