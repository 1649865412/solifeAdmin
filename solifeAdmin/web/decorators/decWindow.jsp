<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%-- Include common set of tag library declarations for each layout --%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="system" tagdir="/WEB-INF/tags/system"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
		<%@ include file="./include/meta.jspf"%>
		<%@ include file="./include/styles.jspf"%>
		<%@ include file="./include/javascripts.jspf"%>
		<title><decorator:title />
		</title>
		<decorator:head />
	</head>
	<body
		<decorator:getProperty property="body.id" writeEntireProperty="true"/>
		<decorator:getProperty property="body.onload" writeEntireProperty="true"/>
		<decorator:getProperty property="body.onbeforeunload" writeEntireProperty="true"/>
		<decorator:getProperty property="body.onunload" writeEntireProperty="true"/>>
		<div class="top-layer" id="ajax_loading_BG" style="display: none;"></div>
	    <div class="loading" id="ajax_loading" style="display: none;"></div>
		<div class="blank10"></div>
		<decorator:body />
		<%-- todo 根据变量,加载ui的js和css--%>
		<%@ include file="./include/uiResource.jspf"%>
		<%@ include file="./include/message.jspf"%>
	</body>
</html>
