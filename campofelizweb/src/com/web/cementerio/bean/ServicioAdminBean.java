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

import com.web.cementerio.bo.PetservicioBO;
import com.web.cementerio.pojo.annotations.Petfotoservicio;
import com.web.cementerio.pojo.annotations.Petservicio;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class ServicioAdminBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4748535305651371565L;
	private int idservicio;
	private Petservicio petservicio;
	private Petservicio petservicioClon;
	private String rutaImagenes;
	private List<Petfotoservicio> lisPetfotoservicio;
	private List<Petfotoservicio> lisPetfotoservicioClon;
	private Petfotoservicio petfotoservicioSeleccionado;
	private StreamedContent streamedContent;
	private UploadedFile uploadedFile;
	private String descripcionFoto;
	private boolean fotoSubida;
	
	public ServicioAdminBean() {
		petservicio = new Petservicio(0, new Setestado(), new Setusuario(), null, null, null, null, null, false, new Date(), null);
		petservicioClon = new Petservicio(0, new Setestado(), new Setusuario(), null, null, null, new Date(), null, false, new Date(), null);
		lisPetfotoservicio = new ArrayList<Petfotoservicio>();
		lisPetfotoservicioClon = new ArrayList<Petfotoservicio>();
		petfotoservicioSeleccionado = new Petfotoservicio();
		descripcionFoto = "";
		fotoSubida = false;
		
		cargarRutaImagenes();
	}
	
	@PostConstruct
	public void initServicioAdminBean() {
		FacesUtil facesUtil = new FacesUtil();
		idservicio = Integer.parseInt(facesUtil.getParametroUrl("idservicio").toString());
		
		if(idservicio > 0){
			consultaServicio();
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
	
	public void consultaServicio(){
		if(this.idservicio > 0){
			try {
				PetservicioBO petservicioBO = new PetservicioBO();
				petservicio = petservicioBO.getPetservicioConObjetosById(idservicio);
				
				if(petservicio != null && petservicio.getIdservicio() > 0){
					petservicioClon = petservicio.clonar();
					
					if(petservicio.getPetfotoservicios() != null && petservicio.getPetfotoservicios().size() > 0){
						lisPetfotoservicio = new ArrayList<Petfotoservicio>(petservicio.getPetfotoservicios());
						
						for(Petfotoservicio petfotoservicio : lisPetfotoservicio){
							lisPetfotoservicioClon.add(petfotoservicio.clonar());
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
		petservicio.setRutafoto(petfotoservicioSeleccionado.getRuta());
		petfotoservicioSeleccionado = new Petfotoservicio();
		new MessageUtil().showInfoMessage("Listo!", "Se ha seleccionado como foto principal");
	}
	
	public void quitarFotoGaleria(){
		lisPetfotoservicio.remove(petfotoservicioSeleccionado);
		petfotoservicioSeleccionado = new Petfotoservicio();
	}
	
	public void borrarFotoSubida(){
		streamedContent = null;
		uploadedFile = null;
		fotoSubida = false;
	}
	
	public void grabar(){
		try{
			boolean ok = false;
			
			PetservicioBO petservicioBO = new PetservicioBO();
			Petfotoservicio petfotoservicio = new Petfotoservicio();
			
			if(fotoSubida && descripcionFoto != null && descripcionFoto.trim().length() > 0){
				petfotoservicio.setDescripcion(descripcionFoto);
			}
			
			if(idservicio == 0){
				ok = petservicioBO.ingresar(petservicio, petfotoservicio, uploadedFile);
			}else{
				ok = petservicioBO.modificar(petservicio, petservicioClon, lisPetfotoservicio, lisPetfotoservicioClon, petfotoservicio, uploadedFile);
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
	
	public String eliminar(){
		String paginaRetorno = null;
		
		try{
			PetservicioBO petservicioBO = new PetservicioBO();
			
			petservicioBO.eliminar(petservicio);

			paginaRetorno = "servicios?faces-redirect=true";
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
		return paginaRetorno;
	}
	
	public Petservicio getPetservicio() {
		return petservicio;
	}

	public void setPetservicio(Petservicio petservicio) {
		this.petservicio = petservicio;
	}

	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}

	public int getIdservicio() {
		return idservicio;
	}

	public void setIdservicio(int idservicio) {
		this.idservicio = idservicio;
	}

	public List<Petfotoservicio> getLisPetfotoservicio() {
		return lisPetfotoservicio;
	}

	public void setLisPetfotoservicio(List<Petfotoservicio> lisPetfotoservicio) {
		this.lisPetfotoservicio = lisPetfotoservicio;
	}

	public Petfotoservicio getPetfotoservicioSeleccionado() {
		return petfotoservicioSeleccionado;
	}

	public void setPetfotoservicioSeleccionado(
			Petfotoservicio petfotoservicioSeleccionado) {
		this.petfotoservicioSeleccionado = petfotoservicioSeleccionado;
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
