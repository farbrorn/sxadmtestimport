/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

/**
 *
 * @author Ulf
 */
public interface TableRowLoadCallback<T> {
	public void onSuccess(T row);
	public void onFailure(Throwable caught);
}
