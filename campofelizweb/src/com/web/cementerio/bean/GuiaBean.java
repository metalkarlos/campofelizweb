package com.web.cementerio.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.bo.PetguiaBO;
import com.web.cementerio.pojo.annotations.Petguia;
import com.web.util.FacesUtil;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;



@ManagedBean
@ViewScoped
public class GuiaBean implements Serializable{

	
	private static final long serialVersionUID = -1862606539425133513L;
	private int idguia;
	private Petguia petguia;
	private String rutaImagenes;
	//private List<Petfotonoticia> lisPetguia;
	
	public GuiaBean(){
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
	
	@PostConstruct
	public void initGuiaBean() {
		FacesUtil facesUtil = new FacesUtil();
		idguia = Integer.parseInt(facesUtil.getParametroUrl("idguia").toString());
		
		if(idguia > 0){
			consultarNoticias();
		}
	}
	
	public void consultarNoticias(){
		if(this.idguia > 0){
			try {
				PetguiaBO petguiaBO = new PetguiaBO();
				petguia= petguiaBO.getPetguiabyId(idguia, 1);
				
				/*if(petnoticia.getPetfotonoticias() != null && petnoticia.getPetfotonoticias().size() > 0){
					lisPetfotonoticia = new ArrayList<Petfotonoticia>(petnoticia.getPetfotonoticias());
				}*/
			} catch(Exception e) {
				e.printStackTrace();
				new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
	}
	
	

	public int getIdguia() {
		return idguia;
	}

	public void setIdguia(int idguia) {
		this.idguia = idguia;
	}

	public Petguia getPetguia() {
		return petguia;
	}

	public void setPetguia(Petguia petguia) {
		this.petguia = petguia;
	}

	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}

}
