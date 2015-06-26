<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<cartmatic:iconBtn icon="plus" textKey="common.link.add" onclick="dlgAccessoryEditor_show();" />
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th><fmt:message key="accessory.accessoryName" /></th>
		<th><fmt:message key="accessory.price" /></th>
		<th><fmt:message key="accessory.sortOrder" /></th>
		<th><fmt:message key="common.action"/></th>
	</tr>
	<c:forEach items="${accessoryGroup.accessorys}" var="accessory">
		<tr>
			<td>
				${accessory.accessoryName}
			</td>
			<td>
				${accessory.price}
			</td>
			<td>
				${accessory.sortOrder}
			</td>
			<td>
				<cartmatic:iconBtn icon="pen" textKey="common.link.edit" onclick="dlgAccessoryEditor_show({accessoryId:${accessory.accessoryId}});" />
				<cartmatic:iconBtn icon="cross" extraCss="negative" textKey="common.link.delete" onclick="fnDeleteAccessory(${accessory.accessoryId},'${accessory.accessoryName}');" />
			</td>
		</tr>
	</c:forEach>
</table>