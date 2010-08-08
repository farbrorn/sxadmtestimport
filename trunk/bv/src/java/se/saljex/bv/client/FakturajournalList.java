/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class FakturajournalList implements IsSerializable{

	public FakturajournalList() {}
	public ArrayList<Fakturajournal> fakturajournalList = new ArrayList();

}
