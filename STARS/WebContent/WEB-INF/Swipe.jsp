<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="fbApp">
<head>
	<title>Attendance</title>
	<meta charset="UTF-8">
	<script src="../js/clock.js"></script>
	<script src="../js/tab.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<link href="../style.css" rel="stylesheet">
	<script>
		function goForward(){
			window.history.forward()
		}
	</script>
</head>
<body>
	<div onload="goForward()">
	<c:choose>
		<c:when test="${swipeView == true}">
			<div class="swipe-container">
				<h1>${name} <small>${currentCourse}</small></h1>
				<h4 id="deadline">Deadline for this course is: ${courseDeadline} ${ampm}</h4>
				<form action="Swipe" method = "post">
					<input type="hidden" name="action" value="update">
					<button type="submit">Update</button>
				</form>
				<form>
					<input type="hidden" name="action" value="changeCourse">
					<button type="submit">Change Course</button>
				</form>
				<hr>
			
				<div class="id-swipe">
					<form class="form-inline" action="Swipe" method="post">
						<input type="hidden" name="action" value="readSwipe">
						<input type="password" class ="btn btn-primary" name="swipeData" placeholder="Swipe Student ID or Enter CIN" autofocus>
					</form>
				</div>
				
				<div class="jumbotron">
					READY FOR SWIPE, GREEN ANI OR RED ANI
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${courseSelectView == true}">	
					<div class="select-form">
						<label id="course-label">Select Your Course</label>
						<form action="Swipe" method="post">
							<select class="form-control" name="courseSelected">
								<c:forEach items="${courses}" var="course">
									<option value="${course.courseName}">${course.courseName}</option>
								</c:forEach>
							</select>
							<input type="hidden" name="action" value="select">
							<button type="submit">Select</button>
						</form>			
					</div>
				</c:when>
				<c:otherwise>
					<div class="login-form">
						<form action="Swipe" method="post">
						<input type="text" name="username">
						<input type="password" name="pw">
						<input type="hidden" name="action" value="login">
						<button type="submit">Login</button>
						</form>
					</div>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	</div>
	<br>
	<div class="clock" id="clockbox"></div>
</body>
</html>