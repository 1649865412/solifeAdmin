/**
 * 
 */
package com.cartmatic.estoresa.order.print;

import java.io.IOException;

import com.cartmatic.estore.core.util.ContextUtil;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author pengzhirong
 *
 */
public class PdfPageEvent extends PdfPageEventHelper {
	public PdfTemplate tpl;
    /** The font that will be used. */
    public BaseFont bf;
	
    public void onOpenDocument(PdfWriter writer, Document document) {
    	// initialization of the template
        tpl = writer.getDirectContent().createTemplate(100, 100);
        try {
        	bf = BaseFont.createFont("STSong-Light",	"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tpl.setFontAndSize(bf, 10);
    }
    
    protected Font getFontChinese(){
		BaseFont bf = null;
		try {
			bf = BaseFont.createFont("STSong-Light",	"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Font fontChinese = new Font(bf, 12, Font.NORMAL); 
		
		return fontChinese;
	}
	
    protected String getMessage(String key, Object... params){
		return ContextUtil.getSpringContext().getMessage(key, params, null);
	}
}
