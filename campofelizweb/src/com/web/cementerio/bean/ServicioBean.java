package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.bo.PetservicioBO;
import com.web.cementerio.pojo.annotations.Petfotoservicio;
import com.web.cementerio.pojo.annotations.Petservicio;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class ServicioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2778897181870142036L;
	private int idservicio;
	private Petservicio petservicio;
	private String rutaImagenes;
	private List<Petfotoservicio> lisPetfotoservicio;
	
	public ServicioBean() {
		petservicio = new Petservicio();
		lisPetfotoservicio = new ArrayList<Petfotoservicio>();
		cargarRutaImagenes();
	}

	private void cargarRutaImagenes(){
		try {
			rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	@PostConstruct
	public void initServicioBean() {
		FacesUtil facesUtil = new FacesUtil();
		idservicio = Integer.parseInt(facesUtil.getParametroUrl("idservicio").toString());
		
		if(idservicio > 0){
			consultarServicio();
		}
	}
	
	public void consultarServicio(){
		if(this.idservicio > 0){
			try {
				PetservicioBO petservicioBO = new PetservicioBO();
				petservicio = petservicioBO.getPetservicioConObjetosById(idservicio);
				
				if(petservicio != null && petservicio.getPetfotoservicios() != null && petservicio.getPetfotoservicios().size() > 0){
					lisPetfotoservicio = new ArrayList<Petfotoservicio>(petservicio.getPetfotoservicios());
				}
			} catch(Exception e) {
				e.printStackTrace();
				new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
	}
	
	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}

	public int getIdservicio() {
		return idservicio;
	}

	public void setIdservicio(int idservicio) {
		this.idservicio = idservicio;
	}

	public Petservicio getPetservicio() {
		return petservicio;
	}

	public void setPetservicio(Petservicio petservicio) {
		this.petservicio = petservicio;
	}

	public List<Petfotoservicio> getLisPetfotoservicio() {
		return lisPetfotoservicio;
	}

	public void setLisPetfotoservicio(List<Petfotoservicio> lisPetfotoservicio) {
		this.lisPetfotoservicio = lisPetfotoservicio;
	}

}
