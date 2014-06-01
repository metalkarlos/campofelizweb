package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.pojo.annotations.Petguia;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;


@ManagedBean
@ViewScoped
public class CementerioVirtualBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7212209718614799223L;

	
	private List<Petguia> listPetguia;
	 

 
    public CementerioVirtualBean() {
    	listPetguia = new ArrayList<Petguia>();
 
    	listPetguia.add(new Petguia(0,new Setestado(),new Setusuario(),"imagenes/instalaciones/01-06-2014-campo-santo.jpg", null,null,null,null,null,null,null,false,null));
    	listPetguia.add(new Petguia(1,new Setestado(),new Setusuario(),"imagenes/instalaciones/01-06-2014-entrada.jpg", null,null,null,null,null,null,null,false,null));
    	listPetguia.add(new Petguia(2,new Setestado(),new Setusuario(),"imagenes/instalaciones/01-06-2014-lapida-01.jpg", null,null,null,null,null,null,null,false,null));
    	
    }



	public List<Petguia> getListPetguia() {
		return listPetguia;
	}



	public void setListPetguia(List<Petguia> listPetguia) {
		this.listPetguia = listPetguia;
	}

	
    
}
