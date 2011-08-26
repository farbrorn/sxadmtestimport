/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import se.saljex.webadm.client.rpcobject.Offert1;
import se.saljex.webadm.client.rpcobject.Offert2;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class Offert2ListWidget extends ListWidget<Offert2>{
	static class Cell extends AbstractCell<Offert2> {
		@Override
		public void render(Offert2 value, Object key, SafeHtmlBuilder sb) {
		  if (value == null) {    return;      }
		  NumberFormat fmt = NumberFormat.getFormat("0.00");
		  build(sb, value.artnr, value.namn, fmt.format(value.best), value.enh, fmt.format(value.pris), fmt.format(value.rab), fmt.format(value.summa));
		}
	}

	public static void renderHeader(SafeHtmlBuilder sb) {
		build(sb,"Art.nr","Benämning", "Antal", "Enh", "Pris", "%", "Summa");

	}
	private static void build(SafeHtmlBuilder sb, String c1, String c2, String c3, String c4, String c5, String c6, String c7) {
			sb.appendHtmlConstant("<table><tr><td style=\"width: 80px\">");
			sb.appendEscaped(c1==null?"":c1);
			sb.appendHtmlConstant("</td><td style=\"width: 300px\">");
			sb.appendEscaped(c2==null?"":c2);
			sb.appendHtmlConstant("</td><td style=\"width: 60px; text-align:right\">");
			sb.appendEscaped(c3==null?"":c3);
			sb.appendHtmlConstant("</td><td style=\"width: 40px\">");
			sb.appendEscaped(c3==null?"":c4);
			sb.appendHtmlConstant("</td><td style=\"width: 60px; text-align:right\">");
			sb.appendEscaped(c3==null?"":c5);
			sb.appendHtmlConstant("</td><td style=\"width: 60px; text-align:right\">");
			sb.appendEscaped(c3==null?"":c6);
			sb.appendHtmlConstant("</td><td style=\"width: 70px; text-align:right\">");
			sb.appendEscaped(c3==null?"":c7);
			sb.appendHtmlConstant("</td></tr></table>");

	}

	public Offert2ListWidget() {
		super(null, new Cell(), new PageLoadOffert2(0, 100, 1000, null) ,null);
	}

	public void loadOffertNr(Integer offertnr) {
		if (offertnr!=null) {
			this.getPageLoad().setSearch("offertnr", offertnr.toString(), "offertnr", SQLTableList.COMPARE_EQUALS, SQLTableList.SORT_ASCENDING);
		}
	}

}
