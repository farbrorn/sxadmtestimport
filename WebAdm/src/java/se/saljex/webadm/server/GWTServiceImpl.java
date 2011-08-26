/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.webadm.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.sql.DataSource;
import se.saljex.sxlibrary.SXUtil;

import se.saljex.sxserver.LocalWebSupportLocal;
import se.saljex.sxserver.SxServerMainLocal;
import se.saljex.sxserver.tables.TableArtikel;
import se.saljex.sxserver.tables.TableKund;

import se.saljex.webadm.client.GWTService;
import se.saljex.webadm.client.rpcobject.Epost;
import se.saljex.webadm.client.rpcobject.ErrorConvertingFromResultsetException;
import se.saljex.webadm.client.rpcobject.IsSQLTable;
import se.saljex.webadm.client.rpcobject.ServerErrorException;
import se.saljex.webadm.client.rpcobject.Kund;
import se.saljex.webadm.client.rpcobject.NotLoggedInException;
import se.saljex.webadm.client.rpcobject.Order1;
import se.saljex.webadm.client.rpcobject.SQLTableList;

/**
 *
 * @author Ulf
 */
public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {
	@EJB
	private SxServerMainLocal sxServerMainBean;
	@EJB
	private LocalWebSupportLocal webBean;

	@javax.annotation.Resource(name = "sxadm")
	private DataSource sxadm;

    public String myMethod(String s) {
        // Do something interesting with 's' here on the server.
		String t = (String)getThreadLocalRequest().getSession().getAttribute("test");
		if (t==null) t = "Null";
        return "Server says: " + s + " test: " + t;
    }

	public ArrayList<Epost> getKundEpostLista(String kundnr) throws ServerErrorException {
		ensureLoggedIn();
		ArrayList<Epost> ret = new ArrayList<Epost>();
		Epost epost;
		Connection con=null;

		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement("select ref, email from kund where nummer=?");
			stm.setString(1, kundnr);
			ResultSet rs = stm.executeQuery();

			if (rs.next()) {
				if (!SXUtil.isEmpty(rs.getString(2))) {
					epost = new Epost();
					epost.namn = rs.getString(1);
					epost.epost = rs.getString(2);
					epost.typ="Allmän";
					ret.add(epost);
				}
			}
			rs.close();
			stm.close();
			stm = con.prepareStatement("select namn, epost, ekonomi, info from kundkontakt where kundnr = ? order by namn");
			stm.setString(1, kundnr);
			rs = stm.executeQuery();

			if (rs.next()) {
				if (!SXUtil.isEmpty(rs.getString(2))) {
					epost = new Epost();
					epost.namn = rs.getString(1);
					epost.epost = rs.getString(2);
					if (rs.getInt(3)!=0) epost.typ="Ekonomi";
					if (rs.getInt(4)!=0) {
						if (epost.typ!=null) epost.typ = epost.typ + ", " + "Info"; else epost.typ="Info";
					}
					ret.add(epost);
				}
			}


		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServerErrorException("SQL-Fel");
		} finally {
			try { con.close(); } catch (Exception e) {}

		}


		return ret;
	}

	public String getArtikel(String artnr) {
		TableArtikel art = new TableArtikel("123");
		return "123";
	}


	public Kund getKund(String kundnr) throws ServerErrorException {
		ensureLoggedIn();
		String sqlField="";
		Connection con=null;

		Kund kund = new Kund();

		sqlField = SQLTableHandler.getSelectColumns(Kund.class);

		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement("select " + sqlField + " from kund where nummer='0555'");
			Object[] parameters = {kundnr};

			ResultSet rs = SQLTableHandler.getResultSet(con, kund, "nummer=?", null, null, parameters, true, false, 0, 0);
			if (rs.next()) {
				SQLTableHandler.getValues(kund, rs);
			} else {
				kund=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServerErrorException("SQL-Fel");
		} catch (ErrorConvertingFromResultsetException ec) {
			ec.printStackTrace();
			throw new ServerErrorException("Fel vid konvertering av SQL ResultSet");
		} finally {
			try { con.close(); } catch (Exception e) {}

		}

		return kund;
	}





/*
	public SQLTableList<Kund> getKundList(String sokString, int sokField, int sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException{
		ensureLoggedIn();
		SQLTableKundGetList g = new SQLTableKundGetList(sxadm);
		SQLTableList<Kund> tableList = new SQLTableList<Kund>();
		g.fillList(tableList, new Kund(), sokString, "nummer", "nummer", compareType, sortOrder, offset, limit);
		return tableList;
	}
*/
	private SQLTableList<IsSQLTable> doGetTableList(IsSQLTable table, Object sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException{
		ensureLoggedIn();
		SQLTableList<IsSQLTable> tableList = new SQLTableList<IsSQLTable>();
		SQLTableGetList g = new SQLTableGetList(sxadm);
		g.fillList(tableList, table, sokString.toString(), sokField, sortField, compareType, sortOrder, offset, limit);
		return tableList;
	}

	public SQLTableList<IsSQLTable> getTableList(IsSQLTable table, String sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException{
		return doGetTableList(table, sokString, sokField, sortField, compareType, sortOrder, offset, limit);
	}

	public SQLTableList<IsSQLTable> getTableList(IsSQLTable table, Integer sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException{
		return doGetTableList(table, sokString, sokField, sortField, compareType, sortOrder, offset, limit);
	}
	public SQLTableList<IsSQLTable> getTableList(IsSQLTable table, java.sql.Date sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException{
		return doGetTableList(table, sokString, sokField, sortField, compareType, sortOrder, offset, limit);
	}
	public SQLTableList<IsSQLTable> getTableList(IsSQLTable table, Double sokString, String sokField, String sortField, int compareType, int sortOrder, int offset, int limit) throws ServerErrorException{
		return doGetTableList(table, sokString, sokField, sortField, compareType, sortOrder, offset, limit);
	}

/*	public SQLTableList getOrder1List(IsSQLTable table, String kundnr,  int offset, int limit) throws ServerErrorException{
		ensureLoggedIn();
		Class c = table.getClass();
		SQLTableList nn = new SQLTableList();
		ArrayList  ar; ar.
		SQLTableKundGetList g = new SQLTableKundGetList(sxadm);
		return g.getList(sokString, sokField, sortField, compareType, sortOrder, offset, limit);
	}*/


	public void deleteKund(String kundnr) throws ServerErrorException {
		ensureLoggedIn();
		throw new ServerErrorException("Inte implementerat");
	}



	public void putKund(Kund newValues, Kund oldValues) throws ServerErrorException {
		ensureLoggedIn();
		Object newObject;
		Object oldObject;
		StringBuilder updateSB = new StringBuilder();
		StringBuilder whereSB = new StringBuilder();
		ArrayList<Object> updateParams = new ArrayList();
		ArrayList<Object> whereParams = new ArrayList();
		Connection con=null;

		//Tillåt inte ändring av kundnummer
		if (newValues.nummer==null || !newValues.nummer.equals(oldValues.nummer)) throw new ServerErrorException("Kan inte ändra kundnummer. Inget sparat");

		//Tillåt inte tomt kundnr
		if (newValues.nummer==null || newValues.nummer.isEmpty()) throw new ServerErrorException("Kundnumret får inte vara tomt. Inget sparat.");

		try {
			con = sxadm.getConnection();
			if (oldValues==null) {			//Vi har en insert av ny kund
				throw new ServerErrorException("Ny kund r inte implementerat...");
			} else { //Vi har en update
				boolean doUpdate;
				Field[] fields = Kund.class.getFields();

				//Sätt upp primary key
				whereSB.append("nummer=?");
				whereParams.add(oldValues.nummer);

				for (Field field : fields) {
					doUpdate = false;
					newObject = field.get(newValues);
					oldObject = field.get(oldValues);
//System.out.print(field.getName());
					if (newObject==null) {
						if (oldObject!=null) { doUpdate = true; }
					} else {
						if (!newObject.equals(oldObject)) {
							doUpdate = true;
						//System.out.print("--" + field.getName() + " old " +
						//		(oldObject==null ? "null" : oldObject.toString()) + " new " +
						//		(newObject==null ? "null" : newObject.toString()));
						}

					}


					if (doUpdate) {
						if (updateSB.length()>0) updateSB.append(", ");
						updateSB.append(field.getName());
						if (newObject==null) {
							updateSB.append("=null");
						} else {
							updateSB.append("=?");
							if (newObject instanceof String) newObject=SXUtil.rightTrim((String)newObject);	//Ta bort avslutande blanksteg
							updateParams.add(newObject);
						}

						if (whereSB.length()>0) whereSB.append(" and ");

						whereSB.append(field.getName());
						if (oldObject==null) {
							whereSB.append(" is null");
						} else {
							whereSB.append("=?");
							whereParams.add(oldObject);
						}
					}
				}

				if (updateSB.length()>0) {
					String sql = " update " + newValues.getSQLTableName() + " set " + updateSB.toString() + " where " + whereSB.toString();
	//System.out.print(sql);
					updateParams.addAll(whereParams);
					PreparedStatement stm = con.prepareStatement(sql);

					int cn=0;
					for (Object p : updateParams) {
						cn++;
						stm.setObject(cn, p);
					}
					int result = stm.executeUpdate();
					if (result > 1) throw new ServerErrorException("Internt serverfel: Servern försöker uppdatera fler kunder.");
					if (result < 1) throw new ServerErrorException("Kunde inte spara. Kunden kan vara ändrad av annan användare. Prova att söka kunden på nytt, gör ändringarna och spara igen");
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServerErrorException("SQL-Fel");
		} finally {
			try { con.close(); } catch (Exception e) {}

		}

	}


	private Kund tableKund2Kund(TableKund tk) {
		if (tk==null) return null;
		Kund k = new Kund();

		k.nummer = tk.getNummer();
		k.namn = tk.getNamn();
		k.adr1 = tk.getAdr1();
		k.adr2 = tk.getAdr2();
		k.adr3 = tk.getAdr3();
		k.lnamn = tk.getLnamn();
		k.ladr2 = tk.getLadr2();
		k.ladr3 = tk.getLadr3();
		k.ref = tk.getRef();
		k.saljare = tk.getSaljare();
		k.tel = tk.getTel();
		k.biltel = tk.getBiltel();
		k.fax = tk.getFax();
		k.sokare = tk.getSokare();
		k.email = tk.getEmail();
		k.hemsida = tk.getHemsida();
		k.k_Dag = tk.getKDag();
		k.k_Tid = tk.getKTid();
		k.k_Datum = tk.getKDatum();
		k.regnr = tk.getRegnr();
		k.rantfakt = tk.getRantfakt();
		k.faktor = tk.getFaktor();
		k.kgrans = tk.getKgrans();
		k.ktid = tk.getKtid();
		k.nettolst = tk.getNettolst();
		k.bonus = tk.getBonus();
		k.elkund = tk.getElkund();
		k.vvskund = tk.getVvskund();
		k.ovrigkund = tk.getOvrigkund();
		k.installator = tk.getInstallator();
		k.butik = tk.getButik();
		k.industri = tk.getIndustri();
		k.oem = tk.getOem();
		k.grossist = tk.getGrossist();
		k.levvillkor = tk.getLevvillkor();
		k.mottagarfrakt = tk.getMottagarfrakt();
		k.fraktbolag = tk.getFraktbolag();
		k.fraktkundnr = tk.getFraktkundnr();
		k.fraktfrigrans = tk.getFraktfrigrans();
		k.ant1 = tk.getAnt1();
		k.ant2 = tk.getAnt2();
		k.ant3 = tk.getAnt3();
		k.distrikt = tk.getDistrikt();
		k.vakund = tk.getVakund();
		k.fastighetskund = tk.getFastighetskund();
		k.basrab = tk.getBasrab();
		k.golvkund = tk.getGolvkund();
		k.ejfakturerbar = tk.getEjfakturerbar();
		k.skrivfakturarskenr = tk.getSkrivfakturarskenr();
		k.sarfaktura = tk.getSarfaktura();
		k.momsfri = tk.getMomsfri();
		k.kgransforfall30 = tk.getKgransforfall30();
		k.kravordermarke = tk.getKravordermarke();
		k.linjenr1 = tk.getLinjenr1();
		k.linjenr2 = tk.getLinjenr2();
		k.linjenr3 = tk.getLinjenr3();
		k.skickafakturaepost = tk.getSkickafakturaepost();
		k.samfakgrans = tk.getSamfakgrans();
		String sqlSelect=" SQL:";
		Field[] fl = TableKund.class.getDeclaredFields();
		for (Field f : fl ) {

			javax.persistence.Column a = f.getAnnotation(javax.persistence.Column.class);
			if (a!=null) sqlSelect = sqlSelect + a.name() +"(" + f.getName() +")" + ", ";
		}
		k.namn=sqlSelect;

		return k;

	}



	public void s() throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		Connection con=null;
		try {
			con = sxadm.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("SQL-Fel");
		} finally {
			try { con.close(); } catch (Exception e) {}

		}

	}

	public Integer sendOffertEpost(String anvandare, String epost, int id) throws ServerErrorException {
		ensureLoggedIn();
		try {
			sxServerMainBean.sendOffertEpost(anvandare, epost, id);
		} catch (Exception e) {throw new ServerErrorException(e.getMessage()); }
		return id;
	}

	public Integer sendFakturaEpost(String anvandare, String epost, int id) throws ServerErrorException {
		ensureLoggedIn();
		try {
			sxServerMainBean.sendFakturaEpost(anvandare, epost, id);
		} catch (Exception e) {throw new ServerErrorException(e.getMessage()); }
		return id;
	}

	private void ensureLoggedIn()  {}

}



/*
		nummer
		namn
		adr1
		adr2
		adr3
		lnamn
		ladr2
		ladr3
		ref
		saljare
		tel
		biltel
		fax
		sokare
		email
		hemsida
		kDag
		kTid
		kDatum
		regnr
		rantfakt
		faktor
		kgrans
		ktid
		nettolst
		bonus
		elkund
		vvskund
		ovrigkund
		installator
		butik
		industri
		oem
		grossist
		levvillkor
		mottagarfrakt
		fraktbolag
		fraktkundnr
		fraktfrigrans
		ant1
		ant2
		ant3
		distrikt
		vakund
		fastighetskund
		basrab
		golvkund
		ejfakturerbar
		skrivfakturarskenr
		sarfaktura
		momsfri
		kgransforfall30
		kravordermarke
		linjenr1
		linjenr2
		linjenr3
		ickafakturaepost
 *
 *
 *
 *
 *
				kund.nummer=rs.getString(1);

 *
 *

*/