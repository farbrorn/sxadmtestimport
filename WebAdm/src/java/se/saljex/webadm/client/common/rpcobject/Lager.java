/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Ulf
 */
public class Lager implements IsSerializable, IsSQLTable{

	@Override
	public Lager newInstance() { return new Lager(); }
	
	public Lager() {
	}

	@Override
	public  String getSQLTableName() {return "lager"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"artnr", "lagernr"};
	}

	public String artnr;
	public short lagernr;

	public double ilager;
	public double bestpunkt;
	public double maxlager;
	public double best;
	public double iorder;
	public String lagerplats;
	public short hindrafilialbest;

}
