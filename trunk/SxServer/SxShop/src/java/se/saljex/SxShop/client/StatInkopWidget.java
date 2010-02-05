/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.ColumnFormatter;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;
import com.google.gwt.user.client.ui.Label;
import java.util.ArrayList;
import se.saljex.SxShop.client.rpcobject.KundresRow;
import se.saljex.SxShop.client.rpcobject.StatInkopHeader;
import se.saljex.SxShop.client.rpcobject.StatInkopRow;

/**
 *
 * @author ulf
 */
public class StatInkopWidget extends SxWidget {
	private FlexTable ft = new FlexTable();
	private CellFormatter ftCellFormatter = ft.getCellFormatter();
	private RowFormatter ftRowFormatter = ft.getRowFormatter();
	private HTML chart = new HTML();


	public StatInkopWidget(GlobalData globalData, String headerText) {
		super(globalData, headerText);
		ft.setWidth("100%");
		add(chart);
		add(ft);
		ft.setCellPadding(1);
		ft.setCellSpacing(0);
		ft.addStyleName(globalData.STYLE_TABLE_INFO);
		fillFt();
	}

	private void fillFt() {
		currentRow=1;
		ft.clear();
		ftCellFormatter.addStyleName(0,0, globalData.STYLE_TD_IDNR);
		ftCellFormatter.addStyleName(0,1, globalData.STYLE_TD_N5);
		ftCellFormatter.addStyleName(0,2, globalData.STYLE_TD_N5);
		ftCellFormatter.addStyleName(0,3, globalData.STYLE_TD_N5);
		ftCellFormatter.addStyleName(0,4, globalData.STYLE_TD_N5);
		ftCellFormatter.addStyleName(0,5, globalData.STYLE_TD_N5);
		ftCellFormatter.addStyleName(0,6, globalData.STYLE_TD_N5);
		ftCellFormatter.addStyleName(0,7, globalData.STYLE_TD_N5);
		ftCellFormatter.addStyleName(0,8, globalData.STYLE_TD_N5);
		ftCellFormatter.addStyleName(0,9, globalData.STYLE_TD_N5);
		ftCellFormatter.addStyleName(0,10, globalData.STYLE_TD_N5);
		ftCellFormatter.addStyleName(0,11, globalData.STYLE_TD_N5);
		ftCellFormatter.addStyleName(0,12, globalData.STYLE_TD_N5);
		ft.setText(0, 0, "År");
		ft.setText(0, 1, "Jan");
		ft.setText(0, 2, "Feb");
		ft.setText(0, 3, "Mar");
		ft.setText(0, 4, "Apr");
		ft.setText(0, 5, "Maj");
		ft.setText(0, 6, "Jun");
		ft.setText(0, 7, "Jul");
		ft.setText(0, 8, "Aug");
		ft.setText(0, 9, "Sep");
		ft.setText(0, 10, "Okt");
		ft.setText(0, 11, "Nov");
		ft.setText(0, 12, "Dec");
		ftRowFormatter.addStyleName(0, globalData.STYLE_TR_RUBRIK);
		globalData.service.getStatInkopRows(5,callbackLoadPage);
		chart.setHTML("");
	}

	@Override
	protected void pageLoaded(Object result) {
		StatInkopHeader st=(StatInkopHeader)result;
		if (st==null || st.rows==null) {
			setError("Det finns inga data");
		} else {
			clearError();
			if (st.chartUrl!=null) chart.setHTML("<img src=\"" + st.chartUrl + "\"/>"); else chart.setHTML("");
			for (StatInkopRow row : st.rows) {
				ft.setText(currentRow, 0, globalData.numberFormatInt.format(row.ar));
				int col=1;
				for (int man=0; man<=11; man++) {
					ft.setText(currentRow, col, globalData.numberFormatInt.format(row.summa[man]));
					col++;
				}
				if (currentRowHighlite ) {
					ftRowFormatter.addStyleName(currentRow, globalData.STYLE_TR_ODDROW);
				}
				currentRow++;
				currentRowHighlite=!currentRowHighlite;
			}
		}

	}

}
