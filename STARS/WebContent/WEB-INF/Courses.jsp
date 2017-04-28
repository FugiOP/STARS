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
			
				<label id="course-label">Select Your Course</label>
				<form action="Courses" method="post">
					<select class="form-control" name="courseName">
						<c:forEach items="${courses}" var="course">
							<option value="${course.courseName}">${course.courseName}</option>
						</c:forEach>
					</select>
					<hr>
					<button type="submit" class="btn btn-success btn-block">Proceed</button>
				</form>
				
			</div>
			
			<br/>
			
			<div class="add-course">
			
			<label id="course-label">Add Course</label>
				<form>
					<input class="form-control" style="width:100%; margin-bottom:10px;" placeholder="Course Name">
					<br/>
					<input class="form-control" type="number" name="hour" placeholder="00" min="0" max="11">
					<input class="form-control" type="number" name="minute" placeholder="00" min="0" max="59">
					<input type="radio" class="radio-inline" value="AM">AM
					<input type="radio" class="radio-inline" value="PM">PM
					<hr>
					<button type="submit" class="btn btn-primary btn-block">Add</button>
				</form>
			</div>
		</div>
		
		<div class="col-xs-6">
			<div class="course-options">
			<label id="course-label">Course Settings</label>
				<form action="Settings" method="post">	
					<hr>
					<label>On-time Deadline</label>
					<div class="deadline-setting">
						<c:forEach items="${courses}" var="course">
							<label>${course.courseName}:</label>
							<br/>
							<c:choose>
								<c:when test="${course.hour<12}">
									<input class="form-control" type="number" name="hour" placeholder="${course.hour}" min="0" max="11">
									<input class="form-control" type="number" name="minute" placeholder="${course.min}" min="0" max="59">
									<input type="radio" class="radio-inline" value="AM" checked>AM
									<input type="radio" class="radio-inline" value="PM">PM
								</c:when>
								<c:otherwise>
									<input class="form-control" type="number" name="hour" placeholder="${course.hour-12}" min="0" max="11">
									<input class="form-control" type="number" name="minute" placeholder="${course.min}" min="0" max="59">
									<input type="radio" class="radio-inline" value="AM">AM
									<input type="radio" class="radio-inline" value="PM" checked>PM
								</c:otherwise>
							</c:choose>
							<hr>
						</c:forEach>
						<button type="submit" class="btn btn-success btn-block">Apply Changes</button>
					</div>
				</form>
				
			</div>
		</div>
	</div>

</body>
</html>