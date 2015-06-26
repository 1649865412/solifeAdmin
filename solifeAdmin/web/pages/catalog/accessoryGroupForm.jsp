<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<app:pageHeading entityName="${accessoryGroup.accessoryGroupName}" entityHeadingKey="accessoryGroupDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'groupName');" />
    <c:if test="${accessoryGroup.accessoryGroupId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this,'groupName');" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="accessoryGroup.*" />
<form class="mainForm" method="post" action="${ctxPath}/catalog/accessoryGroup.html" id="accessoryGroupForm" onsubmit="return validateAccessoryGroup(this);">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="accessoryGroup.accessoryGroupId" />
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="accessoryGroup.groupName" />
			</td>
			<td>
				<input class="Field400" type="text" name="groupName" id="groupName" value="${accessoryGroup.groupName}" />
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="accessoryGroup.groupCode" />
			</td>
			<td>
				<input class="Field400" type="text" name="groupCode" id="groupCode" value="${accessoryGroup.groupCode}" />
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="accessoryGroup.groupDesc" />
			</td>
			<td>
				<input class="Field600" type="text" name="groupDesc" id="groupDesc" value="${accessoryGroup.groupDesc}"/>
			</td>
	    </tr>
 	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<c:if test="${accessoryGroup.accessoryGroupId!=null}">
			<tr>
				<th>
					<fmt:message key="accessoryGroupDetail.accessoryGroup.accessory" />
				</th>
			</tr>
			<tr>
				<td id="accessoryListContent">
					<%@include file="include/accessoryListContent.jsp"%>
				</td>
	        </tr>
		</c:if>
	</table>
	<input type="hidden" name="accessoryGroupId" id="accessoryGroupId" value="${accessoryGroup.accessoryGroupId}" />
</form>
<v:javascript formName="accessoryGroup" staticJavascript="false" />
<fmt:message key="skuOptionDetail.edit.sku.option.value.title" var="editSkuOptionValueTitle" />
<fmt:message key="button.save" var="buttonSave" />
<app:ui_dialog id="AccessoryEditor" title="${editSkuOptionValueTitle}" width="500" height="260" url="${ctxPath}/catalog/accessory/dialog.html?doAction=edit&from=list" buttons="'${buttonSave}':fnSaveAccessory" callback="fnInitSkuOptionValueForm">
</app:ui_dialog>
<script type="text/javascript">
	function fnSaveAccessory() {
		var form = $j("#accessoryForm");
		if (validateAccessory(form.get(0))) {
			var paramData = form.serializeArray();
			var accessoryGroupId = $j("#accessoryGroupId").val();
			$j.post("${ctxPath}/catalog/accessory.html?doAction=save&accessoryGroupId="+ accessoryGroupId, paramData, function (result){
				sysMsg(result.msg,result.status!=1);
				if(result.status==1){
					fnRefurbishAccessoryList();
				}
			},"json");
		}
	}
	function fnRefurbishAccessoryList() {
		dlgAccessoryEditor_close();
		$j("#accessoryListContent").load("${ctxPath}/catalog/accessoryGroup/dialog.html?doAction=refurbishAccessoryList&accessoryGroupId=${accessoryGroup.accessoryGroupId}");
	}
	function fnDeleteAccessory(accessoryId,name){
		if(confirm("确认要删除附件项"+name+"?")){
			$j.post("${ctxPath}/catalog/accessory.html?doAction=delete&accessoryId="+ accessoryId, null,function (result){
					sysMsg(result.msg,result.status!=1);
					if(result.status==1){
						fnRefurbishAccessoryList();
					}
				},"json");
		}
	}
	function fnInitSkuOptionValueForm() {
		$j("#skuOptionValueType_temp").remove();
	}
</script>