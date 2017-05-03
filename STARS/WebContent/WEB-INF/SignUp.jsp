<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">
<title>Sign Up</title>
</head>
<body>
	<div class="signup-form">
		<form action="SignUp" method="post">
      		<div class="name-group">
      			<c:if test="${not empty nameError}">
      				<p id="error">${nameError}</p>
      			</c:if>
  				<input class="name" type="text" name="firstName" placeholder="First Name">
  				<input class="name" type="text" name="lastName" placeholder="Last Name">
      		</div>
			<div class="form-group">
				<c:if test="${not empty (usernameError || usernameTaken)}">
					<br/>
					<p id="error">${usernameError}</p>
					<p id="error">${usernameTaken}</p>
				</c:if>
				<input class="username" type="text" name="username" placeholder="Username">
				<c:if test="${not empty (pwError || pwMismatch)}">
					<br/><br/>
					<p id="error">${pwError}</p>
					<p id="error">${pwMismatch}</p>
				</c:if>
				<input class="pw" type="password" name="pw1" placeholder="Password">
				<input class="pw" type="password" name="pw2" placeholder="Re-Enter Password">
			</div>
			<button type="submit" class="btn btn-primary btn-lg btn-block">Submit</button>
		</form>
	</div>
</body>
</html>
