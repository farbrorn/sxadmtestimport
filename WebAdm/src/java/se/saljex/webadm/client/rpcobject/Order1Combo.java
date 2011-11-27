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
public class Order1Combo extends  Order1Utlev1Combo implements IsSerializable, IsSQLTable{

	@Override
	public Order1Combo newInstance() { return new Order1Combo(); }

	@Override
	public  String getSQLTableName() {return "order1"; }

	@Override
	public String[] getPrimaryKeyLabels() {
		return new String[] {"ordernr"};
	}

	public Order1Combo() {
	}



}
