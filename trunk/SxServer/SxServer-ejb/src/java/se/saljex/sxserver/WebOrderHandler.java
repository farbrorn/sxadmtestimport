/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

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
				SXUtil.log("Kund " + r.getString(2) + " hittades inte vid sparning av weborder " + r.getInt(1));
				continue;
			}

			orderNr = saveAsOrder();
			if (orderNr > 0) {
				SXUtil.log("Sparar")
			}
		} 		
	}
	*/
	
	public ArrayList<Integer> loadWorderAndSaveSkickadAsOrder(int worderNr)  throws java.sql.SQLException, EntityNotFoundException {
		// Returnerar lista med de ordernummer som angiven weborder skapar, eller null om något gick fel
		// Strategi för detta är att från en bean hä'mta lista på webordernr
		// därefter starta en ny bean för varje wordernr, eller på annat sätt köra en separat transaktion fr varje
		// Varje post i worderlistan som genererar ett fel hoppas över, och förhoppningsvis funkar det vid nästa körning
		// Loggar mm sker från anropande rutinen
		loadWorder(worderNr);
		return saveSkickadAsOrder();
	}
	
	public void loadWorder(int worderNr) throws java.sql.SQLException, EntityNotFoundException {
		// Hämtar data ur weborder1 och skapar en SimpleOrderHandler och läser in angiven order.
		// Throws EntityNotFound om inte wordernumret hittas
		Statement s = con.createStatement();
		ResultSet r = s.executeQuery("select wordernr, kundnr, marke, lagernr, antalrader, kreditsparr from weborder1 where wordernr = " + worderNr);
		if (r.next()) {
			loadWorder(r.getInt(1), r.getString(2), r.getString(3), r.getShort(4));
		} else {
			throw new EntityNotFoundException("Hittar inte wordernr " + worderNr);
		}
	}
	
	
	public void loadWorder(int worderNr, String kundNr, String marke, short lagerNr) throws java.sql.SQLException {
		// Skapar en SimpleOrderHandler och läser in angiven order. Ingen läsning sker i weborder1 för att kunna användas i en loop 
		sord = new SimpleOrderHandler(em, kundNr, lagerNr, anvandare, worderNr, marke);
		
		Statement s = con.createStatement();
		ResultSet r = s.executeQuery("select artnr, antal from weborder2 where wordernr = " + worderNr);
		while (r.next()) {
			try {
				sord.addRow(r.getString(1), r.getDouble(2));
			} catch (EntityNotFoundException e) { SXUtil.log("Artikel " + r.getString(1) + " hittades inte och hoppas över vid sparning av weborder " + worderNr);}			// Detta fel ignorerar vi och fortsätter utan raden
		}
		
	}
	
	public ArrayList<Integer> saveSkickadAsOrder() throws java.sql.SQLException {
		// Returnerar en ArrayList av de ordernummerr som det var sparat som
		// om vi returnerar null härifrån, eller får ett undantag så behöver transaktionen avbrytas
		Statement s = con.createStatement();                
		ArrayList<Integer> orderList = sord.saveAsOrder();
		if (s.executeUpdate("update weborder1 set status = 'Mottagen', kreditsparr = 0, mottagendatum = '" + SXUtil.getFormatDate() + "' where status = 'Skickad' and wordernr = " +  sord.getWorderNr()) < 1) {
			// Om vi kommer hit så har antingen en annan process ändrat på ordern, eller så har det blivit något kommunikationsfel
			// och vi måste göra en rollback
			// Vi signalerar det genom att returnera null via orderList
			orderList = null;
		}
		
		return orderList;
	}

public ArrayList<Integer> getSkickadWorderList() throws java.sql.SQLException {                
	// Returnerar en lista på alla weborder med status Skickad
	// Listan kan sedan användas för att loopa igenom, och köra flera omgångar av WebOrderHandler
	// Om vi råkar få en weborder som alltid osrsakar fel, så skippar vi den bara och går vidare med nästa
	// vilket inte går om man kör en select weborder1 vid varje tillfälle - den felaktiga ordern kommer då alltid att komma först
		ArrayList<Integer> listWor2 = new ArrayList();                
		Statement s = con.createStatement();                
		ResultSet r = s.executeQuery("select wordernr from weborder1 where status = 'Skickad'");                
		while (r.next()) {                        
			listWor2.add(r.getInt(1));                
		}  
		return listWor2;
	}
	
}
