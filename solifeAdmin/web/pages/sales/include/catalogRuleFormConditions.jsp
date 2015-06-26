<table border="0" cellspacing="0" cellpadding="0" class="no-border">
  <tr>
    <td class="Field150">
		<i18n:msg key="pmsg.condition.g0"/>
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
		<i18n:msg key="pmsg.condition.g1"/>	 
	</td>
    <td class="Field40">
		<ul class="elementList elementListB" id='conditionMenu' style="z-index:88;">
			<li class="noHover">
				<img src="../images/default/dd/drop-add.gif" />
				<i18n:msg key="pmsg.eligibility.add"/>
				<ul>
					<li>
						<a
							onclick="addCatalogIsProductCondition('e'+newElementId++,'p'+newParamId++,'','');closeMenu('conditionMenu')"><img
								src="../images/default/dd/drop-add.gif" /><i18n:msg key="pmsg.CatalogIsProductCondition"/></a>
					</li>
					
					<li>
						<a
							onclick="addCatalogInCategoryCondition('e'+newElementId++,'p'+newParamId++,'','');closeMenu('conditionMenu')"><img
								src="../images/default/dd/drop-add.gif" /><i18n:msg key="pmsg.CatalogInCategoryCondition"/></a>
					</li>
					
					<li>
						<a
							onclick="addCatalogIsBrandCondition('e'+newElementId++,'p'+newParamId++,'','');closeMenu('conditionMenu')"><img
								src="../images/default/dd/drop-add.gif" /><i18n:msg key="pmsg.CatalogIsBrandCondition"/></a>
					</li>
		
				</ul>
			</li>
		</ul>
	</td>
    <td><div id="needCondition"><i><fmt:message key="pmsg.validate_needCondition"/></i></div></td>
  </tr>
</table>
<div>
	<ul id="myCondition">

	</ul>
</div>
