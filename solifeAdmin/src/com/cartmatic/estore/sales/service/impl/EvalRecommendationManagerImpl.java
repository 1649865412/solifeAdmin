
package com.cartmatic.estore.sales.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.common.model.catalog.Product;
import com.cartmatic.estore.common.model.sales.RecommendedType;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.core.service.impl.GenericManagerImpl;
import com.cartmatic.estore.sales.service.AutoEvalRecommendationManager;
import com.cartmatic.estore.sales.service.EvalRecommendationManager;
import com.cartmatic.estore.sales.service.RecommendedProductManager;
import com.cartmatic.estore.sales.service.RecommendedTypeManager;

public class EvalRecommendationManagerImpl extends GenericManagerImpl<Product>
		implements EvalRecommendationManager {
	private RecommendedTypeManager			recommendedTypeManager;
	private AutoEvalRecommendationManager	autoEvalRecommendationManager;
	private RecommendedProductManager		recommendedProductManager;

	public RecommendedTypeManager getRecommendedTypeManager() {
		return recommendedTypeManager;
	}

	public void setRecommendedTypeManager(
			RecommendedTypeManager recommendedTypeManager) {
		this.recommendedTypeManager = recommendedTypeManager;
	}

	public AutoEvalRecommendationManager getAutoEvalRecommendationManager() {
		return autoEvalRecommendationManager;
	}

	public void setAutoEvalRecommendationManager(
			AutoEvalRecommendationManager autoEvalRecommendationManager) {
		this.autoEvalRecommendationManager = autoEvalRecommendationManager;
	}

	public RecommendedProductManager getRecommendedProductManager() {
		return recommendedProductManager;
	}

	public void setRecommendedProductManager(
			RecommendedProductManager recommendedProductManager) {
		this.recommendedProductManager = recommendedProductManager;
	}

	protected void initManager() {
	}

	protected void onDelete(Product entity) {
	}

	protected void onSave(Product entity) {
	}

	/**
	 * 手动+自动评估 根据[推荐类型Id][源Id]获得特定范围的推荐产品(不能重复,而且必须是激活的)
	 * 其实与getProductsByRecommendedTypeNameByProductId一样,但为了方便cache所以分开两个方法.
	 * @param typeName 推荐类型Code
	 * @param catalogId 目录ID
	 * @param first
	 * @param max
	 * @return
	 */
	public List<Product> getProductsByRecommendedTypeNameByCategoryId(final Store store,final String typeName, 
			final Integer catalogId,
			final int first, 
			final int max) 
	{
		return getProductsByRecommendedTypeNameBySourceId(store,typeName, catalogId, first, max); 
	}
	
	/**
	 * 手动+自动评估 根据[推荐类型Id][源Id]获得特定范围的推荐产品(不能重复,而且必须是激活的)
	 * @param typeName 推荐类型Code
	 * @param catalogId 目录ID
	 * @param first
	 * @param max
	 * @return
	 */
	public List<Product> getProductsByRecommendedTypeNameByProductId(final Store store,final String typeName, 
			final Integer productId,
			final int first, 
			final int max) 
	{
		return getProductsByRecommendedTypeNameBySourceId(store,typeName, productId, first, max); 
	}
	// 手动+自动评估 根据[推荐类型Id][源Id]获得特定范围的推荐产品(不能重复,而且必须是激活的)
	private List<Product> getProductsByRecommendedTypeNameBySourceId(final Store store,final String recommendedTypeName, 
			final Integer sourceId,
			final int firstResult, 
			final int maxResults) {
		RecommendedType recommendedType = recommendedTypeManager.getRecommendedTypeByName(recommendedTypeName);
		if (null == recommendedType)
			return new ArrayList<Product>();
		if (Constants.FLAG_TRUE.equals(recommendedType.getStatus())) {
			// 获得手动评估的产品
			List<Product> manulEvalProductsList = recommendedProductManager.getProductsByRecommendedTypeName(recommendedType, store, sourceId, firstResult, maxResults);
			if (maxResults == manulEvalProductsList.size()) {
				// 数量已经满足
				return manulEvalProductsList;
			} else {
				// 数量还不够
				if (RecommendedType.AUTOEVAL_YES.equals(recommendedType.getAutoEval())) {
					// 如果开启自动评估功能
					List<Product> autoEvalProductsList = autoEvalRecommendationManager.getProductsByRuleCodeBySourceId(store,recommendedType.getRuleCode(), sourceId, firstResult,
									maxResults);
					return mergeList(manulEvalProductsList,
							autoEvalProductsList, maxResults);
				} else {
					return manulEvalProductsList;
				}
			}
		} else {
			return new ArrayList<Product>();
		}
	}

	// 手动+自动评估 根据[推荐类型Id][源Id的list]获得特定范围的推荐产品(不能重复,而且必须是激活的)
	public List<Product> getProductsByRecommendedTypeNameBySourceIdList(final Store store,
			final String recommendedTypeName, final List<Integer> sourceIds,
			final int firstResult, final int maxResults) {

		RecommendedType recommendedType = recommendedTypeManager
				.getRecommendedTypeByName(recommendedTypeName);
		if (null == recommendedType)
			return new ArrayList<Product>();
		if (Constants.FLAG_TRUE.equals(recommendedType.getStatus())) {
			List<Product> list;
			// 获得手动评估的产品
			List<Product> manulEvalProductsList = recommendedProductManager.getProductsByRecommendedTypeName(recommendedType, store, sourceIds, firstResult, maxResults);
			list = manulEvalProductsList;
			// 清除list上的选定元素
			list = excludeList(list, sourceIds);

			if (maxResults == list.size()) {
				// 数量已经满足

			} else {
				// 数量还不够
				if (RecommendedType.AUTOEVAL_YES.equals(recommendedType.getAutoEval())) {
					// 如果开启自动评估功能
					List<Product> autoEvalProductsList = autoEvalRecommendationManager
							.getProductsByRuleCodeBySourceIdList(store,
									recommendedType.getRuleCode(), sourceIds,
									firstResult, maxResults);
					// 清除list上的选定元素
					autoEvalProductsList = excludeList(autoEvalProductsList,
							sourceIds);

					list = mergeList(list, autoEvalProductsList, maxResults);

				}
			}

			return list;
		} else {
			return new ArrayList<Product>();
		}
	}

	/**
	 * 将输入参数的两个list合并成一个大小为maxResult的新list
	 * 
	 * @param manulEvalProductsList
	 * @param autoEvalProductsList
	 * @param maxResults
	 * @return List<Product>
	 */
	private List<Product> mergeList(List<Product> manulEvalProductsList,
			List<Product> autoEvalProductsList, int maxResults) {
		List<Product> list = new ArrayList<Product>();
		list.addAll(manulEvalProductsList);
		for (int i = 0; i < autoEvalProductsList.size(); i++) {
			Product p = (Product) autoEvalProductsList.get(i);
			boolean add = true;
			for (int j = 0; j < list.size(); j++) {
				Product rp = (Product) list.get(j);
				if (p.getProductId().equals(rp.getProductId())) {
					add = false;
					break;
				}
			}
			if (add) {
				list.add(p);
				if (list.size() == maxResults) {
					break;
				}
			}
		}
		return list;
	}

	private List<Product> excludeList(List<Product> _list,
			List<Integer> sourceIds) {
		List<Product> list = _list;
		int i = 0;
		boolean noRemove = true;
		while (i < list.size()) {
			Product product = (Product) list.get(i);
			for (Integer sourceId : sourceIds) {
				if (product.getProductId().equals(sourceId)) {
					list.remove(i);
					noRemove = false;
					break;
				}
			}
			if (noRemove) {
				i++;
			} else {
				i = 0;
				noRemove = true;
			}
		}

		return list;
	}

}
