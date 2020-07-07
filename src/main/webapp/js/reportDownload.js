$(document).ready(function() {
	
	$("#getReport").on('click', function() {
		getResponse();
	}); 
	$("#btReload").on('click', function() {
		refreshPage();
	});
	$("#btReload").attr("disabled", true);
});

function refreshPage() {
	var url = "getReportData";
	var jsonObject = {};

	jsonObject["branCode"] = $('#branCode').val();
	jsonObject["date"] = $('#datepicker-13').val();
	var jsonOb = JSON.stringify(jsonObject);

	$.ajax({
				type : "POST",
				data : jsonOb,
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				url : url,
				error : function(xhr, status, error) {
					alert("Error");
					console.log(xhr.responseText);
					var err = xhr.responseText;
					if (err.toLowerCase().indexOf("session_timed_out") >= 0) {
						window.location = "login.html?statusCheck=SessionExpired";
					}
				},
				beforeSend : function() {
					$("#clockdiv").html(" ");
					openModal();
					$("#clockdiv").empty();
				},
				success : function(response) {
					closeModal();
					 var time_in_minutes = 3;
					 var current_time = Date.parse(new Date());
					 var deadline = new Date(current_time + time_in_minutes*60*1000);
					run_clock('clockdiv',deadline);
					$('#tbodydown').empty()
					if (parseInt(response.error) == 1) {
						$(".responseBarMsg").html(response.errorMsg);
					} else {
						$('#btReload').removeAttr('disabled',true);
						var sr = 1
						$
								.each(
										response.statusList,
										function(index, value) {
                                           var sts = value.status;
											var tr = '<tr>'
											tr += "<td >" + sr + "</td>";
											tr += "<td >" + value.id + "</td>";
											tr += "<td>" + value.fileName
													+ "</td>";
											tr += "<td>" + value.startTime
													+ "</td>";
											tr += "<td>" + value.endTime
													+ "</td>";
											tr += "<td class='status' id='status'>" + value.status
													+ "</td>";
										
											if(sts == 'DELETED' || sts == 'IN QUEUE'){
												tr += '<td >'
													+ "<form id='csvForm' style='padding: 0; position: relative;z-index: 0;' method='post' action='downloadfile'> " +
															"<button type='submit' disabled class='btn btn-default btn-sm  downloadbtn' name='fileName' value='"+ value.fileName + "' id='downloadbtn' style='width: 160%;'>"
													+ "<span class='fa fa-download'/>"
													+ "</button> </form>"
													+ '</td>';
											
											tr += '<td >'
												+ "<button type='button' disabled class='btn btn-default btn-sm deletebtn' name='fileName' value='"
												+ value.id
												+ "' style= 'width: 105%;'>"
												+ "<span class='fa fa-trash-o fa-lg' aria-hidden='true'/>"
												+ "</button>"
												+ '</td>';
											} else if (sts == 'INPROCESS') {
												tr += '<td >'
													+ "<form id='csvForm' style='padding: 0; position: relative;z-index: 0;' method='post' action='downloadfile'> " +
													  "<button type='submit' disabled class='btn btn-default btn-sm  downloadbtn' name='fileName' value='"+ value.fileName + "' id='downloadbtn' style='width: 160%;'>"
													+ "<span class='fa fa-download' aria-hidden='true'/>"
													+ "</button> </form>"
													+ '</td>';
												
												tr += '<td >'
													+ "<button type='button' class='btn btn-default btn-sm deletebtn'  name='fileName' value='"
													+ value.id
													+ "' style= 'width: 105%; color: white;background-color: #b90a0a;'>"
													+ "<span class='fa fa-ban' aria-hidden='true'/>"
													+ "</button>"
													+ '</td>';
												
											}else if (sts == 'STOP' ){
												tr += '<td >'
													+ "<form id='csvForm' style='padding: 0; position: relative;z-index: 0;' method='post' action='downloadfile'> " +
															"<button type='submit' disabled class='btn btn-default btn-sm  downloadbtn' name='fileName' value='"+ value.fileName+ "' id='downloadbtn' style='width: 160%;'>"
													+ "<span class='fa fa-download' aria-hidden='true'/>"
													+ "</button> </form>"
													+ '</td>';
											
											tr += '<td >'
												+ "<button type='button' disabled class='btn btn-default btn-sm deletebtn'  id =stop name='fileName' value='"
												+ value.id
												+ "' style= 'width: 105%;'>"
												+ "<span class='fa fa-ban' aria-hidden='true'/>"
												+ "</button>"
												+ '</td>';
											} else{
												tr += '<td >'
													+ "<form id='csvForm' style='padding: 0; position: relative;z-index: 0;' method='post' action='downloadfile'> " +
															"<button type='submit' class='btn btn-default btn-sm  downloadbtn' name='fileName' value='"+ value.fileName+ "' id='downloadbtn' style='color: white;width: 160%; background: #0a860a;>"
													+ "<span class='fa fa-download' aria-hidden='true'/>"
													+ "</button> </form>"
													+ '</td>';
												
												
												tr += '<td >'
													+ "<button type='button' class='btn btn-default btn-sm deletebtn'  name='fileName' value='"
													+ value.id
													+ "' style= 'width: 105%;color: white;background-color: #b90a0a;'>"
													+ "<span class='fa fa-trash-o fa-lg' aria-hidden='true'/>"
													+ "</button>"
													+ '</td>';
												
											}
											/*tr += '<td >'
													+ "<button type='button' class='btn btn-default btn-sm delbtn'  name='id' onclick='deletreRecord()' value='"
													+ value.id
													+ "' id='deletebtn'style= 'width: 79%;'>"
													+ "<span class='glyphicon glyphicon-remove-sign'/>"
													+ "Delete" + "</button>"
													+ '</td>';
											tr += '</tr>'*/
											$('#tbodydown').append(tr);
											sr++;
											$('.status:contains("INPROCESS")')
													.css('color', 'red');
											$('.status:contains("COMPLETED")')
													.css('color', 'green');
											
											$('.status:contains("DELETED")')
											.css('color', 'blue');
											
											$('.status:contains("STOP")')
											.css('color', 'red');
											
											  if($('#status').val() == 'INPROCESS') {
									                $("#downloadbtn").attr("disabled", true);
									            }
											
										});
						closeModal();
						var table = $('#downfilestatustble').DataTable();
						$("#DButton").attr("disabled", true);
					}

				}

			});
}

function getResponse() {
	var url = "getReportData";
	var jsonObject = {};

	jsonObject["branCode"] = $('#branCode').val();
	jsonObject["date"] = $('#datepicker-13').val();
	var jsonOb = JSON.stringify(jsonObject);

	$.ajax({
				type : "POST",
				data : jsonOb,
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				url : url,
				error : function(xhr, status, error) {
					alert("Error");
					console.log(xhr.responseText);
					var err = xhr.responseText;
					if (err.toLowerCase().indexOf("session_timed_out") >= 0) {
						window.location = "login.html?statusCheck=SessionExpired";
					}
				},
				beforeSend : function() {
					openModal();
					$("#clockdiv").empty();
				},
				success : function(response) {
					closeModal();
					 var time_in_minutes = 3;
					 var current_time = Date.parse(new Date());
					 var deadline = new Date(current_time + time_in_minutes*60*1000);
					run_clock('clockdiv',deadline);
					$('#tbodydown').empty()
					if (parseInt(response.error) == 1) {
						$(".responseBarMsg").html(response.errorMsg);
					} else {
						$('#btReload').removeAttr('disabled',true);
						var sr = 1
						$
								.each(
										response.statusList,
										function(index, value) {
                                           var sts = value.status;
											var tr = '<tr>'
											tr += "<td >" + sr + "</td>";
											tr += "<td >" + value.id + "</td>";
											tr += "<td>" + value.fileName
													+ "</td>";
											tr += "<td>" + value.startTime
													+ "</td>";
											tr += "<td>" + value.endTime
													+ "</td>";
											tr += "<td class='status' id='status'>" + value.status
													+ "</td>";
										
											if(sts == 'DELETED' || sts == 'IN QUEUE'){
												tr += '<td >'
													+ "<form id='csvForm' style='padding: 0; position: relative;z-index: 0;' method='post' action='downloadfile'> " +
															"<button type='submit' disabled class='btn btn-default btn-sm  downloadbtn' name='fileName' value='"+ value.fileName + "' id='downloadbtn' style='width: 160%;'>"
													+ "<span class='fa fa-download' aria-hidden='true'/>"
													+ "</button> </form>"
													+ '</td>';
											
											tr += '<td >'
												+ "<button type='button' disabled class='btn btn-default btn-sm deletebtn'  name='fileName' value='"
												+ value.id
												+ "' style= 'width: 105%;'>"
												+ "<span class='fa fa-trash-o fa-lg' aria-hidden='true'/>"
											    + "</button>"
												+ '</td>';
											} else if (sts == 'INPROCESS') {
												tr += '<td >'
													+ "<form id='csvForm' style='padding: 0; position: relative;z-index: 0;' method='post' action='downloadfile'> " +
															"<button type='submit' disabled class='btn btn-default btn-sm  downloadbtn' name='fileName' value='"+ value.fileName + "' id='downloadbtn' style='width: 160%;'>"
													+ "<span class='fa fa-download' aria-hidden='true'/>"
													+ "</button> </form>"
													+ '</td>';
												
												tr += '<td >'
													+ "<button type='button' class='btn btn-default btn-sm deletebtn'   name='fileName' value='"
													+ value.id
													+ "' style= 'width: 105%;color: white;background-color: #b90a0a;'>"
													+ "<span class='fa fa-ban' aria-hidden='true'/>"
													+ "</button>"
													+ '</td>';
												
											}else if (sts == 'STOP'){
												tr += '<td >'
													+ "<form id='csvForm' style='padding: 0; position: relative;z-index: 0;' method='post' action='downloadfile'> " +
															"<button type='submit' disabled class='btn btn-default btn-sm  downloadbtn' name='fileName' value='"+ value.fileName+ "' id='downloadbtn' style='width: 160%;'>"
													+ "<span class='fa fa-download' aria-hidden='true'/>"
													+ "</button> </form>"
													+ '</td>';
											
											tr += '<td >'
												+ "<button type='button' disabled class='btn btn-default btn-sm deletebtn' id =stop name='fileName' value='"
												+ value.id
												+ "' style= 'width: 105%;'>"
												+ "<span class='fa fa-ban' aria-hidden='true'/>"
												+ "</button>"
												+ '</td>';
											} else{
												tr += '<td >'
													+ "<form id='csvForm' style='padding: 0; position: relative;z-index: 0;' method='post' action='downloadfile'> " +
															"<button type='submit' class='btn btn-default btn-sm  downloadbtn' name='fileName' value='"+ value.fileName+ "' id='downloadbtn' style='color: white; width:160%; background: #0a860a;'>"
													+ "<span class='fa fa-download' aria-hidden='true'/>"
													+ "</button> </form>"
													+ '</td>';
												
												tr += '<td >'
													+ "<button type='button' class='btn btn-default btn-sm deletebtn'  name='fileName' value='"
													+ value.id
													+ "' style= 'width: 110%;color: white;background-color: #b90a0a;'>"
													+ "<span class='fa fa-trash-o fa-lg' aria-hidden='true'/>"
													+ "</button>"
													+ '</td>';
												
											}
											/*tr += '<td >'
													+ "<button type='button' class='btn btn-default btn-sm delbtn'  name='id' onclick='deletreRecord()' value='"
													+ value.id
													+ "' id='deletebtn'style= 'width: 79%;'>"
													+ "<span class='glyphicon glyphicon-remove-sign'/>"
													+ "Delete" + "</button>"
													+ '</td>';
											tr += '</tr>'*/
											$('#tbodydown').append(tr);
											sr++;
											$('.status:contains("INPROCESS")')
													.css('color', 'red');
											$('.status:contains("COMPLETED")')
													.css('color', 'green');
											
											$('.status:contains("DELETED")')
											.css('color', 'blue');
											
											$('.status:contains("STOP")')
											.css('color', 'red');
											  if($('#status').val() == 'INPROCESS') {
									                $("#downloadbtn").attr("disabled", true);
									            }
											
										});
						closeModal();
						var table = $('#downfilestatustble').DataTable();
						var interval = 1000 * 60 * 3; // 3 min for every 3 min  refreshPage() fun calls
						 clearInterval(localStorage.getItem("nthtime")); 
					 	 localStorage.removeItem("nthtime"); 
						 var Nthtime = setInterval('refreshPage()', interval);   // call back function 
					 	localStorage.setItem("nthtime",Nthtime); 
					}

				}

			});
}

$('#downfilestatustble').on('click','.deletebtn', function() {

	var $row = $(this).closest("tr");
	var id = $row.find("td:nth-child(2)").text().trim();
	var url = "getDeleteSts";
	var status = $row.find("td:nth-child(6)").text().trim();
	var date = "";
	var branCode="";
	var startTime = "";
	var endTime = "";
	var queId = "";
	var fileName = "";
	var flag = "";
	//alert
	if(status == "INPROCESS"){
		if(!confirm('Do you want to Stop file ?'))
			{
			return false;
			}
		}
	else{
		if(!confirm('Do you want to Delete file ?'))
		{
		return false;
		}
	}
	var reportDownload = {
		"id" : id,
		"status" : status,
		"date" : date,
		"startTime" : startTime,
		"endTime" : endTime,
		"queId" : queId,
		"fileName" : fileName,
		"flag" : flag,
		"branCode" : branCode,
	}

	$.ajax({
				type : "POST",
				data : JSON.stringify(reportDownload),
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				url : url,
				error : function(xhr, status, error) {
					alert("Error");
					console.log(xhr.responseText);
					var err = xhr.responseText;
					if (err.toLowerCase().indexOf("session_timed_out") >= 0) {
						window.location = "login.html?statusCheck=SessionExpired";
					}
				},
				beforeSend : function() {
					/* $.blockUI(); */
				},
				success : function(response) {
					 alert(response.statusList);
					 refreshPage();
				}

			});
});


function time_remaining(endtime){
	var t = Date.parse(endtime) - Date.parse(new Date());
	var seconds = Math.floor( (t/1000) % 60 );
	var minutes = Math.floor( (t/1000/60) % 60 );
	var hours = Math.floor( (t/(1000*60*60)) % 24 );
	var days = Math.floor( t/(1000*60*60*24) );
	return {'total':t, 'days':days, 'hours':hours, 'minutes':minutes, 'seconds':seconds};
}
function run_clock(id,endtime){
	var clock = document.getElementById(id);
	//clearTimeout(timeinterval);
	timerStopFunction();
	update_clock();
	var timeinterval = setInterval(update_clock,1000);
	localStorage.setItem("mytime",timeinterval);
	
	function update_clock(){
		var t = time_remaining(endtime);
		clock.innerHTML = t.minutes+' m '+' : '+t.seconds+' s ';
		//clearTimeout(t);
		if(t.total<=0){
			clearInterval(timeinterval); 
		}
	}
	function timerStopFunction()
	 {
		 clearInterval(localStorage.getItem("mytime"));
		 localStorage.removeItem("mytime");
	 }
	//update_clock();// run function once at first to avoid delay
	//var timeinterval = setInterval(update_clock,1000);
}


