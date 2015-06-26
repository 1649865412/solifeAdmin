<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="paymentMethodList.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save"  onclick="batchUpdateForm();" />
</content>
<form class="mainForm" id="paymentMethodListForm" name="paymentMethodListForm" method="post" action="${ctxPath}/system/paymentMethod.html">
<input type="hidden" name="doAction" value=""/>
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/system/paymentMethod.html?doAction=edit&from=list" scope="page" />
	<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox"/></c:set>
	<display:table name="${paymentMethodList}" cellspacing="0" cellpadding="0" uid="paymentMethodItem"
		class="table-list" export="false" requestURI="">
		<display:column style="width: 5%;" title="" media="html">
			<input type="hidden" name="paymentMethodId" value="<c:out value="${paymentMethodItem.paymentMethodId}"/>"/>
			<system:userStatusCheckBox id="status${paymentMethodItem_rowNum}" name="status" value="${paymentMethodItem.status}"/>
		</display:column>
	    <display:column style="width:15%;padding-top:6px;" decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="paymentMethod.paymentMethodName">
			<input type="text" class="Field200" name="paymentMethodName" value="${paymentMethodItem.paymentMethodName}" />
		</display:column>	
	    <display:column titleKey="paymentMethod.paymentMethodDetail"
	        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"  style="width:50%;padding:6px 0;">
	        <textarea cols="90" rows="5" name="paymentMethodDetail" style="font-size:12px;">${paymentMethodItem.paymentMethodDetail}</textarea>
		</display:column>
	    <display:column decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="paymentMethod.protocol" style="width:6%;padding-top:6px;">
			<select name="protocol" class="Field100">
				<option value="HTTP" <c:if test="${paymentMethodItem.protocol=='HTTP'}"><c:out value="selected='selected'"/></c:if> >HTTP</option>
				<option value="HTTPS" <c:if test="${paymentMethodItem.protocol=='HTTPS'}"><c:out value="selected='selected'"/></c:if>>HTTPS</option>
			</select>
		</display:column>
		<display:column 
	        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="paymentMethod.sortOrder" style="width:6%;padding-top:6px;">
			<input type="text" class="Field100" name="sortOrder" value="${paymentMethodItem.sortOrder}" />
		</display:column>
	    <display:column  
	        decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="paymentMethod.paymentMethodType" style="width:6%;">
			<fmt:message key="paymentMethod.paymentMethodType${paymentMethodItem.paymentMethodType}"/>
		</display:column>
		<display:column titleKey="public.management" style="width:10%">
			<a onclick="return confirm('<fmt:message key="paymentMethod.delete.confirm" ><fmt:param>${paymentMethodItem.paymentMethodName}</fmt:param></fmt:message>');" href="<c:out value="paymentMethod.html?paymentMethodId=${paymentMethodItem.paymentMethodId}&doAction=deleteAction"/>"><fmt:message key="button.delete"/></a>
			<c:if test="${paymentMethodItem.paymentMethodType==1}">
				| <a href="<c:url value="paymentMethod.html?doAction=configAction&paymentMethodId=${paymentMethodItem.paymentMethodId}"/>"><fmt:message key="paymentMethod.config"/></a>&nbsp;
			</c:if>
		</display:column>
	</display:table>
	<%@include file="/common/pagingOnlyNew.jsp"%>
</form>
<div class="blank8"></div>
<form name="addPaymentMethodForm" id="addPaymentMethodForm" method="Post">
<table class="table-content" border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
  	<th colspan="2"><fmt:message key="paymentGatewayList.title"/></th>
  </tr>
  <tr>
    <td class="FieldLabel"><fmt:message key="paymentGateway.model"/></td>
    <td>
        <SELECT name="paymentMethodId" class="Field400">
            <OPTION value="-1"></OPTION>
            <c:forEach items="${canAddPayMentMethodList}" var="paymentMethod">
                <option value="${paymentMethod.paymentMethodId}"><c:out value="${paymentMethod.paymentMethodName}"/></option>
            </c:forEach>
        </SELECT>
        <input type="hidden" name="doAction" value="addAction"/>
        <cartmatic:iconBtn icon="plus" textKey="common.link.add" onclick="if(fnAddPaymentMethod($('addPaymentMethodForm'))){$('addPaymentMethodForm').submit();}" />
    </td>
  </tr>
</table>
</form>
<script type="text/javascript">
<!--
highlightTableRows("paymentMethodItem");
//batch update the paymentMethod 
function batchUpdateForm(){
	if(confirm(__FMT.common_message_confirmSave)){
		var paymentMethodIds=document.getElementsByName("paymentMethodId");
		if(paymentMethodIds==null||paymentMethodIds.length==0)return;
		
		var sortOrders=document.getElementsByName("sortOrder");
		var rex=/^-?\d+$/;
		for(var i=0;i<sortOrders.length;i++){
			if(rex.test(sortOrders[i].value)==false){
				alert("<fmt:message key="paymentMethod.error.sortOrder"/>");
				sortOrders[i].focus();
				return;
			}
		}
		var form=document.forms["paymentMethodListForm"];
		form.action="paymentMethod.html";
		
		form.doAction.value="multiSave";
		form.submit();
	}
}

function sumbitGatewayForm(form){
	var paymentMethodSelect=form.paymentMethodId;
	var selIndex=paymentMethodSelect.selectedIndex;
	if(paymentMethodSelect.options[selIndex].value==-1){
		alert("<fmt:message key="paymentMethod.selectOneGateway"/>");
		return false;
	}
	return true;
}

function fnAddPaymentMethod(form){
	var paymentMethodSelect=form.paymentMethodId;
	var selIndex=paymentMethodSelect.selectedIndex;
	if(paymentMethodSelect.options[selIndex].value==-1){
		alert("<fmt:message key="paymentMethod.selectOneGateway"/>");
		return false;
	}
	return true;	
}
//-->
</script>