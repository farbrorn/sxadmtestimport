/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package se.saljex.sxserver;

import se.saljex.sxlibrary.SXUtil;
import se.saljex.sxlibrary.SXConstant;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.naming.NamingException;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.io.ByteArrayOutputStream; 
import javax.mail.util.ByteArrayDataSource;
import javax.activation.DataHandler;
import javax.mail.Part;
import javax.persistence.EntityManager;
/**
 *
 * @author Ulf
 */
public class SendMail {
	private Session mailsxmail;
	
	private String user;
	private String password;
	private Integer port;
	private String transport;

	public SendMail(EntityManager em, Session s) {
		this(s, ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVSMTPUSER), ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVSMTPPASSWORD),
				ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVSMTPSERVERPORT), ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVSMTPTRANSPORT));
	}
	
	public SendMail(Session s, String u, String p, String por, String transport) {
		mailsxmail = s;
		user = u;
		password = p;
		this.transport=transport;
		if (transport==null || transport.isEmpty()) transport=SXConstant.SXREG_SXSERVSMTPTRANSPORT_DEFAULT;
		if (por==null) { port=25; }
		else {
			try { port = new Integer(por); } catch (NumberFormatException e) { port = 25; }
		}
	}
	

	/*
	public void sendMail(InternetAddress mailFrom, String mailTo, String subject, String body, ByteArrayOutputStream bs) throws NamingException, MessagingException, UnsupportedEncodingException {
		MimeMessage message;
		Transport tr;
		message = new MimeMessage(mailsxmail);
		message.setSubject(subject);
		message.setRecipients(javax.mail.Message.RecipientType.TO, javax.mail.internet.InternetAddress.parse(mailTo, false));
		//message.setText(body);
		message.setFrom(mailFrom);
		
		Multipart multipart = new MimeMultipart();
		
		MimeBodyPart messageBodyPart =new MimeBodyPart();
		messageBodyPart.setText(body);
		multipart.addBodyPart(messageBodyPart);
		
		messageBodyPart = new MimeBodyPart();
		ByteArrayDataSource b = new ByteArrayDataSource(bs.toByteArray(),"application/pdf");
		messageBodyPart.setDataHandler(new DataHandler(b));
		messageBodyPart.setFileName("Faktura");
		multipart.addBodyPart(messageBodyPart);

		// Put parts in message 
		message.setContent(multipart);
		
		mailsxmail.getProperties().put("mail.smtp.auth", "true");
		tr = mailsxmail.getTransport("smtp");
		tr.connect(user,password);
		tr.sendMessage(message, message.getAllRecipients());
		tr.close(); 
		
		//javax.mail.Transport.send(message);
	}
*/

	public void sendAdminSimpleMail(EntityManager em, String subject, String text) throws NamingException, MessagingException, UnsupportedEncodingException {
		sendMailTextHtml(	new InternetAddress(ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILFROMADRESS, SXConstant.SXREG_SXSERVMAILFROMADRESS_DEFAULT)), ServerUtil.getSXReg(em, SXConstant.SXREG_SXSERVADMINMAIL, SXConstant.SXREG_SXSERVADMINMAIL_DEFAULT), subject, text,text);
	}
	public void sendAdminSimpleMail(EntityManager em, String text) throws NamingException, MessagingException, UnsupportedEncodingException {
		sendAdminSimpleMail(em, "Meddelande från SxServer", text);
	}


	public void sendSimpleMail(EntityManager em, String mailTo, String subject, String text) throws NamingException, MessagingException, UnsupportedEncodingException {
		sendMailTextHtml(	new InternetAddress(ServerUtil.getSXReg(em,SXConstant.SXREG_SXSERVMAILFROMADRESS, SXConstant.SXREG_SXSERVMAILFROMADRESS_DEFAULT)), mailTo, subject, text,text);
	}

	public void sendMailTextHtml(InternetAddress mailFrom, String mailTo, String subject, String bodyText, String bodyHTML) throws NamingException, MessagingException, UnsupportedEncodingException {
		MimeMessage message;
		message = new MimeMessage(mailsxmail);
		message.setSubject(subject,"UTF-8");
		message.setRecipients(javax.mail.Message.RecipientType.TO, javax.mail.internet.InternetAddress.parse(mailTo, false));
		message.setFrom(mailFrom);
		
		MimeMultipart mpRoot = new MimeMultipart("mixed");

		// Create the content multipart (for text and HTML)
		MimeMultipart mpContent = new MimeMultipart("alternative");

		// Create a body part to house the multipart/alternative Part
		MimeBodyPart contentPartRoot = new MimeBodyPart();
		contentPartRoot.setContent(mpContent);

		// Add the root body part to the root multipart
		mpRoot.addBodyPart(contentPartRoot);

		// Add text
		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setText(bodyText,"UTF-8");
		mpContent.addBodyPart(mbp1);

		// Add html
		MimeBodyPart mbp2 = new MimeBodyPart();
		mbp2.setText(bodyHTML,"UTF-8","html");
		mpContent.addBodyPart(mbp2);

		message.setContent(mpRoot);
		
		sendMail(message);
		
	}

	public void sendMailTextHtmlPdf(InternetAddress mailFrom, String mailTo, String subject, String bodyText, String bodyHTML, ByteArrayOutputStream pdfStream, String fileName) throws NamingException, MessagingException, UnsupportedEncodingException {
		MimeMessage message;
		message = new MimeMessage(mailsxmail);
		message.setSubject(subject,"UTF-8");
		message.setRecipients(javax.mail.Message.RecipientType.TO, javax.mail.internet.InternetAddress.parse(mailTo, false));
		message.setFrom(mailFrom);
		
		MimeMultipart mpRoot = new MimeMultipart("mixed");

		// Create the content multipart (for text and HTML)
		MimeMultipart mpContent = new MimeMultipart("alternative");

		// Create a body part to house the multipart/alternative Part
		MimeBodyPart contentPartRoot = new MimeBodyPart();
		contentPartRoot.setContent(mpContent);

		// Add the root body part to the root multipart
		mpRoot.addBodyPart(contentPartRoot);

		// Add text
		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setText(bodyText,"UTF-8");
		mpContent.addBodyPart(mbp1);

		// Add html
		MimeBodyPart mbp2 = new MimeBodyPart();
		mbp2.setText(bodyHTML,"UTF-8","html");
		mpContent.addBodyPart(mbp2);

		// Add an attachment
		MimeBodyPart mbp3 = new MimeBodyPart();
		ByteArrayDataSource b = new ByteArrayDataSource(pdfStream.toByteArray(),"application/pdf");

		mbp3.setDisposition(Part.ATTACHMENT);
		mbp3.setDataHandler(new DataHandler(b));
		mbp3.setFileName(fileName);

		// add the body part to the root
		// NOTE: ADDING TO ROOT, NOT CONTENT ROOT

		mpRoot.addBodyPart(mbp3);

		message.setContent(mpRoot);
		
		sendMail(message);
		
	}
	
	
	private void sendMail(MimeMessage mimeMessage)throws NamingException, MessagingException, UnsupportedEncodingException {
		Transport tr;
		//mailsxmail.getProperties().put("mail.smtp.auth", "true");
		tr = mailsxmail.getTransport(transport);
		//tr = mailsxmail.getTransport("smtp");
		tr.connect(null,user,password);
//		tr.connect(null, port, user,password);
		tr.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		tr.close(); 
	}
}
