<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="packageItemInfo" style="display:none;min-height: 520px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<td colspan="4" class="title">
				<fmt:message key="productPackageItemList.heading" />
				<c:if test="${product.status==0}">
					<cartmatic:cartmaticBtn inputName="addPackageItem" btnType="common" commonBtnValueKey="productPackageItemList.add.product"/>
				</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" id="productPackageItemForm">
					<tr>
						<td width="20%" class="subtitle">
							<fmt:message key="productSku.productSkuCode" />
						</td>
						<td width="20%" class="subtitle">
							<fmt:message key="product.productName" />
						</td>
						<td width="20%" class="subtitle">
							<fmt:message key="product.brandId" />
						</td>
						<td width="20%" class="subtitle">
							<fmt:message key="productSku.price" />
						</td>
						<td width="20%" class="subtitle">
							<fmt:message key="productPackageItem.quantity" />
						</td>
						<c:if test="${product.status==0}">
							<td class="subtitle">
								&nbsp;
							</td>
						</c:if>
					</tr>
					<c:forEach items="${productSku.productPackageItems}" var="productPackageItem">
						<tr>
							<td>
								${productPackageItem.itemSku.productSkuCode}
							</td>
							<td>
								${productPackageItem.itemSku.product.productName}
							</td>
							<td>
								${productPackageItem.itemSku.product.brand.brandName}
							</td>
							<td>
								${productPackageItem.itemSku.price}
							</td>
							<td>
								<c:choose>
									<c:when test="${product.status==0}">
										<input name="productPackage_quantity" type="text" value="${productPackageItem.quantity}"/>
										<input type="hidden" name="productPackage_productPackageItemId" value="${productPackageItem.itemSku.productSkuId}"/>
									</c:when>
									<c:otherwise>
										${productPackageItem.quantity}
									</c:otherwise>
								</c:choose>
							</td>
							<c:if test="${product.status==0}">
								<td>
									<cartmatic:cartmaticBtn btnType="common" onclick="$j(this).parent().parent().remove();" commonBtnValueKey="button.small.delete"/>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
	<c:if test="${isOriented}">
		<div class="add-btn">
			<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(2)" commonBtnValueKey="productDetail.back"/>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<cartmatic:cartmaticBtn btnType="common" onclick="fnTabOriented(4,3)" commonBtnValueKey="productDetail.next"/>
		</div>
	</c:if>
	<product:productSkuSelector id="productSkuSelector" showSelectorBtnId="addPackageItem" ondblclick="fnAddProductPackageItem" autoClose="false" showProductKinds="1,2"></product:productSkuSelector>
</div>