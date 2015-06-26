<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ui_tree" value="true" scope="request" />
<ul id="contentListTree" class="ztree"></ul>
<script type="text/javascript" defer>
var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: fnSelectTreeNode
			}
		};
var zNodes =${treeItemJsonList};
var contentTreeObj=null;
$j(document).ready(function(){
	contentTreeObj=$j.fn.zTree.init($j("#contentListTree"), setting, zNodes);
});
function fnSelectTreeNode(event, treeId, treeNode, clickFlag){
	if(treeNode.pId&&!treeNode.isLinkCategory){ 
		fnRefurbishContentList({categoryId:treeNode.id,btnSearch:'Search'});
		tab_container.appTabs( "select",0);
	}
	fnChangeBtnStatus();
}
function fnSelectNode(treeObj){
	var nodes = treeObj.getSelectedNodes();
	if(nodes.length>0){
		return nodes[0];
	}
	return null;
}
function fnUpdateNode(nodeObj){
	var node = contentTreeObj.getNodeByTId(nodeObj.tId);
	if(node==null){
		var newNode =nodeObj;
		var parentNode=null;
		if(nodeObj.pId){
			parentNode=contentTreeObj.getNodeByTId(nodeObj.pId);
		}
		newNode = contentTreeObj.addNodes(parentNode, newNode);
		node=newNode[0];
	}else{
		node.name=nodeObj.name;
		node.code=nodeObj.code;
		contentTreeObj.updateNode(node);
	}
	return node;
}

function fnOpen(){
	var nodes = contentTreeObj.getSelectedNodes();
	if(nodes.length>0){
		var node=nodes[0];
		fnCreateTabWindow("cate_tab_"+node.id,'#'+node.code,'catalog/contentCategory/window.html','doAction=edit&categoryId='+node.id+'&nId='+node.tId);
	}
}
		
function fnMoveCategoryDown(){
	var nodes = contentTreeObj.getSelectedNodes();
	if(nodes.length>0){
		var node=nodes[0];
		if(!node.isLastNode){
			contentTreeObj.moveNode(node.getNextNode(), node, "next");
			fnSaveCategorySortOrder(node);
		}
	}
}
function fnMoveCategoryUp(){
	var nodes = contentTreeObj.getSelectedNodes();
	if(nodes.length>0){
		var node=nodes[0];
		if(!node.isFirstNode){
			contentTreeObj.moveNode(node.getPreNode(), node, "prev");
			fnSaveCategorySortOrder(node);
		}
	}
}

function fnSaveCategorySortOrder(node){
	var parentNode = node.getParentNode();
	if(parentNode!=null){
		var paramData="";
		var children = parentNode.children;
		for(var i=0;i<children.length;i++){
			var child=children[i];
			paramData+="categoryIds="+child.id+"&";
		}
		$j.post("${ctxPath}/catalog/contentCategory.html?doAction=saveCategorySortOrder",paramData,function(result){
			if(result.status==-500){
				sysMsg(result.msg,true);
			}
		},"json");
	}
}
function fnAddCategoryForm(){
	var nodes = contentTreeObj.getSelectedNodes();
	if(nodes.length>0){
		var node=nodes[0];
		fnCreateTabWindow(null,'#创建目录','catalog/contentCategory/window.html','doAction=add&parentId='+node.id+'&pnId='+node.tId);
	}
}
function fnAddLinkeCategoryForm(){
	var nodes = contentTreeObj.getSelectedNodes();
	if(nodes.length>0){
		var node=nodes[0];
		fnCreateTabWindow(null,'#创建链接目录','catalog/contentCategory/window.html','doAction=add&isLinkCategory=1&parentId='+node.id+'&pnId='+node.tId);
	}
}
</script>