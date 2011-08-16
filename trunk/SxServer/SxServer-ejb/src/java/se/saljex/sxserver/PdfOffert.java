
package se.saljex.sxserver;

import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxserver.tables.TableArtikel;
import se.saljex.sxserver.tables.TableKund;
import se.saljex.sxserver.tables.TableFuppg;
import se.saljex.sxserver.tables.TableBilder;
import se.saljex.sxserver.tables.TableOffert2;
import se.saljex.sxserver.tables.TableOffert1;
import com.lowagie.text.*;//.text.Document;
import com.lowagie.text.pdf.*;
import javax.servlet.http.*;
import java.io.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Ulf
 */
public class PdfOffert extends PdfHandler {

	private Image logoImage = null; 
	private TableOffert1 of1;
	private TableOffert2 of2;
	private TableArtikel art;
	private TableFuppg fup;
	private TableBilder bil;
	private TableKund kun;
    private EntityManager em;
	private double totalsummaNetto=0;
	private double totalsummaMoms=0;
    private double momssats;
	private boolean skrivArtikelradInkMoms=false;
	
    
    public PdfOffert(EntityManager e) throws DocumentException, IOException {
		super();
		em = e;
		
		//Hämta bild från databas
		Query q = em.createQuery("SELECT t FROM TableBilder t WHERE t.namn = ?1");
		String bildNamn = ServerUtil.getSXReg(em,"BildLogo");
		if (bildNamn.isEmpty()) { throw new DocumentException("Kan inte hitta logobildnamn i tabell sxreg. Nyckel: BildLogo"); }
		q.setParameter(1, bildNamn);
		try {
			bil = (TableBilder)q.getSingleResult();
		} catch (NoResultException en) { throw new DocumentException("Kan inte hitta logobilden - " + bildNamn + " - i tabell bilddata. Kolla värdet av nyckel BildLogo i sxreg"); }
		
		logoImage = Image.getInstance(bil.getBilddata());

    }
    
	public ByteArrayOutputStream getPDF(int offertnr) throws IOException, DocumentException {
		return getPDF(offertnr, false);
	}
	
	public ByteArrayOutputStream getPDF(int offertnr, boolean skrivArtikelradInkMoms) throws IOException, DocumentException {
		this.skrivArtikelradInkMoms=skrivArtikelradInkMoms;
		Query q;
		Query q2;
		q = em.createQuery("select f from TableOffert1 f WHERE f.offertnr = " + offertnr);
		//q.setParameter(1, faktnr);
		of1 = (TableOffert1)q.getSingleResult();		//throws NoReultException
		
		q = em.createQuery("select f FROM TableFuppg f");
		fup = (TableFuppg)q.getSingleResult();



	   if (of1.getMoms() == 0) momssats = 0;
	   else if (of1.getMoms() == 1) momssats = fup.getMoms1();
	   else if (of1.getMoms() == 2) momssats = fup.getMoms2();
	   else if (of1.getMoms() == 3) momssats = fup.getMoms3();
	   else momssats = fup.getMoms1();

		initDocument();
		cb.saveState();
		printHeader();
		q = em.createNamedQuery("TableOffert2.findByOffertnr");
		q.setParameter("offertnr", offertnr);
		//q = em.createQuery("select f from TableBest2 f WHERE f.tableBest2PK.bestnr = " + bestnr + " order by f.tableBest2PK.rad");
		java.util.List<TableOffert2> lof2 = q.getResultList();
		
		final int startY = 595;
		final int stopY = 110;
		final int detailArtHeight = 12;
		final int detailTextHeight = 12;
		int offsetY = startY + detailArtHeight;			//På första sidan ska måste vi öka med denna för att offsetminskningen sker före 
														//första raden skrivs ut. Efterföljande sidor kan dock starta på startY
		totalsummaNetto=0;
		totalsummaMoms=0;
		for (TableOffert2 o2 : lof2) {
			of2 = o2;	//För att den globala variabeln ska fungera
			if (!of2.getText().isEmpty() || (of2.getArtnr().isEmpty() && of2.getNamn().isEmpty())) {
				// Skriv ut textrad
				offsetY = checkNewPage(offsetY, startY, stopY, detailTextHeight);
				printDetailTextrad(offsetY);
			} else {
				offsetY = checkNewPage(offsetY, startY, stopY, detailArtHeight);
				printDetailArt(offsetY);
			}
			totalsummaNetto = totalsummaNetto+of2.getSumma();
			totalsummaMoms = totalsummaMoms + (SXUtil.getRoundedDecimal(of2.getSumma() * momssats /100));

		}
		printTotalSumma();
		// Artikelraderna är utskrivna
		cb.restoreState();
		document.close();

		return super.baos;
     
	}

	private int checkNewPage(int offsetY, int startY, int stopY, int detailHeight) throws DocumentException, IOException {
			offsetY -= detailHeight;
			if (offsetY < stopY) {
				printDetailTransport(offsetY + detailHeight);
				offsetY = startY;
				cb.restoreState();
				document.newPage();
				cb.saveState();
				printHeader();
			}
			return offsetY;
	}
	private void printTotalSumma() throws DocumentException  {
       cb.setFontAndSize(FontCourier, 8);



       addText(394,146,"Momssats %:");
	   addText(552,146,SXUtil.getFormatNumber(momssats) ,PdfContentByte.ALIGN_RIGHT );

	   if (!skrivArtikelradInkMoms) {
		   addText(394,136,"Netto:");
		   addText(552,136, SXUtil.getFormatNumber(totalsummaNetto),PdfContentByte.ALIGN_RIGHT );
		}

	   if (skrivArtikelradInkMoms) {
			addText(394,126,"Moms ingår med:");
	   } else {
			addText(394,126,"Moms:");
		}
	   addText(552,126,SXUtil.getFormatNumber(totalsummaMoms,2),PdfContentByte.ALIGN_RIGHT );


//       addText(394,116,"Öresut:");
//	   addText(520,116,SXUtil.getFormatNumber(fa1.getTOrut()),PdfContentByte.ALIGN_RIGHT );

	   

       cb.setFontAndSize(FontTimesBold, 10);
		addText(394,100,"Totalt inkl. moms:");
	   addText(552,100,SXUtil.getFormatNumber(totalsummaNetto + totalsummaMoms,2),PdfContentByte.ALIGN_RIGHT);
	}
	
	
    private void printDetailArt(int offsetY) throws DocumentException ,IOException {
       cb.setFontAndSize(FontCourier, 8);
       final int offsetX = 50;
       addText(offsetX+0,offsetY,of2.getArtnr());
       addText(offsetX+64,offsetY,of2.getNamn());
       addText(offsetX+327,offsetY,of2.getEnh());
       
       addText(offsetX+415,offsetY,SXUtil.getFormatNumber(of2.getRab(),0));
       addText(offsetX+316, offsetY, SXUtil.getFormatNumber(of2.getBest(),2),PdfContentByte.ALIGN_RIGHT );
	   if (skrivArtikelradInkMoms) {
		   addText(offsetX+402, offsetY, SXUtil.getFormatNumber(of2.getPris() + (SXUtil.getRoundedDecimal(of2.getPris() * momssats /100)),2),PdfContentByte.ALIGN_RIGHT );
		   addText(offsetX+502, offsetY, SXUtil.getFormatNumber(of2.getSumma() + (SXUtil.getRoundedDecimal(of2.getSumma() * momssats /100)),2),PdfContentByte.ALIGN_RIGHT );
	   } else {
	       addText(offsetX+402, offsetY, SXUtil.getFormatNumber(of2.getPris(),2),PdfContentByte.ALIGN_RIGHT );
			addText(offsetX+502, offsetY, SXUtil.getFormatNumber(of2.getSumma(),2),PdfContentByte.ALIGN_RIGHT );
		}
    }
	 
    private void printDetailTextrad(int offsetY) throws DocumentException ,IOException {
       cb.setFontAndSize(FontCourier, 8);
       final int offsetX = 50;
       addText(offsetX+0,offsetY,of2.getText());
	   if (skrivArtikelradInkMoms) {
		   addText(offsetX+502, offsetY, SXUtil.getFormatNumber(of2.getSumma() + (SXUtil.getRoundedDecimal(of2.getSumma() * momssats /100)),2),PdfContentByte.ALIGN_RIGHT );
	   } else {
			addText(offsetX+502, offsetY, SXUtil.getFormatNumber(of2.getSumma(),2),PdfContentByte.ALIGN_RIGHT );
		}
    }
    
    
    private void printDetailTransport(int offsetY) throws DocumentException ,IOException {
       final int offsetX = 50;
       addText(offsetX+0,offsetY,"Fortsätter på nästa sida.",PdfContentByte.ALIGN_RIGHT );
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
       drawRec(47, 680, 199, 64,2);
       drawRec(389, 774, 165, 34, 2);
       drawRec(325, 680, 199, 64, 2);
	   drawRec(47, 91, 338, 64, 2);
       cb.closePathStroke();
       
       cb.newPath();
       cb.setLineWidth((float)1);
       drawRec(389, 91, 169, 64, 2);
       cb.closePathStroke();
       
       cb.setLineWidth((float)0.5);
       
       cb.newPath();
       cb.setGrayFill((float)0.9);
       drawRec(47, 605, 510, 10, 2);
       cb.closePathFillStroke();
       cb.resetGrayFill();
       
       cb.setFontAndSize(FontTimesBold, 18);
       addText(254,806,"Offert");
       cb.setFontAndSize(FontTimesBold, 6);
       addText(325,656,"Vår ref.:");
       addText(396,765,"Datum");
       addText(396,787,"Kundnummer:");
       addText(396,801,"Offertnummer:");
       addText(47,746,"Leveransadress");
       addText(325,746,"Kund");
       addText(325,633,"Kredittid " + of1.getKtid() + " dagar. Betalas senast");
       addText(325,667,"Er Ref;");
       //addText(325,622,"Efter kredittiden debiteras ränta med " + of1.getRanta() + " %");
       addText(50,608,"Nummer");
       addText(114,608,"Benämning");
       addText(352,608,"Antal");
       addText(377,608,"Enh");
       addText(441,608,"Pris");
       addText(466,608,"%");
       addText(532,608,"Summa");
       addText(235,82,"Tel:");
       addText(235,75,"Fax:");
       addText(51,45,"Org.Nr:");
       addText(51,52,"Säte:");
       
       cb.setFontAndSize(FontTimes, 8);
       addText(358,656,of1.getSaljare());
       addText(434,765,SXUtil.getFormatDate(of1.getDatum()));
       addText(441,785,of1.getKundnr());
       addText(441,800,""+of1.getOffertnr());
       addText(332,730,of1.getNamn());
       addText(332,720,of1.getAdr1());
       addText(332,710,of1.getAdr2());
       addText(332,700,of1.getAdr3());
       addText(55,730,of1.getLevadr1());
       addText(55,720,of1.getLevadr2());
       addText(55,710,of1.getLevadr3());
       addText(55,622,of1.getMarke());
       
//       addText(414,633,SXUtil.getFormatDate(SXUtil.addDate(of1.getDatum(), of1.getKtid() )));
       
       
       addText(51,82,fup.getNamn());
       addText(51,75,fup.getAdr1());
       addText(51,67,fup.getAdr2());
       addText(51,37,fup.getFakBText1());
       addText(51,30,fup.getFakBText2());
       addText(268,75,fup.getFax());
       addText(268,82,fup.getTel());
       
       if (fup.getHemsida().length() > 0) {
           addText(235,52,"Hemsida");
           addText(268,52,fup.getHemsida());
        }
       if (fup.getEmail().length() > 0) {
           addText(268,59,fup.getEmail());
           addText(235,59,"E-post");
       }

       if (fup.getMobil().length() > 0) {
           addText(235,67,"Mobil:");
           addText(268,67,fup.getMobil());
       }
       
       if (fup.getPostgiro().length() > 0) {
           addText(445,63,"PlusGiro");
           addText(490,63,fup.getPostgiro());
       }
            
       if (fup.getBankgiro().length() > 0) {
           addText(490,78,fup.getBankgiro());
           addText(445,76,"Bankgiro");
       }
       
       addText(81,45,fup.getRegnr());
       addText(81,52,fup.getSate());
//       addText(,,rf.getString(""));
  
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
