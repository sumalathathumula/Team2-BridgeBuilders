package endpoints;

public enum EndPoints {
	
	//Login end points
		USER_SIGN_IN("/login"),
		
		//Batch end points
		CREATE_BATCH("/batches"),
		
		//user end points
		CREATE_USER("/users/roleStatus"),
	
	
	GET_ALL_USERS("/users"),
	GET_ALL_USERS_WITH_FACETS("/v2/users"),
	GET_ALL_ACTIVE_USERS("users/activeUsers"),
	GET_EMAILS_OF_ALL_USERS_WITH_ACTIVE_STATUS("/fetch-emails"),
	GET_ALL_ROLES("/roles"),
	GET_USER_INFORMATION_BY_USERID("/users/{userId}"),
	GET_USER_INFORMATION_BY_ID("/users/{id}"),	
	GET_ALL_USERS_WITH_ROLES("/users/roles"),
	GETS_COUNT_OF_ACTIVE_AND_INACTIVE_USERS("/users/byStatus"),	
    GETS_USERS_FOR_PROGRAM("/users/programs/{programId}"),
    GETS_USERS_FOR_PROGRAM_BATCHES("/users/programBatch/{batchId}"),
    GETS_USER_DETAILS_BY_ID("/users/details/{id}"),
    GETS_USERS_BY_ROLEID("/users/roles/{roleId}"),
    GETS_USERS_BY_ROLEID_V2("/v2/users"),
    
    UPDATE_USER("/users/{userId}"),    
	UPDATE_USER_ROLEID("/users/roleId/{userID}"),
    UPDATE_USER_ROLE_PROGRAM_BATCH_STATUS("/users/roleProgramBatchStatus/{userId}"),
    UPDATE_USER_LOGIN_STATUS("/users/userLogin/{userId}"),
    
    DELETE_USER("/users/{userID}");
	
	private String endpoint;
	private static final String baseUrl = "https://lms-hackthon-feb25-803334c87fbe.herokuapp.com/lms";
	EndPoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
    public String getFullEndpoint() {
        return baseUrl + this.endpoint;  
    }


}
