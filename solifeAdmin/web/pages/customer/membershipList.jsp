<%@ include file="/common/taglibs.jsp"%>
<%@taglib prefix="customer" tagdir="/WEB-INF/tags/customer" %>
<app:pageHeading pageHeadingKey="membershipList.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/customer/membership.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="s.membershipName" attrNameKey="membership.membershipName" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:orderby showOrderDirection="true">
						<option value="membershipId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="membershipListForm" method="post" action="${ctxPath}/customer/membership.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/customer/membership.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${membershipList}" cellspacing="0" cellpadding="0" uid="membershipItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<c:if test="${membershipItem.membershipLevel>1}">
					<input type="checkbox" name="multiIds" value="${membershipItem.membershipId}" class="checkbox" title="${membershipItem.membershipName}"/>
				</c:if>
			</display:column>
			<display:column sortable="false" headerClass="data-table-title" 
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="membership.membershipName">
				<c:choose>
					<c:when test="${membershipItem.membershipLevel<=1 }">
						 ${membershipItem.membershipName}
					</c:when>
					<c:otherwise>
						<a href="${ctxPath}${editURLPath}&membershipId=${membershipItem.membershipId}">${membershipItem.membershipName}</a>
					</c:otherwise>
				</c:choose>
			</display:column>
		    <display:column property="membershipDetail"  maxLength="64" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="membership.membershipDetail"/>
		    <display:column property="membershipLevel" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="membership.membershipLevel"/>
		    <display:column property="upgradeShopPoint" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="membership.upgradeShopPoint"/>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="membership.status">
        		<fmt:message key="membership.status${membershipItem.status}"/>
        	</display:column>        	
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("membershipItem");
</script>