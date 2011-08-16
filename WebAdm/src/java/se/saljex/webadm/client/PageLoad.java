 /* Tcao change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import com.google.gwt.user.cellview.client.AbstractHasData;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import java.util.ArrayList;
import java.util.List;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.RPCBlockedException;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
abstract class PageLoad<T extends IsSQLTable> {

	private int pageSize;
	private int prefetchPageSize;
	private int maxBufferSize;
	private PageLoadCallback<T> callback;
	private Integer currBufferPos=null;
	private List<T> currBuffer=new ArrayList();
	private boolean blockRPC = false;
	private boolean cancelPrefetch = false;
	private String currSortField;
	private String currSokField;
	private String currSokString=null;
	private int currNextOffset=0;
	private int currPreviousOffset=0;
	protected boolean isSuperSok = false;
	private AbstractHasData<T> hasData =null;

	public PageLoad(int pageSize, int prefetchPageSize, int maxBufferSize, PageLoadCallback<T> callback) {
		this.pageSize=pageSize>0 ? pageSize : 10;
		this.prefetchPageSize=prefetchPageSize>0 ? prefetchPageSize : 20;
		this.maxBufferSize=maxBufferSize>pageSize ? maxBufferSize : pageSize*2;
		this.callback=callback;
		if (callback==null) this.callback = new PageLoadCallback<T>() {

			@Override
			public void onRowUpdate(T row) {
			}

			@Override
			public void onBufferUpdate(List<T> bufferList) {
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		};
	}

	public void setPageLoadCallback(PageLoadCallback<T> callback) {
		this.callback=callback;
	}


	abstract void getList(GWTServiceAsync service, String sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit, AsyncCallback<SQLTableList> callback);
//			MainEntryPoint.getService().getKundList(sokString, currSokField , currSortField, isSuperSok ? SQLTableList.COMPARE_SUPERSOK : SQLTableList.COMPARE_GREATER_EQUALS, SQLTableList.SORT_ASCENDING, 0, pageSize, callbackSok);

	public void setCellList(final AbstractHasData<T> hasData) {
		this.hasData = hasData;
		if (hasData.getSelectionModel() instanceof SingleSelectionModel) {
			this.hasData.getSelectionModel().addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

				@Override
				public void onSelectionChange(SelectionChangeEvent event) {
					T k = ((SingleSelectionModel<T>)hasData.getSelectionModel()).getSelectedObject();
					currBufferPos = currBuffer.indexOf(k);
				}
			});
		}
	}

	public void setSearch(String field, String sokString, boolean isSuperSok) {
		if (blockRPC) {
			sendBlockedFailure();
		} else {
			blockRPC=true;
			cancelPrefetch=true;
			currSokField = field;
			currSortField = field;
			currSokString = sokString;
			currNextOffset=0;
			currPreviousOffset=0;
			this.isSuperSok=isSuperSok;

			getList(MainEntryPoint.getService(), sokString, currSokField , currSortField, isSuperSok ? SQLTableList.COMPARE_SUPERSOK : SQLTableList.COMPARE_GREATER_EQUALS, SQLTableList.SORT_ASCENDING, 0, pageSize, callbackSok);
		}
	}

	private void setBuffer(SQLTableList<T> list) {
		currBufferPos=null;
		currBuffer.clear();
		currBuffer.addAll(list.lista);
		callback.onBufferUpdate(currBuffer);
	}

	public List<T> getBufferList() {
		return currBuffer;
	}
	public void setBufferList(List<T> bufferList) {
		currBuffer = bufferList;
	}

	private void sendBlockedFailure() {
		callback.onFailure(new RPCBlockedException());
	}

	public void next() {
		if (currBufferPos==null) currBufferPos=-1;
		if (currBufferPos>=currBuffer.size()-1 ) {
			if(!getNextBufferPage()) sendBlockedFailure();
			cancelPrefetch=true;
		} else {
			currBufferPos++;
			if (hasData!= null) hasData.getSelectionModel().setSelected(currBuffer.get(currBufferPos), true); else
			callback.onRowUpdate(currBuffer.get(currBufferPos));
		}
	}

	public void previous() {
		if (currBufferPos==null) currBufferPos = currBuffer.size();
		if (currBufferPos > 0) {
			currBufferPos--;
			if (hasData!= null) hasData.getSelectionModel().setSelected(currBuffer.get(currBufferPos), true); else
			callback.onRowUpdate(currBuffer.get(currBufferPos));
		} else {
			if (!getPreviousBufferPage()) sendBlockedFailure();
			cancelPrefetch=true;
		}

	}

	private boolean getPreviousBufferPage() {
		if (isSuperSok) { callback.onRowUpdate(null); return true; }
		if (blockRPC) return false;
		blockRPC=true;
		getList(MainEntryPoint.getService(), currSokString, currSokField , currSortField, SQLTableList.COMPARE_LESS, SQLTableList.SORT_DESCANDING, currPreviousOffset, pageSize, callbackPreviousPage);
		return true;
	}

	private boolean getNextBufferPage() {
		if (blockRPC) return false;
		blockRPC=true;
		getList(MainEntryPoint.getService(), currSokString, currSokField , currSortField, isSuperSok ? SQLTableList.COMPARE_SUPERSOK : SQLTableList.COMPARE_GREATER, SQLTableList.SORT_ASCENDING, currNextOffset, pageSize, callbackNextPage);
		return true;

	}

	public boolean prefetchNextBufferPage() {
		if (cancelPrefetch) return false;
		getList(MainEntryPoint.getService(), currSokString, currSokField , currSortField, isSuperSok ? SQLTableList.COMPARE_SUPERSOK : SQLTableList.COMPARE_GREATER, SQLTableList.SORT_ASCENDING, currNextOffset, prefetchPageSize, callbackPrefetchNextPage);
		return true;
	}




	final AsyncCallback callbackSok = new AsyncCallback<SQLTableList<T>>() {
		public void onSuccess(SQLTableList<T> result) {
			currNextOffset = currNextOffset+result.lista.size();
			setBuffer(result);
			next();
			blockRPC=false;
			cancelPrefetch=false;
			prefetchNextBufferPage();
		}

		public void onFailure(Throwable caught) {
			blockRPC=false;
			callback.onFailure(caught);
		}
	};

	final AsyncCallback callbackNextPage = new AsyncCallback<SQLTableList<T>>() {
		public void onSuccess(SQLTableList<T> result) {
			if (result.lista.size()<1) callback.onRowUpdate(null);
			currNextOffset = currNextOffset+result.lista.size();
			currBuffer.addAll(result.lista);
			callback.onBufferUpdate(currBuffer);
			next();
			blockRPC=false;
			cancelPrefetch=false;
			prefetchNextBufferPage();
		}

		public void onFailure(Throwable caught) {
			blockRPC=false;
			callback.onFailure(caught);
		}
	};


	final AsyncCallback callbackPrefetchNextPage = new AsyncCallback<SQLTableList<T>>() {
		public void onSuccess(SQLTableList<T> result) {
			if (!cancelPrefetch && !blockRPC) {
				currNextOffset = currNextOffset+result.lista.size();
				currBuffer.addAll(result.lista);
				callback.onBufferUpdate(currBuffer);
				//if (currBuffer.size() < maxBufferSize) prefetchNextBufferPage();
			} else {
				if (!blockRPC) {
					prefetchNextBufferPage();
				}
			}
			cancelPrefetch=false;

		}

		public void onFailure(Throwable caught) {
		}
	};


	final AsyncCallback callbackPreviousPage = new AsyncCallback<SQLTableList<T>>() {
		public void onSuccess(SQLTableList<T> result) {
			currPreviousOffset = currPreviousOffset+result.lista.size();
			if (result.lista.size()<1) callback.onRowUpdate(null);
			ArrayList<T> ny = new ArrayList();
			//Sorteringsordningen är descanding från från servern, så vi ändrar om den
			for (int i=result.lista.size()-1; i>=0 ; i--){
				ny.add(result.lista.get(i));
			}

			result.lista = ny;
			currBufferPos = currBufferPos+ny.size();
			currBuffer.addAll(0, ny);
			callback.onBufferUpdate(currBuffer);
			previous();
			blockRPC=false;
			cancelPrefetch=false;
			// prefetchNextBufferPage();
		}

		public void onFailure(Throwable caught) {
			blockRPC=false;
			callback.onFailure(caught);
		}
	};


}
