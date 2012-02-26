/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.common;

/**
 *
 * @author Ulf
 */
public interface HasRequestCompleted {
	public void requestCompleted(String info);
	public void requestCancelled(String info);
}
