/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.Column;

/**
 *
 * @author Ulf
 */
public abstract class SxNumberColumn<T> extends Column<T, Number> {

	public SxNumberColumn(NumberFormat fmt) {
		super(new RightCell(fmt));
	}

	static class RightCell extends AbstractCell<Number> {
		private NumberFormat fmt;

		public RightCell(NumberFormat fmt) {
			this.fmt = fmt;
		}

		@Override
		public void render(Cell.Context c, Number value, SafeHtmlBuilder sb) {
			if (value==null) return;
			sb.appendHtmlConstant("<div style=\"text-align: right;\">");
			if (fmt!=null) sb.appendEscaped(fmt.format(value)); else sb.appendEscaped(value.toString());
			sb.appendHtmlConstant("</div>");
		}

	}

}
