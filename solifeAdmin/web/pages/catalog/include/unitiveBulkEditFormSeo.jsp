<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="seoInfo"  class="border01" style="display:none;">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
<%--==================标题=================--%>
<tr>
	<td width="20">
		<input type="checkbox" id="titleCheck" name="titleCheck" value="1"/>
	</td>
	<td width="120">
		<fmt:message key="product.title" />
	</td>
	<td>
		<input type="radio" id="title_method_1" name="title_method" value="1" checked><fmt:message key="unitiveBulkEdit.method.direct"/>&nbsp;&nbsp;
		<span><input id="title" name="title" size="25"/></span>
		<br/>
		<input type="radio" id="title_method_2" name="title_method" value="2" ><fmt:message key="unitiveBulkEdit.method.add"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<fmt:message key="unitiveBulkEdit.method.add.prefix"/><span><input id="title_prefix" name="title_prefix" class="Field200"></span>&nbsp;&nbsp;&nbsp;
		<fmt:message key="unitiveBulkEdit.method.add.suffix"/><span><input id="title_suffix" name="title_suffix" class="Field200"></span>
		<br/>		
		<input type="radio" id="title_method_3" name="title_method" value="3"><fmt:message key="unitiveBulkEdit.method.findAndReplace"/>
		<fmt:message key="unitiveBulkEdit.method.find"/><span><input id="title_findAndReplaceOldString" name="title_findAndReplaceOldString" class="Field200"/></span>
		<fmt:message key="unitiveBulkEdit.method.replace"/><span><input id="title_findAndReplaceNewString" name="title_findAndReplaceNewString" class="Field200"/></span>
	</td>
</tr>


<%--==================url关键字=================--%>
<tr>
	<td>
		<input type="checkbox" id="urlCheck" name="urlCheck" value="1"/>
	</td>
	<td>
		<fmt:message key="product.url" />
	</td>
	<td>
		<input type="radio" id="url_method_1" name="url_method" value="1" checked><fmt:message key="unitiveBulkEdit.method.direct"/>&nbsp;&nbsp;
		<span><input id="url" name="url" size="25"/></span>
		<br/>
		<input type="radio" id="url_method_2" name="url_method" value="2" ><fmt:message key="unitiveBulkEdit.method.add"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<fmt:message key="unitiveBulkEdit.method.add.prefix"/><span><input id="url_prefix" name="url_prefix" class="Field200"></span>&nbsp;&nbsp;&nbsp;
		<fmt:message key="unitiveBulkEdit.method.add.suffix"/><span><input id="url_suffix" name="url_suffix" class="Field200"></span>
		<br/>		
		<input type="radio" id="url_method_3" name="url_method" value="3"><fmt:message key="unitiveBulkEdit.method.findAndReplace"/>
		<fmt:message key="unitiveBulkEdit.method.find"/><span><input id="url_findAndReplaceOldString" name="url_findAndReplaceOldString" class="Field200"/></span>
		<fmt:message key="unitiveBulkEdit.method.replace"/><span><input id="url_findAndReplaceNewString" name="url_findAndReplaceNewString" class="Field200"/></span>
	</td>
</tr>

<%--==================meta关键字=================--%>
<tr>
	<td>
		<input type="checkbox" id="metaKeywordCheck" name="metaKeywordCheck" value="1"/>
	</td>
	<td>
		<fmt:message key="product.metaKeyword" />
	</td>
	<td>
		<input type="radio" id="metaKeyword_method_1" name="metaKeyword_method" value="1" checked><fmt:message key="unitiveBulkEdit.method.direct"/>&nbsp;&nbsp;
		<span><input id="metaKeyword" name="metaKeyword" size="25"/></span>
		<br/>
		<input type="radio" id="metaKeyword_method_2" name="metaKeyword_method" value="2" ><fmt:message key="unitiveBulkEdit.method.add"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
		<fmt:message key="unitiveBulkEdit.method.add.prefix"/><span><input id="metaKeyword_prefix" name="metaKeyword_prefix" class="Field200"></span>&nbsp;&nbsp;&nbsp;
		<fmt:message key="unitiveBulkEdit.method.add.suffix"/><span><input id="metaKeyword_suffix" name="metaKeyword_suffix" class="Field200"></span>
		<br/>		
		<input type="radio" id="metaKeyword_method_3" name="metaKeyword_method" value="3"><fmt:message key="unitiveBulkEdit.method.findAndReplace"/>
		<fmt:message key="unitiveBulkEdit.method.find"/><span><input id="metaKeyword_findAndReplaceOldString" name="metaKeyword_findAndReplaceOldString" class="Field200"/></span>
		<fmt:message key="unitiveBulkEdit.method.replace"/><span><input id="metaKeyword_findAndReplaceNewString" name="metaKeyword_findAndReplaceNewString" class="Field200"/></span>
	</td>
</tr>


<%--==================meta描述=================--%>
<tr>
	<td>
		<input type="checkbox" id="metaDescriptionCheck" name="metaDescriptionCheck" value="1"/>
	</td>
	<td>
		<fmt:message key="product.metaDescription" />
	</td>
	<td>
		<input type="radio" id="metaDescription_method_1" name="metaDescription_method" value="1" checked><fmt:message key="unitiveBulkEdit.method.direct"/>&nbsp;&nbsp;
		<span><input id="metaDescription" name="metaDescription" size="25"/></span>
		<%-- 
		<app:ui_htmlEditor textareaIds="metaDescription" />
		--%>
		<br/>
		<input type="radio" id="metaDescription_method_2" name="metaDescription_method" value="2" ><fmt:message key="unitiveBulkEdit.method.add"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<fmt:message key="unitiveBulkEdit.method.add.prefix"/><span><input id="metaDescription_prefix" name="metaDescription_prefix" class="Field200"></span>&nbsp;&nbsp;&nbsp;
		<fmt:message key="unitiveBulkEdit.method.add.suffix"/><span><input id="metaDescription_suffix" name="metaDescription_suffix" class="Field200"></span>
		<br/>		
		<input type="radio" id="metaDescription_method_3" name="metaDescription_method" value="3"><fmt:message key="unitiveBulkEdit.method.findAndReplace"/>
		<fmt:message key="unitiveBulkEdit.method.find"/><span><input id="metaDescription_findAndReplaceOldString" name="metaDescription_findAndReplaceOldString" class="Field200"/></span>
		<fmt:message key="unitiveBulkEdit.method.replace"/><span><input id="metaDescription_findAndReplaceNewString" name="metaDescription_findAndReplaceNewString" class="Field200"/></span>
	</td>
</tr>

</table>
</div>


