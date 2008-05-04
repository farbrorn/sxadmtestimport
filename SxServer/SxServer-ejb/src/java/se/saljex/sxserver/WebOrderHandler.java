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

	private ArrayList<OrderHandlerRad> wordreg = new ArrayList<OrderHandlerRad>();  //Holds all rows in the order
	
	private Connection con;
	
	private int wordernr;
	private String kundNr;
	private String marke;
	private short lagerNr;
	private int antalRader;
	private short kreditSparr;
	private EntityManager em;
	private String anvandare;
	
	private ArrayList<Integer> listWor2 = new ArrayList();
	
	public WebOrderHandler(EntityManager e, Connection c, String a) {
			em = e;
			con = c;
			anvandare = a;
	}

	public void loadWorder(int worderNr) throws java.sql.SQLException {
		OrderHandlerRad ord;
		Statement s = con.createStatement();
		ResultSet r = s.executeQuery("select wordernr, kundnr, marke, lagernr, antalrader, kreditsparr from weborder1 where wordernr = " + worderNr);
		if (r.next()) {
			this.wordernr = r.getInt(1);
			this.kundNr = r.getString(2);
			this.marke = r.getString(3);
			this.lagerNr = r.getShort(4);
			this.antalRader = r.getInt(6);
			this.kreditSparr = r.getShort(7);
		} else {
			throw new java.sql.SQLException("Kan inte hitta i weborder1 wordernr " + wordernr);
		}
		
		r = s.executeQuery("select artnr, antal from weborder2 where wordernr = " + worderNr);
		while (r.next()) {
			ord = new OrderHandlerRad();
			ord.artnr = r.getString(1);
			ord.best = r.getDouble(2);
			wordreg.add(ord);
		}
	}
	
	public void saveAllaSkickade() throws java.sql.SQLException {
		OrderHandler ord;
		Statement s2 = con.createStatement();
		ResultSet r2;
		Statement s = con.createStatement();
		ResultSet r = s.executeQuery("select wordernr, kundnr, marke, lagernr, antalrader, kreditsparr from weborder1 where status = 'Skickad' order by wordernr");
		while (r.next()) {
			try {
				ord = new OrderHandler(em, r.getString(2), r.getShort(4), anvandare);
			} catch (EntityNotFoundException e) { 	// Detta fel ignorerar vi och fortsätter med nästa weborder
				SXUtil.log("Kund " + r.getString(2) + " hittades inte vid sparning av weborder " + r.getInt(1));
				continue;
			}		
				
			ord.setWordernr(r.getInt(1));			
			ord.setMarke(r.getString(3));
			
			r2 = s2.executeQuery("select artnr, antal from weborder2 where wordernr = " + this.wordernr);
			while(r2.next()) {
				try {
					ord.addRow(r2.getString(1), r2.getDouble(2));
				} catch (EntityNotFoundException e) { SXUtil.log("Artikel " + r2.getString(1) + " hittades inte vid sparning av weborder " + r.getInt(1));}			// Detta fel ignorerar vi och fortsätter utan raden
			}
			spara ordern
				markera om ordern
		} 		
	}

	private void fillWorder2(int worderNr) {
		Statement s = con.createStatement();
		ResultSet r = s.executeQuery("select artnr, antal from weborder2 where wordernr = " + worderNr);
		while (r.next()) {
			ord = new OrderHandlerRad();
			ord.artnr = r.getString(1);
			ord.best = r.getDouble(2);
			wordreg.add(ord);
		}

	}
	
	public void loadWorderSkickadList() throws java.sql.SQLException {
		listWor2 = new ArrayList();
		Statement s = con.createStatement();
		ResultSet r = s.executeQuery("select wordernr, kundnr, marke, lagernr, antalrader, kreditsparr from weborder1 where status = 'Skickad'");
		while (r.next()) {
			listWor2.add(r.getInt(1));
		}
	}
	

}
