package com.web.cementerio.bean;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import com.web.util.FacesUtil;
import com.web.util.MessageUtil;
import com.web.cementerio.bo.PetempresaBO;
import com.web.cementerio.pojo.annotations.Petempresa;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;

@ManagedBean
@ViewScoped
public class EmpresasBean implements Serializable{

	private static final long serialVersionUID = 5406982578731779952L;
    private List<Petempresa> listpetemepresas;
    private Petempresa petempresaseleccionada;
	
	
	public EmpresasBean(){
		petempresaseleccionada = new Petempresa(0, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null,null);
		consultar();
	}
	
	public void consultar(){
		PetempresaBO petempresaBO  = new PetempresaBO();
		try {
			listpetemepresas = new ArrayList<Petempresa>();
			listpetemepresas = petempresaBO.Listpetempresa(1);
			
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	public void onUserRowSelect(SelectEvent event) throws Exception{
		FacesUtil facesUtil = new FacesUtil();
		facesUtil.redirect("../admin/empresa-admin.jsf?faces-redirect=true&idempresa="+petempresaseleccionada.getIdempresa());
	}
	
	public List<Petempresa> getListpetemepresas() {
		return listpetemepresas;
	}

	public void setListpetemepresas(List<Petempresa> listpetemepresas) {
		this.listpetemepresas = listpetemepresas;
	}

	public Petempresa getPetempresaseleccionada() {
		return petempresaseleccionada;
	}

	public void setPetempresaseleccionada(Petempresa petempresaseleccionada) {
		this.petempresaseleccionada = petempresaseleccionada;
	}

	

}
