<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%><%@
attribute name="width" required="false"%>
<c:if test="${empty width}"><c:set var="width" value="244"/></c:if>
<content tag="leftMenu">
<td width="${width}" id="l_menu_content" class="td-left">
	<jsp:doBody />
</td>
<%-- <script type="text/javascript" defer>
function fnToggleMenu()
{
	if($("l_menu_content").style.display == "none")
	{
		$j("#l_menu_content").fadeIn("slow");
		setCookie("L_MENU_SHOW","1");
	}
	else
	{
		$j("#l_menu_content").fadeOut("slow");
		setCookie("L_MENU_SHOW","0");
	}
}
$j(document).ready(function(){
	if (getCookie("L_MENU_SHOW")=='0')
	{
		$j("#l_menu_content").hide();
	}
});
</script>--%>
</content>