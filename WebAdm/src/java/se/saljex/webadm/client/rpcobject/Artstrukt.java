/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.Date;

/**
 *
 * @author Ulf
 */
public class Artstrukt implements IsSerializable, IsSQLTable{

	@Override
	public Artstrukt newInstance() { return new Artstrukt(); }
	
	public Artstrukt() {
	}

	@Override
	public  String getSQLTableName() {return "artstrukt"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"nummer","artnr"};
	}
	
	public String nummer;
	public String artnr;

	public String namn;
	public float pris;
	public float rab;
	public String enh;
	public float antal;
	public float netto;
	public Date datum;

}
