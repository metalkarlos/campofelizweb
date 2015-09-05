package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.web.cementerio.bo.PethomeBO;
import com.web.cementerio.pojo.annotations.Pethome;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class HomeAdminBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8480038994723169491L;
	private int idhome;
	private Pethome pethome;
	private Pethome pethomeClon;
	
	public HomeAdminBean() {
		pethome = new Pethome(0, new Setestado(), new Setusuario(), null, null, 0, new Date(), null, null);
		pethomeClon = new Pethome(0, new Setestado(), new Setusuario(), null, null, 0, new Date(), null, null);
	}
	
	@PostConstruct
	public void PostHomeAdminBean() {
		FacesUtil facesUtil = new FacesUtil();
		
		try {
			Object par = facesUtil.getParametroUrl("idhome");
			
			if(par != null){
				idhome = Integer.parseInt(par.toString());
				
				if(idhome > 0){
					consultaHome();
				}else{
					PethomeBO pethomeBO = new PethomeBO();
					int orden = pethomeBO.getMaxOrden();
					pethome.setOrden(orden + 1);
				}
			}else{
				facesUtil.redirect("../pages/home.jsf");
			}
		} catch(NumberFormatException ne){
			try{facesUtil.redirect("../pages/home.jsf");}catch(Exception e){}
		} catch(Exception e) {
			e.printStackTrace();
			try{facesUtil.redirect("../pages/home.jsf");}catch(Exception e2){}
		}
	}
	
	private void consultaHome(){
		try {
			PethomeBO pethomeBO = new PethomeBO();
			pethome = pethomeBO.getPethomebyId(idhome);
			
			if(pethome != null && pethome.getIdhome() > 0){
				pethomeClon = pethome.clonar();
			}
		} catch(Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	public void grabar(){
		try{
			boolean ok = false;
			
			PethomeBO pethomeBO = new PethomeBO();
			
			if(idhome == 0){
				ok = pethomeBO.grabar(pethome);
			}else{
				ok = pethomeBO.modificar(pethome, pethomeClon);
			}
			
			if(ok){
				FacesUtil facesUtil = new FacesUtil();
				facesUtil.redirect("../pages/home.jsf");
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
			PethomeBO pethomeBO = new PethomeBO();
			
			pethomeBO.eliminar(pethome);

			paginaRetorno = "../pages/home?faces-redirect=true";
		}catch(Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
		return paginaRetorno;
	}
	
	public int getIdhome() {
		return idhome;
	}

	public void setIdhome(int idhome) {
		this.idhome = idhome;
	}

	public Pethome getPethome() {
		return pethome;
	}

	public void setPethome(Pethome pethome) {
		this.pethome = pethome;
	}

	public Pethome getPethomeClon() {
		return pethomeClon;
	}

	public void setPethomeClon(Pethome pethomeClon) {
		this.pethomeClon = pethomeClon;
	}
}
