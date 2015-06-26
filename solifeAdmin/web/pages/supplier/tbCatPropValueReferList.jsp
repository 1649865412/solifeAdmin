<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="tbCatPropValueReferList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<!--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />-->
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
	    <form method="post" action="${ctxPath}/supplier/tbCatPropValueRefer.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#listSelectContent"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<search:input attrPath="s.tbCatPropValueReferName" attrNameKey="tbCatPropValueRefer.tbCatPropValueReferName" datatype="String"
							operator="LIKE" classes="form-inputbox" />
						<search:orderby showOrderDirection="true">
							<option value="tbCatPropValueReferId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="tbCatPropValueReferListForm" method="post" action="${ctxPath}/supplier/tbCatPropValueRefer.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/supplier/tbCatPropValueRefer.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${tbCatPropValueReferList}" cellspacing="0" cellpadding="0" uid="tbCatPropValueReferItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${tbCatPropValueReferItem.tbCatPropValueReferId}" class="checkbox" title="${tbCatPropValueReferItem.tbCatPropValueReferName}"/>
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="tbCatPropValueReferId" paramProperty="tbCatPropValueReferId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="tbCatPropValueRefer.tbCatPropValueReferName">
				${tbCatPropValueReferItem.tbCatPropValueReferName}
			</display:column>
		    <display:column property="accessoryId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="tbCatPropValueRefer.accessoryId"/>
		    <display:column property="tbCatPropReferId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="tbCatPropValueRefer.tbCatPropReferId"/>
		    <display:column property="tbCatPropId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="tbCatPropValueRefer.tbCatPropId"/>
		    <display:column property="tbCatPropValueId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="tbCatPropValueRefer.tbCatPropValueId"/>
		    <display:column property="tbCatPropValueName" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="tbCatPropValueRefer.tbCatPropValueName"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("tbCatPropValueReferItem");
</script>