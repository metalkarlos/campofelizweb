package com.web.cementerio.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class NoticiasBean {

	private String rutaImagenes;
	
	public NoticiasBean() {
		try {
			rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}


}
