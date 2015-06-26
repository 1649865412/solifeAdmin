<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<head>
	<app:pageHeading pageHeadingKey="unitiveBulkEdit.heading" />
</head>
<body>
	<div id="btn-space" style="display: none;">
		<cartmatic:cartmaticBtn btnType="save" onclick="ps('tabIframeId_${param.tid}','fnBulkProduct();');"/>
	</div>
	<div class="blank10"></div>
	<form method="post" id="productForm" name="productForm" action="${ctxPath}/catalog/product/window.html" onsubmit="return false;">
		<input type="hidden" name="productIds" id="productIds" value="${productIds}" />
		<input type="hidden" name="doAction" value="productEachEditUpdate"/>
		<input type="hidden" id="tid" value="${param.tid}" />
		<c:forEach items="${bulkProductList}" var="bulkProduct">
			<div class="eachedit-product">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-eachedit">
					<tr>
						<td>
							<cartmatic:img url="${bulkProduct.product.defaultProductSku.image}" mediaType="product" size="b" width="72" height="72" alt="${mediaPath}${bulkProduct.product.defaultProductSku.image}"></cartmatic:img>
						</td>
						<td>
							<table  width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<fmt:message key="product.${bulkProduct.prodCommAttrs.productName.name}"/>
									</td>
									<td>
										<input id="${bulkProduct.prodCommAttrs.productName.inputName}" name="${bulkProduct.prodCommAttrs.productName.inputName}" value="<c:out value="${bulkProduct.prodCommAttrs.productName.value}"/>" size="50">
									</td>
								</tr>
								<tr>
									<td>
										<fmt:message key="product.${bulkProduct.prodCommAttrs.productCode.name}"/>
									</td>
									<td>
										<input id="${bulkProduct.prodCommAttrs.productCode.inputName}" name="${bulkProduct.prodCommAttrs.productCode.inputName}" value="${bulkProduct.prodCommAttrs.productCode.value}" size="50">
									</td>
								</tr>
								<c:if test="${not empty bulkProduct.prodCommAttrs.extra1}">
									<tr>
										<td>
											<fmt:message key="product.${bulkProduct.prodCommAttrs.extra1.name}"/>
										</td>
										<td>
											<input id="${bulkProduct.prodCommAttrs.extra1.inputName}" name="${bulkProduct.prodCommAttrs.extra1.inputName}" value="<c:out value="${bulkProduct.prodCommAttrs.extra1.value}"/>" size="50">
										</td>
									</tr>
								</c:if>
								<tr>
									<td>
										<fmt:message key="product.${bulkProduct.prodCommAttrs.status.name}"/>
									</td>
									<td>
										<c:choose>
											<c:when test="${bulkProduct.product.status==1||bulkProduct.product.status==2}">
												<product:productStatus name="${bulkProduct.prodCommAttrs.status.inputName}" value="${bulkProduct.prodCommAttrs.status.value}" />
											</c:when>
											<c:otherwise>
												<product:productStatus value="${empty bulkProduct.product.status ? 0 : bulkProduct.product.status}" />
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td>
										发布时间
									</td>
									<td>
										<input name="${bulkProduct.prodCommAttrs.publishTime.inputName}" id="${bulkProduct.prodCommAttrs.publishTime.inputName}" value="<fmt:formatDate value="${bulkProduct.product.publishTime}" pattern="yyyy-MM-dd HH:mm:ss" />" type="text">
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table  width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<fmt:message key="product.${bulkProduct.prodCommAttrs.metaKeyword.name}"/>
									</td>
									<td>
										<input id="${bulkProduct.prodCommAttrs.metaKeyword.inputName}" name="${bulkProduct.prodCommAttrs.metaKeyword.inputName}" value="<c:out value="${bulkProduct.prodCommAttrs.metaKeyword.value}"/>" size="25">
									</td>
								</tr>
								<tr>
									<td>
										<fmt:message key="product.${bulkProduct.prodCommAttrs.metaDescription.name}"/>
									</td>
									<td>
										<textarea name="${bulkProduct.prodCommAttrs.metaDescription.inputName}" id="${bulkProduct.prodCommAttrs.metaDescription.inputName}" rows="5" cols="22">${bulkProduct.prodCommAttrs.metaDescription.value}</textarea>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<fmt:message key="product.${bulkProduct.prodCommAttrs['productDescription.fullDescription'].name}"/>
									</td>
									<td>
										<textarea name="${bulkProduct.prodCommAttrs['productDescription.fullDescription'].inputName}" id="${bulkProduct.prodCommAttrs['productDescription.fullDescription'].inputName}" rows="7" cols="20">${bulkProduct.prodCommAttrs['productDescription.fullDescription'].value}</textarea>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<c:if test="${not empty bulkProduct.prodAttrs}">
					<table  width="100%" border="0" cellspacing="0" cellpadding="0" class="table-eachedit" style="background-color:#F8F8F8;">
						<tr>
							<c:forEach items="${bulkProduct.prodAttrs}" var="prodAttr">
								<td>
								${prodAttr.value.attribute.attributeName}<product:bulkProdAttrInput attribute="${prodAttr.value.attribute}" bulkField="${prodAttr.value}"></product:bulkProdAttrInput>
								</td>
							</c:forEach>
						</tr>
					</table>
				</c:if>
				<c:if test="${not empty bulkProduct.productSkus}">
					<table  width="100%" border="0" cellspacing="0" cellpadding="0" class="table-eachedit">
						<c:forEach items="${bulkProduct.productSkus}" var="bulkProductSku">
							<tr>
								<td>
									<fmt:message key="productSku.${bulkProductSku.skuCommAttrs.productSkuCode.name}"/>
								</td>
								<td>
									<input id="${bulkProductSku.skuCommAttrs.productSkuCode.inputName}" name="${bulkProductSku.skuCommAttrs.productSkuCode.inputName}" value="${bulkProductSku.skuCommAttrs.productSkuCode.value}" size="35">
								</td>
								<c:if test="${bulkProduct.product.productKind==2}">
								<td>
									<fmt:message key="productSku.${bulkProductSku.skuCommAttrs.status.name}"/>
								</td>
								<td>
									<select id="${bulkProductSku.skuCommAttrs.status.inputName}" name="${bulkProductSku.skuCommAttrs.status.inputName}">
										<option value="1" <c:if test="${bulkProductSku.skuCommAttrs.status.value==1}">selected="selected"</c:if>>
											<fmt:message key="status.active" />
										</option>
										<option value="2" <c:if test="${bulkProductSku.skuCommAttrs.status.value==2}">selected="selected"</c:if>>
											<fmt:message key="status.inactive" />
										</option>
									</select>
								</td>
								</c:if>
								<td>
									<fmt:message key="productSku.${bulkProductSku.skuCommAttrs.costPrice.name}"/>
								</td>
								<td>
									<input id="${bulkProductSku.skuCommAttrs.costPrice.inputName}" name="${bulkProductSku.skuCommAttrs.costPrice.inputName}" value="${bulkProductSku.skuCommAttrs.costPrice.value}" size="10">
								</td>
								<td>
									<fmt:message key="productSku.${bulkProductSku.skuCommAttrs.price.name}"/>
								</td>
								<td>
									<input id="${bulkProductSku.skuCommAttrs.price.inputName}" name="${bulkProductSku.skuCommAttrs.price.inputName}" size="10" value="${bulkProductSku.skuCommAttrs.price.value}" <system:isNotAdmin> readonly="true" </system:isNotAdmin>>
									<span class="icon_help">
										<cartmatic:ui_tip id="price_tip_${bulkProductSku.skuCommAttrs.price.inputName}">（根据成本自动计算,只有超级管理员才能修改）</cartmatic:ui_tip>
									</span>
								</td>
								<td>
									<fmt:message key="productSku.${bulkProductSku.skuCommAttrs.salePrice.name}"/>
								</td>
								<td>
									<input id="${bulkProductSku.skuCommAttrs.salePrice.inputName}" size="10" name="${bulkProductSku.skuCommAttrs.salePrice.inputName}" value="${bulkProductSku.skuCommAttrs.salePrice.value}"<system:isNotAdmin> readonly="true" </system:isNotAdmin>>
									<span class="icon_help">
										<cartmatic:ui_tip id="salePrice_tip_${bulkProductSku.skuCommAttrs.salePrice.inputName}">（只有超级管理员才能修改）</cartmatic:ui_tip>
									</span>
								</td>
								<td>
									<fmt:message key="productSku.${bulkProductSku.skuCommAttrs.listPrice.name}"/>
								</td>
								<td>
									<input id="${bulkProductSku.skuCommAttrs.listPrice.inputName}" size="10" name="${bulkProductSku.skuCommAttrs.listPrice.inputName}" value="${bulkProductSku.skuCommAttrs.listPrice.value}"<system:isNotAdmin> readonly="true" </system:isNotAdmin>>
									<span class="icon_help">
										<cartmatic:ui_tip id="price_tip_${bulkProductSku.skuCommAttrs.listPrice.inputName}">（根据成本自动计算,只有超级管理员才能修改）</cartmatic:ui_tip>
									</span>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
		</c:forEach>
	</form>
	<script type="text/javascript" defer="true">
	$j(document).ready(function () {
		btnToP();
	});	
	function fnBulkProduct(){
		if(validateForm($("productForm"))){
			var postData=$j("#productForm").serializeArray();
			var url=__ctxPath+"/catalog/product/window.html";
			$j.post(url,postData,fnBulkProductCallback,"json");
		}else{
			sysMsg4p("数据有错误请检查!",true);
		}
	}
	function fnBulkProductCallback(result){
		sysMsg4p(result.msg,result.status!=1);
		if(result.status==-1){
			var input=$(result.data.bulkUpdateException.bulkField.inputName);
			input.focus();
			input.blur();
			__handleVaErrMsg(input,result.data.bulkUpdateException.msg);
		}
	}
	__fnApplyValidate("input[name^='productName_p_']","required,maxlength=128,noHtml");
	__fnApplyValidate("input[name^='productCode_p_']","required,maxlength=32,code");
	__fnApplyValidate("input[name^='url_p_']","maxlength=255");
	__fnApplyValidate("input[name^='title_p_']","maxlength=128");
	__fnApplyValidate("input[name^='metaKeyword_p_']","maxlength=256");
	__fnApplyValidate("input[name^='metaDescription_p_']","maxlength=256");
	__fnApplyValidate("input[name^='productDescription.shortDescription_p_']","maxlength=512");
	__fnApplyValidate("input[name^='productDescription.fullDescription_p_']","maxlength=2000");
	
	__fnApplyValidate("input[name^='productSkuCode_s_']","required,maxlength=32,code");
	__fnApplyValidate("input[name^='price_s_']","required,price,floatRange=[0-9999999999]");
	__fnApplyValidate("input[name^='costPrice_s_']","required,price,floatRange=[0-9999999999]");
	__fnApplyValidate("input[name^='salePrice_s_']","price,floatRange=[0-9999999999]");
	__fnApplyValidate("input[name^='listPrice_s_']","price,floatRange=[0-9999999999]");
	
	function __fnApplyValidate(str,validates){
		var inputs=$j(str);
		for(var i=0;i<inputs.length;i++){
			applyValidate(inputs[i],validates);
		} 
	}
	</script>
</body>