<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="accessoryList.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<!--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />-->
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/catalog/accessory.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="s.accessoryName" attrNameKey="accessory.accessoryName" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:orderby showOrderDirection="true">
						<option value="accessoryId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="accessoryListForm" method="post" action="${ctxPath}/catalog/accessory.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/catalog/accessory.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${accessoryList}" cellspacing="0" cellpadding="0" uid="accessoryItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${accessoryItem.accessoryId}" class="checkbox" title="${accessoryItem.accessoryName}"/>
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="accessoryId" paramProperty="accessoryId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="accessory.accessoryName">
				${accessoryItem.accessoryName}
			</display:column>
		    <display:column property="accessoryGroupId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="accessory.accessoryGroupId"/>
		    <display:column property="accessoryName" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="accessory.accessoryName"/>
		    <display:column property="price" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="accessory.price"/>
		    <display:column property="sortOrder" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="accessory.sortOrder"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>

</form>
<script type="text/javascript">
highlightTableRows("accessoryItem");
</script>