package com.web.cementerio.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.bo.PetempresaBO;
import com.web.cementerio.pojo.annotations.Petempresa;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.MailUtil;
import com.web.util.MessageUtil;


@ManagedBean
@ViewScoped
public class ContactenosBean implements Serializable {

	
	private static final long serialVersionUID = 4207311339845311723L;
	private Petempresa petempresa;
	private Petempresa petempresaVeterinaria;
	private String nombres;
	private String apellidos;
	private String correo;
	private String mensaje;
	
	
	public  ContactenosBean(){
	  inicializar();
	  consultar();
	}

	public void inicializar(){
		petempresa = new Petempresa(0, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null,null);
		petempresaVeterinaria = new Petempresa(0, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null,null);
		nombres = null;
		apellidos = null;
		correo = null;
		mensaje = null;
	}
	public void consultar(){
		try {
			PetempresaBO petempresaBO= new PetempresaBO();
			petempresa = petempresaBO.getPetempresabyTipo(1, 1);
			petempresaVeterinaria = petempresaBO.getPetempresabyTipo(1, 2);
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
	}
	
	public void enviar(){
		try{
			MailUtil mailUtil = new MailUtil();
			mailUtil.enviarMail(this.correo, "Información - CampoFeliz.com", this.mensaje);
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	public Petempresa getPetempresa() {
		return petempresa;
	}

	public void setPetempresa(Petempresa petempresa) {
		this.petempresa = petempresa;
	}

	public Petempresa getPetempresaVeterinaria() {
		return petempresaVeterinaria;
	}

	public void setPetempresaVeterinaria(Petempresa petempresaVeterinaria) {
		this.petempresaVeterinaria = petempresaVeterinaria;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
