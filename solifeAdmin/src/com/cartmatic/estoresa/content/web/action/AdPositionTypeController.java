package com.cartmatic.estoresa.content.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;

import com.cartmatic.estore.core.controller.GenericController;
import com.cartmatic.estore.common.model.content.AdPositionType;
import com.cartmatic.estore.common.model.system.Store;
import com.cartmatic.estore.content.service.AdPositionTypeManager;
import com.cartmatic.estore.system.service.StoreManager;

public class AdPositionTypeController extends GenericController<AdPositionType> {
    private AdPositionTypeManager adPositionTypeManager = null;
    private StoreManager storeManager=null;
    public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}

	public void setAdPositionTypeManager(AdPositionTypeManager inMgr) {
        this.adPositionTypeManager = inMgr;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController getEntityName(java.lang.Object)
	 */
	@Override
	protected String getEntityName(AdPositionType entity) {
		return entity.getAdPositionTypeName();
	}

	/**
	 * 构造批量更新所需的model。
	 * <P>
	 * 本来这方法对大部分情况也是可以自动分析和转换的，但考虑到比较复杂和难以灵活（验证、缺省值、I18N等、Status转换等），暂时要求各模块自己实现。要求数据要先转换为正确的类型。
	 * 
	 * @param request
	 * @return
	 */
	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(HttpServletRequest request) {
		//FIXME
		throw new RuntimeException("Not implemented yet!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.BaseController#initController()
	 */
	@Override
	protected void initController() throws Exception {
		mgr = adPositionTypeManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cartmatic.estore.core.controller.GenericController onSave(javax.servlet.http.HttpServletRequest,
	 *      java.lang.Object, org.springframework.validation.BindException)
	 */
	@Override
	protected void onSave(HttpServletRequest request, AdPositionType adPositionType, BindException errors) {
		AdPositionType adPositionType2=adPositionTypeManager.getAdPositionTypeByName(adPositionType.getStoreId(),adPositionType.getPositionName());
		if(adPositionType2!=null&&(adPositionType.getAdPositionTypeId()==null||adPositionType.getAdPositionTypeId().intValue()!=adPositionType2.getAdPositionTypeId())){
			if(adPositionType.getStoreId()!=null){
				Store store=storeManager.getById(adPositionType.getStoreId());
				if(store!=null){
					errors.rejectValue("positionName","adPositionType.name.exists",new Object[]{store.getName()},"");
				}else{
					errors.rejectValue("storeId","adPositionType.store");
				}
			}
		}
	}

}
