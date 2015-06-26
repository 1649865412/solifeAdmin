<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="textKey" required="true"%>
<%@ attribute name="display" required="true"%>
<%@ attribute name="style" required="false"%>
<%@ attribute name="classes" required="false"%>
<%@ attribute name="displayType" required="false"%>
<%@ attribute name="isI18n" required="false"%>
<%@ attribute name="productId" required="false"%>
<%@ attribute name="categoryPath" required="false"%>
<%@ attribute name="input" required="true"%>
<%@ attribute name="maxLength" required="false"%>
<%@ attribute name="userLang" %>
<c:set var="img">
 <img border="0" src="<c:url value="/images/i18n.gif"/>" alt="<fmt:message key="i18n.editor.open"/>"/>
</c:set>
<c:if test="${not empty textKey}">
<c:choose>
 <c:when test="${not empty productId}">
  <a href='javascript:fnCreateI18nEditor(this,"<c:url value="/system/i18ntextItems.html"/>?doAction=getEdits&decorator=selecter&productId=${productId}&reqType=${displayType}&maxLength=${maxLength}&userLang=${userLang}","${textKey }","${input}","<fmt:message key="i18n.editor"/>");' alt='<fmt:message key="i18n.editor.open"/>'>${img}</a> 
 </c:when>
 <c:when test="${not empty categoryPath}">
  <a href='javascript:fnCreateI18nEditor(this,"<c:url value="/system/i18ntextItems.html"/>?doAction=getEdits&decorator=selecter&categoryPath=${categoryPath}&reqType=${displayType}&maxLength=${maxLength}&userLang=${userLang}","${textKey}","${input}","<fmt:message key="i18n.editor"/>");' alt='<fmt:message key="i18n.editor.open"/>'>${img}</a>
 </c:when>
 <c:otherwise>
  <a href='javascript:fnCreateI18nEditor(this,"<c:url value="/system/i18ntextItems.html"/>?doAction=getEdits&decorator=selecter&reqType=${displayType}&maxLength=${maxLength}&userLang=${userLang}","${textKey}","${input}","<fmt:message key="i18n.editor"/>");' alt='<fmt:message key="i18n.editor.open"/>'>${img}</a>
 </c:otherwise>
</c:choose>
</c:if>



