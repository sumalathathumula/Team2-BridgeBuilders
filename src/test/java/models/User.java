package models;

import java.util.List;

public class User {
	private String userComments;
	private String userEduPg;
	private String userEduUg;
	private String userFirstName;
	private String userLastName;
	private String userLinkedinUrl;
	private String userLocation;
	private String userMiddleName;
	private String userPhoneNumber;
	private List<UserRoleMap> userRoleMaps;
	private String userTimeZone;
	private String userVisaStatus;
	private UserLogin userLogin;

	// Getters and Setters
	public String getuserComments() {
		return userComments;
	}

	public void setuserComments(String userComments) {
		this.userComments = userComments;
	}

	public String getuserEduPg() {
		return userEduPg;
	}

	public void setuserEduPg(String userEduPg) {
		this.userEduPg = userEduPg;
	}

	public String getuserEduUg() {
		return userEduUg;
	}

	public void setuserEduUg(String userEduUg) {
		this.userEduUg = userEduUg;
	}

	public String getuserFirstName() {
		return userFirstName;
	}

	public void setuserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getuserLastName() {
		return userLastName;
	}

	public void setuserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getuserLinkedinUrl() {
		return userLinkedinUrl;
	}

	public void setuserLinkedinUrl(String userLinkedinUrl) {
		this.userLinkedinUrl = userLinkedinUrl;
	}

	public String getuserLocation() {
		return userLocation;
	}

	public void setuserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	public String getuserMiddleName() {
		return userMiddleName;
	}

	public void setuserMiddleName(String userMiddleName) {
		this.userMiddleName = userMiddleName;
	}

	public String getuserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setuserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public List<UserRoleMap> getuserRoleMaps() {
		return userRoleMaps;
	}

	public void setuserRoleMaps(List<UserRoleMap> userRoleMaps) {
		this.userRoleMaps = userRoleMaps;
	}

	public String getuserTimeZone() {
		return userTimeZone;
	}

	public void setuserTimeZone(String userTimeZone) {
		this.userTimeZone = userTimeZone;
	}

	public String getuserVisaStatus() {
		return userVisaStatus;
	}

	public void setuserVisaStatus(String userVisaStatus) {
		this.userVisaStatus = userVisaStatus;
	}

	public UserLogin getuserLogin() {
		return userLogin;
	}

	public void setuserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}
}
