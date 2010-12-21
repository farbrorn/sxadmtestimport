/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import se.saljex.sxlibrary.SXUtil;

/**
 *
 * @author ulf
 */
public class WebTable <T> {

	protected EntityManager em;
	protected Query q=null;


	public WebTable(EntityManager em) {
		this.em=em;
	}


	private int pageSize = 20;
	private int currentPage=0;
	private int nextPage=0;
	private int page=0;

	private String sql;


	public void close() throws SQLException{
	}

	public void setPageSize(int ps) {
		pageSize = ps;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getCurrentPageNo() {
		return currentPage;
	}
	public int getNextPageNo() {
		return nextPage;
	}
	public int getPreviousPageNo() {
		if (currentPage > 1) { return currentPage-1; } else { return currentPage; }
	}

	public boolean hasNextPage() {
		return nextPage != 0;
	}
	public boolean hasPreviousPage() {
		return currentPage > 1;
	}
	public void setPage(int page) {
		this.page=page;
	}

	public void createQuery(String sql ) {
		q = em.createQuery(sql);
//		this.createQuery(entityClassName,null);

	}
	public void setQueryParameter(String name,  Object value) {
		q.setParameter(name, value);
	}

	public List<T> getPage() {

		if (page <= 0) { page = 1; }
		q.setFirstResult(pageSize*(page-1));

		if (pageSize > 0) {
			q.setMaxResults(pageSize+1);
		}
		List<T> l = q.getResultList();
		if (l.size() > pageSize && pageSize > 0) {
			l.remove(l.size()-1);			// Ta bortt sista raden eftersom den signalerar att det finns fler sidor
			nextPage = page+1;
			currentPage = page;
		} else {
			nextPage = 0;
			currentPage = page;
			if (l.size() < 1) currentPage = 0;
		}
		q = null;
		return l;
	}


}
