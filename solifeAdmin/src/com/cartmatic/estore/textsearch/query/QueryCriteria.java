package com.cartmatic.estore.textsearch.query;

import java.io.Serializable;

/**
 * 专为solr查询而设置的,标准化查询
 * 
 * @author Administrator
 *
 */
public class QueryCriteria implements Serializable{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private int	pageNo = 0;		// 当前页码
	private long totalCount = 0;	// 总记录数
	private int rows = 10;     //default is 10.
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	/**
	 * 当前页的开始数
	 * @return
	 */
	public long getItemNoBegin()
	{
		if (pageNo == 1)
			return 1;
		else
			return (pageNo - 1) * rows;
	}
	
	/**
	 * 当前页的结束数
	 * @return
	 */
	public long getItemNoEnd()
	{
		if (totalCount > pageNo * rows)
			return pageNo * rows;
		else
			return totalCount;
	}
	
	/**
	 * 获得总页数
	 * @return
	 */
	public int getPageCount()
	{
		int pageCount = (int) totalCount/rows;
		if (totalCount%rows > 0)
		{
			pageCount ++;
		}
		return pageCount;
	}
	
	@Override
	public String toString()
	{
		return "pageNo:"+pageNo+" totalCount:"+totalCount+" rows:"+rows;
	}
}
