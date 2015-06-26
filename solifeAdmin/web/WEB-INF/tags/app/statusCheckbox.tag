<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="name" %>
<%@ attribute name="value" %>
<%@ attribute name="style" %>
<c:choose>
    <c:when test="${value == '0'}">
	    <fmt:message key="status.notPublished"/>
	</c:when>
	<c:when test="${value == '1'||empty value}">
	    <input type="checkbox" name="checkbox_${name}" id="checkbox_${name}" onclick="fn${name}(this,'${name}')" class="${style}" checked/>
	    <label for="checkbox_${name}"><fmt:message key="status.active"/></label>
	    <input type="hidden" name="${name}" id="${name}" value="${empty value ? 1 : value}" />
	    <script type="text/javascript" defer>
	    function fn${name}($arg1,$arg2)
	    {
	        if ($arg1.checked==true)
	        {
	        	$($arg2).value = "1";
	        }
	        else
	        {
	        	$($arg2).value = "2";
	        }
	    }
	    </script>
	</c:when>
	<c:when test="${value == '2'}">
	    <input type="checkbox" name="checkbox_${name}" id="checkbox_${name}" onclick="fn${name}(this,'${name}')" class="${style}" />
	    <label for="checkbox_${name}"><fmt:message key="status.active"/></label>
	    <input type="hidden" name="${name}" id="${name}" value="${value}" />
	    <script type="text/javascript" defer>
	    function fn${name}($arg1,$arg2)
	    {
	        if ($arg1.checked==true)
	        {
	        	$($arg2).value = "1";
	        }
	        else
	        {
	        	$($arg2).value = "2";
	        }
	    }
	    </script>
	</c:when>
	<c:when test="${value == '3'}">
	    <fmt:message key="status.deleted"/>
	</c:when>
	<c:when test="${value == '4'}">
	    <fmt:message key="status.expired"/>
	</c:when>
	<c:when test="${value == '5'}">
	    <fmt:message key="status.upcoming"/>
	</c:when>
</c:choose>
