<%@ tag body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="/WEB-INF/StoreAdmin.tld" prefix="StoreAdmin" %>
<%@ attribute name="property" required="true" rtexprvalue="true" type="java.lang.String" %>
<tr>
	<td class="FieldLabel">
		<StoreAdmin:label key="${entityClassName}.${property}" />
	</td>
	<td>
		<form:input path="${property}" cssClass="Field400" />
		<form:errors path="${property}" cssClass="fieldError" />
	</td>
</tr>