	package com.web.cementerio.bean;

	import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.web.cementerio.bo.PetmascotahomenajeBO;
import com.web.cementerio.pojo.annotations.Petespecie;
import com.web.cementerio.pojo.annotations.Petfotomascota;
import com.web.cementerio.pojo.annotations.Petmascotahomenaje;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

	@ManagedBean
	@ViewScoped
	public class MascotaHomenajeAdminBean implements Serializable {

	
		private static final long serialVersionUID = -6818813140393382672L;
		
		private Petmascotahomenaje petmascotahomenaje;
		private Petmascotahomenaje petmascotahomenajeclone;
		private Petfotomascota     petfotomascotaselected;
		private StreamedContent streamedContent;
		private UploadedFile    uploadedFile;
		private int idmascota;
		private String rutaImagenes;
		private boolean fotoSubida;

	

		public MascotaHomenajeAdminBean() {
			inicializarobjetos();
			cargarRutaImagenes();
			
		}
		
		@PostConstruct
		public void initMascotaHomenajeAdminBean() {
			FacesUtil facesUtil = new FacesUtil();
			idmascota = Integer.parseInt(facesUtil.getParametroUrl("idmascota").toString());
			
			if(idmascota > 0){
				consultar();
				clonarobjetos();
			}
		}
		
		
		
		public void inicializarobjetos(){
			petmascotahomenaje = new Petmascotahomenaje(0, new Setestado(), new Setusuario(), new Petespecie(), null, null, null, null, null, null, null, null, null, null, null, null );
			petmascotahomenaje.setPetespecie(new Petespecie());
			petmascotahomenaje.setFechapublicacion(new Date());
			petfotomascotaselected = new Petfotomascota(0,new Setestado(),new Petmascotahomenaje(),new Setusuario(),null,null,null,0,null,null,null);
			rutaImagenes ="";
			idmascota =0;
			fotoSubida=false;
			streamedContent = null;
			uploadedFile = null;
		}
		
		private void cargarRutaImagenes(){
			try {
				rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
			} catch (Exception e) {
				e.printStackTrace();
				new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
		
		public void  grabar() {
			try {
				if(validarcampos()){
					
					PetmascotahomenajeBO petmascotahomenajeBO = new PetmascotahomenajeBO();
					
					if(petmascotahomenaje.getIdmascota()==0){
						petmascotahomenajeBO.ingresarPetmascotahomenajeBO(petmascotahomenaje, 1,uploadedFile);
				    }else if(petmascotahomenaje.getIdmascota()>0){
					  //objeto petmascotahomenaje se ha modificado
					  petmascotahomenajeBO.modificarPetmascotahomenajeBO(petmascotahomenaje,petmascotahomenajeclone,uploadedFile,2);
						 
				  }
					inicializarobjetos();
					FacesUtil facesUtil = new FacesUtil();
					facesUtil.redirect("../pages/mascotashomenaje.jsf");
				}
			} catch (Exception e) {
				e.printStackTrace();
				new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
			
		}
		
		
		public void handleFileUpload(FileUploadEvent event) {
			try{
				//Tamaño imagen menor a 100KB
				if (event.getFile().getSize() < 100000){
					uploadedFile = event.getFile();
					streamedContent = new DefaultStreamedContent(event.getFile().getInputstream(), event.getFile().getContentType());
					
					FacesUtil facesUtil = new FacesUtil();
					UsuarioBean usuarioBean = (UsuarioBean)facesUtil.getSessionBean("usuarioBean");
					usuarioBean.setStreamedContent(streamedContent);
					facesUtil.setSessionBean("usuarioBean", usuarioBean);
					fotoSubida = true;
					
					new MessageUtil().showInfoMessage("Foto en memoria!",uploadedFile.getFileName());
				}else{
					new MessageUtil().showErrorMessage("Error","Tamaño de la imagen no puede ser mayor a 100KB");
				}
				
				
			}catch(Exception x){
				x.printStackTrace();
				new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
		public void borrarFotoSubida(){
			streamedContent = null;
			uploadedFile = null;
			fotoSubida = false;
		}
		
		public void ponerFotoperfil(){
			if (petfotomascotaselected !=null){
				petmascotahomenaje.setRutafoto(petfotomascotaselected.getRuta());
				new MessageUtil().showInfoMessage("Info", "Foto: "+petfotomascotaselected.getRuta()+" seleccionada como foto de perfil");
				petfotomascotaselected= new Petfotomascota();
			}
		}
		
		public void quitarFoto(){
			if (petfotomascotaselected !=null){
				if (!petfotomascotaselected.getRuta().equals(petmascotahomenaje.getRutafoto())){
					petmascotahomenaje.getPetfotomascotas().remove(petfotomascotaselected);
					new MessageUtil().showInfoMessage("Info", "Foto: "+petfotomascotaselected.getNombrearchivo()+" ha sido eliminada de la galería");	
				}
				else {
					new MessageUtil().showInfoMessage("Info", "No se puede eliminar foto que ha sido seleccionada como foto de perfil, cambie de foto de perfil y vuelva a intentarlo");
				}
				petfotomascotaselected= new Petfotomascota();
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

		public void consultar(){
			try {
				PetmascotahomenajeBO mascotaHomenajeBO= new PetmascotahomenajeBO();
				petmascotahomenaje = mascotaHomenajeBO.getPetmascotahomenajebyId(idmascota, 1,true);
				
			} catch (Exception e) {
		      e.printStackTrace();
		      new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
			
		}
		
		
		public void clonarobjetos(){
			try {
				petmascotahomenajeclone = petmascotahomenaje.clonar();
				petmascotahomenajeclone.setPetfotomascotas( new HashSet<Petfotomascota>(0) );
				if((!petmascotahomenaje.getPetfotomascotas().isEmpty()) && (petmascotahomenaje.getPetfotomascotas().size()>0)){
					for(Petfotomascota petfotomascota:petmascotahomenaje.getPetfotomascotas()){
						petmascotahomenajeclone.getPetfotomascotas().add(petfotomascota);
						
					}
				}
				
				} catch (Exception e) {
					e.printStackTrace();
					new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
				}
			
		}
		
		
		public String eliminar(){
			String paginaRetorno = null;
			
			try{
				PetmascotahomenajeBO petmascotahomenajeBO = new PetmascotahomenajeBO();
				petmascotahomenajeBO.eliminarPetmascotahomenajeBO(petmascotahomenaje, petmascotahomenaje.getPetfotomascotas(), 2);
				inicializarobjetos();
				paginaRetorno = "/pages/mascotashomenaje?faces-redirect=true";
				
			}catch(Exception e){
				e.printStackTrace();
				new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
			
			return paginaRetorno;
		}

     
		
		public Petmascotahomenaje getPetmascotahomenaje() {
			return petmascotahomenaje;
		}

		public void setPetmascotahomenaje(Petmascotahomenaje petmascotahomenaje) {
			this.petmascotahomenaje = petmascotahomenaje;
		}


		public Petfotomascota getPetfotomascotaselected() {
			return petfotomascotaselected;
		}


		public void setPetfotomascotaselected(Petfotomascota petfotomascotaselected) {
			this.petfotomascotaselected = petfotomascotaselected;
		}

		
		public Petmascotahomenaje getPetmascotahomenajeclone() {
			return petmascotahomenajeclone;
		}


		public void setPetmascotahomenajeclone(
				Petmascotahomenaje petmascotahomenajeclone) {
			this.petmascotahomenajeclone = petmascotahomenajeclone;
		}

		public int getIdmascota() {
			return idmascota;
		}


		public void setIdmascota(int idmascota) {
			this.idmascota = idmascota;
		}

		public String getRutaImagenes() {
			return rutaImagenes;
		}

		public void setRutaImagenes(String rutaImagenes) {
			this.rutaImagenes = rutaImagenes;
		}

		public StreamedContent getStreamedContent() {
			return streamedContent;
		}

		public void setStreamedContent(StreamedContent streamedContent) {
			this.streamedContent = streamedContent;
		}

		public UploadedFile getUploadedFile() {
			return uploadedFile;
		}

		public void setUploadedFile(UploadedFile uploadedFile) {
			this.uploadedFile = uploadedFile;
		}

		public boolean isFotoSubida() {
			return fotoSubida;
		}

		public void setFotoSubida(boolean fotoSubida) {
			this.fotoSubida = fotoSubida;
		}

		
		
		
	}

	

