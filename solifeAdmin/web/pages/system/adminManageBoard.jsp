<%@ include file="/common/taglibs.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-content">
	<tr>
		<th>
			 更新产品类型
		</th>
	</tr>
	<tr>
		<td>
			<form id="updateProductTypeForm">
				目录编码:<input class="Field200" type="text" name="catalogCode" />
				商品分类编码:<input class="Field400" type="text" name="categoryCode" />
				要更新到的产品类型:<input class="Field200" type="text" name="productTypeName" />
				<input type="button" value="提交" onclick="fnUpdateProductType();">
				<input type="hidden" name="doAction" value="updateProductType">
			</form>
		</td>
    </tr>
    <tr>
		<th>
			删除产品
		</th>
	</tr>
	<tr>
		<td>
			<form id="deleteProductForm">
				目录编码:<input class="Field200" type="text" name="catalogCode" />
				商品分类编码:<input class="Field400" type="text" name="categoryCode" />
				是否包含子目录:<input type="checkbox" name="isIncludeSubCat" value="true" />
				<input type="button" value="提交" onclick="fnDeleteProduct();">
				<input type="hidden" name="doAction" value="deleteProduct">
			</form>
		</td>
    </tr>
</table>
<script type="text/javascript">
	function fnUpdateProductType(){
		var params=$j("#updateProductTypeForm").serializeArray();
		$j.post(__ctxPath+"/system/adminManageBoard.html",params,function(result){
			if(result.status==1){
				sysMsg("更新成功,"+result.msg);
			}else{
				sysMsg("更新失败,"+result.msg,true);
			}
		},"json");
	}
	
	function fnDeleteProduct(){
		var params=$j("#deleteProductForm").serializeArray();
		$j.post(__ctxPath+"/system/adminManageBoard.html",params,function(result){
			if(result.status==1){
				sysMsg("删除成功,"+result.msg);
			}else{
				sysMsg("删除失败,"+result.msg,true);
			}
		},"json");
	}
</script>
