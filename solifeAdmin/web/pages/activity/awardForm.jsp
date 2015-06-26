<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<app:pageHeading entityName="${award.id}"
	entityHeadingKey="awardDetail.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave();" />
	<cartmatic:cartmaticBtn btnType="cancel"
		onclick="return fnDoCancelForm(this);" />
</content>

<app:showBindErrors bindPath="award.*" />
<form:form method="post" cssClass="mainForm" id="awardForm" name="awardForm" commandName="award" action="${ctxPath}/activity/award.html">
	<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<tr>
			<td class="FieldLabel">
				中奖等级
				<input type="hidden" name="id" id="id" value="${award.id}" />
			</td>
			<td>
				<span>
					<form:input path="level" class="Field400"  />
				</span>
				<cartmatic:ui_tip id="giftCertificateNoDesc">(输入中奖等级，纯数字：1, 2, 3)
				</cartmatic:ui_tip>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				等级名称
			</td>
			<td>
				<span>
					<form:input path="title" class="Field400"  />
				</span>
				<cartmatic:ui_tip id="purchaserDesc">
					输入等级名称：1为一等奖，2为二等奖，以此类推
				</cartmatic:ui_tip>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				中奖概率
			</td>
			<td>
				<span><form:input path="chance" class="Field400" />
				</span>
				<cartmatic:ui_tip id="recipientDesc">
					输入中奖概率：输入数值，最终会转化为百分比
				</cartmatic:ui_tip>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				奖品名称
			</td>
			<td>
				<span><form:input path="prize" class="Field400" />
				</span>
				<cartmatic:ui_tip id="recipientEmailDesc">
					中奖等级对应的奖品名称，如“XXX手机”
				</cartmatic:ui_tip>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				中奖描述
			</td>
			<td>
				<textarea class="Field400" rows="20" id="description" name="description">${award.description}</textarea>
			</td>
		</tr>
		<tr>
				<td class="FieldLabel">
					创建时间
				</td>
				<td>
					<fmt:formatDate value="${award.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
					<input type="hidden" name="createTime" value="<fmt:formatDate value="${award.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />">
				</td>
			</tr>
			<tr>
				<td class="FieldLabel">
					最后更新时间
				</td>
				<td>
					<fmt:formatDate value="${award.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
	</table>
</form:form>

<v:javascript formName="award" staticJavascript="false" />

<script type="text/javascript" defer="true">

$j(document).ready(function () {
	applyValidate($("level"),"required");
});	

function fnDoSave(){
	var confirmMsg = __FMT.common_message_confirmSaveThis+"?";
	if(validateAction()){
    	if (confirm(confirmMsg)) {
	    	document.awardForm.action += "?doAction=save";
			document.awardForm.submit();
	    }
    }
}

function validateAction(){
	if(!validateAward($("awardForm"))){
    	return false;
   	}
    return true;
}
</script>