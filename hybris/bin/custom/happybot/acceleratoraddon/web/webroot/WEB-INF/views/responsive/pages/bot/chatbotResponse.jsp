<jsp:useBean id="now" class="java.util.Date" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
<div class="chat-message-group">
  <div class="chat-thumb">
    <figure class="image is-32x32">
      
    </figure>
  </div>
  <div class="chat-messages">
  	<c:if test="${chatBotResponseData.responseText ne '' }">
  		<div class="message">${chatBotResponseData.responseText}</div>
  	</c:if>
  	<c:choose>
	  	<c:when test="${fn:length(chatBotResponseData.products) > 0 }">
 			 <div class="col-md-10 col-md-offset-1">
				<div class="carousel slide" data-ride="carousel" data-type="multi" data-interval="3000" id="myCarousel-${carousalIndex}">
				  <div class="carousel-inner">
  					<c:forEach items="${chatBotResponseData.products}" var="item" varStatus="index"> 
					    <div class="item ${index.first ? 'active' : ''}">
					      <div>
					      <c:url value="${item.url }" var="prodURL" />
						  <product:productPrimaryImage product="${item}" format="thumbnail"/>
					      <div class="carousel__item--name"><a href="${prodURL }" target="_blank">${item.name }</a></div>
								<div class="carousel__item--price"><format:price priceData="${item.price}"/></div>
					      </div>
					    </div>					  
	  				</c:forEach>
				  </div>
				  <a class="left carousel-control" href="#myCarousel-${carousalIndex}" data-slide="prev"><i class="glyphicon glyphicon-chevron-left bootstrap-left-arrow"></i></a>
				  <a class="right carousel-control" href="#myCarousel-${carousalIndex}" data-slide="next"><i class="glyphicon glyphicon-chevron-right bootstrap-right-arrow"></i></a>
				</div>
			</div>	
	  	</c:when>
	  	<c:when test="${fn:length(chatBotResponseData.pointOfServices) > 0 }">
	  			 <div id="chatbot-store-finder-map-${mapIndex }" class="chatbot-store-finder-map"></div>
			<input type="hidden" id="mapIndex" value="${mapIndex }" />
	  		<c:forEach items="${chatBotResponseData.pointOfServices}" var="item"> 
	  			<div class="message">
	  			 <c:url var="itemUrl" value="${item.url }"/>
	  			 <div class=""><a href="${fn:escapeXml(itemUrl)}" target="_blank">${item.displayName}</a></div>
	  			 <div class="">${item.address.line1}</div>
	  			 <div class="">${item.address.line2}</div>
	  			 <div class="">${item.address.town}</div>
	  			 <div class="">${item.address.postalCode}</div>
	  			 <div class="">${item.formattedDistance}</div>
	  			 <input type="hidden" id="storeLat" value="${item.geoPoint.latitude}" />
	  			 <input type="hidden" id="storeLong" value="${item.geoPoint.longitude}" />
	  			 </div>
	  		</c:forEach>
	  	</c:when>
	  	<c:when test="${fn:length(chatBotResponseData.orderHistoryData) > 0 }"><div class="message">
	  			 <table>
		  			 <tbody>
		  			 	<th>Order Number</th>
		  			 	<th>Status</th>
				  		<c:forEach items="${chatBotResponseData.orderHistoryData}" var="item"> 
				  			<tr>
							<spring:url value="/my-account/order/{/orderCode}" var="orderDetailsUrl" htmlEscape="false">
								<spring:param name="orderCode" value="${item.code}"/>
							</spring:url>
				  			 <td><a href="${orderDetailsUrl }" target="_blank">${item.code}</a></td>
				  			 <td><spring:theme code="text.account.order.status.display.${item.statusDisplay}"/></td>
				  			 </tr>
				  		</c:forEach>
		  			</tbody>
	  			 	</table>
	  			 </div>
	  	</c:when>
	  	<c:when test="${fn:length(chatBotResponseData.orderData) > 0 }">
	  		<c:forEach items="${chatBotResponseData.orderData}" var="item"> 
	  			<div class="message">
				<spring:url value="/my-account/order/{/orderCode}" var="orderDetailsUrl" htmlEscape="false">
					<spring:param name="orderCode" value="${item.code}"/>
				</spring:url>
	  			 <div class="">Your order <a href="${orderDetailsUrl }" target="_blank">${item.code}</a> is currently <b><spring:theme code="text.account.order.status.display.${item.statusDisplay}"/></b></div>
	  			 </div>
	  		</c:forEach>
	  	</c:when>
	  	<c:when test="${fn:length(chatBotResponseData.couponData) > 0 }">
			<div id="wrapper">
			            
			        <div id="wheel">
			            <div id="inner-wheel">
			            	<c:forEach items="${chatBotResponseData.couponData}" var="item" varStatus="pos"> 
			            		<div class="sec"><span class="fa fa-usd">
			            			<input type="hidden" value="${item.name }" id="spin_description_${pos.index }"/></span>
		            			</div>
			            	</c:forEach>
			            </div>       			           
			            <div id="spin">
			                <div id="inner-spin"></div>
			            </div>			            
			            <div id="shine"></div>
			        </div>			     			        
			        <div id="txt"></div>
			  </div>
	  	</c:when>
  	</c:choose>
    
	<input type="hidden" id="contextCategory" value="${contextCategory}" />
    <div class="from">Today <fmt:formatDate type="time" dateStyle="short" timeStyle="short" value="${now}" /></div>
  </div>
</div>