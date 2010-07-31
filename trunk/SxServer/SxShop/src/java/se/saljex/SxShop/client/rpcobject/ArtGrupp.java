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
public class ArtGrupp implements IsSerializable {
	public int grpid;
	public int prevgrpid;
	public String rubrik;
	public ArrayList<String> bilder = new ArrayList();

	public ArtGrupp() {}
	public ArtGrupp(int grpid, int prevgrpid, String rubrik) {
		this.grpid = grpid;
		this.prevgrpid = prevgrpid;
		this.rubrik = rubrik;
	}

	public int getGrpid() {
		return grpid;
	}

	public void setGrpid(int grpid) {
		this.grpid = grpid;
	}

	public int getPrevgrpid() {
		return prevgrpid;
	}

	public void setPrevgrpid(int prevgrpid) {
		this.prevgrpid = prevgrpid;
	}

	public String getRubrik() {
		return rubrik;
	}

	public void setRubrik(String rubrik) {
		this.rubrik = rubrik;
	}
	
}
