<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="textMessageList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="create" inputType="button" onclick="return fnDoAdd(this);" />
	<!--<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoMultiSave(this);" />-->
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
	    <form method="post" action="${ctxPath}/customer/textMessage.html">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#listSelectContent"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<search:input attrPath="s.email" attrNameKey="textMessage.email" datatype="String" operator="LIKE" classes="form-inputbox" />
						<search:input attrPath="s.phone" attrNameKey="textMessage.phone" datatype="String" operator="LIKE" classes="form-inputbox" />
						<search:orderby showOrderDirection="true">
							<option value="textMessageId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="textMessageListForm" method="post" action="${ctxPath}/customer/textMessage.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/customer/textMessage.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${textMessageList}" cellspacing="0" cellpadding="0" uid="textMessageItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${textMessageItem.textMessageId}" class="checkbox" title="${textMessageItem.textMessageName}"/>
			</display:column>
			<display:column property="email"  url="${editURLPath}" paramId="textMessageId" paramProperty="textMessageId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="textMessage.email"/>
		    <display:column property="phone"  url="${editURLPath}" paramId="textMessageId" paramProperty="textMessageId" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="textMessage.phone"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("textMessageItem");
</script>