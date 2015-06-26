<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${tbCatPropRefer.tbCatPropReferName}" entityHeadingKey="tbCatPropReferDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
	<%--
	<cartmatic:cartmaticBtn btnType="saveAndNext" onclick="return fnDoSaveAndNext(this);" />
	--%>
    <c:if test="${tbCatPropRefer.tbCatPropReferId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="tbCatPropRefer.*" />
	<form:form method="post" cssClass="mainForm" id="tbCatPropRefer" commandName="tbCatPropRefer"
			action="${ctxPath}/supplier/tbCatPropRefer.html" onsubmit="return validateTbCatPropRefer(this);">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
			<tr>
				<th colspan="2">
					<fmt:message key="tbCatPropReferDetail.heading" /><input type="hidden" name="tbCatPropReferId" value="${tbCatPropRefer.tbCatPropReferId}"/>
				</th>
			</tr>
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="tbCatPropRefer.tbCatPropId" />
				</td>
				<td>
					<input class="Field400" type="text" name="tbCatPropId" id="tbCatPropId" value="<c:out value="${tbCatPropRefer.tbCatPropId}"/>" readonly="readonly"/>
				</td>
		    </tr>
		    <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="tbCatPropRefer.tbCatPropName" />
				</td>
				<td>
					<input class="Field400" type="text" name="tbCatPropName" id="tbCatPropName" value="<c:out value="${tbCatPropRefer.tbCatPropName}"/>" />
				</td>
		    </tr>
		    <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="tbCatPropRefer.accessoryGroupId" />
				</td>
				<td>
					<input class="Field400" type="text" name="accessoryGroupCode" id="accessoryGroupCode" value="<c:out value="${empty tbCatPropRefer.accessoryGroup.groupCode?param.accessoryGroupCode:tbCatPropRefer.accessoryGroup.groupCode}"/>" />(请直接输入对应的附件组编码)
					<input type="hidden" name="accessoryGroupId" id="accessoryGroupId" value="<c:out value="${tbCatPropRefer.accessoryGroupId}"/>" />
				</td>
		    </tr>
		    <c:if test="${not empty tbCatPropRefer.accessoryGroup.groupName}">
		    <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="tbCatPropRefer.accessoryGroupName" />
				</td>
				<td>
					<input class="Field400" type="text" value="<c:out value="${tbCatPropRefer.accessoryGroup.groupName}"/>" readonly="readonly"/>
				</td>
		    </tr>
		    <tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="tbCatPropRefer.accessoryGroup.item" />
				</td>
				<td>
					<c:forEach items="${tbCatPropRefer.accessoryGroup.accessorys}" var="accessory" varStatus="varStatus">
						${accessory.accessoryName}<c:if test="${!varStatus.last}">;&nbsp;</c:if>
					</c:forEach>
				</td>
		    </tr>
		    </c:if>
  		</table>
  		
  		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
			<c:if test="${not empty tbCatPropRefer.tbCatPropValueRefers}">
				<tr>
					<th>
						<fmt:message key="tbCatPropValueRefer.tbCatPropValueReferId" />
					</th>
				</tr>
				<tr>
					<td>
						 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
							<tr>
								<th><fmt:message key="tbCatPropValueRefer.tbCatPropValueId" /></th>
								<th><fmt:message key="tbCatPropValueRefer.tbCatPropValueName" /></th>
								<th><fmt:message key="tbCatPropValueRefer.accessoryId" /></th>
								<th><fmt:message key="common.action"/></th>
							</tr>
							<c:forEach items="${tbCatPropRefer.tbCatPropValueRefers}" var="tbCatPropValueRefer" varStatus="varStatus">
								<tr>
									<td>
										${tbCatPropValueRefer.tbCatPropValueId}
									</td>
									<td>
										${tbCatPropValueRefer.tbCatPropValueName}
									</td>
									<td>
										<c:if test="${not empty tbCatPropRefer.accessoryGroupId}">
											<input id="tbCatPropValueId_${tbCatPropValueRefer.id}" type="hidden" value="${tbCatPropValueRefer.tbCatPropValueId}" >
											<input class="Field200" type="text" id="accessoryName_${tbCatPropValueRefer.id}"  value="<c:out value="${tbCatPropValueRefer.accessory.accessoryName}"/>" />
										</c:if>
									</td>
									<td>
										<c:if test="${not empty tbCatPropRefer.accessoryGroupId}">
											<cartmatic:iconBtn icon="save" textKey="common.link.save" onclick="fnSaveTbCatPropValue(${tbCatPropValueRefer.id})" /><span id="msg_${tbCatPropValueRefer.id}"></span>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
		        </tr>
			</c:if>
		</table>
</form:form>
<v:javascript formName="tbCatPropRefer" staticJavascript="false" />
<script type="text/javascript">
function fnSaveTbCatPropValue(id){
	var tbCatPropValueId=$j("#tbCatPropValueId_"+id).val();
	var accessoryName=$j("#accessoryName_"+id).val();
	$j.post(__ctxPath+"/supplier/tbCatPropValueRefer.html?doAction=save",{tbCatPropValueReferId:id,tbCatPropValueId:tbCatPropValueId,accessoryName:accessoryName},function(result){
			if(result.status==1){
				$j("#msg_"+id).html("保存成功!");
			}else{
				var jFiledErrors = result.data.jFiledErrors;
				$j.each(jFiledErrors, function(index, jFieldError){
					$j("#msg_"+id).html(jFieldError.message);
					sysMsg(jFieldError.field+","+jFieldError.message,true);
				});
			}
		},"json");
}
    document.forms["tbCatPropRefer"].elements["accessoryGroupId"].focus();
</script>
