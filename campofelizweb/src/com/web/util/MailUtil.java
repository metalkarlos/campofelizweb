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
	private String usuario;
	private String clave;
	private String port;
	
	public MailUtil() {
		host = "";
		usuario = "";
		clave = "";
		port = "";
	}
	
	/***
	 * Envía correo
	 * @param destinatario Si es null se envía únicamente al administrador
	 * @param asunto
	 * @param contenido
	 * @throws Exception
	 */
	public void enviarMail(String destinatario, String asunto, String contenido) throws Exception {
		Properties propertiesMail = new FileUtil()
				.getPropertiesFile(Parametro.PROPERTIES_MAIL);
		host = propertiesMail.getProperty("host");
		usuario = propertiesMail.getProperty("usuario");
		clave = propertiesMail.getProperty("clave");
		port = propertiesMail.getProperty("port");

		//Properties properties = System.getProperties();
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);   
		
		properties.put("mail.user", usuario);//
		properties.put("mail.password", clave);//

		properties.put("mail.smtp.starttls.enable","true");
		//properties.put("mail.smtp.EnableSSL.enable","true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.socketFactory.port", port);
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
		properties.put("mail.smtp.socketFactory.fallback", "false");   
		
		properties.put("mail.debug", "true"); 
		 
		//Session mailSession = Session.getDefaultInstance(properties, null);
		Session mailSession = Session.getDefaultInstance(properties, 
			    new javax.mail.Authenticator(){
			        protected PasswordAuthentication getPasswordAuthentication() {
			            return new PasswordAuthentication(
			            		usuario, clave);
			        }
			});

		mailSession.setDebug(true);
		
		MimeMessage message = new MimeMessage(mailSession);
		message.setFrom(new InternetAddress(usuario));
		destinatario = destinatario == null ? usuario : destinatario;
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
		message.setSubject(asunto, "utf-8");
		message.setContent(contenido, "text/html");
		
		Transport.send(message);
		//Transport tr = mailSession.getTransport("smtp");
		//tr.connect(host, to, clave);
		//message.saveChanges();      // don't forget this
		//tr.sendMessage(message, message.getAllRecipients());
		//tr.close();
		
		mailSession.getDebugOut();
	}
	
}
