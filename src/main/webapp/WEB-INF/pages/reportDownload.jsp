<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="header.jsp"%>
<link rel="stylesheet" href="css/style.css">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css"
	rel="stylesheet">
<link
	href="https://cdn.datatables.net/1.10.19/css/dataTables.semanticui.min.css"
	rel="stylesheet">
<style>
table1 {
	background: #fff;
	width: 80%;
	max-width: 97%;
}

table.dataTable tbody th, table.dataTable tbody td {
	padding: 3px 10px;
	/* background: #ece6f3; */
	background: #f7f7f7;
	border-bottom: 1px solid #ccc;
	font-weight: 400;
}

table thead th {
	background: #211777;
	color: #fdfafa;
	font-weight: 500;
}

.formbtn {
	padding: 0%;
	margin: 0%;
	padding-left: 0%;
	padding-right: 27%;
}

tfoot input {
	width: 100%;
	padding: 3px;
	font-weight: 500;
	font-size: 13px;
	box-sizing: border-box;
}

table.table-bordered.dataTable tbody tr:hover {
	background-color: #b0c8dc;
	color: #000;
}

.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th,
	.table>thead>tr>td, .table>thead>tr>th {
	font-size: 16px;
	padding: 3px;
	font-family: serif;
	color: black;
}

table.dataTable thead>tr>th.sorting_asc, table.dataTable thead>tr>th.sorting_desc,
	table.dataTable thead>tr>th.sorting, table.dataTable thead>tr>td.sorting_asc,
	table.dataTable thead>tr>td.sorting_desc, table.dataTable thead>tr>td.sorting
	{
	padding-right: 20px;
	font-size: 13px;
}

.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
	border-bottom-width: 0;
}

.table-bordered>tbody>tr>td, .table-bordered>tbody>tr>th,
	.table-bordered>tfoot>tr>td, .table-bordered>tfoot>tr>th,
	.table-bordered>thead>tr>td, .table-bordered>thead>tr>th {
	border-bottom: 1px solid #ccc;
}

tfoot {
	display: table-header-group;
}

.dataTables_paginate {
	padding-bottom: 15px;
}

label {
	font-weight: 500;
}

.staticformdiv {
	max-width: 600px !important;
	margin-top: 30px;
	margin-left: 380px;
	padding: 34px 0;
	border-style: solid;
	border-width: 8.5px;
	border-color: #d2cccc;
}

.formWrapper {
	margin-left: 134px;
}

.form-horizontal .control-label {
	padding: 15px 0 3px;
	font-size: 12px;
}

.req, .mandatory {
	color: red;
}

.form-check-label {
	margin-bottom: 0;
}

.form-check-input {
	float: left;
	margin: 3px 7px 0 !important;
}

.form-check-label span {
	float: left;
}

.custmform {
	padding: 0% 0;
	position: unset;
	z-index: 2;
}

.header-color {
	line-height: 50px !important;
}

.refbtn {
	width: 100px;
	background: yellowgreen;
}

.downbtn {
	width: 45%;
	background: #5fd3ff;
	margin-left: 26%;
}

.downloadbtn {
	width: 85%;
}

.delbtn {
	width: 85%;
}

.btn-success:hover {
	background-color: #339bb7 !important;
	border-color: #339bb7 !important;
}

.hidebar {
	display: none;
}

.new_for_label {
	float: left;
	margin: 7px 0 0 0;
	width: 30%;
}

.new_for_text {
	float: left;
	width: 60%;
}

.marg {
	margin: 0 !important;
}
</style>
</head>
<body>
	<div>
		<div class="report_bg_patchshow">
			<div id="fade"></div>
			<div id="modal"
				style="height: 50; width: auto; padding: 0; border-radius: 0;">
				<img id="loading5" style="width: 150px;" src="images/loading5.gif">
			</div>
			<div class=" loan_label">
				<h4
					style="border-bottom: 1px solid #f1f1f1; font-size: 20px; font-weight: 500; text-align: center;">
					Download Reports Details</h4>

				<div>
					<element> <form:form method="POST"
						action="downloadfile.html" modelAttribute="MADOWNREPORT"
						id="FORMDOWN">
						<form id="stackedForm">
							<div class="form-group" style="overflow: hidden;">
								<div class="col-md-4">
									<label class="new_for_label" for="stackedFirstName">
										Branch Code </label> <input type="text"
										class="form-control new_for_text" required="required"
										id="branCode" path="branCode"
										value="${MADOWNREPORT.getBranCode()}" name="branCode"
										placeholder="Enter Branch Code"/>
								</div>
								<div class="col-md-4">
									<label class="new_for_label" for="stackedLastName">
										Date </label> <input type="text"
										class="form-control datepicker new_for_text"
										id="datepicker-13" path="date" name="date"
										value="${MADOWNREPORT.getDate()}" placeholder="Date" />
								</div>
								<div class="col-md-4">
									<button type="button" class="btn btn-primary" id="getReport"
										style="width: 100px;">
										<span class="glyphicon glyphicon-list-alt"> </span> Transmit
									</button>
									<button type="button" id="btReload" class="btn btn-info refbtn">
										<span class="glyphicon glyphicon-refresh "> </span> Refresh
									</button>
									<div id="clockdiv" style="margin-left: 60%; margin-top: -6%;"></div>
								</div>
							</div>
						</form>
					</form:form> </element>
				</div>
			</div>
			<div>
				<div class="loan_label" style="padding-left: 3%;padding-right: 3%;">
					<table class="table1" id=downfilestatustble align="center">
						<thead align="center">
							<tr>
								<th>Sr.No</th>
								<th>Queue Id</th>
								<th>File Name</th>
								<th>Start Date</th>
								<th>End Date</th>
								<th>Status</th>
								<th>Download</th>
								<th>Delete</th>
							</tr>
						</thead>
						<tbody id="tbodydown">
						</tbody>
						<tfoot>
						</tfoot>
					</table>
				</div>
			</div>
		</div>

	</div>
	<div><%@ include file="/WEB-INF/pages/Footer.jsp"%></div>
	<script>
		$(function() {
			$('.datepicker').datepicker();
		});
	</script>


	<script type="text/javascript" src="js/reportDownload.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js"></script>

	<script type="text/javascript">
		
	</script>
</body>
</html>