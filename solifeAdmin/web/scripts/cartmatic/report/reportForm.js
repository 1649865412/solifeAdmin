// JavaScript Document
var hasGen = false;
function fnGenReport($frm)
{
	fnResetSiae(); //resetSiae
	hasGen = true;//generated
	var preview = window.frames["reportPreview"]; 
	preview.document.open(); 
	preview.document.write("<html><body>Loading.....</body></html>"); 
	preview.document.close(); 
	var frmAction = $frm.action;
	$frm.target="reportPreview";
	$frm.action =__ctxPath+"/report/renderReport.html";
	$frm.submit();
	$frm.target="";
	$frm.action=frmAction;
}
function fnGenReportByCustom($frm, genUrl)
{
	fnResetSiae(); //resetSiae
	hasGen = true;//generated
	var preview = window.frames["reportPreview"]; 
	preview.document.open(); 
	preview.document.write("<html><body>Loading.....</body></html>"); 
	preview.document.close(); 
	var frmAction = $frm.action;
	$frm.target="reportPreview";
	$frm.action =genUrl;
	$frm.submit();
	$frm.target="";
	$frm.action=frmAction;
}
// Print report
function fnPrintReport()
{
	if (hasGen == false)
	{
		alert("No Report to print.Generate report first.");
		return;
	}
	var preview = window.frames["reportPreview"]; 
	preview.focus();
	preview.print();
}
// Export pdf
function fnExportPdf($frm)
{
	var frmAction = $frm.action;
	$frm.action =__ctxPath+"/report/renderReport.html?media=pdf";
	$frm.submit();
	$frm.action=frmAction;
}
function fnExportPdfByCustom($frm, genUrl)
{
	var frmAction = $frm.action;
	genUrl = genUrl + (genUrl.match(/\?/) ? '&' : '?') + "media=pdf";
	$frm.action =genUrl;
	$frm.submit();
	$frm.action=frmAction;
}
var hasAutoScale = false;
// autoscale iframe height.
function fnAutoScale()
{
	if (!hasAutoScale)
	{
		hasAutoScale = true;
		var preview = document.getElementById("reportPreview"); 
		//preview.height=preview.document.body.scrollHeight;
		if (preview.contentDocument && preview.contentDocument.body.offsetHeight)
		{
      		//FireFox
      		preview.height = preview.contentDocument.body.offsetHeight+18;
    	}
    	else if (preview.document && preview.document.body.scrollHeight)
		{
      		//ie
      		preview.height = preview.document.body.offsetHeight;
    	}
	}
}
// reset iframe height.
function fnResetSiae()
{
	hasAutoScale = false;
	var preview = document.getElementById("reportPreview"); 
	preview.height=500;
}