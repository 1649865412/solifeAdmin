
package com.cartmatic.estore.core.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.core.util.I18nUtil;

/**
 * 简介：处理复杂的I18N信息的TAG，相当于加强版本的fmt:message,在用法上也尽量保持与fmt:message类似，方便移植。
 * <P>
 * 现在我们的I18n信息输出主要使用fmt:message（JSP）或getText/getMessage（一般是Controller层），
 * 但是这两种使用都不方便，每次只能引用一个key，所以一句话就要分开几段来写。
 * 因此引进一个新的TAG，期望可以解决这些问题，并统一输出I18n信息，底层封装，对使用者透明，统一以后并可以增加其他的功能（如统一关闭I18n）。
 * <P>
 * 功能：
 * <li>支持JSP本身的EL。表达式会先被JSP的EL处理然后才轮到本功能进一步处理。
 * <li>通过类似JSP的EL的语法来组合I18n信息，这个可以看作是支持静态的参数，而{0}这样的可以看作是动态的参数</li>
 * <li>对于一个页面多次用到并且性能比较重要的情况，又或者希望进一步处理的情况（例如截短为32字符），可以保存为变量，后面可以引用</li>
 * <li>可以控制用还是不用EL，用key不会使用，用expr则启用</li>
 * <li>使用$[...]标记来指示I18n信息，中间的部分会被分析出来作为key</li>
 * <li>上面的expr表达式信息本身还可以是一个EL，因此支持嵌套</li>
 * <li>当没有发现$[]这样的标记符的时候，自动当作key处理</li>
 * <li>EL可以包含HTML</li>
 * <li>表达式为空的时候不处理，直接返回</li>
 * <li>对.jsp（不经过Spring）以及.html（经过Spring）都可以使用</li>
 * <li>对于找不到的key，提示为???+key+???</li>
 * <li>自动使用当前用户的语言</li>
 * <li>支持Properties文件和数据库里面的信息的混合</li>
 * <li>支持使用参数，最多支持两个，在expr里面用{0}和{1}作为占位符，参数本身“不再”支持表达式（可通过saveAsAttr保存法间接支持），先求expr值，再替换</li>
 * <P>
 * 限制和不足：
 * <li>由于处理稍为复杂，对性能有轻微的影响。最好结合页面静态化或缓存使用。</li>
 * <li>expr支持动态的多层嵌套，例如expr=aa$[A]aa，其中$[A]里面是bb$[B]bb；但不支持expr=$[$[B]]这样的直接嵌套（因会影响分析结果）。</li>
 * <P>
 * 扩展：
 * <li>根据系统配置方便的启用/禁止I18N文字功能，并进行相应的优化，这也是增加这个TAG的最重要的目的。当系统没有启用I18n的时候，只是简单的输出其内容。暂定对于动态管理的数据库I18n信息，在$[后面加多一个[来控制。</li>
 * <P>
 * 用法和举例：
 * 
 * <pre>
 * &lt;i18n:msg expr=&quot;$[static-expr1]text$[[dynamic-expr2]...&quot;/&gt;
 * <P>
 * 举例：&quot;&lt;span&gt;&lt;B&gt;&lt;i18n:msg expr='test1:$[${myexpr}]test2$[[403.title]test3' /&gt;&lt;/B&gt;&lt;/span&gt;&quot;
 * 
 * </pre>
 * 
 * @jsp.tag name="msg" bodycontent="empty"
 */
public class MessageTag extends TagSupport {

	private String				arg1			= null;

	private String				arg2			= null;

	private boolean				argIsI18n		= false;

	private boolean				emtpyWhenNull	= false;

	private String				expr			= null;

	private String				key				= null;

	private transient final Log	logger			= LogFactory
														.getLog(MessageTag.class);

	private String				saveAsAttr		= null;

	public int doStartTag() throws JspException {
		/**
		 * 说明：表达式expr里面的JSP EL（类似${vo.attr}）已经在传进来之前处理过了，这是TAG框架自动处理的
		 */
		String i18nMsg = null;

		try {
			if (key != null) {
				i18nMsg = I18nUtil.getInstance().getMessage(key,
						emtpyWhenNull ? "" : null);
			} else if (expr != null) {
				// 分析和求值：I18N表达式，里面可能包含参数
				i18nMsg = I18nUtil.getInstance().evalMessage(expr);
				// 参数求值和替换
				if (arg1 != null) {
					int arg1Idx = i18nMsg.indexOf("{0}");
					if (arg1Idx >= 0) {
						StringBuilder sb = new StringBuilder(i18nMsg);
						sb.replace(arg1Idx, arg1Idx + 3, argIsI18n ? I18nUtil
								.getInstance().getMessage(arg1) : arg1);
						if (arg2 != null) {
							int arg2Idx = sb.indexOf("{1}");
							if (arg2Idx >= 0) {
								sb.replace(arg2Idx, arg2Idx + 3,
										argIsI18n ? I18nUtil.getInstance()
												.getMessage(arg2) : arg2);
							}
						}
						i18nMsg = sb.toString();
					}
				}
			}
		} catch (Throwable e) {
			logger.error("Error evaluating expression: " + expr, e);
			throw new JspException("Error evaluating expression: " + expr
					+ " Cause: " + e.getMessage());
		}

		// 输出或保存结果
		if (saveAsAttr != null) {
			pageContext.setAttribute(saveAsAttr, i18nMsg);
		} else {
			try {
				writeMessage(i18nMsg);
			} catch (Throwable e) {
				throw new JspException(
						"Error writing message in tag. Expression: " + expr
								+ " Cause: " + e.getMessage());
			}
		}

		// Continue processing this page
		return (SKIP_BODY);
	}

	/**
	 * 暂时无用
	 * 
	 * @param in_expr
	 * @return
	 * @throws ELException
	 */
	private String evalEl(String in_expr) throws ELException {
		return (String) pageContext.getExpressionEvaluator().evaluate(in_expr,
				String.class, pageContext.getVariableResolver(), null);
	}

	/**
	 * Release all allocated resources.
	 */
	public void release() {
		super.release();
		expr = null;
		saveAsAttr = null;
		arg1 = null;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}

	public void setArgIsI18n(boolean arg1) {
		this.argIsI18n = arg1;
	}

	/**
	 * @param emtpyWhenNull
	 *            the emtpyWhenNull to set
	 */
	public void setEmtpyWhenNull(boolean emtpyWhenNull) {
		this.emtpyWhenNull = emtpyWhenNull;
	}

	/**
	 * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setExpr(String in_expr) {
		this.expr = in_expr == null ? null : in_expr.trim();
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setSaveAsAttr(String attrName) {
		this.saveAsAttr = attrName;
	}

	/**
	 * Write the message to the page.
	 * 
	 * @param msg
	 *            the message to write
	 * @throws IOException
	 *             if writing failed
	 */
	private void writeMessage(String msg) throws IOException {
		pageContext.getOut().write(msg);
	}
}
