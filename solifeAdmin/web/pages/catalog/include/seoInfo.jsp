<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<div id="seoInfo" style="display:none">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="productDetail.optional.attrubute" />
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.title" />
			</td>
			<td>
				<input name="title" id="title" type="text" class="Field100pct" value="<c:out value="${product.title}"/>" />
			</td>
        </tr>
        <tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.url" />
			</td>
			<td>
				<input name="url" id="url" type="text" class="Field100pct" value="${product.url}" />
			</td>
        </tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.metaKeyword" />
			</td>
			<td>
				<input name="metaKeyword" id="metaKeyword" type="text" class="Field100pct" value="<c:out value="${product.metaKeyword}"/>" />
			</td>
        </tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="product.metaDescription" />
			</td>
			<td>
				<input name="metaDescription" id="metaDescription" type="text" class="Field100pct"  value="<c:out value="${product.metaDescription}"/>" />
			</td>
        </tr>
	</table>
	<c:if test="${isOriented}">
		<div class="add-btn">
			<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(5)" commonBtnValueKey="productDetail.back"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(7,6)" commonBtnValueKey="productDetail.next"/>
		</div>
	</c:if>
</div>