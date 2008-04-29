/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;
import com.lowagie.text.*;//.text.Document;
import com.lowagie.text.pdf.*;
import java.io.*;
/**
 *
 * @author Ulf
 */
public class PdfHandler {
    protected PdfContentByte cb = null;
    protected Document document = null;
    protected BaseFont FontTimes = null;
    protected BaseFont FontTimesBold = null;
    protected BaseFont FontCourierBold = null;
    protected BaseFont FontCourier = null;
	protected ByteArrayOutputStream baos;
	protected PdfWriter writer;
	
	public PdfHandler() throws DocumentException, java.io.IOException {
		FontTimes = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		FontTimesBold = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		FontCourierBold = BaseFont.createFont(BaseFont.COURIER_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
		FontCourier = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	}
	
	protected void initDocument() throws DocumentException{
		document = new Document(PageSize.A4);
		baos = new ByteArrayOutputStream();
		writer = PdfWriter.getInstance(document, baos);
		document.open();
		cb = writer.getDirectContent(); 
		
	}
}
