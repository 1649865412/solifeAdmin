/*
 * Created on Jun 14, 2006
 * 
 */

package com.cartmatic.estore.common.helper;

import java.util.ArrayList;
import java.util.List;

import com.cartmatic.estore.Constants;
import com.cartmatic.estore.webapp.util.RequestContext;

import net.sf.navigator.menu.MenuComponent;

/**
 * <p>
 * Note: To parse and display current menu correctly, menu structure and url
 * structure must be orginized in the same style. However the directory where
 * the page is in is not important, we decide everything by parsing the url
 * structure. Every submenu should has its own url structure, which is exactly
 * the same as menu structure, or else, this program will return the incorrect
 * parent menu. And Menu must define url/page, and the URL must be unique. The
 * first menu must be home page.
 * </p>
 * 
 * @author Ryan
 * 
 */
public class MenuHelper {
   	
	/**
	 * 找出选中的菜单，并返回导航
	 * @param menus
	 * @param requestUrl
	 * @return
	 */
	public static List<String> getSelectedNavMenu(List<MenuComponent> menus, final String requestUrl)
	{
		
		for (MenuComponent lev1: menus)
		{
			List<MenuComponent> lev2s = lev1.getComponents();
			if (lev2s != null && lev2s.size() > 0)
			{
				for (MenuComponent lev2: lev2s)
				{
					List<MenuComponent> lev3s = lev2.getComponents();
					if (lev3s != null && lev3s.size() > 0)
					{
						for (MenuComponent lev3: lev3s)
						{
							if (isMatch(lev3.getPage(), requestUrl))
							{
								List<String> result = new ArrayList<String>();
								result.add(lev1.getTitle());
								result.add(lev2.getTitle());
								result.add(lev3.getTitle());
								return result;
							}
						}						
					}
					else if (isMatch(lev2.getPage(), requestUrl))
					{
						List<String> result = new ArrayList<String>();
						result.add(lev1.getTitle());
						result.add(lev2.getTitle());
						return result;
					}
				}
			}
			else if (isMatch(lev1.getPage(), requestUrl))
			{
				List<String> result = new ArrayList<String>();
				result.add(lev1.getTitle());				
				return result;
			}
		}
		return null;
	}
	
	public static MenuComponent getCurrentMenu(List<MenuComponent> menus, final String requestUrl)
	{
		
		for (MenuComponent lev1: menus)
		{
			if (isMatch(lev1.getPage(), requestUrl)){
				lev1.setImage("1");
				return lev1;
			}
			List<MenuComponent> lev2s = lev1.getComponents();
			if (lev2s != null && lev2s.size() > 0)
			{
				for (MenuComponent lev2: lev2s)
				{
					if (isMatch(lev2.getPage(), requestUrl))
					{
						lev2.setImage("2");
						return lev2;
					}
					List<MenuComponent> lev3s = lev2.getComponents();
					if (lev3s != null && lev3s.size() > 0)
					{
						for (MenuComponent lev3: lev3s)
						{
							if (isMatch(lev3.getPage(), requestUrl))
							{
								lev3.setImage("3");
								return lev3;
							}
						}	
					}
				}
			}
		}
		return null;
	}
	
	public static List<MenuComponent> getMenuList(List<MenuComponent> menus)
	{
		List<MenuComponent> menuList=new ArrayList<MenuComponent>();
		for (int i = 1; i < menus.size(); i++)
		{
			MenuComponent menuComponent=menus.get(i);
			if(menuComponent.getParent()==null&&RequestContext.authorizeUsingUrlCheck(menuComponent.getPage())){
				menuComponent.setImage("1");
				menuList.add(menuComponent);
				List<MenuComponent> menuContextList2=menuComponent.getComponents();
				if(menuContextList2!=null){
					for (MenuComponent menuComponent2 : menuContextList2)
					{
						if(RequestContext.authorizeUsingUrlCheck(menuComponent2.getPage())){
							menuComponent2.setImage("2");
							menuList.add(menuComponent2);
							
							/*List<MenuComponent> menuContextList3=menuComponent2.getComponents();
							if(menuContextList3!=null){
								for (MenuComponent menuComponent3 : menuContextList3)
								{
									if(RequestContext.authorizeUsingUrlCheck(menuComponent3.getPage())){
										menuComponent3.setImage("3");
										menuList.add(menuComponent3);
									}
								}
							}*/
						}
					}
				}
			}
		}
		return menuList;
	}
	
	private static boolean isMatch(String url, String requestUrl)
	{
		return isMenuUrlMatched(url, requestUrl)
	    || isMenuUrlMatchCustom(url, requestUrl)
	    || isMenuUrlMatchEdit(url, requestUrl);
	}
	

	private static boolean isMenuUrlMatchCustom(final String url1,
			final String url2) {
		String menuUrl = removeParamsFromUrl(url1).toLowerCase();
		String parsedRequestUrl = removeParamsFromUrl(url2).toLowerCase();

		// -----------
		int idx = menuUrl.indexOf(".");
		if (idx > 0) {
			int idx2 = menuUrl.lastIndexOf("/");
			if (idx2 == -1)
				idx2 = 0;
			menuUrl = menuUrl.substring(idx2 + 1, idx - 1);
			if (menuUrl.length() > 1) {
				menuUrl = menuUrl.substring(0, menuUrl.length()) + "_";

			}

			if (parsedRequestUrl.lastIndexOf("/") != -1)
				parsedRequestUrl = parsedRequestUrl.substring(parsedRequestUrl
						.lastIndexOf("/") + 1);
		}
		// System.err.println("---custom---------menuUrl-------"+menuUrl);
		// System.err.println("---custom---------parsedRequestUrl-------"+parsedRequestUrl);
		return (parsedRequestUrl.startsWith(menuUrl));
	}

	private static boolean isMenuUrlMatched(final String url1, final String url2) {
		String uri1 = removeParamsFromUrl(url1);
		String uri2 = removeParamsFromUrl(url2);

		if (uri1.equals(uri2)) {
			return true;
		}
		return false;
	}

	private static boolean isMenuUrlMatchEdit(final String url1,
			final String url2) {

		String menuUrl = removeParamsFromUrl(url1).toLowerCase();
		String parsedRequestUrl = removeParamsFromUrl(url2).toLowerCase();

		// -----------
		int idx = menuUrl.indexOf(".");
		if (idx > 0) {
			int idx2 = menuUrl.lastIndexOf("/");
			if (idx2 == -1)
				idx2 = 0;
			menuUrl = menuUrl.substring(idx2 + 1, idx - 1);
			if (menuUrl.length() > 1) {
				if (!menuUrl.startsWith("edit"))
					menuUrl = "edit" + menuUrl.substring(0, menuUrl.length());
				else
					menuUrl = menuUrl.substring(0, menuUrl.length());
			}
		}
		int idx3 = parsedRequestUrl.indexOf(".");
		if (idx3 > 0) {
			int idx4 = parsedRequestUrl.lastIndexOf("/");
			if (idx4 == -1)
				idx4 = 0;
			parsedRequestUrl = parsedRequestUrl.substring(idx4 + 1, idx3);
			if (parsedRequestUrl.length() > 1) {
				parsedRequestUrl = parsedRequestUrl.substring(0,
						parsedRequestUrl.length());
			}
		}
		// System.err.println("---edit---------menuUrl-------"+menuUrl);
		// System.err.println("---edit---------parsedRequestUrl-------"+parsedRequestUrl);
		return (parsedRequestUrl.equals(menuUrl));
	}

	private static String removeParamsFromUrl(final String url) {
		int idx = url.indexOf("?");
		String parsedUrl = null;
		if (idx > 0) {
			parsedUrl = url.substring(0, idx);
		} else {
			parsedUrl = url;
		}

		if (parsedUrl.endsWith("/")) {
			parsedUrl = parsedUrl.substring(0, parsedUrl.length() - 1);
		}

		return parsedUrl;
	}
}
