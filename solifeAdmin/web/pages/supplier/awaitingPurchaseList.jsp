<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="awaitingPurchaseList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>

<form class="mainForm" name="awaitingPurchaseListForm" method="post" action="${ctxPath}/supplier/awaitingPurchase.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/supplier/awaitingPurchase.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${awaitingPurchaseList}" cellspacing="0" cellpadding="0" uid="awaitingPurchaseItem"
			class="table-list" export="false" requestURI="">			
			<display:column property="supplier.supplierName" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="supplier.supplierName"/>
        	<display:column property="supplier.supplierCode" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="supplier.supplierCode"/>
		    <display:column property="quantity" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="awaitingPurchase.quantity"/>
        	<display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="common.action">
        		<a href="${ctxPath}/supplier/purchaseOrder.html?doAction=createPurchaseOrder&supplierId=${awaitingPurchaseItem.supplierId}">
        		<fmt:message key="awaitingPurchase.createPO"/></a>
        	</display:column>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<script type="text/javascript">
highlightTableRows("awaitingPurchaseItem");
</script>