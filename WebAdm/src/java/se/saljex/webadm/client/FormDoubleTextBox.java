/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

/**
 *
 * @author Ulf
 */
public class FormDoubleTextBox extends DoubleTextBox implements FormWidgetInterface<Double> {
	private FormWidgetGetSet formWidgetGetSet;

	public FormDoubleTextBox(FormWidgetGetSet formWidgetGetSet) {
		super();
		this.formWidgetGetSet = formWidgetGetSet;
	}

	@Override
	public FormWidgetGetSet getFormWidgetGetSet() {return formWidgetGetSet; }

	@Override
	public void setSQLTableValue(Double value) {
		setValue(value);
	}

	@Override
	public Double getSQLTableValue() {
		return getValue();
	}

}
