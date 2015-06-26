<%-- http://plugins.jquery.com/project/ocupload --%>
<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ attribute name="fileInputId" required="false" type="java.lang.String" description="上传文件后的路径输入框"%>
<%@ attribute name="uploadCategory" required="false" description="输入上传的目录名,如category,product等,对应前台media下的目录,默认是others目录"%>
<%@ attribute name="uploadFileTypes" required="false" description="*.*;*.jpg; *.jpeg; *.psd; *.png; *.gif; *.zip; *.mp3; *.doc; *.txt。默认为*.*,表示支持所有类型（需支持多个指定类型的用分号分隔）"%>
<%@ attribute name="onComplete" required="false" description="当上传完毕后，调用的回调函数（注意是每当上传完毕一个文件的都会调用）"%>
<%@ attribute name="onSelect" required="false" description="浏览文件后，调用的回调函数"%>
<%@ attribute name="objId" required="false" description="图片所属对象Id，上传图片按此Id作为路径保存，（产品图片，包括sku，更多媒体图片，都以产品Id作路径保持；目录图片，以其父目录Id作路径保持）"%>
<%@ attribute name="isMultiFiles" required="false" type="java.lang.Boolean" description="是否同时上传多个文件，默认false"%>
<%@ attribute name="btnPlaceHolderId" required="true" type="java.lang.String" description="上传按钮显示位置位置"%>
<%@ attribute name="button_text" required="false" type="java.lang.String" description="按钮文本内容。默认为上传文件"%>
<%@ attribute name="previewSize" required="false" type="java.lang.String" description="预览图片大小，a,b,c,d.默认为v"%>
<%@ attribute name="previewImg" required="false" type="java.lang.String" description="预览图片Img Id"%>
<%@ attribute name="fileSizeLimit" required="false" type="java.lang.String" description="上传图片最大限制，例如 2MB/10MB。默认为2MB"%>
<%@ attribute name="fileImageSize" required="false" type="java.lang.String" description="上传图片后的保存路径的前缀"%>
<c:set var="swf_upload" value="true" scope="request"/>
<script type="text/javascript" defer>
var swfu_${fileInputId};
$j(document).ready(function(){
	swfu_${fileInputId} =initSwfUploadInstance({
	<c:if test="${not empty objId}">"objId" : "${objId}",</c:if>
	<c:if test="${not empty uploadCategory}">"uploadCategory" : "${uploadCategory}",</c:if>
	<c:if test="${not empty fileSizeLimit}">"fileSizeLimit" : "${fileSizeLimit}",</c:if>
	<c:if test="${not empty uploadFileTypes}">"uploadFileTypes" : "${uploadFileTypes}",</c:if>
	<c:if test="${not empty isMultiFiles}">"isMultiFiles" : ${isMultiFiles},</c:if>
	<c:if test="${not empty button_text}">"button_text" : "${button_text}",</c:if>
	<c:if test="${not empty onSelect}">"onSelect" : ${onSelect},</c:if>
	<c:if test="${not empty onComplete}">"onComplete" : ${onComplete},</c:if>
	<c:if test="${not empty previewSize}">"previewSize" : "${previewSize}",</c:if>
	<c:if test="${not empty previewImg}">"previewImg" : "${previewImg}",</c:if>
	<c:if test="${not empty fileInputId}">"fileInputId" : "${fileInputId}",</c:if>
	<c:if test="${not empty fileImageSize}">"fileImageSize" : "${fileImageSize}",</c:if>
	btnPlaceHolderId:"${btnPlaceHolderId}"
	});
});

</script>