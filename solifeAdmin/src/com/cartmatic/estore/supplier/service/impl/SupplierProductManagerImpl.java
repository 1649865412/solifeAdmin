package com.cartmatic.estore.supplier.service.impl;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.supplier.Supplier;
import com.cartmatic.estore.common.model.supplier.SupplierProduct;
import com.cartmatic.estore.common.util.DateUtil;
import com.cartmatic.estore.common.util.NumberUtil;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.customer.service.CustomerManager;
import com.cartmatic.estore.supplier.dao.SupplierProductDao;
import com.cartmatic.estore.supplier.service.SupplierProductManager;
import com.cartmatic.estore.webapp.util.RequestContext;


/**
 * Manager implementation for SupplierProduct, responsible for business processing, and communicate between web and persistence layer.
 */
public class SupplierProductManagerImpl extends GenericManagerImpl<SupplierProduct> implements SupplierProductManager {
	private CustomerManager customerManager=null;
	
	public void setCustomerManager(CustomerManager customerManager) {
		this.customerManager = customerManager;
	}

	private SupplierProductDao supplierProductDao = null;

	/**
	 * @param supplierProductDao
	 *            the supplierProductDao to set
	 */
	public void setSupplierProductDao(SupplierProductDao supplierProductDao) {
		this.supplierProductDao = supplierProductDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#init()
	 */
	@Override
	protected void initManager() {
		dao = supplierProductDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onDelete()
	 */
	@Override
	protected void onDelete(SupplierProduct entity) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.service.impl.GenericManagerImpl#onSave()
	 */
	@Override
	protected void onSave(SupplierProduct entity) {

	}

	public List<SupplierProduct> findSupplierProductByProductId(Integer productId) {
		List<SupplierProduct> supplierProductList=supplierProductDao.findByProperty("product.productId", productId);
		return supplierProductList;
	}

	public List<Supplier> findSuppliersByProductId(Integer productId) {
		return supplierProductDao.findSuppliersByProductId(productId);
	}

	public void deleteProductToSetNull(Integer productId) {
		List<SupplierProduct> supplierProductList=findSupplierProductByProductId(productId);
		for (SupplierProduct supplierProduct : supplierProductList) {
			supplierProduct.setProduct(null);
			save(supplierProduct);
		}
	}

	@Override
	protected boolean hasRight(SupplierProduct entity) {
		if(ContextUtil.isStoreFront()){
			Customer currentCustomer=customerManager.getById(RequestContext.getCurrentUserId());
			Supplier supplier=currentCustomer.getSupplier();
			if(supplier==null)return false;
			return entity.getSupplierId().intValue()==supplier.getSupplierId();
		}else{
			return true;
		}
	}

	@Override
	public SupplierProduct getSupplierProductBySupplierIdAndProductCode(Integer supplierId, String productCode) {
		return supplierProductDao.getSupplierProductBySupplierIdAndProductCode(supplierId, productCode);
	}

	private Object [] getSupplierProductData4Compare(Integer supplierProductId) {
		return supplierProductDao.getSupplierProductData4Compare(supplierProductId);
	}
	
	public void setUploadLogs(SupplierProduct supplierProduct){
		JSONArray updateLogs=null;
		if(StringUtils.isBlank(supplierProduct.getUpdateLogs())||!supplierProduct.getUpdateLogs().startsWith("[")){
			updateLogs=new JSONArray();
		}else{
			updateLogs=JSONArray.fromObject(supplierProduct.getUpdateLogs());
		}
		JSONArray lastUpdateLogs=new JSONArray();
		Object []supplierProductData=getSupplierProductData4Compare(supplierProduct.getId());
		if(((String)supplierProductData[0]).trim().compareTo(supplierProduct.getProductName().trim())!=0){
			lastUpdateLogs.add("产品名");
		}
		if(((String)supplierProductData[1]).trim().compareTo(supplierProduct.getProductCode().trim())!=0){
			lastUpdateLogs.add("产品编码");
		}
		if(((String)supplierProductData[2]).trim().compareTo(supplierProduct.getWholesalePrice().trim())!=0){
			lastUpdateLogs.add("产品价格");
		}
		if(supplierProductData[3]!=null&&supplierProduct.getMediaUrl()!=null&&((String)supplierProductData[3]).trim().compareTo(supplierProduct.getMediaUrl().trim())!=0){
			lastUpdateLogs.add("产品图片");
		}
		if(supplierProductData[4]!=null&&supplierProduct.getTbSellCatProps()!=null&&((String)supplierProductData[4]).trim().compareTo(supplierProduct.getTbSellCatProps().trim())!=0){
			lastUpdateLogs.add("附件属性");
		}
		if(supplierProductData[5]!=null&&supplierProduct.getProductDesc()!=null&&((String)supplierProductData[5]).trim().compareTo(supplierProduct.getProductDesc().trim())!=0){
			lastUpdateLogs.add("产品描述");
		}
		if(supplierProductData[6]!=null&&supplierProduct.getTbCId()!=null&&NumberUtil.longCompare((Long)supplierProductData[6], supplierProduct.getTbCId())!=0){
			lastUpdateLogs.add("Taobao目录");
		}
		if((supplierProductData[7]==null&&supplierProduct.getCategoryId()!=null)
				||(supplierProductData[7]!=null&&supplierProduct.getCategoryId()==null)
				||(supplierProductData[7]!=null&&supplierProduct.getCategoryId()!=null&&((Integer)supplierProductData[7]).compareTo(supplierProduct.getCategoryId())!=0)){
			lastUpdateLogs.add("产品目录");
		}
		
		if(lastUpdateLogs.size()>0){
			lastUpdateLogs.add(0,DateUtil.getNowStr());
			updateLogs.add(0, lastUpdateLogs);
		}
		if(updateLogs.size()>10){
			updateLogs=(JSONArray) updateLogs.subList(0, 9);
		}
		supplierProduct.setUpdateLogs(updateLogs.toString());
	}

}
