<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<app:pageHeading pageHeadingKey="productReviewList.heading" />

<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />
	<cartmatic:cartmaticBtn btnType="common" inputType="button" commonBtnValueKey="button.active" onclick="return fnActiveProductReview(this);" />
	<cartmatic:cartmaticBtn btnType="cancel" inputType="button" onclick="return fnDoUpToParent(this);" />
</content>
<app:ui_leftMenu>
	<div class="sidebar-left">
		<form method="post" action="${ctxPath}/catalog/productReview.html" onsubmit="return false;">
			<app:ui_tabs tabsId="left_menu_tabs" />
			<div class="tab" id="left_menu_tabs">
				<ul>
					<li>
						<a href="#listSelectContent"><fmt:message key="yourposition.search" /> </a>
					</li>
				</ul>
				<div class="content" id="listSelectContent">
					<search:searchBox>
						<search:filter />
						<search:input attrPath="s.subject" attrNameKey="productReview.subject" datatype="String"
							operator="LIKE" classes="form-inputbox" onenter="true"/>
						<search:input attrPath="s.product.productName" attrNameKey="productReview.productId" datatype="String"
							operator="LIKE" classes="form-inputbox" onenter="true"/>
						<search:input attrPath="s.product.productCode" attrNameKey="product.productCode" datatype="String"
							operator="EQ" classes="form-inputbox" onenter="true"/>
						<div class="title">
							<fmt:message key="productReview.status"/>
						</div>
						<div>
							<select name="COL@s.status@Short@EQL" style="width:50%" class="Field400">
								<option value="">  </option>
								<option value="1" <c:if test="${param['COL@s.status@Short@EQL'] eq '1'}">selected</c:if>><fmt:message key="productReview.status_${1}"/></option>
								<option value="2" <c:if test="${param['COL@s.status@Short@EQL'] eq '2'}">selected</c:if>><fmt:message key="productReview.status_${2}"/></option>
							</select>
						</div>
						<search:orderby showOrderDirection="true">
							<option value="productReviewId">
								<fmt:message key="search.order.by.default" />
							</option>
						</search:orderby>
					</search:searchBox>
				</div>
			</div>
		</form>
	</div>
</app:ui_leftMenu>
<form class="mainForm" name="productReviewListForm" method="post" action="${ctxPath}/catalog/productReview.html">
	<!--listing box start-->
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath" value="/catalog/productReview.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll"><input type="checkbox" name="allbox" onclick="checkAll(this.form)" class="checkbox" /></c:set>
		<display:table name="${productReviewList}" cellspacing="0" cellpadding="0" uid="productReviewItem"
			class="table-list" style="table-layout:fixed;" export="false" requestURI="">
			<display:column headerClass="w30" class="w30" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds" value="${productReviewItem.productReviewId}" class="checkbox" title="${productReviewItem.productReviewName}"/>
			</display:column>
			<display:column sortable="false" headerClass="w150" class="w150"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productReview.sotreId">
        		${productReviewItem.store.name}
        	</display:column>
			<display:column sortable="false" headerClass="wauto" class="wauto" url="${editURLPath}" paramId="productReviewId" paramProperty="productReviewId"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productReview.subject">
        		<c:out value="${productReviewItem.subject}"/>
        	</display:column>
		    <display:column sortable="false" headerClass="wauto" class="wauto" 
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productReview.productId">
        		${productReviewItem.product.productName} [${productReviewItem.product.productCode}]
        	</display:column>
		    <display:column sortable="false" headerClass="w200" class="w200"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productReview.customerName">
        		<c:choose>
					<c:when test="${productReviewItem.reviewUser.appuserId==-2}">
						<c:out value="${productReviewItem.customerName}" />(<fmt:message key="productReview.reviewUser.anonymous" />)
					</c:when>
					<c:otherwise>
						${productReviewItem.reviewUser.username}
					</c:otherwise>
				</c:choose>
        	</display:column>
        	<display:column property="createTime" sortable="false" headerClass="w100" class="w100"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productReview.createTime"/>
        	<display:column sortable="false" headerClass="w60" class="w60"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productReview.givenPoint">
        		<c:choose>
					<c:when test="${empty productReviewItem.givenTime}">
						<fmt:message key="productReview.not.given" />
					</c:when>
					<c:otherwise>
						<fmt:message key="productReview.given" />
					</c:otherwise>
				</c:choose>
        	</display:column>
        	<display:column sortable="false" headerClass="w60" class="w60"
        		decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator" titleKey="productReview.status">
        		<fmt:message key="productReview.status_${productReviewItem.status}" />
        	</display:column>
		</display:table>
		<div class="blank10"></div>
		<%@include file="/common/pagingNew.jsp"%>

</form>
<script type="text/javascript">
highlightTableRows("productReviewItem");
function doSearchHandlerWhenEnter(ievent){
	var event = window.event||ievent;
	if( event.keyCode == 13 ){
	 	doSearchHandler();
  	}
  	return false;
}
function fnActiveProductReview(obj){
	var itemNames = fnGetSelectedItemNames();
	if (itemNames=="") {
		return false;
	}
	return fnDoAction(obj, "multiActive", "<fmt:message key="productReviewList.message.confirmActiveSelected" />"+ " " + itemNames+"?");
}
</script>