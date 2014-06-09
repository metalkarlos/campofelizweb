package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.web.cementerio.bo.PetinformacionBO;
import com.web.cementerio.pojo.annotations.Petfotoinformacion;
import com.web.cementerio.pojo.annotations.Petinformacion;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;


@ManagedBean
@ViewScoped
public class QuienesSomosAdminBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8926932712521876552L;

	private Petinformacion petinformacion;
	private Petinformacion petinformacionclone;
	private Petfotoinformacion petfotoinformacionselected;
	private List<Petfotoinformacion> listPetfotoinformacionclone;
	private UploadedFile    uploadedFile;
	private StreamedContent streamedContent;
	private boolean fotoSubida;
	private int idinformacion;
	private String rutaImagenes;
	private String descripcionImagen;
	
	public QuienesSomosAdminBean(){
		inicializarobjetos();
		cargarRutaImagenes();
	}
	
	
	public void inicializarobjetos(){
		petinformacion= new Petinformacion(0,new Setestado(),new Setusuario(),null,null,null,null,null,null,null,null,null,null,null,null);
		petinformacionclone= new Petinformacion(0,new Setestado(),new Setusuario(),null,null,null,null,null,null,null,null,null,null,null,null);
		petfotoinformacionselected = new Petfotoinformacion();
		descripcionImagen = "";
		rutaImagenes = "";
		fotoSubida = false;
		uploadedFile =null;
		streamedContent = null;
	}
	
	
	private void cargarRutaImagenes(){
		try {
			rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	
	@PostConstruct
	public void initQuienesSomosAdminBean() {
		FacesUtil facesUtil = new FacesUtil();
		idinformacion= Integer.parseInt(facesUtil.getParametroUrl("idinformacion").toString());
		
		if(idinformacion > 0){
			consultar();
			clonarobjetos();
		}
	}
	
	
	public void clonarobjetos(){
		try{
			petinformacionclone= new Petinformacion(0,new Setestado(),new Setusuario(),null,null,null,null,null,null,null,null,null,null,null,null);
			if(petinformacion!=null){
				petinformacionclone = petinformacion.clonar();
			}
			if((petinformacion.getPetfotoinformaciones().size()>0) && (!petinformacion.getPetfotoinformaciones().isEmpty())){
				listPetfotoinformacionclone = new ArrayList<Petfotoinformacion>();
				for(Petfotoinformacion petfotoinformacion:petinformacion.getPetfotoinformaciones()){
					listPetfotoinformacionclone.add(petfotoinformacion.clonar());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	
	
	public void consultar(){
		try {
			PetinformacionBO petinformacionBO= new PetinformacionBO();
			petinformacion = new Petinformacion();
			petinformacion = petinformacionBO.getPetinformacionById(idinformacion,1);
			
		} catch (Exception e) {
	      e.printStackTrace();
	      new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
	}
	
	public boolean validarcampos(){
		boolean ok = true;
		if(petinformacion.getQuienessomos()==null|| petinformacion.getQuienessomos().length()==0){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Es necesario ingresar el contenido de Quienes Somos");
		}else if(petinformacion.getMision()==null|| petinformacion.getMision().length()==0){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Es necesario ingresar el contenido de Misión");
			
		}else if(petinformacion.getVision()==null|| petinformacion.getVision().length()==0){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Es necesario ingresar el contenido de Visión");
		}
		return ok;
	}	
	
	public String  grabar() {
		String paginaRetorno = null;
		try {
			if(validarcampos()){
				
				PetinformacionBO petinformacionBO = new PetinformacionBO();
				
				if(petinformacion.getIdinformacion()>0){
				  //objeto petmascotahomenaje se ha modificado
				  petinformacionBO.modificarPetinformacion(petinformacion, petinformacionclone, listPetfotoinformacionclone, uploadedFile, 1, descripcionImagen);
				  inicializarobjetos();
			  }
			   paginaRetorno="/pages/quienessomos?faces-redirect=true"; 
			   //new MessageUtil().showInfoMessage("Listo!", "Datos grabados con Exito!");
			 
			}
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		return paginaRetorno;
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		try{
			
			if (event.getFile().getSize() < 1000000){
				uploadedFile = event.getFile();
				streamedContent = new DefaultStreamedContent(event.getFile().getInputstream(), event.getFile().getContentType());
				
				FacesUtil facesUtil = new FacesUtil();
				UsuarioBean usuarioBean = (UsuarioBean)facesUtil.getSessionBean("usuarioBean");
				usuarioBean.setStreamedContent(streamedContent);
				facesUtil.setSessionBean("usuarioBean", usuarioBean);
				fotoSubida = true;
				
				new MessageUtil().showInfoMessage("Foto en memoria!",uploadedFile.getFileName());
			}else{
				new MessageUtil().showErrorMessage("Error","Tamaño de la imagen no puede ser mayor a 1MG");
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
	
	public void quitarFoto(){
		if (petfotoinformacionselected !=null){
			if (!petfotoinformacionselected.getRuta().equals(petinformacion.getRutafoto())){
				petinformacion.getPetfotoinformaciones().remove(petfotoinformacionselected);
				new MessageUtil().showInfoMessage("Info", "Foto: "+petfotoinformacionselected.getNombrearchivo()+" ha sido eliminada de la galería");	
			}
			else {
				new MessageUtil().showInfoMessage("Info", "No se puede eliminar foto que ha sido seleccionada como foto de perfil, cambie de foto de perfil y vuelva a intentarlo");
			}
			petfotoinformacionselected= new Petfotoinformacion();
		}
		
	}
	
	public Petinformacion getPetinformacion() {
		return petinformacion;
	}


	public void setPetinformacion(Petinformacion petinformacion) {
		this.petinformacion = petinformacion;
	}


	public Petinformacion getPetinformacionclone() {
		return petinformacionclone;
	}


	public void setPetinformacionclone(Petinformacion petinformacionclone) {
		this.petinformacionclone = petinformacionclone;
	}

	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}

	public int getIdinformacion() {
		return idinformacion;
	}

	public void setIdinformacion(int idinformacion) {
		this.idinformacion = idinformacion;
	}


	public List<Petfotoinformacion> getListPetfotoinformacionclone() {
		return listPetfotoinformacionclone;
	}


	public void setListPetfotoinformacionclone(
			List<Petfotoinformacion> listPetfotoinformacionclone) {
		this.listPetfotoinformacionclone = listPetfotoinformacionclone;
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


	public Petfotoinformacion getPetfotoinformacionselected() {
		return petfotoinformacionselected;
	}


	public void setPetfotoinformacionselected(
			Petfotoinformacion petfotoinformacionselected) {
		this.petfotoinformacionselected = petfotoinformacionselected;
	}


	public boolean isFotoSubida() {
		return fotoSubida;
	}


	public void setFotoSubida(boolean fotoSubida) {
		this.fotoSubida = fotoSubida;
	}


	public String getDescripcionImagen() {
		return descripcionImagen;
	}


	public void setDescripcionImagen(String descripcionImagen) {
		this.descripcionImagen = descripcionImagen;
	}

	
}
