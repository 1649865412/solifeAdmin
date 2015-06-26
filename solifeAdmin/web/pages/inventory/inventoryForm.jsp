<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<content tag="buttons">
<c:choose>
	<c:when test="${action==-4}">
		<fmt:message key="inventoryList.sku.product.not.in.stock.sell" />
	</c:when>
	<c:when test="${action==-3}">
		<fmt:message key="inventoryList.sku.product.no.inventory" />
	</c:when>
	<c:when test="${action==-2}">
		<fmt:message key="inventoryList.skuCode.required" />
	</c:when>
	<c:when test="${action==-1}">
		<fmt:message key="inventoryList.invalid.skuCode" />
	</c:when>
	<c:when test="${action==0}">
		<fmt:message key="inventoryList.sku.product.inActive" />
	</c:when>
</c:choose>
</content>
<app:showBindErrors bindPath="inventoryModel.*" />
<c:if test="${empty param.productId}">
<app:ui_leftMenu>
	<div class="sidebar-left">
		<form method="post" action="${ctxPath}/inventory/inventory.html">
			<app:ui_tabs tabsId="left_menu_tabs" />
			<div class="tab" id="left_menu_tabs">
				<ul>
					<li>
						<a href="#listSelectContent"><fmt:message key="search.sku.inventory" /> </a>
					</li>
				</ul>
				<div class="content" id="listSelectContent">
					<div class="title">
						<fmt:message key="productSku.productSkuCode" />
					</div>
					<input id="productSkuCode" name="productSkuCode" type="text" style="width: 200px" value="${not empty inventory.productSku.productSkuCode ? inventory.productSku.productSkuCode : param.productSkuCode}" />
					<div class="blank10"></div>
					<input type="submit" id="SearchButton" name="SearchButton" value="<fmt:message key="button.search"/>" class="btn-search"/>
					<input type="RESET" id="SearchReset" name="SearchReset" value="<fmt:message key="button.clear"/>" class="btn-search"/>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
</c:if>
<c:if test="${not empty inventory}">

				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
					<tr>
						<th colspan="2">
							<fmt:message key="inventoryDetail.basic.info" />
							<c:if test="${not empty param.productId}">
								<authz:authorize url="/catalog/">
									&nbsp;&nbsp;&nbsp;&nbsp;<a href="${ctxPath}/catalog/product/window.html?doAction=edit&productId=${param.productId}">返回编辑产品</a>
								</authz:authorize>
							</c:if> 
						</th>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="productSku.productSkuCode" ignoreValidation="true"/>
						</td>
						<td>
							${inventory.productSku.productSkuCode}
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="cRed">
							<c:choose>
								<c:when test="${inventory.availableQuantity - inventory.preOrBackOrderedQty < 0}">
									<fmt:message key="inventory.status.lackStock"/>
								</c:when>
								<c:when test="${inventory.availableQuantity - inventory.preOrBackOrderedQty < inventory.reorderMinimnm}">
									<fmt:message key="inventory.status.lowStock"/>
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
							</span>
						</td>
		             </tr>
		             <tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="product.availabilityRule" ignoreValidation="true"/>
						</td>
						<td>
							<fmt:message key="product.availabilityRule_${inventory.productSku.product.availabilityRule}" />
						</td>
		             </tr>
		             <tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventory.quantityOnHand" ignoreValidation="true"/>
						</td>
						<td>
							${inventory.quantityOnHand}
							&nbsp;&nbsp;&nbsp;&nbsp;
							(<fmt:message key="inventory.quantityOnHand.desc" />)
						</td>
		             </tr>
		             <tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventory.reservedQuantity" ignoreValidation="true"/>
						</td>
						<td>
							${inventory.reservedQuantity}
							&nbsp;&nbsp;&nbsp;&nbsp;
							(<fmt:message key="inventory.reservedQuantity.desc" />)
						</td>
		             </tr>
		             <tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventory.allocatedQuantity" ignoreValidation="true"/>
						</td>
						<td>
							${inventory.allocatedQuantity}
							&nbsp;&nbsp;&nbsp;&nbsp;
							(<fmt:message key="inventory.allocatedQuantity.desc" />)
						</td>
		             </tr>
		             <tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventory.availableQuantity" ignoreValidation="true"/>
						</td>
						<td>
							${inventory.availableQuantity}
							&nbsp;&nbsp;&nbsp;&nbsp;
							(<fmt:message key="inventory.availableQuantity.desc" />)
						</td>
		             </tr>
		             <c:if test="${inventory.productSku.product.availabilityRule==2||inventory.productSku.product.availabilityRule==3}">
		             <tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventory.preOrBackOrderedQty" ignoreValidation="true"/>
						</td>
						<td>
							${inventory.preOrBackOrderedQty}
							&nbsp;&nbsp;&nbsp;&nbsp;
							(<fmt:message key="inventory.preOrBackOrderedQty.desc" />)
						</td>
		             </tr>
		             <tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="product.preOrBackOrderLimit" ignoreValidation="true"/>
						</td>
						<td>
							${inventory.productSku.product.preOrBackOrderLimit}
						</td>
		             </tr>
		             </c:if>
				</table>
				<form id="inventoryModelForm" class="mainForm" method="post" action="${ctxPath}/inventory/inventory.html" onsubmit="return validateInventoryModel(this);">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
						<tr>
							<th colspan="2">
								<fmt:message key="inventoryDetail.settings.info" />
								<input type="hidden" name="doAction" value="save" />
								<input type="hidden" name="productId" value="${param.productId}" />
								<input type="hidden" name="inventoryId" value="${inventory.inventoryId}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</th>
						</tr>
						<tr>
							<td colspan="2">
								<cartmatic:iconBtn icon="cog" textKey="inventoryModel.save.settings" onclick="return fnDoSave(this);" />
							</td>
						</tr>
						<tr>
							<td class="FieldLabel">
								<StoreAdmin:label key="inventory.reservedQuantity" />
							</td>
							<td>
								<spring:bind path="inventoryModel.reservedQuantity">
									<input class="Field400" id="${status.expression}" name="${status.expression}" type="text" value="${status.value}" />
									<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
								</spring:bind>
							</td>
			             </tr>
			             <tr>
							<td class="FieldLabel">
								<StoreAdmin:label key="inventory.reorderMinimnm" />
							</td>
							<td>
								<spring:bind path="inventoryModel.reorderMinimnm">
									<input class="Field400" id="${status.expression}" name="${status.expression}" type="text" value="${status.value}" />
									<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
								</spring:bind>
							</td>
			             </tr>
			             <tr>
							<td class="FieldLabel">
								<StoreAdmin:label key="inventory.reorderQuantity" />
							</td>
							<td>
								<spring:bind path="inventoryModel.reorderQuantity">
									<input class="Field400" id="${status.expression}" name="${status.expression}" type="text" value="${status.value}" />
									<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
								</spring:bind>
							</td>
			             </tr>
			             <c:if test="${inventory.productSku.product.availabilityRule==3}">
			             <tr>
							<td class="FieldLabel">
								<StoreAdmin:label key="inventory.expectedRestockDate" />
							</td>
							<td>
								<spring:bind path="inventoryModel.expectedRestockDate">
									<input class="Field100" id="${status.expression}" name="${status.expression}" type="text" value="<fmt:formatDate value="${inventory.expectedRestockDate}" pattern="yyyy-MM-dd" />" />
									<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
								</spring:bind>
							</td>
			             </tr>
			             </c:if>
					</table>
				</form>
				<form id="inventoryModelForm2" method="post" action="${ctxPath}/inventory/inventory.html"  onsubmit="return validateForm(this)&&fnValidateAdjustmentQuantity($j('#adjustmentType').val());">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
					<tr>
						<th colspan="2">
							<fmt:message key="inventoryDetail.adjust.quantityOnHand" />
							<input type="hidden" name="doAction" value="adjustStockQuantity" />
							<input type="hidden" name="productId" value="${param.productId}" />
							<input type="hidden" name="inventoryId" value="${inventory.inventoryId}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</th>
					</tr>
					<tr>
						<td colspan="2">
							<cartmatic:iconBtn icon="cog" textKey="inventoryModel.confirm.adjustmentQty" onclick="$j(this).closest('form').submit();" />
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventoryModel.adjustmentType" />
						</td>
						<td>
							<spring:bind path="inventoryModel.adjustmentType">
								<select class="Field400" name="${status.expression}" id="${status.expression}" onchange="fnInitValidate(this.value);">
									<option value="">
										<fmt:message key="inventoryModel.adjustmentType.select" />
									</option>
									<option value="1" <c:if test="${status.value==1}">selected="selected"</c:if>>
										<fmt:message key="inventoryModel.adjustmentType_1" />
									</option>
									<c:if test="${inventory.availableQuantity>0}">
									<option value="2" <c:if test="${status.value==2}">selected="selected"</c:if>>
										<fmt:message key="inventoryModel.adjustmentType_2" />
									</option>
									</c:if>
								</select>
								<c:set var="adjustmentType" value="${status.value}"/>
								<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
							</spring:bind>
						</td>
		             </tr>
		             <tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventoryModel.adjustmentQuantity" />
						</td>
						<td>
							<spring:bind path="inventoryModel.adjustmentQuantity">
								<input class="Field400" id="${status.expression}" name="${status.expression}" type="text" value="${status.value}" disabled="disabled" />
								<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
							</spring:bind>
						</td>
		             </tr>
		             <tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventoryModel.adjustmentReason" />
						</td>
						<td>
							<spring:bind path="inventoryModel.adjustmentReason">
								<select class="Field400" id="${status.expression}_1" disabled="disabled" <c:if test="${empty adjustmentType||adjustmentType==1}">style="display: none"</c:if> onchange="$j('#adjustmentReason').val(this.value);">
									<c:forEach begin="1" end="3" var="reason">
										<fmt:message key="inventoryAudit.adjustmentType_1.reason_${reason}" var="reasonMsg"/>
										<option value="${reasonMsg}" <c:if test="${status.value==reasonMsg}">selected="selected"</c:if>>
											${reasonMsg}
										</option>
									</c:forEach>
								</select>
								<select class="Field400" id="${status.expression}_2" disabled="disabled" <c:if test="${adjustmentType==2}">style="display: none"</c:if> onchange="$j('#adjustmentReason').val(this.value);">
									<c:forEach begin="1" end="4" var="reason">
										<fmt:message key="inventoryAudit.adjustmentType_2.reason_${reason}" var="reasonMsg"/>
										<option value="${reasonMsg}" <c:if test="${status.value==reasonMsg}">selected="selected"</c:if>>
											${reasonMsg}
										</option>
									</c:forEach>
								</select>
								<input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}"/>
								<span class="fieldError"><c:out value="${status.errorMessage}" /></span>
							</spring:bind>
						</td>
		             </tr>
		             <tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="inventoryModel.adjustmentComment" />
						</td>
						<td>
							<spring:bind path="inventoryModel.adjustmentComment">
								<textarea class="Field600" id="${status.expression}" name="${status.expression}" >${status.value}</textarea>
								<span class="fieldError"><c:out value="${status.errorMessage}" /> </span>
							</spring:bind>
						</td>
		             </tr>
            	 </table>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
					<tr>
						<th>
							<fmt:message key="inventory.latest.inventoryAudit" />
						</th>
					</tr>
					<tr>
						<td>
							<%@include file="include/inventoryAuditListContent.jsp"%>
						</td>
					</tr>
					<c:if test="${sc.totalPageCount>1&&(empty param.productId)}">
					<tr>
						<td>
							<a href="${ctxPath}/inventory/inventoryAudit.html?COL@s.productSku.productSkuCode@String@EQ=${not empty inventory.productSku.productSkuCode ? inventory.productSku.productSkuCode : param.productSkuCode}">
								<fmt:message key="inventory.more.inventoryAudit" />
							</a>
						</td>
					</tr>
					</c:if>
					</table>
				</form>
	<fmt:message key="inventoryModel.adjustmentQuantity.must.less.availableQuantity" var="quantity_must_less_availableQty">
		<fmt:param>${inventory.availableQuantity}</fmt:param>
	</fmt:message>
<app:ui_datePicker outPut="expectedRestockDate" />
<v:javascript formName="inventoryModel" staticJavascript="false" />
<script type="text/javascript">
applyValidate($("reservedQuantity"),"required,integer,maxValue=${inventory.availableQuantity+inventory.reservedQuantity}");
function fnInitValidate(adjustmentType){
	if(adjustmentType){
		$j("#adjustmentQuantity").removeAttr("disabled");
		$j("#adjustmentReason_1").removeAttr("disabled");
		$j("#adjustmentReason_2").removeAttr("disabled");
		$j("#adjustmentComment").removeAttr("disabled");
		if(adjustmentType==1){
			$j("#adjustmentReason_1").show();
			$j("#adjustmentReason_2").hide();
		}else{
			$j("#adjustmentReason_1").hide();
			$j("#adjustmentReason_2").show();
		}
		$j("#adjustmentReason").val($j("#adjustmentReason_"+adjustmentType).val());
	}else{
		removeValidate($("adjustmentQuantity"),"true");
		$j("#adjustmentQuantity").attr("disabled","disabled");
		$j("#adjustmentReason_1").attr("disabled","disabled");
		$j("#adjustmentReason_2").attr("disabled","disabled");
		$j("#adjustmentComment").attr("disabled","disabled");
	}
}
$j(document).ready(function(){
	fnInitValidate($j("#adjustmentType").val());
});
function fnValidateAdjustmentQuantity(adjustmentType){
	var adjustmentQuantity=$("adjustmentQuantity").value;
	if(adjustmentType==1){
		if(!(validateRequired(adjustmentQuantity)&&validateInteger(adjustmentQuantity)&&validateMinValue(adjustmentQuantity,1))){
			__handleVaErrMsg($("adjustmentQuantity"),"<fmt:message key="inventoryModel.adjustmentQuantity.must.positiveInteger" />");
			return false;
		}
	}else if(adjustmentType==2){
		if(!(validateRequired(adjustmentQuantity)&&validateInteger(adjustmentQuantity)&&validateMinValue(adjustmentQuantity,1)&&validateMaxValue(adjustmentQuantity,${inventory.availableQuantity}))){
			__handleVaErrMsg($("adjustmentQuantity"),"${quantity_must_less_availableQty}");
			return false;
		}
	}
	return true;
}
</script>
</c:if>
