/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

/**
 *
 * @author Ulf
 */
public class PageLoadTableArtikel {

	private final int pageSize;	//No of rows fetcht at each request
	private final int bufferPages; // No of pages to buffer
	private Page[] pages;
	private boolean recordActive=false;
	private int currRow=0;
	private int currPage=0;




	public PageLoadTableArtikel(int pageSize, int bufferPages) {
		this.pageSize = pageSize >2 ? pageSize : 10;
		this.bufferPages = bufferPages > 2 ? bufferPages : 10;
		resetBuffer();
	}

	public void next() {
//		if (pages[currPage].records[currRow].eof) throw new Exception();
		if (currRow < pageSize-2) {
			currRow++;
		} else {
			if(currPage >= bufferPages-1) {
				getNextPage();
				currRow=0;
				next();
				return;
			}
		}
	}
	private void getNextPage() {
	}

	private void loadNextPage() {

	}
	
	private void addPageAtBeginning(Page page) {
		//Move pages in buffer, popping out the last page
		for (int cn = bufferPages-1; cn > 0; cn--) {
			pages[cn] = pages[cn-1];
		}
		pages[0] = page;
	}
	
	private void addPageAtEnd(Page page) {
		//Move pages in buffer, popping out the first page
		for (int cn = 0; cn < bufferPages-2; cn++) {
			pages[cn] = pages[cn+1];
		}
		pages[0] = page;		
	}

	public void reset(String artnr) {
		resetBuffer();
		recordActive = false;
	}
	
	//Emptys buffer
	public void resetBuffer() {
		pages = new Page[bufferPages];
	}
	
	
	public class Record {
		public String artikel=null;
		public boolean eof=false;
		public boolean bof=false;
	}
	public class Page {
		public Record[] records;
		boolean isLoaded=false;

		public Page() {
			records = new Record[pageSize];
		}
		
	}

}
