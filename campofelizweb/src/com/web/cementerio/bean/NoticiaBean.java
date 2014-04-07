package com.web.cementerio.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.bo.PetnoticiaBO;
import com.web.cementerio.pojo.annotations.Petnoticia;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class NoticiaBean {

	private int idnoticia;
	private Petnoticia petnoticia;
	private String rutaImagenes;
	
	public NoticiaBean() {
		petnoticia = new Petnoticia();
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
	
	public int getIdnoticia() {
		return idnoticia;
	}

	public void setIdnoticia(int idnoticia) {
		this.idnoticia = idnoticia;
		
		if(this.idnoticia > 0){
			try {
				PetnoticiaBO petnoticiaBO = new PetnoticiaBO();
				petnoticia = petnoticiaBO.getPetnoticiaById(idnoticia);
			} catch(Exception e) {
				e.printStackTrace();
				new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
	}

	public Petnoticia getPetnoticia() {
		return petnoticia;
	}

	public void setPetnoticia(Petnoticia petnoticia) {
		this.petnoticia = petnoticia;
	}

	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}
}
