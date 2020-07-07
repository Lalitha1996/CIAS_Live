<title>Download ReportPage</title>
<%@ page import = "java.util.*, javax.servlet.*" %>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<link rel="stylesheet" href="css/style.css"> 
<style type="text/css">
.refbtn {
	width: 100px;
	margin-top: 32px
}

.downloadbtn {
    margin-left: -50px;
    margin-top: 16px;
}

.delbtn {
	width: 100%;
	 padding: 6px
}
.btn-success:hover {
	background-color: #339bb7 !important;
	border-color: #339bb7 !important;
}
</style>
</div>
<div class="downloadReport" style="overflow: hidden">
	<div class=" loan_label">
		<div class="nav-tabs">
			<h2 style="font-size: 28px; text-align: left; margin-left: 31px;">Download
				Reports Details</h2>
		</div>
		<div class="downloadForm">
			<form:form method="POST" action="downloadfile.html"
				modelAttribute="MADOWNREPORT" id="FORMDOWN">
				<form id="stackedForm">
					<div class="form-row" style="overflow: hidden;">
						<div class=" form-group col-md-4">
							<label class="new_for_label" for="stackedFirstName">
								Branch Code </label> <input type="text" style="width: 75%"
								class="form-control new_for_text" required="required"
								id="branCode" path="branCode"
								value="${MADOWNREPORT.getBranCode()}" name="branCode"
								readonly="readonly" />
						</div>
						<div class="form-group col-md-4">
							<label class="new_for_label" for="stackedLastName"> Date
							</label> <input type="text" style="width: 75%; font-size: 15px;"
								class="form-control datepicker new_for_text" id="datepicker-13"
								path="date" name="date" value="${MADOWNREPORT.getDate()}"
								placeholder="Date" />
						</div>
						<div class="col-md-4">
							<button type="button" class="btn btn-outline-primary"
								id="getReport" style="width: 100px; margin-top: 31px;">
								<span class="fa fa-list-alt"> </span> Transmit
							</button>
							<button type="button" id="btReload"
								class="btn btn-outline-info refbtn">
								<span class="fa fa-refresh "> </span> Refresh
							</button>
							<div id="clockdiv" style="margin-left: 60%; margin-top: -6%;"></div>
						</div>
					</div>
				</form>
			</form:form>
		</div>
	</div>
	<div id="fade"></div>
	<div id="modal"
		style="height: auto; width: auto; padding: 0; border-radius: 0;">
		<img id="loader" style="width: 150px;" src="images/Spinner.gif" />
	</div>

<div class="downloadFileTable">
	<div class="card mb-3" style="width: 96%; margin-left: 24px">
		<div class="card-body" style="margin-top: -10px;">
			<div class="loan_label">
				<table id=downfilestatustble align="center" class="table1 table-striped table-bordered" cellspacing="0" width="95%" style="width:100%;border: 1px solid">
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
</div>
<script>
	$(function() {
		$('.datepicker').datepicker();
	});
</script>
<script type="text/javascript" src="js/reportDownload.js"></script>

<div>
<%@ include file="/WEB-INF/pages/Footer.jsp"%></div>
