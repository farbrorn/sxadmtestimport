/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author ulf
 */
public class SokResultKlase implements IsSerializable{
	public ArtSidaKlase artSidaKlase;
	//public String plats;
	public SokResultKlase() {}
	public SokResultKlase(ArtSidaKlase artSidaKlase) {
		this.artSidaKlase=artSidaKlase;
		//this.plats=plats;
	}

}
