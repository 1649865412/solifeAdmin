<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="name" %>
<%@ attribute name="value" %>
<%@ attribute name="style" %>
<c:choose>
   <c:when test="${empty value}">
      <select name="${name}" id="${name}">                     
       <%--  <option value="0" ><fmt:message key="status.notPublished"/></option> --%>
        <option value="1" ><fmt:message key="status.active"/></option>
        <option value="2" ><fmt:message key="status.inactive"/></option>
      </select>
   </c:when> 
   <c:when test="${value ge 0}">
       <c:choose>
        <c:when test="${value == '0'}">
             <input type="checkbox" name="checkbox_${name}" onclick="fn${name}(this,${name})" class="${style}" />
	       <fmt:message key="status.active"/>
	       <input type="hidden" name="${name}" value="0"/>
            <script>
	    function fn${name}($arg1,$arg2)
	    {
	        if ($arg1.checked==true)
	        {
	        	$arg2.value = "1";
	        }
	        else
	        {
	        	$arg2.value = "2";
	        }
              //bug
              if($arg2==true){
                  document.getElementById("status").value="1";                 
             }else if($arg2==false){
                  document.getElementById("status").value="2";   
             }

	    }
	    </script>

        </c:when>
        <c:when test="${value == '1'}">
	    <input type="checkbox" name="checkbox_${name}" onclick="fn${name}(this,${name})" class="${style}" checked="checked"/>
	    <fmt:message key="status.active"/>
	    <input type="hidden" name="${name}" value="${value}" />
	    <script>
	    function fn${name}($arg1,$arg2)
	    {
	        if ($arg1.checked==true)
	        {
	        	$arg2.value = "1";
	        }
	        else
	        {
	        	$arg2.value = "2";
	        }
              //bug
              if($arg2==true){
                  document.getElementById("status").value="1";                 
             }else if($arg2==false){
                  document.getElementById("status").value="2";   
             }

	    }
	    </script>
	</c:when>
	<c:when test="${value == '2'}">
	    <input type="checkbox" name="checkbox_${name}" onclick="fn${name}(this,${name})" class="${style}" />
	    <fmt:message key="status.active"/>
	    <input type="hidden" name="${name}" value="${value}" />
	    <script>
	    function fn${name}($arg1,$arg2)
	    {
	        if ($arg1.checked==true)
	        {
	        	$arg2.value = "1";
	        }
	        else
	        {
	        	$arg2.value = "2";
	        }
               //bug
              if($arg2==true){
                  document.getElementById("status").value="1";                 
             }else if($arg2==false){
                  document.getElementById("status").value="2";   
             }

	    }
	    </script>
	</c:when>
	<c:when test="${value == '3'}">
	    <fmt:message key="status.deleted"/>
	</c:when>

       </c:choose>       
   </c:when> 
   
</c:choose>


