<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>Login Page</title>
<style>
/* NOTE: The styles were added inline because Prefixfree needs access to your styles and they must be inlined if they are on local disk! */
@import url(https://fonts.googleapis.com/css?family=Exo:100,200,400);
@import url(https://fonts.googleapis.com/css?family=Source+Sans+Pro:700,400,300);
#fade {
	display: none;
	position: absolute;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 120%;
	background-color: #ababab;
	z-index: 1001;
	-moz-opacity: 0.8;
	opacity: .70;
	filter: alpha(opacity = 80);
}
#modal {
	display: none;
	position: absolute;
	top: 45%;
	left: 45%;
	width: 64px;
	height: 64px;
	padding: 30px 15px 0px;
	border-radius: 20px;
	z-index: 1002;
	text-align: center;
	overflow: auto;
}
body {
	margin: 0;
	padding: 0;
	background: #fff;
	color: #fff;
	font-family: Arial;
	font-size: 12px;
}

.body {
	position: absolute;
	top: -20px;
	left: -20px;
	right: -40px;
	bottom: -40px;
	width: 1384px;
	height: 637px;
	background-image:
		url(images/city-skyline-wallpapers-006.jpg);
	background-size: cover;
	-webkit-filter: blur(8px);
	z-index: 0;
}

.grad {
	position: absolute;
	top: -20px;
	left: -20px;
	right: -40px;
	bottom: -40px;
	width: 101.3%;
	height: 103%;
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(0, 0
		, 0, 0)), color-stop(100%, rgba(0, 0, 0, 0.65)));
	/* Chrome,Safari4+ */
	z-index: 1;
	opacity: 0.7;
}

.header {
	position: absolute;
	top: calc(50% - 35px);
	left: calc(50% - 255px);
	z-index: 2;
}

.header2 {
	position: absolute;
	top: calc(36% - 35px);
	left: calc(38% - 255px);
	z-index: 2;
}

.header div {
	float: left;
	color: #fff;
	font-family: 'Exo', sans-serif;
	font-size: 35px;
	font-weight: 200;
	margin-left: -263px;
	margin-top: 14px;
}

.header2 div {
	float: left;
	color: #fff;
	font-family: unset;
	font-size: 35px;
	font-weight: 200;
	margin-left: -100px;
	margin-top: 1px;
}

.header div span {
	color: #5379fa !important;
}

.login {
	position: absolute;
	top: calc(40% - 75px);
	left: calc(52% - 50px);
	height: 150px;
	width: 350px;
	padding: 10px;
	z-index: 2;
}

.login input[type=text] {
	width: 295px;
	height: 30px;
	background: transparent;
	border: 1px solid rgba(255, 255, 255, 0.6);
	border-radius: 2px;
	color: #fff;
	font-family: 'Exo', sans-serif;
	font-size: 16px;
	font-weight: 400;
	padding: 4px;
}

.login select[type=dropdown] {
	width: 303px;
	height: 42px;
	background: transparent;
	border: 1px solid rgba(255, 255, 255, 0.6);
	border-radius: 2px;
	color: #fff;
	font-family: 'Exo', sans-serif;
	font-size: 16px;
	font-weight: 400;
	padding: 4px;
	margin-bottom: 10px;
}

.login input[type=password] {
	width: 295px;
	height: 30px;
	background: transparent;
	border: 1px solid rgba(255, 255, 255, 0.6);
	border-radius: 2px;
	color: #fff;
	font-family: 'Exo', sans-serif;
	font-size: 16px;
	font-weight: 400;
	padding: 4px;
	margin-top: 10px;
}

.login button[type=button] {
	width: 306px;
	height: 35px;
	background: #fff;
	border: 1px solid #fff;
	cursor: pointer;
	border-radius: 2px;
	color: #a18d6c;
	font-family: 'Exo', sans-serif;
	font-size: 16px;
	font-weight: 400;
	padding: 6px;
	margin-top: 10px;
}

.login button[type=button]:hover {
	opacity: 0.8;
}

.login  button[type=button]:active {
	opacity: 0.6;
}

.login input[type=text]:focus {
	outline: none;
	border: 1px solid rgba(255, 255, 255, 0.9);
}

.login select[type=dropdown]:focus {
	outline: none;
	border: 1px solid rgba(255, 255, 255, 0.9);
}

.login input[type=password]:focus {
	outline: none;
	border: 1px solid rgba(255, 255, 255, 0.9);
}

.login button[type=button]:focus {
	outline: none;
}

::-webkit-input-placeholder {
	color: rgba(255, 255, 255, 0.6);
}

::-moz-input-placeholder {
	color: rgba(255, 255, 255, 0.6);
}
</style>

<script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>
<style type="text/css">
.errmsg {
	color: red;}
#message {
	color: red;}
.texterror {
	font-family: 'Source Sans Pro', sans-serif;
	font-size: 16px;
	color: red;}

</style>

</head>

<body>

  <div class="body"></div>
		<div class="grad"></div>
		
		<div class="header_main">
		<div class="header2">
		<div style="font-family: 'Exo', sans-serif;">CIAS TOOL</br><p style="font-size: 20px; color:#ffff !important; font-family: 'Exo', sans-serif; font-weight:17px; margin-block-start: 0.2em !important;">C-Edge Intelligence and Analytics Solution</p></div>
		</div></br>
		
		
		<div class="header" >
		<div>C-EDGE</br><span >Innovate Excel Empower</span></div>
		</div>
	</div>
		<br>
		
	<div class="login">
		<form:form id="loginform1" commandName="loginForm" class="form"
			method="POST" action="${pageContext.request.contextPath}/ciaspage">

			<form:select type="dropdown" data-init-plugin="select2" id="namkName"
				path="bankName" class="form-input1" style="display:none;"
				name="bankName" value="">
				<form:option value="Please Select Bank Name"></form:option>
				<!-- <span class="glyphicon glyphicon-triangle-bottom"></span> -->
				<c:forEach items="${banks}" var="bank">
					<form:option value="${bank.bankName}" style="color: black;"></form:option>
				</c:forEach>
			</form:select>

			<form:input id="tellerid" style="margin-top:10px;" placeholder="Username" autocomplete="off" path="tellerid" 
			class="form-input1" type="text" name="tellerid" value=""></form:input><br>
			<span id="errmsg" style="display:inline"></span>
			<form:input path="pwd" class="form-input1" placeholder="Password" type="password" name="pwd" value=""></form:input>
			 <button id="loginform" type="button" class="login-button">
				<span class="glyphicon glyphicon-log-in"></span>&nbsp;&nbsp;&nbsp;Sign in
			</button>

			<div class="text-center w-full p-t-23">
				<p class="texterror">
					<c:out value='${INVALID_USER}' />
				</p>
				<p class="texterror">
					<c:out value='${SESSION_LOGOUT}' />
				</p>
				<p class="texterror">
					<c:out value='${LOGIN_ERROR}' />
				</p>
				<p class="texterror">
					<c:out value='${NO_PRVLG}' />
				</p>
				<p class="texterror">
					<c:out value='${error}' />
				</p>
				<p class="texterror">
					<c:out value='${errCode}' />
				</p>

			</div>
			<p class="authorized_msg">
				Only authorized users are allowed to access company assets.<br>
				All accesses are logged and monitored.
			</p>
		</form:form>
		
	
		</div>	
		<div id="fade"></div>
		<div id="modal" style="height: auto; width: auto; padding: 0; border-radius: 0;">
			<img id="loader" style="width: 150px;" src="images/Spinner.gif" />
		</div>	
<!-- //////////////////////////Jquery Libraries////////////////////////////////////////// -->

	<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script type="text/javascript" src="js/main.js"></script>
	<script type="text/javascript" src="js/report.js"></script>
	
	
<!-- ///////////////////////JavaScript Code/////////////////////////////////////////// -->	
<script>

// this is for Enter key which submits login form.
var input = document.getElementById("pwd");
input.addEventListener("keyup", function(event) {
  if (event.keyCode === 13) {
   event.preventDefault();
   document.getElementById("loginform").click();
  }
});


	$(document).ready(function () {
		
		 document.getElementById('modal').style.display = 'none';  
	   
		window.history.pushState(null, "", window.location.href);        
	        window.onpopstate = function() {
	            window.history.pushState(null, "", window.location.href);
	        };
		
		$('#loginform').click(function(e) {
			processLoginForm();
		});
		
		  //called when key is pressed in textbox
		  $("#tellerid").keypress(function (e) {
		     //if the letter is not digit then display error and don't type anything
		     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
		        //display error message
		        $("#errmsg").html("Digits Only").show().fadeOut("slow");
		               return false;
		    }
		   });
		  
		  $("#tellerid").keyup(function (e) {
			  if($('#tellerid').val()=='501'){
			  document.getElementById('namkName').style.display = 'inline-block';
			/*   $("#namkName").select2(); */
			  }
			  else {
				  document.getElementById('namkName').style.display = 'none';  
			  }
			   });
		});
	</script>	
	
<!-- ///////////////////////JavaScript Ended Code/////////////////////////////////////////// -->		
</body>
</html>