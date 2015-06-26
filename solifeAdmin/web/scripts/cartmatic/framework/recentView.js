
var _MAX_SAVED_ITEMS = 5;
var _HISTORY_MESSAGES_COOKIE = "SMC";
var _ITEM_SEPERATOR = "~~";

/*在本页面集中管理全局的Cookie，一般也不应有非全局的Cookie。*/
function addSimpleCookie(cookieName,cookieValue,expires) {
	setCookie(cookieName, cookieValue, expires, null, null, null);
}

function addCookieItem(cookieName, oItem) {
	var cookieValue = getCookie(cookieName);
	if (!cookieValue || cookieValue == null) {
		cookieValue = oItem;
	} else {
		if (cookieValue.indexOf(oItem) >= 0) {
			return;//identical or refresh,do nothing
		} else {
			cookieValue = oItem + _ITEM_SEPERATOR + cookieValue;
		}
	}
	var idx = 1;
	var itemCount = 0;
	while ((idx > 0) && (itemCount < _MAX_SAVED_ITEMS)) {
		idx = cookieValue.indexOf(_ITEM_SEPERATOR, idx + 1);
		if (idx > 0) {
			itemCount++;
		}
	}
	if (idx > 0) {
		cookieValue = cookieValue.substring(0, idx);
	}
	setCookie(cookieName, cookieValue, null, null, null, null);
}
function stringToArray(strViews, seperator) {
	var arrayViews = null;
	if (strViews && strViews != "") {
		arrayViews = strViews.split(_ITEM_SEPERATOR, _MAX_SAVED_ITEMS);
	}
	return arrayViews;
}
function saveRecentView(cookieName, extraAttrs) {
	var _viewUrl=parent.document.URL;
	var viewUrl=_viewUrl.substring(_viewUrl.indexOf("/", 10), _viewUrl.length);
	var curView = composeNameAndUrlStrView(viewUrl, parent.document.title);
	if (extraAttrs) for (var i=0;i<extraAttrs.length;i++){
		curView=curView+"~"+extraAttrs[i];
	}
	addCookieItem(cookieName, curView);
}
function composeNameAndUrlStrView(viewUrl, viewName) {
	var idx = viewName.indexOf(" | ");
	var tmpStr=viewUrl + "~" +( idx == -1 ? viewName : viewName.substring(idx + 3, viewName.length));
	return tmpStr;
}
function getViewDetails(strView) {
	return strView.split("~");
}
function getRecentViews(cookieName) {
	return navigator.cookieEnabled ? stringToArray(getCookie(cookieName)) : null;
}
function getRecentViewHtml(cookieName) {
	var htmlContent = "";
	if (navigator.cookieEnabled) {
		var arrayViews = getRecentViews(cookieName);
		if (arrayViews && arrayViews.length > 0) {
			for (i = 0; i < arrayViews.length; i++) {
				var viewDetails = getViewDetails(arrayViews[i]);
				if (viewDetails.length==2) {
					htmlContent = htmlContent + "<li><a href='" + viewDetails[0] + "'>" + viewDetails[1] + "</a></li>";
				} else if (viewDetails.length==3) {
					htmlContent = htmlContent + "<div class='box-product-item'><a href='"+viewDetails[0]+"'><img title='"+viewDetails[1]+"' src='"+viewDetails[2]+"'/></a></div>";
				} else {
					htmlContent = htmlContent + "<li><span>" + viewDetails[0]+"</span></li>";
				}
			}
		}
	}
	return htmlContent;
}
function fnFillHistory(boxHolderId,cookieName) {
	if (parent.document.getElementById(boxHolderId)) {
		parent.document.getElementById(boxHolderId).innerHTML=getRecentViewHtml(cookieName);
	}
}
/**scripts for store admin*/
function fnSaveHistoryMsg(sMsg) {
	addCookieItem(_HISTORY_MESSAGES_COOKIE, sMsg);
}
/**
 * TODO: persist click stream to server side, every 5 clicks. Maybe dwr is a good choice.
 */