<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="../css/deleteCourse.css" rel="stylesheet">
</head>
<body>
	<div id="remove-dialouge">
		<form action="DeleteCourse" method="post">
			<p>Removing the course will drop the table from the DataBase and the Attendance taken so far.</p>
			<p>Are you sure you want to remove this course?</p>
			<button id="removeBtn"type="submit" class="btn btn-success" name="action" value="yes">Yes</button>
			<button id="removeBtn" type="submit" class="btn btn-danger" name="action" value="no">No</button>
		</form>
	</div>
</body>
</html>