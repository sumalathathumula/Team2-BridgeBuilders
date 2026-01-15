package APIRequests;

import context.ScenarioContext;
import endpoints.EndPoints;
import io.restassured.response.Response;

import lombok.extern.slf4j.Slf4j;
import models.Batch;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import utilities.ExcelReader;
import utilities.RequestSpecFactory;
import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Slf4j
public class BatchRequest {
	
	//private final ScenarioContext context = ScenarioContext.getInstance();
	private final ScenarioContext context;

    public BatchRequest() {
        this.context = ScenarioContext.getInstance();
    }
 // set end point
    public void setCreateBatchEndpoint() {
         EndPoints.CREATE_BATCH.getEndpoint();
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
				batch.setbatchName(row.get("BatchName"));
				//batch.setbatchName(generateRandomString());
				batch.setbatchNoOfClasses(Integer.parseInt(row.get("NoOfClasses")));
				batch.setbatchStatus(row.get("BatchStatus"));
				batch.setprogramId(Integer.parseInt(row.get("ProgramId")));
//				if(scenarioName.equals("CreateBatchWithEmptyProgramId") || scenarioName.equals("CreateBatchWithInactiveProgramId"))
//					batch.setprogramId(Integer.parseInt(row.get("ProgramId")));
//				else
//					batch.setprogramId(context.getProgramId(0));

				Response response = context.getRequest().body(batch).when().post(endpoint);

				context.setResponse(response);
				context.setRowData(row);

				log.info("Status Code: {}", response.getStatusCode());
				if (response.getStatusCode() != 401 && response.getStatusCode() != 404) {
					log.info("Status Message: {}", response.jsonPath().getString("message"));
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

        context.addBatchId(batchId);
        context.addBatchName(batchName);

        log.info("batchId: {}", batchId);
        log.info("batchName: {}", batchName);
    }

}
