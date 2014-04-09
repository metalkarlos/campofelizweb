package com.web.cementerio.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.pojo.annotations.Petnoticia;

@ManagedBean
@ViewScoped
public class NoticiaAdminBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4748535305651371565L;
	private int idnoticia;
	private Petnoticia petnoticia;

	public NoticiaAdminBean() {
		petnoticia = new Petnoticia();
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

}
