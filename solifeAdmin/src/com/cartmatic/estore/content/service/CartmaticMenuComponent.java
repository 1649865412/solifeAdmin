package com.cartmatic.estore.content.service;

import net.sf.navigator.menu.MenuComponent;

/**
 * Created 2006-7-12--16:03:02
 * @author  fire
 * 
 */
public class CartmaticMenuComponent extends MenuComponent {
    
    String categoryPath;
    
    

    /**
     * @return 
     */
    public String getCategoryPath() {
        return categoryPath;
    }
    /**
     * @param categoryPath 
     */
    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }
}
