package Attendance;

import java.sql.Time;

public class CourseModel {

	String courseName;
	int hour;
	int min;
	
	public CourseModel(String courseName, int hour, int min) {
		super();
		this.courseName = courseName;
		this.hour = hour;
		this.min = min;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}
	
}
