/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

import java.util.List;

/**
 *
 * @author Ulf
 */
public interface PageLoadCallback<T> {
	public void onRowUpdate(T row);
	public void onBufferUpdate(List<T> bufferList);
	public void onFailure(Throwable caught);

}
