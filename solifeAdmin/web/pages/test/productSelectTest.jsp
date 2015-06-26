<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="cartmatic" tagdir="/WEB-INF/tags/cartmatic"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/catalog"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Testing page</title>
		<%@ include file="/decorators/include/styles.jspf"%>
		<%@ include file="/decorators/include/javascripts.jspf"%>
	</head>
	<body>
	<script type="text/javascript">
	/**
 * 显示dialog
 * @param {Object} product 所选择的Product对象
 * @param {Object} paraData 选择器_show（参数）所传递的参数
 * @return Array 返回所选产品的json对象
 */
	function fnTestSelectProduct(product,paraData){
		var data="";
		data+="paraData:"+paraData+"\n";
		data+="productId:"+product.productId+"\n";
		data+="productName:"+product.productName+"\n";
		data+="productCode:"+product.productCode+"\n";
		data+="brandId:"+product.brand.brandId+"\n";
		data+="brandName:"+product.brand.brandName+"\n";
		data+="productSkuCode:"+product.defaultProductSku.productSkuCode+"\n";
		data+="productSkuId:"+product.defaultProductSku.productSkuId+"\n";
		data+="price:"+product.defaultProductSku.price+"\n";
		data+="salePrice:"+product.defaultProductSku.salePrice+"\n";
		data+="image:"+product.defaultProductSku.image+"\n";
		productSelector_close();
		alert(data);
	}
	/**
  * 显示dialog
  * @param {Object} product 所选择的Product对象
  * @param {Object} paraData 选择器_show（参数）所传递的参数
  * @return Array 返回一个数组，数组元素为产品的json对象
  */
	function fnTestSelectProductSku(productSku){
		var data="productSkuCode:"+productSku.productSkuCode+"\n";
		data+="productSkuId:"+productSku.productSkuId+"\n";
		data+="price:"+productSku.price+"\n";
		data+="salePrice:"+productSku.salePrice+"\n";
		data+="image:"+productSku.image+"\n";
		data+="productId:"+productSku.product.productId+"\n";
		data+="productName:"+productSku.product.productName+"\n";
		data+="productCode:"+productSku.product.productCode+"\n";
		data+="brandId:"+productSku.product.brand.brandId+"\n";
		data+="brandName:"+productSku.product.brand.brandName+"\n";
		alert(data);
	}
	/**
	显示dialog
	*/
	function fnTestSelectMultiProduct(productList,paraData){
		var data="";
		data+="paraData:"+paraData+"\n\n";
		for(var i=0;i<productList.length;i++){
			var product=productList[i];
			data+="productId:"+product.productId+"\n";
			data+="productName:"+product.productName+"\n";
			data+="productCode:"+product.productCode+"\n";
			data+="brandId:"+product.brand.brandId+"\n";
			data+="brandName:"+product.brand.brandName+"\n";
			data+="productSkuCode:"+product.defaultProductSku.productSkuCode+"\n";
			data+="productSkuId:"+product.defaultProductSku.productSkuId+"\n";
			data+="price:"+product.defaultProductSku.price+"\n";
			data+="salePrice:"+product.defaultProductSku.salePrice+"\n";
			data+="image:"+product.defaultProductSku.image+"\n\n";
		}
		log(data);
	}

	function fnTestSelectMultiProductSku(productSkuList){
		var data="";
		for(var i=0;i<productSkuList.length;i++){
			var productSku=productSkuList[i];
			data+="productSkuCode:"+productSku.productSkuCode+"\n";
			data+="productSkuId:"+productSku.productSkuId+"\n";
			data+="price:"+productSku.price+"\n";
			data+="salePrice:"+productSku.salePrice+"\n";
			data+="image:"+productSku.image+"\n";
			data+="productId:"+productSku.product.productId+"\n";
			data+="productName:"+productSku.product.productName+"\n";
			data+="productCode:"+productSku.product.productCode+"\n";
			data+="brandId:"+productSku.product.brand.brandId+"\n";
			data+="brandName:"+productSku.product.brand.brandName+"\n";
		}
		alert(data);
	}
</script>
	<input id="b2" type="button" class="admin-btn" value="选择产品1" onclick="productSelector_show('kkk_DIV')"/>
	<product:productSelector id="productSelector" ondblclick="fnTestSelectProduct" autoClose="false"></product:productSelector>
	
	
	<input id="b3" type="button" class="admin-btn"  value="选择SKU"/>
	<product:productSkuSelector id="productSkuSelector" showSelectorBtnId="b3" title="哈哈" ondblclick="fnTestSelectProductSku" showProductKinds="1,2"></product:productSkuSelector>
	
	<%--多选--%>
	<input id="b4" type="button" class="admin-btn" value="选择产品(多选)" onclick="multiSelect_productSelector_show('kkk_DIV')" />
	<product:productSelector id="multiSelect_productSelector" ondblclick="fnTestSelectMultiProduct" autoClose="true" multiSelect="true"></product:productSelector>
	
	
	<input id="b5" type="button" class="admin-btn"  value="选择SKU(多选)"/>
	<product:productSkuSelector id="multiSelect_productSkuSelector" showSelectorBtnId="b5" title="哈哈" ondblclick="fnTestSelectMultiProductSku" showProductKinds="1,2" multiSelect="true"></product:productSkuSelector>
	
	
	<input id="b2" type="button" class="admin-btn" value="选择产品(指定Catalog)" onclick="productSelector_catalog_show('kkk_DIV')"/>
	<product:productSelector id="productSelector_catalog" ondblclick="fnTestSelectProduct" catalogId="4" autoClose="false"></product:productSelector>
	
	<input id="b6" type="button" class="admin-btn" value="选择产品(实体Catalog下产品)" onclick="productSelector_catalog2_show('kkk_DIV')"/>
	<product:productSelector id="productSelector_catalog2" ondblclick="fnTestSelectProduct" virtual="2" autoClose="false"></product:productSelector>
	<%@ include file="/decorators/include/uiResource.jspf"%>
	</body>
</html>