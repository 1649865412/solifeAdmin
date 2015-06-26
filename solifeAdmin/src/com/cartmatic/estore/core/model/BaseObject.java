
package com.cartmatic.estore.core.model;

import java.io.Serializable;

/**
 * Base class for Model objects. Child objects should implement toString(),
 * equals() and hashCode();
 * <P>
 * 一些常用（但不是必须）的属性有：id, name, code/naturalKey, nameKey, status, version, deleted,
 * updateTime等。其中前2个一般都是需要的（仅用于设计需要的表可能不需要name）。Code是业务主键，有时候用其他字段名，如orderNo。
 * 
 */
public abstract class BaseObject implements Serializable {

	protected Integer			createBy;

	protected java.util.Date	createTime;

	protected Integer			updateBy;

	protected java.util.Date	updateTime;

	public abstract boolean equals(Object o);

	// TODO remove getFirstKeyColumnName
	public String getFirstKeyColumnName() {
		return null;
	}

	// TODO all subclass should overwrite this method to bypass the auto detect
	// process
	public Serializable getId() {
		try {
			return (Integer) getClass().getMethod(
					"get"
							+ getFirstKeyColumnName().substring(0, 1)
									.toUpperCase()
							+ getFirstKeyColumnName().substring(1),
					(Class[]) null).invoke(this, (Object[]) null);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer getIntegerId() {
		if (getId() != null) {
			return new Integer(getId().toString());
		}
		return null;
	}

	public Integer getVersion() {
		return null;
	}

	public abstract int hashCode();

	/**
	 * @param createBy
	 *            the createBy to set
	 */
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @param updateBy
	 *            the updateBy to set
	 */
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setVersion(Integer in_version) {

	}

	public abstract String toString();

}
