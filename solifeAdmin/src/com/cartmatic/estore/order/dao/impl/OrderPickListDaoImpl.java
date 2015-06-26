package com.cartmatic.estore.order.dao.impl;

import java.util.Date;
import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.order.OrderPickList;
import com.cartmatic.estore.core.dao.impl.HibernateGenericDaoImpl;
import com.cartmatic.estore.order.dao.OrderPickListDao;

/**
 * Dao implementation for OrderPickList.
*/
public class OrderPickListDaoImpl extends HibernateGenericDaoImpl<OrderPickList> implements OrderPickListDao {
	public List<OrderPickList> getActivePickLists(Integer createdBy){
		String hql = "from OrderPickList opl where opl.createBy=? and opl.isActive=? order by opl.orderPickListId";
		return this.findByHql(hql, createdBy, Constants.FLAG_TRUE);
	}
	
	public List<OrderPickList> getInActivePickLists(Integer fetchSize, Integer createdBy){
		String hql = "from OrderPickList opl where opl.createBy=? and opl.isActive!=? order by opl.updateTime desc";
		return this.find(hql, 0, fetchSize, new Object[]{createdBy, Constants.FLAG_TRUE} );
	}
}
