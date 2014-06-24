package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
				
				//formatear el contenido para el administrador de correo
				String contenido = "";
				contenido += "<html>";
				contenido += "<body>";
				contenido += "<center><h1>Ha recibido una solicitud de cotización del sitio web</h1></center>";
				contenido += "<table style='width: 100%'>";
				contenido += "<tr>";
				contenido += "<td style='width: 150px;'><strong>Nombres: </strong></td><td>" + nombres + "</td>";
				contenido += "</tr>";
				contenido += "<tr>";
				contenido += "<td style='width: 150px;'><strong>Apellidos: </strong></td><td>" + apellidos + "</td>";
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
				}
				contenido += "</table>";
				contenido += "</body>";
				contenido += "</html>";
				
				//enviar al administrador de correo
				mailUtil.enviarMail(null, "Cotización - Campo Feliz", contenido);
				
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
				contenido2 += "<td style='width: 150px;'><strong>Apellidos: </strong></td><td>" + apellidos + "</td>";
				contenido2 += "</tr>";
				contenido2 += "<tr>";	
				contenido2 += "<td><strong>Email: </strong></td><td>" + correo + "</td>";
				contenido2 += "</tr>";
				contenido2 += "<tr style='height: 20px;'>";	
				contenido2 += "<td></td><td></td>";
				contenido2 += "</tr>";
				contenido2 += "<tr>";	
				contenido2 += "<td colspan='2'><strong>Servicios que solicita: </strong></td>";
				contenido2 += "</tr>";
				for(String petservicio : lisPetservicioSeleccionados){
					contenido2 += "<tr>";	
					contenido2 += "<td colspan='2'>" + petservicio + "</td>";
					contenido2 += "</tr>";
				}
				if(otrosServicios != null && otrosServicios.trim().length() > 0){
					contenido2 += "<tr style='height: 20px;'>";	
					contenido2 += "<td></td><td></td>";
					contenido2 += "</tr>";
					contenido2 += "<tr>";	
					contenido2 += "<td colspan='2'><strong>Otros Servicios: </strong></td>";
					contenido2 += "</tr>";
					contenido2 += "<tr>";	
					contenido2 += "<td colspan='2'>" + otrosServicios + "</td>";
					contenido2 += "</tr>";
				}
				contenido2 += "</table>";
				contenido2 += "</body>";
				contenido2 += "</html>";
				
				//enviar respuesta al remitente
				mailUtil.enviarMail(this.correo, "Cotización - Campo Feliz", contenido2);
				
				FacesUtil facesUtil = new FacesUtil();
				facesUtil.redirect("home.jsf");
			}catch(Exception e){
				e.printStackTrace();
				new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
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
