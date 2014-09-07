package com.web.cementerio.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.internet.AddressException;

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
			FacesUtil facesUtil = new FacesUtil();
			
			//formatear el contenido para el remitente de correo
			String contenido2 = contenido("Campo Feliz Cementerio de Mascotas", "Gracias por comunicarte con nosotros! En breve te estaremos respondiendo!");
			
			//enviar respuesta al remitente
			mailUtil.enviarMail(this.correo, "Información - Campo Feliz", contenido2);
			
			//formatear el contenido para el administrador de correo
			String contenido = contenido("Solicitud de Información", "Ha recibido un mensaje de la página de contáctenos");
			
			//enviar al administrador de correo
			mailUtil.enviarMail(null, "Información - Campo Feliz", contenido);
			
			facesUtil.redirect("home.jsf");
		}catch(AddressException e) {
			e.printStackTrace();
			new MessageUtil().showErrorMessage("Error!", "Ingrese una cuenta de correo válida e intente nuevamente.");
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	private String contenido(String titulo, String textoIntroductorio) {
		String contenido = "";
		FacesUtil facesUtil = new FacesUtil();
		String logo = facesUtil.getHostDomain() + "/resources/images/logo.jpg";
		
		contenido += "<html>";
		contenido += "<body style='font: 12px/18px Arial, Helvetica, sans-serif;'>";
		contenido += "<table cellpadding='0' cellspacing='0' style='width: 100%'>";
		contenido += "<tr>";
		contenido += "<td style='border: 1px solid #66ad23;background-color: #66ad23;color: white;border-radius: 4px 4px 0 0;height: 30px;'>";
		contenido += "<h1 style='font-size: 1.3em;line-height: 2.1;text-align: center;'>"+titulo+"</h1>";
		contenido += "</td>";
		contenido += "</tr>";
		contenido += "<tr>";
		contenido += "<td style='border: 1px solid #66ad23;padding: 10px 25px;'>";

		contenido += "<table cellpadding='0' cellspacing='0' style='width: 100%'>";
		contenido += "<tr>";
		contenido += "<td colspan='2'><center><a title='Campo Feliz' href='"+facesUtil.getHostDomain()+"' target='_blank'><img alt='Campo Feliz' src='"+logo+"'></img></a></center></td>";
		contenido += "</tr>";
		contenido += "<tr>";
		contenido += "<td colspan='2'><span>"+textoIntroductorio+"</span></td>";
		contenido += "</tr>";
		contenido += "<tr>";
		contenido += "<td colspan='2'><span>&nbsp;</span></td>";
		contenido += "</tr>";
		contenido += "<tr>";
		contenido += "<td style='width: 10%;'><strong>Nombres: </strong></td><td style='width: 90%;'>" + nombres + "</td>";
		contenido += "</tr>";
		contenido += "<tr>";	
		contenido += "<td><strong>Email: </strong></td><td>" + correo + "</td>";
		contenido += "</tr>";
		contenido += "<tr>";
		contenido += "<td colspan='2'><span>&nbsp;</span></td>";
		contenido += "</tr>";
		contenido += "<tr>";
		contenido += "<td colspan='2' style='text-align: justify;'><span style='padding: 10px 0 0 0'>" + mensaje + "</span></td>";
		contenido += "</tr>";
		contenido += "<tr><td style='height: 20px;'>&nbsp;</td></tr>";
		contenido += "</table>";
		
		contenido += "</td>";
		contenido += "</tr>";
		contenido += "</table>";
		contenido += "</body>";
		contenido += "</html>";
		
		return contenido;
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
