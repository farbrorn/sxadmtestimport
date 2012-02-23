/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.exceptions.SXEntityNotFoundException;
import se.saljex.sxserver.tables.TableFuppg;
import se.saljex.sxserver.tables.TableOffert1;
import se.saljex.sxserver.tables.TableOffert2;

/**
 *
 * @author Ulf
 */
public class HtmlOffertHandler {
	public static String getHtmlOffert (EntityManager em, int offertnr, boolean inkMoms, String logoUrl) throws SXEntityNotFoundException {
		return getHtmlOffert(em, offertnr, inkMoms, logoUrl, null, null, null);
	}
	
	public static String getHtmlOffert (EntityManager em, int offertnr, boolean inkMoms, String logoUrl, String headerHTML, String meddelandeHTML, String footerHTML) throws SXEntityNotFoundException {
		TableOffert1 o1 = em.find(TableOffert1.class, offertnr);
		if (o1 == null) { throw new SXEntityNotFoundException(); }
		
		TableFuppg fup = (TableFuppg)em.createNamedQuery("TableFuppg.findAll").getSingleResult();
		
		Query q = em.createNamedQuery("TableOffert2.findByOffertnr");
		q.setParameter("offertnr", offertnr);
		List<TableOffert2> o2List = q.getResultList();
		
		double momsProc;
		if (o1.getMoms() == 0) {
			momsProc = 0.0;
		} else if (o1.getMoms()==2) {
			momsProc = fup.getMoms2();
		} else if (o1.getMoms()==3) {
			momsProc = fup.getMoms3();
		} else {
			momsProc = fup.getMoms1();
		}
		
	
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body>");
		if (headerHTML!=null) sb.append(headerHTML);
	
		sb.append("<div style = \"width: 808px; border: 1px solid; border-radius: 4px; padding: 1em 4px 1em 4px;\">");
		sb.append("<table style=\"border-collapse: collapse; width: 100%; margin-bottom: 2em;\">");
			sb.append("<tr>");
				sb.append("<td>");
					if (logoUrl!=null) sb.append("<img src =\"" + logoUrl + "\">");
				sb.append("</td>");
				sb.append("<td>");
					sb.append("<span style=\"" + styleRubrik() + "\"> Offert " + offertnr + "</span>");			
				sb.append("</td>");
				sb.append("<td>");
					sb.append("<span style=\"" + styleLabel() + "\">");
						sb.append("Datum");
					sb.append("</span><br>");
					sb.append("<span style=\"" + styleValue() + "\">");
						sb.append(SXUtil.getFormatDate(o1.getDatum()));
					sb.append("</span>");						
				sb.append("</td>");
			sb.append("</tr>");
		sb.append("</table>");
		
		//Offerthuvud
		sb.append("<table style=\"border-collapse: collapse; width: 100%; margin-bottom: 2em;\">");
		sb.append("<tr>");
			sb.append("<td style=\"width: 50%; padding-right: 4px; vertical-align: top;\">");
				sb.append("<span style=\"" + styleLabel() + "\">");
					sb.append("Leveransadress");
				sb.append("</span>");
				sb.append("<div style=\"" + styleValue() + "\">");
					sb.append(SXUtil.toHtml(o1.getLevadr1()));
					sb.append("<br>");
					sb.append(SXUtil.toHtml(o1.getLevadr2()));
					sb.append("<br>");
					sb.append(SXUtil.toHtml(o1.getLevadr3()));
				sb.append("</div>");
			sb.append("</td>");
			sb.append("<td style=\"width: 50%; padding-left: 4px; vertical-align: top;\">");
				sb.append("<span style=\"" + styleLabel() + "\">");
					sb.append("Kund");
				sb.append("</span>");
				sb.append("<div style=\"" + styleValue() + "\">");
					sb.append(SXUtil.toHtml(o1.getNamn()));
					sb.append("<br>");
					sb.append(SXUtil.toHtml(o1.getAdr1()));
					sb.append("<br>");
					sb.append(SXUtil.toHtml(o1.getAdr2()));
					sb.append("<br>");
					sb.append(SXUtil.toHtml(o1.getAdr3()));
				sb.append("</div>");
			sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
			sb.append("<td style=\"width: 50%; padding-left: 4px; vertical-align: top;\">");
				sb.append("<span style=\"" + styleLabel() + "\">");
					sb.append("Vår referens");
				sb.append("</span>");
				sb.append("<div style=\"" + styleValue() + "\">");
					sb.append(SXUtil.toHtml(o1.getSaljare()));
				sb.append("</div>");
			sb.append("</td>");
			sb.append("<td style=\"width: 50%; padding-left: 4px; vertical-align: top;\">");
				sb.append("<span style=\"" + styleLabel() + "\">");
					sb.append("Er referens");
				sb.append("</span>");
				sb.append("<div style=\"" + styleValue() + "\">");
					sb.append(SXUtil.toHtml(o1.getReferens()));
				sb.append("</div>");
			sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
			sb.append("<td style=\"width: 50%; padding-left: 4px; vertical-align: top;\">");
				sb.append("<span style=\"" + styleLabel() + "\">");
					sb.append("Märke");
				sb.append("</span>");
				sb.append("<div style=\"" + styleValue() + "\">");
					sb.append(SXUtil.toHtml(o1.getMarke()));
				sb.append("</div>");
			sb.append("</td>");
			sb.append("<td>");
			sb.append("</td>");
		sb.append("</tr>");
			
		sb.append("</table>");

		sb.append("<div style=\"margin: 0px 2px 2em 2px\">");
			if (meddelandeHTML!=null) sb.append(meddelandeHTML + "<br>");
			
			if (inkMoms) {
				sb.append("Priserna i denna offert är inklusive moms.");
			} else {
				sb.append("Priserna i denna offert är exklusive moms.");
			}
		sb.append("</div>");

		sb.append("<table style=\"border-collapse: collapse; width: 100%; margin-bottom: 2em;\">");
			sb.append("<tr style=\"background-color: #dddddd; \">");
				sb.append("<td style=\"width: 8em; border-left: 1px solid; border-top: 1px solid; " + styleArtHead() + " \">Artikelnr</td><td style=\"width: 22em; border-top: 1px solid; " + styleArtHead() + "\">Benämningg</td>");
				sb.append("<td style=\"width: 6em; border-top: 1px solid; text-align: right; " + styleArtHead() + "\">Antal</td><td style=\"width: 4em; border-top: 1px solid; " + styleArtHead() + "\">Enhet</td>");
				sb.append("<td style=\"width: 6em; border-top: 1px solid; text-align: right; " + styleArtHead() + "\">Pris</td><td style=\"width: 4em; border-top: 1px solid; text-align: right; " + styleArtHead() + "\">%</td>");
				sb.append("<td style=\"width: 7em; border-top: 1px solid; border-right: 1px solid; text-align: right; " + styleArtHead() + "\">Summa</td>");
			sb.append("</tr>");
			boolean odd=false;
			double summa = 0.0;
			double tempSumma = 0.0;
			double tempPris = 0.0;
		for (TableOffert2 o2 : o2List) {
			sb.append("<tr style=\"" + isOdd(odd = !odd) + "\">");
				if(SXUtil.isEmpty(o2.getArtnr()) && !SXUtil.isEmpty(o2.getText())) {
					sb.append("<td colspan=\"6\" style=\"border-left: 1px solid;" + styleArtTD() + "\">");
						sb.append(SXUtil.toHtml(o2.getText()));
					sb.append("</td>");
					sb.append("<td style=\"border-right: 1px solid\"></td>");
				} else {
					sb.append("<td style=\"border-left: 1px solid; width: 10em; " + styleArtTD() + "\">");
						sb.append(SXUtil.toHtml(o2.getArtnr()));
					sb.append("</td>");		
					sb.append("<td style=\"width: 20em; " + styleArtTD() + "\">");
						sb.append(SXUtil.toHtml(o2.getNamn()));
					sb.append("</td>");		
					sb.append("<td style=\"width: 6em; text-align: right; " + styleArtTD() + "\">");
						sb.append(SXUtil.getFormatNumber(o2.getBest()));
					sb.append("</td>");		
					sb.append("<td style=\"width: 4em; " + styleArtTD() + "\">");
						sb.append(o2.getEnh()!=null ? SXUtil.toHtml(o2.getEnh()).toLowerCase() : ""); 
					sb.append("</td>");		
					sb.append("<td style=\"width: 6em; text-align: right; " + styleArtTD() + "\">");
						if (inkMoms) { 
							tempPris = SXUtil.getRoundedDecimal(o2.getPris() * (1+momsProc/100));
						} else {
							tempPris = SXUtil.getRoundedDecimal(o2.getPris());
						}
						sb.append(SXUtil.getFormatNumber(tempPris));
					sb.append("</td>");		
					sb.append("<td style=\"width: 4em; text-align: right; " + styleArtTD() + "\">");
						sb.append(SXUtil.getFormatNumber(o2.getRab(),0));
					sb.append("</td>");		
					sb.append("<td style=\"width: 8em; text-align: right; border-right: 1px solid; " + styleArtTD() + "\">");
						if (inkMoms) { 
							tempSumma = SXUtil.getRoundedDecimal(tempPris * (1-o2.getRab()/100) * o2.getBest());
						} else {
							tempSumma = SXUtil.getRoundedDecimal(o2.getSumma());
						}
						sb.append(SXUtil.getFormatNumber(tempSumma));
						summa += tempSumma;
					sb.append("</td>");		
				}
			sb.append("</tr>");
		}
		
		//Total
		sb.append("<tr><td colspan=\"4\" style=\"border-top: 1px solid;\"></td><td colspan=\"2\" style=\"border-left: 1px solid; font-weight: bold;" + styleArtTD() + "\">");
		if (inkMoms) { 
			sb.append("Summa");
		} else {
			sb.append("Summa exkl. moms");			
		}
		sb.append("</td><td style=\"font-weight: bolder; font-size: 1.1em; text-align: right; border-right: 1px solid; " + styleArtTD() + "\">" + SXUtil.getFormatNumber(summa) + "</td></tr>");
		
		//Moms
		sb.append("<tr><td colspan=\"4\"></td><td colspan=\"2\" style=\"border-left: 1px solid; border-bottom: 1px solid; " + styleArtTD() + "\">");
		if (inkMoms) { 
			sb.append("Moms ingår med");
		} else {
			sb.append("Moms tillkommer med");			
		}
		double momsSumma = inkMoms ? summa-summa/(1+momsProc/100) : summa * (momsProc/100);
		sb.append("</td><td style=\"border-right: 1px solid; border-bottom: 1px solid; text-align: right; " + styleArtTD() + "\">" + SXUtil.getFormatNumber(momsSumma) + "</td></tr>");
		
		
		sb.append("</table>");
		
		
		
		
		
		//Offertfot
//		sb.append("<div style=\"padding: 4px; border: 1px solid black; width: 100%; margin-top:1em; \">");
		sb.append("<table style=\"border-collapse: collapse; width: 100%; border: 1px solid; \">");
			sb.append("<tr>");
				sb.append("<td style=\""+styleFot()+"; width:33%;\">");
					sb.append(SXUtil.toHtml(fup.getNamn()) + "<br>" + SXUtil.toHtml(fup.getAdr1()) + "<br>" + SXUtil.toHtml(fup.getAdr2()) + "<br>" + SXUtil.toHtml(fup.getAdr3()));
				sb.append("</td>");
				sb.append("<td style=\""+styleFot()+"; width:33%;\">");
					sb.append("Tel:  " + SXUtil.toHtml(fup.getTel()) + "<br>Fax: " + SXUtil.toHtml(fup.getFax()) + "<br>E-post: " + SXUtil.toHtml(fup.getEmail()) + "<br>Webbplats: " + SXUtil.toHtml(fup.getHemsida()) );
				sb.append("</td>");
				sb.append("<td style=\""+styleFot()+"; width:33%;\">");
					sb.append("Organisationsnummer: " + SXUtil.toHtml(fup.getRegnr()));
				sb.append("</td>");
			sb.append("</tr>");
		sb.append("</table>");
//		sb.append("</div>");
		
		sb.append("</div>");
		if (footerHTML!=null) sb.append(footerHTML);
		sb.append("</body></html>");
		return sb.toString();
	}
	
	private static String styleRubrik() {return "font-size: 1.4em; font-weight: bolder;"; }
	private static String isOdd(boolean odd) { if (odd) return ""; else return "background-color: #fbfbfb;"; }
	private static String styleLabel() { return "font-size: 0.6em; font-weight: bold; padding: 0px 2px 0px 2px; "; }
	private static String styleValue() { return "background-color: #fbfbfb; padding: 0px 2px 0px 2px; margin: 0px 0px 0.6em 0px;"; }
	private static String styleFot() { return "font-size: 0.5em; border-collapse: collapse; padding: 4px 4px 4px 4px;"; }
	private static String styleArtHead() { return "font-size: 0.5em; font-weight: bold; padding: 0px 2px 0px 2px"; }
	private static String styleArtTD() { return "padding: 0px 2px 0px 2px"; }
	
	
}
