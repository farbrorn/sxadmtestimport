/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.Date;

/**
 *
 * @author Ulf
 */
public class Utlev1Combo extends  Order1Utlev1Combo implements IsSerializable, IsSQLTable{

	@Override
	public Utlev1Combo newInstance() { return new Utlev1Combo(); }

	@Override
	public  String getSQLTableName() {return "utlev1"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"ordernr"};
	}

	public Utlev1Combo() {
	}



}
