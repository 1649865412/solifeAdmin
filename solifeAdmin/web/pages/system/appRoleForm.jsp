<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<app:pageHeading entityName="${appRole.appRoleName}" entityHeadingKey="appRoleDetail.heading" />
<content tag="buttons">
	<cartmatic:cartmaticBtn btnType="save" onclick="return fnDoSave(this,'roleName');" />
    <c:if test="${appRole.appRoleId!=null}">
		<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this,'roleName');" />
	</c:if>
	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<c:set var="checkboxReadOnly" scope="page"></c:set>
<c:if test="${appRole.roleName=='admin'}">
	<c:set var="checkboxReadOnly" scope="page"> onclick="this.checked=true;"</c:set>
</c:if>
<app:showBindErrors bindPath="appRole.*" />
	<form:form method="post" cssClass="mainForm" id="appRole" commandName="appRole"
			action="${ctxPath}/system/appRole.html" onsubmit="return validateAppRole(this);">
		<form:hidden path="version" />
		<input type="hidden" name="appRoleId" value="${appRole.appRoleId}"/> 
		<TABLE class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		    <tr>
		        <td class="FieldLabel">
		            <StoreAdmin:label key="appRole.roleName"/>
		        </td>
		        <td>
		        	<spring:bind path="appRole.roleName">
		        		<c:if test="${empty appRole.appRoleId}">
		            	<input class="Field400" type="text" name="${status.expression}"
		                    id="${status.expression}"
		                	value="${status.value}"/>
		                </c:if>
		                <c:if test="${not empty appRole.appRoleId }">
		                	${appRole.roleName}<input type="hidden" name="roleName" id="roleName" value="${status.value}"/>
		                </c:if>
		            </spring:bind>
		        </td>
		    </tr>
		    <tr>
		        <td class="FieldLabel"><StoreAdmin:label key="appRole.roleDetail"/></td>
		        <td>
		        	<spring:bind path="appRole.roleDetail">
		        		<textarea cols="48" rows="6" id="${status.expression}" name="${status.expression}" class="Field400">${status.value}</textarea>
		            	<span class="fieldError"><c:out value="${status.errorMessage}"/></span>
		            </spring:bind>
		        </td>
		    </tr>
		    <tr>
		    	<td class="FieldLabel"><StoreAdmin:label key="appRoleDetail.authority"/></td>
		        <td>
				<TABLE class="table-list" cellSpacing="0" cellPadding="0" width="100%" border="0">
					<tr height="0px">
						<th  align="left" style="text-align:left">
							<input type="checkbox" name="ckAll" onclick="selectedAll(this);"><fmt:message key="resource.resourceName"/>
						</td>
						<th  align="left" style="text-align:left">
							<fmt:message key="resource.resourceString"/></td>
						<th  align="left" style="text-align:left">
							Action</td>
					</tr>
					<c:forEach items="${allResource}" var="rs" >
						<tr>
							<td align="left" style="text-align:left" width="20%">
								<input type="checkbox" name="ckResourceId" value="${rs.appResourceId}" ${checkboxReadOnly}>
								${rs.resourceName }
							</td>
							<td style="text-align:left;width:30%">${rs.resourceString}</td>
							<td style="text-align:left">
							<% 
								com.cartmatic.estore.common.model.system.AppResource resource = (com.cartmatic.estore.common.model.system.AppResource)pageContext.getAttribute("rs", pageContext.PAGE_SCOPE);;
								if (resource.getResPermission()!= null)
								{
								    String[] masks = resource.getResPermission().split(",");
								    for (int i = 0; i < masks.length; i++)
								    {
								        String mask = masks[i];
								        out.println("<input type='checkbox' name='mask_"+ resource.getAppResourceId()+"' value='"+ (1<<i) +"'/>"+mask);
								    }
								}
							%>
							</td>
						</tr>
					</c:forEach>
				</table>
	        </td>
	    </tr>
  	</table>
</form:form>

<v:javascript formName="appRole" staticJavascript="false" />
<script type="text/javascript">
    document.forms["appRole"].elements["roleName"].focus();
    var selResourceIds=new Array();
	<c:forEach items="${appRole.appResources}" var="rs" varStatus="i">
		selResourceIds[${i.index}]=${rs.appResourceId};
	</c:forEach>
	
	var ckResource=document.getElementsByName("ckResourceId");
	for(var i=0;i<selResourceIds.length;i++){
		for(var j=0;j<ckResource.length;j++){
			if(selResourceIds[i]==ckResource[j].value){
				ckResource[j].checked=true;
				break;
			}
		}
	}
	function selectedAll(obj){
		var ckResourceIds=document.getElementsByName("ckResourceId");
		if(ckResourceIds!=null){
			for(var i=0;i<ckResourceIds.length;i++){
				ckResourceIds[i].checked=obj.checked;
			}
		}
	}
</script>