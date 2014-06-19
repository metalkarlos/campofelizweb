package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.StreamedContent;

import com.web.cementerio.bo.PetempresaBO;
import com.web.cementerio.bo.SetpeticionclaveBO;
import com.web.cementerio.bo.SetusuarioBO;
import com.web.cementerio.pojo.annotations.Petempresa;
import com.web.cementerio.pojo.annotations.Setpeticionclave;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MailUtil;
import com.web.util.MessageUtil;
import com.web.util.Utilities;

@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5577462729566726719L;
	private Setusuario setUsuario;
	private String username;
	private String password;
	private String ip;
	private String sid;
	private boolean autenticado;
	private StreamedContent streamedContent;
	private Petempresa petempresa;
	
	public UsuarioBean(){
		ip = new FacesUtil().getIp();
		sid = new FacesUtil().getSid();
		setUsuario = new Setusuario();
		petempresa = new Petempresa();
		
		consultarEmpresa();
	}
	
	private void consultarEmpresa() {
		try{
			petempresa = new PetempresaBO().getPetempresabyId(1, 1);
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	public void setSetUsuario(Setusuario setUsuario) {
		this.setUsuario = setUsuario;
	}

	public Setusuario getSetUsuario() {
		return setUsuario;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getIp() {
		return ip;
	}

	public String getSid() {
		return sid;
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public String login(){
		String strRedirect = null;
		
		if(validacionOk()){
			try{
				Utilities utilities = new Utilities();
				String cifrado = utilities.cifrar(password);
				setUsuario = new SetusuarioBO().getByUserPasswd(username, cifrado);
				
				if(setUsuario!=null && setUsuario.getIdusuario()>0){
					autenticado = true;
					
					FileUtil fileUtil = new FileUtil();
					strRedirect = fileUtil.getPropertyValue("home");
				}else{
					new MessageUtil().showWarnMessage("Autenticación fallida","Usuario o Contraseña no existen.");
				}
			}catch(Exception re){
				new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
		
		return strRedirect;
	}
	
	private boolean validacionOk(){
		boolean ok = false;
		
		if(username != null && username.trim().length() > 0){
			if(password != null && password.trim().length() > 0){
				ok = true;
			}else{
				new MessageUtil().showWarnMessage("Ingrese la Clave", null);
			}
		}else{
			new MessageUtil().showWarnMessage("Ingrese su Usuario", null);
		}
		
		return ok;
	}
	
	public String logout(){
		FacesUtil facesUtil = new FacesUtil();
		
		try{
			facesUtil.logout();
			facesUtil.redirectByPropertyFileKey("home");
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
		return "";
	}
	
	public void enviarOlvidoClave(){
		if(validacionOlvidoClaveOk()){
			if(usuarioExiste()){
				try{
					//generar uid
					UUID uid = UUID.randomUUID();
					
					Setpeticionclave setpeticionclave = new Setpeticionclave();
					setpeticionclave.setUid(uid);
					setpeticionclave.setUsuario(username);
					
					SetpeticionclaveBO setpeticionclaveBO = new SetpeticionclaveBO();
					
					//insertar en tabla uid y fechaexpiracion
					boolean ok = setpeticionclaveBO.ingresar(setpeticionclave);
					
					if(ok){
						//enviar mail verificacion
						MailUtil mailUtil = new MailUtil();
						
						//formatear el contenido para el administrador de correo
						String formulario = "http://localhost:8080/campofelizweb/pages/cambiarclave.jsf";
						String contenido = "";
						contenido += "<html>";
						contenido += "<body>";
						contenido += "<center><h1>Olvido de Clave</h1></center>";
						contenido += String.format("<p>Ha solicitado cambiar la clave del usuario: <strong>%s</strong>.</p>", username);
						contenido += "<p>De click en el siguiente link para acceder al formulario de cambio de clave.</p>";
						contenido += String.format("<a href='%s?uid=%s'>%s?uid=%s</a>", formulario,uid,formulario,uid);
						contenido += "<p>Este link tiene una validez de <strong>5 minutos<strong>.</p>";
						contenido += "<p>Si ud no ha solicitado el cambio de clave ignore este correo.</p>";
						contenido += String.format("<p>IP desde dónde se realizó la petición: <strong>%s</strong>.</p>", ip);
						contenido += "</body>";
						contenido += "</html>";
						
						//enviar al administrador de correo
						mailUtil.enviarMail(null, "Información - Campo Feliz", contenido);
						//mostrar mensaje en pantalla se ha enviado mail
						new MessageUtil().showInfoMessage("Se ha enviado un link de confirmación a su cuenta de correo", "");
					}else{
						new MessageUtil().showInfoMessage("No se ha podido enviar la solicitud. Intente en unos minutos", "");
					}
				}catch(Exception e){
					e.printStackTrace();
					new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
				}
			}
		}
	}
	
	private boolean validacionOlvidoClaveOk(){
		boolean ok = false;
		
		if(username != null && username.trim().length() > 0){
			ok = true;
		}else{
			new MessageUtil().showWarnMessage("Ingrese su Usuario", "");
		}
		
		return ok;
	}
	
	private boolean usuarioExiste(){
		boolean ok = false;
		
		try{
			SetusuarioBO setusuarioBO = new SetusuarioBO();
			Setusuario setusuario = setusuarioBO.getSetusuarioByUsuario(username);
			
			if(setusuario != null && setusuario.getIdusuario() > 0){
				ok = true;
			}else{
				new MessageUtil().showWarnMessage("Usuario no existe", "");
			}
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
		return ok;
	}
	
	public String redirect(){
		FileUtil fileUtil = new FileUtil();
		
		try{
			String strnotlogged = fileUtil.getPropertyValue("notlogged");
			new FacesUtil().redirect(strnotlogged);
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
		return "";
	}

	public StreamedContent getStreamedContent() {
		return streamedContent;
	}

	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}

	public Petempresa getPetempresa() {
		return petempresa;
	}

	public void setPetempresa(Petempresa petempresa) {
		this.petempresa = petempresa;
	}

}