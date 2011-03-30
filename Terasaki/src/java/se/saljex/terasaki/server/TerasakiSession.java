/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.terasaki.server;

/**
 *
 * @author Ulf
 */
public class TerasakiSession {
	private String anvandare=null;
	private String anvandareKort=null;

	public void setAnvandare(String anvandare, String anvandareKort) {
		this.anvandare=anvandare;
		this.anvandareKort=anvandareKort;
	}
	public boolean isLoggedIn() {
		return anvandare != null;
	}

	public String getAnvandare() { return anvandare; }
	public String getAnvandareKort() { return anvandareKort; }
}
