/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.rpcobject;

/**
 *
 * @author Ulf
 */
public interface IsSQLTable {
	public  String getSQLTableName();
	public String[] getPrimaryKeyLabels();
	public IsSQLTable newInstance(); //skapar en ny instans av classen t.ex return new IsSQLTable();
	
}
