<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ attribute name="icon" required="true"
	description="book|calendar|chat|check|clock|cog|comment|cross|downarrow|
	fork|heart|home|key|leftarrow|lock|loop|magnifier|mail|move|pen|
	pin|plus|reload|rightarrow|rss|tag|trash|unlock|uparrow|user|
	reference http://css3buttons.michaelhenriksen.dk/"%><%@ 
	attribute name="id" rtexprvalue="true"%><%@ 
	attribute name="onclick" rtexprvalue="true"%><%@ 
	attribute name="hrefUrl" rtexprvalue="true"%><%@ 
	attribute name="extraCss" rtexprvalue="true"%><%@ 
	attribute name="extraStyle" rtexprvalue="true"%><%@
	attribute name="text"  rtexprvalue="true"%><%@ 
	attribute name="textKey" rtexprvalue="true"%><%@ attribute name="isDisabled" type="java.lang.Boolean" description="true or false,default false" rtexprvalue="true"%>
<a class="button <c:if test="${not empty isDisabled&&isDisabled}">btn-disabled </c:if> ${extraCss}" href="<c:choose><c:when test="${empty hrefUrl||not empty isDisabled&&isDisabled}">javascript:void(false);</c:when><c:otherwise>${hrefUrl}</c:otherwise></c:choose>"<c:if test="${!(not empty isDisabled&&isDisabled)}"> onclick="${onclick}"</c:if><c:if test="${not empty id}"> id="${id}"</c:if><c:if test="${not empty extraStyle}"> style="${extraStyle}"</c:if>>
<span class="${icon} icon"></span>${text}<c:if test="${not empty textKey}"><fmt:message key="${textKey}"/></c:if></a>