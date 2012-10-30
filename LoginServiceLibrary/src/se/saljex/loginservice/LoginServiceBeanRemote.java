/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.loginservice;

import javax.ejb.Remote;

/**
 *
 * @author Ulf
 */
@Remote
public interface LoginServiceBeanRemote {

	String test();

	void logoutSession(User user);

	User loginByUUID(String uuid);

	User login(String anvandare, String losen);

	void logoutAllUserSessions(User user);
	
}
