<%@ page session="false"%>
<script>
	if (document.location.href.indexOf("postLogin=true")>0) {
		document.location.href=document.location.href.substring(0,document.location.href.lastIndexOf("/"))+"/postLogin.html";
	} else {
	    document.location.href=document.location.href.substring(0,document.location.href.lastIndexOf("/"))+"/index.html";
	}
</script>
