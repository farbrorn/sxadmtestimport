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
public class ArtSida implements IsSerializable {
public int grpid;
public Integer klasid=null;//Om sidan är filtrerad till endast en klase
public String rubrik=null;
public String text=null;
public String infourl=null;
public ArrayList<ArtSidaKlase> klasar=new ArrayList();
public ArtSida() {}
public ArtSida(int grpid, String rubrik, String text, String infourl) {
	this.grpid=grpid;
	this.rubrik=rubrik;
	this.text=text;
	this.infourl=infourl;
}
}
