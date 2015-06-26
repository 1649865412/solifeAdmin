<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<app:pageHeading pageHeadingKey="inventoryList.heading" />
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
		<form method="post" action="${ctxPath}/catalog/brand.html">
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
						：
					</div>
					<input id="productSkuCode" name="productSkuCode" type="text" style="width: 200px" />
					<div class="blank10"></div>
					<cartmatic:cartmaticBtn btnType="common" onclick="fnSearchInventory()" commonBtnValueKey="search.common"/>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form id="inventoryForm" action="${ctxPath}/inventory/inventory.html">
	<div id="inventoryFormContent">
		<%@ include file="inventoryForm.jsp"%>
	</div>
</form>
<script type="text/javascript">
function fnSearchInventory(){
	fillDivWithPage("inventoryFormContent",__ctxPath+"/inventory/inventory/dialog.html?doAction=getInventory&productSkuCode="+$j("#productSkuCode").val());
	//$j("#inventoryFormContent").load("${ctxPath}/inventory/inventory/dialog.html?doAction=getInventory&productSkuCode="+$j("productSkuCode").val());
}
function fnInitDatePicker(outPutId){
	$j('#'+outPutId).datepicker({showOn: 'button', showOtherMonths: true, 
		showWeeks: true, firstDay: 1, changeFirstDay: false,
		onClose: function(date) {validateField($(outPutId));},		
		buttonImageOnly: true, buttonImage: __ctxPath+'/scripts/jquery/themes/cartmatic/images/calendar.gif'});
}

</script>