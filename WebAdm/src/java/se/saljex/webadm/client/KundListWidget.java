/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import se.saljex.webadm.client.rpcobject.Kund;

/**
 *
 * @author Ulf
 */
public class KundListWidget extends ListWidget<Kund> {

	static class Cell extends AbstractCell<Kund> {
		@Override
		public void render(Kund value, Object key, SafeHtmlBuilder sb) {
		  if (value == null) {    return;      }
		  sb.appendEscaped(value.namn);
		}
	}


	public KundListWidget(HasFormUpdater<Kund> formUpdat, HasShowError showError) {
		super(formUpdat, new Cell(), new PageLoadKund(3, 50, 100, null), showError);
	}


}
