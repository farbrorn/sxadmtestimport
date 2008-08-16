/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;
import java.util.ArrayList;
import se.saljex.sxserver.*;

/**
 *Håller reda på alla session-variabler
 * @author ulf
 */

public class SXSession {
	
	
	private boolean inloggad = false;
	private String anvandare = null;
	private String intraAnvandareKort = null;
	private String kundnr = null;
	private String levnr = null;
	private String kundnamn = null;
	private String levnamn = null;
	private Integer inkopInloggatBestNr = null;
	private boolean superuser = false;
	private boolean adminuser = false;
	private boolean intrauser = false;

	private ArrayList<RappEdit> arrRappEdit = new ArrayList();
	public void setInkopInloggatBestNr(Integer inkopInloggatBestNr) { this.inkopInloggatBestNr = inkopInloggatBestNr; }
	public Integer getInkopInloggatBestNr() { return this.inkopInloggatBestNr; }
	
	public boolean checkBehorighetKund() {
		if (inloggad) {
			if (kundnr != null) return true;
			if (superuser || adminuser || intrauser) return true;
		}
		return false;
	}
	public boolean checkIntraBehorighetKund() {
		if (inloggad) { 
			if (superuser || adminuser) return true;
			if (intrauser) return true;
		}
		return false;
	}
	
	public boolean checkIntraBehorighetRapp() {
		if (inloggad) { 
			if (superuser || adminuser) return true;
			if (intrauser) return true;
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

	public String getIntraAnvandareKort() {
		return intraAnvandareKort;
	}
	
	public void setAnvandare(String anvandare) {
		this.anvandare = anvandare;
	}

	public void setIntraAnvandareKort(String intraAnvandareKort) {
		this.intraAnvandareKort = intraAnvandareKort;
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

	  public ArrayList<RappEdit> getArrRappEdit() {
			 return arrRappEdit;
	  }

	  public void setRappEdit(ArrayList<RappEdit> arrRappEdit) {
			 this.arrRappEdit = arrRappEdit;
	  }

	  public boolean isSuperuser() {
			 return superuser;
	  }

	  public void setSuperuser(boolean superuser) {
			 this.superuser = superuser;
	  }

	  public boolean isIntrauser() {
			 return intrauser;
	  }

	  public void setIntrauser(boolean intrauser) {
			 this.intrauser = intrauser;
	  }
	
}
