<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="content" tagdir="/WEB-INF/tags/content"%>
<app:pageHeading pageHeadingKey="adPositionTypeList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<!--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />-->
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
	    <form method="post" action="${ctxPath}/content/adPositionType.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#listSelectContent"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<search:input attrPath="s.positionName" attrNameKey="adPositionType.positionName" datatype="String"
							operator="LIKE" classes="form-inputbox" />
						<div class="title"><fmt:message key="adPositionType.store"/></div>
						<div>
						<select name="COL@s.store.storeId@Integer@EQ" id="storeId" style="width:150px">
						<option value=""></option>
						<c:forEach items="${appConfig.storeMap}" var="storeItem">
						<option value="${storeItem.value.storeId}" 
							<c:if test="${param['COL@s.store.storeId@Integer@EQ'] == storeItem.value.storeId}">selected="selected" </c:if>>${storeItem.value.name}</option>
						</c:forEach>
						</select>
						</div>
						<search:orderby showOrderDirection="true">
							<option value="adPositionTypeId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="adPositionTypeListForm" method="post" action="${ctxPath}/content/adPositionType.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/content/adPositionType.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${adPositionTypeList}" cellspacing="0" cellpadding="0" uid="adPositionTypeItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${adPositionTypeItem.adPositionTypeId}" class="checkbox" title="${adPositionTypeItem.adPositionTypeName}"/>
			</display:column>
			<display:column property="positionName" sortable="false" url="${editURLPath}" paramId="adPositionTypeId" paramProperty="adPositionTypeId" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="adPositionType.positionName" />
			<display:column property="store.name" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="adPositionType.store"/>
			<display:column property="description" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="adPositionType.description"/>	
			<display:column value="${adPositionTypeItem.height} * ${adPositionTypeItem.width }" sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="adPositionType.size" />
			<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="adPositionType.displayType">
				<content:adDisplayType viewType="0" defaultValue="${adPositionTypeItem.displayType}" areaName="no" />
			</display:column>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("adPositionTypeItem");
</script>