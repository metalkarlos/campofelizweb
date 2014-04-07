package com.web.cementerio.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

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
	private UploadedFile uploadedFile;

	public QuienesSomosBean() {
		petinformacion = new Petinformacion(0, new Setestado(), new Setusuario(), null, null, null, null, null, null,null, null, null, null, null);
		
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
	/*
	public void subirimagen(){
		if (uploadedFile !=null){
			petinformacion.setFotoquienessomos(uploadedFile.getFileName());
			new MessageUtil().showInfoMessage("Info", "Foto: "+uploadedFile.getFileName()+" subida con éxito");
			
		}
	}*/
	public void subir(FileUploadEvent event){
		//if (event.getFile() !=null){
			petinformacion.setFotoquienessomos("/resources/images/"+event.getFile().getFileName());
			new MessageUtil().showInfoMessage("Info", "Foto: "+event.getFile().getFileName()+" subida con éxito");
			
		//}
	}
	public void guardarPetinformacion(){
		try{
			 if (validarcampos()){
				if (petinformacion.getIdinformacion() > 0) {
					PetinformacionBO petinformacionBO = new PetinformacionBO();
					petinformacionBO.actualizarPetinformacion(petinformacion);
					
					new MessageUtil().showInfoMessage("Exito", "Registro actualizado");
				}
			 }else{
				 new MessageUtil().showInfoMessage("Informacion", "Es necesario ingresar toda la información");
			 }
				 
		}catch (Exception e){
			e.printStackTrace();
			new MessageUtil().showErrorMessage("Error", "Lamentamos que tenga inconvenientes");
		}
		
		
	}
	
	private boolean validarcampos(){
		boolean ok = true;
		 if(petinformacion.getQuienessomos()==null || petinformacion.getQuienessomos().length() ==0 ){
			ok = false;
		 } else if (petinformacion.getAntecendentes()==null || petinformacion.getAntecendentes().length() ==0 ){
		    ok = false;
		 } else if (petinformacion.getMision()==null || petinformacion.getMision().length() ==0 ){
			ok = false;
		 }else if (petinformacion.getVision()==null || petinformacion.getVision().length() ==0 ){
			ok = false;
		 }else if (petinformacion.getTag()==null || petinformacion.getTag().length() ==0 ){
			ok = false;	
		 }else if (petinformacion.getFotoantecedentes()==null || petinformacion.getFotoantecedentes().length() ==0 ){
			ok = false;	
		 }else if (petinformacion.getFotoquienessomos()==null || petinformacion.getFotoquienessomos().length() ==0 ){
			ok = false;	
		 }
		return ok;
	}
	
	public Petinformacion getPetinformacion() {
		return petinformacion;
	}

	public void setPetinformacion(Petinformacion petinformacion) {
		this.petinformacion = petinformacion;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	
	
	
	
}
