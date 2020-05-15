<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/pages-new.css"> 
<link href="css/pages1.css" rel="stylesheet" type="text/css" /> 
<link href="css/font-awesome.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" />
<link href="css/buttons.dataTables.min.css" rel="stylesheet" type="text/css" />
<link href="css/jquery.dataTables.min.css" rel="stylesheet" type="text/css" />
<link href="css/select2.css" rel="stylesheet" type="text/css" />
<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">


<style type="text/css">
.header-color {
	height: auto;
	width: Auto;
	font-size: 35px;
	text-align: left;
	padding: 11px 0;
	
}

.fa-power-off:hover{
    color: red !important;
}
.dropdown {
  position: relative;
  display: inline-block; 
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f1f1f1;
  min-width: 160px;
  margin-left: -99px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
 
}

.dropdown-content a {
  color: black;
  padding: 5px 10px;
  text-decoration: none;
  display: block;
  z-index: 7 !important;
}

.dropdown-content a:hover {background-color: #ddd;}

.dropdown:hover .dropdown-content {display: block;}

</style>

<!-- <script type="text/javascript" src="js/jquery-ui.js"></script> -->
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/datatables.min.js"></script>


 <script type="text/javascript" src="js/jquery-ui.js"></script> 
<script type="text/javascript" src="js/canvasjs.min.js"></script>
<script type="text/javascript" src="js/report.js"></script>

<script type="text/javascript" src="js/select2.min.js"></script>
<script type="text/javascript" src="js/favouritelist.js"></script>
<script type="text/javascript" src="js/branchInfo.js"></script>
</head>
<body>
	     <%-- <div id="main1" >
	<div class="header-color"><span style="font-size:25px;cursor:pointer" class="hidebar" onclick="openNav()" >&#9776;</span>
		<a href="${pageContext.request.contextPath}/landingDefaultPage"><h3>BrimsTool</h3> </a> --%>
		
		 <div id ="main1" style="overflow: hidden">
		 <nav class="navbar navbar-default">
  <div class="container-fluid">
  <div class="navbar-header">
<span style="font-size: 30px;cursor: pointer;float: left; color: black; margin-right: 16px; margin-top: 5px; " class="hidebar" onclick="openNav()" >&#9776;</span>  <a class="navbar-brand" href="${pageContext.request.contextPath}/cias" style="background-color:#42515b;">CIAS Tool</a>
 </div>
<%--     <ul class="nav navbar-nav">
      <li ><a href="${pageContext.request.contextPath}/dashboard"><i class="fa fa-dashboard"></i>DashBoard</a></li>
      <li><a href="${pageContext.request.contextPath}/report"><i class="fa fa-bar-chart" aria-hidden="true"></i>GenerateReport</a></li>
      <li><a href="${pageContext.request.contextPath}/showFavouriteList"><i class="fa fa-bookmark"></i>FavoriteList</a></li>
      <li><a href="${pageContext.request.contextPath}/settings"><i class="fa fa-cog" ></i>Settings</a></li>
      <li><a href="${pageContext.request.contextPath}/help"><i class="fa fa-question-circle" ></i></i>Help</a></li>
       <li><a href="#" onclick="fullScreen()"><i class="fa fa-expand" ></i>FullScreen</a></li>
    </ul> --%>
    
     <ul class="nav navbar-nav">
		
		<%-- <li class=" nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/dashboard"><i class="fa fa-dashboard"></i>DashBoard</a></li> --%>
		<li class=" nav-item"><a class="nav-link" title="GenerateReport" href="${pageContext.request.contextPath}/report"><i class="fa fa-bar-chart" aria-hidden="true"></i>GenerateReport</a></li>
		<li class=" nav-item"><a class="nav-link" title="FavouriteList" href="${pageContext.request.contextPath}/showFavouriteList"><i class="fa fa-bookmark"></i>FavouriteList</a></li>
		<li class=" nav-item"><a class="nav-link" title="StaticReport" href="${pageContext.request.contextPath}/staticReport"><i class="fa fa-bar-chart" aria-hidden="true"></i>StaticReport</a></li>
		<li class=" nav-item"><a class="nav-link" title="Admin" href="${pageContext.request.contextPath}/settings"><i class="fa fa-cog"></i>Admin</a></li>
		<li class=" nav-item"><a class="nav-link" title="Help" href="${pageContext.request.contextPath}/help"><i class="fa fa-question-circle"></i></i>Help</a></li>
		<li class=" nav-item"><a href="#" onclick="fullScreen()" title="FullScreen"><i class="fa fa-expand"></i>FullScreen</a></li>
    <%--  <div class="dropdown1">
    <span data-toggle="dropdown">Download <i class="fa fa-caret-down"></i>
    </span>
    <div class="dropdown-content1">
       <a id="testing" href="${pageContext.request.contextPath}/downloadreportPage?status=inprocess">Inprocess</a>
       <a href="${pageContext.request.contextPath}/downloadreportPage?status=completed" class="download">Downloaded</a>
       <a href="#">Status</a>
    </div>
  </div>  --%>
    </ul>
    
  </div>
</nav>
</div>

		
		 <div class="bold reportland-id ">
		 <label><span class="fa fa-user"></span>&nbsp;${sessionScope.bank}</label>	 
		 <div class="dropdown">
    <button onclick="openForm()" class="btn popup"  style="border-color:#6d5cae; font-weight: bold;border-radius: 30px;background-color: #6d5cae;width: 45px;padding-left: 13px;"><i class="fa fa-power-off" style=" font-size: 20px; color:white;"></i></button>
   <div class="dropdown-content">
    <a href="#" class="btn btn-sm"><span class="fa fa-user"></span>&nbsp;Profile</a>
    <a href="${pageContext.request.contextPath}/" class="btn btn-info btn-sm" style="background-color: lightcoral;border-color: lightcoral;"><i class="fa fa-power-off"></i>&nbsp;<span>Logout</span></a>
  </div>
		</div>
		</div>
			
	<!-- Modal -->
	<%-- <div class="modal fade animate" id="logout" role="dialog">
		<div class="modal-dialog" id="mydrag">
			<!-- Modal content-->
			<div class="modal-content" style="padding:1px;">
				<div class="modal-header" style="background-color:#000000b5;padding: 14px 3px 3px 8px;">
					<h4 class="modal-title bold" style="color:#fff;margin-top:-12px;"> Logout </h4>
					<button type="button" class="close" data-dismiss="modal" onclick="document.getElementById('logout').style.display='none'"style="font-size: 26px;opacity:1;margin-top:1%;color: #fdfdfdfa;">&times;</button>
				</div>
				<div class="modal-body" style="height: 40px;">
					<p style="font-size:20px;padding-top:6%">Do you want to logout..?</p>
				</div>
				<div class="modal-footer"style="height: 50px;">
					<a href="${pageContext.request.contextPath}/" class="btn btn-info btn-sm" style="background-color: lightcoral;border-color: lightcoral;"> <i class="fa fa-power-off"></i> &nbsp;<span>Logout</span></a>&nbsp;
					<button type="button" class="btn btn-info btn-sm" style="background-color: #4a4a4a;border-color: #4a4a4a;" data-dismiss="modal" onclick="document.getElementById('logout').style.display='none'">Close</button>
				</div>
			</div>

		</div>
	</div> --%>
	
	
	
	<script>
	 $(function(){
	        $('a').each(function(){
	            if ($(this).prop('href') == window.location.href) {
	                $(this).addClass('active'); $(this).parents('li').addClass('active');
	            }
	        });
	    });
	
	
	function openForm() {
		document.getElementById("logout").style.display = "block";
	}
	
			function fullScreen() {
				var isInFullScreen = (document.fullscreenElement && document.fullscreenElement !== null) || (document.webkitFullscreenElement && document.webkitFullscreenElement !== null) || (document.mozFullScreenElement && document.mozFullScreenElement !== null) || (document.msFullscreenElement && document.msFullscreenElement !== null);

				var docElm = document.documentElement;
				if (!isInFullScreen) {
					if (docElm.requestFullscreen) {
						docElm.requestFullscreen();
					} else if (docElm.mozRequestFullScreen) {
						docElm.mozRequestFullScreen();
					} else if (docElm.webkitRequestFullScreen) {
						docElm.webkitRequestFullScreen();
					} else if (docElm.msRequestFullscreen) {
						docElm.msRequestFullscreen();
					}
				} else {
					if (document.exitFullscreen) {
						document.exitFullscreen();
					} else if (document.webkitExitFullscreen) {
						document.webkitExitFullscreen();
					} else if (document.mozCancelFullScreen) {
						document.mozCancelFullScreen();
					} else if (document.msExitFullscreen) {
						document.msExitFullscreen();
					}
				}
			}

		</script>