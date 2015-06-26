
package com.cartmatic.estore.sales.engine;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.common.model.sales.PromoRuleElement;
import com.cartmatic.estore.common.model.sales.PromoRuleParameter;
import com.cartmatic.estore.sales.model.BaseElement;
import com.cartmatic.estore.sales.model.action.Action;
import com.cartmatic.estore.sales.model.condition.Condition;
import com.cartmatic.estore.sales.model.eligibility.Eligibility;

/**
 * 该类是对应PromoRule的一个model类,只是为了方便RuleEngine运行。
 * 
 * @author CartMatic
 * 
 */
public class PRule {
	public final short			ENABLE_DISCOUNT_AGAIN_YES	= 1;

	private List<Eligibility>	eligibilitys				= new ArrayList<Eligibility>();
	private List<Condition>		conditions					= new ArrayList<Condition>();
	private List<Action>		actions						= new ArrayList<Action>();
	private Short				eligibilityOperator;
	private Short				conditionOperator;
	private String				ruleType;
	private Integer				ruleId;
	private String				ruleName;
	private String				description;
	private boolean				enableDiscountAgain			= false;
	private Date	startTime;
	private Date	endTime;
	private String storeCode;

	public PRule() {

	}

	public PRule(PromoRule _rule) {
		try {
			build(_rule);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据PromoRule建造PRule
	 * 
	 * @param _rule
	 * @throws Exception
	 */

	private void build(PromoRule _rule) throws Exception {

		for (PromoRuleElement ruleElement : _rule.getPromoRuleElements()) {
			if (ruleElement.getKind().equals(
					PromoRuleElement.KIND_OF_ELIGIBILITY)) {
				Eligibility eligibility = buildEligibility(ruleElement);
				if (null != eligibility) {
					eligibilitys.add(eligibility);
				}
			}
			if (ruleElement.getKind()
					.equals(PromoRuleElement.KIND_OF_CONDITION)) {
				Condition condition = buildCondition(ruleElement);
				if (null != condition) {
					conditions.add(condition);
				}
			}
			if (ruleElement.getKind().equals(PromoRuleElement.KIND_OF_ACTION)) {

				Action action = buildAction(ruleElement);
				if (null != action) {
					actions.add(action);
				}
			}
		}

		this.eligibilityOperator = _rule.getEligibilityOperator();
		this.conditionOperator = _rule.getConditionOperator();
		this.ruleId = _rule.getPromoRuleId();
		this.ruleName = _rule.getName();
		this.description = _rule.getDescription();
		this.ruleType = _rule.getPromoType();
		this.enableDiscountAgain = (_rule.getEnableDiscountAgain() == ENABLE_DISCOUNT_AGAIN_YES) ? true
				: false;
		this.startTime = _rule.getStartTime();
		this.endTime = _rule.getEndTime();
		if (_rule.getStore() != null)
			this.storeCode = _rule.getStore().getCode();
	}

	/**
	 * 根据PromoRuleElement建造一个Eligibility
	 * 
	 * @param ruleElement
	 * @return Eligibility
	 */
	private Eligibility buildEligibility(PromoRuleElement ruleElement)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException 
	{
		Map<String, String> params = new HashMap<String, String>();
		for (PromoRuleParameter parameter : ruleElement
				.getPromoRuleParameters()) {
			if (params.containsKey(parameter.getParamName())) {
				params.put(parameter.getParamName(), params.get(parameter
						.getParamName())
						+ BaseElement.SEPERATOR_BEW_EXCLUDED_PARAM
						+ parameter.getParamValue());
			} else {
				params.put(parameter.getParamName(), parameter.getParamValue());
			}
		}
		Eligibility eligibility;
		try {
			Class<?> eligibilityClass = Class.forName(Eligibility.class
					.getPackage().getName()
					+ "." + ruleElement.getType());
			eligibility = (Eligibility) eligibilityClass.getConstructor(
					Map.class).newInstance(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return eligibility;
	}

	/**
	 * 根据PromoRuleElement建造一个Condition
	 * 
	 * @param ruleElement
	 * @return Condition
	 */
	private Condition buildCondition(PromoRuleElement ruleElement)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		Map<String, String> params = new HashMap<String, String>();
		for (PromoRuleParameter parameter : ruleElement
				.getPromoRuleParameters()) {
			if (params.containsKey(parameter.getParamName())) {
				params.put(parameter.getParamName(), params.get(parameter
						.getParamName())
						+ BaseElement.SEPERATOR_BEW_EXCLUDED_PARAM
						+ parameter.getParamValue());
			} else {
				params.put(parameter.getParamName(), parameter.getParamValue());
			}
		}
		Condition condition;
		try {
			Class<?> conditionClass = Class.forName(Condition.class
					.getPackage().getName()
					+ "." + ruleElement.getType());
			condition = (Condition) conditionClass.getConstructor(Map.class)
					.newInstance(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return condition;
	}

	/**
	 * 根据PromoRuleElement建造一个Action
	 * 
	 * @param ruleElement
	 * @return Action
	 */
	private Action buildAction(PromoRuleElement ruleElement)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		Map<String, String> params = new HashMap<String, String>();
		for (PromoRuleParameter parameter : ruleElement
				.getPromoRuleParameters()) {
			if (params.containsKey(parameter.getParamName())) {
				params.put(parameter.getParamName(), params.get(parameter
						.getParamName())
						+ BaseElement.SEPERATOR_BEW_EXCLUDED_PARAM
						+ parameter.getParamValue());
			} else {
				params.put(parameter.getParamName(), parameter.getParamValue());
			}
		}
		Action action;
		try {
			Class<?> actionClass = Class.forName(Action.class.getPackage()
					.getName()
					+ "." + ruleElement.getType());
			action = (Action) actionClass.getConstructor(Map.class)
					.newInstance(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return action;
	}

	public List<Eligibility> getEligibilitys() {
		return eligibilitys;
	}

	public void setEligibilitys(List<Eligibility> eligibilitys) {
		this.eligibilitys = eligibilitys;
	}

	public void addEligibility(Eligibility _eligibility) {
		this.eligibilitys.add(_eligibility);
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public void addCondition(Condition _condition) {
		this.conditions.add(_condition);
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public void addAction(Action _action) {
		this.actions.add(_action);
	}

	public Short getEligibilityOperator() {
		return eligibilityOperator;
	}

	public void setEligibilityOperator(Short eligibilityOperator) {
		this.eligibilityOperator = eligibilityOperator;
	}

	public Short getConditionOperator() {
		return conditionOperator;
	}

	public void setConditionOperator(Short conditionOperator) {
		this.conditionOperator = conditionOperator;
	}

	public boolean isEnableDiscountAgain() {
		return enableDiscountAgain;
	}

	public void setEnableDiscountAgain(boolean enableDiscountAgain) {
		this.enableDiscountAgain = enableDiscountAgain;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	
	

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public boolean equals(Object object) {
		if (!(object instanceof PRule)) {
			return false;
		}
		PRule rhs = (PRule) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(
				this.conditionOperator, rhs.conditionOperator).append(
				this.eligibilityOperator, rhs.eligibilityOperator).append(
				this.actions, rhs.actions).append(this.eligibilitys,
				rhs.eligibilitys).append(this.conditions, rhs.conditions)
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(-528696291, 893303257).appendSuper(
				super.hashCode()).append(this.conditionOperator).append(
				this.eligibilityOperator).append(this.actions).append(
				this.eligibilitys).append(this.conditions).toHashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("conditions", this.conditions)
				.append("conditionOperator", this.conditionOperator).append(
						"eligibilityOperator", this.eligibilityOperator)
				.append("actions", this.actions).append("eligibilitys",
						this.eligibilitys).toString();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
