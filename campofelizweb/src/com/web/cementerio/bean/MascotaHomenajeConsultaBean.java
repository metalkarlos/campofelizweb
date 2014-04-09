package com.web.cementerio.bean;


import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


import com.web.cementerio.bo.PetmascotahomenajeBO;
import com.web.cementerio.pojo.annotations.Petmascotahomenaje;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class MascotaHomenajeConsultaBean implements Serializable  {
	
/**
	 * 
	 */
	private static final long serialVersionUID = -4438614858999232988L;
private List<Petmascotahomenaje> listpetmascotahomenaje = null; 

	public MascotaHomenajeConsultaBean(){
		consultarMascotaHomenaje(1);
	}
		
	public void consultarMascotaHomenaje(int idestado){
		if(idestado >0){
			try {
				PetmascotahomenajeBO petmascotahomenajeBO = new PetmascotahomenajeBO();
				listpetmascotahomenaje = petmascotahomenajeBO.getListpetmascotahomenaje(idestado);
				
			} catch (Exception e) {
				e.printStackTrace();
				new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
			}
		}
		
	}

	public List<Petmascotahomenaje> getListpetmascotahomenaje() {
		return listpetmascotahomenaje;
	}

	public void setListpetmascotahomenaje(
			List<Petmascotahomenaje> listpetmascotahomenaje) {
		this.listpetmascotahomenaje = listpetmascotahomenaje;
	}
	
	
	
}
