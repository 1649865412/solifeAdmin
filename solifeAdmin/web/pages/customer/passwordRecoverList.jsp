<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="passwordRecoverList.title"/></title>
<content tag="heading"><fmt:message key="passwordRecoverList.heading"/></content>

<c:set var="buttons">
	<cartmatic:cartmaticBtn btnType="common" onclick="location.href='<c:url value="index.html"/>'" commonBtnValueKey="button.cancel"/>
	<!-- set current method todo for multiActionCotroller -->
	<input type="hidden" name="doAction" value=""/>    
</c:set>
<c:set var="checkAll">
	<input type="checkbox" name="allbox" onclick="checkAll(this.form)" style="margin: 0 0 0 0px" />
</c:set>
<div id="list-btn-wrap" class="alignleft">
${buttons}
</div>
<form class="mainForm" name="passwordRecoverListFrm" method="post">
<display:table name="${passwordRecoverList}" cellspacing="0" cellpadding="0"
	uid="passwordRecoverItem" class="data-table-wrap" 
	export="false" requestURI="" >
	<display:column style="width: 5%" title="${checkAll}" headerClass="data-table-title">
	    <input type="checkbox" name="passwordRecoverId" value="${passwordRecoverItem.passwordRecoverId}" class="checkbox" />
	</display:column>
    <display:column property="passwordRecoverId" sortable="false" headerClass="data-table-titlcom.cartmatic.estore.core.decorator.TblColumnDecoratorator.TblColumnDecorator" titleKey="passwordRecover.passwordRecoverId"/>
    <display:column property="url" sortable="false" headerClass="data-table-ticom.cartmatic.estore.core.decorator.TblColumnDecoratororator.TblColumnDecorator" titleKey="passwordRecover.url"/>
    <display:column property="expiredDate" sortable="false" headerClass="data-table-com.cartmatic.estore.core.decorator.TblColumnDecoratorecorator.TblColumnDecorator" titleKey="passwordRecover.expiredDate"/>
    <display:column property="email" sortable="false" headerClass="data-tablcom.cartmatic.estore.core.decorator.TblColumnDecorator.decorator.TblColumnDecorator" titleKey="passwordRecover.email"/>
    <display:setProperty name="paging.banner.item_name" value="passwordRecover"/>
    <display:setProperty name="paging.banner.items_name" value="passwordRecovers"/>
</display:table>
<%@include file="/common/pagingOnlyNew.jsp"%>

</form>