/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.loginservice;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import se.saljex.sxlibrary.SXConstant;

/**
 *
 * @author Ulf
 */
public class User implements Serializable{

	public User() {
	}
	protected boolean superuser = false;
		
	protected String uuid=null;
	protected java.util.Date uuidCreated=null;
	protected String anvandare=null;
	protected String namn=null;
	protected String epost=null;
	protected int lagernr=0;
	protected ArrayList<String> behorigheter=null;

	public String getAnvandare() {
		return anvandare;
	}

	public ArrayList<String> getBehorigheter() {
		return behorigheter;
	}


	public String getEpost() {
		return epost;
	}


	public int getLagernr() {
		return lagernr;
	}

	public String getNamn() {
		return namn;
	}


	public String getUuid() {
		return uuid;
	}

	public java.util.Date getUuidCreated() {
		return uuidCreated;
	}
	

	public void setUserAsLoggedOut() { 
		behorigheter = null;
		superuser=false;
	}
	

    public boolean isBehorighet(String b) {
	    if (superuser) return true;
        if (behorigheter!=null && behorigheter.indexOf(b) >= 0) return true; else return false;
     }
	
}
