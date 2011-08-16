/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.ui.Widget;
import se.saljex.webadm.client.rpcobject.IsSQLTable;

/**
 *
 * @author Ulf
 */
abstract class  FormWidgetGetSet<T extends IsSQLTable> {
	public FormWidgetGetSet() {


	}
	abstract void get(T table);
	abstract void set(T table);
}
