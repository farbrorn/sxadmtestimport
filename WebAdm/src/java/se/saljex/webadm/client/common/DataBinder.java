/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.webadm.client.common;

import com.google.gwt.user.client.ui.HasValue;

/**
 *
 * @author Ulf
 */
public abstract class DataBinder<T, C> {
	protected HasValue<T> v;

	public DataBinder(HasValue<T> v) {
		this.v=v;
	}
	
	public abstract void hasData2Object(C object);
	public abstract void object2HasData(C object);
	public abstract void clearHasData();
	
}
