
package com.cartmatic.estore.catalog.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.dao.ProductDao;
import com.cartmatic.estore.catalog.service.ProductCodeGenerator;
import com.cartmatic.estore.catalog.service.ProductManager;
import com.cartmatic.estore.common.model.catalog.Accessory;
import com.cartmatic.estore.common.model.catalog.AccessoryGroup;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.catalog.ProductSearchCriteria;
import com.cartmatic.estore.core.search.SearchCriteria;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;

/**
 * Manager implementation for Product, responsible for business processing, and
 * communicate between web and persistence layer.
 */
public class ProductManagerImpl extends GenericManagerImpl<Product> implements ProductManager {
	private ProductDao				productDao				= null;
	private ProductCodeGenerator productCodeGenerator=null;

	public void setProductCodeGenerator(ProductCodeGenerator productCodeGenerator) {
		this.productCodeGenerator = productCodeGenerator;
	}

	/**
	 * @param productDao
	 *            the productDao to set
	 */
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = productDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete(java.lang.Object)
	 */
	@Override
	protected void onDelete(Product entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave(java.lang.Object)
	 */
	@Override
	protected void onSave(Product entity) {
		autoSetProductCode(entity);
		autoSetPublishTime(entity);
	}
	
	private void autoSetProductCode(Product product) {
		if(product.getId()==null&&StringUtils.isBlank(product.getProductCode())){
			String code= productCodeGenerator.generateCode();
			Product tempProduct =getProductByProductCode(code);
			while (tempProduct!=null)
			{
				code= productCodeGenerator.generateCode();
				tempProduct = getProductByProductCode(code);
			}
			product.setProductCode(code);
		}
	}

	private void autoSetPublishTime(Product product){
		if(product.getId()==null&&product.getStatus()!=null&&product.getOrigStatus().intValue()==Constants.STATUS_ACTIVE.intValue()&&product.getStatus().intValue()==Constants.STATUS_ACTIVE.intValue()){
			product.setPublishTime(new Date());
		}else if(product.getStatus()!=null&&product.getOrigStatus().intValue()!=Constants.STATUS_ACTIVE.intValue()&&product.getStatus().intValue()==Constants.STATUS_ACTIVE.intValue()){
			product.setPublishTime(new Date());
		}
	}

	public Product getProductByProductCode(String productCode) {
		return productDao.findUniqueByProperty("productCode",productCode);
	}


	public Product getActiveProduct(Integer productId) {
		Product product = getById(productId);
		if (product != null) {
			if (product.getStatus().equals(Constants.STATUS_ACTIVE)) {
				return product;
			}
		}
		return null;
	}


	@Override
	public void delete(Product product){
		dao.delete(product);
	}
	

	public List<Product> findProductByCategoryIdForShow(SearchCriteria searchCriteria, Integer categoryId, String sorter) {
		return productDao.findProductByCategoryIdForShow(searchCriteria, categoryId, sorter);
	}

	public SearchCriteria getProductSearchCriteria(SearchCriteria searchCriteria,ProductSearchCriteria productSearchCriteria) {
		return productDao.getProductSearchCriteria(searchCriteria, productSearchCriteria);
	}

	public SearchCriteria getProductSearchCriteria4Front(SearchCriteria searchCriteria,ProductSearchCriteria productSearchCriteria) {
		//前台只显示激活的产品，添加默认条件
		productSearchCriteria.setProductStatus("1");
		return productDao.getProductSearchCriteria4Front(searchCriteria, productSearchCriteria);
	}

	public SearchCriteria getProductSkuSearchCriteria(SearchCriteria searchCriteria,ProductSearchCriteria productSearchCriteria) {
		return productDao.getProductSkuSearchCriteria(searchCriteria, productSearchCriteria);
	}

	public void updateStatusForPublish() {
		productDao.updateStatusForPublish();
	}
	
	public List<Product> getByIds(String[] ids){
		return productDao.getByIds(ids);
	}
	
	

	public void refresh(Object entity){
		productDao.refresh(entity);
	}

	public List<Map.Entry<AccessoryGroup, List<Accessory>>> getProductAccessoryMap(Integer productId) {
		Product product=getById(productId);
		Set<Accessory> accessorys=product.getAccessories();
		Map<AccessoryGroup, List<Accessory>>resultMap=new HashMap<AccessoryGroup, List<Accessory>>();
		for (Accessory accessory : accessorys) {
			List<Accessory> tempAccessorys=resultMap.get(accessory.getAccessoryGroup());
			if(tempAccessorys==null){
				tempAccessorys=new ArrayList<Accessory>();
				resultMap.put(accessory.getAccessoryGroup(), tempAccessorys);
			}
			tempAccessorys.add(accessory);
		}
		
		Comparator<Accessory> accessoryComparator=new Comparator<Accessory>(){
			public int compare(Accessory accessory1, Accessory accessory2) {
				if(accessory1.getSortOrder()!=null&&accessory2.getSortOrder()!=null){
					return accessory1.getSortOrder().intValue()-accessory2.getSortOrder();
				}else{
					if(accessory1.getSortOrder()==null&&accessory2.getSortOrder()!=null){
						return -1;
					}else if(accessory1.getSortOrder()!=null&&accessory2.getSortOrder()==null){
						return 1;
					}else{
						return 0;
					}
				}
			}
		};
		
		
		List<Map.Entry<AccessoryGroup, List<Accessory>>>resultList=new ArrayList<Map.Entry<AccessoryGroup,List<Accessory>>>();
		
		Set<Map.Entry<AccessoryGroup, List<Accessory>>>resultSet=resultMap.entrySet();
		for (Map.Entry<AccessoryGroup, List<Accessory>> entry : resultSet) {
			Collections.sort(entry.getValue(), accessoryComparator);
			resultList.add(entry);
		}
		

		Comparator<Map.Entry<AccessoryGroup, List<Accessory>>> accessoryGroupEntryComparator=new Comparator<Map.Entry<AccessoryGroup, List<Accessory>>>(){
			public int compare(Entry<AccessoryGroup, List<Accessory>> accessoryGroup1,
					Entry<AccessoryGroup, List<Accessory>> accessoryGroup2) {
				return accessoryGroup1.getKey().getGroupName().compareToIgnoreCase(accessoryGroup2.getKey().getGroupName());
			}
		};
		Collections.sort(resultList, accessoryGroupEntryComparator);
		return resultList;
	}
}
