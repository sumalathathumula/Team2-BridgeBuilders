package APIRequests;

import java.io.IOException;
import java.util.HashMap;
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
	        public void getallprogram()
			{
				endpoint = EndPoints.GET_ALL_PROGRAMS.getEndpoint();
			}
	        public void getallprogramInvalid() {
				endpoint = EndPoints.GET_ALL_PROGRAMS.getEndpoint()+ "3";
			}
	        public void updateprogrambyprogramid() {
				endpoint = EndPoints.UPDATE_PROGRAM_BYPROGRAMID.getEndpoint();
			}
	        public void updateprogrambyprogramname() {
				endpoint = EndPoints.UPDATE_PROGRAM_BYPROGRAMNAME.getEndpoint();
			}
	        
	        

			private Map<String, Object> buildUpdateRequestBody(Map<String, String> row , String scenarioName) {
			    Map<String, Object> body = new HashMap<>();
			    String programName = row.get("ProgramName");
			    String programStatus = row.get("ProgramStatus");
			    String programDescription = row.get("ProgramDescription");
			   
			    if (programName != null) {
			    body.put("programName", programName.trim());
			    }
			    body.put("programStatus", programStatus.trim());
			    body.put("programDescription",row.getOrDefault("programDescription", "Updated"));
			    return body;
			}
	        public void updateProgramIdFromExcelRow(String scenarioName) throws Exception {
			    List<Map<String, String>> rows =
			            ExcelReader.getData(ConfigReader.getProperty("excelPath"), "Program");
			    for (Map<String, String> row : rows) {
			        if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {
			            context.setRowData(row);
			            Map<String, Object> payload = buildUpdateRequestBody(row,scenarioName);
			            Integer programId;
			          
			           
			            if (scenarioName.contains("InvalidProgramId")) {
			                programId = 999999; // non-existing numeric ID
			            }
			            // Valid Program ID
			            else {
			                programId = context.getProgramId("DELETE_BY_ID");
			              
			            }
			            Response response =
			                    context.getRequest()
			                            .pathParam("programId", programId) // correct param
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
		public void updateProgramNameFromExcelRow(String scenarioName) throws Exception {
		    List<Map<String, String>> rows =
		            ExcelReader.getData(ConfigReader.getProperty("excelPath"), "Program");
		    for (Map<String, String> row : rows) {
		        if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {
		            context.setRowData(row);
		            Map<String, Object> payload = buildUpdateRequestBody(row,scenarioName);
		            String programName;
		          
		            if (scenarioName.contains("InvalidProgramName")) {
		                programName = "zyxcba";
		            }
		         
		            else {
		                programName = context.getProgramName("DELETE_BY_NAME");
	 
		            }
		            Response response =
		                    context.getRequest()
		                            .pathParam("programName", programName) // correct param
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
