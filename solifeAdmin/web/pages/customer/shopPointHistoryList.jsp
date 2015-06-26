<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="shopPointHistoryList.heading" />
<c:if test="${not empty param['tabIndex']}">
	<%@include file="./include/customerTabHeader.jspf" %>
</c:if>
<form class="mainForm" name="shopPointHistoryListForm" method="post" action="${ctxPath}/customer/shopPointHistory.html">
	<table class="table-content">
		<tr>
			<th class="title" colspan="2">
			<fmt:message key="shopPointHistory.customer.title"><fmt:param value="${shopPoint.customer.username }"/></fmt:message>
			</th> 
		</tr>
		<tr>
			<td class="FieldLabel"><b><fmt:message key="shopPoint.total"/></b></td>
			<td>${shopPoint.total }</td>
           </tr>
		<tr>
			<td class="FieldLabel"><b><fmt:message key="shopPoint.updateTime"/></b></td>
			<td>${shopPoint.updateTime }</td>
		</tr>
		<tr>
			<td class="FieldLabel"><b><fmt:message key="shopPoint.usedTotal"/></b></td>
			<td>${shopPoint.usedTotal }</td>
           </tr>
		<tr>
			<td class="FieldLabel"><b><fmt:message key="shopPoint.gainedTotal"/></b></td>
			<td>${shopPoint.gainedTotal }</td>
		</tr>
           <tr>
           	<td colspan="2">
				&nbsp;<cartmatic:iconBtn icon="plus" textKey="common.link.add" onclick="dlgAddShopPoint_show();" />
				<display:table name="${shopPointHistoryList}" cellspacing="0" cellpadding="0"
					uid="shopPointHistoryItem"
					export="false" requestURI="" > 
					<display:column  title="&nbsp;">
						${shopPointHistoryItem_rowNum }
					</display:column>   
					<display:column  sortable="false" headerClass="data-table-title"
				        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="shopPointHistory.shopPointType">
				        	<fmt:message key="shopPoint.shopPointType_${shopPointHistoryItem.shopPointType}"/>
				    </display:column>
					<display:column property="description" sortable="false" headerClass="data-table-title" maxLength="64"
				        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="shopPointHistory.description"/>
				    <display:column property="amount" sortable="false" headerClass="data-table-title"
				        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="shopPointHistory.amount"/>
				    <display:column property="createTime" sortable="false" headerClass="data-table-title"
				        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="shopPointHistory.createTime"/>
				    <display:setProperty name="paging.banner.item_name" value="shopPointHistory"/>
				    <display:setProperty name="paging.banner.items_name" value="shopPointHistorys"/>
				    <display:setProperty name="export.csv.filename" value="shopPointHistorys.csv"/>
					<display:setProperty name="export.excel.filename" value="shopPointHistorys.xls"/>
					<display:setProperty name="export.pdf.filename" value="shopPointHistorys.pdf"/>
					<display:setProperty name="export.xml.filename" value="shopPointHistorys.xml"/>
				</display:table>
	        </td>
           </tr>
	</table>
	<%@include file="/common/pagingOnlyNew.jsp"%>
</form>