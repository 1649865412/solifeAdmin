<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="supplierProductList.heading" />

<content tag="buttons">
	<%-- 
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<!--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />-->
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	--%>
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/supplier/supplierProduct.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="s.productName" attrNameKey="supplierProduct.productName" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<div class="title">状态</div>
					<c:set var="statusValue" value="${sc==null ? requestScope['COL@s.status@Short@EQ'] : sc.param['COL@s.status@Short@EQ']}"/>
					<div>
						<select name="COL@s.status@Short@EQ">
							<option value=""/>全部</option>
							<option value="0"<c:if test="${statusValue==0}"> selected="selected"</c:if>/>未处理</option>
							<option value="1"<c:if test="${statusValue==1}"> selected="selected"</c:if>/>激活</option>
							<option value="3"<c:if test="${statusValue==3}"> selected="selected"</c:if>/>已废除</option>
							<option value="10"<c:if test="${statusValue==10}"> selected="selected"</c:if>/>未处理</option>
						</select>
						<div class="clear"></div>
					</div>
					<search:orderby showOrderDirection="true">
						<option value="supplierProductId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="supplierProductListForm" method="post" action="${ctxPath}/supplier/supplierProduct.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/supplier/supplierProduct.html?doAction=edit" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${supplierProductList}" cellspacing="0" cellpadding="0" uid="supplierProductItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${supplierProductItem.supplierProductId}" class="checkbox" title="${supplierProductItem.supplierProductName}"/>
			</display:column>
			<display:column property="productName" url="${editURLPath}" paramId="supplierProductId" paramProperty="supplierProductId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="supplierProduct.productName"/>
        	<display:column property="productCode" url="${editURLPath}" paramId="supplierProductId" paramProperty="supplierProductId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="supplierProduct.productCode"/>
        	<display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="supplierProduct.categoryId">
        		${supplierProductItem.category.categoryPathName}
        	</display:column>
        	<display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="supplierProduct.supplierId">
        		${supplierProductItem.supplier.supplierName}&nbsp;[${supplierProductItem.supplier.supplierCode}]
        	</display:column>
		    <display:column property="productId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="supplierProduct.productId"/>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="supplierProduct.status">
        		<fmt:message key="supplierProduct.status_${supplierProductItem.status}" />
        	</display:column>	    
		    <display:column property="createTime" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="supplierProduct.createTime"/>
		    <display:column property="updateTime" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="supplierProduct.updateTime"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<script type="text/javascript">
highlightTableRows("supplierProductItem");
</script>