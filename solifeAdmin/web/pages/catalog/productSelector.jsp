<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<c:if test="${param.multiSelect}">
已选择产品：<span id="selectedProduct_${param.id}"></span>
<div><a onclick="fnRemoveAll${param.id}();">移除所有已选择产品</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="fnConfirmSelectedProduct${param.id}();">确认</a></div>
</c:if>
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" style="font-size: 12px;">
	<tr>
		<td valign="top" width="25%">
			<div class="content" id="productSelectorSearch_${param.id}">
				<div class="title">
					<fmt:message key="product.productName" />
				</div>
				<input name="name" type="text" style="width: 200px" />
				<div class="title">
					<fmt:message key="product.productCode" />
				</div>
				<input name="productCode" type="text" style="width: 200px" />
				<div class="title">
					<fmt:message key="productSku.productSkuCode" />
				</div>
				<input name="skuCode" type="text" style="width: 200px" />
				<div class="title">
					<fmt:message key="catalog.catalogId" />
				</div>
				<div class="blank10"></div>
				<select id="catalogId" name="catalogId" style="width: 200px">
					<c:if test="${empty param.catalogId}"><option value=""></option></c:if>
					<c:forEach items="${catalogList}" var="catalog">
						<option value="${catalog.catalogId}" >
							${catalog.name}
						</option>
					</c:forEach> 
				</select>
				<div class="blank10"></div>
				<div class="title">
					<fmt:message key="product.brandId" />
				</div>
				<div class="blank10"></div>
				<select name="brandId" id="brandId" style="width: 200px">
					<option value=""></option>
					<c:forEach items="${brands}" var="brand">
						<option value="${brand.brandId}" <c:if test="${product.brand.brandId==brand.brandId}">selected="selected"</c:if>>
							${brand.brandName}
						</option>
					</c:forEach>
				</select>
				<div class="blank10"></div>
				<input type="button" id="SearchButton" name="SearchButton" onclick="fn${param.id}GetData();" value="<fmt:message key="button.search"/>" class="btn-search"/>
				<div class="blank10"></div>
				<fmt:message key="productSelector.select.clickNameOrCode" />
				<input type="hidden" name="productKind" id="productKind" value="${param.productKind}">
				<input type="hidden" name="multiSelect" id="multiSelect" value="${param.multiSelect}">
				<input type="hidden" name="id" value="${param.id}">
				<input type="hidden" name="virtual" value="${param.virtual}">
			</div>
		</td>
		<td valign="top">
			<div id="productSelectorDataList_${param.id}">
				<%--@ include file="include/productSelectorDataList.jsp"--%>
			</div>
		</td>
	</tr>
</table>
