var table;
function retriveData(){
	
	var jsonObject = {};
	jsonObject["brcdBankCd"] = $('#Bancd').val();
	jsonObject["reportDate"] = $('#ReportDate').val();
	jsonObject["branchCode"] = $('#BranchCode').val();
	jsonObject["acctNo"] = $('#Number').val();
	var jsonObj = JSON.stringify(jsonObject);
	
	$.ajax({
		type : "POST",
		url : 'retriveBranchInfo',
		data:jsonObj,
		dataType : "json",
		async: "false",
		processData : false,
		contentType : "application/json; charset=utf-8",
		
		error : function(xhr, status, error) {
		},
		
		
		
		
		success : function(response) {
//			var table;
			$('.infolist').removeClass('active');
			$('.prolist').addClass('active');
			$('#info1').removeClass('active in');
			$('#productinfo').addClass('active in');
			
for (var i = 0; i < response.length; i++) {
				
				$('#datainfo').append('<tr><td>'+response[i].id.brcdBankCd+'</td><td>'+response[i].id.reportDate+'</td><td>'+response[i].id.branchCode+'</td><td>'+response[i].id.acctNo+'</td><td style="text-align: center; color: blue">'+
						'<button type="button"  id="btnHistory" class="btn-details btn btn-info btn-sm"><span class="glyphicon glyphicon-pencil"></span></button> '+'</td><td style="text-align: center;">'+'<button type="button" id="btndelte" class=" btn btn-info btn-sm" style="background-color: #660066; border: 1px solid transparent;"><span class="glyphicon glyphicon-trash"></span></button>'+'</td><td class="hide">'+response[i].module+'</td><td class="hide">'+response[i].circle+'</td><td class="hide">'+response[i].region+'</td><td class="hide">'+response[i].branchName+'</td><td class="hide">'+response[i].segment
						
						+'</td><td class="hide">'+response[i].cifNo+'</td><td class="hide">'+response[i].sector+'</td><td class="hide">'+response[i].facilityType+'</td><td class="hide">'+response[i].borrName+'</td><td class="hide">'+response[i].limitAmt+'</td><td class="hide">'+response[i].dp+'</td><td class="hide">'+response[i].irregularAmt+'</td><td class="hide">'+response[i].natOfBills+'</td><td class="hide">'+response[i].securityAmt+'</td><td class="hide">'
						
						+response[i].chargesCreated+'</td><td class="hide">'+response[i].primaryTang+'</td><td class="hide">'+response[i].collateralTang+'</td><td class="hide">'+response[i].totalTang+'</td><td class="hide">'+response[i].constituent+'</td><td class="hide">'+response[i].allocated+'</td><td class="hide">'
						+response[i].inttRate+'</td><td class="hide">'+response[i].crSumm+'</td><td class="hide">'+response[i].irregularDate+'</td><td class="hide">'+response[i].lastCrDate+'</td><td class="hide">'+response[i].crr+'</td><td class="hide">'
						+response[i].insuDate+'</td><td class="hide">'+response[i].insuAmt+'</td><td class="hide">'+response[i].receEcgc+'</td><td class="hide">'+response[i].sancDate+'</td><td class="hide">'+response[i].stockStmntDate+'</td><td class="hide">'+response[i].totOs+'</td><td class="hide">'+response[i].secuTangAsset+'</td><td class="hide">'+response[i].bankSecu+'</td><td class="hide">'+response[i].secureEcgc+'</td><td class="hide">'+response[i].unsecured+'</td><td class="hide">'+response[i].assetSubclass+'</td><td class="hide">'+response[i].pbRd+'</td><td class="hide">'+response[i].unrealisedIntt+'</td><td class="hide">'
						+response[i].npaDate+'</td><td class="hide">'+response[i].inca+'</td><td class="hide">'+response[i].provAmt+'</td><td class="hide">'+response[i].provUnsec+'</td><td class="hide">'+response[i].inttSusp+'</td><td class="hide">'
						+response[i].adjUnsecuPortion+'</td><td class="hide">'+response[i].secuPortion+'</td><td class="hide">'+response[i].rstrctrProv+'</td><td class="hide">'+response[i].rstrctrDate+'</td><td class="hide">'
						+response[i].oneNpa+'</td></tr>');
		
			 
			}
				    
			$(document).on("click", "#btnHistory", function(){
				jQuery.noConflict();
				$('#id02').show();
				});
			$(document).on("click", "#btndelte", function() {
				jQuery.noConflict();
				
				var currentRow=$(this).closest("tr");
				var brcdbankcd1=currentRow.find("td:eq(0)").text(); 
			    var reportdte=currentRow.find("td:eq(1)").text();
				var branchcode=currentRow.find("td:eq(2)").text(); 
			    var acctno=currentRow.find("td:eq(3)").text();
			    $("#brcdbancd").val(brcdbankcd1);
				$("#repdate").val(reportdte);
			    $("#branchCode1").val(branchcode);
				$("#AccNo").val(acctno);
				$('#myModal').show();
			});
//			$(document).ready(function() {
				table=$('#example').DataTable({});
//				table.clear();
//				table.draw();
//			});
			
		},
		beforeSend : function() {
		},
	});
	
}



function midifyPopup(e, w) {
	
	var currentRow = $(w).closest("tr");
	var BankCd = currentRow.find("td:eq(0)").text();
	var reportdate = currentRow.find("td:eq(1)").text();
	var branchcode = currentRow.find("td:eq(2)").text();
	var acctno = currentRow.find("td:eq(3)").text();

	var module = currentRow.find("td:eq(6)").text();
	var circle = currentRow.find("td:eq(7)").text();
	var region = currentRow.find("td:eq(8)").text();
	var BranchName = currentRow.find("td:eq(9)").text();

	var segment = currentRow.find("td:eq(10)").text();
	var cifNo = currentRow.find("td:eq(11)").text();
	var sector = currentRow.find("td:eq(12)").text();
	var facilityType = currentRow.find("td:eq(13)").text();

	var borrName = currentRow.find("td:eq(14)").text();
	var limitAmt = currentRow.find("td:eq(15)").text();
	var dp = currentRow.find("td:eq(16)").text();
	var irregularAm = currentRow.find("td:eq(17)").text();

	var natOfBills = currentRow.find("td:eq(18)").text();
	var securityAmt = currentRow.find("td:eq(19)").text();
	var chargesCreated = currentRow.find("td:eq(20)").text();
	var primaryTang = currentRow.find("td:eq(21)").text();

	var collateralTang = currentRow.find("td:eq(22)").text();
	var totalTang = currentRow.find("td:eq(23)").text();
	var constituent = currentRow.find("td:eq(24)").text();
	var allocated = currentRow.find("td:eq(25)").text();

	var inttRate = currentRow.find("td:eq(26)").text();
	var crSumm = currentRow.find("td:eq(27)").text();
	var irregularDate = currentRow.find("td:eq(28)").text();
	var lastCrDate = currentRow.find("td:eq(29)").text();

	var crr = currentRow.find("td:eq(30)").text();
	var insuDate = currentRow.find("td:eq(31)").text();
	var insuAmt = currentRow.find("td:eq(32)").text();
	var receEcgc = currentRow.find("td:eq(33)").text();

	var sancDate = currentRow.find("td:eq(34)").text();
	var stockStmntDate = currentRow.find("td:eq(35)").text();
	var totOs = currentRow.find("td:eq(36)").text();
	var secuTangAsset = currentRow.find("td:eq(37)").text();

	var bankSecu = currentRow.find("td:eq(38)").text();
	var secureEcgc = currentRow.find("td:eq(39)").text();
	var unsecured = currentRow.find("td:eq(40)").text();
	var assetSubclass = currentRow.find("td:eq(41)").text();

	var pbRd = currentRow.find("td:eq(42)").text();
	var unrealisedIntt = currentRow.find("td:eq(43)").text();
	var npaDate = currentRow.find("td:eq(44)").text();
	var inca = currentRow.find("td:eq(45)").text();

	var provAmt = currentRow.find("td:eq(46)").text();
	var provUnsec = currentRow.find("td:eq(47)").text();
	var inttSusp = currentRow.find("td:eq(48)").text();
	var adjUnsecuPortion = currentRow.find("td:eq(49)").text();

	var secuPortion = currentRow.find("td:eq(50)").text();
	var rstrctrProv = currentRow.find("td:eq(51)").text();
	var rstrctrDate = currentRow.find("td:eq(52)").text();
	var oneNpa = currentRow.find("td:eq(53)").text();
	var rec_status = currentRow.find("td:eq(54)").text();

	var REC_STATUS = currentRow.find("td:eq(54)").text();

	$("#BankCd").val(BankCd);
	$("#reportdate").val(reportdate);
	$("#branchcode").val(branchcode);
	$("#accno").val(acctno);

	$("#module").val(module);
	$("#circle").val(circle);
	$("#region").val(region);
	$("#branchname").val(BranchName);

	$("#segment").val(segment);
	$("#cifno").val(cifNo);
	$("#sector").val(sector);
	$("#facility Type").val(facilityType);

	$("#BorrName").val(borrName);
	$("#Limitamt").val(limitAmt);
	$("#dp").val(dp);
	$("#irregularamt").val(irregularAm);

	$("#natofbills").val(natOfBills);
	$("#securityamt").val(securityAmt);
	$("#chargescreated").val(chargesCreated);
	$("#primaryTang").val(primaryTang);
	$("#collateralTang").val(collateralTang);
	$("#toatlTang").val(totalTang);
	$("#constituent").val(constituent);
	$("#allocated").val(allocated);
	$("#inttrate").val(inttRate);

	$("#crsumm").val(crSumm);
	$("#irregulardate").val(irregularDate);
	$("#lastcrdate").val(lastCrDate);
	$("#crr").val(crr);

	$("#insudate").val(insuDate);
	$("#insuamt").val(insuAmt);
	$("#receecgc").val(receEcgc);
	$("#sancDate").val(sancDate);

	$("#stockstmtdate").val(stockStmntDate);
	$("#totos").val(totOs);
	$("#secutandasset").val(secuTangAsset);
	$("#BankSecu").val(bankSecu);

	$("#SecuEcgc").val(secureEcgc);
	$("#Unsecured").val(unsecured);
	$("#assetsubclass").val(assetSubclass);
	$("#pbrd").val(pbRd);
	$("#UnrealisedIntt").val(unrealisedIntt);

	$("#npadate").val(npaDate);
	$("#inca").val(inca);
	$("#Provamt").val(provAmt);
	$("#provUnsec").val(provUnsec);

	$("#initsusp").val(inttSusp);
	$("#adjunsecuportion").val(adjUnsecuPortion);
	$("#secuportion").val(secuPortion);
	$("#rstrctrprov").val(rstrctrProv);

	$("#rstrctrdate").val(rstrctrDate);
	$("#onenpa").val(oneNpa);
	/* $("#recstatus").val(rec_status); */
	/*
	 * var currentRow=$(w).closest("tr"); var values = []; var count = 0;
	 * 
	 * $(currentRow).find("td").each(function () { values[count] =
	 * $(currentRow).text(); count++;
	 * 
	 * });
	 */

}

function updateticketdetails(e) {

	var url = $("#reportForm").attr("action");
	/* var obj=JSON.stringify($("#reportForm").val()); */
	
	var jsonObj = createJsonObject();
	
	$.ajax({
		type : "POST",
		data : jsonObj,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		url : url,
		error : function(xhr, status, error) {
			var err = xhr.responseText;

		},
		success : function(response) {
			//window.location.reload();
			//var currentRow = $(this).closest("tr")
			//$('#datainfo').empty();
			var i = 0;
			var t = document.getElementById('datainfo');
			$("#datainfo tr").each(function() {
				if(response.id.acctNo == $(t.rows[i].cells[3]).text()){
					$(t.rows[i].cells[0]).html(response.id.brcdBankCd);
					$(t.rows[i].cells[1]).html(response.id.reportDate);
					$(t.rows[i].cells[2]).html(response.id.branchCode);
					$(t.rows[i].cells[3]).html(response.id.acctNo);
					
					$(t.rows[i].cells[6]).html(response.module);
					$(t.rows[i].cells[7]).html(response.circle);
					$(t.rows[i].cells[8]).html(response.region);
					$(t.rows[i].cells[9]).html(response.branchName);
					$(t.rows[i].cells[10]).html(response.segment);
					$(t.rows[i].cells[11]).html(response.cifNo);
					$(t.rows[i].cells[12]).html(response.sector);
					$(t.rows[i].cells[13]).html(response.facilityType);
					$(t.rows[i].cells[14]).html(response.borrName);
					$(t.rows[i].cells[15]).html(response.limitAmt);
					$(t.rows[i].cells[16]).html(response.dp);
					$(t.rows[i].cells[17]).html(response.irregularAmt);
					$(t.rows[i].cells[18]).html(response.natOfBills);
					$(t.rows[i].cells[19]).html(response.securityAmt);
					$(t.rows[i].cells[20]).html(response.chargesCreated);
					$(t.rows[i].cells[21]).html(response.primaryTang);
					$(t.rows[i].cells[22]).html(response.collateralTang);
					$(t.rows[i].cells[23]).html(response.totalTang);
					$(t.rows[i].cells[24]).html(response.constituent);
					$(t.rows[i].cells[25]).html(response.allocated);
					$(t.rows[i].cells[26]).html(response.inttRate);
					$(t.rows[i].cells[27]).html(response.crSumm);
					$(t.rows[i].cells[28]).html(response.irregularDate);
					$(t.rows[i].cells[29]).html(response.lastCrDate);
					$(t.rows[i].cells[30]).html(response.crr);
					$(t.rows[i].cells[31]).html(response.insuDate);
					$(t.rows[i].cells[32]).html(response.insuAmt);
					$(t.rows[i].cells[33]).html(response.receEcgc);
					$(t.rows[i].cells[34]).html(response.sancDate);
					$(t.rows[i].cells[35]).html(response.stockStmntDate);
					$(t.rows[i].cells[36]).html(response.totOs);
					$(t.rows[i].cells[37]).html(response.secuTangAsset);
					$(t.rows[i].cells[38]).html(response.bankSecu);
					$(t.rows[i].cells[39]).html(response.secureEcgc);
					$(t.rows[i].cells[40]).html(response.unsecured);
					$(t.rows[i].cells[41]).html(response.assetSubclass);
					$(t.rows[i].cells[42]).html(response.pbRd);
					$(t.rows[i].cells[43]).html(response.unrealisedIntt);
					$(t.rows[i].cells[44]).html(response.npaDate);
					$(t.rows[i].cells[45]).html(response.inca);
					$(t.rows[i].cells[46]).html(response.provAmt);
					$(t.rows[i].cells[47]).html(response.provUnsec);
					$(t.rows[i].cells[48]).html(response.inttSusp);
					$(t.rows[i].cells[49]).html(response.adjUnsecuPortion);
					$(t.rows[i].cells[50]).html(response.secuPortion);
					$(t.rows[i].cells[51]).html(response.rstrctrProv);
					$(t.rows[i].cells[52]).html(response.rstrctrDate);
					$(t.rows[i].cells[53]).html(response.oneNpa);
					
					
					
				}
			i++;	
			$('#id02').hide();
			
			});
			
			alert("succesfully completed");
		},
		beforeSend : function() {

		},

	});

}

function deleteticketdetails(e) {
	var url = $("#deleteform").attr("action");
	var jsonObject = {};
	
	jsonObject["brcdBankCd"] = $('#brcdbancd').val();
	jsonObject["reportDate"] = $('#repdate').val();
	jsonObject["branchCode"] = $('#branchCode1').val();
	jsonObject["acctNo"] = $('#AccNo').val();
	
	var jsonOb= JSON.stringify(jsonObject);
	
	$.ajax({
		type : "POST",
		data : jsonOb,
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		url : url,
		error : function(xhr, status, error) {
			var err = xhr.responseText;

		},
		success : function(response) {
			table.clear();
			table.destroy();
			/*//table.draw();
			$('example')
		    .clear()
		    .draw();*/
			
			   
		

			$("#myModal").hide();
			alert("record gets deleted successfully");
//		$('#datainfo').empty();
			
			for (var i = 0; i < response.length; i++) {
				
			$('#datainfo').append('<tr><td>'+response[i].id.brcdBankCd+'</td><td>'+response[i].id.reportDate+'</td><td>'+response[i].id.branchCode+'</td><td>'+response[i].id.acctNo+'</td><td style="text-align: center; color: blue">'+
					'<button type="button"  id="btnHistory" class="btn-details btn btn-info btn-sm"><span class="glyphicon glyphicon-pencil"></span></button> '+'</td><td style="text-align: center;">'+'<button type="button" id="btndelte" class=" btn btn-info btn-sm" style="background-color: #660066; border: 1px solid transparent;"><span class="glyphicon glyphicon-trash"></span></button>'+'</td><td class="hide">'+response[i].module+'</td><td class="hide">'+response[i].circle+'</td><td class="hide">'+response[i].region+'</td><td class="hide">'+response[i].branchName+'</td><td class="hide">'+response[i].segment
					
					+'</td><td class="hide">'+response[i].cifNo+'</td><td class="hide">'+response[i].sector+'</td><td class="hide">'+response[i].facilityType+'</td><td class="hide">'+response[i].borrName+'</td><td class="hide">'+response[i].limitAmt+'</td><td class="hide">'+response[i].dp+'</td><td class="hide">'+response[i].irregularAmt+'</td><td class="hide">'+response[i].natOfBills+'</td><td class="hide">'+response[i].securityAmt+'</td><td class="hide">'
					
					+response[i].chargesCreated+'</td><td class="hide">'+response[i].primaryTang+'</td><td class="hide">'+response[i].collateralTang+'</td><td class="hide">'+response[i].totalTang+'</td><td class="hide">'+response[i].constituent+'</td><td class="hide">'+response[i].allocated+'</td><td class="hide">'
					+response[i].inttRate+'</td><td class="hide">'+response[i].crSumm+'</td><td class="hide">'+response[i].irregularDate+'</td><td class="hide">'+response[i].lastCrDate+'</td><td class="hide">'+response[i].crr+'</td><td class="hide">'
					+response[i].insuDate+'</td><td class="hide">'+response[i].insuAmt+'</td><td class="hide">'+response[i].receEcgc+'</td><td class="hide">'+response[i].sancDate+'</td><td class="hide">'+response[i].stockStmntDate+'</td><td class="hide">'+response[i].totOs+'</td><td class="hide">'+response[i].secuTangAsset+'</td><td class="hide">'+response[i].bankSecu+'</td><td class="hide">'+response[i].secureEcgc+'</td><td class="hide">'+response[i].unsecured+'</td><td class="hide">'+response[i].assetSubclass+'</td><td class="hide">'+response[i].pbRd+'</td><td class="hide">'+response[i].unrealisedIntt+'</td><td class="hide">'
					+response[i].npaDate+'</td><td class="hide">'+response[i].inca+'</td><td class="hide">'+response[i].provAmt+'</td><td class="hide">'+response[i].provUnsec+'</td><td class="hide">'+response[i].inttSusp+'</td><td class="hide">'
					+response[i].adjUnsecuPortion+'</td><td class="hide">'+response[i].secuPortion+'</td><td class="hide">'+response[i].rstrctrProv+'</td><td class="hide">'+response[i].rstrctrDate+'</td><td class="hide">'
					+response[i].oneNpa+'</td></tr>');
	
		 
		}
			//$('#example').dataTable({});  
		$(document).on("click", "#btnHistory", function(){
			jQuery.noConflict();
			$('#id02').modal('show');
			});
		$(document).on("click", "#btndelte", function() {
			jQuery.noConflict();
			
			var currentRow=$(this).closest("tr");
			var brcdbankcd1=currentRow.find("td:eq(0)").text(); 
		    var reportdte=currentRow.find("td:eq(1)").text();
			var branchcode=currentRow.find("td:eq(2)").text(); 
		    var acctno=currentRow.find("td:eq(3)").text();
		    $("#brcdbancd").val(brcdbankcd1);
			$("#repdate").val(reportdte);
		    $("#branchCode1").val(branchcode);
			$("#AccNo").val(acctno);
			$('#myModal').show();
		});
			
			
		
			
		},

		beforeSend : function() {

		},

	});

}

/*function deleteRow(r) {
    var i = r.parentNode.parentNode.rowIndex;
    document.getElementById("myTable").deleteRow(i);
}*/
/* /////////////////////////// INFO Validations///////////////////////// */

function BranchKode_check() {
	var branch_val = $('#BranchCode').val();

	if (branch_val.length == '') {
		$('#BranchKode').html("*Please fill the BranchCode");
		return false;
	} else {
		$('#BranchKode').hide();
		return true;
	}
	if ((branch_val.length < 4) || (branch_val.length > 10)) {
		$('#BranchKode').html("*BranchCode must be 4 digits");
		return false;
	} else {
		$('#BranchKode').hide();
		return true;
	}
}

function ReportDate_check() {
	var ReportDate = $('#ReportDate').val();

	if (ReportDate.length == '') {
		$('#ReportdateZ').html("*Please Select the ReportDate");
	} else {
		$('#ReportdateZ').hide();
		return true;
	}
}
function createJsonObject() {
	var jsonObject = {};
	var innerjson = {};
	innerjson["brcdBankCd"] = $('#BankCd').val();
	innerjson["reportDate"] = $('#reportdate').val();
	innerjson["branchCode"] = $('#branchcode').val();
	innerjson["acctNo"] = $('#accno').val();
	jsonObject["id"] = innerjson;
	jsonObject["module"] = $('#module').val();
	jsonObject["circle"] = $('#circle').val();
	jsonObject["region"] = $('#region').val();
	jsonObject["branchName"] = $('#branchname').val();
	jsonObject["segment"] = $('#segment').val();
	jsonObject["cifNo"] = $('#cifno').val();
	jsonObject["sector"] = $('#sector').val();
	jsonObject["facilityType"] = $('#facility Type').val();
	jsonObject["borrName"] = $('#BorrName').val();
	jsonObject["limitAmt"] = $('#Limitamt').val();
	jsonObject["dp"] = $('#dp').val();
	jsonObject["irregularAmt"] = $('#irregularamt').val();
	jsonObject["natOfBills"] = $('#natofbills').val();
	jsonObject["securityAmt"] = $('#securityamt').val();
	jsonObject["chargesCreated"] = $('#chargescreated').val();
	jsonObject["primaryTang"] = $('#primaryTang').val();
	jsonObject["collateralTang"] = $('#collateralTang').val();
	jsonObject["totalTang"] = $('#toatlTang').val();
	jsonObject["constituent"] = $('#constituent').val();
	jsonObject["allocated"] = $('#allocated').val();
	jsonObject["inttRate"] = $('#inttrate').val();
	jsonObject["crSumm"] = $('#crsumm').val();
	jsonObject["irregularDate"] = $('#irregulardate').val();
	jsonObject["lastCrDate"] = $('#lastcrdate').val();
	jsonObject["crr"] = $('#crr').val();
	jsonObject["insuDate"] = $('#insudate').val();
	jsonObject["insuAmt"] = $('#insuamt').val();
	jsonObject["receEcgc"] = $('#receecgc').val();
	jsonObject["sancDate"] = $('#sancDate').val();
	jsonObject["stockStmntDate"] = $('#stockstmtdate').val();
	jsonObject["totOs"] = $('#totos').val();
	jsonObject["secuTangAsset"] = $('#secutandasset').val();
	jsonObject["bankSecu"] = $('#BankSecu').val();
	jsonObject["secureEcgc"] = $('#SecuEcgc').val();
	jsonObject["unsecured"] = $('#Unsecured').val();
	jsonObject["assetSubclass"] = $('#assetsubclass').val();
	jsonObject["pbRd"] = $('#pbrd').val();
	jsonObject["unrealisedIntt"] = $('#UnrealisedIntt').val();
	jsonObject["npaDate"] = $('#npadate').val();
	jsonObject["inca"] = $('#inca').val();
	jsonObject["provAmt"] = $('#Provamt').val();
	jsonObject["provUnsec"] = $('#provUnsec').val();
	jsonObject["inttSusp"] = $('#initsusp').val();
	jsonObject["adjUnsecuPortion"] = $('#adjunsecuportion').val();
	jsonObject["secuPortion"] = $('#secuportion').val();
	jsonObject["rstrctrProv"] = $('#rstrctrprov').val();
	jsonObject["rstrctrDate"] = $('#rstrctrdate').val();
	jsonObject["oneNpa"] = $('#onenpa').val();
	return JSON.stringify(jsonObject);
}
