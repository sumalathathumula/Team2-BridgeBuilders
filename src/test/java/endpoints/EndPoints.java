package endpoints;

public enum EndPoints {
	
	//Login end points
		USER_SIGN_IN("/login"),
		
		//Program end points 
		CREATE_PROGRAM("/saveprogram"),
		
		GET_ALL_PROGRAMS("/allPrograms"),
		
		GET_PROGRAM_BYPROGRAMID("/programs/{programId}"),
		
		GET_ALLPROGRAMS_WITHUSERS("/allProgramsWithUsers"),
		
		UPDATE_PROGRAM_BYPROGRAMNAME("/program/{programName}"),
		
		UPDATE_PROGRAM_BYPROGRAMID("/putprogram/{programId}"),
		
		DELETE_PROGRAM_BYPROGRAMID("/deletebyprogid/{programId}"),
		
		DELETE_PROGRAM_BYPROGRAMNAME("/deletebyprogname/{programName}"),
		
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
