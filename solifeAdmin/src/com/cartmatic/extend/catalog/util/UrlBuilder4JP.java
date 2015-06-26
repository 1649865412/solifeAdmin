package com.cartmatic.extend.catalog.util;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.util.UrlBuilder;
import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductCategory;
import com.cartmatic.estore.common.model.content.Content;
import com.cartmatic.estore.core.util.UrlUtil;

public class UrlBuilder4JP implements UrlBuilder{
	protected final transient Log logger = LogFactory.getLog(getClass());
	private  CatalogHelper catalogHelper=CatalogHelper.getInstance();

	public Category getContentCategoryByUri(String uri) {
		String tempUri[]=uri.split("/");
		String code=tempUri[tempUri.length-2];
		Category category=catalogHelper.getCategoryByCode4Front(Constants.CATEGORY_TYPE_CONTENT,code);
		return category;
	}

	public String getContentCategoryUrl(Category category3) {
		List<Category> navigatorCategorys=category3.getNavigatorCategorys();
		if(navigatorCategorys!=null){
			Category category=navigatorCategorys.get(navigatorCategorys.size()-1);
			//是否链接目录
			if(category.getIsLinkCategory().shortValue()==Category.ISLINKCATEGORY_YES){
				if(StringUtils.isNotBlank(category.getLinkUrl())&&category.getLinkUrl().indexOf("http://")!=-1){
					return category.getLinkUrl();
				}else{
					StringBuffer url=new StringBuffer(ConfigUtil.getInstance().getCtxPath());
					if(StringUtils.isNotBlank(category.getLinkUrl())&&!category.getLinkUrl().startsWith("/")){
						url.append("/");
					}
					url.append(category.getLinkUrl());
					return url.toString();
				}
			}
		}
		StringBuffer url=new StringBuffer(ConfigUtil.getInstance().getCtxPath());
		
		String url_prefix="/Customer_Service/";
		if(navigatorCategorys!=null){
			Category category=navigatorCategorys.get(navigatorCategorys.size()-1);
			//TODO 过渡性调整，MA针对内容是Testimonials目录的，不使用原有的Customer_Service mapping
			if(ConfigUtil.getInstance().getCmsAccessCategories().indexOf(","+category.getCategoryCode()+",")!=-1){
				url_prefix="/";
			}
		}
		url.append(url_prefix);
		for (Category category : navigatorCategorys) {
			url.append(UrlUtil.formatUriPart(category.getCategoryCode()));
			url.append("/");
		}
		url.append("contents.html");
		return url.toString();
	}

	public Category getProductCategoryByUri(String uri) {
		String code=uri.substring(uri.lastIndexOf("/")+1, uri.lastIndexOf("_catalog"));
		Category category=catalogHelper.getCategoryByCode4Front(Constants.CATEGORY_TYPE_CATALOG,code);
		return category;
	}

	public String getProductCategoryUrl(Category category) {
		List<Category> navigatorCategorys=category.getNavigatorCategorys();
		StringBuffer url=new StringBuffer(ConfigUtil.getInstance().getCtxPath());
		url.append("/");
		if(navigatorCategorys!=null){
			for (int i = 0; i < navigatorCategorys.size(); i++) {
				Category cat=navigatorCategorys.get(i);
				url.append(UrlUtil.formatUriPart(cat.getCategoryCode()));
				if(i<navigatorCategorys.size()-1){
					url.append("/");
				}
			}
		}
		url.append("_catalog.html");
		return url.toString();
	}
	
	@Override
	public String getProductUrl(Product product,Integer catalogId, Integer categoryId) {
		if(catalogId==null){
			catalogId=ConfigUtil.getInstance().getStore().getCatalogId();
		}
		StringBuffer url=new StringBuffer();
		url.append("/product/"); 
		url.append(product.getId().intValue());
		Category mainCateogry=product.getMainCategory(catalogId);
		//当指定的目录不是主目录时，uri应为productName_pId_cateogryId
		//当主目录为空的时候，直接忽略指定目录
		if(categoryId!=null&&mainCateogry!=null&&mainCateogry.getCategoryId().intValue()!=categoryId.intValue()){
			Set<ProductCategory> productCategorys=product.getProductCategorys();
			for (ProductCategory productCategory : productCategorys) {
				if(catalogId.intValue()==productCategory.getCategory().getCatalogId()&&productCategory.getCategoryId().intValue()==categoryId.intValue()){
					url.append("_");
					url.append(categoryId.intValue());
					break;
				}
			}
		}
		url.append(".html");
		return url.toString();
	}
	
	
	@Override
	public String getContentUrl(Content content) {
		String url_prefix="/Customer_Service/";
		StringBuffer url=new StringBuffer(url_prefix);
		url.append(content.getContentCode());
		url.append("_");
		url.append(content.getId().intValue());
		url.append(".html");
		return url.toString();
	}

	@Override
	public Integer[] getIdsByProductUrl(String uri) {
		Integer pIdcId[]=new Integer[]{0,0};
		try
		{
			String temp_pId_cId=uri.substring(uri.lastIndexOf("/")+1,uri.lastIndexOf("."));
			if(temp_pId_cId.indexOf("_")!=-1){
				String temp_pId_cIds[]=temp_pId_cId.split("_");
				pIdcId[0]=Integer.valueOf(temp_pId_cIds[0]);
				pIdcId[1]=Integer.valueOf(temp_pId_cIds[1]);
			}else{
				pIdcId[0]=Integer.valueOf(temp_pId_cId);
			}
		}
		catch (Exception e)
		{
			logger.error("url:"+uri+e);
		}
		return pIdcId;
	}

	@Override
	public Integer getIdByContentUri(String uri) {
		try
		{
			String temp_contentId=uri.substring(uri.lastIndexOf("_")+1,uri.lastIndexOf("."));
			Integer contentId=Integer.valueOf(temp_contentId);
			return contentId;
		}
		catch (Exception e)
		{
			logger.error("url:"+uri+e);
			return null;
		}
	}

}
