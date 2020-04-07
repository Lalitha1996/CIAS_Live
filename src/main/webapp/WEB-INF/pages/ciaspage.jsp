<%@ include file="/WEB-INF/pages/header.jsp"%>
<title>Home page</title>
<link href="css/main.css" rel="stylesheet" type="text/css" /> 
<link rel="stylesheet" href="css/style.css">
<style type="text/css">
.header-color {
	line-height: 50px !important;
}

.btn-success:hover {
	background-color: #339bb7 !important;
	border-color: #339bb7 !important;
}

.hidebar {
	display: none;
}
.chartsr {
	transition: transform .2s;
}

.chartsr:hover {
	-ms-transform: scale(1.5); /* IE 9 */
	-webkit-transform: scale(0.5); /* Safari 3-8 */
	transform: scale(1.1);
}

</style>

<div class="container ng-scope">
<center>
<div>
<img src="images/logofinal4.PNG" style="width: 265px;height:170px"/>
</div>
</center>
<!-- <h2 style="text-align:center;">Welcome</h2> 
<h2 style="text-align:center;">to </h2>
<h2 style="text-align:center;">CIAS Tool</h2> -->
<marquee behavior="alternate" direction="left" Style="color:blue; font-size:18px; /* width: 900px; */">C-Edge Intelligence and Analytics Solution</marquee>
</div>
<div id="depositChart" class="chartsr" style="height: 300px; width: 30%; margin-left:25px;"></div>
<div id="advanceChart" class="chartsr" style="height: 300px; width: 30%; margin-top:-303px; margin-left:455px;"></div>
<div id="npaChart" class="chartsr" style="height: 300px; width: 30%; margin-top:-300px; margin-left:895px;"></div>
<script type="text/javascript" charset="utf-8">
        
	$(window).on("load",function() {
									$.ajax({
									type : "POST",
									url : 'showchartreports',
									data : '',
									processData : false,
									contentType : "application/json; charset=utf-8",
									error : function(xhr, status, error) {
									},
									success : function(response) {
										var list = response;
										//depositChart
										var chart = new CanvasJS.Chart("depositChart",
												{
													exportEnabled : false,
													animationEnabled : true,
													theme : "light1",
													title : {
														text : "Total Deposits",
														fontSize : 20,
														padding : 10

													},
													axisY : {
														    title : "sum of amount (millions)",
														    titleFontColor:"#369EAD",
													        lineColor:"#369EAD",
													        tickColor:"#369EAD",
													        labelFontColor:"#369EAD"        
													},
													axisX : {
														title : "Branch no",
														interval: 3
													},
													data : [ {
														type : "area",
														color:"#369EAD",
														startAngle : 20,
														toolTipContent : "<b> Branch NO: {label} </b>sum of amount: {y}"+"M",
														dataPoints : list[0]
													} ]
												});
										chart.render();
										//advanceChart
										var chart = new CanvasJS.Chart("advanceChart",
												{
													exportEnabled : false,
													animationEnabled : true,
													theme : "light1",
													title : {
														text : "Total Advances",
														fontSize : 20,
														padding : 10
														
													},
													axisY : {
														title : "sum of amount (millions)",
														titleFontColor:"#C24642",
												        lineColor:"#C24642",
												        tickColor:"#C24642",
												        labelFontColor:"#C24642"
													},
													axisX : {
														title : "Branch no",
														interval: 3
													},
													data : [ {
														type : "line",
														color:"#C24642",
														startAngle : 20,
														toolTipContent : "<b> Branch NO: {label} </b>sum of amount: {y}"+"M",
														dataPoints : list[1]
													} ]
												});
										chart.render();
										//npaChart
										var chart = new CanvasJS.Chart("npaChart",
												{
													exportEnabled : false,
													animationEnabled : true,
													theme : "light1", 
													title : {
														text : "NPA",
														fontSize : 20,
														padding : 10
													},
													axisY : {
														title : "sum of amount in millions"
													},
													axisX : {
														title : "Branch no",
														interval: 3
													},
													data : [ {

														type : "column",
														startAngle : 20,
														toolTipContent : "<b> Branch NO: {label} </b>sum of amount: {y}"+"M",
														dataPoints : list[2]
													} ]
												});
										chart.render();
										if ($('.canvasjs-chart-credit'))
											$('.canvasjs-chart-credit')
													.addClass('hide');
									},
									beforeSend : function() {

									},
								});
					});
</script>

<%@ include file="/WEB-INF/pages/Footer.jsp"%>

<%-- ////////////////////////////////////////////////////////////////////////////

	$
								.ajax({
									type : "POST",
									url : 'advancechartData',
									data : '',
									processData : false,
									contentType : "application/json; charset=utf-8",
									error : function(xhr, status, error) {
									},
									success : function(response) {
										var list = response;
										
										
		$
								.ajax({
									type : "POST",
									url : 'npachartData',
									data : '',
									processData : false,
									contentType : "application/json; charset=utf-8",
									error : function(xhr, status, error) {
									},
									success : function(response) {
										var list = response;
	
										
										
<%@ include file="/WEB-INF/pages/header.jsp"%>
<title>Home page</title>
<link href="css/main.css" rel="stylesheet" type="text/css" /> 
<style type="text/css">
* {
	box-sizing: border-box;
}

#zoom {
	transition: transform .2s;
}

#zoom:hover {
	-ms-transform: scale(1.5); /* IE 9 */
	-webkit-transform: scale(1.5); /* Safari 3-8 */
	transform: scale(1.5);
}

.header-color {
	line-height: 50px !important;
}

.btn-success:hover {
	background-color: #339bb7 !important;
	border-color: #339bb7 !important;
}

.hidebar {
	display: none;
}
</style>


<div class="container ng-scope">
<h1 style="text-align:center;">Welcome</h1> 
<h1 style="text-align:center;">to </h1>
<h1 style="text-align:center;">BIAS Tool</h1>
<marquee behavior="scroll" direction="left" Style="color:blue; font-size:20px; width: 900px;">Banc-Edge Inteligence Analysis and Solution</marquee>
<div class="row">
  <div class="col-md-4" id="zoom">
    <section class="panel panel-default">
      <div class="panel-body">
        <article class="media-box">
        <div class="media-box-photo">
          <img src="images/columnchart.png" class="demo-theme-light lazy-load loaded">
        </div>
        </article>
      </div>
    </section>
  </div>
  <div class="col-md-4" id="zoom">
    <section class="panel panel-default">
      <div class="panel-body">
        <article class="media-box">
        <div class="media-box-photo">
          <img src="images/areachart.png" class="demo-theme-light lazy-load loaded">
        </div>
        </article>
      </div>
    </section>
  </div>
  <div class="col-md-4" id="zoom">
    <section class="panel panel-default">
      <div class="panel-body">
        <article class="media-box">
        <div class="media-box-photo">
          <img src="images/linechart.png" class="demo-theme-light lazy-load loaded">
        </div>
        </article>
      </div>
    </section>
  </div>
</div>
</div>
<%@ include file="/WEB-INF/pages/Footer.jsp"%>
//////////////////////////////////////////////////////////////////////////// --%>