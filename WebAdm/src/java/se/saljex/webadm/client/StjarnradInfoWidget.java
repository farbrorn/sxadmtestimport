/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.Util;
import se.saljex.webadm.client.common.ModalMessageBox;
import se.saljex.webadm.client.common.TableRowLoadCallback;
import se.saljex.webadm.client.common.TableGetRow;
import se.saljex.webadm.client.common.HasData2Form;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.webadm.client.common.rpcobject.Stjarnrad;

/**
 *
 * @author Ulf
 */
public class StjarnradInfoWidget extends ScrollPanel implements HasData2Form<Integer>{

	private final TableRowLoadCallback<Stjarnrad> callbackStj = new TableRowLoadCallback<Stjarnrad>() {

		@Override
		public void onSuccess(Stjarnrad row) {
			if (row!=null) {
				show(row);
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			showError(caught.getMessage());
		}
	};

	TableGetRow<Stjarnrad> tStj = new TableGetRow<Stjarnrad>(new Stjarnrad(), callbackStj);

	ModalMessageBox modalMessageBox = null;

	FlexTable ft = new FlexTable();
	Label stjid = new Label();
	Label artnr = new Label();
	Label namn = new Label();
	Label antal = new Label();
	Label enh = new Label();
	Label kundnr = new Label();
	Label inpris = new Label();
	Label inrab = new Label();
	Label lagernr = new Label();
	Label anvandare = new Label();
	Label regdatum = new Label();
	Label bestdat = new Label();
	Label inkomdatum = new Label();
	Label autobestall = new Label();
	Label bestnr = new Label();
	Label finnsilager = new Label();
	Label fakturanr = new Label();


	public StjarnradInfoWidget() {
		int row=0;
		int col=0;
		ft.setWidget(row,col+1,stjid);
		ft.setWidget(row++,col,new Label("ID"));
		ft.setWidget(row,col+1,artnr);
		ft.setWidget(row++,col,new Label("Artikelnr"));
		ft.setWidget(row,col+1,namn);
		ft.setWidget(row++,col,new Label("Benämning"));
		ft.setWidget(row,col+1,antal);
		ft.setWidget(row++,col,new Label("Antal"));
		ft.setWidget(row,col+1,enh);
		ft.setWidget(row++,col,new Label("Enhet"));
		ft.setWidget(row,col+1,kundnr);
		ft.setWidget(row++,col,new Label("Kundnr"));
		ft.setWidget(row,col+1,inpris);
		ft.setWidget(row++,col,new Label("Inpris"));
		ft.setWidget(row,col+1,inrab);
		ft.setWidget(row++,col,new Label("Rabatt"));

		row=0;
		col=3;
		ft.setWidget(row,col+1,lagernr);
		ft.setWidget(row++,col,new Label("Lagernr"));
		ft.setWidget(row,col+1,anvandare);
		ft.setWidget(row++,col,new Label("Användare"));
		ft.setWidget(row,col+1,regdatum);
		ft.setWidget(row++,col,new Label("Registrerad"));
		ft.setWidget(row,col+1,bestdat);
		ft.setWidget(row++,col,new Label("Beställd"));
		ft.setWidget(row,col+1,inkomdatum);
		ft.setWidget(row++,col,new Label("Inkommen"));
		ft.setWidget(row,col+1,autobestall);
		ft.setWidget(row++,col,new Label("Automatisk beställning"));
		ft.setWidget(row,col+1,bestnr);
		ft.setWidget(row++,col,new Label("Beställningsnummer"));
		ft.setWidget(row,col+1,finnsilager);
		ft.setWidget(row++,col,new Label("Finns i lager"));
		ft.setWidget(row,col+1,fakturanr);
		ft.setWidget(row++,col,new Label("Fakturanr"));

		this.setHeight("100%");
		ft.setWidth("35em");
		VerticalPanel vp = new VerticalPanel();
		Label rubrik = new Label("Stjärnradinfo");
		rubrik.addStyleName("sx-bold");
		vp.add(rubrik);
		vp.add(ft);
		this.add(vp);
	}


	@Override
	public void data2Form(Integer data) {
		show(data);
	}


	private void show(Stjarnrad stj) {
		if (stj==null) return;
		stjid.setText(((Integer)stj.stjid).toString());
		artnr.setText(stj.artnr);
		namn.setText(stj.namn);
		antal.setText(Util.format2Dec(stj.antal));
		enh.setText(stj.enh);
		kundnr.setText(stj.kundnr);
		inpris.setText(Util.format2Dec(stj.inpris));
		inrab.setText(Util.format2Dec(stj.inrab));
		lagernr.setText(((Short)stj.lagernr).toString());
		anvandare.setText(stj.anvandare);
		regdatum.setText(Util.formatDate(stj.regdatum));
		bestdat.setText(Util.formatDate(stj.bestdat));
		inkomdatum.setText(Util.formatDate(stj.inkomdatum));
		autobestall.setText(((Short)stj.autobestall).toString());
		bestnr.setText(((Integer)stj.bestnr).toString());
		finnsilager.setText(((Short)stj.finnsilager).toString());
		fakturanr.setText(((Integer)stj.fakturanr).toString());
	}

	private void showError(String text) {
		if (modalMessageBox==null) modalMessageBox=new ModalMessageBox();
		modalMessageBox.show(text, true);
	}

	public void show(Integer id) {
		if (id!=null) {
			tStj.getRow("stjid", id.toString());
		}
	}


}
