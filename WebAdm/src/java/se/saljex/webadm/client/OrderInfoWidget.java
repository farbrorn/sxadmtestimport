/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.webadm.client.common.HasData2Form;
import se.saljex.webadm.client.common.TableGetRow;
import se.saljex.webadm.client.common.TableRowLoadCallback;
import se.saljex.webadm.client.common.Util;
import se.saljex.webadm.client.common.rpcobject.Order1;
import se.saljex.webadm.client.common.rpcobject.Utlev1;

/**
 *
 * @author Ulf
 */
public class OrderInfoWidget extends ScrollPanel implements HasData2Form<Integer>{
	private final TableRowLoadCallback<Order1> callbackO1 = new TableRowLoadCallback<Order1>() {

		@Override
		public void onSuccess(Order1 row) {
			if (row!=null) {
				showO1(row);
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			showError(caught.getMessage());
		}
	};

	private final TableRowLoadCallback<Utlev1> callbackU1 = new TableRowLoadCallback<Utlev1>() {

		@Override
		public void onSuccess(Utlev1 row) {
			if (row!=null) {
				showU1(row);
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			showError(caught.getMessage());
		}
	};

	OrderhandListWidget orderhandListWidget = new OrderhandListWidget();
	TableGetRow<Order1> tO1 = new TableGetRow<Order1>(new Order1(), callbackO1);
	TableGetRow<Utlev1> tU1 = new TableGetRow<Utlev1>(new Utlev1(), callbackU1);


	FlexTable ft = new FlexTable();
	Label moms = new Label();
	Label ordermeddelande = new Label();
	Label referens = new Label();
	Label saljare = new Label();
	Label ordernr = new Label();
	Label datum = new Label();
	Label dellev = new Label();
	Label direktlevnr = new Label();
	Label faktnr = new Label();
	Label faktor = new Label();
	Label forskatt = new Label();
	Label forskattbetald = new Label();
	Label fraktbolag = new Label();
	Label ktid = new Label();
	Label kundordernr = new Label();
	Label lagernr = new Label();
	Label levadr1 = new Label();
	Label levadr2 = new Label();
	Label levadr3 = new Label();
	Label levdat = new Label();
	Label marke = new Label();


	public OrderInfoWidget() {
		int row=0;
		int col=0;
		ft.setWidget(row,col+1,ordernr);
		ft.setWidget(row++,col,new Label("Ordernr"));
		ft.setWidget(row,col+1,dellev);
		ft.setWidget(row++,col,new Label("Dellev"));
		ft.setWidget(row,col+1,lagernr);
		ft.setWidget(row++,col,new Label("Lagernr"));
		ft.setWidget(row,col+1,datum);
		ft.setWidget(row++,col,new Label("Datum"));
		ft.setWidget(row,col+1,levadr1);
		ft.setWidget(row++,col,new Label("Leveransadress"));
		ft.setWidget(row,col+1,levadr2);
		ft.setWidget(row++,col,new Label("levadr2"));
		ft.setWidget(row,col+1,levadr3);
		ft.setWidget(row++,col,new Label("levadr3"));
		ft.setWidget(row,col+1,levdat);
		ft.setWidget(row++,col,new Label("Leveransdatum"));
		ft.setWidget(row,col+1,marke);
		ft.setWidget(row++,col,new Label("Märke"));
		ft.setWidget(row,col+1,ordermeddelande);
		ft.setWidget(row++,col,new Label("Ordermeddelande"));
		ft.setWidget(row,col+1,referens);
		ft.setWidget(row++,col,new Label("Referens"));
		ft.setWidget(row,col+1,saljare);
		ft.setWidget(row++,col,new Label("Saljare"));

		row=0;
		col=3;
		ft.setWidget(row,col+1,moms);
		ft.setWidget(row++,col,new Label("Moms"));
		ft.setWidget(row,col+1,direktlevnr);
		ft.setWidget(row++,col,new Label("Direktlevnr"));
		ft.setWidget(row,col+1,faktnr);
		ft.setWidget(row++,col,new Label("Faktnr"));
		ft.setWidget(row,col+1,faktor);
		ft.setWidget(row++,col,new Label("Faktoring"));
		ft.setWidget(row,col+1,forskatt);
		ft.setWidget(row++,col,new Label("Förskott"));
		ft.setWidget(row,col+1,forskattbetald);
		ft.setWidget(row++,col,new Label("Förskott betald"));
		ft.setWidget(row,col+1,fraktbolag);
		ft.setWidget(row++,col,new Label("Fraktbolag"));
		ft.setWidget(row,col+1,ktid);
		ft.setWidget(row++,col,new Label("Kredittid"));
		ft.setWidget(row,col+1,kundordernr);
		ft.setWidget(row++,col,new Label("Kundordernr"));

		this.setHeight("100%");
		ft.setWidth("35em");
		orderhandListWidget.setWidth("40em");
		ft.setHeight("65%");
		orderhandListWidget.setHeight("25%");
		VerticalPanel vp = new VerticalPanel();
		Label rubrik = new Label("Orderinfo");
		rubrik.addStyleName("sx-bold");
		vp.add(rubrik);
		vp.add(ft);
		vp.add(orderhandListWidget);
		this.add(vp);
	}


	@Override
	public void data2Form(Integer data) {
		//Gör båda anropen då vi inte vet om det är order eller utlev
		tO1.getRow("ordernr", data.toString());
		tU1.getRow("ordernr", data.toString());
		orderhandListWidget.data2Form(data);
	}


	private void showO1(Order1 o1) {
		if (o1==null) return;
		moms.setText(((Short)o1.moms).toString()); 
		ordermeddelande.setText(o1.ordermeddelande);
		referens.setText(o1.referens);
		saljare.setText(o1.saljare);
		ordernr.setText(((Integer)o1.ordernr).toString());
		datum.setText(Util.formatDate(o1.datum));
		dellev.setText(((Short)o1.dellev).toString());
		direktlevnr.setText(((Integer)o1.direktlevnr).toString());
		faktnr.setText("");
		faktor.setText(((Short)o1.faktor).toString());
		forskatt.setText(((Short)o1.forskatt).toString());
		forskattbetald.setText(((Short)o1.forskattbetald).toString());
		fraktbolag.setText(o1.fraktbolag);
		ktid.setText(((Short)o1.ktid).toString());
		kundordernr.setText(((Integer)o1.kundordernr).toString());
		lagernr.setText(((Short)o1.lagernr).toString());
		levadr1.setText(o1.levadr1);
		levadr2.setText(o1.levadr2);
		levadr3.setText(o1.levadr3);
		levdat.setText(Util.formatDate(o1.levdat));
		marke.setText(o1.marke);		
	}

	private void showU1(Utlev1 o1) {
		if (o1==null) return;
		moms.setText(((Short)o1.moms).toString());
		ordermeddelande.setText(o1.ordermeddelande);
		referens.setText(o1.referens);
		saljare.setText(o1.saljare);
		ordernr.setText(((Integer)o1.ordernr).toString());
		datum.setText(Util.formatDate(o1.datum));
		dellev.setText(((Short)o1.dellev).toString());
		direktlevnr.setText(((Integer)o1.direktlevnr).toString());
		faktnr.setText(((Integer)o1.faktnr).toString());
		faktor.setText(((Short)o1.faktor).toString());
		forskatt.setText(((Short)o1.forskatt).toString());
		forskattbetald.setText(((Short)o1.forskattbetald).toString());
		fraktbolag.setText(o1.fraktbolag);
		ktid.setText(((Short)o1.ktid).toString());
		kundordernr.setText(((Integer)o1.kundordernr).toString());
		lagernr.setText(((Short)o1.lagernr).toString());
		levadr1.setText(o1.levadr1);
		levadr2.setText(o1.levadr2);
		levadr3.setText(o1.levadr3);
		levdat.setText(Util.formatDate(o1.levdat));
		marke.setText(o1.marke);


	}
	
	private void showError(String text) {
		Util.showModalMessage(text);
	}

	public void showOrder(Integer ordernr) {
		if (ordernr!=null) {
			tO1.getRow("ordernr", ordernr.toString());
			orderhandListWidget.data2Form(ordernr);
		}
	}
	public void showUtlev(Integer ordernr) {
		if (ordernr!=null) {
			tU1.getRow("ordernr", ordernr.toString());
			orderhandListWidget.data2Form(ordernr);
		}
	}
	public void showOrder(Order1 o) {
		if (o!=null) {
			showO1(o);
			orderhandListWidget.data2Form(o.ordernr);
		}
	}
	public void showUtlev(Utlev1 u) {
		if (u!=null) {
			showU1(u);
			orderhandListWidget.data2Form(u.ordernr);
		}
	}

}
