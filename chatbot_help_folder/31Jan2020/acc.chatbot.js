ACC.chatbot = {
				
				_autoload: [
					"bindChatBot",
					"toggleChat"
				],

				storeData:{},
				
				bindChatBot: function(){

					$(document).on("click",".button-chatbot", function(e){
						sendMessage();
					});

					$(document).on("click",".js-mini-cart-close-button", function(e){
						e.preventDefault();
						ACC.colorbox.close();
					});
				},
				getLocation: function() {
					  if (navigator.geolocation) {
					    navigator.geolocation.getCurrentPosition(showPosition, showError);
					  } else { 
					    x.innerHTML = "Geolocation is not supported by this browser.";
					  }
					},

					showPosition: function(position) {
					  x.innerHTML = "Latitude: " + position.coords.latitude + 
					  "<br>Longitude: " + position.coords.longitude;
					},

					showError: function(error) {
					  switch(error.code) {
					    case error.PERMISSION_DENIED:
					      x.innerHTML = "User denied the request for Geolocation."
					      break;
					    case error.POSITION_UNAVAILABLE:
					      x.innerHTML = "Location information is unavailable."
					      break;
					    case error.TIMEOUT:
					      x.innerHTML = "The request to get user location timed out."
					      break;
					    case error.UNKNOWN_ERROR:
					      x.innerHTML = "An unknown error occurred."
					      break;
					  }
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
							
							var map = new google.maps.Map(document.getElementById("chatbot-store-finder-map"), mapOptions);
							
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

function sendMessage(){
	var textAreaContent = $("#chatTextarea").val();
	var time = new Date();
	var timeText = time.toLocaleString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true })
	var userWriteContent = '<div class="chat-message-group writer-user"><div class="chat-messages"><div class="message">'
								+ textAreaContent + '</div><div class="from">Today ' + timeText + '</div></div></div>';
	$( userWriteContent ).insertBefore( ".typing-text" );
	$('#chatbox-content').scrollTop($('#chatbox-content')[0].scrollHeight);
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
						data: {term:textAreaContent, latitude:ACC.storefinder.coords.latitude, longitude:ACC.storefinder.coords.longitude},
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
									if(latitude != null){
										ACC.chatbot.storeData.latitude = latitude;
									}

									if(longitude != null){
										ACC.chatbot.storeData.longitude = longitude;
									}

									ACC.global.addGoogleMapsApi("ACC.chatbot.getMapImage");
								}
							}
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
			data: {term:textAreaContent, latitude:ACC.storefinder.coords.latitude, longitude:ACC.storefinder.coords.longitude},
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
						alert(latitude);
						if(latitude != null){
							ACC.chatbot.storeData.latitude = latitude;
						}

						if(longitude != null){
							ACC.chatbot.storeData.longitude = longitude;
						}

						ACC.global.addGoogleMapsApi("ACC.chatbot.getMapImage");
					}
				}
			}
		});
	}
	
}

$('#chatTextarea').keypress(function(event){
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode == '13'){ 
        sendMessage();
    }
});


