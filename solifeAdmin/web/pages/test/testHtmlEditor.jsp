<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing HtmlEditor page</title>
		<%@ include file="/decorators/include/javascripts.jspf"%>
		
	</head>
	<body>
<form method="post" action="http://tinymce.moxiecode.com/dump.php?example=true">
	<h3>Full featured example</h3>

	<p>
		This page shows all available buttons and plugins that are included in the TinyMCE core package.
		There are more examples on how to use TinyMCE in the <a href="http://wiki.moxiecode.com/examples/tinymce/">Wiki</a>.
	</p>

	<!-- Gets replaced with TinyMCE, remember HTML in a textarea should be encoded -->
	<textarea id="elm1" name="elm1" rows="15" cols="80" style="width: 80%">
		&lt;p&gt;
		&lt;img src="media/logo.jpg" alt=" " hspace="5" vspace="5" width="250" height="48" align="right" /&gt;	TinyMCE is a platform independent web based Javascript HTML &lt;strong&gt;WYSIWYG&lt;/strong&gt; editor control released as Open Source under LGPL by Moxiecode Systems AB. It has the ability to convert HTML TEXTAREA fields or other HTML elements to editor instances. TinyMCE is very easy to integrate into other Content Management Systems.
		&lt;/p&gt;
		&lt;p&gt;
		We recommend &lt;a href="http://www.getfirefox.com" target="_blank"&gt;Firefox&lt;/a&gt; and &lt;a href="http://www.google.com" target="_blank"&gt;Google&lt;/a&gt; &lt;br /&gt;
		&lt;/p&gt;
	</textarea>

	<div>
		<!-- Some integration calls -->
		<a href="javascript:;" onmousedown="tinyMCE.get('elm1').show();">[Show]</a>
		<a href="javascript:;" onmousedown="tinyMCE.get('elm1').hide();">[Hide]</a>
		<a href="javascript:;" onmousedown="tinyMCE.get('elm1').execCommand('Bold');">[Bold]</a>
		<a href="javascript:;" onmousedown="alert(tinyMCE.get('elm1').getContent());">[Get contents]</a>
		<a href="javascript:;" onmousedown="alert(tinyMCE.get('elm1').selection.getContent());">[Get selected HTML]</a>
		<a href="javascript:;" onmousedown="alert(tinyMCE.get('elm1').selection.getContent({format : 'text'}));">[Get selected text]</a>
		<a href="javascript:;" onmousedown="alert(tinyMCE.get('elm1').selection.getNode().nodeName);">[Get selected element]</a>
		<a href="javascript:;" onmousedown="tinyMCE.execCommand('mceInsertContent',false,'<b>Hello world!!</b>');">[Insert HTML]</a>
		<a href="javascript:;" onmousedown="tinyMCE.execCommand('mceReplaceContent',false,'<b>{$selection}</b>');">[Replace selection]</a>
	</div>

	<br />
	<input type="submit" name="save" value="Submit" />
	<input type="reset" name="reset" value="Reset" />
</form>
<br/>
<textarea id="elm2" name="elm2" rows="15" cols="80" style="width: 80%">
		
今日广州 	 
	关注热点新闻，畅谈心中感悟，在这里，不同的观点将会激烈碰撞，您的思想也将得到一次深刻的洗礼与升华......
·455条公交可享优惠
·全民关注的医改方案征求意见
·猪肉卖得比人便都算恶性竞争？
·海珠区宝岗大厦一单位发生火灾(08.10.15早上)
·买沙发，要注意D咩？
	</textarea>
	<app:ui_htmlEditor textareaIds="elm1,elm2"/>
	</body>
</html>
