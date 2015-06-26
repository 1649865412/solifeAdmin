<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="tbCategoryReferList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<!--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />-->
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
	    <form method="post" action="${ctxPath}/supplier/tbCategoryRefer.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#listSelectContent"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<search:input attrPath="s.tbCategoryName" attrNameKey="tbCategoryRefer.tbCategoryName" datatype="String"
							operator="LIKE" classes="form-inputbox" />
						<search:input attrPath="s.category.categoryName" attrNameKey="tbCategoryRefer.categoryId" datatype="String"
							operator="LIKE" classes="form-inputbox" />
						<search:orderby showOrderDirection="true">
							<option value="tbCategoryReferId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="tbCategoryReferListForm" method="post" action="${ctxPath}/supplier/tbCategoryRefer.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/supplier/tbCategoryRefer.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${tbCategoryReferList}" cellspacing="0" cellpadding="0" uid="tbCategoryReferItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${tbCategoryReferItem.tbCategoryReferId}" class="checkbox" title="${tbCategoryReferItem.tbCategoryReferName}"/>
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="tbCategoryReferId" paramProperty="tbCategoryReferId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="tbCategoryRefer.tbCategoryName">
				${tbCategoryReferItem.tbCategoryName}
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="tbCategoryReferId" paramProperty="tbCategoryReferId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="tbCategoryRefer.tbCategoryId">
				${tbCategoryReferItem.tbCategoryId}
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="tbCategoryReferId" paramProperty="tbCategoryReferId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="tbCategoryRefer.categoryId">
				${tbCategoryReferItem.category.categoryName}
			</display:column>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("tbCategoryReferItem");
</script>