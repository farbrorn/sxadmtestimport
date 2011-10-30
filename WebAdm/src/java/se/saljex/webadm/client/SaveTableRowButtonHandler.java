/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import se.saljex.webadm.client.rpcobject.IsSQLTable;

/**
 *
 * @author Ulf
 */
public interface SaveTableRowButtonHandler<T extends IsSQLTable> {
	public void onClick(ClickEvent event);
	public void onSuccess(T newRow, T oldRow);
	public void onFailure(Throwable caught);

}
