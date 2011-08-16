/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.server;

import javax.sql.DataSource;

/**
 *
 * @author Ulf
 */
abstract class AbstractSQLTryBlock {

	protected DataSource sxadm;

	public AbstractSQLTryBlock(DataSource sxadm) {
		this.sxadm=sxadm;
	}

	abstract void tryBlock();


}
