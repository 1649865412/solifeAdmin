bCancel=false;
/**
 * 常用基本类型：数字，日期，maxlength，minlength,required,maxvalue,minvalue,double(小数),date,mask(正则表达式)
 * 应用类型：价格/金额，数量，电话，email，fax（同phone），手机号，code/no，noHtml不允许输入html代码，
 * 未实现：mask,名称，中文，地址，zip，搜索，url，ip，creditcard；年龄; noSql,date,
 * 输入类型：主要是text,textarea,select,radio,checkbox(与radio相同)；后3种都只是支持简单的required检查。
 * 说明：日期都用选择器，不提供客户端验证；数量直接使用数字；对于数字和小数，都只是支持正数；code/no只支持字母和数字
 * 致开发者：支持新的类型，需要一个validateFoo方法，一个__vaMsg.foo信息
 * 致使用者，用法：在需要验证的输入框的validConf字段，设置检查的类型，可用逗号分隔多个，需要参数的用=号，举例validConf="required,integer,maxValue=100"；
 * 一般都是由onblur和onsubmit事件自动调用
 * 注意：考虑到排版，输入框等需要有外层的DIV/TD等。
 * 注意：mask还未能支持;floatRange未支持，里面的min、max未支持
 */

/**为某输入框添加验证，*/
function applyValidate(oField, confs) {
	if (oField == null)
		return;
	if (oField.type=="radio" || oField.type=="checkbox") {
		var radios = document.getElementsByName(oField.name);
		for (var i = 0; i < radios.length; i++) {
			radios[i].onclick = validateEventHandler;
			radios[i].setAttribute("validConf", "required");
		}
		validateRadio(radios[0]);
	} else {
		oField.onblur = validateEventHandler;
		if (confs) {
			oField.setAttribute("validConf", confs || "required");
		}
	}
}
/**对radio等还未支持*/
function removeValidate(oField, removeConf) {
	oField.onblur = null;
	if (removeConf) {
		oField.removeAttribute("validConf");
	}
}
/**事件处理比较麻烦所以和直接传dom/id的分开处理*/
function validateEventHandler() {
	var eventObj = getEventSourceObject();
	if (eventObj.type=="radio" || eventObj.type=="checkbox") {
		return validateRadio(eventObj);
	}
	return validateField(eventObj);
}
/**本框架最重要的部分，主要是框架用（通过事件触发），对input/select/textarea进行各种验证，同时支持多种类型，支持参数，最后处理错误信息并返回*/
function validateField(field) {
	var errMsg = "";
	var strConfs = field.getAttribute("validConf");
	if (!field || field.disabled || !strConfs) {/**当为disabled等时仍然要调用__handleVaErrMsg()去除检查提示信息*/
		//return errMsg;
	}else{
		var confs = strConfs.split(",");
		var value = field.value;
		for (var i = 0; i < confs.length; i++) {
			var params = "";
			var idx = confs[i].indexOf("=");
			var valType;
			if (idx > 0) {
				valType = confs[i].substring(0, idx);
				params = confs[i].substring(idx + 1, confs[i].length);
			} else {
				valType = confs[i];
			}
			var methodName = "validate" + valType.substring(0, 1).toUpperCase() + valType.substring(1, valType.length);
			if (!eval(methodName).call(this, value, params)) {
				var fieldMsg=$j(field).attr("v"+valType);
				if(fieldMsg){
					errMsg += fieldMsg;
				}else{
					errMsg += eval("__vaMsg." + valType);
					//mask的校验,不要添加参数到msg中,一般的用户都无法理解正则表达式.
					if (params.length > 0 && methodName != "validateMask") {
						errMsg += params;
					}
				}
				errMsg += " <br/>"
			}
		}
	}
	if (!field){
		return errMsg;
	}
	return __handleVaErrMsg(field, errMsg);
}
/**验证radio有选择（非空），考虑到性能，应尽量只是在第一个item设置激活验证；验证信息添加在组的最后一个后面；同时也用在checkbox；*/
function validateRadio(radio) {
	var radios = document.getElementsByName(radio.name);
	if (radios && radios.length > 0) {
		var errMsg = __vaMsg.radio;
		for (var i = 0; i < radios.length; i++) {
			if (radios[i].checked) {
				errMsg = "";
			}
		}
		return __handleVaErrMsg(radios[radios.length - 1], errMsg);
	}
	return "";
}
var inputFilter = {button:0, submit:0, reset:0, image:0, file:0};
/**验证form/div里面的所有有效输入框；如果都通过才返回true*/
function validateForm(theForm) {
	if (bCancel) {
		return true;
	}
	var oForm=theForm||document.forms[0];
	var elems = oForm.getElementsByTagName('*');
	var invalidCount = 0;
	var   i=-1;
	for(var elem in elems){
		i++;
		if (!elems[i]||!elems[i].type || elems[i].type in inputFilter||elems[i].disabled) {
			continue;
		}
		if (elems[i].type == "radio" || elems[i].type == "checkbox") {
			if (elems[i].getAttribute("validConf") && validateRadio(elems[i]).length > 0) {
				invalidCount++;
			}
		} else {
			if (validateField(elems[i]).length > 0) {
				invalidCount++;
			}
		}
	}   
	return (invalidCount == 0);
}
/**一般由框架调用，自动注册form/div里面的有效的输入框，应该在任何applyValidate前调用*/
function autoApplyValidate(theForm) {
	var oForm = theForm || document.forms[0];
	var elems = oForm.getElementsByTagName('*');
	var   i=-1;
	for(var elem in elems){
		i++;
		if (!elems[i]||!elems[i].type || elems[i].type in inputFilter) {
			continue;
		}
		if (elems[i].getAttribute("validConf")) {
			applyValidate(elems[i]);
		}
	}
}
/**处理错误信息，首先清理使恢复初始状态，然后显示错误提示图标和高亮输入框，label暂时不处理*/
function createTip(uuid,errMsg){
	if(!$j.isFunction($j(document).PQuickTip)){//防止动态加载没有完成而出错
		setTimeout("createTip('"+uuid+"','"+errMsg+"')",500)
	}
	else{
		$j("#validate_"+uuid).PQuickTip({
	    	source:'text',
	    	text:errMsg,
	    	title:'Error'
	    })
	}
}
if(typeof $j =="undefined")
  $j=jQuery.noConflict();
function __handleVaErrMsg(dom, errMsg) {
	if (errMsg && errMsg.length > 0){
		var uuid = dom.id==""?dom.name:dom.id;
		var errorObj = $j(dom);
		var lastChild = $j("#validate_"+uuid);
	    if(lastChild.length==0){		
		    errorObj.addClass("x-form-invalid");
		    $j("<img id='validate_"+uuid+"' src='"+__ctxPath+"/images/validate/exclamation.gif' border='0'>").appendTo("body");
		    $j("#validate_"+uuid).appendTo(errorObj.parent());
		    $j("#validate_"+uuid).bind('remove',function(){
		    	$j(this).PQuickTip.removeTip($(this));
		    })
	    }
	    if(!$j.isFunction($j(document).PQuickTip)){//防止多次加载QuickTip.js文件
	    	$j(document.createElement('script'))
                .attr("src",  __ctxPath + "/scripts/jquery/plugins/quicktip/quicktip.js")
          		.attr("type", "text/javascript").appendTo("head");
            $j(document.createElement('link'))
	            .attr("href",  __ctxPath + "/scripts/jquery/plugins/quicktip/quicktip.css")
	            .attr("rel", "stylesheet").attr("type", "text/css").appendTo("head");
	    }
	    createTip(uuid,errMsg);
	}
	else{
	   var uuid = dom.id||dom.name;
	   var errorObj = $j(dom);
	   errorObj.removeClass("x-form-invalid");
	   $j("#validate_"+uuid).trigger("remove");
	   $j("#validate_"+uuid).remove();
	}
	return errMsg;
}

/**defined regular expressions*/
var reNumber = new RegExp("^[\\d,]*$");
var reDouble = new RegExp("^[\\d,]*(\\.\\d+)?$");
var rePrice = new RegExp("^[\\d,]*(\\.\\d{1,2})?$");
var reDouble4 = new RegExp("^[\\d,]*(\\.\\d{1,4})?$");
var reEmail = /^([\w-]+)(.[\w-]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/;
var reCode = new RegExp("^[\\w-]+$");
var rePhone = new RegExp("^[\\d]{6,8}$");
var reMobile = new RegExp("^[\\d]{9,11}$");
var reUrl = /(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;
var reMoney = new RegExp("^[\\d,-]?[\\d]*(\\.\\d{1,2})?$");

function skipIfEmpty(str) {
	return str == null || str.trim().length == 0;
}
function testRegExp(re, str) {
	return re.test(str);
}
/**defined validation functions*/
function validateInteger(str) {
	if (skipIfEmpty(str)) return true;
	return validateNumber(str) && validateMaxValue(str, 2147483647);
}
function validateLong(str) {
	if (skipIfEmpty(str)) return true;
	return validateNumber(str) && validateMaxValue(str, 2147483647000);
}
function validatePositiveInteger(str) {
	if (skipIfEmpty(str)) return true;
	return validateNumber(str) && validateMaxValue(str, 2147483647) && validateMinValue(str, 1);
}
function validateShort(str) {
	if (skipIfEmpty(str)) return true;
	return validateNumber(str) && validateMaxValue(str, 32767);
}
function validateNumber(str) {
	return skipIfEmpty(str) || testRegExp(reNumber, str);
}
function validateDouble(str) {
	return skipIfEmpty(str) || testRegExp(reDouble, str);
}
function validatePrice(str) {
	return skipIfEmpty(str) || testRegExp(rePrice, str);
}
function validateMoney(str) {
	return skipIfEmpty(str) || testRegExp(reMoney, str);
}
function validateDouble4(str) {
	return skipIfEmpty(str) || testRegExp(reDouble4, str);
}
function validateRequired(str) {
	return !(str == null || str.trim().length == 0);
}
function validateEmail(str) {
	if (skipIfEmpty(str)) return true;
	return testRegExp(reEmail, str) && validateMaxlength(str, 128);
}
function validateCode(str) {
	return skipIfEmpty(str) || testRegExp(reCode, str);
}
function validatePhone(str) {
	return skipIfEmpty(str) || testRegExp(rePhone, str);
}
function validateMobile(str) {
	return skipIfEmpty(str) || testRegExp(reMobile, str);
}
function validateMaxlength(str, maxLen) {
	return skipIfEmpty(str) || str.length <= maxLen;
}
function validateMinlength(str, maxLen) {
	return skipIfEmpty(str) || str.length >= maxLen;
}
function validateMaxValue(str, maxValue) {
	return skipIfEmpty(str) || (parseFloat(str) <= parseFloat(maxValue));
}
function validateMinValue(str, minValue) {
	return skipIfEmpty(str) || (parseFloat(str) >= parseFloat(minValue));
}
function validateNoHtml(str) {
	return (str.indexOf("<")==-1 && str.indexOf("<")==-1);
}
function validateFloatRange(str, range) {
	if (skipIfEmpty(str)) return true;
	var f_range_min = parseFloat(range.substring(1, range.indexOf("-")));
	var f_range_max = parseFloat(range.substring(range.indexOf("-")+1,range.indexOf("]")));
	return parseFloat(str) >= f_range_min && parseFloat(str) <= f_range_max;
}
function validateIntRange(str, range) {
	if (skipIfEmpty(str)) return true;
	var i_range_min = parseInt(range.substring(1, range.indexOf("-")));
	var i_range_max = parseInt(range.substring(range.indexOf("-")+1,range.indexOf("]")));
	return parseInt(str)>=i_range_min && parseInt(str) <= i_range_max;
}
function validateDate(str) {
	if (skipIfEmpty(str)) return true;
	if (str.length != __defDatePattern.length) return false;	
	var validateDate_yyyy = str.substr(__defDatePattern.indexOf("yyyy"), 4);
	var validateDate_MM = str.substr(__defDatePattern.indexOf("MM"), 2);
	var validateDate_dd = str.substr(__defDatePattern.indexOf("dd"), 2);
	var validateDate_date = new Date(validateDate_yyyy,validateDate_MM-1,validateDate_dd);
	if ((new Number(validateDate_dd)).valueOf() == validateDate_date.getDate() 
	 && (new Number(validateDate_MM)).valueOf() == (validateDate_date.getMonth()+1) 
	 && validateDate_yyyy == validateDate_date.getFullYear())
	{
		return true;
	}
	return false;
}
function validateUrl(str){
	return skipIfEmpty(str) || testRegExp(reUrl,str);
}
function validateMask(str, mask){
	return skipIfEmpty(str) || testRegExp(new RegExp(mask),str);
}
function validateEqualTo(str, secondProperty){
	return str==$j("#"+secondProperty).val();
}