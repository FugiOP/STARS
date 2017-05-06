<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">
<title>Course Options and Settings</title>
</head>
<body>
	<ul>
		<li id="text">${user}</li>
		<li>
			<a href="Courses?action=logout">LOGOUT</a>
		</li>
	</ul>
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
					
					<button type="submit" name="action" value="viewAttendance" class="btn btn-primary btn-block">View</button>
					<button type="submit" name="action" value="export" class="btn btn-success btn-block">Export And Download</button>
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
					<input class="form-control" type="number" name="hour" placeholder="00" min="1" max="12">
					<input class="form-control" type="number" name="min" placeholder="00" min="1" max="59">
					<select class="form-control" name="ampm">
							<option value="AM">AM</option>
							<option value="PM">PM</option>
					</select>
					<hr>
					<button type="submit" class="btn btn-primary btn-block">Add</button>
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
								<c:when test="${course.hour<13}">
									<form action="Settings" method="post">
										<input type="hidden" name="courseName" value="${course.courseName}">
										<input class="form-control" type="number" name="hour" value="${course.hour}" min="1" max="12">
										<input class="form-control" type="number" name="min" value="${course.min}" min="0" max="59">
										<select class="form-control" name="ampm">
												<option value="AM">AM</option>
												<option value="PM">PM</option>
										</select>
										<br><br>
										<div class="col-xs-6">
											<button type="submit" name="action" value="change" class="btn btn-success">Apply Changes</button>
										</div>
									</form>
								</c:when>
								<c:otherwise>
									<form action="Settings" method="post">
										<input type="hidden" name="courseName" value="${course.courseName}">
										<input class="form-control" type="number" name="hour" value="${course.hour-12}" min="1" max="12">
										<input class="form-control" type="number" name="min" value="${course.min}" min="1" max="59">
										<select class="form-control" name="ampm">
												<option value="PM">PM</option>
												<option value="AM">AM</option>
										</select>
										<br><br>
										<div class="col-xs-6">
											<button type="submit" name="action" value="change" class="btn btn-success">Apply Changes</button>
										</div>
									</form>
								</c:otherwise>
							</c:choose>
							<form action="Settings" method ="post">
								<input type="hidden" name="courseName" value="${course.courseName}">
								<div class="col-xs-1">
									<button type="submit" name="action" value="remove" class="btn btn-danger">Remove</button>
								</div>
							</form>
							<br>
							<hr>
						</c:forEach>
					</div>
			</div>
		</div>
	</div>
</body>
</html>