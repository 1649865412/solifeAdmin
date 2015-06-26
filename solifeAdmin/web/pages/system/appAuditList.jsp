<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="appAuditList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
	    <form method="post" action="${ctxPath}/system/appAudit.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#listSelectContent"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<div class="title"><fmt:message key="appAudit.procUserId"/></div>
						<div>
							<input type="text" class="Field200" value="${param.userName}" name="userName" id="userName" >
						</div>
						<search:input attrPath="s.actionName" attrNameKey="appAudit.actionName" datatype="String" operator="EQ" classes="form-inputbox" />
						<search:input attrPath="s.procObj" attrNameKey="appAudit.procObj" datatype="String" operator="LIKE" classes="form-inputbox" />
						<div >
							&nbsp;<fmt:message key="appAudit.procTime"/>
							<div style="height:28px">
							<fmt:message key="common.message.from"/>
							<input type="text" id="procTimeStartTime" name="COL@s.procTime@Date_Begin@GTE" readonly="true" style="width:100px"  value="${sc==null ? requestScope['COL@s.procTime@Date_Begin@GTE'] : sc.param['COL@s.procTime@Date_Begin@GTE']}">
							<app:ui_datePicker outPut="procTimeStartTime" />
							</div>
							<div style="height:28px">
							<fmt:message key="common.message.to"/>
							<input type="text" id="procTimeEndTime" name="COL@s.procTime@Date_End@LTE" readonly="true" style="width:100px" value="${sc==null ? requestScope['COL@s.procTime@Date_End@LTE'] : sc.param['COL@s.procTime@Date_End@LTE']}">
							<app:ui_datePicker outPut="procTimeEndTime" />
							</div>
						</div>
						<search:orderby showOrderDirection="true">
							<option value="appAuditId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="appAuditListForm" method="post" action="${ctxPath}/system/appAudit.html">
	<!--listing box start-->
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${appAuditList}" cellspacing="0" cellpadding="0" uid="appAuditItem"
			class="table-list" export="false" requestURI="">
			<display:column sortable="false" headerClass="data-table-title" titleKey="appAudit.procUserId">
				<app:userName userId="${appAuditItem.procUserId}" target="_blank"/>
		    </display:column>
		    <display:column property="actionName" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="appAudit.actionName"/>
		    <display:column property="procObj" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="appAudit.procObj"/>
		    <display:column property="procResult" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="appAudit.procResult"/>
		    <display:column property="procTime" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="appAudit.procTime"/>
        	<display:column property="requestUrl" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="appAudit.requestUrl"/>
        		
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("appAuditItem");
</script>