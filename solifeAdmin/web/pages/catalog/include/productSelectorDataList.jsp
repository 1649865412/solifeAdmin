<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<!-- ========== 列表内容开始 ========= -->
<display:table name="${productList}" cellspacing="0" cellpadding="0" uid="productItem" class="table-list" export="false" requestURI="">
	<c:if test="${param.multiSelect}">
	<display:column style="width: 1%"  media="html">
		<input type="checkbox" name="multiIds" id="sel_ch_${param.id}_${productItem.productId}" value="${productItem.productId}" class="checkbox" title="${productItem.productCode}" onclick="fuSelectProduct${param.id}(${productItem.id},this)"/>
	</display:column>
	</c:if>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.productCode">
		<product:productKindImg product="${productItem}"></product:productKindImg>
		<a href="javascript:void%20${productItem.id}" ondblclick='fuSelectProduct${param.id}(${productItem.id})' title="${productItem.productCode}">${productItem.productCode}</a>
	</display:column>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.productName">
		<a href="javascript:void%20${productItem.id}" ondblclick='fuSelectProduct${param.id}(${productItem.id})' title="${productItem.productName}"><cartmatic:interceptString length="25" str="${productItem.productName}" /></a>
	</display:column>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.brandId">
      	<cartmatic:interceptString length="18" str="${productItem.brand.brandName}" />
    </display:column>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.mainCategory">
      	<cartmatic:interceptString length="20" str="${productItem.mainCategory.categoryName}" />
    </display:column>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.price">
      	${productItem.defaultProductSku.price}<c:if test="${not empty productItem.defaultProductSku.salePrice}">(${productItem.defaultProductSku.salePrice})</c:if>
	</display:column>
</display:table>
<input type="hidden" name="name" value="${productSearchCriteria.name}" />
<input type="hidden" name="productCode" value="${productSearchCriteria.productCode}" />
<input type="hidden" name="skuCode" value="${productSearchCriteria.skuCode}" />
<input type="hidden" name="categoryId" value="${productSearchCriteria.categoryId}" />
<input type="hidden" name="catalogId" value="${productSearchCriteria.catalogId}" />
<input type="hidden" name="brandId" value="${productSearchCriteria.brandId}" />
<input type="hidden" name="productKind" value="${productSearchCriteria.productKind}">
<input type="hidden" name="multiSelect" value="${param.multiSelect}">
<input type="hidden" name="id" value="${param.id}">
<input type="hidden" name="virtual" value="${param.virtual}">
<div style="display:none">
	<c:forEach items="${productList}" var="productItem">
	<span id="jsonDataList_${param.id}_${productItem.id}">${productItem.jsonObject}</span>
	</c:forEach>
</div>
<!-- ========== 列表内容结束 ========= -->
<div class="blank10"></div>
<c:if test="${not empty productList}">
	<!-- ========== 分页开始 ========= -->
	<div class="box-paging">
		<%@include file="/common/pagingOnlyNew.jsp"%>
	</div>
	<!-- ========== 分页结束 ========= -->
</c:if>