/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxserver.tables.TableArtikel;
import se.saljex.sxserver.tables.TableArtgrplank;
import se.saljex.sxserver.tables.TableArtklase;
import se.saljex.sxserver.tables.TableLagerPK;
import se.saljex.sxserver.tables.TableArtgrp;
import se.saljex.sxserver.tables.TableArtklaselank;
import se.saljex.sxserver.tables.TableLager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author ulf
 */
public class WebArtikelUpdater {
	private Connection con;
	
	private EntityManager em;
	
	SimpleOrderHandler sord = null;
	
	
	public WebArtikelUpdater(EntityManager e, Connection c) {
			em = e;
			con = c;
	}

	
	public int updateWArt() throws SQLException {
		ServerUtil.log("Börjar uppdatera WArt");
		Statement s = con.createStatement();
		s.executeUpdate("delete from wartikelup");
		PreparedStatement p = con.prepareStatement("insert into wartikelup (nummer, namn, enhet, utpris, staf_pris1, staf_pris2, staf_pris1_dat, staf_pris2_dat" +
			", staf_antal1, staf_antal2, bestnr, rabkod, kod1, prisdatum, refnr, vikt, volym, minsaljpack, forpack, rsk, enummer, fraktvillkor" +
			", prisgiltighetstid, utgattdatum, katnamn, bildartnr, maxlager, ilager)" +
			" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		List<TableArtikel> lart = em.createNamedQuery("TableArtikel.findAllInArtklaselank").getResultList();
		for (TableArtikel art : lart) {
			TableLager lag = (TableLager)em.find(TableLager.class, new TableLagerPK(art.getNummer(),(short)0));	 //Sätt till lager 0
			p.setString(1, art.getNummer());
			p.setString(2, art.getNamn());
			p.setString(3, art.getEnhet());
			p.setDouble(4, art.getUtpris());
			p.setDouble(5, art.getStafPris1());
			p.setDouble(6, art.getStafPris2());
			p.setDate(7, SXUtil.getSQLDate(art.getStafPris1Dat()));
			p.setDate(8, SXUtil.getSQLDate(art.getStafPris2Dat()));
			p.setDouble(9, art.getStafAntal1());
			p.setDouble(10, art.getStafAntal2());
			p.setString(11, art.getBestnr());
			p.setString(12, art.getRabkod());
			p.setString(13, art.getKod1());
			p.setDate(14, SXUtil.getSQLDate(art.getPrisdatum()));
			p.setString(15, art.getRefnr());
			p.setDouble(16, art.getVikt());
			p.setDouble(17, art.getVolym());
			p.setDouble(18, art.getMinsaljpack());
			p.setDouble(19, art.getForpack());
			p.setString(20, art.getRsk());
			p.setString(21, art.getEnummer());
			p.setShort(22, art.getFraktvillkor());
			p.setInt(23, art.getPrisgiltighetstid());
			p.setDate(24, SXUtil.getSQLDate(art.getUtgattdatum()));
			p.setString(25, art.getKatnamn());
			p.setString(26, art.getBildartnr());
			if (lag != null) {
				p.setDouble(27, lag.getMaxlager());
				p.setDouble(28, lag.getIlager() - lag.getIorder());
			} else {
				p.setDouble(27, 0.0);
				p.setDouble(28, 0.0);
			}
			p.execute();
		}
		s.executeUpdate("delete from wartikel");
		int antalRader = s.executeUpdate("insert into wartikel (nummer, namn, enhet, utpris, staf_pris1, staf_pris2, staf_pris1_dat, staf_pris2_dat" +
			", staf_antal1, staf_antal2, bestnr, rabkod, kod1, prisdatum, refnr, vikt, volym, minsaljpack, forpack, rsk, enummer, fraktvillkor" +
			", prisgiltighetstid, utgattdatum, katnamn, bildartnr, maxlager, ilager) select " +
			" nummer, namn, enhet, utpris, staf_pris1, staf_pris2, staf_pris1_dat, staf_pris2_dat" +
			", staf_antal1, staf_antal2, bestnr, rabkod, kod1, prisdatum, refnr, vikt, volym, minsaljpack, forpack, rsk, enummer, fraktvillkor" +
			", prisgiltighetstid, utgattdatum, katnamn, bildartnr, maxlager, ilager from wartikelup");
		ServerUtil.log("Update WArt färdigt! Antal rader: " + antalRader);
		return antalRader;
	}

	public int updateWArtGrp() throws SQLException {
		ServerUtil.log("Börjar uppdatera WArtGrp");
		Statement s = con.createStatement();
		s.executeUpdate("delete from wartgrpup");
		PreparedStatement p = con.prepareStatement("insert into wartgrpup (grpid, prevgrpid, rubrik, infourl, sortorder, text, html)" +
			" values (?,?,?,?,?,?,?)");
		List<TableArtgrp> la = em.createNamedQuery("TableArtgrp.findAll").getResultList();
		for (TableArtgrp a : la) {
			p.setInt(1, a.getGrpid());
			p.setInt(2, a.getPrevgrpid());
			p.setString(3, a.getRubrik());
			p.setString(4, a.getInfourl());
			p.setInt(5, a.getSortorder());
			p.setString(6, a.getText());
			p.setString(7, a.getHtml());
			p.execute();
		}
		s.executeUpdate("delete from wartgrp");
		int antalRader = s.executeUpdate("insert into wartgrp (grpid, prevgrpid, rubrik, infourl, sortorder, text, html) select grpid, prevgrpid, rubrik, infourl, sortorder, text, html from wartgrpup");
		ServerUtil.log("Update WArtGrp färdigt! Antal rader: " + antalRader);
		return antalRader;
	}


	public int updateWArtGrpLank() throws SQLException {
		ServerUtil.log("Börjar uppdatera WArtGrpLank");
		Statement s = con.createStatement();
		s.executeUpdate("delete from wartgrplankup");
		PreparedStatement p = con.prepareStatement("insert into wartgrplankup (grpid, klasid, sortorder)" +
			" values (?,?,?)");
		List<TableArtgrplank> la = em.createNamedQuery("TableArtgrplank.findAll").getResultList();
		for (TableArtgrplank a : la) {
			p.setInt(1, a.getTableArtgrplankPK().getGrpid());
			p.setInt(2, a.getTableArtgrplankPK().getKlasid());
			p.setInt(3, a.getSortorder());
			p.execute();
		}
		s.executeUpdate("delete from wartgrplank");
		int antalRader = s.executeUpdate("insert into wartgrplank (grpid, klasid, sortorder) select grpid, klasid, sortorder from wartgrplankup");
		ServerUtil.log("Update WArtGrpLank färdigt! Antal rader: " + antalRader);
		return antalRader;
	}

	public int updateWArtKlase() throws SQLException {
		ServerUtil.log("Börjar uppdatera WArtKlase");
		Statement s = con.createStatement();
		s.executeUpdate("delete from wartklaseup");
		PreparedStatement p = con.prepareStatement("insert into wartklaseup (klasid, rubrik, infourl, fraktvillkor, text, html)" +
			" values (?,?,?,?,?,?)");
		List<TableArtklase> la = em.createNamedQuery("TableArtklase.findAll").getResultList();
		for (TableArtklase a : la) {
			p.setInt(1, a.getKlasid());
			p.setString(2, a.getRubrik());
			p.setString(3, a.getInfourl());
			p.setString(4, a.getFraktvillkor());
			p.setString(5, a.getText());
			p.setString(6, a.getHtml());
			p.execute();
		}
		s.executeUpdate("delete from wartklase");
		int antalRader = s.executeUpdate("insert into wartklase (klasid, rubrik, infourl, fraktvillkor, text, html) select klasid, rubrik, infourl, fraktvillkor, text, html from wartklaseup");
		ServerUtil.log("Update WArtKlase färdigt! Antal rader: " + antalRader);
		return antalRader;
	}
	
	public int updateWArtKlaseLank() throws SQLException {
		ServerUtil.log("Börjar uppdatera WArtKlaseLank");
		Statement s = con.createStatement();
		s.executeUpdate("delete from wartklaselankup");
		PreparedStatement p = con.prepareStatement("insert into wartklaselankup (klasid, artnr, sortorder)" +
			" values (?,?,?)");
   	List<TableArtklaselank> la = em.createNamedQuery("TableArtklaselank.findAll").getResultList();
		for (TableArtklaselank a : la) {
			p.setInt(1, a.getTableArtklaselankPK().getKlasid());
			p.setString(2, a.getTableArtklaselankPK().getArtnr());
			p.setInt(3, a.getSortorder());
			p.execute();
		}
		s.executeUpdate("delete from wartklaselank");
		int antalRader = s.executeUpdate("insert into wartklaselank (klasid, artnr, sortorder) select klasid, artnr, sortorder from wartklaselankup");
		ServerUtil.log("Update WArtKlaseLank färdigt! Antal rader: " + antalRader);
		return antalRader;
	}


}

