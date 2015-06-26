<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="paymentHistoryList.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
	    <form method="post" action="${ctxPath}/system/paymentHistory.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#listSelectContent"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<search:input attrPath="s.orderNo" attrNameKey="paymentHistory.orderNo" datatype="String"
							operator="LIKE" classes="form-inputbox" />
						<search:input attrPath="s.remoteIp" attrNameKey="paymentHistory.remoteIp" datatype="String"
							operator="EQ" classes="form-inputbox" />
						<search:orderby showOrderDirection="true">
							<option value="paymentHistoryId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="paymentHistoryListForm" method="post" action="${ctxPath}/system/paymentHistory.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/system/paymentHistory.html?doAction=edit&from=list" scope="page" />
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
		<display:table name="${paymentHistoryList}" cellspacing="0" cellpadding="0"
			uid="paymentHistoryItem" class="table-list" 
			export="false" requestURI="" >
			<display:column style="width: 5%" title="${checkAll}" headerClass="data-table-title" media="html">
			    <input type="checkbox" name="paymentHistoryId" value="${paymentHistoryItem.paymentHistoryId}" class="checkbox" />
			</display:column>
		    <display:column  sortable="false" headerClass="data-table-title" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="paymentHistory.orderNo">
		        <a href="${ctxPath}/order/salesOrder.html?COL@so.orderNo@String@LIKE=${paymentHistoryItem.orderNo}&btnSearch=true">${paymentHistoryItem.orderNo}</a>
		    </display:column>
		    <display:column  sortable="false" headerClass="data-table-title"
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="paymentHistory.flag">
		        <fmt:message key="paymentHistory.flag_${paymentHistoryItem.flag}"/>
		        <c:if test="${paymentHistoryItem.isBrowsed == 1}"><fmt:message key="paymentHistory.repeated"/></c:if>
		    </display:column>
		    <display:column property="remoteIp" sortable="false" headerClass="data-table-title"
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="paymentHistory.remoteIp"/>
		    <display:column sortable="false" headerClass="data-table-title"
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="paymentHistory.errorMessage">
		        ${paymentHistoryItem.errorMessage}
		        <system:paymentMethod paymentMethodId="${paymentHistoryItem.paymentMethodId}" var="paymentMethod"></system:paymentMethod>
		        <c:if test="${not empty paymentMethod}">&nbsp;&nbsp;&nbsp;&nbsp;[${paymentMethod.paymentMethodName}]</c:if>
		   	</display:column>
		    <display:column property="errorCode" sortable="false" headerClass="data-table-title"
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="paymentHistory.errorCode"/>
		    <display:column sortable="false" headerClass="data-table-title"
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="paymentHistory.errorMessage">
		       <c:if test="${not empty paymentHistoryItem.receiveData}">
				<cartmatic:ui_tip id="receiveDataTip_${paymentHistoryItem.id}" type="">${paymentHistoryItem.receiveData}</cartmatic:ui_tip>
				</c:if>
		   </display:column>
		        <display:column property="amount" sortable="false" headerClass="data-table-title"
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="paymentHistory.amount"/>
		    <display:column sortable="false" headerClass="data-table-title"
		        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="paymentHistory.createTime">
		        <fmt:formatDate value="${paymentHistoryItem.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		    </display:column>
		    <display:setProperty name="paging.banner.item_name" value="paymentHistory"/>
		    <display:setProperty name="paging.banner.items_name" value="paymentHistorys"/>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
highlightTableRows("paymentHistoryItem");
</script>