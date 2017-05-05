<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="../css/swipe.css" rel="stylesheet">
<script src="../js/clock.js"></script>
<title>STARS</title>
</head>
<body>

<div class="swipe-container">
	<h1 id="top-name">${instructorName} <small> ${selectedCourse}</small></h1>
	<h4 id="deadline">Deadline for this course is: ${courseDeadline} ${ampm}</h4>
	<form action="Swipe" method = "post">
		<button type="submit" class="btn btn-default" name="action" value="update">Update Changes</button>
	</form>
	<hr>

	<div class="id-swipe">
		<form class="form-inline" action="Swipe" method="post">
			<input type="hidden" name="action" value="readSwipe">
			<input id="btn-swipe" type="password" class ="btn btn-primary" name="swipeData" placeholder="Swipe Student ID" autofocus>
		</form>
	</div>
	
	<div class="jumbotron">
		
		<c:if test="${animation == 'pass'}">
			<span id="animationPass" class="glyphicon glyphicon-ok" aria-hidden="true"></span>
			<span id="aniTextPass">Successful</span>
		</c:if>
		
		<c:if test="${animation == 'fail'}">
			<span id="animationFail" class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			<span id="aniTextFail">Invalid</span>
		</c:if>
		
	</div>
	<c:set var="animation" value="${null}" scope="session" />
	<div class="clock" id="clockbox"></div>
	
	<form action="Swipe" method="post">
		<button id="logout" class="btn btn-success"type="submit" name="action" value="logout"> LOG OUT </button>
	</form>
</div>
</body>
</html>