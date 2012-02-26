/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.server;

import javax.sql.DataSource;
import se.saljex.webadm.client.common.rpcobject.IsSQLTable;
import se.saljex.webadm.client.common.rpcobject.Kund;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;
import se.saljex.webadm.client.common.rpcobject.ServerErrorException;

/**
 *
 * @author Ulf
 */
public class SQLTableKundGetList extends SQLTableGetList{

	public SQLTableKundGetList(DataSource sxadm) {
		super(sxadm);
	}

/*
	@Override
	public SQLTableList<Kund> getList (String sokString, int sokField, int sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException {
		SQLTableList<Kund> kundList = new SQLTableList();
		fillList(kundList, new Kund(), sokString, sokField, sortField, compareType, sortOrder, offset, limit);
		return kundList;
	}

	@Override
	protected String[] getSuperSokColumnNames() {
		String[] s = {"nummer", "namn", "adr1", "adr2", "adr3", "tel", "biltel", "ref", "email"};
		return s;
	}

	@Override
	protected String getFieldNameFromID(int id) throws ServerErrorException{
		String ret;
		if (id==Kund.getFieldNamn()) {  return "namn";}
		else if (id==Kund.getFieldNummer()) {  return "nummer";}
		else throw (new ServerErrorException("Felaktig sok-fält: " + id));
	}
*/
}
