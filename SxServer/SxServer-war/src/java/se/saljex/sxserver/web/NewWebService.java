/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver.web;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author ulf
 */
@WebService()
public class NewWebService {

/**
	   * Web service operation
	   */
	  @WebMethod(operationName = "te")
	  public String te() {
			 //TODO write your implementation code here:
			 return "From webservice";
	  }
}
