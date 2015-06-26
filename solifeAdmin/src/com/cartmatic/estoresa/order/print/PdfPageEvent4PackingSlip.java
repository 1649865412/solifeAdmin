package com.cartmatic.estoresa.order.print;

import java.util.Date;

import com.cartmatic.estore.common.util.DateUtil;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PdfPageEvent4PackingSlip extends PdfPageEvent {
	
	public void onStartPage(PdfWriter writer, Document document) {
			
			try {
	            Rectangle page = document.getPageSize();
	            PdfPTable head = new PdfPTable(new float[]{60, 40});
	            head.getDefaultCell().setBorderWidth(0);
	            head.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
	            head.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
	            Font font = getFontChinese();
	            font.setSize(10);
	            head.addCell(new Phrase(this.getMessage("order.pdf.packingSlip"), font));
	            head.addCell(new Phrase(this.getMessage("order.pdf.dateTime")+DateUtil.convertDateTimeToString(new Date()), font));
	            head.addCell("\n\n");
	            head.addCell("\n\n");
	            head.writeSelectedRows(0, -1, document.leftMargin(), page.getHeight() - document.topMargin() + head.getTotalHeight(),
	                writer.getDirectContent());
	        } catch (Exception e) {
	            throw new ExceptionConverter(e);
	        }
			
	//		HeaderFooter header = new HeaderFooter( new Phrase(("CartMatic发货单"), getFontChinese()), false); 
	//		header.setBorder(0);
	//		document.setHeader(header);
	}

	public void onEndPage(PdfWriter writer, Document document) {
//        PdfContentByte cb = writer.getDirectContent();
//        cb.saveState();
//        // compose the footer
//                
//        String text = getMessage("order.pdf.pageInfo", writer.getPageNumber());
//        float textSize = bf.getWidthPoint(text, 10);
//        float textBase = document.bottom() - 45;
//        cb.beginText();
//        cb.setFontAndSize(bf, 10);
//        cb.setTextMatrix(document.left(), textBase);
//        cb.showText(text);
//        cb.endText();
//        cb.addTemplate(tpl, document.left() + textSize, textBase);
//        cb.saveState();
    }
	
	public void onCloseDocument(PdfWriter writer, Document document) {
//		tpl.beginText();
//		tpl.setFontAndSize(bf, 10);
//		tpl.setTextMatrix(0, 0);
//		tpl.showText(" " + Integer.toString(writer.getPageNumber() - 1) + " "+ getMessage("order.pdf.pageInfo1", null));
//		tpl.endText();
    }

		
}
