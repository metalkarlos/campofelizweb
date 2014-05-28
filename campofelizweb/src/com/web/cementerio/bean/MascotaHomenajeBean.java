package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
public class MascotaHomenajeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5791044831809774834L;
	private Petmascotahomenaje petmascotahomenaje;
	private List<Petfotomascota> listpetfotomascota;
	private int idmascota;
	private String rutaImagenes;
	
	
	public MascotaHomenajeBean() {
		inicializarobjetos();
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
	public void initMascotaHomenajeBean() {
		FacesUtil facesUtil = new FacesUtil();
		idmascota = Integer.parseInt(facesUtil.getParametroUrl("idmascota").toString());
		
		if(idmascota > 0){
			consultarMascotaHomenaje();
		}
	}
	
	public void consultarMascotaHomenaje(){
		try {
			PetmascotahomenajeBO mascotaHomenajeBO= new PetmascotahomenajeBO();
			petmascotahomenaje = new Petmascotahomenaje();
			listpetfotomascota = new ArrayList<Petfotomascota>();
			petmascotahomenaje = mascotaHomenajeBO.getPetmascotahomenajebyId(idmascota, 1,false);
			if(petmascotahomenaje.getPetfotomascotas().size()==0 && petmascotahomenaje.getRutafoto().equals("/mascota/huella.jpg")){
				Petfotomascota petfotomascota = new Petfotomascota();
				petfotomascota.setRuta(petmascotahomenaje.getRutafoto());
				petmascotahomenaje.getPetfotomascotas().add(petfotomascota);
			}
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void inicializarobjetos(){
		
		petmascotahomenaje = new Petmascotahomenaje(0, new Setestado(), new Setusuario(), new Petespecie(), null, null, null, null, null, null, null, null, null, null, null, null );
		Petespecie petespecie = new Petespecie();
		petmascotahomenaje.setPetespecie(petespecie);
		listpetfotomascota = new ArrayList<Petfotomascota>();
		idmascota =0;
		rutaImagenes = "";
	
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
	

	
}
