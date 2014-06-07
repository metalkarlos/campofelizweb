package com.web.cementerio.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;




import com.web.cementerio.bo.PetempresaBO;
import com.web.cementerio.pojo.annotations.Petempresa;
import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class EmpresaAdminBean implements Serializable {

	private static final long serialVersionUID = 590415272909549553L;

	private Petempresa petempresa;
	private Petempresa petempresaclone;
	private int idempresa;
	
	public EmpresaAdminBean(){
		petempresa = new Petempresa(0, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null,null);
		
	}


	public void consultar(){
		PetempresaBO petempresaBO  = new PetempresaBO();
		try {
			petempresa = petempresaBO.getPetempresabyId(1, idempresa);
			
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	@PostConstruct
	public void initEmpresaAdminBean() {
		try{
			FacesUtil facesUtil = new FacesUtil();
			idempresa = Integer.parseInt(facesUtil.getParametroUrl("idempresa").toString());
		
			if(idempresa > 0){
				consultar();
				petempresaclone = new Petempresa(0, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null,null);
				petempresaclone = petempresa.clonar();
			}
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showErrorMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public String grabar(){
		String paginaRetorno = null;
		PetempresaBO petempresaBO = new PetempresaBO();
		try {
			if(validarcampos()){
				if(petempresa.getIdempresa()==0){
					petempresaBO.grabar(petempresa, 1);
					petempresa = new Petempresa(0, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null,null);
					//new MessageUtil().showInfoMessage("Exito", "Información registrada");
					paginaRetorno="/pages/empresas?faces-redirect=true"; 
				}else if(petempresa.getIdempresa() >0){
					if(petempresaBO.modificar(petempresa, petempresaclone, 1)){
						petempresa = new Petempresa(0, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null,null);
						petempresaclone = new Petempresa(0, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null,null);
						paginaRetorno="/pages/empresas?faces-redirect=true"; 
						//new MessageUtil().showInfoMessage("Exito", "Información modificada");
						
					}
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		return paginaRetorno;
	}
	
	
	
	public String eliminar(){
		PetempresaBO petempresaBO = new PetempresaBO();
		String paginaRetorno =null;
		try {
			if(petempresa.getIdempresa() >0){
				petempresaBO.eliminar(petempresa, 2);
				new MessageUtil().showInfoMessage("Exito", "Registro eliminado");
				petempresa = new Petempresa(0, new Setestado(), new Setusuario(), null, null, null, null, null, null, null, null, null, null,null);
				paginaRetorno = "empresas?faces-redirect=true";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		return paginaRetorno;
	}
	
	
	public boolean validarcampos(){
	   boolean ok = true;
	   if(petempresa.getTipoempresa()==0){
		   ok = false;
		   new MessageUtil().showInfoMessage("Info", "Es necesario seleccionar el tipo de la empresa");
	   }
	   else if(petempresa.getNombre()==null|| petempresa.getNombre().length()==0){
		   ok = false;
		   new MessageUtil().showInfoMessage("Info", "Es necesario ingresar el Nombre de la empresa");
	    }else if(petempresa.getDireccion()==null|| petempresa.getDireccion().length()==0){
	       ok = false;
		   new MessageUtil().showInfoMessage("Info", "Es necesario ingresar la dirección de la empresa");
	    }
	    return ok;
	 }
	
	public Petempresa getPetempresa() {
		return petempresa;
	}


	public void setPetempresa(Petempresa petempresa) {
		this.petempresa = petempresa;
	}


	public int getIdempresa() {
		return idempresa;
	}


	public void setIdempresa(int idempresa) {
		this.idempresa = idempresa;
	}


	public Petempresa getPetempresaclone() {
		return petempresaclone;
	}


	public void setPetempresaclone(Petempresa petempresaclone) {
		this.petempresaclone = petempresaclone;
	}
	
	
}
