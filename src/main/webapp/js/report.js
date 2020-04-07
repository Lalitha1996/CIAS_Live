$(function() {
	/*hi koushik*/
	$('#iptext').keyup(function(e) {
		var value = $('#iptext').val();
		var selectedValue = $("#s1").val();
		if ($('#iptext').css('color') == 'rgb(255, 0, 0)') {
			$('#iptext').css('color', 'black');
			$('#btn_transmit').removeClass('disabled');
		}
		if (isCriteriaNumber(selectedValue)) {
			if (!isValidIptext(value)) {
				$('#iptext').addClass('iptext_message');
			} else {
				$('#iptext').removeClass('iptext_message');
			}
		}
	});
});


function blockSpecialChar(e) {
	var k;
	document.all ? k = e.keyCode : k = e.which;
	if ($("#s2").val() == "IN") {
		if (((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32
				|| k == 95 || (k >= 44 && k <= 57))) {
			return true;
		} else {
			$("#errmsg").html("Symbols not allow..").show().fadeOut("slow");
			return false;
		}
	} else {
		if (((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || k == 32
				|| k == 95 || (k >= 48 && k <= 57))) {
			return true;
		} else {
			$("#errmsg").html("Symbols not allow..").show().fadeOut("slow");
			return false;

		}
	}
}

//Join Filter

function addJoinfilter(e){
	
	
	$("#join_message").html("");
	
	  $("#pri").val();
	  $("#sec").val();
	  $("#pri2").val();
	  $("#con").val();
	  $("#conP").val();
	  $("#TextP").val();
	  $("#logicalA").val();
	  
	 if (($("#pri").val() && $("#sec").val())){
		 
		 if(($("#joinTextArea").val()=="")  || ( $("#joinTextArea").val().slice(-1) == ",") ){
			 
			 $("#joinTextArea").append($("#pri").val() +"="+ $("#sec").val()); 
			 
		 }else {
			 
			 $("#joinTextArea").append(" "+$("#logicalA").val()+" "+$("#pri").val() +"="+ $("#sec").val()); 
		 }
			 
			 
	 }if($("#pri2").val() && $("#TextP").val()) {
		 
		 if (!($("#pri").val() && $("#sec").val()) && !($("#joinTextArea").val())  || ( $("#joinTextArea").val().slice(-1) == ",")){
			 
			 $("#joinTextArea").append($("#pri2").val() +$("#conP").val()+"'"+ $("#TextP").val()+"'");  
		 }else{
			 $("#joinTextArea").append(" "+$("#logicalA").val()+" "+$("#pri2").val() +$("#conP").val()+"'"+ $("#TextP").val()+"'"); 
		 }
		 
	 }
	
	 if (!($("#joinTextArea").val())) {
		 alert("Please select at least one filter criteria..!!");
		 return false;
	 }
	
	 if(($("#pri").val() && $("#sec").val())){
		 $("select#pri").val('');
		 $("select#sec").val('');
	 }
	 if(($("#pri2").val() && $("#TextP").val())){
		 $("select#pri2").val('');
		 $("#TextP").val("");
	 }
	
	  $("#exampleModal").hide();
}

function addJoinfilterValidation(tablelist,event){
	var condition=false;
	$("input[name='joincheck']:checkbox").each(function() {
		if ($(this).is(":checked") && event.currentTarget.value!=$(this).val()){
			if($("#joinTextArea").val().includes($(this).val())){
				condition=true;
			}else{
				condition=false;
				return false;
			}
		}
	});
	return condition;
}


function isValidIptext(value) {
	var filter = "";
	if ($("#s2").val() == "IN") {
		filter = /^[0-9-+]+$/;
	} else {
		filter = /^[0-9,-,+]+$/;
	}

	if (filter.test(value)) {
		return true;
	} else {
		return false;
	}
}
function addToFilter() {
	
	  $("#Cmsg1").html("");
	  $("#Cmsg2").html("");
      $("#Cmsg3").html("");
        
	var DD1 = $("#s1").val(), alias = "";
	var DD2 = $("#s2").val(), ipVal = '';
	var Dj  = $("#sj").val();
	if ($("#sj").val()!="") {
		      DD1 = Dj;
	    }
	
	if ($("#iptext").val()) {
		ipVal = $("#iptext").val();
	} else if ($("#iptextdate").val()) {
		ipVal = $("#iptextdate").val();
	}
	var fromValue = $("#from").val(), toValue = $("#to").val(), todate = $("#todate").val(), fromdate = $("#fromdate").val();
	var data = $("#criteriaTextArea").val();
	if ((ipVal != "" && ipVal != null) || (fromValue != "" && toValue != null)|| (todate != "" && fromdate != null)) {
		if (data == "") {
			addFirstCriteria(DD1, DD2, ipVal, alias);
		} else {
			addMultipleCriteria(data, DD1, DD2, ipVal, alias);
		}
	} else {
		alert("Enter Data for before Criteria");
	}
}

function addFirstCriteria(DD1, DD2, ipVal, alias) {

	if (isCriteriaString(DD1)
			&& (!$("#s2 option:selected").text() == 'Start Like' || !$(
					"#s2 option:selected").text() == 'End Like')) {
		$("#criteriaTextArea").html(
				alias + DD1 + " " + DD2 + " " + "'" + ipVal + "'");
	} else if (isCriteriaString(DD1)
			&& $("#s2 option:selected").text() == 'Start Like') {
		$("#criteriaTextArea").html(
				alias + DD1 + " " + DD2 + " " + "'" + ipVal + "%'");
	} else if (isCriteriaString(DD1)
			&& $("#s2 option:selected").text() == 'End Like') {
		$("#criteriaTextArea").html(
				alias + DD1 + " " + DD2 + " " + "'%" + ipVal + "'");
	} else if (isCriteriaNumber(DD1) && $('#s2').val() == 'between') {
		$("#criteriaTextArea").html(
				alias + DD1 + " BETWEEN " + $('#from').val() + " AND "
						+ $('#to').val());
	} else if (isCriteriaDate(DD1) && $('#s2').val() == 'Fromto') {
		$("#criteriaTextArea").html(
				alias + DD1 + " BETWEEN " + "'" + $('#fromdate').val() + "'"
						+ " AND " + "'" + $('#todate').val() + "'");
	} else if (isCriteriaNumber(DD1) && $('#s2').val() == 'IN') {
		$("#criteriaTextArea").html(alias + DD1 + " IN " + "(" + ipVal + ")");
	} else if (isCriteriaNumber(DD1)) {
		$("#criteriaTextArea").html(alias + DD1 + " " + DD2 + " " + ipVal);
	} else {
		$("#criteriaTextArea").html(
				alias + DD1 + " " + DD2 + " " + "'" + ipVal + "'");
	}
}
function addMultipleCriteria(data, DD1, DD2, ipVal, alias) {

	var newdata;
	if (isCriteriaNumber(DD1)
			&& (!$("#s2 option:selected").text() == 'Start Like' || !$(
					"#s2 option:selected").text() == 'End Like')) {
		newdata = data + "\n" + " AND" + " " + alias + DD1 + " " + DD2 + " "
				+ "'" + ipVal + "'";
	} else if (isCriteriaString(DD1)
			&& $("#s2 option:selected").text() == 'Start Like') {
		newdata = data + "\n" + " AND" + " " + alias + DD1 + " " + DD2 + " "
				+ "'" + ipVal + "%'";
	} else if (isCriteriaString(DD1)
			&& $("#s2 option:selected").text() == 'End Like') {
		newdata = data + "\n" + " AND" + " " + alias + DD1 + " " + DD2 + " "
				+ "'%" + ipVal + "'";
	} else if (isCriteriaNumber(DD1) && $('#s2').val() == 'between') {
		newdata = data + "\n" + " AND" + " " + alias + DD1 + " BETWEEN "
				+ $('#from').val() + " AND " + $('#to').val();
	} else if (isCriteriaDate(DD1) && $('#s2').val() == 'Fromto') {
		newdata = data + "\n" + " AND" + " " + alias + DD1 + " BETWEEN " + "'"
				+ $('#fromdate').val() + "'" + " AND " + "'"
				+ $('#todate').val() + "'";
	} else if (isCriteriaNumber(DD1) && $('#s2').val() == 'IN') {
		newdata = data + "\n" + " AND" + " " + alias + DD1 + " IN " + "("
				+ ipVal + ")";
	} else if (isCriteriaNumber(DD1)) {
		newdata = data + "\n" + " AND" + " " + alias + DD1 + " " + DD2 + " "
				+ ipVal;
	} else {
		newdata = data + "\n" + " AND" + " " + alias + DD1 + " " + DD2 + " "
				+ "'" + ipVal + "'";
	}
	$("#criteriaTextArea").html(newdata);

}
function addINToFilter() {

	var DD1 = $("#s1").val();
	var DD2 = $("#s2").val();
	var ipVal = $("#iptext").val();
	var Data = $("#criteriaTextArea").val();
	if (ipVal != "" && ipVal != null) {
		if (Data == "") {
			$("#criteriaTextArea").html(DD1 + " " + DD2 + " " + ipVal);
		} else {
			var newdata = Data + "\n" + "AND" + " " + DD1 + " " + DD2 + " "
					+ ipVal;
			$("#criteriaTextArea").html(newdata);
		}
	} else {
		alert("Enter Data for before Criteria");
	}
}

function addORToFilter() {
	var DD1 = $("#s1").val(), alias = "";
	var DD2 = $("#s2").val();
    var ipVal="";
	var Dj  = $("#sj").val();
	if ($("#sj").val()!="") {
		      DD1 = Dj;
	    }
	
	if ($("#iptext").val()) {
		ipVal = $("#iptext").val();
	} else if ($("#iptextdate").val()) {
		ipVal = $("#iptextdate").val();
	}
	
	var fromValue = $("#from").val(), toValue = $("#to").val(),
	todate = $("#todate").val(), fromdate = $("#fromdate").val();
	var data = $("#criteriaTextArea").val();
	if ((ipVal != "" && ipVal != null) || (fromValue != "" && toValue != null) || (todate != "" && fromdate != null)) {
		if (data == "") {
			addFirstCriteria(DD1, DD2, ipVal, alias);
		} else {
			var newdata;
			if (isCriteriaNumber(DD1)
					&& (!$("#s2 option:selected").text() == 'Start Like' || !$(
							"#s2 option:selected").text() == 'End Like')) {
				newdata = data + "\n" + " OR" + " " + alias + DD1 + " " + DD2
						+ " " + "'" + ipVal + "'";
			} else if (isCriteriaString(DD1)
					&& $("#s2 option:selected").text() == 'Start Like') {
				newdata = data + "\n" + " OR" + " " + alias + DD1 + " " + DD2
						+ " " + "'" + ipVal + "%'";
			} else if (isCriteriaString(DD1)
					&& $("#s2 option:selected").text() == 'End Like') {
				newdata = data + "\n" + " OR" + " " + alias + DD1 + " " + DD2
						+ " " + "'%" + ipVal + "'";
			} else if (isCriteriaNumber(DD1) && $('#s2').val() == 'between') {
				newdata = data + "\n" + " OR" + " " + alias + DD1 + " BETWEEN "
						+ $('#from').val() + " AND " + $('#to').val();
			} else if (isCriteriaDate(DD1) && $('#s2').val() == 'Fromto') {
				newdata = data + "\n" + " OR" + " " + alias + DD1 + " BETWEEN "
						+ "'" + $('#fromdate').val() + "'" + " AND " + "'"
						+ $('#todate').val() + "'";
			} else if (isCriteriaNumber(DD1) && $('#s2').val() == 'IN') {
				newdata = data + "\n" + " OR" + " " + alias + DD1 + " IN "
						+ "(" + ipVal + ")";
			} else if (isCriteriaNumber(DD1)) {
				newdata = data + "\n" + " OR" + " " + alias + DD1 + " " + DD2
						+ " " + ipVal;
			} else {
				newdata = data + "\n" + " OR" + " " + alias + DD1 + " " + DD2
						+ " " + "'" + ipVal + "'";
			}
			$("#criteriaTextArea").html(newdata);
		}
	} else {
		alert("Enter Data for before Criteria");
	}
}

function isCriteriaNumber(DD1) {
	var response = JSON.parse(localStorage.getItem("dbColumns"));
	var flag = false;
	$.each(response, function(i, value) {
		if (DD1 == value.name) {
			flag = isNumber(value);
		}
	});
	return flag;
}
function isCriteriaDate(DD1) {
	var response = JSON.parse(localStorage.getItem("dbColumns"));
	var flag = false;
	$.each(response, function(i, value) {
		if (DD1 == value.name) {
			flag = isDate(value);
		}
	});
	return flag;
}
function isCriteriaString(DD1) {
	var response = JSON.parse(localStorage.getItem("dbColumns"));
	var flag = false;
	$.each(response, function(i, value) {
		if (DD1 == value.name) {
			flag = isString(value);
		}
	});
	return flag;
}
function hideDivs() {
	document.getElementById('fromToDiv').style.display = 'none';
	document.getElementById('iptextdateDiv').style.display = 'none';
	document.getElementById('betweenDiv').style.display = 'none';
}
function validateSelectedValue() {
	var response = JSON.parse(localStorage.getItem("dbColumns"));
	
	var tablelist=getTablelist();
	var totFields=[];
	
	if(tablelist.length>0){
	for(var i=1;i<=tablelist.length;i++){
		var arr = JSON.parse(localStorage.getItem("columnsJoin"+i));
		for (var j=0;j<=arr.length-1;j++){
			totFields.push(arr[j]);
		}
	  }
	}
	
	localStorage.setItem("JoindbColumns", JSON.stringify(totFields));
	var Joinresponse = JSON.parse(localStorage.getItem("JoindbColumns"));
	
	var selectedValue = $('#s1').val();
	var selectedJoinValue = $('#sj').val();
	$('#s2').val('');
	$('#iptext').val('');
	
	$.each(response,function(i, value) {
						if (selectedValue == value.name) {
							if (isNumber(value)) {
								hideDivs();
								document.getElementById('iptext').style.display = 'block';
								addNumberSymbol();

							} else if (isString(value)) {
								hideDivs();
								document.getElementById('iptext').style.display = 'block';
								addStringSymbol();
							} else if (isDate(value)) {
								document.getElementById('iptextdateDiv').style.display = 'block';
								document.getElementById('iptext').style.display = 'none';
								document.getElementById('betweenDiv').style.display = 'none';
								addDateSymbol();
							}
						}
					});
	
	if (Joinresponse!=null) {
		
	$.each(Joinresponse,function(i, value) {
		if (selectedJoinValue == value.name) {
			if (isNumber(value)) {
				hideDivs();
				document.getElementById('iptext').style.display = 'block';
				addNumberSymbol();

			} else if (isString(value)) {
				hideDivs();
				document.getElementById('iptext').style.display = 'block';
				addStringSymbol();
			} else if (isDate(value)) {
				document.getElementById('iptextdateDiv').style.display = 'block';
				document.getElementById('iptext').style.display = 'none';
				document.getElementById('betweenDiv').style.display = 'none';
				addDateSymbol();
			 }
		  } 
	  });
   }
}
function addDateSymbol() {

	$('#s2').empty().append($('<option>', {
		value : '=',
		text : '='
	}));
	$('#s2').append($('<option>', {
		value : '>',
		text : '>'
	}));
	$('#s2').append($('<option>', {
		value : '<',
		text : '<'
	}));
	$('#s2').append($('<option>', {
		value : '>=',
		text : '>='
	}));
	$('#s2').append($('<option>', {
		value : '<=',
		text : '<='
	}));
	$('#s2').append($('<option>', {
		value : 'Fromto',
		text : 'From to'
	}));
}

function addNumberSymbol() {

	$('#s2').empty().append($('<option>', {
		value : '=',
		text : '='
	}));
	$('#s2').append($('<option>', {
		value : '>',
		text : '>'
	}));
	$('#s2').append($('<option>', {
		value : '<',
		text : '<'
	}));
	$('#s2').append($('<option>', {
		value : '>=',
		text : '>='
	}));
	$('#s2').append($('<option>', {
		value : '<=',
		text : '<='
	}));
	$('#s2').append($('<option>', {
		value : 'IN',
		text : 'In'
	}));
	$('#s2').append($('<option>', {
		value : 'between',
		text : 'between'
	}));
}
function addStringSymbol() {
	$('#s2').empty().append($('<option>', {
		value : 'like',
		text : 'Like'
	}));
	$('#s2').append($('<option>', {
		value : 'like',
		text : 'Start Like'
	}));
	$('#s2').append($('<option>', {
		value : 'like',
		text : 'End Like'
	}));
}

function isDate(value) {
	var isDate = false;
	if ('DATE' == value.dataType) {
		isDate = true;
	}else if ('date' == value.dataType) {
		isDate = true;
	}
	return isDate;
}

function isNumber(value) {
	var isNumber = false;
	if ('decimal' == value.dataType) {
		isNumber = true;
	} else if ('int' == value.dataType) {
		isNumber = true;
	} else if ('FLOAT' == value.dataType) {
		isNumber = true;
	} else if ('NUMBER' == value.dataType) {
		isNumber = true;
	}else if ('double precision' == value.dataType) {
		isNumber = true;
	}else if ('numeric' == value.dataType) {
		isNumber = true;
	}
	return isNumber;
}

function isString(value) {
	var isString = false;
	if ('VARCHAR2' == value.dataType) {
		isString = true;
	} else if ('CHAR' == value.dataType) {
		isString = true;
	}else if ('character varying' == value.dataType) {
		isString = true;
	}
	return isString;
}

function validateRequiredFields(e) {
	if ($("#table_select option:selected").val() == "") {
		$("#message").html("Please select the table");
		$("#table_select").addClass("table_select_error");
	} else {
		$("#message").html("");
	}
}
function isAggregate(value) {
	var isAggregate = false;
	if (value.includes('AVG') || value.includes('MIN') || value.includes('MAX') || value.includes('SUM')) {
		isAggregate = true;
	}
	return isAggregate;
}

function isTableinJoins(tableList){
	var condition=false;
	
	for (i=0;i<tableList.length;i++){
		 if(($('#joinTextArea').val().includes(tableList[i])) && $('#joinTextArea').val()!=""){
			 condition=true;
		 }else{
			 condition=false;
			 $("#join_message").html("Please select the join table: " + tableList[i]);
			 $("#joinTextArea").addClass("table_select_error");
			 return false;
		 }
	}
	return condition;
}

function submitCf(e) {
	validateRequiredFields(e);
	var tableList = getTablelist();
	
	if(!$("#ondate").val()){
		$("#message1").html("Please select ondate...!");
		return false;
		}
	
	if ($('#table_select').val() && $('#drag').text()) {
		
		// Table 2 validation
		if(tableList.length!=0){
			
		if (!isTableinJoins(tableList)) {
			return false;
		}}
        // group by making automation
		
		/*if (isAggregate(parameter) && !$('#groupby').is(':checked')) {
			$("#drag_message").html("Please select Group by..");
			return false;
		}*/
		 var params=dragParams();
		 if (isAggregate(params) && !$('#groupby').is(':checked')) {
			$( "#groupby" ).prop( "checked", true);
			if ($('#groupby').is(':checked')) { 
				 groupBy();
			}
	     }
		
		var json = formatData();
		var jsonObject = JSON.parse(json);
		var parameter = jsonObject['parameter'];
		var colums = jsonObject['columnNames'];
		var dtA = jsonObject['dateA'];
		var dtB = jsonObject['dateB'];
		localStorage.setItem("columsHeads",colums);
		
		
		
		var url = 'getTableData';
		         $.ajax({
					type : "POST",
					url : url,
					data : json,
					dataType : "json",
					contentType : "application/json; charset=utf-8",
					error : function(xhr, status, error) {
						closeModal();
						var err = xhr.responseText;
						alert("Session Time Out");
						if (err.toLowerCase().indexOf("session_timed_out") >= 0) {
							window.location = "login.html?statusCheck=SessionExpired";
						}
					},
					success : function(response) {
						
						closeModal();
						// Chart Code Started....
						if(response[0].chartsData!=0 || response[0].chartsData!=null){
				        var charts = response[0].chartsData;
				        var chartsfields = response[0].chartsDataFields;
				        
				        function getUnique(arr){
                            return arr.filter((e,i)=> arr.indexOf(e) >= i);
                         }
                        console.log(getUnique(chartsfields));
                        
                        var DataSets=[];
                        var fields=null;
                        var Type=null;
                        var Ord_fields=null;
                          
                        var  arr = getUnique(chartsfields);
                        
                        Array.prototype.swapItems = function(a, b){
						    this[a] = this.splice(b, 1, this[a])[0];
						    return this;
						}
                        if(arr.includes("Branch_No")) {
                        var n = arr.indexOf("Branch_No");
    						arr.swapItems(0, n);
                        }
                        
                        if (arr.length==2)  {
                         Type="area";
                         fields=arr;
                         var DatapointsData = zeroOneFieldsData(charts,fields);
                         DataSets.push(DatapointsData);
                    
                       } 
                        if (arr.length==3)  {
                        	Type="column";
                            fields=arr;
                            var DatapointsData = zeroOneFieldsData(charts,fields);
                            var DatapointsData2 = zeroTwoFieldsData(charts,fields);
                            DataSets.push(DatapointsData);
                            DataSets.push(DatapointsData2);
                          }
                        if (arr.length==4)  {
                        	
                        	Type="bar";
                            fields=arr;
                            var DatapointsData = zeroOneFieldsData(charts,fields);
                            var DatapointsData2 = zeroTwoFieldsData(charts,fields);
                            var DatapointsData3 = zeroThreeFieldsData(charts,fields);
                            DataSets.push(DatapointsData);
                            DataSets.push(DatapointsData2);
                            DataSets.push(DatapointsData3);
                            
                          }
                        if (arr.length==5){
                        	
                        	  Type="column";
                        	  fields=arr;
                              var DatapointsData = zeroOneFieldsData(charts,fields);
                              var DatapointsData2 = zeroTwoFieldsData(charts,fields);
                              var DatapointsData3 = zeroThreeFieldsData(charts,fields);
                              var DatapointsData4 = zeroFourFieldsData(charts,fields);
                              DataSets.push(DatapointsData);
                              DataSets.push(DatapointsData2);
                              DataSets.push(DatapointsData3);
                              DataSets.push(DatapointsData4);
                        }
					}  
                         // Chart code closed ...!!               
                        
                        $("#chartContainer").hide();
						$('#serverValidation').empty();
						$('#serverValidation').hide();
						$( "#groupby" ).empty();
						$( ".groupbycolumns" ).empty();
						$( "#groupby" ).prop( "checked", false );
						if (response.length > 0 && response[0].appMessage) {
							var divContainer = document.getElementById("menu4");
							divContainer.innerHTML = "";
							$.each(response[0].appMessage,
									function(i, message) {
										$('#serverValidation').show();
										$('#serverValidation').append(
												"<i class='glyphicon glyphicon-warning-sign'></i>&nbsp;&nbsp;<span>"
														+ message.description
														+ "</span><br/>");
									  });
						} else {
							$('.favourite').show();
							//$( "#groupby" ).empty();
							//$( "#groupby" ).prop( "checked", false );
							CreateTableFromJSON(response,dtA,dtB);
							$('.report_field_table')
									.DataTable(
											{
												dom : 'Bfrtip',
												"scrollX": true,
												//Footer callback for total of columns for double precision
												 "footerCallback": function ( row, data, start, end, display ) {
                                                     var api = this.api(),data;
                                                     var tabledata = data;
                                                     nb_cols = api.columns().nodes().length;
                                                     for(i=0;i<nb_cols;i++){
                                                     var rowdata = tabledata[0][i];
                                                     if(rowdata.includes('.')){
                                                     var pageTotal = api.column( i, { page: 'current'} ).data().reduce( function (a, b) {
                                                             return Number(a) + Number(b);
                                                                 }, 0 );
                                                     $( api.column( i ).footer() ).html("Total: "+pageTotal.toFixed(2)); // Update footer
                                                         }
                                                      }
                                                     },
												buttons : [
														{
															text : '<i class="fa fa-file-excel-o"></i> Excel',
															action : function(e, dt,node,config) {
																e.preventDefault();
																value="excelForm";
																click : downloadValidation();
																//filtercriteriavalidation();
																//$("#excelForm").submit();
															}
														},
														{
															text : '<i class="fa fa-file-text-o"></i> CSV PIPE',
															action : function(e, dt,node,config) {
																e.preventDefault();
																value="pipecsvForm";
																click : downloadValidation();
																//filtercriteriavalidation();
																//$("#pipecsvForm").submit();
															}
														},
														{
															text : '<i class="fa fa-file-pdf-o"></i> PDF',
															action : function(e, dt,node,config) {
																e.preventDefault();
																value="pdfForm";
																click : downloadValidation();
																//filtercriteriavalidation();
																//$("#pdfForm").submit();
															}
														},
														{
															extend : 'copyHtml5',
															text : '<i class="fa fa-file"></i> Copy',
															titleAttr : 'Copy'
														},
										   				{
														   text : '<i class="fa fa-bar-chart-o" style ="background-color: #fff !important;"></i> Charts',
                                                           action : function(e, dt,node,config) {
                                                               e.preventDefault();
                                                               $("#compareDateForm").hide();
                                                                $('.dataTables_table').hide();
																$('.dataTables_filter').hide();
																$('.dataTables_info').hide();
																$('.dataTables_paginate').hide();
																$('.report_field_table').hide();

																if (fields!=null) {
																	
																	if(fields.includes("Branch_No")) {
																		
																		Piechart(DataSets,fields,Type);
																		$("#chartContainer").show();	
																	
																	}else {
																		alert("Branch No is required to render chart.!!");
																		    $('.dataTables_table').show();
																			$('.dataTables_filter').show();
																			$('.dataTables_info').show();
																			$('.dataTables_paginate').show();
																			$('.report_field_table').show();
																	}
																	
																}else {
																	
																	alert("Chart can't render more then five fields (OR) Insufficient data chart can't render..!!");
																	
																	    $('.dataTables_table').show();
																		$('.dataTables_filter').show();
																		$('.dataTables_info').show();
																		$('.dataTables_paginate').show();
																		$('.report_field_table').show();
																}
                                                              }    
                                                           },
                                                        {
                                                           text : '<i class="fa fa-table" ></i> DataTable',
                                                           action : function(e, dt,node,config) {
                                                               e.preventDefault();
                                                               
                                                                $("#chartContainer").hide();
                                                                $("#compareDateForm").hide();
                                                                $('.dataTables_table').show();
																$('.dataTables_filter').show();
																$('.dataTables_info').show();
																$('.dataTables_paginate').show();
																$('.report_field_table').show();
                                                           }
                                                       },
                                                       {
															text :'<i class="fa fa-compare" ></i> Comparision',
															action : function(e, dt,node,config) {
																e.preventDefault();
																 $("#chartContainer").hide();
																 $('.dataTables_table').hide();
																	$('.dataTables_filter').hide();
																	$('.dataTables_info').hide();
																	$('.dataTables_paginate').hide();
																	$('.report_field_table').hide();
																	$("#date1").val(''); 
																	$("#date2").val('');
																$("#compareDateForm").show();
																 
															}
														}
                                                           ]
											});
						}
					},
					beforeSend : function() {
						$("#drag_message").html("");
						$("#joinTextArea_message").html("");
						 $("#Cmsg1").html("");
						  $("#Cmsg2").html("");
					      $("#Cmsg3").html("");
					      $("#date1").val(''); 
							$("#date2").val('');
						if ($("#drag").hasClass("table_select_error"))
							$("#drag").removeClass("table_select_error");
						if ($("#table_select").hasClass("table_select_error"))
							$("#table_select").removeClass("table_select_error");
						if($("#joinTextArea").hasClass("table_select_error"))
							$("#joinTextArea").removeClass("table_select_error");
						if($("#ondate").hasClass("table_select_error"));
					         $("#ondate").removeClass("table_select_error");
					         $("#message1").html("");
						openModal();
					},
				});

	} else {
		if (!$('#table_select').val()) {
		} else if (!$('#drag').text()) {
			$("#drag_message").html("Please select the columns");
			$("#drag").addClass("table_select_error");
		}
	}
}

function zeroOneFieldsData(charts,arr){
	var label=[];
    var label2=[];
    
    $.each(JSON.parse(charts), function(i, item){
    	label[i] = item[arr[0]];
    	label2[i] = item[arr[1]];
	});
    
    label = label.filter(function( element )  {
		   return element !== undefined;
		});
	
	label2 = label2.filter(function( element ) {
	      return element !== undefined;
	    });
	
	var DatapointsData=[];
	
	for (var j=0 ; j<=label.length-1;j++){
		
		DatapointsData[j]={label:label[j],y:label2[j]};
	}
	return DatapointsData;
}

function zeroTwoFieldsData(charts,arr){
	var label=[];
    var label2=[];
    
    $.each(JSON.parse(charts), function(i, item){
    	label[i] = item[arr[0]];
    	label2[i] = item[arr[2]];
	});
    
    label = label.filter(function( element )  {
		   return element !== undefined;
		});
	
	label2 = label2.filter(function( element ) {
	      return element !== undefined;
	    });
	
	var DatapointsData=[];
	
	for (var j=0 ; j<=label.length-1;j++){
		
		DatapointsData[j]={label:label[j],y:label2[j]};
	}
	return DatapointsData;
}

function zeroThreeFieldsData(charts,arr){
	var label=[];
    var label2=[];
    
    $.each(JSON.parse(charts), function(i, item){
    	label[i] = item[arr[0]];
    	label2[i] = item[arr[3]];
	});
    
    label = label.filter(function( element )  {
		   return element !== undefined;
		});
	
	label2 = label2.filter(function( element ) {
	      return element !== undefined;
	    });
	
	var DatapointsData=[];
	
	for (var j=0 ; j<=label.length-1;j++){
		
		DatapointsData[j]={label:label[j],y:label2[j]};
	}
	return DatapointsData;
}

function zeroFourFieldsData(charts,arr){
	var label=[];
    var label2=[];
    
    $.each(JSON.parse(charts), function(i, item){
    	label[i] = item[arr[0]];
    	label2[i] = item[arr[3]];
	});
    
    label = label.filter(function( element )  {
		   return element !== undefined;
		});
	
	label2 = label2.filter(function( element ) {
	      return element !== undefined;
	    });
	
	var DatapointsData=[];
	
	for (var j=0 ; j<=label.length-1;j++){
		
		DatapointsData[j]={label:label[j],y:label2[j]};
	}
	return DatapointsData;
}




function openModal() {
	document.getElementById('modal').style.display = 'block';
	document.getElementById('fade').style.display = 'block';
}

function closeModal() {
	document.getElementById('modal').style.display = 'none';
	document.getElementById('fade').style.display = 'none';
}
 function dragParams() {
	   var parameter = "";
	
	$('a', $('#drag')).each(function() {
		if (parameter != "") {
			parameter = parameter + $(this).attr('value') + ",";
		} else {
			parameter = $(this).attr('value') + ",";
		}
	});
	return parameter;
}
function formatData() {
	
	var parameter = "", columnNames = "", checkBox = "";
	$('a', $('#drag')).each(function() {
		if (parameter != "") {
			parameter = parameter + $(this).attr('value') + ",";
			columnNames = columnNames + $(this).text() + ",";
		} else {
			parameter = $(this).attr('value') + ",";
			columnNames = columnNames + $(this).text() + ",";
		}
	});
	if ($('#groupby').is(':checked')) {
		$('.groupbycolumns').each(function() {
			if (checkBox != "") {
				
				if ($(this).attr("title").includes("AS")){
	                  var each=$(this).attr("title");
	                  each = each.substring(0,each.indexOf("AS")-1);
	                  checkBox = checkBox + each + ",";
					}
			} else {
				if ($(this).attr("title").includes("AS")){
					  var each=$(this).attr("title");
	                  each = each.substring(0,each.indexOf("AS")-1);
	                  checkBox = each + ",";
				}
				
			}
		});
	}
	var criteria = $("#criteriaTextArea").val(), table1 = $("#table_select").val(),table2=getTablelist().toString(),joinText=$("#joinTextArea").val(),joinType=$("#joinType").val(),ondate=$("#ondate").val(),d1=$("#date1").val(),d2=$("#date2").val();
	
	var jsonObject = {};
	jsonObject["table1"] = table1;
	jsonObject["table2"] = table2;
	jsonObject["parameter"] = parameter;
	jsonObject["columnNames"] = columnNames;
	jsonObject["query"] = criteria;
	jsonObject["groupby"] = checkBox;
	jsonObject["joinFilter"] = joinText;
	jsonObject["joinType"] = joinType;
	jsonObject["ondate"] = ondate;
	jsonObject["dateA"] = d1;
	jsonObject["dateB"] = d2;
	var json = JSON.stringify(jsonObject);
	$('#column').val(columnNames);
	$("#pdf").val(json);
	$("#excel").val(json);
	$(".csv").val(json);
	return json;
}

function getTablelist(){
	
	var tablelist = [];
	var tabletext = [];
	var tableBtns=[];
	var validList=[];
	$("input[name='joincheck']:checkbox").each(function(){
		if ($(this).is(":checked")){
		 tablelist.push($(this).val());
		 tabletext.push($(this).next('label').text().trim());
		}
	});
	
	if(tablelist.length>0){
	for (var i=1; i<=tablelist.length;i++){
		if($("#table_btn_join"+i).val()){
			tableBtns.push($("#table_btn_join"+i).val().replace("(Fields)",'').trim());
		}
	}
	for(var j=0; j<tableBtns.length;j++) {
		   for(var i=0;i<tablelist.length;i++){
		   if(tableBtns[j]==tabletext[i]){
			   validList.push(tablelist[i]);
		     }
		  } 
	  }
	}
	return validList;
}

var col = [], colCode = [];
function CreateTableFromJSON(results,dtA,dtB) {
	 var columns = localStorage.getItem("columsHeads").split(","); //uguh
	  col = [];
	  if(dtA==""&&dtB==""){
		  	col = columns.filter(function( element )  {
			   return element !=="";
			});
		  }else{
			  col = columns.filter(function( element ){
				   return element !=="";
				});
			 var colA=[];
			 for (var i=0;i<col.length;i++){
				 colA.push(col[i]+" "+dtA);
			 }
			 //colA=colA+" ";
			 var colB=[];
			 for (var i=0;i<col.length;i++){
				 colB.push(col[i]+" "+dtB);
			 }
			 var colR=colA.concat(colB);
			  col=colR;
		  }
	  /*if(dtA==""&&dtB==""){
		  	col = columns.filter(function( element )  {
			   return element !=="";
			});
		  }else{
			  col = columns.filter(function( element ){
				   return element !=="";
				});
			 var colA=[];
			 for (var i=0;i<col.length;i++){
				 colA.push(col[i]+" "+dtA);
			 }
			 //colA=colA+" ";
			 var colB=[];
			 for (var i=0;i<col.length;i++){
				 colB.push(col[i]+" "+dtB);
			 }
			 var colR=colA.concat(colB);
			  col=colR;
		  }*/
	  
	/*$.each(results[0].names, function(i, names) {
		if (col.indexOf(names.name) === -1) {
			col.push(names.name);
			//colCode.push(getAppLabelCode(results[0].appLabels,names.name)); //jbjbn
		}
	});*/

	// CREATE DYNAMIC TABLE.
	    var table = document.createElement("table");
	    var thead = table.createTHead();
	    table.classList.add("report_field_table", "display", "nowrap");
	    var tblBody = document.createElement("tbody");
	    table.appendChild(tblBody);
	    var tfoot = table.createTFoot();
	    
	    var tr = thead.insertRow(-1);
	    for (var i = 0; i < col.length; i++) {
	        var th = document.createElement("th"); // TABLE HEADER.
	        th.innerHTML = col[i];
	        tr.appendChild(th);
	    }
	    var tr = tfoot.insertRow(-1);
	    for (var i = 0; i < col.length; i++) {
	        var th = document.createElement("th"); // TABLE Footer.
	        th.innerHTML = " ";
	        tr.appendChild(th);
	    }
	    
	    for (var i = 0; i < results[0].names.length; i++) {
	        tr = tblBody.insertRow(-1);
	        for (var j = 0; j < col.length; j++) {
	            var tabCell = tr.insertCell(-1);
	            if ((col.length - 1) != j)
	                tabCell.innerHTML = results[0].names[i++].field;
	            if ((col.length - 1) == j)
	                tabCell.innerHTML = results[0].names[i].field;
	        }
	    }
	    // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
	    var divContainer = document.getElementById("menu4");
	    divContainer.innerHTML = "";
	    divContainer.appendChild(table);
	}
function createPdf() {
	if ($('#table_select').val() && $('#drag').text()) {
		var json = formatData();
		$.ajax({
			type : "POST",
			url : 'createPdf',
			data : json,
			processData : false,
			contentType : "application/json; charset=utf-8",
			error : function(xhr, status, error) {
				closeModal();
			},
			success : function(response) {
				closeModal();
			},
			beforeSend : function() {
				openModal();
			},
		});
	} else {
		if (!$('#table_select').val()) {
			alert("please select the table");
		} else if (!$('#drag').text()) {
			alert("please select the column");
		}
	}

}

function saveByteArray(pdfName, byte) {
	var blob = new Blob([ byte ], {
		type : "application/pdf"
	});
	var link = document.createElement('a');
	link.href = window.URL.createObjectURL(blob);
	var fileName = pdfName + ".pdf";
	link.download = fileName;
	link.click();
};

function callMetaDataTable() {
	$("#message").html("");
	$("#grag_message").html("");
	
	
	if ($("#table_select").hasClass('table_select_error')) {
		$("#table_select").removeClass("table_select_error");
	}
	if ($("#drag").hasClass('table_select_error')) {
		$("#drag").removeClass("table_select_error");
	}
	$.ajax({
		type : "GET",
		url : 'tables',
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		error : function(xhr, status, error) {
			closeModal();
		},
		success : function(response) {
			closeModal();
			$('#table_select').empty();
			$("#ondate").val('');
			$.each(response, function(i, value) {
				$('#table_select').append(
						$('<option>').text(getAppLabel(response, value.tableName)).attr('value', value.tableName));
			});
			$('#table_select').append($('<option>').text('').attr('value', ''));
			$('#table_select').select2();
			clearAllTableBtnJoins();
			clearAllcolumnsJoin();
			$("#multiJoinTablediv").slideUp();
			$("#filterJoinLabel").slideUp();
			$("#sj").slideUp();
			$("#columns").slideUp("slow");
			
    		//  unchecking jointable selected
    		$("input[name='joincheck']:checkbox").each(function() {
    			if ($(this).is(":checked")){
    				 $(this).prop("checked", false);
    			}
    		});
			
    		var tblabel=response[0].name.trim();
			$("#table_btn").val(tblabel+"(Fields)");
			retriveColumnAjaxCall();
		},
		beforeSend : function() {
			openModal();
		},
	});
}


function retriveColumnAjaxCall() {
    columnbox();
   
            $.ajax({   
    	        type : "POST",
                url : 'retriveColumns',
                dataType : "json",
                data : $('#table_select').val(),
                contentType : "application/json; charset=utf-8",
                error : function(xhr, status, error) {
                    closeModal();
                },
                beforeSend : function() {
                    if ($("#table_select").hasClass("table_select_error"))
                        $("#table_select").removeClass("table_select_error");
                    openModal();
                },
                success : function(response) {
                    clearAllFilterFields();
                    closeModal();
                    localStorage.setItem("dbColumns", JSON.stringify(response));
                    
                 // Creating  Optgroup and appending to Select attr Id #s1 both js and jquery
                     
                  /* var optSelects1 = document.getElementById('s1');
                     var optGroups1 = document.createElement('optgroup');
                     optGroups1.setAttribute('label', $('#table_btn').val().substr(0,$('#table_btn').val().indexOf("(")));
                     optGroups1.setAttribute('id',"s1Id");
                     optSelects1.append(optGroups1);*/
                    
                      var uniqCol = $('#table_btn').val().replace("(Fields)",'').match(/\b(\w)/g).join(''); //
                     
                      $('#s1').append($("<optgroup style='font-size:small'>")
                             .attr({id :"s1Id",label:$('#table_btn').val().substr(0,$('#table_btn').val().indexOf("(")).trim()})		
                             );
                    
                     // Creating  Optgroup and appending to Select attr Id # #pri @join model

                     
                     $('#pri').append($("<optgroup style='font-size:small'>")
                             .attr({id :"priId",label:$('#table_btn').val().substr(0,$('#table_btn').val().indexOf("(")).trim()})		
                             );
                   
                     // Creating  Optgroup and appending to Select attr Id # #pri2 @join model
                      
                      $('#pri2').append($("<optgroup style='font-size:small'>")
                              .attr({id :"pri2Id",label:$('#table_btn').val().substr(0,$('#table_btn').val().indexOf("(")).trim()})		
                              );
                    
                    $.each(response,function(i, value) {
                        
                    	if(isNumber(value)){
                              $('#columns').append($("<li class = 'active'>")
                                           .append($("<a class='btn btn-secondary' style='background-color:#566573;color:white' data-toggle='collapse' aria-expanded='false'>")
                                           .text("("+uniqCol+") "+getAppLabel(response,value.name))
                                           .attr({href:'#'+stringRemove(value.name), value :value.name, id : "drag" +i, }))
                                           .append($("<ul class='collapse list-unstyled'>")
                                           .attr('id',stringRemove(value.name))
                                           .append($("<li>") 
                                           .append($("<a class='btn btn-secondary' style='background-color:#566573;color:white;'>")
                                           .text("("+uniqCol+") "+getAppLabel(response,value.name))
                                           .attr({value:value.name+' AS '+stringRemove(value.name),onclick:'moveButton(this)',id : "drag" +i, })))
                                  
                                           .append($("<li>")
                                           .append($("<a class='btn btn-secondary' style='border-color:green'>")
                                           .text('SUM('+"("+uniqCol+") "+getAppLabel(response,value.name)+')')
                                           .attr({ value:'SUM('+value.name+') As '+'Sum'+stringRemoveOFlabel(value.name),onclick :'moveButton(this)',id : "drag" +i,}))) 
                                           
                                           .append($("<li>")
                                           .append($("<a class='btn btn-secondary' style='border-color:green'>")
                                           .text('AVG('+"("+uniqCol+") "+getAppLabel(response,value.name)+')')
                                           .attr({ value:'AVG('+value.name+') As '+'Avg'+stringRemoveOFlabel(value.name),onclick :'moveButton(this)',id : "drag" +i,}))) 
                                           .append($("<li>") 
                                           .append($("<a class='btn btn-secondary' style='border-color:green'>")
                                           .text('MIN('+"("+uniqCol+") "+getAppLabel(response, value.name)+')')
                                           .attr({value:'MIN('+value.name+') As '+'Min'+stringRemoveOFlabel(value.name),onclick :'moveButton(this)',id : "drag" +i,}))) 
                                           .append($("<li>") 
                                           .append($("<a class='btn btn-secondary' style='border-color:green'>")
                                           .text('MAX('+"("+uniqCol+") "+getAppLabel(response,value.name)+')')
                                           .attr({value:'MAX('+value.name+') As '+'Max'+stringRemoveOFlabel(value.name),onclick :'moveButton(this)',id : "drag" +i,}))) 
                                           )); 
                                        } else if(isRequired(value)) {
                                               $('#columns').append($("<li class = 'active'>")
                                                            .append($("<a type ='button' class ='btn btn-secondary' style='background-color:#EC7063;color:white;' data-toggle = 'dropdown'>")
                                                            .text("("+uniqCol+") "+getAppLabel(response,value.name))
                                                            .attr({ href : '#'+ value.name,value : value.name+' AS '+stringRemove(value.name),onclick : 'moveButton(this)',id : "drag"+ i,})));
                                              } else {
                                            	  
                                            	  $('#columns').append($("<li class = 'active'>")
                                                          .append($("<a type ='button' class ='btn btn-secondary ' style='background-color:#566573;color:white;' data-toggle = 'dropdown'>")
                                                          .text("("+uniqCol+") "+getAppLabel(response,value.name))
                                                          .attr({ href : '#'+ value.name,value : value.name+' AS '+stringRemove(value.name),onclick : 'moveButton(this)',id : "drag"+ i,})));
                                              }
                    	
                                 	// appending to criteria.....
                            
                                        if (isRequired(value)) {
                                        	$('#s1Id').append($('<option></option>')
                                            		.text("")
                                                    .attr({ value : "", }));
                                        	
                                            $('#s1Id').append($('<option></option>')
                                                    .text(getAppLabel(response,value.name))
                                                    .attr({ value : value.name,style : 'color:red;',}));
                                            
                                        } else if (isString(value)) {
                                        	
                                            $('#s1Id').append($('<option></option>')
                                            		.text("")
                                                    .attr({ value : "", }));
                                            		
                                            $('#s1Id').append($('<option></option>')
                                                    .text(getAppLabel(response,value.name))
                                                    .attr({value : value.name, }));
                                        } else {
                                        	
                                        	$('#s1Id').append($('<option></option>')
                                            		.text("")
                                                    .attr({ value : "", }));
                                        	
                                            $('#s1Id').append($('<option></option>')
                                                    .text(getAppLabel(response,value.name))
                                                    .attr({value : value.name,}));
										    }
                                        
                                        
                                        // appending to join keys...
                                        
                                        if (isKeyField(value)) {
                                            
                                            $('#priId').append($('<option></option>')
                                                    .text(getAppLabel(response,value.name))
                                                    .attr({ value : value.name,style : 'color:red;', }));
                                            
                                            $('#pri2Id').append($('<option></option>')
                                                    .text(getAppLabel(response,value.name))
                                                    .attr({ value : value.name,style : 'color:red;',}));
                                            
                                        }
                                        
                                        
									});
                    
       // Code for Correspond Join Table By Mskh 
                    
                 /* var selOpts = "";
                    var selOpts1 ="";
                    for (i=0;i<response[0].joinTables.length;i++)
                    {
                        var val = response[0].joinTables[i]['tableName'];
                        var label = response[0].joinTables[i]['name'];
                        selOpts += "<option value='"+val+"'>"+label+"</option>";
                    }
                    $('#table_select2').empty();
                    selOpts1 += "<option></option>";
                    $('#table_select2').append(selOpts1);
                    $('#table_select2').append(selOpts);*/
                    
                    openNav();
					validateSelectedValue();
					
					
				},
			});
        }
 



function retriveJoinColumnAjaxCall(tablelist,table,tabletext,unchecked,event) {
	// shaik strong validation 
	
	if(unchecked) {
		return false;
	}
	var columnjoin,filterjoin,pri,pri2,uniqCol,dragCon;
	
	if (tablelist.length==1 && tabletext!=null) {
		$("#table_btn_join1").show();
		$("#table_btn_join1").val(tabletext+ "(Fields)");
		uniqCol = tabletext.match(/\b(\w)/g).join(''); // [get First letter from each word to attach on cloumns]
		columnjoin = "columnsJoin1",filterjoin = "filterjoin1",pri="priId1",pri2="pri2Id1",dragCon="a";
	}
	if (tablelist.length == 2 && tabletext!=null) {
		if(addJoinfilterValidation(tablelist,event)){
		$("#table_btn_join2").show();
		$("#table_btn_join2").val(tabletext + "(Fields)");
		uniqCol = tabletext.match(/\b(\w)/g).join(''); // [get First letter from each word to attach on cloumns]
		columnjoin = "columnsJoin2",filterjoin = "filterjoin2",pri="priId2",pri2="pri2Id2",dragCon="b";
		$("#joinTextArea").append(",");
		}else{
			$("#join_message").html("Please add join condition to table: "+$("#table_btn_join1").val().replace("(Fields)",'')+"");
			$("#joinTextArea").addClass("table_select_error");
			if (event.currentTarget.checked) {
				   $(event.currentTarget).prop("checked", false);
				}
			return false;
		}
	}
	if (tablelist.length == 3 && tabletext!=null) {
		if(addJoinfilterValidation(tablelist,event)){
		$("#table_btn_join3").show();
		$("#table_btn_join3").val(tabletext + "(Fields)");
		uniqCol = tabletext.match(/\b(\w)/g).join(''); // [get First letter from each word to attach on cloumns]
		columnjoin = "columnsJoin3",filterjoin = "filterjoin3",pri="priId3",pri2="pri2Id3",dragCon="c";
		$("#joinTextArea").append(",");
		}else{
			$("#join_message").html("Please add join condition to table: "+$("#table_btn_join2").val().replace("(Fields)",'')+"");
			$("#joinTextArea").addClass("table_select_error");
			if (event.currentTarget.checked) {
				   $(event.currentTarget).prop("checked", false);
				}
			return false;
		}
	}
	if (tablelist.length == 4 && tabletext!=null) {
		if(addJoinfilterValidation(tablelist,event)){
		$("#table_btn_join4").show();
		$("#table_btn_join4").val(tabletext + "(Fields)");
		uniqCol = tabletext.match(/\b(\w)/g).join(''); // [get First letter from each word to attach on cloumns]
		columnjoin = "columnsJoin4",filterjoin = "filterjoin4",pri="priId4",pri2="pri2Id4",dragCon="d";
		$("#joinTextArea").append(",");
		}else{
			$("#join_message").html("Please add join condition to table: "+$("#table_btn_join3").val().replace("(Fields)",'')+"");
			$("#joinTextArea").addClass("table_select_error");
			if (event.currentTarget.checked) {
				   $(event.currentTarget).prop("checked", false);
				}
			return false;
		}
	}
	if (tablelist.length == 5 && tabletext!=null) {
		if(addJoinfilterValidation(tablelist,event)){
		$("#table_btn_join5").show();
		$("#table_btn_join5").val(tabletext + "(Fields)");
		uniqCol = tabletext.match(/\b(\w)/g).join(''); // [get First letter from each word to attach on cloumns]
        columnjoin = "columnsJoin5",filterjoin = "filterjoin5",pri="priId5",pri2="pri2Id5",dragCon="e";
        $("#joinTextArea").append(",");
		}else{
			$("#join_message").html("Please add join condition to table: "+$("#table_btn_join3").val().replace("(Fields)",'')+"");
			$("#joinTextArea").addClass("table_select_error");
			if (event.currentTarget.checked) {
				   $(event.currentTarget).prop("checked", false);
				}
			return false;
		}
	}
	if (tablelist.length >=6 && tabletext!=null){
		 alert("Sorry can't exceed join tables more then 5 ..!!!");
		 if (event.currentTarget.checked){
			   $(event.currentTarget).prop("checked", false);
			}
			return false;
	}
	
	
	
	$.ajax({   
        type : "POST",
        url : 'retriveColumns',
        dataType : "json",
        data : table,
        contentType : "application/json; charset=utf-8",
        error : function(xhr, status, error) {
            closeModal();
        },
        beforeSend : function() {
            if ($("#table_select2").hasClass("table_select_error"))
                $("#table_select2").removeClass("table_select_error");
            openModal();
        },
        success : function(response) {
            clearAllFilterJoinFields(columnjoin);
            closeModal();
            localStorage.setItem(columnjoin, JSON.stringify(response));
           // localStorage.setItem("JoindbColumns", JSON.stringify(response));
            
            // Creating  Optgroup and appending to Select attr Id #sj both js and jquery
            
            /* var optSelect = document.getElementById('sj');
             var optGroup = document.createElement('optgroup');
             optGroup.setAttribute('label', tabletext.trim());
             optGroup.setAttribute('id',filterjoin);
             optSelect.append(optGroup);*/
             
        	// sj is select attr id and filterjoin is optgroup id creating dynamically
             $('#sj').append($("<optgroup style='font-size:small'>")
                     .attr({id :filterjoin,label:tabletext.trim()})		
                     );
             
             // Creating  Optgroup and appending to Select attr Id #pri both js and jquery
             
              $('#pri').append($("<optgroup style='font-size:small'>")
                      .attr({id :pri,label:tabletext.trim()})		
                      );
              
              // Creating  Optgroup and appending to Select attr Id #pri2 both js and jquery
               
               $('#pri2').append($("<optgroup style='font-size:small'>")
                       .attr({id :pri2,label:tabletext.trim()})		
                       );
               
               // Creating  Optgroup and appending to Select attr Id #sec both js and jquery
                
                $('#sec').append($("<optgroup style='font-size:small'>")
                        .attr({id :"secId",label:tabletext.trim()})		
                        );
              
            $.each(response,function(i, value) {
                   if(isNumber(value)){
                      $('#'+columnjoin).append($("<li class = 'active'>")
                                   .append($("<a class='btn btn-secondary' style='background-color:#3F729B;color:white' data-toggle='collapse' aria-expanded='false'>")
                                   .text("("+uniqCol+") "+getAppLabel(response,value.name))
                                   .attr({  href:'#'+stringRemove(value.name), value :value.name, id : "drag" +i+dragCon, }))
                                   .append($("<ul class='collapse list-unstyled'>")
                                   .attr('id',stringRemove(value.name))
                                   .append($("<li>") 
                                   .append($("<a class='btn btn-secondary' style='background-color:#3F729B;color:white'>")
                                   .text("("+uniqCol+") "+getAppLabel(response,value.name))
                                   .attr({value:value.name+' AS '+stringRemove(value.name),onclick:'moveButtonJoin(this,'+columnjoin+')',id : "drag" +i+dragCon, })))
                                   
                                    .append($("<li>")
                                    .append($("<a class='btn btn-secondary' style='border-color:green'>")
                                    .text('SUM('+"("+uniqCol+") "+getAppLabel(response,value.name)+')')
                                    .attr({ value:'SUM('+value.name+') As '+'Sum'+stringRemoveOFlabel(value.name),onclick :'moveButtonJoin(this,'+columnjoin+')',id : "drag" +i+dragCon,}))) 
                                   
                                   .append($("<li>")
                                   .append($("<a class='btn btn-secondary' style='border-color:green'>")
                                   .text('AVG('+"("+uniqCol+") "+getAppLabel(response,value.name)+')')
                                   .attr({ value:'AVG('+value.name+') As '+'Avg'+stringRemoveOFlabel(value.name),onclick :'moveButtonJoin(this,'+columnjoin+')',id : "drag" +i+dragCon,}))) 
                                   .append($("<li>") 
                                   .append($("<a class='btn btn-secondary' style='border-color:green'>")
                                   .text('MIN('+"("+uniqCol+") "+getAppLabel(response, value.name)+')')
                                   .attr({value:'MIN('+value.name+') As '+'Min'+stringRemoveOFlabel(value.name),onclick :'moveButtonJoin(this,'+columnjoin+')',id : "drag" +i+dragCon,}))) 
                                   .append($("<li>") 
                                   .append($("<a class='btn btn-secondary' style='border-color:green'>")
                                   .text('MAX('+"("+uniqCol+") "+getAppLabel(response,value.name)+')')
                                   .attr({value:'MAX('+value.name+') As '+'Max'+stringRemoveOFlabel(value.name),onclick :'moveButtonJoin(this,'+columnjoin+')',id : "drag" +i+dragCon,}))) 
                                   )); 
                                } else if(isRequired(value)){
                                       $('#'+columnjoin).append($("<li class = 'active'>")
                                                    .append($("<a type ='button' class ='btn btn-secondary ' style='background-color:#EC7063;color:white;' data-toggle = 'dropdown'>")
                                                    .text("("+uniqCol+") "+getAppLabel(response,value.name))
                                                    .attr({ href : '#'+ value.name,value : value.name+' AS '+stringRemove(value.name),onclick : 'moveButtonJoin(this,'+columnjoin+')',id : "drag"+ i+dragCon,})));
                                      } else {
                                    	  $('#'+columnjoin).append($("<li class = 'active'>")
                                                  .append($("<a type ='button' class ='btn btn-secondary ' style='background-color:#3F729B;color:white' data-toggle = 'dropdown'>")
                                                  .text("("+uniqCol+") "+getAppLabel(response,value.name))
                                                  .attr({ href : '#'+ value.name,value : value.name+' AS '+stringRemove(value.name),onclick : 'moveButtonJoin(this,'+columnjoin+')',id : "drag"+ i+dragCon,})));
                                      }
                              // appending to join criteria...
                   
                                if (isRequired(value)) {
                                	
                                	/*    $('#sj').append($('<option></option>')
                                    		.text("")
                                            .attr({ value : "", }));    */
                                	
                                	// sj is select attr id and filterjoin is optgroup id
                                	
                                	$('#'+filterjoin).append($('<option></option>')
                                    		.text("")
                                            .attr({ value : "", }));
                                	
                                    $('#'+filterjoin).append($('<option></option>')
                                            .text(getAppLabel(response,value.name))
                                            .attr({ value : value.name,style : 'color:red;', }));
                                   
                                } else if (isString(value)) {
                                	
                                	$('#'+filterjoin).append($('<option></option>')
                                    		.text("")
                                            .attr({ value : "", }));
                                	
                                    $('#'+filterjoin).append($('<option></option>')
                                            .text(getAppLabel(response,value.name))
                                            .attr({value : value.name,style : 'color:green;',}));
                                } else {
                                	
                                	$('#'+filterjoin).append($('<option></option>')
                                    		.text("")
                                            .attr({ value : "", }));
                                	
                                    $('#'+filterjoin).append($('<option></option>')
                                            .text(getAppLabel(response,value.name))
                                            .attr({value : value.name,style : 'color:green;',}));
								    }
                                // appending to join keys...
                                
                             // here appending to same pri which is from basetable ajaxcolums for getting under same textbox
                                if (isKeyField(value)) {
                                
                                $('#'+pri).append($('<option></option>')
                                        .text(getAppLabel(response,value.name))
                                        .attr({ value : value.name,style : 'color:red;font-size:small', }));
                                
                                $('#'+pri2).append($('<option></option>')
                                        .text(getAppLabel(response,value.name))
                                        .attr({ value : value.name,style : 'color:red;font-size:small', }));
                                
                               
                                $('#secId').append($('<option></option>')
                                        .text(getAppLabel(response,value.name))
                                        .attr({ value : value.name,style : 'color:red;font-size:small', }));
                                
                                }
                                
							});
        	    /*openNav();*/
			validateSelectedValue();
			
		      	// Removing drag area fields when multiple selection... // currently not using
			
			                 /* $('a', $('#drag')).each(function() {
									if ($(this).text().includes("(S)")) {
										$(this).remove();
							        }
					           });*/
			                  
		     },
	});
}

function JoinDescription(e){
	var val="";
	val=$("#joinType").val();
	if(val=="JOIN"){
		$(".simplejoin").show();
		$(".leftjoin").hide();
		$(".rightjoin").hide();
		$(".fulljoin").hide();
	}
	if(val=="LEFT JOIN"){
		$(".simplejoin").hide();
		$(".leftjoin").show();
		$(".rightjoin").hide();
		$(".fulljoin").hide();
	}
	if(val=="RIGHT JOIN"){
		$(".simplejoin").hide();
		$(".leftjoin").hide();
		$(".rightjoin").show();
		$(".fulljoin").hide();
	}
	if(val=="FULL JOIN"){
		$(".simplejoin").hide();
		$(".leftjoin").hide();
		$(".rightjoin").hide();
		$(".fulljoin").show();
	}
}


function clearAllTableBtnJoins(){
	for(var i=1;i<=5;i++){
		$("#table_btn_join"+i).val('');
		$("#table_btn_join"+i).hide();
	}
}

function baseTableSelectOperation(){
	
	if ($("#table_select").val()) {
		$("#ondate").val('');
		$("#table_btn").show();
		$("#multiJoinTablediv").hide();
		$("#filterJoinLabel").hide();
		$("#sj").hide();
		
		clearAllTableBtnJoins();
		
		$("input[name='joincheck']:checkbox").each(function() {
			if ($(this).is(":checked")){
				 $(this).prop("checked", false);
			}
		});
		
		var btn = $("#table_select option:selected").text();
		$("#table_btn").val(btn.trim() + "(Fields)");

		if ($("#table_select2").val()) {
			$("#table_select2").val("-1");
			$("#table_select2").select2();

			// removing key fields except first "" value...
			$('#sec option[value!=""]').remove();
			$('#sec2 option[value!=""]').remove();
		}

	} else {
		$("#multiJoinTablediv").hide();
		$("#filterJoinLabel").hide();
		$("#sj").hide();
		
		clearAllTableBtnJoins();
		
		 $('#s1 optgroup[value!=""]').remove();
		 $('#s1 option[value!=""]').remove();
		
		$("input[name='joincheck']:checkbox").each(function() {
			if ($(this).is(":checked")){
				 $(this).prop("checked", false);
			}
		});
		
		closeNav();
		$("#columns").empty();
		var btn = $("#table_select option:selected").text();
		$("#table_btn").val(btn.trim());
		
		
		
	}
}




function moveButton(elem) {
   // var regExp = /\(([^)]+)\)/;
	var regExp = /SUM|AVG|MAX|MIN/i;
    var matches = regExp.exec($(event.target).text());
    if ($(elem).parent().attr("id") == "drag" && matches == null) {
        $($("<li class = 'active'>").append(elem)).detach().appendTo('#columns');
    } else if ($(elem).parent().attr("id") == "drag" && matches != null) {
        $($("<li class='active'>").append(elem)).detach().appendTo('#'+elem.id);
    } else {
        
        $(elem).detach().appendTo('#drag');
    }
}

function moveButtonJoin(elem,columnjoin) {
   // var regExp = /\(([^)]+)\)/;
    var regExp = /SUM|AVG|MAX|MIN/i;
    var matches = regExp.exec($(event.target).text());
    if ($(elem).parent().attr("id") == "drag" && matches == null) {
        $($("<li class = 'active'>").append(elem)).detach().appendTo('#'+columnjoin.id);
    } else if ($(elem).parent().attr("id") == "drag" && matches != null) {
        $($("<li class='active'>").append(elem)).detach().appendTo('#'+elem.id);
    } else {
        $(elem).detach().appendTo('#drag');
    }
}






function stringRemove(value){
	 var str = value;
	// var res= str.substring(str.indexOf(".")+1, str.length);
	 var res =str.replace(".","");
	 return res;
}

String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}

function stringRemoveOFlabel(value){
	 var str = value;
	 var res= "_"+"Of"+"_"+str.substring(str.indexOf(".")+1, str.length).capitalize();
	 return res;
}

function getAppLabel(response, value) {
	var appLabel = value;
	$.each(response[0].appLabels, function(i, label) {
		if (label && label.labelCode == appLabel)
			appLabel = label.appLabel;
		return appLabel;
	});
	return appLabel;
}


/* function getAppLabelCode(response, value) { var appLabel = value;
 $.each(response, function(i, label) { if (label && label.appLabel ==
 appLabel){ appLabel = label.labelCode; return label.labelCode; } }); return
 appLabel; }*/

 function isRequired(value) {
	var isActive = false;
	if (value && value.required == 'Y') {
		return isActive = true;
	}
	return isActive;
}
 function isKeyField(value) {
		var isActive = false;
		if (value && value.keyField == 'Y') {
			return isActive = true;
		}
		return isActive;
	}
 
function clearAllFields() {
	//retriveJoinColumnAjaxCall();
	retriveColumnAjaxCall();
	clearAllcolumnsJoin();
	$("#ondate").val('');
	clearAllTableBtnJoins();
	$("#columns").slideUp("slow");
	$("input[name='joincheck']:checkbox").each(function() {

		if ($(this).is(":checked")){
		 $(this).prop("checked", false);
		}
		});

		$("#multiJoinTablediv").slideUp();
		$("#filterJoinLabel").slideUp();
		$("#sj").slideUp();
	
	var divCoulmnsContainer = document.getElementsByClassName("table_field");
	for (var i = 0; i < divCoulmnsContainer.length; i++) {
		divCoulmnsContainer[i].innerHTML = "";
	}
	document.getElementById("joinTextArea").innerHTML = "";
	document.getElementById("criteriaTextArea").innerHTML = "";
	var divContainer = document.getElementById("menu4");
	divContainer.innerHTML = "";

	var table = $('.report_field_table').DataTable();
	table.clear();
	document.getElementById("s1").empty();
	document.getElementById("sj").empty();
	
	 $('#s1 option[value!=""]').remove();
	 $('#s1 opgroup[value!=""]').remove();
	 $('#sj option[value!=""]').remove();
	 $('#sj opgroup[value!=""]').remove();
	
	 $('#pri option[value!=""]').remove();
	 $('#pri optgroup[value!=""]').remove();
	 $('#pri2 option[value!=""]').remove();
	 $('#pri2 opgroup[value!=""]').remove();
	 $('#sec option[value!=""]').remove();
	 $('#sec opgroup[value!=""]').remove();
	
}
function clearAllcolumnsJoin(){
	for(var i=1;i<=5;i++){
		$("#columnsJoin"+i).empty();
	}
}


function clearAllFilterFields() {
	var divCoulmnsContainer = document.getElementsByClassName("table_field");
	for (var i = 0; i < divCoulmnsContainer.length; i++) {
		divCoulmnsContainer[i].innerHTML = "";
	}
	
	
	document.getElementById("joinTextArea").innerHTML = "";
	document.getElementById("criteriaTextArea").innerHTML = "";
	var divContainer = document.getElementById("menu4");
	divContainer.innerHTML = "";
	 $("#compareDateForm").hide();
	 $("#columns").slideUp("slow");
	 $("#columns").empty();
	 clearAllcolumnsJoin();
	 $("#ondate").val("");
	 $("#join_message").html("");
	 if($("#joinTextArea").hasClass("table_select_error")){
		  $("#joinTextArea").removeClass("table_select_error");
	 }
	 
	  $("#Cmsg1").html("");
	  $("#Cmsg2").html("");
      $("#Cmsg3").html("");
	 // claering both optgroup and option value except empty space
	 $('#s1 optgroup[value!=""]').remove();
	 $('#s1 option[value!=""]').remove();
	 $('#sj optgroup[value!=""]').remove();
	 $('#sj option[value!=""]').remove();
	
	 $('#pri optgroup[value!=""]').remove();
	 $('#pri option[value!=""]').remove();
	 $('#pri2 optgroup[value!=""]').remove();
	 $('#pri2 option[value!=""]').remove();
	 $('#sec optgroup[value!=""]').remove();
	 $('#sec option[value!=""]').remove();
	
	var table = $('.report_field_table').DataTable();
	table.clear();
}

function clearAllFilterJoinFields(columnjoin) {
	
	//document.getElementById("joinTextArea").innerHTML = "";
	//document.getElementById("criteriaTextArea").innerHTML = "";
	 $("#Cmsg1").html("");
	  $("#Cmsg2").html("");
     $("#Cmsg3").html("");
	var divContainer = document.getElementById("menu4");
	divContainer.innerHTML = "";
	
	$("#"+columnjoin).slideUp("slow");
	$("#"+columnjoin).empty();
	
	//$("#sj").empty();
	
	// removing optgroup and options for every request except empty space
	$('#sec optgroup[value!=""]').remove();
	$('#sec option[value!=""]').remove();
	
	//$('#sec2 option[value!=""]').remove();
	
	var table = $('.report_field_table').DataTable();
	table.clear();
}
function addBetweenFilter() {
	if ((isCriteriaDate($('#s1').val()) && ($('#s2').val() != "Fromto"))) {
		$('#iptext').hide();
		$('#fromToDiv').hide();
		$('#iptextdateDiv').show();
	} else if ($('#s2').val() == "between") {
		$('#iptextdateDiv').hide();
		$('#iptext').hide();
		$('#betweenDiv').show();
	} else if ($('#s2').val() == "Fromto") {
		$('#iptextdateDiv').hide();
		$('#iptext').hide();
		$('#fromToDiv').show();
	} else {
		$('#iptextdateDiv').hide();
		$('#iptext').show();
		$('#betweenDiv').hide();
		$('#iptext').show();
		$('#fromToDiv').hide();
	}
}

function groupBy() {
	/*if ($(e.target).is(':checked')) {*/
	if ($('#groupby').is(':checked')) {
		$('a', $('#drag')).each(
				function() {
					if (!isAggregate($(this).attr('value'))) {
						var html = '<span class="groupbycolumns" title="'
								+ $(this).attr('value') + '"/>';
						$('#groupby').append(html);
					}
				});
	}
}

function saveFavouriteQuery(e) {
	var obj = JSON.parse($('#pdf').val());
	obj.qryTitle = $('#qryTitle').val();
	obj.purpose = $('#purpose').val();
	obj.description = $('#description').val();
	var json = JSON.stringify(obj);

	if ($("#qryTitle").val() == "") {
				$("#qryTitle_message").html("This field is required");
				$("#qryTitle").addClass("table_select_error");
				return false;
			}else{
					$("#qryTitle_message").html("");
					$("#qryTitle").removeClass("table_select_error");
			}
	
	$.ajax({
		type : 'POST',
		url : 'favourite',
		data : json,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		error : function(xhr, status, error) {
			console.log(xhr.responseText);
			var err = xhr.responseText;
			if (err.toLowerCase().indexOf("session_timed_out") >= 0) {
				window.location = "login.html?statusCheck=SessionExpired";
			}
		},
		beforeSend : function() {

		},
		success : function(response) {
			alert(response.saved);
			$('#saveFavourite').hide();
			$('.modal-backdrop').hide();
		},
		complete : function() {
		}
	});
}
function columnbox() {
	var data = $('#table_select').val();
	if (data) {
		$("#table_field").show();
		$("#table_field1").show();
	} else {
		$("#table_field").hide();
		$("#table_field1").show();
	}
}
function processLoginForm() {
	openModal();
	var name = $('#namkName').val();
	if (name == 'Please Select Bank Name' && $('#tellerid').val() == '501') {
		alert("please select the bank");
		closeModal();
		return false;
	} else {
		$("#loginform1").submit();
		return true;
	}

}
function hideBranchDiv(e) {
	if ($('#selectall').val() == 'isSelectall'
			|| $('#consolidated').val() == 'isConsolidated') {
		$('.branchdiv').hide();
		$('.branchrangediv').hide();

	} else {
		$('.branchdiv').show();
	}
}

function addRangeDiv(e) {
	if ($('#rangeid').val() == 'isRangetrue') {
		$('.branchrangediv').show();
		$('.branchdiv').show();

	} else {

		$('#branchcd').attr('value', '');
		$('.branchrangediv').hide();
	}
	document.getElementById('tobranchcddiv').style.display = 'block';
}

function submitstaticForm() {
	if (!$('#bankcd').val() == '') {
		if ($('#selectall').is(':checked')) {
			$('#errMessage').html("");
			$('#branchcd').val('');
		}
		if ($('#branchcd').val() == '' && !$('#selectall').is(':checked') && !$('#consolidated').is(':checked')) {
			$('#errMessage').html('*Please enter branch code');
			return false;
		}

		if ($('#rangeid').is(':checked')) {
			if ($('#tobranchcd').val() == '') {
				$('#errMessage').html("*Please enter tobranch code");
				return false;
			}
		}

		if (($('#textid').is(':checked')) || ($('#pdfid').is(':checked'))) {
			$('#errMessage').html("");
			$("#staticformid").submit();
		} else {
			$('#errMessage').html("*Please select file format");
		}
	}
	$('#staticformid').each(function() {
		if ($('#bankcd').val() == '') {
			this.reset();
		}
	});
}


function getViewDetails(e) {
	$.ajax({
		type : "POST",
		url : 'viewDeatils',
		processData : false,
		contentType : "application/json; charset=utf-8",
		error : function(xhr, status, error) {
		},
		success : function(response) {
			for (var i = 0; i < response.length; i++) {
				$('#viewdtls').append('<tr><td>'+getAppLabel(response,response[i].viewName)  /*response[i].viewName*/+'</td><td>'+response[i].description+'</td>');
			}
		},
		beforeSend : function() {
		},
	});
}

function Piechart(DataSets,fields,Type){
	
    var chart = new CanvasJS.Chart("chartContainer", {
    		width : "1048",
    		height : "450",
            exportEnabled: true,
            animationEnabled: true,
            theme: "light1", 
            
            title:{
                text: "Bank_Transaction Details",
                fontSize: 20,
                padding: 10,
                verticalAlign: "bottom",
            },
            axisY: {
                title: fields[1]+" , "+fields[2]+" , "+fields[3]+" , "+fields[4]
            },
            axisX: {
                title: fields[0]
            },
            data: [{        
                type: Type,
                lineColor:"red",
                startAngle: 20,
                toolTipContent: "<b> "+fields[0]+":{label} </b>"+fields[1]+": {y}",
                legendText: "{label}",
                indexLabelFontSize: 12,
                //indexLabel: "{label} - {y}",
                dataPoints:DataSets[0]
            },
            {        
                type: Type,
                startAngle: 20,
                toolTipContent: "<b> "+fields[0]+":{label} </b>"+fields[2]+": {y}",
                legendText: "{label}",
                indexLabelFontSize: 12,
                //indexLabel: "{label} - {y}",
                dataPoints:DataSets[1]
            }, {        
                type: Type,
                startAngle: 20,
                toolTipContent: "<b> "+fields[0]+":{label} </b>"+fields[3]+": {y}",
                legendText: "{label}",
                indexLabelFontSize: 12,
                //indexLabel: "{label} - {y}",
                dataPoints:DataSets[2]
            }, {        
                type: Type,
                startAngle: 20,
                toolTipContent: "<b> "+fields[0]+":{label} </b>"+fields[4]+": {y}",
                legendText: "{label}",
                indexLabelFontSize: 12,
                //indexLabel: "{label} - {y}",
                dataPoints:DataSets[3]
            }]
        });
        
        chart.render();
        if($('.canvasjs-chart-credit'))
             $('.canvasjs-chart-credit').addClass('hide');
        
        
        
}
 
function downloadValidation(){
	var val=$(this).attr("value"); // which gives an id of form to submit
	var tables = getTablelist();
	tables.push($("#table_select").val());
	$.ajax({
	    type: "POST",
	    url:'iscriteria_tables',
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    success: function(response){
	    tablelist=response;
	   	if($("#criteriaTextArea").val().trim()=="") {
	   		     for(var i=0; i<tables.length; i++) { 
	   		    	 for(j=0;j<tablelist.length;j++)  {
	   		 		   if(tables[i].trim()==tablelist[j].trim()){
	   		 	    alert("Please add filter criteria to --> "+tablelist[j].trim());
	   		 		$("#Cmsg1").html("Please add criteria..!!   => Click on:");
	   		 	    $("#Cmsg2").html("Transmit");
	   		        $("#Cmsg3").html("Then download.");
	   		 			return false;
	   		 		}
	   		 	}
	   	 	}	
	   		$("#"+val).submit();
	   	}	
	else{
	   		var json = $("#excel").val();
	   		var jsonObject = JSON.parse(json);
			var criteria_old = jsonObject['query'];
			var criteria_new = $("#criteriaTextArea").val().trim();
			if (criteria_old.trim()==criteria_new.trim()){
				$("#"+val).submit();
			}else{
				alert("please Click on Transmit then proceed...");
			}
	      }
	    }
	});
}

function compareData(e){
	if($("#date1").val()!=""&&$("#date2").val()!=""){
		//submitCompareDate(e);
		var columnNames = "", checkBox = "";
		$('a', $('#drag')).each(function() {
			columnNames = columnNames + $(this).text() + ",";
	});
		var array= columnNames.substring(0, columnNames.length - 1).split(",");
	    var columnFields="";
		$.each(array,function(i){
	  if(array[i].includes("Branch")||array[i].includes("SUM")||array[i].includes("Run")){
		 columnFields+=array[i]+",";
		}else 
		{
		  columnFields="";
		  return false;
		}
	});  

	if (columnFields.includes("Branch") && columnFields.includes("SUM") && columnFields.includes("Run")){
	 submitCf(e);
	}else{
	  alert("Please select only Branch No, SUM|AVG|MIN(AggrigateFun's)&& RunDate");
	}
		
	}else{
		alert("date1 && date2 can't be empty...!!!");
	}
	
	
}

/*function onDateSet(){
	 var date = new Date();
	 date.setDate(date.getDate() - 1);
    
	 month = '' + (date.getMonth() + 1),
     day = '' + date.getDate(),
     year = date.getFullYear();

 if (month.length < 2) 
     month = '0' + month;
 if (day.length < 2) 
     day = '0' + day;
 
 var d = [day,month,year].join('-');
	
	$("#ondate").val(d);
}
*/




////////////////////////////////////////
/*if(isNumber(value)){
	 $('#columns').append($("<li class = 'active'>")
     .append($("<a class='btn btn-success' data-toggle='collapse' aria-expanded='false'>")
	 .text(getAppLabel(response, value.name)) 
	 .attr({ href:'#'+value.name, value : value.name, 
      onclick :'moveButton(this)', id : "drag" + i, }))
	 .append($("<ul class='collapse list-unstyled'>").attr('id',value.name)
	 .append($("<li>")
	 .append($("<a class='btn  btn-success'>") 
	 .text(getAppLabel(response,value.name))
	 .attr({value:value.name,onclick
	 :'moveButton(this)'}))) 
	 .append($("<li>")
	 .append($("<a class='btn btn-success'>")  
	 .text('AVG('+ getAppLabel(response,value.name)+')')
	 .attr({value:'AVG('+value.name+') As '+value.name,
     onclick :'moveButton(this)'})))
     .append($("<li>")
     .append($("<a class='btn btn-success'>")   
     .text('MIN('+getAppLabel(response,value.name)+')').attr({value:'MIN('+value.name+') As '+value.name,
     onclick :'moveButton(this)'}))) 
     .append($("<li>") .append($("<a class='btn btn-success'>")
     .text('MAX('+getAppLabel(response,value.name)+')').attr({value:'MAX('+value.name+') As '+value.name,
     onclick :'moveButton(this)'})
	   )) )); }else{*/
	