<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">
<title>Select Course</title>
</head>
<body>
	<div class="row">
		<div class="col-xs-6">
		
			<div class="course-select">
			
				<label id="course-label">View Course Attendance</label>
				<form action="Courses" method="post">
					<select class="form-control" name="courseName">
						<c:forEach items="${courses}" var="course">
							<option value="${course.courseName}">${course.courseName}</option>
						</c:forEach>
					</select>
					<hr>
					<input type="hidden" name="action" value="viewAttendance">
					<button type="submit" class="btn btn-success btn-block">View</button>
				</form>
				
			</div>
			
			<br/>
			
			<div class="add-course">
			
				<label id="course-label">Add Course</label>
				<c:if test="${not empty (addError || duplicateError)}">
					<p id="error">${addError}</p>
					<p id="error">${duplicateError}</p>
					<p id="error">${invalidName}</p>
				</c:if>
				<form action="AddCourse" method="post">
					<input class="form-control" name="courseName" style="width:100%; margin-bottom:10px;" placeholder="Course Name">
					<br/>
					<input class="form-control" type="number" name="hour" placeholder="00" min="0" max="11">
					<input class="form-control" type="number" name="min" placeholder="00" min="0" max="59">
					<input type="radio" name="ampm" class="radio-inline" value="AM">AM
					<input type="radio" name="ampm" class="radio-inline" value="PM">PM
					<hr>
					<button type="submit" class="btn btn-primary btn-block">Add</button>
				</form>
			</div>
			
			<div class="col-xs-6">
				EXPORT Selected ATTENDANCE to CSV FILE
				<form action="Courses" method="post">
					<input type="hidden" name="action" value="logout">
					<button type="submit">Logout</button>
				</form>
			</div>
		</div>
		
		<div class="col-xs-6">
			<div class="course-options">
			<label id="course-label">Course Settings</label>	
					<hr>
					<label>On-time Deadline</label>
					<div class="deadline-setting">
						<c:forEach items="${courses}" var="course">
							<label>${course.courseName}:</label>
							<br/>
							<c:choose>
								<c:when test="${course.hour<12}">
									<form action="Settings" method="post">
										<input type="hidden" name="courseName" value="${course.courseName}">
										<input class="form-control" type="number" name="hour" placeholder="${course.hour}" min="0" max="11">
										<input class="form-control" type="number" name="min" placeholder="${course.min}" min="0" max="59">
										<input type="radio" class="radio-inline" value="AM" checked>AM
										<input type="radio" class="radio-inline" value="PM">PM
										<input type="hidden" name="action" value="1">
										<button type="submit" class="btn btn-success btn-block">Apply Changes</button>
									</form>
								</c:when>
								<c:otherwise>
									<form action="Settings" method="post">
										<input type="hidden" name="courseName" value="${course.courseName}">
										<input class="form-control" type="number" name="hour" placeholder="${course.hour-12}" min="0" max="11">
										<input class="form-control" type="number" name="min" placeholder="${course.min}" min="0" max="59">
										<input type="radio" class="radio-inline" value="AM">AM
										<input type="radio" class="radio-inline" value="PM" checked>PM
										<input type="hidden" name="action" value="1">
										<button type="submit" class="btn btn-success btn-block">Apply Changes</button>
									</form>
								</c:otherwise>
							</c:choose>
							<form action="Settings" method ="post">
								<input type="hidden" name="courseName" value="${course.courseName}">
								<input type="hidden" name="action" value="2">
								<button type="submit" class="btn btn-danger btn-block">Remove</button>
							</form>
							<hr>
						</c:forEach>
					</div>
			</div>
		</div>
	</div>
</body>
</html>