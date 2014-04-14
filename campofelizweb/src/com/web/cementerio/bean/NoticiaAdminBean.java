package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
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
	private String rutaImagenes;
	private List<Petfotonoticia> lisPetfotonoticia;
	private Petfotonoticia petfotonoticiaSeleccionada;
	private UploadedFile uploadedFile;

	public NoticiaAdminBean() {
		petnoticia = new Petnoticia();
		lisPetfotonoticia = new ArrayList<Petfotonoticia>();
		petfotonoticiaSeleccionada = new Petfotonoticia();
		
		cargarRutaImagenes();
	}
	
	private void cargarRutaImagenes(){
		try {
			rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	public int getIdnoticia() {
		return idnoticia;
	}

	public void setIdnoticia(int idnoticia) {
		this.idnoticia = idnoticia;
		
		if(this.idnoticia > 0){
			try {
				PetnoticiaBO petnoticiaBO = new PetnoticiaBO();
				petnoticia = petnoticiaBO.getPetnoticiaConObjetosById(idnoticia);
				
				if(petnoticia.getPetfotonoticias() != null && petnoticia.getPetfotonoticias().size() > 0){
					lisPetfotonoticia = new ArrayList<Petfotonoticia>(petnoticia.getPetfotonoticias());
				}
			} catch(Exception e) {
				e.printStackTrace();
				new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		try{
			//uploadedFile = event.getFile();
			UsuarioBean usuarioBean = (UsuarioBean)new FacesUtil().getSessionBean("usuarioBean");
			usuarioBean.setStreamedContent(new DefaultStreamedContent(event.getFile().getInputstream(), event.getFile().getContentType()));
			new FacesUtil().setSessionBean("usuarioBean", usuarioBean);
			new MessageUtil().showInfoMessage("Foto en memoria!", event.getFile().getFileName());
		}catch(Exception x){
			x.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
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

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

}
