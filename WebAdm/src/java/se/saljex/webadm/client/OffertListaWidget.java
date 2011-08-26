/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.Label;
import se.saljex.webadm.client.rpcobject.Offert1;

/**
 *
 * @author Ulf
 */
public class OffertListaWidget extends FlexTable implements HasFormUpdater<Offert1>{
	private static final int EPOSTBTNROW = 3;
	private static final int EPOSTBTNCOL = 0;

	Offert1ListWidget o1 = new Offert1ListWidget(this);
	Offert2ListWidget o2 = new Offert2ListWidget();

	FlexTable o2Widget = new FlexTable();
	FlexTable o1DataWidget = new FlexTable();

	InfoLabel infoLabel = new InfoLabel();

	KundEpostSelectWidget epostSelect;


	Button epostBtn = new Button("Sänd som e-post", new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			showEpostSelect();
		}
	});


	public OffertListaWidget() {
		o1.setHeight("400px");
		o2.setHeight("400px");
		SafeHtmlBuilder sb = new SafeHtmlBuilder();

		initO1DataWidget();


		o2Widget.setWidget(1, 0, o2);
		o2Widget.setWidget(2, 0, o1DataWidget);
		o2Widget.setWidget(EPOSTBTNROW, EPOSTBTNCOL, epostBtn);

		this.setWidget(0, 0, infoLabel);
		Offert1ListWidget.renderHeader(sb);
		this.setHTML(1, 0, sb.toSafeHtml());
		sb = new SafeHtmlBuilder();
		Offert2ListWidget.renderHeader(sb);
		this.setHTML(1, 1, sb.toSafeHtml());

		this.getRowFormatter().setVerticalAlign(2, HasVerticalAlignment.ALIGN_TOP);
		this.setWidget(2, 0, o1);
		this.setWidget(2, 1, o2Widget);
	}

	@Override
	public void data2Form(Offert1 data) {
		if (o2 != null) {
			o2.loadOffertNr(data.offertnr);
		}
		setO1DataWidgetData(data);
	}

	private void initO1DataWidget() {
		o1DataWidget.setWidget(0, 0, new Label("Offertnr"));
		o1DataWidget.setWidget(1, 0, new Label("Datum"));
		o1DataWidget.setWidget(2, 0, new Label("Kund"));
	}

	private void setO1DataWidgetData(Offert1 data) {
		o1DataWidget.setWidget(0, 1, new Label(data.offertnr.toString()));
		o1DataWidget.setWidget(1, 1, new Label(data.datum.toString()));
		o1DataWidget.setWidget(2, 1, new Label(o1.getSelectedObject().namn));

	}

	private void showEpostSelect() {
		epostSelect = new KundEpostSelectWidget(o1.getSelectedObject().kundnr, new SendOffertEpostHandler("00", o1.getSelectedObject().offertnr, infoLabel, infoLabel), new HasRequestCompleted() {

			@Override
			public void requestCompleted(String info) {		}

			@Override
			public void requestCancelled(String info) {				}
		});

		epostSelect.center();
		epostSelect.show();
		//o2Widget.setWidget(EPOSTBTNROW, EPOSTBTNCOL, epostSelect);
	}

	@Override
	public Offert1 form2Data(){ return null;}



}
