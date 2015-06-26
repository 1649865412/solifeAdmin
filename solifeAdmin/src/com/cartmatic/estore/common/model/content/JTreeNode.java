
package com.cartmatic.estore.common.model.content;

public class JTreeNode {
	private String	text;
	private String	id;
	private boolean	leaf	= false;

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public String toString(){
		return "id="+id+",text="+text+",leaf="+leaf;
	}
}
