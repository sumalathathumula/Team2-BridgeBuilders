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

}
