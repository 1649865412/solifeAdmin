/**
 * 
 */

package com.cartmatic.estore.core.exception;

/**
 * 支持比较友好的错误信息的异常。msg相当于给开发看的信息，errorCode对应给用户看的信息，可支持I18n（抽出来管理）以及参数。
 * 
 * @author Ryan
 * 
 */
public class ApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -14561090463768497L;

	private Object[]			args;

	private String				errorCode;

	public ApplicationException(String msg, String errorCode, Object... args) {
		super(msg);
		this.errorCode = errorCode;
		this.args = args;
	}

	public ApplicationException(String msg, Throwable t) {
		super(msg, t);
	}

	public ApplicationException(String msg, Throwable t, String errorCode,
			Object... args) {
		super(msg, t);
		this.errorCode = errorCode;
		this.args = args;
	}

	/**
	 * @return the args
	 */
	public Object[] getArgs() {
		return this.args;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return this.errorCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.getMessage()).append(" Error Code: [")
				.append(errorCode).append("], arguments: [");
		if (args != null)
		{
			for (int i = 0; i < args.length; i++) {
				sb.append(args[i].toString()).append(", ");
			}
		}
		sb.append("].");
		return sb.toString();
	}
}
