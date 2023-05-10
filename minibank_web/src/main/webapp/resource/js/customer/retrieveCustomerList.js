$(function() {
	var contextPath = $('#contextPath').val();
	var customerApiUrl = $('#customerApiUrl').val();
	$("#cstmId").focus();
	$('#customerMenu').addClass('selected-menu');
	$("#btnSearch").on("click", function(){
	    $('#customerList td').remove();
		var cstmId = $('#cstmId').val();
		var cstmNm = $('#cstmNm').val();
		var acntNo = $('#acntNo').val();
		retrieveCustomerList(cstmId, cstmNm, acntNo);
	});

	function retrieveCustomerList(cstmId, cstmNm, acntNo){
		var resMsg = "";
		$.ajax({
			type : 'GET',
			url : customerApiUrl+'/list/rest/v0.8',
			data : { 'cstmId':cstmId, 'cstmNm':cstmNm, 'acntNo':acntNo },
			contentType: 'application/json',
			datatype : 'json',
			xhrFields : {
				withCredentials : true
			},
			crossDmain: true,
			beforeSend : function(){
		        $('.wrap-loading').removeClass('display-none');
			},
			complete:function(){
		        $('.wrap-loading').addClass('display-none');
			},
			success : function(data) {
				for(var i = 0; i < data.length; i++)
					appendCustomerInfo(data[i]);
				
			},
		    error: function (jqXHR, textStatus, errorThrown) {
		    	if(jqXHR.status == '417' || jqXHR.status == '500'){
			    	var responseText = jqXHR.responseText;
			    	var body = JSON.parse(responseText);
			    	alert(body.message);
		    	}
		    	else
		    		alert("[연결 오류]\n서버와 연결에 실패했습니다.");
				$(location).attr("href", contextPath + "/customer/view/retrieveCustomerList");
		    }
		});
		return resMsg;
	}
	
	function addCommas(str){
		if(!isNaN(str))
			return str.toString().replace(/^0+/, '').replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		else
			return 0;
    }
	function appendCustomerInfo(customerData){
		var account_count=0, account_blance_sum =0;
		for(var a=0; a<customerData.accounts.length; a++){
			account_count ++;
			account_blance_sum =+ customerData.accounts[a].acntBlnc;
		}
		
		var html = "<tr style='text-align:center;'>"
				+"<td>"+customerData.cstmId+"</td>"
				+"<td>"+customerData.cstmNm+"</td>"
				+"<td>"+customerData.cstmPn+"</td>"
				+"<td style='text-align:right;'>"+addCommas(customerData.oneDyTrnfLmt)+"</td>"
				+"<td style='text-align:right;'>"+addCommas(customerData.oneTmTrnfLmt)+"</td>"
				+"<td>"+account_count+"</td>"
				+"<td style='text-align:right;'>"+addCommas(account_blance_sum)+"</td>"
				"</tr>";
		
		$('#customerList').append(html);
	}
});