/*
 * create 2006-8-28
 * 
 * 
 */

package com.cartmatic.estore.core.view.export;

import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.displaytag.Messages;
import org.displaytag.exception.BaseNestableJspTagException;
import org.displaytag.exception.SeverityEnum;
import org.displaytag.export.BinaryExportView;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.TagConstants;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Ryan
 * 
 * 
 */
public class PdfView implements BinaryExportView {

	/**
	 * Wraps IText-generated exceptions.
	 * 
	 * @author Fabrizio Giustina
	 * @version $Revision: 737 $ ($Author: fgiust $)
	 */
	static class PdfGenerationException extends BaseNestableJspTagException {

		/**
		 * D1597A17A6.
		 */
		private static final long	serialVersionUID	= 899149338534L;

		/**
		 * Instantiate a new PdfGenerationException with a fixed message and the
		 * given cause.
		 * 
		 * @param cause
		 *            Previous exception
		 */
		public PdfGenerationException(Throwable cause) {
			super(PdfView.class,
					Messages.getString("PdfView.errorexporting"), cause); //$NON-NLS-1$
		}

		/**
		 * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
		 */
		public SeverityEnum getSeverity() {
			return SeverityEnum.ERROR;
		}
	}

	private static Properties	userProperties	= new Properties();

	/**
	 * decorate export?
	 */
	private boolean				decorated;

	/**
	 * export full list?
	 */
	private boolean				exportFull;

	/**
	 * include header in export?
	 */
	private boolean				header;

	/**
	 * TableModel to render.
	 */
	private TableModel			model;

	private Rectangle			pageSize		= PageSize.A4;

	/**
	 * The default font used in the document.
	 */
	private Font				smallFont;

	/**
	 * This is the table, added as an Element to the PDF document. It contains
	 * all the data, needed to represent the visible table into the PDF
	 */
	private Table				tablePDF;

	/**
	 * @see org.displaytag.export.BinaryExportView#doExport(OutputStream)
	 */
	public void doExport(OutputStream out) throws JspException {
		try {
			// Initialize the table with the appropriate number of columns
			initTable();

			// Initialize the Document and register it with PdfWriter listener
			// and the OutputStream
			Document document = new Document(pageSize, 60, 60, 40, 40);
			document.addCreationDate();
			HeaderFooter footer = new HeaderFooter(new Phrase(
					TagConstants.EMPTY_STRING, smallFont), true);
			footer.setBorder(Rectangle.NO_BORDER);
			footer.setAlignment(Element.ALIGN_CENTER);

			PdfWriter.getInstance(document, out);

			// Fill the virtual PDF table with the necessary data
			generatePDFTable();
			document.open();
			document.setFooter(footer);
			document.add(this.tablePDF);
			document.close();

		} catch (Exception e) {
			throw new PdfGenerationException(e);
		}
	}

	/**
	 * Generates the header cells, which persist on every page of the PDF
	 * document.
	 * 
	 * @throws BadElementException
	 *             IText exception
	 */
	protected void generateHeaders() throws BadElementException {
		Iterator iterator = this.model.getHeaderCellList().iterator();

		while (iterator.hasNext()) {
			HeaderCell headerCell = (HeaderCell) iterator.next();

			String columnHeader = headerCell.getTitle();

			if (columnHeader == null) {
				columnHeader = StringUtils.capitalize(headerCell
						.getBeanPropertyName());
			}

			Cell hdrCell = getCell(columnHeader);
			hdrCell.setGrayFill(0.9f);
			hdrCell.setHeader(true);
			tablePDF.addCell(hdrCell);

		}
	}

	/**
	 * The overall PDF table generator.
	 * 
	 * @throws JspException
	 *             for errors during value retrieving from the table model
	 * @throws BadElementException
	 *             IText exception
	 */
	protected void generatePDFTable() throws JspException, BadElementException {
		if (this.header) {
			generateHeaders();
		}
		tablePDF.endHeaders();
		generateRows();
	}

	/**
	 * Generates all the row cells.
	 * 
	 * @throws JspException
	 *             for errors during value retrieving from the table model
	 * @throws BadElementException
	 *             errors while generating content
	 */
	protected void generateRows() throws JspException, BadElementException {
		// get the correct iterator (full or partial list according to the
		// exportFull field)
		RowIterator rowIterator = this.model.getRowIterator(this.exportFull);
		// iterator on rows
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			// iterator on columns
			ColumnIterator columnIterator = row.getColumnIterator(this.model
					.getHeaderCellList());

			while (columnIterator.hasNext()) {
				Column column = columnIterator.nextColumn();

				// Get the value to be displayed for the column
				Object value = column.getValue(this.decorated);

				Cell cell = getCell(ObjectUtils.toString(value));
				tablePDF.addCell(cell);
			}
		}
	}

	/**
	 * Returns a formatted cell for the given value.
	 * 
	 * @param value
	 *            cell value
	 * @return Cell
	 * @throws BadElementException
	 *             errors while generating content
	 */
	private Cell getCell(String value) throws BadElementException {
		Cell cell = new Cell(new Chunk(StringUtils.trimToEmpty(value),
				smallFont));
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setLeading(8);
		return cell;
	}

	/**
	 * @see org.displaytag.export.BaseExportView#getMimeType()
	 * @return "application/pdf"
	 */
	public String getMimeType() {
		return "application/pdf"; //$NON-NLS-1$
	}

	/**
	 * Initialize the main info holder table.
	 * 
	 * @throws BadElementException
	 *             for errors during table initialization
	 */
	protected void initTable() throws BadElementException {
		// TableProperties.setUserProperties() tp = new TableProperties();
		if (userProperties.isEmpty()) {
			try {
				userProperties.load(PdfView.class.getResourceAsStream("/"
						+ TableProperties.DEFAULT_FILENAME));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		tablePDF = new Table(this.model.getNumberOfColumns());
		tablePDF.setAlignment(Element.ALIGN_TOP);
		tablePDF.setCellsFitPage(true);
		tablePDF.setWidth(100);
		tablePDF.setPadding(2);
		tablePDF.setSpacing(0);

		// init param from properties
		String p_pageSize = null;
		String p_pageRotate = null;
		String p_font = null;
		int fontSize = 7;
		boolean embeded = false;
		if (userProperties != null) {
			p_pageSize = (String) userProperties.get("pdf.style.pageSize");
			p_pageRotate = (String) userProperties.get("pdf.style.pageRotate");
			p_font = (String) userProperties.get("pdf.style.font");
			String p_fontSize = (String) userProperties
					.get("pdf.style.fontSize");
			try {
				if (p_fontSize != null) {
					fontSize = new Integer(p_fontSize).intValue();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			String p_embeded = (String) userProperties.get("pdf.style.embeded");
			if (p_embeded != null && p_embeded.equals("true")) {
				embeded = true;
			}
		}
		// smallFont = FontFactory.getFont(FontFactory.HELVETICA, 7,
		// Font.NORMAL, new Color(0, 0, 0));
		// init Font.
		try {

			BaseFont bfChinese = null;
			if (p_font == null) // default use asia font [iTextAsian.jar]
			{
				bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
						embeded);
			} else // use customer font.
			{
				bfChinese = BaseFont.createFont(p_font, BaseFont.IDENTITY_H,
						embeded);
			}
			smallFont = new Font(bfChinese, fontSize, Font.NORMAL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// init pageSize available is [A2-A5|B2-B5]
		if (p_pageSize != null) {
			if ("A2".equals(p_pageSize)) {
				pageSize = PageSize.A2;
			} else if ("A3".equals(p_pageSize)) {
				pageSize = PageSize.A3;
			} else if ("A4".equals(p_pageSize)) {
				pageSize = PageSize.A4;
			} else if ("A5".equals(p_pageSize)) {
				pageSize = PageSize.A5;
			} else if ("B2".equals(p_pageSize)) {
				pageSize = PageSize.B2;
			} else if ("B3".equals(p_pageSize)) {
				pageSize = PageSize.B3;
			} else if ("B4".equals(p_pageSize)) {
				pageSize = PageSize.B4;
			} else if ("B5".equals(p_pageSize)) {
				pageSize = PageSize.B5;
			}
		}
		if (p_pageRotate != null && p_pageRotate.equals("1")) {
			pageSize = pageSize.rotate();
		}
	}

	/**
	 * @see org.displaytag.export.ExportView#setParameters(TableModel, boolean,
	 *      boolean, boolean)
	 */
	public void setParameters(TableModel tableModel, boolean exportFullList,
			boolean includeHeader, boolean decorateValues) {
		this.model = tableModel;
		this.exportFull = exportFullList;
		this.header = includeHeader;
		this.decorated = decorateValues;
	}
}