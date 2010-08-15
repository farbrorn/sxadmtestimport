package se.saljex.bv.client;

/**
 *
 * @author ulf
 */
public class NotLoggedInException extends java.lang.Exception{
	public NotLoggedInException() {
		super("Inte inloggad");
	}
	public NotLoggedInException (String s) {
		super(s);
	}

}
