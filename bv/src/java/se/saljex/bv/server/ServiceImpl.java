/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.bv.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ejb.EJB;
import javax.sql.DataSource;
import se.saljex.bv.client.Order1;
import se.saljex.bv.client.Order1List;
import se.saljex.bv.client.ServerErrorException;
import se.saljex.sxserver.SXConstant;
import se.saljex.sxserver.SxServerMainLocal;

/**
 *
 * @author ulf
 */
public class ServiceImpl {
	private SxServerMainLocal sxServerMainBean;
	private DataSource bvDataSource;
	private DataSource sxDataSource;

	private final static String BVKUNDNR="0855115290";
	 public final static int FILTER_ALLA=0;
	 public final static int FILTER_FOROVERFORING=1;
	 public final static int FILTER_OVERFORDA=2;
	 public final static int FILTER_AVVAKTANDE=3;
 	 public final static int FILTER_FORSKOTT=4;


	public ServiceImpl(SxServerMainLocal sxServerMainBean, DataSource bvDataSource, DataSource sxDataSource) {
		this.sxServerMainBean = sxServerMainBean;
		this.sxDataSource=sxDataSource;
		this.bvDataSource=bvDataSource;
	}


	 private PreparedStatement getOrder1PreparedStatement(Connection con, String sqlWhere) throws SQLException{
		if (sqlWhere==null) sqlWhere="1=1";
		return con.prepareStatement(
"select o1.lagernr, o1.ordernr, o1.status, o1.kundnr, o1.namn, o1.datum, o3.summa, " +
" case when o1.moms=1 then (1+f.moms1/100)*o3.summa else case when o1.moms=2 then (1+f.moms2/100)*o3.summa else case when o1.moms=3 then (1+f.moms3/100)*o3.summa else o3.summa end end end "+
" from order1 o1 left outer join " +
" (select ordernr as ordernr, sum(o2.summa) as summa from order2 o2 group by ordernr) o3 on o3.ordernr=o1.ordernr "+
" left outer join fuppg f on 1=1 "+
" where " + sqlWhere +
" order by o1.ordernr"
		  );

	 }
	 private Order1 getOrder1FromResultset(ResultSet rs) throws SQLException{
		Order1 order1 = new Order1();
		order1.lagernr = rs.getShort(1);
		order1.ordernr = rs.getInt(2);
		order1.status = rs.getString(3);
		order1.kundnr = rs.getString(4);
		order1.namn = rs.getString(5);
		order1.datum = new java.util.Date(rs.getDate(6).getTime());
		order1.summaInkMoms = rs.getDouble(7);
		if (SXConstant.ORDER_STATUS_SPARAD.equals(order1.status)) order1.isOverforbar=true; else order1.isOverforbar=false;
		return order1;
	 }

	 public Order1List getOrder1List(int filter) throws ServerErrorException {
		 Order1List order1List = new Order1List();
		 Connection con=null;
		 String sqlWhere=null;
		 switch (filter) {
			case FILTER_FOROVERFORING:
				sqlWhere="status='" + SXConstant.ORDER_STATUS_SPARAD + "'";
				break;
			case FILTER_AVVAKTANDE:
				sqlWhere="status='" + SXConstant.ORDER_STATUS_AVVAKT + "'";
				break;
			case FILTER_FORSKOTT:
				sqlWhere="forskatt=1" ;
				break;
			case FILTER_OVERFORDA:
				sqlWhere="status='" + SXConstant.ORDER_STATUS_OVERFORD + "'";
				break;
			default:
				sqlWhere="1=1";
				break;
		 }



		try {
			con = bvDataSource.getConnection();
			PreparedStatement stm = getOrder1PreparedStatement(con, sqlWhere);


			//stm.setString(1, sxSession.getKundnr());
			ResultSet rs = stm.executeQuery();
			Order1 order1;

			while (rs.next()) {
				order1List.orderLista.add(getOrder1FromResultset(rs));
			}

		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));}
		catch (Exception ee) {ee.printStackTrace();throw(new ServerErrorException("Okänt fel"));}
		finally {
			try { con.close(); } catch (Exception e) {}
		}
		 return order1List;
	 }

}
