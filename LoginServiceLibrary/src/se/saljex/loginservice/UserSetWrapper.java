/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.loginservice;

import se.saljex.loginservice.User;
import java.sql.Date;
import java.util.ArrayList;
import se.saljex.sxlibrary.SXConstant;

/**
 *
 * @author Ulf
 */
public class UserSetWrapper extends User{

	public UserSetWrapper() {
	}
	public void setAnvandare(String anvandare) {
		this.anvandare = anvandare;
	}

	public void setBehorigheter(ArrayList<String> behorigheter) {
		this.behorigheter = behorigheter;
		superuser=false;
		if ( behorigheter!=null) {
			if (behorigheter.indexOf(SXConstant.BEHORIGHET_INTRA_SUPERUSER) >= 0) superuser = true;
		}		
	}
	public void setEpost(String epost) {
		this.epost = epost;
	}
	public void setLagernr(int lagernr) {
		this.lagernr = lagernr;
	}

	public void setNamn(String namn) {
		this.namn = namn;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public void setUuidCreated(java.util.Date uuidCreated) {
		this.uuidCreated = uuidCreated;
	}
	
	
}
