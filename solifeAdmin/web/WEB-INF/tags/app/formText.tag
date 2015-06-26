<%@ tag body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="/WEB-INF/StoreAdmin.tld" prefix="StoreAdmin" %>
<%@ attribute name="label" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="value" required="true" rtexprvalue="true" %>
<tr>
	<td class="FieldLabel">
		<StoreAdmin:label key="${label}" />
	</td>
	<td>
		<SPAN>${value}</SPAN>
	</td>
</tr>
