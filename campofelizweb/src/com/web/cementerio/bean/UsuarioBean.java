package com.web.cementerio.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.web.cementerio.bo.SetusuarioBO;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

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
	
	public UsuarioBean(){
		ip = new FacesUtil().getIp();
		sid = new FacesUtil().getSid();
		setUsuario = new Setusuario();
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
		
		try{
			setUsuario = new SetusuarioBO().getByUserPasswd(username, password);
			
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
		
		return strRedirect;
	}
	
	public String logout(){
		FacesUtil facesUtil = new FacesUtil();
		
		try{
			facesUtil.logout();
			FileUtil fileUtil = new FileUtil();
			String strLogin = fileUtil.getPropertyValue("home");
			facesUtil.redirect(strLogin);
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
		return "";
	}
	
	public String homePage(){
		String homePage = null;
		
		try{
			FileUtil fileUtil = new FileUtil();
			homePage = fileUtil.getPropertyValue("home");
		}
		catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
		return homePage;
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
	
}