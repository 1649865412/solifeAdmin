<script type='text/javascript'>
  		$j(function() {
		   	$j.ajax({type: "GET", url: __ctxPath+"/customer/membership.html?doAction=getMemberships", async: false ,dataType: "json",success:getMembershipsCallback});
		    $j.ajax({type: "GET", url: __ctxPath+"/catalog/brand.html?doAction=getBrands", async: false ,dataType: "json",success:getBrandsCallback});
		    $j.ajax({type: "GET", url: __ctxPath+"/system/shippingMethod.html?doAction=getShippingMethods", async: false ,dataType: "json",success:getShippingMethodsCallback});
		    $j.ajax({type: "GET", url: __ctxPath+"/sales/promoRule.html?doAction=getCouponTypes", async: false ,dataType: "json",success:getCouponTypesCallback});
		   
		   	promoType = "<c:out value='${promoRule.promoType}'/>";
		   
    		<c:forEach items="${elementList}" var="element">
    			<c:if test="${element.kind == 'eligibility'}">
					
					//***initialize EveryoneEligibility
					<c:if test="${element.type == 'EveryoneEligibility'}">
						
						addEveryoneEligibility("o${element.id}");
					</c:if>
					
					//***initialize FirstTimeBuyerEligibility
					<c:if test="${element.type == 'FirstTimeBuyerEligibility'}">
						addFirstTimeBuyerEligibility("o${element.id}");
					</c:if>
					
					//***initialize MembershipEligibility
					<c:if test="${element.type == 'MembershipEligibility'}">
						
						var membership_id;
						var membership;
						var membership_title;
						
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'MEMBERSHIP'}){
								membership_id = "${parameter.id}";
								membership = "${parameter.paramValue}";
								membership_title = "${parameter.paramTitle}";
							}	
						</c:forEach>
						addMembershipEligibility("o${element.id}",membership_id,membership,membership_title);
					</c:if>
					
				</c:if>
				<c:if test="${element.kind == 'condition'}">
					//***initialize CartTotalCondition
					<c:if test="${element.type == 'CartTotalCondition'}">
						var total_amount_id;
						var total_amount;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'TOTAL_AMOUNT'}){
								total_amount_id = "${parameter.id}";
								total_amount = "${parameter.paramValue}";
							}	
						</c:forEach>
						addCartTotalCondition("o${element.id}",total_amount_id,total_amount);
					</c:if>
					
					//***initialize CartAnySkuInCartCondition
					
					<c:if test="${element.type == 'CartAnySkuInCartCondition'}">
						var numitems_id;
						var numitems;
						var numitems_quantifier_id;
						var numitems_quantifier;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'NUMITEMS'}){
								numitems_id = "${parameter.id}";
								numitems = "${parameter.paramValue}";
							}	
							if(${parameter.paramName == 'NUMITEMS_QUANTIFIER'}){
								numitems_quantifier_id = "${parameter.id}";
								numitems_quantifier = "${parameter.paramValue}";
							}
						</c:forEach>
						addCartAnySkuInCartCondition("o${element.id}",numitems_id,numitems,numitems_quantifier_id,numitems_quantifier);
					</c:if>	
						
						//***initialize CartContainsItemsOfCategoryCondition
					
					<c:if test="${element.type == 'CartContainsItemsOfCategoryCondition'}">
						var category_id;
						var category;
						var category_title;
						var numitems_id;
						var numitems;
						var numitems_quantifier_id;
						var numitems_quantifier;
						var excludedParams = new Array();
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'CATEGORY'}){
								category_id = "${parameter.id}";
								category = "${parameter.paramValue}";
								category_title = "${parameter.paramTitle}";
							}	
							if(${parameter.paramName == 'NUMITEMS'}){
								numitems_id = "${parameter.id}";
								numitems = "${parameter.paramValue}";
							}	
							if(${parameter.paramName == 'NUMITEMS_QUANTIFIER'}){
								numitems_quantifier_id = "${parameter.id}";
								numitems_quantifier = "${parameter.paramValue}";
							}
							if(${parameter.paramName == 'CATEGORY_EXCLUDED'|| parameter.paramName =='PRODUCT_EXCLUDED'|| parameter.paramName =='SKU_EXCLUDED'}){
								excludedParams.push("${parameter.id}"+"|-|"+"${parameter.paramName}"+"|-|"+"${parameter.paramValue}"+"|-|"+"${parameter.paramTitle}");
							}
							
						</c:forEach>
						addCartContainsItemsOfCategoryCondition("o${element.id}",category_id,category,category_title,numitems_id,numitems,numitems_quantifier_id,numitems_quantifier);
						for(var i=0; i< excludedParams.length; i++){
							var param = excludedParams[i].split("|-|");
							if(param[1] == "CATEGORY_EXCLUDED"){
								addExcludedOfCategory("o${element.id}",param[0],param[2],param[3]);
							}
							if(param[1] == "PRODUCT_EXCLUDED"){
								addExcludedOfProduct("o${element.id}",param[0],param[2],param[3]);
							}
							if(param[1] == "SKU_EXCLUDED"){
								addExcludedOfSku("o${element.id}",param[0],param[2],param[3]);
							}
						}
					</c:if>
					
					//***initialize CartProductInCartCondition
					
					<c:if test="${element.type == 'CartProductInCartCondition'}">
						var product_id;
						var product;
						var product_title;
						var numitems_id;
						var numitems;
						var numitems_quantifier_id;
						var numitems_quantifier;
						var excludedParams = new Array();
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'PRODUCT'}){
								product_id = "${parameter.id}";
								product = "${parameter.paramValue}";
								product_title = "${parameter.paramTitle}";
							}	
							if(${parameter.paramName == 'NUMITEMS'}){
								numitems_id = "${parameter.id}";
								numitems = "${parameter.paramValue}";
							}	
							if(${parameter.paramName == 'NUMITEMS_QUANTIFIER'}){
								numitems_quantifier_id = "${parameter.id}";
								numitems_quantifier = "${parameter.paramValue}";
							}
							if(${parameter.paramName == 'SKU_EXCLUDED'}){
								excludedParams.push("${parameter.id}"+"|-|"+"${parameter.paramName}"+"|-|"+"${parameter.paramValue}"+"|-|"+"${parameter.paramTitle}");
							}
						</c:forEach>
						addCartProductInCartCondition("o${element.id}",product_id,product,product_title,numitems_id,numitems,numitems_quantifier_id,numitems_quantifier);
						for(var i=0; i< excludedParams.length; i++){
							var param = excludedParams[i].split("|-|");
							if(param[1] == "SKU_EXCLUDED"){
								addExcludedOfSku("o${element.id}",param[0],param[2],param[3]);
							}
						}
					</c:if>
					
					
					
					//***initialize CartSkuInCartCondition
					
					<c:if test="${element.type == 'CartSkuInCartCondition'}">
						var sku_id;
						var sku;
						var sku_title;
						var numitems_id;
						var numitems;
						var numitems_quantifier_id;
						var numitems_quantifier;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'SKU'}){
								sku_id = "${parameter.id}";
								sku = "${parameter.paramValue}";
								sku_title = "${parameter.paramTitle}";
							}	
							if(${parameter.paramName == 'NUMITEMS'}){
								numitems_id = "${parameter.id}";
								numitems = "${parameter.paramValue}";
							}	
							if(${parameter.paramName == 'NUMITEMS_QUANTIFIER'}){
								numitems_quantifier_id = "${parameter.id}";
								numitems_quantifier = "${parameter.paramValue}";
							}
						</c:forEach>
						addCartSkuInCartCondition("o${element.id}",sku_id,sku,sku_title,numitems_id,numitems,numitems_quantifier_id,numitems_quantifier);
					</c:if>
					
					
					//***initialize CatalogIsProductCondition
					
					<c:if test="${element.type == 'CatalogIsProductCondition'}">
						var product_id;
						var product;
						var product_title;
						
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'PRODUCT'}){
								product_id = "${parameter.id}";
								product = "${parameter.paramValue}";
								product_title = "${parameter.paramTitle}";
							}	
							
						</c:forEach>
						addCatalogIsProductCondition("o${element.id}",product_id,product,product_title);
						
					</c:if>
					
					//***initialize CatalogInCategoryCondition
					
					<c:if test="${element.type == 'CatalogInCategoryCondition'}">
						var category_id;
						var category;
						var category_title;
						
						var excludedParams = new Array();
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'CATEGORY'}){
								category_id = "${parameter.id}";
								category = "${parameter.paramValue}";
								category_title = "${parameter.paramTitle}";
							}	
							
							if(${parameter.paramName == 'CATEGORY_EXCLUDED'|| parameter.paramName =='PRODUCT_EXCLUDED'|| parameter.paramName =='SKU_EXCLUDED'}){
								excludedParams.push("${parameter.id}"+"|-|"+"${parameter.paramName}"+"|-|"+"${parameter.paramValue}"+"|-|"+"${parameter.paramTitle}");
							}
							
						</c:forEach>
						addCatalogInCategoryCondition("o${element.id}",category_id,category,category_title);
						for(var i=0; i< excludedParams.length; i++){
							var param = excludedParams[i].split("|-|");
							if(param[1] == "CATEGORY_EXCLUDED"){
								addExcludedOfCategory("o${element.id}",param[0],param[2],param[3]);
							}
							if(param[1] == "PRODUCT_EXCLUDED"){
								addExcludedOfProduct("o${element.id}",param[0],param[2],param[3]);
							}
							if(param[1] == "SKU_EXCLUDED"){
								addExcludedOfSku("o${element.id}",param[0],param[2],param[3]);
							}
						}
					</c:if>
					
					
					//***initialize CatalogIsBrandCondition
					
					<c:if test="${element.type == 'CatalogIsBrandCondition'}">
						var brand_id;
						var brand;
						var brand_title;
						
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'BRAND'}){
								brand_id = "${parameter.id}";
								brand = "${parameter.paramValue}";
								brand_title = "${parameter.paramTitle}";
							}	
							
						</c:forEach>
						addCatalogIsBrandCondition("o${element.id}",brand_id,brand,brand_title);
						
					</c:if>
					
					
					
				</c:if>	
				
				<c:if test="${element.kind == 'action'}">
					
					//***initialize CartTotalPercentDiscountAction
					<c:if test="${element.type == 'CartTotalPercentDiscountAction'}">
						
						var discount_percent_id;
						var discount_percent;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DISCOUNT_PERCENT'}){
								discount_percent_id = "${parameter.id}";
								discount_percent = "${parameter.paramValue}";
							}	
						</c:forEach>
						addCartTotalPercentDiscountAction("o${element.id}",discount_percent_id,discount_percent);
					</c:if>
					
					//***initialize CartTotalAmountDiscountAction
					<c:if test="${element.type == 'CartTotalAmountDiscountAction'}">
						
						var discount_amount_id;
						var discount_amount;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DISCOUNT_AMOUNT'}){
								discount_amount_id = "${parameter.id}";
								discount_amount = "${parameter.paramValue}";
							}	
						</c:forEach>
						addCartTotalAmountDiscountAction("o${element.id}",discount_amount_id,discount_amount);
					</c:if>
					
					
					//***initialize CartProductPercentDiscountAction
					<c:if test="${element.type == 'CartProductPercentDiscountAction'}">
						var product_id;
						var product;
						var product_title;
						var discount_percent_id;
						var discount_percent;
						var excludedParams = new Array();
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DISCOUNT_PERCENT'}){
								discount_percent_id = "${parameter.id}";
								discount_percent = "${parameter.paramValue}";
							}	
							if(${parameter.paramName == 'PRODUCT'}){
								product_id = "${parameter.id}";
								product = "${parameter.paramValue}";
								product_title = "${parameter.paramTitle}";
							}	
							
							if(${parameter.paramName == 'SKU_EXCLUDED'}){
								excludedParams.push("${parameter.id}"+"|-|"+"${parameter.paramName}"+"|-|"+"${parameter.paramValue}"+"|-|"+"${parameter.paramTitle}");
							}
						</c:forEach>
						addCartProductPercentDiscountAction("o${element.id}",product_id,product,product_title,discount_percent_id,discount_percent);
						for(var i=0; i< excludedParams.length; i++){
							var param = excludedParams[i].split("|-|");
							if(param[1] == "SKU_EXCLUDED"){
								addExcludedOfSku("o${element.id}",param[0],param[2],param[3]);
							}
						}
					</c:if>
					
					
					//***initialize CartProductAmountDiscountAction
					<c:if test="${element.type == 'CartProductAmountDiscountAction'}">
						var product_id;
						var product;
						var product_title;
						var discount_amount_id;
						var discount_amount;
						var excludedParams = new Array();
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DISCOUNT_AMOUNT'}){
								discount_amount_id = "${parameter.id}";
								discount_amount = "${parameter.paramValue}";
								
							}	
							if(${parameter.paramName == 'PRODUCT'}){
								product_id = "${parameter.id}";
								product = "${parameter.paramValue}";
								product_title = "${parameter.paramTitle}";
							}	
							
							if(${parameter.paramName == 'SKU_EXCLUDED'}){
								excludedParams.push("${parameter.id}"+"|-|"+"${parameter.paramName}"+"|-|"+"${parameter.paramValue}"+"|-|"+"${parameter.paramTitle}");
							}
						</c:forEach>
						addCartProductAmountDiscountAction("o${element.id}",product_id,product,product_title,discount_amount_id,discount_amount);
						for(var i=0; i< excludedParams.length; i++){
							var param = excludedParams[i].split("|-|");
							if(param[1] == "SKU_EXCLUDED"){
								addExcludedOfSku("o${element.id}",param[0],param[2],param[3]);
							}
						}
					</c:if>
					
					
					//***initialize CartCategoryPercentDiscountAction
					<c:if test="${element.type == 'CartCategoryPercentDiscountAction'}">
						var category_id;
						var category;
						var category_title;
						var discount_percent_id;
						var discount_percent;
						var excludedParams = new Array();
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DISCOUNT_PERCENT'}){
								discount_percent_id = "${parameter.id}";
								discount_percent = "${parameter.paramValue}";
							}	
							if(${parameter.paramName == 'CATEGORY'}){
								category_id = "${parameter.id}";
								category = "${parameter.paramValue}";
								category_title = "${parameter.paramTitle}";
							}	
							
							if(${parameter.paramName == 'CATEGORY_EXCLUDED'|| parameter.paramName =='PRODUCT_EXCLUDED'|| parameter.paramName =='SKU_EXCLUDED'}){
								excludedParams.push("${parameter.id}"+"|-|"+"${parameter.paramName}"+"|-|"+"${parameter.paramValue}"+"|-|"+"${parameter.paramTitle}");
							}
						</c:forEach>
						addCartCategoryPercentDiscountAction("o${element.id}",category_id,category,category_title,discount_percent_id,discount_percent);
						for(var i=0; i< excludedParams.length; i++){
							var param = excludedParams[i].split("|-|");
							if(param[1] == "CATEGORY_EXCLUDED"){
								addExcludedOfCategory("o${element.id}",param[0],param[2],param[3]);
							}
							if(param[1] == "PRODUCT_EXCLUDED"){
								addExcludedOfProduct("o${element.id}",param[0],param[2],param[3]);
							}
							if(param[1] == "SKU_EXCLUDED"){
								addExcludedOfSku("o${element.id}",param[0],param[2],param[3]);
							}
						}
					</c:if>
					
					
					//***initialize CartCategoryAmountDiscountAction
					<c:if test="${element.type == 'CartCategoryAmountDiscountAction'}">
						var category_id;
						var category;
						var category_title;
						var discount_amount_id;
						var discount_amount;
						var excludedParams = new Array();
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DISCOUNT_AMOUNT'}){
								discount_amount_id = "${parameter.id}";
								discount_amount = "${parameter.paramValue}";
							}	
							if(${parameter.paramName == 'CATEGORY'}){
								category_id = "${parameter.id}";
								category = "${parameter.paramValue}";
								category_title = "${parameter.paramTitle}";
							}	
							
							if(${parameter.paramName == 'CATEGORY_EXCLUDED'|| parameter.paramName =='PRODUCT_EXCLUDED'|| parameter.paramName =='SKU_EXCLUDED'}){
								excludedParams.push("${parameter.id}"+"|-|"+"${parameter.paramName}"+"|-|"+"${parameter.paramValue}"+"|-|"+"${parameter.paramTitle}");
							}
						</c:forEach>
						addCartCategoryAmountDiscountAction("o${element.id}",category_id,category,category_title,discount_amount_id,discount_amount);
						for(var i=0; i< excludedParams.length; i++){
							var param = excludedParams[i].split("|-|");
							if(param[1] == "CATEGORY_EXCLUDED"){
								addExcludedOfCategory("o${element.id}",param[0],param[2],param[3]);
							}
							if(param[1] == "PRODUCT_EXCLUDED"){
								addExcludedOfProduct("o${element.id}",param[0],param[2],param[3]);
							}
							if(param[1] == "SKU_EXCLUDED"){
								addExcludedOfSku("o${element.id}",param[0],param[2],param[3]);
							}
						}
					</c:if>
					
					
					//***initialize CartSkuPercentDiscountAction
					<c:if test="${element.type == 'CartSkuPercentDiscountAction'}">
						var sku_id;
						var sku;
						var sku_title;
						var discount_percent_id;
						var discount_percent;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DISCOUNT_PERCENT'}){
								discount_percent_id = "${parameter.id}";
								discount_percent = "${parameter.paramValue}";
							}	
							if(${parameter.paramName == 'SKU'}){
								sku_id = "${parameter.id}";
								sku = "${parameter.paramValue}";
								sku_title = "${parameter.paramTitle}";
							}	
						</c:forEach>
						addCartSkuPercentDiscountAction("o${element.id}",sku_id,sku,sku_title,discount_percent_id,discount_percent);
						
					</c:if>
					
					
					//***initialize CartSkuAmountDiscountAction
					<c:if test="${element.type == 'CartSkuAmountDiscountAction'}">
						var sku_id;
						var sku;
						var sku_title;
						var discount_amount_id;
						var discount_amount;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DISCOUNT_AMOUNT'}){
								discount_amount_id = "${parameter.id}";
								discount_amount = "${parameter.paramValue}";
							}	
							if(${parameter.paramName == 'SKU'}){
								sku_id = "${parameter.id}";
								sku = "${parameter.paramValue}";
								sku_title = "${parameter.paramTitle}";
							}	
							
						</c:forEach>
						addCartSkuAmountDiscountAction("o${element.id}",sku_id,sku,sku_title,discount_amount_id,discount_amount);
						
					</c:if>
					
					
					//***initialize CartFreeSkuAction
					<c:if test="${element.type == 'CartFreeSkuAction'}">
						var sku_id;
						var sku;
						var sku_title;
						var discount_quantity_id;
						var discount_quantity;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DISCOUNT_QUANTITY'}){
								discount_quantity_id = "${parameter.id}";
								discount_quantity = "${parameter.paramValue}";
							}	
							if(${parameter.paramName == 'SKU'}){
								sku_id = "${parameter.id}";
								sku = "${parameter.paramValue}";
								sku_title = "${parameter.paramTitle}";
							}	
							
						</c:forEach>
						addCartFreeSkuAction("o${element.id}",sku_id,sku,sku_title,discount_quantity_id,discount_quantity);
						
					</c:if>
					
					
					//***initialize CartShippingFeePercentDiscountAction
					<c:if test="${element.type == 'CartShippingFeePercentDiscountAction'}">
						var shipping_method_id;
						var shipping_method;
						var shipping_method_title;
						var discount_percent_id;
						var discount_percent;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DISCOUNT_PERCENT'}){
								discount_percent_id = "${parameter.id}";
								discount_percent = "${parameter.paramValue}";
							}	
							if(${parameter.paramName == 'SHIPPING_METHOD'}){
								shipping_method_id = "${parameter.id}";
								shipping_method = "${parameter.paramValue}";
								shipping_method_title = "${parameter.paramTitle}";
							}	
						</c:forEach>
						addCartShippingFeePercentDiscountAction("o${element.id}",shipping_method_id,shipping_method,shipping_method_title,discount_percent_id,discount_percent);
						
					</c:if>
					
					
					//***initialize CartShippingFeeAmountDiscountAction
					<c:if test="${element.type == 'CartShippingFeeAmountDiscountAction'}">
						var shipping_method_id;
						var shipping_method;
						var shipping_method_title;
						var discount_amount_id;
						var discount_amount;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DISCOUNT_AMOUNT'}){
								discount_amount_id = "${parameter.id}";
								discount_amount = "${parameter.paramValue}";
							}	
							if(${parameter.paramName == 'SHIPPING_METHOD'}){
								shipping_method_id = "${parameter.id}";
								shipping_method = "${parameter.paramValue}";
								shipping_method_title = "${parameter.paramTitle}";
							}	
							
						</c:forEach>
						addCartShippingFeeAmountDiscountAction("o${element.id}",shipping_method_id,shipping_method,shipping_method_title,discount_amount_id,discount_amount);
						
					</c:if>
					
					
					//***initialize CartFixedPointDonateAction
					<c:if test="${element.type == 'CartFixedPointDonateAction'}">
						
						var point_id;
						var point;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'POINT'}){
								point_id = "${parameter.id}";
								point = "${parameter.paramValue}";
							}	
							
						</c:forEach>
						addCartFixedPointDonateAction("o${element.id}",point_id,point);
						
					</c:if>
					
					
					//***initialize CartTotalPercentPointDonateAction
					<c:if test="${element.type == 'CartTotalPercentPointDonateAction'}">
						
						var donate_percent_id;
						var donate_percent;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DONATE_PERCENT'}){
								donate_percent_id = "${parameter.id}";
								donate_percent = "${parameter.paramValue}";
							}	
						</c:forEach>
						addCartTotalPercentPointDonateAction("o${element.id}",donate_percent_id,donate_percent);
					</c:if>
					
					
					//***initialize CartProductPercentPointDonateAction
					<c:if test="${element.type == 'CartProductPercentPointDonateAction'}">
						var product_id;
						var product;
						var product_title;
						var donate_percent_id;
						var donate_percent;
						var excludedParams = new Array();
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DONATE_PERCENT'}){
								donate_percent_id = "${parameter.id}";
								donate_percent = "${parameter.paramValue}";
							}	
							if(${parameter.paramName == 'PRODUCT'}){
								product_id = "${parameter.id}";
								product = "${parameter.paramValue}";
								product_title = "${parameter.paramTitle}";
							}	
							
							if(${parameter.paramName == 'SKU_EXCLUDED'}){
								excludedParams.push("${parameter.id}"+"|-|"+"${parameter.paramName}"+"|-|"+"${parameter.paramValue}"+"|-|"+"${parameter.paramTitle}");
							}
						</c:forEach>
						addCartProductPercentPointDonateAction("o${element.id}",product_id,product,product_title,donate_percent_id,donate_percent);
						for(var i=0; i< excludedParams.length; i++){
							var param = excludedParams[i].split("|-|");
							if(param[1] == "SKU_EXCLUDED"){
								addExcludedOfSku("o${element.id}",param[0],param[2],param[3]);
							}
						}
					</c:if>
					
					
					//***initialize CartCategoryPercentPointDonateAction
					<c:if test="${element.type == 'CartCategoryPercentPointDonateAction'}">
						var category_id;
						var category;
						var category_title;
						var donate_percent_id;
						var donate_percent;
						var excludedParams = new Array();
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DONATE_PERCENT'}){
								donate_percent_id = "${parameter.id}";
								donate_percent = "${parameter.paramValue}";
							}	
							
							if(${parameter.paramName == 'CATEGORY'}){
								category_id = "${parameter.id}";
								category = "${parameter.paramValue}";
								category_title = "${parameter.paramTitle}";
							}	
							
							if(${parameter.paramName == 'CATEGORY_EXCLUDED'|| parameter.paramName =='PRODUCT_EXCLUDED'|| parameter.paramName =='SKU_EXCLUDED'}){
								excludedParams.push("${parameter.id}"+"|-|"+"${parameter.paramName}"+"|-|"+"${parameter.paramValue}"+"|-|"+"${parameter.paramTitle}");
							}
						</c:forEach>
						
						addCartCategoryPercentPointDonateAction("o${element.id}",category_id,category,category_title,donate_percent_id,donate_percent);
						for(var i=0; i< excludedParams.length; i++){
							var param = excludedParams[i].split("|-|");
							if(param[1] == "CATEGORY_EXCLUDED"){
								addExcludedOfCategory("o${element.id}",param[0],param[2],param[3]);
							}
							if(param[1] == "PRODUCT_EXCLUDED"){
								addExcludedOfProduct("o${element.id}",param[0],param[2],param[3]);
							}
							if(param[1] == "SKU_EXCLUDED"){
								addExcludedOfSku("o${element.id}",param[0],param[2],param[3]);
							}
						}
					</c:if>
					
					
					//***initialize CartSkuPercentPointDonateAction
					<c:if test="${element.type == 'CartSkuPercentPointDonateAction'}">
						var sku_id;
						var sku;
						var sku_title;
						var donate_percent_id;
						var donate_percent;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DONATE_PERCENT'}){
								donate_percent_id = "${parameter.id}";
								donate_percent = "${parameter.paramValue}";
							}	
							if(${parameter.paramName == 'SKU'}){
								sku_id = "${parameter.id}";
								sku = "${parameter.paramValue}";
								sku_title = "${parameter.paramTitle}"
							}	
						</c:forEach>
						addCartSkuPercentPointDonateAction("o${element.id}",sku_id,sku,sku_title,donate_percent_id,donate_percent);
						
					</c:if>
					
					
					//***initialize CartCouponDonateAction
					<c:if test="${element.type == 'CartCouponDonateAction'}">
						var coupon_type_id;
						var coupon_type;
						var coupon_type_title;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'COUPON_TYPE'}){
								coupon_type_id = "${parameter.id}";
								coupon_type = "${parameter.paramValue}";
								coupon_type_title = "${parameter.paramTitle}";
							}	
						</c:forEach>
						addCartCouponDonateAction("o${element.id}",coupon_type_id,coupon_type,coupon_type_title);
						
					</c:if>
					
					
					
					//***initialize CatalogSkuPercentDiscountAction
					<c:if test="${element.type == 'CatalogSkuPercentDiscountAction'}">
						
						var discount_percent_id;
						var discount_percent;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DISCOUNT_PERCENT'}){
								discount_percent_id = "${parameter.id}";
								discount_percent = "${parameter.paramValue}";
							}	
						</c:forEach>
						addCatalogSkuPercentDiscountAction("o${element.id}",discount_percent_id,discount_percent);
					</c:if>
					
					//***initialize CatalogSkuAmountDiscountAction
					<c:if test="${element.type == 'CatalogSkuAmountDiscountAction'}">
						
						var discount_amount_id;
						var discount_amount;
						<c:forEach items="${element.promoRuleParameters}" var="parameter">
							if(${parameter.paramName == 'DISCOUNT_AMOUNT'}){
								discount_amount_id = "${parameter.id}";
								discount_amount = "${parameter.paramValue}";
							}	
						</c:forEach>
						addCatalogSkuAmountDiscountAction("o${element.id}",discount_amount_id,discount_amount);
					</c:if>
					
				</c:if>
				
			</c:forEach>
			
		  
  		});
  		
  		
  		
  		
</script>





