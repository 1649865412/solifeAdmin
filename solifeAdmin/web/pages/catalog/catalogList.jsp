<%@page import="com.cartmatic.estore.webapp.util.RequestContext"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="search" tagdir="/WEB-INF/tags/search"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<app:pageHeading pageHeadingKey="catalogList.heading" />
<script type="text/javascript" defer>
var productListTabs=null;
$j(document).ready(function(){
	productListTabs=$j("#productListTabs").appTabs({selected:${empty param.productListTab ? 0 : 1}});
	<c:if test="${param.name!=null}">
		productListTabs.appTabs( "select", 1);
		fnSearchProduct();
	</c:if>
});
</script>
<app:ui_leftMenu>
	<div class="sidebar-left">
		<form method="post" action="${ctxPath}/catalog/catalog.html" onsubmit="return false;">
		<div class="tab" id="productListTabs">
			<ul>
				<li>
					<a href="#productListcategorySelector" onclick="fnChangeBtnStatus(1);"><fmt:message key="productList.productCategory" /> </a>
				</li>
				<li>
					<a href="#productListSearchCondition" onclick="fnChangeBtnStatus(2);"><fmt:message key="productList.search" /> </a>
				</li>
			</ul>
			<div style="padding:0;width:244px;" id="productListcategorySelector">
				<div class="tree-do-wrap">
					<input id="btn_openCategory" type="button" value="" onclick="fnOpen();" class="button_view_details button-disabled" title="打开" disabled="disabled">
					<input id="btn_moveCategoryDown" type="button" value="" onclick="fnMoveCategoryDown();" class="button_move_down button-disabled" title="<fmt:message key="button.small.down" />">
					<input id="btn_moveCategoryUp" type="button" value="" onclick="fnMoveCategoryUp();" class="button_move_up button-disabled" title="<fmt:message key="button.small.up" />">
					<input id="btn_addCategory" type="button" value="" onclick="fnAddCategoryForm();" class="button_category_create button-disabled" title="添加商品分类">
					<input id="btn_addCatalog" type="button" value="" onclick="fnAddCatalogForm();" class="button_catalog_create" title="添加目录">
					<input id="btn_addVirtualCatalog" type="button" value="" onclick="fnAddVirtualCatalogForm();" class="button_catalog_virtual_create" title="添加虚拟目录">
					<input id="btn_deletNode" type="button" value="" onclick="fnDeleteNode();" class="button_remove button-disabled" title="删除">
				</div>
				<%--左边搜索目录列表--%>
				<%@ include file="include/catalogListTree.jsp"%>
			</div>
			<div class="content" id="productListSearchCondition">
				<div class="title">
					<fmt:message key="product.productName" />
				</div>
				<input name="name" type="text" style="width: 200px" value="${param.name}" />
				<div class="title">
					<fmt:message key="product.productCode" />
				</div>
				<input name="productCode" type="text" style="width: 200px" />
				<div class="title">
					<fmt:message key="productSku.productSkuCode" />
				</div>
				<input name="skuCode" type="text" style="width: 200px" />
				<div class="title">
					<fmt:message key="category.categoryId" />
				</div>
				<select id="categoryId" name="categoryId" style="width: 200px">
					<option value=""></option>
					<c:forEach items="${categoryTreeItems}" var="categoryTreeItem">
						<option value="${categoryTreeItem.categoryId}">
							<c:if test="${categoryTreeItem.level-1>=0}">
								<c:forEach begin="1" end="${categoryTreeItem.level-1}" var="level">—</c:forEach>
							</c:if>
							${categoryTreeItem.categoryName}
						</option> 
					</c:forEach>
				</select>
				<div class="title">
					<fmt:message key="product.productTypeId" />
				</div>
				<select id="productTypeId" name="productTypeId" style="width: 200px">
					<option value=""></option>
					<c:forEach items="${productTypes}" var="productType">
						<option value="${productType.productTypeId}" <c:if test="${product.productType.productTypeId==productType.productTypeId}">selected="selected"</c:if>>
							${productType.productTypeName}
						</option>
					</c:forEach>
				</select>
				<div class="title">
					<fmt:message key="product.brandId" />
				</div>
				<select name="brandId" id="brandId" style="width: 200px">
					<option value=""></option>
					<c:forEach items="${brands}" var="brand">
						<option value="${brand.brandId}" <c:if test="${product.brand.brandId==brand.brandId}">selected="selected"</c:if>>
							${brand.brandName}
						</option>
					</c:forEach>
				</select>
				<%--======================状态查询条件================================--%>
				<div class="title">
					<fmt:message key="product.status" />
				</div>
				<select name="productStatus" id="productStatus" style="width: 200px">
					<option value=""></option>
					<option value="0"><fmt:message key="product.status_0"/></option>
					<option value="1"><fmt:message key="product.status_1"/></option>
					<option value="2"><fmt:message key="product.status_2"/></option>
					<option value="6"><fmt:message key="product.status_6"/></option>
				</select>
				<%--======================产品类型查询条件================================--%>
				<div class="title">
					<fmt:message key="product.productKind" />
				</div>
				<select name="productKind" id="productKind" style="width: 200px">
					<option value=""></option>
					<option value="1"><fmt:message key="product.productKind_1"/></option>
					<option value="2"><fmt:message key="product.productKind_2"/></option>
					<option value="3"><fmt:message key="product.productKind_3"/></option>
				</select>
				<%--======================销售规则查询条件================================--%>
				<div class="title">
					<fmt:message key="product.availabilityRule" />
				</div>
				<select name="availabilityRule" id="availabilityRule" style="width: 200px">
					<option value=""></option>
					<option value="1"><fmt:message key="product.availabilityRule_1"/></option>
					<option value="2"><fmt:message key="product.availabilityRule_2"/></option>
					<option value="3"><fmt:message key="product.availabilityRule_3"/></option>
					<option value="4"><fmt:message key="product.availabilityRule_4"/></option>
					<option value="5"><fmt:message key="product.availabilityRule_5"/></option>
				</select>
				<div class="title">
					<fmt:message key="product.supplierId" />
				</div>
				<select name="supplierId" id="supplierId" style="width: 200px">
					<option value=""></option>
					<c:forEach items="${suppliers}" var="supplier">
						<option value="${supplier.supplierId}" <c:if test="${product.supplier.supplierId==supplier.supplierId}">selected="selected"</c:if>>
							${supplier.supplierName}
						</option>
					</c:forEach>
				</select>
				<div class="blank10"></div>
				<input type="hidden" id="btnSearch" name="btnSearch" value="Search" />
				<input type="hidden" id="productListTab" name="productListTab" value="searchContent" />
				<input type="submit" id="SearchButton" name="SearchButton" onclick="fnSearchProduct()" value="<fmt:message key="button.search"/>" class="btn-search"/>
				<input type="RESET" id="SearchReset" name="SearchReset" value="<fmt:message key="button.clear"/>" class="btn-search"/>
			</div>
		</div>
		</form>
	</div>
</app:ui_leftMenu>
<div class="box-list">
	<div id="tab_container">
		<ul>
			<li><a href="#productListContent">商品列表</a></li>
		</ul>
		<div id="productListContent">
			<div class="blank10"></div>
			<div id="btn-space" style="display: none;">
				<div id="productListBtns">
					<cartmatic:cartmaticBtn btnType="addProduct" inputType="button" isDisabled="true" onclick="fnAddProduct()" inputName="btn_addProduct"/>
					<cartmatic:cartmaticBtn btnType="addExistingProduct" inputType="button" isDisabled="true" inputName="btn_addExistingProduct" onclick="catalogProductSelector_show();" />
					<cartmatic:cartmaticBtn btnType="createProductPackage" inputType="button" isDisabled="true" onclick="fnAddProductPackage()" inputName="btn_createProductPackage"/>
					<cartmatic:cartmaticBtn btnType="copy" inputType="button" isDisabled="true" onclick="fnCloneProduct();" inputName="btn_copyProduct"/>
					<cartmatic:cartmaticBtn btnType="allEdit" inputType="button" isDisabled="true" onclick="fnUnitiveBulkEdit();" inputName="btn_allEdit"/>
					<cartmatic:cartmaticBtn btnType="eachEdit" inputType="button" isDisabled="true" onclick="fnEachEditSelect();" inputName="btn_eachEdit"/>
					<cartmatic:cartmaticBtn btnType="delete" inputType="button" isDisabled="true" onclick="fnDeleteProducts()" inputName="btn_delete"/>
					<cartmatic:cartmaticBtn btnType="removeProduct" inputType="button" isDisabled="true" onclick="fnRemoveProducts()" inputName="btn_reomveProduct"/>
					<cartmatic:cartmaticBtn btnType="refurbishList" inputType="button" isDisabled="true" onclick="fnRefurbishProductList()" inputName="btn_refurbishList"/>
					<cartmatic:cartmaticBtn btnType="listView0" inputType="button" isDisabled="true" onclick="fnUpdateProductViewSetting(0)" inputName="btn_listView0"/>
					<cartmatic:cartmaticBtn btnType="listView1" inputType="button" isDisabled="true" onclick="fnUpdateProductViewSetting(1)" inputName="btn_listView1"/>
				</div>
			</div>
			<div id="productListContentBody">
				<%--@ include file="include/productListContent.jsp"--%>
			</div>
		</div>
	</div>
	<img src="${ctxPath}/images/icon/loading.gif" style="display: none">
</div>
<product:productSelector id="catalogProductSelector" ondblclick="fnAddExistingProduct" virtual="2" ></product:productSelector>
<SCRIPT type="text/javascript">
	//搜索商品
	function fnSearchProduct(){
		var paraData=$j("#productListSearchCondition :input").serializeArray();
		fnRefurbishProductList(paraData);
		tab_container.appTabs( "select",0);
		$j("#productListBtns").show();
	}
	//刷新商品列表,搜索,目录,分页都使用
	function fnRefurbishProductList(paraData){
		if(!paraData){
			paraData=$j("#productListContentBody :input").serializeArray();
		}
		fillDivWithPage($j("#productListContentBody"),"${ctxPath}/catalog/product/dialog.html?doAction=search",paraData);
	}
	//分页刷新数据
	function fnOnGoToPage(){
		fnRefurbishProductList();
	}
	//编辑商品
	function fnOpenProductForm(pid,pcode){
		fnCreateTabWindow(pid,'#'+pcode,'catalog/product/window.html','doAction=edit&productId='+pid);
	}
	//添加商品
	function fnAddProduct(param){;
		param=param?('&'+param):'';
		var node=fnSelectNode(catalogTreeObj);
		if(node!=null&&node.pId!=null&&!node.isVirtual){
			fnCreateTabWindow(null,'#创建新商品','catalog/product/window.html','doAction=add&categoryId='+node.id+param);
		}else{
			alert("请先选择要添加商品的商品分类!");
		}
	}
	//添加商品包
	function fnAddProductPackage(){
		fnAddProduct("isPackage=1");
	}
	//增加新的映射产品
	function fnAddExistingProduct(product){
		var node=fnSelectNode(catalogTreeObj);
		if(node!=null&&node.pId!=null){
			var paramData={productId:product.productId,categoryId:node.id};
			$j.post(__ctxPath+"/catalog/productCategory/dialog.html?doAction=addExistingProduct",paramData,function (result){
				if(result.status==1){
					fnRefurbishProductList();
				}
				sysMsg(result.msg,result.status!=1);
			},"json");
		}
	}
	//移除映射产品
	function fnRemoveProducts(paraData){
		var paraData=$j("#productListContentBody :input[name='multiIds']").serializeArray();
		var node=fnSelectNode(catalogTreeObj);
		if(node!=null&&paraData.length>0){
			var itemNames = fnGetSelectedItemNames();
			if(confirm( __FMT.common_message_confirmRemoveProduct+ " " + itemNames+"?")){
				$j.post("${ctxPath}/catalog/productCategory/dialog.html?doAction=removeExistingProduct&categoryId="+node.id,paraData,function (result){
					if(result.status==1){
						sysMsg(result.msg);
						fnRefurbishProductList();
					}else{
						sysMsg(result.msg,true);
					}
				},"json");
			}
		}else{
			alert("<fmt:message key="productList.must.selectProduct" />");
		}
	}
	
	function fnDeleteNode(){
		var node=fnSelectNode(catalogTreeObj);
		if(node!=null&&confirm(__FMT.common_message_confirmDeleteThis+node.name+"?")){
			if(node.pId==null){
				var paramData={catalogId:node.cId};
				$j.post(__ctxPath+"/catalog/catalog.html?doAction=delete",paramData,function (result){
					if(result.status==1){
						fnRemoveTab("catalog_tab_"+node.id);
						catalogTreeObj.removeNode(node);
					}
					sysMsg(result.msg,result.status!=1);
				},"json");
			}else{
				var paramData={categoryId:node.id};
				$j.post(__ctxPath+"/catalog/productCategory.html?doAction=delete",paramData,function (result){
					if(result.status==1){
						$j("#productListContentBody").empty();
						fnRemoveTab("cate_tab_"+node.id);
						catalogTreeObj.removeNode(node);
					}
					sysMsg(result.msg,result.status!=1);
				},"json");
			}
		}
	}
	
	function fnChangeBtnStatus(a){
		var productListTabsSelectedIndex=0;
		if(productListTabs){
			productListTabsSelectedIndex=productListTabs.appTabs("option","selected");
		}
		if(a){
			productListTabsSelectedIndex=a-1;
		}
		var tabContainerSelectedIndex=0;
		if(tab_container){
			tabContainerSelectedIndex=tab_container.appTabs("option","selected");
		}
		var node=fnSelectNode(catalogTreeObj);
		var isEntityCate=false;
		if(node&&node.pId>0&&!node.isVirtual){
			isEntityCate=true;
		}
		var isVirtualCate=false;
		if(node&&node.pId>0&&node.isVirtual){
			isVirtualCate=true;
		}
		var isEntityCatalog=(node&&node.pId==null&&!node.isVirtual);
		
		var isVirtualCatalog=(node&&node.pId==null&&node.isVirtual);
		
		var isSelectedProdct=$j("#productItem").find("input[type='checkbox'][name='multiIds']:checked").length>0;
		
		var isHasChild=(node!=null&&node.children!=null&&node.children.length>0);
		if(isEntityCate&&productListTabsSelectedIndex!=1){
			$j("#btn_addProduct,#btn_createProductPackage").removeClass("btn-disabled").removeAttr("disabled");
		}else{
			$j("#btn_addProduct,#btn_createProductPackage").addClass("btn-disabled").attr("disabled","disabled");
		}
		if(isVirtualCate&&productListTabsSelectedIndex!=1){
			$j("#btn_addExistingProduct").removeClass("btn-disabled").removeAttr("disabled");
		}else{
			$j("#btn_addExistingProduct").addClass("btn-disabled").attr("disabled","disabled");
		}
		if(isEntityCate||isVirtualCate||isEntityCatalog||isVirtualCatalog){
			$j("#btn_openCategory,#btn_addCategory").removeClass("button-disabled").removeAttr("disabled");
			if(isHasChild){
				$j("#btn_deletNode").addClass("button-disabled").attr("disabled","disabled");
			}else{
				$j("#btn_deletNode").removeClass("button-disabled").removeAttr("disabled");
			}
		}else{
			$j("#btn_openCategory,#btn_addCategory,#btn_deletNode").addClass("button-disabled").attr("disabled","disabled");
		}
		if(isEntityCate||isVirtualCate){
			$j("#btn_moveCategoryDown,#btn_moveCategoryUp").removeClass("button-disabled").removeAttr("disabled");
		}else{
			$j("#btn_moveCategoryDown,#btn_moveCategoryUp").addClass("button-disabled").attr("disabled","disabled");
		}
		if((isEntityCate||isVirtualCate)&&productListTabsSelectedIndex!=1){
			$j("#btn_refurbishList").removeClass("btn-disabled").removeAttr("disabled");
			fnChangeProductViewBtnStatus();
		}else{
			$j("#btn_refurbishList").addClass("btn-disabled").attr("disabled","disabled");
			fnChangeProductViewBtnStatus();
		}
		if(isSelectedProdct){
			$j("#btn_copyProduct,#btn_allEdit,#btn_eachEdit,#btn_delete").removeClass("btn-disabled").removeAttr("disabled");
		}else{
			$j("#btn_copyProduct,#btn_allEdit,#btn_eachEdit,#btn_delete").addClass("btn-disabled").attr("disabled","disabled");
		}
		if(isVirtualCate&&isSelectedProdct){
			$j("#btn_reomveProduct").removeClass("btn-disabled").removeAttr("disabled");
		}else{
			$j("#btn_reomveProduct").addClass("btn-disabled").attr("disabled","disabled");
		}
		if(tabContainerSelectedIndex==0){
			getSbtn("productListContent");
		}
	}
	
	
	
	//复制产品
	function fnCloneProduct(){
		var paramData=$j("#productListContentBody :input[name='multiIds']").serializeArray();
		if(paramData.length>0){
			var url="${ctxPath}/catalog/product/dialog.html?doAction=copyProduct&productId="+paramData[0].value;
			jQuery.ajax({type: "GET",url: url,success: fnCloneProductHandler,async: false,dataType: "json"});
		}else{
			alert("<fmt:message key="productList.must.selectProduct" />");
		}
	}
	function fnCloneProductHandler(result){
		sysMsg(result.msg,result.status!=1);
		if(result.status==1){
			var data=result.data;
			fnRefurbishProductList();
			fnCreateTabWindow(data.productId,'#'+data.productCode,'catalog/product/window.html','doAction=edit&productId='+data.productId);
		}
	}
	
	//统一修改
	function fnUnitiveBulkEdit(paraData){
		var paraData=$j("#productListContentBody :input[name='multiIds']").serializeArray();
		if(paraData.length>0){
			var productIds="";
			$j.each(paraData,function(i,n){
				productIds += n.value+",";
			});
			productIds = productIds.substring(0,productIds.length-1);
			fnCreateTabWindow(null,'#统一修改','catalog/product/window.html','doAction=unitiveBulkEdit&productIds='+productIds);
		}else{
			alert("<fmt:message key="productList.must.selectProduct" />");
		}
	}
	
	//逐个修改
	function fnEachEditSelect(){
		var paraData=$j("#productListContentBody :input[name='multiIds']").serializeArray();
		if(paraData.length>0){
			var productIds="";
			$j.each(paraData,function(i,n){
				productIds += n.value+",";
			});
			productIds = productIds.substring(0,productIds.length-1);
			fnCreateTabWindow(null,'#逐个修改','catalog/product/window.html','doAction=productEachEdit&productIds='+productIds);
		}else{
			alert("<fmt:message key="productList.must.selectProduct" />");
		}
	}
	
	function fnDeleteProducts(paraData){
		var paraData=$j("#productListContentBody :input[name='multiIds']").serializeArray();
		if(paraData.length>0){
			var itemNames = fnGetSelectedItemNames();
			if(confirm( __FMT.common_message_confirmDeleteThis+ " " + itemNames+"?")){
				$j.post("${ctxPath}/catalog/product/dialog.html?doAction=multiDelete",paraData,function (result){
					if(result.status==1){
						sysMsg(result.msg);
						fnRefurbishProductList();
					}else{
						sysMsg(result.msg,true);
					}
				},"json");
			}
		}else{
			alert("<fmt:message key="productList.must.selectProduct" />");
		}
	}
	var productViewType=<%=RequestContext.getAdminInfo().getProductViewSetting()%>;
	function fnChangeProductViewBtnStatus(){
		$j("input[name=btn_listView"+productViewType+"]").addClass("btn-disabled").attr("disabled","disabled").siblings("input[name^=btn_listView]").removeClass("btn-disabled").removeAttr("disabled");
	}
	function fnUpdateProductViewSetting(view){
		$j.post(__ctxPath+"/editProfile.html?doAction=updateProductViewSetting&productView="+view,function (result){
			if(result.status==1){
				sysMsg(result.msg);
				fnRefurbishProductList();
				productViewType=view;
				fnChangeProductViewBtnStatus();
			}else{
				sysMsg(result.msg,true);
			}
		},"json");
	}
</SCRIPT>