/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.sxlibrary.SXConstant;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.Order1Combo;
import se.saljex.webadm.client.rpcobject.SQLTableList;
import se.saljex.webadm.client.rpcobject.SqlSelectParameters;
import se.saljex.webadm.server.SQLTableHandler;

/**
 *
 * @author Ulf
 */
public class OverforOrderMainWidget extends FlowPanel{
	Button testBtn = new Button("Test", new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			VerifyAnvandare vf = new VerifyAnvandare(new AsyncCallback<String>() {

				@Override
				public void onFailure(Throwable caught) {
					Util.showModalMessage("onFailure");
				}

				@Override
				public void onSuccess(String result) {
					Util.showModalMessage("onSucess " + result);
				}
			});
			vf.show();
		}
	});

	FlowPanel sokPanel = new FlowPanel();
	Label sokLabel = new Label("Sök:");
	TextBox	sokInput = new TextBox();
		Button sokBtn = new Button("Sök", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sok();
			}
		});
	private static final String filterGroupName = "f";
	Label filterLabel = new Label("Filter: ");
	RadioButton r1 = new RadioButton(filterGroupName, "Alla");
	RadioButton r2 = new RadioButton(filterGroupName, "Sparade");
	RadioButton r3 = new RadioButton(filterGroupName, "Avvaktande");
	RadioButton r4 = new RadioButton(filterGroupName, "Förskott");

	int selectedFilterRadio = 0;
	private static final int selectedRadioAlla = 0;
	private static final int selectedRadioSparade = 1;
	private static final int selectedRadioAvvaktande = 2;
	private static final int selectedRadioForskott = 3;

	SqlSelectParameters sqlSelectParameters = new SqlSelectParameters();


	OverforOrderWidget<Order1Combo> overforWidget = new OverforOrderWidget(new Order1Combo());

	public OverforOrderMainWidget() {

			sokInput.addKeyUpHandler(new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (event.isDownArrow() || event.isUpArrow()) {
						overforWidget.getCellTable().setFocus(true);
					} else	sok();
				}
			});
			sokPanel.add(sokLabel);
			sokPanel.add(sokInput);
			sokPanel.add(sokBtn);
		sokPanel.add(testBtn);
			//Filter
			r1.setValue(true);
			filterLabel.addStyleName(Const.Style_Display_Inline);
			filterLabel.addStyleName(Const.Style_Margin_1em_Left);
			sokPanel.add(filterLabel);
			sokPanel.add(r1);
			sokPanel.add(r2);
			sokPanel.add(r3);
			sokPanel.add(r4);
			r1.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override	public void onValueChange(ValueChangeEvent<Boolean> event) {
					if (event.getValue()) { // Radio Clicked
						selectedFilterRadio = selectedRadioAlla;
						sok();
					}
				}
			});
			r2.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override	public void onValueChange(ValueChangeEvent<Boolean> event) {
					if (event.getValue()) { // Radio Clicked
						selectedFilterRadio = selectedRadioSparade;
						sok();
					}
				}
			});
			r3.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override	public void onValueChange(ValueChangeEvent<Boolean> event) {
					if (event.getValue()) { // Radio Clicked
						selectedFilterRadio = selectedRadioAvvaktande;
						sok();
					}
				}
			});
			r4.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
				@Override	public void onValueChange(ValueChangeEvent<Boolean> event) {
					if (event.getValue()) { // Radio Clicked
						selectedFilterRadio = selectedRadioForskott;
						sok();
					}
				}
			});


			sokLabel.addStyleName(Const.Style_FloatLeft);
			add(sokPanel);
			add(overforWidget);
			overforWidget.addStyleName(Const.Style_FloatLeft);
			sokPanel.setHeight("2.2em");
			overforWidget.setHeight("85%");
	}

	private void sok() {
		sqlSelectParameters.clearWhereParameters();
		if (selectedFilterRadio==selectedRadioAvvaktande) sqlSelectParameters.addWhereParameter("status", SQLTableList.COMPARE_EQUALS , Const.ORDER_STATUS_AVVAKT);
		else if(selectedFilterRadio == selectedRadioForskott) sqlSelectParameters.addWhereParameter("status", SQLTableList.COMPARE_EQUALS, Const.ORDER_STATUS_FORSKOTT);
		else if(selectedFilterRadio == selectedRadioSparade) sqlSelectParameters.addWhereParameter("status", SQLTableList.COMPARE_EQUALS, Const.ORDER_STATUS_SPARAD);

		if (!Util.isEmpty(sokInput.getText())) sqlSelectParameters.addWhereParameter(SQLTableList.BOOL_CONNECTOR_AND, "ordernr", SQLTableList.COMPARE_SUPERSOK, sokInput.getText());

		sqlSelectParameters.clearOrderByParameters();
		sqlSelectParameters.addOrderByParameter("ordernr", SQLTableList.SORT_DESCANDING);
		
		overforWidget.setSearch(sqlSelectParameters);

//		if (Util.isEmpty(sokInput.getText())) overforWidget.setSearch("ordernr", "0", "ordernr", SQLTableList.COMPARE_NONE, SQLTableList.SORT_DESCANDING);
//		else overforWidget.getPageLoad().setSearch("ordernr", sokInput.getText(), "ordernr", SQLTableList.COMPARE_SUPERSOK, SQLTableList.SORT_DESCANDING);
	}

}
