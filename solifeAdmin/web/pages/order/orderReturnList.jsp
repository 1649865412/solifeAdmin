<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="orderReturnList.heading" />
<fmt:message key="orderReturnSku.action.receiveReturn" var="MSG_RECEIVE_RETURN"/>
<content tag="buttons">
</content>
<app:ui_leftMenu>
	<form method="post" action="${ctxPath}/inventory/orderReturn.html" onsubmit="return false;">
	    <div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs"/>
		    <div class="tab" id="left_menu_tabs">
			    <ul>
					<li><a href="#glSearchBar"><fmt:message key="yourposition.search"/></a></li>
				</ul>
				<search:searchBox>
					<div class="title"><fmt:message key="salesOrder.store"/></div>
					<div>
					<select name="COL@s.salesOrder.store.storeId@Integer@EQ" id="storeId" style="width:150px">
						<option value=""></option>
						<c:forEach items="${appConfig.storeMap}" var="storeItem">
						<option value="${storeItem.value.storeId}" <c:if test="${sc.param['COL@s.salesOrder.store.storeId@Integer@EQ'] == storeItem.value.storeId}">selected="selected"</c:if> >${storeItem.value.name}</option>
						</c:forEach>
					</select>
					</div>
					<search:input attrPath="s.rmaNo" attrNameKey="orderReturn.rmaNo" datatype="String"
						operator="EQL" classes="form-inputbox" />
					<search:input attrPath="s.salesOrder.orderNo" attrNameKey="salesOrder.orderNo" datatype="String"
						operator="LIKE" classes="form-inputbox"/>
					<search:input attrPath="concat(s.salesOrder.customerFirstname,s.salesOrder.customerLastname)" attrNameKey="salesOrder.customerName" datatype="String"
						operator="LIKE" classes="form-inputbox"/>
				</search:searchBox>
			</div>
			<br/>
			<br/>
		</div>
	</form>
</app:ui_leftMenu>
<form class="mainForm" name="orderReturnListForm" method="post" action="${ctxPath}/inventory/orderReturn.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/inventory/orderReturn.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" style="margin: 0 0 0 0px" /></c:set>
		<display:table name="${orderReturnList}" cellspacing="0" cellpadding="0" uid="orderReturn"
			class="table-list" export="false" requestURI="">
		    <display:column style="width:8%" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderReturn.rmaNo">
        		<a href="javascript:fnOpenOrderReturn('${orderReturn.orderReturnId}')">${orderReturn.rmaNo}</a>
        	</display:column>
        	<display:column style="width:10%" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="salesOrder.orderNo">
        		<a onclick="fnNewWindow('${ctxPath}/order/window.html?doAction=edit&salesOrderId=${orderReturn.salesOrder.salesOrderId}',650,1300);" href="javascript:void(false);">${orderReturn.salesOrder.orderNo}</a>
        	</display:column>
        	<display:column style="width:6%" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderReturn.returnType">
        		<c:choose>
					<c:when test="${orderReturn.returnType eq 0}"><fmt:message key="orderReturn.return"/></c:when>
					<c:otherwise><fmt:message key="orderReturn.exchange"/></c:otherwise>
				</c:choose>
        	</display:column>
        	<display:column style="width:12%" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="common.message.createBy">
        		<app:userName userId="${orderReturn.createBy}"/>	
        	</display:column>
		    <display:column style="width:12%" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderReturn.receivedBy">
        		<app:userName userId="${orderReturn.receivedBy}"/>
        	</display:column>
		    <display:column style="width:12%" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="orderReturn.status">
        		<c:choose>
					<c:when test="${orderReturn.status eq 10}"><span style="color:green"><fmt:message key="return.status.awaiting.return"/></span></c:when>
					<c:when test="${orderReturn.status eq 20}"><span style="color:green"><fmt:message key="return.status.awaiting.completion"/></span></c:when>
					<c:when test="${orderReturn.status eq 30}"><span style="color:green"><fmt:message key="return.status.complete"/></span></c:when>
					<c:when test="${orderReturn.status eq 40}"><span style="color:red"><fmt:message key="return.status.cancelled"/></span></c:when>
				</c:choose>
        	</display:column>
        	<display:column style="width:10%" property="createTime" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="common.message.createTime"/>
        	<display:column  style="text-align:center" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="common.action">
        		<c:if test="${orderReturn.status eq 10}"><a href="javascript:fnOpenOrderReturn('${orderReturn.orderReturnId}')">${MSG_RECEIVE_RETURN}</a></c:if>
        	</display:column>
		</display:table>
		<%@include file="/common/pagingOnlyNew.jsp"%>
	
</form>
<fmt:message key="common.message.complete" var="buttonComplete"/>
<fmt:message key="common.message.cancel" var="buttonCancel"/>
<app:ui_dialog id="ReceiveReturnDlg" title="${MSG_RECEIVE_RETURN}" width="650" height="600" buttons="'${buttonComplete}':fnCompleteReceiveReturn, '${buttonCancel}':dlgReceiveReturnDlg_close">
</app:ui_dialog>

<script type="text/javascript">
highlightTableRows("orderReturn");

function fnOpenOrderReturn(orderReturnId){
	dlgReceiveReturnDlg_show();
	fillDivWithPage("divDlgReceiveReturnDlg","${ctxPath}/order/return/dialog.html?doAction=receiveReturn&orderReturnId="+orderReturnId, null,"POST");
}

function fnCompleteReceiveReturn(){
	if(!validateForm( document.getElementById("receiveReturnForm") )){
		alert(__vaMsg.notPass);
		return;
	}
	var parameters = $j("#receiveReturnForm").serialize();
   	$j.post('${ctxPath}/inventory/orderReturn.html?doAction=completeReceiveReturn', parameters, fnCompleteReceiveReturnCallbackHandler,"json");
}

function fnCompleteReceiveReturnCallbackHandler(result){
	if(result.status==1){
		dlgReceiveReturnDlg_close();
		window.location.href="${ctxPath}/inventory/orderReturn.html";
	}else{
		sysMsg(result.msg,true);
	}
}
</script>