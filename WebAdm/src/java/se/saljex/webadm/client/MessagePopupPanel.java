/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import se.saljex.webadm.client.constants.Const;

/**
 *
 * @author Ulf
 */
public class MessagePopupPanel extends PopupPanel implements HasShowMessage{


	Button okBtn=null;
	Grid messageGrid =null;

	public MessagePopupPanel() {
		this(true);
	}
	public MessagePopupPanel(boolean modal) {
		super(false, modal);
	}

	private void setUp(Widget widget) {
		if (messageGrid == null) {
			messageGrid =  new Grid(2, 1);
			okBtn = new Button("OK", new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					close();
				}
			});
			messageGrid.setWidget(1, 0, okBtn);
			this.add(messageGrid);
		}
		messageGrid.setWidget(0, 0, widget);
		center();
		show();
	}

	private void close() {
		this.hide();
	}

	@Override
	public void showErr(String err) {
		Label label = new Label(err);
		label.addStyleName(Const.Style_ErrorLabel);
		setUp(label);
	}

	@Override
	public void showInfo(String info) {
		Label label = new Label(info);
		label.addStyleName(Const.Style_InfoLabel);
		setUp(label);
	}

	public void showWidget(Widget widget) {
		setUp(widget);
	}



}
