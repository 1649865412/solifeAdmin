<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@
attribute name="tabsId" required="true"%>
<%@ attribute name="type" type="java.lang.Short" rtexprvalue="true" description="显示类型,分别有1,2.默认为1"%>
<%@ attribute name="selected" type="java.lang.Integer" rtexprvalue="true" description="显示第几个Tab内容,默认为0,表示显示第一个tab内容"%>
<c:set var="ui_tabs" value="true" scope="request"/>
<script type="text/javascript" defer>
var app_tabs_${tabsId};
$j(document).ready(function(){
	app_tabs_${tabsId} = $j("#${tabsId}").appTabs({type:${(not empty type&&type>0)?type:1},selected:${(not empty selected&&selected>0)?selected:0}}<c:if test="${not empty selected&&selected>0}"></c:if>);
});
</script>