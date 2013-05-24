/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver.client;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Ulf
 */
public class TablePageReply<T> implements Serializable{
	private List<T> list = null;

	int limit;
	int offset;
	boolean hasMoreRows;
	
	public TablePageReply() {
	}

	public List<T> getRows() {
		return list;
	}

	public void setRows(List<T> list) {
		this.list = list;
	}

	public boolean isHasMoreRows() {
		return hasMoreRows;
	}

	public void setHasMoreRows(boolean hasMoreRows) {
		this.hasMoreRows = hasMoreRows;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getRowCount() {
		return list!=null ? list.size() : 0;
	}
	
	
	
}
