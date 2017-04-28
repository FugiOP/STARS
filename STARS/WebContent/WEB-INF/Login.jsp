<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">
<title>STARS Login</title>
</head>
<body>
	<div class="container">

	<img src="../images/starslogo.png" alt="STARS">
		<div class="login-form">
			<div class="login-error">${loginError}</div>
			<form action="Login" method="post">
				<input class="form-control" type="text" name="username" placeholder="Username">
				<input class="form-control" type="password" name="password" placeholder="Password">
				<br/>
				<button type="submit" class="btn btn-primary btn-lg btn-block">Log In</button>
			</form>
			
			<hr>
			
			<form action="Login" method="post">
				<input type="hidden" name="guest" value="yes">
				<button type="submit" class="btn btn-primary btn-lg btn-block">Sign Up</button>
			</form>
		</div>
	</div>
</body>
</html>