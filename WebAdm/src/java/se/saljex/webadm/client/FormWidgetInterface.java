/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Ulf
 */
public interface FormWidgetInterface<T> {
	public HandlerRegistration addKeyDownHandler(KeyDownHandler handler);
	public FormWidgetGetSet getFormWidgetGetSet();
	public T getSQLTableValue();
	public void setSQLTableValue(T value);
	public void setFocus(boolean focus);
	public Widget asWidget();

}
