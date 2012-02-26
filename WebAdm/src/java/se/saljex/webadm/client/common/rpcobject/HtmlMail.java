/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.webadm.client.common.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Ulf
 */
public class HtmlMail implements IsSerializable{

	public HtmlMail() {
	}
	
	public String to = null;
	public String cc = null;
	public String bcc = null;
	
	public String rubrik = null;
	public String html = null;
	public String altText = null;
	
}
