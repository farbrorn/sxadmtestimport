/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.webadm.client.orderregistrering;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import se.saljex.webadm.client.commmon.constants.Const;
import se.saljex.webadm.client.common.HasData2Form;
import se.saljex.webadm.client.common.ListWidget;
import se.saljex.webadm.client.common.PageLoad;
import se.saljex.webadm.client.common.rpcobject.ArtikelSuggestion;
import se.saljex.webadm.client.common.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class SelectArtikel extends DialogBox{
	public static enum SEARCH_FIELD {
		ARTNR, NAMN, SUPERSOK
	}
	
	public static final int SEARCH_FIELD_NAMN=1;
	public static final int SEARCH_FIELD_SUPERSOK=2;
	
	private final FocusPanel focusPanel = new FocusPanel();
	private final FlowPanel vp = new FlowPanel();
	private final HorizontalPanel btnPanel = new HorizontalPanel();

	private SelectCallback<ArtikelSuggestion> callback;
	
	
	private final Button okBtn = new Button("OK", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			doSelect();
		}
	});

	private final Button avbrytBtn = new Button("Avbryt", new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			doCancel();
		}
	});
/*
	boolean initialFocusSet = false;
	
	HasData2Form<ArtikelSuggestion> artikelSuggest = new HasData2Form<ArtikelSuggestion>() {

		@Override
		public void data2Form(ArtikelSuggestion data) {
			if (!initialFocusSet) {
				initialFocusSet = true;

				Timer t = new Timer() {

					@Override
					public void run() {
			//				DebugMessagePanel.addMessage("Select " + listWidget.getSelectedObject().nummer);
							listWidget.getCellTable().getSelectionModel().setSelected(listWidget.getListDataProvider().getList().get(1), true);
							
							fir();
					}
				};
				t.schedule(3000);
//				Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
//					public void execute () {
//					}
//				});		
			}
	};};
			
	private void fir() {
				NativeEvent ne = Document.get().createKeyPressEvent(false, false, false, false, KeyCodes.KEY_TAB);
				
				DomEvent.fireNativeEvent(ne, this.getParent());
	}
*/	
	ListWidget<ArtikelSuggestion> listWidget = new ListWidget<ArtikelSuggestion>(null, new PageLoad<ArtikelSuggestion>(new ArtikelSuggestion(), 10, 100, 1000, null) ,null) {

		@Override
		public void addListColumns(CellTable<ArtikelSuggestion> cellTable) {
			getCellTable().addColumnStyleName(0, Const.Style_S10);
			getCellTable().addColumnStyleName(1, Const.Style_S20);
			TextColumn<ArtikelSuggestion> c1 = new TextColumn<ArtikelSuggestion>() {
				@Override
				public String getValue(ArtikelSuggestion object) {
					return object.nummer;
				}
			};
			cellTable.addColumn(c1, "Nummer");

			c1 = new TextColumn<ArtikelSuggestion>() {
				@Override
				public String getValue(ArtikelSuggestion object) {
					return object.namn;
				}
			};
			cellTable.addColumn(c1, "Namn");
		}
	};
	
	public SelectArtikel(SelectCallback<ArtikelSuggestion> callback) {
//		this.setSize("40em", Math.round(Window.getClientHeight()*0.5) + "px");
		this.callback = callback;
		listWidget.setHeight(Math.round(Window.getClientHeight()*0.5) + "px");
		listWidget.setWidth("30em");
		vp.add(listWidget);
		btnPanel.add(okBtn);
		btnPanel.add(avbrytBtn);
		vp.add(btnPanel);
		focusPanel.add(vp);
		this.setWidget(focusPanel);
		focusPanel.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				int keycode = event.getNativeKeyCode();
				if (keycode==KeyCodes.KEY_ENTER) doSelect();
				else if (keycode==KeyCodes.KEY_ESCAPE) doCancel();
			}
		});
	}
	
	public void setCallback(SelectCallback<ArtikelSuggestion> callback) {
		this.callback = callback;
	}
	
	private void doSelect() {
//		initialFocusSet = false;
		callback.onSelect(listWidget.getSelectedObject());
		this.hide();
	}
	
	private void doCancel() {
//		initialFocusSet = false;
		callback.onCancel();
		this.hide();
	}
	
	public void show(String sokString, int compareType, SEARCH_FIELD searchField) {
		switch (searchField) {
			case NAMN:
				listWidget.getPageLoad().setSearch("namn", sokString, "namn", compareType, SQLTableList.SORT_ASCENDING);
				break;
			case SUPERSOK:
				listWidget.getPageLoad().setSearch("namn", sokString, "nummer", SQLTableList.COMPARE_SUPERSOK, SQLTableList.SORT_ASCENDING);
				break;
			default:
				listWidget.getPageLoad().setSearch("nummer", sokString, "nummer", compareType, SQLTableList.SORT_ASCENDING);
				break;
		}
			
		
		focusPanel.setFocus(true);
		this.center();
		this.show();
	}
	

	
	
}
