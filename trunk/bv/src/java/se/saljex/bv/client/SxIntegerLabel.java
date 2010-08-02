/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.user.client.ui.Label;

/**
 *
 * @author ulf
 */
public class SxIntegerLabel extends Label{
	private Integer integerValue;
	public SxIntegerLabel(Integer integerValue) {
		super();
		this.integerValue = integerValue;
		if (integerValue==null) setText(""); else setText(""+integerValue);
	}

	public int getIntValue() { return integerValue; }

}
