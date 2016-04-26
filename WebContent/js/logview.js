    var table1=null;
	var table2=null;
	var table3=null;
	
	var count=0;
    
    function validate() {
    	var runid = "";
    	var etldate = "";
    	var tabs = "";
    	
    	    	
    	if($("#runid").val() != "")
    		{
    			runid = parseInt($("#runid").val());
    		}
    	
    	if ($("#etldate").val() != "")
    		{
    			etldate = $("#etldate").val();
    		}
    	
    	if ($("#tables").val() != "")
		{
    		tabs = $("#tables").val();
    		//alert(entity);
		}
    	
    	if(!runid && !etldate && !tabs) {
    		
    		alert("Either RunID,Entity or Date is required !!");
    		
    	} 
    	else {
    		//alert("http://" + location.hostname +":8080/DBLogViewer/rest/joblog/retrivejoblog?runid=" + runid + "&&etl_date=" + etldate + "&&enitity=" + entity);
    		$("#datatable").removeClass("hidden");
        	$( "#tabs" ).tabs();
        	switch (tabs) { 
        	case 'revenue_fact': 
        		$("#ui-id-3").removeClass("tabhidden");
        		$('#tabs-3').removeClass("tabhidden");
        		
        		break;
        	case 'po_fact': 
        		$("#ui-id-3").removeClass("tabhidden");
        		$('#tabs-3').removeClass("tabhidden");
        		
        		break;
        	case 'so_fact': 
        		$("#ui-id-3").removeClass("tabhidden");
        		$('#tabs-3').removeClass("tabhidden");
        		
        		break;
        	case 'amortization_fact': 
        		$("#ui-id-3").removeClass("tabhidden");
        		$('#tabs-3').removeClass("tabhidden");
        		
        		break;
        	case 'vb_fact': 
        		$("#ui-id-3").removeClass("tabhidden");
        		$('#tabs-3').removeClass("tabhidden");
        		
        		break;
        	case 'opportunity_fact': 
        		$("#ui-id-3").removeClass("tabhidden");
        		$('#tabs-3').removeClass("tabhidden");
        		
        		break;
        	case 'budgetforecast_fact': 
        		$("#ui-id-3").removeClass("tabhidden");
        		$('#tabs-3').removeClass("tabhidden");
        		break;
        default:
        		$("#ui-id-3").addClass("tabhidden");
                $('#tabs-3').addClass("tabhidden");
        		break;
        		
        	}
        	
        	/*$body = $("body");

        	$(document).on({
        	    ajaxStart: function() { $body.addClass("loading");    },
        	     ajaxStop: function() { $body.removeClass("loading"); }    
        	});*/
        	
        	$(document).ajaxStart(function(){
        	    $('#loading').show();
        	 }).ajaxStop(function(){
        	    $('#loading').hide();
        	 });
        if (count==0)
        	{
        	table1 = $('#JobLog').DataTable({
        	    	"ajax" : "http://" + location.hostname +":8080/DBLogViewer/rest/joblog/retrivejoblog?runid=" + runid + "&&etl_date=" + etldate + "&&tabs=" + tabs,
        	    	//"ajax" : "retrivemsglog.txt",
        	    	"columns" : [
        				{ "data": "JobId" },
        				{ "data": "RunId" },
        				{ "data": "Entity" },
        				{ "data": "Run Mode" },
        				{ "data": "ExtractStartTime" },
        				{ "data": "ExtractEndTime" },
        				{ "data": "S3LoadStartTime" },
        				{ "data": "S3LoadEndTime" },
        				{ "data": "RedShiftLoadStart" },
        				{ "data": "RedShiftLoadEnd" },
        				{ "data": "Job Status" },
        				{ "data": "Subsidiary ID" }],
        	    	"scrollY":        "auto",
        	    	"scrollX":        "1000px",
        	    		 
        	      	    });
        
        	table2 = $('#MessageLog').DataTable({
    		     	//"ajax" : "json.txt",
    				"ajax" : "http://" + location.hostname +":8080/DBLogViewer/rest/joblog/retrivemsglog?runid=" + runid + "&&etl_date=" + etldate + "&&tabs=" + tabs,
    	    		"columns" : [
    	    	    				{ "data": "MessageId" },
    	    	    				{ "data": "RunId" },
    	    	    				{ "data": "Message Desc" },
    	    	    				{ "data": "Target Table" },
    	    	    				{ "data": "Message Stage" },
    	    	    				{ "data": "Message Type" },
    	    	    				{ "data": "Message TimeStamp" },
    	    	    				{ "data": "Subsidiary ID" }],
    	    	    	    	"scrollY":        "auto",
    	    	    	    	"scrollX":        "1000px",
    	    	    	    		
    	    });  
        
        	$.ajax({
                "url": "http://" + location.hostname +":8080/DBLogViewer/rest/joblog/retriveerrorlog?runid=" + runid + "&&etl_date=" + etldate + "&&tabs=" + tabs,
                "success": function(json) {
                    var tableHeaders;
                    $.each(json.columns, function(i, val){
                        tableHeaders += "<th>" + val + "</th>";
                    });
                     
                    $("#tabs-3").empty();
                    $("#tabs-3").append('<table id="displayTable" class="table table-bordered display compact" cellspacing="0" width="100%"><thead><tr>' + tableHeaders + '</tr></thead><tfoot><tr>' + tableHeaders + '</tr></tfoot></table>');
                    $('#displayTable').dataTable(json);
                    
                },
                "dataType": "json"
            });
        	
        	count++;
        	
        	
        }
        else
        {
        	table1.destroy();
        	table2.destroy();
        	//table3.destroy();
        	
        	table1 = $('#JobLog').DataTable({
    	    	"ajax" : "http://" + location.hostname +":8080/DBLogViewer/rest/joblog/retrivejoblog?runid=" + runid + "&&etl_date=" + etldate + "&&tabs=" + tabs,
    	    	//"ajax" : "retrivemsglog.txt",
    	    	"columns" : [
    				{ "data": "JobId" },
    				{ "data": "RunId" },
    				{ "data": "Entity" },
    				{ "data": "Run Mode" },
    				{ "data": "ExtractStartTime" },
    				{ "data": "ExtractEndTime" },
    				{ "data": "S3LoadStartTime" },
    				{ "data": "S3LoadEndTime" },
    				{ "data": "RedShiftLoadStart" },
    				{ "data": "RedShiftLoadEnd" },
    				{ "data": "Job Status" },
    				{ "data": "Subsidiary ID" }],
    	    	"scrollY":        "auto",
    	    	"scrollX":        "1000px",
    	    		 
    	      	    });
        	table2 = $('#MessageLog').DataTable({
		     	
				"ajax" : "http://" + location.hostname +":8080/DBLogViewer/rest/joblog/retrivemsglog?runid=" + runid + "&&etl_date=" + etldate + "&&tabs=" + tabs,
	    		"columns" : [
	    	    				{ "data": "MessageId" },
	    	    				{ "data": "RunId" },
	    	    				{ "data": "Message Desc" },
	    	    				{ "data": "Target Table" },
	    	    				{ "data": "Message Stage" },
	    	    				{ "data": "Message Type" },
	    	    				{ "data": "Message TimeStamp" },
	    	    				{ "data": "Subsidiary ID" }],
	    	    	    	"scrollY":        "auto",
	    	    	    	"scrollX":        "1000px",
	    	    	    		
	    });  
        	
        	
        	$.ajax({
                "url": "http://" + location.hostname +":8080/DBLogViewer/rest/joblog/retriveerrorlog?runid=" + runid + "&&etl_date=" + etldate + "&&tabs=" + tabs,
                "success": function(json) {
                    var tableHeaders;
                    $.each(json.columns, function(i, val){
                        tableHeaders += "<th>" + val + "</th>";
                    });
                     
                    $("#tabs-3").empty();
                    $("#tabs-3").append('<table id="displayTable" class="table table-bordered display compact" cellspacing="0" width="100%"><thead><tr>' + tableHeaders + '</tr></thead><tfoot><tr>' + tableHeaders + '</tr></tfoot></table>');
                    $('#displayTable').dataTable(json);
                },
                "dataType": "json"
            });
        }
    	}
    }
    

    
    $(document).click(function(event) {
        var event = $(event.target);
        if(event[0].id=="ui-id-2"){
        	$('#thOne').click();
        	 
        }
    });
    
    $(document).click(function(event) {
        var event = $(event.target);
        if(event[0].id=="ui-id-1"){
        	$('#th2').click();
        	 
        }
    });
   
    $(document).click(function(event) {
        var event = $(event.target);
        if(event[0].id=="ui-id-3"){
        	$('#abs-3').click();
        	 
        }
    });
   
  