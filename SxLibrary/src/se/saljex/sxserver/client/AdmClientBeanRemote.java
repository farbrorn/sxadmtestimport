/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver.client;

import javax.ejb.Remote;
import se.saljex.sxlibrary.exceptions.SXSecurityException;
import se.saljex.sxserver.tables.TableKund;

/**
 *
 * @author Ulf
 */
@Remote
public interface AdmClientBeanRemote {

	void setTest(String v);

	String getTest();

	TablePageReply<TableKund> getKundRowList(String sokStr, int limit, int offset);

	
}
