/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client;

/**
 *
 * @author Ulf
 */
public interface HasFormUpdater<T> {
	public void data2Form(T data);
	public T form2Data();
}
