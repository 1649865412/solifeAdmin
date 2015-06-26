<%-- http://wiki.moxiecode.com/index.php/TinyMCE:Index --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ 
attribute name="textareaIds" required="true" description="格式是id1,id2的方式,需要变成html编辑器的textarea的id."%>
<script type="text/javascript" src="${ctxPath}/scripts/tiny_mce/tiny_mce.js" ></script>
<script type="text/javascript">
	tinyMCE.init({
		// General options
		mode : "exact",
		elements : "${textareaIds}",		
		theme : "advanced",
		language :"zh",
		convert_urls : 0,
		plugins : "safari,style,layer,table,advhr,advimage,advlink,inlinepopups,media,print,contextmenu,directionality,fullscreen,noneditable,xhtmlxtras",

		// Theme options
		theme_advanced_buttons1 : "newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,formatselect,fontselect,fontsizeselect,bullist,numlist,|,outdent,indent",
		theme_advanced_buttons2 : "link,unlink,image,cleanup,|,forecolor,backcolor,|,tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,media,advhr,|,print,|,code,fullscreen",
		theme_advanced_toolbar_location : "top",
		theme_advanced_toolbar_align : "left",
		theme_advanced_statusbar_location : "bottom",
		theme_advanced_resizing : true,
		// Example content CSS (should be your site CSS)
		// content_css : "${ctxPath}/scripts/tiny_mce/css/content.css",
		// Drop lists for link/image/media/template dialogs
		template_external_list_url : "lists/template_list.js",
		external_link_list_url : "lists/link_list.js",
		//external_image_list_url : "lists/image_list.js",
		media_external_list_url : "lists/media_list.js",

		// Replace values for the template plugin
		template_replace_values : {
			username : "Some User",
			staffid : "991234"
		}
	});
</script>