/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class FakturaBean {
	ArrayList<FakturaBeanRow> ord = new ArrayList();
	FakturaBeanRow cur = null;
	int curpos = 0;
	public void addRow(String artnr, String namn) {
		ord.add(new FakturaBeanRow(artnr, namn));
	}
	
	public void setFirstRow() {
		curpos = 0;
		try {
			cur = ord.get(curpos);
		} catch (Exception e) { }
	}
	public boolean next() {
		try {
			cur = ord.get(curpos);
		} catch (Exception e) { return false; }
		curpos++;
		return true;
	}
	public String getArtnr() {
		return cur.artnr;
	}
	public String getNamn() {
		return cur.namn;
	}

		
class FakturaBeanRow {
		public String artnr;
		public String namn;
		
	public FakturaBeanRow(String artnr, String namn) {
		this.artnr = artnr;
		this.namn = namn;
	}
}
	
}

