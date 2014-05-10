package com.web.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.web.cementerio.global.Parametro;

public class MailUtil {

	private String host;
	private String to;
	private String clave;
	
	public void enviarMail(String from, String asunto, String mensaje) throws Exception {
		Properties propertiesMail = new FileUtil()
				.getPropertiesFile(Parametro.PROPERTIES_MAIL);
		host = propertiesMail.getProperty("host");
		to = propertiesMail.getProperty("usuario");
		clave = propertiesMail.getProperty("clave");

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");   
		
		//properties.put("mail.user", to);
		//properties.put("mail.password", clave);

		properties.put("mail.smtp.starttls.enable","true");
		//properties.put("mail.smtp.EnableSSL.enable","true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.socketFactory.port", "465");
		
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
		properties.put("mail.smtp.socketFactory.fallback", "false");   
		
		properties.put("mail.debug", "true"); 
		 
		//Session mailSession = Session.getDefaultInstance(properties);
		//Authenticator myAuth = new MyAuthenticator();
		Session mailSession = Session.getDefaultInstance(properties, 
			    new javax.mail.Authenticator(){
			        protected PasswordAuthentication getPasswordAuthentication() {
			            return new PasswordAuthentication(
			            		to, clave);// Specify the Username and the PassWord
			        }
			});

		mailSession.setDebug(true);
		
		MimeMessage message = new MimeMessage(mailSession);
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(asunto);
		message.setContent(mensaje, "text/html");
		
		Transport.send(message);
		
		mailSession.getDebugOut();
	}
	
}
