<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="storeList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
	    <form method="post" action="${ctxPath}/system/store.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#listSelectContent"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<search:input attrPath="s.name" attrNameKey="store.name" datatype="String"
							operator="LIKE" classes="form-inputbox" />
						<search:orderby showOrderDirection="true">
							<option value="storeId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="storeListForm" method="post" action="${ctxPath}/system/store.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/system/store.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${storeList}" cellspacing="0" cellpadding="0" uid="storeItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${storeItem.storeId}" class="checkbox" title="${storeItem.storeName}"/>
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="storeId" paramProperty="storeId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="store.name">
				${storeItem.name}
			</display:column>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="store.catalogId">
        		${storeItem.catalog.name}
        	</display:column>		    
		    <display:column property="code" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="store.code"/>
		    <display:column property="siteUrl" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="store.siteUrl"/>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="store.status">
        		<fmt:message key="store.status.${storeItem.status}"/>
        	</display:column>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("storeItem");
</script>