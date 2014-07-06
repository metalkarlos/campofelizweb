package com.web.cementerio.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.bo.PetempresaBO;
import com.web.cementerio.pojo.annotations.Petempresa;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
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
			
			//formatear el contenido para el administrador de correo
			String contenido = "";
			contenido += "<html>";
			contenido += "<body>";
			contenido += "<center><h1>Ha recibido un mensaje de la p&aacute;gina de cont&aacute;ctenos</h1></center>";
			contenido += "<table style='width: 100%'>";
			contenido += "<tr>";
			contenido += "<td style='width: 150px;'><strong>Nombres: </strong></td><td>" + nombres + "</td>";
			contenido += "</tr>";
			contenido += "<tr>";	
			contenido += "<td><strong>Email: </strong></td><td>" + correo + "</td>";
			contenido += "</tr>";
			contenido += "<tr>";
			contenido += "<td colspan='2'><p style='text-align: justify;padding: 10px 0 0 0'>" + mensaje + "</p></td>";
			contenido += "</tr>";
			contenido += "</table>";
			contenido += "</body>";
			contenido += "</html>";
			
			//enviar al administrador de correo
			mailUtil.enviarMail(null, "Información - Campo Feliz", contenido);
			
			//formatear el contenido para el remitente de correo
			String contenido2 = "";
			contenido2 += "<html>";
			contenido2 += "<body>";
			contenido2 += "<center><h1>Campo Feliz Cementerio de Mascotas</h1></center>";
			contenido2 += "<p style='text-align: justify;padding: 10px 0 0 0'>Gracias por comunicarte con nosotros! En breve te estaremos respondiendo.</p>";
			contenido2 += "<table style='width: 100%'>";
			contenido2 += "<tr>";
			contenido2 += "<td style='width: 150px;'><strong>Nombres: </strong></td><td>" + nombres + "</td>";
			contenido2 += "</tr>";
			contenido2 += "<tr>";	
			contenido2 += "<td><strong>Email: </strong></td><td>" + correo + "</td>";
			contenido2 += "</tr>";
			contenido2 += "<tr>";
			contenido2 += "<td colspan='2'><p style='text-align: justify;padding: 10px 0 0 0'>" + mensaje + "</p></td>";
			contenido2 += "</tr>";
			contenido2 += "</table>";
			contenido2 += "</body>";
			contenido2 += "</html>";
			
			//enviar respuesta al remitente
			mailUtil.enviarMail(this.correo, "Información - Campo Feliz", contenido2);
			
			FacesUtil facesUtil = new FacesUtil();
			facesUtil.redirect("home.jsf");
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
