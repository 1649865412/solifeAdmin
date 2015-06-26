package com.cartmatic.estore.system.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.service.CategoryManager;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.model.catalog.Category;
import com.cartmatic.estore.common.model.catalog.CategoryTreeItem;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSearchCriteria;
import com.cartmatic.estore.common.model.content.Content;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.common.util.FileUtil;
import com.cartmatic.estore.content.service.ContentManager;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.system.service.SiteMapManager;
import com.cartmatic.estore.system.service.StoreManager;

public class SiteMapManagerImpl implements SiteMapManager
{
	private static final transient Log logger = LogFactory.getLog(SiteMapManagerImpl.class);

	private ProductManager productManager = null;
	
	private CategoryManager categoryManager = null;
	
	private ContentManager contentManager=null;
	
	private StoreManager storeManager=null;
	
	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}


	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}


	public void setContentManager(ContentManager contentManager) {
		this.contentManager = contentManager;
	}
	
	public void makeMapSite(){
		List<Store> stores = storeManager.getAllActiveStores();
		for (Store store : stores){
			makeMapSite(store);
		}
	}


	private void makeMapSite(Store store)
	{
		ConfigUtil configUtil=ConfigUtil.getInstance();
	    if(!configUtil.getIsAutoPostSiteMap())
	    	 return;
		
		String url = store.getSiteUrl();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sitemapIndexStr=new StringBuffer();
		String sitemapIndexHeader="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
		   "<sitemapindex xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/siteindex.xsd\" xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n";
		sitemapIndexStr.append(sitemapIndexHeader);
		
		//是否包含主页
		if(configUtil.getSitemapIncludeHomepage()){
			List<String> productCategorySitemap=genHomeSitemap(store);
			for(int i=0;i<productCategorySitemap.size();i++){
				String loc=url+"/sitemap/"+productCategorySitemap.get(i);
				String lastmod=format.format(new Date());
				sitemapIndexStr.append(appendSitemapIndex(loc, lastmod));
			}
		}
		
		//是否包含产品产品目录
		if(configUtil.getSitemapIncludeProducts()){
			List<String> productCategorySitemap=genProductCategorySitemap(store);
			for(int i=0;i<productCategorySitemap.size();i++){
				String loc=url+"/sitemap/"+productCategorySitemap.get(i);
				String lastmod=format.format(new Date());
				sitemapIndexStr.append(appendSitemapIndex(loc, lastmod));
			}
		}
		
		//是否包含产品
		if(configUtil.getSitemapIncludeProducts()){
			List<String> productStrItems=genProductSitemap(store);
			for(int i=0;i<productStrItems.size();i++){
				String loc=url+"/sitemap/"+productStrItems.get(i);
				String lastmod=format.format(new Date());
				sitemapIndexStr.append(appendSitemapIndex(loc, lastmod));
			}
		}
		
		if(configUtil.getSitemapIncludeContents()){
			List<String> contentStrItems=genContentSitemap(store);
			for(int i=0;i<contentStrItems.size();i++){
				String loc=url+"/sitemap/"+contentStrItems.get(i);
				String lastmod=format.format(new Date());
				sitemapIndexStr.append(appendSitemapIndex(loc, lastmod));
			}
		}
		
		sitemapIndexStr.append("</sitemapindex>\n");
		
		
		String sourcePath = configUtil.getAssetsPath();
		if (sourcePath == null || "".equals(sourcePath))
		{
			logger.warn("StoreFrontInstallationPath is empty!");
			return;
		}
		if (!sourcePath.endsWith("/"))
		{
			sourcePath += "/";
		}
		sourcePath +="sitemap/"+ store.getCode()+ "/sitemap.xml";
		
		try
		{
//			writeGzipFile(sourcePath, sitemapIndexStr.toString());
			FileUtil.writeStringToFile(sourcePath, sitemapIndexStr.toString());
		}
		catch (Exception e)
		{
			if (logger.isDebugEnabled())
			{
				logger.debug(e.toString());
			}
		}
		String postSiteUrl=configUtil.getSiteMapPostUrl();
		if(postSiteUrl.equals(""))
			   return;
		try {
			url=url+"/sitemap.xml";
			url=url.replace("/", "%2F").replace(":", "%3A");
			
			URL postUrl= new URL(postSiteUrl+url);
			HttpURLConnection urlConnect=(HttpURLConnection)postUrl.openConnection();
			urlConnect.setRequestMethod("GET");
			urlConnect.connect();
			int responseCode= urlConnect.getResponseCode();
			if (logger.isDebugEnabled())
			{
				logger.debug("Post the sitemap on ["+postUrl+"] and responseCode["+responseCode+"]!");
			}
			if(responseCode!=200){
				logger.warn("It's failed to post the sitemap on ["+postUrl+"]!");
			}
		    
		} catch (Exception e) {
			logger.error(e);			
		}
	}
	
	private String appendSitemapIndex(String loc,String lastmod){
		StringBuffer sb=new StringBuffer();
		sb.append("<sitemap>\n");
		sb.append("<loc>"+loc+"</loc>\n");
		sb.append("<lastmod>"+lastmod+"</lastmod>\n");
		sb.append("</sitemap>\n");
		return sb.toString();
	}

	public void doMakeGoogleMap()
	{
          
	}

	
	private List<String> genHomeSitemap(Store store){
		List<String> productSitemapFiles=new ArrayList<String>();
		StringBuffer sb=new StringBuffer();
		ConfigUtil configUtil=ConfigUtil.getInstance();
		String url=store.getSiteUrl();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotBlank(url)&&!url.endsWith("/")){
			url+="/";
		}
		String loc=url;
		String lastmod=format.format(new Date());
		String changefreq=configUtil.getSitemapHomepageFrequency();
		String priority=configUtil.getSitemapHomepagePriority();
		sb.append(genSitemapItem(loc, lastmod, changefreq, priority));
		String indexFileName="home.xml.gz";
		writeSitemapItem(store,sb.toString(),indexFileName,false);
		productSitemapFiles.add(indexFileName);
		return productSitemapFiles;
	}
	
	
	private List<String> genProductCategorySitemap(Store store){
		List<String> productSitemapFiles=new ArrayList<String>();
		if(store.getCatalogId()!=null){
			List<CategoryTreeItem> categoryList=categoryManager.getActionCatalogTreeItemList(store.getCatalogId());
			StringBuffer sb=new StringBuffer();
			genProductCategorySitemap(store.getSiteUrl(),sb,categoryList);
			String indexFileName="category.xml.gz";
			writeSitemapItem(store,sb.toString(),indexFileName,false);
			productSitemapFiles.add(indexFileName);
		}
		return productSitemapFiles;
	}
	
	private void genProductCategorySitemap(String siteUrl,StringBuffer sb,List<CategoryTreeItem> categoryList){
		ConfigUtil configUtil=ConfigUtil.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (Category categoryTreeItem : categoryList) {
			String categoryUrl=CatalogHelper.getInstance().getProductCategoryUrl(categoryTreeItem);
			categoryUrl=categoryUrl.replaceAll(ConfigUtil.getInstance().getCtxPath(), "");
			String loc=siteUrl+categoryUrl;
			//String lastmod=format.format(categoryTreeItem.getUpdateTime());
			String lastmod="";
			String changefreq=configUtil.getSitemapProductCategoriesFrequency();
			String priority=configUtil.getSitemapProductCategoriesPriority();
			sb.append(genSitemapItem(loc, lastmod, changefreq, priority));
		}
		
		/*for (Category categoryTreeItem : categoryList) {
			List<CategoryTreeItem> children=new ArrayList<CategoryTreeItem>(categoryTreeItem.getCategorys());
			if(children!=null&&children.size()>0){
				genProductCategorySitemap(siteUrl, sb, children);
			}
		}*/
	}
    
	@SuppressWarnings("unchecked")
	private List<String> genProductSitemap(Store store){
		int itemsPerPage=1000;
		int currentPage=1;
		List<String> productSitemapFiles=new ArrayList<String>();
		SearchCriteria searchCriteria =SearchCriteria.getHqlPagingInstance("", null,currentPage, itemsPerPage, new HashMap());
		ProductSearchCriteria productSearchCriteria=new ProductSearchCriteria();
		productSearchCriteria.setCatalogId(store.getCatalogId());
		searchCriteria=productManager.getProductSearchCriteria4Front(searchCriteria, productSearchCriteria);
		List<Product> pageProducts=productManager.searchByCriteria(searchCriteria);
		ConfigUtil configUtil=ConfigUtil.getInstance();
		boolean isIncludeImages=configUtil.getSitemapProductIncludeImages();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 1; i <= searchCriteria.getTotalPageCount(); i++) {
			if(i!=1){
				searchCriteria.changePaging(i, itemsPerPage);
				pageProducts=productManager.searchByCriteria(searchCriteria);
			}
			if(pageProducts.size()==0){
				break;
			}
			StringBuffer sb=new StringBuffer();
			for (Product product : pageProducts) {
				String url = store.getSiteUrl();
				String mediaUrl = url+configUtil.getMediaPath();
				String productUrl=CatalogHelper.getInstance().getProductUrl(product,store.getCatalogId(), null);
				String loc=url+productUrl;
				String lastmod=format.format(product.getUpdateTime());
				String changefreq=configUtil.getSitemapProductsFrequency();
				String priority=configUtil.getSitemapProductsPriority();
				String images[]=null;
				if(isIncludeImages){
					String image=mediaUrl+"/product/d/"+product.getDefaultProductSku().getImage();
					images=new String[]{image};
				}
				sb.append(genSitemapItem(loc, lastmod, changefreq, priority,images));
			}
			String indexFileName="product_"+i+".xml.gz";
			writeSitemapItem(store,sb.toString(),indexFileName,isIncludeImages);
			productSitemapFiles.add(indexFileName);
		}
		return productSitemapFiles;
	}

	private List<String> genContentSitemap(Store store){
		List<String> productSitemapFiles=new ArrayList<String>();
		StringBuffer sb=new StringBuffer();
		ConfigUtil configUtil=ConfigUtil.getInstance();
		String contentCategoryCodes[]=configUtil.getSitemapContentCategories();
		for (String contentCategoryCode : contentCategoryCodes) {
			Category category=categoryManager.getContentCategoryByCode(store.getStoreId(), contentCategoryCode);
			if(category!=null){
				//TODO 读取数量hardcode 2000
				List<Content> contents=contentManager.searchContentByCategory(category, 0, 2000);
				sb.append(genSitemapContents(store,contents));
			}
			List<Category>childrenCategories =category.getAllChildren();
			if(childrenCategories!=null){
				for (Category category2 : childrenCategories) {
					List<Content> contents=contentManager.searchContentByCategory(category2, 0, 2000);
					sb.append(genSitemapContents(store,contents));
				}
			}
		}
		String indexFileName="contents.xml.gz";
		writeSitemapItem(store,sb.toString(),indexFileName,false);
		productSitemapFiles.add(indexFileName);
		return productSitemapFiles;
		
	}
	
	private void writeSitemapItem(Store store,String itemValue,String indexFileName,boolean isIncludeImages){
		StringBuffer mapString=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		mapString.append("<urlset xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\" xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"");
		if(isIncludeImages){
			mapString.append(" xmlns:image=\"http://www.google.com/schemas/sitemap-image/1.1\"");
		}
		mapString.append(">\n");
		mapString.append(itemValue);
		mapString.append("</urlset>");
		
		writeSitemapFile(store, indexFileName, mapString.toString());
	}
	
	private void writeSitemapFile(Store store,String indexFileName,String fileContent){
		String sourcePath = ConfigUtil.getInstance().getAssetsPath();
		if (sourcePath == null || "".equals(sourcePath))
		{
			logger.warn("StoreFrontInstallationPath is empty!");
			return;
		}
		if (!sourcePath.endsWith("/"))
		{
			sourcePath += "/";
		}
		sourcePath +="sitemap/"+ store.getCode()+"/"+indexFileName;
		try
		{
			writeGzipFile(sourcePath, fileContent);
			//FileUtil.writeStringToFile(sourcePath, itemValue);
		}
		catch (Exception e)
		{
			logger.error(e);
		}
	}
	
	private String genSitemapContents(Store store,List<Content> contents){
		StringBuffer buf=new StringBuffer();
		ConfigUtil configUtil=ConfigUtil.getInstance();
		String url = store.getSiteUrl();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (Content content : contents) {
			if(content.getStatus().intValue()==Constants.STATUS_ACTIVE){
				//TODO 这样的目录URL存在不准确性
				String contentUrl=CatalogHelper.getInstance().getContentUrl(content);
				String loc=url+contentUrl;
				String lastmod=format.format(content.getUpdateTime());
				String changefreq=configUtil.getSitemapProductsFrequency();
				String priority=configUtil.getSitemapProductsPriority();
				buf.append(genSitemapItem(loc, lastmod, changefreq, priority));
			}
		}
		return buf.toString();
	}
	
	private String genSitemapItem(String loc,String lastmod,String changefreq,String priority,String...images){
		StringBuffer buf=new StringBuffer();
		buf.append("<url>\n");
		buf.append("<loc>"+loc+"</loc>\n");
		if(images!=null){
			for (String image : images) {
				buf.append("<image:image>\n");
				buf.append("<image:loc>"+encoderUrlFileName(image)+"</image:loc>\n");
				buf.append("</image:image>\n");
			}
		}
		buf.append("<lastmod>"+lastmod+"</lastmod>\n");
		buf.append("<changefreq>"+changefreq+"</changefreq>\n");
		buf.append("<priority>"+priority+"</priority>\n");
		buf.append("</url>\n");
		return buf.toString();
	}
	
	private String encoderUrlFileName(String url){
		String url1=url.substring(0,url.lastIndexOf("/")+1);
		String url2=url.substring(url.lastIndexOf("/")+1,url.length());
		try {
			url2=URLEncoder.encode(url2,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url2=url2.replaceAll("\\+","%20");
		url=url1+url2;
		return url;
	}
	
	private void writeGzipFile(String fileName,String stringValue){
		FileOutputStream fout=null; 
		GZIPOutputStream gzout=null; 
		try { 
			 fout=new FileOutputStream(fileName); 
			 gzout=new GZIPOutputStream(fout); 
			gzout.write(stringValue.getBytes());
	
			}catch(IOException e){ 
				if (logger.isDebugEnabled())
				{
					logger.debug(e.toString());
				}
			}finally{
				try {
					if(gzout!=null)
					  gzout.close();
				} catch (IOException e) {
				}
				try {
					if(fout!=null)
					fout.close();
				} catch (IOException e) {
				} 
			}
	}
}