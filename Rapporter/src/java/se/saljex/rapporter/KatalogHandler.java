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
private final static String q_select = 
"select g.grpid, g.prevgrpid, g.rubrik, g.text, g.infourl, g.sortorder, k.klasid, gl.sortorder, k.rubrik, k.text, " +
" k.infourl, kl.sortorder, a.nummer, a.namn, a.enhet, a.utpris, a.staf_pris1, a.staf_pris2, a.staf_antal1, a.staf_antal2, " +
" a.rabkod, a.kod1, a.prisdatum, a.vikt, a.volym, a.forpack, a.kop_pack, a.inprisny, 0, 0, " +
" a.utprisnydat, a.rsk, a.enummer, a.fraktvillkor, a.dagspris, a.utgattdatum,  a.minsaljpack, a.katnamn, a.bildartnr " +
" from artgrp g " +
" left outer join artgrplank gl on gl.grpid = g.grpid " +
" left outer join artklase k on k.klasid=gl.klasid " +
" left outer join artklaselank kl on kl.klasid = k.klasid " +
" left outer join artikel a on  a.nummer = kl.artnr ";

private final static String q_where_1 = 
" where (g.prevgrpid = ? )";
private final static String q_where_2 = 
" where (g.grpid = ?)";


private final static String q_orderby = 
" order by g.sortorder, g.grpid, gl.sortorder, gl.klasid, kl.sortorder, kl.artnr";

	
	public static Katalog getKatalog(Connection con, int rootId) throws SQLException {
		return getKatalog(con, rootId, false, null);
	}
	
	public static Katalog getKatalog(Connection con, int rootId, boolean onlyGroups) throws SQLException {
		return getKatalog(con, rootId, onlyGroups, null);
	}
	
	public static Katalog getKatalog(Connection con, int rootId, boolean onlyGroups, ArrayList<Integer> excludeGroups) throws SQLException {
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
		fillGrupp(con, kat, rootId, 0, onlyGroups, sqlExcludeGroups);

		return kat;
	}
	
	public static void fillGrupp(Connection con, Katalog kat, int grpId, int treeLevel, boolean onlyGroups, String sqlExcludeGroups) throws SQLException {
		PreparedStatement stm;
		String q_groups;
		if (sqlExcludeGroups!=null) q_groups = " and g.grpid not in (" + sqlExcludeGroups + ") "; else q_groups="";
		if (treeLevel==0 && grpId!=0) {
			
			stm = con.prepareStatement(q_select + q_where_2 + q_groups + q_orderby);
			stm.setInt(1, grpId);
		} else {
			stm = con.prepareStatement(q_select + q_where_1 + q_groups + q_orderby);
			stm.setInt(1, grpId);
		}
		
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
				fillGrupp(con, kat, grupp.getGrpId(), treeLevel+1, onlyGroups, sqlExcludeGroups);
			}
			if ((klase==null || klase.getId() != rs.getInt(7)) && !onlyGroups) {
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
			if(rs.getString(13)!=null && !onlyGroups) {	//Om vi har artikel
				artikel = new KatalogArtikel();
				klase.getArtiklar().add(artikel);
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
				artikel.setSortOrder(rs.getInt(12));			
				artikel.setUtgattdatum(rs.getDate(36));			
			}

		}
		
	}


	public static String convertEnhet2Readable(String enh) {
		if (enh==null) return null;
		return enh.toLowerCase();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
