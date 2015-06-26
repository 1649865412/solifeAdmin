<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<script type="text/javascript" src="../scripts/jquery/plugins/droppy/jquery.droppy.custom.js"></script>
<link rel="stylesheet" type="text/css" href="../scripts/jquery/plugins/droppy/droppy.custom.css" />
<app:pageHeading entityName="${promoRule.name}"
	entityHeadingKey="promoRuleDetail.heading" />
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(fnDoSaveCallback);" />
<c:if test="${promoRule.promoRuleId!=null}">
	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
</c:if>
<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<app:showBindErrors bindPath="promoRule.*" />
<app:ui_tabs tabsId="ruleDetail" type="2"/>
<div id="ruleDetail" style="display: none">
	<ul>
		<li id="ruleDetail-summary-tab">
			<a href="#ruleDetail-summary"><span><fmt:message key="promoRuleDetail.summary" /> </span> </a>
		</li>
		<li id="ruleDetail-elements-tab">
			<a href="#ruleDetail-elements"><span><fmt:message key="promoRuleDetail.detail" /> </span> </a>
		</li>
		<c:if test="${promoRule.promoType == 'couponPromotion'}">
		<li id="ruleDetail-coupons-tab">
			<a  href="#ruleDetail-coupons"><span><fmt:message key="promoRuleDetail.coupon" /> </span> </a>
		</li>
		</c:if>
	</ul>
	<div class="blank10"></div>
	<div id="promoRule">
		<form:form method="post" cssClass="mainForm" id="promoRuleForm" name="promoRuleForm" commandName="promoRule" action="${ctxPath}/sales/promoRule.html">
		<%@ include file="./include/promoRuleFormSummary.jsp"%>
		<%@ include file="./include/promoRuleFormElements.jsp"%>
		<c:if test="${promoRule.promoType == 'couponPromotion'}">
		<%@ include file="./include/promoRuleFormCoupons.jsp"%>
		</c:if>
		</form:form>
	</div>
</div>
<v:javascript formName="promoRule" staticJavascript="false" />