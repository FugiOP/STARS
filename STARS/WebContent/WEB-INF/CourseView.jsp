<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="../css/swipe.css" rel="stylesheet">
<title>STARS</title>
</head>
<body>
	<div class="select-form">
		<label id="course-label">Select Your Course</label>
		<form action="Swipe" method="post">
			<select class="form-control" name="courseSelected">
				<c:forEach items="${courses}" var="course">
					<option value="${course.courseName}">${course.courseName}</option>
				</c:forEach>
			</select>
			<hr>
			<button class="btn btn-primary btn-block" type="submit" name="action" value="select">Select</button>
		</form>			
	</div>
</body>
</html>