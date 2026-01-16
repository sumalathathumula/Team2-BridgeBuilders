package APIRequests;

import context.ScenarioContext;
import endpoints.EndPoints;
import io.restassured.response.Response;
import models.SkillMaster;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import java.util.List;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.io.IOException;
import utilities.RequestSpecFactory;


public class SkillMasterRequest {
    private final ScenarioContext context;
    private String endpoint;


    public SkillMasterRequest() {
        this.context = ScenarioContext.getInstance();

    }


    //CREATESKILL PREPAREREQUEST
    public void prepareCreateSkillMasterRequest() {
        endpoint = EndPoints.CREATE_NEWSKILL.getEndpoint();
    }

    //UPDATESKILL PREPAREREQUEST
    public void UpdateSkillMasterRequest() {
        endpoint = EndPoints.UPDATESKILL_BYSKILLID.getEndpoint();
    }

    public void UpdateSkillForInvalidEndpoint() {
        endpoint = EndPoints.UPDATESKILL_BYSKILLIDINVALID.getEndpoint() + "1";
    }
     //DELETESKILL PREPAREREQUEST
    public void prepareDeleteSkillMasterRequest() {
        endpoint = EndPoints.DELETESKILL_BYSKILLID.getEndpoint();
    }
    public void getallskillmaster() {
        endpoint = EndPoints.GETALL_SKILLMASTER.getEndpoint();
    }

    public void getallskillmasterInvalid() {
        endpoint = EndPoints.GETALL_SKILLMASTERINVALID.getEndpoint() + "3";
    }

    public void createSkillFromExcelRow(String scenarioName) throws InvalidFormatException, IOException {


    List<Map<String, String>> skillData =
            ExcelReader.getData(
                    ConfigReader.getProperty("excelPath"),
                    "skill"
            );

    for (Map<String, String> row : skillData) {

        if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {

            SkillMaster skill = new SkillMaster();
            skill.setskillName(row.get("SkillName"));


            Response response = context.getRequest()
                    .body(skill)
                    .post(endpoint);

            context.setResponse(response);
            context.setRowData(row);

            LoggerLoad.info("Status Code: " + response.getStatusCode());
            if (response.getStatusCode() != 401 &&
                    response.getStatusCode() != 404) {
                LoggerLoad.info("Status Message: " +
                        response.jsonPath().getString("message"));
            }
            break;
        }
    }
    
}
}

