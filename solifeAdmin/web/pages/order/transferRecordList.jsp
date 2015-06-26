<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="transferRecordList.heading" />
<%--
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<!--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />-->
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
--%>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/order/transferRecord.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<search:filter />
					<search:input attrPath="s.mtcnNo" attrNameKey="transferRecord.mtcnNo" datatype="String"
						operator="EQ" classes="form-inputbox" />
					<search:input attrPath="s.firstname" attrNameKey="transferRecord.firstname" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:input attrPath="s.middlename" attrNameKey="transferRecord.middlename" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:input attrPath="s.lastname" attrNameKey="transferRecord.lastname" datatype="String"
						operator="LIKE" classes="form-inputbox" />
					<search:input attrPath="s.phone" attrNameKey="transferRecord.phone" datatype="String"
						operator="EQ" classes="form-inputbox" />
					<search:orderby showOrderDirection="true">
						<option value="transferRecordId">
							<fmt:message key="search.order.by.default" />
						</option>
					</search:orderby>
				</search:searchBox>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="transferRecordListForm" method="post" action="${ctxPath}/order/transferRecord.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/order/transferRecord.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${transferRecordList}" cellspacing="0" cellpadding="0" uid="transferRecordItem"
			class="table-list" style="table-layout:fixed;" export="false" requestURI="">
		    <display:column property="orderNo" sortable="false" headerClass="w100" 
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="transferRecord.orderNo" url="${editURLPath}" paramId="transferRecordId" paramProperty="transferRecordId"/>
		    <display:column sortable="false" headerClass="w150"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="transferRecord.type"  >
        		<fmt:message key="transferRecord.type_${transferRecordItem.type}" />
        	</display:column>
        	<display:column sortable="false" headerClass="wauto"  
        	    decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="transferRecord.name">
        		${transferRecordItem.firstname}${emptySpace}${transferRecordItem.middlename}${emptySpace}${transferRecordItem.lastname}
        	</display:column>
		    <display:column property="amount" sortable="false" headerClass="w100" 
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="transferRecord.amount"/>
		    <display:column property="country" sortable="false" headerClass="w100" 
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="transferRecord.country"/>
		    <display:column property="phone" sortable="false" headerClass="w100"  
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="transferRecord.phone"/>
		    <display:column sortable="false" headerClass="w100"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="transferRecord.status">
        		<fmt:message key="transferRecord.status_${transferRecordItem.status}" />
        	</display:column>
		    <display:column sortable="false" headerClass="w100"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="transferRecord.updateBy">
				<app:userName userId="${transferRecordItem.updateBy}" noFormat="true"></app:userName>
        	</display:column>
		    <display:column sortable="false" headerClass="w200"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="transferRecord.updateTime">
        			<fmt:formatDate value="${transferRecordItem.updateTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
        	</display:column>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<script type="text/javascript">
highlightTableRows("transferRecordItem");
</script>