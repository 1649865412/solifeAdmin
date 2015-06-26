<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form id="accessoryForm">
	<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<tr>
			<td>
				<input type="hidden" name="version" value="${accessory.version}" />
				<input type="hidden" name="accessoryId" value="${accessory.accessoryId}" />
				<StoreAdmin:label key="accessory.accessoryName" />
			</td>
			<td style="width: 70%">
				<input class="Field200" type="text" name="accessoryName" id="accessoryName" value="${accessory.accessoryName}" />
			</td>
		</tr>
		<tr>
			<td>
				<StoreAdmin:label key="accessory.price" />
			</td>
			<td>
				<input class="Field200" type="text" name="price" id="price" value="${accessory.price}" />
			</td>
		</tr>
		<tr>
			<td>
				<StoreAdmin:label key="accessory.sortOrder" />
			</td>
			<td>
				<input class="Field200" type="text" name="sortOrder" id="sortOrder" value="${accessory.sortOrder}" />
			</td>
		</tr>
	</table>
</form>
<v:javascript formName="accessory" staticJavascript="false" />