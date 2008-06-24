/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import javax.servlet.http.HttpSession;

/**
 *
 * @author ulf
 */
public class WebUtil {
	public static SXSession getSXSession (HttpSession session) {
		SXSession s = (SXSession)session.getAttribute("sxsession");
		if (s == null) { 
			s = new SXSession(); 
			session.setAttribute("sxsession", s);
		}
		return s;
	}
}
