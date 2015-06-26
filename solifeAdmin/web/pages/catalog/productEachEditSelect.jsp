<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<app:pageHeading pageHeadingKey="unitiveBulkEdit.heading" />
<div class="btn-space">
	<div class="left">
		<cartmatic:cartmaticBtn btnType="eachEdit" inputType="button" onclick="fnBulkProductList(1);" />
		<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="window.close();" />
	</div>
	<div class="right">
	</div>
</div>
<div id="message"></div>
<form method="post" id="productForm" name="productForm" action="${ctxPath}/catalog/product/window.html" onsubmit="return false;">
	<div id="productsDiv" class="box-content-wrap">
		<div class="box-content">
			<div class="top">
				<input type="hidden" name="productIds" id="productIds" value="${productIds}" />
				<input type="hidden" name="editType" id="editType" />
				<input type="hidden" name="doAction" value="productEachEdit"/>
			</div>
			<div class="top01">
				<fmt:message key="unitiveBulkEdit.product.list">
					<fmt:param value="${fn:length(productList)}"></fmt:param>
				</fmt:message>
				:
				<c:forEach items="${productList}" var="product" varStatus="status">
					${product.productName}
					<c:if test="${!status.last}">,
					</c:if>
				</c:forEach>
			</div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content border01">
					<tr>
						<th width="20%">
							产品基本属性
						</th>
						<td style="text-align: left">
							<input type="checkbox" onclick="$j('input[type=\'checkbox\'][name=\'sel_bulk_pro_comm_attrs\']').attr('checked',this.checked);"/>本系列属性全选
							<br/>
							<c:forEach items="${prodCommAttrs}" var="attrName">
								<input type="checkbox" name="sel_bulk_pro_comm_attrs" value="${attrName}" />&nbsp;<fmt:message key="product.${attrName}"/>
								&nbsp;&nbsp;
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th width="20%">
							SKU属性
						</th>
						<td style="text-align: left">
							<input type="checkbox" onclick="$j('input[type=\'checkbox\'][name=\'sel_bulk_sku_attrs\']').attr('checked',this.checked);"/>本系列属性全选
							<br/>
							<c:forEach items="${skuCommAttrs}" var="attrName">
								<input type="checkbox" name="sel_bulk_sku_attrs" value="${attrName}" />&nbsp;<fmt:message key="productSku.${attrName}"/>
								&nbsp;&nbsp;
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th width="20%">
							产品自定义属性
						</th>
						<td style="text-align: left">
							<input type="checkbox" onclick="$j('input[type=\'checkbox\'][name=\'sel_bulk_pro_attrs\']').attr('checked',this.checked);"/>本系列属性全选
							<br/>
							<c:forEach items="${prodAttrs}" var="prodAttr">
								<input type="checkbox" name="sel_bulk_pro_attrs" value="${prodAttr.attributeCode}" />&nbsp;
								${prodAttr.attributeName}
								&nbsp;&nbsp;
							</c:forEach>
							<c:if test="${empty prodAttrs}">
							无
							</c:if>
						</td>
					</tr>
					
					<tr>
						<th width="20%">
							&nbsp;
						</th>
						<td style="text-align: left">
							<input type="checkbox" onclick="$j('input[type=\'checkbox\']').attr('checked',this.checked);"/>&nbsp;全选
						</td>
					</tr>
			</table>
		</div>
	</div>
</form>
<script type="text/javascript" defer="true">
function fnBulkProductList(editType){
	var selectAttrs=$j("#productForm :input[type='checkbox']").serializeArray();
	if(selectAttrs.length>0){
		$("editType").value=editType;
		$("productForm").submit();
	}else{
		alert("请选择要修改的属性");
	}
}
</script>