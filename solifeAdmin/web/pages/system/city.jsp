<%@ include file="/common/taglibs.jsp"%>
<table class="table-content" cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td >
		<b>Name:</b>&nbsp;${state.regionName}
		<br/>
		<b>Code:</b>&nbsp;${state.regionCode}
		</td>		
	</tr>
	<tr>
		<td>
		<c:forEach var="city" items="${cities}" >
			<c:out value="${city.regionName}" />,
		</c:forEach>
		</td>		
	</tr>
</table>