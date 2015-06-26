<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${wrap.wrapName}" entityHeadingKey="wrapDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'wrapName');" />
    <c:if test="${wrap.wrapId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this,'wrapName');" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="wrap.*" />
	<form:form method="post" cssClass="mainForm" id="wrap" commandName="wrap"
			action="${ctxPath}/system/wrap.html" onsubmit="return validateWrap(this);">
		<form:hidden path="version" />
		<input type="hidden" name="wrapId" value="${wrap.wrapId}"/> 
		<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<app:input property="defaultPrice" />
 		<app:input property="wrapName" />
 		<app:input property="description" />
 		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="wrap.imageSrc" />
			</td>
			<td>
				<spring:bind path="wrap.imageSrc">
	        		<div style="float:left;">
						<cartmatic:img url="${status.value}" mediaType="other" id="image" height="100" width="100"></cartmatic:img>
					</div>
					<div style="float:left; margin:20px;">
						<span id="imageSrc_btnPlaceHolderId"></span>
						<input type="hidden" name="${status.expression}" id="${status.expression}" value="${status.value}">
						<br/>
						(<fmt:message key="wrap.imageSrc.desc" />)
						<cartmatic:iconBtn icon="cross" extraCss="negative" text="清空图片" onclick="$('image').src='${ctxPath}/images/default/00.jpg';$j('#imageSrc').val('');" />
					</div>
					<span class="fieldError">${status.errorMessage}</span>
	            </spring:bind>
			</td>
	    </tr>
  	</table>
</form:form>
<v:javascript formName="wrap" staticJavascript="false" />
<cartmatic:swf_upload btnPlaceHolderId="imageSrc_btnPlaceHolderId" uploadFileTypes="*.jpg;*.gif" previewImg="image" fileInputId="imageSrc" uploadCategory="other"></cartmatic:swf_upload>
<script type="text/javascript">
    document.forms["wrap"].elements["defaultPrice"].focus();
</script>