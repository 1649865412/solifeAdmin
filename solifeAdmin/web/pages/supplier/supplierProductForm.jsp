<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="supplier" tagdir="/WEB-INF/tags/supplier"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<app:pageHeading entityName="${supplierProduct.supplierProductName}" entityHeadingKey="supplierProductDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'productName');" />
	<%--
    <c:if test="${supplierProduct.supplierProductId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
	</c:if>
	 --%>
	<c:if test="${supplierProduct.status==10}">
		<cartmatic:cartmaticBtn btnType="common" onclick="pass();return false;" commonBtnValue="产品信息审核通过"/>
		<cartmatic:cartmaticBtn btnType="common" onclick="noPass();return false;" commonBtnValue="产品信息审核不通过"/>
	</c:if>
	<c:choose>
		<c:when test="${not empty supplierProduct.product}">
			<cartmatic:cartmaticBtn btnType="common" onclick="fnOpenProductWindow(${supplierProduct.productId});return false;" commonBtnValue="编辑在线产品"/>
		</c:when>
		<c:otherwise>
			<cartmatic:cartmaticBtn btnType="common" onclick="dlgcreateProduct_show();return false;" commonBtnValue="产品信息没有问题，创建在线产品"/>
		</c:otherwise>
	</c:choose>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="supplierProduct.*" />
<form id="supplierProduct" class="mainForm" action="${ctxPath}/supplier/supplierProduct.html" method="post" onsubmit="return validateSupplierProduct(this);">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="supplierProductDetail.heading" /><input type="hidden" name="supplierProductId" value="${supplierProduct.supplierProductId}"/> 
			</th>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="supplierProduct.supplierId" />
			</td>
			<td>
				${supplierProduct.supplier.supplierName}
			</td>
        </tr>
		<tr>
			<td class="FieldLabel">
				对应在线销售产品:
			</td>
			<td>
				<c:choose>
					<c:when test="${not empty supplierProduct.product}">
						<a href="javascript:void(${supplierProduct.productId});" onclick="fnOpenProductWindow(${supplierProduct.productId});">
						${supplierProduct.product.productName}&nbsp;&nbsp;&nbsp;&nbsp;[${supplierProduct.product.productCode}]&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="status_${supplierProduct.product.status}" /></a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0);" onclick="dlgcreateProduct_show();">未创建，马上创建</a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="supplierProduct.productName" />
			</td>
			<td>
				${supplierProduct.productName}<input id="productName" type="hidden" value="${supplierProduct.productName}" />
			</td>
        </tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="supplierProduct.productCode" />
			</td>
			<td>
				${supplierProduct.productCode}
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="supplierProduct.categoryId" />
			</td>
			<td>
				${supplierProduct.category.categoryName}
			</td>
        </tr>
		<c:if test="${not empty supplierProduct.tbCId}">
        <tr>
			<td class="FieldLabel">
				宝贝类目:
			</td>
			<td>
				<supplier:tbCategoryRefer var="tbCategoryRefer" tbCId="${supplierProduct.tbCId}"></supplier:tbCategoryRefer>
				${tbCategoryRefer.tbCategoryId}[${tbCategoryRefer.tbCategoryName}]
			</td>
        </tr>
		</c:if>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="supplierProduct.status" />
			</td>
			<td>
				<fmt:message key="supplierProduct.status_${supplierProduct.status}" />
			</td>
		</tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<th colspan="2">
			<fmt:message key="supplierProduct.wholesalePrice" />
		</th>
		<tr>
			<td colspan="2">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<fmt:message key="supplierProduct.wholesalePrice.qty" />
						</td>
						<td>
							<fmt:message key="supplierProduct.wholesalePrice.price" />
						</td>
					</tr>
					<c:forEach items="${supplierProduct.wholesalePriceList}" var="wholesalePrice">
						<tr>
							<td>
								${wholesalePrice[0]}
							</td>
							<td>
								${wholesalePrice[1]}
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
     </table>
     <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<th colspan="2">
				<fmt:message key="supplierProduct.mediaUrl" />
			</th>
		</tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<tr>
			<td colspan="2">
				<c:forEach items="${supplierProduct.mediaUrlList}" var="mediaUrl" varStatus="varStatus">
					<cartmatic:img url="${mediaUrl}" size="v" mediaType="supplierProduct" width="100" height="100" ></cartmatic:img>
				</c:forEach>
			</td>
		</tr>
		<th colspan="2">
			<fmt:message key="supplierProduct.productDesc" />
		</th>
		<tr>
			<td colspan="2">
				<textarea id="productDesc" rows="12" cols="80" style="width: 100%; height: 500px;">${supplierProduct.productDesc}</textarea>
			</td>
		</tr>
        </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
		<th colspan="2">
			<fmt:message key="supplierProduct.modifyLogs" />
		</th>
		<tr>
			<td colspan="2">
				<textarea id="modifyLogs" name="modifyLogs" rows="12" cols="80" style="width: 100%; height: 250px;">${supplierProduct.modifyLogs}</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<c:forEach items="${updateLogs}" var="updateLog">
					<c:forEach items="${updateLog}" var="log" varStatus="varStatus">
						${log}
						<c:choose>
							<c:when test="${varStatus.first}">:<br /></c:when>
							<c:when test="${varStatus.last}">.<br /></c:when>
							<c:otherwise>,</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:forEach>
			</td>
		</tr>
	</table>
</form>
<app:ui_htmlEditor textareaIds="productDesc" />
<v:javascript formName="supplierProduct" staticJavascript="false" />
<script type="text/javascript">
function pass(){
	$j("#supplierProduct").append('<input type="hidden" name="status" value="1"/><input type="hidden" name="doAction" value="save"/>').submit();
}
function noPass(){
	$j("#supplierProduct").append('<input type="hidden" name="status" value="0"/><input type="hidden" name="doAction" value="save"/>').submit();
}
function fnOpenProductWindow(productId){
	fnNewWindow("${ctxPath}/catalog/product/window.html?doAction=edit&productId="+productId,650,1100);
}

/**
 * 添加产品时，选择产品类型后的回调；主要检查当前产品是否可以选择设置多个Sku
 */
function fnSelectProductTypeHandler(productTypeId){
	var hasActiveSkuOptionProductTypeIds = $j("#hasActiveSkuOptionProductTypeIds").val();
	if (hasActiveSkuOptionProductTypeIds.indexOf("," + productTypeId + ",") == -1) {
		$j("#isVariationSpan").hide();
	} else {
		$j("#isVariationSpan").show();
	}
	fnShowProductSkuWholesalePricesForm();
}

/**
 * 新增批发价
 */
function fnAddWholesalePrice(){
	var productSkuWholesalePricesFormTR = '';
	productSkuWholesalePricesFormTR += '<tr>';
	productSkuWholesalePricesFormTR += '<td><input name="wholesalePrice_minQuantity"  type="text" style="width:40px;"/></td>';
	productSkuWholesalePricesFormTR += '<td><input name="wholesalePrice_price" type="text" style="width:100px;"/></td>';
	productSkuWholesalePricesFormTR += '<td><input id="btnDelWholesalePrice" type="button" class="btn-back" value="' + __FMT.button_small_delete + '" onclick="fnDeleleTableTR(this)"/></td>';
	productSkuWholesalePricesFormTR += '</tr>';
	$j("#productSkuWholesalePricesForm").append(productSkuWholesalePricesFormTR);
	//fnInitValidateWholesaPrice();
}

/**
 * 删除表格当前行
 * @param {Object} btnDel
 */
function fnDeleleTableTR(btnDel){
	$j($j(btnDel).parents("tr")[0]).remove();
}

function fnCreateProduct(){
	var productTypeId=$j("#productTypeId").val();
	if(productTypeId==""){
		alert("请选择产品类型!");
	}else{
		var paramData=$j("#createProductForm").find("input,select").serialize();
		var url="${ctxPath}/supplier/supplierProduct.html?doAction=createProduct&supplierProductId=${supplierProduct.supplierProductId}";
		jQuery.ajax({type: "POST",url: url,data:paramData,success: fnCreateProductHandler,async: false,dataType: "json"});
	}
}

function fnCreateProductHandler(result){
	if(result.status==1){
		fnOpenProductWindow(result.data.productId);
		window.location.reload();
	}else if(result.status==-500){
		alert("创建在线销售产品失败!");
	}else{
		alert(result.msg);
	}
}

function fnShowProductSkuWholesalePricesForm(){
	var isVariation=$j("#isVariation").val();
	var productTypeId=$j("#productTypeId").val();
	var hasActiveSkuOptionProductTypeIds = $j("#hasActiveSkuOptionProductTypeIds").val();
	if(isVariation=="true"&&hasActiveSkuOptionProductTypeIds.indexOf("," + productTypeId + ",") != -1){
		$j("#productSkuWholesalePricesForm").hide();
	}else{
		$j("#productSkuWholesalePricesForm").show();
	}
}
</script>
<app:ui_dialog height="300" width="450" title="创建在线销售产品" id="createProduct" buttons="'创建在线销售产品':fnCreateProduct">
<div id="createProductForm">
产品类型：
<select id="productTypeId" name="productTypeId" onchange="fnSelectProductTypeHandler(this.value);">
	<option value=""></option>
	<c:forEach items="${productTypeList}" var="productType">
		<option value="${productType.productTypeId}">
			${productType.productTypeName}
		</option>
	</c:forEach>
</select>
&nbsp;&nbsp;&nbsp;&nbsp;
<input type="hidden" id="hasActiveSkuOptionProductTypeIds" value="${hasActiveSkuOptionProductTypeIds}" />
<span id="isVariationSpan" style="display:none"><input type="checkbox" onclick="if(this.checked){$j('#isVariation').val('true');}else{$j('#isVariation').val('false');}fnShowProductSkuWholesalePricesForm();"/> <input type="hidden" name="isVariation" id="isVariation" /> <fmt:message key="product.isVariation" /></span>
<c:if test="${not empty supplierProduct.tbCId}">
<br/>
产品目录:${tbCategoryRefer.category.categoryPathName}<c:if test="${empty tbCategoryRefer.category}">请完善目录对照表</c:if>
</c:if>
<br/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="productSkuWholesalePricesForm">
	<tr>
		<td colspan="3">
			价格：
		</td>
	</tr>
	<tr>
		<td>
			<fmt:message key="wholesalePrice.minQuantity" />
		</td>
		<td>
			<fmt:message key="wholesalePrice.price" />
		</td>
		<td>
			<cartmatic:cartmaticBtn inputName="btnAddWholesalePrice" btnType="common" onclick="fnAddWholesalePrice();" commonBtnValueKey="button.small.add"/>
		</td>
	</tr>
	<c:forEach items="${supplierProduct.wholesalePriceList}" var="wholesalePrice" varStatus="varStatus">
		<tr>
			<td>
				<input name="wholesalePrice_minQuantity" type="text" value="${wholesalePrice[0]}" style="width: 40px;" <c:if test="${varStatus.first}"> readonly="readonly"</c:if>/>
			</td>
			<td>
				<input name="wholesalePrice_price" type="text" value="${wholesalePrice[1]}" style="width: 100px;" />
			</td>
			<td>&nbsp;</td>
		</tr>
	</c:forEach>
</table>
</div>
</app:ui_dialog>