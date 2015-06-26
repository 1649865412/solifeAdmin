<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="menu.viewOrderPickShipping" />
<fmt:message key="order.shipment.action.complete" var="completeShippingTitle" />
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/inventory/orderPickListExtend.html"  onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
				<ul>
					<li>
						<a href="#listSelectContent"><fmt:message key="yourposition.search" /> </a>
					</li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:input attrPath="u.username" attrNameKey="orderPickList.createBy" datatype="String" operator="LIKE" classes="form-inputbox" onenter="true"/>
						<div class="title"><fmt:message key="orderPickList.isActive"/></div>
						<div>
							<select name="COL@s.isActive@Short@EQ">
								<option value=""></option>
								<option value="1" <c:if test="${sc.param['COL@s.isActive@Short@EQ'] eq 1}">selected="true"</c:if>><fmt:message key="orderPickList.isActive.yes"/></option>
								<option value="0" <c:if test="${sc.param['COL@s.isActive@Short@EQ'] eq 0}">selected="true"</c:if>><fmt:message key="orderPickList.isActive.no"/></option>
							</select>
							<div class="blank10"></div>
						</div>
						<div class="title"><fmt:message key="orderPickList.createTime"/></div>
						<div>
							<div style="height:28px">
								<fmt:message key="common.message.from"/>
								<input type="text" id="startTime" name="COL@s.createTime@Date_Begin@GTE" readonly="true" 
								value="${sc.param['COL@s.createTime@Date_Begin@GTE']}"  style="width:100px"">
								<app:ui_datePicker outPut="startTime" />
							</div>
							<div style="height:28px">
								<fmt:message key="common.message.to"/>
								<input type="text" id="endTime" name="COL@s.createTime@Date_End@LTE" readonly="true" 
								value="${sc.param['COL@s.createTime@Date_End@LTE']}"  style="width:100px">
								<app:ui_datePicker outPut="endTime" />
							</div>
						</div>
						<search:orderby showOrderDirection="true">
							<option value="orderPickListId">
								<fmt:message key="search.order.by.default" />
							</option>
							<option value="createTime">
								<fmt:message key="orderPickList.createTime" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</div>
	</form>
</app:ui_leftMenu>
<fmt:message key="shipment.status.shipped" var="MSG_SHIPPED"/>
<form class="mainForm" id="orderPickListForm" name="orderPickListForm" method="post" action="${ctxPath}/inventory/orderPickListExtend.html">
	<!--listing box start-->
	
		<div><c:if test="${not empty orderPickList}"><fmt:message key="orderPickList.createTime"/><fmt:formatDate value="${orderPickList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/><br/><br/></c:if></div>
		<display:table name="${orderPickListList}" cellspacing="0" cellpadding="0" uid="orderPickList"
			class="table-list" export="false" requestURI="">
			<display:column sortable="false" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderPickList.createTime">
				<a href="${ctxPath}/inventory/orderPickList.html?orderPickListId=${orderPickList.orderPickListId}"><fmt:formatDate value="${orderPickList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></a>
			</display:column>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderPickList.createBy">
        		<app:userName userId="${orderPickList.createBy}" noFormat="true"></app:userName>   		
        	</display:column>
		    <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderPickList.orderShipments.num">
        		${fn:length(orderPickList.orderShipments)}
        	</display:column>
        	 <display:column sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderPickList.isActive">
        		<c:choose>
        			<c:when test="${orderPickList.isActive==1}">
        				<fmt:message key="orderPickList.isActive.yes"/>
        			</c:when>
        			<c:otherwise>
        				<fmt:message key="orderPickList.isActive.no"/>
        			</c:otherwise>
        		</c:choose>
        	</display:column>
		</display:table>
		<div class="blank10"></div>
		<%@ include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<c:set var="ui_tabs" value="true" scope="request"/>