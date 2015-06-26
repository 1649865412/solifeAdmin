
/**
 * 
 * @author CartMatic-Patrick
 *
 */
/** --------word comment [!!!read recommend!!!] --------**/
/**
old element ---- element which is existed in database already and its id is prefixed by 'o' in page
new element ---- element which is add in page and its id is prefixed by 'e'

old parameter --- parameter which is existed in database already and has none prefix
new parameter --- parameter which is add in page and its id is prefixed by 'p'


id  --- the only flage of element/parameter, it may comes from database item record (if it is 'old') when we call it 'id in db', or from the auto-increment function (if it is 'new') when we call it 'tempory id' or 'id in page'.
		If the form is commit in ajax method, all the 'temporary id' should be changed to 'id in db'. 
**/

/** ------------------------------------ resource --------------------------**/
var imageOfRemove = "../images/default/dd/drop-remove.gif";
var imageOfAdd = "../images/default/dd/drop-add.gif";

/** ------------------------------------ variables --------------------------**/ 
//the promoType
var promoType ; 			
//the new element id is assigned by auto-increment   			
var newElementId = 0;

//the new param id is assigned by auto-increment  			
var newParamId = 0;

//record those elements deleted which is existed in database before  			
var deleteElementArray = new Array();

//record those params  deleted which is existed in database before			
var deleteParamArray = new Array();

//record membership list
var membershipIdArray = new Array();
var membershipNameArray = new Array();

//record brand list
var brandIdArray = new Array();
var brandNameArray = new Array();

//record shippingMethod list
var shippingMethodIdArray = new Array();
var shippingMethodNameArray = new Array();

//record couponType list
var couponTypeIdArray = new Array();
var couponTypeNameArray = new Array();

//this can avoid the menu display problem
var zIndex = 70;


/** ------------------------------------ test --------------------------**/ 
//for test


function test() {
	
	
}



/** -------------------------------------- page load --------------------------------------**/ 

//set focus
//document.forms['promoRule'].elements['name'].focus();

//initialize the menus 
$j(document).ready(function () {
	$j('#conditionMenu').droppy({speed:0});
	$j('#actionMenu').droppy({speed:0});
	$j('#eligibilityMenu').droppy({speed:0});
	$j("#ruleDetail").show();
});


function getMembershipsCallback(result){
	var data=result.data;
	for(var i=0; i< data.length; i++){
		membershipIdArray.push(data[i].membershipId);
		membershipNameArray.push(data[i].membershipName);
	}
}

function getBrandsCallback(result){
	var data=result.data;
	for(var i=0; i< data.length; i++){
		brandIdArray.push(data[i].brandId);
		brandNameArray.push(data[i].brandName);
	}
}

function getShippingMethodsCallback(result){
	if(result.status==-500){
		alert("出错了");
	}else{
		var data=result.data;
		for(var i=0; i< data.length; i++){
			shippingMethodIdArray.push(data[i].shippingMethodId);
			shippingMethodNameArray.push(data[i].shippingMethodName);
		}
	}
}

function getCouponTypesCallback(result){
	var data=result.data;
	for(var i=0; i< data.length; i++){
		couponTypeIdArray.push(data[i].couponTypeId);
		couponTypeNameArray.push(data[i].couponTypeName);
	}
}

/** --------------------------- method of selector's callback----------------------**/ 
function getProduct(product,id){
	$j('#'+id).attr("value",product.productId);
	$j('#t'+id).attr("size",getLen(product.productName));
	$j('#t'+id).attr("value",product.productName);
}

function getProductSku(productSku,id){
	$j('#'+id).attr("value",productSku.productSkuId);
	$j('#t'+id).attr("size",getLen(productSku.productSkuCode));
	$j('#t'+id).attr("value",productSku.productSkuCode);
}

function getCategory(category,id){
	$j('#'+id).attr("value",category.categoryId);
	$j('#t'+id).attr("size",getLen(category.name));
	$j('#t'+id).attr("value",category.name);
}

/** --------------------------- method of closing menu----------------------**/ 
function closeMenu(menu){
	$j('#'+menu+' > li>ul').hide('fast');
}

  			
/** --------------------------- method of construction and initialization----------------------**/ 

//initilize the excludedParamMenu
function initExcludedParamMenu(id) {
	$j(function () {
		$j("#" + id).droppy({speed:0});
	});
}

// add category excludedParam
function addExcludedOfCategory(parentId, childId, category,category_title) {
	if( childId == "-1"){
		childId = "p"+(newParamId++);
	}
	$j('#' + parentId).append(getExcludedParamBegin(childId)+pmsg.Excluding_category + getCategoyExcludedSection(childId,category,category_title)+getExcludedParamEnd());
	applyValidate($(childId),"required");
}

// add product excludedParam
function addExcludedOfProduct(parentId, childId, product, product_title) {
	if( childId == "-1"){
		childId = "p"+(newParamId++);
	}
	$j('#' + parentId).append(getExcludedParamBegin(childId)+pmsg.Excluding_product + getProductExcludedSection(childId,product,product_title)+getExcludedParamEnd());
	applyValidate($(childId),"required");
}

// add sku excludedParam
function addExcludedOfSku(parentId, childId, sku, sku_title) {
	if( childId == "-1"){
		childId = "p"+(newParamId++);
	}
	$j('#' + parentId).append(getExcludedParamBegin(childId)+pmsg.Excluding_sku + getSkuExcludedSection(childId,sku,sku_title)+getExcludedParamEnd());
	applyValidate($(childId),"required");
}

function getExcludedParamBegin(childId){
	return '<ul id="excludedParam' + childId + '" class="excludedPara"><img id="img' + childId + '" src="'+imageOfRemove+'" onclick=\'removeParam("' + childId + '")\'/>  ';
}

function getExcludedParamEnd(){
	return '</ul>';
}

//add excludedParam Menu of Category
function addExcludedParamCategoryMenu(id) {
	$j('#' + id).append(getParameterExcludedSectionBegin(id)+getParameterCategoryExcludedSection(id)+getParameterProductExcludedSection(id)+getParameterSkuExcludedSection(id)+getParameterExcludedSectionEnd());
	$j('#' + id).append('<div style="clear:left"></div>');
	initExcludedParamMenu("excludedParamMenu" + id);
}

//add excludedParam Menu of Product
function addExcludedParamProductMenu(id) {
	$j('#' + id).append(getParameterExcludedSectionBegin(id)+getParameterSkuExcludedSection(id)+getParameterExcludedSectionEnd());
	$j('#' + id).append('<div style="clear:left"></div>');
	initExcludedParamMenu("excludedParamMenu" + id);
}

function getParameterExcludedSectionBegin(id){
	return '<ul class="excludedParameterList excludedParameterListB" style="z-index:'+(zIndex--)+';" id="excludedParamMenu' + id + '"><li class="noHover"><img src="'+imageOfAdd+'" />'+pmsg.Add+'<ul>';
}

function getParameterCategoryExcludedSection(id){
	return '<li><a onclick=\'addExcludedOfCategory("' + id + '",-1,"","");closeMenu("excludedParamMenu'+id+'")\'><img src="'+imageOfAdd+'">'+pmsg.Excluding_category+'</a></li>';
}

function getParameterProductExcludedSection(id){
	return '<li><a onclick=\'addExcludedOfProduct("' + id + '",-1,"","");closeMenu("excludedParamMenu'+id+'")\'><img src="'+imageOfAdd+'">'+pmsg.Excluding_product+'</a></li>';
}

function getParameterSkuExcludedSection(id){
	return '<li><a onclick=\'addExcludedOfSku("' + id + '",-1,"","");closeMenu("excludedParamMenu'+id+'")\'> <img src="'+imageOfAdd+'">'+pmsg.Excluding_sku+' </a>';
}

function getParameterExcludedSectionEnd(){
	return '</ul></li></ul>';
}


function getProductSection(product_id,product,product_title){
	var section ="";
	var title = product_title;
	if(title == ''){
		title = pmsg.Choose;
	}
	section = '<input class="choose" readonly="true" onclick="productSelector_show(\''+product_id+'\')" title="'+pmsg.click_product_tip+'" id="t'+product_id+'" name="PRODUCT_TITLE" value="'+title+'" size="'+getLen(title)+'"></input>';
	return section+ '<var><input type="hidden" name="PRODUCT" id="'  + product_id + '" value="' + product + '"></input></var>';
}

function getProductExcludedSection(product_id,product,product_title){
	var section ="";
	var title = product_title;
	if(title == ''){
		title = pmsg.Choose;
	}
	section = '<input class="choose" readonly="true" onclick="productSelector_show(\''+product_id+'\')" title="'+pmsg.click_product_tip+'" id="t'+product_id+'" name="PRODUCT_TITLE" value="'+title+'" size="'+getLen(title)+'"></input>';
	return section+ '<var><input type="hidden" name="PRODUCT_EXCLUDED" id="'  + product_id + '" value="' + product + '"></input></var>';
}

function getCategoySection(category_id,category,category_title){
	var section ="";
	var title = category_title;
	if(title == ''){
		title = pmsg.Choose;
	}
	section = '<input class="choose"  readonly="true" onclick="categorySelector_show(\''+category_id+'\')" title="'+pmsg.click_category_tip+'" id="t'+category_id+'" name="CATEGORY_TITLE"  value="'+title+'" size="'+getLen(title)+'"></input>';
	
	return section+ '<var><input type="hidden" name="CATEGORY" id="'  + category_id + '" value="' + category + '"></input></var>';
}

function getCategoyExcludedSection(category_id,category,category_title){
	var section ="";
	var title = category_title;
	if(title == ''){
		title = pmsg.Choose;
	}
	section = '<input class="choose" readonly="true" onclick="categorySelector_show(\''+category_id+'\')" title="'+pmsg.click_category_tip+'" id="t'+category_id+'" name="CATEGORY_TITLE"  value="'+title+'" size="'+getLen(title)+'"></input>';
	
	return section+ '<var><input type="hidden" name="CATEGORY_EXCLUDED" id="'  + category_id + '" value="' + category + '"></input></var>';
}

function getSkuSection(sku_id,sku,sku_title){
	var section ="";
	var title = sku_title;
	if(title == ''){
		title = pmsg.Choose;
	}
	section = '<input class="choose" readonly="true" onclick="productSkuSelector_show(\''+sku_id+'\')" title="'+pmsg.click_sku_tip+'" id="t'+sku_id+'" name="SKU_TITLE" value="'+title+'" size="'+getLen(title)+'"></input>';
	return section+ '<var><input type="hidden" name="SKU" id="'  + sku_id + '" value="' + sku + '"></input></var>';
}

function getSkuExcludedSection(sku_id,sku,sku_title){
	var section ="";
	var title = sku_title;
	if(title == ''){
		title = pmsg.Choose;
	}
	section = '<input class="choose" readonly="true" onclick="productSkuSelector_show(\''+sku_id+'\')" title="'+pmsg.click_sku_tip+'" id="t'+sku_id+'" name="SKU_TITLE" value="'+title+'" size="'+getLen(title)+'"></input>';
	return section+ '<var><input type="hidden" name="SKU_EXCLUDED" id="'  + sku_id + '" value="' + sku + '"></input></var>';
}



function getElementSectionBegin(id,type){
	return '<li id="' + id + '" name="'+type+'"><span>';
}
function getElementSectionFloatBegin(id,type){
	return '<li id="' + id + '" name="'+type+'"><span style="float:left;">';
}

function getElementSectionEnd(){
	return '</span> </li>';
}
function getElementImageSection(id){
	return '<img id="img' + id + '" src="'+imageOfRemove+'" onclick=\'removeElement("' + id + '")\'/> ';
}

function getMembershipSection(membership_id,membership,membership_title){
	var memberships = '';
	var pleaseSeclectSection = '<option value="">'+pmsg.please_select+'</option>';	
	var selected ='';
	for(var i=0; i < membershipIdArray.length; i++){
		selected = isSelected(membershipIdArray[i], membership);
		memberships += '<option value="'+membershipIdArray[i]+'"' + selected + '>'+membershipNameArray[i]+'</option>';
		
	}
	return ' <var><select name="MEMBERSHIP" id="' + membership_id + '">'+pleaseSeclectSection+memberships+'</select></var>';
	
}

function getBrandSection(brand_id,brand,brand_title){
	var brands = '';
	var pleaseSeclectSection = '<option value="">'+pmsg.please_select+'</option>';	
	var selected ='';
	for(var i=0; i < brandIdArray.length; i++){
		selected = isSelected(brandIdArray[i], brand);
		brands += '<option value="'+brandIdArray[i]+'"' + selected + '>'+brandNameArray[i]+'</option>';
	}
	return ' <var><select name="BRAND" id="' + brand_id + '">'+pleaseSeclectSection+brands+'</select></var>';
}


function getShippingMethodSection(shipping_method_id,shipping_method,shipping_method_title){
	var shippingMethods = '';
	var pleaseSeclectSection = '<option value="">'+pmsg.please_select+'</option>';	
	var selected ='';
	for(var i=0; i < shippingMethodIdArray.length; i++){
		selected = isSelected(shippingMethodIdArray[i], shipping_method);
		shippingMethods += '<option value="'+shippingMethodIdArray[i]+'"' + selected + '>'+shippingMethodNameArray[i]+'</option>';
	}
	return ' <var><select name="SHIPPING_METHOD" id="' + shipping_method_id + '">'+pleaseSeclectSection+shippingMethods+'</select></var>';
}


function getCouponTypeSection(coupon_type_id, coupon_type,coupon_type_title){
	var couponTypes = '';
	var pleaseSeclectSection = '<option value="">'+pmsg.please_select+'</option>';	
	var selected ='';
	for(var i=0; i < couponTypeIdArray.length; i++){
		selected = isSelected(couponTypeIdArray[i], coupon_type);
		couponTypes += '<option value="'+couponTypeIdArray[i]+'"' + selected + '>'+couponTypeNameArray[i]+'</option>';
	}
	return ' <var><select name="COUPON_TYPE" id="' + coupon_type_id + '">'+pleaseSeclectSection+couponTypes+'</select></var>';
}



function getTotalAmountSection(total_amount_id,total_amount){
	return '<var><input type="text" name="TOTAL_AMOUNT" id="' + total_amount_id + '"  value="' + total_amount + '" ></input></var>';
}

function getNumitemsQuantifierSection(numitems_quantifier_id,numitems_quantifier){
	return ' <select name="NUMITEMS_QUANTIFIER" id="'  + numitems_quantifier_id + '"><option value="1"' + isSelected("1", numitems_quantifier) + '> >= </option><option value="0" '+ isSelected("0", numitems_quantifier)+ '> =</option><option value="-1" ' + isSelected("-1", numitems_quantifier) + '><=</option></select>'
}
function getNumitemsSection(numitems_id,numitems){
	return ' <var><input type="text" name="NUMITEMS" id="'  + numitems_id + '" value="' + numitems + '"</input></var>';
}

function getDiscountPercentSection(discount_percent_id,discount_percent){
	return ' <var><input type="text" name="DISCOUNT_PERCENT" id="'  + discount_percent_id + '" value="' + discount_percent + '"></input></var>%';
}

function getDiscountAmountSection(discount_amount_id,discount_amount){
	return ' $ <var><input type="text" name="DISCOUNT_AMOUNT" id="'  + discount_amount_id + '" value="' + discount_amount + '"></input></var>';
}

function getDiscountQuantitySection(discount_quantity_id,discount_quantity){ 
	return ' <var><input type="text" name="DISCOUNT_QUANTITY" id="'  + discount_quantity_id + '" value="' + discount_quantity + '"></input></var> ';
}
function getPointSection(point_id,point){
	return ' <var><input type="text" name="POINT" id="'  + point_id + '" value="' + point + '"></input></var>';
}
function getDonatePercentSection(donate_percent_id,donate_percent){
	return ' <var><input type="text" name="DONATE_PERCENT" id="'  + donate_percent_id + '" value="' + donate_percent + '"></input></var>%';
}

/** ------------eligbilities-----------------**/ 
function addEveryoneEligibility(id) {
	$j('#myEligibility').append(getElementSectionBegin(id,"EveryoneEligibility")+getElementImageSection(id)+pmsg.EveryoneEligibility_t0+getElementSectionEnd());
	validateEligibilityNums();
}
function addFirstTimeBuyerEligibility(id) {
	$j('#myEligibility').append(getElementSectionBegin(id,"FirstTimeBuyerEligibility")+getElementImageSection(id)+pmsg.FirstimeBuyerEligibility_t0+getElementSectionEnd());
	validateEligibilityNums();
}
function addMembershipEligibility(id, membership_id, membership,membership_title) {
	$j('#myEligibility').append(getElementSectionBegin(id,"MembershipEligibility")+getElementImageSection(id)+pmsg.MembershipEligibility_t0+getMembershipSection(membership_id,membership,membership_title)+getElementSectionEnd());
	applyValidate($(membership_id),"required");
	validateEligibilityNums();
}



/** ------------conditions-----------------**/ 
function addCartTotalCondition(id, total_amount_id, total_amount) {
	$j('#myCondition').append(getElementSectionBegin(id,"CartTotalCondition")+getElementImageSection(id)+pmsg.CartTotalCondition_t0+' >= '+getTotalAmountSection(total_amount_id,total_amount) +getElementSectionEnd());
	applyValidate($(total_amount_id),"required");
	validateConditionNums();
}
function addCartAnySkuInCartCondition(id, numitems_id, numitems, numitems_quantifier_id, numitems_quantifier) {
	$j('#myCondition').append(getElementSectionBegin(id,"CartAnySkuInCartCondition")+getElementImageSection(id)+pmsg.CartAnySkuInCartCondition_t0+ getNumitemsQuantifierSection(numitems_quantifier_id,numitems_quantifier)+getNumitemsSection(numitems_id,numitems)+getElementSectionEnd());
	applyValidate($(numitems_id),"required,integer");
	validateConditionNums();
}
function addCartContainsItemsOfCategoryCondition(id, category_id, category,category_title, numitems_id, numitems, numitems_quantifier_id, numitems_quantifier) {
	$j('#myCondition').append(getElementSectionFloatBegin(id,"CartContainsItemsOfCategoryCondition")+getElementImageSection(id)+pmsg.CartContainsItemsOfCategoryCondition_t0+getCategoySection(category_id,category,category_title)+pmsg.CartContainsItemsOfCategoryCondition_t1+getNumitemsQuantifierSection(numitems_quantifier_id,numitems_quantifier)+getNumitemsSection(numitems_id,numitems)+pmsg.Excluding +getElementSectionEnd());
	addExcludedParamCategoryMenu(id);
	applyValidate($(category_id),"required");
	applyValidate($(numitems_id),"required,integer");
	validateConditionNums();
}
function addCartProductInCartCondition(id, product_id, product, product_title,numitems_id, numitems, numitems_quantifier_id, numitems_quantifier) {
	$j('#myCondition').append(getElementSectionFloatBegin(id,"CartProductInCartCondition")+getElementImageSection(id)+pmsg.CartProductInCartCondition_t0+getProductSection(product_id,product,product_title)+pmsg.CartProductInCartCondition_t1+getNumitemsQuantifierSection(numitems_quantifier_id,numitems_quantifier)+getNumitemsSection(numitems_id,numitems)+pmsg.Excluding +getElementSectionEnd());
	addExcludedParamProductMenu(id);
	applyValidate($(product_id),"required");
	applyValidate($(numitems_id),"required,integer");
	validateConditionNums();
}
function addCartSkuInCartCondition(id, sku_id, sku, sku_title,numitems_id, numitems, numitems_quantifier_id, numitems_quantifier) {
	$j('#myCondition').append(getElementSectionBegin(id,"CartSkuInCartCondition")+getElementImageSection(id)+pmsg.CartSkuInCartCondition_t0+getSkuSection(sku_id,sku,sku_title)+pmsg.CartSkuInCartCondition_t1+getNumitemsQuantifierSection(numitems_quantifier_id,numitems_quantifier)+getNumitemsSection(numitems_id,numitems)+getElementSectionEnd());
	applyValidate($(sku_id),"required");
	applyValidate($(numitems_id),"required,integer");
	validateConditionNums();
}

function addCatalogIsProductCondition(id, product_id, product,product_title) {
	$j('#myCondition').append(getElementSectionBegin(id,"CatalogIsProductCondition")+getElementImageSection(id)+pmsg.CatalogIsProductCondition_t0+getProductSection(product_id,product,product_title)+getElementSectionEnd());
	applyValidate($(product_id),"required");
	validateConditionNums();
}

function addCatalogInCategoryCondition(id, category_id, category,category_title) {
	$j('#myCondition').append(getElementSectionFloatBegin(id,"CatalogInCategoryCondition")+getElementImageSection(id)+pmsg.CatalogInCategoryCondition_t0+getCategoySection(category_id,category,category_title)+"  "+pmsg.Excluding+getElementSectionEnd());
	applyValidate($(category_id),"required");
	addExcludedParamCategoryMenu(id);
	validateConditionNums();
}

function addCatalogIsBrandCondition(id, brand_id, brand,brand_title) {
	$j('#myCondition').append(getElementSectionBegin(id,"CatalogIsBrandCondition")+getElementImageSection(id)+pmsg.CatalogIsBrandCondition_t0+getBrandSection(brand_id,brand,brand_title)+getElementSectionEnd());
	applyValidate($(brand_id),"required");
	validateConditionNums();
}

/** ------------actions-----------------**/ 
function addCartTotalPercentDiscountAction(id, discount_percent_id, discount_percent) {
	$j('#myAction').append(getElementSectionBegin(id,"CartTotalPercentDiscountAction")+getElementImageSection(id)+pmsg.CartTotalPercentDiscountAction_t0+getDiscountPercentSection(discount_percent_id,discount_percent)+getElementSectionEnd());
	applyValidate($(discount_percent_id),"required,integer,minValue=0,maxValue=100");
	validateActionNums();
}
function addCartTotalAmountDiscountAction(id, discount_amount_id, discount_amount) {
	$j('#myAction').append(getElementSectionBegin(id,"CartTotalAmountDiscountAction")+getElementImageSection(id)+pmsg.CartTotalAmountDiscountAction_t0+getDiscountAmountSection(discount_amount_id,discount_amount)+getElementSectionEnd());
	applyValidate($(discount_amount_id),"required,price");
	validateActionNums();
}

function addCartProductPercentDiscountAction(id, product_id, product,product_title, discount_percent_id, discount_percent) {
	$j('#myAction').append(getElementSectionFloatBegin(id,"CartProductPercentDiscountAction")+getElementImageSection(id)+pmsg.CartProductPercentDiscountAction_t0 + getProductSection(product_id,product,product_title)+pmsg.CartProductPercentDiscountAction_t1+getDiscountPercentSection(discount_percent_id,discount_percent)+pmsg.Excluding+getElementSectionEnd());
	addExcludedParamProductMenu(id);
	applyValidate($(product_id),"required");
	applyValidate($(discount_percent_id),"required,integer,minValue=0,maxValue=100");
	validateActionNums();
}

function addCartProductAmountDiscountAction(id, product_id, product,product_title ,discount_amount_id, discount_amount) {
	$j('#myAction').append(getElementSectionFloatBegin(id,"CartProductAmountDiscountAction")+getElementImageSection(id)+pmsg.CartProductAmountDiscountAction_t0 +getProductSection(product_id,product,product_title)+pmsg.CartProductAmountDiscountAction_t1+getDiscountAmountSection(discount_amount_id,discount_amount)+pmsg.Excluding+getElementSectionEnd());
	addExcludedParamProductMenu(id);
	applyValidate($(product_id),"required");
	applyValidate($(discount_amount_id),"required,price");
	validateActionNums();
}

function addCartCategoryPercentDiscountAction(id, category_id, category, category_title,discount_percent_id, discount_percent) {
	$j('#myAction').append(getElementSectionFloatBegin(id,"CartCategoryPercentDiscountAction")+getElementImageSection(id)+pmsg.CartCategoryPercentDiscountAction_t0 +getCategoySection(category_id,category,category_title)+pmsg.CartCategoryPercentDiscountAction_t1+getDiscountPercentSection(discount_percent_id,discount_percent)+pmsg.Excluding+getElementSectionEnd());
	addExcludedParamCategoryMenu(id);
	applyValidate($(category_id),"required");
	applyValidate($(discount_percent_id),"required,integer,minValue=0,maxValue=100");
	validateActionNums();
}

function addCartCategoryAmountDiscountAction(id, category_id, category, category_title,discount_amount_id, discount_amount) {
	$j('#myAction').append(getElementSectionFloatBegin(id,"CartCategoryAmountDiscountAction")+getElementImageSection(id)+pmsg.CartCategoryAmountDiscountAction_t0 +getCategoySection(category_id,category,category_title)+pmsg.CartCategoryAmountDiscountAction_t1+getDiscountAmountSection(discount_amount_id,discount_amount)+pmsg.Excluding+getElementSectionEnd());
	addExcludedParamCategoryMenu(id);
	applyValidate($(category_id),"required");
	applyValidate($(discount_amount_id),"required,price");
	validateActionNums();
}

function addCartSkuPercentDiscountAction(id, sku_id, sku, sku_title, discount_percent_id, discount_percent) {
	$j('#myAction').append(getElementSectionBegin(id,"CartSkuPercentDiscountAction")+getElementImageSection(id)+pmsg.CartSkuPercentDiscountAction_t0 +getSkuSection(sku_id,sku,sku_title)+pmsg.CartSkuPercentDiscountAction_t1+getDiscountPercentSection(discount_percent_id,discount_percent) +getElementSectionEnd());
	applyValidate($(sku_id),"required");
	applyValidate($(discount_percent_id),"required,integer,minValue=0,maxValue=100");
	validateActionNums();
}

function addCartSkuAmountDiscountAction(id, sku_id, sku, sku_title, discount_amount_id, discount_amount) {
	$j('#myAction').append(getElementSectionBegin(id,"CartSkuAmountDiscountAction")+getElementImageSection(id)+pmsg.CartSkuAmountDiscountAction_t0 +getSkuSection(sku_id,sku,sku_title)+pmsg.CartSkuAmountDiscountAction_t1+getDiscountAmountSection(discount_amount_id,discount_amount) +getElementSectionEnd());
	applyValidate($(sku_id),"required");
	applyValidate($(discount_amount_id),"required,price");
	validateActionNums();
}

function addCartFreeSkuAction(id, sku_id, sku, sku_title, discount_quantity_id, discount_quantity) {
	$j('#myAction').append(getElementSectionBegin(id,"CartFreeSkuAction")+getElementImageSection(id)+pmsg.CartFreeSkuAction_t0 +getSkuSection(sku_id,sku,sku_title)+pmsg.CartFreeSkuAction_t1+getDiscountQuantitySection(discount_quantity_id,discount_quantity) +getElementSectionEnd());
	applyValidate($(sku_id),"required");
	applyValidate($(discount_quantity_id),"required,integer,maxValue=1000");
	validateActionNums();
	
}

function addCartShippingFeePercentDiscountAction(id, shipping_method_id, shipping_method, shipping_method_title, discount_percent_id, discount_percent) {
	$j('#myAction').append(getElementSectionBegin(id,"CartShippingFeePercentDiscountAction")+getElementImageSection(id)+pmsg.CartShippingFeePercentDiscountAction_t0 +getShippingMethodSection(shipping_method_id,shipping_method,shipping_method_title)+pmsg.CartShippingFeePercentDiscountAction_t1+getDiscountPercentSection(discount_percent_id,discount_percent)+getElementSectionEnd());
	applyValidate($(discount_percent_id),"required,integer,minValue=0,maxValue=100");
	applyValidate($(shipping_method_id),"required");
	validateActionNums();
}

function addCartShippingFeeAmountDiscountAction(id, shipping_method_id, shipping_method, shipping_method_title, discount_amount_id, discount_amount) {
	$j('#myAction').append(getElementSectionBegin(id,"CartShippingFeeAmountDiscountAction")+getElementImageSection(id)+pmsg.CartShippingFeeAmountDiscountAction_t0 +getShippingMethodSection(shipping_method_id,shipping_method,shipping_method_title)+pmsg.CartShippingFeeAmountDiscountAction_t1+getDiscountAmountSection(discount_amount_id,discount_amount)+getElementSectionEnd());
	applyValidate($(discount_amount_id),"required,price");
	applyValidate($(shipping_method_id),"required");
	validateActionNums();
}

function addCartFixedPointDonateAction(id, point_id, point) {
	$j('#myAction').append(getElementSectionBegin(id,"CartFixedPointDonateAction")+getElementImageSection(id)+pmsg.CartFixedPointDonateAction_t0 + getPointSection(point_id,point)+getElementSectionEnd());
	applyValidate($(point_id),"required,integer,maxValue=30000");
	validateActionNums();
	
}

function addCartTotalPercentPointDonateAction(id, donate_percent_id, donate_percent) {
	$j('#myAction').append(getElementSectionBegin(id,"CartTotalPercentPointDonateAction")+getElementImageSection(id)+pmsg.CartTotalPercentPointDonateAction_t0+getDonatePercentSection(donate_percent_id,donate_percent)+getElementSectionEnd());
	applyValidate($(donate_percent_id),"required,integer,minValue=0,maxValue=100");
	validateActionNums();
}

function addCartProductPercentPointDonateAction(id, product_id, product, product_title, donate_percent_id, donate_percent) {
	$j('#myAction').append(getElementSectionFloatBegin(id,"CartProductPercentPointDonateAction")+getElementImageSection(id)+pmsg.CartProductPercentPointDonateAction_t0 +getProductSection(product_id,product,product_title)+pmsg.CartProductPercentPointDonateAction_t1+getDonatePercentSection(donate_percent_id,donate_percent)+pmsg.Excluding+getElementSectionEnd());
	addExcludedParamProductMenu(id);
	applyValidate($(product_id),"required");
	applyValidate($(donate_percent_id),"required,integer,minValue=0,maxValue=100");
	validateActionNums();
}

function addCartCategoryPercentPointDonateAction(id, category_id, category,category_title, donate_percent_id, donate_percent) {
	$j('#myAction').append(getElementSectionFloatBegin(id,"CartCategoryPercentPointDonateAction")+getElementImageSection(id)+pmsg.CartCategoryPercentPointDonateAction_t0 +getCategoySection(category_id,category,category_title)+pmsg.CartCategoryPercentPointDonateAction_t1+getDonatePercentSection(donate_percent_id,donate_percent)+pmsg.Excluding+getElementSectionEnd());
	addExcludedParamCategoryMenu(id);
	applyValidate($(category_id),"required");
	applyValidate($(donate_percent_id),"required,integer,minValue=0,maxValue=100");
	validateActionNums();
}

function addCartSkuPercentPointDonateAction(id, sku_id, sku, sku_title, donate_percent_id, donate_percent) {
	$j('#myAction').append(getElementSectionBegin(id,"CartSkuPercentPointDonateAction")+getElementImageSection(id)+pmsg.CartSkuPercentPointDonateAction_t0 +getSkuSection(sku_id,sku,sku_title)+pmsg.CartSkuPercentPointDonateAction_t1+getDonatePercentSection(donate_percent_id,donate_percent) +getElementSectionEnd());
	applyValidate($(sku_id),"required");
	applyValidate($(donate_percent_id),"required,integer,minValue=0,maxValue=100");
	validateActionNums();
}


function addCartCouponDonateAction(id, coupon_type_id, coupon_type,coupon_type_title) {
	$j('#myAction').append(getElementSectionBegin(id,"CartCouponDonateAction")+getElementImageSection(id)+pmsg.CartCouponDonateAction_t0 +pmsg.CartCouponDonateAction_t1 +getCouponTypeSection(coupon_type_id, coupon_type,coupon_type_title)+getElementSectionEnd());
	applyValidate($(coupon_type_id),"required");
	validateActionNums();
}



function addCatalogSkuPercentDiscountAction(id, discount_percent_id, discount_percent) {
	$j('#myAction').append(getElementSectionBegin(id,"CatalogSkuPercentDiscountAction")+getElementImageSection(id)+pmsg.CatalogSkuPercentDiscountAction_t0+getDiscountPercentSection(discount_percent_id,discount_percent)+getElementSectionEnd());
	applyValidate($(discount_percent_id),"required,integer,minValue=0,maxValue=100");
	validateActionNums();
}
function addCatalogSkuAmountDiscountAction(id, discount_amount_id, discount_amount) {
	$j('#myAction').append(getElementSectionBegin(id,"CatalogSkuAmountDiscountAction")+getElementImageSection(id)+pmsg.CatalogSkuAmountDiscountAction_t0+getDiscountAmountSection(discount_amount_id,discount_amount)+getElementSectionEnd());
	applyValidate($(discount_amount_id),"required,price");
	validateActionNums();
}

/** ------------------------------------ method of remove  --------------------------------**/ 
//remove the element    			
function removeElement(id) {
	if (id.indexOf("o") != -1) {
  		//it is an old element ,so it should be record and used in save/update function when ajax request is post .
		deleteElementArray.push(id);
	} else {
	}
  	//remove it from page
	$j('#' + id).remove();
	
	//validate nums
	if(promoType == "shoppingcartPromotion" | promoType == "couponPromotion" ){
		validateEligibilityNums();
		validateConditionNums();
		validateActionNums();
	}else{
		validateConditionNums();
		validateActionNums();
	}	
}

//remove the parameter
function removeParam(id) {
	if (id.indexOf("p") != -1) {
	} else {
		//it is a new parameter, so it should be record and used in save/update function when ajax request is post .
		deleteParamArray.push(id);
	}
	//removed it from  page
	$j('#excludedParam' + id).remove();
}

/** ------------------------------------ method of util ---------------------------------**/ 

//return true if the 'endTime' is later than 'startTime'    
function checkEndTime(){
   	if (fnCompareDate($("startTime").value,$("endTime").value) >0 | (fnCompareDate($("startTime").value,$("endTime").value) == 0 && Number($("startHour").value) >= Number($("endHour").value))){
   		$j('#endTime').focus();
   		alert(pmsg.validate_endTimeGtStartTime);
   		return false;
   	}else{
   		return true;
   	}
}

//persist the value of enableDiscount
function ChangeValueOfEnableDiscountAgain(checkbox,persistInput)
{
    if (checkbox.checked==true)
    {
       	$(persistInput).value = 1;
    }
    else
    {
      	$(persistInput).value = 0;
    }
}

//persist the value of status
function ChangeValueOfStatus(checkbox,persistInput)
{
    if (checkbox.checked==true)
    {
      	$(persistInput).value = 1;
    }
    else
    {
      	$(persistInput).value = 2;
    }
}


//return String("selected") if i == j
function isSelected(i, j) {
	if (i == j) {
		return "selected";
	} else {
		return "";
	}
}

//return the  id in page ('id in page')    
function getIdInPage(updateString){
   	return updateString.substring(0,updateString.indexOf('~'));
}

//return the  id in database ('id in db')    
function getIdInDB(updateString){
   	return updateString.substring(updateString.indexOf('~')+1);
}

//get the string length to handler the chinese char problem 
function getLen( str ) { 
	var len=4;
	var char_;
	if(str != null){
		for(var i=0;i<str.length;i++) { 
			char_ = str.charCodeAt(i); 
   			if(!(char_>255)) { 
     			len = len + 1; 
  			} else { 
     			len = len + 2; 
  			} 
		}
		return len;
	}else{
		return 50;
	}
}

/** ------------------------------------ method of page action ---------------------------------**/

//construct the string of delete elements      
function getDeleteElementsString(){
	var data = "";
	for(var i=0; i < deleteElementArray.length; i++){
		data += "&deleteElement="+ deleteElementArray[i];
	}    
	//empty the deleteElementArray!
	deleteElementArray.length = 0 ;
	//alert(data);
	return data;
}

//construct the string of delete parameters    
function getDeleteParamsString(){
	var data = "";
	for(var i=0; i < deleteParamArray.length; i++){
		data += "&deleteParam="+ deleteParamArray[i];
	}    
	//empty the deleteParamArray!
	deleteParamArray.length = 0 ;
	//alert(data);
	return data;
}

//construct the string of update(including new) elements, including update/new parameters     
function getUpdateElementsString(){
   	var data="";
   	data += getUpdateStringByIdAndKind("myCondition","condition");
   	data += getUpdateStringByIdAndKind("myAction","action");
   	data += getUpdateStringByIdAndKind("myEligibility","eligibility");
   	//alert(data);
   	return data; 
}

//construct the update string by id and kind
function getUpdateStringByIdAndKind(id,kind){
   	var data = "";
   	for(var i=0; i< $j('#'+id+' > li').size(); i++){
		data += "&elementString="+$j('#'+id+' > li:nth-child('+(i+1)+')').attr('id')+","+kind+","+$j('#'+id+'  >li:nth-child('+(i+1)+')').attr('name')+"|";
		$j('#'+id+' > li:nth-child('+(i+1)+') input').each(function(j){
			
			if(this.id.indexOf('t')== -1){
				//not include the input of title kind
				data+= this.id+":"+this.name+":"+this.value+",";
			}
		});
		$j('#'+id+' > li:nth-child('+(i+1)+') select').each(function(j){
			data+= this.id+":"+this.name+":"+this.value+",";
		});
	}
	//alert(data);
	return data;
}

function fnDoSave(callbackFunction){
	//clear the message
	//$j('#message').empty();

	//get the url  	
    var url=$j('#promoRuleForm').attr('action')+"?doAction=save";
    if(validateAction()){
    	//post it, the callback function is fnDoSaveCallback
    	//the request string
    	var entityName=$j("#name").val();
    	if (confirm(__FMT.common_message_confirmSaveThis + " " + entityName + "?")) {
			var postData="";
	    	postData += $j('#promoRule :input').serialize();
	    	postData += getUpdateElementsString();
	    	postData += getDeleteElementsString();
	    	postData += getDeleteParamsString();
	   	
	   		//TODO for debug 
	   		if(postData.indexOf('undefined')!= -1){
	    		alert("error!!!!!!"+postData);
	    	}
			//$j('#message').append(pmsg.commit_wait);
	    	//alert(postData);
	    	$j.post(url,postData,callbackFunction,"json");
		}
    }
}




function fnDoSaveAndNext(){
	//clear the message
	//$j('#message').empty();
	
	//get the url  	
    var url=$j('#promoRuleForm').attr('action')+"?doAction=saveAndNext";
    if(validateAction()){
    	//post it, the callback function is fnDoSaveCallback
    	//the request string
    	
   		var postData="";
    	postData += getUpdateElementsString();
    	postData += getDeleteElementsString();
    	postData += getDeleteParamsString();
   	
   		//TODO for debug 
   		if(postData.indexOf('undefined')!= -1){
    		alert("error!!!!!!"+postData);
    	}
    
    	
		//$j('#message').append(pmsg.commit_wait);
	
  	
    	document.promoRuleForm.action = url+postData;
    	//alert(document.promoRuleForm.action);
   		document.promoRuleForm.submit();
    }
    
    
}

function refreshTitlePosition(promoRuleName){
	$j('.position > div > span').html(pmsg.title_position_edit+promoRuleName);
}

 
function validateAction(url,callbackFunction){

	$j('#description').val(tinyMCE.get('description').getContent());
	//validate the form	  	  
    if(!validatePromoRule($j('#promoRule').get(0))){
    	setFocusOnTab($j('.x-form-invalid:first').parents('.ui-tabs-panel')[0].id);
    	return false;
   	}
   	//validate the end time
    if(!checkEndTime()){
    	setFocusOnTab('ruleDetail-summary');
    	return false;
    }
    //validate the element type's nums.(each should be ge 1)
    if(!validateElementTypeNums()){
    	alert(pmsg.validate_notPass);
    	setFocusOnTab('ruleDetail-elements');
    	return false;
    }
    
    return true;
    
}


function validateElementTypeNums(){
	if(promoType == "shoppingcartPromotion" | promoType == "couponPromotion" ){
		return validateEligibilityNums() && validateConditionNums() && validateActionNums();
	}else{
		return validateConditionNums() && validateActionNums();
	}
	
}

function setFocusOnTab(id){
	$j('#'+id+'-tab').addClass("ui-tabs-selected");
	$j('#'+id).removeClass("ui-tabs-hide");
	$j('#'+id+'-tab').siblings().removeClass("ui-tabs-selected");
	$j('#'+id).siblings().addClass("ui-tabs-hide");
}




function validateEligibilityNums(){
	if($j('#myEligibility li').length == 0){
		addNeedEligibilityClass();
		return false;
	}else{
		removeNeedEligibilityClass();
		return true;
	}
}

function validateConditionNums(){
	if($j('#myCondition li').length == 0){
		addNeedConditionClass();
		return false;
	}else{
		removeNeedConditionClass();
		return true;
	}
}

function validateActionNums(){
	if($j('#myAction li').length == 0){
		addNeedActionClass();
		return false;
	}else{
		removeNeedActionClass();
		return true;
	}
}

function addNeedEligibilityClass(){
	$j('#needEligibility').addClass("top-showMsg");
	$j('#needEligibility').focus();
}

function removeNeedEligibilityClass(){
	$j('#needEligibility').removeClass("top-showMsg");
}

function addNeedConditionClass(){
	$j('#needCondition').addClass("top-showMsg");
	$j('#needCondition').focus();
}

function removeNeedConditionClass(){
	$j('#needCondition').removeClass("top-showMsg");
}

function addNeedActionClass(){
	$j('#needAction').addClass("top-showMsg");
	$j('#needAction').focus();
}

function removeNeedActionClass(){
	$j('#needAction').removeClass("top-showMsg");
}

//callback funtion of fnDoSave    
function fnDoSaveCallback(result){
	if(result.status==1){
		var data=result.data;
	   	//alert(data.updateFields);
	   	for(var i=0; i< data.updateFields.length; i++){
	   		var idInPage = getIdInPage(data.updateFields[i]);
	   		var idInDB = getIdInDB(data.updateFields[i]);
	   		
	   		if( idInPage.indexOf("e") != -1){
	   			//it is a element update
	   			$j('#'+idInPage).attr("id","o"+idInDB);
	    		$j('#img'+idInPage).remove();
	    		$j('#o'+idInDB+' span').prepend('<img id="imgo'+idInDB+'" src="'+imageOfRemove+'" onclick="removeElement(\'o'+idInDB+'\')"/>');
	    		
	    		if(($j('#o'+idInDB+' > div > li ').length) != 0){
					//more it's a element contain excludeParamMenu
	    			for(var k=0 ; k< $j('#o'+idInDB+' > div > li > ul > li').length; k++){
	    			var html = $j('#o'+idInDB+' > div > li > ul > li:nth-child('+(k+1)+')').html();
	    				if( html.indexOf('category')!= -1 ){
	    					$j('#o'+idInDB+' > div > li > ul > li:nth-child('+(k+1)+')').html('<a  onclick="addExcludedOfCategory(\'o'+idInDB+'\',-1,\'\')">category</a>');
		    			}
		    			if( html.indexOf('product')!= -1 ){
	    					$j('#o'+idInDB+' > div > li > ul > li:nth-child('+(k+1)+')').html('<a  onclick="addExcludedOfProduct(\'o'+idInDB+'\',-1,\'\')">product</a>');
	    				}
	    				if( html.indexOf('sku')!= -1 ){
	    					$j('#o'+idInDB+' > div > li > ul > li:nth-child('+(k+1)+')').html('<a  onclick="addExcludedOfSku(\'o'+idInDB+'\',-1,\'\')">sku</a>');
	   					}
	   				}
	   			}
	   		}else{
				//it is a parameter update
				$j('#'+idInPage).attr("id",idInDB);
				if(($j('#excludedParam'+idInPage).length) != 0){
					//more it's a excluded type element
					$j('#excludedParam'+idInPage).attr("id",'excludedParam'+idInDB);
					$j('#img'+idInPage).remove();
	    			$j('#excludedParam'+idInDB).prepend('<img id="img'+idInDB+'" src="'+imageOfRemove+'" onclick="removeParam(\''+idInDB+'\')"/>');	
				}
	   		}
	   	}
	   	
	   	
	   	//update rule
	   	$j('#promoRuleId').attr("value",data.promoRuleId);
	   	$j('#version').attr("value",data.version);
	   	$j('#updateTime').attr("value",data.updateTime);
	   	$j('#updateTimeShow').html(data.updateTimeShow);
	   	$j('#createTime').attr("value",data.createTime);
	   	$j('#availableCountRecord').attr("value",data.availableCount);
	   	$j('#createTimeShow').html(data.createTimeShow);
	   	$j('#createBy').attr("value",data.createBy);
	   	$j('#updateBy').attr("value",data.updateBy);
	   	   	
	   	setState(data.state);
	   	
	   	//refresh title
		refreshTitlePosition(data.promoRuleName);	
	   	//update message
	   	//$j('#message').empty();
	   	//$j('#message').append(pmsg.commit_success);
		sysMsg(result.msg);
	   	
	   	//update opener
	   	
	   	if(opener&&!opener.closed){
	   		opener.refresh();
	   	}
	}else{
		sysMsg(result.msg,true);
	}
}

function setState(state){

	if(state == "0"){
   		$j('#state').html(pmsg.state_invalid);
   	}else if(state == "1"){
   		$j('#state').html(pmsg.state_doing);
   	}else if(state == "2"){
   		$j('#state').html(pmsg.state_future);
   	}else if(state == "3"){
   		$j('#state').html(pmsg.state_past);
   	}else{
   	
   	}

}
    
