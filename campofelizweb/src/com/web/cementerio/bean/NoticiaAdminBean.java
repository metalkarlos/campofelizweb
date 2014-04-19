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

import com.web.cementerio.bo.PetnoticiaBO;
import com.web.cementerio.pojo.annotations.Petfotonoticia;
import com.web.cementerio.pojo.annotations.Petnoticia;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class NoticiaAdminBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4748535305651371565L;
	private int idnoticia;
	private Petnoticia petnoticia;
	private Petnoticia petnoticiaClon;
	private String rutaImagenes;
	private List<Petfotonoticia> lisPetfotonoticia;
	private List<Petfotonoticia> lisPetfotonoticiaClon;
	private Petfotonoticia petfotonoticiaSeleccionada;
	private StreamedContent streamedContent;
	private UploadedFile uploadedFile;
	private String descripcionFoto;
	private boolean fotoSubida;
	
	public NoticiaAdminBean() {
		petnoticia = new Petnoticia();
		petnoticiaClon = new Petnoticia();
		lisPetfotonoticia = new ArrayList<Petfotonoticia>();
		lisPetfotonoticiaClon = new ArrayList<Petfotonoticia>();
		petfotonoticiaSeleccionada = new Petfotonoticia();
		descripcionFoto = "";
		fotoSubida = false;
		
		cargarRutaImagenes();
	}
	
	@PostConstruct
	public void initNoticiaAdminBean() {
		FacesUtil facesUtil = new FacesUtil();
		idnoticia = Integer.parseInt(facesUtil.getParametroUrl("idnoticia").toString());
		
		if(idnoticia > 0){
			consultaNoticia();
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
	
	public void consultaNoticia(){
		if(this.idnoticia > 0){
			try {
				PetnoticiaBO petnoticiaBO = new PetnoticiaBO();
				petnoticia = petnoticiaBO.getPetnoticiaConObjetosById(idnoticia);
				
				if(petnoticia != null && petnoticia.getIdnoticia() > 0){
					petnoticiaClon = petnoticia.clonar();
					
					if(petnoticia.getPetfotonoticias() != null && petnoticia.getPetfotonoticias().size() > 0){
						lisPetfotonoticia = new ArrayList<Petfotonoticia>(petnoticia.getPetfotonoticias());
						
						for(Petfotonoticia petfotonoticia : lisPetfotonoticia){
							lisPetfotonoticiaClon.add(petfotonoticia.clonar());
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
			uploadedFile = event.getFile();
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
	
	public void ponerFotoPrincipal(){
		petnoticia.setRutafoto(petfotonoticiaSeleccionada.getRuta());
		petfotonoticiaSeleccionada = new Petfotonoticia();
		new MessageUtil().showInfoMessage("Listo!", "Se ha seleccionado como foto principal");
	}
	
	public void quitarFotoGaleria(){
		lisPetfotonoticia.remove(petfotonoticiaSeleccionada);
		petfotonoticiaSeleccionada = new Petfotonoticia();
	}
	
	public void borrarFotoSubida(){
		streamedContent = null;
		uploadedFile = null;
		fotoSubida = false;
	}
	
	public void grabar(){
		try{
			boolean ok = false;
			
			PetnoticiaBO petnoticiaBO = new PetnoticiaBO();
			Petfotonoticia petfotonoticia = new Petfotonoticia();
			
			if(descripcionFoto != null && descripcionFoto.trim().length() > 0){
				petfotonoticia.setDescripcion(descripcionFoto);
			}
			
			if(idnoticia == 0){
				ok = petnoticiaBO.ingresar(petnoticia, petfotonoticia, uploadedFile);
			}else{
				ok = petnoticiaBO.modificar(petnoticia, petnoticiaClon, lisPetfotonoticia, lisPetfotonoticiaClon, petfotonoticia, uploadedFile);
			}
			
			if(ok){
				new MessageUtil().showInfoMessage("Listo!", "Datos grabados con Exito!");
			}else{
				new MessageUtil().showInfoMessage("Aviso", "No existen cambios que guardar");
			}
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	public int getIdnoticia() {
		return idnoticia;
	}

	public void setIdnoticia(int idnoticia) {
		this.idnoticia = idnoticia;
	}
	
	public Petnoticia getPetnoticia() {
		return petnoticia;
	}

	public void setPetnoticia(Petnoticia petnoticia) {
		this.petnoticia = petnoticia;
	}

	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}

	public List<Petfotonoticia> getLisPetfotonoticia() {
		return lisPetfotonoticia;
	}

	public void setLisPetfotonoticia(List<Petfotonoticia> lisPetfotonoticia) {
		this.lisPetfotonoticia = lisPetfotonoticia;
	}

	public Petfotonoticia getPetfotonoticiaSeleccionada() {
		return petfotonoticiaSeleccionada;
	}

	public void setPetfotonoticiaSeleccionada(
			Petfotonoticia petfotonoticiaSeleccionada) {
		this.petfotonoticiaSeleccionada = petfotonoticiaSeleccionada;
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

	public String getDescripcionFoto() {
		return descripcionFoto;
	}

	public void setDescripcionFoto(String descripcionFoto) {
		this.descripcionFoto = descripcionFoto;
	}
}
