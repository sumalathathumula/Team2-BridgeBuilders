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
	//UPDATE_USERROLE_PROGRAM_BATCH_STATUS
	@Given("Admin creates PUT Request with valid LMS endpoint to assign admin")
	public void admin_creates_put_request_with_valid_lms_endpoint_to_assign_admin() {
		userRequest.prepareAssignAdminRequest();

		String token= context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
	    
	}

	@When("Admin sends HTTPS Request with data from row {string} for assign admin")
	public void admin_sends_https_request_with_data_from_row_for_assign_admin(String scenario) throws Exception {
		userRequest.assignAdminFromExcelRow(scenario);
	}

	@Then("the response status should be equal to ExpectedStatus for assign admin")
	public void the_response_status_should_be_equal_to_expected_status_for_assign_admin() {
		Response response = context.getResponse();
		Map<String, String> row = context.getRowData();

		int expectedStatus = Integer.parseInt(row.get("ExpectedStatusCode"));
		int actStatusCode= response.getStatusCode();

		ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
		//ResponseValidator.validateContentType(response.getContentType(), row.get("ContentType"));
		System.out.println("Status for User:"+response.getStatusCode());
		System.out.println("Status for User:"+response.getStatusLine());
		System.out.println("body: "+response.asString());		
		
	}
	
	//UPDATE_USER_ROLEID
	@Given("Admin creates PUT Request with valid LMS endpoint to update admin role status")
	public void admin_creates_put_request_with_valid_lms_endpoint_to_update_admin_role_status() {
		userRequest.prepareUpdateAdminRoleStatusRequest();
	}

	@When("Admin sends HTTPS Request with data from row {string} for update admin role status")
	public void admin_sends_https_request_with_data_from_row_for_update_admin_role_status(String scenario) throws Exception {
		userRequest.updateAdminRoleStatusFromExcelRow(scenario);
	}

	@Then("the response status should be equal to ExpectedStatus for update admin role status")
	public void the_response_status_should_be_equal_to_expected_status_for_update_admin_role_status() {
		Response response = context.getResponse();
		Map<String, String> row = context.getRowData();

		int expectedStatus = Integer.parseInt(row.get("ExpectedStatusCode"));
		int actStatusCode= response.getStatusCode();

		ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
		//ResponseValidator.validateContentType(response.getContentType(), row.get("ContentType"));
		System.out.println("Status for User:"+response.getStatusCode());
		System.out.println("Status for User:"+response.getStatusLine());
		System.out.println("body: "+response.asString());
	}
	
	//get all active users
	@Given("Admin creates GET Request for API endpoint with valid admin ID")
	public void admin_creates_get_request_for_api_endpoint_with_valid_admin_id() {
		endPoint = EndPoints.GET_ALL_ACTIVE_USERS.getEndpoint();
		String token= context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
	}

	@When("Admin sends HTTPS Request with endpoint to get all active users")
	public void admin_sends_https_request_with_endpoint_to_get_all_active_users() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
	}

	@Then("Admin receives {int} OK Status with response for all active users.")
	public void admin_receives_ok_status_with_response_for_all_active_users(Integer expStatusCode) {
		int actStatusCode = context.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	
	//get all active users-negative	
	@Given("Admin creates GET Request with invalid endpoint for all active users")
	public void admin_creates_get_request_with_invalid_endpoint_for_all_active_users() {
		endPoint = EndPoints.GET_ALL_ACTIVE_USERS.getEndpoint()+"ghv";
		String token= context.getToken();
		context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
	}
	
	@When("Admin sends HTTPS Request with  invalid endpoint for all active users")
	public void admin_sends_https_request_with_invalid_endpoint_for_all_active_users() {
		Response response = context.getRequest().when().get(endPoint);
		context.setResponse(response);
	}
	
	@Then("Admin receives {int} status with message Not Found for get all active users")
	public void admin_receives_status_with_message_not_found_for_get_all_active_users(Integer expStatusCode) {
		int actStatusCode = context.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	//@Getemailsofalluserswithactivestatus
		@Given("Admin creates GET Request for the LMS API with valid admin ID")
		public void admin_creates_get_request_for_the_lms_api_with_valid_admin_id() {
			endPoint = EndPoints.GET_EMAILS_OF_ALL_USERS_WITH_ACTIVE_STATUS.getEndpoint();
			String token= context.getToken();
			context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
		}
		@When("Admin sends HTTPS Request with endpoint to get email of all active users")
		public void admin_sends_https_request_with_endpoint_to_get_email_of_all_active_users() {
			Response response = context.getRequest().when().get(endPoint);
			context.setResponse(response);
		}
		@Then("Admin receives {int} OK Status with response body for all active users.")
		public void admin_receives_ok_status_with_response_body_for_all_active_users(Integer expStatusCode) {
			int actStatusCode = context.getResponse().getStatusCode();
			LoggerLoad.info("actStatusCode : "+actStatusCode);
			ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		}


		//@Getemailsofalluserswithactivestatus-negative
		@Given("Admin creates GET Request with invalid endpoint to fetch email of all active users")
		public void admin_creates_get_request_with_invalid_endpoint_to_fetch_email_of_all_active_users() {
			endPoint = EndPoints.GET_EMAILS_OF_ALL_USERS_WITH_ACTIVE_STATUS.getEndpoint()+"hvbj";
			String token= context.getToken();
			context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
		}
		@When("Admin sends HTTPS Request to fetch email with  invalid endpoint for all active users")
		public void admin_sends_https_request_to_fetch_email_with_invalid_endpoint_for_all_active_users() {
			Response response = context.getRequest().when().get(endPoint);
			context.setResponse(response);
		}
		@Then("Admin receives {int} status with error message Not Found for get all active users")
		public void admin_receives_status_with_error_message_not_found_for_get_all_active_users(Integer expStatusCode) {
			int actStatusCode = context.getResponse().getStatusCode();
			LoggerLoad.info("actStatusCode : "+actStatusCode);
			ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		}
		//get all roles
		@Given("Admin creates GET Request to get all roles API endpoint with valid admin ID")
		public void admin_creates_get_request_to_get_all_roles_api_endpoint_with_valid_admin_id() {
			endPoint = EndPoints.GET_ALL_ROLES.getEndpoint();
			String token= context.getToken();
			context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
		}
		@When("Admin sends HTTPS Request with endpoint to get all roles")
		public void admin_sends_https_request_with_endpoint_to_get_all_roles() {
			Response response = context.getRequest().when().get(endPoint);
			context.setResponse(response);
		}
		@Then("Admin receives {int} OK Status with response body for all roles.")
		public void admin_receives_ok_status_with_response_body_for_all_roles(Integer expStatusCode) {
			int actStatusCode = context.getResponse().getStatusCode();
			LoggerLoad.info("actStatusCode : "+actStatusCode);
			ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		}


	  //get all roles negative
		@Given("Admin creates GET Request with invalid endpoint")
		public void admin_creates_get_request_with_invalid_endpoint() {
			endPoint = EndPoints.GET_ALL_ROLES.getEndpoint()+"hgv";
			String token= context.getToken();
			context.setRequest(given().spec(RequestSpecFactory.withAuth(token)));
		}
		@When("Admin sends HTTPS Request with  invalid endpoint for all roles")
		public void admin_sends_https_request_with_invalid_endpoint_for_all_roles() {
			Response response = context.getRequest().when().get(endPoint);
			context.setResponse(response);
		}
		@Then("Admin receives {int} status with error message Not Found for get all user roles")
		public void admin_receives_status_with_error_message_not_found_for_get_all_user_roles(Integer expStatusCode) {
			int actStatusCode = context.getResponse().getStatusCode();
			LoggerLoad.info("actStatusCode : "+actStatusCode);
			ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		}
		
		//UPDATE USER
		@Given("Admin creates PUT Request with valid LMS endpoint to update admin")
		public void admin_creates_put_request_with_valid_lms_endpoint_to_update_admin() {
		userRequest.prepareUpdateUserRequest();
		}

		@When("Admin sends HTTPS Request with data from row {string} for update admin")
		public void admin_sends_https_request_with_data_from_row_for_update_admin(String scenario) throws Exception {
			 userRequest.updateAdminFromExcelRow(scenario);
		}

		@Then("the response status should be equal to ExpectedStatus for update admin")
		public void the_response_status_should_be_equal_to_expected_status_for_update_admin() {
			Response response = context.getResponse();
			System.out.println("body: "+response.asString());	
			
			Map<String, String> row = context.getRowData();

			int expectedStatus = Integer.parseInt(row.get("ExpectedStatusCode"));
			int actStatusCode= response.getStatusCode();

			ResponseValidator.validateStatusCode(response.getStatusCode(), expectedStatus);
			//ResponseValidator.validateContentType(response.getContentType(), row.get("ContentType"));
			System.out.println("Status for User:"+response.getStatusCode());
			System.out.println("Status for User:"+response.getStatusLine());
				
			
		}
		
		//@getuserbyprogramId
		@Given("Admin creates GET Request for the LMS API endpoint for getuserbyprogramId")
		public void admin_creates_get_request_for_the_lms_api_endpoint_for_getuserbyprogram_id() {
			endPoint = EndPoints.GETS_USERS_FOR_PROGRAM.getEndpoint();
			String token= context.getToken();
			//context.setRequest(given().pathParam("userId", context.getUserId()).spec(RequestSpecFactory.withAuth(token)));
			context.setRequest(
				    given()
				        .filter((req, res, ctx) -> {
				            System.out.println("Final Request URI: " + req.getURI());
				            return ctx.next(req, res);
				        })
				        .pathParam("programId", context.getProgramId("BATCH"))
				        .spec(RequestSpecFactory.withAuth(token))
				);
		}

		@When("Admin sends HTTPS Request with endpoint to getuserbyprogramId  details")
		public void admin_sends_https_request_with_endpoint_to_getuserbyprogram_id_details() {
			Response response = context.getRequest().when().get(endPoint);
			context.setResponse(response);
		}

		@Then("Admin receives {int} OK Status with response body for getuserbyprogramId.")
		public void admin_receives_ok_status_with_response_body_for_getuserbyprogram_id(Integer expStatusCode) {
			int actStatusCode = context.getResponse().getStatusCode();
			LoggerLoad.info("actStatusCode : "+actStatusCode);
			ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		}
		
		//@getuserbyInvalidprogramId
		@Given("Admin creates GET Request for the LMS API endpoint for getuserbyprogramId with invalid program id")
		public void admin_creates_get_request_for_the_lms_api_endpoint_for_getuserbyprogram_id_with_invalid_program_id() {
			endPoint = EndPoints.GETS_USERS_FOR_PROGRAM.getEndpoint();
			String token= context.getToken();
			//context.setRequest(given().pathParam("userId", context.getUserId()).spec(RequestSpecFactory.withAuth(token)));
			context.setRequest(
				    given()
				        .filter((req, res, ctx) -> {
				            System.out.println("Final Request URI: " + req.getURI());
				            return ctx.next(req, res);
				        })
				        .pathParam("programId", context.getProgramId("BATCH")+"1")
				        .spec(RequestSpecFactory.withAuth(token))
				);
		}

		@When("Admin sends HTTPS Request with endpoint to getuserbyprogramId  details with invalid program id")
		public void admin_sends_https_request_with_endpoint_to_getuserbyprogram_id_details_with_invalid_program_id() {
			Response response = context.getRequest().when().get(endPoint);
			context.setResponse(response);
		}

		@Then("Admin receives {int} status with error message Not Found for get all user programs.")
		public void admin_receives_status_with_error_message_not_found_for_get_all_user_programs(Integer expStatusCode) {
			int actStatusCode = context.getResponse().getStatusCode();
			LoggerLoad.info("actStatusCode : "+actStatusCode);
			ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		}
		
		//getuserbyProgrambatchId
		@Given("Admin creates GET Request for the LMS API endpoint for getuserbyProgrambatchId")
		public void admin_creates_get_request_for_the_lms_api_endpoint_for_getuserby_programbatch_id() {
			endPoint = EndPoints.GETS_USERS_FOR_PROGRAM_BATCHES.getEndpoint();
			String token= context.getToken();
			//context.setRequest(given().pathParam("userId", context.getUserId()).spec(RequestSpecFactory.withAuth(token)));
			context.setRequest(
				    given()
				        .filter((req, res, ctx) -> {
				            System.out.println("Final Request URI: " + req.getURI());
				            return ctx.next(req, res);
				        })
				        .pathParam("batchId", context.getBatchId("USER"))
				        .spec(RequestSpecFactory.withAuth(token))
				);
		}

		@When("Admin sends HTTPS Request with endpoint to getuserbyProgrambatchId  details")
		public void admin_sends_https_request_with_endpoint_to_getuserby_programbatch_id_details() {
			Response response = context.getRequest().when().get(endPoint);
			context.setResponse(response);
		}

		@Then("Admin receives {int} OK Status with response body for getuserbyProgrambatchId.")
		public void admin_receives_ok_status_with_response_body_for_getuserby_programbatch_id(Integer expStatusCode) {
			int actStatusCode = context.getResponse().getStatusCode();
			LoggerLoad.info("actStatusCode : "+actStatusCode);
			ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		}
		
		//getuserbyProgramInvalidbatchId
		@Given("Admin creates GET Request for the LMS API endpoint for getuserbyprogramId with invalid program batch id")
		public void admin_creates_get_request_for_the_lms_api_endpoint_for_getuserbyprogram_id_with_invalid_program_batch_id() {
			endPoint = EndPoints.GETS_USERS_FOR_PROGRAM_BATCHES.getEndpoint();
			String token= context.getToken();
			//context.setRequest(given().pathParam("userId", context.getUserId()).spec(RequestSpecFactory.withAuth(token)));
			context.setRequest(
				    given()
				        .filter((req, res, ctx) -> {
				            System.out.println("Final Request URI: " + req.getURI());
				            return ctx.next(req, res);
				        })
				        .pathParam("batchId", context.getBatchId("USER")+"1")
				        .spec(RequestSpecFactory.withAuth(token))
				);
		}

		@When("Admin sends HTTPS Request with endpoint to getuserbyprogramId  details with invalid program batchid")
		public void admin_sends_https_request_with_endpoint_to_getuserbyprogram_id_details_with_invalid_program_batchid() {
			Response response = context.getRequest().when().get(endPoint);
			context.setResponse(response);
		}

		@Then("Admin receives {int} status with error message Not Found for get all user program batches")
		public void admin_receives_status_with_error_message_not_found_for_get_all_user_program_batches(Integer expStatusCode) {
			int actStatusCode = context.getResponse().getStatusCode();
			LoggerLoad.info("actStatusCode : "+actStatusCode);
			ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		}
		
		//DELETE_USER
		
		@Given("Admin creates a DELETE request with a valid adminId.")
		public void admin_creates_a_delete_request_with_a_valid_admin_id() {
			endPoint = EndPoints.DELETE_USER.getEndpoint();
			String token= context.getToken();
			//context.setRequest(given().pathParam("userId", context.getUserId()).spec(RequestSpecFactory.withAuth(token)));
			context.setRequest(
				    given()
				        .filter((req, res, ctx) -> {
				            System.out.println("Final Request URI: " + req.getURI());
				            return ctx.next(req, res);
				        })
				        .pathParam("userID", context.getUserId())
				        
				        .spec(RequestSpecFactory.withAuth(token))
				        
				);
		}

		@When("Admin sends  HTTPS request to the endpoint for deleting admin by admin Id")
		public void admin_sends_https_request_to_the_endpoint_for_deleting_admin_by_admin_id() {
			Response response = context.getRequest().when().delete(endPoint);
			context.setResponse(response);
		}

		@Then("Admin receives a {int} OK status with a message confirming the deletion of the admin by adminId.")
		public void admin_receives_a_ok_status_with_a_message_confirming_the_deletion_of_the_admin_by_admin_id(Integer expStatusCode) {
			int actStatusCode = context.getResponse().getStatusCode();
			LoggerLoad.info("actStatusCode : "+actStatusCode);
			ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		}
		
		//invalid admin id
		@Given("Admin creates DELETE Request with invalid adminId")
		public void admin_creates_delete_request_with_invalid_admin_id() {
			endPoint = EndPoints.DELETE_USER.getEndpoint();
			String token= context.getToken();
			//context.setRequest(given().pathParam("userId", context.getUserId()).spec(RequestSpecFactory.withAuth(token)));
			context.setRequest(
				    given()
				        .filter((req, res, ctx) -> {
				            System.out.println("Final Request URI: " + req.getURI());
				            return ctx.next(req, res);
				        })
				        .pathParam("userID", context.getUserId()+"1")
				        
				        .spec(RequestSpecFactory.withAuth(token))
				        
				);
		}

		@When("Admin sends HTTPS Request  with invalid adminId for deleting a batch by BatchId")
		public void admin_sends_https_request_with_invalid_admin_id_for_deleting_a_batch_by_batch_id() {
			Response response = context.getRequest().when().delete(endPoint);
			context.setResponse(response);
		}

		@Then("Admin receives {int} Not Found with Message and boolean success details for the deletion of the admin by BatchId.")
		public void admin_receives_not_found_with_message_and_boolean_success_details_for_the_deletion_of_the_admin_by_batch_id(Integer expStatusCode) {
			int actStatusCode = context.getResponse().getStatusCode();
			LoggerLoad.info("actStatusCode : "+actStatusCode);
			ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		}

		@Given("Admin creates DELETE Request  without authorization for the deletion of the admin by adminId")
		public void admin_creates_delete_request_without_authorization_for_the_deletion_of_the_admin_by_admin_id() {
			endPoint = EndPoints.DELETE_USER.getEndpoint();			
			context.setRequest(given().spec(RequestSpecFactory.withoutAuth()));
		}

		@When("Admin sends  HTTPS request to the endpoint for deleting admin by adminId without authorization")
		public void admin_sends_https_request_to_the_endpoint_for_deleting_admin_by_admin_id_without_authorization() {
			Response response = context.getRequest().when().delete(endPoint);
			context.setResponse(response);
		}

		@Then("Admin receives {int} Unauthorized Status for the deletion of the admin by adminId.")
		public void admin_receives_unauthorized_status_for_the_deletion_of_the_admin_by_admin_id(Integer expStatusCode) {
			int actStatusCode = context.getResponse().getStatusCode();
			LoggerLoad.info("actStatusCode : "+actStatusCode);
			ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		}



}
