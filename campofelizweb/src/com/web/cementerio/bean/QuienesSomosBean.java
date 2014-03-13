package com.web.cementerio.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.bo.PetinformacionBO;
import com.web.cementerio.pojo.annotations.Petinformacion;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class QuienesSomosBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3186508458073717263L;
	private String prueba;
	private Petinformacion petinformacion;

	public QuienesSomosBean() {
		prueba = "Soy una prueba";
		petinformacion = new Petinformacion();
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
	
	private void ingresarPetinformacion(){
		try{
			PetinformacionBO petinformacionBO = new PetinformacionBO();
			petinformacionBO.ingresarPetinformacion(petinformacion);
		}catch (Exception e){
			e.printStackTrace();
			new MessageUtil().showErrorMessage("Error", "Lamentamos que tenga inconvenientes");
		}
		
		
	}
	
	private void actualizarPerinformacion(){
		try{
			PetinformacionBO petinformacionBO = new  PetinformacionBO();
			petinformacionBO.actualizarPetinformacion(petinformacion);
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showErrorMessage("Error","Lamentamos que tenga inconvenientes");
		}
	}

	public String getPrueba() {
		return prueba;
	}

	public void setPrueba(String prueba) {
		this.prueba = prueba;
	}

	public Petinformacion getPetinformacion() {
		return petinformacion;
	}

	public void setPetinformacion(Petinformacion petinformacion) {
		this.petinformacion = petinformacion;
	}
}
