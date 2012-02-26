/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

import se.saljex.webadm.client.MainEntryPoint;
import se.saljex.webadm.client.common.HasShowMessage;

/**
 *
 * @author Ulf
 */
public class SendFakturaEpostHandler extends SendEpostHandler {

	public SendFakturaEpostHandler(String anvandare, int fakturanr) {
		super(anvandare, fakturanr);
	}
	public SendFakturaEpostHandler(String anvandare, int fakturanr, HasShowMessage showMessage) {
		super(anvandare, fakturanr, showMessage);
	}

	@Override
	public void sendEpost(String epost) {
		MainEntryPoint.getService().sendFakturaEpost(anvandare, epost, id, callback);
	}


}
