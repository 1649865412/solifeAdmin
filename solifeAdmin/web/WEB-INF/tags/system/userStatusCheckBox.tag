<%
	//Desc:user define  checkbox for status
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="id" %>
<%@ attribute name="name" %>
<%@ attribute name="value"%>
<%@ attribute name="disabled"%>
<%
	if(request.getAttribute("activeStatus")==null){
		request.setAttribute("activeStatus",com.cartmatic.estore.Constants.STATUS_ACTIVE);
		request.setAttribute("inactiveStatus",com.cartmatic.estore.Constants.STATUS_INACTIVE);
	}
	
%>
<c:if test="${empty function}">
	<c:set var="function" scope="request">
		<script type="text/javascript">
			function changeStatus(obj){		
				var hidStatus=document.getElementById("ck_"+obj.id);
				if(hidStatus.value==<c:out value="${activeStatus}"/>){
					hidStatus.value=<c:out value="${inactiveStatus}"/>;
				}
				else{
					hidStatus.value=<c:out value="${activeStatus}"/>;
				}
				
			}

			function checkAllOption(obj){
				var ck=document.getElementsByName("${name}");
				var ckVal=document.getElementsByName("ck_${name}");
				if(obj.checked){
					for(var i=0;i< ck.length;i++){
						ck[i].checked=true;
						ckVal[i].value=${activeStatus};
					}
				}else{
					for(var i=0;i< ck.length;i++){
						ck[i].checked=false;
						ckVal[i].value=${inactiveStatus};
					}
				}
			}
		</script>
	</c:set>
	<c:out value="${function}" escapeXml="false"/>
</c:if>
<c:set var="disabledVal" scope="page" value=""/>
<c:if test="${not empty disabled}">
	<c:set var="disabledVal" value="disabled"/> 
</c:if>
<c:choose>
	<c:when test="${value==activeStatus}">
		<input id="${id}" type="checkbox" name="${name}" value="${activeStatus}"  onclick="changeStatus(this)" checked="checked" ${disabledVal} />
		<fmt:message key="status.active"/>
		<input id="ck_${id}" type="hidden" name="ck_${name}" value="${activeStatus}"/>
	</c:when>
	<c:when test="${value==inactiveStatus}">
		<input id="${id}" type="checkbox" name="${name}" value="${inactiveStatus}" onclick="changeStatus(this)"/>
		<font color="red"><fmt:message key="status.inactive"/></font>
		<input id="ck_${id}" type="hidden" name="ck_${name}" value="${inactiveStatus}"/>
	</c:when>
	<c:otherwise>
		<input id="${id}" type="checkbox" name="${name}" value="${inactiveStatus}" onclick="changeStatus(this)"/>
		<input id="ck_${id}" type="hidden" name="ck_${name}" value="${inactiveStatus}"/>
	</c:otherwise>
</c:choose>

