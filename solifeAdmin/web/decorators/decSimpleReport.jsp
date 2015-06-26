<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<div id="list-btn-wrap" class="alignleft">
	<div class="tbox-title">
		<div class="header">
			<decorator:title />
		</div>
		<div class="others">
			<decorator:getProperty property="page.controls" />
		</div>
	</div>
	<div style="font:0px/0px sans serif; clear:both; display: block"></div>
</div>
<div class="clear"></div>
<div class="content">
	<decorator:body />
</div>
