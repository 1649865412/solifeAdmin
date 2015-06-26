<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ui_tree" value="true" scope="request" />
<ul id="catalogListTree" class="ztree"></ul>
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
var zNodes =${catalogTreeItemJsonList};
var catalogTreeObj=null;
$j(document).ready(function(){
	catalogTreeObj=$j.fn.zTree.init($j("#catalogListTree"), setting, zNodes);
});
function fnSelectTreeNode(event, treeId, treeNode, clickFlag){
	if(treeNode.pId){
		fnRefurbishProductList({categoryId:treeNode.id,btnSearch:'Search'});
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
	var node = catalogTreeObj.getNodeByTId(nodeObj.tId);
	if(node==null){
		var newNode =nodeObj;
		var parentNode=null;
		if(nodeObj.pId){
			parentNode=catalogTreeObj.getNodeByTId(nodeObj.pId);
		}
		newNode = catalogTreeObj.addNodes(parentNode, newNode);
		node=newNode[0];
	}else{
		node.name=nodeObj.name;
		node.code=nodeObj.code;
		catalogTreeObj.updateNode(node);
	}
	return node;
}

function fnAddNodes(parentNode,subTreeItemJsonList){
	if(subTreeItemJsonList){
		catalogTreeObj.addNodes(parentNode, subTreeItemJsonList);
	}
}

function fnOpen(){
	var nodes = catalogTreeObj.getSelectedNodes();
	if(nodes.length>0){
		var node=nodes[0];
		if(!node.pId){
			fnCreateTabWindow("catalog_tab_"+node.id,'#'+node.code,'catalog/catalog/window.html','doAction=edit&catalogId='+node.cId+'&nId='+node.tId);
		}else{
			fnCreateTabWindow("cate_tab_"+node.id,'#'+node.code,'catalog/productCategory/window.html','doAction=edit&categoryId='+node.id+'&nId='+node.tId);
		}
	}
}
		
function fnMoveCategoryDown(){
	var nodes = catalogTreeObj.getSelectedNodes();
	if(nodes.length>0){
		var node=nodes[0];
		if(!node.isLastNode){
			catalogTreeObj.moveNode(node.getNextNode(), node, "next");
			fnSaveCategorySortOrder(node);
		}
	}
}
function fnMoveCategoryUp(){
	var nodes = catalogTreeObj.getSelectedNodes();
	if(nodes.length>0){
		var node=nodes[0];
		if(!node.isFirstNode){
			catalogTreeObj.moveNode(node.getPreNode(), node, "prev");
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
		$j.post("${ctxPath}/catalog/productCategory.html?doAction=saveCategorySortOrder",paramData,function(result){
			if(result.status==-500){
				sysMsg(result.msg,true);
			}
		},"json");
	}
}
function fnAddCatalogForm(){
	fnCreateTabWindow(null,'#创建目录','catalog/catalog/window.html','doAction=add&isVirtual=0');
}
function fnAddVirtualCatalogForm(){
	fnCreateTabWindow(null,'#创建虚拟目录','catalog/catalog/window.html','doAction=add&isVirtual=1');
}
function fnAddCategoryForm(){
	var nodes = catalogTreeObj.getSelectedNodes();
	if(nodes.length>0){
		var node=nodes[0];
		fnCreateTabWindow(null,'#创建商品分类','catalog/productCategory/window.html','doAction=add&parentId='+node.id+'&pnId='+node.tId);
	}
}
</script>