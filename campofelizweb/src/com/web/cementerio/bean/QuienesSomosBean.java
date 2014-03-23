package com.web.cementerio.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.bo.PetinformacionBO;
import com.web.cementerio.pojo.annotations.Petinformacion;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class QuienesSomosBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3186508458073717263L;
	private Petinformacion petinformacion;

	public QuienesSomosBean() {
		petinformacion = new Petinformacion(0, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null);
		consultarInformacion();
	}
	
	private void consultarInformacion(){
		try{
			PetinformacionBO petinformacionBO = new PetinformacionBO();
			petinformacion = petinformacionBO.getPetinformacionById(1);
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showErrorMessage("Error", "Lamentamos que tenga inconvenientes");
		}
	}
	
	public void guardarPetinformacion(){
		try{
			if (petinformacion.getIdinformacion() > 0) {
				PetinformacionBO petinformacionBO = new PetinformacionBO();
				petinformacionBO.actualizarPetinformacion(petinformacion);
				
				new MessageUtil().showInfoMessage("Exito", "Registro actualizado");
			}
		}catch (Exception e){
			e.printStackTrace();
			new MessageUtil().showErrorMessage("Error", "Lamentamos que tenga inconvenientes");
		}
		
		
	}
	
	public Petinformacion getPetinformacion() {
		return petinformacion;
	}

	public void setPetinformacion(Petinformacion petinformacion) {
		this.petinformacion = petinformacion;
	}
}
