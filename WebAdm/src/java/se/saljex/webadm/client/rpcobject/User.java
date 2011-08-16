/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author Ulf
 */
public class User implements IsSerializable{
	public static final String FaktAdmin = "FaktAdmin";
	public static final String FaktArtAndra = "FaktArtAndra";
	public static final String FaktArtAndraPris = "FaktArtAndraPris";
	public static final String FaktArtNy = "FaktArtNy";
	public static final String FaktArtRadera = "FaktArtRadera";
	public static final String FaktArtSeInpris = "FaktArtSeInpris";
	public static final String FaktBest = "FaktBest";
	public static final String FaktBestEjBehov = "FaktBestEjBehov";
	public static final String FaktBestLagervärde = "FaktBestLagervärde";
	public static final String FaktFaktura = "FaktFaktura";
	public static final String FaktFakturaNegTB = "FaktFakturaNegTB";
	public static final String FaktInlev = "FaktInlev";
	public static final String FaktKundAndra = "FaktKundAndra";
	public static final String FaktKundAndraKGrans = "FaktKundAndraKGrans";
	public static final String FaktKundNy = "FaktKundNy";
	public static final String FaktKundRadera = "FaktKundRadera";
	public static final String FaktKundres = "FaktKundres";
	public static final String FaktLagerIDAndra = "FaktLagerIDAndra";
	public static final String FaktLagerIDNy = "FaktLagerIDNy";
	public static final String FaktLagerIDRadera = "FaktLagerIDRadera";
	public static final String FaktLevAndra = "FaktLevAndra";
	public static final String FaktLevNy = "FaktLevNy";
	public static final String FaktLevRadera = "FaktLevRadera";
	public static final String FaktLevRes = "FaktLevRes";
	public static final String FaktLevSeFakturor = "FaktLevSeFakturor";
	public static final String FaktLogin = "FaktLogin";
	public static final String FaktOrderKGrans = "FaktOrderKGrans";
	public static final String FaktSaljareAndra = "FaktSaljareAndra";
	public static final String FaktSaljareNy = "FaktSaljareNy";
	public static final String FaktSaljareRadera = "FaktSaljareRadera";
	public static final String FaktSamfakt = "FaktSamfakt";
	public static final String BokLogin = "BokLogin";
	public static final String LonLogin = "LonLogin";

	public User() {
	}

	private String anvandareKort=null;
	private String namn=null;
	private  String email=null;
	private  ArrayList<String> behorighetList = new ArrayList();
	private boolean isFaktAdmin=false;

	public void setUser(String anvandareKort, String namn, String email, ArrayList<String> behorighetList) {
		this.anvandareKort=anvandareKort;
		this.namn=namn;
		this.email=email;
		this.behorighetList=behorighetList;
		isFaktAdmin = isBehorig(FaktAdmin);
	}

	public String getAnvandareKort() {
		return anvandareKort;
	}

	public String getEmail() {
		return email;
	}

	public String getNamn() {
		return namn;
	}

	public boolean isBehorig(String behorighet) {
		return isFaktAdmin || behorighetList.contains(behorighet);
	}


}
