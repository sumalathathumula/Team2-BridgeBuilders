package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

import APIRequests.ProgramRequest;
import context.ScenarioContext;
import endpoints.EndPoints;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Program;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import utilities.RequestSpecFactory;
import utilities.ResponseValidator;

public class Programsteps {

	RequestSpecification reqspec;
	public static Response response;
	private final ScenarioContext context = ScenarioContext.getInstance();
	private String endPoint;
	
	private final ProgramRequest pgm = new ProgramRequest();
	

	@Given("Admin has valid authorization to create Program Id")
	public void admin_has_valid_authorization_to_create_program_id() {
		pgm.CreateProgramRequest();
	}
	
	@Given("Admin creates POST Request with different scenario")
	public void admin_creates_post_request_with_different_scenario() {
		pgm.CreateProgramRequest();
		String token = context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
		
	}

	@When("Admin sends HTTPS Request {string} with endpoint")
	public void admin_sends_https_request_with_endpoint(String scenarioName) throws Exception {
		pgm.CreateProgramfromExcel(scenarioName);
		
	}

	@Then("Admin receives status code with response body")
	public void admin_receives_status_code_with_response_body() {
		Response response = context.getResponse();
		Map<String, String> row = context.getRowData();
		
		int expectedStatus = Integer.parseInt(row.get("ExpectedStatusCode"));
		int actStatusCode= response.getStatusCode();
		
		ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
		//ResponseValidator.validateContentType(response.getContentType(), row.get("ContentType"));
		System.out.println("Status for User:"+response.getStatusCode());
		System.out.println("Status for User:"+response.getStatusLine());
		System.out.println("------body:" + response.asString());
			

	}
	//GETALLPROGRAMS
	@Given("Admin creates GET Request for the LMS API")
	public void admin_creates_get_request_for_the_lms_api() {
		endPoint = EndPoints.GET_ALL_PROGRAMS.getEndpoint();
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
//GETALLPROGRAMS-Negative
	@Given("Admin creates GET Request for LMS API")
	public void admin_creates_get_request_for_lms_api() {
		endPoint = EndPoints.GET_ALL_PROGRAMS.getEndpoint()+"1";
	    String token= context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
	    
	}
	@When("Admin sends HTTPS Request with InValid Endpoint")
	public void admin_sends_https_request_with_in_valid_endpoint() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
	}
	@Then("Admin receives {int} with Not found message")
	public void admin_receives_with_not_found_message(Integer expStatusCode) {
		int actStatusCode = context.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	//GETPROGRAMBYPROGRAMID
	@Given("Admin creates GET Request with  valid programId for the LMS API")
	public void admin_creates_get_request_with_valid_program_id_for_the_lms_api() {
	   endPoint = EndPoints.GET_PROGRAM_BYPROGRAMID.getEndpoint();
	   String token = context.getToken();
	   //int programId = testContext.getProgramId(0);

	   // Response response = ProgramRequest.getProgramById(programId);
	   context.setRequest(
			    given()
			        .filter((req, res, ctx) -> {
			            System.out.println("Final Request URI: " + req.getURI());
			            return ctx.next(req, res);
			        })
			        .pathParam("programId", 16)
			        .spec(RequestSpecFactory.withAuth(token))
			);
	}
	@When("Admin sends HTTPS Request with with valid endpoint")
	public void admin_sends_https_request_with_with_valid_endpoint() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
	}
	
}
