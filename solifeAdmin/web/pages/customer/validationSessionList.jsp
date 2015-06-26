<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="validationSessionList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<!--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />-->
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/customer/validationSession.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="s.validationSessionName" attrNameKey="validationSession.validationSessionName" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:orderby showOrderDirection="true">
						<option value="validationSessionId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="validationSessionListForm" method="post" action="${ctxPath}/customer/validationSession.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/customer/validationSession.html?doAction=edit&from=list" scope="page" />

		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${validationSessionList}" cellspacing="0" cellpadding="0" uid="validationSessionItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${validationSessionItem.validationSessionId}" class="checkbox" title="${validationSessionItem.validationSessionName}"/>
			</display:column>
			<display:column sortable="false" url="${editURLPath}" paramId="validationSessionId" paramProperty="validationSessionId"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="validationSession.validationSessionName">
				${validationSessionItem.validationSessionName}
			</display:column>
		    <display:column property="url" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="validationSession.url"/>
		    <display:column property="expiredDate" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="validationSession.expiredDate"/>
		    <display:column property="email" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="validationSession.email"/>
		    <display:column property="vsType" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="validationSession.vsType"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("validationSessionItem");
</script>