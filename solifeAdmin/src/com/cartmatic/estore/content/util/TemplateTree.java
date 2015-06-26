package com.cartmatic.estore.content.util;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
 * CreateTime 2006-6-5
 *
 */

/**
 * @author Luo Shunkui
 * 
 *
 */
public class TemplateTree {
	private List tree=new ArrayList();
	
	public TemplateTree(){
		CartmaticTreeNode node=new CartmaticTreeNode(0,"",0,0,true,"");
		tree.add(node);
		
	}
	public List getTree(){
		return tree;
	}
	public void makeTree(Set s){
		
		
	}
	public int addLeaf(String leafInfo,int id){
		String strTemp=leafInfo;
		int parentId=0;
		int level=0;
		int position=0;
		while(strTemp.indexOf("/")!=-1){
			
			String node=strTemp.substring(0,strTemp.indexOf("/"));
			strTemp=strTemp.substring(strTemp.indexOf("/")+1);
			
			position+=node.length()+level;
			String nodeData=leafInfo.substring(0,position);
			if(node!=null){
				
			parentId=this.addNode(level,parentId,node,true,nodeData,id);
			}
			level++;
			
		}
		
		if(strTemp!=null&&!strTemp.endsWith("/")&&!strTemp.equals("")){
			this.addNode(level,parentId,strTemp,false,leafInfo,id);
		}
		return this.tree.size();
		
	}
	
	private int addNoode(String nodeS){
		return 0;
	}
	
	private int addNode(int level,int parentId,Object nodeData,boolean isNode,Object nodeId,int id){
		CartmaticTreeNode node=new CartmaticTreeNode();
		node.setNodeId(nodeId);
		node.setLevel(level);
		node.setNode(isNode);
		node.setId(this.tree.size());
		node.setParentId(parentId);
		node.setData(nodeData);
		boolean isExits=false;
		for(int i=0;i<this.tree.size();i++){
			CartmaticTreeNode that=(CartmaticTreeNode)this.tree.get(i);
			isExits=node.isExits(that);
			if(isExits)
				return that.getId();
		}
	    if(!isExits)
			this.tree.add(node);
		
		return node.getId();
		
	}


}
