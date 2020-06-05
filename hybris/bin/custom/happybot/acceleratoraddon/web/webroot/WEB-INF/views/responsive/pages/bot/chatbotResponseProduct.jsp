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
			
			
			<c:when test="${chat_category eq 'productdetail-inquiry'}">
				<div>
						We are Sorry. There is no exchange offer available right now.
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