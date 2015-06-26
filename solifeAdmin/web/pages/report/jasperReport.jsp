<%@ include file="/common/taglibs.jsp"%><head>
<script type="text/javascript" src="<c:url value="/scripts/cartmatic/catelog/selector.js"/>
"></script>
	<script type="text/javascript">
var showReportParamsUrl = "${ctxPath}/report/jasperReport.html?decorator=selecter&doAction=showReportParams&reportName=";
var currentTabs = null;
var currentReport=null;

function fnChangeView(id,el){
 var viewP = $("viewPanel");
 var mangeP = $("managePanel");
 var htmlView = $("ViewHtml");
	if(id==0){
		fnShow(viewP);
		fnShow(htmlView);		
		fnHide(mangeP);
	}else{
		fnShow(mangeP);
		fnHide(htmlView);		
		fnHide(viewP);
	}
 
}

function fnSelectReport(reportName,el) {
    $j('#managePanel').hide();
    $j('#viewPanel').show();
	 el.style.color="red";
	 if (currentReport!=null)
	  currentReport.style.color="#104368";
	 currentReport=el;
	 var delForm=$("deleteReportForm");	
	 if(delForm){
	  delForm.reportFileName.value=reportName;
	 }
    $("reportName").value=reportName;
    if (reportName=="") {
        return;
    }
    var url=showReportParamsUrl+reportName;
    fillDivWithPage("boxReportCriteria",url);
    $("reportPreview").src="";
    //alert($j("#reportForm").get(0).innerHTML);
}
function fnDelReport(fileName){
 if(confirm("<fmt:message key="jasperreport.question"/>")){
   var frm=$("deleteReportForm");
   if(frm){
   	frm.reportFileName.value=fileName;
   	frm.submit();
   }
  }
}

function fnGetReport(type){
	$j("#ViewHtml").show();
 var reportType=$("_REPORT_FORMAT");
 var frm = $("reportForm");
 if(reportType){
 	reportType.value=type.value;
 }
 if(frm){
 frm.action="${ctxPath}/report/jasperReport.html?doAction=generateReport&rand="+Math.random();
 	frm.submit();
 }
}

function fnC($result)
{
	var cName = document.getElementById("cName");
	var categoryPath = document.getElementById("cPath");
	cName.value = $result[0][1];
	categoryPath.value = $result[0][2]+"%";
}

function fnClean($name, $id)
{
	var arg1 = document.getElementById($name);
	var arg2 = document.getElementById($id);
	arg1.value="";
	arg2.value="";
}
function  printHtml(){  
	var win=window.open("about:blank");//打开一个空页面
	//win.document.write($j("#reportPreview").get(0).document.body.innerHTML);
	//alert($j("#reportPreview").get(0).src);
	if (document.all){//IE
    	doc = document.frames["reportPreview"].document;
    }else{//Firefox    
    	doc = $("reportPreview").contentDocument;
    }
    win.document.write(doc.body.innerHTML);
    win.print();
    //win.close();
}
</script>
	<title><fmt:message key="report.JasperReport.heading" /></title>

	<content tag="heading">
	<fmt:message key="report.JasperReport.heading" />
	</content>
</head>
<form name="deleteReportForm" id="deleteReportForm"
	action="${ctxPath}/report/jasperReport.html" method="post"
	onsubmit="return fnDelReport();">
	<input type="hidden" name="doAction" value="deleteReport" />
	<input type="hidden" name="reportFileName" value="" />
</form>
<%--  
<content tag="buttons">
<cartmatic:cartmaticBtn btnType="viewReport" onclick="fnChangeView(0,this);" />
</content>
--%>
 
<div class="box-content-wrap">
	<app:ui_leftMenu>
	<div class="sidebar-left">
		<app:ui_tabs tabsId="report_menu_tabs" />
		<div class="tab" id="report_menu_tabs">
			<ul>
				<li>
					<a href="#"><fmt:message key="report.title"/> </a>
				</li>
			</ul>
			<div class="content">
				<%--View--%>
				<div>
					<c:set var="step" value="0" />
					<c:forEach var="reportItem" items="${reportList}">
						<c:if test="${not fn:startsWith(reportItem[1],'sub')}">
							<div style="margin-top: 10px;">
								<a href="javascript:;" onclick="javascript:fnSelectReport('${reportItem[0]}',this);"><fmt:message key="report.${reportItem[1]}" />
								</a>
							</div>
						</c:if>
					</c:forEach>
					<div style="margin-top: 10px;">
						<a href="javascript:;" onclick="$j('#viewPanel').hide();$j('#ViewHtml').hide();$j('#managePanel').show();">安装编译</a>
					</div>
				<div>
			</div>
		</div>
	</div>
	</app:ui_leftMenu>
	<div class="box-content" id="viewPanel">
		<div class="content">
			<%--View--%>
			<form name="reportForm" id="reportForm"
				action="${ctxPath}/report/jasperReport.html?doAction=generateReport"
				method="post" target="reportPreview">
						<div>
						<input type="hidden" id="reportName" name="reportName" value="">
						<input type="hidden" id="decorator" name="decorator"
							value="selecter">
						</div>
						<div id="boxReportCriteria">
						</div>
			</form>
		</div>
		
	</div>
	<div class="box-content" id="managePanel"<c:if test="${!(param.doAction=='compileJasperReports'||param.doAction=='uploadReport')}"> style="display: none;"</c:if>>
		<div class="top"></div>
		<div class="content">
			<%--Manage--%>
			<table width="100%" class="table-content" border="0" cellspacing="0"
				cellpadding="0">

				<tr>
					<td>
						<c:set var="step" value="0" />
						<c:forEach var="reportItem" items="${reportList}">
							<div style="margin:2px 0px 4px 0px;width:25%; float:left;">
								${reportItem[1]}&nbsp;
								<a href="javascript:;"
									onclick="javascript:fnDelReport('${reportItem[0]}');"><fmt:message
										key="jasperreport.delete" /> </a>
							</div>
						</c:forEach>

					</td>
				</tr>
				<tr>
					<td>
						<table class="table-content" cellspacing="0" cellpadding="0"
							width="100%" border="0">
							<tr>
								<td class="FieldLabel">
									<fmt:message key="jasperreport.complie" />
								</td>
								<td>
									<form name="reportManagementForm"
										action="${ctxPath}/report/jasperReport.html" method="post">
										<input type="hidden" id="doAction" name="doAction"
											value="defaultAction">
										<input type="submit" id="btnCompileReports"
											name="btnCompileReports"
											value="<fmt:message key="jasperreport.complie"/>"
											onclick='javascript:$("doAction").value="compileJasperReports"'
											class="btn-common">
									</form>
								</td>
							</tr>
							<tr>
								<td class="FieldLabel">
									<fmt:message key="jasperreport.setup" />
								</td>
								<td>

									<form name="reportManagementUpload"
										action="${ctxPath}/report/jasperReport.html?doAction=uploadReport" method="post"
										enctype="multipart/form-data">
										<input type="file" name="file" />
										<input type="submit" class="btn-common" value="<fmt:message key="jasperreport.upload" />">
									</form>

								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="blank8"></div>
	<div class="box-content" id="ViewHtml">
		<div class="content">
			<iframe  width="100%" scrolling="auto" frameborder="0" height="800"
				name="reportPreview" id="reportPreview"></iframe>	
		</div>
	</div>
</div>