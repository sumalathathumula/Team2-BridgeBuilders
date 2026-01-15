package endpoints;

public enum EndPoints {
	
	//Login end points
		USER_SIGN_IN("/login"),
		//user end points
		CREATE_USER("/users/roleStatus"),
        CREATE_NEWSKILL("/SaveSkillMaster"),
        CREATE_NEWSKILLINVALID("/SaveSkillMaster/M"),
        GETALL_SKILLMASTER("/allSkillMaster"),//valid
        GETALL_SKILLMASTERINVALID("/allSkillMasterss"),//invalid
        GETSKILL_BYSKILLNAME("/skills/{skillMasterName}"),//valid
        GETSKILL_BYSKILLNAMEINVALID("/skills/{skillMasterName}/m"),//invalid
        UPDATESKILL_BYSKILLID("/updateSkills/{SkillId}"),
        UPDATESKILL_BYSKILLIDINVALID("/updateSkills/{SkillId}/n"),
        DELETESKILL_BYSKILLID("/deletebySkillId/{skillId}"),
        DELETESKILL_BYSKILLIDINVALID ("/deletebySkillId/{skillId}/M");


	
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
