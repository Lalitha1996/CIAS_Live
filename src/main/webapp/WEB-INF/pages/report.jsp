<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import = "java.util.*, javax.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="header.jsp"%>
<link rel="stylesheet" href="css/style.css"> 
<style type="text/css">
.select2-results li {
color:black;
font-weight: 500;
font-size: 12px;
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
	font-size:medium;
	font-family:Georgia;
}
#message1 {
	margin-left: 300px;
	color: red;
	font-size:medium;
	font-family:Georgia;
}

.table_select_error {
	border-color: red;
}

#drag_message {
	color: red;
}

#joinTextArea_message {
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

.header-color {
	line-height: 10px !important;
}

.select2-container .select2-choice {
	padding: 0 0 0 5px;
	background: none;
	border: none;
}

.select2-container .select2-choice .select2-arrow {
	background: none;
	border: 0;
	border-radius: 0;
}

.select2-results .select2-highlighted .select2-result-label {
	color: #fff !important;
}
.button:hover{
border-color:#DCDCDC rgb(220,220,220);
}
.button{
    border-radius: 10px;
    font-weight: bold;
    color: white;
    border: 5px double white;
    background: #777;    
    outline:0;
    width: 250px;
    height: 50px;
}

.modal-open{
overflow: auto !important;
}
.border {
	border-radius: 20px;
	box-shadow: 0 0 8px rgba(0, 0, 0, 0.5);
	background: #fff;
	width:90%;
	border-width: 1px;
	border-color: #32a8a6;
	border-style: solid;
	margin:4px 0px 20px 27px;
	height: 350px; 
	position: relative;
}


/* Scroll top styles */

#back2Top {
    width: 40px;
    line-height: 40px;
    overflow: hidden;
    z-index: 999;
    display: none;
    cursor: pointer;
    -moz-transform: rotate(270deg);
    -webkit-transform: rotate(270deg);
    -o-transform: rotate(270deg);
    -ms-transform: rotate(270deg);
    transform: rotate(270deg);
    position: fixed;
    bottom: 50px;
    right: 0;
    background-color: #DDD;
    color: #42515b;
    text-align: center;
    font-size: 30px;
    text-decoration: none;
}
#back2Top:hover {
    background-color: #DDF;
    color: #000;
}
::placeholder {
  color: #2c2c2c87 !important;
  opacity: 1;
}
.input-group-addondate {
    padding: 4px 7px;
    font-size: 14px;
    font-weight: 400;
    line-height: 1;
    color: #555;
    text-align: center;
    background-color: #eee;
    border: 1px solid #ccc;
    border-radius: 4px;
}
.leftjoin,.rightjoin,.fulljoin {
display: none;
}
#dwld:hover {
    background-color: #F60 !important;
    /* color:black !important; */
}




</style>
</head>
<body>
	<div id="mySidenav" class="sidenav" style="border-right: 1px solid #ccc;">
		<input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for names.." title="Type in a name"> <a href="javascript:void(0)" class="closebtn" onclick="closeNav()"
			style="line-height: 1;">&times;</a>
	 <nav id="sidebar">
		<div class="dropdown" id = "table_dropdown" >
			 <input type="button" class="button" id ="table_btn" class="btn btn-default dropdown-toggle" type="button" data-toggle="toggle" value="">
			<ul id="columns" style="display:none" class="list-unstyled components"></ul>
		</div>
		<div class="dropdown" id = "table_dropdown" >
			 <input type="submit" class="button" id ="table_btn_join1" class="btn btn-default dropdown-toggle" type="button" data-toggle="toggle" value="">
			<ul id="columnsJoin1" style="display:none" class="list-unstyled components"></ul>
		</div>
	 <div class="dropdown" id = "table_dropdown" >
			 <input type="submit" class="button" id ="table_btn_join2" class="btn btn-default dropdown-toggle" type="button" data-toggle="toggle" value="">
			<ul id="columnsJoin2" style="display:none" class="list-unstyled components"></ul>
		</div>
		<div class="dropdown" id = "table_dropdown" >
			 <input type="submit" class="button" id ="table_btn_join3" class="btn btn-default dropdown-toggle" type="button" data-toggle="toggle" value="">
			<ul id="columnsJoin3" style="display:none" class="list-unstyled components"></ul>
		</div>
		<div class="dropdown" id = "table_dropdown" >
			 <input type="submit" class="button" id ="table_btn_join4" class="btn btn-default dropdown-toggle" type="button" data-toggle="toggle" value="">
			<ul id="columnsJoin4" style="display:none" class="list-unstyled components"></ul>
		</div>
		<div class="dropdown" id = "table_dropdown" >
			 <input type="submit" class="button" id ="table_btn_join5" class="btn btn-default dropdown-toggle" type="button" data-toggle="toggle" value="">
			<ul id="columnsJoin5" style="display:none" class="list-unstyled components"></ul>
		</div> 
	  </nav>
	</div>

	<div id="main">
		<!-- <div class="header-color"><span style="font-size:25px;cursor:pointer" onclick="openNav()">&#9776;</span></div> -->
		<div class="scrollable">
			
				<div class="row">
					<div class="col-md-12">
						<div class="col-md-12 no-padding">
							<label class="pull-left fs-14" style="margin: 5px 15px 0 0;">Table Name</label> 
							<select id="table_select" data-init-plugin="select2" class="form_input pull-left half_width makingnull" style="width: 25%; cursor: pointer; margin-right: 15px; height: 28px;">
								 <c:forEach items="${tables}" var="table">
									<option value="<c:out value="${table.tableName}"></c:out>"><c:out value="${table.name}"></c:out></option>
								</c:forEach>
								<option value="<c:out value="${favouriteLists.table1}"></c:out>" selected="selected"><c:out value="${favouriteLists.table1}"></c:out></option>
							</select>
							<label style="float: left;">OnDate: 
         				  <input placeholder="dd-mm-yyyy"  name="ondate" type="text" class="form_input half_width" id ="ondate" style="width:90px;" autocomplete="off" value="${favouriteLists.ondate}"/><span class="input-group-addondate"><span class="fa fa-calendar"></span></span></label>&nbsp;&nbsp;
							<button id="tables" class="btn btn-outline-info">
								<i class="fa fa-file-text"></i>&nbsp;Get Tables
							</button>
							<button id="jointables" class="btn btn-outline-success" data-toggle="collapse">
                                <i class="fa fa-random"></i>&nbsp;&nbsp;Join Tables
                            </button>
							<button style="display: none;" id="" class="btn btn-outline-success favourite" data-toggle="modal" data-target="#saveFavourite">
								<span class="fa fa-file-text"></span>&nbsp;Save
							</button>

							<button id="clear_btn_all" class="btn btn-outline-warning">
								<i class="fa fa-remove"></i>&nbsp;Clear All
         				    </button>&nbsp;&nbsp;
         				 
         				<%--  <!-- Queue download page Button -->
						 <a href="${pageContext.request.contextPath}/downloadreportPage"><button id="dwld" style="color:white;background-color:#666;margin-top:3px;" class="btn btn-success">
						 <i class="	glyphicon glyphicon-save-file"></i>&nbsp;Go to Download
						 </button>
						 </a> --%>
         				  
                          <a class="file_action" href="#"> <input style="float: left; margin: 4px 4px 0 0; display:none" id="groupby" type="checkbox"><span><label style="cursor: pointer; display:none">Groups by</label></span></a>
							<div class="clearfix"></div>
							<span id="message"></span>
							<span id="message1"></span>
							
							<!-- Hello -->
							<!-- ############### changes to get side by side of two different divs ############### -->
							
							 <div id="multiJoinTablediv" class="JoinTable" style="display:none;">
							<label class="pull-left fs-14" style="margin: 5px 8px 0 0;">Join Table To:</label> <br>
							<div id="jointablediv"  style="margin-top:5px; float:left; width: 360px; height: 110px;overflow-y: auto;">
                            <ul>
							<c:forEach items="${tables}" var="tableJoin">
								 <li>
									<input type="checkbox" value="${tableJoin.tableName}" name="joincheck" id="checkjoin" class="checkjoincls" style="float: center">&nbsp;
									<label for="joincheck"><c:out value="${tableJoin.name}"></c:out></label>
									<br>
								</li>
							</c:forEach>
							
							  <c:forTokens var="table" items="${favouriteLists.table2}" delims=",">
                                 <li>
									<input type="checkbox" value="${table}" name="joincheck" id="checkjoin" class="checkjoincls" style="float: center" checked="true">&nbsp;
									<label for="joincheck"><c:out value="${table}"></c:out></label>
									<br>
								</li>
							 </c:forTokens>
							</ul>
                            </div> 
                          
		            
						<!-- Button trigger modal --><!-- data-target="#exampleModal" this removed from button -->
						
						<div class="" id ="joindivArea" style="float:left;  margin-top: -16px; margin-left: 24px;">
						<textarea disabled rows="3" cols="132" style="width:500px; margin-top: 20px;" class="" id="joinTextArea"><c:out value="${favouriteLists.joinFilter}"></c:out></textarea>
						<span id="joinTextArea_message"></span><br><br>
					   <!-- click joinBtn to show popup-->
						<div id="jf" style="">
						<button type="button" class="btn btn-outline-primary report-btn" data-toggle="modal"  id="joinBtn" ><i class="fa fa-filter"></i>&nbsp;Join Filter</button>
						<button class="joinClear btn btn-outline-warning report-btn"><i class="fa fa-remove"></i>&nbsp;Clear</button>
						</div>
					    </div>
				 </div> 
				 </div>
                <span id="join_message" style="color:red;font-size:medium;font-family:Georgia;"></span> <!-- join table select alert -->

					<!-- Modal popup for JOIN -->
					<div class="modal animate" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" style="overflow-x: scroll">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title bold" id="exampleModalLabel" style="margin-top :-15px;"><i class="fa fa-filter"></i>Join Filter</h5>
									<button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="$('#exampleModal').hide()" >
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="border">
								 <div class="modal-body">
											<div class="col-xs-4" id="join">
											<!-- <select id ="logicalC" class="form_input pull-left half_width" style="width:52%;margin-top: -9px;">
											<option value="AND">AND</option>
											<option value="OR">OR</option>
											</select> -->	
										    <label class="pull-left fs-14" style="margin: 15px 15px 0 0;" id="pt1">All Table Keys</label><br> 
											<select class="form-control" id="pri">
													<option value=""></option>
											</select><br>
										    <select id ="logicalA" class="form_input pull-left half_width" style="width:52%;margin-top: -13px;">
											<option value="AND">AND</option>
											<option value="OR">OR</option>
											</select>
											
											<label class="pull-left fs-14" style="margin: 2px 15px 0 0;" id="pt2">All Table Keys</label><br> 
											<select class="form-control" id="pri2">
													<option value=""></option>
											</select><br>
											<!-- <select id ="logicalB" class="form_input pull-left half_width" style="width:52%;margin-top: -10px;">
											<option value="AND">AND</option>
											<option value="OR">OR</option>
											</select><br> -->
											
											<!-- <label class="pull-left fs-14" style="margin: -6px 15px 0 0;" id="st1">SFieldName</label><br> 
											<select class="form-control" id="sec2" style="margin-top: -10px;">
													<option value=""></option>
										    </select> -->
											
											</div>
											<div class="col-xs-4">
											<label class="pull-left fs-14" style="margin: 15px 15px 0 0;">Condition</label><br> 
										    <select class="form-control" id="con1">
													<option value="=">=</option>
											</select><br>
											<label class="pull-left fs-14" style="margin: 15px 15px 0 0;">Condition</label><br>
											<select class="form-control" id="conP" >
													<option value="=">=</option>
													<option value=">">></option>
													<option value="<"><</option>
													<option value=">=">>=</option>
													<option value="<="><=</option>
											</select><br> 
											
											<%-- <label class="pull-left fs-14" style="margin: 15px 15px 0 0;">Condition</label><br>
											<select class="form-control" id="conS">
													<option value="=">=</option>
													<option value=">">></option>
													<option value="<"><</option>
													<option value=">=">>=</option>
													<option value="<="><=</option>
											</select><br> --%>
											</div>
											<div class="col-xs-4">
											<label class="pull-left fs-14" style="margin: 15px 15px 0 0;" id="st2">Join Table Keys</label><br> 
											<select class="form-control" id="sec">
													<option value=""></option>
										    </select><br>
										    <label class="pull-left fs-14" style="margin: 15px 15px 0 0;">Value</label><br> 
										    <input type="text" class="form-control" id="TextP" ><br>
										    <!-- <label class="pull-left fs-14" style="margin: 15px 15px 0 0;">Value</label><br> 
										    <input type="text" class="form-control" id="TextS"> -->
											</div>
								</div><br><br>
								<div class="modal-footer" style="margin-top:-15px;">
							    <label class="pull-left bold fs-14" style="margin: 2px 15px 0 0;">Join Type:</label>
								<select id ="joinType" class="form_input pull-left half_width" style="width:30%;margin-top:0px;font-size:small">
											<option value="JOIN" title="Select records that contains matching values in both tables.">SIMPLE JOIN</option>
											<option value="LEFT JOIN" title="Select records from the first (left-most) table with matching right table records.">LEFT JOIN</option>
											<option value="RIGHT JOIN" title="Select records from the second (right-most) table with matching left table records.">RIGHT JOIN</option>
											<option value="FULL JOIN" title="Selects all records that match either left or right table records.">FULL JOIN</option>
								</select>
									<button type="button" class="btn btn-primary" id="addJoin">Ok</button>
									<button type="button" class="btn btn-secondary" data-dismiss="modal" style="background-color:#D3D3D3" onclick="$('#exampleModal').hide()">Close</button>
								</div>
								<div class="simplejoin">
								<img style="width: 50px;" src="images/img_innerjoin.gif" align="left"/><p style="color:#3300ff">SIMPLE JOIN : Select records that contains matching values in both tables.</p>
								</div>
								<div class="leftjoin" >
								 <img style="width: 50px;" src="images/img_leftjoin.gif" align="left"/><p style="color:#3300ff">LEFT JOIN : Select records from the first (left-most) table with matching right table records.</p>
								</div>
								<div class="rightjoin">
								 <img style="width: 50px;" src="images/img_rightjoin.gif" align="left"/><p style="color:#3300ff">RIGHT JOIN : Select records from the second (right-most) table with matching left table records.</p>
								</div>
								<div class="fulljoin">
								 <img style="width: 50px;" src="images/img_fulljoin.gif" align="left"/><p style="color:#3300ff">FULL JOIN : Selects all records that match either left or right table records.</p>
								</div>
								</div>
							</div>
						</div>
					</div>

					<div class="clearfix"></div>
						<div style="display: none;" id='table_field' class="drag_drop_div">
							<label class="field_head">Select Reports Fields</label>&nbsp;&nbsp;<span class="fs-12">(Please click on field)</span>
							<div class="clearfix"></div>
							<div class="hide col-md-6 p-r-5 p-l-0" id="div1" ondrop="drop(event)" ondragover="allowDrop(event)">
								<div class="table_field b-grey" id="drop"></div>
							</div>
							<div class="col-md-12 p-l-5 p-r-0" id="div2" ondrop="drop(event)" ondragover="allowDrop(event)">
								<div class="table_field b-grey" id="drag">
									<%-- <c:out value="${favouriteLists.parameter}"></c:out> --%>
								</div>
								<span id="drag_message"></span>
						   </div>
						</div>
					</div>
				</div>
				<div class="row" style="margin: 10px 0;">
				<%-- 	<div class="col-md-12 font-icon">
						<div class="pull-right">
							<a class="file_action" href="#"><i class="fa fa-edit"></i><span><label>Graphs</label></span></a> <a class="file_action" href="${pageContext.request.contextPath}/showFavouriteList"><i
								class="glyphicon glyphicon-star-empty"></i><span><label style="cursor: pointer">Favourite List</label></span></a> <a class="file_action" href="#"> <!-- <i id="groupby" class="fa fa-edit"> </i>-->
								<input style="float: left; margin: 4px 4px 0 0;" id="groupby" type="checkbox"><span><label>Groups by</label></span></a>
						</div>
					</div> --%>
				</div>
				<div class="row">
					<div class="col-md-12">
						<!-- <div class="b-b b-r b-l b-t b-grey loan_label" style="padding: 5px 10px 40px; background: #fff; box-shadow: 0 0 8px rgba(0, 0, 0, 0.5);"> -->
							<p class="field_head">Table Criteria</p>
							<div class="m-t-10">
								<div class="filter_row1">
									<label class="">Filter</label> 
									<label class="" style="margin: -21px 0px 0px 0px;"><font color="blue">(P)</font></label>
									<select class="form_input m-r-5" id="s1" style="width: 15.7%; border-radius: 0; padding: 4px;">
					                <option value=""></option>
							        </select>
							        <label class="" id="filterJoinLabel" style="margin: -21px 0px 0px 0px; display:none;"><font color="blue">(S)</font></label>
							        <select class="form_input m-r-5" id="sj" style="width: 15.7%; border-radius: 0; padding: 4px; display:none;">
					                <option value=""></option>
							        </select>
							 <select class="form_input m-r-5" id="s2" style="width: 7%; border-radius: 0; padding: 4px; "></select> 
							 <input type="text" id="iptext" class="form_input m-r-5" onkeypress="return blockSpecialChar(event)" style="width: 23.3%; display: block" />
							 <div id="iptextdateDiv" style="display: none; padding-left: 0;" class='col-md-2'>
										<div class="form-group">
											<div class='input-group date'>
												<input type='text' id="iptextdate" class="form_input_date m-r-5" placeholder=" Select date" /> <span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
												</span>
											</div>
										</div>
									</div>

									<div id="fromToDiv" style="display: none">
										<div class='col-md-2' style="padding-left: 0; padding-right: 5px;">
											<div class="form-group">
												<div class='input-group date'>
													<input type='text' id="fromdate" class="form_input_date m-r-5" placeholder=" Start date" /> <span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
													</span>
												</div>
											</div>
										</div>
										<div class='col-md-2' style="padding-right: 5px; padding-left: 0;">
											<div class="form-group">
												<div class='input-group date'>
													<input type='text' id="todate" class="form_input_date m-r-5" placeholder=" End date" /> <span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
													</span>
												</div>
											</div>
										</div>
									</div>

									<div id="dateDiv" style="display: none">
										<div class='col-md-2'>
											<div class="form-group">
												<div class='input-group date' id='datetimepicker6'>
													<input type='text' id="iptextdate" class="form_input_date m-r-5" /> <span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
													</span>
												</div>
											</div>
										</div>
									</div>

									<div id="betweenDiv" style="display: none">
										<input type="text" id="from" class="form_input m-r-5" onkeypress="return blockSpecialChar(event)" style="width: 15.3%;" /> <label class="bold">To</label> <input type="text" id="to"
											class="form_input m-r-5" onkeypress="return blockSpecialChar(event)" style="width: 15.3%;" />
									</div>


									<button class="btn btn-outline-info addto">
										<i class="fa fa-filter"></i>&nbsp;Add to Filter
									</button>
									<button class="btn btn-outline-info orto">
										<i class="fa fa-filter"></i>&nbsp;Or to Filter
									</button>


									<span id="errmsg"></span><br>

									<!-- <div id="serverValidation" style="display: none" class="alert alert-danger"></div> -->

									<div class="" style="margin-top:10px;color: red;font-size:medium;font-family:Georgia;" >
										<span>Note:</span><span>1.Red color fields are mandatory </span>
									</div>
								</div>

								<div class="">
									<textarea disabled rows="5" cols="132" style="width: 775px; margin-top: 20px;" class="" id="criteriaTextArea"><c:out value="${favouriteLists.query}"></c:out></textarea>
								</div>
								<div>
									<br>
									 
									<button class="btn btn-outline-success submitCf"  data-toggle="modal" data-target="#myModal" id="btn_transmit">
										<i class="fa fa-share-alt"></i>&nbsp;Transmit
									</button>
									<!-- btn-primary new_btn_style  -->
									<button class="submitClear btn btn-outline-warning">
										<i class="fa fa-remove"></i>&nbsp;Clear
									</button>&nbsp;&nbsp;&nbsp;
									  <!-- is Required Criteria..!!! -->
									 <span style="text-align:right; color:red;font-size:medium;font-family:Georgia;" id="Cmsg1"></span>
									 <span style="text-align:right; color:green;font-size:medium;font-family:Georgia;"><b id="Cmsg2"></b></span>
									 <span style="text-align:right; color:#3b4752;font-size:medium;font-family:Georgia;" id="Cmsg3"></span>
									
									<p style="text-align:center; color: green">
										<b id="favouriteMsg"></b>
									</p>
									
									<div class="hide">
										<form id="pdfForm" method="post" action="${pageContext.request.contextPath}/createPdf">
											<input type="hidden" name="pdfJson" id="pdf" value="">
											<button class="pdfByItext" type="submit">PDF</button>
										</form>
								   </div>
									<div class="hide">
										<form id="excelForm" method="post" action="${pageContext.request.contextPath}/createExcel">
											<input type="hidden" name="excelJson" id="excel" value="">
											<button class="excel" type="submit">Excel</button>
										</form>
									</div>
									<%-- <div class="hide">
										<form id="csvForm" method="post" action="${pageContext.request.contextPath}/createCsv">
											<input type="hidden" name="csvJson" class="csv" value="">
											<button class="csv" type="submit">Excel</button>
										</form>
									</div> --%>
									<div class="hide">
										<form id="pipecsvForm" method="post" action="${pageContext.request.contextPath}/createPipeCsv">
											<input type="hidden" name="csvJson" class="csv" value="">
											<button class="csv" type="submit">Excel</button>
										</form>
									</div>
									<div id="serverValidation" style="display: none" class="alert alert-danger"></div>
								</div>
							</div>
						<!-- </div> -->
					</div>
					<div class="col-md-12 m-t-10" >
					<div id="queuedownloads" style="display:none">
					    <button  class="btn btn-outline-success" id="xls_btn">XLSX</button>
						<button  class="btn btn-outline-success" id="csvpipe_btn">CSVPIPE</button>
						<button  class="btn btn-outline-success" id="pdf_btn">PDF</button>
						<button class="btn btn-outline-success" id="csv_btn">CSV</button>
					</div>&nbsp;
					
						<div class="row b-b b-l b-r b-t b-grey" style="background: #fff;overflow: auto;">
							<div style="margin: 10px;">
								 <div id="menu4">
									<!-- Adding dynamic data table here -->
							    </div>
								<br>
								<div class="clearfix"></div>
								<div class="row text-center"></div>
								 <div id="chartContainer" style="height: 500px; width: 100%; display:none;"></div>
							
									<!-- pages-new.css 585 width from 100 to 25-->
									
									<div id="compareDateForm" style="display:none; border: 0.5px solid #0099cc5c;height:60px;padding:  15px 15px 15px 15px;">
									Date1: <input  name="Date1" id="date1" class="form_input_date m-r-5" style="width: 20.3%; !important"/> 
									Date2: <input name="Date2" id="date2" class="form_input_date m-r-5" style="width: 20.3%; !important"/>
									<button id="compareDate" type="submit" class="btn btn-success report-btn submit" style="background-color:green;color:white;"><i class="glyphicon glyphicon-share-alt"></i>&nbsp;Compare</button>				
							         </div>
							<br><br><br>
							
							</div>
					
					
						</div>
					</div>
				</div>


			<!-- </div> -->
		</div>
	
	<div id="fade"></div>
	<div id="modal" style="height: auto; width: auto; padding: 0; border-radius: 0;">
		<img id="loader" style="width: 150px;" src="images/Spinner.gif" />
	</div>

	<!-- -----------------------Add To Favourite popup-----------------------------------style="overflow: hidden" -->

	<!-- Modal -->
	<div class="modal animate" id="saveFavourite" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="overflow: hidden">
		<div class="modal-dialog">
			<div class="modal-content animate">
				<!-- Modal Header -->
				<div class="modal-header" style="background-color:#000000b5;padding: 12px 6px 3px 8px;">
					<button type="button" class="close" onclick="document.getElementById('saveFavourite').style.display='none'" style="font-size: 26px;opacity:1;margin-top:3%;color: #fdfdfdfa;">
						<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel" style="color: #fff;"><i class="fa fa-star"></i>&nbsp;Add Favourite Details</h4>
				</div>

				<!-- Modal Body -->
					<div class="modal-body" style="padding-left: 0%; height: 320px;">

						<form id="formdata" role="form">
							<div class="form-group ">
								<label for="qryTitle">Query Title</label> <input type="text" name="qryTitle" class="form-control " style="width: 340px; height: 35px;" id="qryTitle" placeholder="Enter query title"
									onkeypress="KeyPressQT()" /> <span id="qryTitle_message" style="color: red"></span>
							</div>
							<div class="form-group">
								<label for="purpose">Description</label>
								<textarea name="description" class="form-control" style="width: 340px; height: 50px;" id="description" placeholder="Enter description"></textarea>
							</div>
							<div class="form-group">
								<label for="purpose">Purpose</label>
								<textarea name="purpose" class="form-control" style="width: 340px; height: 50px;" id="purpose" placeholder="Enter purpose"></textarea>
							</div>
							<!-- Modal Footer -->
							<div class="modal-footer">
								<button id="favouriteForm" style="width: 80px;" type="button" class="btn btn-primary">Submit</button>
								<button type="button" style="width: 80px;background-color:#D3D3D3;" class="btn btn-secondary"  onclick="$('#saveFavourite').hide()">Cancel</button>
							</div>
						</form>
					</div>

					<!-- Modal Footer -->
				<!-- <div class="modal-footer">
					<button type="button" class="btn btn-success report-btn"  onclick="document.getElementById('saveFavourite').style.display='none'">Close</button>
				</div> -->
			</div>
		</div>

	</div>

	<%@ include file="/WEB-INF/pages/Footer.jsp"%>
	<!-- -------------------------------Add To Favourite popup End---------------------------- -->


	<script>
		function myFunction() {
			var input, filter, ul, li, a, i;
			input = document.getElementById("myInput");
			filter = input.value.toUpperCase();
			ul = document.getElementById("columns");
			li = ul.getElementsByTagName("li");
			for (i = 0; i < li.length; i++) {
				a = li[i].getElementsByTagName("a")[0];
				if (a && a.innerHTML.toUpperCase().indexOf(filter) > -1) {
					li[i].style.display = "";
				} else {
					li[i].style.display = "none";

				}
			}
			 
			for(var j=1;j<=5;j++){
			uljoin = document.getElementById("columnsJoin"+j);
			lijoin = uljoin.getElementsByTagName("li");
			
			for (i = 0; i < lijoin.length; i++) {
				a = lijoin[i].getElementsByTagName("a")[0];
				if (a && a.innerHTML.toUpperCase().indexOf(filter) > -1) {
					lijoin[i].style.display = "";
				} else {
					lijoin[i].style.display = "none";
				}
			  }
			}
		}

		function openNav() {
			document.getElementById("mySidenav").style.width = "250px";
			document.getElementById("main1").style.marginLeft = "250px";
			document.getElementById("main").style.marginLeft = "250px";
			//document.getElementById("main4").style.marginLeft = "250px";
		}

		function closeNav() {
			document.getElementById("mySidenav").style.width = "0";
			document.getElementById("main").style.marginLeft = "0";
			document.getElementById("main1").style.marginLeft = "0";
			//document.getElementById("main4").style.marginLeft = "0";
		}

		function allowDrop(ev) {
			ev.preventDefault();
		}
		function drag(ev) {
			ev.dataTransfer.setData("text", ev.target.id);
		}
		function drop(ev) {
			ev.preventDefault();
			var data = ev.dataTransfer.getData("text");
			var dragValue = document.getElementById(data).value;
			isCriteriaNumber(dragValue);
			ev.target.appendChild(document.getElementById(data));

		}
	</script>

	<script>
		$(window).load(function() {
			localStorage.removeItem("dbColumns");
			localStorage.removeItem("JoindbColumns");
			retriveColumns();
		    
			  //retriveJoinColumns();
			  //onDateSet();
		});

		$(document).ready(function() {
			$(function() {
				$("#date1").datepicker({
					dateFormat : 'yy-mm-dd'
				});
				$("#date2").datepicker({
					dateFormat : 'yy-mm-dd'
				});
				$("#table_select").select2();
				$("#fromdate").datepicker({
					dateFormat : 'yy-mm-dd'
				});
				
				$("#table_select2").select2();
				$("#fromdate").datepicker({
					dateFormat : 'yy-mm-dd'
				});
				$("#todate").datepicker({
					dateFormat : 'yy-mm-dd'
				});
				$("#iptextdate").datepicker({
					dateFormat : 'yy-mm-dd'
				});
				
				 $('#ondate').click(function(e) {
						$("#ondate").datepicker({
							dateFormat : 'dd-mm-yy'
						});
					});

			});
			
			// queue sending...
			$('#csv_btn').click(function(e) {
				createCsv();
			});

			$('#csvpipe_btn').click(function(e) {
				createCsvPipe();
			});

			$('#xls_btn').click(function(e) {
				createXls();
			});
			
			$('#pdf_btn').click(function(e) {
				createPdf();
			});
		

			$('#btn_transmit').click(function(e) {
				submitCf(e);

			});

			$('#compareDate').click(function(e) {
				compareData(e);
	        });
		
			
			$(".addto").on("click", function() {
				addToFilter();
			});

			$(".orto").on("click", function() {
				addORToFilter();
			});
			$('.submitClear').click(function(e) {
				document.getElementById("criteriaTextArea").innerHTML = "";
			});

			$('.joinClear').click(function(e) {
				document.getElementById("joinTextArea").innerHTML = "";
			});

			$('#s1').change(function(e) {
				validateSelectedValue();
			});
			$('#sj').change(function(e) {
				validateSelectedValue();
			});
			$('#sj').click(function(e) {
				if ($('#s1').val() != "") {
					alert("please select only one field at a time...!!!");
					return false;
				}
			});
			$('#s1').click(function(e) {
				if ($('#sj').val() != "") {
					alert("please select only one field at a time...!!!");
					return false;
				}
			});
			$('#tables').click(function(e) {
				callMetaDataTable();
			});

			$('#table_select').change(function(e) {
				retriveColumnAjaxCall();
			});

			$('#table_select2').change(function(e) {
				retriveJoinColumnAjaxCall();
			});

			$('#clear_btn_all').click(function(e) {
				clearAllFields();
			});
			$('#s2').click(function(e) {
				addBetweenFilter();
			});
			$('#groupby').click(function(e) {
				groupBy(e);
			});
			$('#favouriteForm').click(function(e) {
				saveFavouriteQuery(e);
			});
			$("#joinType").click(function(e){
				JoinDescription(e);
			});
			$('#jointables').click(function(e) {
                
				if ($("#ondate").val().trim()) {
					
					if ($("#table_select").val()) {
						$("#multiJoinTablediv").slideToggle();
						$("#filterJoinLabel").slideToggle();
						$("#sj").slideToggle();
					} else {
						alert("Primary Table is Required to Join..!!");
					}
				}
			else
				{
				alert("Please select ondate..!");
				}
				
				
			});

			$('#joinBtn').click(function(e) {

				  $("#join_message").html("");
				  $("#joinTextArea").removeClass("table_select_error");
				  
				 if ($("#table_btn_join1").val()!="" || $("#table_btn_join2").val()!=""  || $("#table_btn_join3").val()!=""  || $("#table_btn_join4").val()!="" ||$("#table_btn_join5").val()!="" ) {
					
					 $("#exampleModal").show();
					 
				} else {
					$("#join_message").html("Please select join table..!!");
				} 
			});

			$('#addJoin').click(function(e) {
				addJoinfilter(e);
			});
		});

		$(document).ready(function() {

			$("#table_select").change(function() {
				baseTableSelectOperation();
			});
           
			// not using this function replaced by below function
			$("#table_select2").change(function() {
				$("#table_btn").show();

				if ($("#table_select2").val()) {
					$("#table_btn_join").show();
					var btn = $("#table_select2 option:selected").text();
					$("#table_btn_join").val(btn.trim() + "(Fields)");
				} else {
					$("#columnsJoin").empty();
					var btn = $("#table_select2 option:selected").text();
					$("#table_btn_join").val(btn.trim());

					$('a', $('#drag')).each(function() {
						if ($(this).text().includes("(S)")) {
							$(this).remove();
						}
					});

					$("#joinTextArea").html("");

				}
			});
			
			
		$(".checkjoincls[name='joincheck']:checkbox").change(function(event) {
			     
			    $("#join_message").html("");
			     var table,unchkTable;
			     var tabletext=null; var unchecked=false;
			     
				if ($(this).is(":checked")) {
				    table = $(this).val();
				    tabletext=$(this).next('label').text().trim();
				}
				
				// removing sideToggle btns when unchecked
				if ($(this).is(":not(:checked)")) {
					unchecked=true;
					unchkTable=$(this).next('label').text().trim();
					table = $(this).val();
					
				     	for (var i=1;i<=5;i++) {
			          if($("#table_btn_join"+i).val()){
							
					var colbtntxt=	$("#table_btn_join"+i).val().trim();
					colbtntxt=colbtntxt.substring(0,colbtntxt.indexOf("("));
						
						if (unchkTable==colbtntxt.trim()){
							$("#table_btn_join"+i).val('');
							$("#table_btn_join"+i).hide();
							$("#columnsJoin"+i).empty();
							$("#sj").find('optgroup, option').remove("optgroup[label="+"'"+unchkTable+"'"+"]");
							$("#pri").find('optgroup, option').remove("optgroup[label="+"'"+unchkTable+"'"+"]");
							$("#pri2").find('optgroup, option').remove("optgroup[label="+"'"+unchkTable+"'"+"]");
							$("#sec").find('optgroup, option').remove("optgroup[label="+"'"+unchkTable+"'"+"]");
						   }
					   } 
					}
				     // Wirte code here for Remove drag fields when unchecked that corresponding table.	
				    	$('a', $('#drag')).each(function() {
							if ($(this).attr('value').includes(table)) {
								$(this).remove();
							}
						});
				     	
				}
				 var tablelist = [];
					$("input[name='joincheck']:checkbox").each(function() {
						if ($(this).is(":checked") && !(tablelist.includes($(this).val()))){
							if($("#table_btn").val().replace("(Fields)",'').trim()!=tabletext){
								tablelist.push($(this).val());
							}else{
								$("#join_message").html("Sorry same table can't be joined..!!");
								$(event.currentTarget).prop("checked", false);
								 unchecked=true;
								 return false;
							}
						}
					});
				
				if(tablelist.length >=2 && $(this).is(":checked")){
					if(!confirm("please confirm do you want join more tables!")) {
						if ($(this).is(":checked")) {
							   $(this).prop("checked", false);
							}
							return false;
					}
				}
				retriveJoinColumnAjaxCall(tablelist,table,tabletext,unchecked,event);
			});
		});

		$(document).ready(function() {
			
			   $('#table_btn').click(function(e)  {
					if ($("#ondate").val().trim() == "")
						{
						alert("Please select ondate..!");
						}
					else
						{
						$("#columns").slideToggle("slow");
						}
				});
			
			
			$("#table_btn_join1").click(function() {
				$("#columnsJoin1").slideToggle("slow");
			});
			$("#table_btn_join2").click(function() {
				$("#columnsJoin2").slideToggle("slow");
			});
			$("#table_btn_join3").click(function() {
				$("#columnsJoin3").slideToggle("slow");
			});
			$("#table_btn_join4").click(function() {
				$("#columnsJoin4").slideToggle("slow");
			});
			$("#table_btn_join5").click(function() {
				$("#columnsJoin5").slideToggle("slow");
			});
		});
		function KeyPressQT() {
			$("#qryTitle_message").html("");
			$("#qryTitle").removeClass("table_select_error");
		}

		/* scroll top js */

		$(window).scroll(function() {
			var height = $(window).scrollTop();
			if (height > 100) {
				$('#back2Top').fadeIn();
			} else {
				$('#back2Top').fadeOut();
			}
		});

		$(document).ready(function() {
			$("#back2Top").click(function(event) {
				event.preventDefault();
				$("html, body").animate({
					scrollTop : 0
				}, "fast");
				return false;
			});

		});
		$(document).ready(function(){
			$("#joinType").mouseover(function() {
				var title = $("#joinType option:selected").attr('title')
			    $(this).attr({
			        title: title
			    });
			});
			});

	</script>
	
	<a id="back2Top" title="Back to top" href="#">&#10148;</a>
</body>
</html>

     
                            <%-- <select id="table_select2" data-init-plugin="select2" class="form_input pull-left half_width"  style="width: 25%; cursor: pointer; margin-right: 15px; height: 28px;">
                                 <c:forEach items="${tables}" var="tableJoin">
                                  <input type="checkbox" class='checkbox' name="languages" value="${tableJoin.tableName}"><c:out value="${tableJoin.name}"></c:out><br>
								  <option value="<c:out value="${tableJoin.tableName}"></c:out>"><c:out value="${tableJoin.name}"></c:out></option> 
								</c:forEach>
                                <option value="<c:out value="${favouriteLists.table2}"></c:out>" selected="selected"><c:out value="${favouriteLists.table2}"></c:out></option>
                            </select> --%>