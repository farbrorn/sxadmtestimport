/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;

import java.util.Date;

/**
 *
 * @author ulf
 */
public class BetalaForskatPopUp extends DialogBox{
	private static final int COL_RUBRIK = 0;
	private static final int COL_INPUT = 1;

	private final	int ordernr;

	private final	Label statusMsg = new Label();
	private final	TextBox beloppBox = new TextBox();

	private final	HorizontalPanel betalsattPanel = new HorizontalPanel();
	private final	RadioButton betalsattRadioK = new RadioButton("b", "Kassa");
	private final	RadioButton betalsattRadioB = new RadioButton("b", "Bank");
	private final	RadioButton betalsattRadioP = new RadioButton("b", "Plusgiro");

	private final	VerticalPanel datePanel = new VerticalPanel();
	private final	Label dateText = new Label();
	private final	DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");

	private final	DatePicker datePicker  = new DatePicker();

	private final	TextBox talongLopNrLabel = new TextBox();

	private final	HorizontalPanel btnPanel = new HorizontalPanel();
	private final	Button btnOK = new Button("OK");
	private final	Button btnAvbryt = new Button("Avbryt");

	public BetalaForskatPopUp(final int ordernr, Double orderSumma) {
		super();
		FlexTable ft = new FlexTable();
		this.add(ft);
		this.ordernr = ordernr;
		if (orderSumma==null) orderSumma = 0.0;
		int row = 0;

		beloppBox.setValue(orderSumma.toString());

		betalsattRadioK.setValue(true);
		betalsattPanel.add(betalsattRadioK);
		betalsattPanel.add(betalsattRadioB);
		betalsattPanel.add(betalsattRadioP);

		datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date date = event.getValue();
				String dateString = dateFormat.format(date);
				dateText.setText(dateString);
			}
		});

		datePicker.setValue(new Date(), true);
		DateBox dateBox = new DateBox();
		dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));

		datePanel.add(dateText);
		datePanel.add(datePicker);
		datePanel.add(dateBox);

		btnOK.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doBtnOk();
			}
		});
		btnAvbryt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doBtnAvbryt();
			}
		});
		btnPanel.add(btnOK);
		btnPanel.add(btnAvbryt);

		ft.setWidget(row, COL_RUBRIK, statusMsg);
		ft.getFlexCellFormatter().setColSpan(row, COL_RUBRIK, 2);
		row++;

		ft.setWidget(row, COL_RUBRIK, new Label("Ordernr:"));
		ft.setWidget(row, COL_INPUT, new Label("" + ordernr));
		row++;

		ft.setWidget(row, COL_RUBRIK, new Label("Betalt Belopp:"));
		ft.setWidget(row, COL_INPUT, beloppBox);
		row++;

		ft.setWidget(row, COL_RUBRIK, new Label("Betalsätt:"));
		ft.setWidget(row, COL_INPUT, betalsattPanel);
		row++;

		ft.setWidget(row, COL_RUBRIK, new Label("Betaldatum:"));
		ft.setWidget(row, COL_INPUT, datePanel);
		row++;

		ft.setWidget(row, COL_RUBRIK, new Label("Talonglöpnr:"));
		ft.setWidget(row, COL_INPUT, talongLopNrLabel);
		row++;

		ft.setWidget(row, COL_RUBRIK, btnPanel);
		ft.getFlexCellFormatter().setColSpan(row, COL_RUBRIK, 2);
		row++;

	}

	private void doBtnOk() {
		statusMsg.setText("btnOK");
	}

	private void doBtnAvbryt() {
		this.hide();
	}
}
