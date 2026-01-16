
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
    private Map<String, Integer> programIds = new HashMap<>();
    private Map<String, String> programNames = new HashMap<>();
    private Map<String, Integer> batchIds = new HashMap<>();
    private Map<String, String> batchNames = new HashMap<>();
	private String userId;
	private String role_id;
	private String Program_id;
	
	
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

    public void addProgram(String key, int id, String name) {
        programIds.put(key, id);
        programNames.put(key, name);
    }

    public int getProgramId(String key) {
        return programIds.get(key);
    }

    public String getProgramName(String key) {
        return programNames.get(key);
    }
    
    public Integer getLatestProgramId() {
        return programIds.values().iterator().next(); 
    }
    
    public String getLatestProgramName() {
    	return programNames.values().iterator().next();
    			}
    

    // BATCH
    
    public void addBatch(String key, int id, String name) {
        batchIds.put(key, id);
        batchNames.put(key, name);
    }

    public int getBatchId(String key) {
        return batchIds.get(key);
    }

    public String getBatchName(String key) {
        return batchNames.get(key);
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
