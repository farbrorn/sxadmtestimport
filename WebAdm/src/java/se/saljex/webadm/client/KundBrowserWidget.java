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
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class KundBrowserWidget extends ListWidget<Kund> {
/*
	static class Cell extends AbstractCell<Kund> {
		@Override
		public void render(Kund value, Object key, SafeHtmlBuilder sb) {
		  if (value == null) {    return;      }
			build(sb, value.namn, value.adr3);

		}
	}

	private static void build(SafeHtmlBuilder sb, String c1, String c2) {
			sb.appendHtmlConstant("<table><tr><td style=\"width: 250px\">");
			sb.appendEscaped(c1==null?"":c1);
			sb.appendHtmlConstant("</td><td style=\"width:	200px\">");
			sb.appendEscaped(c2==null?"":c2);
			sb.appendHtmlConstant("</td></tr></table>");
	}
*/
	public KundBrowserWidget(HasFormUpdater<Kund> formUpdat) {
		super(formUpdat, new PageLoadKund(10, 100, 1000, null) ,null);
		this.getPageLoad().setSearch("nummer", "0", "namn", SQLTableList.COMPARE_NONE, SQLTableList.SORT_ASCENDING);
	}

	@Override
	void addListColumns(CellTable<Kund> cellTable) {
		getCellTable().addColumnStyleName(0, Const.Style_S30);
		getCellTable().addColumnStyleName(1, Const.Style_S20);

		TextColumn<Kund> c1 = new TextColumn<Kund>() {
			@Override
			public String getValue(Kund object) {
				return object.namn;
			}
		};
		cellTable.addColumn(c1, "Namn");

		TextColumn<Kund> c2 = new TextColumn<Kund>() {
			@Override
			public String getValue(Kund object) {
				return object.adr3;
			} };
		cellTable.addColumn(c2, "adr3");
	}


}
