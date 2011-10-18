/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import se.saljex.webadm.client.rpcobject.IsSQLTable;

/**
 *
 * @author Ulf
 */
public class FormLayoutFlowPanel<T extends IsSQLTable> extends FormLayoutPanel<T>{

	FlowPanel p = new FlowPanel();

	public FormLayoutFlowPanel() {
	}

	@Override
	public void addFocusWidget(FormWidgetInterface formWidget) {
		super.addFocusWidget(formWidget);
	}

	@Override
	public void addLayoutGroup(Widget widget) {
		p.add(widget);
	}

	@Override
	public Panel asPanel() {
		return p;
	}


}
