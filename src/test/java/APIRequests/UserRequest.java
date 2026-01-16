package APIRequests;
import java.util.*;
import context.ScenarioContext;
import endpoints.EndPoints;
import io.restassured.response.Response;
import models.User;
import models.UserLogin;
import models.UserRoleMap;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;

public class UserRequest {

    private final ScenarioContext context = ScenarioContext.getInstance();
    private String endpoint;
    
    // PrepareRequest
    public void prepareCreateUserRequest() {
        endpoint = EndPoints.CREATE_USER.getEndpoint();
    }
    public void prepareCreateUserRequestForInvalidEndpoint() {
        endpoint = EndPoints.CREATE_USER.getEndpoint()+"1";
    }
 // Prepare Request
    public void prepareUpdateAdminRoleStatusRequest() {
   	    endpoint = EndPoints.UPDATE_USER_ROLEID.getEndpoint();
   	}
    
    public void prepareUpdateUserRequest() {
        endpoint = EndPoints.UPDATE_USER.getEndpoint();
    }
    
    //Read Excel and Build User POJO  
       
    private User buildUserFromExcelRow(Map<String, String> row) {

        User user = new User();

        user.setuserFirstName(row.get("userFirstName"));
        user.setuserLastName(row.get("userLastName"));
        user.setuserMiddleName(row.get("userMiddleName"));
        user.setuserPhoneNumber(row.get("userPhoneNumber"));
        user.setuserLocation(row.get("userLocation"));
        user.setuserTimeZone(row.get("userTimeZone"));
        user.setuserVisaStatus(row.get("userVisaStatus"));
        user.setuserComments(row.get("userComments"));
        user.setuserLinkedinUrl(row.get("userLinkedinUrl"));
        user.setuserEduUg(row.get("userEduUg"));

        //Role Map

        if (row.get("roleId") != null && row.get("userRoleStatus") != null) {

            List<UserRoleMap> roleMaps = new ArrayList<>();

            String[] roleIds = row.get("roleId").split(",");
            String[] roleStatuses = row.get("userRoleStatus").split(",");

            for (int i = 0; i < roleIds.length; i++) {
                UserRoleMap role = new UserRoleMap();
                role.setroleId(roleIds[i]);
                role.setuserRoleStatus(roleStatuses[i]);
                roleMaps.add(role);
            }
            user.setuserRoleMaps(roleMaps);
        }

       //LoginEmail, loginStatus

        if (row.get("userLoginEmail") != null) {
            UserLogin login = new UserLogin();
            login.setuserLoginEmail(row.get("userLoginEmail"));
            login.setloginStatus(row.get("loginStatus"));
            user.setuserLogin(login);
        }

        return user;
    }

    // CreateUserAPI to call in Step Definition
    public void createUserFromExcelRow(String scenarioName) throws Exception {

        List<Map<String, String>> rows =
                ExcelReader.getData(ConfigReader.getProperty("excelPath"), "User");

        for (Map<String, String> row : rows) {

            if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {

                User user = buildUserFromExcelRow(row);

                Response response = context.getRequest()
                        .body(user)
                        .post(endpoint);

                context.setResponse(response);
                context.setRowData(row);

                if (response.getStatusCode() == 201) {
                    context.setUserId(response.jsonPath().getString("user.userId"));
                    context.setRoleId(response.jsonPath().getString("roles[0].roleId"));
                }
                break;
            }
        }
    }   
    
//Prepare Request
 public void prepareAssignAdminRequest() {
     endpoint = EndPoints.UPDATE_USER_ROLE_PROGRAM_BATCH_STATUS.getEndpoint();
 }

 public void prepareAssignAdminRequestForInvalidEndpoint() {
     endpoint = EndPoints.UPDATE_USER_ROLE_PROGRAM_BATCH_STATUS.getEndpoint() + "1";
 }

 //Build Payload

 private Map<String, Object> buildAssignAdminPayload(
         Map<String, String> row,
         String scenarioName) {

     Map<String, Object> payload = new HashMap<>();

     // userId
     if (scenarioName.equalsIgnoreCase("AssignAdminToProgramBatchWithInvalidAdminId")) {
         payload.put("userId", row.get("userId"));
     } else {
         payload.put("userId", context.getUserId());
     }

     // roleId
     payload.put("roleId", context.getRoleId());

     // programId
     if (scenarioName.equalsIgnoreCase("AssignAdminToProgramBatchWithDeletedProgramId")) {
         payload.put("programId", context.getProgramId("DELETE_BY_ID"));
     } else {
         payload.put("programId", context.getProgramId("BATCH"));
     }

     // batch mapping (mandatory unless missing-field scenario)
     if (!scenarioName.equalsIgnoreCase("AssignAdminToProgramBatchWithMissingMandatoryField")) {

         Map<String, Object> batchMap = new HashMap<>();

         if (scenarioName.equalsIgnoreCase("AssignAdminToProgramBatchWithInvalidBatchId")) {
             batchMap.put("batchId", 0);
         } else {
             batchMap.put("batchId", context.getBatchId("USER"));
         }

         batchMap.put(
                 "userRoleProgramBatchStatus",
                 row.get("userRoleProgramBatchStatus")
         );

         payload.put(
                 "userRoleProgramBatches",
                 List.of(batchMap)
         );
     }

     return payload;
 }

 //PUT Assign Admin API

 public void assignAdminFromExcelRow(String scenarioName) throws Exception {

	 List<Map<String, String>> rows =
		        ExcelReader.getData(ConfigReader.getProperty("excelPath"), "User");

		for (Map<String, String> row : rows) {

		    if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {

		        Map<String, Object> payload =
		                buildAssignAdminPayload(row, scenarioName);

		        String userId;

		        if (scenarioName.contains("InvalidAdminId")) {
		            userId = "U999999";
		        } else if (scenarioName.contains("MissingMandatoryField")) {
		            userId = null;
		        } else {
		            userId = context.getUserId();
		        }

		        Response response;

		        if (userId != null) {
		            response = context.getRequest()
		                    .pathParam("userId", userId)
		                    .body(payload)
		                    .put(endpoint);
		        } else {
		            response = context.getRequest()
		                    .body(payload)
		                    .put(endpoint.replace("/{userId}", ""));
		        }

		        context.setResponse(response);
		        context.setRowData(row);

		        LoggerLoad.info("Status Code: " + response.getStatusCode());
		        LoggerLoad.info("Response Body: " + response.asPrettyString());

		        if (response.getStatusCode() != 401 && response.getStatusCode() != 404) {
		            LoggerLoad.info("Status Message: " +
		                    response.jsonPath().getString("message"));
		        }

		        break;
		    }
		}
 }
 
 
 //PUTREQUEST(Update admin by admin ID)
 private Map<String, Object> buildUpdateAdminRolePayload(
	        Map<String, String> row,
	        String scenarioName) {

	    Map<String, Object> payload = new HashMap<>();
	    List<Map<String, Object>> userRoleList = new ArrayList<>();

	    Map<String, Object> roleMap = new HashMap<>();

	    // Missing mandatory field scenario
	    if (scenarioName.contains("MissingMandatoryField")) {

	        // Intentionally skip roleId or roleStatus
	        roleMap.put("roleId", row.get("roleId"));
	        // userRoleStatus missing

	    } else {

	        roleMap.put("roleId", row.get("roleId"));
	        roleMap.put("userRoleStatus", row.get("userRoleStatus"));
	    }

	    userRoleList.add(roleMap);
	    payload.put("userRoleList", userRoleList);

	    return payload;
	}
 
 
 public void updateAdminRoleStatusFromExcelRow(String scenarioName) throws Exception {

	    List<Map<String, String>> rows =
	            ExcelReader.getData(ConfigReader.getProperty("excelPath"), "User");

	    for (Map<String, String> row : rows) {

	        if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {

	            context.setRowData(row);

	            Map<String, Object> payload =
	                    buildUpdateAdminRolePayload(row, scenarioName);

	            String userId;

	            // Invalid Admin ID
	            if (scenarioName.contains("InvalidAdminId")) {
	                userId = "U0000";   // non-existing
	            } else {
	                userId = context.getUserId(); // valid
	            }

	            Response response = context.getRequest()
	                    .pathParam("userID", userId)
	                    .body(payload)
	                    .log().body()
	                    .put(endpoint);

	            context.setResponse(response);

	            LoggerLoad.info("Status Code: " + response.getStatusCode());
	            LoggerLoad.info("Response Body: " + response.asPrettyString());

	            break;
	        }
	    }
	}

//updateuser
 private User buildUpdateAdminPayload(
	        Map<String, String> row,
	        String scenarioName) {

	    User user = new User();

	    // Mandatory fields
	    if (!scenarioName.contains("MissingMandatoryFields")) {
	        user.setuserFirstName(row.get("userFirstName"));
	        user.setuserLastName(row.get("userLastName"));
	        user.setuserPhoneNumber(row.get("userPhoneNumber"));
	    }

	    // Optional / additional fields
	    user.setuserMiddleName(row.get("userMiddleName"));
	    user.setuserLocation(row.get("userLocation"));
	    user.setuserTimeZone(row.get("userTimeZone"));
	    user.setuserLinkedinUrl(row.get("userLinkedinUrl"));
	    user.setuserEduUg(row.get("userEduUg"));
	    user.setuserEduPg(row.get("userEduPg"));
	    user.setuserComments(row.get("userComments"));
	    user.setuserVisaStatus(row.get("userVisaStatus"));
	    if (row.get("userLoginEmail") != null) {
            UserLogin login = new UserLogin();
            login.setuserLoginEmail(row.get("userLoginEmail"));
            login.setloginStatus(row.get("loginStatus"));
            user.setuserLogin(login);
        }

	    return user;
	}
 
 public void updateAdminFromExcelRow(String scenarioName) throws Exception {

	    List<Map<String, String>> rows =
	            ExcelReader.getData(ConfigReader.getProperty("excelPath"), "User");

	    for (Map<String, String> row : rows) {

	        if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {

	            context.setRowData(row);

	            User payload = buildUpdateAdminPayload(row, scenarioName);

	            String userId;

	            if (scenarioName.contains("InvalidAdminId")) {
	                userId = "U0000"; // invalid admin ID
	            } else {
	                userId = context.getUserId(); // valid admin ID
	            }

	            Response response = context.getRequest()
	                    .pathParam("userId", userId)
	                    .body(payload)
	                    .log().body()
	                    .put(endpoint);

	            context.setResponse(response);

	            LoggerLoad.info("Status Code: " + response.getStatusCode());
	            LoggerLoad.info("Response Body: " + response.asPrettyString());

	            break;
	        }
	    }
	}


}
