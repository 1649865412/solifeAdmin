package com.cartmatic.estore.core.controller;

import java.io.Serializable;

import com.cartmatic.estore.core.search.SearchCriteria;

public class NavigationModel implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String navName;
    private String uri;
    private SearchCriteria searchCriteria;
    private boolean isFrom = false;
    
    public boolean getIsFrom()
    {
        return isFrom;
    }
    public void setIsFrom(boolean avalue)
    {
        this.isFrom = avalue;
    }
    public String getNavName()
    {
        return navName;
    }
    public void setNavName(String navName)
    {
        this.navName = navName;
    }
    public String getUri()
    {
        return uri;
    }
    public void setUri(String uri)
    {
        this.uri = uri;
    }
    public SearchCriteria getSearchCriteria()
    {
        return searchCriteria;
    }
    public void setSearchCriteria(SearchCriteria searchCriteria)
    {
        this.searchCriteria = searchCriteria;
    }
    
    
}
