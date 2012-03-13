/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.webadm.client.common;

import com.google.gwt.user.client.ui.FocusWidget;
import java.text.ParseException;

/**
 *
 * @author Ulf
 */
public abstract class DataBinder<T extends FocusWidget, C> {
	protected T v;

	public DataBinder(T v) {
		this.v=v;
	}
	public T getWidget() { return v; }
	public abstract void hasData2Object(C object) throws ParseException;
	public abstract void object2HasData(C object);
	public abstract void clearHasData();
	
}
