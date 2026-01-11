package endpoints;

public enum EndPoints {
	
	//Login end points
		USER_SIGN_IN("/login"),
		//user end points
		CREATE_USER("/users/roleStatus");
	
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
