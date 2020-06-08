<jsp:useBean id="now" class="java.util.Date" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
<div class="chat-message-group">
	<div class="chat-thumb">
		<figure class="image is-32x32">

		</figure>
	</div>
	<div class="chat-messages">
		<c:if test="${chatBotResponseData.responseText ne '' }">
			<div class="message">${chatBotResponseData.responseText}</div>
			<input type="hidden" id="responseText" value="${chatBotResponseData.responseText}" /> 
		</c:if>
		<c:choose>
			
			
			<c:when test="${chat_category eq 'order-inquiry'}">
				<c:forEach items="${chatBotResponseData.orderData}" var="item">
					<c:forEach items="${item.consignments}" var="consignments" varStatus="cnt">
						<div>Your consignment info
						
						<table>
						<tbody>
							<th>Order Item</th>
							<th>Status</th>
							<th>Tracking ID </th>
							
								<tr>
									
									<td> Item ${loop.index}</td>
									<td>${consignments.status}</td>
									<td>${consignments.trackingID}</td>
									
								</tr>
							
						</tbody>
					</table>
						
						
						</div>
					</c:forEach>
				</c:forEach>
			</c:when>
			
			<c:when test="${chat_category eq 'delivery-info'}">
				<c:forEach items="${chatBotResponseData.orderData}" var="item">
					<c:forEach items="${item.consignments}" var="consignments">
						
						<div>
						Your order is on ${consignments.status} It is shipped on ${consignments.targetShipDate}
						 and you will be received the same on ${consignments.targetArrivalDate}
						</div>
						 
						
						
					</c:forEach>
				</c:forEach>
			</c:when>
			
			<c:when test="${chat_category eq 'expadite-delivery'}">
				<div>
				We have informed delivery team to speed-up the delivery process 
				</div>
			</c:when>
			
			
		</c:choose>

		<input type="hidden" id="contextCategory" value="${contextCategory}" />
		<div class="from">
			Today
			<fmt:formatDate type="time" dateStyle="short" timeStyle="short"
				value="${now}" />
		</div>
	</div>
</div>