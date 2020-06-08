ACC.chatbot = {

				
		
			};

$(document).ready(function ()
{
//	if ($('#registerForm').html() != null || $('#updateEmailForm').html() != null)
//	{
		$('#chatbox-content').scrollTop($('#chatbox-content')[0].scrollHeight);
//	}
		
		
});

$('#botchat').click(function(){
    $('#chatBot').show();
    $('#liveChat').hide();
})

$('#livechat').click(function(){
	$('#chatBot').hide();
	$('#liveChat').show();
	getQuestions();
})

$('#live-chat-close').click(function(){
	$('#chatBot').hide();
	$('#liveChat').hide();
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
function  getQuestions(){
	  $.ajax({
	    	url: ACC.config.encodedContextPath + "/chat/getActivityQuestions",
			type: 'GET',
			success : function(data){
				 for (var key in data.responseObject) {
						var userWriteContent ='<div>'+data.responseObject[key].description+'</div><div class="chat-message-group"><div class="textContent"><textarea id="liveChatText" class="chat-textarea" placeholder="Type your answer here.."></textarea><div class="send-icon" id="postQuestion"> <a class="button-chatbot is-white" title="send message"><i class="fa fa-paper-plane" aria-hidden="true"></i></a></div>	</div>	</div>';
						$( userWriteContent ).insertBefore( ".typing-text1" );
						
		            }
	        },
	    });
}
$('#chatTextarea').keypress(function(event){
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode == '13'){ 
    	var pCode = "TestProduct";
        sendMessage(pCode,false);
    }
});


$( window ).on( "load", function() {
    console.log( "window loaded" );
    var refInterval = window.setInterval('update1()', 30000); // 30 seconds
	var update1 = function() {
		console.log( "window loadedassadsa" );
	/*    $.ajax({
	    	url: ACC.config.encodedContextPath + "/addon/happbot",
			type: 'GET',
			success : function(data){
	            $('.voters').html(data);
	        },
	    });*/
	};
update1();
});


function fetchdata(){
	/* $.ajax({
	  url: 'fetch_details.php',
	  type: 'post',
	  success: function(response){
	   // Perform operation on the return value
	   alert(response);
	  }
	 });*/
	}

	$(document).ready(function(){
	 setInterval(fetchdata,5000);
	});
	
	$(document).ready(function() {
	    $("#postQuestion a").click(function() {
	    	var textAreaContent = $("#liveChatText").val();
	    	//alert("textAreaContent======"+textAreaContent);
	    	var uri = ACC.config.encodedContextPath + "/chat/saveActivityQuestions";
	    	var postData={};
	    	postData.description=textAreaContent;
	    	postData.productCode="1981415";
	    	jQuery.ajax({
	    	    url: uri,
	    	    type: "POST",
	    	    data: JSON.stringify(postData),
	    	    dataType: 'json',
	    	    contentType: "application/json",
	    	    success: function(result) {
	    	    	$("#liveChatText").val('');
	    	    }
	    	}); 

	    		
	    	
	    });
	});