/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.orderregistrering;

import com.google.gwt.user.client.ui.*;
import se.saljex.webadm.client.common.FlowLabelWidgetPar;
import se.saljex.webadm.client.common.FormNavigator;
import se.saljex.webadm.client.common.IntegerTextBox;
import se.saljex.webadm.client.common.SxNumberColumn;
import se.saljex.webadm.client.common.rpcobject.Order1;

/**
 *
 * @author Ulf
 */
public class OrderHeaderWidget extends FlowPanel {
	private final TextBox kundnr = new TextBox();
	private final TextBox kundnamn = new TextBox();
	private final TextBox kundadr1 = new TextBox();
	private final TextBox kundadr2 = new TextBox();
	private final TextBox kundadr3 = new TextBox();
		private final TextBox linjenr1 = new TextBox();
		private final TextBox linjenr2 = new TextBox();
		private final TextBox linjenr3 = new TextBox();
		private final TextBox marke = new TextBox();
		private final TextBox ordermeddelande = new TextBox();
		private final TextBox referens = new TextBox();
		private final TextBox saljare = new TextBox();
		private final IntegerTextBox lagernr = new IntegerTextBox();

	


	public OrderHeaderWidget() {
		add(createTab1());

	}
	
	
	private Panel createTab1() {
		FlowPanel fp = new FlowPanel();
		FormNavigator navigator = new FormNavigator();
		addInput(fp, "Kundnr", kundnr, "8em", "14em", navigator);
		addInput(fp, "Namn", kundnamn, "8em", "20em", navigator);
		addInput(fp, "Adress", kundadr1, "8em", "20em", navigator);
		addInput(fp, ".", kundadr2, "8em", "20em", navigator);
		addInput(fp, ".", kundadr3, "8em", "20em", navigator);
		addInput(fp, "Märke", marke, "8em", "20em", navigator);
		addInput(fp, "Referens", referens, "8em", "20em", navigator);
		addInput(fp, "Säljare", saljare, "8em", "20em", navigator);
		return fp;
	}
	
	private void addInput(Panel panel, String Label, FocusWidget widget, String labelWidth, String widgetWidth, FormNavigator navigator) {
		panel.add(new FlowLabelWidgetPar(Label, widget, labelWidth, widgetWidth));
		if (navigator!=null) navigator.add(widget);
	}

	public Order1 toOrder1() {
		Order1 o1 = new Order1();
		o1.adr1 = kundadr1.getValue();
		o1.adr2 = kundadr2.getValue();
		o1.adr3 = kundadr3.getValue();
		o1.annanlevadress=0;
		o1.betalsatt="";
		o1.bonus=0;
		o1.doljdatum=null;
		o1.faktor=0;
		o1.forskatt=0;
		o1.forskattbetald=0;
		o1.fraktbolag="";
		o1.fraktfrigrans=0;
		o1.fraktkundnr="";
		o1.ktid=0;
		o1.kundnr = kundnr.getValue();
		o1.kundordernr = 0;
		o1.lagernr= lagernr.getValue().shortValue();
		o1.levadr1="";
		o1.levadr2="";
		o1.levadr3="";
		o1.levdat=null;
		o1.levvillkor="";
		o1.linjenr1=linjenr1.getValue();
		o1.linjenr2=linjenr2.getName();
		o1.linjenr3=linjenr3.getValue();
		o1.marke=marke.getValue();
		o1.moms=1;
		o1.mottagarfrakt=0;
		o1.namn=kundnamn.getValue();
		o1.ordermeddelande=ordermeddelande.getValue();
		o1.referens=referens.getValue();
		o1.saljare=saljare.getValue();
		o1.tidigastfaktdatum=null;
		o1.veckolevdag=0;
		o1.wordernr=0;
		o1.ordernr=0;
		o1.dellev=0;
		o1.status="";
		return o1;
	}

}
