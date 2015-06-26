package com.cartmatic.estore.catalog.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.dao.ProductDao;
import com.cartmatic.estore.catalog.util.SearchUtil;
import com.cartmatic.estore.common.helper.CatalogHelper;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSearchCriteria;
import com.cartmatic.estore.common.model.catalog.SearchAttributeItem;
import com.cartmatic.estore.common.model.catalog.SearchAttributeModel;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.core.search.SearchCriteria;

/**
 * Dao implementation for Product.
*/
public class ProductDaoImpl extends HibernateGenericDaoImpl<Product> implements ProductDao {

	public SearchCriteria getProductSearchCriteria4Front(SearchCriteria searchCriteria,ProductSearchCriteria productSearchCriteria){
		String orderClause=CatalogHelper.getInstance().convertSortOrder(productSearchCriteria.getSort());
		return searchProducts(searchCriteria, productSearchCriteria,"p","",orderClause);
	}
	
	
	public SearchCriteria getProductSearchCriteria(SearchCriteria searchCriteria,ProductSearchCriteria productSearchCriteria){
		return searchProducts(searchCriteria, productSearchCriteria,"p","","p.productId desc");
	}
	
	public SearchCriteria getProductSkuSearchCriteria(SearchCriteria searchCriteria,ProductSearchCriteria productSearchCriteria){
		return searchProducts(searchCriteria, productSearchCriteria,"ps","","ps.productSkuId desc");
	}
	
	/**
	 * @param searchCriteria新款框架搜索条件及分页对象
	 * @param productSearchCriteria 产品搜索条件
	 * @param defaultSelectObject 搜索对象别名
	 * @param defaultWhereClause 默认的搜索条件
	 * @param orderBy 排序
	 * @return
	 */
	private SearchCriteria searchProducts(SearchCriteria searchCriteria,ProductSearchCriteria productSearchCriteria,String defaultSelectObject,String defaultWhereClause,String orderBy){
		boolean requireDistinct=false;
		String selectObject="";
		String fromClause="";
		String whereClause="";
		if(StringUtils.isNotEmpty(defaultSelectObject)){
			selectObject+=defaultSelectObject;
		}
		if(StringUtils.isNotEmpty(defaultWhereClause)){
			whereClause+=defaultWhereClause;
		}
		if(selectObject.equals("p")){
			fromClause=getFromClause(fromClause,"Product p");
		}else if(selectObject.equals("ps")){
			fromClause=getFromClause(fromClause,"ProductSku ps");
		}
		List<Object> paramList=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(productSearchCriteria.getName())){
			fromClause=getFromClause(fromClause,"Product p");
			String subClause="LOWER(p.productName) like ?";
			paramList.add("%"+productSearchCriteria.getName().toLowerCase()+"%");
			
			if(productSearchCriteria.isSearchNameInSkuCode()){
				String subClause2="LOWER(ps.productSkuCode)=?";
				fromClause=getFromClause(fromClause,"ProductSku ps");
				paramList.add(productSearchCriteria.getName().toLowerCase());
				subClause=getOrClause(subClause, subClause2);
				requireDistinct=true;
			}
			
			whereClause=getAndClause(whereClause, "("+subClause+")");
			
			
			/*
			whereClause=getAndClause(whereClause, "LOWER(p.productName) like ?");
			paramList.add("%"+productSearchCriteria.getName().toLowerCase()+"%");*/
		}
		if(StringUtils.isNotEmpty(productSearchCriteria.getProductCode())){
			fromClause=getFromClause(fromClause,"Product p");
			whereClause=getAndClause(whereClause, "LOWER(p.productCode) like ?");
			paramList.add("%"+productSearchCriteria.getProductCode().toLowerCase()+"%");
		}
		if(productSearchCriteria.getProductTypeId()!=null){
			fromClause=getFromClause(fromClause,"Product p");
			whereClause=getAndClause(whereClause, "p.productType.productTypeId = ?");
			paramList.add(productSearchCriteria.getProductTypeId());
		}
		if(productSearchCriteria.getBrandId()!=null){
			fromClause=getFromClause(fromClause,"Product p");
			whereClause=getAndClause(whereClause, "p.brand.brandId = ?");
			paramList.add(productSearchCriteria.getBrandId());
		}
		if(productSearchCriteria.getSupplierId()!=null){
			fromClause=getFromClause(fromClause,"Product p");
			whereClause=getAndClause(whereClause, "p.supplier.supplierId = ?");
			paramList.add(productSearchCriteria.getSupplierId());
		}
		if(StringUtils.isNotEmpty(productSearchCriteria.getProductStatus())){
			fromClause=getFromClause(fromClause,"Product p");
			Short status[]=(Short[])ConvertUtils.convert(productSearchCriteria.getProductStatus().split(","), Short.class);
			String subClause=convertIsOrIn("p.status", status, paramList);
			whereClause=getAndClause(whereClause,subClause);
		}
		if(StringUtils.isNotEmpty(productSearchCriteria.getAvailabilityRule())){
			fromClause=getFromClause(fromClause,"Product p");
			whereClause=getAndClause(whereClause, "p.availabilityRule = ?");
			paramList.add(new Short(productSearchCriteria.getAvailabilityRule()));
		}
		//不包含某产品状态
		if(StringUtils.isNotEmpty(productSearchCriteria.getExcludeProductStatus())){
			fromClause=getFromClause(fromClause,"Product p");
			Short status[]=(Short[])ConvertUtils.convert(productSearchCriteria.getExcludeProductStatus().split(","), Short.class);
			String subClause=convertNotOrNotIn("p.status", status, paramList);
			whereClause=getAndClause(whereClause,subClause);
		}
		if(StringUtils.isNotEmpty(productSearchCriteria.getProductKind())){
			fromClause=getFromClause(fromClause,"Product p");
			Short productKind[]=(Short[])ConvertUtils.convert(productSearchCriteria.getProductKind().split(","), Short.class);
			String subClause=convertIsOrIn("p.productKind", productKind, paramList);
			whereClause=getAndClause(whereClause,subClause);
		}
		if(productSearchCriteria.getSkuStatus()!=null){
			fromClause=getFromClause(fromClause,"ProductSku ps");
			whereClause=getAndClause(whereClause, "ps.status = ?");
			paramList.add(productSearchCriteria.getSkuStatus());
		}
		if(productSearchCriteria.getFromPrice()!=null){
			fromClause=getFromClause(fromClause,"ProductSku ps");
			whereClause=getAndClause(whereClause, "ifnull(p.defaultProductSku.salePrice,p.defaultProductSku.price) >= ?");
			paramList.add(productSearchCriteria.getFromPrice());
			//产品，sku一对多会产生重复
			requireDistinct=true;
		}
		if(productSearchCriteria.getToPrice()!=null){
			fromClause=getFromClause(fromClause,"ProductSku ps");
			whereClause=getAndClause(whereClause, "ifnull(p.defaultProductSku.salePrice,p.defaultProductSku.price) <= ?");
			paramList.add(productSearchCriteria.getToPrice());
			//产品，sku一对多会产生重复
			requireDistinct=true;
		}
	
		if(productSearchCriteria.getCategoryId()!=null){
			fromClause=getFromClause(fromClause,"ProductCategory pc");
			whereClause=getAndClause(whereClause, "pc.category.categoryId=?");
			paramList.add(productSearchCriteria.getCategoryId());
			requireDistinct=true;
		}
		
		if(StringUtils.isNotEmpty(productSearchCriteria.getCategoryPath())&&!productSearchCriteria.getCategoryPath().equals(Constants.ROOT_CATEGORY_CATALOG.toString())){
			fromClause=getFromClause(fromClause,"ProductCategory pc");
			whereClause=getAndClause(whereClause, "pc.categoryPath like ?");
			paramList.add(productSearchCriteria.getCategoryPath()+"%");
			//因为包含父目录时会导致数据重复（即点击父目录时，子目录的产品也要查找出来时，会导致产品重复）
			requireDistinct=true;
		}
		
		if(productSearchCriteria.getCatalogId()!=null){
			fromClause=getFromClause(fromClause,"ProductCategory pc");
			whereClause=getAndClause(whereClause, "pc.category.catalog.catalogId=?");
			paramList.add(productSearchCriteria.getCatalogId());
			requireDistinct=true;
		}
		
		if(productSearchCriteria.getVirtual()!=null&&productSearchCriteria.getVirtual().intValue()!=0){
			fromClause=getFromClause(fromClause,"ProductCategory pc");
			whereClause=getAndClause(whereClause, "pc.category.catalog.isVirtual=?");
			paramList.add(productSearchCriteria.getVirtual().intValue()==1?Constants.FLAG_TRUE:Constants.FLAG_FALSE);
			requireDistinct=true;
		}
		
		if(StringUtils.isNotEmpty(productSearchCriteria.getSkuCode())){
			fromClause=getFromClause(fromClause,"ProductSku ps");
			whereClause=getAndClause(whereClause, "LOWER(ps.productSkuCode) like ?");
			//skucode为模糊查询时，需去除重复
			requireDistinct=true;
			paramList.add("%"+productSearchCriteria.getSkuCode().toLowerCase()+"%");
		}
		
		if(fromClause.indexOf(",")!=-1){
			if(fromClause.indexOf("ProductSku ps")!=-1&&fromClause.indexOf("Product p")!=-1){
				whereClause=getAndClause(whereClause, "p.productId=ps.product.productId");
			}
			if(fromClause.indexOf("ProductCategory pc")!=-1&&fromClause.indexOf("Product p")!=-1){
				whereClause=getAndClause(whereClause, "p.productId=pc.product.productId");
				requireDistinct=true;
			}
		}
		//涉及产品统计的，要加上ProductStat
		if(StringUtils.isNotEmpty(orderBy)&&orderBy.indexOf("pstat.")!=-1){
			whereClause=getAndClause(whereClause, "p.productId=pstat.product.productId");
			fromClause=getFromClause(fromClause,"ProductStat pstat");
		}
		
		//普通自定义属性（目前同一属性的，只会or 关联）
		List<SearchAttributeModel> searchAttributeList=productSearchCriteria.getSearchAttributeList();
		if(searchAttributeList!=null&&searchAttributeList.size()>0){
			StringBuffer attributeQuery=new StringBuffer();
			for (int i = 0; i < searchAttributeList.size(); i++) {
				SearchAttributeModel searchAttributeModel=searchAttributeList.get(i);
				if(i==0){
					attributeQuery.append("pav.attribute.attributeId=? and (");
					paramList.add(searchAttributeModel.getId());
					List<SearchAttributeItem> searchAttributeItemList=searchAttributeModel.getAttributes();
					for (int j = 0; j < searchAttributeItemList.size(); j++) {
						SearchAttributeItem searchAttributeItem=searchAttributeItemList.get(j);
						String attributeName=SearchUtil.getAttributeNameByDataType(searchAttributeItem.getDataType());
						attributeQuery.append("pav.");
						attributeQuery.append(attributeName);
						String opertorAndValue[]=SearchUtil.getOperatorAndValue(searchAttributeItem.getOperator(), searchAttributeItem.getValue());
						attributeQuery.append(opertorAndValue[0]);
						paramList.add(opertorAndValue[1]);
						if(j<searchAttributeItemList.size()-1)
							attributeQuery.append(" or ");
					}
					attributeQuery.append(")");
					if(searchAttributeList.size()>0){
						whereClause=getAndClause(whereClause, "pav.product.productId=p.productId");
						fromClause=getFromClause(fromClause,"ProductAttrValue pav");
					}
				}else{
					//第二个属性起，用in处理
					if(i==1)
						attributeQuery.append(" and p.productId in(select pav.product.productId from ProductAttrValue pav where ");
					attributeQuery.append("(pav.attribute.attributeId=? and (");
					paramList.add(searchAttributeModel.getId());
					List<SearchAttributeItem> searchAttributeItemList=searchAttributeModel.getAttributes();
					for (int j = 0; j < searchAttributeItemList.size(); j++) {
						SearchAttributeItem searchAttributeItem=searchAttributeItemList.get(j);
						String attributeName=SearchUtil.getAttributeNameByDataType(searchAttributeItem.getDataType());
						attributeQuery.append("pav.");
						attributeQuery.append(attributeName);
						String opertorAndValue[]=SearchUtil.getOperatorAndValue(searchAttributeItem.getOperator(), searchAttributeItem.getValue());
						attributeQuery.append(opertorAndValue[0]);
						paramList.add(opertorAndValue[1]);
						if(j<searchAttributeItemList.size()-1)
							attributeQuery.append(" or ");
					}
					attributeQuery.append(")");
					attributeQuery.append(")");
					if(i<searchAttributeList.size()-1)
						attributeQuery.append(" or ");
					else
						attributeQuery.append(")");
				}
			}
			whereClause=getAndClause(whereClause, attributeQuery.toString());
		}
		
		
		
		
		String hql="select ";
		if(requireDistinct){
			hql+=" distinct ";
		}
		hql+=selectObject+" from "+fromClause;
		if(StringUtils.isNotEmpty(whereClause)){
			hql+=" where "+whereClause;
		}
		if(StringUtils.isNotEmpty(orderBy)){
			hql+=" order by "+orderBy;
		}
		searchCriteria.setHql(hql);
		for (Object object : paramList) {
			searchCriteria.addParamValue(object);
		}
		
		return searchCriteria;
	}
	
	
	private String getFromClause(String fromClause, String table) {
		if(StringUtils.isEmpty(fromClause)){
			return table;
		}else if (StringUtils.isEmpty(table)) {
			return fromClause;
		}else if (fromClause.toLowerCase().indexOf(table.toLowerCase()) != -1) {
			return fromClause;
		} else {
			return fromClause + "," + table;
		}
	}
	

	private String getAndClause(String oldPattern, String newPattern) {
		if(StringUtils.isEmpty(newPattern)){
			return oldPattern;
		}else if (StringUtils.isEmpty(oldPattern)) {
			return newPattern;
		} else {
			String temp=oldPattern;
			temp=temp.trim().toLowerCase();
			temp=temp.substring(temp.length()-3);
			if(!temp.equals("and")){
				oldPattern+=" and ";
			}
			return oldPattern + newPattern;
		}
	}
	
	
	private String getOrClause(String oldPattern, String newPattern) {
		if (StringUtils.isEmpty(newPattern)) {
			return oldPattern;
		} else if (StringUtils.isEmpty(oldPattern)) {
			return newPattern;
		} else {
			String temp = oldPattern;
			temp = temp.trim().toLowerCase();
			temp = temp.substring(temp.length() - 2);
			if (!temp.equals("or")) {
				oldPattern += " or ";
			}
			return oldPattern + newPattern;
		}

	}
	
	


	@SuppressWarnings("unchecked")
	public List<Product> findProductByCategoryIdForShow(SearchCriteria searchCriteria, Integer categoryId, String sorter) {
		String selectObject="pc.product ";
		String fromClause="ProductCategory pc ";
		String whereClause="pc.category.categoryId=? and pc.product.status=1 and pc.category.status=1 ";
		String orderBy="";
		if(StringUtils.isNotEmpty(sorter)){
			orderBy=CatalogHelper.getInstance().convertSortOrder(sorter);
			orderBy=orderBy.replace("p.", "pc.product.");
			if(StringUtils.isNotEmpty(orderBy)&&orderBy.indexOf("pstat.")!=-1){
				whereClause=getAndClause(whereClause, "pc.product.productId=pstat.product.productId");
				fromClause=getFromClause(fromClause,"ProductStat pstat");
			}
			orderBy=" order by "+orderBy;
		}
		StringBuffer hql=new StringBuffer("select ");
		hql.append(selectObject);
		hql.append(" from ");
		hql.append(fromClause);
		hql.append(" where ");
		hql.append(whereClause);
		hql.append(orderBy);
		searchCriteria.setHql(hql.toString());
		searchCriteria.addParamValue(categoryId);
		List<Product> result=searchByCriteria(searchCriteria);
		return result;
	}
	
	
	public String convertIsOrIn(String subClause, Object[] objs,List<Object> paramList) {
		StringBuffer bf = new StringBuffer(subClause);
		if (objs.length == 1) {
			bf.append(" = ?");
			paramList.add(objs[0]);
		} else {
			bf.append(" in(");
			for (int i = 0; i < objs.length; i++) {
				paramList.add(objs[i]);
				bf.append("?");
				if (i < objs.length - 1)
					bf.append(",");
			}
			bf.append(")");
		}
		return bf.toString();
	}
	
	public String convertNotOrNotIn(String subClause, Object[] objs,List<Object> paramList) {
		StringBuffer bf = new StringBuffer(subClause);
		if (objs.length == 1) {
			bf.append(" <> ?");
			paramList.add(objs[0]);
		} else {
			bf.append(" not in(");
			for (int i = 0; i < objs.length; i++) {
				paramList.add(objs[i]);
				bf.append("?");
				if (i < objs.length - 1)
					bf.append(",");
			}
			bf.append(")");
		}
		return bf.toString();
	}


	public void updateStatusForPublish() {
		
		//对产品进行激活上架
		StringBuffer activeSql = new StringBuffer("update Product a set a.status=");
		activeSql.append(String.valueOf(Constants.STATUS_ACTIVE));
		activeSql.append(",a.publishTime=? ");
		activeSql.append("where a.status=");
		activeSql.append(String.valueOf(Constants.STATUS_INACTIVE));
		activeSql.append(" and a.planStartTime<=? and (a.planEndTime is null or a.planEndTime>?)");
		
		//对产品进行非激活下架
		StringBuffer inactiveSql =new StringBuffer("update Product a set a.status="); 
		inactiveSql.append(String.valueOf(Constants.STATUS_INACTIVE));
		inactiveSql.append(" where a.status=");
		inactiveSql.append(String.valueOf(Constants.STATUS_ACTIVE));
		inactiveSql.append(" and (a.planEndTime<=? or a.planStartTime>?)");
		
		//进行激活上架的产品Ids
		StringBuffer activeProductIdsSql = new StringBuffer("select a.productId from Product a ");
		activeProductIdsSql.append("where a.status=");
		activeProductIdsSql.append(String.valueOf(Constants.STATUS_INACTIVE));
		activeProductIdsSql.append(" and a.planStartTime<=? and (a.planEndTime is null or a.planEndTime>?)");
		//进行非激活下架的产品Ids
		StringBuffer inactiveProductIdsSql =new StringBuffer("select a.productId from Product a "); 
		inactiveProductIdsSql.append(" where a.status=");
		inactiveProductIdsSql.append(String.valueOf(Constants.STATUS_ACTIVE));
		inactiveProductIdsSql.append(" and (a.planEndTime<=? or a.planStartTime>?)");
		
		Date now = new Date();
		
		List<Integer>activeProductIds=findByHql(activeProductIdsSql.toString(),new Object[]{now,now});
		List<Integer>inactiveProductIds=findByHql(inactiveProductIdsSql.toString(),new Object[]{now,now});
		
		
		Query activeQuery =getSession().createQuery(activeSql.toString());
		activeQuery.setDate(0, now);
		activeQuery.setDate(1, now);
		activeQuery.setDate(2, now);
		int activeCount=activeQuery.executeUpdate();
		if(logger.isInfoEnabled()){
			logger.info("自动上架产品数量："+activeCount);
		}
		Query inactiveQuery = getSession().createQuery(inactiveSql.toString());
		inactiveQuery.setTimestamp(0, now);
		inactiveQuery.setDate(1, now);
		int inactiveCount=inactiveQuery.executeUpdate();
		if(logger.isInfoEnabled()){
			logger.info("自动下架产品数量："+inactiveCount);
		}
		
		//更新索引
		activeProductIds.addAll(inactiveProductIds);
		if(inactiveProductIds.size()>0)
			CatalogHelper.getInstance().indexNotifyUpdateEvent(activeProductIds.toArray(new Integer[]{}));
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Product> getByIds(final String[] ids){
		if(ids==null||ids.length==0)
			return new ArrayList<Product>();
		StringBuffer sb=new StringBuffer();
		sb.append("from Product p where ");
		for (int i = 0; i < ids.length; i++) {
			sb.append("p.productId=");
			sb.append(ids[i]);
			if(i<ids.length-1){
				sb.append(" or ");
			}
		}
		return findByHql(sb.toString());
	}
	
	
	/*hqm*/
	public void refresh(Object entity){
		getHibernateTemplate().refresh(entity);
	}


	@Override
	public String getMaxAutoCode(String sample) {
		List<String> codes=find("select p.productCode from Product p where p.productCode like ? order by p.productId desc", 0, 1, sample+"%");
		if(codes!=null&&codes.size()>0){
			return codes.get(0);
		}
		return null;
	}
	
}
