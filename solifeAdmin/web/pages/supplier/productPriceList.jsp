<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="supplierList.heading" />
<content tag="buttons">
	<%--
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />--%>
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/supplier/productPrice.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:input attrPath="s.product.productName" attrNameKey="product.productName" datatype="String" operator="LIKE" classes="form-inputbox" />
					<search:input attrPath="s.productSkuCode" attrNameKey="productSku.productSkuCode" datatype="String" operator="LIKE" classes="form-inputbox" />
					<search:input attrPath="s.product.supplier.supplierName" attrNameKey="product.supplierId" datatype="String" operator="LIKE" classes="form-inputbox" />
					<search:input attrPath="s.product.supplier.supplierCode" attrNameKey="supplier.supplierCode" datatype="String" operator="LIKE" classes="form-inputbox" />
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="supplierListForm" method="post" action="${ctxPath}/supplier/productPrice.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/supplier/productPrice.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${productPriceList}" cellspacing="0" cellpadding="0" uid="productSkuItem" class="table-list" export="false" requestURI="">
			<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.image" style="width:72px; padding-top:8px;" >
				<cartmatic:img url="${productSkuItem.image}" id="productImage" mediaType="product" size="b" width="72" height="72"></cartmatic:img>
			</display:column>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productPrice.product" >
        		<a href="${appConfig.store.siteUrl}/_p${productSkuItem.product.id}.html" target="_blank">${productSkuItem.product.productName}</a>
        		<br />
        		code:${productSkuItem.productSkuCode}
        		<br />
        		<fmt:message key="product.supplierId"/>:${productSkuItem.product.supplier.supplierName}
        	</display:column>
        	<display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productPrice.price" style="width:300px;">
                <table border="0" cellspacing="0" cellpadding="0" width="100%" style="border: 1px solid #BABFC5; border-collapse:collapse; margin:10px 0; line-height:200%;">
					<tr>
						<td>
							<fmt:message key="productSku.costPrice"/>:
						</td>
						<td>
							<span id="span_costPrice_${productSkuItem.id}">${productSkuItem.costPrice}</span>
						</td>
						<td>
							<input type="text" id="input_costPrice_${productSkuItem.id}" name="input_costPrice_${productSkuItem.id}" value="${productSkuItem.costPrice}" size="7"/>
						</td>
					</tr>
					<tr>
						<td>
							<fmt:message key="productSku.price"/>:
						</td>
						<td>
							<span id="span_price_${productSkuItem.id}">${productSkuItem.price}</span>
						</td>
						<td style="padding-top:6px;">
							<input type="text" id="input_price_${productSkuItem.id}" name="input_price_${productSkuItem.id}" value="${productSkuItem.price}" size="7"/>
						</td>
					</tr>
					<tr>
						<td>
							<fmt:message key="productSku.listPrice"/>:
						</td>
						<td>
							<span id="span_listPrice_${productSkuItem.id}">${productSkuItem.listPrice}</span>
						</td>
						<td style="padding-top:6px;">
							<input type="text" id="input_listPrice_${productSkuItem.id}" name="input_listPrice_${productSkuItem.id}" value="${productSkuItem.listPrice}" size="7"/>
						</td>
					</tr>
					<tr>
						<td>
							<fmt:message key="productSku.salePrice"/>:
						</td>
						<td>
							<span id="span_salePrice_${productSkuItem.id}">${productSkuItem.salePrice}</span>
						</td>
						<td style="padding-top:6px;">
							<input type="text" name="input_salePrice_${productSkuItem.id}" value="${productSkuItem.salePrice}" size="7"/>
						</td>
					</tr>
				</table>
        	</display:column>
        	<display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productSku.wholeSalePrice" style="width: 280px;">
	        		<table width="100%" border="0" cellspacing="0" cellpadding="0" id="productSkuWholesalePricesForm_${productSkuItem.id}" style="border: 1px solid #BABFC5; border-collapse:collapse; margin:10px 0; line-height:200%;">
						<tr>
							<td>
								<fmt:message key="wholesalePrice.minQuantity" />
							</td>
							<td>
								<fmt:message key="wholesalePrice.price" />
							</td>
							<td style="padding-top:2px;">
								<cartmatic:iconBtn icon="plus"  onclick="fnAddWholesalePrice(${productSkuItem.id});" textKey="common.link.add"/>
							</td>
						</tr>
						<c:forEach items="${productSkuItem.wholesalePrices}" var="wholesalePrice">
							<tr>
								<td>
									<input name="input_wholesalePrice_minQuantity_${productSkuItem.id}" id="height" type="text" value="${wholesalePrice.minQuantity}" style="width: 28px;" />
								</td>
								<td>
									<input name="input_wholesalePrice_price_${productSkuItem.id}" id="height" type="text" value="${wholesalePrice.price}" size="7"/>
								</td>
								<td style="padding-top:2px;">
									<cartmatic:iconBtn icon="cross" extraCss="negative" onclick="fnDeleleTableTR(this);" textKey="button.small.delete"/>
								</td>
							</tr>
						</c:forEach>
					</table>
        	</display:column>
        	<display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="common.action" style="width: 35px;padding-top:56px;">
        		<cartmatic:iconBtn icon="check"  onclick="fnSaveProductSkuPrices(${productSkuItem.id});" textKey="common.link.save"/>
				<div id="msg_info_${productSkuItem.id}"></div> 		
        	</display:column>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("supplierItem");
$j(document).ready(function(){
	var input_prices = $j("[name^='input_costPrice_'],[name^='input_price_'],[name^='input_wholesalePrice_price_']");
	$j.each(input_prices, function(){
		applyValidate(this, "required,price");
	});
	input_prices = $j("[name^='input_salePrice_'],[name^='input_listPrice_']");
	$j.each(input_prices, function(){
		applyValidate(this, "price");
	});
	input_prices = $j("[name^='input_wholesalePrice_minQuantity_']");
	$j.each(input_prices, function(){
		applyValidate(this, "required,integer,minValue=2");
	});
});
/**
 * 新增批发价
 */
function fnAddWholesalePrice(skuId){
	var productSkuWholesalePricesFormTR = '';
	productSkuWholesalePricesFormTR += '<tr>';
	productSkuWholesalePricesFormTR += '<td><input name="input_wholesalePrice_minQuantity_'+skuId+'"  type="text" style="width:28px;"/></td>';
	productSkuWholesalePricesFormTR += '<td><input name="input_wholesalePrice_price_'+skuId+'" type="text" size="7"/></td>';
	productSkuWholesalePricesFormTR += '<td><a onclick="fnDeleleTableTR(this);" href="javascript:void(false);" class="button negative"><span class="cross icon"></span>'+ __FMT.button_small_delete +'</a></td>';
	productSkuWholesalePricesFormTR += '</tr>';
	$j("#productSkuWholesalePricesForm_"+skuId).append(productSkuWholesalePricesFormTR);
	fnInitValidateWholesaPrice(skuId);
}
/**
 * 初始化批发价验证
 */
function fnInitValidateWholesaPrice(skuId){
	var wholesalePrice_minQuantitys = $j("#productSkuWholesalePricesForm_"+skuId+" [name='input_wholesalePrice_minQuantity_"+skuId+"']");
	$j.each(wholesalePrice_minQuantitys, function(){
		applyValidate(this, "required,integer,minValue=2");
	});
	var wholesalePrice_prices = $j("#productSkuWholesalePricesForm_"+skuId+" [name='input_wholesalePrice_price_"+skuId+"']");
	$j.each(wholesalePrice_prices, function(){
		applyValidate(this, "required,price");
	});
}
/**
 * 删除表格当前行
 * @param {Object} btnDel
 */
function fnDeleleTableTR(btnDel){
	$j($j(btnDel).parents("tr")[0]).remove();
}
function fnSaveProductSkuPrices(skuId){
	var param="";
	var from_tr=$j("input[name='input_price_"+skuId+"']").parents("tr[class]");
	var inputs=$j("input[type='text']",from_tr);
	/*
	var invalidCount = 0;
	$j.each(inputs, function(){
		if (validateField(this).length > 0) {
			invalidCount++;
		}
	});
	*/
	if(validateForm(from_tr.get(0))){
		$j("#msg_info_"+skuId).html('<img src="'+__ctxPath+'/images/icon/loading.gif">');
		$j.post(__ctxPath+'/supplier/productPrice.html?doAction=save&skuId='+skuId,inputs.serializeArray(),fnSaveProductSkuPricesHandler,"json");
	}else{
		sysMsg(__vaMsg.notPass,true);
	}
}
function fnSaveProductSkuPricesHandler(result){
	if(result.status==1){
		var data=result.data;
		$j("#span_costPrice_"+data.skuId).html(data.costPrice);
		$j("#span_price_"+data.skuId).html(data.price);
		$j("#span_salePrice_"+data.skuId).html(data.salePrice);
		$j("#span_listPrice_"+data.skuId).html(data.listPrice);
		$j("#msg_info_"+data.skuId).html('<img src="'+__ctxPath+'/images/icon/info_success.png">保存成功!');
	}else{
		$j("#msg_info_"+result.data.skuId).html('<img src="'+__ctxPath+'/images/iconWarning.gif">保存失败!');
	}
	setTimeout(function(){$j("#msg_info_"+data.skuId).empty()},2000);
}
</script>