/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import java.util.ArrayList;
import java.util.List;
import se.saljex.webadm.client.constants.KeyCodes;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public abstract class TableFormWidget<T extends IsSQLTable> extends FlowPanel implements HasFormUpdater<T>{

	FormInputPanel<T> focusForm = new FormInputPanel();
	protected  PageLoad<T> pageLoad;
	protected  T originalSQLTableRow;
	private List<TableFormRowUpdatedInterface<T>> tableRowUpdatedList=new ArrayList<TableFormRowUpdatedInterface<T>>(1);
	
	protected	final SaveTableRowButton<T> saveBtn = new SaveTableRowButton<T>();
		final SaveTableRowButtonHandler<T> saveBtnHandler = new SaveTableRowButtonHandler<T>() {

			@Override
			public void onClick(ClickEvent event) {
				saveBtn.doClick(form2Data(), originalSQLTableRow);
			}

			@Override
			public void onSuccess(T newRow, T oldRow) {
				doTableRowUpdated(newRow, oldRow);
			}

			@Override
			public void onFailure(Throwable caught) {

			}
		};


	public TableFormWidget() {
		this(null);
	}
	public TableFormWidget(PageLoad<T> pageLoad) {
		this.pageLoad = pageLoad;
		saveBtn.setHandler(saveBtnHandler);
	}


	public void addTableRowUpdatedListener(TableFormRowUpdatedInterface<T> tableRowUpdated) {
		tableRowUpdatedList.add(tableRowUpdated);
	}

	protected  void doTableRowUpdated(T newRow, T oldRow) {
		data2Form(newRow);
		for (TableFormRowUpdatedInterface<T> tableRowUpdated : tableRowUpdatedList) {
			tableRowUpdated.onRowUpdated(newRow, oldRow);
		}
	}

	protected final KeyDownHandler standardKeyDownHandler = new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				int code=event.getNativeKeyCode();
				if (pageLoad!=null) {
					if (code==KeyCodes.F7 && !event.isAnyModifierKeyDown()) pageLoad.previous();
					else if(code == KeyCodes.F8 && !event.isAnyModifierKeyDown()) pageLoad.next();
				}
			}
		};

	protected  void addInput(FlexTable ft, String label, FormWidgetInterface widget) {
		int row = ft.getRowCount();
		ft.setWidget(row, 0, new Label(label));
		ft.setWidget(row, 1, widget.asWidget());
		focusForm.addFocusWidget(widget);
	}

	protected  void addInputWithStandardKeyDownHandler(FlexTable ft, String label, FormWidgetInterface widget) {
		addInput(ft, label, widget);
		widget.addKeyDownHandler(standardKeyDownHandler);
	}

	protected  void doSetKeyCodeSearch(String field, String value, KeyDownEvent event) {
		if (pageLoad!=null) {
			int code=event.getNativeKeyCode();
			if (code==KeyCodes.F9 && !event.isAnyModifierKeyDown()) pageLoad.setSearch(field, value, field, SQLTableList.COMPARE_GREATER_EQUALS , SQLTableList.SORT_ASCENDING);
			else if(code == KeyCodes.F5 && !event.isAnyModifierKeyDown()) pageLoad.setSearch(field, value, field, SQLTableList.COMPARE_SUPERSOK, SQLTableList.SORT_ASCENDING);
		}
	}


	@Override
	public abstract T form2Data();
	@Override
	public abstract void data2Form(T kund);


}
