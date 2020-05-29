//set default degree (360*5)
var degree = 1800;
//number of clicks = 0
var clicks = 0;

$(document).ready(function(){
	
	/*WHEEL SPIN FUNCTION*/
	$(document).on('click', '#spin', function(){
		//add 1 every click
		clicks ++;
		
		/*multiply the degree by number of clicks
	  generate random number between 1 - 360, 
    then add to the new degree*/
		var newDegree = degree*clicks;
		var extraDegree = Math.floor(Math.random() * (360 - 1 + 1)) + 1;
		totalDegree = newDegree+extraDegree;
		var offerDesc = "";
		if((extraDegree>0 && extraDegree <=30) || (extraDegree>330 && extraDegree <=360)){
			offerDesc = $("#spin_description_5").val();
		}
		else if(extraDegree>30 && extraDegree <=90){
			offerDesc = $("#spin_description_4").val();
		}
		else if(extraDegree>90 && extraDegree <=150){
			offerDesc = $("#spin_description_3").val();
		}
		else if(extraDegree>150 && extraDegree <=210){
			offerDesc = $("#spin_description_2").val();
		}
		else if(extraDegree>210 && extraDegree <=270){
			offerDesc = $("#spin_description_1").val();
		}
		else if(extraDegree>270 && extraDegree <=330){
			offerDesc = $("#spin_description_0").val();
		}
		
		/*let's make the spin btn to tilt every
		time the edge of the section hits 
		the indicator*/
		$('#wheel .sec').each(function(){
			var t = $(this);
			var noY = 0;
			
			var c = 0;
			var n = 700;	
			var interval = setInterval(function () {
				c++;				
				if (c === n) { 
					clearInterval(interval);				
				}	
					
				var aoY = t.offset().top;
//				$("#txt").html(aoY);
//				console.log(aoY);
				
				/*23.7 is the minumum offset number that 
				each section can get, in a 30 angle degree.
				So, if the offset reaches 23.7, then we know
				that it has a 30 degree angle and therefore, 
				exactly aligned with the spin btn*/
				if(aoY < 23.89){
					console.log('<<<<<<<<');
					$('#spin').addClass('spin');
					setTimeout(function () { 
						$('#spin').removeClass('spin');
					}, 100);	
				}
			}, 10);
			
			$('#inner-wheel').css({
				'transform' : 'rotate(' + totalDegree + 'deg)'			
			});
		 
			noY = t.offset().top;
			
		});
		const el = document.querySelector('#inner-wheel');

		el.addEventListener('transitionend', function() {
			var time = new Date();
			var timeText = time.toLocaleString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true })
			var userWriteContent = '<div class="chat-message-group"><div class="chat-messages"><div class="message"> Congratulations!!! '
				+ offerDesc + '</div><div class="from">Today ' + timeText + '</div></div></div>';
			$( userWriteContent ).insertBefore( ".typing-text" );
			$('#chatbox-content').scrollTop($('#chatbox-content')[0].scrollHeight);
		});
	});
	
	
	
});//DOCUMENT READY
	

