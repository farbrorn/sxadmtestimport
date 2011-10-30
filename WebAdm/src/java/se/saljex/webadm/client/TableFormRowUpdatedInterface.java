/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.rpcobject.IsSQLTable;

/**
 *
 * @author Ulf
 */
public interface TableFormRowUpdatedInterface<T extends IsSQLTable> {
	public void onRowUpdated(T newRow, T oldRow);
}
