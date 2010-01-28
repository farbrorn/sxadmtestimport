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
public class LagerSaldoRad implements IsSerializable{
		public LagerSaldoRad() {}
		public int lagernr;
		public String lagerNamn=null;
		public double tillgangliga;


}
