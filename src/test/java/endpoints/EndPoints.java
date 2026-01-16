package endpoints;

public enum EndPoints {
	
	//Login end points
		USER_SIGN_IN("/login"),
		//user end points
		CREATE_USER("/users/roleStatus"),
	    GET_BATCHES("/batches"),
	    INVALID_BATCH_ENDPOINT("/batchess"),
	    GET_BATCH_BY_NAME("/batches/batchName/"),
	    GET_BATCH_BY_ID("/batches/batchId/"),
	    GET_BATCH_BY_PROGRAMID("/batches/program/");

	
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
