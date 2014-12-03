package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.bo.PethomeBO;
import com.web.cementerio.pojo.annotations.Pethome;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class HomeBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7268920983615910156L;
	private List<Pethome> lisPethome;

	public HomeBean() {
		lisPethome = new ArrayList<Pethome>();
	}
	
	@PostConstruct
	public void initHomeBean() {
		consultarHome();
	}
	
	private void consultarHome() {
		try {
			PethomeBO pethomeBO = new PethomeBO();
			lisPethome = pethomeBO.lisPethome();
		} catch(Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	public List<Pethome> getLisPethome() {
		return lisPethome;
	}

	public void setLisPethome(List<Pethome> lisPethome) {
		this.lisPethome = lisPethome;
	}
	
	
}
