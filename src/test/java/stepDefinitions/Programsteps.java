package stepDefinitions;

import context.ScenarioContext;
import endpoints.EndPoints;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import models.Program;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import utilities.RequestSpecFactory;
import utilities.ResponseValidator;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import APIRequests.ProgramRequest;
import APIRequests.UserRequest;

public class Programsteps {
	private final ScenarioContext context = ScenarioContext.getInstance();
	private String endPoint;
	private final ProgramRequest programRequest = new ProgramRequest();
	
	@Given("Admin has valid authorization to create Program Id")
	public void admin_has_valid_authorization_to_create_program_id() {
		endPoint = EndPoints.CREATE_PROGRAM.getEndpoint();
	}
	
	@Given("Admin creates POST Request with different scenario")
	public void admin_creates_post_request_with_different_scenario() {
		
		programRequest.prepareProgramRequest();
		String token = context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
		
	}

	@When("Admin sends HTTPS Request {string} with endpoint")
	public void admin_sends_https_request_with_endpoint(String scenarioName) throws InvalidFormatException, IOException {
		programRequest.createProgramFromExcelRow(scenarioName);
		
	}

	@Then("Admin receives status code with response body")
	public void admin_receives_status_code_with_response_body() {
		Response response = context.getResponse();
		Map<String, String> row = context.getRowData();
		System.out.println("------body:" + response.asString());
		
		int expectedStatus = Integer.parseInt(row.get("ExpectedStatusCode"));
		int actStatusCode= response.getStatusCode();

		ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
		//ResponseValidator.validateContentType(response.getContentType(), row.get("ContentType"));
		System.out.println("Status for Program:"+response.getStatusCode());
		System.out.println("Status for Program:"+response.getStatusLine());
		System.out.println("------body:" + response.asString());
		if (expectedStatus == 201 && actStatusCode == 201) {
			int programId = Integer.parseInt(response.jsonPath().getString("programId"));
			String programName = response.jsonPath().getString("programName");

			//context.addProgramId(programId);
			//context.addProgramName(programName);
			context.addProgram("BATCH", programId, programName);
			context.addProgram("DELETE_BY_ID", programId, programName);
			context.addProgram("DELETE_BY_NAME", programId, programName);

			LoggerLoad.info("programId :" + programId);
			LoggerLoad.info("programName :" + programName);					
			
		}  			

	}
	
	//////
	//GETALLPROGRAMS
	@Given("Admin creates GET Request for the LMS API")
	public void admin_creates_get_request_for_the_lms_api() {
		//programRequest.getallprogram();
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
		//programRequest.getallprogramInvalid();
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
			        .pathParam("programId", context.getProgramId("DELETE_BY_ID"))
			        .spec(RequestSpecFactory.withAuth(token))
			);
	}
	
	
	@When("Admin sends HTTPS Request with with valid endpoint")
	public void admin_sends_https_request_with_with_valid_endpoint() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
	}
	
	//GETPROGRAMBYPROGRAMID-negative
	
	@Given("Admin creates GET Request with  Invalid programId for the LMS API")
	public void admin_creates_get_request_with_invalid_program_id_for_the_lms_api() {
		endPoint = EndPoints.GET_PROGRAM_BYPROGRAMID.getEndpoint()+"/";
	    String token= context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
		
		
	
	}
	@When("Admin sends HTTPS Request with with Invalid endpoint")
	public void admin_sends_https_request_with_with_invalid_endpoint() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
	}
	
	
	//GETALLPROGRAMWITHUSER
	
	@Given("Admin creates GET Request to get all programs with user details")
	public void admin_creates_get_request_to_get_all_programs_with_user_details() {
		endPoint = EndPoints.GET_ALLPROGRAMS_WITHUSERS.getEndpoint();
	    String token= context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
	}
	@When("Admin sends Request with valid endpoint")
	public void admin_sends_request_with_valid_endpoint() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
	}
	@Then("Admin receives {int} OK with response body")
	public void admin_receives_ok_with_response_body(Integer expStatusCode) {
		int actStatusCode = context.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	
	//GETALLPROGRAMWITHUSER-Negative
	@Given("Admin creates GET Request to get all program user details")
	public void admin_creates_get_request_to_get_all_program_user_details() {
		endPoint = EndPoints.GET_ALLPROGRAMS_WITHUSERS.getEndpoint()+"1";
	    String token= context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
	}
	@When("Admin sends Request with Invalid endpoint")
	public void admin_sends_request_with_invalid_endpoint() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
	}
	
	//UPDATEPROGRAMBYPROGRAMID
	@Given("Admin updates PUT Request with valid Endpoint")
	public void admin_updates_put_request_with_valid_endpoint() {
		programRequest.updateprogrambyprogramid();
	}
	@When("Admin sends HTTPS {string} RequestBody")
	public void admin_sends_https_request_body(String Scenario) throws Exception {
		programRequest.updateProgramIdFromExcelRow(Scenario);
	}
	@Then("Admin receives status code and message")
	public void admin_receives_status_code_and_message() {
		Response response = context.getResponse();
		Map<String, String> row = context.getRowData();
		int expectedStatus = Integer.parseInt(row.get("ExpectedStatusCode"));
		int actStatusCode= response.getStatusCode();
		ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
		System.out.println("body: "+response.asString());
	}
	
	//UPDATEPROGRAMBYPROGRAMNAME
	@Given("Admin updates PUT Request for program by program name")
	public void admin_updates_put_request_for_program_by_program_name() {
		programRequest.updateprogrambyprogramname();
	}
	@When("Admin sends HTTPS {string} RequestBody for update")
	public void admin_sends_https_request_body_for_update(String string) {
		programRequest.updateprogrambyprogramname();
	  
	}
	@Then("Admin receives status code with status message")
	public void admin_receives_status_code_with_status_message() {
		Response response = context.getResponse();
		Map<String, String> row = context.getRowData();
		int expectedStatus = Integer.parseInt(row.get("ExpectedStatusCode"));
		int actStatusCode= response.getStatusCode();
		ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
		System.out.println("body: "+response.asString());
	}
	
	

}
