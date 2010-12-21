
package se.saljex.sxserver;

import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxserver.tables.TableArtikel;
import se.saljex.sxserver.tables.TableSaljare;
import se.saljex.sxserver.tables.TableKund;
import se.saljex.sxserver.tables.TableFuppg;
import se.saljex.sxserver.tables.TableLagerid;
import se.saljex.sxserver.tables.TableBilder;
import se.saljex.sxserver.tables.TableBest2;
import se.saljex.sxserver.tables.TableBest1;
import se.saljex.sxserver.tables.TableStjarnrad;
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
public class PdfBest extends PdfHandler {

	private Image logoImage = null; 
	private TableBest1 be1;
	private TableBest2 be2;
	private TableArtikel art;
	private TableFuppg fup;
	private TableBilder bil;
	private TableKund kun;
	private TableStjarnrad stj;
	private TableSaljare slj;
	private TableLagerid lid;
    private EntityManager em;  
	
    
    public PdfBest(EntityManager e) throws DocumentException, IOException {
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
    
	
	public ByteArrayOutputStream getPDF(int bestnr) throws IOException, DocumentException {
		Query q;
		Query q2;
		q = em.createQuery("select f from TableBest1 f WHERE f.bestnr = " + bestnr);
		//q.setParameter(1, faktnr);
		be1 = (TableBest1)q.getSingleResult();		//throws NoReultException
		
		q = em.createQuery("select f FROM TableFuppg f");
		fup = (TableFuppg)q.getSingleResult();

		
		initDocument();
		cb.saveState();
		printHeader();
		q = em.createNamedQuery("TableBest2.findByBestnr");
		q.setParameter("bestnr", bestnr);
		//q = em.createQuery("select f from TableBest2 f WHERE f.tableBest2PK.bestnr = " + bestnr + " order by f.tableBest2PK.rad");
		java.util.List<TableBest2> lbe2 = q.getResultList();
		
		final int startY = 595;
		final int stopY = 110;
		final int detailArtHeight = 22;
		final int detailStjRadHeight = 22;
		int offsetY = startY + detailArtHeight;			//På första sidan ska måste vi öka med denna för att offsetminskningen sker före 
														//första raden skrivs ut. Efterföljande sidor kan dock starta på startY
		
		for (TableBest2 b2 : lbe2) {
			be2 = b2;	//För att den globala variabeln ska fungera
			
			// Skriv vanlig artikelrad
			if (be2.getStjid() == 0) {
				offsetY = checkNewPage(offsetY, startY, stopY, detailArtHeight);
				printDetailArt(offsetY);
			} else {
				String saljare = null;
				String saljareTel = null;
				String saljareEpost = null;
				Short lagernr  = null;
				stj = em.find(TableStjarnrad.class, be2.getStjid());
				if (stj != null) {
					q2 = em.createNamedQuery("TableSaljare.findByForkortning");
					q2.setParameter("forkortning", stj.getAnvandare());
					slj = null;
					try { 
						slj = (TableSaljare)q2.getResultList().get(0); 
						saljare = slj.getNamn();
						lagernr = slj.getLagernr();
					} catch (Exception e) {}
					if (lagernr != null) {
						lid = em.find(TableLagerid.class, lagernr);
						if (lid != null) {
							saljareTel = lid.getTel();
							saljareEpost = lid.getEmail();
						}
					}
				}
				offsetY = checkNewPage(offsetY, startY, stopY, detailStjRadHeight);
				printDetailStjRad(offsetY, saljare, saljareTel, saljareEpost);
			}
		}
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
	private void printTotalSumma(Double tNettoMomsplikt0, Double tNettoMomsplikt) throws DocumentException  {
 //      addText(0,0,r.getString("artnr"));
       cb.setFontAndSize(FontCourier, 8);
//       addText(80,146,fa1.getText1());
	}
	
	
    private void printDetailArt(int offsetY) throws DocumentException ,IOException {
       cb.setFontAndSize(FontCourier, 8);
       final int offsetX = 50;
       addText(offsetX+0,offsetY,be2.getArtnr());
       addText(offsetX+64,offsetY,be2.getArtnamn());
       addText(offsetX+327,offsetY,be2.getEnh());
       
       addText(offsetX+415,offsetY,SXUtil.getFormatNumber(be2.getRab(),0));
       addText(offsetX+316, offsetY, SXUtil.getFormatNumber(be2.getBest(),2),PdfContentByte.ALIGN_RIGHT );
       addText(offsetX+402, offsetY, SXUtil.getFormatNumber(be2.getPris(),2),PdfContentByte.ALIGN_RIGHT );
       addText(offsetX+502, offsetY, SXUtil.getFormatNumber(be2.getSumma(),2),PdfContentByte.ALIGN_RIGHT );

       addText(offsetX+20,offsetY-10,be2.getBartnr());
		 
    }
    private void printDetailStjRad(int offsetY, String saljare, String saljareTel, String saljareEpost) throws DocumentException ,IOException {
       cb.setFontAndSize(FontCourier, 8);
       final int offsetX = 50;
       addText(offsetX+0,offsetY,be2.getArtnr());
       addText(offsetX+64,offsetY,be2.getArtnamn());
       addText(offsetX+327,offsetY,be2.getEnh());
       
       addText(offsetX+415,offsetY,SXUtil.getFormatNumber(be2.getRab(),0));
       addText(offsetX+316, offsetY, SXUtil.getFormatNumber(be2.getBest(),2),PdfContentByte.ALIGN_RIGHT );
       addText(offsetX+402, offsetY, SXUtil.getFormatNumber(be2.getPris(),2),PdfContentByte.ALIGN_RIGHT );
       addText(offsetX+502, offsetY, SXUtil.getFormatNumber(be2.getSumma(),2),PdfContentByte.ALIGN_RIGHT );

		 if (saljare != null) {
			String t = "Detta är en specialartikel. Vid frågor kontakta " + saljare + ".";
			if (saljareTel != null) t = t=t+" Tel: " + saljareTel;
			if (saljareEpost != null) t = t=t+" E-post: " + saljareEpost;
		
			addText(offsetX+20,offsetY-10,t);
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
//       drawRec(47, 159, 510, 456, 2);
       drawRec(47, 91, 510, 524, 2);
       drawRec(47, 680, 199, 64,2);
       drawRec(389, 774, 165, 34, 2);
       drawRec(325, 680, 199, 64, 2);
	   //drawRec(47, 91, 338, 64, 2);
       cb.closePathStroke();
       
      // cb.newPath();
      // cb.setLineWidth((float)1);
      // drawRec(389, 91, 169, 64, 2);
      // cb.closePathStroke();
       
       cb.setLineWidth((float)0.5);
       
       cb.newPath();
       cb.setGrayFill((float)0.9);
       drawRec(47, 605, 510, 10, 2);
       cb.closePathFillStroke();
       cb.resetGrayFill();
       
       cb.setFontAndSize(FontTimesBold, 18);
       addText(254,806,"Beställning");
       cb.setFontAndSize(FontTimesBold, 6);
       addText(325,656,"Vår ref.:");
       addText(396,765,"Datum");
       //addText(389,810,"Uppge vid betalning");
       addText(396,787,"Kundnummer:");
       addText(396,801,"Fakturanummer:");
       addText(47,746,"Leveransadress");
       addText(325,746,"Beställare");
       addText(325,667,"Er Ref;");
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
       addText(358,656,be1.getVarRef());
       addText(434,765,SXUtil.getFormatDate(be1.getDatum()));
       //addText(441,785,be1.getKundnr());
       addText(441,800,""+be1.getBestnr());
       addText(332,730,fup.getNamn());
       addText(332,720,fup.getAdr1());
       addText(332,710,fup.getAdr2());
       addText(332,700,fup.getAdr3());
       addText(55,730,be1.getLevadr0());
       addText(55,720,be1.getLevadr1());
       addText(55,710,be1.getLevadr2());
       addText(55,700,be1.getLevadr3());
       addText(55,622,be1.getMarke());
              
       
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
