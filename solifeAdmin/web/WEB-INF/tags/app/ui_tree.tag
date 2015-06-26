<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@
attribute name="treeId" required="true"%><%@
attribute name="autoclose" required="false" description="true 为自动close."%>
<c:set var="ui_tree" value="true" scope="request"/>
<script type="text/javascript" defer>
var ui_tree_${treeId};
$j(document).ready(function(){
	ui_tree_${treeId} = $j('#${treeId} > ul').simpleTree({
		drag:false,
		<c:if test="${not empty autoclose}">autoclose: ${autoclose},</c:if>		
		afterClick:function(node){
			//alert("text-"+$('span:first',node).text());
		},
		afterDblClick:function(node){
			//alert("text-"+$('span:first',node).text());
		},
		afterMove:function(destination, source, pos){
			//alert("destination-"+destination.attr('id')+" source-"+source.attr('id')+" pos-"+pos);
		},
		afterAjax:function()
		{
			//alert('Loaded');
		},
		animate:true
		//,docToFolderConvert:true
	});
});
</script>