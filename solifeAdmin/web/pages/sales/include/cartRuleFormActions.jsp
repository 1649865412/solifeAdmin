<table border="0" cellspacing="0" cellpadding="0" class="no-border">
  <tr>
   <td width="230">
		<i18n:msg key="pmsg.action.t0" />
	</td>
   <td width="40">
		<ul class="elementList elementListB" id='actionMenu' style="z-index:87;">
			<li class="noHover">
				<img src="../images/default/dd/drop-add.gif" />
				<i18n:msg key="pmsg.eligibility.add" />
				<ul>
					<li>
						<a><img src="../images/default/dd/drop-add.gif" /> <i18n:msg
								key="pmsg.CartTotalDiscount" /> <img
								src="../images/default/dd/arrow_right.gif" /> </a>
						<ul>
							<li>
								<a
									onclick="addCartTotalPercentDiscountAction('e'+newElementId++,'p'+newParamId++,'');closeMenu('actionMenu')"><img
										src="../images/default/dd/drop-add.gif" /> <i18n:msg
										key="pmsg.CartTotalPercentDiscountAction" /> </a>
							</li>
							<li>
								<a
									onclick="addCartTotalAmountDiscountAction('e'+newElementId++,'p'+newParamId++,'');closeMenu('actionMenu')"><img
										src="../images/default/dd/drop-add.gif" /> <i18n:msg
										key="pmsg.CartTotalAmountDiscountAction" /> </a>
							</li>
						</ul>
					</li>
					<li>
						<a><img src="../images/default/dd/drop-add.gif" /> <i18n:msg
								key="pmsg.CartItemDiscount" /> <img
								src="../images/default/dd/arrow_right.gif" /> </a>
						<ul> 
								<li>
									<a><img src="../images/default/dd/drop-add.gif" /> <i18n:msg
											key="pmsg.CartProductDiscount" /> <img
											src="../images/default/dd/arrow_right.gif" /> </a>
										<ul>
												<li>
													<a
														onclick="addCartProductPercentDiscountAction('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'');closeMenu('actionMenu')"><img
															src="../images/default/dd/drop-add.gif" /> <i18n:msg
															key="pmsg.CartProductPercentDiscountAction" /> </a>
												</li>
												<li>
													<a
														onclick="addCartProductAmountDiscountAction('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'');closeMenu('actionMenu')"><img
															src="../images/default/dd/drop-add.gif" /> <i18n:msg
															key="pmsg.CartProductAmountDiscountAction" /> </a>
												</li>
										</ul>	
								</li>
								
								<li>
									<a><img src="../images/default/dd/drop-add.gif" /> <i18n:msg
											key="pmsg.CartCategoryDiscount" /> <img
											src="../images/default/dd/arrow_right.gif" /> </a>
										<ul>
												<li>
													<a
														onclick="addCartCategoryPercentDiscountAction('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'');closeMenu('actionMenu')"><img
															src="../images/default/dd/drop-add.gif" /> <i18n:msg
															key="pmsg.CartCategoryPercentDiscountAction" /> </a>
												</li>
												<li>
													<a
														onclick="addCartCategoryAmountDiscountAction('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'');closeMenu('actionMenu')"><img
															src="../images/default/dd/drop-add.gif" /> <i18n:msg
															key="pmsg.CartCategoryAmountDiscountAction" /> </a>
												</li>
										</ul>	
								</li>
								
								<li>
									<a><img src="../images/default/dd/drop-add.gif" /> <i18n:msg
											key="pmsg.CartSkuDiscount" /> <img
											src="../images/default/dd/arrow_right.gif" /> </a>
										<ul>
													<li>
														<a
															onclick="addCartSkuPercentDiscountAction('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'');closeMenu('actionMenu')"><img
																src="../images/default/dd/drop-add.gif" /> <i18n:msg
																key="pmsg.CartSkuPercentDiscountAction" /> </a>
													</li>
													<li>
														<a
															onclick="addCartSkuAmountDiscountAction('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'');closeMenu('actionMenu')"><img
																src="../images/default/dd/drop-add.gif" /> <i18n:msg
																key="pmsg.CartSkuAmountDiscountAction" /> </a>
													</li>
													<li>
														<a
															onclick="addCartFreeSkuAction('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'');closeMenu('actionMenu')"><img
																src="../images/default/dd/drop-add.gif" /> <i18n:msg
																key="pmsg.CartFreeSkuAction" /> </a>
													</li>
										</ul>	
								</li>
							
							
						
						</ul>
					</li>
					<li>
						<a><img src="../images/default/dd/drop-add.gif" /> <i18n:msg
								key="pmsg.ShippingDiscount" /> <img
								src="../images/default/dd/arrow_right.gif" /> </a>
						<ul>
							<li>
								<a
									onclick="addCartShippingFeePercentDiscountAction('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'');closeMenu('actionMenu')"><img
										src="../images/default/dd/drop-add.gif" /> <i18n:msg
										key="pmsg.CartShippingFeePercentDiscountAction" /> </a>
							</li>
							<li>
								<a
									onclick="addCartShippingFeeAmountDiscountAction('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'');closeMenu('actionMenu')"><img
										src="../images/default/dd/drop-add.gif" /> <i18n:msg
										key="pmsg.CartShippingFeeAmountDiscountAction" /> </a>
							</li>
						</ul>
					</li>
					<li>
						<a><img src="../images/default/dd/drop-add.gif" /> <i18n:msg
								key="pmsg.PointDiscount" /> <img
								src="../images/default/dd/arrow_right.gif" /> </a>
						<ul>
							<li>
								<a
									onclick="addCartFixedPointDonateAction('e'+newElementId++,'p'+newParamId++,'');closeMenu('actionMenu')"><img
										src="../images/default/dd/drop-add.gif" /> <i18n:msg
										key="pmsg.CartFixedPointDonateAction" /> </a>
							</li>
							<li>
								<a><img
										src="../images/default/dd/drop-add.gif" /> <i18n:msg
										key="pmsg.CartPercentPointDonate" /> <img
								src="../images/default/dd/arrow_right.gif" /></a>
									<ul>
											<li>
											<a	onclick="addCartTotalPercentPointDonateAction('e'+newElementId++,'p'+newParamId++,'','p'+newParamId++,'');closeMenu('actionMenu')"><img
													src="../images/default/dd/drop-add.gif" /> <i18n:msg
													key="pmsg.CartTotalPercentPointDonateAction" /> </a>
											</li>
											<li>
												<a
													onclick="addCartProductPercentPointDonateAction('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'');closeMenu('actionMenu')"><img
														src="../images/default/dd/drop-add.gif" /> <i18n:msg
														key="pmsg.CartProductPercentPointDonateAction" /> </a>
											</li>
											<li>
												<a
													onclick="addCartCategoryPercentPointDonateAction('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'');closeMenu('actionMenu')"><img
														src="../images/default/dd/drop-add.gif" /> <i18n:msg
														key="pmsg.CartCategoryPercentPointDonateAction" /> </a>
											</li>
											<li>
												<a
													onclick="addCartSkuPercentPointDonateAction('e'+newElementId++,'p'+newParamId++,'','','p'+newParamId++,'');closeMenu('actionMenu')"><img
														src="../images/default/dd/drop-add.gif" /> <i18n:msg
														key="pmsg.CartSkuPercentPointDonateAction" /> </a>
											</li>
									</ul>
							</li>
							
						</ul>
					</li>
		
					<c:if test="${promoRule.promoType == 'shoppingcartPromotion'}">
						<li>
							<a><img src="../images/default/dd/drop-add.gif" /> <i18n:msg
									key="pmsg.CouponDiscount" /> <img
									src="../images/default/dd/arrow_right.gif" /> </a>
							<ul>
								<li>
									<a
										onclick="addCartCouponDonateAction('e'+newElementId++,'p'+newParamId++,'','');closeMenu('actionMenu')"><img
											src="../images/default/dd/drop-add.gif" /> <i18n:msg
											key="pmsg.CartCouponDonateAction" /> </a>
								</li>
		
							</ul>
						</li>
					</c:if>
		
				</ul>
			</li>
		</ul>	
	</td>
    <td><div id="needAction"><i><fmt:message key="pmsg.validate_needAction"/></i></div>
	</td>
  </tr>
  <tr>
  	<td colspan="3">
    	<ul id="myAction"></ul>
    </td>
  </tr>
</table>