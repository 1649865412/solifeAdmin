<table border="0" cellspacing="0" cellpadding="0" class="no-border">
  <tr>
    <td class="Field150"><i18n:msg key="pmsg.action.g0" /></td>
    <td class="Field40">
		<ul class="elementList elementListB" id='actionMenu' style="z-index:87">
			<li class="noHover">
				<img src="../images/default/dd/drop-add.gif" />
				<i18n:msg key="pmsg.eligibility.add" />
				<ul>
		
					<li>
						<a
							onclick="addCatalogSkuPercentDiscountAction('e'+newElementId++,'p'+newParamId++,'');closeMenu('actionMenu')"><img
								src="../images/default/dd/drop-add.gif" /><i18n:msg key="pmsg.CatalogSkuPercentDiscountAction" />
						</a>
					</li>
					<li>
						<a
							onclick="addCatalogSkuAmountDiscountAction('e'+newElementId++,'p'+newParamId++,'');closeMenu('actionMenu')"><img
								src="../images/default/dd/drop-add.gif" /><i18n:msg key="pmsg.CatalogSkuAmountDiscountAction" />
						</a>
					</li>
		
		
				</ul>
			</li>
		</ul>
	</td>
    <td><div id="needAction"><i><fmt:message key="pmsg.validate_needAction"/></i></div></td>
  </tr>
</table>
<div>
	<ul id="myAction">

	</ul>
</div>
