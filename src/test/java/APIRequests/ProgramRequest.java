package APIRequests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import context.ScenarioContext;
import endpoints.EndPoints;
import io.restassured.response.Response;
import models.Program;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;

public class ProgramRequest {
	 
	    private String endpoint;  
	    
	        private final ScenarioContext context;

	        public ProgramRequest() {
	            this.context = ScenarioContext.getInstance();
	        }
	     // PrepareRequest
	 	   
	        public void prepareProgramRequest() {
	            endpoint = EndPoints.CREATE_PROGRAM.getEndpoint();
	        }
	        
	        public void createProgramFromExcelRow(String scenarioName) throws InvalidFormatException, IOException {

	            //try {
	                List<Map<String, String>> programData =
	                        ExcelReader.getData(
	                                ConfigReader.getProperty("excelPath"),
	                                "Program"
	                        );

	                for (Map<String, String> row : programData) {

	                    if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {

	                        Program program = new Program();
	                        program.setprogramName(row.get("ProgramName"));
	                        program.setprogramDescription(row.get("ProgramDescription"));
	                        program.setprogramStatus(row.get("ProgramStatus"));

	                        Response response = context.getRequest()
	                                .body(program)
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
//
//	            } catch (Exception e) {
//	                LoggerLoad.error("Program creation failed: " + e);
//	            }
	        }

}
