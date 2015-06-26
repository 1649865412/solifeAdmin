<table border="0" cellspacing="0" cellpadding="0" class="no-border">
  <tr>
    <td width="230">
	<i18n:msg key="pmsg.eligibility.t0"/>
	<select id="eligibilityOperator" name="eligibilityOperator">
		<option value="1"
			<c:if test="${promoRule.eligibilityOperator == '1' }">selected</c:if>>
			<i18n:msg key="pmsg.operator.all"/>
		</option>
		<option value="0"
			<c:if test="${promoRule.eligibilityOperator == '0' }">selected</c:if>>
			<i18n:msg key="pmsg.operator.any"/>
		</option>
	</select>
	<i18n:msg key="pmsg.eligibility.t1"/>
	</td>
    <td width="40">
	<ul class="elementList elementListB" id='eligibilityMenu' style="z-index:89;">
		<li class="noHover">
			<img src="../images/default/dd/drop-add.gif" />
			<i18n:msg key="pmsg.eligibility.add"/>
			<ul>
				<li>
					<a onclick="addEveryoneEligibility('e'+newElementId++);closeMenu('eligibilityMenu')"><img
							src="../images/default/dd/drop-add.gif" /><i18n:msg key="pmsg.EveryoneEligibility"/></a>
				</li>
				<li>
					<a onclick="addFirstTimeBuyerEligibility('e'+newElementId++);closeMenu('eligibilityMenu')"><img
							src="../images/default/dd/drop-add.gif" /><i18n:msg key="pmsg.FirstimeBuyerEligibility"/></a>
				</li>
				
				<li>
					<a
						onclick="addMembershipEligibility('e'+newElementId++,'p'+newParamId++,'','');closeMenu('eligibilityMenu')"><img
							src="../images/default/dd/drop-add.gif" /><i18n:msg key="pmsg.MembershipEligibility"/></a>
				</li>
			</ul>
		</li>
	</ul>
	</td>
	<td><div id="needEligibility"><i><fmt:message key="pmsg.validate_needEligibility"/></i></div></td>
  </tr>
  <tr>
  	<td colspan="3">
    	<ul id="myEligibility"></ul>
    </td>
  </tr>
</table>