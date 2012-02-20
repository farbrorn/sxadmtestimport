
package se.saljex.sxserver;

import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxserver.tables.TableFaktura1;
import se.saljex.sxserver.tables.TableArtikel;
import se.saljex.sxserver.tables.TableKund;
import se.saljex.sxserver.tables.TableFuppg;
import se.saljex.sxserver.tables.TableFaktura2;
import se.saljex.sxserver.tables.TableBilder;
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
public class PdfFaktura extends PdfHandler {

	private Image logoImage = null; 
	private TableFaktura1 fa1;
	private TableFaktura2 fa2;
	private TableFuppg fup;
	private TableBilder bil;
	private TableKund kun;
    private EntityManager em;  
	
    
    public PdfFaktura(EntityManager e) throws DocumentException, IOException {
		super();
		em = e;
		
		//Hämta bild från databas
		Query q = em.createQuery("SELECT t FROM TableBilder t WHERE t.namn = ?1");
		String bildNamn = ServerUtil.getSXReg(em,"BildLogo");
		if (SXUtil.isEmpty(bildNamn)) { throw new DocumentException("Kan inte hitta logobildnamn i tabell sxreg. Nyckel: BildLogo"); }
		q.setParameter(1, bildNamn);
		try {
			bil = (TableBilder)q.getSingleResult();
		} catch (NoResultException en) { throw new DocumentException("Kan inte hitta logobilden - " + bildNamn + " - i tabell bilddata. Kolla värdet av nyckel BildLogo i sxreg"); }
		
		logoImage = Image.getInstance(bil.getBilddata());

    }


    private TableArtikel getArtikel(String artnr) {
		return em.find(TableArtikel.class, artnr);
//		Query q = em.createQuery("SELECT a.rsk, a.enummer FROM TableArtikel a WHERE a.nummer = ?1");
//		q.setParameter(1, artnr);
//		art = (TableArtikel)q.getSingleResult();
    }
    
    
    private boolean getKundSkrivFakturaRskEnr(String kundnr) {
		Short s;
		Query q = em.createQuery("SELECT k.skrivfakturarskenr FROM TableKund k WHERE k.nummer = ?1");
		q.setParameter(1, kundnr);
		try {
			s = (Short)q.getSingleResult();
		} catch (NoResultException en) { throw new NoResultException ("Kuns " + kundnr + " saknas. " + en.toString()); }
		if (s > 0) { return true; } else {return false; }
    }

	
	public ByteArrayOutputStream getPDF(int faktnr) throws IOException, DocumentException {
		Query q;
		q = em.createQuery("select f from TableFaktura1 f WHERE f.faktnr = " + faktnr);
		//q.setParameter(1, faktnr);
		fa1 = (TableFaktura1)q.getSingleResult();		//throws NoReultException
		
		q = em.createQuery("select f FROM TableFuppg f");
		fup = (TableFuppg)q.getSingleResult();

		boolean KundSkrivFakturaRskEnr = getKundSkrivFakturaRskEnr(fa1.getKundnr());
		
		initDocument();
		cb.saveState();
		printHeader();

		q = em.createQuery("select f from TableFaktura2 f WHERE f.tableFaktura2PK.faktnr = " + faktnr + " order by f.tableFaktura2PK.pos");
		java.util.List<TableFaktura2> lfa2 = q.getResultList();
		
//		fa2.getFaktNr(faktnr);
//		art = null;
		final int startY = 595;
		final int stopY = 170;
		final int detailRantaHeight = 10;
		final int detailArtHeight = 10;
		final int detailTextHeight = 10;
		final int detailFaktorHeight = 40;
		final int detailObsHeight = 50;
		String artnr;
		int offsetY = startY + detailArtHeight;			//På första sidan ska måste vi öka med denna för att offsetminskningen sker före 
														//första raden skrivs ut. Efterföljande sidor kan dock starta på startY
		String enr;
		String rsk;
		Double tNettoMomsplikt0 = 0.0;
		Double tNettoMomsplikt = 0.0;
		
		for (TableFaktura2 f2 : lfa2) {
			fa2 = f2;	//För att den globala variabeln ska fungera
			//Räkna delsummor för olika momssatser
			if (fa2.getRantafakturanr() != 0 || fa1.getMoms() == 0) {
				tNettoMomsplikt0 += SXUtil.getRoundedDecimal(fa2.getSumma());
			} else {
				tNettoMomsplikt += SXUtil.getRoundedDecimal(fa2.getSumma());
			}
			
			
			if (!SXUtil.isEmpty(fa2.getText()) || (SXUtil.isEmpty(fa2.getArtnr()) && SXUtil.isEmpty(fa2.getNamn()))) {
				// Skriv ut textrad
				offsetY = checkNewPage(offsetY, startY, stopY, detailTextHeight);
				printDetailTextrad(offsetY);
			} else if (fa2.getRantafakturanr() > 0 ) {
				// Skriv räntadetail
				offsetY = checkNewPage(offsetY, startY, stopY, detailRantaHeight);
				printDetailRanta(offsetY);
			} else {
				// Skriv vanlig artikelrad
				offsetY = checkNewPage(offsetY, startY, stopY, detailArtHeight);
				printDetailArt(offsetY);
				if (KundSkrivFakturaRskEnr) {
                    artnr = fa2.getArtnr();
                    if (artnr.length() > 0 && !artnr.startsWith("*")) {
						TableArtikel art = getArtikel(artnr);
						if (art!=null) {
							rsk = art.getRsk();
							enr = art.getEnummer();
							if (rsk == null) {rsk = "";}
							if (enr == null) {enr = "";}
							if (rsk.length() == 7) {rsk = rsk.substring(0,3) + " " + rsk.substring(3,5) + " " + rsk.substring(5,7);}    // Om det är 7 tecken så formaterar vi utskriften
							if (enr.length() == 7) {enr = enr.substring(0,2) + " " + enr.substring(2,5) + " " + enr.substring(5,7);}
							if (rsk.length() > 0 || enr.length() > 0 ) {
								offsetY -= detailArtHeight;
								printDetailRskEnr(rsk,enr, offsetY);
							}
						}
                    }
                }
            }
		}
		// Artikelraderna är utskrivna
		if (!SXUtil.isEmpty(fa1.getFaktortext1()) || !SXUtil.isEmpty(fa1.getFaktortext2()) || !SXUtil.isEmpty(fa1.getFaktortext3())) {
			offsetY = checkNewPage(offsetY, startY, stopY, detailFaktorHeight);
			printDetailFaktoring(offsetY );
		}

		if (!SXUtil.isEmpty(fup.getFakObstext1())) {
			offsetY = checkNewPage(offsetY, startY, stopY, detailObsHeight);
			printDetailObs(offsetY );
		}

		printTotalSumma(tNettoMomsplikt0, tNettoMomsplikt);
		cb.restoreState();
		document.close();

		return super.baos;
     
	}

	private int checkNewPage(int offsetY, int startY, int stopY, int detailHeight) throws DocumentException, IOException {
			offsetY -= detailHeight;
			if (offsetY < stopY) {
				offsetY = startY;
				cb.restoreState();
				document.newPage();
				cb.saveState();
				printHeader();
			}
			return offsetY;
	}
	private void printTotalSumma(Double tNettoMomsplikt0, Double tNettoMomsplikt) throws DocumentException  {
 //      addText(0,0,r.getString("artnr"));
       cb.setFontAndSize(FontCourier, 8);
       addText(80,146,fa1.getText1());
       addText(80,136,fa1.getText2());
       addText(80,126,fa1.getText3());
       addText(80,116,fa1.getText4());
       addText(80,106,fa1.getText5());
	   
       addText(394,146,"Momssats %:");
	   if (tNettoMomsplikt0 > 0.0) { addText(460,146,"0,00%",PdfContentByte.ALIGN_RIGHT); }
	   addText(520,146,SXUtil.getFormatNumber(fa1.getMomsproc()) ,PdfContentByte.ALIGN_RIGHT );
	   
       addText(394,136,"Netto:");
	   if (tNettoMomsplikt0 > 0.0) { addText(460,136, SXUtil.getFormatNumber(tNettoMomsplikt0),PdfContentByte.ALIGN_RIGHT ); }
	   addText(520,136, SXUtil.getFormatNumber(tNettoMomsplikt),PdfContentByte.ALIGN_RIGHT );
	   
       addText(394,126,"Moms:");
	   if (tNettoMomsplikt0 > 0.0) { addText(460,126,"0,00",PdfContentByte.ALIGN_RIGHT); }
	   addText(520,126,SXUtil.getFormatNumber(fa1.getTMoms()),PdfContentByte.ALIGN_RIGHT );
	   
	   
       addText(394,116,"Öresut:");
	   addText(520,116,SXUtil.getFormatNumber(fa1.getTOrut()),PdfContentByte.ALIGN_RIGHT );
	   
	   
	   
       cb.setFontAndSize(FontTimesBold, 10);
	   if (fa1.getTAttbetala() >= 0) {
		addText(394,100,"Att betala:");
	   } else {
		addText(394,100,"Er tillgodo:");
	   }
	   addText(520,100,SXUtil.getFormatNumber(fa1.getTAttbetala(),2),PdfContentByte.ALIGN_RIGHT);
	}
	
	
    private void printDetailArt(int offsetY) throws DocumentException ,IOException {
       cb.setFontAndSize(FontCourier, 8);
       final int offsetX = 50;
       String tempString;
       addText(offsetX+0,offsetY,fa2.getArtnr());
       addText(offsetX+64,offsetY,fa2.getNamn());
       addText(offsetX+327,offsetY,fa2.getEnh());
       
       if (fa2.getRab() ==  0.0) {   
           tempString = "nto";
       } else {
           tempString = SXUtil.getFormatNumber(fa2.getRab(),0);
       }
       addText(offsetX+415,offsetY,tempString);
       addText(offsetX+316, offsetY, SXUtil.getFormatNumber(fa2.getLev(),2),PdfContentByte.ALIGN_RIGHT );
       addText(offsetX+402, offsetY, SXUtil.getFormatNumber(fa2.getPris(),2),PdfContentByte.ALIGN_RIGHT );
       addText(offsetX+502, offsetY, SXUtil.getFormatNumber(fa2.getSumma(),2),PdfContentByte.ALIGN_RIGHT );

    }
    
    private void printDetailRskEnr(String Rsk, String Enr, int offsetY) throws DocumentException ,IOException{
       final int offsetX = 50;
       cb.setFontAndSize(FontCourier, 8);
       addText(offsetX+0,offsetY,Rsk);
       addText(offsetX+64,offsetY,Enr);
    }    
    
    private void printDetailTextrad(int offsetY) throws DocumentException ,IOException{
		final int offsetX = 50;
		cb.setFontAndSize(FontCourier, 8);
		addText(offsetX+0,offsetY,fa2.getText());
		if (fa2.getSumma() > 0.0) {
			addText(offsetX+502, offsetY, SXUtil.getFormatNumber(fa2.getSumma(),2),PdfContentByte.ALIGN_RIGHT );
		}
	}
    
    private void printDetailRanta(int offsetY) throws DocumentException ,IOException {
       final int offsetX = 50;
       cb.setFontAndSize(FontCourier, 8);
       addText(offsetX+0,offsetY,"Ränta faktura: " + fa2.getRantafakturanr() +
									" Period: " +
									SXUtil.getFormatDate(fa2.getRantafalldatum()) + 
									"-" + 
									SXUtil.getFormatDate(fa2.getRantabetaldatum()) +
									" Belopp: " +
									SXUtil.getFormatNumber(fa2.getRantabetalbelopp()) +
									" %: " +
									fa2.getRantaproc()
									);
       addText(offsetX+502, offsetY, SXUtil.getFormatNumber(fa2.getSumma(),2),PdfContentByte.ALIGN_RIGHT );
	   
    }    
    
    private void printDetailTransport(Double summa, int offsetY) throws DocumentException ,IOException {
       final int offsetX = 50;
       addText(offsetX+0,offsetY,"Transport:    " + SXUtil.getFormatNumber(summa,2),PdfContentByte.ALIGN_RIGHT );
    }    
    private void printDetailFaktoring(int offsetY) throws DocumentException ,IOException {
       final int offsetX = 50;
       cb.setFontAndSize(FontCourierBold, 8);
       addText(offsetX+0,offsetY-10,fa1.getFaktortext1());
       addText(offsetX+0,offsetY-20,fa1.getFaktortext2());
       addText(offsetX+0,offsetY-30,fa1.getFaktortext3());
    }    

	private void printDetailObs(int offsetY) throws DocumentException ,IOException {
       final int offsetX = 60;
       cb.setLineWidth((float)1);
       cb.newPath();
       cb.rectangle(offsetX, offsetY-10, 484, -40);
       cb.closePathStroke();
	   
       cb.setFontAndSize(FontTimesBold, 12);
       addText(offsetX+20,offsetY-26,"Obs!");
       cb.setFontAndSize(FontTimesBold, 10);
       addText(offsetX+20,offsetY-38,fup.getFakObstext1());
//       addText(offsetX+20,offsetY-38,"Observer att prisjusteringar sker löpande!!!");
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
       addText(254,806,"Faktura");
       cb.setFontAndSize(FontTimesBold, 6);
       addText(325,656,"Vår ref.:");
       addText(396,765,"Datum");
       addText(389,810,"Uppge vid betalning");
       addText(396,787,"Kundnummer:");
       addText(396,801,"Fakturanummer:");
       addText(47,746,"Leveransadress");
       addText(325,746,"Kund");
       addText(325,633,"Kredittid " + fa1.getKtid() + " dagar. Betalas senast");
       addText(325,667,"Er Ref;");
       addText(325,622,"Efter kredittiden debiteras ränta med " + fa1.getRanta() + " %");
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
       addText(358,656,fa1.getSaljare());
       addText(434,765,SXUtil.getFormatDate(fa1.getDatum()));
       addText(441,785,fa1.getKundnr());
       addText(441,800,""+fa1.getFaktnr());
       addText(332,730,fa1.getNamn());
       addText(332,720,fa1.getAdr1());
       addText(332,710,fa1.getAdr2());
       addText(332,700,fa1.getAdr3());
       addText(55,730,fa1.getLevadr1());
       addText(55,720,fa1.getLevadr2());
       addText(55,710,fa1.getLevadr3());
       addText(55,622,fa1.getMarke());
       
       addText(414,633,SXUtil.getFormatDate(SXUtil.addDate(fa1.getDatum(), fa1.getKtid() )));
       
       
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
           cb.showTextAligned(align, SXUtil.toStr(t),x,y,0);
         cb.endText();
        
    }
    
}
