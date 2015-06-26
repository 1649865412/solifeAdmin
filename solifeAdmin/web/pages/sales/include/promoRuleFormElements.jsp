<div id="ruleDetail-elements" >
	<table class="table-content" width="100%">
		<tr><td>
		<script type="text/javascript" src="../scripts/cartmatic/sales/promoRuleFormi18n_cn.js"></script>
		<script type="text/javascript" src="../scripts/cartmatic/sales/promoRuleForm.js"></script>
		<%@ include file="./promoRuleFormRuleLoad.jsp"%>
		<div class="inbox">
		<c:if test="${promoRule.promoType == 'shoppingcartPromotion' || promoRule.promoType == 'couponPromotion' }">
		<div class="inbox-sub"><%@ include file="./cartRuleFormEligibilitys.jsp"%></div>
		<div class="inbox-sub"><%@ include file="./cartRuleFormConditions.jsp"%></div>
		<div class="inbox-sub"><%@ include file="./cartRuleFormActions.jsp"%></div>
		</c:if>
		<c:if test="${promoRule.promoType == 'catalogPromotion'}">
		<input type="hidden" id="eligibilityOperator" name="eligibilityOperator" value="1"/>
		<div class="inbox-sub"><%@ include file="./catalogRuleFormConditions.jsp"%></div> 
		<div class="inbox-sub"><%@ include file="./catalogRuleFormActions.jsp"%></div>
		</c:if>
		</div>
		</td></tr>
		<product:productSelector id="productSelector" ondblclick="getProduct" autoClose="true" catalogId="${promoRule.store.catalog.id}"></product:productSelector>
		<product:productSkuSelector id="productSkuSelector"  ondblclick="getProductSku" autoClose="true" catalogId="${promoRule.store.catalog.id}"></product:productSkuSelector>
		<product:categorySelector id="categorySelector" catalogId="${promoRule.store.catalog.id}" ondblclick="getCategory" autoClose="true"></product:categorySelector>
	</table>
</div>