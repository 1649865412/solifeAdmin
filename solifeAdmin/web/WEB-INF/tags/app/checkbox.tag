<%@ tag body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="/WEB-INF/StoreAdmin.tld" prefix="StoreAdmin"%>
<%@ attribute name="property" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="value" required="true" rtexprvalue="true" type="java.lang.Object"%>
<%@ attribute name="valueLabelKey" required="false"  rtexprvalue="true" type="java.lang.String"%>
<tr>
	<td class="FieldLabel">
		<StoreAdmin:label key="${entityClassName}.${property}" />
	</td>
	<td>
		<form:checkbox path="${property}" value="${value}" />
		<c:if test="${not empty valueLabelKey}">
		  <fmt:message key="${valueLabelKey}"/>
		</c:if>
		<form:errors path="${property}" cssClass="fieldError" />
	</td>
</tr>