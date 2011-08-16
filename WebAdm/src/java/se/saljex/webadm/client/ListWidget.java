/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import java.util.List;
import se.saljex.webadm.client.rpcobject.IsSQLTable;

/**
 *
 * @author Ulf
 */
public class ListWidget<T extends IsSQLTable> extends ScrollPanel {

	private  final CellList<T> cellList;

	private  final ListDataProvider<T>	listDataProvider ;
	private  final HasFormUpdater<T> formUpdater;
	private  final PageLoad<T> pageLoad;
	private  final SingleSelectionModel<T> selectionModel  = new SingleSelectionModel<T>();
	protected final HasShowError showError;

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


	public ListWidget(HasFormUpdater<T> formUpdat, AbstractCell cell, PageLoad<T> pageLoad, HasShowError showError) {
		super();
		this.showError=showError;
		this.formUpdater = formUpdat;
		this.pageLoad=pageLoad;

		pageLoad.setPageLoadCallback(callback);

		cellList = new CellList<T>(cell);


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

		setHeight("100%");
		add(cellList);

	}

	public CellList<T> getCellList() {return cellList; }
	public PageLoad<T> getPageLoad() {return pageLoad; }

}
