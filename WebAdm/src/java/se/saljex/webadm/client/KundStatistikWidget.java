/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import se.saljex.webadm.client.common.HasData2Form;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import java.util.ArrayList;
import se.saljex.webadm.client.common.rpcobject.Kund;

/**
 *
 * @author Ulf
 */
public class KundStatistikWidget extends FlowPanel implements HasData2Form<Kund>{

	private AsyncCallback callback = new AsyncCallback<ArrayList<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				drawError(caught);
			}

			@Override
			public void onSuccess(ArrayList<String> urlList) {
				draw(urlList);
			}
		};

	public KundStatistikWidget() {

	}

	public void setKund(String kundnr) {
		this.clear();

		MainEntryPoint.getService().getChartKund(kundnr, 400, 200, callback);
	}

	private void draw(ArrayList<String> urlList) {
		for (String url : urlList) {
			add(new Image(url));
		}
	}

	private void drawError(Throwable caught) {
		add(new Label("Kunde inte hämta statistik: " + caught.getMessage()));
	}

	@Override
	public void data2Form(Kund data) {
		setKund(data.nummer);
	}


}
