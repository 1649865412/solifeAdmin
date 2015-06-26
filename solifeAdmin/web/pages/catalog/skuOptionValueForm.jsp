<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<form id="skuOptionValueForm">
	<table class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<tr>
			<td>
				<input type="hidden" name="version" value="${skuOptionValue.version}" />
				<input type="hidden" name="skuOptionValueId" value="${skuOptionValue.skuOptionValueId}" />
				<StoreAdmin:label key="skuOptionValue.skuOptionValueName" />
			</td>
			<td style="width: 70%">
				<input type="text" name="skuOptionValueName" id="skuOptionValueName" value="${skuOptionValue.skuOptionValueName}" />
			</td>
		</tr>
		<tr>
			<td>
				<StoreAdmin:label key="skuOptionValue.skuOptionValueType" />
			</td>
			<td>
				<input type="hidden" name="skuOptionValueType" id="skuOptionValueType_temp"/>
				<input type="radio" onclick="fnSelectValueTypeHandler(this.value)" name="skuOptionValueType" value="1" <c:if test="${empty skuOptionValue.skuOptionValueType||skuOptionValue.skuOptionValueType==1}">checked="checked"</c:if>>
				&nbsp;
				<fmt:message key="skuOptionValue.skuOptionValueType_1" />
				<input type="radio" onclick="fnSelectValueTypeHandler(this.value)" name="skuOptionValueType" value="2"  <c:if test="${skuOptionValue.skuOptionValueType==2}">checked="checked"</c:if>>
				&nbsp;
				<fmt:message key="skuOptionValue.skuOptionValueType_2" />
				<input type="radio" onclick="fnSelectValueTypeHandler(this.value)" name="skuOptionValueType" value="3"  <c:if test="${skuOptionValue.skuOptionValueType==3}">checked="checked"</c:if>>
				&nbsp;
				<fmt:message key="skuOptionValue.skuOptionValueType_3" />
			</td>
		</tr>
		<tr>
			<td>
				<StoreAdmin:label key="skuOptionValue.skuOptionValue" />
			</td>
			<td>
				<input type="text" name="skuOptionValue" id="skuOptionValue" value="${skuOptionValue.skuOptionValue}" />
			</td>
		</tr>
		<tr id="skuOptionValue_color" <c:if test="${skuOptionValue.skuOptionValueType!=2}">style="display:none"</c:if>>
			<td align="left">
				&nbsp;
			</td>
			<td align="left">
				<span id="colorpanel"></span>
			</td>
		</tr>
		<tr id="skuOptionValue_image" <c:if test="${skuOptionValue.skuOptionValueType!=3}">style="display:none"</c:if>>
			<td align="left">
				&nbsp;
			</td>
			<td align="left">
				<div style="float: left;">
					<cartmatic:img id="skuOptionValue_img" url="${skuOptionValue.skuOptionValueType==3 ? skuOptionValue.skuOptionValue : ''}" mediaType="skuOptionValue" width="40" height="40"></cartmatic:img>
				</div>
				<div style="float: left; margin: 20px;">
					<span id="skuOptionValue_imgBtnPlaceHolderId"></span>
					<input type="hidden" id="skuOptionValue_img_input">
				<br/>
				(<fmt:message key="skuOptionValue.skuOptionValueType.iamge.desc" />)
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<StoreAdmin:label key="skuOptionValue.sortOrder" />
			</td>
			<td>
				<input type="text" name="sortOrder" id="sortOrder" value="${skuOptionValue.sortOrder}" />
			</td>
		</tr>
		<tr>
			<td>
				<StoreAdmin:label key="skuOptionValue.status" />
			</td>
			<td>
				<select id="status" name="status">
					<option value="1" <c:if test="${skuOptionValue.status==1}">selected="selected"</c:if>>
						<fmt:message key="status.active" />
					</option>
					<option value="2" <c:if test="${skuOptionValue.status==2}">selected="selected"</c:if>>
						<fmt:message key="status.inactive" />
					</option>
				</select>
			</td>
		</tr>
	</table>
</form>
<v:javascript formName="skuOptionValue" staticJavascript="false" />
<cartmatic:swf_upload btnPlaceHolderId="skuOptionValue_imgBtnPlaceHolderId" fileInputId="skuOptionValue" uploadFileTypes="*.jpg" uploadCategory="skuOptionValue" previewImg="skuOptionValue_img" onComplete="fnUploadSkuOptionValueImagehandler"></cartmatic:swf_upload>