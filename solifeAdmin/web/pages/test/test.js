
function isJsLoaded(url) {
	var ss = document.getElementsByTagName("script");
	for (i = 0; i < ss.length; i++) {
		if (ss[i].src && ss[i].src.indexOf(url) != -1) {
			return true;
		}
	}
	return false;
}
function loadJS(url, defer) {
	if (isJsLoaded(url)) {
		return;
	}
	var xhr = null;
	if (window.ActiveXObject) {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		if (window.XMLHttpRequest) {
			xhr = new XMLHttpRequest();
		}
	}
	xhr.open("get", url, true);
	xhr.send(null);
	if (oXmlHttp.readyState == 4 && (oXmlHttp.status == 200 || oXmlHttp.status == 304)) {
		var jsContent = xhr.responseText;
		if (jsContent != null) {
			var oHead = document.getElementsByTagName("HEAD").item(0);
			var oScript = document.createElement("script");
			oScript.type = "text/javascript";
			oScript.defer = defer || true;
			oScript.text = jsContent;
			oHead.appendChild(oScript);
		}
	}
}
