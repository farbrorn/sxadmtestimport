/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.loginservice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Ulf
 */
public class Util {
	public static Cookie createLoginCookie(String uuid) {
		Cookie co = new Cookie(LoginServiceConstants.COOKIE_LOGINSERVICE, uuid); 
		co.setMaxAge(24*60*60*10);
		co.setPath("/");
		return co;
	}
	
	public static Cookie getLoginCookie(Cookie cArr[]) {
		Cookie co = null;
		if (cArr!=null) {
			for (int x=0; x<cArr.length; x++) {
				if (LoginServiceConstants.COOKIE_LOGINSERVICE.equals(cArr[x].getName()) && cArr[x].getValue()!=null && cArr[x].getValue().length()>0 ) {
					co = cArr[x];
					break;
				}
			}
		}
		return co;	
	}
	
}
