<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th colspan="3">
			<fmt:message key="product.sku.tab.basicInfo" />
		</th>
	</tr>
	<%--==================状态=================--%>
	<tr>
		<td>
			<input type="checkbox" id="productSkuStatusCheck" name="productSkuStatusCheck" value="1" />
		</td>
		<td>
			<fmt:message key="productSku.status" />
		</td>
		<td>
			<select id="productSkuStatus" name="productSkuStatus">
				<option value="1">
					<fmt:message key="status.active" />
				</option>
				<option value="2">
					<fmt:message key="status.inactive" />
				</option>
			</select>
		</td>
	</tr>
	<%--==================图片=================--%>
	<tr>
		<td>
			<input type="checkbox" id="imageCheck" name="imageCheck" value="1" />
		</td>
		<td>
			<fmt:message key="productSku.image" />
		</td>
		<td>
			<div style="float: left;">
				<cartmatic:img url="" id="productImage" mediaType="product" size="b" width="80" height="80"></cartmatic:img>
			</div>
			<div style="float: left; margin: 20px;">
				<span id="image_imgBtnPlaceHolderId"></span>
				<input type="hidden" name="image" id="image"value="" />
				<br />
				(<fmt:message key="productSku.image.desc" />)
			</div>
		</td>
	</tr>
	<cartmatic:swf_upload btnPlaceHolderId="image_imgBtnPlaceHolderId" uploadFileTypes="*.jpg" uploadCategory="product" fileInputId="image" previewImg="productImage" previewSize="b"></cartmatic:swf_upload>
</table>