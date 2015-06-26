<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<!-- ========== 列表内容开始 ========= -->
<form action="${ctxPath}/catalog/product.html?doAction=search" target="_blank" method="post">
	<c:set var="editURLPath" value="/catalog/product.html?doAction=edit" scope="page" />
	<c:set var="checkAll">
		<input type="checkbox" name="allbox" onclick="checkAll(this.form);fnChangeBtnStatus();" class="checkbox"  />
	</c:set>
	<display:table name="${productList}" cellspacing="0" cellpadding="0" uid="productItem" class="table-list" style="table-layout:fixed;" export="false" requestURI="">
		<display:column headerClass="w30" class="w30" title="${checkAll}" media="html">
			<input type="checkbox" name="multiIds" value="${productItem.productId}" class="checkbox" title="${productItem.productName}" onchange="fnChangeBtnStatus();" />
		</display:column>
		<display:column headerClass="w30" class="product-kind w30" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" media="html">
			<product:productKindImg product="${productItem}"></product:productKindImg>
		</display:column>
		<display:column sortable="false" headerClass="w100" class="w100" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.productCode">
			<a href="javascript:void%20${productItem.productId}" onclick="fnOpenProductForm(${productItem.productId},'${productItem.productCode}');">${productItem.productCode}</a>
		</display:column>
		<display:column sortable="false" headerClass="wauto" class="wauto" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.productName">
			<a href="javascript:void%20${productItem.productId}" onclick="fnOpenProductForm(${productItem.productId},'${productItem.productCode}');">${productItem.productName}</a>
		</display:column>
		<display:column sortable="false" headerClass="w150" class="w150" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.mainCategory">
      		${productItem.mainCategory.categoryName}
      	</display:column>
		<display:column sortable="false" headerClass="w60" class="w60" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.price">
      		${productItem.defaultProductSku.price}<c:if test="${not empty productItem.defaultProductSku.salePrice}">(${productItem.defaultProductSku.salePrice})</c:if>
		</display:column>
		<display:column  sortable="false" headerClass="w100" class="w100" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.updateBy">
      		<c:if test="${not empty productItem.updateBy}"><app:userName userId="${productItem.updateBy}" noFormat="true"></app:userName></c:if>
      	</display:column>
      	<display:column sortable="false" headerClass="w100" class="w100" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.updateTime">
      		<fmt:formatDate value="${productItem.updateTime}" pattern="yyyy-MM-dd"/>
      	</display:column>
		<display:column sortable="false" headerClass="w60" class="w60" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.status">
			<product:productStatus value="${productItem.status}" />
		</display:column>
	</display:table>
<!-- ========== 列表内容结束 ========= -->
<div class="blank10"></div>
<c:if test="${not empty productList}">
	<!-- ========== 分页开始 ========= -->
	<%@include file="/common/pagingNew.jsp"%>
	<!-- ========== 分页结束 ========= -->
</c:if>
<%--搜索条件--%>
<input type="hidden" name="name" value="${productSearchCriteria.name}" />
<input type="hidden" name="categoryId" value="${productSearchCriteria.categoryId}" />
<input type="hidden" name="productCode" value="${productSearchCriteria.productCode}" />
<input type="hidden" name="skuCode" value="${productSearchCriteria.skuCode}" />
<input type="hidden" name="brandId" value="${productSearchCriteria.brandId}" />
<input type="hidden" name="supplierId" value="${productSearchCriteria.supplierId}" />
<input type="hidden" name="productTypeId" value="${productSearchCriteria.productTypeId}" />
<input type="hidden" name="productStatus" value="${productSearchCriteria.productStatus}" />
<input type="hidden" name="productKind" value="${productSearchCriteria.productKind}" />
<input type="hidden" name="availabilityRule" value="${productSearchCriteria.availabilityRule}" />
<input type="hidden" id="btnSearch" name="btnSearch" value="Search" />
</form>
<script type="text/javascript" defer>
highlightTableRows("productItem");
fnChangeBtnStatus();
</script>