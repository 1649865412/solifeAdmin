<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${companyInfo.companyInfoName}" entityHeadingKey="companyInfoDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this);" />
	<%--<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />--%>
</content>
<app:showBindErrors bindPath="companyInfo.*" />
	<form:form method="post" cssClass="mainForm" id="companyInfo" commandName="companyInfo"
			action="${ctxPath}/system/companyInfo.html" onsubmit="return validateCompanyInfo(this);">
		<input type="hidden" name="companyInfoId" value="${companyInfo.companyInfoId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="companyName" />
 		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="companyInfo.logo" />
			</td>
			<td>
				<spring:bind path="companyInfo.logo">
					<div style="float:left;">
						<cartmatic:img url="${status.value}" mediaType="other" id="logoView" height="100" width="100"></cartmatic:img>
					</div>
					<div style="float:left; margin:20px;">
					<span id="logo_btnPlaceHolderId"></span>
					<input type="hidden" name="${status.expression}" id="${status.expression}" value="${status.value}">
					</div>
					<span class="fieldError">${status.errorMessage}</span>
				</spring:bind>
			</td>
		</tr>
 		<app:input property="country" />
 		<app:input property="state" />
 		<app:input property="city" />
 		<app:input property="address" />
 		<app:input property="zip" />
 		<app:input property="phone" />
 		<app:input property="fax" />
 		<app:input property="email" />
 		<app:input property="qq" />
 		<app:input property="msn" />
 		<app:input property="telephoneHotLine" />
  	</table>
</form:form>
<v:javascript formName="companyInfo" staticJavascript="false" />
<cartmatic:swf_upload btnPlaceHolderId="logo_btnPlaceHolderId" uploadFileTypes="*.jpg;*.gif" previewImg="logoView" fileInputId="logo" uploadCategory="other"></cartmatic:swf_upload>
<script type="text/javascript">
    document.forms["companyInfo"].elements["companyName"].focus();
</script>
