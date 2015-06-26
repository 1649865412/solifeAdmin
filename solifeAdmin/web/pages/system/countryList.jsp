<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<c:set var="ui_jTip" value="true" scope="request"/>

<title><fmt:message key="countryList.title" /></title>
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" onclick="location.href='${ctxPath}/system/index.html';" />
</content>
<app:ui_leftMenu>
<div class="sidebar-left">
		<form method="post" action="${ctxPath}/system/country.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs" />
			<div class="tab" id="left_menu_tabs">
				<ul>
					<li>
						<a href="#listSelectContent"><fmt:message key="yourposition.search" /> </a>
					</li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:input attrPath="s.regionName" attrNameKey="country.countryName" 
						datatype="String" operator="LIKE" classes="form-inputbox" onenter="true"/>
						<search:input attrPath="s.regionCode" attrNameKey="country.countryCode" 
						datatype="String" operator="LIKE" classes="form-inputbox" onenter="true"/>												
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>

<form class="mainForm" name="regionListForm" method="post" action="${ctxPath}/system/country.html">
<input type="hidden" name="doAction" value="" />

	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/system/contryEdit.html?from=list"
		scope="page" />
	<c:set var="checkAll">
	<input type="checkbox" name="allbox" onclick="checkAll(this.form)"
		style="margin: 0 0 0 0px" />
	</c:set>

	<display:table name="${countryList}" cellspacing="0" cellpadding="0" uid="regionItem" class="table-list" export="false" requestURI="">
		<display:column  decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
			titleKey="country.countryName">
			<input type="hidden" name="multiIds" value="<c:out value="${regionItem.regionId}"/>" />
			${regionItem.regionName}
		</display:column>
		<display:column property="regionCode" sortable="false"  style="width:10%" 
			decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
			titleKey="country.countryCode" />

		<display:column  titleKey="country.state" media="html" style="width:50%" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
			<c:forEach items="${regionItem.subRegion}" var="subRegion"  varStatus="subRegionStatus">
				<a href="javascript:;" onclick="dlgShowCity_show({stateId:${subRegion.regionId},title:'${fn:trim(regionItem.regionName)}-->${fn:trim(subRegion.regionName)}:'})">${fn:trim(subRegion.regionName)}</a>
			</c:forEach>
		</display:column>
		<display:column  titleKey="region.status" media="html" style="width:10%" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator">
			<input type="checkbox" name="status_${regionItem.regionId}"  <c:if test="${regionItem.status==1}">checked</c:if>  value="1"/>
			<c:choose>
			<c:when test="${regionItem.status==1}"><font color="green"><fmt:message key="status.active"/></font></c:when>
			<c:otherwise><font color="red"><fmt:message key="status.inactive"/></font></c:otherwise>
		</c:choose>
		</display:column>
		
		<display:setProperty name="paging.banner.item_name" value="region" />
		<display:setProperty name="paging.banner.items_name" value="regions" />
	</display:table>
	<%@include file="/common/pagingOnlyNew.jsp"%>
</form>

<fmt:message key="button.cancel" var="buttonCancel"/>
<fmt:message key="country.cityList" var="cityTitle"/>
<app:ui_dialog id="ShowCity" width="400" height="200" title="${cityTitle}" url="${ctxPath}/system/city/dialog.html" buttons="'${buttonCancel}':dlgShowCity_close"/>



