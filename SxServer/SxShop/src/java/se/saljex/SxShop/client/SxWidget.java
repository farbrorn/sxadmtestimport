/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author ulf
 */
public class  SxWidget extends VerticalPanel {
	protected GlobalData globalData;

	public SxWidget(GlobalData globalData, String hederString) {
		this.globalData=globalData;
		Label l = new Label(hederString);
		l.addStyleName(globalData.STYLE_HUVUDRUBRIK);
		add(l);
	}

	interface SxWidgetConstructor {
		public SxWidget getWidget();
	}

}
