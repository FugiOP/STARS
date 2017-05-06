<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta http-equiv="refresh" content="5" />
<title>${currentCourse} Attendance View</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

</head>
<body>
<div  class="container">

<sql:setDataSource var="snapshot" driver="com.mysql.jdbc.Driver"
     url="jdbc:mysql://cs3.calstatela.edu/cs3220stu98"
     user="cs3220stu98"  password="!SagHy*C"/>

<sql:update dataSource="${snapshot}">
CREATE TABLE temp LIKE ${currentCourse}_${instructorID};
</sql:update>

<sql:update dataSource="${snapshot}"> 
INSERT temp SELECT * FROM ${currentCourse}_${instructorID};
</sql:update>

<sql:update dataSource="${snapshot}">
ALTER TABLE temp
DROP firstname , DROP lastname;
</sql:update>

<sql:query dataSource="${snapshot}" var="result">
SELECT * FROM temp;
</sql:query>

<sql:update dataSource="${snapshot}">
DROP TABLE temp;
</sql:update>


<table class="table table-bordered table-hover" border="1">
	<tr>
		<c:forEach var="col" items="${result.columnNames}">
			<th>${col}</th>
		</c:forEach>
	</tr>
	<c:forEach var="row" items="${result.rowsByIndex}">
		<tr>
			<c:forEach var="i" begin="0" end="${fn:length(row)-1}" step="1">
				<td>${row[i]}</td>
			</c:forEach>
		</tr>
	</c:forEach>
</table>
</div>
</body>
</html>