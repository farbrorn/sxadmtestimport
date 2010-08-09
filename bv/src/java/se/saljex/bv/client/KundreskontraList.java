/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author Ulf
 */
public class KundreskontraList implements IsSerializable {
	public KundreskontraList(){}

	public ArrayList<Kundreskontra> kundreskontraList = new ArrayList();

}
