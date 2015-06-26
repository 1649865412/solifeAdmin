package com.cartmatic.estore.common.model.sales.base;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.sales.RecommendedProduct;
import com.cartmatic.estore.common.model.sales.RecommendedType;

/**
 * RecommendedType Base Java Bean, base class for the model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * Types of recommend purpose
 */
public class RecommendedTypeTbl extends com.cartmatic.estore.core.model.BaseObject implements Serializable {

	private static final long	serialVersionUID	= -8437294690322588335L;
	
	protected Integer recommendedTypeId;
    protected String recommendTitle; //推荐类型界面显示名称
	protected String typeName;  //推荐类型编码
	protected Integer ruleCode; //评估的规则
	protected Short autoEval; //是否自动评估 0否,1是,2不能
	protected Short status; //是否激活
	protected Short isSystem; //是否系统定义
	protected String templatePath; //模板路径
	protected Short isApplyToProduct; //与商品相关
	protected Short isApplyToCategory; //与目录相关
	protected Integer version;
	protected Set<RecommendedProduct> recommendedProducts = new HashSet<RecommendedProduct>();

	public Short getIsApplyToCategory()
	{
		return isApplyToCategory;
	}

	public void setIsApplyToCategory(Short isApplyToCategory)
	{
		this.isApplyToCategory = isApplyToCategory;
	}

	public Short getIsApplyToProduct()
	{
		return isApplyToProduct;
	}

	public void setIsApplyToProduct(Short isApplyToProduct)
	{
		this.isApplyToProduct = isApplyToProduct;
	}

	/**
	 * Default Empty Constructor for class RecommendedType
	 */
	public RecommendedTypeTbl () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class RecommendedType
	 */
	public RecommendedTypeTbl (
		 Integer in_recommendedTypeId
        ) {
		this.setRecommendedTypeId(in_recommendedTypeId);
    }


	public Set<RecommendedProduct> getRecommendedProducts () {
		return recommendedProducts;
	}	
	
	public void setRecommendedProducts (Set<RecommendedProduct> in_recommendedProducts) {
		this.recommendedProducts = in_recommendedProducts;
	}

	/**
	 * 
	 * @return Integer
     * @hibernate.id column="recommendedTypeId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getRecommendedTypeId() {
		return this.recommendedTypeId;
	}
	
	/**
	 * Set the recommendedTypeId
	 */	
	public void setRecommendedTypeId(Integer aValue) {
		this.recommendedTypeId = aValue;
	}	

	/**
	 * 
	 * @return String
	 * @hibernate.property column="typeName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getTypeName() {
		return this.typeName;
	}
	
	/**
	 * Set the typeName
	 * @spring.validator type="required"
	 */	
	public void setTypeName(String aValue) {
		this.typeName = aValue;
	}	

	/**
	 * 
	 * @return Integer
	 * @hibernate.property column="version" type="java.lang.Integer" length="11" not-null="true" unique="false"
	 */
	public Integer getVersion() {
		return this.version;
	}
	
	/**
	 * Set the version
	 * @spring.validator type="required"
	 */	
	public void setVersion(Integer aValue) {
		this.version = aValue;
	}	

	/**
	 * 
	 * @return Short
	 * @hibernate.property column="autoEval" type="java.lang.Short" length="6" not-null="true" unique="false"
	 */
	public Short getAutoEval() {
		return this.autoEval;
	}
	
	/**
	 * Set the autoEval
	 * @spring.validator type="required"
	 */	
	public void setAutoEval(Short aValue) {
		this.autoEval = aValue;
	}	

	/**
	 * 
	 * @return Integer
	 * @hibernate.property column="ruleCode" type="java.lang.Integer" length="11" not-null="false" unique="false"
	 */
	public Integer getRuleCode() {
		return this.ruleCode;
	}
	
	/**
	 * Set the ruleCode
	 */	
	public void setRuleCode(Integer aValue) {
		this.ruleCode = aValue;
	}	

	/**
	 * 
	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="6" not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 * @spring.validator type="required"
	 */	
	public void setStatus(Short aValue) {
		this.status = aValue;
	}	

	/**
	 * 
	 * @return Short
	 * @hibernate.property column="isSystem" type="java.lang.Short" length="6" not-null="true" unique="false"
	 */
	public Short getIsSystem() {
		return this.isSystem;
	}
	
	/**
	 * Set the isSystem
	 * @spring.validator type="required"
	 */	
	public void setIsSystem(Short aValue) {
		this.isSystem = aValue;
	}	
	
	
	

    public String getRecommendTitle()
    {
        return recommendTitle;
    }
    public void setRecommendTitle(String recommendTitle)
    {
        this.recommendTitle = recommendTitle;
    }
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof RecommendedType)) {
			return false;
		}
		RecommendedType rhs = (RecommendedType) object;
		return new EqualsBuilder()
				.append(this.recommendedTypeId, rhs.recommendedTypeId)
				.append(this.typeName, rhs.typeName)
				.append(this.version, rhs.version)
				.append(this.autoEval, rhs.autoEval)
				.append(this.ruleCode, rhs.ruleCode)
				.append(this.status, rhs.status)
				.append(this.isSystem, rhs.isSystem)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.recommendedTypeId) 
				.append(this.typeName) 
				.append(this.version) 
				.append(this.autoEval) 
				.append(this.ruleCode) 
				.append(this.status) 
				.append(this.isSystem) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("recommendedTypeId", this.recommendedTypeId) 
				.append("typeName", this.typeName) 
				.append("version", this.version) 
				.append("autoEval", this.autoEval) 
				.append("ruleCode", this.ruleCode) 
				.append("status", this.status) 
				.append("isSystem", this.isSystem) 
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "recommendedTypeId";
	}
	
	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Integer getId() {
		return recommendedTypeId;
	}

	public String getTemplatePath()
	{
		return templatePath;
	}

	public void setTemplatePath(String templatePath)
	{
		this.templatePath = templatePath;
	}

}