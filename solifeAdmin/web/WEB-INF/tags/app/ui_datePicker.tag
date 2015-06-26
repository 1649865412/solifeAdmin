<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
attribute name="outPut" required="true"%>
<%@ attribute name="isHide" required="false" rtexprvalue="true" type="java.lang.Boolean" description="是否隐藏日期按钮"%>
<%@ attribute name="pattern" required="false"  type="java.lang.String" description="格式化"%>
<script type="text/javascript" defer>
$j(document).ready(function(){
	$j('#${outPut}').datepicker({showOn: 'button', showOtherMonths: true, 
		showWeeks: true, firstDay: 1, changeFirstDay: false,closeAtTop:false,
		onClose: function(date) {validateField($("${outPut}"));},		
		buttonImageOnly: true, buttonImage: '${ctxPath}/scripts/jquery/themes/cartmatic/images/calendar.gif'<c:if test="${pattern != null}">,dateFormat:'${pattern}'</c:if>}
	    );
		<c:if test="${isHide}">
		$j('#${outPut}').next().hide();
		</c:if>
});
</script>
