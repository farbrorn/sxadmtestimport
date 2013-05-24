/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.rapporter;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Ulf
 */
public class KatalogHandler {

	
	public static Katalog getKatalog(Connection con, int rootId) throws SQLException {
		return getKatalog(con, rootId, false, null,null, null);
	}
	
	public static Katalog getKatalog(Connection con, int rootId, boolean onlyGroups) throws SQLException {
		return getKatalog(con, rootId, onlyGroups, null,null, null);
	}
	public static Katalog getKatalog(Connection con, int rootId, boolean onlyGroups, ArrayList<Integer> excludeGroups) throws SQLException {
		return getKatalog(con, rootId, onlyGroups, excludeGroups,null, null);
	}
	public static Katalog getKatalog(Connection con, int rootId, boolean onlyGroups, ArrayList<Integer> excludeGroups, Integer lagernr) throws SQLException {
		return getKatalog(con, rootId, onlyGroups, excludeGroups,lagernr, null);
	}
	
	public static Katalog getKatalog(Connection con, int rootId, boolean onlyGroups, ArrayList<Integer> excludeGroups, Integer lagernr, String lev) throws SQLException {
		Katalog kat = new Katalog();
		String sqlExcludeGroups=null;
		if (excludeGroups!=null) {
			for (Integer i : excludeGroups) {
				if (i!=null) {
					if (sqlExcludeGroups==null) sqlExcludeGroups="";
					if (!sqlExcludeGroups.isEmpty()) sqlExcludeGroups = sqlExcludeGroups + ",";
					sqlExcludeGroups = sqlExcludeGroups+i.toString();
				}
			}
		}
		fillGrupp(con, kat, rootId, 0, onlyGroups, sqlExcludeGroups, lagernr, lev);
		
		if (lagernr != null && lagernr >= 0) {
			
		}

		return kat;
	}
	
	public static int fillGrupp(Connection con, Katalog kat, int grpId, int treeLevel, boolean onlyGroups, String sqlExcludeGroups, Integer lagernr, String lev) throws SQLException {
		String artOn = "(a.lev=? or 1=1) ";
		if (lev!=null) artOn="a.lev=? ";
		String q_select = 
		"select g.grpid, g.prevgrpid, g.rubrik, g.text, g.infourl, g.sortorder, k.klasid, gl.sortorder, k.rubrik, k.text, " +
		" k.infourl, kl.sortorder, a.nummer, a.namn, a.enhet, a.utpris, a.staf_pris1, a.staf_pris2, a.staf_antal1, a.staf_antal2, " +
		" a.rabkod, a.kod1, a.prisdatum, a.vikt, a.volym, a.forpack, a.kop_pack, a.inprisny, 0, 0, " +
		" a.utprisnydat, a.rsk, a.enummer, a.fraktvillkor, a.dagspris, a.utgattdatum,  a.minsaljpack, a.katnamn, a.bildartnr , l.maxlager, a.refnr, a.bestnr" +
		" from artgrp g " +
		" left outer join artgrplank gl on gl.grpid = g.grpid " +
		" left outer join artklase k on k.klasid=gl.klasid " +
		" left outer join artklaselank kl on kl.klasid = k.klasid " +
		" left outer join artikel a on  a.nummer = kl.artnr and "  + artOn +
		" left outer join lager l on l.artnr=kl.artnr and l.lagernr=? ";

		String q_where_1 = 
		" where (g.prevgrpid = ? )";
		String q_where_2 = 
		" where (g.grpid = ?)";


		String q_orderby = 
		" order by g.sortorder, g.grpid, gl.sortorder, gl.klasid, kl.sortorder, kl.artnr";

		PreparedStatement stm;
		String q_groups;
		int antalArtiklarTotaltDennaRootGrupp = 0;
		int antalArtiklarDennaGrupp = 0;
		int antalArtiklarSubGrupp = 0;
		int antalArtiklarDennaKlase = 0;
		if (sqlExcludeGroups!=null) q_groups = " and g.grpid not in (" + sqlExcludeGroups + ") "; else q_groups="";
		if (treeLevel==0 && grpId!=0) {
			
			stm = con.prepareStatement(q_select + q_where_2 + q_groups + q_orderby);
			stm.setInt(3, grpId);
		} else {
			stm = con.prepareStatement(q_select + q_where_1 + q_groups + q_orderby);
			stm.setInt(3, grpId);
		}
		stm.setString(1, lev);
		stm.setInt(2, lagernr==null ? -1 : lagernr);
		
		KatalogGrupp grupp = null;
		
		
		if (treeLevel==0 && grpId==0) { //Här har vi sökt efter rootgruppen, som inte går att slå upp i databasen, och därför sätter vi värdena manuellt.
			grupp = new KatalogGrupp();
			grupp.setGrpId(0);
			grupp.setPrevGrpId(0);
			grupp.setSortOrder(0);
			grupp.setTreeLevel(0);
			grupp.setRubrik("Huvudsortiment");
			kat.getGrupper().add(grupp);
			treeLevel++;
			grupp = null;
		}

		ResultSet rs = stm.executeQuery();
		KatalogKlase klase = null;
		KatalogArtikel artikel = null;

		while (rs.next()) {
			if (grupp==null || grupp.getGrpId() != rs.getInt(1)) {
				if (grupp != null && antalArtiklarSubGrupp <= 0 && antalArtiklarDennaGrupp <= 0 && lagernr != null) kat.getGrupper().remove(grupp);
				if (klase!=null && antalArtiklarDennaKlase <=0 && lagernr != null) grupp.getKlasar().remove(klase);
				
				antalArtiklarSubGrupp=0;					
				antalArtiklarDennaGrupp=0;
				antalArtiklarDennaKlase=0;
				//Nollställ klasen
				klase=null;
				
				grupp = new KatalogGrupp();
				kat.getGrupper().add(grupp);
				grupp.setGrpId(rs.getInt(1));
				grupp.setInfoUrl(rs.getString(5));
				grupp.setPrevGrpId(rs.getInt(2));
				grupp.setRubrik(rs.getString(3));
				grupp.setSortOrder(rs.getInt(6));
				grupp.setText(rs.getString(5));
				grupp.setTreeLevel(treeLevel);
				
				antalArtiklarSubGrupp = fillGrupp(con, kat, grupp.getGrpId(), treeLevel+1, onlyGroups, sqlExcludeGroups, lagernr, lev);
				antalArtiklarTotaltDennaRootGrupp += antalArtiklarSubGrupp;
			}
			if ((klase==null || klase.getId() != rs.getInt(7)) && !onlyGroups) {
				if (klase!=null && antalArtiklarDennaKlase <=0 && lagernr != null) grupp.getKlasar().remove(klase);
				antalArtiklarDennaKlase=0;
				klase=null;		//Nollställ under alla händelser
				if (rs.getInt(7) > 0 ) { //Om vi har en klase
					klase = new KatalogKlase();
					grupp.getKlasar().add(klase);
					klase.setId(rs.getInt(7));
					klase.setInfourl(rs.getString(11));
					klase.setRubrik(rs.getString(9));
					klase.setSortOrder(rs.getInt(8));
					klase.setText(rs.getString(10));

				}
			}
			if(rs.getString(13)!=null && !onlyGroups && (lagernr==null || rs.getDouble(40) > 0)) {	//Om vi har artikel
				artikel = new KatalogArtikel();
				klase.getArtiklar().add(artikel);
				antalArtiklarDennaGrupp++;
				antalArtiklarTotaltDennaRootGrupp++;
				antalArtiklarDennaKlase++;
				artikel.setAntalStaf1(rs.getDouble(19));
				artikel.setAntalStaf2(rs.getDouble(20));
				artikel.setArtnr(rs.getString(13));
				if (rs.getString(39)!= null && rs.getString(39).length()>0)	artikel.setBildArtNr(rs.getString(39)); else artikel.setBildArtNr(rs.getString(13));
				artikel.setEnhet(convertEnhet2Readable(rs.getString(15)));
				artikel.setForpackning(rs.getDouble(26));
				artikel.setFraktvillkor(rs.getInt(34));
				artikel.setKatalogtext(rs.getString(38));
				artikel.setMinSaljpack(rs.getDouble(37));
				artikel.setPris(rs.getDouble(16));
				artikel.setPrisStaf1(rs.getDouble(17));
				artikel.setPrisStaf2(rs.getDouble(18));
				artikel.setRabkod(rs.getString(21));
				artikel.setKod1(rs.getString(22));
				artikel.setSortOrder(rs.getInt(12));			
				artikel.setUtgattdatum(rs.getDate(36));		
				artikel.setMaxlager(rs.getDouble(40));
				artikel.setRefnr(rs.getString(41));
				artikel.setBestnr(rs.getString(42));
				artikel.setRsk(rs.getString(32));
				artikel.setEnr(rs.getString(33));
				
			}

		}
		if (grupp != null && antalArtiklarSubGrupp <= 0 && antalArtiklarDennaGrupp <= 0 && lagernr != null) kat.getGrupper().remove(grupp);
		if (klase!=null && antalArtiklarDennaKlase <=0 && lagernr != null) grupp.getKlasar().remove(klase);
		
		return antalArtiklarTotaltDennaRootGrupp;
		
	}


	public static String convertEnhet2Readable(String enh) {
		if (enh==null) return null;
		return enh.toLowerCase();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
