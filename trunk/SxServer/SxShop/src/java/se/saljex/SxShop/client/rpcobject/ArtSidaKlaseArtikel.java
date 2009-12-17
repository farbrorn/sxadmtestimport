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
public class ArtSidaKlaseArtikel implements IsSerializable {
public String nummer=null;
public String namn=null;
public String katnamn=null;
public String enhet=null;
public Double utpris =null;
public Double staf_pris1 =null;
public Double staf_pris2 =null;
public Double staf_antal1 =null;
public Double staf_antal2 =null;
public String rabkod=null;
public String kod1=null;
public java.sql.Date prisdatum=null;
public Double forpack =null;
public Double minsaljpack =null;
public int prisgiltighetstid=0;
public String bildurl=null;
public ArtSidaKlaseArtikel() {}
}
