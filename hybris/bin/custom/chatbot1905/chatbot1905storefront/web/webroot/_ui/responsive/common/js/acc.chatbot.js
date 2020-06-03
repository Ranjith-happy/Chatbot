ACC.chatbot = {
				
				_autoload: [
					"bindChatBot",
					"toggleChat"
				],

				storeData:{},
				
				bindChatBot: function(){

					$(document).on("click",".button-chatbot", function(e){
						var pCode = "TestProduct";
						sendMessage(pCode,false);
					});
					
					$(document).on("click",".placeOrderid", function(e){
						
						/*var textArea = "Place order for " +pCode;
						$("#chatTextarea").val(textArea);*/
						$("#contextCategory").val('place-order');
						var pCode = $(this).data('value');
						sendMessage(pCode,false);
						
					});
					
					$(document).on("click",".chatBotAllOrder", function(e){
						
						/*var textArea = "Place order for " +pCode;
						$("#chatTextarea").val(textArea);*/
						$("#contextCategory").val('place-order');
						var pCode = $(this).data('value');
						sendMessage(pCode,false);
						
					});
					
					$(document).on("click",".addToCartId", function(e){
				
						$("#contextCategory").val('addto-cart');
						var pCode = $(this).data('value');
						
						sendMessage(pCode,true);
						
					});
					

					$(document).on("click",".js-mini-cart-close-button", function(e){
						e.preventDefault();
						ACC.colorbox.close();
					});
					
				},
					
					getMapImage: function() {
							storeInformation = ACC.chatbot.storeData;
							var centerPoint = new google.maps.LatLng(storeInformation.latitude, storeInformation.longitude);
							
							var mapOptions = {
								zoom: 13,
								zoomControl: true,
								panControl: true,
								streetViewControl: false,
								mapTypeId: google.maps.MapTypeId.ROADMAP,
								center: centerPoint
							}
							var divElement = "chatbot-store-finder-map-"+storeInformation.mapIndex;
							var map = new google.maps.Map(document.getElementById(divElement), mapOptions);
							
							var marker = new google.maps.Marker({
								position: new google.maps.LatLng(storeInformation.latitude, storeInformation.longitude),
								map: map,
								title: "Store name",
								icon: "https://maps.google.com/mapfiles/marker" + 'A' + ".png"
							});
							var infowindow = new google.maps.InfoWindow({
				                content: ACC.common.encodeHtml("store name"),
								disableAutoPan: true
							});
							google.maps.event.addListener(marker, 'click', function (){
								infowindow.open(map, marker);
							});
						},
				toggleChat: function(){
				      if($("#chatbox-area").data("toggle")){
				        $('#chatbox-area').hide();
				        $('#chatbox-area').data('toggle',0);
				      }else{
				        $('#chatbox-area').show();
				        $('#chatbox-area').data('toggle',1);
						$('#chatbox-content').scrollTop($('#chatbox-content')[0].scrollHeight);
				      }
				    },
				updateMiniCartDisplay: function(){
					var cartItems = $(".js-mini-cart-link").data("miniCartItemsText");
					var miniCartRefreshUrl = $(".js-mini-cart-link").data("miniCartRefreshUrl");
					$.ajax({
						url: miniCartRefreshUrl,
						cache: false,
						type: 'GET',
						dataType: 'json',
						success: function(jsonData){
							var $cartItems = $("<span>").addClass("items-desktop hidden-xs hidden-sm").text(" " + cartItems);
							var $numberItem = $("<span>").addClass("nav-items-total").text(jsonData.miniCartCount).append($cartItems);
							$(".js-mini-cart-link .js-mini-cart-count").empty();
							$(".js-mini-cart-link .js-mini-cart-count").append($numberItem);
							$(".js-mini-cart-link .js-mini-cart-price").text(jsonData.miniCartPrice);	
						}
					});
				}

			};

$(document).ready(function ()
{
//	if ($('#registerForm').html() != null || $('#updateEmailForm').html() != null)
//	{
		$('#chatbox-content').scrollTop($('#chatbox-content')[0].scrollHeight);
//	}
		
		
});


$('#chat-icon').click(function(){
	$('#chatBox').show();
	$('#chat-icon').hide();
	$('#chatbox-area').show();
    $('#chatbox-area').data('toggle',1);
	$('#chatbox-content').scrollTop($('#chatbox-content')[0].scrollHeight);
})

$('#chat-close').click(function(){
	$('#chatBox').hide();
	$('#chat-icon').show();
	$('#chatbox-area').hide();
    $('#chatbox-area').data('toggle',0);
})

function sendMessage(pCode,addtoCart){
	
	var textAreaContent = $("#chatTextarea").val();
	var time = new Date();
	var timeText = time.toLocaleString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true })
	if(textAreaContent.trim() == ''){
		

	}else{
		
			var userWriteContent = '<div class="chat-message-group writer-user"><div class="chat-messages"><div class="message">'
			+ textAreaContent + '</div><div class="from">Today ' + timeText + '</div></div></div>';
		$( userWriteContent ).insertBefore( ".typing-text" );
	}
	$('#chatbox-content').scrollTop($('#chatbox-content')[0].scrollHeight);
	var contextCategory = $("#contextCategory").val();
	//alert(contextCategory);
	$("#chatTextarea").val('');
	$('.typing-text-display').show();
	if(ACC.storefinder.coords.latitude == undefined && textAreaContent.includes("store")){
		if(navigator.geolocation){
			navigator.geolocation.getCurrentPosition(
				function (position){
					ACC.storefinder.coords = position.coords;
					$.ajax({
						url: ACC.config.encodedContextPath + "/addon/happbot",
						type: 'GET',
						data: {term:textAreaContent, latitude:ACC.storefinder.coords.latitude, longitude:ACC.storefinder.coords.longitude, contextCategory: contextCategory, productCode:pCode, addToCartFlag:addtoCart},
						cache: false,
						success: function (html)
						{
							$('.typing-text-display').hide();
							if ($(html) != [])
							{
								$(html).insertBefore( ".typing-text" );
								$('#chatbox-content').scrollTop($('#chatbox-content')[0].scrollHeight);
								if(html.includes("chatbot-store-finder-map")){
									var latitude = $(html).find("input[id='storeLat']").val();
									var longitude = $(html).find("input[id='storeLong']").val();
									var mapIndex = $(html).find("input[id='mapIndex']").val();
									if(latitude != null){
										ACC.chatbot.storeData.latitude = latitude;
									}

									if(longitude != null){
										ACC.chatbot.storeData.longitude = longitude;
									}

									if(mapIndex != null){
										ACC.chatbot.storeData.mapIndex = mapIndex;
									}

									ACC.global.addGoogleMapsApi("ACC.chatbot.getMapImage");
								}
							}
						},
						error: function(xhr, status, error) {

							var userWriteContent = '<div class="chat-message-group"><div class="chat-messages"><div class="message"> Sorry there seems to be some technical issues. '
								  + 'You can reach out to our customer care help line for further details. Number - +918152061100 Emailid -saravnan.k@happiestminds.com '
								+ '</div><div class="from">Today ' + timeText + '</div></div></div>';
							$( userWriteContent ).insertBefore( ".typing-text" );
							$('#chatbox-content').scrollTop($('#chatbox-content')[0].scrollHeight);
							$('.typing-text-display').hide();
							}
					});
				},
				function (error)
				{
					console.log("An error occurred... The error code and message are: " + error.code + "/" + error.message);    // NOSONAR
				}
			);
		}
	}else{
		$.ajax({
			url: ACC.config.encodedContextPath + "/addon/happbot",
			type: 'GET',
			data: {term:textAreaContent, latitude:ACC.storefinder.coords.latitude, longitude:ACC.storefinder.coords.longitude, contextCategory: contextCategory, productCode:pCode, addToCartFlag:addtoCart},
			cache: false,
			success: function (html)
			{
				
				
				$('.typing-text-display').hide();
				if ($(html) != [])
				{
					$(html).insertBefore( ".typing-text" );
					$('#chatbox-content').scrollTop($('#chatbox-content')[0].scrollHeight);
					if(html.includes("chatbot-store-finder-map")){
						var latitude = $(html).find("input[id='storeLat']").val();
						var longitude = $(html).find("input[id='storeLong']").val();
						var mapIndex = $(html).find("input[id='mapIndex']").val();
						if(latitude != null){
							ACC.chatbot.storeData.latitude = latitude;
						}

						if(longitude != null){
							ACC.chatbot.storeData.longitude = longitude;
						}

						if(mapIndex != null){
							ACC.chatbot.storeData.mapIndex = mapIndex;
						}

						ACC.global.addGoogleMapsApi("ACC.chatbot.getMapImage");
					}
					
					if($('#voiceStartedFlag').length){
						var responseText = $(html).find("input[id='responseText']").val();
						var msg = new SpeechSynthesisUtterance(responseText);
						window.speechSynthesis.speak(msg);
					}
				}
				
				
			},
			error: function(xhr, status, error) {

				var userWriteContent = '<div class="chat-message-group"><div class="chat-messages"><div class="message"> Sorry there seems to be some technical issues. '
					  + 'You can reach out to our customer care help line for further details. Number - +918152061100 Emailid -saravnan.k@happiestminds.com '
					+ '</div><div class="from">Today ' + timeText + '</div></div></div>';
				$( userWriteContent ).insertBefore( ".typing-text" );
				$('#chatbox-content').scrollTop($('#chatbox-content')[0].scrollHeight);
				$('.typing-text-display').hide();
				}
		});
	}
	
	recognition.stop();
	
}

$('#chatTextarea').keypress(function(event){
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode == '13'){ 
    	var pCode = "TestProduct";
        sendMessage(pCode,false);
    }
});

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

/*
$( window ).on( "load", function() {
    console.log( "window loaded***************" );
    var refInterval = window.setInterval('update5()', 3000); // 30 seconds
	var update5 = function() {
	    $.ajax({
	    	url: ACC.config.encodedContextPath + "/addon/happbot",
			type: 'GET',
			success : function(data){
	            $('.voters').html(data);
	        },
	    });

		   
		   
		};
update5();
});*/


function fetchdata(){
	  $.ajax({
	    	url: ACC.config.encodedContextPath + "/chat/resetflag",
			type: 'GET',
			success : function(data){
	       console.log(data);
			},
	    });
}

function getLikesCount(){
	  $.ajax({
	    	url: ACC.config.encodedContextPath + "/chat/resetflag",
			type: 'GET',
			success : function(data){
	       console.log(data);
			},
	    });
}



	$(document).ready(function(){
	 setInterval(fetchdata,5000);
	});

