/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

import com.google.gwt.user.client.ui.Widget;
import se.saljex.webadm.client.common.rpcobject.IsSQLTable;

/**
 *
 * @author Ulf
 */
abstract public class  FormWidgetGetSet<T extends IsSQLTable> {
	public FormWidgetGetSet() {


	}
	public abstract void get(T table);
	public abstract void set(T table);
}
