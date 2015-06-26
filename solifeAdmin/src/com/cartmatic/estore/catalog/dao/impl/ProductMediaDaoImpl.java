package com.cartmatic.estore.catalog.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.catalog.dao.ProductMediaDao;
import com.cartmatic.estore.common.model.catalog.ProductMedia;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;

/**
 * Dao implementation for ProductMedia.
*/
public class ProductMediaDaoImpl extends HibernateGenericDaoImpl<ProductMedia> implements ProductMediaDao {

	@SuppressWarnings("unchecked")
	public List<ProductMedia> findProductMediaByProductIdAndType(Integer productId, Short...mediaType) {
		StringBuffer hql=new StringBuffer("from ProductMedia pm where pm.product.productId=? ");
		List<Object> objList=new ArrayList<Object>();
		objList.add(productId);
		if(mediaType.length>1){
			hql.append("and pm.mediaType in(");
			for (int i = 0; i < mediaType.length; i++) {
				if(i!=0)
					hql.append(",");
				hql.append("?");
				objList.add(mediaType[i]);
			}
			hql.append(")");
		}else{
			hql.append("and pm.mediaType=?");
			objList.add(mediaType[0]);
		}
		hql.append(" order by pm.sortOrder asc");
		List<ProductMedia> productMedias=findByHql(hql.toString(), objList.toArray());
		return productMedias;
	}

	@Override
	public boolean existImageReference(String imageUrl) {
		String hql = "from ProductMedia pm where pm.mediaUrl = ? and pm.product.status <>?";
		Long count=countByHql(hql, imageUrl,Constants.STATUS_DELETED);
		return count>0;
	}

}