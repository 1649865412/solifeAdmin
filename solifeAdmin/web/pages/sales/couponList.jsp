<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<app:pageHeading pageHeadingKey="couponList.heading" />
<content tag="buttons">
</content>

<form class="mainForm" name="couponListForm" method="post" action="${ctxPath}/sales/coupon.html">
<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/sales/coupon.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" style="margin: 0 0 0 0px" /></c:set>
		
		<display:table name="${couponList}" cellspacing="0" cellpadding="0" uid="couponItem"
			class="table-list" export="false" requestURI="">
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${couponItem.couponId}"	class="checkbox" title="${couponItem.couponNo}"/>
			</display:column>
		    <display:column property="couponNo" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="coupon.couponNo"/>
		    
		    <display:column property="remainedTimes" sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="coupon.remainedTimes"/>
		    <display:column  sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="coupon.isSent">
        		<fmt:message key="coupon.isSent.s${couponItem.isSent}"/>
        	</display:column>	
        	 <display:column  sortable="false" headerClass="data-table-title"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="coupon.sendEmail">
        		<c:if test="${couponItem.isSent == 0 and couponItem.remainedTimes > 0}">
        			<cartmatic:iconBtn icon="mail" textKey="coupon.sendEmail" onclick="fnInitSendCouponForm('${couponItem.couponNo}');dlgsendCouponDialog_show();"/>
        		</c:if>
        	</display:column>	
		</display:table>
		<%@include file="/common/pagingNew.jsp"%>
	
</form>
<script type="text/javascript">
highlightTableRows("couponItem");
function gotoPage(pageNo) {
	gotoPageInPromo(pageNo);
}
function fnDoMultiDelete(obj) {
	fnDoMultiDeleteInPromo(obj);
}

</script>