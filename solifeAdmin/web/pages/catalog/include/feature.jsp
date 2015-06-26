<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="featureInfo" style="display:none">
<c:if test="${not empty productAttGroups}">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<%--自定义属性--%>
	<c:forEach items="${productAttGroups}" var="productAttGroup">
		<tr>
			<th colspan="2">
				${productAttGroup.productAttGroupName}
			</th>
		</tr>
		<cartmatic:collectionToList var="productAttGroupItems" itmes="${productAttGroup.productAttGroupItems}"></cartmatic:collectionToList>
		<c:forEach items="${productAttGroupItems}" var="productAttGroupItem" varStatus="varStatus" >
			<tr>
				<td class="FieldLabel">
					${productAttGroupItem.attribute.attributeName}:
				</td>
				<td>
					<attribute:input cssName="Field400" cssNameForTextarea="Field600" isdisplayHelp="true" attribute="${productAttGroupItem.attribute}" entity="${product}"></attribute:input>
				</td>
             </tr>
		</c:forEach>
	</c:forEach>
</table>
</c:if>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<c:if test="${(not empty product.productId)&&product.productKind==1}">
		<tr>
			<th colspan="2">
				产品附件项
			</th>
		</tr>
		<c:forEach items="${productAccessoryList}" var="accessoryEntry" varStatus="varStatus">
		<tr id="accessoryGroupId_${accessoryEntry.key.accessoryGroupId}">
			<td class="FieldLabel">
				<cartmatic:iconBtn icon="cross" extraCss="negative" textKey="common.link.delete" onclick="fnDelAccessionGroup(${accessoryEntry.key.accessoryGroupId})" />
				&nbsp;&nbsp;<input type="checkbox" onclick="fnSelectAllGroupItem(this)"/>
				&nbsp;&nbsp;${accessoryEntry.key.groupName}:
			</td>
			<td>
				<c:forEach items="${accessoryEntry.key.accessorys}" var="accessory" varStatus="varStatus_accessory">
					<c:set var="temp_accessoryId" value=",${accessory.accessoryId},"/>
					<input name="accessoryIds" id="accessoryIds_${accessory.accessoryId}" type="checkbox" value="${accessory.accessoryId}" <c:if test="${fn:contains(productAccessoryIds,temp_accessoryId)}">checked="checked"</c:if>/><label for="accessoryIds_${accessory.accessoryId}">${accessory.accessoryName}<c:if test="${accessory.price>0}">(<system:CurrencyForRate value="${accessory.price}" />)</c:if></label>&nbsp;&nbsp;
				</c:forEach>
			</td>
		</tr>
		</c:forEach>
		<tr>
			<td class="FieldLabel">
				添加附件项:
			</td>
			<td>
				<select id="accessorySelect"></select><cartmatic:iconBtn icon="plus" text="添加" onclick="fnAddAccessionGroup();" />
			</td>
        </tr>
	</c:if>
</table>
<c:if test="${isOriented}">
	<div class="add-btn">
		<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(2)" commonBtnValueKey="productDetail.back"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(4,3)" commonBtnValueKey="productDetail.next"/>
	</div>
</c:if>
<c:if test="${not empty product.productId}">
<script type="text/javascript">
var accessoryGroupJSONList=eval(${accessoryGroupJSONList});
</script>
</c:if>
</div>