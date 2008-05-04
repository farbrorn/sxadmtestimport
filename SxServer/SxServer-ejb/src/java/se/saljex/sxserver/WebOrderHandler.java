/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
	

	
	public WebOrderHandler(Connection c) {
			con = c;
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
	
	

}
