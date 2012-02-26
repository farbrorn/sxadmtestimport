/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import java.util.List;
import se.saljex.webadm.client.common.rpcobject.IsSQLTable;
import se.saljex.webadm.client.common.rpcobject.SqlSelectParameters;

/**
 *
 * @author Ulf
 */
public abstract class ListWidget<T extends IsSQLTable> extends ScrollPanel implements TableFormRowUpdatedInterface<T>{

//	private  final CellList<T> cellList;
private final CellTable<T> cellList;
	private  final ListDataProvider<T>	listDataProvider ;
	private  final HasData2Form<T> formUpdater;
	private  final PageLoad<T> pageLoad;
	private  final SingleSelectionModel<T> selectionModel  = new SingleSelectionModel<T>();
	protected final HasShowMessage showError;
	private Anchor merAnchor  = new Anchor("Visa fler rader...");

	@Override
	public void onRowUpdated(T newRow, T oldRow) {
		int id = listDataProvider.getList().indexOf(oldRow);
		if (id>=0) {
			listDataProvider.getList().set(id, newRow);
		}
	}
	protected PageLoadCallback<T> callback = new PageLoadCallback<T>() {

		@Override
		public void onRowUpdate(T row) {

		}

		@Override
		public void onBufferUpdate(List<T> bufferList) {

		}

		@Override
		public void onFailure(Throwable caught) {
			if (showError!=null) showError.showErr(caught.toString());
		}
	};

	public ListDataProvider<T> getListDataProvider() {return listDataProvider; }

	public ListWidget(HasData2Form<T> formUpdat, final PageLoad<T> pageLoad, HasShowMessage showError) {
		super();
		VerticalPanel mainPanel = new VerticalPanel();
		this.showError=showError;
		this.formUpdater = formUpdat;
		this.pageLoad=pageLoad;

		pageLoad.setPageLoadCallback(callback);

//		cellList = new CellList<T>(cell);
cellList = new CellTable<T>();
addListColumns(cellList);
cellList.addStyleName("sx-cellpanel");

		listDataProvider = new ListDataProvider<T>();
		listDataProvider.addDataDisplay(cellList);
		pageLoad.setBufferList(listDataProvider.getList());
		cellList.setSelectionModel(selectionModel);
		pageLoad.setCellList(cellList);		//Anropas efter att setSelectionModel

		cellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		cellList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);


		cellList.addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
			@Override
			public void onRowCountChange(RowCountChangeEvent event) {
				cellList.setPageSize(cellList.getRowCount());
				merAnchor.setVisible(pageLoad.getHasMoreRows());
			}
		});

		if (formUpdater!=null) {
			selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
				@Override
				public void onSelectionChange(SelectionChangeEvent event) {
					formUpdater.data2Form(selectionModel.getSelectedObject());

				}
			});
		}

		merAnchor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getPageLoad().prefetchNextBufferPage();
			}
		});

		setHeight("100%");
		cellList.setWidth("100%");
		merAnchor.setVisible(false);
//		cellList.setHeight("90%");
//		merAnchor.setHeight("10%");
		mainPanel.add(cellList);
		mainPanel.add(merAnchor);
		add(mainPanel);
	}

	public void setSearch(String field, String sokString, String sortField, int sokTyp, int sortOrder) {
		pageLoad.setSearch(sortField, sokString, sortField, sokTyp, sortOrder);
	}
	public void setSearch(SqlSelectParameters s) {
		pageLoad.setSearch(s);
	}

	public abstract void addListColumns(CellTable<T> cellTable) ;
//	public CellList<T> getCellList() {return cellList; }
	public PageLoad<T> getPageLoad() {return pageLoad; }

	public T getSelectedObject() { return selectionModel.getSelectedObject(); }
	public CellTable getCellTable() {return cellList; }



}
