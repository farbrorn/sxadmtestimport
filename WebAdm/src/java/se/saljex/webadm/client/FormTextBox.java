/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.ui.TextBox;

/**
 *
 * @author Ulf
 */
public class FormTextBox extends TextBox implements FormWidgetInterface<String> {

	private FormWidgetGetSet formWidgetGetSet;

	public FormTextBox(FormWidgetGetSet formWidgetGetSet) {
		super();
		this.formWidgetGetSet = formWidgetGetSet;
	}

	@Override
	public FormWidgetGetSet getFormWidgetGetSet() {return formWidgetGetSet; }

	@Override
	public void setSQLTableValue(String value) {
		setValue(value);
	}

	@Override
	public String getSQLTableValue() {
		return getValue();
	}
	
}
