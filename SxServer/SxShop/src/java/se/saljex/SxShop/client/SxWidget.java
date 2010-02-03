/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *
 * @author ulf
 */
public class  SxWidget extends VerticalPanel {
	protected GlobalData globalData;
	private Label errorLabel = new Label();
	protected Anchor visaFlerRaderAnchor = new Anchor("Visa fler rader");
	protected  int currentRow=1;
	protected  boolean blockTillFinishedLoading=false;
	protected  static final int pageSize=20;
	protected  boolean currentRowHighlite = true;

	protected AsyncCallback callbackLoadPage = new AsyncCallback() {

		public void onFailure(Throwable caught) {
			blockTillFinishedLoading=false;
			pageLoadError(caught);
		}

		public void onSuccess(Object result) {
			clearError();
			pageLoaded(result);
			blockTillFinishedLoading=false;
		}
	};

	public SxWidget(GlobalData globalData, String hederString) {
		visaFlerRaderAnchor.setVisible(false);
		visaFlerRaderAnchor.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!blockTillFinishedLoading) {
					visaFlerRaderAnchor.setVisible(false);
					blockTillFinishedLoading=true;
					loadNextPage();
				}
			}	});

		setWidth("100%");

		add(errorLabel);
		errorLabel.setVisible(false);
		errorLabel.addStyleName(globalData.STYLE_ERROR);
		this.globalData=globalData;
		Label l = new Label(hederString);
		l.addStyleName(globalData.STYLE_HUVUDRUBRIK);
		add(l);
	}

	interface SxWidgetConstructor {
		public SxWidget getWidget();
	}

	// Overrida denna metod för att hämta fler rader via Visa fler rader-länken
	protected void loadNextPage() {

	}

	// Overrida denna metod för att processa callbackLoadPage
	protected void pageLoaded(Object o) {

	}

	// Overrida denna metod för att processa callbackLoadPage vid error
	protected void pageLoadError(Throwable caught) {
			setError("Fel:" + caught.getMessage());
	}
	
	protected void setError(String msg) {
		errorLabel.setText(msg);
		errorLabel.setVisible(true);
	}

	protected void clearError() {
		errorLabel.setVisible(false);
	}


}
