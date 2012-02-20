/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import se.saljex.webadm.client.constants.Const;
import com.google.gwt.event.dom.client.KeyCodes;

/**
 *
 * @author Ulf
 */
public class MessagePopupPanel extends DialogBox implements HasShowMessage{


	private final HorizontalPanel btnPanel = new HorizontalPanel();

	private final ArrayList<Button> btnList = new ArrayList<Button>();
	Grid messageGrid = new Grid(2,1);

	public MessagePopupPanel() {
		this(true,null);
	}
	public MessagePopupPanel(String titel) {
		this(true, titel);
	}
	public MessagePopupPanel(boolean modal) {
		this(modal,null);
	}
	public MessagePopupPanel(boolean modal, String titel) {
		this(false, modal, titel);
	}

	public MessagePopupPanel(boolean autoHide, boolean modal, String titel) {
		super(autoHide,modal);
		this.setText(titel);
		messageGrid.setWidget(1, 0, btnPanel);
		setWidget(messageGrid);
		btnPanel.setSpacing(2);

	}

	private void close() {
		this.hide();
	}

	@Override
	public void showErr(String err) {
		Label label = new Label(err);
		label.addStyleName(Const.Style_ErrorLabel);
		showWidget(label);
	}

	@Override
	public void showInfo(String info) {
		Label label = new Label(info);
		label.addStyleName(Const.Style_InfoLabel);
		showWidget(label);
	}

	public void show(String s, final MessagePopuCallback callback) {
		showWidget(new Label(s), callback);
	}
	public void show(String s) {
		show(s, null);
	}

	public void showWidget(Widget widget, final MessagePopuCallback callback) {
		showWithButtonList(widget, new String[]{"OK"}, callback);
	}
	public void showWidget(Widget widget) {
		showWidget(widget, null);
	}

	// Vid callback så är OK=0, Avbryt=1
	public void showWidgetWithOkAvbryt(Widget widget, final MessagePopuCallback callback) {
		showWithButtonList(widget, new String[]{"OK", "Avbryt"}, callback);
	}
	public void showWidgetWithOkAvbryt(Widget widget) {
		showWidgetWithOkAvbryt(widget, null);
	}


	public void showWithOkAvbryt(String s, final MessagePopuCallback callback) {
		showWidgetWithOkAvbryt(new Label(s), callback);
	}
	public void showWithOkAvbryt(String s) {
		showWithOkAvbryt(s, null);
	}

	public void showWithButtonList(String s, String[] buttons, final MessagePopuCallback callback) {
		showWithButtonList(new Label(s), buttons, callback);
	}
	public void showWithButtonList(String s, String[] buttons) {
		showWithButtonList(s, buttons, null);
	}

	
	public void showWithButtonList(Widget widget, String[] buttons) {
		showWithButtonList(widget, buttons, null);
	}

	public void showWithButtonList(Widget widget, String[] buttons, final MessagePopuCallback callback) {
		Button btn;
		btnPanel.clear();
		btnList.clear();
		messageGrid.setWidget(0, 0, widget);
		if (buttons!=null) {
			for (int cn=0; cn<buttons.length;cn++) {
				if (buttons[cn]!=null) {
					final int cnFinal = cn;
					btn = new Button(buttons[cn], new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							close();
							if (callback!=null) callback.btnClicked(cnFinal);
						}
					});
					btn.addKeyDownHandler(new KeyDownHandler() {

						@Override
						public void onKeyDown(KeyDownEvent event) {
							int keycode = event.getNativeKeyCode();
							if (keycode==KeyCodes.KEY_DOWN || keycode==KeyCodes.KEY_RIGHT) {
								setFocusNext(cnFinal);
							} else if (keycode==KeyCodes.KEY_UP || keycode==KeyCodes.KEY_LEFT) {
								setFocusPrevious(cnFinal);
							}
						}
					});
					btnList.add(btn);
					btnPanel.add(btn);
				}
			}
		}
		center();
		show();
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			public void execute () {
				if (btnList.size()>0) btnList.get(0).setFocus(true);
			}
		});
	}

	private void setFocusNext(int currBtn) {
		if (btnList.size()-1 > currBtn) btnList.get(currBtn+1).setFocus(true);
		else btnList.get(0).setFocus(true);
	}

	private void setFocusPrevious(int currBtn) {
		if (currBtn<=0) btnList.get(btnList.size()-1).setFocus(true);
		else btnList.get(currBtn-1).setFocus(true);
	}

}
