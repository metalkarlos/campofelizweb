package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.web.cementerio.bo.PetfotoinstalacionBO;
import com.web.cementerio.global.Parametro;
import com.web.cementerio.pojo.annotations.Petfotoinstalacion;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class CementerioVirtualAdminBean implements Serializable{

	private static final long serialVersionUID = -3312876102105882061L;
	private String rutaImagenes;
	private String descripcionFoto;
	private boolean fotoSubida;
	private UploadedFile uploadedFile;
	private StreamedContent streamedContent;
	private List<Petfotoinstalacion> listpetfotoinstalacion;
	private List<Petfotoinstalacion> listpetfotoinstalacionclone;
	private Petfotoinstalacion petfotoinstalacion;
	private Petfotoinstalacion petfotoseleccionada;
	
	
	public CementerioVirtualAdminBean(){
		petfotoinstalacion = new Petfotoinstalacion();
		petfotoseleccionada = new Petfotoinstalacion();
		cargarRutaImagenes();
		consultar();
	}
	
	
	public void consultar(){
	   try {
		    listpetfotoinstalacion      = new ArrayList<Petfotoinstalacion>();
		    listpetfotoinstalacionclone = new ArrayList<Petfotoinstalacion>();
		    
			PetfotoinstalacionBO petfotoinstalacioBO = new PetfotoinstalacionBO();
			listpetfotoinstalacion = petfotoinstalacioBO.lisPetfotoinstalacion(1);
				
			if(listpetfotoinstalacion != null && listpetfotoinstalacion.size()>0){
			   for(Petfotoinstalacion petfotoinstalacion : listpetfotoinstalacion){
				  listpetfotoinstalacionclone.add(petfotoinstalacion.clonar());
			   }
			}
		} catch(Exception e) {
		  e.printStackTrace();
		  new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		 }
	  
	}


	public void handleFileUpload(FileUploadEvent event) {
		try{
			if (event.getFile().getSize() <= Parametro.TAMAÑO_IMAGEN){
				uploadedFile = event.getFile();
				streamedContent = new DefaultStreamedContent(event.getFile().getInputstream(), event.getFile().getContentType());
				
				FacesUtil facesUtil = new FacesUtil();
				UsuarioBean usuarioBean = (UsuarioBean)facesUtil.getSessionBean("usuarioBean");
				usuarioBean.setStreamedContent(streamedContent);
				facesUtil.setSessionBean("usuarioBean", usuarioBean);
				fotoSubida = true;
				new MessageUtil().showInfoMessage("Presione Grabar para guardar los cambios.","");
			}else{
				new MessageUtil().showErrorMessage("Error","Tamaño de la imagen no puede ser mayor a 100KB");
			}	
		}catch(Exception x){
			x.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void quitarFotoGaleria(){
		if (petfotoseleccionada !=null){
			if (!petfotoseleccionada.getRuta().equals(petfotoinstalacion.getRuta())){
				listpetfotoinstalacion.remove(petfotoseleccionada);
				petfotoseleccionada = new Petfotoinstalacion();
				new MessageUtil().showInfoMessage("Presione Grabar para guardar los cambios.","");
			}
			else {
				new MessageUtil().showInfoMessage("Info", "No se puede eliminar foto que ha sido seleccionada como foto de perfil, cambie de foto de perfil y vuelva a intentarlo");
			}
		}		
	}
	
	public void borrarFotoSubida(){
		streamedContent = null;
		uploadedFile = null;
		fotoSubida = false;
	}
	
	public void grabar(){
		 try{
			//if(validarcampos()){
				PetfotoinstalacionBO petfotoinstalacionBO = new PetfotoinstalacionBO();
				petfotoinstalacionBO.procesar(uploadedFile, descripcionFoto, petfotoinstalacion, listpetfotoinstalacion, listpetfotoinstalacionclone);
				petfotoinstalacion = new Petfotoinstalacion(0,new Setestado(), new Setusuario() ,null, null, null, new Date(), new Date(), 0, null);
				listpetfotoinstalacion      = new ArrayList<Petfotoinstalacion>();
			    listpetfotoinstalacionclone = new ArrayList<Petfotoinstalacion>();

				FacesUtil facesUtil = new FacesUtil();
				facesUtil.redirect("../pages/cementeriovirtual.jsf");	 
			//}
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		  }
		}
		
		/*
		public boolean validarcampos(){
			boolean ok = true;
			if(petfotoinstalacion==null|| petguia.getTitulo().length()==0){
				ok = false;
				new MessageUtil().showInfoMessage("Info", "Es necesario ingresar el Título del artículo");
			}
			else if(textodescripcion==null|| textodescripcion.length()==0){
				ok = false;
				new MessageUtil().showInfoMessage("Info", "Es necesario ingresar el contendio del artículo");
			}else if (petguia.getFechapublicacion().after(fechaactual)){
				ok = false;
				new MessageUtil().showInfoMessage("Info", "Fecha de publicación no pueder ser mayor a la fecha de hoy");
			}		
			return ok;
		}*/

	public String getRutaImagenes() {
		return rutaImagenes;
	}


	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}


	public String getDescripcionFoto() {
		return descripcionFoto;
	}


	public void setDescripcionFoto(String descripcionFoto) {
		this.descripcionFoto = descripcionFoto;
	}


	public boolean isFotoSubida() {
		return fotoSubida;
	}


	public void setFotoSubida(boolean fotoSubida) {
		this.fotoSubida = fotoSubida;
	}


	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}


	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}


	public StreamedContent getStreamedContent() {
		return streamedContent;
	}


	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}


	public List<Petfotoinstalacion> getListpetfotoinstalacion() {
		return listpetfotoinstalacion;
	}


	public void setListpetfotoinstalacion(
			List<Petfotoinstalacion> listpetfotoinstalacion) {
		this.listpetfotoinstalacion = listpetfotoinstalacion;
	}


	public List<Petfotoinstalacion> getListpetfotoinstalacionclone() {
		return listpetfotoinstalacionclone;
	}


	public void setListpetfotoinstalacionclone(
			List<Petfotoinstalacion> listpetfotoinstalacionclone) {
		this.listpetfotoinstalacionclone = listpetfotoinstalacionclone;
	}


	public Petfotoinstalacion getPetfotoinstalacion() {
		return petfotoinstalacion;
	}


	public void setPetfotoinstalacion(Petfotoinstalacion petfotoinstalacion) {
		this.petfotoinstalacion = petfotoinstalacion;
	}


	public Petfotoinstalacion getPetfotoseleccionada() {
		return petfotoseleccionada;
	}


	public void setPetfotoseleccionada(Petfotoinstalacion petfotoseleccionada) {
		this.petfotoseleccionada = petfotoseleccionada;
	}


	private void cargarRutaImagenes(){
		try {
			rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
}
