package Attendance;

public class StudentModel {
	String cin;
	String firstName;
	String lastName;
	
	public StudentModel(String firstName, String lastName, String cin) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.cin = cin;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}