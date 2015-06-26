
package com.cartmatic.estore.sales.service.impl;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.sales.AlsoBuy;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.sales.dao.AlsoBuyDao;
import com.cartmatic.estore.sales.service.AlsoBuyManager;

public class AlsoBuyManagerImpl extends GenericManagerImpl<AlsoBuy> implements
		AlsoBuyManager {
	private AlsoBuyDao	alsoBuyDao	= null;

	public void setAlsoBuyDao(AlsoBuyDao alsoBuyDao) {
		this.alsoBuyDao = alsoBuyDao;
	}

	protected void initManager() {
		dao = alsoBuyDao;
	}

	protected void onDelete(AlsoBuy entity) {

	}

	protected void onSave(AlsoBuy entity) {

	}

	public List<Product> getProductsBySourceId(final Integer categoryId,final Integer productId,
			final int firstResult, final int maxResults) {
		return alsoBuyDao.getProductsBySourceId(categoryId,productId, firstResult,
				maxResults);
	}
	
	public List<Product> getProductsBySourceIdList(final Integer categoryId,final List<Integer> productIds,	final int firstResult, final int maxResults){
		return alsoBuyDao.getProductsBySourceIdList(categoryId,productIds, firstResult,
				maxResults);
	}

	public AlsoBuy getAlsoBuyByProductIdByAlsoProductId(Integer productId,
			Integer alsoProductId) {
		return alsoBuyDao.getAlsoBuyByProductIdByAlsoProductId(productId,
				alsoProductId);
	}

	public int removeAlsoBuyProductsByProductId(final Integer productId) {
		return alsoBuyDao.removeAlsoBuyProductsByProductId(productId);
	}

	public void saveAlsoBuyProducts(List<Integer> productIds) {
		if (productIds.size() < 2) {
			return;
		}
		
		for(Integer productId : productIds){
			for(Integer alsoProductId: productIds){
				if(productId.equals(alsoProductId)){
					//自己不推荐自己
					continue;
				}
				AlsoBuy alsoBuy = alsoBuyDao.getAlsoBuyByProductIdByAlsoProductId(productId, alsoProductId);
				if (null == alsoBuy) {
					//不存在，创建一个新的AlsoBuy
					alsoBuy = new AlsoBuy();
					alsoBuy.setProductId(productId);
					alsoBuy.setAlsoProductId(alsoProductId);
					alsoBuy.setTimes(new Integer(1));
					alsoBuy.setCreateTime(new Date());
				} else {
					//已存在，次数加1
					int times = alsoBuy.getTimes().intValue();
					times++;
					alsoBuy.setTimes(new Integer(times));
					alsoBuy.setUpdateTime(new Date());
				}
				dao.save(alsoBuy);
			}
		}
	}
}
