function fnOnSearchAdvanced(){
	document.salesOrderListFrm.searchflag.value="1";
	return true;
}    
function fnEnableSpecifyDate(val){
	var defineDateRow=document.getElementById("defineDateRow");
	if(val=="specify"){
		defineDateRow.style.display="";
	}else{
		defineDateRow.style.display="none";
	}
}
function fnCheckBeforeSubmit(frm){
	var minPrice=frm.minPrice;
	var maxPrice=frm.maxPrice;
	if(minPrice!=""&&maxPrice!=null){
		
	}
	return validateSalesOrderListFrm(frm);
}
function fnOnSubmit(obj,actionNo){
	if(actionNo!=null){
		obj.form.doAction.value=obj.name;
		return fnCheckStatus(obj,actionNo);
	}else{
		return validateSalesOrderListFrm(obj.form);		
	}
}

function fnFlowCheck(status,actionNo){
	//should be fill flow rule here
	if(actionNo==status){
		return true;
	}
	return false;
}


function selectTextItem(selObj,text){
	for(var i=0;i<selObj.options.length;i++){
		if(selObj.options[i].text==text){
			selObj.options[i].selected=true;
			return selObj.options[i].value;
		}
	}
	return -1;
}
function selCountry(obj){
	var selCountryId=obj.options[obj.selectedIndex].value;
	regionSelector.regionSelect("shippingStateId",selCountryId,2,null);
	if(obj.options[obj.selectedIndex].value!=""){
		document.getElementById("shippingCountry").value=obj.options[obj.selectedIndex].text;
	}else{
		document.getElementById("shippingCountry").value="";
	}
	document.getElementById("shippingState").value="";
	document.getElementById("shippingCity").value="";
}

function selState(obj){
	var selStateId=obj.options[obj.selectedIndex].value;
	regionSelector.regionSelect("shippingCityId",selStateId,3,null);
	if(obj.options[obj.selectedIndex].value!=""){
		document.getElementById("shippingState").value=obj.options[obj.selectedIndex].text;
	}else{
		document.getElementById("shippingState").value="";
	}
	document.getElementById("shippingCity").value="";
}

function selCity(obj){
	if(obj.options[obj.selectedIndex].value!=""){
		document.getElementById("shippingCity").value=obj.options[obj.selectedIndex].text;
	}else{
		document.getElementById("shippingCity").value="";
	}
}