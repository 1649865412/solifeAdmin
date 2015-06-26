package com.cartmatic.estore.core.taglib;

import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorResources;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.MessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springmodules.validation.commons.ValidatorFactory;



/**
 * 参考 org.springmodules.validation.commons.taglib.JavascriptValidatorTag
 * 重构了原来的tag,改为自己的实现.现只是根据validation.xml中的form的配置来实现新框架的校验.
 * 依赖validation.js
 * 
 * @author Ryan
 *
 */
public class CartmaticJsValidatorTag extends BodyTagSupport
{
    protected RequestContext requestContext;

    /**
     * The name of the form that corresponds with the action name
     * in struts-config.xml. Specifying a form name places a
     * &lt;script&gt; &lt;/script&gt; around the javascript.
     */
    protected String formName = null;

    /**
     * The line ending string.
     */
    protected static String lineEnd = System.getProperty("line.separator");

    /**
     * The current page number of a multi-part form.
     * Only valid when the formName attribute is set.
     */
    protected int page = 0;

    /**
     * This will be used as is for the JavaScript validation method name if it has a value.  This is
     * the method name of the main JavaScript method that the form calls to perform validations.
     */
    protected String methodName = null;

    /**
     * The static JavaScript methods will only be printed if this is set to "true".
     */
    protected String staticJavascript = "true";

    /**
     * The dynamic JavaScript objects will only be generated if this is set to "true".
     */
    protected String dynamicJavascript = "true";

    /**
     * The src attribute for html script element (used to include an external script
     * resource). The src attribute is only recognized
     * when the formName attribute is specified.
     */
    protected String src = null;

    /**
     * The JavaScript methods will enclosed with html comments if this is set to "true".
     */
    protected String htmlComment = "true";

    /**
     * The generated code should be XHTML compliant when "true". When true,
     * this setting prevents the htmlComment setting from having an effect.
     */
    protected String xhtml = "false";

    /**
     * Hide JavaScript methods in a CDATA section for XHTML when "true".
     */
    protected String cdata = "true";

    private String htmlBeginComment = "\n";

    private String htmlEndComment = "\n";

    /**
     * Gets the key (form name) that will be used
     * to retrieve a set of validation rules to be
     * performed on the bean passed in for validation.
     */
    public String getFormName() {
        return formName;
    }

    /**
     * Sets the key (form name) that will be used
     * to retrieve a set of validation rules to be
     * performed on the bean passed in for validation.
     * Specifying a form name places a
     * &lt;script&gt; &lt;/script&gt; tag around the javascript.
     */
    public void setFormName(String formName) {
        this.formName = formName;
    }

    /**
     * Gets the current page number of a multi-part form.
     * Only field validations with a matching page numer
     * will be generated that match the current page number.
     * Only valid when the formName attribute is set.
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the current page number of a multi-part form.
     * Only field validations with a matching page numer
     * will be generated that match the current page number.
     * Only valid when the formName attribute is set.
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Gets the method name that will be used for the Javascript
     * validation method name if it has a value.  This overrides
     * the auto-generated method name based on the key (form name)
     * passed in.
     */
    public String getMethod() {
        return methodName;
    }

    /**
     * Sets the method name that will be used for the Javascript
     * validation method name if it has a value.  This overrides
     * the auto-generated method name based on the key (form name)
     * passed in.
     */
    public void setMethod(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Gets whether or not to generate the static
     * JavaScript.  If this is set to 'true', which
     * is the default, the static JavaScript will be generated.
     */
    public String getStaticJavascript() {
        return staticJavascript;
    }

    /**
     * Sets whether or not to generate the static
     * JavaScript.  If this is set to 'true', which
     * is the default, the static JavaScript will be generated.
     */
    public void setStaticJavascript(String staticJavascript) {
        this.staticJavascript = staticJavascript;
    }

    /**
     * Gets whether or not to generate the dynamic
     * JavaScript.  If this is set to 'true', which
     * is the default, the dynamic JavaScript will be generated.
     */
    public String getDynamicJavascript() {
        return dynamicJavascript;
    }

    /**
     * Sets whether or not to generate the dynamic
     * JavaScript.  If this is set to 'true', which
     * is the default, the dynamic JavaScript will be generated.
     */
    public void setDynamicJavascript(String dynamicJavascript) {
        this.dynamicJavascript = dynamicJavascript;
    }

    /**
     * Gets the src attribute's value when defining
     * the html script element.
     */
    public String getSrc() {
        return src;
    }

    /**
     * Sets the src attribute's value when defining
     * the html script element. The src attribute is only recognized
     * when the formName attribute is specified.
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * Gets whether or not to delimit the
     * JavaScript with html comments.  If this is set to 'true', which
     * is the default, the htmlComment will be surround the JavaScript.
     */
    public String getHtmlComment() {
        return htmlComment;
    }

    /**
     * Sets whether or not to delimit the
     * JavaScript with html comments.  If this is set to 'true', which
     * is the default, the htmlComment will be surround the JavaScript.
     */
    public void setHtmlComment(String htmlComment) {
        this.htmlComment = htmlComment;
    }

    /**
     * Returns the cdata setting "true" or "false".
     *
     * @return String - "true" if JavaScript will be hidden in a CDATA section
     */
    public String getCdata() {
        return cdata;
    }

    /**
     * Sets the cdata status.
     *
     * @param cdata The cdata to set
     */
    public void setCdata(String cdata) {
        this.cdata = cdata;
    }

    /**
     * Gets whether or not to generate the xhtml code.
     * If this is set to 'true', which is the default,
     * XHTML will be generated.
     */
    public String getXhtml() {
        return xhtml;
    }

    /**
     * Sets whether or not to generate the xhtml code.
     * If this is set to 'true', which is the default,
     * XHTML will be generated.
     */
    public void setXhtml(String xhtml) {
        this.xhtml = xhtml;
    }

    /**
     * Render the JavaScript for to perform validations based on the form name.
     *
     * @throws javax.servlet.jsp.JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        StringBuffer results = new StringBuffer();

        Locale locale = RequestContextUtils.getLocale((HttpServletRequest) pageContext.getRequest());

        ValidatorResources resources = getValidatorResources();

        Form form = resources.getForm(locale, formName);
        if (form != null)
        {
            results.append(getJavascriptBegin("validateForm(form)"));
            // Get List of actions for this Form
            for (Iterator i = form.getFields().iterator(); i.hasNext();) 
            {
                Field field = (Field) i.next();
                field.getDependencyList();
                //实际生成的效查是
                //  applyValidate($("testInput2"),"require,integer,maxValue=200");
                results.append("   applyValidate($(\""+ field.getKey() +"\"),\"");
                for (Iterator x = field.getDependencyList().iterator(); x.hasNext();) {
                    String depend =  (String) x.next();
                    results.append(depend);
                    int args_count = 0;
                    org.apache.commons.validator.Arg[] args = field.getArgs(depend);
                    for (int arg_i=1; arg_i < args.length; arg_i++)
                    {
                        if(args[arg_i] != null)
                            args_count ++;
                    }
                    //只有一个参数，就像maxlenth=100这种。
                    if (args_count == 1)
                    {
                        results.append("=");
                        for (int arg_i=1; arg_i < args.length; arg_i++)
                        {
                            if(args[arg_i] != null)
                                results.append(args[arg_i].getKey());                         
                        }                        
                    }
                    //多于1个参数，如floatRange=[0,1000]
                    else if (args_count >1)
                    {
                        results.append("=[");
                        for (int arg_i=1; arg_i < args.length; arg_i++)
                        {
                            if(args[arg_i] != null)
                                results.append(args[arg_i].getKey());
                            //如果还有下一个的话，就加上"-"
                            if (arg_i < args.length -1 && args[arg_i+1] != null)
                                results.append("-");
                        }
                        results.append("]");
                    }                    
                    
                    if (x.hasNext())
                        results.append(",");
                }
                results.append("\");\n");
            }
        }
        results.append(getJavascriptEnd());
        JspWriter writer = pageContext.getOut();
        try {
            writer.print(results.toString());
        }
        catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return (SKIP_BODY);

    }

    /**
     * Release any acquired resources.
     */
    public void release() {
        super.release();
        //      bundle = Globals.MESSAGES_KEY;
        formName = null;
        page = 0;
        methodName = null;
        staticJavascript = "true";
        dynamicJavascript = "true";
        htmlComment = "true";
        cdata = "true";
        src = null;
    }

    /**
     * Returns the opening script element and some initial javascript.
     */
    protected String getJavascriptBegin(String methods) {
        StringBuffer sb = new StringBuffer();
        String name =
            formName.substring(0, 1).toUpperCase()
                + formName.substring(1, formName.length());

        sb.append(this.getStartElement());

        if (this.isXhtml() && "true".equalsIgnoreCase(this.cdata)) {
            sb.append("//<![CDATA[\r\n");
        }

        if (!this.isXhtml() && "true".equals(htmlComment)) {
            sb.append(htmlBeginComment);
        }
        sb.append("\n     var bCancel = false; \n\n");

        if (methodName == null || methodName.length() == 0) {
            sb.append("    function validate"  + name + "(form) {\n");
        } else {
            sb.append(" function " + methodName + "(form) {                                                                   \n");
        }
        sb.append("  if (bCancel) \n");
        sb.append("    return true; \n");
        sb.append("  else if (validateForm(form)) \n return true; \n");
        sb.append("  else \n  {alert(__vaMsg.notPass); \n    return false; }\n");
        sb.append("} \n\n");

        return sb.toString();
    }

    protected String getJavascriptStaticMethods(ValidatorResources resources) {
        StringBuffer sb = new StringBuffer();

        sb.append("\n\n");
        Iterator actions = resources.getValidatorActions().values().iterator();
        while (actions.hasNext()) {
            ValidatorAction va = (ValidatorAction) actions.next();
            if (va != null) {
                String javascript = va.getJavascript();
                if (javascript != null && javascript.length() > 0) {
                    sb.append(javascript + "\n");
                }
            }
        }

        return sb.toString();
    }

    /**
     * Returns the closing script element.
     */
    protected String getJavascriptEnd() {
        StringBuffer sb = new StringBuffer();

        sb.append("\n");
        if (!this.isXhtml() && "true".equals(htmlComment)) {
            sb.append(htmlEndComment);
        }

        if (this.isXhtml() && "true".equalsIgnoreCase(this.cdata)) {
            sb.append("//]]>\r\n");
        }
        sb.append("</script>\n\n");

        return sb.toString();
    }

   

    /**
     * Constructs the beginning &lt;script&gt; element depending on xhtml status.
     */
    private String getStartElement() {
        StringBuffer start = new StringBuffer("<script type=\"text/javascript\" defer=\"true\"");

        // there is no language attribute in xhtml
        //if (!this.isXhtml()) {
        //    start.append(" language=\"Javascript1.1\"");
        //}

        if (this.src != null) {
            start.append(" src=\"" + src + "\"");
        }

        start.append("> \n");
        return start.toString();
    }

    /**
     * Returns true if this is an xhtml page.
     */
    private boolean isXhtml() {
        return "true".equalsIgnoreCase(xhtml);
    }

    /**
     * Use the application context itself for default message resolution.
     */
    private MessageSource getMessageSource() {
        try {
            this.requestContext =
                new RequestContext((HttpServletRequest) this.pageContext.getRequest());
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        catch (Exception ex) {
            pageContext.getServletContext().log("Exception in custom tag", ex);
        }
        return requestContext.getWebApplicationContext();
    }

    /**
     * Get the validator resources from a ValidatorFactory defined in the
     * web application context or one of its parent contexts.
     * The bean is resolved by type (org.springmodules.commons.validator.ValidatorFactory).
     *
     * @return ValidatorResources from a ValidatorFactory
     */
    private ValidatorResources getValidatorResources() {
        WebApplicationContext ctx = (WebApplicationContext)
            pageContext.getRequest().getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        if (ctx == null) {
            // look in main application context (i.e. applicationContext.xml)
            ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
        }
        ValidatorFactory factory = (ValidatorFactory)
            BeanFactoryUtils.beanOfTypeIncludingAncestors(ctx, ValidatorFactory.class, true, true);
        return factory.getValidatorResources();
    }
}
