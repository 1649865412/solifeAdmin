<div id="ruleDetail-coupons">
	<script type="text/javascript" src="${ctxPath}/scripts/cartmatic/sales/promoRuleFormCoupons.js"></script>

	<table class="table-content" width="100%">
		<tr>
			<td width="5%">
				<form  name="hihi" id="hihi" method="post" action="">
				<%-- 不知道为什么必须在前面加一个form 否则下面的form不会被编译出来，不要问我为什么--%>
					<input type="hidden" name="hi" id="hi"/>
				</form>
			</td>
			<td width="90%">
				<form  name="genCouponForm" id="genCouponForm" method="post" action="">
					<fmt:message key="couponDetail.genCouponsT1" />
					<fmt:message key="couponDetail.couponStyle" />
					:
					<select id="couponStyle" name="couponStyle" onchange="fnChangeCouponSytle()">
						<option value="1">
							<fmt:message key="couponDetail.couponStyle.number" />
						</option>
						<option value="2">
							<fmt:message key="couponDetail.couponStyle.string" />
						</option>
						<option value="3">
							<fmt:message key="couponDetail.couponStyle.specialNo" />
						</option>
					</select>
					<span id="inputGenNos" >
						<fmt:message key="couponDetail.prefix" />
						:
						<input type="text" id="prefix" name="prefix" value="" size="8" />
						<fmt:message key="couponDetail.genCouponsT2" />
						<input type="text" id="quantity" name="quantity" value="1"	size="8" />
						<fmt:message key="couponDetail.genCouponsT3" />
					</span>
					<span id="inputSpecialNo" style="display: none;">Special Coupon NO.:
						<input type="text" id="speCouponNo" name="speCouponNo" value="" size="8" />
					</span>					
					<cartmatic:iconBtn icon="check" textKey="couponDetail.genCouponsButton" onclick="return genCoupons();"/>
				</form>				
				<br />
				<div id="coupon_message"></div>
				<b><fmt:message key="couponList.heading" /></b>
				<div id="ruleDetail-couponList" class="ruleDetail-couponList"></div>


			</td>

			<td width="5%">

			</td>
		</tr>
	</table>

		<fmt:message key="coupon.title"
			var="sendCouponDialogTitle" />
	<fmt:message key="coupon.sendEmail" var="buttonSendEmail"/>
	<app:ui_dialog id="sendCouponDialog"
		title="${sendCouponDialogTitle}" width="480" height="250"
		buttons="'${buttonSendEmail}':sendCoupon">
		<div id="sendCouponDiv">
			<form name="sendCouponForm" id="sendCouponForm" method="post">
				<table class="table-content" width="100%" style="border:none;">

					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="coupon.firstName" />
						</td>
						<td>
							<input type="input" name="firstName" id="firstName" value="" />
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="coupon.lastName" />
						</td>
						<td>
							<input type="input" name="lastName" id="lastName" value="" />
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="coupon.email" />(*):
						</td>
						<td>
							<input type="input" name="email" id="email" value="" size="30"/>
						</td>
					</tr>
					<tr>
						<td class="FieldLabel">
							<StoreAdmin:label key="coupon.couponNo" />
						</td>
						<td>
							<input type="input" name="couponNo" id="couponNo" value="" readonly="true" size="30"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
</div>
</app:ui_dialog>
</div>
<script type="text/javascript">
$j(document).ready(function(){
	applyValidate($("availableCount"),"required,integer,minValue=1");
	applyValidate($("priority"),"required,integer,minValue=0");
});
</script>