/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import se.saljex.webadm.client.constants.Const;
import se.saljex.webadm.client.rpcobject.WelcomeData;

/**
 *
 * @author Ulf
 */
public class WelcomeWidget extends FlowPanel{

	public WelcomeWidget() {
		Label namn = new Label("Välkommen  till " + MainEntryPoint.getInitialData().foretagNamn);
		namn.addStyleName(Const.Style_Bolder);
		namn.addStyleName(Const.Style_Blue);
		namn.addStyleName(Const.Style_TextLarge);
		add(namn);
		Util.getService().getWelcomeData((short)MainEntryPoint.getInloggadAnvandare().defaultLagernr, callback);

	}

	AsyncCallback<WelcomeData> callback = new AsyncCallback<WelcomeData>() {

		@Override
		public void onFailure(Throwable caught) {
			showError(caught);
		}

		@Override
		public void onSuccess(WelcomeData result) {
			showSucess(result);
		}
	};

	private void showError(Throwable caught) {
		add(new Label("Kan inte läsa data: " + caught.getMessage()));
	}

	private void showSucess(WelcomeData data) {
		Image chartTotalt = new Image(data.forsaljningChartURLTotalt);
		chartTotalt.addStyleName(Const.Style_FloatLeft);
		add(chartTotalt);
		add(new Image(data.forsaljningChartURLFilial));
	}


}
