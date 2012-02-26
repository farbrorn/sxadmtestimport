/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common.window;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 *
 * @author Ulf
 */
public class PopupMenu extends Button{
	private final MenuBar content = new MenuBar(true);
	private final PopupPanel pp = new PopupPanel(true);

	public PopupMenu(String text) {
		super(text);
		this.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showMenu();
			}
		});
	}

	public void showMenu() {
		showMenu(this.getAbsoluteLeft(), this.getAbsoluteTop()+this.getOffsetHeight());
	}

	public void showMenu(int x, int y) {
		pp.setPopupPosition(x,y);
		pp.show();
	}

	public void hideMenu() {
		pp.hide();
	}
		
	public void addMenuItem(final String text, final Command cmd) {
		Command cmd2 = new Command() {
			@Override
			public void execute() {
				hideMenu();
				if (cmd!=null) cmd.execute();
			}
		};
		content.addItem(text, cmd2).addStyleName("sx-popupmenu-item");
	}

	public MenuBar getMenu() { return content; }
}
