/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

import se.saljex.webadm.client.common.FormWidgetGetSet;
import com.google.gwt.user.client.ui.CheckBox;

/**
 *
 * @author Ulf
 */
public class FormCheckBox extends CheckBox implements FormWidgetInterface<Short> {
	private FormWidgetGetSet formWidgetGetSet;

	public FormCheckBox(String label, FormWidgetGetSet formWidgetGetSet) {
		super(label);
		init(formWidgetGetSet);
	}
	public FormCheckBox(FormWidgetGetSet formWidgetGetSet) {
		super();
		init(formWidgetGetSet);
	}
	private void init(FormWidgetGetSet formWidgetGetSet) {
		this.formWidgetGetSet = formWidgetGetSet;
		
	}

	@Override
	public FormWidgetGetSet getFormWidgetGetSet() {return formWidgetGetSet; }

	@Override
	public void setSQLTableValue(Short value) {
		if (value==null) value=0;
		setValue(!value.equals((short)0));
	}

	@Override
	public Short getSQLTableValue() {
		return getValue() ? (short)1 : (short)0;
	}

}
