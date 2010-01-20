/*
 * SxShopRPCImpl.java
 *
 * Created on den 3 december 2009, 10:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package se.saljex.SxShop.server;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.Date;
import java.util.Random;
import javax.ejb.EJB;
import javax.persistence.EntityNotFoundException;
import javax.sql.DataSource;
import se.saljex.SxShop.client.rpcobject.ArtGrupp;
import se.saljex.SxShop.client.rpcobject.ArtSida;
import se.saljex.SxShop.client.rpcobject.ArtSidaKlase;
import se.saljex.SxShop.client.rpcobject.ArtSidaKlaseArtikel;
import se.saljex.SxShop.client.rpcobject.SokResult;
import se.saljex.SxShop.client.rpcobject.SokResultKlase;
import se.saljex.SxShop.client.SxShopRPC;
import se.saljex.SxShop.client.rpcobject.Anvandare;
import se.saljex.SxShop.client.rpcobject.VaruKorgRad;
import se.saljex.SxShop.client.rpcobject.NotLoggedInException;
import se.saljex.SxShop.client.rpcobject.SxShopKreditSparrException;
import se.saljex.SxShop.client.rpcobject.FelaktigtAntalException;


import se.saljex.SxShop.client.rpcobject.IncorrectLogInException;
import se.saljex.SxShop.client.rpcobject.ServerErrorException;
import se.saljex.sxserver.KreditSparrException;
import se.saljex.sxserver.SXUtil;
import se.saljex.sxserver.SxServerMainLocal;
import se.saljex.sxserver.websupport.SXSession;
import se.saljex.sxserver.websupport.WebUtil;

/**
 *
 * @author ulf
 */
public class SxShopRPCImpl extends RemoteServiceServlet implements
		  SxShopRPC {
	@EJB
	private SxServerMainLocal sxServerMainBean;
	@javax.annotation.Resource(name = "sxadm")
	private DataSource sxadm;


	public String myMethod(String s) {
		// Do something interesting with 's' here on the server.
		return "Server says: " + s;
	}
	public ArrayList getArtikelTrad() {
		ArrayList<ArtGrupp> ar = new ArrayList();


		Connection con=null;
		try {
			con = sxadm.getConnection();
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select grpid, prevgrpid, rubrik from artgrp order by prevgrpid, sortorder, grpid");
			while (rs.next()) {
				ar.add(new ArtGrupp(rs.getInt(1), rs.getInt(2), rs.getString(3)));
			}
		} catch (SQLException e) { System.out.print("Exception " + e.toString()); e.printStackTrace();
		} finally {
			try { con.close(); } catch (Exception e) {}
		}

		return ar;
	}

	public ArtSida getArtSida(int grpid) {
		ArtSida artSida = null;
		Connection con=null;
		try {
			con = sxadm.getConnection();
			Statement stm = con.createStatement();
			Statement stm2 = con.createStatement();
			ResultSet rs = stm.executeQuery("select grpid, rubrik, text, infourl from artgrp where grpid=" + grpid);
			if (rs.next()) {
				artSida = new ArtSida(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4));
			}
			ResultSet klase = stm.executeQuery("select k.klasid, k.rubrik, k.text, k.infourl from artgrplank g, artklase k where g.klasid=k.klasid and g.grpid=" + grpid + " order by g.sortorder, g.klasid");
			while (klase.next()){
				ArtSidaKlase artSidaKlase = new ArtSidaKlase(klase.getInt(1),klase.getString(2),klase.getString(3), klase.getString(4));
				ResultSet art = stm2.executeQuery("select a.nummer, a.namn, a.katnamn, a.enhet, a.utpris, a.staf_pris1, a.staf_pris2, a.staf_antal1, a.staf_antal2, a.rabkod, a.kod1, a.prisdatum, a.forpack, a.minsaljpack, a.prisgiltighetstid, a.bildartnr from artikel a, artklaselank l where l.artnr=a.nummer and l.klasid="+ klase.getInt(1) +" order by l.sortorder, a.nummer");
				while (art.next()) {
					ArtSidaKlaseArtikel artSidaKlaseArtikel = new ArtSidaKlaseArtikel();
					artSidaKlaseArtikel.nummer=art.getString(1);
					artSidaKlaseArtikel.namn=art.getString(2);
					artSidaKlaseArtikel.katnamn=art.getString(3);
					artSidaKlaseArtikel.enhet=anpassaEnhet(art.getString(4));
					artSidaKlaseArtikel.utpris=SXUtil.getRoundedDecimal(art.getDouble(5));
					artSidaKlaseArtikel.staf_pris1=SXUtil.getRoundedDecimal(art.getDouble(6));
					artSidaKlaseArtikel.staf_pris2=SXUtil.getRoundedDecimal(art.getDouble(7));
					artSidaKlaseArtikel.staf_antal1=art.getDouble(8);
					artSidaKlaseArtikel.staf_antal2=art.getDouble(9);
					artSidaKlaseArtikel.rabkod=art.getString(10);
					artSidaKlaseArtikel.kod1=art.getString(11);
					artSidaKlaseArtikel.prisdatum=art.getDate(12);
					artSidaKlaseArtikel.forpack=art.getDouble(13);
					artSidaKlaseArtikel.minsaljpack=art.getDouble(14);
					artSidaKlaseArtikel.prisgiltighetstid=art.getInt(15);
					String bild = art.getString(16);
					if (bild==null || bild.isEmpty()) bild=artSidaKlaseArtikel.nummer;
					artSidaKlaseArtikel.bildurl=bild;
					artSidaKlase.artiklar.add(artSidaKlaseArtikel);
				}
				artSida.klasar.add(artSidaKlase);
			}
		} catch (SQLException e) { System.out.print("Exception " + e.toString()); e.printStackTrace();
		} finally {
			try { con.close(); } catch (Exception e) {}
		}

		return artSida;
	}


	private void doUpdateVarukorg(Connection con, SXSession sxSession, String artnr, double antal) throws SQLException {
		PreparedStatement stm=con.prepareStatement("update varukorg set antal=? where kontaktid=? and typ='VK' and artnr=?");
		stm.setDouble(1,antal);
		stm.setInt(2, sxSession.getKundKontaktId());
		stm.setString(3, artnr);
		stm.executeUpdate();
	}

	private void doDeleteVarukorg(Connection con, SXSession sxSession, String artnr) throws SQLException {
		PreparedStatement stm=con.prepareStatement("delete from varukorg where kontaktid=? and typ='VK' and artnr=?");
		stm.setInt(1, sxSession.getKundKontaktId());
		stm.setString(2, artnr);
		stm.executeUpdate();
	}


	public Anvandare getInloggadAnvandare() {

		// *********** Inloggning för teständamål
//		try {
//		ensureLoggedIn();
//		} catch (Exception e) {}

		SXSession sxSession = WebUtil.getSXSession(getThreadLocalRequest().getSession());
		Anvandare anvandare=new Anvandare();
		if (!sxSession.checkBehorighetKund()) {
			sxSession.setGastLogin(true);
			sxSession.setKundKontaktId(null);
			sxSession.setKundnamn(null);
			sxSession.setKundKontaktNamn(null);
			sxSession.setKundLoginNamn(null);
		}
		anvandare.gastlogin=sxSession.isGastLogin();
		anvandare.kontaktnamn=sxSession.getKundKontaktNamn();
		anvandare.kundnamn=sxSession.getKundnamn();
		anvandare.gastlogin=sxSession.isGastLogin();
		anvandare.loginnamn = sxSession.getKundLoginNamn();
		anvandare.autoLoginId = sxSession.getKundAutoLogInId();
		return anvandare;
	}

	public Anvandare autoLogin(String anvandare, String autoLogInId) throws IncorrectLogInException, ServerErrorException {
		SXSession sxSession = WebUtil.getSXSession(getThreadLocalRequest().getSession());
		
		if (!sxSession.getInloggad()) {	// Om vi redan är inloggad så ska vi inte försöka igen
			if (anvandare!=null && autoLogInId!=null) {
				if (!anvandare.isEmpty() && !autoLogInId.isEmpty()) {
					Connection con=null;
					try {
						con = sxadm.getConnection();
						//Autoinlogga om möjligt. Skulle det inte gå faller vi igenom och functionen returnerar gästlogin
						WebUtil.autoLogInKund(getThreadLocalRequest(), con, anvandare, autoLogInId);

					} catch (SQLException e) {
						e.printStackTrace();
						throw (new ServerErrorException());
					} finally {
						try { con.close(); } catch(Exception e) {}
					}
				}
			}
		}
		return getInloggadAnvandare();
	}

	public Anvandare logIn(String anvandare, String losen, boolean stayLoggedIn) throws IncorrectLogInException, ServerErrorException {
		Connection con=null;
		try {
			con = sxadm.getConnection();
			if (WebUtil.logInKund(getThreadLocalRequest(), con, anvandare, losen, stayLoggedIn)) {
				return getInloggadAnvandare();
			} else {
				throw new IncorrectLogInException("Felaktig användare/lösen");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw (new ServerErrorException());
		} finally {
			try { con.close(); } catch(Exception e) {}
		}

	}

	public Anvandare logOut() {
		Connection con=null;
		try {
			con = sxadm.getConnection();
			WebUtil.logOutKund(getThreadLocalRequest(),con);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { con.close(); } catch(Exception e) {}
		}
//		getThreadLocalRequest().getSession().invalidate();
		return getInloggadAnvandare();
	}

	private void ensureLoggedIn() throws NotLoggedInException {
		// Throw exception om inte inloggad, annars ingen åtgärd

/*		//För testkörning - slipper login
		SXSession sxSession = WebUtil.getSXSession(getThreadLocalRequest().getSession());
		sxSession.setKundKontaktId(1);
		sxSession.setKundnr("055513915");
		sxSession.setKundKontaktNamn("Ulf");
		sxSession.setKundLoginNamn("ulf");
		sxSession.setKundnamn("Säljex A");
		sxSession.setInloggad(true);
*/
		if (!WebUtil.getSXSession(getThreadLocalRequest().getSession()).checkBehorighetKund()) throw new NotLoggedInException();
	}

	public ArrayList<VaruKorgRad> deleteVaruKorg(String artnr) throws NotLoggedInException {
		SXSession sxSession = WebUtil.getSXSession(getThreadLocalRequest().getSession());

		ensureLoggedIn();

		Connection con=null;
		try {
			con = sxadm.getConnection();
			doDeleteVarukorg(con, sxSession, artnr);
			return getVaruKorg(con, sxSession);
		} catch (SQLException e) { System.out.print("Exception " + e.toString()); e.printStackTrace();
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return null;
	}


	// Kollar om angiven artikel finns i artikelregistret och angivet antal stämmer mot minsäljpack
	// Throws FelaktigtAntalException om antalet är felaktigt
	// Throws EntityNotFoundException om artikeln inte finns
	private void checkFelaktigtAntalAndThrowException(Connection con, String artnr, double antal) throws FelaktigtAntalException, EntityNotFoundException, SQLException {
			PreparedStatement stm=con.prepareStatement("select namn, minsaljpack, enhet from artikel where nummer=?");
			stm.setString(1, artnr);
			ResultSet rs = stm.executeQuery();
			double minSaljpack = 0;
			if (rs.next()) {
				minSaljpack = rs.getDouble(2);
				if (minSaljpack!=0 && antal % minSaljpack > 0) throw new FelaktigtAntalException("Angivet antal för artikel " + artnr + " " + rs.getString(1) + " går inte jämt upp i minsta odelbara förpackning " + minSaljpack + " " + rs.getString(3) + ". Kontrollera antal och enhet.");
			} else throw new EntityNotFoundException();
	}

	public ArrayList<VaruKorgRad> updateVaruKorg(String artnr, double antal) throws NotLoggedInException, FelaktigtAntalException{
		ArrayList<VaruKorgRad> ar=null;
		SXSession sxSession = WebUtil.getSXSession(getThreadLocalRequest().getSession());

		ensureLoggedIn();

		Connection con=null;
		try {
			con = sxadm.getConnection();
			try {		checkFelaktigtAntalAndThrowException(con, artnr, antal); }	catch (EntityNotFoundException e) {}

			if (antal>0) {
				//Spara nytt värde i varukorgen
				doUpdateVarukorg(con, sxSession, artnr, antal);
			} else {
				// Om antalet är <= 0 så tar vi bort raden helt
				doDeleteVarukorg(con, sxSession, artnr);
			}
			ar= getVaruKorg(con,sxSession);

		} catch (SQLException e) { System.out.print("Exception " + e.toString()); e.printStackTrace();
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return ar;
	}


	public ArrayList<VaruKorgRad> addVaruKorg(String artnr, double antal) throws NotLoggedInException, FelaktigtAntalException {
		ArrayList<VaruKorgRad> ar=null;
		SXSession sxSession = WebUtil.getSXSession(getThreadLocalRequest().getSession());

		ensureLoggedIn();

		Connection con=null;
		try {
			con = sxadm.getConnection();

			//Kolla om artikeln finns i varukorgen
			PreparedStatement stm = con.prepareStatement("select sum(antal), count(*) from varukorg where kontaktid=? and typ='VK' and artnr=?");
			stm.setInt(1, sxSession.getKundKontaktId());
			stm.setString(2, artnr);
			ResultSet rs = stm.executeQuery();
			double gammaltAntal=0;
			boolean artikelFinnsIVarukorg=false;
			if (rs.next()) {
				gammaltAntal = rs.getDouble(1);
				if (rs.getInt(2)>0) artikelFinnsIVarukorg=true;
			}
			rs.close();
			stm.close();

			//Kolla om antalet är giltigt samt om artikeln finns i artikelregistret
			boolean artnrGiltigt=true;
			try {		checkFelaktigtAntalAndThrowException(con, artnr, antal); }	catch (EntityNotFoundException e) { artnrGiltigt=false; }

			//Spara nytt värde i varukorgen
			if (artnrGiltigt) {
				if (artikelFinnsIVarukorg) {	// Artikeln finns i varukorgen med detta antal
					doUpdateVarukorg(con, sxSession, artnr, gammaltAntal+antal);
				} else {
					stm=con.prepareStatement("insert into varukorg (kontaktid, typ, artnr, antal) values (?,?,?,?)");
					stm.setInt(1, sxSession.getKundKontaktId());
					stm.setString(2, "VK");
					stm.setString(3, artnr);
					stm.setDouble(4, antal);
					stm.executeUpdate();
				}
			}
			ar= getVaruKorg(con,sxSession);

		} catch (SQLException e) { System.out.print("Exception " + e.toString()); e.printStackTrace();
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return ar;
	}

	private ArrayList<VaruKorgRad> getVaruKorg(Connection con, SXSession sxSession) throws SQLException{
		ArrayList<VaruKorgRad> ar=new ArrayList();
		PreparedStatement stm = con.prepareStatement
				  ("select v.artnr,  a.artnamn, v.antal, a.enhet, a.utpris, a.staf_pris1, a.staf_pris2, a.staf_antal1, a.staf_antal2, a.basrab, a.gruppbasrab, a.undergrupprab, a.nettopris, a.rabkod " +
				  " from varukorg v, vartkundorder a where a.artnr=v.artnr and a.lagernr=0 and a.kundnr=? and v.kontaktid=? and v.typ='VK' order by v.artnr");
		stm.setString(1, sxSession.getKundnr());
		stm.setInt(2, sxSession.getKundKontaktId());
		ResultSet rs = stm.executeQuery();
		VaruKorgRad r;
		while (rs.next()) {
			double antal = rs.getDouble(3);
			r = new VaruKorgRad();
			r.artnr=rs.getString(1);
			r.namn=rs.getString(2);
			r.antal=antal;
			r.enhet=rs.getString(4);
			double bastaRab=0;
			// Kolla nu bästa priset för kunden. Tar tyvärr inte hänsyn till kampanjer fn.
			//Om vi har nettoprisgrupp så ska bara rabatten räknas om den ligger på undergruppnivå. Annars 0
			if (!"NTO".equals(rs.getString(14))) {
				if (rs.getDouble(10) > bastaRab) bastaRab=rs.getDouble(10);
				if (rs.getDouble(11) > bastaRab) bastaRab=rs.getDouble(11);
			}
			if (rs.getDouble(12) > bastaRab) bastaRab=rs.getDouble(12);

			// Ta fram lägsta bruttopris
			double lagstaBrutto=rs.getDouble(5);
			// Kolla staf_pris1
			if (rs.getDouble(8) > 0 && antal >= rs.getDouble(8) && rs.getDouble(6) > 0 && rs.getDouble(6) < lagstaBrutto) lagstaBrutto=rs.getDouble(6);
			// Kolla staf_pris2
			if (rs.getDouble(9) > 0 && antal >= rs.getDouble(9) && rs.getDouble(7) > 0 && rs.getDouble(7) < lagstaBrutto) lagstaBrutto=rs.getDouble(7);

			//Kolla bästa pris - Brutto eller netto
			if (rs.getDouble(13) > 0 && rs.getDouble(13) < (lagstaBrutto * (1-bastaRab/100)) ) { //Nettopris
				r.pris=rs.getDouble(13);
				r.rab=0;
			} else { // Bruttopris
				r.pris=lagstaBrutto;
				r.rab=bastaRab;
			}
			r.pris = SXUtil.getRoundedDecimal(r.pris); //Avrundat till 2 decimaler
			r.rab = SXUtil.getRoundedDecimal(r.rab);
			ar.add(r);

		}
		return ar;
	}


	public ArrayList<VaruKorgRad> getVaruKorg() throws NotLoggedInException {
		SXSession sxSession = WebUtil.getSXSession(getThreadLocalRequest().getSession());

		ensureLoggedIn();
	
		Connection con=null;
		try {
			con = sxadm.getConnection();
			return getVaruKorg(con, sxSession);
		} catch (SQLException e) { System.out.print("Exception " + e.toString()); e.printStackTrace();
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return null;
	}

	public ArrayList<Integer> skickaOrder(String marke) throws NotLoggedInException, SxShopKreditSparrException {
		//Sparar varukorgen som en order
		//Returnerar null om inga rader finns i varukorgen
		SXSession sxSession = WebUtil.getSXSession(getThreadLocalRequest().getSession());
		ensureLoggedIn();
		Connection con=null;
		ArrayList<Integer> orders=null;
		try {
			con = sxadm.getConnection();
			orders =	sxServerMainBean.saveSxShopOrder(sxSession.getKundKontaktId(), sxSession.getKundnr(), sxSession.getKundKontaktNamn(), (short)0, marke);
			for (Integer o : orders) {
				WebUtil.logAnvandarhandelse(getThreadLocalRequest(), con, sxSession.getKundLoginNamn(), "Sparad order: " + o);
			}
		} catch (KreditSparrException e) { throw new SxShopKreditSparrException(); }
		catch (SQLException se) { se.printStackTrace(); }// I övrigt ingnorera denna eftersom ordrarna ändå är sparade
		finally { try { con.close(); } catch (Exception ee) {}
		}
		return orders;

	}

	public SokResult getSokArtikel(String sokStr, int maxRader) {
		SokResult sokResult = new SokResult(sokStr);
		sokResult.maxRader=maxRader;
		Connection con=null;
String qa1 = "select case when 1=0 " ;
String qa1_2 = " or upper(a.nummer) like upper(?) ";
String qa1_3 = " then -1000 else 0 end as prio, " +
 " a.nummer, a.namn, a.katnamn, a.enhet, a.utpris, a.staf_pris1, a.staf_pris2, a.staf_antal1, a.staf_antal2, a.rabkod, a.kod1, a.prisdatum, a.forpack, a.minsaljpack, a.prisgiltighetstid, a.bildartnr"+
" from artgrp ag, artgrplank agl, artklase ak, artklaselank akl, artikel a"+
" where ag.grpid=agl.grpid and agl.klasid=ak.klasid and ak.klasid=akl.klasid and a.nummer=akl.artnr ";
String qa2=" and (upper(a.nummer) like upper(?)"+
" or upper(a.namn) like upper(?)"+
" or upper(a.bestnr) like upper(?)"+
" or upper(a.refnr) like upper(?)"+
" or upper(a.rsk) like upper(?)"+
" or upper(a.enummer) like upper(?)"+
" or upper(a.katnamn) like upper(?)"+
" or upper(ag.rubrik) like upper(?)"+
" or upper(ag.text) like upper(?)"+
" or upper(ak.rubrik) like upper(?)"+
" or upper(ak.text) like upper(?)"+
" )";

String qa3=" order by prio, a.nummer";
			try {
				con = sxadm.getConnection();
				StringBuilder saWhere = new StringBuilder();	//För artikelsök
				StringBuilder saCase = new StringBuilder();
				//sa.append(qa1);
				String[] sokDelar = sokStr.split(" ");
				int sokordCn=0;
				//Kolla efter giltiga sökord
				for (String s : sokDelar) {
					if (!(s==null || s.isEmpty())) {
						sokordCn++;
						saWhere.append(qa2);
						saCase.append(qa1_2);
					}
				}
				PreparedStatement stmArtikel=con.prepareStatement(qa1 + saCase.toString()+qa1_3+saWhere.toString()+qa3);

				// Sätt alla parametrar
				int artikelCn=0;
				for (String s : sokDelar) {	//Först fyller vi allt i case-satsen
					if (!(s==null || s.isEmpty())) {
						artikelCn++;
						stmArtikel.setString(artikelCn, s+"%");
					}
				}
				for (String s : sokDelar) {		//Nu fyller vi allt i where-satsen
					if (!(s==null || s.isEmpty())) {
						for (int cn=0; cn<11; cn++) {
							artikelCn++;
							stmArtikel.setString(artikelCn, "%"+s+"%");
						}
					}
				}

				ArtSidaKlase artSidaKlase = new ArtSidaKlase(0, "Sökresultat", "", "");
				ArtSidaKlaseArtikel artSidaKlaseArtikel=null;
				ResultSet rsArtikel = stmArtikel.executeQuery();
				int raderArtikelCn=0;
				while (rsArtikel.next()) {
					raderArtikelCn++;
					if (maxRader>0 && raderArtikelCn>maxRader) {
						sokResult.merRaderFinns=true;
						break;
					}
					artSidaKlaseArtikel = new ArtSidaKlaseArtikel();
					artSidaKlaseArtikel.nummer=rsArtikel.getString(2);
					artSidaKlaseArtikel.namn=rsArtikel.getString(3);
					artSidaKlaseArtikel.katnamn=rsArtikel.getString(3);
					artSidaKlaseArtikel.enhet=anpassaEnhet(rsArtikel.getString(5));
					artSidaKlaseArtikel.utpris=SXUtil.getRoundedDecimal(rsArtikel.getDouble(6));
					artSidaKlaseArtikel.staf_pris1=SXUtil.getRoundedDecimal(rsArtikel.getDouble(7));
					artSidaKlaseArtikel.staf_pris2=SXUtil.getRoundedDecimal(rsArtikel.getDouble(8));
					artSidaKlaseArtikel.staf_antal1=rsArtikel.getDouble(9);
					artSidaKlaseArtikel.staf_antal2=rsArtikel.getDouble(10);
					artSidaKlaseArtikel.rabkod=rsArtikel.getString(11);
					artSidaKlaseArtikel.kod1=rsArtikel.getString(12);
					artSidaKlaseArtikel.prisdatum=rsArtikel.getDate(13);
					artSidaKlaseArtikel.forpack=rsArtikel.getDouble(14);
					artSidaKlaseArtikel.minsaljpack=rsArtikel.getDouble(15);
					artSidaKlaseArtikel.prisgiltighetstid=rsArtikel.getInt(16);
					String bild = rsArtikel.getString(17);
					if (bild==null || bild.isEmpty()) bild=artSidaKlaseArtikel.nummer;
					artSidaKlaseArtikel.bildurl=bild;
					artSidaKlase.artiklar.add(artSidaKlaseArtikel);
				}
				if (raderArtikelCn>0) {
					sokResult.sokResultKlasar.add(new SokResultKlase(artSidaKlase));
				}

			} catch (SQLException e) { System.out.print("Exception " + e.toString()); e.printStackTrace();
			} finally {
				try { con.close(); } catch (Exception e) {}
			}

		return sokResult;
	}





	public SokResult getSokResult(String sokStr, int maxRader) {
		Connection con=null;
		SokResult sokResult = new SokResult(sokStr);
		sokResult.maxRader=maxRader;

String qa1 = "select a.nummer, a.namn, a.katnamn, a.enhet, a.utpris, a.staf_pris1, a.staf_pris2, a.staf_antal1, a.staf_antal2, a.rabkod, a.kod1, a.prisdatum, a.forpack, a.minsaljpack, a.prisgiltighetstid, a.bildartnr"+
" from artgrp ag, artgrplank agl, artklase ak, artklaselank akl, artikel a"+
" where ag.grpid=agl.grpid and agl.klasid=ak.klasid and ak.klasid=akl.klasid and a.nummer=akl.artnr "+
" and ( 1=0";
String qa2=" or upper(a.nummer) like upper(?)"+
" or upper(a.bestnr) like upper(?)"+
" or upper(a.refnr) like upper(?)"+
" or upper(a.rsk) like upper(?)"+
" or upper(a.enummer) like upper(?)";
String qa3=" ) order by a.nummer";


	String q1 =
"select"+
" case when ag6.rubrik is null then '' else ag6.rubrik || ' -> ' end ||"+
" case when ag5.rubrik is null then '' else ag5.rubrik || ' -> ' end ||"+
" case when ag4.rubrik is null then '' else ag4.rubrik || ' -> ' end ||"+
" case when ag3.rubrik is null then '' else ag3.rubrik || ' -> ' end ||"+
" case when ag2.rubrik is null then '' else ag2.rubrik || ' -> ' end ||"+
" g.grprubrik as plats,"+
" g.grpid,g.prevgrpid, g.grprubrik, g.klasid, g.klasrubrik, g.klastext, g.klasinfourl, g.artnr, g.namn, g.katnamn, g.enhet, g.utpris, g.staf_pris1,"+
" g.staf_pris2, g.staf_antal1, g.staf_antal2, g.rabkod, g.kod1, g.prisdatum, g.forpack, g.minsaljpack, g.prisgiltighetstid, g.bildartnr"+
" from ("+
" select ag.grpid as grpid, ag.prevgrpid as prevgrpid, ag.rubrik as grprubrik, agl.sortorder as klassortorder, ak.klasid as klasid,"+
" ak.rubrik as klasrubrik, ak.text as klastext, ak.infourl as klasinfourl, akl.sortorder as artsortorder, a.nummer as artnr, a.namn as namn,"+
" a.katnamn as katnamn, a.enhet as enhet, a.utpris as utpris, a.staf_pris1 as staf_pris1, a.staf_pris2 as staf_pris2,"+
" a.staf_antal1 as staf_antal1, a.staf_antal2 as staf_antal2, a.rabkod as rabkod, a.kod1 as kod1, a.prisdatum as prisdatum,"+
" a.forpack as forpack, a.minsaljpack as minsaljpack, a.prisgiltighetstid as prisgiltighetstid, a.bildartnr as bildartnr"+
" from artgrp ag, artgrplank agl, artklase ak, artklaselank akl, artikel a"+
" where ag.grpid=agl.grpid and agl.klasid=ak.klasid and ak.klasid=akl.klasid and a.nummer=akl.artnr";

String q2 = " and ("+
" upper(a.namn) like upper(?)"+
" or upper(a.nummer) like upper(?)"+
" or upper(a.bestnr) like upper(?)"+
" or upper(a.refnr) like upper(?)"+
" or upper(a.rsk) like upper(?)"+
" or upper(a.enummer) like upper(?)"+
" or upper(a.katnamn) like upper(?)"+
" or upper(ag.rubrik) like upper(?)"+
" or upper(ag.text) like upper(?)"+
" or upper(ak.rubrik) like upper(?)"+
" or upper(ak.text) like upper(?)"+
" )";

String q3=" ) as g"+
" left outer join artgrp ag2 on ag2.grpid=g.prevgrpid"+
" left outer join artgrp ag3 on ag3.grpid=ag2.prevgrpid"+
" left outer join artgrp ag4 on ag4.grpid=ag3.prevgrpid"+
" left outer join artgrp ag5 on ag5.grpid=ag4.prevgrpid"+
" left outer join artgrp ag6 on ag6.grpid=ag5.prevgrpid"+
" order by  g.grpid, g.klassortorder, g.klasid, g.artsortorder, g.artnr";

			try {
				con = sxadm.getConnection();

				StringBuilder sa = new StringBuilder();	//För artikelsök
				sa.append(qa1);

				StringBuilder sb = new StringBuilder(1000);	//För katalogsök
				sb.append(q1);

				String[] sokDelar = sokStr.split(" ");
				int sokordCn=0;
				//Kolla efter giltiga sökord
				for (String s : sokDelar) {
					if (!(s==null || s.isEmpty())) {
						sokordCn++;
						sb.append(q2);
						sa.append(qa2);
					}
				}
				if (sokordCn < 1) { sb.append("and 1=0"); }	//Vi har inga sökord, och förhindrar frågan att ge resultat
				sb.append(q3);
				sa.append(qa3);

				PreparedStatement stmKatalog=con.prepareStatement(sb.toString());
				PreparedStatement stmArtikel=con.prepareStatement(sa.toString());

				// Sätt alla parametrar
				int artikelCn=0;
				int katalogCn=0;
				for (String s : sokDelar) {
					if (!(s==null || s.isEmpty())) {
						for (int cn=0; cn<11; cn++) {
							katalogCn++;
							stmKatalog.setString(katalogCn, "%"+s+"%");
						}
						for (int cn=0; cn<5; cn++) {
							artikelCn++;
							stmArtikel.setString(artikelCn, "%"+s+"%");
						}
					}
				}
				ArtSidaKlase artSidaKlase = new ArtSidaKlase(0, "Söktoppen", "", "");
				ArtSidaKlaseArtikel artSidaKlaseArtikel=null;
				ResultSet rsKatalog = stmKatalog.executeQuery();
				ResultSet rsArtikel = stmArtikel.executeQuery();
				int raderArtikelCn=0;
				while (rsArtikel.next()) {
					raderArtikelCn++;
					if (raderArtikelCn>10) break;
					artSidaKlaseArtikel = new ArtSidaKlaseArtikel();
					artSidaKlaseArtikel.nummer=rsArtikel.getString(1);
					artSidaKlaseArtikel.namn=rsArtikel.getString(2);
					artSidaKlaseArtikel.katnamn=rsArtikel.getString(2);
					artSidaKlaseArtikel.enhet=anpassaEnhet(rsArtikel.getString(4));
					artSidaKlaseArtikel.utpris=rsArtikel.getDouble(5);
					artSidaKlaseArtikel.staf_pris1=rsArtikel.getDouble(6);
					artSidaKlaseArtikel.staf_pris2=rsArtikel.getDouble(7);
					artSidaKlaseArtikel.staf_antal1=rsArtikel.getDouble(8);
					artSidaKlaseArtikel.staf_antal2=rsArtikel.getDouble(9);
					artSidaKlaseArtikel.rabkod=rsArtikel.getString(10);
					artSidaKlaseArtikel.kod1=rsArtikel.getString(11);
					artSidaKlaseArtikel.prisdatum=rsArtikel.getDate(12);
					artSidaKlaseArtikel.forpack=rsArtikel.getDouble(13);
					artSidaKlaseArtikel.minsaljpack=rsArtikel.getDouble(14);
					artSidaKlaseArtikel.prisgiltighetstid=rsArtikel.getInt(15);
					String bild = rsArtikel.getString(16);
					if (bild==null || bild.isEmpty()) bild=artSidaKlaseArtikel.nummer;
					artSidaKlaseArtikel.bildurl=bild;
					artSidaKlase.artiklar.add(artSidaKlaseArtikel);
				}
				if (raderArtikelCn>0) {
					sokResult.sokResultKlasar.add(new SokResultKlase(artSidaKlase));
				}

				Integer tempKlasid=null;
				artSidaKlase=null;
				artSidaKlaseArtikel=null;
				int raderKatalogCn=0;
				while (rsKatalog.next()) {
					raderKatalogCn++;
					if (maxRader>0 && maxRader<raderKatalogCn) {
						sokResult.merRaderFinns=true;
						break;
					}
					SokResultKlase sokResultKlase;
					if (tempKlasid==null) {
						//Första gången
						artSidaKlase=new ArtSidaKlase(rsKatalog.getInt(5), rsKatalog.getString(6), rsKatalog.getString(7), rsKatalog.getString(8));
						sokResultKlase = new SokResultKlase(artSidaKlase);
						sokResultKlase.artSidaKlase.platsText=rsKatalog.getString(1);
						sokResult.sokResultKlasar.add(sokResultKlase);
						tempKlasid=rsKatalog.getInt(5);
					}
					if (!tempKlasid.equals(rsKatalog.getInt(5))) {
						artSidaKlase=new ArtSidaKlase(rsKatalog.getInt(5), rsKatalog.getString(6), rsKatalog.getString(7), rsKatalog.getString(8));
						sokResultKlase = new SokResultKlase(artSidaKlase);
						sokResultKlase.artSidaKlase.platsText=rsKatalog.getString(1);
						sokResult.sokResultKlasar.add(sokResultKlase);
						tempKlasid=rsKatalog.getInt(5);
					}
					artSidaKlaseArtikel = new ArtSidaKlaseArtikel();
					artSidaKlaseArtikel.nummer=rsKatalog.getString(9);
					artSidaKlaseArtikel.namn=rsKatalog.getString(10);
					artSidaKlaseArtikel.katnamn=rsKatalog.getString(11);
					artSidaKlaseArtikel.enhet=anpassaEnhet(rsKatalog.getString(12));
					artSidaKlaseArtikel.utpris=rsKatalog.getDouble(13);
					artSidaKlaseArtikel.staf_pris1=rsKatalog.getDouble(14);
					artSidaKlaseArtikel.staf_pris2=rsKatalog.getDouble(15);
					artSidaKlaseArtikel.staf_antal1=rsKatalog.getDouble(16);
					artSidaKlaseArtikel.staf_antal2=rsKatalog.getDouble(17);
					artSidaKlaseArtikel.rabkod=rsKatalog.getString(18);
					artSidaKlaseArtikel.kod1=rsKatalog.getString(19);
					artSidaKlaseArtikel.prisdatum=rsKatalog.getDate(20);
					artSidaKlaseArtikel.forpack=rsKatalog.getDouble(21);
					artSidaKlaseArtikel.minsaljpack=rsKatalog.getDouble(22);
					artSidaKlaseArtikel.prisgiltighetstid=rsKatalog.getInt(23);
					String bild = rsKatalog.getString(24);
					if (bild==null || bild.isEmpty()) bild=artSidaKlaseArtikel.nummer;
					artSidaKlaseArtikel.bildurl=bild;
					artSidaKlase.artiklar.add(artSidaKlaseArtikel);
				}
			} catch (SQLException e) { System.out.print("Exception " + e.toString()); e.printStackTrace();
			} finally {
				try { con.close(); } catch (Exception e) {}
			}



		return sokResult;
	}
	/*public ArtSida getArtSidaKlase(int grpid, int klasid) {
		ArtSida artSida = null;
		Connection con=null;
		try {
			con = sxadm.getConnection();
			Statement stm = con.createStatement();
			ResultSet art = stm.executeQuery(
"select a.nummer, a.namn, a.katnamn, a.enhet, a.utpris, a.staf_pris1, a.staf_pris2, a.staf_antal1, a.staf_antal2, a.rabkod, a.kod1, a.prisdatum, a.forpack, a.minsaljpack, a.prisgiltighetstid, a.bildartnr "
+" ag.grpid, ag.rubrik, ag.text, ag.infourl, ak.klasid, ak.rubrik, ak.text, ak.infourl"
+" from artikel a, artklaselank l, artklase ak, artgrplank agl, artgrp ag  "
+" where ag.grpid=agl.grpid and ak.klasid=agl.klasid and ak.klasid=l.klasid and l.artnr=a.nummer and l.klasid="+klasid+" and ag.grpid="+grpid+" order by l.sortorder, a.nummer");
			boolean firstRun = true;
			ArtSidaKlase artSidaKlase=null;
			while (art.next()) {
				if (firstRun) {
					artSida=new ArtSida(art.getInt(17), art.getString(18), art.getString(19), art.getString(20));
					artSida.klasid=klasid;
					artSidaKlase = new ArtSidaKlase(art.getInt(21),art.getString(22),art.getString(23), art.getString(24));
					firstRun=false;
				 }
				ArtSidaKlaseArtikel artSidaKlaseArtikel = new ArtSidaKlaseArtikel();
				artSidaKlaseArtikel.nummer=art.getString(1);
				artSidaKlaseArtikel.namn=art.getString(2);
				artSidaKlaseArtikel.katnamn=art.getString(3);
				artSidaKlaseArtikel.enhet=art.getString(4);
				artSidaKlaseArtikel.utpris=art.getDouble(5);
				artSidaKlaseArtikel.staf_pris1=art.getDouble(6);
				artSidaKlaseArtikel.staf_pris2=art.getDouble(7);
				artSidaKlaseArtikel.staf_antal1=art.getDouble(8);
				artSidaKlaseArtikel.staf_antal2=art.getDouble(9);
				artSidaKlaseArtikel.rabkod=art.getString(10);
				artSidaKlaseArtikel.kod1=art.getString(11);
				artSidaKlaseArtikel.prisdatum=art.getDate(12);
				artSidaKlaseArtikel.forpack=art.getDouble(13);
				artSidaKlaseArtikel.minsaljpack=art.getDouble(14);
				artSidaKlaseArtikel.prisgiltighetstid=art.getInt(15);
				String bild = art.getString(16);
				if (bild==null || bild.isEmpty()) bild=artSidaKlaseArtikel.nummer;
				artSidaKlaseArtikel.bildurl=bild;
				artSidaKlase.artiklar.add(artSidaKlaseArtikel);
			}
			artSida.klasar.add(artSidaKlase);
		} catch (SQLException e) { System.out.print("Exception " + e.toString()); e.printStackTrace();
		} finally {
			try { con.close(); } catch (Exception e) {}
		}

		return artSida;
	}
*/



	public String anpassaEnhet(String enhet)  {
		if ("M".equals(enhet)) return "m";
		if ("ST".equals(enhet)) return "st";
		if ("KG".equals(enhet)) return "Kg";
		if ("PKT".equals(enhet)) return "paket";
		if ("LST".equals(enhet)) return "list";
		if ("L".equals(enhet)) return "l";
		return enhet;
	}
}

