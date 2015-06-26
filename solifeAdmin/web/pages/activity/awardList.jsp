<%@ include file="/common/taglibs.jsp"%>
<app:pageHeading pageHeadingKey="awardList.heading" />
<content tag="buttons">

<cartmatic:cartmaticBtn btnType="create" onclick="return fnDoAdd(this);" />
<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDelete(this);" />

</content>

	<app:ui_leftMenu>
		<div class="sidebar-left">
			<app:ui_tabs tabsId="left_menu_tabs" />
			<form name="searchAwardForm" method="post" action="${ctxPath}/activity/award.html" onsubmit="return false;">
				<div class="tab" id="left_menu_tabs">
					<ul>
						<li>
							<a href="#glSearchBar"><fmt:message key="yourposition.search" />
							</a>
						</li>
					</ul>
					<search:searchBox>
						<%--<search:filter />
						<search:input attrPath="s.name" attrNameKey="promoRule.name" datatype="String" operator="LIKE" classes="form-inputbox" onenter="true"/>
						<div class="blank10"></div>
						<div class="title">
							<fmt:message key="promoRule.promoType" />
						</div>
						<select name="COL@s.promoType@String@EQ">
							<option></option>
							<option value="shoppingcartPromotion" <c:if test="${param['COL@s.promoType@String@EQ']=='shoppingcartPromotion'}">selected</c:if>>
							<fmt:message key="promoRule.promoType.shoppingcartPromotion"/> </option>
							<option value="catalogPromotion" <c:if test="${param['COL@s.promoType@String@EQ']=='catalogPromotion'}">selected</c:if>>
							<fmt:message key="promoRule.promoType.catalogPromotion"/> </option>
							<option value="couponPromotion" <c:if test="${param['COL@s.promoType@String@EQ']=='couponPromotion'}">selected</c:if>>
							<fmt:message key="promoRule.promoType.couponPromotion"/> </option>
						</select>
						<div class="blank10"></div>
						<div class="title">
							<fmt:message key="promoRule.store" />
						</div>
						<div>
							<select name="COL@s.store.storeId@Integer@EQ"  class="Field200" style="width:150px">
								<option></option>
								<c:forEach var="store" items="${stores}">
								<option value="${store.storeId}" <c:if test="${param['COL@s.store.storeId@Integer@EQ'] == store.storeId}">selected="selected" </c:if>>${store.name}</option>
								</c:forEach>								
							</select>
						</div>						
						<div class="blank10"></div>
					--%></search:searchBox>
					
				</div>
			</form>
		</div>
	</app:ui_leftMenu>
	<!--listing box start-->
<form class="mainForm" name="mainForm" method="post" action="${ctxPath}/activity/award.html">
	<!--editURLPath is used in TblDecorator-->
	<c:set var="editURLPath"
		value="/activity/award.html?doAction=edit&from=list" scope="page" />
	
		<c:set var="checkAll">
			<input type="checkbox" name="allbox" onclick="checkAll(this.form)"
				style="margin: 0 0 0 0px" />
		</c:set>
		<display:table name="${awardList}" cellspacing="0" cellpadding="0"
			uid="awardItem" class="table-list" export="false" requestURI="">
			
			<display:column style="width: 3%" title="${checkAll}" media="html">
				<input type="checkbox" name="multiIds"
					value="${awardItem.id}" class="checkbox"
					title="" />
			</display:column>
			
			<display:column style="width: 10%" property="level" url="${editURLPath}"
				paramId="awardId" paramProperty="id" sortable="false"
				headerClass="data-table-title"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				title="奖品等级" />
			
			<display:column style="width: 10%" property="chance"
				headerClass="data-table-title"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				title="中奖概率" />
			
			<display:column style="width: 20%" property="prize" 
				headerClass="data-table-title"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				title="奖品名称" />
			
			<display:column style="width: 40%" property="description"
				headerClass="data-table-title"
				decorator="com.cartmatic.estore.core.decorator.TblColumnDecorator"
				title="描述" />


		</display:table>
		<%@ include file="/common/pagingOnlyNew.jsp"%>
</form>
<script type="text/javascript">
function fnCreatePromotion()
{
	var form=document.mainForm;
	fnAddHiddenField("promotionType", $j("#promoType").val(), form);
	fnAddHiddenField("storeId", $j("#storeId").val(), form);
	return fnDoAction(form, "edit");
}
<%--function fnSearchRules(){
	
	var form=document.searchRuleForm;
	fnAddHiddenField("doAction","search",form);
	form.submit();
}

function onPromoRuleSearch(event){
 	//按下回车搜索事件 
 	var event = window.event||event;
	if(event.keyCode==13){	
	  	 fnAddHiddenField("newSearch","1",document.searchRuleForm);
	     fnSearchRules();
  	}
}--%>
</script>
<app:ui_dialog id="CreatePromoDialog"
		title="Create Promotion" width="460" height="200" buttons="'OK':fnCreatePromotion , 'Cancel':dlgCreatePromoDialog_close">
		<div id="CreatePromoDialogDiv">			
				<table class="table-content" width="100%" style="border:none;">
					<tr>
						<td class="FieldLabel" width="40%">
							促销类型
						</td>
						<td>
							<select id="promoType" class="Field200">
								<option value="shoppingcartPromotion"><fmt:message key="button.promotion-shoppingcart"/></option>
								<option value="catalogPromotion"><fmt:message key="button.promotion-catalog"/></option>
								<option value="couponPromotion"><fmt:message key="button.promotion-coupon"/></option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="promoRule.store" />
						</td>
						<td>
							<select id="storeId" class="Field200">
								<c:forEach var="store" items="${stores}">
								<option value="${store.storeId}">${store.name}</option>
								</c:forEach>								
							</select>
						</td>
					</tr>					
				</table>			
		</div>
</app:ui_dialog>