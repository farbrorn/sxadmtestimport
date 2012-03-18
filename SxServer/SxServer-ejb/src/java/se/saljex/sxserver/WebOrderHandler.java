/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.exceptions.KreditSparrException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import se.saljex.sxlibrary.exceptions.SxInfoException;

/**
 *
 * @author ulf
 */
public class WebOrderHandler {

	private Connection con;
	
	private EntityManager em;
	private String anvandare;
	
	SimpleOrderHandler sord = null;
	
	
	public WebOrderHandler(EntityManager e, Connection c, String anvandare) {
			em = e;
			con = c;
			this.anvandare = new String(anvandare);
	}

	
	/*
	public void saveAllaSkickade() throws java.sql.SQLException {
		int orderNr;
		Statement s = con.createStatement();
		Statement s3 = con.createStatement();
		ResultSet r = s.executeQuery("select wordernr, kundnr, marke, lagernr, antalrader, kreditsparr from weborder1 where status = 'Skickad' order by wordernr");
		while (r.next()) {
			try {
				loadWorder(r.getInt(1), r.getString(2), r.getString(3), r.getShort(4));
			} catch (EntityNotFoundException e) { 	// Detta fel ignorerar vi och fortsätter med nästa weborder
				ServerUtil.log("Kund " + r.getString(2) + " hittades inte vid sparning av weborder " + r.getInt(1));
				continue;
			}

			orderNr = saveAsOrder();
			if (orderNr > 0) {
				ServerUtil.log("Sparar")
			}
		} 		
	}
	*/
	
	public ArrayList<Integer> loadWorderAndSaveSkickadAsOrder(int worderNr)  throws java.sql.SQLException, KreditSparrException, SxInfoException {
		// Returnerar lista med de ordernummer som angiven weborder skapar, eller null om något gick fel
		// Strategi för detta är att från en bean hä'mta lista på webordernr
		// därefter starta en ny bean för varje wordernr, eller på annat sätt köra en separat transaktion fr varje
		// Varje post i worderlistan som genererar ett fel hoppas över, och förhoppningsvis funkar det vid nästa körning
		// Loggar mm sker från anropande rutinen
	ServerUtil.logDebug("WebOrderHandler - loadWorderAndSaveSkickadAsOrder(int) - Start");
		loadWorder(worderNr);
		ArrayList<Integer> ret = saveSkickadAsOrder();
		return ret;
	}
	
	public void loadWorder(int worderNr) throws java.sql.SQLException, EntityNotFoundException {
		// Hämtar data ur weborder1 och skapar en SimpleOrderHandler och läser in angiven order.
		// Throws EntityNotFound om inte wordernumret hittas
	ServerUtil.logDebug("WebOrderHandler - loadWorder(int) - Start");
		Statement s = con.createStatement();
		ResultSet r = s.executeQuery("select wordernr, kundnr, marke, lagernr, antalrader from weborder1 where wordernr = " + worderNr);
		if (r.next()) {
			loadWorder(r.getInt(1), r.getString(2), r.getString(3), r.getShort(4));
		} else {
			throw new EntityNotFoundException("Hittar inte wordernr " + worderNr);
		}
	}
	
	
	public void loadWorder(int worderNr, String kundNr, String marke, short lagerNr) throws java.sql.SQLException {
		// Skapar en SimpleOrderHandler och läser in angiven order. Ingen läsning sker i weborder1 för att kunna användas i en loop 
	ServerUtil.logDebug("WebOrderHandler - loadWorder(int, string, string, short) - Start");
		sord = new SimpleOrderHandler(em, kundNr, lagerNr, anvandare, worderNr, marke);
		
		Statement s = con.createStatement();
		ResultSet r = s.executeQuery("select artnr, antal from weborder2 where wordernr = " + worderNr);
		while (r.next()) {
			try {
				sord.addRow(r.getString(1), r.getDouble(2));
			} catch (EntityNotFoundException e) { ServerUtil.log("Artikel " + r.getString(1) + " hittades inte och hoppas över vid sparning av weborder " + worderNr);}			// Detta fel ignorerar vi och fortsätter utan raden
		}
		
	}
	
	public ArrayList<Integer> saveSkickadAsOrder() throws java.sql.SQLException, KreditSparrException, SxInfoException {
		// Returnerar en ArrayList av de ordernummerr som det var sparat som
		// om vi returnerar null härifrån, eller får ett undantag så behöver transaktionen avbrytas
	ServerUtil.logDebug("WebOrderHandler - saveSkickadAsOrder() - Start");
		Statement s = con.createStatement();    
		ArrayList<Integer> orderList = sord.saveAsOrder();
		if (s.executeUpdate("update weborder1 set status = 'Mottagen', mottagendatum='" + SXUtil.getFormatDate() + "'  where status = 'Skickad' and wordernr = " +  sord.getWorderNr()) < 1) {
			// Om vi kommer hit så har antingen en annan process ändrat på ordern, eller så har det blivit något kommunikationsfel
			throw new SQLException("Kan inte uppdatera weborder1. Förmodligen är det en annan process som bearbetar ordern samtidigt");
		}
		return orderList;
	}

public ArrayList<Integer> getSkickadWorderList() throws java.sql.SQLException {                
	// Returnerar en lista på alla weborder med status Skickad
	// Listan kan sedan användas för att loopa igenom, och köra flera omgångar av WebOrderHandler
	// Om vi råkar få en weborder som alltid osrsakar fel, så skippar vi den bara och går vidare med nästa
	// vilket inte går om man kör en select weborder1 vid varje tillfälle - den felaktiga ordern kommer då alltid att komma först
	ServerUtil.logDebug("WebOrderHandler - getSkickadWorderList() - Startad");
		ArrayList<Integer> listWor2 = new ArrayList();                
		Statement s = con.createStatement();                
		ResultSet r = s.executeQuery("select wordernr from weborder1 where status = 'Skickad'");                
		while (r.next()) {                        
			listWor2.add(r.getInt(1));                 
		}  
		return listWor2;
	} 


/*
public int updateWArtikel() throws SQLException {
	con.createStatement().executeUpdate("delete from wartikelup");		// Rensar temporära tebellen
	
	List<TableArtikel> r = em.createNamedQuery("TableArtikel.findAllInArtklaselank").getResultList(); // Hämtar alla aritklar som är med i någon klase
	Integer cn = 0;
	TableLager lag;
	// Prepare frågan för att användas i insert-loopen
	PreparedStatement prep = con.prepareStatement("insert into wartikelup (namn, enhet, utpris, staf_pris1, staf_pris2, staf_pris1_dat, staf_pris2_dat," +
		" staf_antal1, staf_antal2, bestnr, rabkod, kod1, prisdatum, refnr, vikt, volym, minsaljpack, forpack, rsk, enummer," +
		" fraktvillkor, prisgiltighetstid, utgattdatum, katnamn, bildartnr, maxlager, ilager) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	for (TableArtikel a : r) {
		lag = em.find(TableLager.class, new TableLagerPK(a.getNummer(),(short)0));		// Hämtar lagervärdena
		prep.setString	(1 , a.getNummer());
		prep.setString	(2 , a.getEnhet());
		prep.setDouble	(3 , a.getUtpris());
		prep.setDouble	(4 , a.getStafPris1());
		prep.setDouble	(5 , a.getStafPris2()); 
		prep.setDate	(6 , new java.sql.Date(a.getStafPris1Dat().getTime()));
		prep.setDate	(7 , new java.sql.Date(a.getStafPris2Dat().getTime()));
		prep.setDouble	(8 , a.getStafAntal1());
		prep.setDouble	(9, a.getStafAntal2());
		prep.setString	(10, a.getBestnr());
		prep.setString	(11, a.getRabkod());
		prep.setString	(12, a.getKod1());
		prep.setDate	(13, new java.sql.Date(a.getPrisdatum().getTime()));
		prep.setString	(14, a.getRefnr());
		prep.setDouble	(15, a.getVikt());
		prep.setDouble	(16, a.getVolym());
		prep.setDouble	(17, a.getMinsaljpack());
		prep.setDouble	(18, a.getForpack());
		prep.setString	(19, a.getRsk());
		prep.setString	(20, a.getEnummer());
		prep.setShort	(21, a.getFraktvillkor());
		prep.setInt		(22, a.getPrisgiltighetstid());
		prep.setDate	(23, new java.sql.Date(a.getUtgattdatum().getTime()));
		prep.setString	(24, a.getKatnamn());
		prep.setString	(25, a.getBildartnr());
		prep.setDouble	(26, lag.getMaxlager());
		prep.setDouble	(27, lag.getIlager() - lag.getIorder());
		prep.executeUpdate();
		cn++;		
	}
	con.createStatement().executeUpdate("drop table wartikel");											// Rensar originaltabellen på webb-sertvern
	con.createStatement().executeUpdate("create table wartikel select * from wartikelup");		// Kopierar temporära tabellen till original
	
	return cn;
}

public int updateWArtgrp() throws SQLException {
	con.createStatement().executeUpdate("delete from wartgrpup");		// Rensar temporära tebellen
	int cn = 0;
	
	List<TableArtgrp> r = em.createNamedQuery("TableArtgrp.findAll").getResultList(); // Hämtar alla rader
	// Prepare frågan för att användas i insert-loopen
	PreparedStatement prep = con.prepareStatement("insert into wartgrpup (grpid, prevgrpid, rubrik, infourl, sortorder, text, html)" +
		" values (?,?,?,?,?,?,?)");
	for (TableArtgrp a : r) {
		prep.setInt		(1 , a.getGrpid());
		prep.setInt		(2 , a.getPrevgrpid());
		prep.setString	(3 , a.getRubrik());
		prep.setString	(4 , a.getInfourl());
		prep.setInt		(5 , a.getSortorder());		
		prep.setString	(6 , a.getText());
		prep.setString	(7 , a.getHtml());
		prep.executeUpdate();
		cn++;		
	}
	con.createStatement().executeUpdate("drop table wartgrp");											// Rensar originaltabellen på webb-sertvern
	con.createStatement().executeUpdate("create table wartgrp select * from wartgrpup");		// Kopierar temporära tabellen till original
	return cn;	
}

public int updateWArtgrplank() throws SQLException {
	con.createStatement().executeUpdate("delete from wartgrplankup");		// Rensar temporära tebellen
	int cn = 0;
	
	List<TableArtgrplank> r = em.createNamedQuery("TableArtgrplank.findAll").getResultList(); // Hämtar alla rader
	// Prepare frågan för att användas i insert-loopen
	PreparedStatement prep = con.prepareStatement("insert into wartgrplankup (grpid, klasid, sortorder)" +
		" values (?,?,?)");
	for (TableArtgrplank a : r) {
		prep.setInt		(1 , a.getTableArtgrplankPK().getGrpid());
		prep.setInt		(2 , a.getTableArtgrplankPK().getKlasid());
		prep.setInt		(3 , a.getSortorder());	
		prep.executeUpdate();
		cn++;		
	}
	con.createStatement().executeUpdate("drop table wartgrplank");											// Rensar originaltabellen på webb-sertvern
	con.createStatement().executeUpdate("create table wartgrplank select * from wartgrplankup");		// Kopierar temporära tabellen till original
	return cn;	
}

public int updateWArtklase() throws SQLException {
	con.createStatement().executeUpdate("delete from wartklaseup");		// Rensar temporära tebellen
	int cn = 0;
	
	List<TableArtklase> r = em.createNamedQuery("TableArtklase.findAll").getResultList(); // Hämtar alla rader
	// Prepare frågan för att användas i insert-loopen
	PreparedStatement prep = con.prepareStatement("insert into wartklaseup (klasid, rubrik, infourl, fraktvillkor, text, html)" +
		" values (?,?,?,?,?,?)");
	for (TableArtklase a : r) {
		prep.setInt			(1 , a.getKlasid());
		prep.setString		(2 , a.getRubrik());
		prep.setString		(3 , a.getInfourl());	
		prep.setString		(4 , a.getFraktvillkor());
		prep.setString		(5 , a.getText());	
		prep.setString		(6 , a.getHtml());	
		prep.executeUpdate();
		cn++;		
	}
	con.createStatement().executeUpdate("drop table wartklase");											// Rensar originaltabellen på webb-sertvern
	con.createStatement().executeUpdate("create table wartklase select * from wartklaseup");		// Kopierar temporära tabellen till original
	return cn;	
}

public int updateWArtklaselank() throws SQLException {
	con.createStatement().executeUpdate("delete from wartklaselankup");		// Rensar temporära tebellen
	int cn = 0;
	
	List<TableArtklaselank> r = em.createNamedQuery("TableArtklaselank.findAll").getResultList(); // Hämtar alla rader
	// Prepare frågan för att användas i insert-loopen
	PreparedStatement prep = con.prepareStatement("insert into wartklaselankup (klasid, artnr, sortorder)" +
		" values (?,?,?)");
	for (TableArtklaselank a : r) {
		prep.setInt		(1 , a.getTableArtklaselankPK().getKlasid());
		prep.setString	(2 , a.getTableArtklaselankPK().getArtnr());
		prep.setInt		(3 , a.getSortorder());	
		prep.executeUpdate();
		cn++;		
	}
	con.createStatement().executeUpdate("drop table wartklaselank");											// Rensar originaltabellen på webb-sertvern
	con.createStatement().executeUpdate("create table wartklaselank select * from wartklaselankup");		// Kopierar temporära tabellen till original
	return cn;	
}
*/
}
    