/**
 * 最常用、可重用、业务无关、技术无关、框架无关、最精简的JS方法集合
 */
/*Prototype $() 替代方法*/
function $(el) {
	if (arguments.length > 1) {
		for (var i = 0, els = [], length = arguments.length; i < length; i++) {
			els.push($(arguments[i]));
		}
		return els;
	}
	if (typeof el == "string") {
		el = document.getElementById(el);
	}
	if ((typeof Prototype=='undefined') || (typeof Element == 'undefined') || (typeof Element.Methods=='undefined')) {
		return el;
	} else {
		return Element.extend(el);
	}
}
/* 分析日期,并返加日期对象 */
function fnPaserDate($str)
{
	var var_d1 = new Date();
	var_d1.setMonth($str.substr(__defDatePattern.indexOf("MM"), 2) - 1);
	var_d1.setDate($str.substr(__defDatePattern.indexOf("dd"), 2));
	var_d1.setFullYear($str.substr(__defDatePattern.indexOf("yyyy"), 4));
	var_d1.setHours(0);
	var_d1.setMinutes(0);
	var_d1.setSeconds(0);
	var_d1.setMilliseconds(0);
	return var_d1;	
}
/*This function is used to compare date*/
function fnCompareDate(d1,d2){
	if(!d1 || !d2) {
		return;
	}
	var dd1=fnPaserDate(d1).getTime();
	var dd2=fnPaserDate(d2).getTime();
	return dd1-dd2;
}

/*隐藏组件*/
function fnHide(el) {
	$(el).style.display = "none";
}
/*显示组件*/
function fnShow(el) {
	$(el).style.display = "";
}
/*增加一个样式*/
function addClass(el, className) {
	removeClass(el, className);
	$(el).className += " " + className;
};
/*删除一个样式*/
function removeClass(el, className) {
	var regex = new RegExp("(^|\\s)" + className + "(\\s|$)", "g");
	$(el).className = $(el).className.replace(regex, "");
};

/*切换旧样式到新样式*/
function fnSetStyle(el,reomveClassName,addClassName){		
	removeClass(el, reomveClassName);
	addClass(el, addClassName);
}
/*切换radio或checkbox的状态*/
function toggleChoice(el) {
    $(el).checked=!$(el).checked;
}
/*String增加trim方法，去除头尾的空格*/
String.prototype.trim = function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
/*去除字符串头尾的空格*/
function trim(str) {
    return str==null?null:str.trim();
} 

/*在原有url添加参数的简便方法*/
function fnAppendUrl(url,paramName,paramValue) {
	if (!url || !paramName || !paramValue) {
		return url;
	}
	var tmpUrl = removeParamFromUrl(url,paramName);
	return tmpUrl + (tmpUrl.indexOf('?') > -1 ? '&' : '?') + paramName+"="+paramValue;
}
/*在原有url删除指定参数的简便方法*/
function removeParamFromUrl(url, paramName) {
	return url.replace(new RegExp("("+paramName+"=[^&]*[&])|([?|&]"+paramName+"=[^&]*$)"), "");
}
/*动态创建指定类型的组件的简便方法*/
function createFormElement(parent,tagName, id, name, type, value) {
    var e = document.createElement(tagName);
    e.setAttribute("id", id||name);
    e.setAttribute("name", name||id);
    e.setAttribute("type", type);
    e.setAttribute("value", value);
    parent.appendChild(e);
    return e;
}
/*在指定或第一个form添加一个隐藏字段的简便方法*/
function fnAddHiddenField(fieldName,fieldValue,form) {
	for (var i = 0; i < form.elements.length; i++) {
		if (form[i].type in {button:0,submit:0,reset:0,image:0,file:0}) continue;
      	if (fieldName == form[i].name) {
        	form[i].value=fieldValue;
			return form[i];
      	} 
	}
	return createFormElement(form||document.forms[0],"INPUT",null,fieldName,"hidden",fieldValue);
}
/*选择某个下拉框指定值的简便方法*/
function fnSelectOption(selectObj,value) {
	for (var j = 0; j < selectObj.options.length ; j++) {
		if (value==selectObj.options[j].value) {
			selectObj.options[j].selected=true;
			selectObj.selectedIndex=j;
		}
	}
}
/*根据text选择某个下拉框指定值的简便方法*/
function fnSelectOptionText(selectObj,value) {
	for (var j = 0; j < selectObj.options.length ; j++) {
		if (value.trim()==selectObj.options[j].text.trim()) {
			selectObj.options[j].selected=true;
			selectObj.selectedIndex=j;
		}
	}
}
/**
* 取到当前的事件对象,同时兼容ie和ff的写法 
*/
function getEvent(){   
    if (document.all) {
    	return window.event;
    }         
    var func=getEvent.caller;             
    while(func!=null){     
        var arg0=func.arguments[0]; 
        if(arg0){ 
            if((arg0.constructor==Event || arg0.constructor ==MouseEvent) 
                || (typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation)){     
               return arg0; 
            } 
        } 
        func=func.caller; 
    } 
    return null; 
}
/**
	取到当前事件的源对象
**/
function getEventSourceObject(){
	var evt=getEvent();
    return evt.srcElement || evt.target;
}

/**
 * 把传入的Java序列化对象（例如通过DWR转换的）或JSON对象的属性绑定到指定表单的同名属性的值。
 * 局限性：还不能支持多层（请在VO层先把对象属性扁平化）。还不能支持复杂的属性（例如属性是一个列表，需要递归才能处理，对于这些复杂的情况，可以在调用本方法后自己处理）。
 * 说明：DWR的方法更强，但太依赖id，不能支持一个页面有两个相似的form。
 */
function setFormValues(oData,sFormName) {
	if (!oData) {
		return;
	}
	var oForm = $(sFormName);
	if (!oForm) {
		alert("Form not found:"+sFormName);
		return;
	}
    for (var i = 0; i < oForm.elements.length; i++) {
      if (oForm[i].type in {button:0,submit:0,reset:0,image:0,file:0}) continue;
      var name="";
      if (oForm[i].name) {
        name = oForm[i].name;
      } else {
        if (oForm[i].id) name = oForm[i].id;
        else name = "element" + i;
      }
      if (oData[name]) {
      	oForm[i].value=oData[name];
      }
    }
}


function _defaultAjaxErrorHandler(errMsg,xhr) {
	alert(errMsg);
}
/*在Firebug记录调试信息的简便方法，但响应的log语句在调试通过后应删除以提高效率*/
function log(sMsg) {
	typeof console=='undefined'?alert(sMsg):console.log(sMsg);
}
/*Cookie管理注意事项：Cookie会被发到服务器因而占用带宽，要提高性能就要减少Cookie的消耗，有以下原则：
 *原则1：尽量不使用Cookie，而且一般也不应有非全局的Cookie，还需要限制Cookie的path的范围
 *原则2：尽量在服务器端处理Cookie而不是客户端，客户端只读
 *原则3：Cookie必须尽量简短，且不能存储敏感数据
 *原则4：尽量集中管理全局Cookie（如在一个隐藏Frame处理；统一不设置path，因为隐藏页面只能读本身URL匹配的path的Cookie，但需采用异步方式，较麻烦－－Cookie的读取和进一步处理一般是在onload里面，或onclick等事件，以保证这时隐藏frame已经准备好）；
 */
function setCookie(name,value,expires,path,domain,secure) {
  document.cookie = name + "=" + escape (value) +
    ((expires) ? "; expires=" + expires.toGMTString() : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") + ((secure) ? "; secure" : "");
}
function getCookie(name) {
	var prefix = name + "=" 
	var start = document.cookie.indexOf(prefix) 
	if (start==-1) {
		return null;
	}
	start+=prefix.length;
	var end = document.cookie.indexOf(";", start) 
	if (end==-1) {
		end=document.cookie.length;
	}
	return unescape(document.cookie.substring(start, end));
}

/**
* 按比例缩小图片。如图片实际大小比要求的小，无需调整；否则，只需要调整width或hieight（浏览器会自动调整）
* oImg image对象
* reqWidth 期望中的width
* reqHeight 期望中的height 
*/
function fnResizeImage(oImg, reqWidth, reqHeight) {
	if (reqWidth >= oImg.width && reqHeight >= oImg.height){
		return;
	}
	if ((oImg.width/oImg.height) > (reqWidth/reqHeight)) {
		oImg.width = reqWidth;
	} else {
		oImg.height = reqHeight;
	}
}

/**
 * 获取元素的绝对位置
 */
function getElementPos(obj) {
		var ua = navigator.userAgent.toLowerCase();
		var isOpera = (ua.indexOf('opera') != -1);
		var isIE = (ua.indexOf('msie') != -1 && !isOpera); // not opera spoof
	
		var el = obj;
	
		if(el.parentNode === null || el.style.display == 'none') 
		{
			return false;
		}
	
		var parent = null;
		var pos = [];
		var box;
	
		if(el.getBoundingClientRect)	//IE
		{
			box = el.getBoundingClientRect();
			var scrollTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
			var scrollLeft = Math.max(document.documentElement.scrollLeft, document.body.scrollLeft);
	
			return {x:box.left + scrollLeft, y:box.top + scrollTop};
		}
		else if(document.getBoxObjectFor)	// gecko
		{
			box = document.getBoxObjectFor(el);
			   
			var borderLeft = (el.style.borderLeftWidth)?parseInt(el.style.borderLeftWidth):0;
			var borderTop = (el.style.borderTopWidth)?parseInt(el.style.borderTopWidth):0;
	
			pos = [box.x - borderLeft, box.y - borderTop];
		}
		else	// safari & opera
		{
			pos = [el.offsetLeft, el.offsetTop];
			parent = el.offsetParent;
			if (parent != el) {
				while (parent) {
					pos[0] += parent.offsetLeft;
					pos[1] += parent.offsetTop;
					parent = parent.offsetParent;
				}
			}
			if (ua.indexOf('opera') != -1 
				|| ( ua.indexOf('safari') != -1 && el.style.position == 'absolute' )) 
			{
					pos[0] -= document.body.offsetLeft;
					pos[1] -= document.body.offsetTop;
			} 
		}
			
		if (el.parentNode) { parent = el.parentNode; }
		else { parent = null; }
	  
		while (parent && parent.tagName != 'BODY' && parent.tagName != 'HTML') 
		{ // account for any scrolled ancestors
			pos[0] -= parent.scrollLeft;
			pos[1] -= parent.scrollTop;
	  
			if (parent.parentNode) { parent = parent.parentNode; } 
			else { parent = null; }
		}
		return {x:pos[0], y:pos[1]};
}

/**
 *在StoreAdmin和StoreFront都常用的脚本，其实里面大部分都只是在后台用，但可能以后在前台也使用。主要和框架相关。
 */
function getCurrentUserId(){
	return getCookie("UID")||-2;
}
/**不严格的简单判断用户是否登录的方法，性能较好，缺省设置为5分钟更新一次*/
function isLogined() {
	return getCurrentUserId()>0;
}
function getCurrentUserName(loginRequired) {
	return (!loginRequired||isLogined())&&getCookie("UNAME")||"";
}

