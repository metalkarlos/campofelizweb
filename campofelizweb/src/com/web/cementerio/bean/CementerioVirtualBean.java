package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.pojo.annotations.Petguia;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;


@ManagedBean
@ViewScoped
public class CementerioVirtualBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7212209718614799223L;

	
	private List<Petguia> listPetguia;
	private String rutaImagenes;

 
    public CementerioVirtualBean() {
    	listPetguia = new ArrayList<Petguia>();
 
    	listPetguia.add(new Petguia(0,new Setestado(),new Setusuario(),"/instalaciones/01-06-2014-campo-santo.jpg", null,null,null,null,null,null,null,false,null));
    	listPetguia.add(new Petguia(1,new Setestado(),new Setusuario(),"/instalaciones/01-06-2014-entrada.jpg", null,null,null,null,null,null,null,false,null));
    	listPetguia.add(new Petguia(2,new Setestado(),new Setusuario(),"/instalaciones/01-06-2014-lapida-01.jpg", null,null,null,null,null,null,null,false,null));
    	
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

	public List<Petguia> getListPetguia() {
		return listPetguia;
	}



	public void setListPetguia(List<Petguia> listPetguia) {
		this.listPetguia = listPetguia;
	}

	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}
    
}
