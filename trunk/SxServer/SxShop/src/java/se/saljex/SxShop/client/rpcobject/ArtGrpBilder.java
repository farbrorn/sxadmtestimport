/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.SxShop.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author ulf
 */
public class ArtGrpBilder implements IsSerializable{
	public ArtGrpBilder() {}
	public int grpid=0;
	public ArrayList<String> bilder = new ArrayList();

}
