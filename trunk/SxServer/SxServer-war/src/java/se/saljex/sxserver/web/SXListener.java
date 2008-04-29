/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.*;
import javax.ejb.EJB;
import se.saljex.sxserver.SxServerMainLocal;


/**
 *
 * @author Ulf
 */
public class SXListener implements ServletContextListener{
    @EJB
    //private dbTesterLocal dbTesterBean;
	private SxServerMainLocal sxServerMainBean;
    
    
private ServletContext context = null;

  /*This method is invoked when the Web Application has been removed 
  and is no longer able to accept requests
  */

  public void contextDestroyed(ServletContextEvent event)
  {
    this.context = null;

  }


  //This method is invoked when the Web Application
  //is ready to service requests

  public void contextInitialized(ServletContextEvent event)
  {
    this.context = event.getServletContext();
    sxServerMainBean.main();
  }

}
