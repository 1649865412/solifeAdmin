<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ attribute name="btnType" required="true"
	description="supported btnType:common,create,save,delete,copy,
	compare,generateLink,reset,preview,cancel,
	check,search,message,browse,print,
	help,active,inactive,genReport,publish,
	sendEmail,saveAndAdd,addCategory,addProduct,import,
	saveAndNext,showAll,cancelOrder,synch,viewReport,
	manageReport,download"%><%@ 
	attribute name="onclick" rtexprvalue="true"%><%@ 
	attribute name="inputName" description="default is btnType" rtexprvalue="true"%><%@ 
	attribute name="inputType" description="supported inputType:button,submit;default is submit." rtexprvalue="true"%><%@ 
	attribute name="commonBtnValue" description="if commonBtnValue not empty use it else use commonBtnValueKey" rtexprvalue="true"%><%@ 
	attribute name="commonBtnValueKey" rtexprvalue="true"%><%@ 
	attribute name="isHidden" description="true or false,default false" rtexprvalue="true"%>
	<%@ attribute name="isDisabled" type="java.lang.Boolean" description="true or false,default false" rtexprvalue="true"%>
<c:set var="currInputType" value="button" />
<c:if test="${not empty inputType}"><c:set var="currInputType" value="${inputType}" /></c:if>
<c:set var="currInputName" value="${btnType}" />
<c:if test="${not empty inputName}"><c:set var="currInputName" value="${inputName}" /></c:if>
<c:set var="currOnclick" value="${onclick}" />
<c:choose>
	<c:when test="${btnType eq 'common'}">
		<c:choose>
			<c:when test="${not empty  commonBtnValue}">
				<c:set var="value">${commonBtnValue}</c:set>
			</c:when>
			<c:when test="${not empty commonBtnValueKey}">
				<c:set var="value"><fmt:message key="${commonBtnValueKey}"/></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="value">${currInputName }</c:set>
			</c:otherwise>
		</c:choose>
		<input class="btn-action<c:if test="${not empty isDisabled&&isDisabled}"> btn-disabled</c:if>" name="${currInputName }" type="${currInputType }" value="${value}" title="${value}" 
			id="${currInputName }" onclick="${currOnclick }" <c:if test="${not empty isHidden&&isHidden}">style="display: none"  </c:if>
			<c:if test="${not empty isDisabled&&isDisabled}">disabled="true" </c:if>/>			
	</c:when>
	<c:otherwise>
		<input class="btn-action<c:if test="${not empty isDisabled&&isDisabled}"> btn-disabled</c:if>" name="${currInputName }" type="${currInputType }"
			value="<fmt:message key="button.${btnType}"/>" 
			<c:if test="${btnType == 'save'}">accesskey="S" </c:if>title="<fmt:message key="button.${btnType}"/>"
			id="${currInputName }" onclick="${currOnclick }" <c:if test="${not empty isHidden&&isHidden}">style="display: none"  </c:if>
			<c:if test="${not empty isDisabled&&isDisabled}">disabled="true" </c:if>/>		
	</c:otherwise>
</c:choose>