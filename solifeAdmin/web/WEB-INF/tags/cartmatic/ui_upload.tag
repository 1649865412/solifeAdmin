<%-- http://plugins.jquery.com/project/ocupload --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="fileInputId" required="true"%>
<%@ attribute name="uploadCategory" required="false" description="输入上传的目录名,如category,product等,对应前台media下的目录,默认是others目录"%>
<%@ attribute name="uploadFileTypes" required="false" description="格式是jpg,gif"%>
<%@ attribute name="onComplete" required="false" description="当上传完毕后，调用的回调函数"%>
<%@ attribute name="onSelect" required="false" description="浏览文件后，调用的回调函数"%>
<%@ attribute name="objId" required="false" description="图片所属对象Id，上传图片按此Id作为路径保存，（产品图片，包括sku，更多媒体图片，都以产品Id作路径保持；目录图片，以其父目录Id作路径保持）"%>
<c:set var="ui_upload" value="true" scope="request"/>
<c:if test="${empty uploadCategory}">
	<c:set var="uploadCategory" value="others" scope="page"/>
</c:if>
<c:if test="${empty uploadFileTypes}">
	<c:set var="uploadFileTypes" value="gif,jpg" scope="page"/>
</c:if>
<script type="text/javascript" defer>
$j(document).ready(function(){
	var urlAction='/dialog/upload.html?mediaType=${uploadCategory}&fileInputId=${fileInputId}&objId=${objId}';
	<c:if test="${not empty onComplete}">
		var onCompleteHandler=${onComplete};
		urlAction+="&onCompleteHandler="+onCompleteHandler.toLocaleString().substring(9,onCompleteHandler.toLocaleString().indexOf("("))
	</c:if>
	var ui_upload_valid=true;
	$j("<input type='button' class='btn-back' value='"+__FMT.button_uploadFile+"' onmouseover='$j(this).removeClass(\"btn-back\").addClass(\"btn-hover\");' onmouseout='$j(this).removeClass(\"btn-hover\").addClass(\"btn-back\");'/>").insertAfter("#${fileInputId}").upload({
	   	name: 'file',
       	action:  __ctxPath+urlAction,
       	enctype: 'multipart/form-data',
       	params: {},
       	onSubmit: function() {return ui_upload_valid;},
       	onComplete: function() {},
       	onSelect: function() {
       		var supportedSuffixs=[<c:forTokens items="${uploadFileTypes}" delims="," var="item">'${item}',</c:forTokens>];
       		var idx = this.filename().lastIndexOf(".");
			if (idx > 0 && idx < this.filename().length) {
				var suffix = this.filename().substring(idx+1, this.filename().length).toLowerCase();
				ui_upload_valid =(supportedSuffixs.toString().indexOf(suffix)>=0);
			}
			else
			{
				ui_upload_valid =false;
			}
			if (!ui_upload_valid) {
				alert(__FMT.error_upload_unsupport+":"+supportedSuffixs.toString());
				return false;
			}
			<c:if test="${not empty onSelect}">
				ui_upload_valid=${onSelect}.call(this,this.filename());
			</c:if>
       	},
       	autoSubmit: true
	});
	$j("#${fileInputId}").wrap("<div style='float:left;'></div>"); 
});
</script>