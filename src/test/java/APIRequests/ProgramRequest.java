package APIRequests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import context.ScenarioContext;
import endpoints.EndPoints;
import io.restassured.response.Response;
import models.Program;
import models.User;
import models.UserLogin;
import models.UserRoleMap;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;

public class ProgramRequest {

	private final ScenarioContext context;
	
	public ProgramRequest() {
		 this.context = ScenarioContext.getInstance();
	}

	private String endpoint;

	public void CreateProgramRequest() {
		endpoint = EndPoints.CREATE_PROGRAM.getEndpoint();
	}

	
	private Program buildProgramFromExcelRow(Map<String, String> row) {

		Program pgm = new Program();

		pgm.setprogramDescription(row.get("programDescription"));
		pgm.setprogramName(row.get("programName"));
		pgm.setprogramStatus(row.get("programStatus"));

    return pgm;
    }
	

	public void CreateProgramfromExcel(String scenarioName) throws Exception {
		List<Map<String, String>> programdata = ExcelReader.getData(ConfigReader.getProperty("excelPath"), "Program");
		for (Map<String, String> row : programdata) {
			String excelScenario = row.get("Scenario");
			if (scenarioName.equalsIgnoreCase(excelScenario)) {

				Program pgm = buildProgramFromExcelRow(row);

				Response response = context.getRequest().body(pgm).when().post(endpoint);

				context.setResponse(response);
				context.setRowData(row);;

				LoggerLoad.info("Status Code: " + response.getStatusCode());
				if (response.getStatusCode() != 401 && response.getStatusCode() != 404) {
					LoggerLoad.info("Status Message: " + response.jsonPath().getString("message"));

					if (response.getStatusCode() == 201) {
						int programId = Integer.parseInt(response.jsonPath().getString("programId"));
				        String programName = response.jsonPath().getString("programName");

				        context.addBatchId(programId);
				        context.addBatchName(programName);
					}
				}
			}
		}
	}
	
	   private void saveProgramDetails(Response response) {

	        int programId = Integer.parseInt(response.jsonPath().getString("programId"));
	        String programName = response.jsonPath().getString("programName");

	        context.addBatchId(programId);
	        context.addBatchName(programName);

	        LoggerLoad.info("programId: " + programId);
	        LoggerLoad.info("programName: " + programName);
	    }
	
	public void getallprogram()
	{
		endpoint = EndPoints.GET_ALL_PROGRAMS.getEndpoint();
	}
	
	public void getallprogramInvalid() {
		endpoint = EndPoints.GET_ALL_PROGRAMS.getEndpoint()+ "3";
	}
	
	
}
