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
	private String kundnr = null;
	private String levnr = null;
	private String kundnamn = null;
	private String levnamn = null;
	private boolean superuser = false;
	private boolean adminuser = false;
	private boolean internuser = false;
	
	public boolean checkBehorighetKund() {
		if (inloggad) {
			if (kundnr != null) return true;
			if (superuser || adminuser || internuser) return true;
		}
		return false;
	}
	public boolean checkInternBehorighetKund() {
		if (inloggad) { 
			if (superuser || adminuser) return true;
			if (internuser) return true;
		}
		return false;
	}
	
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

	public String getKundnr() {
		return kundnr;
	}
	
	public void setKundnr(String kundnr) {
		this.kundnr = kundnr;
	}

	public String getLevnr() {
		return levnr;
	}
	
	public void setLevnr(String levnr) {
		this.levnr = levnr;
	}

	  public String getKundnamn() {
			 return kundnamn;
	  }

	  public void setKundnamn(String kundnamn) {
			 this.kundnamn = kundnamn;
	  }

	  public boolean isAdminuser() {
			 return adminuser;
	  }

	  public void setAdminuser(boolean adminuser) {
			 this.adminuser = adminuser;
	  }

	  public String getLevnamn() {
			 return levnamn;
	  }

	  public void setLevnamn(String levnamn) {
			 this.levnamn = levnamn;
	  }

	  public boolean isSuperuser() {
			 return superuser;
	  }

	  public void setSuperuser(boolean superuser) {
			 this.superuser = superuser;
	  }

	  public boolean isInternuser() {
			 return internuser;
	  }

	  public void setInternuser(boolean internuser) {
			 this.internuser = internuser;
	  }
	
}
