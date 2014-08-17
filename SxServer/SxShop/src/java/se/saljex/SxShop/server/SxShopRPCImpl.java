/*
 * SxShopRPCImpl.java
 *
 * Created on den 3 december 2009, 10:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package se.saljex.SxShop.server;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import javax.annotation.security.RunAs;
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
import se.saljex.SxShop.client.rpcobject.AnvandareUppgifter;
import se.saljex.SxShop.client.rpcobject.BetalningList;
import se.saljex.SxShop.client.rpcobject.BetalningRow;
import se.saljex.SxShop.client.rpcobject.FakturaHeader;
import se.saljex.SxShop.client.rpcobject.FakturaHeaderList;
import se.saljex.SxShop.client.rpcobject.FakturaHeaderOrderMarke;
import se.saljex.SxShop.client.rpcobject.FakturaInfo;
import se.saljex.SxShop.client.rpcobject.FakturaRow;
import se.saljex.SxShop.client.rpcobject.VaruKorgRad;
import se.saljex.SxShop.client.rpcobject.NotLoggedInException;
import se.saljex.SxShop.client.rpcobject.SxShopKreditSparrException;
import se.saljex.SxShop.client.rpcobject.FelaktigtAntalException;


import se.saljex.SxShop.client.rpcobject.IncorrectLogInException;
import se.saljex.SxShop.client.rpcobject.KundresRow;
import se.saljex.SxShop.client.rpcobject.LagerSaldo;
import se.saljex.SxShop.client.rpcobject.LagerSaldoRad;
import se.saljex.SxShop.client.rpcobject.OffertHeader;
import se.saljex.SxShop.client.rpcobject.OffertHeaderList;
import se.saljex.SxShop.client.rpcobject.OffertInfo;
import se.saljex.SxShop.client.rpcobject.OffertRow;
import se.saljex.SxShop.client.rpcobject.OrderHeader;
import se.saljex.SxShop.client.rpcobject.OrderHeaderList;
import se.saljex.SxShop.client.rpcobject.OrderInfo;
import se.saljex.SxShop.client.rpcobject.OrderRow;
import se.saljex.SxShop.client.rpcobject.ServerErrorException;
import se.saljex.SxShop.client.rpcobject.StatArtikelFakturaRow;
import se.saljex.SxShop.client.rpcobject.StatArtikelList;
import se.saljex.SxShop.client.rpcobject.StatArtikelRow;
import se.saljex.SxShop.client.rpcobject.StatInkopHeader;
import se.saljex.SxShop.client.rpcobject.StatInkopRow;
import se.saljex.SxShop.client.rpcobject.UtlevInfo;
import se.saljex.SxShop.client.rpcobject.UtlevList;
import se.saljex.SxShop.client.rpcobject.UtlevRow;
import se.saljex.sxlibrary.exceptions.KreditSparrException;
import se.saljex.sxlibrary.SXConstant;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxserver.websupport.GoogleChartHandler;
import se.saljex.sxlibrary.SXSession;
import se.saljex.sxlibrary.WebSupport;
import se.saljex.sxlibrary.LocalWebSupportRemote;
import se.saljex.sxlibrary.SxServerMainRemote;
import se.saljex.sxlibrary.exceptions.SxInfoException;

/**
 *
 * @author ulf
 */
@RunAs("admin")
public class SxShopRPCImpl extends RemoteServiceServlet implements
		  SxShopRPC {
	@EJB
	private LocalWebSupportRemote localWebSupportBean;
	@EJB	
	private SxServerMainRemote sxServerMainBean;

	@javax.annotation.Resource(mappedName = "sxadm")
	private DataSource sxadm;

	private static final String SELECT_ORDERHEADER = "select ordernr, marke, datum, lagernr, status, levadr1, levadr2, levadr3, referens, direktlevnr, lastdatum from order1";
	private static final String SELECT_UTLEVROW = "select u1.ordernr, u1.datum, u1.marke, u1.referens, u1.levadr1, u1.levadr2, u1.levadr3, u1.lagernr, f1.faktnr, f1.datum " +
													" from utlev1 u1 left outer join faktura1 f1 on f1.faktnr = u1.faktnr ";

	private static final String SELECT_FAKTURA = 	"select f1.faktnr, f1.datum, f1.datum+f1.ktid, f1.t_attbetala, f1.ordernr, f1.marke " +
														", f2.pos, f2.artnr, f2.namn, f2.text, f2.lev, f2.enh, f2.pris, f2.rab, f2.ordernr, f2.summa " +
														"from faktura2 f2 left outer join faktura1 f1 on f1.faktnr=f2.faktnr";
	private static final String SELECT_OFFERT = "select o2.offertnr, o1.datum, o1.marke, o2.pos, o2.artnr, o2.namn, o2.text, o2.best, o2.enh, o2.pris, o2.rab, o2.summa "+
														" from offert1 o1 left outer join offert2 o2 on o1.offertnr=o2.offertnr";

	public ArrayList getArtikelTrad(int maxBilder) {
		ArrayList<ArtGrupp> ar = new ArrayList();
		ArtGrupp ag;


		Connection con=null;
		try {
			con = sxadm.getConnection();
			Statement stm = con.createStatement();

//			ResultSet rs = stm.executeQuery("select grpid, prevgrpid, rubrik from artgrp order by prevgrpid, sortorder, grpid");

			//Jag vill ändra denna sql-sats så att den direkt filtrerar ut maxBilder antal rader per grupp
			//men kan inet komma på hur. Tills vidare får filtreringen ske programeringsmässigt
			ResultSet rs = stm.executeQuery(
							"select  ag.grpid, ag.prevgrpid, ag.rubrik, g2.artnr, g2.bildartnr "+
							" from artgrp ag  left outer join "+
							" ( "+
								" select a.nummer as artnr, a.bildartnr as bildartnr, a1.grpid1 as grpid1 from "+
								 " ( "+
									 " select min(akl.artnr) as artnr, g.grpid1 as grpid1 from "+
									 " ( "+
										 " select ag1.grpid as grpid1, ag2.grpid as grpid2, ag3.grpid as grpid3, ag4.grpid as grpid4 "+
										 " from artgrp ag1 "+
										 " left outer join artgrp ag2 on ag2.prevgrpid = ag1.grpid "+
										 " left outer join artgrp ag3 on ag3.prevgrpid = ag2.grpid "+
										 " left outer join artgrp ag4 on ag4.prevgrpid = ag3.grpid "+
									 " ) g "+
									 " left outer join artgrplank agl on agl.grpid=g.grpid1 or agl.grpid=g.grpid2 or agl.grpid=g.grpid3 or agl.grpid=g.grpid4 "+
									 " left outer join artklaselank akl on akl.klasid=agl.klasid "+
									 " group by g.grpid1, akl.klasid "+
								 " ) a1 "+
								 " join artikel a on a.nummer=a1.artnr "+
								 " order by random() "+
							" ) g2 on g2.grpid1=ag.grpid "+
							" order by ag.prevgrpid, ag.sortorder, ag.grpid"
					  );


/*			PreparedStatement stmBilder = con.prepareStatement(
							"select a.nummer, a.bildartnr from "+
							" ( "+
							" select min(akl.artnr) as artnr from "+
								" ( "+
								" select ag1.grpid as grpid1, ag2.grpid as grpid2, ag3.grpid as grpid3, ag4.grpid as grpid4 " +
								" from artgrp ag1  "+
									 " left outer join artgrp ag2 on ag2.prevgrpid = ag1.grpid "+
									" left outer join artgrp ag3 on ag3.prevgrpid = ag2.grpid "+
									" left outer join artgrp ag4 on ag4.prevgrpid = ag3.grpid "+
								" where ag1.grpid=? "+
								" ) g "+
								" left outer join artgrplank agl on agl.grpid=g.grpid1 or agl.grpid=g.grpid2 or agl.grpid=g.grpid3 or agl.grpid=g.grpid4 "+
								" left outer join artklaselank akl on akl.klasid=agl.klasid "+
								" group by akl.klasid "+
							" ) a1 "+
							" join artikel a on a.nummer=a1.artnr "+
							" order by random() limit ?"
					  ); */
			int tempGrp=0;
			int rowCn=0;
			boolean firstRun=true;
			ag=null;
			while (rs.next()) {
				if (tempGrp != rs.getInt(1) || firstRun) {
					ag=new  ArtGrupp(rs.getInt(1), rs.getInt(2), rs.getString(3));
					ar.add(ag);
					tempGrp = rs.getInt(1);
					rowCn=0;
					firstRun=false;
				}
				if (rowCn < maxBilder)	{
					rowCn++;
					ag.bilder.add((rs.getString(5)==null || rs.getString(5).isEmpty()) ? rs.getString(4) : rs.getString(5));
				}
//				stmBilder.setInt(1, rs.getInt(1));
//				stmBilder.setInt(2, maxBilder);
//				ResultSet rsBilder = stmBilder.executeQuery();
//				while (rsBilder.next()) {
//					ag.bilder.add((rsBilder.getString(2)==null || rsBilder.getString(2).isEmpty()) ? rsBilder.getString(1) : rsBilder.getString(2));
//				}
//				ar.add(ag);
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
				artSida = new ArtSida(rs.getInt(1),rs.getString(2),SXUtil.toHtml(rs.getString(3)), rs.getString(4));
			}
			ResultSet klase = stm.executeQuery("select k.klasid, k.rubrik, k.text, k.infourl from artgrplank g, artklase k where g.klasid=k.klasid and g.grpid=" + grpid + " order by g.sortorder, g.klasid");
			while (klase.next()){
				ArtSidaKlase artSidaKlase = new ArtSidaKlase(klase.getInt(1),klase.getString(2),SXUtil.toHtml(klase.getString(3)), klase.getString(4));
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

		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
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
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
		
		if (!sxSession.getInloggad()) {	// Om vi redan är inloggad så ska vi inte försöka igen
			if (anvandare!=null && autoLogInId!=null) {
				if (!anvandare.isEmpty() && !autoLogInId.isEmpty()) {
					Connection con=null;
					try {
						con = sxadm.getConnection();
						//Autoinlogga om möjligt. Skulle det inte gå faller vi igenom och functionen returnerar gästlogin
						WebSupport.autoLogInKund(getThreadLocalRequest(), con, anvandare, autoLogInId);

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
			if (WebSupport.logInKund(getThreadLocalRequest(), con, anvandare, losen, stayLoggedIn)) {
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
			WebSupport.logOutKund(getThreadLocalRequest(),con);
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
		if (!WebSupport.getSXSession(getThreadLocalRequest().getSession()).checkBehorighetKund()) throw new NotLoggedInException();
	}

	public ArrayList<VaruKorgRad> deleteVaruKorg(String artnr) throws NotLoggedInException {
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

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
	// Returnerar strukturnr om det är en strukturartikel, annars null
	private String checkFelaktigtAntalAndThrowException(Connection con, String artnr, double antal) throws FelaktigtAntalException, EntityNotFoundException, SQLException {
			String struktnr = null;
			PreparedStatement stm=con.prepareStatement("select namn, minsaljpack, enhet, struktnr from artikel where nummer=?");
			stm.setString(1, artnr);
			ResultSet rs = stm.executeQuery();
			double minSaljpack = 0;
			if (rs.next()) {
				minSaljpack = rs.getDouble(2);
				struktnr = rs.getString(4);
				if (struktnr!=null && struktnr.isEmpty()) struktnr=null;	//Sätt till null om tom sträng
				if (minSaljpack!=0 && antal % minSaljpack > 0) throw new FelaktigtAntalException("Angivet antal för artikel " + artnr + " " + rs.getString(1) + " går inte jämt upp i minsta odelbara förpackning " + minSaljpack + " " + rs.getString(3) + ". Kontrollera antal och enhet.");
			} else throw new EntityNotFoundException();
			return struktnr;
	}

	public ArrayList<VaruKorgRad> updateVaruKorg(String artnr, double antal) throws NotLoggedInException, FelaktigtAntalException{
		ArrayList<VaruKorgRad> ar=null;
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

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


	public ArrayList<VaruKorgRad> addVaruKorg(String artnr, double antal) throws NotLoggedInException, FelaktigtAntalException, ServerErrorException {
		ArrayList<VaruKorgRad> ar=null;
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

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
			String struktnr=null;
			try {		struktnr=checkFelaktigtAntalAndThrowException(con, artnr, antal); }	catch (EntityNotFoundException e) { artnrGiltigt=false; }

			//Spara nytt värde i varukorgen
			if (artnrGiltigt) {
				if (artikelFinnsIVarukorg) {	// Artikeln finns i varukorgen med detta antal
					doUpdateVarukorg(con, sxSession, artnr, gammaltAntal+antal);
				} else {
					//Kolla nu om det är en struktur, och i så fall lägg till varje rad i strukturen
					
					if (struktnr!=null && !struktnr.isEmpty()) {
						stm=con.prepareStatement("select artnr, antal from artstrukt where nummer=?");
						stm.setString(1, struktnr);
						rs = stm.executeQuery();
						while (rs.next()) {
							try { checkFelaktigtAntalAndThrowException(con, rs.getString(1), rs.getDouble(2)); }	catch (Exception e) { throw new ServerErrorException("Fel i strukturen"); }
							doInsertVarukorg(con, sxSession.getKundKontaktId(), rs.getString(1), rs.getDouble(2));
						}
					} else {		//Annars lägg till aritkeln
						doInsertVarukorg(con, sxSession.getKundKontaktId(), artnr, antal);
					}
				}
			}
			ar= getVaruKorg(con,sxSession);

		} catch (SQLException e) { System.out.print("Exception " + e.toString()); e.printStackTrace();
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return ar;
	}

	private void doInsertVarukorg(Connection con, int kontaktid, String artnr, double antal) throws SQLException {
		PreparedStatement stm=con.prepareStatement("insert into varukorg (kontaktid, typ, artnr, antal) values (?,?,?,?)");
		stm.setInt(1, kontaktid);
		stm.setString(2, "VK");
		stm.setString(3, artnr);
		stm.setDouble(4, antal);
		stm.executeUpdate();
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
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

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

	public ArrayList<Integer> skickaOrder(String marke) throws NotLoggedInException, SxShopKreditSparrException, ServerErrorException {
		//Sparar varukorgen som en order
		//Returnerar null om inga rader finns i varukorgen
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
		ensureLoggedIn();
		Connection con=null;
		ArrayList<Integer> orders=null;
		try {
			con = sxadm.getConnection();
			orders =	sxServerMainBean.saveSxShopOrder(sxSession.getKundKontaktId(), sxSession.getKundnr(), sxSession.getKundKontaktNamn(), (short)0, marke);
			for (Integer o : orders) {
				WebSupport.logAnvandarhandelse(getThreadLocalRequest(), con, sxSession.getKundLoginNamn(), "Sparad order: " + o);
			}
		} catch (KreditSparrException e) { throw new SxShopKreditSparrException(); }
		catch (SxInfoException ee)  { throw new ServerErrorException(ee.getMessage()); }
		catch (SQLException se) { se.printStackTrace(); }// I övrigt ingnorera denna eftersom ordrarna ändå är sparade
		finally { try { con.close(); } catch (Exception ee) {}
		}
		return orders;

	}

	//Snabbsöl artikel
	public SokResult getSokArtikel(String sokStr, int maxRader) {
		SokResult sokResult = new SokResult(sokStr);
		sokResult.maxRader=maxRader;
		Connection con=null;
String qa1 = "select distinct case when 1=0 " ;
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

String qa1 = "select distinct a.nummer, a.namn, a.katnamn, a.enhet, a.utpris, a.staf_pris1, a.staf_pris2, a.staf_antal1, a.staf_antal2, a.rabkod, a.kod1, a.prisdatum, a.forpack, a.minsaljpack, a.prisgiltighetstid, a.bildartnr"+
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



	//Returnerar alla artiklar som ligger på kampanj
	public SokResult getKampanjartiklar() throws ServerErrorException, NotLoggedInException{
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
		Connection con=null;
		SokResult sokResult = new SokResult();
		sokResult.maxRader=0;
			try {
				con = sxadm.getConnection();
				PreparedStatement stmKund = con.prepareStatement("select elkund, vvskund, vakund, golvkund, fastighetskund, installator, butik, industri, oem, industri from kund where nummer=?");
				stmKund.setString(1, sxSession.getKundnr());
				ResultSet rsKund = stmKund.executeQuery();
				if (rsKund.next()) {
					PreparedStatement stmArtikel = con.prepareStatement(
						 "select 0, a.nummer, a.namn, a.katnamn, a.enhet, a.kamppris, a.kampprisstaf1, a.kampprisstaf2, a.staf_antal1, a.staf_antal2, 'Netto', '', a.prisdatum, a.forpack, a.minsaljpack, a.prisgiltighetstid, a.bildartnr, a.kampkundgrp, a.kampkundartgrp"+
						 " from artikel a " +
						" where current_date between kampfrdat and kamptidat" +
						" order by a.nummer"
					);
					ArtSidaKlase artSidaKlase = new ArtSidaKlase(0, "Kampanjartiklar", "", "");
					ArtSidaKlaseArtikel artSidaKlaseArtikel=null;
					ResultSet rsArtikel = stmArtikel.executeQuery();
					int raderArtikelCn=0;
					int q1;
					int q2;
					while (rsArtikel.next()) {
						q1 = rsKund.getInt(1)*SXConstant.KAMPBIT_ELKUND + rsKund.getInt(2)*SXConstant.KAMPBIT_VVSKUND + rsKund.getInt(3)*SXConstant.KAMPBIT_VAKUND + rsKund.getInt(4)*SXConstant.KAMPBIT_GOLVKUND + rsKund.getInt(5)*SXConstant.KAMPBIT_FASTIGHETSKUND;
						q2 = rsKund.getInt(6)*SXConstant.KAMPBIT_INSTALLATOR + rsKund.getInt(7)*SXConstant.KAMPBIT_BUTIK + rsKund.getInt(8)*SXConstant.KAMPBIT_INDUSTRI + rsKund.getInt(9)*SXConstant.KAMPBIT_OEM + rsKund.getInt(10)*SXConstant.KAMPBIT_GROSSIST;
						if (			(rsArtikel.getInt(18) == 0 && rsArtikel.getInt(19)==0) ||
										((rsArtikel.getInt(19) & q1) > 0 && (rsArtikel.getInt(18) & q2) > 0)	  ) {
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
							raderArtikelCn++;
						}
					}
					if (raderArtikelCn>0) {
						sokResult.sokResultKlasar.add(new SokResultKlase(artSidaKlase));
					}
				}

			} catch (SQLException e) { System.out.print("Exception " + e.toString()); e.printStackTrace();
			} finally {
				try { con.close(); } catch (Exception e) {}
			}

		return sokResult;
	}






	public String anpassaEnhet(String enhet)  {
		if ("M".equals(enhet)) return "m";
		if ("ST".equals(enhet)) return "st";
		if ("KG".equals(enhet)) return "Kg";
		if ("PKT".equals(enhet)) return "paket";
		if ("LST".equals(enhet)) return "list";
		if ("L".equals(enhet)) return "l";
		return enhet;
	}


	public void skickaInloggningsuppgifter(String anvandareOrEpost) throws IncorrectLogInException {
		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement st = con.prepareStatement("select kk.epost, kl.loginnamn, kl.loginlosen from kund k, kundkontakt kk, kundlogin kl where k.nummer = kk.kundnr and kk.kontaktid = kl.kontaktid and (kl.loginnamn=? or kk.epost=?)");
			st.setString(1, anvandareOrEpost);
			st.setString(2, anvandareOrEpost);
			ResultSet rs = st.executeQuery();
			boolean err;
			boolean minstEnUppgiftHittad=false;
			while (rs.next()) {
				err=sxServerMainBean.sendSimpleMail(rs.getString(1), "Inloggningsuppgifter för vår webbutik", "Hej! <br>Här kommer dina inloggningsuppgifter.<br>" +
						  "Användarnamn: " + rs.getString(2) + "<br>Lösenord: " + rs.getString(3) + "<br>");
				if (!err) minstEnUppgiftHittad=true;
			}

			if (!minstEnUppgiftHittad) throw new IncorrectLogInException();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IncorrectLogInException();
		} finally {
			try { con.close(); } catch (Exception e) {}
		}

	}

	public LagerSaldo getLagerSaldon(String artnr) throws NotLoggedInException, ServerErrorException {
		LagerSaldo lagerSaldo = new LagerSaldo();
		lagerSaldo.artnr=artnr;
		ensureLoggedIn();

		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement("select l.lagernr, lid.bnamn, l.ilager-case when iorder > 0 then iorder else 0 end as tillgangliga from artikel a, lager l, lagerid lid where a.nummer=l.artnr and lid.lagernr=l.lagernr and a.nummer=? order by l.lagernr");
			stm.setString(1, artnr);
			ResultSet rs = stm.executeQuery();
			LagerSaldoRad s;
			while (rs.next()) {
				s = new LagerSaldoRad();
				s.lagernr = rs.getInt(1);
				s.lagerNamn = rs.getString(2);
				s.tillgangliga = rs.getDouble(3);
				lagerSaldo.lagerSaldoRader.add(s);
			}
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return lagerSaldo;
	}


	private ArrayList<FakturaHeaderOrderMarke> getFakturaHeaderOrderMarken(Connection con, int faktnr) throws SQLException {
		ArrayList<FakturaHeaderOrderMarke> omArr = new ArrayList();
		PreparedStatement stm = con.prepareStatement("select distinct u1.ordernr, u1.marke, f1.ordernr, f1.marke from faktura2 f2, utlev1 u1, faktura1 f1 where f1.faktnr=f2.faktnr and f2.faktnr=? and u1.ordernr=f2.ordernr order by u1.ordernr desc");
		stm.setInt(1, faktnr);
		ResultSet rs = stm.executeQuery();
		FakturaHeaderOrderMarke fom;
		boolean firstRun=true;
		while(rs.next()) {
			//Lägg till ev. ordernr från fakturan, men bara i första loopen
			if (firstRun && rs.getInt(3) != 0 || (rs.getString(4) != null && !rs.getString(4).isEmpty())) {
				fom = new FakturaHeaderOrderMarke();
				fom.ordernr=rs.getInt(3);
				fom.marke=rs.getString(4);
				omArr.add(fom);
			}
			fom = new FakturaHeaderOrderMarke();
			fom.ordernr=rs.getInt(1);
			fom.marke=rs.getString(2);
			omArr.add(fom);
			firstRun=false;
		}
		
		return omArr;  
	}
	
	
	//Get fakturalista med start från offset om pageSize antal rader
	public FakturaHeaderList getFakturaHeaders(int startRow, int pageSize) throws ServerErrorException, NotLoggedInException {
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
		ensureLoggedIn();

		FakturaHeaderList list = new FakturaHeaderList();
		if (startRow<0) startRow=0;
		if (pageSize<1) pageSize=30;
		list.pageSize=pageSize;

		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement("select faktnr, datum, datum+ktid, t_attbetala, ordernr, marke from faktura1 where kundnr=? order by faktnr desc offset ? limit ?");
//			PreparedStatement stmo = con.prepareStatement("select distinct u1.ordernr, u1.marke from faktura2 f2, utlev1 u1 where f2.faktnr=? and u1.ordernr=f2.ordernr order by ordernr desc");
			stm.setString(1, sxSession.getKundnr());
			stm.setInt(2, startRow);
			stm.setInt(3, pageSize+1);//Limit till 1 mer rad än pagesize för att se om det finns fler rader efter sista
			ResultSet rs = stm.executeQuery();
			ResultSet rso;
			FakturaHeader fh;
			int cn=0;
			while (rs.next()) {
				cn++;
				if(cn>pageSize) { list.hasMoreRows=true; cn--; break; }	//Minska cn eftersom vi använder den till att räkna fram näsat rads offset
				fh=new FakturaHeader();
				fh.faktnr=rs.getInt(1);
				fh.datum=rs.getDate(2);
				fh.fallDatum=rs.getDate(3);
				fh.t_attbetala=rs.getDouble(4);
				list.rader.add(fh);
				fh.orderMarken = getFakturaHeaderOrderMarken(con, rs.getInt(1));
			}
			list.nextRow = startRow+cn;
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return list;

	}

	private FakturaRow getNewFakturaRow(ResultSet rs) throws SQLException{
				FakturaRow fakturaRow = new FakturaRow();
				fakturaRow.pos = rs.getInt(7);
				fakturaRow.artnr = rs.getString(8);
				fakturaRow.namn = rs.getString(9);
				fakturaRow.text = rs.getString(10);
				fakturaRow.lev = rs.getDouble(11);
				fakturaRow.enh = rs.getString(12);
				fakturaRow.pris = rs.getDouble(13);
				fakturaRow.rab = rs.getDouble(14);
				fakturaRow.ordernr = rs.getInt(15);
				fakturaRow.summa=rs.getDouble(16);
				return fakturaRow;
	}


	public FakturaInfo getFakturaInfo(int faktnr) throws ServerErrorException, NotLoggedInException {
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
		ensureLoggedIn();

		FakturaInfo fakturaInfo = null;

		Connection con=null;
		try {
			con = sxadm.getConnection();
			//PreparedStatement stm = con.prepareStatement("select faktnr, datum, datum+ktid, t_attbetala, ordernr, marke from faktura1 where kundnr=? order by faktnr desc offset ? limit ?");
			PreparedStatement stm = con.prepareStatement( SELECT_FAKTURA +
					  " where f2.faktnr=? and f1.kundnr=? order by f2.pos");
			stm.setInt(1, faktnr);
			stm.setString(2, sxSession.getKundnr());
			ResultSet rs = stm.executeQuery();
			boolean firstRun=true;
			FakturaRow fakturaRow;
			while (rs.next()) {
				//Lägg till fakturainfo bara i första loopen
				if (firstRun) {
					fakturaInfo = new FakturaInfo();
					fakturaInfo.header.faktnr=rs.getInt(1);
					fakturaInfo.header.datum=rs.getDate(2);
					fakturaInfo.header.fallDatum=rs.getDate(3);
					fakturaInfo.header.t_attbetala=rs.getDouble(4);
				}
				fakturaInfo.rows.add(getNewFakturaRow(rs));
				firstRun=false;
			}

			// Bara om vi hittade en faktura på angiven kund
			if (fakturaInfo!=null) {
				fakturaInfo.header.orderMarken = getFakturaHeaderOrderMarken(con, fakturaInfo.header.faktnr);

				stm = con.prepareStatement("select faktnr, betdat, bet, betsatt from betjour where faktnr=? order by betdat");
				stm.setInt(1, fakturaInfo.header.faktnr);
				rs = stm.executeQuery();
				BetalningRow fakturaBetalning;
				while (rs.next()) {
					fakturaBetalning = new BetalningRow();
					fakturaBetalning.faktnr = rs.getInt(1);
					fakturaBetalning.betdat = rs.getDate(2);
					fakturaBetalning.summa = rs.getDouble(3);
					fakturaBetalning.betsatt = getBetalningBetsattString(rs.getString(4));
					fakturaInfo.betalningar.add(fakturaBetalning);
				}
			}
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return fakturaInfo;
	}


	//Get offertlista med start från offset om pageSize antal rader
	public OffertHeaderList getOffertHeaders(int startRow, int pageSize) throws ServerErrorException, NotLoggedInException {
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
		ensureLoggedIn();

		OffertHeaderList list = new OffertHeaderList();
		if (startRow<0) startRow=0;
		if (pageSize<1) pageSize=30;
		list.pageSize=pageSize;

		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement("select offertnr, datum, marke from offert1 where kundnr=? order by offertnr desc offset ? limit ?");
			stm.setString(1, sxSession.getKundnr());
			stm.setInt(2, startRow);
			stm.setInt(3, pageSize+1);//Limit till 1 mer rad än pagesize för att se om det finns fler rader efter sista
			ResultSet rs = stm.executeQuery();
			OffertHeader oh;
			int cn=0;
			while (rs.next()) {
				cn++;
				if(cn>pageSize) { list.hasMoreRows=true; cn--; break; }	//Minska cn eftersom vi använder den till att räkna fram näsat rads offset
				oh=new OffertHeader();
				oh.offertnr=rs.getInt(1);
				oh.datum=rs.getDate(2);
				oh.marke=rs.getString(3);
				list.rader.add(oh);
			}
			list.nextRow = startRow+cn;
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return list;
	}


	//private static final String SELECT_OFFERT = "select o2.offertnr, o1.datum, o1.marke, o2.pos, o2.artnr, o2.namn, o2.text, o2.best, o2.enh, o2.pris, o2.rab, o2.summa "+
	//													" from offert1 o1 left outer join offert2 o2 on o1.offertnr=o2.offertnr";

	public OffertInfo getOffertInfo(int offertnr) throws ServerErrorException, NotLoggedInException {
		OffertInfo info=null;
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
		ensureLoggedIn();
		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement(SELECT_OFFERT + " where o1.kundnr=? and o1.offertnr=?");
			stm.setString(1, sxSession.getKundnr());
			stm.setInt(2, offertnr);
			ResultSet rs = stm.executeQuery();
			OffertRow or;
			boolean firstRun=true;
			while (rs.next()) {
				if (firstRun) {
					info = new OffertInfo();
					OffertHeader oh = new OffertHeader();
					info.offertHeader = oh;
					oh.offertnr = rs.getInt(1);
					oh.datum=rs.getDate(2);
					oh.marke=rs.getString(3);
				}
				or=new OffertRow();
				or.offertnr=rs.getInt(1);
				or.pos = rs.getInt(4);
				or.artnr=rs.getString(5);
				or.namn=rs.getString(6);
				or.text=rs.getString(7);
				or.antal=rs.getDouble(8);
				or.enh=rs.getString(9);
				or.pris=rs.getDouble(10);
				or.rab=rs.getDouble(11);
				or.summa=rs.getDouble(12);
				info.artikelrader.add(or);
			}
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return info;
	}


	private boolean doesOrderstatusPermitChange(String status) {
		if ("Sparad".equals(status)) return true;
		return false;
	}
	private boolean isOrderDeletable(Connection con, int ordernr) throws SQLException{
		PreparedStatement stm = con.prepareStatement(
			"select o1.status, o1.lastdatum, count(s.stjid) "+
			" from order1 o1 left outer join order2 o2 on o1.ordernr=o2.ordernr and o2.artnr like '*%' "+
			" left outer join stjarnrad s on s.stjid=o2.stjid and s.stjid>0 and (s.bestdat is not null or s.finnsilager<>0) "+
			" where o1.ordernr=? "+
			" group by o1.status, o1.lastdatum"
		);
		stm.setInt(1, ordernr);
		ResultSet rs = stm.executeQuery();
		if(rs.next()) {
			if (doesOrderstatusPermitChange(rs.getString(1))) {
				if (rs.getInt(3)==0 && rs.getDate(2)==null) return true;
			}
		}
		return false;
	}

	private OrderHeader getNewOrderHeader(Connection con, ResultSet rs) throws SQLException{
		OrderHeader header = new OrderHeader();
		header.ordernr=rs.getInt(1);
		header.marke=rs.getString(2);
		header.datum=rs.getDate(3);
		header.lagernr=rs.getInt(4);
		header.status=rs.getString(5);
		header.levadr1=rs.getString(6);
		header.levadr2=rs.getString(7);
		header.levadr3=rs.getString(8);
		header.referens=rs.getString(9);
		header.direktlevnr=rs.getInt(10);
		header.isDeletable=isOrderDeletable(con, header.ordernr);
		return header;
	}

	private OrderHeader doGetOrderHeader(Connection con, int ordernr, String kundnr) throws SQLException{
		OrderHeader header=null;
		PreparedStatement stm = con.prepareStatement(
			SELECT_ORDERHEADER +
			" where ordernr=? and kundnr=?"
		);
		stm.setInt(1, ordernr);
		stm.setString(2, kundnr);
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
			header=getNewOrderHeader(con, rs);
		}
		return header;
	}

	private ArrayList<OrderHeader> doGetOrderHeaders(Connection con, String kundnr) throws SQLException{
		ArrayList<OrderHeader> headers = new ArrayList();
		OrderHeader header=null;
		PreparedStatement stm = con.prepareStatement(
			SELECT_ORDERHEADER +
			" where kundnr=? order by ordernr desc"
		);
		stm.setString(1, kundnr);
		ResultSet rs = stm.executeQuery();
		while (rs.next()) {
			headers.add(getNewOrderHeader(con, rs));
		}
		if (headers.size()==0) headers=null;
		return headers;
	}

	private ArrayList<OrderRow> doGetOrderRows(Connection con, int ordernr) throws SQLException {
		ArrayList<OrderRow> rows = new ArrayList();
		OrderRow row;
		PreparedStatement stm = con.prepareStatement(
			" select o2.pos, o2.artnr, o2.namn, o2.best, o2.enh, o2.pris, o2.rab, o2.summa, o2.text, o1.status, l.ilager-l.iorder+o2.best, s.bestdat, s.finnsilager, o2.ordernr "+
			" from order1 o1 left outer join order2 o2 on o1.ordernr=o2.ordernr "+
			" left outer join lager l on l.artnr=o2.artnr and l.lagernr=o1.lagernr and o2.artnr not like '*%' "+
			" left outer join stjarnrad s on s.stjid=o2.stjid and o2.stjid>0 "+
			" where o1.ordernr=? " +
			" order by o2.pos"
		);
		stm.setInt(1, ordernr);
		ResultSet rs = stm.executeQuery();
		//Om det inte finns några rader i ett tomt orderhuvud så kommer en tom rad att läggas till
		// Detta förhindrar vi genom att kolla så att det verkligen finns ett ordernummer i order2-tabellen
		//Vi kan inte kolla mot null, så ordernumret måste vara > 0 för att raden ska läggas till
		while(rs.next()) {
			if (rs.getInt(14)!=0) {	//order2.ordernr
				row = new OrderRow();
				row.ordernr=ordernr;
				row.pos = rs.getInt(1);
				row.artnr = rs.getString(2);
				row.namn = rs.getString(3);
				row.antal = rs.getDouble(4);
				row.enh = rs.getString(5);
				row.pris = rs.getDouble(6);
				row.rab = rs.getDouble(7);
				row.summa = rs.getDouble(8);
				row.text = rs.getString(9);
				row.tillgangligtAntal=rs.getDouble(11);
				row.isChangeable=false;
				if (doesOrderstatusPermitChange(rs.getString(10))) {
					if (rs.getDate(12)==null && rs.getInt(13)==0) row.isChangeable=true;
				}
				rows.add(row);
			}
		}
		return rows;
	}
	public OrderInfo getOrderInfo(int ordernr) throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
		OrderInfo orderInfo=new OrderInfo();
		Connection con=null;
		try {
			con = sxadm.getConnection();
			orderInfo.header = doGetOrderHeader(con, ordernr, sxSession.getKundnr());
			if (orderInfo.header==null) {
				orderInfo=null;
			} else {
				orderInfo.rows=doGetOrderRows(con, ordernr);
			}
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return orderInfo;
	}
	
	public OrderHeaderList getOrderHeaders() throws ServerErrorException, NotLoggedInException{
		ensureLoggedIn();
		OrderHeaderList list =new OrderHeaderList();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
		Connection con=null;
		try {
			con = sxadm.getConnection();
			list.rader = doGetOrderHeaders(con, sxSession.getKundnr());
			return list;
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
	}

	public void deleteOrder(int ordernr) throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
		Connection con=null;
		try {
			con = sxadm.getConnection();
			if (isOrderBehorigAndDeletable(con, ordernr)) localWebSupportBean.deleteOrder(ordernr);
			else throw new ServerErrorException("Kunde inte radera ordern. Antingen har du ingen behörighet eller så är ordern låst för ändringar.");
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
	}

	public void changeOrderRow(int ordernr, int pos, String antal) throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
		Connection con=null;
		double antalD;
		try {
			antalD = Double.parseDouble(antal);
		} catch (Exception e) { throw new ServerErrorException("Felaktigt antal.");}
		try {
			con = sxadm.getConnection();
			if (isOrderRowBehorigAndChangable(con, ordernr, pos))	localWebSupportBean.changeOrderRowAntal(ordernr, (short)pos, antalD);
			else throw new ServerErrorException("Kunde inte ändra raden. Antingen har du ingen behörighet eller så är ordern låst för ändringar.");
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
	}


	private boolean isOrderRowBehorigAndChangable(Connection con, int ordernr, int pos) throws SQLException {
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
		PreparedStatement stm = con.prepareStatement("select status, lastdatum from order1 where ordernr=? and kundnr=?");
		stm.setInt(1, ordernr);
		stm.setString(2, sxSession.getKundnr());
		ResultSet rs = stm.executeQuery();
		if (rs.next()) {
			if (rs.getDate(2)==null) {	//Om ordern inte är låst
				if (doesOrderstatusPermitChange(rs.getString(1))) {
					stm = con.prepareStatement("select o2.artnr, o2.stjid, s.bestdat from order2 o2 left outer join stjarnrad s on s.stjid=o2.stjid where o2.ordernr=? and o2.pos=?");
					stm.setInt(1, ordernr);
					stm.setInt(2, pos);
					rs = stm.executeQuery();
					if (rs.next()) {
						if (rs.getDate(3)==null) return true;
					}
				}
			}
		}
		
		return false;
	}
	private boolean isOrderBehorigAndDeletable(Connection con, int ordernr) throws SQLException {
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
		OrderHeader oh = doGetOrderHeader(con, ordernr, sxSession.getKundnr());
		if (oh!=null && oh.isDeletable) return true;
		return false;
	}


	public ArrayList<KundresRow> getKundresLista() throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

		ArrayList<KundresRow> arr = new ArrayList();
		KundresRow row;
		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement("select faktnr, tot, datum, falldat, pdat1, pdat2, pdat3, inkassodatum from kundres where kundnr=? order by faktnr");
			stm.setString(1, sxSession.getKundnr());
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				row = new KundresRow();
				row.kundnr = sxSession.getKundnr();
				row.faktnr=rs.getInt(1);
				row.tot = rs.getDouble(2);
				row.datum = rs.getDate(3);
				row.falldat = rs.getDate(4);
				row.pdat1 = rs.getDate(5);
				row.pdat2 = rs.getDate(6);
				row.pdat3 = rs.getDate(7);
				row.inkassodatum = rs.getDate(8);
				arr.add(row);
			}
			if (arr.isEmpty()) arr=null;
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return arr;
	}

	private String getBetalningBetsattString(String b) {
		if ("K".equals(b)) return  "Kontant";
		else if ("B".equals(b)) return  "BankGiro";
		else if ("P".equals(b)) return  "Plusgiro";
		else return b;
	}

	public BetalningList getBetalningList(int startRow, int pageSize) throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

		BetalningList list = new BetalningList();
		if (startRow<0) startRow=0;
		if (pageSize<1) pageSize=30;
		list.pageSize=pageSize;

		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement("select faktnr, betdat, bet, betsatt from betjour where kundnr=? order by betdat desc, faktnr desc offset ? limit ?");
			stm.setString(1, sxSession.getKundnr());
			stm.setInt(2, startRow);
			stm.setInt(3, pageSize+1);//Limit till 1 mer rad än pagesize för att se om det finns fler rader efter sista
			ResultSet rs = stm.executeQuery();
			BetalningRow br;
			int cn=0;
			while (rs.next()) {
				cn++;
				if(cn>pageSize) { list.hasMoreRows=true; cn--; break; }	//Minska cn eftersom vi använder den till att räkna fram näsat rads offset
				br=new BetalningRow();
				br.faktnr=rs.getInt(1);
				br.betdat=rs.getDate(2);
				br.summa=rs.getDouble(3);
				br.betsatt = getBetalningBetsattString(rs.getString(4));
				list.rader.add(br);
			}
			list.nextRow = startRow+cn;
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return list;
	}




	private UtlevRow getNewUtlevRow(ResultSet rs) throws SQLException{
		UtlevRow ur = new UtlevRow();
		ur.ordernr = rs.getInt(1);
		ur.orderdatum = rs.getDate(2);
		ur.marke = rs.getString(3);
		ur.referens = rs.getString(4);
		ur.lavadr1 = rs.getString(5);
		ur.lavadr2 = rs.getString(6);
		ur.lavadr3 = rs.getString(7);
		ur.lagernr = rs.getInt(8);
		ur.faktnr = rs.getInt(9);
		ur.faktdatum = rs.getDate(10);
		return ur;
	}



	public UtlevList getUtlevList(int startRow, int pageSize, String frdat, String tidat, String sokstr) throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

		UtlevList list = new UtlevList();
		if (startRow<0) startRow=0;
		if (pageSize<1) pageSize=30;

		String sokStrinSQL="";
		java.sql.Date frDate = null;
		java.sql.Date tiDate = null;

		try {
			if (frdat!=null && !frdat.isEmpty()) {
				frDate = new java.sql.Date(SXUtil.parseDateStringToDate(frdat).getTime());
			} else {
				frDate = new java.sql.Date((new Date().getTime()) - (1000*60*60*24*365)); // 1 år bakåt
			}
			sokStrinSQL = sokStrinSQL + " and u1.datum >= ?";
		} catch (ParseException e) { throw new ServerErrorException("Felaktigt angivet startdatum"); }

		try {
			if (tidat!=null && !tidat.isEmpty()) {
				tiDate = new java.sql.Date(SXUtil.parseDateStringToDate(tidat).getTime());
			} else {
				tiDate = new java.sql.Date(new java.util.Date().getTime());
			}
			sokStrinSQL = sokStrinSQL + " and u1.datum <= ?";
		} catch (ParseException e) { throw new ServerErrorException("Felaktigt angivet slutdatum"); }

		if (sokstr!=null && !sokstr.isEmpty()) {
			sokStrinSQL = sokStrinSQL + " and upper(u1.marke) like upper(?)";
		}

		list.frdat=SXUtil.getFormatDate(frDate);
		list.tidat=SXUtil.getFormatDate(tiDate);
		list.pageSize=pageSize;
		list.sokstr=sokstr;

		Connection con=null;
		try {
			int paramCn=1;
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement( SELECT_UTLEVROW +
				" where u1.kundnr=? " +sokStrinSQL +
				" order by u1.ordernr desc offset ? limit ?"
			);
			stm.setString(paramCn++, sxSession.getKundnr());
			stm.setDate(paramCn++, frDate);
			stm.setDate(paramCn++, tiDate);
			if (sokstr!=null && !sokstr.isEmpty()) {
				stm.setString(paramCn++, "%"+sokstr.replaceAll(" ", "%")+"%");
			}
			stm.setInt(paramCn++, startRow);
			stm.setInt(paramCn++, pageSize+1);//Limit till 1 mer rad än pagesize för att se om det finns fler rader efter sista
			ResultSet rs = stm.executeQuery();
			UtlevRow ur;
			int cn=0;
			while (rs.next()) {
				cn++;
				if(cn>pageSize) { list.hasMoreRows=true; cn--; break; }	//Minska cn eftersom vi använder den till att räkna fram näsat rads offset
				list.rader.add(getNewUtlevRow(rs));
			}
			list.nextRow = startRow+cn;
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return list;
	}

	public UtlevInfo getUtlevInfo(int  ordernr) throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

		UtlevInfo utlevInfo = new UtlevInfo();
		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement(SELECT_UTLEVROW +
					  " where u1.kundnr=? and u1.ordernr=?"
					  );
			stm.setString(1, sxSession.getKundnr());
			stm.setInt(2, ordernr);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				utlevInfo.utlev=getNewUtlevRow(rs);
			}
			stm = con.prepareStatement( SELECT_FAKTURA +
					  " where f2.ordernr=? and f1.kundnr=? order by f2.pos");
			stm.setInt(1, ordernr);
			stm.setString(2, sxSession.getKundnr());
			rs = stm.executeQuery();
			while (rs.next()) {
				utlevInfo.artikelrader.add(getNewFakturaRow(rs));
			}

		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return utlevInfo;
	}


	public StatArtikelList getStatArtikelList(int startRow, int pageSize, String frdat, String tidat, String sokstr, String orderBy) throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

		StatArtikelList list = new StatArtikelList();
		if (startRow<0) startRow=0;
		if (pageSize<1) pageSize=30;

		String sokStringSQL=null;
		String orderByStringSQL;
		java.sql.Date frDate = null;
		java.sql.Date tiDate = null;
		try {
			if (frdat!=null && !frdat.isEmpty()) {
				frDate = new java.sql.Date(SXUtil.parseDateStringToDate(frdat).getTime());
			} else {
				frDate = new java.sql.Date(SXUtil.addDate(new java.util.Date(), -365).getTime());
			}
		} catch (ParseException e) { throw new ServerErrorException("Felaktigt angivet startdatum"); }

		try {
			if (tidat!=null && !tidat.isEmpty()) {
				tiDate = new java.sql.Date(SXUtil.parseDateStringToDate(tidat).getTime());
			} else {
				tiDate = new java.sql.Date(new java.util.Date().getTime());
			}
		} catch (ParseException e) { throw new ServerErrorException("Felaktigt angivet slutdatum"); }

		if (sokstr!=null && !sokstr.isEmpty()) {
			sokStringSQL = " and upper(f2.artnr) like upper(?)";
		}

		if (StatArtikelList.ORDER_BY_ARTNR.equals(orderBy)) orderByStringSQL = "artnr";
		else if (StatArtikelList.ORDER_BY_ANTAL.equals(orderBy)) orderByStringSQL = "sum(f2.lev) desc";
		else if (StatArtikelList.ORDER_BY_KOPTILLFALLEN.equals(orderBy)) orderByStringSQL = "count(*) desc";
		else orderByStringSQL = "sum(f2.summa) desc";

		list.frdat=SXUtil.getFormatDate(frDate);
		list.tidat=SXUtil.getFormatDate(tiDate);
		list.pageSize=pageSize;
		list.sokstr=sokstr;
		list.orderBy=orderBy;

		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement(
				"select f2.artnr, max(f2.namn), sum(f2.lev), max(f2.enh), sum(f2.summa), count(*) "+
				" from faktura1 f1, faktura2 f2 "+
				" where f1.faktnr=f2.faktnr and f1.kundnr=? and f1.datum between ? and ? "+
				" and f2.artnr<>'*BONUS*' and f2.artnr<>'*RÄNTA*' "+
				" and f2.lev <> 0 " + (sokStringSQL != null ? sokStringSQL : "") +
				" group by f2.artnr "+
				" order by " + orderByStringSQL
			);
			stm.setString(1, sxSession.getKundnr());
			stm.setDate(2, frDate);
			stm.setDate(3, tiDate);
			if (sokStringSQL != null) stm.setString(4, sokstr+"%");

			ResultSet rs = stm.executeQuery();
			StatArtikelRow sr;
			int cn=0;
			while (rs.next()) {
				cn++;
				if(cn>pageSize) { list.hasMoreRows=true; cn--; break; }	//Minska cn eftersom vi använder den till att räkna fram näsat rads offset
				sr = new StatArtikelRow();
				sr.artnr = rs.getString(1);
				sr.namn = rs.getString(2);
				sr.antal = rs.getDouble(3);
				sr.enh = rs.getString(4);
				sr.summa = rs.getDouble(5);
				sr.koptillfallen=rs.getInt(6);
				list.rader.add(sr);
			}
			list.nextRow = startRow+cn;
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return list;

	}

	public ArrayList<StatArtikelFakturaRow> getStatArtikelFakturaRows(String artnr, String frdat, String tidat) throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

		ArrayList<StatArtikelFakturaRow> rader = new ArrayList();

		java.sql.Date frDate = null;
		java.sql.Date tiDate = null;
		try {
			if (frdat!=null && !frdat.isEmpty()) {
				frDate = new java.sql.Date(SXUtil.parseDateStringToDate(frdat).getTime());
			} else {
				frDate = new java.sql.Date(SXUtil.addDate(new java.util.Date(), -365).getTime());
			}
		} catch (ParseException e) { throw new ServerErrorException("Felaktigt angivet startdatum"); }

		try {
			if (tidat!=null && !tidat.isEmpty()) {
				tiDate = new java.sql.Date(SXUtil.parseDateStringToDate(tidat).getTime());
			} else {
				tiDate = new java.sql.Date(new java.util.Date().getTime());
			}
		} catch (ParseException e) { throw new ServerErrorException("Felaktigt angivet slutdatum"); }


		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement(
				"select f1.faktnr, f1.datum, f2.artnr, f2.namn, f2.lev, f2.enh, f2.pris, f2.rab, f2.summa, f2.ordernr "+
				" from faktura1 f1, faktura2 f2 "+
				" where f1.faktnr=f2.faktnr and f1.kundnr=? and f1.datum between ? and ? "+
				" and f2.lev <> 0 and f2.artnr=? " +
				" order by f1.faktnr desc, f2.pos"
			);
			stm.setString(1, sxSession.getKundnr());
			stm.setDate(2, frDate);
			stm.setDate(3, tiDate);
			stm.setString(4, artnr);

			ResultSet rs = stm.executeQuery();
			StatArtikelFakturaRow sr;
			while (rs.next()) {
				sr = new StatArtikelFakturaRow();
				sr.faktnr=rs.getInt(1);
				sr.datum=rs.getDate(2);
				sr.artnr=rs.getString(3);
				sr.namn=rs.getString(4);
				sr.lev=rs.getDouble(5);
				sr.enh=rs.getString(6);
				sr.pris=rs.getDouble(7);
				sr.rab=rs.getDouble(8);
				sr.summa=rs.getDouble(9);
				sr.ordernr=rs.getInt(10);
				rader.add(sr);
			}
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return rader;

	}

	public StatInkopHeader getStatInkopRows(int antalArBakat) throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());
		StatInkopHeader st = new StatInkopHeader();
		StatInkopRow row=null;
		Connection con=null;
		if (antalArBakat<0) antalArBakat=antalArBakat*(-1);
		if(antalArBakat > 100) antalArBakat=100;	//Bara så vi inte får in orinligt stora tal
		int startAr;
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		startAr = c.get(Calendar.YEAR) - antalArBakat;

		GoogleChartHandler gch = new GoogleChartHandler();
		gch.setEtiketterJanToDec();
		gch.setSize(350, 200);
		gch.clearSerier();




		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement(
				"select year(f1.datum), month(f1.datum), round(cast(sum(t_netto/1000) as numeric),0) from faktura1 f1 "+
				" where f1.kundnr=? and year(f1.datum)>=? "+
				" group by year(f1.datum), month(f1.datum) "+
				" order by year(f1.datum) desc, month(f1.datum)"
			);
			stm.setString(1, sxSession.getKundnr());
			stm.setInt(2, startAr);
			ResultSet rs = stm.executeQuery();
			int currentAr=0;


			while (rs.next()) {
				if (rs.getInt(1) != currentAr) {
					if (row!=null) { //Om vi har data för ett år så sparar vi det//Om vi har data för ett år så sparar vi det
						st.rows.add(row);
						gch.addSerie(""+row.ar, row.summa);
					}
					row=new StatInkopRow();
					currentAr=rs.getInt(1);
				}
				row.ar=rs.getInt(1);
				row.summa[rs.getInt(2)-1] = rs.getDouble(3);
			}
			st.rows.add(row);						//Lägg till den siata raden
			gch.addSerie(""+row.ar, row.summa);
			st.chartUrl=gch.getURL();
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));}
		catch (Exception ee) {ee.printStackTrace();throw(new ServerErrorException("Okänt fel"));}
		finally {
			try { con.close(); } catch (Exception e) {}
		}
		return st;
	}



	public void updateAnvandareUppgifter(AnvandareUppgifter a) throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement("update kundkontakt set namn=?, tel=?, mobil=?, fax=?, adr1=?, adr2=?, adr3=?, epost=?, ekonomi=?, info=? " +
					  " where kontaktid=?");
			stm.setString(1, a.kontaktNamn);
			stm.setString(2, a.kontaktTel);
			stm.setString(3, a.kontaktMobil);
			stm.setString(4, a.kontaktFax);
			stm.setString(5, a.kontaktAdr1);
			stm.setString(6, a.kontaktAdr2);
			stm.setString(7, a.kontaktAdr3);
			stm.setString(8, a.kontaktEpost);
			stm.setInt(9, a.kontaktEkonomiFlagga ? 1 : 0);
			stm.setInt(10, a.kontaktInfoFlagga ? 1 : 0);
			stm.setInt(11, sxSession.getKundKontaktId());
			if (stm.executeUpdate()==0) throw new ServerErrorException("Kunde inte spara");
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
	}

	public AnvandareUppgifter getAnvandareUppgifter() throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

		AnvandareUppgifter a =new AnvandareUppgifter();

		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement("select namn, tel, mobil, fax, adr1, adr2, adr3, epost, ekonomi, info " +
					  " from kundkontakt " +
					  " where kontaktid=?");
			stm.setInt(1, sxSession.getKundKontaktId());
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				a.kontaktNamn = rs.getString(1);
				a.kontaktTel = rs.getString(2);
				a.kontaktMobil = rs.getString(3);
				a.kontaktFax = rs.getString(4);
				a.kontaktAdr1 = rs.getString(5);
				a.kontaktAdr2 = rs.getString(6);
				a.kontaktAdr3 = rs.getString(7);
				a.kontaktEpost = rs.getString(8);
				a.kontaktEkonomiFlagga = rs.getInt(9) != 0 ? true : false;
				a.kontaktInfoFlagga = rs.getInt(10) != 0 ? true : false;
			} else {
				throw new ServerErrorException("Kan inte hitta uppgifter");
			}
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return a;
	}


	public void updateLosen(String nyttLosen, String upprepaLosen, String gammaltLosen) throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

		if (nyttLosen==null) throw new ServerErrorException("Inget lösenord angivet");
		if (nyttLosen.length() < 5) throw new ServerErrorException("Lösenordet är för kort");
		if (!nyttLosen.equals(upprepaLosen)) throw new ServerErrorException("Din upprepning av lösenordet stämmer inte");
		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement("update kundlogin set loginlosen=? " +
					  " where kontaktid=? and loginlosen=?");
			stm.setString(1, nyttLosen);
			stm.setInt(2, sxSession.getKundKontaktId());
			stm.setString(3, gammaltLosen);
			if (stm.executeUpdate()==0) throw new ServerErrorException("Gammalt lösenord är felaktigt");
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
	}


	/*
	public ArrayList<ArtGrpBilder> getBilderForArtGrpNodes(int grpid, int maxbilder) throws ServerErrorException {
		ArrayList<ArtGrpBilder> arr = new ArrayList();
		ArtGrpBilder bilder;
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement stmGrupper = con.prepareStatement("select grpid from artgrp where prevgrpid=?");
			stmGrupper.setInt(1, grpid);
			ResultSet rsGrupper = stmGrupper.executeQuery();
			PreparedStatement stm = con.prepareStatement(
							"select a.nummer, a.bildartnr from "+
							" ( "+
							" select min(akl.artnr) as artnr from "+
								" ( "+
								" select ag1.grpid as grpid1, ag2.grpid as grpid2, ag3.grpid as grpid3, ag4.grpid as grpid4 " +
								" from artgrp ag1  "+
									 " left outer join artgrp ag2 on ag2.prevgrpid = ag1.grpid "+
									" left outer join artgrp ag3 on ag3.prevgrpid = ag2.grpid "+
									" left outer join artgrp ag4 on ag4.prevgrpid = ag3.grpid "+
								" where ag1.grpid=? "+
								" ) g "+
								" left outer join artgrplank agl on agl.grpid=g.grpid1 or agl.grpid=g.grpid2 or agl.grpid=g.grpid3 or agl.grpid=g.grpid4 "+
								" left outer join artklaselank akl on akl.klasid=agl.klasid "+
								" group by akl.klasid "+
							" ) a1 "+
							" join artikel a on a.nummer=a1.artnr "+
							" order by random() limit ?"
					  );
			while (rsGrupper.next()) {
				bilder = new ArtGrpBilder();
				stm.setInt(1, rsGrupper.getInt(1));
				stm.setInt(2, maxbilder);
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					bilder.bilder.add((rs.getString(2)==null || rs.getString(2).isEmpty()) ? rs.getString(1) : rs.getString(2));
				}
				bilder.grpid=rsGrupper.getInt(1);
				arr.add(bilder);
			}
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
		return arr;

	}
	public void dummyFunctionToHoldSkelton() throws ServerErrorException, NotLoggedInException {
		ensureLoggedIn();
		SXSession sxSession = WebSupport.getSXSession(getThreadLocalRequest().getSession());

		Connection con=null;
		try {
			con = sxadm.getConnection();
			PreparedStatement stm = con.prepareStatement("");
			stm.setString(1, null);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
			}
		} catch (SQLException e) { e.printStackTrace(); throw(new ServerErrorException("Fel vid kommunikation med databasen"));
		} finally {
			try { con.close(); } catch (Exception e) {}
		}
	}
*/



}

