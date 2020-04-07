
function retriveColumns() {
		columnbox();
		if($('#table_select').val()){
			
			// assigning table select value to columns toggle while retrieving favorite list js
			$("#table_btn").val($('#table_select').text().trim()+"(Fields)");
			$("#table_btn_join1").hide();
			$("#table_btn_join2").hide();
			$("#table_btn_join3").hide();
			$("#table_btn_join4").hide();
			$("#table_btn_join5").hide();
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
	                 //clearAllFilterFields();
	                	
	                	
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
	                      
	                	
	                    closeModal();
	                    localStorage.setItem("dbColumns", JSON.stringify(response));
	                    $.each(response,function(i, value) {
	                           if(isNumber(value)){
	                              $('#columns').append($("<li class = 'active'>")
	                                           .append($("<a class='btn btn-secondary' style='background-color:#566573;color:white' data-toggle='collapse' aria-expanded='false'>")
	                                           .text("("+uniqCol+") "+getAppLabel(response,value.name))
	                                           .attr({  href:'#'+stringRemove(value.name), id : "drag" +i, }))
	                                           .append($("<ul class='collapse list-unstyled'>")
	                                           .attr('id',stringRemove(value.name))
	                                           .append($("<li>") 
	                                           .append($("<a class='btn btn-secondary' style='background-color:#566573;color:white'>")
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
	                                        } else if(isRequired(value) && isString(value) ){
	                                               $('#columns').append($("<li class = 'active'>")
	                                                            .append($("<a type ='button' class ='btn btn-secondarys' style='background-color:#EC7063;color:white;' data-toggle = 'dropdown'>")
	                                                            .text("("+uniqCol+") "+getAppLabel(response,value.name))
	                                                            .attr({ href : '#'+ value.name,value : value.name+' AS '+stringRemove(value.name),onclick : 'moveButton(this)',id : "drag"+ i,})));
	                                              } else{
	                                            	  $('#columns').append($("<li class = 'active'>")
	                                                            .append($("<a type ='button' class ='btn btn-secondarys' style='background-color:#566573;color:white' data-toggle = 'dropdown'>")
	                                                            .text("("+uniqCol+") "+getAppLabel(response,value.name))
	                                                            .attr({ href : '#'+ value.name,value : value.name+' AS '+stringRemove(value.name),onclick : 'moveButton(this)',id : "drag"+ i,})));
	                                              }
	                            
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
	                                        
	                                        if (isKeyField(value)) {
	                                        	
	                                        	$('#priId').append($('<option></option>')
	                                                    .text(getAppLabel(response,value.name))
	                                                    .attr({ value : value.name,style : 'color:red;', }));
	                                            
	                                            $('#pri2Id').append($('<option></option>')
	                                                    .text(getAppLabel(response,value.name))
	                                                    .attr({ value : value.name,style : 'color:red;',}));
	                                        }
	                                        
										});
	                    openNav();
	                    validateSelectedValue();
						filterColumns(response)
						callRetriveJoinColumnsFun();
					},
				});
		}
	}

/*function makeFavariteCheck(){
	$("input[name='joincheck']:checkbox").each(function(){
		if ($(this).is(":not(:checked)")) {
			$(this).prop("checked", true);
		}
	});
}*/
function callRetriveJoinColumnsFun(){
	var tableList =[];var table="";
	
	$("input[name='joincheck']:checkbox").each(function() {
		if ($(this).is(":checked")){
		    table = $(this).val();
			tableList.push($(this).val());
			retriveJoinColumns(table,tableList);
		}
	});
	
	if(tableList.length>0){
		$("#multiJoinTablediv").slideToggle();
		$("#filterJoinLabel").slideToggle();
		$("#sj").slideToggle();	
	}else {
		$("#jointables").hide();
	}
}


function retriveJoinColumns(table,tableList) {
	if(tableList.length>0){
			
		var columnjoin,filterjoin,pri,pri2,uniqCol,dragCon;
			
		if (tableList.length==1) {
			$("#table_btn_join1").show();
			$("#table_btn_join1").val(table+ "(Fields)");
			uniqCol = table.match(/\b(\w)/g).join(''); // [get First letter from each word to attach on cloumns]
			columnjoin = "columnsJoin1",filterjoin = "filterjoin1",pri="priId1",pri2="pri2Id1",dragCon="a";
		}
		if (tableList.length==2) {
			$("#table_btn_join2").show();
			$("#table_btn_join2").val(table+ "(Fields)");
			uniqCol = table.match(/\b(\w)/g).join(''); // [get First letter from each word to attach on cloumns]
			columnjoin = "columnsJoin2",filterjoin = "filterjoin2",pri="priId2",pri2="pri2Id2",dragCon="b";
		}	
		if (tableList.length==3) {
			$("#table_btn_join3").show();
			$("#table_btn_join3").val(table+ "(Fields)");
			uniqCol = table.match(/\b(\w)/g).join(''); // [get First letter from each word to attach on cloumns]
			columnjoin = "columnsJoin3",filterjoin = "filterjoin3",pri="priId3",pri2="pri2Id3",dragCon="c";
		}
		if (tableList.length==4) {
			$("#table_btn_join4").show();
			$("#table_btn_join4").val(table+ "(Fields)");
			uniqCol = table.match(/\b(\w)/g).join(''); // [get First letter from each word to attach on cloumns]
			columnjoin = "columnsJoin4",filterjoin = "filterjoin4",pri="priId4",pri2="pri2Id4",dragCon="d";
		}
		if (tableList.length==5) {
			$("#table_btn_join5").show();
			$("#table_btn_join5").val(table+ "(Fields)");
			uniqCol = table.match(/\b(\w)/g).join(''); // [get First letter from each word to attach on cloumns]
			columnjoin = "columnsJoin2",filterjoin = "filterjoin5",pri="priId5",pri2="pri2Id5",dragCon="e";
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
	       // clearAllFilterJoinFields();
	        	
	        	// sj is select attr id and filterjoin is optgroup id creating dynamically
	             $('#sj').append($("<optgroup style='font-size:small'>")
	                     .attr({id :filterjoin,label:table.trim()})		
	                     );
	             
	             // Creating  Optgroup and appending to Select attr Id #pri both js and jquery
	             
	              $('#pri').append($("<optgroup style='font-size:small'>")
	                      .attr({id :pri,label:table.trim()})		
	                      );
	              
	              // Creating  Optgroup and appending to Select attr Id #pri2 both js and jquery
	               
	               $('#pri2').append($("<optgroup style='font-size:small'>")
	                       .attr({id :pri2,label:table.trim()})		
	                       );
	               
	               // Creating  Optgroup and appending to Select attr Id #sec both js and jquery
	                
	                $('#sec').append($("<optgroup style='font-size:small'>")
	                        .attr({id :"secId",label:table.trim()})		
	                        );
	            closeModal();
	            localStorage.setItem(columnjoin, JSON.stringify(response));
	            $.each(response,function(i, value) {
	                   if(isNumber(value)){
	                      $('#'+columnjoin).append($("<li class = 'active'>")
	                                   .append($("<a class='btn btn-secondary' style='background-color:#3F729B;color:white' data-toggle='collapse' aria-expanded='false'>")
	                                   .text("("+uniqCol+") "+getAppLabel(response,value.name))
	                                   .attr({  href:'#'+stringRemove(value.name), id : "drag" +i, }))
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
	                                } else if(isRequired(value) && isString(value) ){
	                                       $('#'+columnjoin).append($("<li class = 'active'>")
	                                                    .append($("<a type ='button' class ='btn btn-secondary' style='background-color:#EC7063;color:white;' data-toggle = 'dropdown'>")
	                                                    .text("("+uniqCol+") "+getAppLabel(response,value.name))
	                                                    .attr({ href : '#'+ value.name,value : value.name+' AS '+stringRemove(value.name),onclick : 'moveButtonJoin(this,'+columnjoin+')',id : "drag"+ i+dragCon,})));
	                                      } else{
	                                    	  $('#'+columnjoin).append($("<li class = 'active'>")
	                                                    .append($("<a type ='button' class ='btn btn-secondary' style='background-color:#3F729B;color:white' data-toggle = 'dropdown'>")
	                                                    .text("("+uniqCol+") "+getAppLabel(response,value.name))
	                                                    .attr({ href : '#'+ value.name,value : value.name+' AS '+stringRemove(value.name),onclick : 'moveButtonJoin(this,'+columnjoin+')',id : "drag"+ i+dragCon,})));
	                                      }
	                    
	                                if (isRequired(value)) {
	                                	
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
	                                
	                                
	                                if (isKeyField(value)) {
	                                	
	                                	 $('#secId').append($('<option></option>')
		                                            .text(getAppLabel(response,value.name))
		                                            .attr({ value : value.name,style : 'color:red;', }));
		                                    
		                                    $('#'+pri).append($('<option></option>')
		                                            .text(getAppLabel(response,value.name))
		                                            .attr({ value : value.name,style : 'color:red;', }));
		                                    
		                                    $('#'+pri2).append($('<option></option>')
		                                            .text(getAppLabel(response,value.name))
		                                            .attr({ value : value.name,style : 'color:red;', }));
	                                }
	                                
	                                
								});
	            
				     validateSelectedValue();
						/*openNav();*/
					 filterColumns(response,columnjoin)
					},
				});
	       }
      }	
	
	
function filterColumns(response,columnjoin) {
	var parameter=localStorage.getItem("parameter");
	var parameterObj = parameter.split(',');
	$.each(parameterObj, function(key, value) {
		$("#columns li a").each(function() {
			var text = $(this).attr("value");
			if (value == text) {
				$(this).detach().appendTo('#drag');
			}
		});

	});
	
	$.each(parameterObj, function(key, value) {
		$("#"+columnjoin+" li a").each(function() {
			var textjoin = $(this).attr("value");
			if (value == textjoin) {
				$(this).detach().appendTo('#drag');
			}
		});

	});
//localStorage.setItem("parameter", "");
}



// check box select 

$('.checkid').click(function(){
	 var valu="";
	 var valu1="";
	 valu=$(this).val();
	 if ($(this).is(":checked")) {
		 $("input[id='checkid2']:checkbox").each(function(){
			 valu1=$(this).val();
			 if(valu == valu1) {
				 $(this).prop('checked',true);
			 }
		});  
	 } else {
		 $("input[id='checkid2']:checkbox").each(function(){
			 valu1=$(this).val();
			 if(valu == valu1) {
				 $(this).prop('checked',false);
			 }
		});
	 }
	 
});

$('.checkid1').click(function(){
	 var valu="";
	 var valu1="";
	 valu=$(this).val();
	 if ($(this).is(":checked")) {
		 $("input[id='checkid1']:checkbox").each(function(){
			 valu1=$(this).val();
			 if(valu == valu1){
				 $(this).prop('checked',true);
			 } 
		});  
	 } else {
		 $("input[id='checkid1']:checkbox").each(function(){
			 valu1=$(this).val();
			 if(valu == valu1){
				 $(this).prop('checked',false);
			 } 
		});  
	 }
	  
});
