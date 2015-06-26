<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="carrierList.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<!--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />-->
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
	    <form method="post" action="${ctxPath}/system/carrier.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#listSelectContent"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<search:input attrPath="s.carrierName" attrNameKey="carrier.carrierName" datatype="String"
							operator="LIKE" classes="form-inputbox" />
						<search:orderby showOrderDirection="true">
							<option value="carrierId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="carrierListForm" method="post" action="${ctxPath}/system/carrier.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/system/carrier.html?doAction=edit&from=list" scope="page" />
	<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
	<display:table name="${carrierList}" cellspacing="0" cellpadding="0" uid="carrierItem" class="table-list" export="false" requestURI="">
		<display:column title="${checkAll}"  media="html">
		    <div style="padding-top:8px;"><input  type="checkbox" name="multiIds" value="<c:out value="${carrierItem.carrierId}"/>" title="${carrierItem.carrierName}"/></div>
		</display:column>
		<display:column sortable="false" url="${editURLPath}" paramId="carrierId" paramProperty="carrierId"
			decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="carrier.carrierName">
			${carrierItem.carrierName}
		</display:column>
		<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="carrier.transportComIcon">
			<cartmatic:img url="${carrierItem.logo}" mediaType="other" id="image" height="120" width="120"></cartmatic:img>
		</display:column>
		<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="carrier.companyAddress">
			${carrierItem.carrierAddress}<br>${carrierItem.carrierAddress2}
		</display:column>
		<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="carrier.linkman">
			${carrierItem.linkman}
		</display:column>
		<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="carrier.telephone">
			${carrierItem.telephone}
		</display:column>
		<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="carrier.status">
			<c:if test="${carrierItem.status==1}" ><fmt:message key="status.active"/></c:if>
			<c:if test="${carrierItem.status==2}" ><fmt:message key="status.inactive"/></c:if>
		</display:column>
	</display:table>
	<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("carrierItem");
</script>