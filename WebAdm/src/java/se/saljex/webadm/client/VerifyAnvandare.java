/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import se.saljex.webadm.client.constants.Const;

/**
 *
 * @author Ulf
 */
public class VerifyAnvandare extends DialogBox {

	private final FlowPanel p = new FlowPanel();
	HorizontalPanel hp = new HorizontalPanel();
	HorizontalPanel hpBtn = new HorizontalPanel();
	private final Label inputLabel = new Label("Användare:");
	private final TextBox inputBox = new TextBox();
	private final Button okBtn = new Button("OK");
	private final Button avbrytBtn = new Button("Avbryt");
	AsyncCallback<String> callbackResponse;

	public VerifyAnvandare(AsyncCallback<String> callbackResponse) {
		super(false, true);
		this.setText("Bekräfta inloggning");
		this.callbackResponse = callbackResponse;


		inputLabel.addStyleName(Const.Style_Margin_1em_Right);
		inputBox.setMaxLength(3);
		inputBox.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (!event.isAnyModifierKeyDown()) {
					if (event.getNativeKeyCode()==KeyCodes.KEY_ENTER ) doOk();
					else if (event.getNativeKeyCode()==KeyCodes.KEY_ESCAPE ) doAvbryt();
				}
			}
		});

		inputBox.setWidth("5em");
		okBtn.addStyleName(Const.Style_Margin_1em_Right);
		okBtn.setWidth("5em");
		avbrytBtn.setWidth("5em");
		p.addStyleName(Const.Style_Margin_1em_Bottom);
		p.addStyleName(Const.Style_Margin_1em_Top);

		hp.addStyleName(Const.Style_Margin_1em_Bottom);
		hp.add(inputLabel);
		hp.add(inputBox);
		hpBtn.add(okBtn);
		hpBtn.add(avbrytBtn);

		p.add(hp);
		p.add(hpBtn);

		okBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doOk();
			}
		});

		avbrytBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doAvbryt();
			}
		});

		this.add(p);
		this.center();
	}


	@Override
	public void show() {
		inputBox.setValue("");
		super.show();
		inputBox.setFocus(true);
	}

	private void doOk() {
		Util.showModalWait();
		MainEntryPoint.getService().verifyAnvandareKort(inputBox.getValue(), new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Util.hideModalWait();
				doAvbryt();
			}

			@Override
			public void onSuccess(String result) {
				Util.hideModalWait();
				doSuccess(result);
			}
		});
	}

	private void doAvbryt() {
		this.hide();
		callbackResponse.onFailure(null);
	}

	private void doSuccess(String result) {
		if (!Util.isEmpty(result) && result.equals(inputBox.getValue())) {
			this.hide();
			callbackResponse.onSuccess(result);
		} else {
			Util.showModalMessage("Felaktig användare");
			inputBox.selectAll();
			inputBox.setFocus(true);
		}
	}

}
