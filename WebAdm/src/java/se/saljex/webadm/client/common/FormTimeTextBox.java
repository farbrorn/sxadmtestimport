/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

import se.saljex.webadm.client.common.FormWidgetGetSet;
import java.util.Date;

/**
 *
 * @author Ulf
 */
public class FormTimeTextBox extends TimeTextBox implements FormWidgetInterface<Date> {
	private FormWidgetGetSet formWidgetGetSet;

	public FormTimeTextBox(FormWidgetGetSet formWidgetGetSet) {
		super();
		this.formWidgetGetSet = formWidgetGetSet;
	}

	@Override
	public FormWidgetGetSet getFormWidgetGetSet() {return formWidgetGetSet; }

	@Override
	public void setSQLTableValue(Date value) {
		setValue(value);
	}

	@Override
	public Date getSQLTableValue() {
		return getValue();
	}


}
