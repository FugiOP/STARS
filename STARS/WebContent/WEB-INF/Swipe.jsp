<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="../style.css" rel="stylesheet">
<title>Attendance</title>
</head>
<body>
	<div class="swipe-container">

		<h1>${user} <small>${currentCourse}</small></h1>
		<h4 id="deadline">${courseDeadline}</h4>
		<hr>
		<div class="tab">
			<button class="tablinks" onclick="openForm(event, 'Swipe')">Swipe ID</button>
			<button class="tablinks" onclick="openForm(event, 'Manual')">Enter CIN</button>
		</div>
		<div id="Swipe" class="tabcontent">
			<div class="id-swipe">
				<form class="form-inline" action="Swipe" method="post">
					<input class ="btn btn-primary" name="swipe" placeholder="Swipe Student ID" type="password">
				</form>
			</div>
		</div>
		
		<div id="Manual" class="tabcontent">
			<div class="manual">
				<form>
					<input class="form-control" placeholder="Enter CIN">
				</form>
			</div>
		</div>
		
		<div class="clock" id="clockbox"></div>
		</div>
</body>
<script type="text/javascript">
function GetClock(){
var d=new Date();
var nhour=d.getHours(),nmin=d.getMinutes(),nsec=d.getSeconds(),ap;

if(nhour==0){ap=" AM";nhour=12;}
else if(nhour<12){ap=" AM";}
else if(nhour==12){ap=" PM";}
else if(nhour>12){ap=" PM";nhour-=12;}

if(nmin<=9) nmin="0"+nmin;
if(nsec<=9) nsec="0"+nsec;

document.getElementById('clockbox').innerHTML=""+nhour+":"+nmin+":"+nsec+ap+"";
}

window.onload=function(){
GetClock();
setInterval(GetClock,1000);
}
</script>
<script>
function openForm(evt, formName) {
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(formName).style.display = "block";
    evt.currentTarget.className += " active";
}
</script>
</html>