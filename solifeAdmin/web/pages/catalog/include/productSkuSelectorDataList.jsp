<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<!-- ========== 列表内容开始 ========= -->
<display:table name="${productSkuList}" cellspacing="0" cellpadding="0" uid="productSkuItem" class="table-list" export="false" requestURI="">
	<c:if test="${param.multiSelect}">
		<display:column style="width: 1%"  media="html">
			<input type="checkbox" name="multiIds" id="sel_ch_${param.id}_${productSkuItem.productSkuId}" value="${productSkuItem.productSkuId}" class="checkbox" title="${productSkuItem.productSkuCode}" onclick='fuSelectProductSku${param.id}(${productSkuItem.id},this);'/>
		</display:column>
	</c:if>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.productSkuCode">
		<product:productKindImg product="${productSkuItem.product}"></product:productKindImg>
		<a href="javascript:void%200" ondblclick='fuSelectProductSku${param.id}(${productSkuItem.id})' title="${productSkuItem.productSkuCode}}">${productSkuItem.productSkuCode}</a>
	</display:column>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.productName">
		<a href="javascript:void%200" ondblclick='fuSelectProductSku${param.id}(${productSkuItem.id})' title="${productSkuItem.product.productName}"><cartmatic:interceptString length="25" str="${productSkuItem.product.productName}" /></a>
	</display:column>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.brandId">
  		<cartmatic:interceptString length="20" str="${productSkuItem.product.brand.brandName}" />
  	</display:column>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="product.mainCategory">
  		<cartmatic:interceptString length="20" str="${productSkuItem.product.mainCategory.categoryName}" />
  	</display:column>
	<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.price">
  		${productSkuItem.price}<c:if test="${not empty productSkuItem.salePrice}">(${productSkuItem.salePrice})</c:if>
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
	<c:forEach items="${productSkuList}" var="productSkuItem">
	<span id="jsonDataList_${param.id}_${productSkuItem.id}">${productSkuItem.jsonObject}</span>
	</c:forEach>
</div>
<!-- ========== 列表内容结束 ========= -->
<div class="blank10"></div>
<c:if test="${not empty productSkuList}">
	<!-- ========== 分页开始 ========= -->
	<div class="box-paging">
		<%@include file="/common/pagingOnlyNew.jsp"%>
	</div>
	<!-- ========== 分页结束 ========= -->
</c:if>