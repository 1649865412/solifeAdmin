<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="customer" tagdir="/WEB-INF/tags/customer"%>
<%@ taglib prefix="system" tagdir="/WEB-INF/tags/system"%>
<title><fmt:message key="appAdminDetail.title"/></title>
<script type="text/javascript">
	var isNeedSetRole=false;
	<c:if test="${appAdmin.username!='admin'}">
		isNeedSetRole=true;
	</c:if>
	var selRoleIds=new Array();
	window.onload=function(){
		if(!isNeedSetRole) return;
		<c:forEach items="${appAdmin.userRoles}" var="role" varStatus="i">
			selRoleIds[${i.index}]=${role.appRoleId};
		</c:forEach>
		var roleSelector=$("appRoleIds");
		for(var i=0;i<selRoleIds.length;i++){
			for(var j=0;j<roleSelector.options.length;j++){
				if(selRoleIds[i]==roleSelector.options[j].value){
					roleSelector.options[j].selected=true;
					break;
				}
			}
		}
	}
</script>
<app:showBindErrors bindPath="appAdmin.*" />
<content tag="buttons">		
	<cartmatic:cartmaticBtn btnType="save" onclick="submitThisForm();"/>
    <c:if test="${(not empty appAdmin.appuserId) and appAdmin.username!='admin'}">
    	<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoDelete(this);" />
    </c:if>
   	<cartmatic:cartmaticBtn btnType="cancel" onclick="return fnDoCancelForm(this);" />
</content>
<!-- grant role window start-->
<div id="roleSelectorDiv" style="position:absolute;width:540px;height:320px;display:none"></div>
<!-- grant role window end-->
<form class="mainForm" method="post" action="<c:url value="/system/appAdmin.html"/>" id="appAdmin" >
<input type="hidden" name="appAdminId" value="${appAdmin.appuserId}"/> 
<spring:bind path="appAdmin.version">
	<input type="hidden" name="version" value="<c:out value="${status.value}"/>"/> 
</spring:bind>
<spring:bind path="appAdmin.appuserId">
	<input type="hidden" name="appuserId" value="<c:out value="${status.value}"/>"/> 
</spring:bind>
	<TABLE class="table-content" cellSpacing="0" cellPadding="0" width="100%" border="0">
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.username"/>
		 	</td>
		 	<td>
				<spring:bind path="appAdmin.username">
				<c:if test="${empty appAdmin.appuserId}">
					<input class="Field400" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}"/>
					<cartmatic:ui_tip id="userNameruleTip"><fmt:message key="appUser.userName.rule"/></cartmatic:ui_tip>
				</c:if>
				<c:if test="${not empty appAdmin.appuserId}">
					<c:out value="${status.value}"/>
					<input type="hidden" name="username" value="${appAdmin.username}"/>
				</c:if>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.title"/>
		 	</td>
			<td>
				<spring:bind path="appAdmin.title">
					<customer:userTitle id="title" name="title" value="${status.value}" />
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.firstname" />
			</td>
			<td>
				<spring:bind path="appAdmin.firstname">
					<input class="Field400" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /></span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.lastname" />
			</td>
			<td>
				<spring:bind path="appAdmin.lastname">
					<input class="Field400" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /></span>
				</spring:bind>
			</td>
		</tr>
		<c:if test="${appAdmin.username!='admin'}">
   		<tr>
			<td class="FieldLabel">
				<fmt:message key="appAdmin.password" />：
			</td>
			<td>
				<input type="password" class="Field400" name="newPassword" id="newPassword" />
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<fmt:message key="appAdmin.confirmPassword" />：
			</td>
			<td>
				<input type="password" class="Field400" name="confirmPassword" id="confirmPassword" vequalTo="与密码不一致,请重新输入"/>
			</td>
		</tr>
   		</c:if>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.email" />
			</td>
			<td>
				<spring:bind path="appAdmin.email">
					<input class="Field400" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
				</spring:bind>
				<span class="fieldError"><c:out value="${status.errorMessage}" />
				</span>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.telephone" />
			</td>
			<td>
				<spring:bind path="appAdmin.telephone">
					<input class="Field400" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" />
					</span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.fax" />
			</td>
			<td>
				<spring:bind path="appAdmin.fax">
					<input class="Field400" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /></span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="adminInfo.department" />
			</td>
			<td>
				<spring:bind path="appAdmin.adminInfo.department">
					<input class="Field400" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /></span>
				</spring:bind>
			</td>
		</tr>
		<tr>
			<td class="FieldLabel">
				<StoreAdmin:label key="appAdmin.userPosition" />
			</td>
			<td>
				<spring:bind path="appAdmin.userPosition">
					<input class="Field400" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" />
					<span class="fieldError"><c:out value="${status.errorMessage}" /></span>
				</spring:bind>
			</td>
		</tr>
		<c:if test="${appAdmin.username!='admin'}">
			<tr>
				<td class="FieldLabel"></td>
				<td>

					<select id="appRoleIds" name="appRoleIds" multiple="multiple" class="Field400">
						<c:forEach items="${allRoles}" var="userRole">
							<option value="${userRole.appRoleId}">
								${userRole.roleName}
							</option>
						</c:forEach>
					</select>
					<input type="button" onclick="document.getElementById('appRoleIds').selectedIndex=-1;" value="<fmt:message key="resource.roles.clear"/>" class="btn-back" />
				</td>
			</tr>
			<tr>
			<td class="FieldLabel"><StoreAdmin:label key="appAdmin.status" /></td>
			<td>
				<app:statusCheckbox name="status" value="${appAdmin.status}"></app:statusCheckbox>
			</td>
			</tr>
		</c:if>
	</table>
</form>
<v:javascript formName="appAdmin" staticJavascript="false"/>
<script type="text/javascript">
	<c:if test="${empty appAdmin.appuserId}">
    	$("username").focus();
		applyValidate($("newPassword"), "required,minlength=6,maxlength=20");
    </c:if>
    <c:if test="${not empty appAdmin.appuserId}">
    	$("title").focus();
    </c:if>

	function submitThisForm(){
		var hasConfirm=false;
		function isRoleSelected(form){
			if(!isNeedSetRole){
				return true;
			}
			var isRoleSel=false;
			var appRoleIds=form.appRoleIds;
			for(var i=0;i<appRoleIds.length;i++){
				if(appRoleIds.options[i].selected){
					isRoleSel=true;
					break;
				}
			}
			if(isRoleSel==false){
				if(confirm("<fmt:message key="appAdmin.admin.noRoleSelected"/>")){
					hasConfirm=true;
					return true;	
				}else{
					return false;
				}
			}else{
				return true;
			}
		}
		var oForm = $j('form.mainForm').get(0);
		if(validateAppAdmin(oForm)){
			var confirmMsg=__FMT.common_message_confirmSave;
			if(isRoleSelected(oForm)){
				if(hasConfirm||confirm(confirmMsg)){
					fnSubmitActionForm(oForm,"save"); 
				}
			}
		}
	}
</script>