package com.cartmatic.estore.content.util;
/*
 * Create Time 2006-6-5
 *
 */

/**
 * @author Luo Shunkui
 *
 */
public class CartmaticTreeNode {
	private int level;
	private Object data;
	private int id;
	private int parentId;
	private boolean isNode;
	private Object nodeId;
	
	
	public CartmaticTreeNode(){
		
	}
	
	
	public CartmaticTreeNode(int level,Object data,int id,int parentId,boolean isNode,Object nodeId){
		this.level = level;
		this.data = data;
		this.id = id;
		this.parentId = parentId;
		this.isNode = isNode;
		this.nodeId = nodeId;
	}
	public CartmaticTreeNode(Integer level,Object data,Integer id,Integer parentId,Boolean isNode,Object nodeId){
		this.level = level.intValue();
		this.data = data;
		this.id = id.intValue();
		this.parentId = parentId.intValue();
		this.isNode = isNode.booleanValue();
		this.nodeId = nodeId;
	}
	/**
	 * @return nodeId
	 */
	public Object getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId nodeId
	 */
	public void setNodeId(Object nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @return data
	 */
	public Object getData() {
		return data;
	}
	/**
	 * @param data 
	 */
	public void setData(Object data) {
		this.data = data;
	}
	/**
	 * @return id。
	 */
	
	public int getId() {
		return id;
	}
	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @return  parentId
	 */
	public int getParentId() {
		return parentId;
	}
	/**
	 * @param parentId
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return isNode
	 */
	public int getIsNode() {
		if(isNode)
		 return 1;
		else
			return 0;
	}
	/**
	 * @param isNode 
	 */
	public void setNode(boolean isNode) {
		this.isNode = isNode;
	}
	
	public boolean isExits(CartmaticTreeNode that){
		if(this.data.equals(that.data)&&this.parentId==that.parentId)
			return true;
	   else
		return false;
	}
	public String toString(){
		StringBuffer buf=new StringBuffer();
		buf.append(" nodeId:"+this.nodeId);
		buf.append(" Id:"+this.id);
		buf.append(" data:"+this.data);
		buf.append(" parentId:"+this.parentId);
		buf.append(" level:"+this.level);
		buf.append(" isNode:"+this.isNode);
		return buf.toString();
	}
}
