package Attendance;

import java.sql.Time;

public class CourseModel {

	String courseName;
	Time deadline;
	int hour;
	int min;
	
	public CourseModel(String courseName, Time deadline, int hour, int min) {
		super();
		this.courseName = courseName;
		this.deadline = deadline;
		this.hour = hour;
		this.min = min;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Time getDeadline() {
		return deadline;
	}

	public void setDeadline(Time deadline) {
		this.deadline = deadline;
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
