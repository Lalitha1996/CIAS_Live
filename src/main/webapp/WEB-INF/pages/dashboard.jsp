<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <%@ include file="header.jsp" %> 
      <link rel="stylesheet" href="css/style.css">
      <style>
         .header-color {
         line-height: 50px !important;
         }
         .btn-success:hover{background-color: #339bb7 !important;border-color: #339bb7 !important;}
         .hidebar{
         display:none;
         }
      </style>
   </head>
   <body>
      <div class="report_bg_patchshow">
         <div id="fade"></div>
         <div id="modal" style="height: 50; width: auto; padding: 0; border-radius: 0;">
            <img id="loading5" style="width: 150px;" src="images/loading5.gif">
         </div>
         <div class="b-b b-r b-l b-t b-grey loan_label" style="padding: 5px 10px 40px; background: #fff; box-shadow: 0 0 8px rgba(0, 0, 0, 0.5);">
            <h4 class="text-center bold b-b report_head m-t-0" style="height: 45px; background: #fff; border-radius: 0;">
               <b>Bank Loan Transaction Report</b>
            </h4>
            <div class="b-b b-r b-l b-t b-grey loan_label" style="padding: 5px 10px 40px; background: #fff; box-shadow: 0 0 8px rgba(0, 0, 0, 0.5);">
               <div id="chartContainer" style="height: 500px; width: 100%;"></div>
              <!--  <div id="loanchart" style="height: 500px; width: 100%;"></div> -->
            </div>
         </div>
      </div>
      <%@ include file="/WEB-INF/pages/Footer.jsp"%>
      <script type="text/javascript" charset="utf-8">
         $(window).on("load", function() {
         	$.ajax({
         		type : "POST",
         		url : 'showchartResult',
         		data : '',
         		processData : false,
         		contentType : "application/json; charset=utf-8",
         		error : function(xhr, status, error) {
         		},
         		success : function(response) {
         			var list = response;
         
         			var chart = new CanvasJS.Chart("chartContainer", {
         				exportEnabled: true,
         				animationEnabled: true,
         				theme: "light1", 
         				title:{
         					text: "Profit & Loss",
         					fontSize: 20,
         			        padding: 10,
         			        verticalAlign: "bottom",
         				},
         				axisY: {
         					title: "sum of amount (Billions)"
         				},
         				axisX: {
         					title: "date"
         				},
         				data: [{        
         					type: "area",
         					startAngle: 20,
         					toolTipContent: "<b> {label} </b>sum of amount: {y}"+"B",
         					legendText: "{label}",
         					indexLabelFontSize: 18,
         					indexLabel: "{y}"+"B",
         					dataPoints:list
         				}]
         			});
         			chart.render();
         			if($('.canvasjs-chart-credit'))
         				$('.canvasjs-chart-credit').addClass('hide');
         		},
         		beforeSend : function() {
         
         		},
         	});
         });
      </script>
   </body>
</html>