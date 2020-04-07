<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Help Page</title>
<%@ include file="header.jsp"%>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<style type="text/css">
a:hover, a:focus, a:active {
	text-decoration: none;
}

.hidebar {
	display: none;
}

.panel-heading {
	padding: 5px 15px !important;
	color: #000 !important;
}

.container-fluid {
	margin-top: 0% !important;
}

.panel-body {
	padding: 15px !important;
}

p {
	color: #626262;
}

.nav-tabs ~.tab-content {
	overflow: hidden;
	padding: 15px;
	border: 1px solid #ddd;
	margin-top: -1px;
}

.help_header {
	color: black;
	font-size: 18px;
	text-align: left;
	margin: 0px auto 10px;
	border-bottom: 1px solid #ddd;
	padding: 0 0 10px;
}

.panel-title {
	text-transform: capitalize !important;
}

.panel-heading a:not (.btn ){
	color: #000 !important;
}

.panel-group .panel-heading .panel-title>a {
	font-size: 15px;
}

.panel.panel-default {
	border: 1px solid rgba(0, 0, 0, 0.07) !important;
}

.table thead tr th {
	color: #fff;
	text-align: center;
}

.table tbody tr td {
	font-size: 12px;
	border: 1px solid #ddd;
}
</style>
</head>
<body style="background: #f7f7f7;">

	
	<div class="report_bg_patch" style="height: auto;padding: 10px;">
		<h2 class="help_header">
			<i class="glyphicon glyphicon-question-sign"></i>&nbsp;Help
		</h2>
		<div class="" style="overflow-y: scroll;height: 410px;">
			<div class="scrollable1">
				<div class="report_bg_patch1">

					<ul class="nav nav-tabs">
						<li class="active"><a data-toggle="tab" href="#faq">FAQ</a></li>
						<li><a id="viewdescription" data-toggle="tab" href="#tablecontent">Table Info</a></li>
					</ul>

					<div class="tab-content">

						<!--  first tab details start -->

						<div id="faq" class="tab-pane fade in active">
							<div class="panel-group" id="accordion">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#collapse1">How to save queries ?</a>
										</h4>
									</div>
									<div id="collapse1" class="panel-collapse collapse in">
										<div class="panel-body">
											<p>1. Click on transmit button</p>
											<p>2. Will get the save button on page</p>
											<p style="margin: 0;">3. Provide all the details click on save</p>
										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#collapse2">How to save data to csv ?</a>
										</h4>
									</div>
									<div id="collapse2" class="panel-collapse collapse">
										<div class="panel-body">
											<p>1. Click on transmit button</p>
											<p>2. You will get the result in tabular format</p>
											<p style="margin: 0;">3. Click on csv format</p>

										</div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#collapse3">How to provide where clouse ?</a>
										</h4>
									</div>
									<div id="collapse3" class="panel-collapse collapse">
										<div class="panel-body">
											<p>1. Select table click on cloumn</p>
											<p>2. Table Criteria pannel get visible</p>
											<p>3. In filter option select criteria</p>
											<p>4. Click on add to filter</p>
											<p style="margin: 0;">5. Click on transmit.</p>
										</div>
									</div>
								</div>
							</div>
							
							<!-- <div class="container">
  
  <p style="margin-left:-41px;">The form below contains a textarea for User Queries:</p>
  <form action="QueryInfo" id="Query" method="post">
    <div class="form-group">
      <label for="comment" style="margin-left: -103px;">Question:</label>
      <textarea class="form-control" rows="5" id="comment" style="width: 1129px;margin-left: -101px"></textarea>
    </div>
    <button type="submit" class="button1" id="updatebtn" style="margin-left: 852px;">Submit</button>
  </form>
</div>
 -->						</div>
						<!--  second tab details -->
						<div id="tablecontent" class="tab-pane fade">
							<table class="table table-hover">
							<thead>
							<tr>
							<th style="width:30%;">Name</th>
							<th>Description</th>
							</tr>
							</thead>
							<tbody id="viewdtls">
							</tbody>
							</table>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
	$(document).ready(function() {
		$('#viewdescription').click(function(e) {
			getViewDetails(e);
		});
	});
	</script>
<%@ include file="/WEB-INF/pages/Footer.jsp"%>