package com.web.cementerio.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.bo.PetmascotahomenajeBO;
import com.web.cementerio.pojo.annotations.Petmascotahomenaje;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class MascotaHomenajeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5791044831809774834L;
	private Petmascotahomenaje petmascotahomenaje;
	
	
	
	
	public MascotaHomenajeBean() {
		petmascotahomenaje = new Petmascotahomenaje();
	}
	
	
	
	
	public void ingresarHomenajemascota() {
		try {
			PetmascotahomenajeBO petmascotahomenajeBO = new PetmascotahomenajeBO();
			petmascotahomenajeBO.ingresarPetmascotahomenajeBO(petmascotahomenaje);
			new MessageUtil().showInfoMessage("Exito", "Información registrada");
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showErrorMessage("Error", "Lamentamos que tenga inconvenietnes");
		}
	}

	public Petmascotahomenaje getPetmascotahomenaje() {
		return petmascotahomenaje;
	}

	public void setPetmascotahomenaje(Petmascotahomenaje petmascotahomenaje) {
		this.petmascotahomenaje = petmascotahomenaje;
	}

	
}
