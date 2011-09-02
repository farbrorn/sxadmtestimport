/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.HasErrorHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author Ulf
 */
public class SendOffertEpostHandler implements HasSendEpost{

	HasShowMessage showMessage;
	int offertnr;
	String anvandare;

	public SendOffertEpostHandler(String anvandare, int offertnr, HasShowMessage showMessage) {
		this.showMessage = showMessage;
		this.anvandare=anvandare;
		this.offertnr=offertnr;
	}

	@Override
	public void sendEpost(String epost) {
		MainEntryPoint.getService().sendOffertEpost(anvandare, epost, offertnr, callback);
	}

	AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

		@Override
		public void onFailure(Throwable caught) {
			showMessage.showErr("Kunde inte skaicka e-post, Fel: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Integer result) {
			showMessage.showInfo("Offert " + result + " är lagd i kö för att skickas.");
		}
	};

}
