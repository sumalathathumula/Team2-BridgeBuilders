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
		//endPoint = EndPoints.CREATE_PROGRAM.getEndpoint();
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
			System.out.println("programId for batch:" +context.getProgramId("BATCH"));
			System.out.println("programName for batch:" +context.getProgramName("BATCH"));
			System.out.println("programId for delete:" +context.getProgramId("DELETE_BY_ID"));
			System.out.println("programName for delete:" +context.getProgramName("DELETE_BY_NAME"));			
			
		}  
			

	}
	

}
