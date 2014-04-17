	package com.web.cementerio.bean;

	import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;




	import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

	import com.web.cementerio.bo.PetfotomascotaBO;
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
		private Petfotomascota petfotomascotaselected;
		private Petfotomascota petfotomascota;
		private Petfotomascota petfotomascotabuscar;
		private List<Petfotomascota> listpetfotomascota;
		private List<Petfotomascota> listpetfotomascotaclone;
		private List<Petfotomascota> listpetfotomascotaagregar;
		private List<Petfotomascota> listpetfotomascotaeliminar;
		private StreamedContent streamedContent;
		private int indice;
		private int indiceagregar;
		private int indiceeliminar;
		private int idmascota;
		private String rutaImagenes;
		private boolean fotoSubida;

	

		public MascotaHomenajeAdminBean() {
			cargarRutaImagenes();
			inicializarobjetos();
			
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
			Petespecie petespecie = new Petespecie();
			petmascotahomenaje.setPetespecie(petespecie);
			petmascotahomenaje.setFechapublicacion(new Date());
			petfotomascotaselected = new Petfotomascota(0,new Setestado(),new Petmascotahomenaje(),new Setusuario(),null,null,null,0,null,null,null);
			petfotomascota = new Petfotomascota(0,new Setestado(),new Petmascotahomenaje(),new Setusuario(),null,null,null,0,null,null,null);
			petfotomascotabuscar = new Petfotomascota(0,new Setestado(),new Petmascotahomenaje(),new Setusuario(),null,null,null,0,null,null,null);
			listpetfotomascota = new ArrayList<Petfotomascota>();
			listpetfotomascotaagregar = null;
			listpetfotomascotaeliminar = null;
			rutaImagenes ="";
			indice =0;
			indiceeliminar =0;
			indiceagregar =0;
			idmascota =0;
			fotoSubida=false;

		
		}
		
		private void cargarRutaImagenes(){
			try {
				rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
			} catch (Exception e) {
				e.printStackTrace();
				new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
		public void grabar() {
			try {
				if(validarcampos()){
					PetmascotahomenajeBO petmascotahomenajeBO = new PetmascotahomenajeBO();
					
					if(petmascotahomenaje.getIdmascota()==0){
						petmascotahomenajeBO.ingresarPetmascotahomenajeBO(petmascotahomenaje, listpetfotomascota, 1);
						new MessageUtil().showInfoMessage("Exito", "Información registrada");
						inicializarobjetos();
						
				    }else if(petmascotahomenaje.getIdmascota()>0){
					  //objeto petmascotahomenaje se ha modificado
					  if(!petmascotahomenaje.equals(petmascotahomenajeclone)){
						 petmascotahomenajeBO.modificarPetmascotahomenajeBO(petmascotahomenaje, 1);
						
						 if(!listpetfotomascotaagregar.isEmpty()){
							 PetfotomascotaBO petfotomascotaBO = new PetfotomascotaBO();
							 petfotomascotaBO.ingresarPetfotomascotaBO(listpetfotomascotaagregar, 1, petmascotahomenaje);
							 
						 }
						 if(!listpetfotomascotaeliminar.isEmpty()){
							 PetfotomascotaBO petfotomascotaBO = new PetfotomascotaBO();
							 petfotomascotaBO.modificarPetfotomascotaBO(listpetfotomascotaeliminar, 2, petmascotahomenaje);
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
				   
					if (listpetfotomascota.isEmpty()){
						indice =0;
					}
					petfotomascota = new Petfotomascota();
					petfotomascota.setIdfotomascota(indice);
					petfotomascota.setNombrearchivo(event.getFile().getFileName());
					petfotomascota.setRuta("/resources/images/"+event.getFile().getFileName());
					listpetfotomascota.add(indice, petfotomascota);
					
				    if(petmascotahomenaje.getIdmascota() >0){
				    	agregarfotoxmodificacion(petfotomascota.getNombrearchivo());
				    	
				    }
				    indice ++;
				    new MessageUtil().showInfoMessage("Info", "Foto: "+event.getFile().getFileName()+" subida con éxito");
				}else{
				  new MessageUtil().showInfoMessage("Info", "El tamaño de la imagen debe ser menor a 1MB");
				}
				
			}
		}
		
		public void handleFileUpload(FileUploadEvent event) {
			try{
				streamedContent = new DefaultStreamedContent(event.getFile().getInputstream(), event.getFile().getContentType());
				
				FacesUtil facesUtil = new FacesUtil();
				UsuarioBean usuarioBean = (UsuarioBean)facesUtil.getSessionBean("usuarioBean");
				usuarioBean.setStreamedContent(streamedContent);
				facesUtil.setSessionBean("usuarioBean", usuarioBean);
				fotoSubida = true;
				
				new MessageUtil().showInfoMessage("Foto en memoria!", event.getFile().getFileName());
			}catch(Exception x){
				x.printStackTrace();
				new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
		public void borrarFotoSubida(){
			streamedContent = null;
			fotoSubida = false;
		}
		
		public void ponerFotoperfil(){
			if (petfotomascotaselected !=null){
				petmascotahomenaje.setRutafoto(petfotomascotaselected.getRuta());
				new MessageUtil().showInfoMessage("Info", "Foto: "+petfotomascotaselected.getNombrearchivo()+" seleccionada como foto de perfil");
			}
		}
		
		public void quitarFoto(){
			if (petfotomascotaselected !=null){
				if (!petfotomascotaselected.getRuta().equals(petmascotahomenaje.getRutafoto())){
					if(petmascotahomenaje.getIdmascota()==0){
					   listpetfotomascota.remove(petfotomascotaselected);
					}
					else{
					    eliminarfotoxmodificacion(petfotomascotaselected.getNombrearchivo()) ;
					}
					new MessageUtil().showInfoMessage("Info", "Foto: "+petfotomascotaselected.getNombrearchivo()+" ha sido eliminada de la galería");	
				}
				else {
					new MessageUtil().showInfoMessage("Info", "No se puede eliminar foto que ha sido seleccionada como foto de perfil, cambie de foto de perfil y vuelva a intentarlo");
				}
			}
			
		}
		
		public void agregarfotoxmodificacion(String nombre){
			if (listpetfotomascotaagregar.isEmpty()){
				indiceagregar =0;
			}
			if(buscarobjetolista(nombre, listpetfotomascotaclone)){
				if(buscarobjetolista(nombre, listpetfotomascotaeliminar)){
					listpetfotomascotaeliminar.remove(petfotomascotabuscar);
					indiceeliminar--;
				}
			}else{
				listpetfotomascotaagregar.add(indiceagregar, petfotomascota);
				indiceagregar++;
			}
			//listpetfotomascota.add(indice, petfotomascota);
			//indice++;
			
		}
		
		public void eliminarfotoxmodificacion(String nombre){
			if (listpetfotomascotaeliminar.isEmpty()){
				indiceeliminar =0;
			}
			if(buscarobjetolista(nombre, listpetfotomascotaclone)){
			  listpetfotomascotaeliminar.add(indiceeliminar,petfotomascotaselected);
			  indiceeliminar++;
			}
			if(buscarobjetolista(nombre, listpetfotomascotaagregar)){
			   listpetfotomascotaagregar.remove(petfotomascotabuscar);
			   indiceagregar--;
			}
			listpetfotomascota.remove(petfotomascotaselected);
			indice--;
		}
		
		//buscar en la lista a enviar la imagen que se agregara o eliminara
		public boolean buscarobjetolista(String nombre, List<Petfotomascota> listPetfotomascotas){
			petfotomascotabuscar = new Petfotomascota();
			boolean encontro = false;
			if(!listPetfotomascotas.isEmpty()){
			   for(Petfotomascota petfotomascotalista :listPetfotomascotas){
				 if(petfotomascotalista.getNombrearchivo().equals(nombre)){
					encontro= true; 
					petfotomascotabuscar= petfotomascotalista;
					return encontro;
				 }
			   }
			}	
		return encontro;
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
				petmascotahomenaje = new Petmascotahomenaje();
				petmascotahomenaje = mascotaHomenajeBO.getPetmascotahomenajebyId(idmascota, 1,true);
				
				listpetfotomascota = new ArrayList<Petfotomascota>();
				PetfotomascotaBO petfotomascotaBO = new PetfotomascotaBO();
				listpetfotomascota = petfotomascotaBO.getListpetfotomascota(this.idmascota, 1);
				
			} catch (Exception e) {
		      e.printStackTrace();
		      new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
			
		}
		
		
		public void clonarobjetos(){
			try {
				listpetfotomascotaagregar  = new ArrayList<Petfotomascota>();
				listpetfotomascotaeliminar = new ArrayList<Petfotomascota>();
				listpetfotomascotaclone = new ArrayList<Petfotomascota>();
				petmascotahomenajeclone = petmascotahomenaje.clonar();
				indice =0;
				if(!listpetfotomascota.isEmpty()){
					for(Petfotomascota petfotomascota:listpetfotomascota){
						listpetfotomascotaclone.add(petfotomascota.clonar());
						indice++;
					}
				}
				
				} catch (Exception e) {
					e.printStackTrace();
					new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
				}
			
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


		public Petfotomascota getPetfotomascotabuscar() {
			return petfotomascotabuscar;
		}


		public void setPetfotomascotabuscar(Petfotomascota petfotomascotabuscar) {
			this.petfotomascotabuscar = petfotomascotabuscar;
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

		public List<Petfotomascota> getListpetfotomascotaclone() {
			return listpetfotomascotaclone;
		}


		public void setListpetfotomascotaclone(
				List<Petfotomascota> listpetfotomascotaclone) {
			this.listpetfotomascotaclone = listpetfotomascotaclone;
		}


		public int getIndiceagregar() {
			return indiceagregar;
		}


		public void setIndiceagregar(int indiceagregar) {
			this.indiceagregar = indiceagregar;
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

		public boolean isFotoSubida() {
			return fotoSubida;
		}

		public void setFotoSubida(boolean fotoSubida) {
			this.fotoSubida = fotoSubida;
		}

		
		
		
	}

	

