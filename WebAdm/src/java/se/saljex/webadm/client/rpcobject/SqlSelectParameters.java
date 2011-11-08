/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 *
 * @author Ulf
 */
public class SqlSelectParameters implements IsSerializable{


	public ArrayList<SqlWhereParameter> sqlWhereParameters = new ArrayList<SqlWhereParameter>();
	public ArrayList<SqlOrderByParameter> sqlOrderByParameters = new ArrayList<SqlOrderByParameter>();
	
	public SqlSelectParameters() {}
	
	public void addWhereParameter(int boolConnector, int noStartPar, String column, int operator, String value, int noEndPar) {
		sqlWhereParameters.add(new SqlWhereParameter(boolConnector, noStartPar, column, operator, value, noEndPar));
	}
	public void addOrderByParameter(String column, int sortorder) {
		sqlOrderByParameters.add(new SqlOrderByParameter(column, sortorder));
	}

}
