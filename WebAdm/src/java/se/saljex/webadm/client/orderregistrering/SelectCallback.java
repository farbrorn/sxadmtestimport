/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.webadm.client.orderregistrering;

/**
 *
 * @author Ulf
 */
public interface SelectCallback<T> {
	public void onSelect(T object);
	public void onCancel();
}
