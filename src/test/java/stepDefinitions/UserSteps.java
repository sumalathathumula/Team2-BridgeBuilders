package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import APIRequests.UserRequest;
import context.ScenarioContext;
import endpoints.EndPoints;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import models.User;
import models.UserLogin;
import models.UserRoleMap;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import utilities.RequestSpecFactory;
import utilities.ResponseValidator;

public class UserSteps {

	private final ScenarioContext context = ScenarioContext.getInstance();
	private final UserRequest userRequest = new UserRequest();
	private String endPoint;

	@Given("Admin sets Authorization to Bearer Token to create user.")
	public void admin_sets_authorization_to_bearer_token_to_create_user() {

		endPoint = EndPoints.CREATE_USER.getEndpoint();

	}

	@Given("Admin creates POST Request  with valid data in request body for create user")
	public void admin_creates_post_request_with_valid_data_in_request_body_for_create_user() {
		//endPoint = EndPoints.CREATE_USER.getEndpoint();
		userRequest.prepareCreateUserRequest();
		String token= context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
	}

	@When("Admin sends HTTPS Request with data from row {string} for create user")
	public void admin_sends_https_request_with_data_from_row_for_create_user(String scenarioName) throws Exception {

		userRequest.createUserFromExcelRow(scenarioName);

	}

	@Then("the response status should be equal to ExpectedStatus for create user")
	public void the_response_status_should_be_equal_to_expected_status_for_create_user() {
		Response response = context.getResponse();
		Map<String, String> row = context.getRowData();

		int expectedStatus = Integer.parseInt(row.get("ExpectedStatusCode"));
		int actStatusCode= response.getStatusCode();

		ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
		//ResponseValidator.validateContentType(response.getContentType(), row.get("ContentType"));
		System.out.println("Status for User:"+response.getStatusCode());
		System.out.println("Status for User:"+response.getStatusLine());
		System.out.println("body: "+response.asString());

		if (expectedStatus == 201 && actStatusCode == 201) {
			String userID = response.jsonPath().getString("user.userId");
			context.setUserId(userID); // Setting userid for chaining
			String roleID = response.jsonPath().getString("roles[0].roleId");
			context.setRoleId(roleID);
		}
		System.out.println("Status for UserID:"+context.getUserId());
		System.out.println("Status for UserID:"+context.getRoleId());


	}

	@Given("Admin sets Authorization to No Auth, creates POST Request with valid data in request body for create user")
	public void admin_sets_authorization_to_no_auth_creates_post_request_with_valid_data_in_request_body_for_create_user() {
		//endPoint = EndPoints.CREATE_USER.getEndpoint();
		userRequest.prepareCreateUserRequest();
		context.setRequest(given().spec(RequestSpecFactory.withoutAuth()));

	}

	@Given("Admin creates POST Request  with valid data in request body with invalid endpoint for create user")
	public void admin_creates_post_request_with_valid_data_in_request_body_with_invalid_endpoint_for_create_user() {
		//endPoint = EndPoints.CREATE_USER.getEndpoint()+"1";
		userRequest.prepareCreateUserRequestForInvalidEndpoint();
		String token= context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));

	}
	//*************GET ALL USERS*************//

	@Given("Admin creates GET Request for the LMS API endpoint")
	public void admin_creates_get_request_for_the_lms_api_endpoint() {
		endPoint = EndPoints.GET_ALL_USERS.getEndpoint();
		String token= context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));

	}

	@When("Admin sends HTTPS Request with endpoint for get all users")
	public void admin_sends_https_request_with_endpoint_for_get_all_users() {

		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
	}

	@Then("Admin receives {int} OK Status with response body for get all users.")
	public void admin_receives_ok_status_with_response_body_for_get_all_users(Integer expStatusCode) {
		int actStatusCode = context.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	// sending get all users request with invalid end point
	@Given("Admin creates GET Request with invalid endpoint for all users")
	public void admin_creates_get_request_with_invalid_endpoint_for_all_users() {
		endPoint = EndPoints.GET_ALL_USERS.getEndpoint()+"1";
		String token= context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
	}

	@When("Admin sends HTTPS Request with  invalid endpoint for all users")
	public void admin_sends_https_request_with_invalid_endpoint_for_all_users() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
	}

	@Then("Admin receives {int} status with error message Not Found for get all users")
	public void admin_receives_status_with_error_message_not_found_for_get_all_users(Integer expStatusCode) {
		int actStatusCode = context.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	//----get user by user id ---

	@Given("Admin creates GET Request for the LMS API endpoint with valid admin ID")
	public void admin_creates_get_request_for_the_lms_api_endpoint_with_valid_admin_id() {
		endPoint = EndPoints.GET_USER_INFORMATION_BY_USERID.getEndpoint();
		String token= context.getToken();
		//context.setRequest(given().pathParam("userId", context.getUserId()).spec(RequestSpecFactory.withAuth(token)));
		context.setRequest(
			    given()
			        .filter((req, res, ctx) -> {
			            System.out.println("Final Request URI: " + req.getURI());
			            return ctx.next(req, res);
			        })
			        .pathParam("userId", context.getUserId())
			        .spec(RequestSpecFactory.withAuth(token))
			);
	}

	@When("Admin sends HTTPS Request with endpoint to getuserbyuserid  details")
	public void admin_sends_https_request_with_endpoint_to_getuserbyuserid_details() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);

	}

	@Then("Admin receives {int} OK Status with response body for getuserbyuserid.")
	public void admin_receives_ok_status_with_response_body_for_getuserbyuserid(Integer expStatusCode) {
		int actStatusCode = context.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	//...get users by role id
	@Given("Admin creates GET Request for the LMS API endpoint with valid role ID")
	public void admin_creates_get_request_for_the_lms_api_endpoint_with_valid_role_id() {
		endPoint = EndPoints.GETS_USERS_BY_ROLEID.getEndpoint();
		String token= context.getToken();
		context.setRequest(
			    given()
			        .filter((req, res, ctx) -> {
			            System.out.println("Final Request URI: " + req.getURI());
			            return ctx.next(req, res);
			        })
			        .pathParam("roleId", context.getRoleId())
			        .spec(RequestSpecFactory.withAuth(token))
			);
	}

	@When("Admin sends HTTPS Request with endpoint to getusersbyroleid  details")
	public void admin_sends_https_request_with_endpoint_to_getusersbyroleid_details() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
	}

	@Then("Admin receives {int} OK Status with response body for getusersbyroleid.")
	public void admin_receives_ok_status_with_response_body_for_getusersbyroleid(Integer expStatusCode) {
		int actStatusCode = context.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}


}
