/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;
import se.saljex.sxserver.*;

/**
 *Håller reda på alla session-variabler
 * @author ulf
 */

public class SXSession {
	
	
	private boolean inloggad = false;
	private String anvandare = null;
	public TableKund kun = null;
	public TableLev lev = null;
	
	
	public boolean getInloggad() {
		return inloggad;
	}
	
	public void setInloggad(boolean inloggad) {
		this.inloggad = inloggad;
	}
	
	public String getAnvandare() {
		return anvandare;
	}
	
	public void setAnvandare(String anvandare) {
		this.anvandare = anvandare;
	}
	
}
