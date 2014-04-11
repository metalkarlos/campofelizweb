package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;

import com.web.cementerio.bo.PetmascotahomenajeBO;
import com.web.cementerio.pojo.annotations.Petespecie;
import com.web.cementerio.pojo.annotations.Petfotomascota;
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
	private List<Petfotomascota> listpetfotomascota = null;
	private Petfotomascota petfotomascotaselected = null;
	private Petfotomascota petfotomascota = null;
	private int indice = 0;

	
	
	
	
	public MascotaHomenajeBean() {
		inicializarobjetos();
	}
	
	
	public void inicializarobjetos(){
		petmascotahomenaje = new Petmascotahomenaje();
		Petespecie petespecie = new Petespecie();
		petmascotahomenaje.setPetespecie(petespecie);
		petmascotahomenaje.setFechapublicacion(new Date());
		petfotomascotaselected = new Petfotomascota(0,null,null,null,null,null,null,0,null,null);
		petfotomascota = new Petfotomascota(0,null,null,null,null,null,null,0,null,null);
		listpetfotomascota = new ArrayList<Petfotomascota>();
		indice =0;
	}
	
	public void ingresarHomenajemascota() {
		try {
			if(validarcampos()){
				PetmascotahomenajeBO petmascotahomenajeBO = new PetmascotahomenajeBO();
				petmascotahomenajeBO.ingresarPetmascotahomenajeBO(petmascotahomenaje, listpetfotomascota, 1);
				new MessageUtil().showInfoMessage("Exito", "Información registrada");
				inicializarobjetos();
			}
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showErrorMessage("Error", "Lamentamos que tenga inconvenietnes");
		}
	}
	
	
	public void subir(FileUploadEvent event){
		if (event.getFile() !=null){
			long tamaño = event.getFile().getSize();
			if (tamaño<100000) {
			   
				if (listpetfotomascota.size()==0){
					indice =0;
				}
				petfotomascota = new Petfotomascota();
				petfotomascota.setIdfotomascota(indice);
				petfotomascota.setNombrearchivo(event.getFile().getFileName());
				petfotomascota.setRuta("/resources/images/"+event.getFile().getFileName());
				listpetfotomascota.add(indice, petfotomascota);
			   
			   indice ++;
			   new MessageUtil().showInfoMessage("Info", "Foto: "+event.getFile().getFileName()+" subida con éxito");
			   
			}else{
			  new MessageUtil().showInfoMessage("Info", "El tamaño de la imagen debe ser menor a 1MB");
			}
			
		}
	}
	
	public void setearFotoperfil(){
		if (petfotomascotaselected !=null){
			petmascotahomenaje.setRutafoto(petfotomascotaselected.getRuta());
			new MessageUtil().showInfoMessage("Info", "Foto: "+petfotomascotaselected.getNombrearchivo()+" seleccionada como foto de perfil");
		}
	}
	
	public void eliminarFoto(){
		if (petfotomascotaselected !=null){
			if (!petfotomascotaselected.getRuta().equals(petmascotahomenaje.getRutafoto())){
				listpetfotomascota.remove(petfotomascotaselected.getIdfotomascota());
				new MessageUtil().showInfoMessage("Info", "Foto: "+petfotomascotaselected.getNombrearchivo()+" ha sido eliminada de la galería");	
			}
			else {
				new MessageUtil().showInfoMessage("Info", "No se puede eliminar foto que ha sido seleccionada como foto de perfil, cambie de foto de perfil y vuelva a intentarlo");
			}
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
			new MessageUtil().showInfoMessage("Info", "Fecha de fallecimiento debe ser mayor o igual a la fecha de nacimiento");
		}
		else if (petmascotahomenaje.getFechapublicacion().before(petmascotahomenaje.getFechanacimiento())){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Fecha de publicación debe ser mayor o igual a la fecha de fallecimiento");
		}
		else if (petmascotahomenaje.getFechapublicacion().before(petmascotahomenaje.getFechafallecimiento())){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Fecha de publicación debe ser mayor o igual a la fecha de fallecimiento");
		}
		else if (petmascotahomenaje.getFechapublicacion().after(fechaactual)){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Fecha de publicación no pueder ser mayor a la fecha de hoy");
		}
		return ok;
		
	}

	public Petmascotahomenaje getPetmascotahomenaje() {
		return petmascotahomenaje;
	}

	public void setPetmascotahomenaje(Petmascotahomenaje petmascotahomenaje) {
		this.petmascotahomenaje = petmascotahomenaje;
	}


	public List<Petfotomascota> getListpetfotomascota() {
		return listpetfotomascota;
	}


	public void setListpetfotomascota(List<Petfotomascota> listpetfotomascota) {
		this.listpetfotomascota = listpetfotomascota;
	}


	public Petfotomascota getPetfotomascotaselected() {
		return petfotomascotaselected;
	}


	public void setPetfotomascotaselected(Petfotomascota petfotomascotaselected) {
		this.petfotomascotaselected = petfotomascotaselected;
	}


	public Petfotomascota getPetfotomascota() {
		return petfotomascota;
	}


	public void setPetfotomascota(Petfotomascota petfotomascota) {
		this.petfotomascota = petfotomascota;
	}


	public int getIndice() {
		return indice;
	}


	public void setIndice(int indice) {
		this.indice = indice;
	}


	


	


	
	
}
