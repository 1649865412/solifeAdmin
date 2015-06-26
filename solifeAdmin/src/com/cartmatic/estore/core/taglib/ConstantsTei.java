
package com.cartmatic.estore.core.taglib;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cartmatic.estore.Constants;

/**
 * Implementation of <code>TagExtraInfo</code> for the <b>constants</b> tag,
 * identifying the scripting object(s) to be made visible.
 * 
 * @author Matt Raible
 * @version $Revision: 1.1 $ $Date: 2006/04/21 02:01:43 $
 */
public class ConstantsTei extends TagExtraInfo {
	private final Log	log	= LogFactory.getLog(ConstantsTei.class);

	/**
	 * Return information about the scripting variables to be created.
	 */
	public VariableInfo[] getVariableInfo(TagData data) {
		// loop through and expose all attributes
		List vars = new ArrayList();

		try {
			String clazz = data.getAttributeString("className");

			if (clazz == null) {
				clazz = Constants.class.getName();
			}

			Class c = Class.forName(clazz);

			// if no var specified, get all
			if (data.getAttributeString("var") == null) {
				Field[] fields = c.getDeclaredFields();

				AccessibleObject.setAccessible(fields, true);

				for (int i = 0; i < fields.length; i++) {
					vars.add(new VariableInfo(fields[i].getName(),
							"java.lang.String", true, VariableInfo.AT_END));
				}
			} else {
				String var = data.getAttributeString("var");
				vars.add(new VariableInfo(c.getField(var).getName(),
						"java.lang.String", true, VariableInfo.AT_END));
			}
		} catch (Exception cnf) {
			log.error(cnf.getMessage());
			cnf.printStackTrace();
		}

		return (VariableInfo[]) vars.toArray(new VariableInfo[] {});
	}
}
