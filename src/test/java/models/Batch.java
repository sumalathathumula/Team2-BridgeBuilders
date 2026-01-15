package models;

public class Batch {

	private String batchDescription;
	private String batchName;
	private int batchNoOfClasses;
	private String batchStatus;
	private int programId;
	
	public String getbatchDescription() {
		return batchDescription;
	}
	public void setbatchDescription(String batchDescription) {
		this.batchDescription = batchDescription;
	}
	public String getbatchName() {
		return batchName;
	}
	public void setbatchName(String batchName) {
		this.batchName = batchName;
	}
	public int getbatchNoOfClasses() {
		return batchNoOfClasses;
	}
	public void setbatchNoOfClasses(int batchNoOfClasses) {
		this.batchNoOfClasses = batchNoOfClasses;
	}
	public String getbatchStatus() {
		return batchStatus;
	}
	public void setbatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}
	public int getprogramId() {
		return programId;
	}
	public void setprogramId(int programId) {
		this.programId = programId;
	}
}
