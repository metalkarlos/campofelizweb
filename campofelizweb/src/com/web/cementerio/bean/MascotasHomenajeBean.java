package com.web.cementerio.bean;




import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import com.web.cementerio.bo.PetmascotahomenajeBO;
import com.web.cementerio.pojo.annotations.Petmascotahomenaje;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class MascotasHomenajeBean  {

private List<Petmascotahomenaje> listpetmascotahomenaje; 
private int idespecie;
private String nombre;
private String rutaImagenes;

	public MascotasHomenajeBean(){
		idespecie=0;
		nombre ="";
		rutaImagenes = "";
		cargarRutaImagenes();
		consultarMascotaHomenaje(1);
		
		
	}
		
	private void cargarRutaImagenes(){
		try {
			rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	
	public void consultarMascotaHomenaje(int idestado){
		if(idestado >0){
			try {
				PetmascotahomenajeBO petmascotahomenajeBO = new PetmascotahomenajeBO();
				listpetmascotahomenaje = petmascotahomenajeBO.getListpetmascotahomenaje(idestado);
				
			} catch (Exception e) {
				e.printStackTrace();
				new MessageUtil().showFatalMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
		
	}
	
    public void buscarmascotahomenajebycriteria(){
    	if ((idespecie > 0) && ( (!nombre.equals(null)) && (nombre.length() > 0))){
    		try {
				listpetmascotahomenaje = new ArrayList<Petmascotahomenaje>();
	    		PetmascotahomenajeBO petmascotahomenajeBO = new PetmascotahomenajeBO();
	    		listpetmascotahomenaje = petmascotahomenajeBO.getListpetmascotahomenajebycriteria(1, idespecie, nombre);
    		} catch (Exception e) {
			   e.printStackTrace();
			   new MessageUtil().showFatalMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
    	}else{
    		new MessageUtil().showInfoMessage("Info", "Por favor ingrese el nombre y especie de la mascota a consultar");
    	}
		
	}

	public List<Petmascotahomenaje> getListpetmascotahomenaje() {
		return listpetmascotahomenaje;
	}

	public void setListpetmascotahomenaje(
			List<Petmascotahomenaje> listpetmascotahomenaje) {
		this.listpetmascotahomenaje = listpetmascotahomenaje;
	}

	public int getIdespecie() {
		return idespecie;
	}

	public void setIdespecie(int idespecie) {
		this.idespecie = idespecie;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getRutaImagenes() {
		return rutaImagenes;
	}


	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}


	
	
	
}
