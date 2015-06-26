<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="productSkuInfo" style="display:none;min-height: 520px;">
	<c:choose>
		<c:when test="${product.productKind!=2}">
			<%@ include file="productSkuFormInfo.jsp"%>
		</c:when>
		<c:otherwise>
			<div id="productSkuList">
				<%@ include file="productSkuList.jsp"%>
			</div>
			<c:if test="${isOriented}">
				<div class="add-btn">
					<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(1)" commonBtnValueKey="productDetail.back"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(3)" commonBtnValueKey="productDetail.next"/>
				</div>
			</c:if>
			<fmt:message key="productDetail.sku.edit" var="titleDlg" />
			<fmt:message key="button.save" var="buttonSave" />
			<app:ui_dialog id="ProductSkuEditor" title="${titleDlg}" width="800" height="505" url="${ctxPath}/catalog/productSku/dialog.html?doAction=edit" buttons="'${buttonSave}':fnSaveProductSkuInfo" callback="fnInitProductSkuInfoForm"></app:ui_dialog>
		</c:otherwise>
	</c:choose>
</div>