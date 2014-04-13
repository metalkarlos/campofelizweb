package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import org.primefaces.event.FileUploadEvent;

import com.web.cementerio.bo.PetfotomascotaBO;
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
	private Petmascotahomenaje petmascotahomenajeclone;
	private Petfotomascota petfotomascotaselected = null;
	private Petfotomascota petfotomascota = null;
	private List<Petfotomascota> listpetfotomascota = null;
	private List<Petfotomascota> listpetfotomascotaagregar = null;
	private List<Petfotomascota> listpetfotomascotaeliminar = null;
	private int indice = 0;
	private int indiceeliminar = 0;
	private int idmascota =0;
	private boolean editar =false;
	private int idusuario=0;

	
	
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
		listpetfotomascotaagregar = null;
		listpetfotomascotaeliminar = null;
		indice =0;
		indiceeliminar =0;
	}
	
	public void guardarHomenajemascota() {
		try {
			if(validarcampos()){
				PetmascotahomenajeBO petmascotahomenajeBO = new PetmascotahomenajeBO();
				if(editar  && petfotomascota.getIdfotomascota()==0){
					petmascotahomenajeBO.ingresarPetmascotahomenajeBO(petmascotahomenaje, listpetfotomascota, 1);
					new MessageUtil().showInfoMessage("Exito", "Información registrada");
					inicializarobjetos();
					
			    }else if(editar  && petfotomascota.getIdfotomascota()>0){
				  //objeto petmascotahomenaje se ha modificado
				  if(compararobjecto()){
					 petmascotahomenajeBO.modificarPetmascotahomenajeBO(petmascotahomenaje, 1);
					
					 if(!listpetfotomascotaagregar.isEmpty()){
						 PetfotomascotaBO petfotomascotaBO = new PetfotomascotaBO();
						 petfotomascotaBO.ingresarPetfotomascotaBO(listpetfotomascotaagregar, 1, petmascotahomenaje.getIdmascota());
						 
					 }
					 if(!listpetfotomascotaeliminar.isEmpty()){
						 PetfotomascotaBO petfotomascotaBO = new PetfotomascotaBO();
						 petfotomascotaBO.modificarPetfotomascotaBO(listpetfotomascotaeliminar, 2, petmascotahomenaje.getIdmascota());
					 }
					 new MessageUtil().showInfoMessage("Exito", "Información modificada");
					 inicializarobjetos();
				  }
			  }
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
			    if(editar){
			    	listpetfotomascotaagregar.add(indice,petfotomascota);
			    }
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
				if(editar){
			      listpetfotomascotaeliminar.add(indiceeliminar,petfotomascota);
			      indiceeliminar++;
			    }
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

	public void consultarfotosmascota(){
		try {
			
			listpetfotomascota = new ArrayList<Petfotomascota>();
			PetfotomascotaBO petfotomascotaBO = new PetfotomascotaBO();
			listpetfotomascota = petfotomascotaBO.getListpetfotomascota(this.idmascota, 1);
			
		} catch (Exception e) {
	      e.printStackTrace();
	      new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
	}
	
	
	
	public boolean compararobjecto(){
		boolean diferentes = false;
		if (petmascotahomenaje !=null && petmascotahomenajeclone !=null){
			if(!petmascotahomenaje.getNombre().equals(petmascotahomenajeclone.getNombre())){
				diferentes = true;
			}else if(!petmascotahomenaje.getFamilia().equals(petmascotahomenajeclone.getFamilia())){
				diferentes = true;
			}else if(!petmascotahomenaje.getFechanacimiento().equals(petmascotahomenajeclone.getFechanacimiento())){
				diferentes = true;
			}else if(!petmascotahomenaje.getFechafallecimiento().equals(petmascotahomenajeclone.getFechafallecimiento())){
				diferentes = true;
			}else if(!petmascotahomenaje.getFechapublicacion().equals(petmascotahomenajeclone.getFechapublicacion())){
				diferentes = true;
			}else if(!petmascotahomenaje.getDedicatoria().equals(petmascotahomenajeclone.getDedicatoria())){
				diferentes = true;
			}
		}
		return diferentes;
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

	
	public int getIndiceeliminar() {
		return indiceeliminar;
	}


	public void setIndiceeliminar(int indiceeliminar) {
		this.indiceeliminar = indiceeliminar;
	}
	
	public boolean isEditar() {
		return editar;
	}


	public int getIdusuario() {
		return idusuario;
	}


	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
		try {
			if(editar && petfotomascota.getIdfotomascota()>0){
				petmascotahomenajeclone = petmascotahomenaje.clonar();
				listpetfotomascotaagregar = new ArrayList<Petfotomascota>();
				listpetfotomascotaeliminar = new ArrayList<Petfotomascota>();
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}


	public void setEditar(boolean editar) {
		this.editar = editar;
        try {
			if(editar && petfotomascota.getIdfotomascota()>0){
				petmascotahomenajeclone = petmascotahomenaje.clonar();
				listpetfotomascotaagregar = new ArrayList<Petfotomascota>();
				listpetfotomascotaeliminar = new ArrayList<Petfotomascota>();
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}

	}

	
	public Petmascotahomenaje getPetmascotahomenajeclone() {
		return petmascotahomenajeclone;
	}


	public void setPetmascotahomenajeclone(
			Petmascotahomenaje petmascotahomenajeclone) {
		this.petmascotahomenajeclone = petmascotahomenajeclone;
	}


	public List<Petfotomascota> getListpetfotomascotaagregar() {
		return listpetfotomascotaagregar;
	}


	public void setListpetfotomascotaagregar(
			List<Petfotomascota> listpetfotomascotaagregar) {
		this.listpetfotomascotaagregar = listpetfotomascotaagregar;
	}


	public List<Petfotomascota> getListpetfotomascotaeliminar() {
		return listpetfotomascotaeliminar;
	}


	public void setListpetfotomascotaeliminar(
			List<Petfotomascota> listpetfotomascotaeliminar) {
		this.listpetfotomascotaeliminar = listpetfotomascotaeliminar;
	}


	public int getIdmascota() {
		return idmascota;
	}


	public void setIdmascota(int idmascota) {
		this.idmascota = idmascota;
		
		if(this.idmascota >0){
			try {
				PetmascotahomenajeBO mascotaHomenajeBO= new PetmascotahomenajeBO();
				petmascotahomenaje = new Petmascotahomenaje();
				petmascotahomenaje = mascotaHomenajeBO.getPetmascotahomenajebyId(idmascota, 1);
				consultarfotosmascota();
			} catch (Exception e) {
				e.printStackTrace();
				new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
	}

	

	


	


	
	
}
