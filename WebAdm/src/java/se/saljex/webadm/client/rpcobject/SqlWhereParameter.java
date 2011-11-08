package se.saljex.webadm.client.rpcobject;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SqlWhereParameter implements IsSerializable {

	public int boolConnector = 0; //Vilken connector som ska inleda delsatsen - ingen, and, or
	public int noStartPar = 0; //Antal startparanteser '(' som ska följa efter boolConnecto
	public String column = null; //Kolumnnamnet för operatorn
	public int operator = 0; //Operator - =, >, like osv...
	public String value = null; //Värdet som används till filtret
	public int noEndPar = 0; //Antal slutparanteser ')' som ska följa efter delsatsen

	public SqlWhereParameter() {
		
	}

	public SqlWhereParameter(int boolConnector, int noStartPar, String column, int operator, String value, int noEndPar) {
		this.boolConnector = boolConnector;
		this.noStartPar = noStartPar;
		this.column = column;
		this.operator = operator;
		this.value = value;
		this.noEndPar = noEndPar;
	}
}
