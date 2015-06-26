<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>

<app:pageHeading pageHeadingKey="associateBulkEdit.heading" />

<div class="btn-space">
	<div class="left">
		<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave();" />
		<cartmatic:cartmaticBtn btnType="cancel" inputType="button"
			onclick="window.close();" />
	</div>
	<div class="right">

	</div>
</div>
<div id="message"></div>
<form method="post" id="productForm" name="productForm"
	action="${ctxPath}/catalog/product.html">

	<div id="productsDiv" class="box-content-wrap">
		<div class="box-content">
			<div class="top">
				<input type="hidden" name="productIds" id="productIds"
					value="${productIds}" />
				<input type="hidden" name="productSkuIds" id="productSkuIds"
					value="${productSkuIds}" />
			</div>
			<div class="content">
				<table class="table-spe table-spe01" cellSpacing="0" cellPadding="0"
					width="100%" border="0">
					<c:forEach items="${productList}" var="product">
						<tr>
							<th width="20">
								&nbsp;

							</th>
							<th width="300" class="name">
								<product:productKindImg product="${product}"></product:productKindImg>
								&nbsp;${product.productName}
							</th>
							<th>
								<StoreAdmin:label key="product.status" />
								<c:choose>
									<c:when test="${product.status==1 or product.status==2}">
										<select id="status_${product.productId}"
											name="status_${product.productId}">
											<option value="1"
												<c:if test="${product.status==1}">selected</c:if>>
												<fmt:message key="product.status_1" />
											</option>
											<option value="2"
												<c:if test="${product.status==2}">selected</c:if>>
												<fmt:message key="product.status_2" />
											</option>
										</select>
									</c:when>
									<c:otherwise>
										<fmt:message key="product.status_${product.status}" />
									</c:otherwise>
								</c:choose>
							</th>
							<th>
								<c:choose>
									<c:when test="${product.status==0 or product.status==1 or product.status==2}">
										<StoreAdmin:label key="product.planStartTime" />
										<span><input name="planStartTime_${product.productId}"
												id="planStartTime_${product.productId}" type="text"
												value="<fmt:formatDate value="${product.planStartTime}" pattern="yyyy-MM-dd" />" />
										</span>
										<app:ui_datePicker
											outPut="planStartTime_${product.productId}" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<StoreAdmin:label key="product.planEndTime" />
										<span><input name="planEndTime_${product.productId}"
												id="planEndTime_${product.productId}" type="text"
												value="<fmt:formatDate value="${product.planEndTime}" pattern="yyyy-MM-dd" />" />
										</span>
										<app:ui_datePicker
											outPut="planEndTime_${product.productId}" />
									</c:when>
									<c:otherwise>
										<fmt:message key="associateBulkEdit.product.cannotEdit"></fmt:message>
										
									</c:otherwise>
								</c:choose>
							</th>
						</tr>
						<c:if test="${product.status==1 or product.status==2}">
							<c:forEach items="${product.productSkus}" var="productSku">
								<tr>
									<td>
										&nbsp;

									</td>
									<td class="sku">
										&nbsp;&nbsp;&nbsp;&nbsp;
										<fmt:message key="productSku.productSkuCode" />
										:${productSku.productSkuCode}
									</td>
									<td>
										<fmt:message key="productSku.status" />:
										<select id="productSkuStatus_${productSku.productSkuId}"
											name="productSkuStatus_${productSku.productSkuId}">
											<option value="1"
												<c:if test="${productSku.status==1}">selected="selected"</c:if>>
												<fmt:message key="status.active" />
											</option>
											<option value="2"
												<c:if test="${productSku.status==2}">selected="selected"</c:if>>
												<fmt:message key="status.inactive" />
											</option>
										</select>
									</td>
									<td>
										<StoreAdmin:label key="productSku.price" />
										<span><input name="price_${productSku.productSkuId}"
												id="price_${productSku.productSkuId}" type="text"
												value="${productSku.price}" /> </span> &nbsp;&nbsp;&nbsp;
										<StoreAdmin:label key="productSku.salePrice" />
										<span><input
												name="salePrice_${productSku.productSkuId}"
												id="salePrice_${productSku.productSkuId}" type="text"
												value="${productSku.salePrice}" /> </span> &nbsp;&nbsp;&nbsp;
										<StoreAdmin:label key="productSku.listPrice" />
										<span><input
												name="listPrice_${productSku.productSkuId}"
												id="listPrice_${productSku.productSkuId}" type="text"
												value="${productSku.listPrice}" /> </span>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</c:forEach>



				</table>
			</div>
		</div>
	</div>
</form>

<v:javascript formName="product" staticJavascript="false" />
<script type="text/javascript" defer="true">
$j(document).ready(function () {
	var productIds = $('productIds').value.split(",");
	for (var i=0;i<productIds.length;i++){
		applyValidate($("planStartTime_"+productIds[i]),"date");
		applyValidate($("planEndTime_"+productIds[i]),"date");
	}
	var productSkuIds = $('productSkuIds').value.split(",");
	for (var i=0;i<productSkuIds.length;i++){
		applyValidate($("price_"+productSkuIds[i]),"required,price");
		applyValidate($("salePrice_"+productSkuIds[i]),"price");
		applyValidate($("listPrice_"+productSkuIds[i]),"price");
	}
	
	
});	


function fnDoSave(){
	//clear the message
	$j('#message').empty()
	var confirmMsg = __FMT.common_message_confirmSaveThis+"?";
	if(validateAction()){
    	if (!confirmMsg || confirm(confirmMsg)) {
	    	var url = document.productForm.action+"?doAction=associatedBulkEditSave";
	    	var postData = $j('#productsDiv :input').serialize();
	    	$j('#message').append("<div class='message'><img src='${ctxPath}/images/icon/loading.gif'/>"+"<fmt:message key='bulkEdit.commit_wait'/>"+"</div>");
			$j.post(url,postData,fnDoSaveCallback,"json");
	    }
    }
}

function fnDoSaveCallback(){
	$j('#message').empty();
   	$j('#message').append("<div class='message'><img src='${ctxPath}/images/icon/info_success.png'/>"+"<fmt:message key='bulkEdit.commit_success'/>"+"</div>");
}

function checkPlanEndTime(){
	var productIds = $('productIds').value.split(",");
	for (var i=0;i<productIds.length;i++){
		if(null != $("planStartTime_"+productIds[i])){
			if (($("planStartTime_"+productIds[i]).value !="") &&(fnCompareDate($("planStartTime_"+productIds[i]).value,$("planEndTime_"+productIds[i]).value) >=0) ){
			   	$j('#planEndTime_'+productIds[i]).focus();
			   	alert(__FMT. productDetail_validate_endTimeGtStartTime);
			   	return false;
	   		}
   		}
	}
	
   	return true;
}
function validateAction(){
	if(!checkPlanEndTime()){
    	return false;
   	}
	 if(!validateProduct()){
    	return false;
   	}
   	   
    return true;
}
</script>