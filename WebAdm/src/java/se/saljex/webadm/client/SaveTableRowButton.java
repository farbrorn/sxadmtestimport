/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.saljex.webadm.client.common.rpcobject.IsSQLTable;
import se.saljex.webadm.client.common.rpcobject.Kund;

/**
 *
 * @author Ulf
 */
public class SaveTableRowButton<T extends IsSQLTable> extends Button {
	private VerticalPanel vp = new VerticalPanel();
	private PopupPanel messageBox;
	private static final Label spararLabel = new Label("Sparar...");
	SaveTableRowButtonHandler<T> handler;

	private T newRow;
	private T oldRow;

	ClickHandler clickHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			if (handler!=null) handler.onClick(event);
		}
	};

	public SaveTableRowButton() {
		this(null);
	}
	public SaveTableRowButton(SaveTableRowButtonHandler<T> handler) {
		super("Spara");
		this.addClickHandler(clickHandler);
		setHandler(handler);
	}

	public void setHandler(SaveTableRowButtonHandler<T> handler) {
		this.handler=handler;
	}


	AsyncCallback callback = new AsyncCallback() {

		@Override
		public void onFailure(Throwable caught) {
			showError(caught);
			if (handler!= null) handler.onFailure(caught);
		}

		@Override
		public void onSuccess(Object result) {
			showError("Utfört");
			if (handler!=null) handler.onSuccess(newRow, oldRow);
		}
	};




	public void doClick(T newRow, T oldRow) {
		messageBox = new PopupPanel(false, true);
		vp.setHeight("10em");
		vp.setWidth("30em");
		vp.add(spararLabel);
		messageBox.setWidget(vp);
		messageBox.center();
		messageBox.show();
		if (newRow==null) {
			showError("Den uppdaterade raden är tom.");
		} else {
			this.newRow = newRow;
			this.oldRow = oldRow;
			MainEntryPoint.getService().putTableRow("", newRow, oldRow, callback);
		}
		
	}
	
	private void showError(Throwable caught) {
		showError(caught.getMessage());
	}
	
	private void showError(String errorStr) {
		Button okBtn = new Button("Ok", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				messageBox.hide();
			}
		});
		vp.clear();
		vp.add(new Label("Fel vid spara"));
		vp.add(new Label(errorStr));
		vp.add(okBtn);
	}

	private void showOk() {
		messageBox.hide();
	}

}
