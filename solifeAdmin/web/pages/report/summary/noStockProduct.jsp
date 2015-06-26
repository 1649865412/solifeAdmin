<%@ include file="/common/taglibs.jsp"%>
<div class="box-content">
	<div class="top">
		<div class="content-title">
			<fmt:message key="report.nostockproduct" />
		</div>
	</div>
	<div class="content">
<display:table name="${noStockProduct}" cellspacing="0" cellpadding="0" uid="items" class="table-list"
	export="false" requestURI="" length="10">
	<display:column 
		style="width: 25%" titleKey="product.productCode" headerClass="data-table-title" media="html">
	<a href="<c:url value="/catalog/editProduct.html?from=list&productId=${items[0]}"/>">		
	    &nbsp;${items[1]}
	</a>
	</display:column>
	<display:column style="width: 25%" titleKey="product.productName" headerClass="data-table-title"
		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
		&nbsp;${items[2]}
	</display:column>
	<display:column style="width: 20%" titleKey="report.stockqty" headerClass="data-table-title" media="html">
	    &nbsp;${items[3]}
	</display:column>
	<display:column style="width: 30%" titleKey="report.stockminQuantity" headerClass="data-table-title" media="html">
	    &nbsp;${items[4]}
	    <c:if test="${empty items[4]}">&nbsp</c:if>
	</display:column>
</display:table>
</div>
</div>
