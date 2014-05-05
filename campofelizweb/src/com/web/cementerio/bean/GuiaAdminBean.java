package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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

	
	private static final long serialVersionUID = -9142652616171071320L;
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
	
	

	public GuiaAdminBean(){
		petguia = new Petguia(0, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null);
		petguiaClon = new Petguia(0, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null);
		lisPetfotoguia = new ArrayList<Petfotoguia>();
		lisPetfotoguiaClon = new ArrayList<Petfotoguia>();
		petfotoguiaSeleccionada =  new Petfotoguia(0, new Setestado(), new Setusuario(), new Petguia(), null, null, null, null, null, null, null);
		fotoSubida =false;
		
		cargarRutaImagenes();
	}
	
	
	@PostConstruct
	public void initGuiaAdminBean() {
		FacesUtil facesUtil = new FacesUtil();
		idguia = Integer.parseInt(facesUtil.getParametroUrl("idguia").toString());
		
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
				PetguiaBO petguiaBO = new PetguiaBO();
				petguia = petguiaBO.getPetguiabyId(idguia, 1);
				
				if(petguia != null && petguia.getIdguia() > 0){
					petguiaClon = petguia.clonar();
					
					if(petguia.getPetfotoguias() != null && petguia.getPetfotoguias().size() > 0){
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
	
	
}
