$j(document).ready(function(){
	fillDivWithPage("ruleDetail-couponList", __ctxPath+"/sales/coupon.html?promoRule="+$("promoRuleId").value+"&decorator=selecter");
});

function gotoPageInPromo(pageNo) {
		var frm=document.getElementById("paging_nav_btn").form;
		frm.action = frm.action + (frm.action.match(/\?/) ? '&' : '?') + "PrmPageNo="+pageNo;
		var prmItemsPerPage= document.getElementById("PrmItemsPerPage").value;
		fillDivWithPage("ruleDetail-couponList", frm.action+"&PrmItemsPerPage="+prmItemsPerPage+"&decorator=selecter");
}


function fnDoMultiDeleteInPromo(obj) {
	var itemNames = fnGetSelectedItemNames();
	var frm=document.couponListForm;
	
	//get the delete multiIds
	if (itemNames=="") {
		return false;
	}
	var multiIds = document.getElementsByName("multiIds");
	var multiIdString = "";
	for(var i=0; i< multiIds.length; i++){
		if(multiIds[i].checked){
			multiIdString += "&multiIds="+multiIds[i].value;
		}
	}	
	if (confirm(__FMT.common_message_confirmDeleteThis+ " " + itemNames+"?")) {
		//delete first by ajax
		$j.post(frm.action+"?doAction=multiDelete"+multiIdString+"&decorator=selecter","",multiDeleteCallback);
		//update message
   		//$j('#coupon_message').empty();
   		//$j('#coupon_message').append(pmsg.coupon_commit_wait);
	}	
}

function multiDeleteCallback(){
	//fill the div
	var frm=document.couponListForm;
	fillDivWithPage("ruleDetail-couponList", frm.action+"?doAction=search"+"&decorator=selecter");	
	//update message
   	//$j('#coupon_message').empty();
   	//$j('#coupon_message').append(pmsg.coupon_commit_success);
}

function validateGenCouponForm(){
	if ($j("#couponStyle").val() == '3')
	{
		applyValidate($("speCouponNo"),"required,code,maxlength=16");
		removeValidate($("quantity"),"required,integer,minValue=1,maxValue=500");
		removeValidate($("prefix"),"code");
	}
	else
	{
		removeValidate($("speCouponNo"),"required,code,maxlength=16");
		applyValidate($("quantity"),"required,integer,minValue=1,maxValue=500");
		applyValidate($("prefix"),"code");
	}
	if (validateForm(document.genCouponForm)) {
  		return true;
  	} 
  	else {
  		alert(__vaMsg.notPass); 
  		return false; 
  	}
}

function genCoupons(){
	if($("promoRuleId").value == ''){
		alert(pmsg.coupon_gen_after_save);
		return false;
	}
	if(!validateGenCouponForm()){
		return false;
	}
	//get the url  	
    var url=$j('#promoRuleForm').attr('action')+"?doAction=generateCoupon";
	var postData="";
    postData += $j('#promoRule :input').serialize();
    //alert(postData);
    $j.post(url,postData,genCouponCallback,"json");
    //update message
   	$j('#coupon_message').empty();
   	$j('#coupon_message').append(pmsg.coupon_commit_wait);
}

function fnChangeCouponSytle(){
	if($j("#couponStyle").val() == '3'){
		$j("#inputSpecialNo").show();
		$j("#inputGenNos").hide();
	}
	else
	{
		$j("#inputSpecialNo").hide();
		$j("#inputGenNos").show();
	}
}

function genCouponCallback(result){
	if(result.status==1){
		//update message
		var frm=document.couponListForm;
		fillDivWithPage("ruleDetail-couponList", frm.action+"?promoRule="+$j('#promoRuleId').val()+"&doAction=search"+"&decorator=selecter");	
		$j('#coupon_message').empty();
		$j('#coupon_message').append(pmsg.coupon_commit_success);
	}else{
		alert(result.msg);
	}
}

function validateSendCouponForm(){
	applyValidate($("firstName"),"maxlength=10");
	applyValidate($("lastName"),"maxlength=10");
	applyValidate($("email"),"required,email,maxlength=50");
	applyValidate($("couponNo"),"required,maxlength=30");
	if (bCancel) return true; 
  	else if (validateForm(document.sendCouponForm)) {
  		removeValidate($("firstName"),true);
  		removeValidate($("lastName"),true);
  		removeValidate($("email"),true);
  		removeValidate($("couponNo"),true);
  		return true;
  	} 
  	else {
  		alert(__vaMsg.notPass); 
  		return false; 
  	}
	
	
}

function fnInitSendCouponForm(couponNo){
	$j('#couponNo').val(couponNo);
	
}

function sendCoupon(){
	if(!validateSendCouponForm()){
		return false;
	}
	//get the url  	
    var url=$j('#promoRuleForm').attr('action')+"?doAction=sendCoupon";
	var postData="";
  	var actionnn = $j('#sendCouponForm').attr("id");
    postData += $j('#sendCouponForm :input').serialize();
    //alert(postData);
    $j.post(url,postData,sendCouponCallback);
    //close dialog
    dlgsendCouponDialog_close();
    //update message
   	$j('#coupon_message').empty();
   	$j('#coupon_message').append(pmsg.coupon_commit_wait);
}

function sendCouponCallback(result){
	if(result.status==1){
		//update message
		var frm=document.couponListForm;
		fillDivWithPage("ruleDetail-couponList", frm.action+"?promoRule="+$j('#promoRuleId').val()+"&doAction=search"+"&decorator=selecter");	
   		$j('#coupon_message').empty();
   		$j('#coupon_message').append(pmsg.send_coupon_success);
	}else{
		alert(result.msg);
	}
}
