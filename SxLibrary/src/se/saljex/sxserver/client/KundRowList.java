/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import se.saljex.sxserver.tables.TableKund;

/**
 *
 * @author Ulf
 */
public class KundRowList implements Serializable{
	private List<TableKund> kundRows = new ArrayList<TableKund>();

	int limit;
	int offset;
	boolean hasMoreRows;
	
	public KundRowList() {
	}

	public List<TableKund> getKundRows() {
		return kundRows;
	}

	public void setKundRows(List<TableKund> kundRows) {
		this.kundRows = kundRows;
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
	
	
	
}
