package context;

import java.util.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ScenarioContext {

    private static ScenarioContext instance;

    private RequestSpecification request;
    private Response response;

    private String token;
    private Map<String, String> rowData;
    private int deletedBatchId;

    private List<Integer> programIds = new ArrayList<>();
    private List<Integer> batchIds = new ArrayList<>();   
    private List<String> programNames = new ArrayList<>();
    private List<String> batchNames = new ArrayList<>();

	private String userId;

	private String role_id;

    private ScenarioContext() {}

    public static synchronized ScenarioContext getInstance() {
        if (instance == null) {
            instance = new ScenarioContext();
        }
        return instance;
    }

    //  REQUEST / RESPONSE 

    public void setRequest(RequestSpecification request) {
        this.request = request;
    }

    public RequestSpecification getRequest() {
        return request;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    // AUTH 

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    // TEST DATA 

    public void setRowData(Map<String, String> rowData) {
        this.rowData = rowData;
    }

    public Map<String, String> getRowData() {
        return rowData;
    }

    //PROGRAM 

    public void addProgramId(int id) {
        programIds.add(id);
    }

    public int getProgramId(int index) {
        return programIds.get(index);
    }

    public void addProgramName(String name) {
        programNames.add(name);
    }

    public String getProgramName(int index) {
        return programNames.get(index);
    }

    // BATCH

    public void addBatchId(int id) {
        batchIds.add(id);
    }

    public int getBatchId(int index) {
        return batchIds.get(index);
    }
    public void addBatchName(String name) {
        batchNames.add(name);
    }
    public String getBatchName(int index) {
    	return batchNames.get(index);
    }
    public void addDeletedBatchId(int deletedBatchId) {
        this.deletedBatchId = deletedBatchId;
    }

    public int getDeletedBatchId() {
        return deletedBatchId;
    }
    

    public  void setUserId(String userId) {
    	this.userId = userId;
		
	}
    public  void setRoleId(String role_id) {
    	this.role_id = role_id;
	}
    public String getUserId() {
        return userId;
    }
    public String getRoleId() {
        return role_id;
    }

    // CLEANUP 

    public void clear() {
        request = null;
        response = null;
        token = null;
        rowData = null;

        programIds.clear();
        programNames.clear();
        batchIds.clear();        
    }
}