/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxlibrary;

import javax.ejb.Remote;

/**
 *
 * @author ulf
 */
@Remote
public interface LocalWebSupportRemote {
	public void deleteOrder(int ordernr);

	public void changeOrderRowAntal(int ordernr,short pos, double antal);

}
