/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Ulf
 */
public class ArtStat implements IsSerializable, IsSQLTable{

	@Override
	public ArtStat newInstance() { return new ArtStat(); }
	
	public ArtStat() {
	}

	@Override
	public  String getSQLTableName() {return "artstat"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"artnr","ar","man"};
	}

	public String artnr;
	public short ar;
	public short man;
	public double salda;
	public double tbidrag;

}
