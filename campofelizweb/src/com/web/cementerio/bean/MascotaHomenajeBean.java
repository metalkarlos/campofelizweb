package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.bo.PetmascotahomenajeBO;
import com.web.cementerio.pojo.annotations.Petespecie;
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
		inicializarobjetos();
	}
	
	
	public void inicializarobjetos(){
		petmascotahomenaje = new Petmascotahomenaje();
		Petespecie petespecie = new Petespecie();
		petmascotahomenaje.setPetespecie(petespecie);
	}
	
	public void ingresarHomenajemascota() {
		try {
			if(validarcampos()){
				PetmascotahomenajeBO petmascotahomenajeBO = new PetmascotahomenajeBO();
				petmascotahomenajeBO.ingresarPetmascotahomenajeBO(petmascotahomenaje);
				new MessageUtil().showInfoMessage("Exito", "Información registrada");
				inicializarobjetos();
			}
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showErrorMessage("Error", "Lamentamos que tenga inconvenietnes");
		}
	}
	
	public boolean validarcampos(){
		boolean ok = true;
		Date fechaactual = new Date();
		if(petmascotahomenaje.getNombre()==null|| petmascotahomenaje.getNombre().length()==0){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Es necesario ingresar el Nombre de la mascota");
		}else if(petmascotahomenaje.getPetespecie().getIdespecie() ==0){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Es necesario seleccionar la especie");
			
		}else if (petmascotahomenaje.getFechanacimiento() == null){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Es necesario seleccionar la fecha de nacimiento de la mascota");
		}
		else if (petmascotahomenaje.getFechafallecimiento()==null){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Es necesario seleccionar la fecha de fallecimiento de la mascota");
		}
		else if (petmascotahomenaje.getFamilia()==null||petmascotahomenaje.getFamilia().length()==0){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Es necesario ingresar la familia de la mascota");
		}
		else if (petmascotahomenaje.getFechanacimiento()==null &&  petmascotahomenaje.getFechanacimiento().after(fechaactual) ){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Fecha de nacimiento debe ser menor o igual a la fecha actual");
		}
		else if (petmascotahomenaje.getFechafallecimiento()==null &&  petmascotahomenaje.getFechafallecimiento().after(fechaactual) ){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Fecha de fallecimiento debe ser menor o igual a la fecha actual");
		}
		else if (petmascotahomenaje.getFechanacimiento().after(petmascotahomenaje.getFechafallecimiento())){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Fecha de nacimiento debe ser menor o igual a la fecha de fallecimiento");
		}
		else if (petmascotahomenaje.getFechafallecimiento().before(petmascotahomenaje.getFechanacimiento())){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Fecha de fallecimiento debe ser menor o igual a la fecha de nacimiento");
		}
		return ok;
		
	}

	public Petmascotahomenaje getPetmascotahomenaje() {
		return petmascotahomenaje;
	}

	public void setPetmascotahomenaje(Petmascotahomenaje petmascotahomenaje) {
		this.petmascotahomenaje = petmascotahomenaje;
	}

	
}
