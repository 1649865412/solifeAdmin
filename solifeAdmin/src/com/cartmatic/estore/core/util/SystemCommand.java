/*
 * Created on Aug 29, 2006
 * 
 */

package com.cartmatic.estore.core.util;


/**
 * @author Ryan
 * 
 */
@Deprecated
public class SystemCommand {
	public static SystemCommand parse(String str) {
		String[] temp = StringUtil.toArrayByDel(str, ";");
		String[] params = null;
		if (temp.length > 1) {
			params = StringUtil.toArrayByDel(temp[1], ",");
		}

		SystemCommand sc = new SystemCommand(temp[0], params);
		return sc;
	}

	private String		command;

	private String[]	params;

	public SystemCommand(String command, String[] params) {
		this.command = command;
		this.params = params;
	}

	public String getCommand() {
		return command;
	}

	public String[] getParams() {
		return params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer(command);
		if (params != null && params.length > 0) {
			buf.append(";");
			for (int i = 0; i < params.length; i++) {
				buf.append(params[i]).append(",");
			}
		}

		return buf.toString();
	}
}
