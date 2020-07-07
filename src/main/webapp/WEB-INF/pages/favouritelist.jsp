<title>Favourite List</title>
<%@ include file="header.jsp" %>

<link href="css/style.css" rel="stylesheet" type="text/css"/> 
<style type="text/css">
 .hidebar{
display:none;
}
.container-fluid{
margin-top:0% !important;
}
.item {
    height: 133px;
    width: 150px;
    margin: 0 73px 50px 0;
    border-radius: 5px;
    float: left;
}
.dataTables_wrapper {
    position: relative;
    clear: both;
    margin-top: -67px;
    
}
#getreportdetails1 {
    height: 107px;
    border: 1px solid #ccc;
    border-radius: 5px;
    }
#myTab {
     padding-left: 535px;
     margin-bottom: 0px;
    margin-top: -42px !important;
    list-style: none !important;
}
.report_bg_patch {
    padding-top: 10px !important;
    border-radius: 0px;
    background: #fff;
    width: 100% !important;
    border-width: 0px !important;
    border-color: #0000001c;
    border-style: solid;
    margin: 0 auto;
    overflow: auto; 
    /* height: 649px; */
    position: relative;
    z-index: 0 !important;
}
.list-item-label {
    display: block;
    overflow: hidden;
    text-overflow: ellipsis;
    border: 1px solid transparent;
}
.checkbox label, .radio label {
    min-height: 20px;
    padding-left: 20px !important;
    margin-bottom: 0;
    font-weight: 400;
    cursor: pointer;
}
.list-item-label {
    display: block;
    overflow: hidden;
    text-overflow: ellipsis;
    border: 1px solid transparent;
}
.modal-dialog2 {
    width: 600px;
    margin: 140px auto;
}
.alert{
width: 300px;
color: green;
margin-left: 60px;
position:fixed;
top: 100px; 
right: 30px; 
z-index:9999;
}
</style>
</head>
<body>
   
	<!-- <div class="report_bg_patch"> -->
	<div id="userFav">
		<h2 style="color: black; font-size: 28px; text-align: left; margin-top: 0px">
			<i class="fa fa-star" style="margin-left: 15px;"></i>&nbsp;&nbsp;User Favourite List
		</h2>
		<div>
			<ul class="nav nav-tabs" id="myTab" role="tablist">
				<li title="Grid view" class="active"><a data-toggle="tab"
					role="tab" href="#info1" class="btn btn-default tab-unselectable"
					style="background-color: rgba(255, 255, 255, 0.9); min-width: 0px"><img
						src="images/gridview.jfif" style="width: 30px; padding: 2px;" /></a></li>
				<li class="infolist" title="List view"><a data-toggle="tab"
					role="tab" href="#branchinfo"
					class="btn btn-default tab-unselectable"
					style="background-color: rgba(255, 255, 255, 0.9); min-width: 0px"><img
						src="images/listview.jfif" style="width: 30px; padding: 2px;" /></a></li>
				<li title="delete views" style="padding-left: 195px"><button
						type="button" id="deleteAcc">
						<img src="images/deleteicon.png"
							style="width: 24px; padding: 2px;" />
					</button></li>
				<li><input type="text" id="myInput" placeholder="Search.." autocomplete="off"
					style="float: right; width: 267px; padding-top: 1px; margin-bottom: 6px;margin-left: 140px;" />
				</li>
			</ul>
		</div>
	</div>
	<!-- </div> -->
	<div class="tab-content" role="tab-content" style="margin-bottom: 42px;">
		<!-- first tab content -->
		<div id="branchinfo" style="" class="tab-pane fade">
			<!-- <h2 style="color: black; font-size: 28px; text-align: left;">
					<i class="glyphicon glyphicon-star-empty"></i>&nbsp;&nbsp;User Favourite List
				</h2> -->
			<div class="scrollable1">
				<div class="report_bg_patch" style="height:450px">
					<!-- <div class="tablebg"> -->
					<table class="table" style="color: #000; width: 405px" id="example1">
						<thead>
							<tr>
								<th style="color: white; font-size: 11px; background-color: gray">Select</th>
								<th style="color: white; font-size: 11px; background-color: gray">Title</th>
								<th style="color: white; font-size: 11px; background-color: gray">Purpose</th>
								<th style="color: white; font-size: 11px; background-color: gray">Description</th>
								<th style="color: white; font-size: 11px; background-color: gray">Table1</th>
								<th style="color: white; font-size: 11px; background-color: gray">Table2</th>
								<th style="color: white; font-size: 11px; background-color: gray">Selected Columns</th>
								<th style="color: white; font-size: 11px; background-color: gray">Filter</th>
								<th style="color: white; font-size: 11px; background-color: gray">JoinFilter</th>
								<th style="color: white; font-size: 11px; background-color: gray">Date</th>
								<th style="color: white; font-size: 11px; background-color: gray">Action</th>
							</tr>
						</thead>
						<tbody class="myTable">
							<c:forEach items="${favouriteLists}" var="tables">
								<tr>
									<td><input type="checkbox" class="checkid" id ="checkid1" name='titleval' value="${tables.id}"></td>
									<td>${tables.qryTitle}</td>
									<td>${tables.purpose}</td>
									<td>${tables.description}</td>
									<td>${tables.table1}</td>
									<td>${tables.table2}</td>
									<td class="parameter">${tables.parameter}</td>
									<td>${tables.query}</td>
									<td>${tables.joinFilter}</td>
									<td>${tables.currentDate}</td>
									<td style="cursor: pointer">
										<form id="reportdetailsForm" style="padding: 0px" method="post" action="${pageContext.request.contextPath}/getReportDetails">
											<input type="hidden" name="id" value="${tables.id}">
											<button class="getreportdetails" id="getreportdetails" style="width: 82px; padding: 0; font-size: 15px;" class="btn btn-success report-btn" type="submit">
												<span class="fa fa-edit"></span>&nbsp;Edit
											</button>
										</form>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<h2 style="color: white; font-size: 28px; text-align: center;">
				<b><c:out value='${nocontent}' /></b>
			</h2>
		</div>

		<div id="info1" class="tab-pane fade in active" style="margin-left: -27px;">
			<c:forEach items="${favouriteLists}" var="tablee">
				<div class="item tab-unselectable" style="width: 50px; height: 112px;margin: 5px 71px 38px 0;">
					<table border="0px" class="table" id="example1">
						<tbody class="myTable">
							<tr>
								<td class="hide">${tablee.qryTitle}</td>
								<td class="hide">${tablee.purpose}</td>
								<td class="hide">${tablee.description}</td>
								<td class="hide">${tablee.table1}</td>
								<td class="hide">${tablee.table2}</td>
								<td class="parameter" style="display: none">${tablee.parameter}</td>
								<td class="hide">${tablee.query}</td>
								<td>${tables.joinFilter}</td>
								<td class="hide">${tablee.currentDate}</td>
								<td style="padding-top: 0.5rem; padding-bottom: 1px; padding: 0px !important">
									<form id="reportdetailsForm" style="padding: 0px;z-index:0" method="post" action="${pageContext.request.contextPath}/getReportDetails">
										<input type="hidden" name="id" value="${tablee.id}">
										<div>
											<button class="getreportdetails" id="getreportdetails1" style="width: 107px; padding: 0; font-size: 15px;" class="btn btn-success report-btn" type="submit">
												<img src="images/editimage1.jfif" style="width: 78px" />
											</button>
											<div class="item-footer tab-unselectable" style="margin-top: 8px">
												<label class="list-item-label"><input type="checkbox" value="${tablee.id}" name="titleval" class="checkid1" id="checkid2">${tablee.qryTitle}</label>
											</div>
										</div>
									</form>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br><br>
	
	 <div class="alert alert-success" id ="delete1" style="float:right;">
			<p style="color:green">
	<c:out value="${delete_msg}"/>
	</p>
	</div>
	
	<%@ include file="/WEB-INF/pages/Footer.jsp"%>
    <script type="text/javascript" src="js/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="js/favouritelist.js"></script>
	<script type="text/javascript" src="js/datatables.min.js"></script>
	<script type="text/javascript">
	var selected = new Array();
		 $(document).ready(function() {
			$('.getreportdetails').click(function(e) {
				var parameter=$(this).closest('.table tr').children('td.parameter').text();
				localStorage.setItem("parameter", parameter);
				$('#reportdetailsForm').submit();
			});
		}); 
		 
		$(document).ready(function() {
			 $('.getreportdetails').click(function(e) {
				var parameter=$(this).closest('.table tr').children('td.parameter').text();
				localStorage.setItem("parameter", parameter);
				$('#reportdetailsForm1').submit();
			});
		}); 
		
		$('.table1').on('click', '.btn-details', function(e) {
			modifyPopup(e, this);
		});
		$(document).ready(function() {
			$("#example1").DataTable({"sDom":"ltipr"});
		});
		
		 $(document).ready(function(){
		  $("#myInput").on("keyup", function() {
		    var value = $(this).val().toLowerCase();
		    $(".myTable tr").filter(function() {
		      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		    });
		  });
		}); 
 $("#deleteAcc").click(function(e){
		var titlee = "";
		var yes =false;
			$("input[name='titleval']:checkbox").each(function() {
				if ($(this).is(":checked")) {
					yes =true;
					$('#id02').show();
					titlee += "'" + $(this).val() + "'" + ",";
				}
			});
			titlee = titlee.substring(1, titlee.length - 2);
			$("#qtitlee").val(titlee);
			if (yes==false){
				alert("please select the check box...!!!");
			}
		});
	setTimeout(function(){
	 $('#delete1').fadeOut("slow");}, 500);
	</script>

	<%-- <div id="id02" class="modal2" style="z-index: 2;">
		<!-- //<div class="modal fade" role="dialog"> -->
		<div class="modal-content3 animate" style="width: 30%;">
			<div class="modal-header">
				<h4 class="modal-title">
					<span style="font-weight: 500; font-size: 17px;">Do you want to delete?</span>
				</h4>
			</div>
			<br>
			<div>
				<form:form method="post" action="deleteview" commandName="checkedhisto">
					<form:hidden path="parameter" id="qtitlee" value="" />
					<input type="submit" class="btn btn-danger btn-sm" id="deletebtn1" value="Delete" />&nbsp;&nbsp;&nbsp;
					<input type="button" id="btn1" onclick="document.getElementById('id02').style.display='none'" class="btn btn-danger btn-sm" data-dismiss="modal" value="No" />
				</form:form>
			</div>
		</div>
	</div>
	======
	 --%>
	<div class="modal fade animate" id="id02" role="dialog">
		<div class="modal-dialog" id="mydrag">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header" style="background-color:#000000b5;padding: 14px 3px 3px 8px;">
					<h4 class="modal-title bold" style="color:#fff;margin-top:-12px;"> Delete </h4>
					<button type="button" class="close" data-dismiss="modal" onclick="document.getElementById('id02').style.display='none'"style="font-size: 26px;opacity:1;margin-top:-1.2%;color: #fdfdfdfa;">&times;</button>
				</div>
				<div class="modal-body" style="height: 40px;">
					<p style="font-size:20px;">Do you want to delete?</p>
				</div>
				<div class="modal-footer" style="height: 60px;">
					<form:form method="post" action="deleteview" commandName="checkedhisto" style="margin-top:-25px">
					<form:hidden path="parameter" id="qtitlee" value="" />
					<input type="submit" class="btn btn-danger btn-sm" id="deletebtn1" value="Delete" style="width: 24%;height: 100%;margin-left: 5px;" />&nbsp;&nbsp;&nbsp;
					<input type="button" id="btn1" onclick="document.getElementById('id02').style.display='none'" class="btn btn-danger btn-sm" data-dismiss="modal" value="No" style="width: 25%;height: 100%;margin-right: 0px;"/>
				</form:form>
			   </div>
			</div>

		</div>
	</div>
	
	
	