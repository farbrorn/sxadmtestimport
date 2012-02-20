/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.orderregistrering;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

/**
 *
 * @author Ulf
 */
public class OrderHeaderWidget extends FlowPanel {
	private final FlexTable kundTable = new FlexTable();
	private final TextBox kundnr = new TextBox();
	private final TextBox kundnamn = new TextBox();
	private final TextBox kundadr1 = new TextBox();
	private final TextBox kundadr2 = new TextBox();
	private final TextBox kundadr3 = new TextBox();


	public OrderHeaderWidget() {
		int cn=0;
		kundTable.setWidget(cn++, 0, new Label("Kundnr"));
		kundTable.setWidget(cn++, 0, new Label("Namn"));
		kundTable.setWidget(cn++, 0, new Label("Adress"));

		cn=0;
		kundnr.setWidth("20em");
		kundnamn.setWidth("30em");
		kundadr1.setWidth("30em");
		kundadr2.setWidth("30em");
		kundadr3.setWidth("30em");
		kundTable.setWidget(cn++, 1, kundnr);
		kundTable.setWidget(cn++, 1, kundnamn);
		kundTable.setWidget(cn++, 1, kundadr1);
		kundTable.setWidget(cn++, 1, kundadr2);
		kundTable.setWidget(cn++, 1, kundadr3);

		add(kundTable);

	}

}
