/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

/**
 *
 * @author Ulf
 */
public class SendEpostButtonOffert extends SendEpostButton{
	public SendEpostButtonOffert(String anvandare, SendEpostInterface sendEpostInerface) {
		super(anvandare, sendEpostInerface);
	}

	@Override
	protected void doClick() {
		showEpostSelect(new SendOffertEpostHandler(anvandare, sendEpostInerface.getId()));
	}

}
