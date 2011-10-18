/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author Ulf
 */
public class FormLayoutGroupFlexTable extends FormLayoutGroup {

	FlexTable layoutWidget = new FlexTable();
	int row = 0;
	public FormLayoutGroupFlexTable(FormLayoutPanel panel) {
		super(panel);
	}

	@Override
	public void add(String label, FormWidgetInterface widget) {
		layoutWidget.setWidget(row, 0, new Label(label));
		layoutWidget.setWidget(row, 1, widget.asWidget());
		getPanel().addFocusWidget(widget);
		row++;
	}

	@Override
	public FlexTable getLayoutWidget() { return layoutWidget; }

	@Override
	protected void attachToPanel() {
		getPanel().addLayoutGroup(layoutWidget);
	}


}
