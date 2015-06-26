<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<app:pageHeading entityName="${brand.brandName}" entityHeadingKey="brandDetail.heading" />
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'brandName');" />
<c:if test="${brand.brandId!=null}">
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this,'brandName');" />
</c:if>
<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="brand.*" />
<form id="brand" class="mainForm" action="${ctxPath}/catalog/brand.html" method="post" onsubmit="return validateBrand(this);">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="brandDetail.heading" /><input type="hidden" name="brandId" value="${brand.brandId}" /> 
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="brand.brandName" />
			</td>
			<td>
				<input class="Field400" type="text" name="brandName" id="brandName" value="<c:out value="${brand.brandName}"/>" />
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="brand.designer" />
			</td>
			<td>
				<input class="Field400" type="text" name="designer" id="designer" value="<c:out value="${brand.designer}"/>" />
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="brand.brandCode" />
			</td>
			<td>
				<input class="Field400" type="text" name="brandCode" id="brandCode" value="${brand.brandCode}" />
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="brand.website" />
			</td>
			<td>
				<input class="Field400" type="text" name="website" id="website" value="${brand.website}" />
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="brand.countryCode" />
			</td>
			<td>
				<input class="Field400" type="text" name="countryCode" id="countryCode" value="${brand.countryCode}" />
				<span><a href="http://en.wikipedia.org/wiki/ISO_3166-1" target="_blank">参考2位国家代码</a>，大写英文</span>
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="brand.story" />
			</td>
			<td>
				<textarea id="story" name="story" rows="4" cols="80" class="Field400">${brand.story}</textarea>
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="brand.sortOrder" />
			</td>
			<td>
				<input class="Field400" type="text" name="sortOrder" id="sortOrder" value="${empty brand.sortOrder ? 10 : brand.sortOrder}" /> 
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="brand.icon" />
			</td>
			<td>
				<div style="float: left;">
					<cartmatic:img url="${brand.icon}" mediaType="other" id="iconImage" height="100" width="100"></cartmatic:img>
				</div>
				<div style="float: left; margin: 20px;">
					<span id="iconImageBtnPlaceHolderId"></span>
					<input type="hidden" id="icon" name="icon" value="${brand.icon}" />
					<br/>
					(<fmt:message key="brand.icon.desc" />)
					<cartmatic:iconBtn icon="cross" extraCss="negative" text="清空图片" onclick="$('iconImage').src='${ctxPath}/images/default/00.jpg';$j('#icon').val('');" />
				</div>
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="brand.logo" />
			</td>
			<td>
				<div style="float: left;">
					<cartmatic:img url="${brand.logo}" mediaType="other" id="logoImage" height="100" width="100"></cartmatic:img>
				</div>
				<div style="float: left; margin: 20px;">
					<span id="logoImageBtnPlaceHolderId"></span>
					<input type="hidden" id="logo" name="logo" value="${brand.logo}" />
					<br/>
					(<fmt:message key="brand.logo.desc" />)
					<cartmatic:iconBtn icon="cross" extraCss="negative" text="清空图片" onclick="$('logoImage').src='${ctxPath}/images/default/00.jpg';$j('#logo').val('');" />
				</div>
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="brand.pic" />
			</td>
			<td>
				<div style="float: left;">
					<cartmatic:img url="${brand.pic}" mediaType="other" id="picImage" height="100" width="100"></cartmatic:img>
				</div>
				<div style="float: left; margin: 20px;">
					<span id="picImageBtnPlaceHolderId"></span>
					<input type="hidden" id="pic" name="pic" value="${brand.pic}" />
					<br/>
					(<fmt:message key="brand.pic.desc" />)
					<cartmatic:iconBtn icon="cross" extraCss="negative" text="清空图片" onclick="$('picImage').src='${ctxPath}/images/default/00.jpg';$j('#pic').val('');" />
				</div>
			</td>
	    </tr>
	    <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="brand.pic2" />
			</td>
			<td>
				<div style="float: left;">
					<cartmatic:img url="${brand.pic2}" mediaType="other" id="pic2Image" height="100" width="100"></cartmatic:img>
				</div>
				<div style="float: left; margin: 20px;">
					<span id="pic2ImageBtnPlaceHolderId"></span>
					<input type="hidden" id="pic2" name="pic2" value="${brand.pic2}" />
					<br/>
					(<fmt:message key="brand.pic2.desc" />)
					<cartmatic:iconBtn icon="cross" extraCss="negative" text="清空图片" onclick="$('pic2Image').src='${ctxPath}/images/default/00.jpg';$j('#pic2').val('');" />
				</div>
			</td>
	    </tr>
	</table>
</form>
<cartmatic:swf_upload btnPlaceHolderId="iconImageBtnPlaceHolderId" uploadCategory="other" uploadFileTypes="*.jpg" fileInputId="icon" previewImg="iconImage" ></cartmatic:swf_upload>
<cartmatic:swf_upload btnPlaceHolderId="logoImageBtnPlaceHolderId" uploadCategory="other" uploadFileTypes="*.jpg" fileInputId="logo" previewImg="logoImage" ></cartmatic:swf_upload>
<cartmatic:swf_upload btnPlaceHolderId="picImageBtnPlaceHolderId" uploadCategory="other" uploadFileTypes="*.jpg" fileInputId="pic" previewImg="picImage" ></cartmatic:swf_upload>
<cartmatic:swf_upload btnPlaceHolderId="pic2ImageBtnPlaceHolderId" uploadCategory="other" uploadFileTypes="*.jpg" fileInputId="pic2" previewImg="pic2Image" ></cartmatic:swf_upload>
<v:javascript formName="brand" staticJavascript="false" />
<script type="text/javascript">
    document.forms["brand"].elements["brandName"].focus();
</script>