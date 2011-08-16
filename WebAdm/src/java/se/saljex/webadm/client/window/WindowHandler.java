/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.window;

import se.saljex.webadm.client.window.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;

/**
 *
 * @author Ulf
 */
public class WindowHandler extends VerticalPanel implements WindowHandlerInterface {
	private final HorizontalPanel titleBar = new HorizontalPanel();
	private final VerticalPanel windowPanel = new VerticalPanel();
	private final ScrollPanel windowScrollPanel = new ScrollPanel(windowPanel);

	private final ArrayList<WindowTitleWidget> windowTitleList = new ArrayList();

	public WindowHandler() {
		super();
		com.google.gwt.user.client.Window.enableScrolling(false);
		addStyleName("w-windowhandler");
		titleBar.addStyleName("w-tabpanel");
		windowPanel.addStyleName("w-windowpanel");
		add(titleBar);
		add(windowScrollPanel);

		windowScrollPanel.setPixelSize(com.google.gwt.user.client.Window.getClientWidth()-windowScrollPanel.getAbsoluteLeft(), com.google.gwt.user.client.Window.getClientHeight()-windowScrollPanel.getAbsoluteTop());
		addWindow(new Label("Tab4"), "Tebben n4");

	}
//	private ArrayList<Window> windowList = new ArrayList();




	@Override
	public void closeWindow(Window window) {
		WindowTitleWidget prevWindow = null;
		WindowTitleWidget nextWindow = null;
		windowPanel.clear();

		for (WindowTitleWidget wt : windowTitleList) {
			if (wt.getWindow()==window) {
				prevWindow = nextWindow;
				wt.removeFromParent();
				windowTitleList.remove(wt);
			} else {
				nextWindow = wt;
			}
		}

		if (nextWindow!=null) setWindowActive(nextWindow.getWindow()); else if (prevWindow!= null) setWindowActive(prevWindow.getWindow());



	}

	@Override
	public void setWindowActive(Window window) {
		windowPanel.clear();
		windowPanel.add(window.getWindowWidget());

		for (WindowTitleWidget wt : windowTitleList) {
			if (wt.getWindow()==window) {
				wt.setActive(true);
			} else {
				wt.setActive(false);
			}
		}

	}

	@Override
	public void addWindow(Widget widget, String title) {
		Window window = new Window(this, widget, title);
		WindowTitleWidget windowTitle  = new WindowTitleWidget(window);
		windowTitleList.add(windowTitle);
		titleBar.add(windowTitle);
		setWindowActive(window);

	}
	@Override
	public void addWindow(Window window) {
		WindowTitleWidget windowTitle  = new WindowTitleWidget(window);
		windowTitleList.add(windowTitle);
		titleBar.add(windowTitle);
		setWindowActive(window);
	}
}
