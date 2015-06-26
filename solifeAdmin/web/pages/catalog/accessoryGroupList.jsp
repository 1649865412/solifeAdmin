<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="accessoryGroupList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<!--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />-->
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/catalog/accessoryGroup.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="s.groupName" attrNameKey="accessoryGroup.groupName" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:orderby showOrderDirection="true">
						<option value="accessoryGroupId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="accessoryGroupListForm" method="post" action="${ctxPath}/catalog/accessoryGroup.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/catalog/accessoryGroup.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${accessoryGroupList}" cellspacing="0" cellpadding="0" uid="accessoryGroupItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${accessoryGroupItem.accessoryGroupId}" class="checkbox" title="${accessoryGroupItem.accessoryGroupName}"/>
			</display:column>
		    <display:column property="groupName" url="${editURLPath}" paramId="accessoryGroupId" paramProperty="accessoryGroupId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="accessoryGroup.groupName"/>
        	<display:column property="groupCode" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="accessoryGroup.groupCode"/>
		    <display:column property="groupDesc" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="accessoryGroup.groupDesc"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>

</form>
<script type="text/javascript">
highlightTableRows("accessoryGroupItem");
</script>