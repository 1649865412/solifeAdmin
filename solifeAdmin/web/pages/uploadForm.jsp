<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="upload.title"/></title>
<content tag="heading"><fmt:message key="upload.heading"/></content>

<!--
	The most important part is to declare your form's enctype to be "multipart/form-data"
-->

<spring:bind path="fileUpload.*">
    <c:if test="${not empty status.errorMessages}">
    <div class="error">	
        <c:forEach var="error" items="${status.errorMessages}">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                alt="<fmt:message key="icon.warning"/>" class="icon" />
            <c:out value="${error}" escapeXml="false"/><br />
        </c:forEach>
    </div>
    </c:if>
</spring:bind>

<fmt:message key="upload.message"/>
<div class="separator"></div>

<form class="mainForm" method="post" id="uploadForm" action="<c:url value="/uploadFile.html"/>"
    enctype="multipart/form-data" onsubmit="return validateFileUpload(this)">
<TABLE class="tables-wrap" cellSpacing="0" cellPadding="0" width="100%" border="0">
    <tr>
        <th>
            <StoreAdmin:label key="uploadForm.name" />
        </th>
        <td>
        	<spring:bind path="fileUpload.name">
            <input type="text" name="name" id="name" size="40" value="<c:out value="${status.value}"/>"/>
            <span class="fieldError"><c:out value="${status.errorMessage}"/></span>
            </spring:bind>
        </td>
    </tr>
    <tr>
        <th>
            <StoreAdmin:label key="uploadForm.file"/>
        </th>
        <td>
        	<spring:bind path="fileUpload.file">
            <input type="file" name="file" id="file" size="50" value="<c:out value="${status.value}"/>"/>
            <span class="fieldError"><c:out value="${status.errorMessage}"/></span>
            </spring:bind>
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="buttonBar">
            <input type="submit" name="upload" class="button" onclick="bCancel=false"
                value="<fmt:message key="button.upload"/>" />
            <input type="submit" name="cancel" class="button" onclick="bCancel=true"
                value="<fmt:message key="button.cancel"/>" />
        </td>
    </tr>
</table>
</form>

<script type="text/javascript">
<!--
highlightFormElements();
// -->
</script>
<v:javascript formName="fileUpload" staticJavascript="false"/>
