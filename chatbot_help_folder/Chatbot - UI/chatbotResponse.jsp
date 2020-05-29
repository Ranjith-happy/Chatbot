<jsp:useBean id="now" class="java.util.Date" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
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
 			 <div class="col-md-12 col-md-offset-1">
				<div class="carousel slide" data-ride="carousel" data-type="multi" data-interval="3000" id="myCarousel">
				  <div class="carousel-inner">
  					<c:forEach items="${chatBotResponseData.products}" var="item" varStatus="index"> 
					    <div class="col-md-12 item ${index.first ? 'active' : ''}">
					      <div>
						  <product:productPrimaryImage product="${item}" format="thumbnail"/>
					      <div class="carousel__item--name">${item.name }</div>
								<div class="carousel__item--price"><format:price priceData="${item.price}"/></div>
					      </div>
					    </div>					  
	  				</c:forEach>
				  </div>
				  <a class="left carousel-control" href="#myCarousel" data-slide="prev"><i class="glyphicon glyphicon-chevron-left bootstrap-left-arrow"></i></a>
				  <a class="right carousel-control" href="#myCarousel" data-slide="next"><i class="glyphicon glyphicon-chevron-right bootstrap-right-arrow"></i></a>
				</div>
			</div>	
	  	</c:when>
	  	<c:when test="${fn:length(chatBotResponseData.pointOfServices) > 0 }">
	  		<c:forEach items="${chatBotResponseData.pointOfServices}" var="item"> 
	  			 <div class="message">${item.name}</div>
	  		</c:forEach>
	  	</c:when>
	  	<c:otherwise>
	  		<div class="message">Can you please enter a valid question. I can help you with product search, store search and order search</div>
	  	</c:otherwise>
  	</c:choose>
    
    <div class="from">Today <fmt:formatDate type="time" dateStyle="short" timeStyle="short" value="${now}" /></div>
  </div>
</div>