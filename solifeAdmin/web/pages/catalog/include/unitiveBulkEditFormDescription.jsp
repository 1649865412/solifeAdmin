<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="descriptionInfo"  style="display:none;">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
<%--==================简单描述=================--%>
<tr>
	<td>
		<input type="checkbox" id="shortDescriptionCheck" name="shortDescriptionCheck" value="1"/>
	</td>
	<td>
		<fmt:message key="productDescription.shortDescription" />
	</td>
	<td>
		<input type="radio" id="shortDescription_method_1" name="shortDescription_method" value="1" checked><fmt:message key="unitiveBulkEdit.method.direct"/>&nbsp;&nbsp;
		<span><textarea id="shortDescription" name="shortDescription" rows="8" class="Field600"></textarea></span>
		<br/>
		<input type="radio" id="shortDescription_method_2" name="shortDescription_method" value="2" ><fmt:message key="unitiveBulkEdit.method.add"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<fmt:message key="unitiveBulkEdit.method.add.prefix"/><span><input id="shortDescription_prefix" name="shortDescription_prefix" class="Field200"></span>&nbsp;&nbsp;&nbsp;
		<fmt:message key="unitiveBulkEdit.method.add.suffix"/><span><input id="shortDescription_suffix" name="shortDescription_suffix" class="Field200"></span>
		<br/>		
		<input type="radio" id="shortDescription_method_3" name="shortDescription_method" value="3"><fmt:message key="unitiveBulkEdit.method.findAndReplace"/>
		<fmt:message key="unitiveBulkEdit.method.find"/><span><input id="shortDescription_findAndReplaceOldString" name="shortDescription_findAndReplaceOldString" class="Field200"/></span>
		<fmt:message key="unitiveBulkEdit.method.replace"/><span><input id="shortDescription_findAndReplaceNewString" name="shortDescription_findAndReplaceNewString" class="Field200"/></span>
	</td>
	
</tr>

<%--==================详细描述=================--%>
<tr>
	<td>
		<input type="checkbox" id="fullDescriptionCheck" name="fullDescriptionCheck" value="1"/>
	</td>
	<td>
		<fmt:message key="productDescription.fullDescription" />
	</td>
	<td>
		<input type="radio" id="fullDescription_method_1" name="fullDescription_method" value="1" checked><fmt:message key="unitiveBulkEdit.method.direct"/>
		<span><textarea id="fullDescription" name="fullDescription" rows="8" class="Field600"></textarea>
		<app:ui_htmlEditor textareaIds="fullDescription" />
		<br/>
		<input type="radio" id="fullDescription_method_2" name="fullDescription_method" value="2" ><fmt:message key="unitiveBulkEdit.method.add"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<fmt:message key="unitiveBulkEdit.method.add.prefix"/><span><input id="fullDescription_prefix" name="fullDescription_prefix" class="Field200"></span>&nbsp;&nbsp;&nbsp;
		<fmt:message key="unitiveBulkEdit.method.add.suffix"/><span><input id="fullDescription_suffix" name="fullDescription_suffix" class="Field200"></span>
		<br/>		
		<input type="radio" id="fullDescription_method_3" name="fullDescription_method" value="3"><fmt:message key="unitiveBulkEdit.method.findAndReplace"/>
		<fmt:message key="unitiveBulkEdit.method.find"/><span><input id="fullDescription_findAndReplaceOldString" name="fullDescription_findAndReplaceOldString" class="Field200"/></span>
		<fmt:message key="unitiveBulkEdit.method.replace"/><span><input id="fullDescription_findAndReplaceNewString" name="fullDescription_findAndReplaceNewString" class="Field200"/></span>
	</td>
	
</tr>
</table>
</div>


