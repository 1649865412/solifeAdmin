


















if (dwr == null) var dwr = {};
if (dwr.engine == null) dwr.engine = {};
if (DWREngine == null) var DWREngine = dwr.engine;





dwr.engine.setErrorHandler = function(handler) {
dwr.engine._errorHandler = handler;
};





dwr.engine.setWarningHandler = function(handler) {
dwr.engine._warningHandler = handler;
};





dwr.engine.setTextHtmlHandler = function(handler) {
dwr.engine._textHtmlHandler = handler;
};





dwr.engine.setTimeout = function(timeout) {
dwr.engine._timeout = timeout;
};





dwr.engine.setPreHook = function(handler) {
dwr.engine._preHook = handler;
};





dwr.engine.setPostHook = function(handler) {
dwr.engine._postHook = handler;
};





dwr.engine.setHeaders = function(headers) {
dwr.engine._headers = headers;
};





dwr.engine.setParameters = function(parameters) {
dwr.engine._parameters = parameters;
};


dwr.engine.XMLHttpRequest = 1;


dwr.engine.IFrame = 2;


dwr.engine.ScriptTag = 3;






dwr.engine.setRpcType = function(newType) {
if (newType != dwr.engine.XMLHttpRequest && newType != dwr.engine.IFrame && newType != dwr.engine.ScriptTag) {
dwr.engine._handleError(null, { name:"dwr.engine.invalidRpcType", message:"RpcType must be one of dwr.engine.XMLHttpRequest or dwr.engine.IFrame or dwr.engine.ScriptTag" });
return;
}
dwr.engine._rpcType = newType;
};





dwr.engine.setHttpMethod = function(httpMethod) {
if (httpMethod != "GET" && httpMethod != "POST") {
dwr.engine._handleError(null, { name:"dwr.engine.invalidHttpMethod", message:"Remoting method must be one of GET or POST" });
return;
}
dwr.engine._httpMethod = httpMethod;
};





dwr.engine.setOrdered = function(ordered) {
dwr.engine._ordered = ordered;
};





dwr.engine.setAsync = function(async) {
dwr.engine._async = async;
};





dwr.engine.setActiveReverseAjax = function(activeReverseAjax) {
if (activeReverseAjax) {

if (dwr.engine._activeReverseAjax) return;
dwr.engine._activeReverseAjax = true;
dwr.engine._poll();
}
else {

if (dwr.engine._activeReverseAjax && dwr.engine._pollReq) dwr.engine._pollReq.abort();
dwr.engine._activeReverseAjax = false;
}



};





dwr.engine.defaultErrorHandler = function(message, ex) {
dwr.engine._debug("Error: " + ex.name + ", " + ex.message, true);
if (message == null || message == "") alert("A server error has occured.");

else if (message.indexOf("0x80040111") != -1) dwr.engine._debug(message);
else alert(message);
};





dwr.engine.defaultWarningHandler = function(message, ex) {
dwr.engine._debug(message);
};





dwr.engine.beginBatch = function() {
if (dwr.engine._batch) {
dwr.engine._handleError(null, { name:"dwr.engine.batchBegun", message:"Batch already begun" });
return;
}
dwr.engine._batch = dwr.engine._createBatch();
};





dwr.engine.endBatch = function(options) {
var batch = dwr.engine._batch;
if (batch == null) {
dwr.engine._handleError(null, { name:"dwr.engine.batchNotBegun", message:"No batch in progress" });
return;
}
dwr.engine._batch = null;
if (batch.map.callCount == 0) return;


if (options) dwr.engine._mergeBatch(batch, options);


if (dwr.engine._ordered && dwr.engine._batchesLength != 0) {
dwr.engine._batchQueue[dwr.engine._batchQueue.length] = batch;
}
else {
dwr.engine._sendData(batch);
}
};


dwr.engine.setPollMethod = function(type) { dwr.engine.setPollType(type); };
dwr.engine.setMethod = function(type) { dwr.engine.setRpcType(type); };
dwr.engine.setVerb = function(verb) { dwr.engine.setHttpMethod(verb); };
dwr.engine.setPollType = function() { dwr.engine._debug("Manually setting the Poll Type is not supported"); };






dwr.engine._origScriptSessionId = "0996B01F72D2699FECEDC854EF937608";


dwr.engine._sessionCookieName = "JSESSIONID";


dwr.engine._allowGetForSafariButMakeForgeryEasier = "false";


dwr.engine._scriptTagProtection = "throw 'allowScriptTagRemoting is false.';";


dwr.engine._defaultPath = "/Buysino/dwr";


dwr.engine._pollWithXhr = "false";


dwr.engine._scriptSessionId = null;


dwr.engine._getScriptSessionId = function() {
if (dwr.engine._scriptSessionId == null) {
dwr.engine._scriptSessionId = dwr.engine._origScriptSessionId + Math.floor(Math.random() * 1000);
}
return dwr.engine._scriptSessionId;
};


dwr.engine._errorHandler = dwr.engine.defaultErrorHandler;


dwr.engine._warningHandler = dwr.engine.defaultWarningHandler;


dwr.engine._preHook = null;


dwr.engine._postHook = null;


dwr.engine._batches = {};


dwr.engine._batchesLength = 0;


dwr.engine._batchQueue = [];


dwr.engine._rpcType = dwr.engine.XMLHttpRequest;


dwr.engine._httpMethod = "POST";


dwr.engine._ordered = false;


dwr.engine._async = true;


dwr.engine._batch = null;


dwr.engine._timeout = 0;


dwr.engine._DOMDocument = ["Msxml2.DOMDocument.6.0", "Msxml2.DOMDocument.5.0", "Msxml2.DOMDocument.4.0", "Msxml2.DOMDocument.3.0", "MSXML2.DOMDocument", "MSXML.DOMDocument", "Microsoft.XMLDOM"];


dwr.engine._XMLHTTP = ["Msxml2.XMLHTTP.6.0", "Msxml2.XMLHTTP.5.0", "Msxml2.XMLHTTP.4.0", "MSXML2.XMLHTTP.3.0", "MSXML2.XMLHTTP", "Microsoft.XMLHTTP"];


dwr.engine._activeReverseAjax = false;


dwr.engine._outstandingIFrames = [];


dwr.engine._pollReq = null;


dwr.engine._pollCometInterval = 200;


dwr.engine._pollRetries = 0;
dwr.engine._maxPollRetries = 0;


dwr.engine._textHtmlHandler = null;


dwr.engine._headers = null;


dwr.engine._parameters = null;


dwr.engine._postSeperator = "\n";
dwr.engine._defaultInterceptor = function(data) { return data; };
dwr.engine._urlRewriteHandler = dwr.engine._defaultInterceptor;
dwr.engine._contentRewriteHandler = dwr.engine._defaultInterceptor;
dwr.engine._replyRewriteHandler = dwr.engine._defaultInterceptor;


dwr.engine._nextBatchId = 0;


dwr.engine._propnames = [ "rpcType", "httpMethod", "async", "timeout", "errorHandler", "warningHandler", "textHtmlHandler" ];


dwr.engine._partialResponseNo = 0;
dwr.engine._partialResponseYes = 1;
dwr.engine._partialResponseFlush = 2;










dwr.engine._execute = function(path, scriptName, methodName, vararg_params) {
var singleShot = false;
if (dwr.engine._batch == null) {
dwr.engine.beginBatch();
singleShot = true;
}
var batch = dwr.engine._batch;

var args = [];
for (var i = 0; i < arguments.length - 3; i++) {
args[i] = arguments[i + 3];
}

if (batch.path == null) {
batch.path = path;
}
else {
if (batch.path != path) {
dwr.engine._handleError(batch, { name:"dwr.engine.multipleServlets", message:"Can't batch requests to multiple DWR Servlets." });
return;
}
}


var callData;
var lastArg = args[args.length - 1];
if (typeof lastArg == "function" || lastArg == null) callData = { callback:args.pop() };
else callData = args.pop();


dwr.engine._mergeBatch(batch, callData);
batch.handlers[batch.map.callCount] = {
exceptionHandler:callData.exceptionHandler,
callback:callData.callback
};


var prefix = "c" + batch.map.callCount + "-";
batch.map[prefix + "scriptName"] = scriptName;
batch.map[prefix + "methodName"] = methodName;
batch.map[prefix + "id"] = batch.map.callCount;
for (i = 0; i < args.length; i++) {
dwr.engine._serializeAll(batch, [], args[i], prefix + "param" + i);
}


batch.map.callCount++;
if (singleShot) dwr.engine.endBatch();
};


dwr.engine._poll = function(overridePath) {
if (!dwr.engine._activeReverseAjax) return;

var batch = dwr.engine._createBatch();
batch.map.id = 0;
batch.map.callCount = 1;
batch.isPoll = true;
if (dwr.engine._pollWithXhr == "true") {
batch.rpcType = dwr.engine.XMLHttpRequest;
batch.map.partialResponse = dwr.engine._partialResponseNo;
}
else {
if (navigator.userAgent.indexOf("Gecko/") != -1) {
batch.rpcType = dwr.engine.XMLHttpRequest;
batch.map.partialResponse = dwr.engine._partialResponseYes;
}




else if (navigator.userAgent.indexOf("Safari/")) {
batch.rpcType = dwr.engine.XMLHttpRequest;
batch.map.partialResponse = dwr.engine._partialResponseYes;
}
else {
batch.rpcType = dwr.engine.XMLHttpRequest;
batch.map.partialResponse = dwr.engine._partialResponseNo;
}
}
batch.httpMethod = "POST";
batch.async = true;
batch.timeout = 0;
batch.path = (overridePath) ? overridePath : dwr.engine._defaultPath;
batch.preHooks = [];
batch.postHooks = [];
batch.errorHandler = dwr.engine._pollErrorHandler;
batch.warningHandler = dwr.engine._pollErrorHandler;
batch.handlers[0] = {
callback:function(pause) {
dwr.engine._pollRetries = 0;
setTimeout("dwr.engine._poll()", pause);
}
};


dwr.engine._sendData(batch);
if (batch.rpcType == dwr.engine.XMLHttpRequest && batch.map.partialResponse == dwr.engine._partialResponseYes) {
dwr.engine._checkCometPoll();
}
};


dwr.engine._pollErrorHandler = function(msg, ex) {

dwr.engine._pollRetries++;
dwr.engine._debug("Reverse Ajax poll failed (pollRetries=" + dwr.engine._pollRetries + "): " + ex.name + " : " + ex.message);
if (dwr.engine._pollRetries < dwr.engine._maxPollRetries) {
setTimeout("dwr.engine._poll()", 10000);
}
else {
dwr.engine._activeReverseAjax = false;
dwr.engine._debug("Giving up.");
}
};


dwr.engine._createBatch = function() {
var batch = {
map:{
callCount:0,
page:window.location.pathname + window.location.search,
httpSessionId:dwr.engine._getJSessionId(),
scriptSessionId:dwr.engine._getScriptSessionId()
},
charsProcessed:0, paramCount:0,
parameters:{}, headers:{},
isPoll:false, handlers:{}, preHooks:[], postHooks:[],
rpcType:dwr.engine._rpcType,
httpMethod:dwr.engine._httpMethod,
async:dwr.engine._async,
timeout:dwr.engine._timeout,
errorHandler:dwr.engine._errorHandler,
warningHandler:dwr.engine._warningHandler,
textHtmlHandler:dwr.engine._textHtmlHandler
};
if (dwr.engine._preHook) batch.preHooks.push(dwr.engine._preHook);
if (dwr.engine._postHook) batch.postHooks.push(dwr.engine._postHook);
var propname, data;
if (dwr.engine._headers) {
for (propname in dwr.engine._headers) {
data = dwr.engine._headers[propname];
if (typeof data != "function") batch.headers[propname] = data;
}
}
if (dwr.engine._parameters) {
for (propname in dwr.engine._parameters) {
data = dwr.engine._parameters[propname];
if (typeof data != "function") batch.parameters[propname] = data;
}
}
return batch;
};


dwr.engine._mergeBatch = function(batch, overrides) {
var propname, data;
for (var i = 0; i < dwr.engine._propnames.length; i++) {
propname = dwr.engine._propnames[i];
if (overrides[propname] != null) batch[propname] = overrides[propname];
}
if (overrides.preHook != null) batch.preHooks.unshift(overrides.preHook);
if (overrides.postHook != null) batch.postHooks.push(overrides.postHook);
if (overrides.headers) {
for (propname in overrides.headers) {
data = overrides.headers[propname];
if (typeof data != "function") batch.headers[propname] = data;
}
}
if (overrides.parameters) {
for (propname in overrides.parameters) {
data = overrides.parameters[propname];
if (typeof data != "function") batch.map["p-" + propname] = "" + data;
}
}
};


dwr.engine._getJSessionId =  function() {
var cookies = document.cookie.split(';');
for (var i = 0; i < cookies.length; i++) {
var cookie = cookies[i];
while (cookie.charAt(0) == ' ') cookie = cookie.substring(1, cookie.length);
if (cookie.indexOf(dwr.engine._sessionCookieName + "=") == 0) {
return cookie.substring(dwr.engine._sessionCookieName.length + 1, cookie.length);
}
}
return "";
};


dwr.engine._checkCometPoll = function() {
for (var i = 0; i < dwr.engine._outstandingIFrames.length; i++) {
var text = "";
var iframe = dwr.engine._outstandingIFrames[i];
try {
text = dwr.engine._getTextFromCometIFrame(iframe);
}
catch (ex) {
dwr.engine._handleWarning(iframe.batch, ex);
}
if (text != "") dwr.engine._processCometResponse(text, iframe.batch);
}
if (dwr.engine._pollReq) {
var req = dwr.engine._pollReq;
var text = req.responseText;
if (text != null) dwr.engine._processCometResponse(text, req.batch);
}


if (dwr.engine._outstandingIFrames.length > 0 || dwr.engine._pollReq) {
setTimeout("dwr.engine._checkCometPoll()", dwr.engine._pollCometInterval);
}
};


dwr.engine._getTextFromCometIFrame = function(frameEle) {
var body = frameEle.contentWindow.document.body;
if (body == null) return "";
var text = body.innerHTML;

if (text.indexOf("<PRE>") == 0 || text.indexOf("<pre>") == 0) {
text = text.substring(5, text.length - 7);
}
return text;
};


dwr.engine._processCometResponse = function(response, batch) {
if (batch.charsProcessed == response.length) return;
if (response.length == 0) {
batch.charsProcessed = 0;
return;
}

var firstStartTag = response.indexOf("//#DWR-START#", batch.charsProcessed);
if (firstStartTag == -1) {

batch.charsProcessed = response.length;
return;
}




var lastEndTag = response.lastIndexOf("//#DWR-END#");
if (lastEndTag == -1) {

return;
}


if (response.charCodeAt(lastEndTag + 11) == 13 && response.charCodeAt(lastEndTag + 12) == 10) {
batch.charsProcessed = lastEndTag + 13;
}
else {
batch.charsProcessed = lastEndTag + 11;
}

var exec = response.substring(firstStartTag + 13, lastEndTag);

dwr.engine._receivedBatch = batch;
dwr.engine._eval(exec);
dwr.engine._receivedBatch = null;
};


dwr.engine._sendData = function(batch) {
batch.map.batchId = dwr.engine._nextBatchId;
dwr.engine._nextBatchId++;
dwr.engine._batches[batch.map.batchId] = batch;
dwr.engine._batchesLength++;
batch.completed = false;

for (var i = 0; i < batch.preHooks.length; i++) {
batch.preHooks[i]();
}
batch.preHooks = null;

if (batch.timeout && batch.timeout != 0) {
batch.interval = setInterval(function() { dwr.engine._abortRequest(batch); }, batch.timeout);
}

if (batch.rpcType == dwr.engine.XMLHttpRequest) {
if (window.XMLHttpRequest) {
batch.req = new XMLHttpRequest();
}

else if (window.ActiveXObject && !(navigator.userAgent.indexOf("Mac") >= 0 && navigator.userAgent.indexOf("MSIE") >= 0)) {
batch.req = dwr.engine._newActiveXObject(dwr.engine._XMLHTTP);
}
}

var prop, request;
if (batch.req) {

if (batch.async) {
batch.req.onreadystatechange = function() {
if (typeof dwr != 'undefined') dwr.engine._stateChange(batch);
};
}

if (batch.isPoll) {
dwr.engine._pollReq = batch.req;

if (!document.all) batch.req.batch = batch;
}

var indexSafari = navigator.userAgent.indexOf("Safari/");
if (indexSafari >= 0) {
var version = navigator.userAgent.substring(indexSafari + 7);
if (parseInt(version, 10) < 400) {
if (dwr.engine._allowGetForSafariButMakeForgeryEasier == "true") batch.httpMethod = "GET";
else dwr.engine._handleWarning(batch, { name:"dwr.engine.oldSafari", message:"Safari GET support disabled. See getahead.org/dwr/server/servlet and allowGetForSafariButMakeForgeryEasier." });
}
}
batch.mode = batch.isPoll ? dwr.engine._ModePlainPoll : dwr.engine._ModePlainCall;
request = dwr.engine._constructRequest(batch);
try {
batch.req.open(batch.httpMethod, request.url, batch.async);
try {
for (prop in batch.headers) {
var value = batch.headers[prop];
if (typeof value == "string") batch.req.setRequestHeader(prop, value);
}
if (!batch.headers["Content-Type"]) batch.req.setRequestHeader("Content-Type", "text/plain");
}
catch (ex) {
dwr.engine._handleWarning(batch, ex);
}
batch.req.send(request.body);
if (!batch.async) dwr.engine._stateChange(batch);
}
catch (ex) {
dwr.engine._handleError(batch, ex);
}
}
else if (batch.rpcType != dwr.engine.ScriptTag) {
var idname = batch.isPoll ? "dwr-if-poll-" + batch.map.batchId : "dwr-if-" + batch.map["c0-id"];

if (batch.isPoll && window.ActiveXObject) {
batch.htmlfile = new window.ActiveXObject("htmlfile");
batch.htmlfile.open();
batch.htmlfile.write("<html>");

batch.htmlfile.write("<div><iframe className='wibble' src='javascript:void(0)' id='" + idname + "' name='" + idname + "' onload='dwr.engine._iframeLoadingComplete(" + batch.map.batchId + ");'></iframe></div>");
batch.htmlfile.write("</html>");
batch.htmlfile.close();
batch.htmlfile.parentWindow.dwr = dwr;
batch.document = batch.htmlfile;
}
else {
batch.div = document.createElement("div");

document.body.appendChild(batch.div);
batch.div.innerHTML = "<iframe src='javascript:void(0)' frameborder='0' style='width:0px;height:0px;border:0;' id='" + idname + "' name='" + idname + "' onload='dwr.engine._iframeLoadingComplete (" + batch.map.batchId + ");'></iframe>";
batch.document = document;
}
batch.iframe = batch.document.getElementById(idname);
batch.iframe.batch = batch;
batch.mode = batch.isPoll ? dwr.engine._ModeHtmlPoll : dwr.engine._ModeHtmlCall;
if (batch.isPoll) dwr.engine._outstandingIFrames.push(batch.iframe);
request = dwr.engine._constructRequest(batch);
if (batch.httpMethod == "GET") {
batch.iframe.setAttribute("src", request.url);
}
else {
batch.form = batch.document.createElement("form");
batch.form.setAttribute("id", "dwr-form");
batch.form.setAttribute("action", request.url);
batch.form.setAttribute("target", idname);
batch.form.target = idname;
batch.form.setAttribute("method", batch.httpMethod);
for (prop in batch.map) {
var value = batch.map[prop];
if (typeof value != "function") {
var formInput = batch.document.createElement("input");
formInput.setAttribute("type", "hidden");
formInput.setAttribute("name", prop);
formInput.setAttribute("value", value);
batch.form.appendChild(formInput);
}
}
batch.document.body.appendChild(batch.form);
batch.form.submit();
}
}
else {
batch.httpMethod = "GET";
batch.mode = batch.isPoll ? dwr.engine._ModePlainPoll : dwr.engine._ModePlainCall;
request = dwr.engine._constructRequest(batch);
batch.script = document.createElement("script");
batch.script.id = "dwr-st-" + batch.map["c0-id"];
batch.script.src = request.url;
document.body.appendChild(batch.script);
}
};

dwr.engine._ModePlainCall = "/call/plaincall/";
dwr.engine._ModeHtmlCall = "/call/htmlcall/";
dwr.engine._ModePlainPoll = "/call/plainpoll/";
dwr.engine._ModeHtmlPoll = "/call/htmlpoll/";


dwr.engine._constructRequest = function(batch) {

var request = { url:batch.path + batch.mode, body:null };
if (batch.isPoll == true) {
request.url += "ReverseAjax.dwr";
}
else if (batch.map.callCount == 1) {
request.url += batch.map["c0-scriptName"] + "." + batch.map["c0-methodName"] + ".dwr";
}
else {
request.url += "Multiple." + batch.map.callCount + ".dwr";
}

var sessionMatch = location.href.match(/jsessionid=([^?]+)/);
if (sessionMatch != null) {
request.url += ";jsessionid=" + sessionMatch[1];
}

var prop;
if (batch.httpMethod == "GET") {


batch.map.callCount = "" + batch.map.callCount;
request.url += "?";
for (prop in batch.map) {
if (typeof batch.map[prop] != "function") {
request.url += encodeURIComponent(prop) + "=" + encodeURIComponent(batch.map[prop]) + "&";
}
}
request.url = request.url.substring(0, request.url.length - 1);
}
else {

request.body = "";
for (prop in batch.map) {
if (typeof batch.map[prop] != "function") {
request.body += prop + "=" + batch.map[prop] + dwr.engine._postSeperator;
}
}
request.body = dwr.engine._contentRewriteHandler(request.body);
}
request.url = dwr.engine._urlRewriteHandler(request.url);
return request;
};


dwr.engine._stateChange = function(batch) {
var toEval;

if (batch.completed) {
dwr.engine._debug("Error: _stateChange() with batch.completed");
return;
}

var req = batch.req;
try {
if (req.readyState != 4) return;
}
catch (ex) {
dwr.engine._handleWarning(batch, ex);

dwr.engine._clearUp(batch);
return;
}

try {
var reply = req.responseText;
reply = dwr.engine._replyRewriteHandler(reply);
var status = req.status;

if (reply == null || reply == "") {
dwr.engine._handleWarning(batch, { name:"dwr.engine.missingData", message:"No data received from server" });
}
else if (status != 200) {
dwr.engine._handleError(batch, { name:"dwr.engine.http." + status, message:req.statusText });
}
else {
var contentType = req.getResponseHeader("Content-Type");
if (!contentType.match(/^text\/plain/) && !contentType.match(/^text\/javascript/)) {
if (contentType.match(/^text\/html/) && typeof batch.textHtmlHandler == "function") {
batch.textHtmlHandler();
}
else {
dwr.engine._handleWarning(batch, { name:"dwr.engine.invalidMimeType", message:"Invalid content type: '" + contentType + "'" });
}
}
else {

if (batch.isPoll && batch.map.partialResponse == dwr.engine._partialResponseYes) {
dwr.engine._processCometResponse(reply, batch);
}
else {
if (reply.search("//#DWR") == -1) {
dwr.engine._handleWarning(batch, { name:"dwr.engine.invalidReply", message:"Invalid reply from server" });
}
else {
toEval = reply;
}
}
}
}
}
catch (ex) {
dwr.engine._handleWarning(batch, ex);
}

dwr.engine._callPostHooks(batch);


dwr.engine._receivedBatch = batch;
if (toEval != null) toEval = toEval.replace(dwr.engine._scriptTagProtection, "");
dwr.engine._eval(toEval);
dwr.engine._receivedBatch = null;
dwr.engine._validateBatch(batch);
dwr.engine._clearUp(batch);
};







dwr.engine._validateBatch = function(batch) {

if (!batch.completed) {
for (var i = 0; i < batch.map.callCount; i++) {
if (batch.handlers[i] != null) {
dwr.engine._handleWarning(batch, { name:"dwr.engine.incompleteReply", message:"Incomplete reply from server" });
break;
}
}
}
}


dwr.engine._iframeLoadingComplete = function(batchId) {

var batch = dwr.engine._batches[batchId];
if (batch) dwr.engine._validateBatch(batch);
}


dwr.engine._remoteHandleCallback = function(batchId, callId, reply) {
var batch = dwr.engine._batches[batchId];
if (batch == null) {
dwr.engine._debug("Warning: batch == null in remoteHandleCallback for batchId=" + batchId, true);
return;
}


try {
var handlers = batch.handlers[callId];
batch.handlers[callId] = null;
if (!handlers) {
dwr.engine._debug("Warning: Missing handlers. callId=" + callId, true);
}
else if (typeof handlers.callback == "function") handlers.callback(reply);
}
catch (ex) {
dwr.engine._handleError(batch, ex);
}
};


dwr.engine._remoteHandleException = function(batchId, callId, ex) {
var batch = dwr.engine._batches[batchId];
if (batch == null) { dwr.engine._debug("Warning: null batch in remoteHandleException", true); return; }
var handlers = batch.handlers[callId];
batch.handlers[callId] = null;
if (handlers == null) { dwr.engine._debug("Warning: null handlers in remoteHandleException", true); return; }
if (ex.message == undefined) ex.message = "";
if (typeof handlers.exceptionHandler == "function") handlers.exceptionHandler(ex.message, ex);
else if (typeof batch.errorHandler == "function") batch.errorHandler(ex.message, ex);
};


dwr.engine._remoteHandleBatchException = function(ex, batchId) {
var searchBatch = (dwr.engine._receivedBatch == null && batchId != null);
if (searchBatch) {
dwr.engine._receivedBatch = dwr.engine._batches[batchId];
}
if (ex.message == undefined) ex.message = "";
dwr.engine._handleError(dwr.engine._receivedBatch, ex);
if (searchBatch) {
dwr.engine._receivedBatch = null;
dwr.engine._clearUp(dwr.engine._batches[batchId]);
}
};


dwr.engine._remotePollCometDisabled = function(ex, batchId) {
dwr.engine.setActiveReverseAjax(false);
var searchBatch = (dwr.engine._receivedBatch == null && batchId != null);
if (searchBatch) {
dwr.engine._receivedBatch = dwr.engine._batches[batchId];
}
if (ex.message == undefined) ex.message = "";
dwr.engine._handleError(dwr.engine._receivedBatch, ex);
if (searchBatch) {
dwr.engine._receivedBatch = null;
dwr.engine._clearUp(dwr.engine._batches[batchId]);
}
};


dwr.engine._remoteBeginIFrameResponse = function(iframe, batchId) {
if (iframe != null) dwr.engine._receivedBatch = iframe.batch;
dwr.engine._callPostHooks(dwr.engine._receivedBatch);
};


dwr.engine._remoteEndIFrameResponse = function(batchId) {
dwr.engine._clearUp(dwr.engine._receivedBatch);
dwr.engine._receivedBatch = null;
};


dwr.engine._eval = function(script) {
if (script == null) return null;
if (script == "") { dwr.engine._debug("Warning: blank script", true); return null; }

return eval(script);
};


dwr.engine._abortRequest = function(batch) {
if (batch && !batch.completed) {
clearInterval(batch.interval);
dwr.engine._clearUp(batch);
if (batch.req) batch.req.abort();
dwr.engine._handleError(batch, { name:"dwr.engine.timeout", message:"Timeout" });
}
};


dwr.engine._callPostHooks = function(batch) {
if (batch.postHooks) {
for (var i = 0; i < batch.postHooks.length; i++) {
batch.postHooks[i]();
}
batch.postHooks = null;
}
};


dwr.engine._clearUp = function(batch) {
if (!batch) { dwr.engine._debug("Warning: null batch in dwr.engine._clearUp()", true); return; }
if (batch.completed == "true") { dwr.engine._debug("Warning: Double complete", true); return; }


if (batch.div) batch.div.parentNode.removeChild(batch.div);
if (batch.iframe) {

for (var i = 0; i < dwr.engine._outstandingIFrames.length; i++) {
if (dwr.engine._outstandingIFrames[i] == batch.iframe) {
dwr.engine._outstandingIFrames.splice(i, 1);
}
}
batch.iframe.parentNode.removeChild(batch.iframe);
}
if (batch.form) batch.form.parentNode.removeChild(batch.form);


if (batch.req) {

if (batch.req == dwr.engine._pollReq) dwr.engine._pollReq = null;
delete batch.req;
}

if (batch.map && batch.map.batchId) {
delete dwr.engine._batches[batch.map.batchId];
dwr.engine._batchesLength--;
}

batch.completed = true;




if (dwr.engine._batchQueue.length != 0) {
var sendbatch = dwr.engine._batchQueue.shift();
dwr.engine._sendData(sendbatch);
}
};


dwr.engine._handleError = function(batch, ex) {
if (typeof ex == "string") ex = { name:"unknown", message:ex };
if (ex.message == null) ex.message = "";
if (ex.name == null) ex.name = "unknown";
if (batch && typeof batch.errorHandler == "function") batch.errorHandler(ex.message, ex);
else if (dwr.engine._errorHandler) dwr.engine._errorHandler(ex.message, ex);
if (batch) dwr.engine._clearUp(batch);
};


dwr.engine._handleWarning = function(batch, ex) {
if (typeof ex == "string") ex = { name:"unknown", message:ex };
if (ex.message == null) ex.message = "";
if (ex.name == null) ex.name = "unknown";
if (batch && typeof batch.warningHandler == "function") batch.warningHandler(ex.message, ex);
else if (dwr.engine._warningHandler) dwr.engine._warningHandler(ex.message, ex);
if (batch) dwr.engine._clearUp(batch);
};








dwr.engine._serializeAll = function(batch, referto, data, name) {
if (data == null) {
batch.map[name] = "null:null";
return;
}

switch (typeof data) {
case "boolean":
batch.map[name] = "boolean:" + data;
break;
case "number":
batch.map[name] = "number:" + data;
break;
case "string":
batch.map[name] = "string:" + encodeURIComponent(data);
break;
case "object":
if (data instanceof String) batch.map[name] = "String:" + encodeURIComponent(data);
else if (data instanceof Boolean) batch.map[name] = "Boolean:" + data;
else if (data instanceof Number) batch.map[name] = "Number:" + data;
else if (data instanceof Date) batch.map[name] = "Date:" + data.getTime();
else if (data && data.join) batch.map[name] = dwr.engine._serializeArray(batch, referto, data, name);
else batch.map[name] = dwr.engine._serializeObject(batch, referto, data, name);
break;
case "function":

break;
default:
dwr.engine._handleWarning(null, { name:"dwr.engine.unexpectedType", message:"Unexpected type: " + typeof data + ", attempting default converter." });
batch.map[name] = "default:" + data;
break;
}
};


dwr.engine._lookup = function(referto, data, name) {
var lookup;

for (var i = 0; i < referto.length; i++) {
if (referto[i].data == data) {
lookup = referto[i];
break;
}
}
if (lookup) return "reference:" + lookup.name;
referto.push({ data:data, name:name });
return null;
};


dwr.engine._serializeObject = function(batch, referto, data, name) {
var ref = dwr.engine._lookup(referto, data, name);
if (ref) return ref;



if (data.nodeName && data.nodeType) {
return dwr.engine._serializeXml(batch, referto, data, name);
}


var reply = "Object_" + dwr.engine._getObjectClassName(data) + ":{";
var element;
for (element in data) {
if (typeof data[element] != "function") {
batch.paramCount++;
var childName = "c" + dwr.engine._batch.map.callCount + "-e" + batch.paramCount;
dwr.engine._serializeAll(batch, referto, data[element], childName);

reply += encodeURIComponent(element) + ":reference:" + childName + ", ";
}
}

if (reply.substring(reply.length - 2) == ", ") {
reply = reply.substring(0, reply.length - 2);
}
reply += "}";

return reply;
};


dwr.engine._errorClasses = { "Error":Error, "EvalError":EvalError, "RangeError":RangeError, "ReferenceError":ReferenceError, "SyntaxError":SyntaxError, "TypeError":TypeError, "URIError":URIError };
dwr.engine._getObjectClassName = function(obj) {


if (obj && obj.constructor && obj.constructor.toString)
{
var str = obj.constructor.toString();
var regexpmatch = str.match(/function\s+(\w+)/);
if (regexpmatch && regexpmatch.length == 2) {
return regexpmatch[1];
}
}




if (obj && obj.constructor) {
for (var errorname in dwr.engine._errorClasses) {
if (obj.constructor == dwr.engine._errorClasses[errorname]) return errorname;
}
}



if (obj) {
var str = Object.prototype.toString.call(obj);
var regexpmatch = str.match(/\[object\s+(\w+)/);
if (regexpmatch && regexpmatch.length==2) {
return regexpmatch[1];
}
}


return "Object";
};


dwr.engine._serializeXml = function(batch, referto, data, name) {
var ref = dwr.engine._lookup(referto, data, name);
if (ref) return ref;

var output;
if (window.XMLSerializer) output = new XMLSerializer().serializeToString(data);
else if (data.toXml) output = data.toXml;
else output = data.innerHTML;

return "XML:" + encodeURIComponent(output);
};


dwr.engine._serializeArray = function(batch, referto, data, name) {
var ref = dwr.engine._lookup(referto, data, name);
if (ref) return ref;

var reply = "Array:[";
for (var i = 0; i < data.length; i++) {
if (i != 0) reply += ",";
batch.paramCount++;
var childName = "c" + dwr.engine._batch.map.callCount + "-e" + batch.paramCount;
dwr.engine._serializeAll(batch, referto, data[i], childName);
reply += "reference:";
reply += childName;
}
reply += "]";

return reply;
};


dwr.engine._unserializeDocument = function(xml) {
var dom;
if (window.DOMParser) {
var parser = new DOMParser();
dom = parser.parseFromString(xml, "text/xml");
if (!dom.documentElement || dom.documentElement.tagName == "parsererror") {
var message = dom.documentElement.firstChild.data;
message += "\n" + dom.documentElement.firstChild.nextSibling.firstChild.data;
throw message;
}
return dom;
}
else if (window.ActiveXObject) {
dom = dwr.engine._newActiveXObject(dwr.engine._DOMDocument);
dom.loadXML(xml);
return dom;
}
else {
var div = document.createElement("div");
div.innerHTML = xml;
return div;
}
};


dwr.engine._newActiveXObject = function(axarray) {
var returnValue;
for (var i = 0; i < axarray.length; i++) {
try {
returnValue = new ActiveXObject(axarray[i]);
break;
}
catch (ex) {   }
}
return returnValue;
};


dwr.engine._debug = function(message, stacktrace) {
var written = false;
try {
if (window.console) {
if (stacktrace && window.console.trace) window.console.trace();
window.console.log(message);
written = true;
}
else if (window.opera && window.opera.postError) {
window.opera.postError(message);
written = true;
}
}
catch (ex) {   }

if (!written) {
var debug = document.getElementById("dwr-debug");
if (debug) {
var contents = message + "<br/>" + debug.innerHTML;
if (contents.length > 2048) contents = contents.substring(0, 2048);
debug.innerHTML = contents;
}
}
};

