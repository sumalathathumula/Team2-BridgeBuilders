package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

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
	

	@Given("Admin has valid authorization to create Program Id")
	public void admin_has_valid_authorization_to_create_program_id() {
		endPoint = EndPoints.CREATE_PROGRAM.getEndpoint();
	}
	
	@Given("Admin creates POST Request with different scenario")
	public void admin_creates_post_request_with_different_scenario() {
		endPoint = EndPoints.CREATE_PROGRAM.getEndpoint();
		String token = context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
		
	}

	@When("Admin sends HTTPS Request {string} with endpoint")
	public void admin_sends_https_request_with_endpoint(String scenarioName) {
		try {
			List<Map<String, String>> programdata = ExcelReader.getData(ConfigReader.getProperty("excelPath"), "Program");
			for (Map<String, String> row : programdata) {
				if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {
					
					Program pgm = new Program();
					
					pgm.setprogramDescription(row.get("programDescription"));
					pgm.setprogramName(row.get("programName"));
					pgm.setprogramStatus(row.get("programStatus"));
					
					Response response = context.getRequest().body(pgm).when().post(endPoint);
					
					context.setResponse(response);
					context.setRowData(row);
					
					LoggerLoad.info("Status Code: " + response.getStatusCode());
					if(response.getStatusCode() != 401 && response.getStatusCode() != 404) {
						LoggerLoad.info("Status Message: " + response.jsonPath().getString("message"));
					}
					break;
				}
					
			}

		} catch (Exception e) {
			LoggerLoad.error(e.getMessage());
		}
		
		
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

	@Given("Admin creates GET Request for LMS API")
	public void admin_creates_get_request_for_lms_api() {
	    endPoint = EndPoints.GET_ALL_PROGRAMS_INVALID.getEndpoint();
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
	
}
