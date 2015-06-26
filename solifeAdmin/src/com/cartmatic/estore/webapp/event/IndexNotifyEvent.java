package com.cartmatic.estore.webapp.event;

import java.util.ArrayList;
import java.util.List;

import com.cartmatic.estore.core.event.ApplicationEvent;
import com.cartmatic.estore.textsearch.SearchConstants;

public class IndexNotifyEvent extends ApplicationEvent
{
    
    /**
     * serial version
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID,实体是Integer.
     */
    private List<Integer> ids;
    
    /**
     * 某些情况下,提供hql可能会更方便.
     * 更新索引时会根据hql中的记录来处理.
     */
    private String hql;
    
    /**
     * update type
     * 
     */
    private SearchConstants.UPDATE_TYPE updateType;
    /**
     * 目前只有product和content
     */
    private String core;
    
    public IndexNotifyEvent(String core, SearchConstants.UPDATE_TYPE updateType)
    {
        super(core + ":" + updateType);
        this.core = core;
        this.updateType = updateType;
    }
    

    public List<Integer> getIds()
    {
        return ids;
    }

    public SearchConstants.UPDATE_TYPE getUpdateType()
    {
        return updateType;
    }

    public String getCore()
    {
        return core;
    }

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}


	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	
	public void setIds(String[] avalue) {
		if (ids == null)
    	{
    		ids = new ArrayList<Integer>();
    	}
		for (String id: avalue)
			ids.add(Integer.parseInt(id));
	}
    
	/**
	 * 添加id
	 * @param id
	 */
    public void addId(Integer id)
    {
    	if (ids == null)
    	{
    		ids = new ArrayList<Integer>();
    	}
    	ids.add(id);
    }
}
