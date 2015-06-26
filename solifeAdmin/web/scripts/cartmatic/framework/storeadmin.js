/**
 *在StoreAdmin常用的脚本
 */
var bCancel=false;

function checkAll(theForm) { // check all the checkboxes in the list
	for (var i=0;i<theForm.elements.length;i++) {
    	var e = theForm.elements[i];
		var eName = e.name;
    	if (eName != 'allbox' && (e.type.indexOf("checkbox") == 0)) {
        	e.checked = theForm.allbox.checked;		
		}
	} 
}
/*  This function is to select all options in a multi-valued <select> */
function selectAll(listId) {
	var list = document.getElementById(listId);
	for (var i = 0; i < list.options.length; i++) {
		list.options[i].selected = true;
	}
}

/**
 * when an input field value changed, check the coresponding multiIds checkbox.
 */
function fnSelectItemById(idValue) {
	if (idValue) {
		var idCheckboxs=document.getElementsByName("multiIds");
		for (i=0;i<idCheckboxs.length;i++) {
			idCheckboxs[i].checked=idCheckboxs[i].value==idValue;
		}
	}
}
/*批量操作的时候选择的记录的名称，需要设置title*/
function fnGetSelectedItemNames() {
	var itemNames="";
	var tmpDlm="";
	var wrapCount = 0;
	var idCheckboxs=document.getElementsByName("multiIds");
	for (i=0;i<idCheckboxs.length;i++) {
		if (idCheckboxs[i].checked) {
			itemNames+=tmpDlm+(idCheckboxs[i].title?idCheckboxs[i].title:idCheckboxs[i].value);
			wrapCount++;
			if(wrapCount >= 5){
				tmpDlm=",\n";
				wrapCount = 0;
			}else{
				tmpDlm=",";
			}
			
		}
	}
	return itemNames;
}

function fnOpenPrintPage(curActionName) {
	if ($j("form.mainForm")) {
		var frm = $j("form.mainForm").get(0);
		var frmAction = frm.action;
		frm.target = "_blank";
		frm.action = frmAction + (frmAction.match(/\?/) ? "&" : "?") + "printable=true";
		fnSubmitActionForm(frm, curActionName);
		frm.target = "";
		frm.action = frmAction;
	}
}

function fnSubmitActionForm(oForm,sActionName) {
	//先过滤在form.action上的doAction参数。
	oForm.action = removeParamFromUrl(oForm.action,"doAction");
	if (oForm.doAction) {
		oForm.doAction.value = sActionName;
	} else {
		fnAddHiddenField("doAction",sActionName,oForm);
	}
	oForm.submit();
}

function fnChangeLanguage(Lcode){
	location.href=fnAppendUrl(location.href,"preferredLocale",Lcode);
}

function fnGetCheckedValue(radioName) { 
	var radios=document.getElementsByName(radioName);
	var val="";
  for (var i=0;i<radios.length;i++) {
  	if(radios[i].checked){
		val=radios[i].value;
		break;
  	}
  }
	return val;  
}

function checkAllByName(theForm,idName) { // check all the checkboxes in the list
	for (var i=0;i<theForm.elements.length;i++) {
    	var e = theForm.elements[i];
		var eName = e.name;
    	if (eName != 'allbox' && (e.type.indexOf("checkbox") == 0) && eName == idName) {
        	e.checked = theForm.allbox.checked;		
		}
	} 
}

function highlightTableRows(tableId) {
    var table = document.getElementById(tableId);
    if (!table) {
    	return;
    } 
    var previousClass = null;
    var tbody = table.getElementsByTagName("tbody")[0];
    var rows;
    if (tbody == null) {
        rows = table.getElementsByTagName("tr");
    } else {
        rows = tbody.getElementsByTagName("tr");
    }
    // add event handlers so rows light up and are clickable
    for (i=0; i < rows.length; i++) {
        rows[i].onmouseover = function() { previousClass=this.className;this.className+=' over' };
        rows[i].onmouseout = function() { this.className=previousClass };
    }
}
//处理查询
function doSearchHandler() {
	var glSearchBar=$j("#glSearchBar");
	var oForm=glSearchBar.parents("form").get(0);
	//只是为了兼容旧的框架
	if (validateForm($("glSearchBar")))
	{
		fnAddHiddenField("btnSearch","true",oForm);
		oForm.submit();
		return true;
	}
	else
	{
		alert(__vaMsg.notPass);
		return false;
	}
}

function doSearchHandlerWhenEnter(ievent){
	var event = window.event||ievent;
	if( event.keyCode == 13 ){
	 	doSearchHandler();
  	}
  	return false;
}

function doSave_old(validateFrmName){
	var confirmMsg=__FMT.common_message_confirmSave;
	bCancel=false;
	oForm = $j('form.mainForm').get(0);
	if(validateFrmName){
		var methodName="validate"+validateFrmName;
		if (!eval(methodName).call(this, oForm)) {
			return false;
		}
	}
	if(confirm(confirmMsg)){
		if(oForm.doAction){
			oForm.doAction.value="save";
		}
		oForm.submit();
	}
}
function doSaveAfterValidate_old(validateFrmName){
	oForm = $j('form.mainForm').get(0);
	var methodName="validate"+validateFrmName;
	if(eval(methodName).call(this, oForm) && confirm(__FMT.common_message_confirmSave)){
		bCancel=false;
		if(validateFrmName){
			var methodName="validate"+validateFrmName;
			if (!eval(methodName).call(this, oForm)) {
				return false;
			}
		}
		if(oForm.doAction){
			oForm.doAction.value="save";
		}
		oForm.submit();
	}
}

function doMutiSave_old(ids,shortMsg){
	if(confirm(__FMT.common_message_confirmSave)){
		oForm = $j('form.mainForm').get(0);
		oForm.doAction.value="multiSave";
		oForm.submit();
	}
}

function doDelete_old(shortMsg){
	var confirmMsg=__FMT.common_message_confirmDeleteThis;
	if(shortMsg) confirmMsg=confirmMsg+shortMsg;
	//confirmMsg+="?";
	
	if(confirm(confirmMsg)){
		bCancel=true;
		var oForm = $j('form.mainForm').get(0);
		createFormElement(oForm,"input",null,"delete","hidden","delete");
		oForm.submit();
	}
}

function doMutiDelete_old(ids,shortMsg){
	if(ids!=null){
        var len = ids.length; 
        var checked = false; 
		if(len>=0){
	        for (i = 0; i < len; i++) { 
	            if (ids[i].checked == true) { 
	                checked = true; 
	                break; 
	            } 
	        } 
		}else{
			if(ids.checked==true){
				checked=true;		
			}	
	    }
        if (!checked) { 
            alert(__FMT.common_message_deleteOne); 
            return false; 
        } 
    }
    bCancel=true;
    var oForm = $j('form.mainForm').get(0);
    var confirmMsg=__FMT.common_message_confirmDelete;
	if(shortMsg) confirmMsg=confirmMsg+shortMsg;
	//confirmMsg+="?";
	if(confirm(confirmMsg)){
		if(oForm.doAction){	
			oForm.doAction.value="multiDelete";
		}else{	  	  
			if(oForm.action!=null&&oForm.action.indexOf("?")!=-1){
		       oForm.action=oForm.action+"doAction=multiDelete";	  
		    }else{
		       oForm.action=oForm.action+"?doAction=multiDelete";	
		    }
	  	}
	  	oForm.submit();
	}
}

function doCancel_old(url){
	bCancel=true;
    var oForm = $j('form.mainForm').get(0);
    createFormElement(oForm,"input",null,"cancel","hidden","cancel");
    oForm.submit();
}

function doAction_old(actionName,confirmMsg){
	oForm = $j('form.mainForm').get(0);
	if(oForm.doAction && actionName){
		oForm.doAction.value=actionName;
	}
	oForm.submit();
}

//以下是新框架的Form提交方法
/* 通过doAction提交的方法,暂时obj是没有用的 */
function fnDoAction(obj,actionName,confirmMsg) {
	var form=$j(obj).closest("form.mainForm").get(0);
	if(!form){
		form=$j("form.mainForm").get(0);
	}
	if (form && !bCancel) {
		var __onsubmit= form.onsubmit;
		var result=true;
		if (typeof __onsubmitt=="string") {
			result=eval(__onsubmit);
		} else if (typeof __onsubmit=="function") {
			result=__onsubmit.call(form);
		}
		if (result==false) {
			return false;
		}
	}
	if (!confirmMsg || confirm(confirmMsg)) {
		fnSubmitActionForm(form,actionName);
	}	
	return false;
}

function fnDoSave(obj,nameFieldId) {
	bCancel=false;
	var entityName="";
	if (nameFieldId && $(nameFieldId) && $(nameFieldId).value) {
		entityName = "："+$(nameFieldId).value;
	}
	return fnDoAction(obj, "save", __FMT.common_message_confirmSaveThis+entityName+"?");
}

function fnDoSaveAndNext(obj,nameFieldId) {
	bCancel=false;
	var entityName="";
	if (nameFieldId && $(nameFieldId) && $(nameFieldId).value) {
		entityName = $(nameFieldId).value;
	}
	return fnDoAction(obj, "saveAndNext", __FMT.common_message_confirmSaveThis+entityName+"?");
}

function fnDoDelete(obj,nameFieldId) {
	bCancel=true;
	var entityName="";
	if (nameFieldId && $(nameFieldId) && $(nameFieldId).value) {
		entityName = $(nameFieldId).value;
	}
	return fnDoAction(obj, "delete", __FMT.common_message_confirmDeleteThis+ " " + entityName+"?");
}

function fnDoSimpleAction(obj,actionName,idValue) {
	bCancel=true;
	fnSelectItemById(idValue);
	return fnDoAction(obj, actionName);
}

function fnDoCancelForm(obj) {
	bCancel=true;
	return fnDoAction(obj, "cancelForm");
}

function fnDoUpToParent(objOrUrl) {
	bCancel=true;
	if (typeof objOrUrl=="string") {
		location.href=objOrUrl;
		return false;
	}
	return fnDoAction(objOrUrl, "upToParent");
}

function fnDoAdd(obj) {
	return fnDoAction(obj, "add");
}
function fnDoMultiSave(obj) {
	return fnDoAction(obj, "multiSave", __FMT.common_message_confirmSave);
}
function fnDoMultiDelete(obj) {
	var itemNames = fnGetSelectedItemNames();
	if (itemNames=="") {
		return false;
	}
	return fnDoAction(obj, "multiDelete", __FMT.common_message_confirmDeleteThis+ " " + itemNames+"?");
}
function fnDoReturnToSearch(obj) {
	bCancel=true;
	//document.forms[0].action=obj.getAttribute("savedUri");
	return fnDoAction(obj, "returnToSearch");
}
function fnDoNextItem(obj) {
	bCancel=true;
	return fnDoAction(obj, "nextItem");
}
function fnDoPrevItem(obj) {
	bCancel=true;
	return fnDoAction(obj, "prevItem");
}

var filterConditions=[];
/**
 * Manage which input control can be used.
 */
function fnChangeFilter(filterIndex) {
	var condictions = filterConditions[filterIndex];
	if (!condictions) {
		return;
	}
	var inputs = $("glSearchBar").getElementsByTagName('input');
	var selects = $("glSearchBar").getElementsByTagName('select');
	//get all input and select fields of search box
	var fields = [];
	for (i=0;i<inputs.length;i++) {
		if (inputs[i].type.toLowerCase()!="hidden") {
			fields.push(inputs[i]);
		}
	}
	for (i=0;i<selects.length;i++) {
		fields.push(selects[i]);
	}
	//set disallow mode or allow mode
	var disabled=(condictions.indexOf("disallow")==0);
	if (condictions.indexOf(",all")>0) {
		for (i=0;i<fields.length;i++) {
			var field = fields[i];
			if ("SRH@filter"!=field.name) {
				field.disabled=disabled;
			}
		}
	} else {
		for (i=0;i<fields.length;i++) {
			var field = fields[i];
			if (field.name && "SRH@filter"!=field.name) {
				if (condictions.indexOf(field.name)>0) {
					field.disabled=disabled;
				} else {
					field.disabled=!disabled;
				}
			}
		}
	}
}
var loadingMessage = "Loading...";
//set ajax Loading Msg.
jQuery.ajaxSetup({
   beforeSend: function(){
	fnShowLoading();
   },
   complete: function(){
	fnHideLoading();
   },
   success:function(){
	fnHideLoading();
	}
 });
 
function ajaxSuccessHandler(){
	log(ajaxSuccessHandler);
}

function ajaxCompleteHandler(){
	log(ajaxCompleteHandler);
}
/**
 * 当ajax在加载的时候，会提示这个loading，并锁屏。
 */
function fnShowLoading(){
	$j("#ajax_loading,#ajax_loading_BG").show();
}
function fnHideLoading(){
	$j("#ajax_loading,#ajax_loading_BG").hide();
}

// 显示提示信息
function sysMsg(msg,isError) {
	if(isError==undefined){isError=false;}
	$j("#showMsg_id").hide();
	$j("#showMsg_id div").slice(2).remove();
	$j("#showMsg_id").prepend("<div class='"+(isError?"del":"ok")+"'>"+msg+"</div>");
	$j("#showMsg_id").fadeIn("slow"); 
	__scrollFollow();
	__setTimeoutSysMsg();
}
var msgTimeout;
function __setTimeoutSysMsg(){
	if(msgTimeout){
		clearTimeout(msgTimeout);
		msgTimeout=null;
	}
	msgTimeout=setTimeout(function(){
		$j("#showMsg_id").slideUp("slow");
		$j(".top-message").fadeIn("slow");
		},5000);
}
function __scrollFollow(){
	var box=$j("#showMsg_id");
	var pageScroll =  parseInt( $j( document ).scrollTop() );
	var aniTop=pageScroll <=12?12:pageScroll;
	//box.animate({top:aniTop},1000);
	box.css("top",aniTop);
}


function sysMsg4p(msg,isError){
	if (opener && !opener.closed) {
		if (opener.sysMsg) 
			sysMsg(msg,isError);
	}else if(parent){
		if (parent.sysMsg) 
			parent.sysMsg(msg,isError);
	}
}

function ps(iframeId,methodName){
	var iframe=$j('#'+iframeId);
	if(iframe&&iframe.size()>0){
		//iframe.get(0).contentWindow.methodName();
		//eval("$j('#"+iframeId+"').get(0).contentWindow."+methodName).call(this);
		eval("$j('#"+iframeId+"').get(0).contentWindow."+methodName);
	}else{
		//eval(methodName).call(this);
		eval(methodName);
	}
}
function btnToP(){
	if(parent&&parent.tab_container){
		if(parent.__btnToP_check($j("#tid").val())){
			var pBtnSpace = $j(parent.document.getElementById("active-btn-space"));
			var btnSpace=$j("#btn-space");
			if(pBtnSpace.length>0&&btnSpace.length>0){
				var btns_clone=btnSpace.children().clone().removeAttr("id");
				btns_clone.find("*").removeAttr("id");
				$j(pBtnSpace).empty().append(btns_clone);
			}
		}
	}
}
function __btnToP_check(tid){
	var tabContainerSelectedIndex=tab_container.appTabs("option","selected");
	var href=$j("#tab_container").children("ul").find("a").eq(tabContainerSelectedIndex).attr("href");
	return href=="#"+tid;
}
function getSbtn(iframeId){
	if(tab_container&&$j(tab_container).length>0){
		var pBtnSpace=$j("#active-btn-space");
		var iframe=$j('#'+iframeId);
		var btnSpace=$j('#'+iframeId).find("#btn-space");
		if(btnSpace.length==0){
			btnSpace=$j('#'+iframeId).children("iframe").contents().find("#btn-space");
		}
		if(btnSpace){
			var btns_clone=btnSpace.children().clone().removeAttr("id");
			btns_clone.find("*").removeAttr("id");
			pBtnSpace.empty().append(btns_clone);
		}
	}
}

/**
 * 载入远程 HTML 文件代码并插入至 DOM 中,
 * @param {Object} prmDiv 需载入HTML 文件代码的Id，或jquery对象
 * @param {Object} prmUrl 待装入 HTML 网页网址
 * @param {Object} prms (可选) 发送至服务器的 key/value 数据;JSON格式
 * @param {Object} fillCallback (可选) 载入成功时回调函数
 * @param {Object} methodType 请求方式 ("POST" 或 "GET")， 默认为 "GET"
 */
function fillDivWithPage(prmDiv, prmUrl, prms, fillCallback,methodType) {
	var prmDivObj=null;
	if(prmDiv instanceof Object){
		prmDivObj=prmDiv;
	}else{
		prmDivObj=$j("#"+prmDiv);
	}
	if(!jQuery.isFunction( fillCallback )){
		methodType=fillCallback;
		fillCallback=null;
	}
	var type="GET";
	if(methodType){
		if(methodType.toUpperCase()=="POST")
			type="POST";
	}
	if(type=="GET"){
		if(prms)
			prmUrl+=(prmUrl.match(/\?/) ? "&" : "?") + $j.param(prms);
		prmDivObj.load(prmUrl,function(){initBtnStyle(prmDivObj);if(fillCallback)fillCallback.call(this);});
	}else{
		if(!prms)prms={};
		prmDivObj.load(prmUrl,prms,function(){initBtnStyle(prmDivObj);if(fillCallback)fillCallback.call(this);});
	}
}
/**
 * ajax保存数据后的回调，主要是处理错误信息
 * @param {Object} data
 */
var errors = new Array();
function fnSaveCallbackShowError(result){
	//清除原来的提示信息
	for (var i = 0; i < errors.length; i++) {
		errors[i].remove();
	}
	//当保存数据有错误后，显示相应的信息
	if (result.status == 2) {
		var jFiledErrors = result.data.jFiledErrors;
		$j.each(jFiledErrors, function(index, jFieldError){
			var fieldInput = $j(':input[name=' + jFieldError.field + ']:last');
			error = $j("<span class=\"fieldError\">" + jFieldError.message + "</span>");
			fieldInput.after(error);
			errors.push(error);
			error = $j('<img src="' + __ctxPath + '/images/iconWarning.gif" alt="' + __FMT.icon_warning + '" class="validationWarning"/>');
			fieldInput.parent().prev().append(error);
			errors.push(error);
			sysMsg4p(jFieldError.field+","+jFieldError.message,true);
		});
	}
}

function format(source, params) {
	if ( arguments.length == 1 ) 
		return function() {
			var args = $j.makeArray(arguments);
			args.unshift(source);
			return format.apply( this, args );
		};
	if ( arguments.length > 2 && params.constructor != Array  ) {
		params = $j.makeArray(arguments).slice(1);
	}
	if ( params.constructor != Array ) {
		params = [ params ];
	}
	$j.each(params, function(i, n) {
		source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
	});
	return source;
};
/**
全局搜索函数
**/
function fnGlobalSearch(){
	var globalSearchForm=$j("#globalSearchForm");
    var gsType = document.getElementById("gsType").value;
    var gsKeyword = document.getElementById("gsKeyword");
    
    if(gsType == "product"){
		globalSearchForm.attr('action',__ctxPath+"/catalog/product.html");
		gsKeyword.name="name";
    }
    if(gsType == "order"){
		globalSearchForm.attr('action',__ctxPath+"/order/salesOrder.html?orderNo="+gsKeyword.value);
		globalSearchForm.append('<input type="hidden" value="1" name="btnSearch"/>');
    }
    if(gsType == "content"){
		globalSearchForm.attr('action',__ctxPath+"/content/content.html");
		gsKeyword.name="gsContentTitle";
		globalSearchForm.append('<input type="hidden" value="1" name="globalSearch"/>');
    }
    if(gsType == "customer"){
		globalSearchForm.attr('action',__ctxPath+"/customer/customer.html");
		gsKeyword.name="username";
		globalSearchForm.append('<input type="hidden" value="1" name="btnSearch"/>');
		globalSearchForm.append('<input type="hidden" value="searchAction" name="doAction"/>');
    }
	globalSearchForm.get(0).submit();
}
function initBtnStyle(div){
	var btn_backs=div?jQuery("input.btn-back",jQuery(div)):jQuery("input.btn-back");
	//btn_backs.button();
}
function fnNewWindow(url,height,width){
	//settings=settings.replace(/\s*/g,"").toLowerCase();
	var LeftPosition = (screen.width) ? (screen.width-width)/2 : 0;
	var TopPosition = (screen.height) ? (screen.height-height)/2 : 0;
	var winName=new Date().getTime().toString().substr(6);
	var settings ='height='+height+',width='+width+',top='+TopPosition+',left='+LeftPosition+',scrollbars=yes,resizable=yes,location=yes,toolbar=yes';
	var win = window.open(url,winName,settings);
	win.focus();
}
function fnAutoRefreshTabHeight(tid){
	var iframe = parent.document.getElementById("tabIframeId_"+tid);
	$j(iframe).height($j(iframe).contents().find("body").attr('scrollHeight'));
	setInterval(function (){
		//var scrollHeight=$j(iframe).contents().find("body").attr('scrollHeight');
		var iframe_s=$j(iframe);
		var height=iframe_s.contents().find("body").height();
		var dialog=iframe_s.contents().find("div.ui-dialog:visible");
		var dialogHeight=0;
		if(dialog.length>0){
			//dialogHeight=dialog.height();
		}
		if(dialogHeight>0&&height<dialogHeight+110){
			height=dialogHeight+110;
		}
		if(height>0&&iframe_s.height()!=height){iframe_s.height(height);}
		},300);
}
var tab_container=null;
function fnCreateTabWindow(tabId,label,url,param){
	tabId=tabId?tabId:new Date().getTime().toString().substr(6);
	label=label?label:'New '+tabId;
	var index = $j( "li", tab_container ).index( $j( "a[href='#"+tabId+"']",tab_container).parent());
	param=param?('?'+param):'';
	param=param+"&tid="+tabId;
	if(index==-1){
		var hostUrl=location.protocol+"//"+location.hostname+":"+location.port;
		var pathname=location.pathname;
		if(pathname=="/"){
			hostUrl=hostUrl+pathname;
		}else{
			hostUrl=hostUrl+pathname.substring(0,pathname.indexOf("/",1)+1);
		}
		tab_container.append("<div id='"+tabId+"' style='display:none'>"+
				'<iframe id="tabIframeId_'+tabId+'" height="600" width="100%" scrolling="no" frameborder="0"></iframe>'+
				"</div>");
		tab_container.appTabs( "add" , "#"+tabId ,label );
		if (document.all){//IE
	    	doc = document.frames["tabIframeId_"+tabId].document;
	    }else{//Firefox
	    	doc = $("tabIframeId_"+tabId).contentDocument;
	    }
		try {
			doc.body.innerHTML='<img src="'+hostUrl+'images/icon/loading.gif">loading...';
		} catch (e) {}
		$j('#tabIframeId_'+tabId).attr("src",hostUrl+url+param);
		$j("#active-btn-space").empty();
		tab_container.appTabs( "select",111);
	}else{
		$j("#active-btn-space").empty();
		tab_container.appTabs( "select" , index );
		getSbtn('tabIframeId_'+tabId);
	}
}

function fnRemoveTab(tid){
	if($j("#"+tid).length>0){
		$j("#active-btn-space").empty();
		var index = $j( "li",tab_container).index($j( "a[href='#"+tid+"']",tab_container).parent() );
		tab_container.appTabs( "remove", index );
		getSbtn('tabIframeId_'+tid);
	}
}

function fnUpdateTab(tid,label,newTId){
	var a=$j( "a[href='#"+tid+"']",tab_container).html(label);
	if(newTId&&a){
		a.attr("href","#"+newTId);
		$j("#"+tid).attr("id",newTId);
		$j("#tabIframeId_"+tid).attr("id","tabIframeId_"+newTId);
	}
}
/**自有Tabs*/
jQuery.fn.extend({
  appTabs: function(o,o2,label) {
  	if((o)&&typeof(o)=="string"){
		if(o=="remove"){
			var lis=$j( " > li:has(a[href])", this.list);
			var li=lis.eq(o2);
			var href = li.find("a[href]:first-child").attr( "href" );
			var panel=$j(href,this);
			li.remove();
			panel.remove();
			var anchors=$j("ul:first li a[href]:first-child",this);
			if (li.hasClass( "selected") && anchors.length >= 1) {
				select(this,o2);
			}
		}
		if(o=="add"){
			var li = $j('<li><a href="'+o2+'">'+label+'</a><input type="button" value="" class="close" title="close"/></li>');
			li.appendTo(this.list);
			var panel = $j(o2);
			panel.hide().appendTo( this.list[0].parentNode);
		}
		if(o=="select"){
			select(this,o2);
			//getSbtn(iframeId)
		}
		if(o=="length"){
			var anchors=$j("ul:first li a[href]:first-child",this);
			return anchors.length;
		}
		if(o=="option"&&o2=="selected"){
			var lis=$j( " > li:has(a[href])", this.list);
			var index=-1
			for(var i=0;i<lis.length;i++){
				if($j(lis.get(i)).hasClass("selected")){
					index=i;
					break;
				}
			}
			return index;
		}
		return this;
	}
	function select(self,index){
		var anchors=$j("ul:first li a[href]:first-child",self);
		index=index>=anchors.length?anchors.length-1:index;
		anchors.each(function( i, a ) {
			$j(a).parent().toggleClass("selected",i==index);
			var href = $j( a ).attr( "href" );
			var hrefBase = href.split( "#" );
			if (hrefBase[0]) {
			}else if(hrefBase[1]) {
				var panel=$j(href,self).addClass("content");
				if(i==index){
					panel.show();
					getSbtn(hrefBase[1]);
				}else{
					panel.hide();
				}
			}
		});
	}
	o=$j.extend({selected:0},{type:1},o);
	var self=$j(this);
	self.list=self.find("ul:first").addClass(o.type==1?"top-tab":"sub-tab").eq( 0 );
	self.anchors=$j("ul:first li a[href]:first-child",self);
	select(self,o.selected);
	/*self.anchors.each(function(i,a) {
		$j(a).parent().toggleClass("selected",i==o.selected);
		var href = $j(a).attr("href");
		var hrefBase = href.split("#");
		if (hrefBase[0]) {
		}else if(hrefBase[1]) {
			var panel=$j(href,self);
			if(i==o.selected){
				panel.show();
			}else{
				panel.hide();
			}
		}
	});*/
	self.anchors.live("click",function(){
		var self2=this;
		var href = $j(this).attr("href");
		if($j(this).attr("target")){
			return true;
		}
		var hrefBase = href.split( "#" );
		if(hrefBase[ 0 ]){
		}else{
			var anchors2=$j("ul:first li a[href]:first-child",self);
			anchors2.each(function( i, a ) {
				href = $j( a ).attr( "href" );
				hrefBase = href.split( "#" );
				$j(a).parent().toggleClass("selected",a==self2);
				if (hrefBase[0]) {
				}else if(hrefBase[1]) {
					if(a==self2){
						$j(href,self).show();
						getSbtn(hrefBase[1]);
					}else{
						$j(href,self).hide();
					}
				}
			});
		}
		return false;
	});
	return self;
  }
});
jQuery(document).ready(function(){
	initBtnStyle();
	tab_container=$j("#tab_container").appTabs();
	if(parent&&parent.document.getElementById("tab_container")&&document.getElementById("tid")){
		fnAutoRefreshTabHeight($j("#tid").val());
	}
	$j("ul:first li input.close[type='button']",tab_container).live( "click", function() {
		var index = $j( "ul:first li", tab_container ).index( $j( this ).parent() );
		tab_container.appTabs( "remove", index );
	});
	$j( window ).scroll(__scrollFollow).resize(__scrollFollow);
	$j(".top-message").click(function(){$j("#showMsg_id").fadeIn("slow");__setTimeoutSysMsg();});
});

function openPopMenu(btn,id){
	var menu=$j("#"+id);
	var closePopMenu=function(){
		var target=getEventSourceObject();
		if($j(target).closest("#"+id).length==0&&target!=btn&&$j(target).closest(btn).length==0){
			$j("body").live("die",closePopMenu);
			menu.slideUp();
			$j(btn).removeClass("selected");
		}
	};
	if(menu.is(":hidden")){
		menu.slideDown();
		$j(btn).addClass("selected");
		$j("body").live("click",closePopMenu);
		menu.find("a").live("click",function(){
			menu.hide();
			$j(btn).removeClass("selected");
		});
	}else if(menu.is(":visible")){
		$j("body").live("die",closePopMenu);
		menu.slideUp();
		$j(btn).removeClass("selected");
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

function fnUpdatePagingSetting(count){
	$j.post(__ctxPath+"/editProfile.html?doAction=updatePagingSetting&count="+count,function (result){
		if(result.status==1){
			sysMsg(result.msg);
		}else{
			sysMsg(result.msg,true);
		}
	},"json");
}