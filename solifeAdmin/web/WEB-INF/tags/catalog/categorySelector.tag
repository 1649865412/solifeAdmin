<%@tag import="net.sf.json.JSONObject"%>
<%@tag import="net.sf.json.JSONArray"%>
<%@tag import="com.cartmatic.estore.common.model.catalog.CategoryTreeItem"%>
<%@tag import="java.util.List"%>
<%@tag import="com.cartmatic.estore.core.util.ContextUtil"%>
<%@tag import="com.cartmatic.estore.catalog.service.CategoryManager"%>
<%@tag import="com.cartmatic.estore.Constants"%>
<%@ tag body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ attribute name="id" required="true" rtexprvalue="true" type="java.lang.String" description="提示：可以用dlg加上这个Id来引用这个窗口，所以showDialogBtnId可以不需要"%>
<%@ attribute name="showSelectorBtnId" required="false" rtexprvalue="true" type="java.lang.String" description="打开本窗口的按钮的ID；留空并且自己设置该按钮的onclick事件也可"%>
<%@ attribute name="onclick" required="false" rtexprvalue="true" type="java.lang.String" description="单击目录树回调函数,返回参数为category的json对象,{categoryId:'',categoryName:'',items:{....}"%>
<%@ attribute name="ondblclick" required="false" rtexprvalue="true" type="java.lang.String" description="双击目录树回调函数,返回参数为category的json对象,{id,name,items}"%>
<%@ attribute name="autoClose" required="false"  rtexprvalue="true" type="java.lang.Boolean" description="当设置了onclick或ondblclick事件时，是否自动关闭选择器，默认true"%>
<%@ attribute name="canSelectRoot" required="false"  rtexprvalue="true" type="java.lang.Boolean" description="根目录是否可选,默认可选"%>
<%@ attribute name="categoryType" type="java.lang.Short" description="目录类型，1为产品目录，2为内容目录。默认为1"%>
<%@ attribute name="catalogId" type="java.lang.Integer" description="只显示指定目录下的商品分类"%>
<%@ attribute name="storeId" type="java.lang.Integer" description="只显示指定商店下的目录"%>
<%@ attribute name="virtual" type="java.lang.Integer" description="2表示区分所有显示,1只显示虚拟的,0只显示实体的.默认为2"%>
<c:set var="ui_tree" value="true" scope="request"/>
<fmt:message key="category.selecter.title" var="categorySelectTitle"/>
<%
	if(categoryType==null){
		categoryType=new Short("1");
	}
	if(categoryType==Constants.CATEGORY_TYPE_CATALOG.intValue()){
		if(virtual==null){
			virtual=new Integer("2");
		}
		CategoryManager categoryManager=(CategoryManager)ContextUtil.getSpringBeanById("categoryManager");
		List<CategoryTreeItem> catalogTreeItems=categoryManager.getCatalogTreeItemList(catalogId,virtual);
		JSONArray catalogTreeItemJsonList=new JSONArray();
		for (CategoryTreeItem categoryTreeItem : catalogTreeItems){
			JSONObject item=new JSONObject();
			item.put("name", categoryTreeItem.getCategoryName());
			item.put("code", categoryTreeItem.getCategoryCode());
			item.put("categoryPath", categoryTreeItem.getCategoryPath());
			item.put("isLinkcategory", categoryTreeItem.getLinkedCategory()!=null);
			item.put("id", categoryTreeItem.getId());
			item.put("pId", categoryTreeItem.getParentId()==null?0:categoryTreeItem.getParentId());
			item.put("cId", categoryTreeItem.getCatalog().getCatalogId());
			item.put("isVirtual", categoryTreeItem.getCatalog().getIsVirtual()==Constants.FLAG_TRUE.shortValue());
			item.put("isCatalog", categoryTreeItem.getIsCatalog()==Constants.FLAG_TRUE.intValue());
			
			//node 图标指定
			if(categoryTreeItem.getIsCatalog()==Constants.FLAG_TRUE.intValue()){
				item.put("iconSkin", categoryTreeItem.getCatalog().getIsVirtual()==Constants.FLAG_TRUE.shortValue()?"catalog_virtual":"catalog");
			}else{
				item.put("iconSkin", categoryTreeItem.getCatalog().getIsVirtual()==Constants.FLAG_TRUE.shortValue()?"category_linked":"category");
			}
			
			catalogTreeItemJsonList.add(item);
		}
		jspContext.setAttribute("selector_catalogTreeItems",catalogTreeItemJsonList);
	}else{
		CategoryManager categoryManager=(CategoryManager)ContextUtil.getSpringBeanById("categoryManager");
		List<CategoryTreeItem> catalogTreeItems=categoryManager.getContentTreeItemList(storeId);
		JSONArray catalogTreeItemJsonList=new JSONArray();
		for (CategoryTreeItem categoryTreeItem : catalogTreeItems){
			if(categoryTreeItem.getIsLinkCategory()!=Constants.FLAG_TRUE.shortValue()){
				JSONObject item=new JSONObject();
				item.put("name", categoryTreeItem.getCategoryName());
				item.put("code", categoryTreeItem.getCategoryCode());
				item.put("categoryPath", categoryTreeItem.getCategoryPath());
				item.put("isLinkCategory", categoryTreeItem.getIsLinkCategory()==Constants.FLAG_TRUE.shortValue());
				item.put("id", categoryTreeItem.getId());
				item.put("pId", categoryTreeItem.getParentId()==null?0:categoryTreeItem.getParentId());
				item.put("sId", categoryTreeItem.getStore().getStoreId());
				
				//node 图标指定
				if(categoryTreeItem.getIsFalseEntity()==Constants.FLAG_TRUE.intValue()){
					item.put("iconSkin", "catalog");
				}else{
					item.put("iconSkin", categoryTreeItem.getIsLinkCategory()==Constants.FLAG_TRUE.shortValue()?"category_linked":"category");
				}
				
				catalogTreeItemJsonList.add(item);
			}
		}
		jspContext.setAttribute("selector_catalogTreeItems",catalogTreeItemJsonList);
	}
%>
<div id="categorySelector_dialog_${id}" style="display: none;">
<ul id="categorySelector_${id}" class="ztree"></ul>
</div>
<script type="text/javascript" defer>
var categorySelector_zNodes_${id} =${selector_catalogTreeItems};
var categorySelectorTreeObj_${id}=null;
var categorySelector_onclick_callback_${id}=null;
var categorySelector_dblClick_callback_${id}=null;
$j(document).ready(function(){
	categorySelector_onclick_callback_${id}=eval(${onclick});
	categorySelector_dblClick_callback_${id}=eval(${ondblclick});
	var categorySelector_setting_${id} = {
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: categorySelector_onclick_${id},
			onDblClick:categorySelector_dblClick_${id}
		}
	};
	categorySelectorTreeObj_${id}=$j.fn.zTree.init($j("#categorySelector_${id}"), categorySelector_setting_${id}, categorySelector_zNodes_${id});
});
function categorySelector_onclick_${id}(event, treeId, treeNode){
	if(categorySelector_onclick_callback_${id}){
		var node={id:treeNode.id,categoryId:treeNode.id,name:treeNode.name,categoryName:treeNode.name,code:treeNode.code,cId:treeNode.cId,pId:treeNode.pId,categoryPath:treeNode.categoryPath,isCatalog:treeNode.isCatalog,isVirtual:treeNode.isVirtual,isLinkcategory:treeNode.isLinkcategory};
		if(categorySelector_check_select_${id}(node)){
			categorySelector_onclick_callback_${id}.call(this,node,categorySelector_paramData_${id});
			if(${empty autoClose ? true : autoClose}){
				${id}_close();
			}
		}
	}
}
function categorySelector_dblClick_${id}(event, treeId, treeNode){
	if(categorySelector_dblClick_callback_${id}){
		var node={id:treeNode.id,categoryId:treeNode.id,name:treeNode.name,categoryName:treeNode.name,code:treeNode.code,cId:treeNode.cId,pId:treeNode.pId,categoryPath:treeNode.categoryPath,isCatalog:treeNode.isCatalog,isVirtual:treeNode.isVirtual,isLinkcategory:treeNode.isLinkcategory};
		if(categorySelector_check_select_${id}(node)){
			categorySelector_dblClick_callback_${id}.call(this,node,categorySelector_paramData_${id});
			if(${empty autoClose ? true : autoClose}){
				${id}_close();
			}
		}
	}
}

function categorySelector_check_select_${id}(treeNode){
	<c:if test="${(not empty canSelectRoot)&&(!canSelectRoot)}">
		if(treeNode.isCatalog){
			return false;
		}
	</c:if>
	return true;
}

var categorySelector_dialog_${id};
var categorySelector_tree_${id};
$j(document).ready(function(){
	<c:if test="${not empty showSelectorBtnId}">
	$j("#${showSelectorBtnId}").bind("click", function(){${id}_show();});
	</c:if>
	if (categorySelector_dialog_${id} == undefined){
		categorySelector_dialog_${id} = $j("#categorySelector_dialog_${id}");
	}
}); 

var categorySelector_paramData_${id};
function ${id}_show(paramData)
{
	categorySelector_paramData_${id}=paramData;
	if(!(categorySelector_dialog_${id}.dialog("isOpen")==true||categorySelector_dialog_${id}.dialog("isOpen")==false)){
		categorySelector_dialog_${id}.css("visibility","visible");
		categorySelector_dialog_${id}.dialog({ title:"${categorySelectTitle}", modal: true, height:450, width:400, buttons: {${buttons}}});
    }
	categorySelector_dialog_${id}.dialog("open");
}
function ${id}_close()
{
	categorySelector_dialog_${id}.dialog("close");
}
</script>