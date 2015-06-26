<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<app:pageHeading entityName="${giftCertificate.giftCertificateNo}"
	entityHeadingKey="giftCertificateDetail.heading" />

<content tag="buttons">
<c:choose>
	<c:when test="${empty giftCertificate.id}">
		<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave();" />
		<cartmatic:cartmaticBtn btnType="saveSendMail" onclick="fnDoSaveAndSendEmail();" />
	</c:when>
	<c:when test="${(!empty giftCertificate.id)  and (giftCertificate.status == 0) }">
	</c:when>
	<c:when test="${(!empty giftCertificate.id)  and (giftCertificate.status == 1) }">
		<cartmatic:cartmaticBtn btnType="forbid" onclick="return fnDoForbit();" />
		<cartmatic:cartmaticBtn btnType="sendMail" onclick="fnDoSendEmail();" />
	</c:when>
	<c:when test="${(!empty giftCertificate.id)  and (giftCertificate.status == 2) }">
		<cartmatic:cartmaticBtn btnType="active" onclick="return fnDoActive();" />
	</c:when>
</c:choose>
<cartmatic:cartmaticBtn btnType="cancel"
	onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="giftCertificate.*" />
<form:form method="post" cssClass="mainForm" id="giftCertificateForm" name="giftCertificateForm" commandName="giftCertificate" action="${ctxPath}/sales/giftCertificate.html">
	<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="giftCertificate.giftCertificateNo" />
				<input type="hidden" name="giftCertificateId" id="giftCertificateId" value="${giftCertificate.giftCertificateId}" />
			</td>
			<td>
				<span><input name="giftCertificateNo" id="giftCertificateNo" class="Field400" readonly="true" value="${giftCertificate.giftCertificateNo}" />
				</span>
				<cartmatic:ui_tip id="giftCertificateNoDesc">(<fmt:message key="giftCertificate.giftCertificateNo.format" />)<fmt:message key="giftCertificate.giftCertificateNo.readme" />
				</cartmatic:ui_tip>
				&nbsp;&nbsp;&nbsp;
				<c:if test="${not empty giftCertificate.giftCertificateId}">
					<b><fmt:message key="giftCertificate.sa_state.s${giftCertificate.status}" />
					</b>
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="giftCertificate.purchaser" />
			</td>
			<td>
				<span><form:input path="purchaser" class="Field400" />
				</span>
				<cartmatic:ui_tip id="purchaserDesc">
					<fmt:message key="giftCertificate.purchaser.tip" />
				</cartmatic:ui_tip>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="giftCertificate.recipient" />
			</td>
			<td>
				<span><form:input path="recipient" class="Field400" />
				</span>
				<cartmatic:ui_tip id="recipientDesc">
					<fmt:message key="giftCertificate.recipient.tip" />
				</cartmatic:ui_tip>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="giftCertificate.recipientEmail" />
			</td>
			<td>
				<span><form:input path="recipientEmail" class="Field400" />
				</span>
				<cartmatic:ui_tip id="recipientEmailDesc">
					<fmt:message key="giftCertificate.recipientEmail.tip" />
				</cartmatic:ui_tip>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="giftCertificate.message" />
			</td>
			<td>
				<textarea class="Field400" rows="20" id="message" name="message">${giftCertificate.message}</textarea>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="giftCertificate.giftCertAmt" />
			</td>
			<td>
				<span><form:input path="giftCertAmt" class="Field400" />
				</span>
				<cartmatic:ui_tip id="giftCertAmtDesc">
					<fmt:message key="giftCertificate.giftCertAmt.tip" />
				</cartmatic:ui_tip>
				(
				<system:CurrencyForRate value="${appConfig.giftCertificateMinAmt}" />
				-
				<system:CurrencyForRate value="${appConfig.giftCertificateMaxAmt}" />
				)
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				类型
			</td>
			<td>
				<span>
				<select name="giftType">
				 <option value="0" <c:if test="${giftCertificate.giftType==0}">selected="selected"</c:if>>普通</option>
				 <option value="1" <c:if test="${giftCertificate.giftType==1}">selected="selected"</c:if>>500元礼品卡</option>
				 <option value="2" <c:if test="${giftCertificate.giftType==2}">selected="selected"</c:if>>1000元礼品卡</option>
				</select>
				</span>
			</td>
		</tr>
		<c:if test="${not empty giftCertificate.giftCertificateId}">
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="giftCertificate.remainedAmt" />
				</td>
				<td>
					<span><form:input path="remainedAmt" class="Field400" />
					</span>
				</td>
			</tr>
			<tr>
				<td class="FieldLabel">
					各月份余额
				</td>
				<td>
					第1月：${giftCertificate.m1Amt} &nbsp;
					第2月：${giftCertificate.m2Amt} &nbsp;
					第3月：${giftCertificate.m3Amt} &nbsp;
					第4月：${giftCertificate.m4Amt} &nbsp;
					第5月：${giftCertificate.m5Amt} &nbsp;
					第6月：${giftCertificate.m6Amt} &nbsp;
					第7月：${giftCertificate.m7Amt} &nbsp;
					第8月：${giftCertificate.m8Amt} &nbsp;
					第9月：${giftCertificate.m9Amt} &nbsp;
					第10月：${giftCertificate.m10Amt} &nbsp;
					第11月：${giftCertificate.m11Amt} &nbsp;
					第12月：${giftCertificate.m12Amt} &nbsp;
				</td>
			</tr>
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="giftCertificate.orderNo" />
				</td>
				<td>
					<span><form:input path="orderNo" class="Field400" />
					</span>
				</td>
			</tr>			
		</c:if>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="giftCertificate.expireTime" />
			</td>
			<td>
				<span><form:input id="expireTime" path="expireTime" cssClass="form-inputbox" class="Field400" />
				</span>
				<cartmatic:ui_tip id="expireTimeDesc">
					<fmt:message key="giftCertificate.expireTime.tip" />
				</cartmatic:ui_tip>
				<c:if test="${empty giftCertificate.giftCertificateId}">
					<app:ui_datePicker outPut="expireTime" />
				</c:if>
			</td>
		</tr>
		<c:if test="${not empty giftCertificate.giftCertificateId}">
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="common.message.createTime" />
				</td>
				<td>
					<fmt:formatDate value="${giftCertificate.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
			<tr>
				<td class="FieldLabel">
					<StoreAdmin:label key="common.message.updateTime" />
				</td>
				<td>
					<fmt:formatDate value="${giftCertificate.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
			</tr>
		</c:if>
	</table>
</form:form>

<v:javascript formName="giftCertificate" staticJavascript="false" />
<script type="text/javascript" defer="true">
$j(document).ready(function () {
	applyValidate($("giftCertAmt"),"required,integer,minValue="+${appConfig.giftCertificateMinAmt}+",maxValue="+${appConfig.giftCertificateMaxAmt});
	
	if(${!empty giftCertificate.giftCertificateId}){
		$j('#giftCertificateForm :input').attr("readOnly","readOnly");
	}
});	

function fnDoSendEmail(){
	document.giftCertificateForm.action += "?doAction=sendEmail";
	document.giftCertificateForm.submit();
}

function fnDoSaveAndSendEmail(){
	var confirmMsg = __FMT.common_message_confirmSaveThis+"?";
    if (!confirmMsg || confirm(confirmMsg)) {
		document.giftCertificateForm.action += "?doAction=saveAndSendEmail";
		document.giftCertificateForm.submit();
	}
}
function fnDoForbit(){
	document.giftCertificateForm.action += "?doAction=forbit";
	document.giftCertificateForm.submit();
}
function fnDoActive(){
	document.giftCertificateForm.action += "?doAction=active";
	document.giftCertificateForm.submit();
}

function fnDoSave(){
	var confirmMsg = __FMT.common_message_confirmSaveThis+"?";
	if(validateAction()){
    	if (confirm(confirmMsg)) {
	    	document.giftCertificateForm.action += "?doAction=save";
			document.giftCertificateForm.submit();
	    }
    }
}

function checkExpireTime(){
	var today = new Date();
	today.setHours(0);
	today.setMinutes(0);
	today.setSeconds(0);
	today.setMilliseconds(0);
	var expireTime = fnPaserDate($("expireTime").value);
	expireTime.setHours(0);
	expireTime.setMinutes(0);
	expireTime.setSeconds(0);
	expireTime.setMilliseconds(0);
   	if (today.getTime() >= expireTime.getTime() ){
   		$j('#expireTime').focus();
   		alert('<fmt:message key="giftCertificate.validate_expireTimeGtNow"/>');
   		return false;
   	}else{
   		return true;
   	}
}
function validateAction(){
	if(!checkExpireTime()){
    	return false;
   	}
	if(!validateGiftCertificate($("giftCertificateForm"))){
    	return false;
   	}
    return true;
}
</script>