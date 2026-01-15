package APIRequests;

import context.ScenarioContext;
import endpoints.EndPoints;
import io.restassured.response.Response;
import models.Batch;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import utilities.RequestSpecFactory;
import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class BatchRequest {
	
	
	private final ScenarioContext context;

    public BatchRequest() {
        this.context = ScenarioContext.getInstance();
    }
    
    private String endpoint;
    
    // PrepareRequest
    public void prepareCreateBatchEndpoint() {
        endpoint = EndPoints.CREATE_BATCH.getEndpoint();
    }
    
    // prepare request with token
    public void prepareRequestWithAuth() {
        String token = context.getToken();
        context.setRequest( given().spec(RequestSpecFactory.withAuth(token)));
    }
    public void createBatchFromExcelRow(String scenarioName)
            throws InvalidFormatException, IOException {

        List<Map<String, String>> data =
                ExcelReader.getData("src/test/resources/Testdata.xlsx", "Batch");

        for (Map<String, String> row : data) {
            if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {

                Batch batch = new Batch();
                batch.setbatchDescription(row.get("BatchDescription"));
                String scenario = scenarioName.trim();
                String batchName;
                int programId;

                if (scenario.equalsIgnoreCase("CreateBatchWithValidData")) {
                	//  From Program created earlier
                	programId = context.getProgramId("BATCH");
                	// Use Program Name from Context
                    batchName = context.getProgramName("BATCH") + "23";
                } else {
                	//  From Excel
                	programId = Integer.parseInt(row.get("ProgramId"));
                	batchName = row.get("BatchName");
                }

                batch.setprogramId(programId);
                batch.setbatchName(batchName);
                
                if ( scenario.equalsIgnoreCase("CreateBatchWithMissingAdditionalFields")) {

                    // Use Program Name from Context
                    batchName = context.getProgramName("BATCH") + "25";
                    programId = context.getProgramId("BATCH");

                } else {
                    //  Use Excel for negative / invalid cases
                	programId = Integer.parseInt(row.get("ProgramId"));
                    batchName = row.get("BatchName");
                }

                batch.setbatchName(batchName);
				batch.setbatchName(row.get("BatchName"));
				//batch.setbatchName(generateRandomString());
				batch.setbatchNoOfClasses(Integer.parseInt(row.get("NoOfClasses")));
				batch.setbatchStatus(row.get("BatchStatus"));
				batch.setprogramId(Integer.parseInt(row.get("ProgramId")));
				if(scenarioName.equals("CreateBatchWithEmptyProgramId") || scenarioName.equals("CreateBatchWithInactiveProgramId"))
					batch.setprogramId(Integer.parseInt(row.get("ProgramId")));
				else
					batch.setprogramId(context.getProgramId("BATCH"));

				Response response = context.getRequest().body(batch).when().post(endpoint);

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
    
    //  Save IDs
    private void saveBatchDetails(Response response) {

        int batchId =
                Integer.parseInt(response.jsonPath().getString("batchId"));
        String batchName =
                response.jsonPath().getString("batchName");
      
        context.getBatchId("User");
        context.getBatchName("User");

        LoggerLoad.info("batchId: " + batchId);
        LoggerLoad.info("batchName: " + batchName);
    }

}
