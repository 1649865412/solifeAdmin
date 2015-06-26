
package com.cartmatic.estore.core.security;

public interface ConfigableFilterInvocationDefinition {
	/** The ant path expression */
	String	ANT_PATH_KEY			= "PATTERN_TYPE_APACHE_ANT";

	/** The Perl5 expression */
	String	PERL5_KEY				= "PATTERN_TYPE_PERL5";

	/** * */
	String	STAND_DELIM_CHARACTER	= ",";

	/**
	 * for the url pattern
	 */
	String	URL_PATH_KEY			= "PATTERN_TYPE_URL";

	/**
	 * 
	 * @return resource expression
	 */
	String getResourceExpression();

	/**
	 * 
	 * @return whether convert url to lowercase before comparison
	 */
	boolean isConvertUrlToLowercaseBeforeComparison();

	/**
	 * Set whether convert url to lowercase before comparison
	 * 
	 * @param convertUrlToLowercaseBeforeComparison
	 *            whether convertUrlToLowercaseBeforeComparison
	 */
	void setConvertUrlToLowercaseBeforeComparison(
			boolean convertUrlToLowercaseBeforeComparison);

	/**
	 * Set resource expression, the value must be {@link #PERL5_KEY_REG_EXP} or
	 * {@link #ANT_PATH_KEY}
	 * 
	 * @see #REOURCE_EXPRESSION_PERL5_REG_EXP
	 * @see #RESOURCE_EXPRESSION_ANT_PATH_KEY
	 * @param resourceExpression
	 *            the resource expression
	 */
	void setResourceExpression(String resourceExpression);

}
