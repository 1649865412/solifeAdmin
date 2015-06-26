package com.cartmatic.estore.common.helper;

import com.cartmatic.estore.system.service.AppAuditManager;

/**
 * 用法：
 * AppAuditHelper.getInstance().doAuditAction(AppAuditHelper.ACTION_LOGIN,
 *                        "Audit content is here.",
 *                        AppAuditHelper.RESULT_SUCCESS,
 *                        targetUrl);
 * 分别输入审计的action，内容，结果，相关url，相关用户ID（可选）。
 * 
 * @author Ryan
 *
 */
public class AppAuditHelper
{

    /**
     * 相关的Action
     */
    public static final String ACTION_STARTUP = "START UP";
    public static final String ACTION_LOGIN = "LOGIN";
    public static final String ACTION_REMEBER_ME_LOGIN = "REMEMBER ME LOGIN";
    public static final String ACTION_UPDATE = "UPDATE";
    public static final String ACTION_DEL_PRODUCT = "DEL PRODUCT";
    public static final String ACTION_DEL_CATEGORY = "DEL CATEGORY";
    public static final String ACTION_DEL_CUSTOMER = "DEL CUSTOMER";
    
    /**
     * 相尖的结果
     */
    public static final String RESULT_FAILED = "FAILED";
    public static final String RESULT_SUCCESS = "SUCCESS";
    
    private static AppAuditHelper instance = new AppAuditHelper();
    private AppAuditManager mgr;
    
    private AppAuditHelper()
    {}
    
    public static AppAuditHelper getInstance(){
        return instance;
    }
    
    /**
     * 
     * @param actionName 参考常量 ACTION_*
     * @param procObj    审计的信息内容
     * @param procResult 结果，只有RESULT_FAILED or RESULT_SUCCESS 
     * @param requestUrl 相关的url
     */
    public void doAuditAction(String actionName, Object procObj,
                    Object procResult, String requestUrl)
    {
        mgr.doAuditAction(actionName, procObj, procResult, requestUrl);
    }

    /**
     * 
     * @param actionName 参考常量 ACTION_*
     * @param procObj    审计的信息内容
     * @param procResult 结果，只有RESULT_FAILED or RESULT_SUCCESS
     * @param requestUrl 相关的url
     * @param userId     相关的操作者Id
     */
    public void doAuditAction(String actionName, Object procObj,
                    Object procResult, String requestUrl, Integer userId)
    {
        mgr.doAuditAction(actionName, procObj, procResult, requestUrl, userId, null);
    }
    
    public void setAppAuditManager(AppAuditManager avalue)
    {
        mgr = avalue;
    }
}
