<table border="0" cellspacing="0" cellpadding="0" class="no-border">
  <tr>
    <td width="230">
		<i18n:msg key="pmsg.condition.t0"/>
		 <select
				id="conditionOperator" name="conditionOperator">
				<option value="1"
					<c:if test="${promoRule.conditionOperator == '1' }">selected</c:if>>
					<i18n:msg key="pmsg.operator.all"/>
				</option>
				<option value="0"
					<c:if test="${promoRule.conditionOperator == '0' }">selected</c:if>>
					<i18n:msg key="pmsg.operator.any"/>
				</option>
			</select>
		<i18n:msg key="pmsg.condition.t1"/>	 
	</td>
    <td width="40">
		<ul class="elementList elementListB" id='conditionMenu' style="z-index:88;">
			<li class="noHover">
				<img src="../images/default/dd/drop-add.gif" />
				<i18n:msg key="pmsg.eligibility.add"/>
				<ul>
					<li>
						<a
							onclick="addCartTotalCondition('e'+newElementId++,'p'+newParamId++,'');closeMenu('conditionMenu')"><img
								src="../images/default/dd/drop-add.gif" /><i18n:msg key="pmsg.CartTotalCondition"/></a>
					</li>
					<li>
						<a><img
								src="../images/default/dd/drop-add.gif" /><i18n:msg key="pmsg.CartNumsCondition"/>
								<img
								src="../images/default/dd/arrow_right.gif" /> </a>
							<ul>
								<li>
									<a
										onclick="addCartAnySkuInCartCondition('e'+newElementId++,'p'+newParamId++,'','p'+newParamId++,'');closeMenu('conditionMenu')"><img
											src="../images/default/dd/drop-add.gif" /><i18n:msg key="pmsg.CartAnySkuInCartCondition"/></a>
								</li>
								<li>
									<a
										onclick="addCartContainsItemsOfCategoryCondition('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'','p'+newParamId++,'');closeMenu('conditionMenu')"><img
											src="../images/default/dd/drop-add.gif" /><i18n:msg key="pmsg.CartContainsItemsOfCategoryCondition"/></a>
								</li>
								<li>
									<a
										onclick="addCartProductInCartCondition('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'','p'+newParamId++,'');closeMenu('conditionMenu')"><img
											src="../images/default/dd/drop-add.gif" /><i18n:msg key="pmsg.CartProductInCartCondition"/></a>
								</li>
								<li>
									<a
										onclick="addCartSkuInCartCondition('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'','p'+newParamId++,'');closeMenu('conditionMenu')"><img
											src="../images/default/dd/drop-add.gif" /><i18n:msg key="pmsg.CartSkuInCartCondition"/></a>
								</li>
							
							</ul>	
					</li>
				</ul>
			</li>
		</ul>
	</td>
    <td><div id="needCondition"><i><fmt:message key="pmsg.validate_needCondition"/></i></div>
	</td>
  </tr>
  <tr>
  	<td colspan="3">
    	<ul id="myCondition"></ul>
    </td>
  </tr>
</table>