/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.webadm.server;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import se.saljex.sxlibrary.SxServerMainRemote;
import se.saljex.sxserver.LocalWebSupportLocal;
import se.saljex.sxserver.SxServerMainLocal;

/**
 *
 * @author Ulf
 */
public class RequestHandler {
	public static final String SxServMainLocal = "SxServMainLocal";
	public static final String SxServMainRemote = "SxServMainRemote";
	public static final String LocalWebSupportLocal = "LocalWebSupprtBeanLocal";
	public static final String sxadm = "sxadm";
	

	public static SxServerMainLocal getSxServerMainLocal(HttpServletRequest req) { return (SxServerMainLocal)req.getAttribute(SxServMainLocal); }
	public static SxServerMainRemote getSxServerMainRemote(HttpServletRequest req) { return (SxServerMainRemote)req.getAttribute(SxServMainRemote); }
	public static LocalWebSupportLocal getLocalWebSupportLocal(HttpServletRequest req) { return (LocalWebSupportLocal)req.getAttribute(LocalWebSupportLocal); }
	public static DataSource getSxadm(HttpServletRequest req) { return (DataSource)req.getAttribute(sxadm); }

	public static void setSxServerMainLocal(HttpServletRequest req, SxServerMainLocal v) { req.setAttribute(SxServMainLocal, v); }
	public static void setSxServerMainRemote(HttpServletRequest req, SxServerMainRemote v) { req.setAttribute(SxServMainRemote, v); }
	public static void setLocalWebSupportLocal(HttpServletRequest req, LocalWebSupportLocal v) { req.setAttribute(LocalWebSupportLocal, v); }
	public static void setSxadm(HttpServletRequest req, DataSource v) { req.setAttribute(sxadm, v); }
	
	
}
