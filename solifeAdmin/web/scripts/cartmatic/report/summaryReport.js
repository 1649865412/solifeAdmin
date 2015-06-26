
function fnRefreshReport(srcEventObj) {
	var obj = srcEventObj;
	var count = 1;
	while (obj != null && count < 8) {
		if (obj.className == "box-content-wrap") {
			fnShowReport(obj.id, srcEventObj);
			break;
		} else {
			obj = obj.parentNode;
			count++;
		}
	}
	return false;
}
var reportUrlPrefix = __ctxPath+"/report/simple/";
function fnShowReport(reportName, eventObj) {
	
	var reportUrl = reportUrlPrefix + reportName + ".html";
	var divId = reportName;
	var paramId = "prm_" + reportName;
	if (eventObj) {
		reportUrl = reportUrl + "?" + eventObj.id + "=" + eventObj.value;
	}
	fillDivWithPage(divId, reportUrl);
}
function fnShowAllReports() {
	var divCol = $("reports").getElementsByTagName("DIV");
	for (i = 0; i < divCol.length; i++) {
		if (divCol[i].className == "box-content-wrap") {
			fnShowReport(divCol[i].id);
		}
	}
}
fnShowAllReports();

