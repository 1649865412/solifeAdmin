<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<app:pageHeading pageHeadingKey="contentCategoryList.heading" />
<script type="text/javascript" defer>
var contentListTabs=null;
$j(document).ready(function(){
	contentListTabs=$j("#contentListTabs").appTabs({selected:${empty param.contentListTab ? 0 : 1}});
	<c:if test="${param.name!=null}">
		contentListTabs.appTabs( "select", 1);
		fnSearchContent();
	</c:if>
});
</script>
<app:ui_leftMenu>
	<div class="sidebar-left">
		<div class="tab" id="contentListTabs">
			<ul class="top-tab">
				<li id="contentListcategorySelector-tab" class="selected">
					<a href="#contentListCategorySelector" onclick="fnChangeBtnStatus(1);"><fmt:message key="contentList.contentCategory" /> </a>
				</li>
				<li id="searchContent-tab">
					<a href="#searchContentCondition" onclick="fnChangeBtnStatus(2);"><fmt:message key="yourposition.search"/> </a>
				</li>
			</ul>
			<div class="content" id="contentListCategorySelector" style="padding:0;width:244px;">
				<div class=" tree-do-wrap">
					<input id="btn_openCategory" type="button" value="" onclick="fnOpen();" class="button_view_details button-disabled" title="打开" disabled="disabled">
					<input id="btn_moveCategoryDown" type="button" value="" onclick="fnMoveCategoryDown();" class="button_move_down button-disabled" disabled="disabled" title="<fmt:message key="button.small.down" />">
					<input id="btn_moveCategoryUp" type="button" value="" onclick="fnMoveCategoryUp();" class="button_move_up button-disabled" disabled="disabled" title="<fmt:message key="button.small.up" />">
					<input id="btn_addCategory" type="button" value="" onclick="fnAddCategoryForm();" class="button_category_create button-disabled" disabled="disabled" title="添加目录">
					<input id="btn_addLinkCategory" type="button" value="" onclick="fnAddLinkeCategoryForm();" class="button_category_linked_add button-disabled" disabled="disabled" title="添加链接目录">
					<input id="btn_addContent" type="button" value="" onclick="fnAddContent();" class="button_content_add button-disabled" disabled="disabled" title="添加内容">
					<input id="btn_deletNode" type="button" value="" onclick="fnDeleteNode();" class="button_remove button-disabled" disabled="disabled" title="删除">
				</div>
				<%@ include file="include/contentListTree.jsp"%>
			</div>
			<form method="post" action="${ctxPath}/content/contentCategory.html"  onsubmit="return false;">
			<div id="searchContentCondition">
				<div class="title">
				<fmt:message key="content.contentTitle" />
				</div>
				<input name="contentTitle" id="contentTitle" type="text" style="width: 200px"/>
				<div class="blank10"></div>
				<div class="title">
					<fmt:message key="content.category" />
				</div>
				<select id="categoryId" name="categoryId" style="width: 200px">
					<option value=""></option>
					<c:forEach items="${categoryTreeItems}" var="categoryTreeItem">
						<c:if test="${categoryTreeItem.isLinkCategory!=1}">
							<option value="${categoryTreeItem.categoryId}">
								<c:if test="${categoryTreeItem.level-1>=0}">
									<c:forEach begin="1" end="${categoryTreeItem.level-1}" var="level">—</c:forEach>
								</c:if>
								${categoryTreeItem.categoryName}
							</option> 
						</c:if>
					</c:forEach>
				</select>
				<div class="blank10"></div>
				<input type="submit" id="SearchButton" name="SearchButton" onclick="fnSearchContent()" value="<fmt:message key="button.search"/>" class="btn-search"/>
				<input type="RESET" id="SearchReset" name="SearchReset" value="<fmt:message key="button.clear"/>" class="btn-search"/>
			</div>
			</form>
		</div>
	</div>
</app:ui_leftMenu>
<div class="box-list">
	<div id="tab_container">
		<ul>
			<li><a href="#contentListContent">内容列表</a></li>
		</ul>
		<div id="contentListContent">
			<div class="blank10"></div>
			<div id="btn-space" style="display: none;">
				<div id="contentListBtns" class="left">
					<cartmatic:cartmaticBtn btnType="create" onclick="return fnAddContent();" isDisabled="true" inputName="btn_addContent2" />
					<cartmatic:cartmaticBtn btnType="delete" onclick="return fnDoMultiDeleteContents(this);" isDisabled="true" inputName="btn_deleteContent"/>
					<cartmatic:cartmaticBtn btnType="refurbishList" inputType="button" isDisabled="true" onclick="fnRefurbishContentList()" inputName="btn_refurbishList"/>
				</div>
			</div>
			<div id="contentListBody">
				<%--@ include file="content/contentListBody.jsp"--%>
			</div>
		</div>
	</div>
	<img src="${ctxPath}/images/icon/loading.gif" style="display: none">
</div>

<script type="text/javascript" defer>
$j(document).ready(function(){
	btnToP();
});
//搜索商品
function fnSearchContent(){
	$j("#selectCategoryTitle").remove();
	var paraDate=$j("#searchContentCondition :input").serializeArray();
	fnRefurbishContentList(paraDate);
	tab_container.appTabs( "select",0);
	$j("#contentListBtns").show();
}
function fnRefurbishContentList(paraData){
	if(!paraData){
		paraData=$j("#contentListBody :input").serializeArray();
	}
	$j("#contentListBody").empty().append(__FMT.contentListBody_wait);	
	fillDivWithPage($j("#contentListBody"),"${ctxPath}/content/content/dialog.html?doAction=search",paraData);
}
//分页刷新数据
function fnOnGoToPage(){
	fnRefurbishContentList();
}
function fnChangeBtnStatus(a){
	var contentListTabsSelectedIndex=0;
	if(contentListTabs){
		contentListTabsSelectedIndex=contentListTabs.appTabs("option","selected");
	}
	if(a){
		contentListTabsSelectedIndex=a-1;
	}
	var tabContainerSelectedIndex=0;
	if(tab_container){
		tabContainerSelectedIndex=tab_container.appTabs("option","selected");
	}
	var node=fnSelectNode(contentTreeObj);
	
	var isStoreCate=(node&&node.pId==null&&!node.isLinkCategory);
	
	var isEntityCate=(node&&node.pId>0&&!node.isLinkCategory);
	
	var isLinkCategory=(node&&node.pId>0&&node.isLinkCategory);
	
	var isSelectedContent=$j("#contentItem").find("input[type='checkbox'][name='multiIds']:checked").length>0;
	
	var isHasChild=(node!=null&&node.children!=null&&node.children.length>0);
	
	if(isEntityCate||isStoreCate){
		$j("#btn_addCategory").removeClass("button-disabled").removeAttr("disabled");
	}else{
		$j("#btn_addCategory").addClass("button-disabled").attr("disabled","disabled");
	}
	
	if(isEntityCate){
		$j("#btn_addContent,#btn_addLinkCategory").removeClass("button-disabled").removeAttr("disabled");
		$j("#btn_addContent2,#btn_refurbishList").removeClass("btn-disabled").removeAttr("disabled");
	}else{
		$j("#btn_addContent,#btn_addLinkCategory").addClass("button-disabled").attr("disabled","disabled");
		$j("#btn_addContent2,#btn_refurbishList").addClass("btn-disabled").attr("disabled","disabled");
	}
	
	if(isLinkCategory||(isEntityCate&&!isHasChild)){
		$j("#btn_deletNode").removeClass("button-disabled").removeAttr("disabled");
	}else{
		$j("#btn_deletNode").addClass("button-disabled").attr("disabled","disabled");
	}

	if(isEntityCate||isLinkCategory){
		$j("#btn_moveCategoryDown,#btn_moveCategoryUp,#btn_openCategory").removeClass("button-disabled").removeAttr("disabled");
	}else{
		$j("#btn_moveCategoryDown,#btn_moveCategoryUp,#btn_openCategory").addClass("button-disabled").attr("disabled","disabled");
	}
	if(isSelectedContent){
		$j("#btn_deleteContent").removeClass("btn-disabled").removeAttr("disabled");
	}else{
		$j("#btn_deleteContent").addClass("btn-disabled").attr("disabled","disabled");
	}
	if(tabContainerSelectedIndex==0){
		getSbtn("contentListContent");
	}
}
function fnDeleteNode(){
	var node=fnSelectNode(contentTreeObj);
	if(node!=null&&confirm(__FMT.common_message_confirmDeleteThis+node.name+"?")){
		var paramData={categoryId:node.id};
		$j.post(__ctxPath+"/catalog/contentCategory.html?doAction=delete",paramData,function (result){
			if(result.status==1){
				$j("#contentCategoryFormContent").empty();
				fnRemoveTab("cate_tab_"+node.id);
				contentTreeObj.removeNode(node);
			}
			sysMsg(result.msg,result.status!=1);
		},"json");
	}
}
function fnOpenContentForm(id,code){
	fnCreateTabWindow(id,'#'+code,'content/content/window.html','doAction=edit&contentId='+id);
}

//添加商品
function fnAddContent(param){
	param=param?('&'+param):'';
	var node=fnSelectNode(contentTreeObj);
	if(node!=null&&node.pId!=null&&!node.isLinkCategory){
		fnCreateTabWindow(null,"#创建新内容",'content/content/window.html','doAction=add&categoryId='+node.id+param);
	}else{
		alert("请先选择要添加内容的目录!");
	}
}



var errors=new Array();
function fnSaveCategoryHandler(result){
	for(var i=0;i<errors.length;i++){
    	errors[i].remove(); 
    }
	var data=result.data;
	if(result.status==-500){
		sysMsg(result.msg,true);
	}else if(result.status==2){
   		var jFiledErrors=data.jFiledErrors;
   		$j.each(jFiledErrors,function(index,jFieldError){
    		var fieldInput=$j(':input[name='+jFieldError.field+']:last');
    		error=$j("<span class=\"fieldError\">"+jFieldError.message+"</span>");
    		fieldInput.after(error);
    		errors.push(error);
    		error=$j('<img src="${ctxPath}/images/iconWarning.gif" alt="<fmt:message key="icon.warning"/>" class="validationWarning"/>');
    		fieldInput.parent().prev().append(error);
    		errors.push(error);
    		sysMsg(jFieldError.field+","+jFieldError.message,true);
    	});
   	}else{
   		if($j("#categoryId").val()==""){
   			$j("#categoryId").val(data.categoryId);
   			$j("#isLinkCategoryCheckbox").empty();
   			$j("#isLinkCategoryMessage").empty();
   			$j("#isLinkCategoryMessage").show();
   			if(data.isLinkCategory == 1){
   				$j("#isLinkCategoryMessage").html("<fmt:message key="category.isLinkCategory.yes1"/>");
   			}else{
   				$j("#linkUrlLabel").empty();
   				$j("#isLinkCategoryMessage").html("<fmt:message key="category.isLinkCategory.yes0"/>");
   				$j("#linkUrlDiv").hide();
   			}
   			fnAddItemToTree(data.categoryId,data.categoryName);
   		}else{
   			var selectSpan=$j(".active",$j("#contentList_categorySelector_tree"));
   			$j("#titleCategoryName").html(data.categoryName);
   			selectSpan.html(data.categoryName);
   		}
   		sysMsg(result.msg);
   	}
}

function fnDeleteCategory(){
	var treeItem=fnFindActiveItem();
	if(treeItem&&confirm(__FMT.common_message_confirmDeleteThis+treeItem.name+"?")){
		var paramData={categoryId:treeItem.id};
		$j.post(__ctxPath+"/catalog/contentCategory.html?doAction=delete",paramData,fnDeleteCategoryHandler,"json");
	}
}

function fnDeleteCategoryHandler(result){
	if(result.status==1){
		fnDeleteTreeItem();
		$j("#contentCategoryFormContent").empty();
		sysMsg(result.msg);
	}else{
		sysMsg(result.msg,true);
	}
}
</script>
<c:set var="swf_upload" value="true" scope="request"/>