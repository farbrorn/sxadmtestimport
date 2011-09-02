/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import se.saljex.webadm.client.rpcobject.Kund;

/**
 *
 * @author Ulf
 */
public class KundListWidget extends ListWidget<Kund> {
/*
	static class Cell extends AbstractCell<Kund> {
		@Override
		public void render(Kund value, Object key, SafeHtmlBuilder sb) {
		  if (value == null) {    return;      }
		  sb.appendEscaped(value.namn);
		}
	}

*/
	public KundListWidget(HasFormUpdater<Kund> formUpdat, HasShowMessage showError) {
//		super(formUpdat, new Cell(), new PageLoadKund(3, 50, 100, null), showError);
		super(null, new PageLoadKund(3, 50, 100, null), showError);
	}

	@Override
	void addListColumns(CellTable<Kund> cellTable) {

		TextColumn<Kund> c1 = new TextColumn<Kund>() {

			@Override
			public String getValue(Kund object) {
				return object.namn;
			}
		};
		cellTable.addColumn(c1, "Namn");
	}


}
