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
public class SokResult implements IsSerializable{
public String sokStr=null;
public ArrayList<ArtSidaKlaseArtikel> artiklar=new ArrayList();
public ArrayList<SokResultKlase> sokResultKlasar = new ArrayList();
public boolean merRaderFinns=false;
public int maxRader=0;
public SokResult() {}
public SokResult(String sokStr) {
	this.sokStr=sokStr;
}

/*public void addKlase(ArtSidaKlase artSidaKlase, String plats) {
	sokResultKlasar.add(new SokResultKlase(artSidaKlase, plats));
}
*/
}
