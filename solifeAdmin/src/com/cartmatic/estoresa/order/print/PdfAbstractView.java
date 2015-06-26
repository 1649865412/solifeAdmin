/**
 * 
 */
package com.cartmatic.estoresa.order.print;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.cartmatic.estore.core.util.ContextUtil;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author pengzhirong
 *
 */
public abstract class PdfAbstractView extends AbstractView {

	protected PdfWriter writer = null;
	
	protected Font fontChinese = null; 
	
	protected String getMessage(String key){
		return ContextUtil.getSpringContext().getMessage(key, null, null);
	}
	
	protected PdfPCell getThCell(String thText) throws BadElementException{
		PdfPCell cell = new PdfPCell();
		cell.setLeading(30f, 20f);
		cell.setBorderWidth(0);
		cell.setBorderWidthTop(1.5f);
		cell.setBorderWidthBottom(1.5f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.addElement(new Phrase(thText, fontChinese));
		return cell;
	}
	
	protected PdfPCell getTdCell(String tdText, boolean lastOne) throws BadElementException{
		PdfPCell cell = new PdfPCell();
		cell.setBorderWidth(0);
		if(lastOne)
			cell.setBorderWidthBottom(1f);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		
		cell.addElement(new Phrase(tdText, fontChinese));
		return cell;
	}
	
	public PdfAbstractView() {
		setContentType("application/pdf");
	}

	protected boolean generatesDownloadContent() {
		return true;
	}

    protected final void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        java.io.ByteArrayOutputStream baos = createTemporaryOutputStream();
        Document document = newDocument();
        writer = newWriter(document, baos);
        prepareWriter(model, writer, request);
        buildPdfMetadata(model, document, request);

        //添加页面事件
        addPageEventOnDocumentOpen();
        
        document.open();
        buildPdfDocument(model, document, writer, request, response);
        document.close();
        writeToResponse(response, baos);
    }
    
    protected abstract void addPageEventOnDocumentOpen();
    
    protected abstract void buildPdfDocument(Map model, Document document,    
            PdfWriter writer, HttpServletRequest request,    
            HttpServletResponse response)throws Exception;
    
    protected Document newDocument() {
		return new Document(PageSize.A4, 15f, 15f, 50f, 70f);
	}

	protected PdfWriter newWriter(Document document, OutputStream os)
			throws DocumentException {
		return PdfWriter.getInstance(document, os);
	}

	protected void prepareWriter(Map model, PdfWriter writer,
			HttpServletRequest request) throws DocumentException {
		writer.setViewerPreferences(getViewerPreferences());
	}

	protected int getViewerPreferences() {
		return 2053;
	}

	protected void buildPdfMetadata(Map map, Document document1,
			HttpServletRequest httpservletrequest) {
	}

}
