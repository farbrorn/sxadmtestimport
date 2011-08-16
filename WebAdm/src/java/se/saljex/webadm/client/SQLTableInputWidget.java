/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.user.client.Command;
import java.util.ArrayList;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.Kund;

/**
 *
 * @author Ulf
 */
public class SQLTableInputWidget<T extends IsSQLTable> {

	final String[] columnNames;
	final ArrayList<WidgetInfo> widgetList = new ArrayList();
	boolean isInitialized=false;

	public SQLTableInputWidget(String[] columnNames) {
		this.columnNames = columnNames;
		try {
		Kund.class.getName();
		} catch (Exception e) {}
		

	}

	public void addWidget(HasFocusHandlers widget, String sqlColumnName, Command widget2Bean) {

	}
		Kund k;

	Command cmd = new Command() {

		@Override
		public void execute() {
			;
		}
	};
	private class WidgetInfo {

		public WidgetInfo(HasFocusHandlers widget, String sqlColumnName) {
			this.widget=widget;
			this.sqlColumnName=sqlColumnName;
		}

		HasFocusHandlers widget;
		String sqlColumnName;
	}

}