/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

import se.saljex.webadm.client.common.MessagePopupPanel;
import se.saljex.webadm.client.common.HasShowMessage;
import se.saljex.webadm.client.common.HasSendEpost;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author Ulf
 */
public abstract class SendEpostHandler implements HasSendEpost{


	protected HasShowMessage showMessage;
	protected int id;
	protected String anvandare;


	//Cosntructor utan HasShowMessage skapar automatiskt en egen ShowMessage
	//För att anropa utan HasShowMessage använd constructor med HasShowMessage och sätt till null
	public SendEpostHandler(String anvandare, int fakturanr) {
		this(anvandare, fakturanr, new MessagePopupPanel(true));
	}

	public SendEpostHandler(String anvandare, int fakturanr, HasShowMessage showMessage) {
		this.showMessage = showMessage;
		this.anvandare=anvandare;
		this.id=fakturanr;
	}


	@Override
	public abstract void sendEpost(String epost);

	AsyncCallback<Integer> callback = new AsyncCallback<Integer>() {

		@Override
		public void onFailure(Throwable caught) {
			if (showMessage!=null) showMessage.showErr("Kunde inte skicka e-post, Fel: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Integer result) {
			if (showMessage!=null) showMessage.showInfo("Dokument " + result + " är lagd i kö för att skickas.");
		}
	};

}
