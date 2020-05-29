ACC.chatbot = {
				
				_autoload: [
					"bindChatBot",
					"toggleChat"
				],

				bindChatBot: function(){

					$(document).on("click",".button-chatbot", function(e){
						
						var textAreaContent = $("#chatTextarea").text();
						var time = new Date();
						var timeText = time.toLocaleString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true })
						var userWriteContent = '<div class="chat-message-group writer-user"><div class="chat-messages"><div class="message">'
													+ textAreaContent + '</div><div class="from">Today ' + timeText + '</div></div></div>';
						$( userWriteContent ).insertBefore( ".typing-text" );
						$('#chatbox-content').scrollTop($('#chatbox-content')[0].scrollHeight);
						$("#chatTextarea").text('');
						if(ACC.storefinder.coords.latitude == undefined && textAreaContent.includes("store")){
							if(navigator.geolocation){
								navigator.geolocation.getCurrentPosition(
									function (position){
										ACC.storefinder.coords = position.coords;
									},
									function (error)
									{
										console.log("An error occurred... The error code and message are: " + error.code + "/" + error.message);    // NOSONAR
									}
								);
							}
						}
						$.ajax({
							url: ACC.config.encodedContextPath + "/addon/happbot",
							type: 'GET',
							data: {term:textAreaContent, latitude:ACC.storefinder.coords.latitude, longitude:ACC.storefinder.coords.longitude},
							cache: false,
							success: function (html)
							{
								if ($(html) != [])
								{
									$(html).insertBefore( ".typing-text" );
									$('#chatbox-content').scrollTop($('#chatbox-content')[0].scrollHeight);
								}
							}
						});
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



var element = $('.floating-chat');
var myStorage = localStorage;

if (!myStorage.getItem('chatID')) {
    myStorage.setItem('chatID', createUUID());
}

setTimeout(function() {
    element.addClass('enter');
}, 1000);

element.click(openElement);

function openElement() {
    var messages = element.find('.messages');
    var textInput = element.find('.text-box');
    element.find('>i').hide();
    element.addClass('expand');
    element.find('.chat').addClass('enter');
    var strLength = textInput.val().length * 2;
    textInput.keydown(onMetaAndEnter).prop("disabled", false).focus();
    element.off('click', openElement);
    element.find('.header button').click(closeElement);
    element.find('#sendMessage').click(sendNewMessage);
    messages.scrollTop(messages.prop("scrollHeight"));
}

function closeElement() {
    element.find('.chat').removeClass('enter').hide();
    element.find('>i').show();
    element.removeClass('expand');
    element.find('.header button').off('click', closeElement);
    element.find('#sendMessage').off('click', sendNewMessage);
    element.find('.text-box').off('keydown', onMetaAndEnter).prop("disabled", true).blur();
    setTimeout(function() {
        element.find('.chat').removeClass('enter').show()
        element.click(openElement);
    }, 500);
}

function createUUID() {
    // http://www.ietf.org/rfc/rfc4122.txt
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
}

function sendNewMessage() {
    var userInput = $('.text-box');
    var newMessage = userInput.html().replace(/\<div\>|\<br.*?\>/ig, '\n').replace(/\<\/div\>/g, '').trim().replace(/\n/g, '<br>');

    if (!newMessage) return;

    var messagesContainer = $('.messages');

    messagesContainer.append([
        '<li class="self">',
        newMessage,
        '</li>'
    ].join(''));

    // clean out old message
    userInput.html('');
    // focus on input
    userInput.focus();

    messagesContainer.finish().animate({
        scrollTop: messagesContainer.prop("scrollHeight")
    }, 250);
}

function onMetaAndEnter(event) {
    if ((event.metaKey || event.ctrlKey) && event.keyCode == 13) {
        sendNewMessage();
    }
}


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

