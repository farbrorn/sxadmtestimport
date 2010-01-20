/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class ArtSidaKlase implements IsSerializable{
public int klasid;
public String rubrik=null;
public String text=null;
public String infourl=null;
public String platsText=null;
public ArrayList<ArtSidaKlaseArtikel> artiklar=new ArrayList();
public ArtSidaKlase() {}
public ArtSidaKlase(int klasid, String rubrik, String text, String infourl) {
	this.klasid=klasid;
	this.rubrik=rubrik;
	this.text=text;
	this.infourl=infourl;
}
}
