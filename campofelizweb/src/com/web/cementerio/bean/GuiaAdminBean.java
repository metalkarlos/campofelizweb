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
import org.primefaces.model.UploadedFile;

import com.web.cementerio.bo.PetguiaBO;

import com.web.cementerio.pojo.annotations.Petfotoguia;
import com.web.cementerio.pojo.annotations.Petguia;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class GuiaAdminBean  implements Serializable{
	
	private static final long serialVersionUID = -1314030580304673400L;
	private int idguia;
	private Petguia petguia;
	private Petguia petguiaClon;
	private String rutaImagenes;
	private List<Petfotoguia> lisPetfotoguia;
	private List<Petfotoguia> lisPetfotoguiaClon;
	private Petfotoguia petfotoguiaSeleccionada;
	private StreamedContent streamedContent;
	private UploadedFile uploadedFile;
	private String descripcionFoto;
	private boolean fotoSubida;
	private int indice;
	
	

	public GuiaAdminBean(){
		petguia = new Petguia(0, new Setestado(), new Setusuario(), null, null, null, null,null,null, new Date(), null,false, null);
		lisPetfotoguia = new ArrayList<Petfotoguia>();
		petfotoguiaSeleccionada =  new Petfotoguia(0, new Setestado(), new Setusuario(), new Petguia(), null, null, null, null, null, null, null);
		fotoSubida =false;
		descripcionFoto="";
		rutaImagenes="";
		idguia=0;
		cargarRutaImagenes();
	}
	
	 
	@PostConstruct
	public void initGuiaAdminBean() {
		FacesUtil facesUtil = new FacesUtil();
		//idguia = Integer.parseInt(facesUtil.getParametroUrl("idguia").toString());
		idguia= (facesUtil.getParametroUrl("idguia")==null?0:Integer.parseInt(facesUtil.getParametroUrl("idguia").toString()));
		if(idguia > 0){
			consultaGuia();
			
		}
	}
	
	private void cargarRutaImagenes(){
		try {
			rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void consultaGuia(){
		if(this.idguia > 0){
			try {
				petguiaClon = new Petguia(0, new Setestado(), new Setusuario(), null, null, null, null,null,null, null, null,false, null);
				lisPetfotoguiaClon = new ArrayList<Petfotoguia>();
				PetguiaBO petguiaBO = new PetguiaBO();
				petguia = petguiaBO.getPetguiabyId(idguia, 1);
				
				if(petguia != null && petguia.getIdguia() > 0){
					petguiaClon = petguia.clonar();
					
					if(petguia.getPetfotoguias() != null && !petguia.getPetfotoguias().isEmpty()){
						lisPetfotoguia = new ArrayList<Petfotoguia>(petguia.getPetfotoguias());
						
						for(Petfotoguia petfotoguia : lisPetfotoguia){
							lisPetfotoguiaClon.add(petfotoguia.clonar());
						}
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
				new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
	}

	
	public void handleFileUpload(FileUploadEvent event) {
		try{
			if (event.getFile().getSize() < 100000){
				uploadedFile = event.getFile();
				streamedContent = new DefaultStreamedContent(event.getFile().getInputstream(), event.getFile().getContentType());
				
				FacesUtil facesUtil = new FacesUtil();
				UsuarioBean usuarioBean = (UsuarioBean)facesUtil.getSessionBean("usuarioBean");
				usuarioBean.setStreamedContent(streamedContent);
				facesUtil.setSessionBean("usuarioBean", usuarioBean);
				fotoSubida = true;
				
				new MessageUtil().showInfoMessage("Foto en memoria!", event.getFile().getFileName());
			}else{
				new MessageUtil().showErrorMessage("Error","Tamaño de la imagen no puede ser mayor a 100KB");
			}	
		}catch(Exception x){
			x.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void ponerFotoPrincipal(){
		petguia.setRutafoto(petfotoguiaSeleccionada.getRuta());
		petfotoguiaSeleccionada = new Petfotoguia();
		new MessageUtil().showInfoMessage("Listo!", "Se ha seleccionado como foto principal");
	}
	
	public void quitarFotoGaleria(){
		lisPetfotoguia.remove(petfotoguiaSeleccionada);
		petfotoguiaSeleccionada = new Petfotoguia();
	}
	
	public void borrarFotoSubida(){
		streamedContent = null;
		uploadedFile = null;
		fotoSubida = false;
	}
	
	public void grabar(){
	 try{
		if(validarcampos()){
			boolean ok = false;
			
			PetguiaBO petguiaBO = new PetguiaBO();
				
			if(idguia == 0){
				ok = petguiaBO.ingresarPetguiaBO(petguia,1,  uploadedFile,descripcionFoto);
				petguia = new Petguia(0, new Setestado(), new Setusuario(), null, null, null, null,null,null, new Date(), null,false, null);
				lisPetfotoguia = new ArrayList<Petfotoguia>();
				petguiaClon= new Petguia(0, new Setestado(), new Setusuario(), null, null, null, null,null,null, new Date(), null,false, null);
				lisPetfotoguiaClon = new ArrayList<Petfotoguia>();
			}else{
				ok = petguiaBO.modificar(petguia, petguiaClon, lisPetfotoguia, lisPetfotoguiaClon,2,uploadedFile,descripcionFoto);
				petguia = new Petguia(0, new Setestado(), new Setusuario(), null, null, null, null,null,null, new Date(), null,false, null);
				lisPetfotoguia = new ArrayList<Petfotoguia>();
				petguiaClon= new Petguia(0, new Setestado(), new Setusuario(), null, null, null, null,null,null, new Date(), null,false, null);
				lisPetfotoguiaClon = new ArrayList<Petfotoguia>();
			}
				
			if(ok){
				new MessageUtil().showInfoMessage("Listo!", "Datos grabados con Exito!");
			}else{
				new MessageUtil().showInfoMessage("Aviso", "No existen cambios que guardar");
			}
		}
	}catch(Exception e){
		e.printStackTrace();
		new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
	  }
	}
	
	
	public boolean validarcampos(){
		boolean ok = true;
		Date fechaactual = new Date();
		if(petguia.getTitulo()==null|| petguia.getTitulo().length()==0){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Es necesario ingresar el Título del artículo");
		}
		/*else if((streamedContent ==null && petguia.getIdguia()==0)||(streamedContent ==null && lisPetfotoguia.size()==0 && petguia.getIdguia()>0)){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Es necesario subir foto asociada al artículo");
		}*/
		
		else if(petguia.getDescripcion()==null|| petguia.getDescripcion().length()==0){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Es necesario ingresar el contendio del artículo");
		}else if (petguia.getFechapublicacion().after(fechaactual)){
			ok = false;
			new MessageUtil().showInfoMessage("Info", "Fecha de publicación no pueder ser mayor a la fecha de hoy");
		}		
		return ok;
	}
	
	public String eliminar(){
		String paginaRetorno = null;
		
		try{
			PetguiaBO petguiaBO = new PetguiaBO();
			
			petguiaBO.eliminarBO(petguia, lisPetfotoguiaClon,2);
			
			petguia = new Petguia(0, new Setestado(), new Setusuario(), null, null, null, null,null,null, new Date(), null,false, null);
			petguiaClon= new Petguia(0, new Setestado(), new Setusuario(), null, null, null, null,null,null, new Date(), null,false, null);
			lisPetfotoguiaClon = new ArrayList<Petfotoguia>();
			paginaRetorno = "/pages/guiageneral?faces-redirect=true";
			
			
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
		return paginaRetorno;
	}

	public Petguia getPetguia() {
		return petguia;
	}

	public void setPetguia(Petguia petguia) {
		this.petguia = petguia;
	}

	public Petguia getPetguiaClon() {
		return petguiaClon;
	}

	public void setPetguiaClon(Petguia petguiaClon) {
		this.petguiaClon = petguiaClon;
	}

	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}

	public List<Petfotoguia> getLisPetfotoguia() {
		return lisPetfotoguia;
	}

	public void setLisPetfotoguia(List<Petfotoguia> lisPetfotoguia) {
		this.lisPetfotoguia = lisPetfotoguia;
	}

	public List<Petfotoguia> getLisPetfotoguiaClon() {
		return lisPetfotoguiaClon;
	}

	public void setLisPetfotoguiaClon(List<Petfotoguia> lisPetfotoguiaClon) {
		this.lisPetfotoguiaClon = lisPetfotoguiaClon;
	}

	public Petfotoguia getPetfotoguiaSeleccionada() {
		return petfotoguiaSeleccionada;
	}

	public void setPetfotoguiaSeleccionada(Petfotoguia petfotoguiaSeleccionada) {
		this.petfotoguiaSeleccionada = petfotoguiaSeleccionada;
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


	public int getIdguia() {
		return idguia;
	}


	public void setIdguia(int idguia) {
		this.idguia = idguia;
	}


	public int getIndice() {
		return indice;
	}


	public void setIndice(int indice) {
		this.indice = indice;
	}
	
	
}
