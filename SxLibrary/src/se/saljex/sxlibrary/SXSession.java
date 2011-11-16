/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxlibrary;
//import se.saljex.sxserver.web.*;
import java.util.ArrayList;

/**
 *Håller reda på alla session-variabler
 * @author ulf
 */

public class SXSession {
	
	
	private boolean inloggad = false;

	private String intraAnvandare = null;
	private String intraAnvandareKort = null;
	private Integer intraAnvandareLagerNr = null;

	private String levnr = null;
	private String levnamn = null;

	private String kundnr = null;
	private String kundnamn = null;
	private String kundKontaktNamn = null;
	private Integer kundKontaktId = null;
	private String kundLoginNamn = null;
	private boolean kundStayLoggedIn=false;		//Om kunden vill ha autoinloggning
	private String kundAutoLogInId=null;

	private Integer inkopInloggatBestNr = null;

	private boolean superuser = false;
	private boolean adminuser = false;
	private boolean intrauser = false;

	private boolean gastLogin=false;



        private ArrayList<String> arrBehorighet = new ArrayList();

//	private ArrayList<RappEdit> arrRappEdit = new ArrayList();
	private ArrayList arrRappEdit = new ArrayList();
	public void setInkopInloggatBestNr(Integer inkopInloggatBestNr) { this.inkopInloggatBestNr = inkopInloggatBestNr; }
	public Integer getInkopInloggatBestNr() { return this.inkopInloggatBestNr; }

        public void addBehorighet(String b) {
            arrBehorighet.add(b);
        }
        public boolean isBehorighet(String b) {
	    if (superuser || adminuser) return true;
            if (arrBehorighet.indexOf(b) >= 0) return true; else return false;
        }
        public void clearBehorighet() {
            arrBehorighet.clear();
        }

		public ArrayList<String> getArrBehorighet() { return arrBehorighet; }

	public boolean checkBehorighetKund() {
		if (inloggad) {
			if (kundLoginNamn != null) return true;
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

	public boolean checkIntraBehorighetIntraWebApp() {
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
	
	public String getIntraAnvandare() {
		return intraAnvandare;
	}

	public String getIntraAnvandareKort() {
		return intraAnvandareKort;
	}
	
	public void setIntraAnvandare(String anvandare) {
		this.intraAnvandare = anvandare;
	}

	public void setIntraAnvandareKort(String intraAnvandareKort) {
		this.intraAnvandareKort = intraAnvandareKort;
	}

	public Integer getIntraAnvandareLagerNr() {		return intraAnvandareLagerNr;	}
	public void setIntraAnvandareLagerNr(Integer intraAnvandareLagerNr) {		this.intraAnvandareLagerNr = intraAnvandareLagerNr;	}
	
	public String getKundnr() {
		return kundnr;
	}
	
	public void setKundnr(String kundnr) {
		this.kundnr = kundnr;
	}

	public Integer getKundKontaktId() {
		return kundKontaktId;
	}

	public void setKundKontaktId(Integer kundKontaktId) {
		this.kundKontaktId = kundKontaktId;
	}

	public String getKundKontaktNamn() {
		return kundKontaktNamn;
	}

	public void setKundKontaktNamn(String kundKontaktNamn) {
		this.kundKontaktNamn = kundKontaktNamn;
	}

	public String getKundLoginNamn() {
		return kundLoginNamn;
	}

	public void setKundLoginNamn(String kundLoginNamn) {
		this.kundLoginNamn = kundLoginNamn;
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

//	  public ArrayList<RappEdit> getArrRappEdit() {
	  public ArrayList getArrRappEdit() {
			 return arrRappEdit;
	  }

//	  public void setRappEdit(ArrayList<RappEdit> arrRappEdit) {
	  public void setRappEdit(ArrayList arrRappEdit) {
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

	public boolean isGastLogin() {
		return gastLogin;
	}

	public void setGastLogin(boolean gastLoggin) {
		this.gastLogin = gastLoggin;
	}

	public boolean isKundStayLoggedIn() { return kundStayLoggedIn; }
	public void setKundStayLoggedIn(boolean kundStayLoggedIn) { this.kundStayLoggedIn = kundStayLoggedIn; }

	public String getKundAutoLogInId() {
		return kundAutoLogInId;
	}

	public void setKundAutoLogInId(String kundAutoLogInId) {
		this.kundAutoLogInId = kundAutoLogInId;
	}
	
}
