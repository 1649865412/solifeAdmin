package com.cartmatic.estore.core.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestKey;

import com.cartmatic.estore.common.model.system.AppRole;
import com.cartmatic.estore.system.service.AppRoleManager;

public class RequestMapFactoryBean implements FactoryBean<LinkedHashMap<RequestKey, Collection<ConfigAttribute>>>
{
	private static final Log logger	= LogFactory.getLog(RequestMapFactoryBean.class);
	private LinkedHashMap<RequestKey, Collection<ConfigAttribute>> requestMap;
	private static final String ANAONYMOUS_ROLE_NAME = "ROLE_ANONYMOUS";
	private AppRoleManager appRoleManager = null;
	private ResourceMappingProvider	resourceMappingProvider;
	/**
	 * for the public url
	 */
	private List<String> publicURLs	= null;
	/**
	 * for anonymous url
	 */
	private List<String> anonymousURLs = null;
	
	public void reload() {
		init();
	}
	
	public void init() {
		requestMap = new LinkedHashMap<RequestKey, Collection<ConfigAttribute>>();
		ResourceMapping[] resources = resourceMappingProvider.getResourceMappings();
		
		//List<Resource> resources = resourceDao.findAll();
		for (ResourceMapping resource : resources) 
		{
			//如果是restful风格的应用，则构造方法的参数应该是url和method
			RequestKey key = new RequestKey(resource.getResourcePath());
			Collection<ConfigAttribute> attributes = new HashSet<ConfigAttribute>();
			GrantedAuthority[] roles = resource.getRecipients();
			for (GrantedAuthority role : roles)
			{
				ConfigAttribute  attr = new SecurityConfig(role.getAuthority());
				attributes.add(attr);
			}
			requestMap.put(key, attributes);
			if (logger.isDebugEnabled())
			{
				logger.debug("Setup resource from DB. key="+key+" atts:"+attributes.toString());
			}
		}
		//for publicURL 所有login的角色都能访问的url
		List<AppRole> allRolesList = appRoleManager.getAll();
		for (String resource : publicURLs)
		{
			RequestKey key = new RequestKey(resource);
			Collection<ConfigAttribute> attributes = new HashSet<ConfigAttribute>();
			for (GrantedAuthority role : allRolesList)
			{
				ConfigAttribute  attr = new SecurityConfig(role.getAuthority());
				attributes.add(attr);
			}
			requestMap.put(key, attributes);
			if (logger.isDebugEnabled())
			{
				logger.debug("Setup resource from publicURL. key="+key+" atts:"+attributes.toString());
			}
		}
		//设置匿名url
		for (String resource : anonymousURLs)
		{
			RequestKey key = new RequestKey(resource);
			Collection<ConfigAttribute> attributes = new HashSet<ConfigAttribute>();			
			ConfigAttribute  attr = new SecurityConfig(ANAONYMOUS_ROLE_NAME);
			attributes.add(attr);			
			requestMap.put(key, attributes);
		}
	}

	public LinkedHashMap<RequestKey, Collection<ConfigAttribute>> getObject() throws Exception {
		if (requestMap == null) {
			init();
		}
		return requestMap;
	}

	public Class getObjectType() {
		return LinkedHashMap.class;
	}

	public boolean isSingleton() {
		return true;
	}
	
	public void setResourceMappingProvider(ResourceMappingProvider avalue) {
		resourceMappingProvider = avalue;
	}
	public void setAppRoleManager(AppRoleManager avalue) {
		appRoleManager = avalue;
	}
	public void setPublicURLs(List<String> avalue) {
		publicURLs = avalue;
	}
	public void setAnonymousURLs(List<String> avalue)
	{
		anonymousURLs = avalue;
	}
}
