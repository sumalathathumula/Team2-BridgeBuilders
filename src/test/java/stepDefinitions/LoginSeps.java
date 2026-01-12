package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import context.ScenarioContext;
import endpoints.EndPoints;
import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Login;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import utilities.RequestSpecFactory;
import utilities.ResponseValidator;

public class LoginSeps {

//	 RequestSpecification req_Base;
//	 Response response;
	private final ScenarioContext context = ScenarioContext.getInstance();
	private String endPoint;

	@Given("admin sets No Auth")
	public void admin_sets_no_auth() {
		endPoint = EndPoints.USER_SIGN_IN.getEndpoint();
		//context.setRequest(RequestSpecFactory.withoutAuth());
	}

	@Given("Admin creates request with valid credentials")
	public void admin_creates_request_with_valid_credentials() {
		endPoint = EndPoints.USER_SIGN_IN.getEndpoint();
		context.setRequest(given().spec(RequestSpecFactory.withoutAuth()));

	}

	@When("Admin calls Post HTTPS method with valid endpoint with data from row {string}")
	public void admin_calls_post_https_method_with_valid_endpoint_with_data_from_row(String scenarioName)
			throws InvalidFormatException, IOException {

//	List<Map<String, String>> data =
//            ExcelReader.getData(ConfigReader.getProperty("excelPath"), "Login");
		// Temporary hardcoded path for testing
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/Testdata.xlsx";

		List<Map<String, String>> data = ExcelReader.getData(excelPath, "Login");

		for (Map<String, String> row : data) {
			if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {

				Login login = new Login();
				login.setuserLoginEmailId(row.get("userLoginEmailId"));
				login.setpassword(row.get("password"));

				Response response = context.getRequest().body(login).when().post(endPoint);

				context.setResponse(response);
				context.setRowData(row);
				break;
			}
		}

	}

	@Then("Admin receives {int} created with auto generated token")
	public void admin_receives_created_with_auto_generated_token(Integer s) {
//		response.then().statusCode(s);
//		System.out.println("body: "+response.asString());
		Response response = context.getResponse();
		Map<String, String> row = context.getRowData();

		int expectedStatus = Integer.parseInt(row.get("ExpectedStatusCode"));

		ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
		ResponseValidator.validateContentType(response.getContentType(), row.get("ContentType"));

		if (expectedStatus == 200) {
			String token = response.jsonPath().getString("token");
			context.setToken(token);
			LoggerLoad.info("TOKEN GENERATED: " + token);
		}
	}

}
