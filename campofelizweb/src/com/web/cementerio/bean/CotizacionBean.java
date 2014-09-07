package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.internet.AddressException;

import com.web.cementerio.bo.PetservicioBO;
import com.web.cementerio.pojo.annotations.Petservicio;
import com.web.util.FacesUtil;
import com.web.util.MailUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class CotizacionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4833526764348322717L;
	private List<String> lisPetservicio;
	private String[] lisPetservicioSeleccionados;
	private String nombres;
	private String apellidos;
	private String correo;
	private String otrosServicios;
	
	public CotizacionBean() {
		lisPetservicio = new ArrayList<String>();
		nombres = null;
		apellidos = null;
		correo = null;
		otrosServicios = "";
		
		consultarServicios();
	}
	
	private void consultarServicios(){
		try{
			PetservicioBO petservicioBO = new PetservicioBO(); 
			List<Petservicio> tmp = petservicioBO.lisPetservicio();
			for(Petservicio petservicio : tmp){
				lisPetservicio.add(petservicio.getNombre());
			}
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!", null);
		}
	}
	
	public void enviar(){
		if(validacionOk()){
			try{
				MailUtil mailUtil = new MailUtil();
				FacesUtil facesUtil = new FacesUtil();
				
				//formatear el contenido para el remitente de correo
				String contenido2 = contenido("Campo Feliz Cementerio de Mascotas", "Gracias por comunicarte con nosotros! En breve te estaremos respondiendo!");
				
				//enviar respuesta al remitente
				mailUtil.enviarMail(this.correo, "Cotización - Campo Feliz", contenido2);
				
				//formatear el contenido para el administrador de correo
				String contenido = contenido("Solicitud de Cotización", "Ha recibido una solicitud de cotización del sitio web");
				
				//enviar al administrador de correo
				mailUtil.enviarMail(null, "Cotización - Campo Feliz", contenido);
				
				facesUtil.redirect("home.jsf");
			}catch(AddressException e) {
				e.printStackTrace();
				new MessageUtil().showErrorMessage("Error!", "Ingrese una cuenta de correo válida e intente nuevamente.");
			}catch(Exception e){
				e.printStackTrace();
				new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
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
		contenido += "<td><strong>Apellidos: </strong></td><td>" + apellidos + "</td>";
		contenido += "</tr>";
		contenido += "<tr>";	
		contenido += "<td><strong>Email: </strong></td><td>" + correo + "</td>";
		contenido += "</tr>";
		contenido += "<tr style='height: 20px;'>";	
		contenido += "<td></td><td></td>";
		contenido += "</tr>";
		contenido += "<tr>";	
		contenido += "<td colspan='2'><strong>Servicios que solicita: </strong></td>";
		contenido += "</tr>";
		for(String petservicio : lisPetservicioSeleccionados){
			contenido += "<tr>";	
			contenido += "<td colspan='2'>" + petservicio + "</td>";
			contenido += "</tr>";
		}
		if(otrosServicios != null && otrosServicios.trim().length() > 0){
			contenido += "<tr style='height: 20px;'>";	
			contenido += "<td></td><td></td>";
			contenido += "</tr>";
			contenido += "<tr>";	
			contenido += "<td colspan='2'><strong>Otros Servicios: </strong></td>";
			contenido += "</tr>";
			contenido += "<tr>";	
			contenido += "<td colspan='2'>" + otrosServicios + "</td>";
			contenido += "</tr>";
			contenido += "<tr><td style='height: 20px;'>&nbsp;</td></tr>";
		}
		contenido += "</table>";
		
		contenido += "</td>";
		contenido += "</tr>";
		contenido += "</table>";
		contenido += "</body>";
		contenido += "</html>";
		
		return contenido;
	}
	
	private boolean validacionOk(){
		boolean ok = false;
		
		if(nombres != null && nombres.trim().length() > 0){
			if(apellidos != null && apellidos.trim().length() > 0){
				if(correo != null && correo.trim().length() > 0){
					if(lisPetservicioSeleccionados != null && lisPetservicioSeleccionados.length > 0){
						ok = true;
					}else{
						new MessageUtil().showWarnMessage("*Servicios", null);
					}
				}else{
					new MessageUtil().showWarnMessage("*Email", null);
				}
			}else{
				new MessageUtil().showWarnMessage("*Apellidos", null);
			}
		}else{
			new MessageUtil().showWarnMessage("*Nombres", null);
		}
		
		return ok;
	}
	
	public String getOtrosServicios() {
		return otrosServicios;
	}

	public void setOtrosServicios(String otrosServicios) {
		this.otrosServicios = otrosServicios;
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

	public List<String> getLisPetservicio() {
		return lisPetservicio;
	}

	public void setLisPetservicio(List<String> lisPetservicio) {
		this.lisPetservicio = lisPetservicio;
	}

	public String[] getLisPetservicioSeleccionados() {
		return lisPetservicioSeleccionados;
	}

	public void setLisPetservicioSeleccionados(String[] lisPetservicioSeleccionados) {
		this.lisPetservicioSeleccionados = lisPetservicioSeleccionados;
	}

}
