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
		//String token= context.getToken();
		//endPoint = EndPoints.CREATE_BATCH.getEndpoint();
		batchRequest.prepareCreateBatchEndpoint();
	}

	@Given("Admin creates POST Request with valid data in request body for create batch")
	public void admin_creates_post_request_with_valid_data_in_request_body_for_create_batch() {
//		String token = context.getToken();
//		endPoint = EndPoints.CREATE_BATCH.getEndpoint();		
//		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
		 batchRequest.prepareRequestWithAuth();
	}

	@When("Admin sends HTTPS Request with data from row {string} for create batch")
	public void admin_sends_https_request_with_data_from_row_for_create_batch(String scenarioName) throws InvalidFormatException, IOException {		
				
		batchRequest.createBatchFromExcelRow(scenarioName);
	   
	}

	@Then("the response status should be equal to ExpectedStatus for create batch")
	public void the_response_status_should_be_equal_to_expected_status_for_create_batch() {
		Response response = context.getResponse();
		Map<String, String> row = context.getRowData();

		int expectedStatus = Integer.parseInt(row.get("ExpectedStatusCode"));
		int actStatusCode= response.getStatusCode();

		ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
		//ResponseValidator.validateContentType(response.getContentType(), row.get("ContentType"));
		System.out.println("Status for Batch:"+response.getStatusCode());
		System.out.println("Status for Batch:"+response.getStatusLine());
		System.out.println("Batch body: "+response.asString());
		
		if (expectedStatus == 201 && actStatusCode == 201) {
			int batchId = Integer.parseInt(response.jsonPath().getString("batchId"));
			String batchName = response.jsonPath().getString("batchName");

			context.addBatchId(batchId);
			context.addBatchName(batchName);
			LoggerLoad.info("batchId :" + batchId);
			LoggerLoad.info("batchName :" + batchName);
			System.out.println("batchId for user:" +context.getBatchId(0));
			System.out.println("batchName for user:" +context.getBatchName(0));
			System.out.println("batchId for delete:" +context.getBatchId(1));
			System.out.println("batchName for delete:" +context.getBatchName(1));
			
		
		}  
	}

}
