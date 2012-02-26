/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.FormWidgetInterface;
import com.google.gwt.user.client.ui.Widget;
import se.saljex.webadm.client.common.rpcobject.IsSQLTable;

/**
 *
 * @author Ulf
 */
public abstract class FormLayoutGroup {

	private FormLayoutPanel panel;
	public FormLayoutGroup(FormLayoutPanel panel) {
		this.panel = panel;
		attachToPanel();
	}

	public FormLayoutPanel getPanel() { return panel; }

	
	public abstract void add(String label, FormWidgetInterface widget);
	public abstract Widget getLayoutWidget();
	protected  abstract void attachToPanel();


}
