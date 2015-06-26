<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="regionList.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
	    <form method="post" action="${ctxPath}/system/region.html" onsubmit="return false;" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#listSelectContent"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<search:input attrPath="s.regionName" attrNameKey="region.regionName" datatype="String"
							operator="LIKE" classes="form-inputbox" />
						<search:orderby showOrderDirection="true">
							<option value="regionId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="regionListForm" method="post" action="${ctxPath}/system/region.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/system/region.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${regionList}" cellspacing="0" cellpadding="0" uid="regionItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${regionItem.regionId}" class="checkbox" title="${regionItem.regionName}"/>
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="regionId" paramProperty="regionId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="region.regionName">
				${regionItem.regionName}
			</display:column>
		    <display:column property="regionCode" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="region.regionCode"/>
        	<display:column style="width: 50%" titleKey="region.Items" headerClass="data-table-title" media="html">
	        	<c:forEach var="subItem" items="${regionItem.regionItems}" varStatus="varStatus">
	        		${subItem.itemName}<c:if test="${!varStatus.last}">,</c:if>
	        	</c:forEach>
	        </display:column>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("regionItem");
</script>