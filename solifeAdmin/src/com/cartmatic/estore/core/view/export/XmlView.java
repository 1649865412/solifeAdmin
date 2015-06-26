/*
 * create 2006-8-28
 * 
 * 
 */

package com.cartmatic.estore.core.view.export;

import org.apache.commons.lang.StringEscapeUtils;
import org.displaytag.export.BaseExportView;
import org.displaytag.model.TableModel;

/**
 * @author Ryan
 * 
 * 
 */
public class XmlView extends BaseExportView {

	/**
	 * @see org.displaytag.export.BaseExportView#escapeColumnValue(java.lang.Object)
	 */
	protected String escapeColumnValue(Object value) {
		return StringEscapeUtils.escapeXml(value.toString());
	}

	/**
	 * @see org.displaytag.export.BaseExportView#getAlwaysAppendCellEnd()
	 */
	protected boolean getAlwaysAppendCellEnd() {
		return true;
	}

	/**
	 * @see org.displaytag.export.BaseExportView#getAlwaysAppendRowEnd()
	 */
	protected boolean getAlwaysAppendRowEnd() {
		return true;
	}

	/**
	 * @see org.displaytag.export.BaseExportView#getCellEnd()
	 */
	protected String getCellEnd() {
		return "</column>\n"; //$NON-NLS-1$
	}

	/**
	 * @see org.displaytag.export.BaseExportView#getCellStart()
	 */
	protected String getCellStart() {
		return "<column>"; //$NON-NLS-1$
	}

	/**
	 * @see org.displaytag.export.BaseExportView#getDocumentEnd()
	 */
	protected String getDocumentEnd() {
		return "</table>\n"; //$NON-NLS-1$
	}

	/**
	 * @see org.displaytag.export.BaseExportView#getDocumentStart()
	 */
	protected String getDocumentStart() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<table>\n"; //$NON-NLS-1$
	}

	/**
	 * @see org.displaytag.export.ExportView#getMimeType()
	 */
	public String getMimeType() {
		return "text/xml"; //$NON-NLS-1$
	}

	/**
	 * @see org.displaytag.export.BaseExportView#getRowEnd()
	 */
	protected String getRowEnd() {
		return "</row>\n"; //$NON-NLS-1$
	}

	/**
	 * @see org.displaytag.export.BaseExportView#getRowStart()
	 */
	protected String getRowStart() {
		return "<row>\n"; //$NON-NLS-1$
	}

	/**
	 * @see org.displaytag.export.BaseExportView#setParameters(TableModel,
	 *      boolean, boolean, boolean)
	 */
	public void setParameters(TableModel tableModel, boolean exportFullList,
			boolean includeHeader, boolean decorateValues) {
		super.setParameters(tableModel, exportFullList, includeHeader,
				decorateValues);
	}

}