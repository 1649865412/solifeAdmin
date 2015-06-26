<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="purchaseOrderList.heading" />
<content tag="buttons">
<%--
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />--%>
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/supplier/purchaseOrder.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:input attrPath="s.purchaseOrderNo" attrNameKey="purchaseOrder.purchaseOrderNo" datatype="String" operator="LIKE" classes="form-inputbox" />
					<div class="title"><fmt:message key="purchaseOrder.poStatus"/></div>
					<div>
					<select name="COL@s.poStatus@Short@EQ" id="poStatus" style="width:150px" >
						<option value=""></option>
						<option value="0" <c:if test="${sc.param['COL@s.poStatus@Short@EQ'] eq 0}">selected="true"</c:if>><fmt:message key="purchaseOrder.poStatus0"/></option>
						<option value="1" <c:if test="${sc.param['COL@s.poStatus@Short@EQ'] eq 1}">selected="true"</c:if>><fmt:message key="purchaseOrder.poStatus1"/></option>
						<option value="2" <c:if test="${sc.param['COL@s.poStatus@Short@EQ'] eq 2}">selected="true"</c:if>><fmt:message key="purchaseOrder.poStatus2"/></option>
						<option value="3" <c:if test="${sc.param['COL@s.poStatus@Short@EQ'] eq 3}">selected="true"</c:if>><fmt:message key="purchaseOrder.poStatus3"/></option>
						<option value="4" <c:if test="${sc.param['COL@s.poStatus@Short@EQ'] eq 4}">selected="true"</c:if>><fmt:message key="purchaseOrder.poStatus4"/></option>
					</select>
					</div>
					<search:orderby showOrderDirection="true">
						<option value="purchaseOrderId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="purchaseOrderListForm" method="post" action="${ctxPath}/supplier/purchaseOrder.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/supplier/purchaseOrder.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${purchaseOrderList}" cellspacing="0" cellpadding="0" uid="purchaseOrderItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${purchaseOrderItem.purchaseOrderId}" class="checkbox" title="${purchaseOrderItem.purchaseOrderName}"/>
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="purchaseOrderId" paramProperty="purchaseOrderId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="purchaseOrder.purchaseOrderNo">
				${purchaseOrderItem.purchaseOrderName}
			</display:column>
		    <display:column property="supplierName" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="purchaseOrder.supplierName"/>
		    <display:column property="trackingNo" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="purchaseOrder.trackingNo"/>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="purchaseOrder.poStatus">
        		<fmt:message key="purchaseOrder.poStatus${purchaseOrderItem.poStatus}"/>
        	</display:column>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="purchaseOrder.poResult">
        		<fmt:message key="purchaseOrder.poStatus${purchaseOrderItem.poStatus}"/>
        	</display:column>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="purchaseOrder.createBy">
        		<app:userName userId="${purchaseOrderItem.createBy}" />
        	</display:column>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="purchaseOrder.updateBy">
        		<app:userName userId="${purchaseOrderItem.updateBy}" />
        	</display:column>
		    <display:column property="updateTime" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="purchaseOrder.updateTime"/>
		    <display:column property="createTime" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="purchaseOrder.createTime"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<script type="text/javascript">
highlightTableRows("purchaseOrderItem");
</script>