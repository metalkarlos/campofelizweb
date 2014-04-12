package com.web.cementerio.bean;



import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import com.web.cementerio.bo.PetmascotahomenajeBO;
import com.web.cementerio.pojo.annotations.Petmascotahomenaje;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class MascotaHomenajeConsultaBean  {

private List<Petmascotahomenaje> listpetmascotahomenaje = null; 
private int idespecie = 0;
private String nombre = null;

	public MascotaHomenajeConsultaBean(){
		consultarMascotaHomenaje(1);
		
	}
		
	public void navegaenlace(ActionEvent actionEvent){
		try {
			FacesContext context = FacesContext.getCurrentInstance();
					context.getExternalContext().redirect("mascotahomenajedetalle.jsf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void consultarMascotaHomenaje(int idestado){
		if(idestado >0){
			try {
				PetmascotahomenajeBO petmascotahomenajeBO = new PetmascotahomenajeBO();
				listpetmascotahomenaje = petmascotahomenajeBO.getListpetmascotahomenaje(idestado);
				
			} catch (Exception e) {
				e.printStackTrace();
				new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
		
	}
	
    public void buscarmascotahomenajebycriteria(){
    	if ((idespecie > 0) && ( (!nombre.equals(null)) && (nombre.length() > 0))){
    		try {
				listpetmascotahomenaje = new ArrayList<Petmascotahomenaje>();
	    		PetmascotahomenajeBO petmascotahomenajeBO = new PetmascotahomenajeBO();
	    		listpetmascotahomenaje = petmascotahomenajeBO.getListpetmascotahomenajebycriteria(1, idespecie, nombre);
    		} catch (Exception e) {
			   e.printStackTrace();
			   new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
    	}else{
    		new MessageUtil().showInfoMessage("Info", "Por favor ingrese el nombre y especie de la mascota a consultar");
    	}
		
	}

	public List<Petmascotahomenaje> getListpetmascotahomenaje() {
		return listpetmascotahomenaje;
	}

	public void setListpetmascotahomenaje(
			List<Petmascotahomenaje> listpetmascotahomenaje) {
		this.listpetmascotahomenaje = listpetmascotahomenaje;
	}

	public int getIdespecie() {
		return idespecie;
	}

	public void setIdespecie(int idespecie) {
		this.idespecie = idespecie;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	
	
	
}
