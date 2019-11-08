<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>BMI Calculator</title>
</head>
<body>
<center><h2>BMI Calculator</h2>
<form action = "BMIServlet" method="post">
<h4><label>Enter your weight: </label></h4>
<input type = "text" name = "weight"/><br>
<h4><label>Enter your height: </label></h4>
<input type = "text" name = "height"/><br><br>
<input type = "submit" value = "Calculate"/><label></label>
</form></center>
</body>
</html>
