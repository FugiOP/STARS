package Attendance;

public class StudentModel {
	int cin;
	String firstName;
	String lastName;
	
	public StudentModel(String firstName, String lastName, int cin) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.cin = cin;
	}

	public int getCin() {
		return cin;
	}

	public void setCin(int cin) {
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