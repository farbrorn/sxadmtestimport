
package se.saljex.sxserver;

import se.saljex.sxserver.tables.TableBilder;
import com.lowagie.text.*;//.text.Document;
import com.lowagie.text.pdf.*;
import java.io.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import se.saljex.sxserver.tables.TableSteprodukt;
import se.saljex.sxserver.tables.TableSteproduktnot;

/**
 *
 * @author Ulf
 */
public class PdfSteServiceorder extends PdfHandler {

	private Image logoImage = null;
	private TableSteprodukt steprod;
	private TableSteproduktnot stenot;
//	private TableFuppg fup;
	private TableBilder bil;
//	private TableKund kun;
    private EntityManager em;


    public PdfSteServiceorder(EntityManager e) throws DocumentException, IOException {
		super();
		em = e;

		//Hämta bild från databas
		Query q = em.createQuery("SELECT t FROM TableBilder t WHERE t.namn = ?1");
		String bildNamn = SXUtil.getSXReg(em,"BildLogoSteService");
		if (bildNamn.isEmpty()) { throw new DocumentException("Kan inte hitta logobildnamn i tabell sxreg. Nyckel: BildLogoSteService"); }
		q.setParameter(1, bildNamn);
		try {
			bil = (TableBilder)q.getSingleResult();
		} catch (NoResultException en) { throw new DocumentException("Kan inte hitta logobilden - " + bildNamn + " - i tabell bilddata. Kolla värdet av nyckel BildLogoSteService i sxreg"); }

		logoImage = Image.getInstance(bil.getBilddata());

    }


	public ByteArrayOutputStream getPDF(int id) throws IOException, DocumentException {
		stenot = em.find(TableSteproduktnot.class, id);
		steprod = em.find(TableSteprodukt.class, stenot.getSn());


		initDocument();
		cb.saveState();
		printHeader();

		final int startY = 595;
		final int stopY = 110;
		final int detailArtHeight = 12;
		final int detailTextHeight = 12;
		int offsetY = startY + detailArtHeight;			//På första sidan ska måste vi öka med denna för att offsetminskningen sker före
														//första raden skrivs ut. Efterföljande sidor kan dock starta på startY

		// Artikelraderna är utskrivna
       cb.setFontAndSize(FontCourier, 8);

		 addText(50, 600, stenot.getFraga());
		cb.restoreState();
		document.close();

		return super.baos;

	}




	private void drawRec(int x, int y, int w, int h, int r) throws DocumentException {
		//Rita en rektangel med kurvade hörn.
		// x,y = Startposition, w = width, h = height, r = radie på hörnet
		cb.moveTo(x,y+r);
		cb.lineTo(x, y+h-r);
		cb.curveTo(x, y+h, x+r, y+h);
		cb.lineTo(x-r+w, y+h);
		cb.curveTo(x+w, y+h, x+w, y+h-r);
		cb.lineTo(x+w, y+r);
		cb.curveTo(x+w, y, x+w-r, y);
		cb.lineTo(x+r, y);
		cb.curveTo(x, y, x, y+r);
	}

    private void printHeader() throws DocumentException ,IOException  {

		cb.restoreState();
		logoImage = Image.getInstance(logoImage);
		logoImage.setAbsolutePosition(40, 800);
		logoImage.scaleToFit(110,60);
		document.add(logoImage);
		cb.saveState();
       cb.setLineWidth((float)0.5);

       cb.newPath();
       drawRec(47, 159, 510, 456, 2);
//       drawRec(47, 680, 199, 64,2);
//       drawRec(389, 774, 165, 34, 2);
//       drawRec(325, 680, 199, 64, 2);
	   drawRec(47, 91, 510, 64, 2);

		cb.moveTo(150,140);
		cb.lineTo(200, 140);
		cb.moveTo(120,100);
		cb.lineTo(220, 100);
		cb.moveTo(340,100);
		cb.lineTo(440, 100);

		cb.closePathStroke();


       cb.setFontAndSize(FontTimesBold, 18);
       addText(200,806,"Stiebel Eltron Serviceorder");

       cb.setFontAndSize(FontTimesBold, 6);
       addText(325,656,"Vår ref.:");

       addText(396,775,"Serviceorder nr:");

       addText(396,765,"Datum:");

       addText(47,740,"Modell:");
       addText(47,730,"Serienr:");
       addText(47,700,"Installationsadress:");
       addText(47,660,"Tel:");
       addText(47,650,"Mobil:");
       addText(325,740,"Installerad av:");
       //addText(325,622,"Efter kredittiden debiteras ränta med " + of1.getRanta() + " %");
       cb.setFontAndSize(FontTimes, 8);
       addText(450,775,stenot.getId().toString());
       addText(450,765,SXUtil.getFormatDate(stenot.getCrdt()));
       addText(358,656,stenot.getAnvandare());

       addText(100,740,steprod.getModell());
       addText(100,730,steprod.getSn());


       addText(100,700,steprod.getNamn());
		 addText(100,690,steprod.getAdr1());
       addText(100,680,steprod.getAdr2());
       addText(100,670,steprod.getAdr3());
       addText(100,660,steprod.getTel());
       addText(100,650,steprod.getMobil());

       addText(375,740,steprod.getInstallatornamn());



       addText(55,140,"Arbetet slutfört datum");
       addText(55,100,"Signatur slutkund");
       addText(250,100,"Signatur serviceombud");


//       addText(441,785,of1.getKundnr());
//       addText(441,800,""+of1.getOffertnr());
 //      addText(332,730,of1.getNamn());
//       addText(332,720,of1.getAdr1());
//       addText(332,710,of1.getAdr2());
//       addText(332,700,of1.getAdr3());
//       addText(55,622,of1.getMarke());

//       addText(414,633,SXUtil.getFormatDate(SXUtil.addDate(of1.getDatum(), of1.getKtid() )));


    }

    private void addText(int x,int y, String t) {
        addText(x,y,t,PdfContentByte.ALIGN_LEFT);
    }
    private void addText(int x,int y, String t, int align) {
           cb.beginText();
           cb.showTextAligned(align, t,x,y,0);
         cb.endText();

    }

}
