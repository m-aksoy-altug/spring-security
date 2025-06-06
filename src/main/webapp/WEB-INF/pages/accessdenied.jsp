<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html"; charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
<script>
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"
</script>
<script>
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
</script>

<title>Courier App Demo</title>
</head>
   <body>
		<br>
		<br>
		<br>
		<div class="container" style="background-color:white; margin-top: 20px; padding-top: 0px;">
			<div class="col-xs-6 col-xs-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<div class="row">
							<img alt="" src="<spring:url value='/images/dummy.jpeg'/>"
									class="col-xs-4 col-xs-offset-4">
						</div>
						<div class="row" align="center">
							<h2> Welcome to Demo App</h2> 
						</div>
			 		</div>
						<div class="panel-body" align="center">
							<div class="row"> 
								<div class="col-xs-6" align="left"> 
											<a href="#"> Home</a>																
									</div>
								<div class="col-xs-6" align="right"> 
											<a href="logout"> Logout</a>																
									</div>		
							</div>
							<br> <br>
								<a href="report.htm"> Generate Report</a> <br>
								<a href="transactions.htm"> Send a Courier</a> <br>																
								<a href="register"> Register</a> 
						</div>				
				
				</div>				
			</div>
		</div> 
	 </body>
</html>