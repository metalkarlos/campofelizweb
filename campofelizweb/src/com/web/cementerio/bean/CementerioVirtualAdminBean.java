package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
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
	private boolean ingreso;
	private boolean modificacion;
	private boolean fotosubida;
	private UploadedFile uploadedFile;
	private StreamedContent streamedContent;
	private Petfotoinstalacion petfotoinstalacion;
	private Petfotoinstalacion petfotoinstalacionclone;
	private int idfoto;
	
	
	public CementerioVirtualAdminBean(){
		petfotoinstalacion = new Petfotoinstalacion();
		petfotoinstalacionclone = new Petfotoinstalacion();
		idfoto =0;
		fotosubida = false;
		cargarRutaImagenes();
	}
	
	
	public void consultar(){
	   try {
	
		   PetfotoinstalacionBO petfotoinstalacioBO = new PetfotoinstalacionBO();
		   petfotoinstalacion = petfotoinstalacioBO.getPetfotoinstalacionbyId(idfoto, 1);
				
			if(petfotoinstalacion != null ){
			  petfotoinstalacionclone = petfotoinstalacion.clonar();
			   
			}
		} catch(Exception e) {
		  e.printStackTrace();
		  new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		 }
	  
	}


	
	@PostConstruct
	public void initCementerioVirtualAdminBean() {
		FacesUtil facesUtil = new FacesUtil();
		idfoto = Integer.parseInt(facesUtil.getParametroUrl("idfoto").toString());
		
		if(idfoto > 0){
			ingreso = true;
			modificacion=false;
			consultar();
		}else{
			ingreso = false;
			modificacion = true;
		}
	}
	
	
	
	public void handleFileUpload(FileUploadEvent event) {
		try{
			if (event.getFile().getSize() <= Parametro.TAMAÑO_IMAGEN){
				uploadedFile = event.getFile();
				streamedContent = new DefaultStreamedContent(event.getFile().getInputstream(), event.getFile().getContentType());
				fotosubida = true;
				FacesUtil facesUtil = new FacesUtil();
				UsuarioBean usuarioBean = (UsuarioBean)facesUtil.getSessionBean("usuarioBean");
				usuarioBean.setStreamedContent(streamedContent);
				facesUtil.setSessionBean("usuarioBean", usuarioBean);
				new MessageUtil().showInfoMessage("Presione Grabar para guardar los cambios.","");
			}else{
				new MessageUtil().showErrorMessage("Error","Tamaño de la imagen no puede ser mayor a 700KB");
			}	
		}catch(Exception x){
			x.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	
	public void borrarFotoSubida(){
		streamedContent = null;
		uploadedFile = null;
		ingreso = false;
		modificacion = true;
		fotosubida = false;
	}
	
	public void grabar(){
	 try{
		if(validarcampos()){
			PetfotoinstalacionBO petfotoinstalacionBO = new PetfotoinstalacionBO();
			//ingreso
			if(petfotoinstalacion.getIdfotoinstalacion()==0){
			  petfotoinstalacionBO.ingresarPetfotoinstalacion(1, petfotoinstalacion, uploadedFile);
			}
			else if(petfotoinstalacion.getIdfotoinstalacion()>0){
			  petfotoinstalacionBO.modificarPetfotoinstalacion(petfotoinstalacion, petfotoinstalacionclone);
			}
			petfotoinstalacion = new Petfotoinstalacion(0,new Setestado(), new Setusuario() ,null, null, null, new Date(), new Date(), 0, null);
			FacesUtil facesUtil = new FacesUtil();
			facesUtil.redirect("../pages/cementeriovirtual.jsf");	 
		}
	  }catch(Exception e){
		e.printStackTrace();
		new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
	  }
	}
		
	
	public void eliminar(){
	  try{
			PetfotoinstalacionBO petfotoinstalacionBO = new PetfotoinstalacionBO();
			//eliminacion
			if(petfotoinstalacion.getIdfotoinstalacion()>0){
			  petfotoinstalacionBO.eliminarPetfotoinstalacion(petfotoinstalacion, petfotoinstalacionclone, 2);
			}
			
			petfotoinstalacion = new Petfotoinstalacion(0,new Setestado(), new Setusuario() ,null, null, null, new Date(), new Date(), 0, null);
			petfotoinstalacionclone = new Petfotoinstalacion(0,new Setestado(), new Setusuario() ,null, null, null, new Date(), new Date(), 0, null);
			FacesUtil facesUtil = new FacesUtil();
			facesUtil.redirect("../pages/cementeriovirtual.jsf");	 
		
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		  }
		}
	
		public boolean validarcampos(){
			boolean ok = true;
			if(petfotoinstalacion.getDescripcion()==null|| petfotoinstalacion.getDescripcion().length()==0){
				ok = false;
				new MessageUtil().showInfoMessage("Info", "Es necesario ingresar la descripción de la foto a subir");
			}
			else if(petfotoinstalacion.getOrden()<=0){
				ok = false;
				new MessageUtil().showInfoMessage("Info", "Es necesario ingresar el orden de presentación de la foto a subir");
			}		
			return ok;
		}

		
	private void cargarRutaImagenes(){
	try {
			rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
		} catch (Exception e) {
		e.printStackTrace();
		new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
      }
	}

	public String getRutaImagenes() {
		return rutaImagenes;
	}


	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}


	public boolean isIngreso() {
		return ingreso;
	}


	public void setIngreso(boolean ingreso) {
		this.ingreso = ingreso;
	}


	public boolean isModificacion() {
		return modificacion;
	}


	public boolean isFotosubida() {
		return fotosubida;
	}


	public void setFotosubida(boolean fotosubida) {
		this.fotosubida = fotosubida;
	}


	public void setModificacion(boolean modificacion) {
		this.modificacion = modificacion;
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


	public Petfotoinstalacion getPetfotoinstalacion() {
		return petfotoinstalacion;
	}


	public void setPetfotoinstalacion(Petfotoinstalacion petfotoinstalacion) {
		this.petfotoinstalacion = petfotoinstalacion;
	}


	public int getIdfoto() {
		return idfoto;
	}


	public void setIdfoto(int idfoto) {
		this.idfoto = idfoto;
	}

	public Petfotoinstalacion getPetfotoinstalacionclone() {
		return petfotoinstalacionclone;
	}


	public void setPetfotoinstalacionclone(
			Petfotoinstalacion petfotoinstalacionclone) {
		this.petfotoinstalacionclone = petfotoinstalacionclone;
	}


	


}
