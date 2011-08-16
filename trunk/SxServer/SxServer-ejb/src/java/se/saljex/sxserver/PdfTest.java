/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import se.saljex.sxlibrary.SXUtil;

/**
 *
 * @author Ulf
 */
public class PdfTest extends PdfHandler{
	    public PdfTest() throws DocumentException, IOException {
			super();
		}
	public ByteArrayOutputStream getPDF() throws IOException, DocumentException {
		initDocument();
		cb.saveState();
       cb.setFontAndSize(FontTimesBold, 18);

		addText(200, 200, "Auto");
//		writer.addJavaScript("var pp = this.getPrintParams();pp.interactive = pp.constants.interactionLevel.automatic;pp.printerName=\"Fax\";this.print(pp);",false);
//		writer.addJavaScript("var pp = this.getPrintParams();pp.interactive = pp.constants.interactionLevel.automatic;this.print(pp);",false);
//		writer.addJavaScript("var pp = this.getPrintParams();pp.interactive = pp.constants.interactionLevel.silent;pp.printerName=\"a\";this.print(pp);",false);
		writer.addJavaScript("var pp = this.getPrintParams();pp.printerName=\"Fax\";this.print({bUI: false, bSilent: true, bShrinkToFit: true, printParams: pp});",false);
//		writer.addJavaScript("this.print({bUI: false, bSilent: true, bShrinkToFit: true});",false);
//		writer.addJavaScript("this.closeDoc();");
		
		cb.restoreState();
		document.close();

		return super.baos;

	}

    private void addText(int x,int y, String t) {
        addText(x,y,t,PdfContentByte.ALIGN_LEFT);
    }
    private void addText(int x,int y, String t, int align) {
           cb.beginText();
           cb.showTextAligned(align, SXUtil.toStr(t),x,y,0);
         cb.endText();

    }


}
