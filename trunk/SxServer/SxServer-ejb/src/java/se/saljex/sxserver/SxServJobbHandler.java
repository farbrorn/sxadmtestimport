/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import java.util.Date;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import se.saljex.sxlibrary.SXConstant;
import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.exceptions.SXEntityNotFoundException;
import se.saljex.sxserver.tables.TableFaktura1;
import se.saljex.sxserver.tables.TableOffert1;
import se.saljex.sxserver.tables.TableSxservjobb;

/**
 *
 * @author Ulf
 */
public  class SxServJobbHandler {

	public static void sendOffertEpost(EntityManager em, String anvandare, int id, String epost) throws SXEntityNotFoundException{
		validateEpost(epost);
		TableOffert1 o1 = em.find(TableOffert1.class, id);
		if (o1==null) throw new SXEntityNotFoundException("Offertnr " + id + " saknas, och kan inte skickas.");

		TableSxservjobb jobb = new TableSxservjobb();
		jobb.setAnvandare(anvandare);
		jobb.setUppgift(SXConstant.SERVJOBB_UPPGIFT_SAND);
		jobb.setSandsatt(SXConstant.SERVJOBB_SANDSATT_EPOST);
		jobb.setDokumenttyp(SXConstant.SERVJOBB_DOKUMENTTYP_OFFERT);
		jobb.setEpost(epost);
		jobb.setExternidint(id);
		jobb.setSkapad(new java.sql.Date(new Date().getTime()));
		persistServJobb(em, jobb);
	}

	public static void sendFakturaEpost(EntityManager em, String anvandare, int id, String epost) throws SXEntityNotFoundException{
		validateEpost(epost);
		TableFaktura1 f1 = em.find(TableFaktura1.class, id);
		if (f1==null) throw new SXEntityNotFoundException("Fakturanr " + id + " saknas, och kan inte skickas.");

		TableSxservjobb jobb = new TableSxservjobb();
		jobb.setAnvandare(anvandare);
		jobb.setUppgift(SXConstant.SERVJOBB_UPPGIFT_SAND);
		jobb.setSandsatt(SXConstant.SERVJOBB_SANDSATT_EPOST);
		jobb.setDokumenttyp(SXConstant.SERVJOBB_DOKUMENTTYP_FAKTURA);
		jobb.setEpost(epost);
		jobb.setExternidint(id);
		jobb.setSkapad(new java.sql.Date(new Date().getTime()));
		persistServJobb(em, jobb);
	}

	private static void validateEpost(String epost) throws SXEntityNotFoundException {
		String[] terms = ((String)epost).split(",");
		for (String term : terms) {
			if (!SXUtil.isValidEmailAddress(term)) throw new SXEntityNotFoundException("Ogiltig e-postadress: " + term);
		}

	}

	public static void persistServJobb(EntityManager em, TableSxservjobb jobb) throws SXEntityNotFoundException {
		Integer jobbId=null;

		boolean error=false;
		for (int cn=1; cn <= 10; cn++) {
			try {
				error = false;
				jobbId = (Integer)em.createNativeQuery("select max(jobbid) from sxservjobb").getSingleResult();
				if (jobbId==null) jobbId=0;
				jobbId++;
				jobb.setJobbid(jobbId);
				em.persist(jobb);
			} catch (EntityExistsException e) { error = true; continue; }
			break;
		}
		if (error) throw new SXEntityNotFoundException("Kan inte skapa jobb. jobbid Entity exists.");

	}

}
