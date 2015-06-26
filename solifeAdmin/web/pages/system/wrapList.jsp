<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<app:pageHeading pageHeadingKey="wrapList.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
	    <form method="post" action="${ctxPath}/system/wrap.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs" />
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li>
						<a href="#listSelectContent"><fmt:message key="yourposition.search"/></a>
					</li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<search:input attrPath="s.wrapName" attrNameKey="wrap.wrapName" datatype="String" operator="LIKE" classes="form-inputbox" />
						<search:orderby showOrderDirection="true">
							<option value="wrapId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="wrapListForm" method="post" action="${ctxPath}/system/wrap.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/system/wrap.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${wrapList}" cellspacing="0" cellpadding="0" uid="wrapItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${wrapItem.wrapId}" class="checkbox" title="${wrapItem.wrapName}"/>
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="wrapId" paramProperty="wrapId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="wrap.wrapName">
				${wrapItem.wrapName}
			</display:column>
		    <display:column property="defaultPrice" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="wrap.defaultPrice"/>
		    <display:column property="wrapName" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="wrap.wrapName"/>
		    <display:column property="description" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="wrap.description"/>
		    <display:column property="imageSrc" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="wrap.imageSrc"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("wrapItem");
</script>