<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<!-- ========== 列表内容开始 ========= -->
<form action="${ctxPath}/catalog/product.html?doAction=search" target="_blank" method="post">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-list table-opip" id="productItem">
        <tr>
            <th width="5"><input type="checkbox" name="allbox" onclick="checkAll(this.form);fnChangeBtnStatus();" class="checkbox"  /></th>
            <th width="130">产品图片</th>
            <th>产品描述</th>
            <th width="5"></th>
            <th width="130">产品图片</th>
            <th>产品描述</th>
         </tr>
         <tr>
            <td colspan="6">
                 <c:forEach var="productItem" items="${productList}" varStatus="varStatus" step="2">
                     <div class="opip-wrap">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="5"><input type="checkbox" name="multiIds" value="${productItem.productId}" class="checkbox" title="${productItem.productName}" onchange="fnChangeBtnStatus();" /></td>
                            <td width="130">
                            	<cartmatic:img url="${productItem.defaultProductSku.image}" id="productImage" mediaType="product" size="c" width="130" height="130"></cartmatic:img>
                            </td>
                            <td><a href="javascript:void%20${productItem.productId}" onclick="fnOpenProductForm(${productItem.productId},'${productItem.productCode}');">${productItem.productCode}</a>
                            <br/>
                          	${productItem.productName}
                            <br/>
                            ${productItem.defaultProductSku.price}<c:if test="${not empty productItem.defaultProductSku.salePrice}">(${productItem.defaultProductSku.salePrice})</c:if>
                            <br/>
                            <product:productStatus value="${productItem.status}" /></td>
                          </tr>
                        </table>
                    </div>
                    <c:if test="${fn:length(productList)>(varStatus.index+1)}"></c:if>
                    <c:choose>
                    	<c:when test="${fn:length(productList)>(varStatus.index+1)}">
                    		<div class="opip-wrap">
		                    	<c:set var="productItem" value="${productList[varStatus.index+1]}"></c:set>
		                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		                          <tr>
		                            <td width="5"><input type="checkbox" name="multiIds" value="${productItem.productId}" class="checkbox" title="${productItem.productName}" onchange="fnChangeBtnStatus();" /></td>
		                            <td width="130">
		                            	<cartmatic:img url="${productItem.defaultProductSku.image}" id="productImage" mediaType="product" size="c" width="130" height="130"></cartmatic:img>
		                            </td>
		                            <td><a href="javascript:void%20${productItem.productId}" onclick="fnOpenProductForm(${productItem.productId},'${productItem.productCode}');">${productItem.productCode}</a>
		                            <br/>
		                            ${productItem.productName}
		                            <br/>
		                            ${productItem.defaultProductSku.price}<c:if test="${not empty productItem.defaultProductSku.salePrice}">(${productItem.defaultProductSku.salePrice})</c:if>
		                            <br/>
		                            <product:productStatus value="${productItem.status}" /></td>
		                          </tr>
		                        </table>
		                    </div>
                    	</c:when>
                    	<c:otherwise>
                    		<div class="opip-wrap">
		                    	&nbsp;
		                    </div>
                    	</c:otherwise>
                    </c:choose>
                    <div class="blank24"></div>
                </c:forEach>
            </td>
         </tr>
</table>
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