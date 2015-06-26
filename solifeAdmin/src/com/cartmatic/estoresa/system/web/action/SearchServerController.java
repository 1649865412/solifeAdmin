package com.cartmatic.estoresa.system.web.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.common.util.NamedList;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.cartmatic.estore.common.helper.ConfigUtil;
import com.cartmatic.estore.common.service.SolrService;
import com.cartmatic.estore.common.util.FileUtil;
import com.cartmatic.estore.core.controller.BaseController;
import com.cartmatic.estore.core.model.Message;
import com.cartmatic.estore.core.util.ContextUtil;
import com.cartmatic.estore.textsearch.SearchConstants;
import com.cartmatic.estore.webapp.event.IndexNotifyEvent;

/**
 * 用于管理searchServer的controller
 * 默认会显示searchServer的状态。
 * 
 * @author Administrator
 *
 */
public class SearchServerController extends BaseController 
{
	private SolrService solr;	
	
	public ModelAndView defaultAction(HttpServletRequest req, HttpServletResponse resp) 
	{
		removeNavFromSearchCriteria(req);
		//获得admin的solrServer
		SolrServer coreadmin = solr.getSolrServer(SearchConstants.CORE_NAME_ADMIN);
		
		ModelAndView mv = new ModelAndView("system/searchServer");
		try {
			//获得product的status
			CoreAdminResponse mcr = CoreAdminRequest.getStatus(SearchConstants.CORE_NAME_PRODUCT, coreadmin);
			NamedList rs = mcr.getCoreStatus().get(SearchConstants.CORE_NAME_PRODUCT);
			NamedList indexInfo = (NamedList) rs.get("index");
			mv.addObject("productCore", rs);
			mv.addObject("productIndexInfo", indexInfo);
			//获得salesorder的status
			CoreAdminResponse so_mcr = CoreAdminRequest.getStatus(SearchConstants.CORE_NAME_SALESORDER, coreadmin);
			NamedList so_rs = so_mcr.getCoreStatus().get(SearchConstants.CORE_NAME_SALESORDER);
			NamedList so_indexInfo = (NamedList) so_rs.get("index");
			mv.addObject("salesOrderCore", so_rs);
			mv.addObject("salesOrderIndexInfo", so_indexInfo);
			//获得salesorder的status
			CoreAdminResponse cn_mcr = CoreAdminRequest.getStatus(SearchConstants.CORE_NAME_CONTENT, coreadmin);
			NamedList cn_rs = cn_mcr.getCoreStatus().get(SearchConstants.CORE_NAME_CONTENT);
			NamedList cn_indexInfo = (NamedList) cn_rs.get("index");
			mv.addObject("contentCore", cn_rs);
			mv.addObject("contentIndexInfo", cn_indexInfo);
			
			String searchServerPath = ConfigUtil.getInstance().getStoreSearchPath();
			if (logger.isDebugEnabled())
				logger.debug("searchServerPath=" + searchServerPath);
			if (searchServerPath != null)
			{
				String synonyms = FileUtil.readFileToString(searchServerPath + "/product/conf/synonyms.txt");
				String protwords = FileUtil.readFileToString(searchServerPath + "/product/conf/protwords.txt");
				mv.addObject("synonyms", synonyms);
				mv.addObject("protwords", protwords);
			}
			
		} catch (Exception e) {
			logger.error(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * save Synonym
	 * @param req
	 * @param resp
	 * @return
	 */
	public ModelAndView saveSynonym(HttpServletRequest req, HttpServletResponse resp)
	{
		String synonym = req.getParameter("synonym");
		String searchServerPath = ConfigUtil.getInstance().getStoreSearchPath();
		try {
			FileUtil.writeStringToFile(searchServerPath + "/product/conf/synonyms.txt", synonym);
			saveMessage(Message.info("searchServer.save.synonym.succeeded"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		ModelAndView mv = new ModelAndView(new RedirectView("/system/searchServer.html", true));
		return mv;
	}
	
	/**
	 * save Protected
	 * @param req
	 * @param resp
	 * @return
	 */
	public ModelAndView saveProtected(HttpServletRequest req, HttpServletResponse resp)
	{
		String protectedWords = req.getParameter("protectedWords");
		String searchServerPath = ConfigUtil.getInstance().getStoreSearchPath();
		try {
			FileUtil.writeStringToFile(searchServerPath + "/product/conf/protwords.txt", protectedWords);
			saveMessage(Message.info("searchServer.save.protected.succeeded"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		ModelAndView mv = new ModelAndView(new RedirectView("/system/searchServer.html", true));
		return mv;
	}
	
	/**
	 * 重启
	 * @param req
	 * @param resp
	 * @return
	 */
	public ModelAndView restart(HttpServletRequest req, HttpServletResponse resp)
	{
		SolrServer coreadmin = solr.getSolrServer(SearchConstants.CORE_NAME_ADMIN);
		try {
			CoreAdminRequest.reloadCore(SearchConstants.CORE_NAME_PRODUCT, coreadmin);
			CoreAdminRequest.reloadCore(SearchConstants.CORE_NAME_CONTENT, coreadmin);
			CoreAdminRequest.reloadCore(SearchConstants.CORE_NAME_SALESORDER, coreadmin);
			saveMessage(Message.info("searchServer.confirm.restart.succeeded"));
		} catch (Exception e) {
			logger.error(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView(new RedirectView("/system/searchServer.html", true));
		return mv;
	}
	
	/**
	 * 重建索引
	 * @param req
	 * @param resp
	 * @return
	 */
	public ModelAndView rebuildProductIndex(HttpServletRequest req, HttpServletResponse resp)
	{
		//使用notifyEvent通知search server进行重建索引
		IndexNotifyEvent event = new IndexNotifyEvent(SearchConstants.CORE_NAME_PRODUCT, SearchConstants.UPDATE_TYPE.REBUILD_ALL);
		ContextUtil.getInstance().fireApplicationEvent(event);
		saveMessage(Message.info("searchServer.rebuild.product.index.succeeded"));
		ModelAndView mv = new ModelAndView(new RedirectView("/system/searchServer.html", true));
		return mv;
	}
	
	public ModelAndView rebuildSalesOrderIndex(HttpServletRequest req, HttpServletResponse resp)
	{
		//使用notifyEvent通知search server进行重建索引
		IndexNotifyEvent event = new IndexNotifyEvent(SearchConstants.CORE_NAME_SALESORDER, SearchConstants.UPDATE_TYPE.REBUILD_ALL);
		ContextUtil.getInstance().fireApplicationEvent(event);
		saveMessage(Message.info("searchServer.rebuild.order.index.succeeded"));
		ModelAndView mv = new ModelAndView(new RedirectView("/system/searchServer.html", true));
		return mv;
	}
	
	public ModelAndView rebuildContentIndex(HttpServletRequest req, HttpServletResponse resp)
	{
		//使用notifyEvent通知search server进行重建索引
		IndexNotifyEvent event = new IndexNotifyEvent(SearchConstants.CORE_NAME_CONTENT, SearchConstants.UPDATE_TYPE.REBUILD_ALL);
		ContextUtil.getInstance().fireApplicationEvent(event);
		saveMessage(Message.info("searchServer.rebuild.content.index.succeeded"));
		ModelAndView mv = new ModelAndView(new RedirectView("/system/searchServer.html", true));
		return mv;
	}
	
	public void setSolrService(SolrService avalue)
	{
		solr = avalue;
	}

	@Override
	protected Map<Integer, Map<String, Object>> getMultiSaveModel(
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initController() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
}
