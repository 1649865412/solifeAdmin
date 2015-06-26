package com.cartmatic.estore.order.service;

import java.io.Serializable;
import java.util.Map;

import com.cartmatic.estore.common.model.order.OrderReturn;
import com.cartmatic.estore.common.model.system.AppUser;
import com.cartmatic.estore.core.service.GenericManager;
import com.cartmatic.estore.exception.OutOfStockException;

/**
 * Manager interface for OrderReturn, responsible for business processing, and communicate between web and persistence layer.
 *
 */
public interface OrderReturnManager extends GenericManager<OrderReturn> {
	/**
	 * 创建退货
	 * @param params 页面表单参数
	 * @param curUser
	 * @return 1 成功
	 * 		   0 参数异常	
	 * 		  -1 业务异常
	 */
	public int createReturn(Map params, AppUser curUser);
	
	/**
	 * 编辑退货
	 * @param params 页面表单参数
	 * @param curUser
	 * @return 1 成功
	 * 		   0 参数异常	
	 * 		  -1 业务异常 编辑必须在未收到原货之前
	 */
	public int editReturn(Map params, AppUser curUser);
	
	/**
	 * 取消退换货
	 * @param orderReturnId
	 * @param curUser
	 * @return 1 成功
	 * 		   0 参数异常	
	 * 		  -1 业务异常
	 */
	public int doCancelReturn(Serializable orderReturnId, AppUser curUser);
	
	/**
	 * 完成退换货
	 * @param params 页面表单参数
	 * @param curUser
	 * @return 1 成功
	 * 		   0 参数异常	
	 * 		  -1 业务异常
	 */
	public int doCompleteReturn(Map params, AppUser curUser);
	
	/**
	 * 收到顾客退回的商品
	 * @param params
	 * @param curUser
	 * @return 1 成功
	 * 		   0 参数异常	
	 * 		  -1 业务异常
	 */
	public int doReceiveReturn(Map params, AppUser curUser);
	
	/**
	 * 创建换货，先退货后生成换货订单
	 * @param params 页面表单参数
	 * @param curUser
	 * @param ipAddressFromCurUser
	 * @return 1 成功
	 * 		   0 参数异常	
	 * 		  -1 业务异常
	 */
	public int createExchange(Map params, AppUser curUser, String ipAddressFromCurUser) throws OutOfStockException;
}
