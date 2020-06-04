/*
 * [y] hybris Platform
 *
 * Copyright (c) 2017 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */

$(document).on("click","#mylogout",function(){ 
	
	$.ajax({
		url : ACC.config.encodedContextPath + "/chat/resetflag",
		type : 'GET',
		success : function(data) {
		if(data !== "Server Error")
		{
		window.location.href = ACC.config.encodedContextPath + "/logout";
		}
	}

  });
	
});


$(document).on("click","#likeButton",function(){ 
	
	$.ajax({
		url : ACC.config.encodedContextPath + "/chat/updatelikes",
		type : 'POST',
		data : {
			userId : "pooja@happiestminds.com"
			
		},
		success : function(data) {
		if(data !== "Server Error")
		{
			
		}
	}

  });
	
});



$( '.LiveChat_AutoComplete' ).on( "keyup", function() {
	var length = $(this).val().length;
	var searchTerm = $(this).val();
	
	if(length >= 3 && length !== 0 && length !== 1){
		
		$(".selectAutoSuggest li").remove();
		$.ajax({
			url : ACC.config.encodedContextPath + "/search/autocomplete/SearchBox",    
			 data :{
			    "term" : searchTerm
			 },
			type : 'GET',
			dataType: 'json',
			success : function(data) {
				if(data !== "Server Error")
				{
					$.each(data.products, function(i, item){
    					$(".LiveChat_areaAutoComplete").find(".selectAutoSuggest").fadeIn("fast");
    					var nsCompleteValue=item.name;
    					var index1 = nsCompleteValue.indexOf("<");
    					var index2 = nsCompleteValue.indexOf(">");
    					var subString = nsCompleteValue.substring(index1, index2 + 1);
    					while (nsCompleteValue.includes(subString) && nsCompleteValue.includes("</em>") ) {
    						nsCompleteValue = nsCompleteValue.replace(subString," ");
    						nsCompleteValue = nsCompleteValue.replace("</em>"," ");
    					    }
    					
    					if(item.images[0].url != null){
    						var nsCompleteValueWithImage = "<img src=" + item.images[0].url +" />"+"  " +nsCompleteValue;
    					}
    					var line = "<li class='productNameList'  data-url='"+item.url+"' data-value='"+nsCompleteValue+"'>"+nsCompleteValueWithImage;
    					line += "</li>";
    					$(".selectAutoSuggest[data-text='add_product_name']").append(line);
    				});
				}
			},
			
		});
		$(".selectAutoSuggest").show();
	} else {
		$(".selectAutoSuggest li").remove();
	}
});



$(document).on("click","li.productNameList",function(){
	var url = $(this).data("url");
	var value = $(this).data("value");
	url = ACC.config.encodedContextPath + url ;
    $('.LiveChat_AutoComplete').val(value);
    $('#productUrl').attr('href', url);
    $(".selectAutoSuggest li").remove();

    
});


/*$("#productUrl").click(function (event) {
    event.preventDefault();
    var popupTitle = $(this).data('product-popup-title');
    var url = $(this).attr("href");
    var popupTitleHtml = ACC.common.encodeHtml(popupTitle);
    
    if(url != '#')
    	{
    	    	$.get(url, undefined, undefined, 'html').done(function (data) {
    	    		ACC.colorbox.open(popupTitleHtml,{
    	    			html: data,
    	    			height: 500,
    	    			width:1000,
    	    			onComplete: function () {
    	    				ACC.common.refreshScreenReaderBuffer();
    	    			},
    	    			onClosed: function () {
    	    				ACC.common.refreshScreenReaderBuffer();
    	    			}
    	    		});
    	    	});
    	    	
    	}
   
});*/


$(document).on("click","#send",function(){
	jQuery.ajax({
	    url: ACC.config.encodedContextPath + "/chat/saveActivityQuestions",
	    type: "POST",
	    data: JSON.stringify({description: "what is return policy of this product",productCode: "280916" }),
	    dataType: "json",
	    contentType: "application/json",
	    success: function(result) {
	    alert("hii");
	    }
	}); 
	});


