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
		<%@ include file="./include/uiResource.jspf"%>
		<title><fmt:message key="webapp.name" /> | <decorator:title />
		</title> 
		<decorator:head />
	</head>
	<body
		<decorator:getProperty property="body.id" writeEntireProperty="true"/>
		<decorator:getProperty property="body.onload" writeEntireProperty="true"/>
		<decorator:getProperty property="body.onunload" writeEntireProperty="true"/>>
		<div class="lyt-wrap">
			<%-- ========== 头部开始 ========= --%>
			<div class="lyt-header">
			<%@ include file="./include/header.jspf"%>
				<div class="position">
					<div class="left"><decorator:title /></div>
				</div>
			</div>
			<%-- ========== 头部结束 ========= --%>
			<decorator:body />
			<div class="lyt-footer">
				<%@ include file="./include/footer.jspf"%>
			</div>
		</div>
	</body>
</html>
