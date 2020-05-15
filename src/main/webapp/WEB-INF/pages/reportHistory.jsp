<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="header.jsp"%>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/reportHistory.css">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.css"
	rel="stylesheet">
<link
	href="https://cdn.datatables.net/1.10.19/css/dataTables.semanticui.min.css"
	rel="stylesheet">

<style type="text/css">
.btn-group button {
	background-color: #4CAF50; /* Green background */
	border: 1px solid green; /* Green border */
	color: white; /* White text */
	padding: 10px 24px; /* Some padding */
	cursor: pointer; /* Pointer/hand icon */
	width: 50%; /* Set a width if needed */
	display: block; /* Make the buttons appear below each other */
}

.btn-group










 










button




















:not










 










(
:last-child










 










)
{
border-bottom




















:










 










none




















; /* Prevent double borders */
}

/* Add a background color on hover */
.btn-group button:hover {
	background-color: #3e8e41;
}

.select2-results li {
	color: black;
	font-weight: 500;
	font-size: 12px;
}

table {
	table-layout: fixed;
}

.result {
	font-family: -webkit-body;
	margin-top: 0%;
	color: red;
	background: aliceblue;
}

#errmsg {
	color: red;
}

#serverValidation {
	color: red;
	margin-top: 48px;
}

#message {
	margin-left: 85px;
	color: red;
}

.table_select_error {
	border-color: red;
}

.acc_table {
	width: 100%;
	color: gainsboro;
	border: solid;
	margin-bottom: 7%;
}

.scrollit {
	overflow: scroll;
	height: 643px;
}

.acc_table thead {
	background: linear-gradient(to bottom, rgba(28, 90, 169, 1) 0%,
		rgba(20, 113, 187, 1) 100%);
	color: #fff;
}

.acc_table td {
	text-align: center;
	padding: 1px;
	border: 1px solid #ccc;
	background-color: #f1f1f100;;
}

.acc_table td input {
	float: none;
	display: inline;
	width: 100%
}

#drag_message {
	color: red;
}

.iptext_message {
	color: red !important;
}

ul>li, ol>li {
	line-height: 1.4;
	padding-left: 0;
	border-bottom: 1px solid #ccc;
}

.btn {
	text-align: left;
}

.table_field a {
	margin-right: 5px;
	margin-bottom: 5px;
}
/* .btn-success:focus{background-color: #E94057;border-color: #E94057;} */
.btn-success:hover {
	background-color: #339bb7 !important;
	border-color: #339bb7 !important;
}

.scrollable {
	overflow-y: auto;
	-webkit-overflow-scrolling: touch;
	padding-top: 0px;
}

.header-color {
	line-height: 10px !important;
}

.select2-container .select2-choice {
	padding: 0 0 0 5px;
	background: none;
	border: none;
}

tbody {
	height: 100px;
	overflow-y: auto;
	overflow-x: hidden;
}

.select2-container .select2-choice .select2-arrow {
	background: none;
	border: 0;
	border-radius: 0;
}

.select2-results .select2-highlighted .select2-result-label {
	color: #fff !important;
}
</style>
</head>
<body>
	<div id="main">
		<!-- <div class="header-color"><span style="font-size:25px;cursor:pointer" onclick="openNav()">&#9776;</span></div> -->
		<div>
			<div class="report_bg_patch scrollit">
				<div class="row">
					<h1 style="padding-left: 43%;
    margin-bottom: -4%">Report History</h1>
				</div>
				<div class="row" style="padding-top: 9%; padding-left: 22%;">
					<div class="col-2"></div>
					<div class="col-4">
						<div id="calendarContainer"></div>
					</div>
					<div class="col-4">
						<div id="organizerContainer" style="margin-left: 8px;"></div>
					</div>
				</div>
				<%-- <div class="row" style="padding-top: 9%; padding-left: 22%;">
					<table id="id">
						<tr>
							<th>ID</th>
							<th>File</th>
							<th>Start</th>
							<th>END</th>

						</tr>
						<c:forEach items="${data}" var="theme" varStatus="status">
							<tr>
								<td>${theme.id}</td>
								<td style="align: center">${theme.filename}</td>
								<td>${theme.startTime}</td>
								<td>${theme.endTime}</td>
							</tr>
						</c:forEach> 
					</table>
				</div> --%>
				<div></div>
			</div>
		</div>
	</div>


	<div id="fade"></div>
	<div id="modal"
		style="height: auto; width: auto; padding: 0; border-radius: 0;">
		<img id="loader" style="width: 150px;" src="images/loading5.gif" />
	</div>


	<div style="margin-top: -3%;">
		<%@ include file="/WEB-INF/pages/Footer.jsp"%>
	</div>
	<!-- Full Height Modal Right-------------------------------------->
	<div class="modal fade right" id="fullHeightModalRight" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

		<!-- Add class .modal-full-height and then add class .modal-right (or other classes from list above) to set a position to the modal -->
		<div class="modal-dialog-lg modal-full-height modal-right" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title w-100" id="myModalLabel">Modal title</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body"><div class="row" style="padding-top: 1%;padding-left: 15%;padding-right: 15%;">
					<table class="table1" id=downfilestatustble align="center">
						<thead align="center">
							<tr>
								<th>Queue Id</th>
								<th>File Name</th>
								<th>Start Date</th>
								<th>End Date</th>
							</tr>
						</thead>
					</table>
				</div></div>
				<div class="modal-footer justify-content-center">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<!-------------------------------- Full Height Modal Right------------------------ -->
	
	
	<input type="hidden" id="jsonBom" value='${data}' />

	<script>
		var List = $('#jsonBom').val();
	</script>

	<script type="text/javascript" src="js/reportHistory.js"></script>
	<script type="text/javascript" src="js/jquery.validate.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.3.1/semantic.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.19/js/dataTables.semanticui.min.js"></script>

</body>
</html>