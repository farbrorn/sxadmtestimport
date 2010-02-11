/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import se.saljex.SxShop.client.rpcobject.ArtSidaKlase;
import se.saljex.SxShop.client.rpcobject.ArtGrupp;

/**
 *
 * @author ulf
 */
public class ArtTradUserObject {
public ArtGrupp artGrupp=null;
public  ArtSidaKlase artSidaKlase = null;
public Integer cashId=null;//Id i array om sidan är cachad hos klienten
public boolean klasarTillGrpIsScanned=false; //Om klasarna till noden är scannade
public boolean isKampanjNod=false;
public ArtTradUserObject() {
}
public ArtTradUserObject(ArtGrupp artGrupp) {
	this.artGrupp=artGrupp;
}
public ArtTradUserObject(ArtGrupp artGrupp, ArtSidaKlase artSidaKlase) {
	this.artGrupp=artGrupp;
	this.artSidaKlase=artSidaKlase;
}


}