package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.bo.PetfotoinstalacionBO;
import com.web.cementerio.pojo.annotations.Petfotoinstalacion;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;


@ManagedBean
@ViewScoped
public class CementerioVirtualBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7212209718614799223L;
	
	private String rutaImagenes;
	private List<Petfotoinstalacion> lisPetfotoinstalacion;
 
    public CementerioVirtualBean() {
    	lisPetfotoinstalacion = new ArrayList<Petfotoinstalacion>();

    	cargarRutaImagenes();
    	consultarInstalacion();
    }

    private void cargarRutaImagenes(){
		try {
			rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

    private void consultarInstalacion(){
    	try
		{
    		PetfotoinstalacionBO petfotoinstalacionBO = new PetfotoinstalacionBO();
    		lisPetfotoinstalacion = petfotoinstalacionBO.lisPetfotoinstalacion();
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Ha ocurrido un error inesperado. Comunicar al Webmaster!", "");
		}
    }
    
	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}

	public List<Petfotoinstalacion> getLisPetfotoinstalacion() {
		return lisPetfotoinstalacion;
	}

	public void setLisPetfotoinstalacion(
			List<Petfotoinstalacion> lisPetfotoinstalacion) {
		this.lisPetfotoinstalacion = lisPetfotoinstalacion;
	}

}
