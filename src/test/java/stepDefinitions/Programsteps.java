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

	@Given("Admin creates POST Request with different scenario")
	public void admin_creates_post_request_with_different_scenario() {
		
		String token = context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
		
	}

	@When("Admin sends HTTPS Request {string} with endpoint")
	public void admin_sends_https_request_with_endpoint(String scenarioName) {
		try {
			List<Map<String, String>> programdata = ExcelReader.getData(ConfigReader.getProperty("excelPath"),
					"program");
			endPoint = EndPoints.CREATE_PROGRAM.getEndpoint();
			for (Map<String, String> row : programdata) {
				if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {
					
					Program pgm = new Program();
					
					pgm.setprogramDescription(row.get("programDescription"));
					pgm.setprogramName(row.get("programName"));
					pgm.setprogramStatus(row.get("prgoramStatus"));
					
					Response response = context.getRequest().body(pgm).when().post(endPoint);
					
					context.setResponse(response);
					context.setRowData(row);
					
					LoggerLoad.info("Status Code: " + response.getStatusCode());
					if(response.getStatusCode() != 401 && response.getStatusCode() != 404) {
						LoggerLoad.info("Status Message: " + response.jsonPath().getString("message"));
					}
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

		ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
		//ResponseValidator.validateContentType(response.getContentType(), row.get("ContentType"));
		System.out.println("Status for User:"+response.getStatusCode());
		System.out.println("Status for User:"+response.getStatusLine());
		System.out.println("------body:" + response.asString());

	}

}
