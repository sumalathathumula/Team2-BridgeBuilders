package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import APIRequests.BatchRequest;
import context.ScenarioContext;
import endpoints.EndPoints;
import io.cucumber.java.en.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.Batch;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import utilities.RequestSpecFactory;
import utilities.ResponseValidator;

public class BatchSteps {

	private final ScenarioContext context = ScenarioContext.getInstance();
	private final BatchRequest batchRequest = new BatchRequest();
	private String endPoint;

	@Given("Admin sets Authorization to Bearer Token.")
	public void admin_sets_authorization_to_bearer_token() {

		batchRequest.prepareCreateBatchEndpoint();
	}

	@Given("Admin creates POST Request with valid data in request body for create batch")
	public void admin_creates_post_request_with_valid_data_in_request_body_for_create_batch() {
		batchRequest.prepareRequestWithAuth();
	}

	@When("Admin sends HTTPS Request with data from row {string} for create batch")
	public void admin_sends_https_request_with_data_from_row_for_create_batch(String scenarioName)
			throws InvalidFormatException, IOException {

		batchRequest.createBatchFromExcelRow(scenarioName);

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

			context.addBatch("USER", batchId, batchName);
			context.addBatch("DELETE_BY_BATCHID", batchId, batchName);
			LoggerLoad.info("batchId :" + batchId);
			LoggerLoad.info("batchName :" + batchName);
			System.out.println("batchId for user:" + context.getBatchId("USER"));
			System.out.println("batchName for user:" + context.getBatchName("USER"));
			System.out.println("batchId for delete:" + context.getBatchId("DELETE_BY_BATCHID"));
			System.out.println("batchName for delete:" + context.getBatchName("DELETE_BY_BATCHID"));

			JsonPath jsonPath = response.jsonPath();
			Map<String, String> expRow = context.getRowData();
			// Validate schema
			ResponseValidator.validateJsonSchema(response, "Batch_schema.json");

			// Validate Data type
			ResponseValidator.validateDataType(response, "batchId", Integer.class);
			ResponseValidator.validateDataType(response, "batchName", String.class);
			// ResponseValidator.validateDataType(response, "batchDescription",
			// String.class);
			ResponseValidator.validateDataType(response, "batchStatus", String.class);
			ResponseValidator.validateDataType(response, "programName", String.class);
			ResponseValidator.validateDataType(response, "batchNoOfClasses", Integer.class);
			ResponseValidator.validateDataType(response, "programId", Integer.class);

			// Validate Data
			ResponseValidator.validateData(jsonPath.getString("batchName"), expRow.get("BatchName"));
			ResponseValidator.validateData(jsonPath.getString("batchDescription"), expRow.get("BatchDescription"));
			ResponseValidator.validateData(jsonPath.getString("batchNoOfClasses"), expRow.get("NoOfClasses"));
			ResponseValidator.validateData(jsonPath.getString("batchStatus"), expRow.get("BatchStatus"));
			ResponseValidator.validateData(jsonPath.getString("programId"),
					String.valueOf(context.getProgramId("BATCH")));
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
		Response response = context.getRequest().when().post(endPoint);
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
			endPoint = EndPoints.GET_BATCHES.getEndpoint();
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

	@Then("Admin should receive {int} with {string}")
	public void admin_should_receive_with(Integer int1, String string) {
		Response response = context.getResponse();
		ResponseValidator.validateStatusCode(response.getStatusCode(), int1);
		System.out.println("Status Code: " + response.getStatusCode());
		System.out.println("Response Body: " + response.asString());
	}

	@Given("Admin creates  GET request with {string}")
	public void admin_creates_get_request_with(String scenario) {
		switch (scenario.toLowerCase()) {
		case "noauth":
			context.setRequest(given().spec(RequestSpecFactory.withoutAuth()));
			endPoint = EndPoints.GET_BATCH_BY_ID.getEndpoint();
			break;
		case "validbatchid":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCH_BY_ID.getEndpoint() + context.getBatchId("USER");
			break;
		case "retrievebatchafterdeleting":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCHES.getEndpoint() + context.getBatchId("USER");
			break;
		case "retrievebatchwithinvalidbatchid":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCH_BY_ID.getEndpoint() + "999999";
			break;
		case "batchinvalidendpoint":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.INVALID_BATCH_ENDPOINT.getEndpoint() + context.getBatchId("USER");
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
		switch (scenario.toLowerCase()) {
		case "noauth":
			context.setRequest(given().spec(RequestSpecFactory.withoutAuth()));
			endPoint = EndPoints.GET_BATCH_BY_NAME.getEndpoint();
			break;
		case "validbatchname":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCH_BY_NAME.getEndpoint() + context.getBatchName("User");
			break;
		case "invalidbatchname":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCH_BY_NAME.getEndpoint() + "InvalidBatchXYZ";
			break;
		case "retrievedeletedbatchbyname":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCHES.getEndpoint() + context.getBatchName("User");
			break;
		case "batchinvalidendpoint":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.INVALID_BATCH_ENDPOINT.getEndpoint() + context.getBatchName("User");
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

	@Given("Admin prepares GET request with ProgramID {string}")
	public void admin_prepares_get_request_with_program_id(String scenario) {
		switch (scenario.toLowerCase()) {
		case "noauth":
			context.setRequest(given().spec(RequestSpecFactory.withoutAuth()));
			endPoint = EndPoints.GET_BATCH_BY_PROGRAMID.getEndpoint();
			break;
		case "validprogramid":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCH_BY_PROGRAMID.getEndpoint() + context.getProgramId("User");
			break;
		case "invalidprogramid":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCH_BY_PROGRAMID.getEndpoint() + "InvalidBatchXYZ";
			break;
		case "retrievebatchafterprogramdeleted":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCHES.getEndpoint() + context.getProgramId("User");
			break;
		case "programinvalidendpoint":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.INVALID_BATCH_ENDPOINT.getEndpoint() + 2356890;
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
		switch (scenario.toLowerCase()) {
		case "validbatchid":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCHES.getEndpoint();
			break;
		case "invalidendpoint":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.INVALID_BATCH_ENDPOINT.getEndpoint() + context.getBatchId("USER");
			break;
		case "invalidbatchid":
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
			endPoint = EndPoints.GET_BATCHES.getEndpoint() + 922781930;
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

	@Given("Admin creates UPDATE Request with valid data in request body for update batch")
	public void admin_creates_update_request_with_valid_data_in_request_body_for_update_batch() {
		endPoint = EndPoints.CREATE_BATCH.getEndpoint();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
	}

	@When("Admin sends HTTPS Request with data from row {string} for update batch")
	public void admin_sends_https_request_with_data_from_row_for_update_batch(String scenarioName)
			throws InvalidFormatException, IOException {
		List<Map<String, String>> data = ExcelReader.getData("src/test/resources/Testdata.xlsx", "Batch");
		// context.addBatch("User",102,"APISquad7759");
		for (Map<String, String> row : data) {
			if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {
				Batch batch = new Batch();
				// Decide batchId based on scenario
				int batchId;
				switch (scenarioName) {
				case "UpdateBatchIdWithAllFields":
				case "UpdateBatchIdWithMandatoryFields":
				case "UpdateBatchWithInvalidData":
					batchId = context.getBatchId("User"); // existing batch
					break;
				case "UpdateBatchWithDeletedBatchId":
					batchId = row.get("BatchId") != null ? Integer.parseInt(row.get("BatchId")) : 99;
					break;
				case "UpdateBatchWithDeletedProgramID":
					batchId = context.getBatchId("User");
					break;
				case "UpdateBatchWithInvalidBatchId":
					batchId = 37428; // invalid ID
					break;
				default:
					throw new IllegalArgumentException("Unknown scenario: " + scenarioName);
				}
				batch.setbatchName(row.get("BatchName"));
				batch.setbatchDescription(row.get("BatchDescription"));
				batch.setbatchNoOfClasses(
						row.get("NoOfClasses") != null ? Integer.parseInt(row.get("NoOfClasses")) : 0);
				batch.setbatchStatus(row.get("BatchStatus"));
				batch.setprogramId(row.get("ProgramId") != null ? Integer.parseInt(row.get("ProgramId")) : 0);
				Response response = context.getRequest().body(batch).when()
						.put(EndPoints.CREATE_BATCH.getEndpoint() + "/" + batchId);
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

	@Then("the response status should be equal to ExpectedStatus for update batch")
	public void the_response_status_should_be_equal_to_expected_status_for_update_batch() {
		Response response = context.getResponse();
		Map<String, String> row = context.getRowData();
		int expectedStatus = Integer.parseInt(row.get("ExpectedStatusCode"));
		int actualStatusCode = response.getStatusCode();
		ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
		// ResponseValidator.validateContentType(response.getContentType(),
		// row.get("ContentType"));
		System.out.println("Status for Batch:" + response.getStatusCode());
		System.out.println("Status for Batch:" + response.getStatusLine());
		System.out.println("Batch body: " + response.asString());
	}

	@Given("Admin sets update request to {string}")
	public void admin_sets_update_request_to(String authType) {
		if ("NoAuth".equalsIgnoreCase(authType)) {
			context.setRequest(given().spec(RequestSpecFactory.withoutAuth()));
		} else if ("BearerToken".equalsIgnoreCase(authType)) {
			context.setRequest(given().spec(RequestSpecFactory.withAuth(context.getToken())));
		} else {
			throw new IllegalArgumentException("Unknown Authorization type: " + authType);
		}
	}

	@When("Admin sends update request to {string}")
	public void admin_sends_update_request_to(String endpointType) {
		if ("valid".equalsIgnoreCase(endpointType)) {
			endPoint = EndPoints.GET_BATCHES.getEndpoint(); // valid endpoint
		} else if ("invalid".equalsIgnoreCase(endpointType)) {
			endPoint = EndPoints.INVALID_BATCH_ENDPOINT.getEndpoint(); // invalid endpoint
		} else {
			throw new IllegalArgumentException("Unknown endpoint type: " + endpointType);
		}
		Response response = context.getRequest().when().put(endPoint);
		context.setResponse(response);
		LoggerLoad.info("POST request to " + endPoint + " returned Status Code: " + response.getStatusCode());
	}

}
