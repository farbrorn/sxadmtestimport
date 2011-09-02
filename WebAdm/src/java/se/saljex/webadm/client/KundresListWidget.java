/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.Kundres;

/**
 *
 * @author Ulf
 */
public class KundresListWidget extends ListWidget<Kundres> {
/*
	static class Cell extends AbstractCell<Kundres> {
		@Override
		public void render(Kundres value, Object key, SafeHtmlBuilder sb) {
		  if (value == null) {    return;      }
		  NumberFormat fmt = NumberFormat.getFormat("0.00");

		  build(sb, value.faktnr.toString(), value.datum.toString(), value.falldat.toString(), fmt.format(value.tot), value.pdat1.toString(), value.pdat2.toString(), value.inkassodatum.toString(), value.inkassostatus);
		}
	}

	private static void build(SafeHtmlBuilder sb, String faktnr, String datum, String falldat, String tot, String pdat1, String pdat2, String inkassodatum, String inkassostatus) {
			sb.appendHtmlConstant("<table><tr><td style=\"width: 50px\">");
			addString(sb, faktnr);
			sb.appendHtmlConstant("</td><td style=\"width:	50px\">");
			addString(sb, datum);
			sb.appendHtmlConstant("</td><td style=\"width:	50px\">");
			addString(sb, falldat);
			sb.appendHtmlConstant("</td><td style=\"width:	50px\">");
			addString(sb, tot);
			sb.appendHtmlConstant("</td><td style=\"width:	50px\">");
			addString(sb, pdat1);
			sb.appendHtmlConstant("</td><td style=\"width:	50px\">");
			addString(sb, pdat2);
			sb.appendHtmlConstant("</td><td style=\"width:	50px\">");
			addString(sb, inkassodatum);
			sb.appendHtmlConstant("</td><td style=\"width:	50px\">");
			addString(sb, inkassostatus);
			sb.appendHtmlConstant("</td></tr></table>");
	}

	private static void addString(SafeHtmlBuilder sb, String s) {
			sb.appendEscaped(s==null?"":s);
	}

	public static void buildHeader(SafeHtmlBuilder sb) {
		build(sb, "Faktura", "Datum", "Förfallodatum", "Belopp", "Påminnelse 1", "Påminnelse 2", "Inkasso", "Inkassostatus");
	}
*/
	public KundresListWidget(HasFormUpdater<Kundres> formUpdat, HasShowMessage showError) {

		super(formUpdat, new PageLoadKundres(3, 50, 100, null), showError);
	}

	@Override
	void addListColumns(CellTable<Kundres> cellTable) {
		int cn = 0;

		getCellTable().addColumnStyleName(1, Const.Style_S15);
		getCellTable().addColumnStyleName(2, Const.Style_S15);
		getCellTable().addColumnStyleName(3, Const.Style_N10);
		getCellTable().addColumnStyleName(4, Const.Style_S15);


		TextColumn<Kundres> c1 = new TextColumn<Kundres>() {
			@Override
			public String getValue(Kundres object) {
				return object.faktnr.toString();
			}
		};
		cellTable.addColumn(c1, "Faktura");
		getCellTable().addColumnStyleName(cn++, Const.Style_S10);

		c1 = new TextColumn<Kundres>() {
			@Override
			public String getValue(Kundres object) {
				return object.datum.toString();
			}
		};
		cellTable.addColumn(c1, "Datum");
		getCellTable().addColumnStyleName(cn++, Const.Style_S15);

		c1 = new TextColumn<Kundres>() {
			@Override
			public String getValue(Kundres object) {
				return Util.formatDate(object.falldat);
			}
		};
		cellTable.addColumn(c1, "Falldatum");
		getCellTable().addColumnStyleName(cn++, Const.Style_S15);

		c1 = new TextColumn<Kundres>() {
			@Override
			public String getValue(Kundres object) {
				return Util.format2Dec(object.tot);
			}
		};
		cellTable.addColumn(c1, "Belopp");
		getCellTable().addColumnStyleName(cn++, Const.Style_N10);

		c1 = new TextColumn<Kundres>() {
			@Override
			public String getValue(Kundres object) {
				return Util.formatDate(object.pdat1);
			}
		};
		cellTable.addColumn(c1, "Påminnelse1");
		getCellTable().addColumnStyleName(cn++, Const.Style_S15);
	}


}
