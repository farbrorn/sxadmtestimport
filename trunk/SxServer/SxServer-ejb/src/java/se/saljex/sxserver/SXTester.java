/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver; 

import se.saljex.sxlibrary.exceptions.SXEntityNotFoundException;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.exceptions.KreditSparrException;
import se.saljex.sxserver.tables.TableOrder1;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import se.saljex.sxlibrary.exceptions.SxInfoException;

/**
 *
 * @author ulf
 */
public class SXTester {
	protected EntityManager em;
	private Connection con;
	private Connection webcon;
	
	public SXTester(EntityManager em, Connection con, Connection webcon) {
		this.em  = em;
		this.con = con;
		this.webcon = webcon;
	}
	
	public String tester(String testTyp) {
		String ret = "";
		
		if (testTyp.equals("OrderHandler")) {
			System.out.println("tester OrderHandler startad");
			ret = ret + "Skapar OrderHandler instans<br>";
			OrderHandler oh = new OrderHandler(em,"0555",(short)0,"tes");
			try {
				oh.addRow("EA153", 1.0);
				oh.addRow("Q006", 2.0);
				oh.addRow("Q001", 2.0);
				oh.addRow("Q055", 2.0);
				oh.addRow("EG1021", 2.0);
				oh.addRow("99SB210", 2.0);
			} catch (SXEntityNotFoundException e) {}
			
			ret = ret + "hämtar tillbaka rader<br>";
			System.out.println("tester OrderHandler rader adderade ");
			
			ret = ret + testerPrintOrder(oh.getTableOrder1(),oh.getOrdreg(),"Originalorder");
			oh.sortLagerPlatsArtNr();
			ret = ret + testerPrintOrder(oh.getTableOrder1(),oh.getOrdreg(),"Efter sortering Lagerplats+artnr");
			oh.sortLevNr();
			ret = ret + testerPrintOrder(oh.getTableOrder1(),oh.getOrdreg(),"Efter sortering Lev");
			
			oh.setLagerNr((short)1);
			ret = ret + testerPrintOrder(oh.getTableOrder1(),oh.getOrdreg(),"Efter byte till lager 1");
			

		} else if (testTyp.equals("SimpleOrderHandler"))	 {
			System.out.println("tester SimpleOrderHandler - 0");
			ret = ret + "Skapar SimpleOrderHandler instans<br>";
			SimpleOrderHandler soh = new SimpleOrderHandler(em,"0555",(short)0,"tes",1,"märket");
			soh.addRow("EA153", 1.0);
			soh.addRow("Q006", 2.0);
			soh.addRow("Q001", 2.0);
			soh.addRow("Q055", 2.0);
			soh.addRow("EG1021", 2.0);
			soh.addRow("99SB210", 2.0);
			soh.addRow("EA7232", 2.0);
			soh.addRow("EA6901", 2.0);
			soh.addRow("WA1008", 2.0);
			System.out.println("tester SimpleOrderHandler - 1");
			soh.addRow("felaktig", 2.0);
			System.out.println("tester SimpleOrderHandler - 2");
			ret = ret + testerPrintOrder(soh.getTableOrder1(),soh.getOrdreg(),"Original SimpleOrder");
			System.out.println("tester SimpleOrderHandler - 3");

			OrderHandler oh  = soh.testSplitOrderSetUp();
			System.out.println("tester SimpleOrderHandler - 4");
			
			ret = ret + testerPrintOrder(oh.getTableOrder1(),oh.getOrdreg(),"Original Order med priser (efter testsplitordersetup) som ska splittas upp");
			System.out.println("tester SimpleOrderHandler - 5");
			
			OrderHandler splitoh;
			int cn = 0; 
			while (( splitoh = soh.getNextSplitOrder(oh)) != null ) {
				
				cn++;
				ret = ret + testerPrintOrder(splitoh.getTableOrder1(),splitoh.getOrdreg(),"splitad delOrder " + cn);
				
			}
			ret = ret + "<br> totalt antal spliorder = " + cn + "<br>";
		} else if (testTyp.equals("SimpleOrderHandlerWithSave"))	 {
			System.out.println("tester SimpleOrderHandler With Save- 0");
			ret = ret + "Skapar SimpleOrderHandler instans<br>";
			SimpleOrderHandler soh = new SimpleOrderHandler(em,"0555",(short)0,"tes",1,"märket");
			soh.addRow("EA153", 1.0);
			soh.addRow("Q006", 2.0);
			soh.addRow("Q001", 2.0);
			soh.addRow("Q055", 2.0);
			soh.addRow("EG1021", 2.0);
			soh.addRow("99SB210", 2.0);
			soh.addRow("EA7232", 2.0);
			soh.addRow("EA6901", 2.0);
			soh.addRow("WA1008", 2.0);
			System.out.println("tester SimpleOrderHandler - 1");
			soh.addRow("felaktig", 2.0);
			System.out.println("tester SimpleOrderHandler - 2");
			ret = ret + testerPrintOrder(soh.getTableOrder1(),soh.getOrdreg(),"Original SimpleOrder");
			System.out.println("tester SimpleOrderHandler - 3");
			ArrayList<Integer> al = null;
			try {
				al = soh.saveAsOrder();
			} catch (KreditSparrException ke) { ServerUtil.log("Kreditspärr exception"); ke.printStackTrace();}
			 catch (SxInfoException ie) { ServerUtil.log("Info exception: " + ie.getMessage()); ie.printStackTrace();}
			for (Integer il : al) {
				ret = ret + "<br> Sparad som ordernr: " + il;
			}
		} else if (testTyp.equals("dum"))	 {
			ret = dumtest();
		} else { ret = "<br>Ogigiltig test: " + testTyp + "<br>"; }
		return ret;

		
		
	}

	private String testerPrintOrder(TableOrder1 t, ArrayList<OrderHandlerRad> or, String rubrik) {
		String ret;
		ret = "<br><b>" + rubrik + "</b><br>" + "<table><tr>"
			+ "<td>Ordernr</td><td>" + t.getOrdernr() + "</td><td>Datum</td><td>" + t.getDatum() + "</td><td>KundNr</td><td>" + t.getKundnr() + "</td><td>Lagernr</td><td>" + t.getLagernr() + "</td></tr>"
			+ "<td>Adress</td><td>" + t.getAdr1() + "</td><td> </td><td>" + t.getAdr2() + "</td><td> </td><td>" + t.getAdr3() + "</td></tr>"
			+ "<td>Fraktbolag</td><td>" + t.getFraktbolag() + "</td><td>FraktKundnr</td><td>" + t.getFraktkundnr() + "</td><td>Linjenr</td><td>" + t.getLinjenr1() + "</td></tr>"
			+ "<td>Levadress</td><td>" + t.getLevadr1() + "</td><td> </td><td>" + t.getLevadr2() + "</td><td> </td><td>" + t.getLevadr3() + "</td></tr>"
			+ "<td>Märke</td><td colspan)\"5\">" + t.getMarke() +  "</td></tr>"
			+ "<td>Säljare</td><td>" + t.getSaljare() + "</td><td>Status</td><td>" + t.getStatus() + "</td><td>AnnanLevAdr</td><td>" + t.getAnnanlevadress() + "</td></tr>"
			+ "<td>Direktlevnr</td><td>" + t.getDirektlevnr() + "</td><td>Moms</td><td>" + t.getMoms() + "</td><td>Webordernr</td><td>" + t.getWordernr() + "</td></tr>"
			+ "</table>"
			
			+ "<table><tr><th>Rad</th><th>Artikelnr</th><th>Namn</th><th>Best</th><th>Lev</th><th>Enhet</th><th>Pris</th><th>Rab</th><th>Summa</th><th>Netto</th>" + 
					"<th>Stjid</th><th>Levdat</th><th>Lev</th><th>ilager</th><th>iorder</th><th>beställda</th><th>Lagerplats</th><th>Direktlev</th></tr>";
		for (OrderHandlerRad ohr : or) { 
			ret = ret + "<tr><td>" + ohr.pos + "</td><td>" + ohr.artnr + "</td><td>" + ohr.namn + "</td><td>" + ohr.best + "</td><td> " + ohr.lev + "</td><td>"
				+ ohr.enh + "</td><td>" + ohr.pris + "</td><td>" + ohr.rab + "</td><td>" + ohr.summa
				+ "</td><td>" + ohr.netto + "</td><td>" + ohr.stjid + "</td><td>" + ohr.levdat + "</td><td>" + ohr.levnr
				+ "</td><td>" + ohr.artIlager + "</td><td>" + ohr.artIorder + "</td><td>" + ohr.artBest + "</td><td>" + ohr.artLagerplats + "</td><td>"+ ohr.artDirektlev + "</td></tr>";
		}
		ret = ret + "</table><br>";
		return ret;
			
	}

	private String dumtest() {
		WebArtikelUpdater w = new WebArtikelUpdater(em, webcon);
		try {
			w.updateWArt();
			w.updateWArtGrp();
			w.updateWArtGrpLank();
			w.updateWArtKlase();
			w.updateWArtKlaseLank();
		} catch(SQLException e) { ServerUtil.log(e.toString()); }
		return "dumtest";
	}
}
