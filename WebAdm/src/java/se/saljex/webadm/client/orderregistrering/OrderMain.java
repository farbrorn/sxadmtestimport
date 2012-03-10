/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.orderregistrering;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import se.saljex.webadm.client.MainEntryPoint;
import se.saljex.webadm.client.commmon.constants.Const;
import se.saljex.webadm.client.common.DebugMessagePanel;
import se.saljex.webadm.client.common.Util;
import se.saljex.webadm.client.common.rpcobject.Order1;

/**
 *
 * @author Ulf
 */
public class OrderMain extends FlowPanel implements OrderFormCallback {

	private String anvandare = "UB";
	private final OrderHeaderWidget head = new OrderHeaderWidget();
	private final OrderFormWidget form = new OrderFormWidget(this);
	private final FlowPanel orderBottomFlow = new FlowPanel();
//	private final ScrollPanel formScroll = new ScrollPanel(form);
	private final ScrollPanel headScroll = new ScrollPanel(head);
	private final ScrollPanel bottomScroll = new ScrollPanel(orderBottomFlow);
	
	private final Button btnSpara = new Button("Spara order", new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			doSparaClick();
		}
	});
	private final Button btnAvbryt = new Button("Avbryt");
	
	private final Label orderSumma = new Label("0");
	private final Label orderSummaPrompt = new Label("Total: ");
	
	

	public OrderMain() {
		
		this.addStyleName(Const.Style_Orderreg);
		
		orderSumma.setWidth("10em");
		orderSumma.addStyleName(Const.Style_AlignRight);
		orderSumma.addStyleName(Const.Style_FloatRight);
		orderSummaPrompt.addStyleName(Const.Style_FloatRight);
		btnSpara.addStyleName(Const.Style_Margin_1em_Right);
		btnSpara.addStyleName(Const.Style_FloatLeft);
		btnAvbryt.addStyleName(Const.Style_Margin_1em_Right);
		btnAvbryt.addStyleName(Const.Style_FloatLeft);
		orderBottomFlow.add(orderSumma);
		orderBottomFlow.add(orderSummaPrompt);
		orderBottomFlow.add(btnSpara);
		orderBottomFlow.add(btnAvbryt);
		
		headScroll.addStyleName(Const.Style_Orderreg_Head);
		add(headScroll);
		
		add(form);
		
		bottomScroll.addStyleName(Const.Style_Orderreg_Bottom);
		
		add(bottomScroll);
		
	}
	
	public void doSparaClick() {
		Util.showModalWait();
		
		Order1 tor1 = new Order1();
		ArrayList<OrderRad> rader = new ArrayList<OrderRad>();
		for (OrderFormWidget.Rad rad : form.getRader()) {
			rader.add(rad.orderRad);
		}
		
		tor1.adr1 = "";
		tor1.adr2 = "";
		tor1.adr3 = "";
		tor1.annanlevadress = 0;
		tor1.betalsatt = "";
		tor1.bonus = 1;
		tor1.doljdatum = null;
		tor1.faktor = 0;
		tor1.forskatt = 0;
		tor1.forskattbetald = 0;
		tor1.fraktbolag = "";
		tor1.fraktfrigrans = 0;
		tor1.fraktkundnr = "";
		tor1.ktid = 30;
		tor1.kundnr = "";
		tor1.kundordernr = 0;
		tor1.lagernr = 0;
		tor1.levadr1 = "Lev 1";
		tor1.levadr2 = "Lev 2";
		tor1.levadr3 = "Lev 3";
		tor1.levdat = null;
		tor1.levvillkor = "";
		tor1.linjenr1 = "";
		tor1.linjenr2 = "";
		tor1.linjenr3 = "";
		tor1.marke = "Ordermärke";
		tor1.moms = 1;
		tor1.mottagarfrakt = 0;
		tor1.namn = "";
		tor1.ordermeddelande = "";
		tor1.referens = "";
		tor1.saljare = "";
		tor1.tidigastfaktdatum = null;
		tor1.veckolevdag = 0;
		tor1.wordernr = 0;
		
		
		MainEntryPoint.getService().saveOrder(anvandare, tor1, rader, new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				Util.hideModalWait();
				DebugMessagePanel.addMessage("Fel vid spara order: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Integer result) {
				Util.hideModalWait();
				Util.showModalMessage("Order " + result + " sparad!");
			}
		});
	}

	@Override
	public void onNewOrdersumma(double summa) {
		orderSumma.setText(Util.format0Dec(summa));
	}



}

