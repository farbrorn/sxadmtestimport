/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import se.saljex.webadm.client.rpcobject.Offert1;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class Offert1ListWidget extends ListWidget<Offert1> {


	static class Cell extends AbstractCell<Offert1> {
		@Override
		public void render(Offert1 value, Object key, SafeHtmlBuilder sb) {
		  if (value == null) {    return;      }
			build(sb, value.offertnr.toString(), value.datum.toString(), value.namn, value.marke==null?"":value.marke);

		}
	}

	public static void renderHeader(SafeHtmlBuilder sb) {
		build(sb, "Offertnr", "Datum", "Kund", null);
	}

	private static void build(SafeHtmlBuilder sb, String c1, String c2, String c3, String marke) {
			sb.appendHtmlConstant("<table><tr><td style=\"width: 60px\">");
//		sb.appendHtmlConstant("<div style=\"width: 60px; float:left;\">");
			sb.appendEscaped(c1==null?"":c1);
//		sb.appendHtmlConstant("</div><div style=\"width: 70px; float:left;\">");

			sb.appendHtmlConstant("</td><td style=\"width: 70px\">");
			sb.appendEscaped(c2==null?"":c2);
//		sb.appendHtmlConstant("</div><div style=\"width: 300px; clear: right;\">");
			sb.appendHtmlConstant("</td><td style=\"width: 300px\">");
			sb.appendEscaped(c3==null?"":c3);
//		sb.appendHtmlConstant("</div>");
			if (marke!=null) {
				sb.appendHtmlConstant("</td></tr><tr><td></td><td colspan=\"2\">");
				if (marke.length()==0) sb.appendHtmlConstant("&nbsp;"); else sb.appendEscaped(marke);
			}
			sb.appendHtmlConstant("</td></tr></table>");
	}

	public Offert1ListWidget(HasFormUpdater<Offert1> formUpdat) {
		super(formUpdat, new Cell(), new PageLoadOffert1(10, 100, 1000, null) ,null);
		this.getPageLoad().setSearch("kundnr", "0", "offertnr", SQLTableList.COMPARE_NONE, SQLTableList.SORT_DESCANDING);

	}



}
