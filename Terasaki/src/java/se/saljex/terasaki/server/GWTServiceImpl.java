/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.terasaki.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import se.saljex.terasaki.client.Artikel;
import se.saljex.terasaki.client.ArtikelBest;
import se.saljex.terasaki.client.ArtikelInfo;
import se.saljex.terasaki.client.ArtikelList;
import se.saljex.terasaki.client.ArtikelOrder;
import se.saljex.terasaki.client.ArtikelStatistik;
import se.saljex.terasaki.client.ArtikelStatistikDetalj;
import se.saljex.terasaki.client.Faktura2;
import se.saljex.terasaki.client.Faktura2List;

import se.saljex.terasaki.client.GWTService;
import se.saljex.terasaki.client.Kund;
import se.saljex.terasaki.client.KundFaktura1;
import se.saljex.terasaki.client.KundInfo;
import se.saljex.terasaki.client.KundList;
import se.saljex.terasaki.client.KundOrder;
import se.saljex.terasaki.client.NotLoggedInException;
import se.saljex.terasaki.client.ServerErrorException;

/**
 *
 * @author Ulf
 */
public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {
	@javax.annotation.Resource(name = "sxadm")
	private DataSource sxadm;

	public static final String TERASAKISESSION = "terasakisession";

    public String myMethod(String s) {
        // Do something interesting with 's' here on the server.
        return "Server says: " + s;
    }
	
	public ArtikelList getArtikelList() throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		Connection con=null;
		ArtikelList artikelList = new ArtikelList();
		Artikel artikel;
		try {
			con = sxadm.getConnection();
			
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select a.nummer, a.namn, a.bestnr, a.enhet, a.utpris, a.inpris, a.rab, l.ilager, l.bestpunkt, l.maxlager, l.iorder, l.best"
					+ " from artikel a left outer join lager l on l.artnr=a.nummer and l.lagernr=5 where a.nummer like '++%' order by a.nummer");
			while (rs.next()) {
				artikel = new Artikel();
				artikel.artnr = rs.getString(1);
				artikel.namn = rs.getString(2);
				artikel.bestnr = rs.getString(3);
				artikel.enhet = rs.getString(4);
				artikel.utpris = rs.getDouble(5);
				artikel.inpris = rs.getDouble(6);
				artikel.rab = rs.getDouble(7);
				artikel.ilager = rs.getDouble(8);
				artikel.bestpunkt = rs.getDouble(9);
				artikel.maxlager = rs.getDouble(10);
				artikel.iorder = rs.getDouble(11);
				artikel.best = rs.getDouble(12);
				artikelList.artiklar.add(artikel);
			}
			return artikelList;
		} catch (SQLException e) { 
			throw new ServerErrorException("SQL-Fel: "  + e.getMessage() + " " + e.toString());
		} finally {
			try { con.close(); } catch (Exception e) {}
					
		}
		
	}



	public ArtikelInfo getArtikelInfo(String artnr) throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		Connection con=null;
		ArtikelInfo a = new ArtikelInfo();
		try {
			con = sxadm.getConnection();
			PreparedStatement stm;
			ResultSet rs;

			stm = con.prepareStatement("select o1.ordernr, o1.datum, o1.kundnr, o1.namn, o2.best from order1 o1, order2 o2 where o1.ordernr=o2.ordernr and o2.artnr=? order by o1.ordernr, o2.pos");
			stm.setString(1, artnr);
			rs = stm.executeQuery();
			ArtikelOrder ao;
			while (rs.next()) {
				ao = new ArtikelOrder();
				ao.ordernr = rs.getInt(1);
				ao.datum = rs.getDate(2);
				ao.kundnr = rs.getString(3);
				ao.kundnamn = rs.getString(4);
				ao.antal = rs.getDouble(5);
				a.artikelOrderList.add(ao);
			}

			stm = con.prepareStatement("select b1.bestnr, b1.datum, b2.best from best1 b1, best2 b2 where b1.bestnr=b2.bestnr and b2.artnr=? order by b1.bestnr, b2.rad");
			stm.setString(1, artnr);
			rs = stm.executeQuery();
			ArtikelBest ab;
			while (rs.next()) {
				ab = new ArtikelBest();
				ab.bestnr = rs.getInt(1);
				ab.datum = rs.getDate(2);
				ab.antal = rs.getDouble(3);
				a.artikelBestList.add(ab);
			}
			
			stm = con.prepareStatement("select year(f1.datum), month(f1.datum), sum(f2.lev), sum(f2.summa), sum(f2.summa-f2.lev*f2.netto) from faktura1 f1, faktura2 f2 where f1.faktnr = f2.faktnr and f2.artnr=? and year(f1.datum) > year(current_date)-5 group by year(f1.datum), month(f1.datum) order by year(f1.datum), month(f1.datum)");
			stm.setString(1, artnr);
			rs = stm.executeQuery();
			ArtikelStatistik as=new ArtikelStatistik();
			ArtikelStatistikDetalj asd;
			int currAr=0;
			while (rs.next()) {
				if (rs.getInt(1) != currAr) {
					currAr = rs.getInt(1);
					as = new ArtikelStatistik();
					as.ar = rs.getInt(1);
					a.artikelStatistikList.add(as);
				}
				asd = new ArtikelStatistikDetalj();
				asd.man = rs.getInt(2);
				asd.antal = rs.getDouble(3);
				asd.summa = rs.getDouble(4);
				asd.tb = rs.getDouble(5);
				as.artikelStatistikDetalj.add(asd);
			}

			a.artnr = artnr;
			return a;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServerErrorException("SQL-Fel");
		} finally {
			try { con.close(); } catch (Exception e) {}

		}

	}



	public KundList getKundList() throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		Connection con=null;
		KundList kundList = new KundList();
		Kund kund;
		try {
			con = sxadm.getConnection();

			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select k.nummer, k.namn, k.ref, k.adr1, k.adr2, k.adr3, k.tel, k.biltel, sum(f2.summa) "
					+ " from kund k, faktura1 f1, faktura2 f2 "
					+ " where f1.kundnr=k.nummer and f1.faktnr=f2.faktnr and f2.artnr like '++%' "
					+ " group by k.nummer, k.namn, k.ref, k.adr1, k.adr2, k.adr3, k.tel, k.biltel "
					+ " order by k.nummer ");
			while (rs.next()) {
				kund = new Kund();
				kund.kundnr = rs.getString(1);
				kund.namn = rs.getString(2);
				kund.ref = rs.getString(3);
				kund.adr1 = rs.getString(4);
				kund.adr2 = rs.getString(5);
				kund.adr3 = rs.getString(6);
				kund.tel = rs.getString(7);
				kund.biltel = rs.getString(8);
				kund.omsattning = rs.getDouble(9);
				kundList.kunder.add(kund);
			}
			return kundList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServerErrorException("SQL-Fel: "  + e.getMessage() + " " + e.toString());
		} finally {
			try { con.close(); } catch (Exception e) {}

		}

	}

	public KundInfo getKundInfo(String kundnr) throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		Connection con=null;
		KundInfo k = new KundInfo();
		try {
			con = sxadm.getConnection();
			PreparedStatement stm;
			ResultSet rs;

			stm = con.prepareStatement("select o1.ordernr, o1.datum, o2.artnr, o2.namn, o2.best, o2.pris, o2.rab "
					+ " from order1 o1, order2 o2 "
					+ " where o1.ordernr=o2.ordernr and o1.kundnr=? "
					+ " order by o1.ordernr, o2.pos ");
			stm.setString(1, kundnr);
			rs = stm.executeQuery();
			KundOrder ko;
			while (rs.next()) {
				ko = new KundOrder();
				ko.ordernr = rs.getInt(1);
				ko.datum = rs.getDate(2);
				ko.artnr = rs.getString(3);
				ko.artnamn = rs.getString(4);
				ko.antal = rs.getDouble(5);
				ko.pris = rs.getDouble(6);
				ko.rab = rs.getDouble(7);

				k.orderRader.order.add(ko);
			}


			stm = con.prepareStatement("select f1.faktnr, f1.datum, f1.t_attbetala "
					+ " from faktura1 f1 "
					+ " where f1.kundnr=? "
					+ " order by f1.faktnr desc ");
			stm.setString(1, kundnr);
			rs = stm.executeQuery();
			KundFaktura1 kf;
			int cn=0;
			final int maxFakturor=200;
			k.fakturor.maxAntalFakturor = maxFakturor;
			while (rs.next()) {
				cn++;
				if (cn > maxFakturor) {
					k.fakturor.flerFakturorFinns=true;
					break;
				}
				kf = new KundFaktura1();
				kf.faktnr = rs.getInt(1);
				kf.datum = rs.getDate(2);
				kf.t_attebtala = rs.getDouble(3);
				k.fakturor.fakturor.add(kf);
			}

			k.kundnr = kundnr;
			return k;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServerErrorException("SQL-Fel");
		} finally {
			try { con.close(); } catch (Exception e) {}

		}

	}




	public Faktura2List getFaktura2(int faktnr) throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		Connection con=null;
		Faktura2List f = new Faktura2List();
		try {
			con = sxadm.getConnection();
			PreparedStatement stm;
			ResultSet rs;

			stm = con.prepareStatement("select f2.artnr, f2.namn, f2.lev, f2.enh, f2.pris, f2.rab, f2.summa "
					+ " from faktura2 f2 "
					+ " where f2.faktnr=? "
					+ " order by f2.pos ");
			stm.setInt(1, faktnr);
			rs = stm.executeQuery();
			Faktura2 f2;
			while (rs.next()) {
				f2 = new Faktura2();
				f2.artnr = rs.getString(1);
				f2.namn = rs.getString(2);
				f2.antal = rs.getDouble(3);
				f2.enh = rs.getString(4);
				f2.pris = rs.getDouble(5);
				f2.rab = rs.getDouble(6);
				f2.summa = rs.getDouble(7);

				f.rader.add(f2);
			}


			return f;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServerErrorException("SQL-Fel");
		} finally {
			try { con.close(); } catch (Exception e) {}

		}

	}




	private void ensureLoggedIn() throws NotLoggedInException {
		if (!getTerasakiSession().isLoggedIn()) throw new NotLoggedInException();
	}

	public Boolean isLoggedIn() {
		return getTerasakiSession().isLoggedIn();
	}

	public void logOut() {
		clearTerasakiSession();
	}

	public Boolean logIn(String anvandareKort, String losen) throws ServerErrorException {
		Connection con=null;
		try {
			con = sxadm.getConnection();
			clearTerasakiSession();
			PreparedStatement st = con.prepareStatement("select forkortning, namn, a.behorighet, lagernr from saljare s, anvbehorighet a where a.anvandare = s.namn " +
							" and s.forkortning=? and s.losen=?");
			st.setString(1, anvandareKort);
			st.setString(2, losen);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				if ("TerasakiLogin".equals(rs.getString(3)) || "IntraLogin".equals(rs.getString(3)) || "IntraAdmin".equals(rs.getString(3)) || "IntraSuperuser".equals(rs.getString(3))) {
					getTerasakiSession().setAnvandare(rs.getString(2), rs.getString(1));
				}
			}
			return getTerasakiSession().isLoggedIn();
		} catch (SQLException e) {
			throw new ServerErrorException("SQL-Fel: " + e.getMessage() + " " + e.toString());
		} finally {
			try { con.close(); } catch (Exception e) {}

		}

	}

	private void clearTerasakiSession() {
		getThreadLocalRequest().getSession().setAttribute(TERASAKISESSION, new TerasakiSession());
	}

	private TerasakiSession getTerasakiSession() {
		TerasakiSession ts = (TerasakiSession)getThreadLocalRequest().getSession().getAttribute(TERASAKISESSION);
		if (ts==null) clearTerasakiSession();
		return (TerasakiSession)getThreadLocalRequest().getSession().getAttribute(TERASAKISESSION);
	}

}



/*
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
*/
