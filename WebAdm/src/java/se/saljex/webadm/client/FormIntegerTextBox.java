/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

/**
 *
 * @author Ulf
 */
public class FormIntegerTextBox extends IntegerTextBox implements FormWidgetInterface<Integer> {
	private FormWidgetGetSet formWidgetGetSet;

	public FormIntegerTextBox(FormWidgetGetSet formWidgetGetSet) {
		super();
		this.formWidgetGetSet = formWidgetGetSet;
	}

	@Override
	public FormWidgetGetSet getFormWidgetGetSet() {return formWidgetGetSet; }

	@Override
	public void setSQLTableValue(Integer value) {
		setValue(value);
	}

	@Override
	public Integer getSQLTableValue() {
		return getValue();
	}


}
