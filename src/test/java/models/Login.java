package models;

public class Login {
	
	private String userLoginEmailId;
	private String password;
	
	public String getuserLoginEmailId() {
		return userLoginEmailId;
	}
	public void setuserLoginEmailId(String userLoginEmailId) {
		this.userLoginEmailId = userLoginEmailId;
	}
	public String getpassword() {
		return password;
	}
	public void setpassword(String password) {
		this.password = password;
	}

}
