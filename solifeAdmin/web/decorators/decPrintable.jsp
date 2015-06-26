<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%-- Include common set of tag library declarations for each layout --%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
		<%@ include file="./include/meta.jspf"%>
		<%@ include file="./include/styles.jspf"%>
		<%@ include file="./include/javascripts.jspf"%>
		<title><fmt:message key="webapp.name" /> | <decorator:title />
		</title>
		<style type="text/css" media="print">
	<!--
	  .notPrint{
	   display: none;
	   }
	-->
	</style>
		<style type="text/css">
	button{ display:none;}
	input{	
            background-color: #fff;
			background-repeat: no-repeat;
            font-family:verdana,arial,helvetica,sans-serif; 
			font-size: 8pt;
			font-weight: normal;
			color: #2e8bc3;
			border: 1px solid #fff;
			padding:1px 0 2px 2px !important;padding:1px 0 2px 2px;
			}
	.btn-update{ display:none;}
	.btn-delete{ display:none;}
	.btn-blue{ display:none;}
	</style>
		<decorator:head />
	</head>
	<body
		<decorator:getProperty property="body.id" writeEntireProperty="true"/>
		<decorator:getProperty property="body.onload" writeEntireProperty="true"/>
		<decorator:getProperty property="body.onunload" writeEntireProperty="true"/>>
		<div class='notPrint'>
			<div class="wrap-print">
				<a href="javascript:window.print()"><span class="btn-print" style="padding-left:10px;">print</span>
				</a>&nbsp;&nbsp;
				<a href="javascript:window.close()"><span class="btn-cancel" style="padding-left:10px;">close</span>
				</a>
			</div>
		</div>
		<decorator:getProperty property="page.form-start" />
		<div id="content-title">
			<decorator:getProperty property="page.heading" />
		</div>
		<decorator:body />
	</body>
</html>
