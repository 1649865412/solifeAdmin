<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<div id="productBasisInfo">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content border01">

<%--==================产品名称=================--%>
<tr>
	<td width="20">
		<input type="checkbox" id="nameCheck" name="nameCheck" value="1"/>
	</td>
	<td width="120">
		<fmt:message key="product.productName" />
	</td>
	<td>
		<input type="radio" id="name_method_2" name="name_method" value="2" checked="checked"><fmt:message key="unitiveBulkEdit.method.add"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<fmt:message key="unitiveBulkEdit.method.add.prefix"/><span><input id="name_prefix" name="name_prefix" class="Field200"></span>&nbsp;&nbsp;&nbsp;
		<fmt:message key="unitiveBulkEdit.method.add.suffix"/><span><input id="name_suffix" name="name_suffix" class="Field200"></span>
		<br/>		
		<input type="radio" id="name_method_3" name="name_method" value="3"><fmt:message key="unitiveBulkEdit.method.findAndReplace"/>
		<fmt:message key="unitiveBulkEdit.method.find"/><span><input id="name_findAndReplaceOldString" name="name_findAndReplaceOldString"  class="Field200"/></span>
		<fmt:message key="unitiveBulkEdit.method.replace"/><span><input id="name_findAndReplaceNewString" name="name_findAndReplaceNewString"  class="Field200"/></span>
	</td>
</tr>

<%--==================产品状态=================--%>
<tr>
	<td>
		<input type="checkbox" id="statusCheck" name="statusCheck" value="1"/>
	</td>
	<td>
		<fmt:message key="product.status" />
	</td>
	<td>
		<select id="status" name="status" class="Field100">
			<option value="1">
				<fmt:message key="product.status_1" />
			</option>
			<option value="2">
				<fmt:message key="product.status_2" />
			</option>
		</select>
		<cartmatic:ui_tip id="status_tip"><fmt:message key="unitiveBulkEdit.product.status.tip" />	</cartmatic:ui_tip>
	</td>
</tr>
<%--==================品牌=================--%>
<tr>
	<td>
		<input type="checkbox" id="brandCheck" name="brandCheck" value="1"/>
	</td>
	<td>
		<fmt:message key="product.brandId" />
	</td>
	<td>
		<select id="brand" name="brand" class="Field100">
			<option value=""><fmt:message key="unitiveBulkEdit.brand.null"/></option>
			<c:forEach items="${brandList}" var="brand">
			<option value="${brand.brandId}"><c:out value="${brand.brandName}"/>
			</option>
			</c:forEach>
		</select>
	</td>
</tr>
<%--==================最少订购量=================--%>
<tr>
	<td>
		<input type="checkbox" id="minOrderQuantityCheck" name="minOrderQuantityCheck" value="1"/>
	</td>
	<td>
		<fmt:message key="product.minOrderQuantity" />
	</td>
	<td>
		<input type="text" name="minOrderQuantity" id="minOrderQuantity" value="<c:out value="${product.minOrderQuantity}"/>" class="Field100"/>
	</td>
</tr>
<%--==================是否接受评论=================--%>
<tr>
	<td>
		<input type="checkbox" id="isAllowReviewCheck" name="isAllowReviewCheck" value="1"/>
	</td>
	<td>
		<fmt:message key="product.isAllowReview" />
	</td>
	<td>
		<input type="checkbox" onclick="javascript:if(this.checked){$j('#isAllowReview').val(1);}else{$j('#isAllowReview').val(0);}" />
		<input type="hidden" name="isAllowReview" id="isAllowReview" />
		<fmt:message key="product.isAllowReview.yes" />
	</td>
</tr>

<%--==================模板路径=================--%>
<tr>
	<td>
		<input type="checkbox" id="templatePathCheck" name="templatePathCheck" value="1"/>
	</td>
	<td>
		<fmt:message key="product.templatePath" />
	</td>
	<td>
		<app:templateSelect templateList="${appConfig.productTemplates}" name="templatePath" value="${product.templatePath}"></app:templateSelect>
	</td>
</tr>

<%--==================产品计划上架时间=================--%>
<tr>
	<td>
		<input type="checkbox" id="planStartTimeCheck" name="planStartTimeCheck" value="1"/>
	</td>
	<td>
		<fmt:message key="product.planStartTime" />
	</td>
	<td>
		<input name="planStartTime" id="planStartTime" type="text" readonly="true" value="<fmt:formatDate value="${product.planStartTime}" pattern="yyyy-MM-dd" />" class="Field100"/>
		<app:ui_datePicker outPut="planStartTime" />
	</td>
</tr>


<%--==================产品计划下架时间=================--%>
<tr>
	<td>
		<input type="checkbox" id="planEndTimeCheck" name="planEndTimeCheck" value="1"/>
	</td>
	<td>
		<fmt:message key="product.planEndTime" />
	</td>
	<td>
		<input name="planEndTime" id="planEndTime" type="text" readonly="true" value="<fmt:formatDate value="${product.planEndTime}" pattern="yyyy-MM-dd" />" class="Field100"/>
		<app:ui_datePicker outPut="planEndTime" />
	</td>
</tr>


<c:forEach items="${categoryTreeItems}" var="catalogTreeItem">
<c:if test="${catalogTreeItem.isCatalog==1&&catalogTreeItem.hasChildren}">
<tr>
	<td colspan="2">${catalogTreeItem.name}
		<input type="hidden" name="catalogId" value="${catalogTreeItem.catalog.id}"/>
	</td>
</tr>
<%--==================主目录=================--%>
<tr>
	<td>
		<input type="checkbox" id="mainCategory_${catalogTreeItem.catalog.id}Check" name="mainCategory_${catalogTreeItem.catalog.id}Check" value="1"/>
	</td>
	<td>
		<fmt:message key="productDetail.mainCategory" />
	</td>
	<td>
		<input id="mainCategory_${catalogTreeItem.catalog.id}" name="mainCategory_${catalogTreeItem.catalog.id}" readonly="readonly" class="Field400"/>
		<input id="mainCategoryId_${catalogTreeItem.catalog.id}" name="mainCategoryId_${catalogTreeItem.catalog.id}" type="hidden"/>
		<input type="image" src="${ctxPath}/images/select.gif" onclick="try{CategorySelector_${catalogTreeItem.catalog.id}_show({name:'mainCategory',id:'${catalogTreeItem.catalog.id}'});}catch(e){alert(e.message);}return false;" title="<fmt:message key="productDetail.msg.addCategory" />" />
	</td>
</tr>

<%--==================额外目录=================--%>
<tr>
	<td>
		<input type="checkbox" id="otherCategory_${catalogTreeItem.catalog.id}Check" name="otherCategory_${catalogTreeItem.catalog.id}Check" value="1"/>
	</td>
	<td>
		<fmt:message key="productDetail.additionalCategories" />
	</td>
	<td>
		<input type="radio" name="otherCategory_method_${catalogTreeItem.catalog.id}" value="1" checked="checked"><fmt:message key="unitiveBulkEdit.method.replaceCat"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  name="otherCategory_method_${catalogTreeItem.catalog.id}" value="2"><fmt:message key="unitiveBulkEdit.method.addCat"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  name="otherCategory_method_${catalogTreeItem.catalog.id}" value="3"><fmt:message key="unitiveBulkEdit.method.delCat"/><br />
		<select id="otherCategory_${catalogTreeItem.catalog.id}" class="Field400" size="5" multiple="multiple">					
		</select>
		&nbsp;&nbsp;
		<input type="image" src="${ctxPath}/images/select.gif" onclick="try{CategorySelector_${catalogTreeItem.catalog.id}_show({name:'otherCategory',id:'${catalogTreeItem.catalog.id}'});}catch(e){alert(e.message);}return false;" title="<fmt:message key="productDetail.msg.addCategory" />" />
		<input type="image" src="${ctxPath}/images/clear_field.gif" onclick="try{fnDeleteCategory('${catalogTreeItem.catalog.id}');}catch(e){alert(e.message);}return false;" title="<fmt:message key="productDetail.msg.removeCategory" />" />
	</td>
</tr>
<product:categorySelector id="CategorySelector_${catalogTreeItem.catalog.id}" ondblclick="fnModifyCategoryHandler" canSelectRoot="false" catalogId="${catalogTreeItem.catalog.id}"></product:categorySelector>
</c:if>
</c:forEach>
</table>
</div>

