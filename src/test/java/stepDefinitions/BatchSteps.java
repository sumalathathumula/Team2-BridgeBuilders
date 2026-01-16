package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import context.ScenarioContext;
import endpoints.EndPoints;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import models.Batch;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import utilities.RequestSpecFactory;
import utilities.ResponseValidator;

public class BatchSteps {

	private final ScenarioContext context = ScenarioContext.getInstance();
	private String endPoint;

	@Given("Admin sets Authorization to Bearer Token.")
	public void admin_sets_authorization_to_bearer_token() {
		// String token= context.getToken();
		endPoint = EndPoints.GET_BATCHES.getEndpoint();
	}

	@Given("Admin creates POST Request with valid data in request body for create batch")
	public void admin_creates_post_request_with_valid_data_in_request_body_for_create_batch() {
		String token = context.getToken();
		endPoint = EndPoints.GET_BATCHES.getEndpoint();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
	}

	@When("Admin sends HTTPS Request with data from row {string} for create batch")
	public void admin_sends_https_request_with_data_from_row_for_create_batch(String scenarioName)
			throws InvalidFormatException, IOException {

		// try {

		List<Map<String, String>> batchData = ExcelReader.getData(ConfigReader.getProperty("excelPath"), "Batch");
		for (Map<String, String> row : batchData) {
			if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {

				Batch batch = new Batch();
				batch.setbatchDescription(row.get("BatchDescription"));
				batch.setbatchName(row.get("BatchName"));
				// batch.setbatchName(generateRandomString());
				batch.setbatchNoOfClasses(Integer.parseInt(row.get("NoOfClasses")));
				batch.setbatchStatus(row.get("BatchStatus"));
				batch.setprogramId(Integer.parseInt(row.get("ProgramId")));
				Response response = context.getRequest().body(batch).when().post(endPoint);
				context.setResponse(response);
				context.setRowData(row);
				LoggerLoad.info("Status Code: " + response.getStatusCode());
				if (response.getStatusCode() != 401 && response.getStatusCode() != 404) {
					LoggerLoad.info("Status Message: " + response.jsonPath().getString("message"));
				}

				break;
			}
		}

	}

	@Then("the response status should be equal to ExpectedStatus for create batch")
	public void the_response_status_should_be_equal_to_expected_status_for_create_batch() {
		Response response = context.getResponse();
		Map<String, String> row = context.getRowData();

		int expectedStatus = Integer.parseInt(row.get("ExpectedStatusCode"));
		int actStatusCode = response.getStatusCode();

		ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
		// ResponseValidator.validateContentType(response.getContentType(),
		// row.get("ContentType"));
		System.out.println("Status for Batch:" + response.getStatusCode());
		System.out.println("Status for Batch:" + response.getStatusLine());
		System.out.println("Batch body: " + response.asString());

		if (expectedStatus == 201 && actStatusCode == 201) {
			int batchId = Integer.parseInt(response.jsonPath().getString("batchId"));
			String batchName = response.jsonPath().getString("batchName");

			context.addBatchId(batchId);
			context.addBatchName(batchName);
			LoggerLoad.info("batchId :" + batchId);
			LoggerLoad.info("batchName :" + batchName);

		}
	}
	
	@Given("Admin sets Authorization to {string}")
	public void admin_sets_authorization_to(String authType) {
		if ("NoAuth".equalsIgnoreCase(authType)) {
	        context.setRequest(given().spec(RequestSpecFactory.withoutAuth()));
	    } else if ("BearerToken".equalsIgnoreCase(authType)) {
	        context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
	    } else {
	        throw new IllegalArgumentException("Unknown Authorization type: " + authType);
	    }
	}
	
	@When("Admin sends HTTPS POST request to {string}")
	public void admin_sends_https_post_request_to(String endpointType) {
		if ("valid".equalsIgnoreCase(endpointType)) {
	        endPoint = EndPoints.GET_BATCHES.getEndpoint(); // valid endpoint
	    } else if ("invalid".equalsIgnoreCase(endpointType)) {
	        endPoint = EndPoints.INVALID_BATCH_ENDPOINT.getEndpoint(); // invalid endpoint
	    } else {
	        throw new IllegalArgumentException("Unknown endpoint type: " + endpointType);
	    }
		Response response = context.getRequest()
                .when()
                .post(endPoint);

context.setResponse(response);
LoggerLoad.info("POST request to " + endPoint + " returned Status Code: " + response.getStatusCode());
	}
	@Then("Admin should receive {string} response")
	public void admin_should_receive_response(String expectedStatus) {
		int actualStatus = context.getResponse().getStatusCode();
	    int expected = Integer.parseInt(expectedStatus);

	    if (actualStatus != expected) {
	        throw new AssertionError("Expected Status Code: " + expected + ", but got: " + actualStatus);
	    }
	}

	@Given("Admin prepares GET request with {string}")
	public void admin_prepares_get_request_with(String scenario) {
		switch (scenario.toLowerCase()) {

		case "valid":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			break;
		case "searchvalid":
			context.setRequest(
					given().spec(RequestSpecFactory.withAuth(context.getToken())).queryParam("search", "DA"));
			endPoint = EndPoints.GET_BATCHES.getEndpoint();
			break;
		case "searchinvalid":
			context.setRequest(
					given().spec(RequestSpecFactory.withAuth(context.getToken())).queryParam("search", "+++"));
			endPoint = EndPoints.GET_BATCHES.getEndpoint();
			break;
		case "noauth":
			context.setRequest(given().spec(RequestSpecFactory.withoutAuth()));
			endPoint = EndPoints.GET_BATCHES.getEndpoint();
			break;

		case "validwithinvalidendpoint":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.INVALID_BATCH_ENDPOINT.getEndpoint(); // intentionally wrong endpoint
			break;

		default:
			throw new IllegalArgumentException("Invalid auth type: " + scenario);
		}
	}

	@When("Admin sends GET request to batches endpoint")
	public void admin_sends_get_request_to_batches_endpoint() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
		LoggerLoad.info("GET ALL BATCHES Status Code: " + response.getStatusCode());
	}

	@Given("Admin creates  GET request {string}")
	public void admin_creates_get_request_with(String scenario) {
		context.addBatchId(102);
		context.addBatchId(104);
		switch (scenario.toLowerCase()) {
		case "noauth":
			context.setRequest(given().spec(RequestSpecFactory.withoutAuth()));
			endPoint = EndPoints.GET_BATCH_BY_ID.getEndpoint();
			break;

		case "validbatchid":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCH_BY_ID.getEndpoint() + context.getBatchId(0);
			break;

		case "retrievebatchafterdeleting":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCHES.getEndpoint() + context.getBatchId(1);
			break;
		case "retrievebatchwithinvalidbatchid":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCH_BY_ID.getEndpoint() + "999999";
			break;

		case "batchinvalidendpoint":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.INVALID_BATCH_ENDPOINT.getEndpoint() + context.getBatchId(0);
			break;

		default:
			throw new IllegalArgumentException("Invalid scenario: " + scenario);
		}
	}

	@When("Admin sends GET request to batches endpoint with batchid")
	public void admin_sends_get_request_to_batches_endpoint_with_batchid() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);

	}

	@Then("Admin receive {int} with {string}")
	public void admin_receive_with(Integer int1, String string) {
		Response response = context.getResponse();
		ResponseValidator.validateStatusCode(response.getStatusCode(), int1);
		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.asString());
	}

	@Given("Admin creates GET request with batchname and {string}")
	public void admin_creates_get_request_with_batchname_and(String scenario) {
		context.addBatchName("APISquad7759");
		context.addBatchName("APISquad101");
		switch (scenario.toLowerCase()) {

		case "noauth":
			context.setRequest(given().spec(RequestSpecFactory.withoutAuth()));
			endPoint = EndPoints.GET_BATCH_BY_NAME.getEndpoint();
			break;

		case "validbatchname":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCH_BY_NAME.getEndpoint() + context.getBatchName(0);
			break;

		case "invalidbatchname":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCH_BY_NAME.getEndpoint() + "InvalidBatchXYZ";
			break;
		case "retrievedeletedbatchbyname":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCHES.getEndpoint() + context.getBatchName(1);
			break;
		case "batchinvalidendpoint":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.INVALID_BATCH_ENDPOINT.getEndpoint()+context.getBatchName(0) ;
			break;
			
		default:
			throw new IllegalArgumentException("Unsupported scenario type: " + scenario);
		}
	}

	@When("Admin sends GET request to batches endpoint with batchname")
	public void admin_sends_get_request_to_batches_endpoint_with_batchname() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
	}

	@Then("Admin should receive {int} with {string}")
	public void admin_should_receive_with(Integer int1, String string) {
		Response response = context.getResponse();
		ResponseValidator.validateStatusCode(response.getStatusCode(), int1);
		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.asString());
		
	}
	@Given("Admin prepares GET request with ProgramID {string}")
	public void admin_prepares_get_request_with_program_id(String scenario) {
		context.addProgramId(39);
		context.addProgramId(4);
		switch (scenario.toLowerCase()) {

		case "noauth":
			context.setRequest(given().spec(RequestSpecFactory.withoutAuth()));
			endPoint = EndPoints.GET_BATCH_BY_PROGRAMID.getEndpoint();
			break;

		case "validprogramid":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCH_BY_PROGRAMID.getEndpoint() + context.getProgramId(0);
			break;

		case "invalidprogramid":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCH_BY_PROGRAMID.getEndpoint() + "InvalidBatchXYZ";
			break;
		case "retrievebatchafterprogramdeleted":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCHES.getEndpoint() + context.getProgramId(1);
			break;
		case "programinvalidendpoint":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.INVALID_BATCH_ENDPOINT.getEndpoint()+context.getProgramId(0) ;
			break;
			
		default:
			throw new IllegalArgumentException("Unsupported scenario type: " + scenario);
		}
	}
	@When("Admin sends GET request to batches endpoint with Program ID")
	public void admin_sends_get_request_to_batches_endpoint_with_program_id() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
	    
	}
	
	@Given("Admin sets base URI and prepares DELETE request for {string}")
	public void admin_sets_base_uri_and_prepares_delete_request_for(String scenario) {
		context.addBatchId(274);
		
		switch (scenario.toLowerCase()) {

		case "validbatchid":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCHES.getEndpoint();
			break;

		case "invalidendpoint":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.INVALID_BATCH_ENDPOINT.getEndpoint()+context.getBatchId(0);
			break;

		case "invalidbatchid":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCHES.getEndpoint()+ 922781930;
			break;
			
		case "withoutauthorization":
			context.setRequest(given().spec(RequestSpecFactory.withoutAuth()));
			endPoint = EndPoints.GET_BATCHES.getEndpoint();
			break;
			
		default:
			throw new IllegalArgumentException("Unsupported scenario type: " + scenario); 
		}
	}
	@When("Admin sends DELETE request")
	public void admin_sends_delete_request() {
		Response response = context.getRequest().when().delete(endPoint);
		context.setResponse(response);
	}
	@Then("the response status should be {string}")
	public void the_response_status_should_be(String string) {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
	}
}