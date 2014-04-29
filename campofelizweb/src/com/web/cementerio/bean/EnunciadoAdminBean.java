package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;



import com.web.cementerio.bo.PetenunciadoBO;

import com.web.cementerio.pojo.annotations.Petenunciado;
import com.web.cementerio.pojo.annotations.Petvenunciado;

import com.web.cementerio.pojo.annotations.Setestado;
import com.web.cementerio.pojo.annotations.Setusuario;
import com.web.util.FacesUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class EnunciadoAdminBean implements Serializable {

	private static final long serialVersionUID = -6325180654691511826L;
	private int idenunciado;
	private Petenunciado petenunciadopregunta;
	private Petenunciado petenunciadorespuesta;
	private List<Petvenunciado> listpetvenunciado;
	private List<Petenunciado> listpetenunciadoclone;
	private String paginaRetorno;
	

	public EnunciadoAdminBean(){
		idenunciado =0;
		petenunciadopregunta = new Petenunciado(0, new Setestado(),new Setusuario(), null, null, 0, null, null,null, null);
		petenunciadorespuesta = new Petenunciado(0, new Setestado(),new Setusuario(), null, null, 0, null, null,null, null);
	}

	@PostConstruct
	public void initEnunciadoBean() {
		try{
			FacesUtil facesUtil = new FacesUtil();
			idenunciado = Integer.parseInt(facesUtil.getParametroUrl("idenunciado").toString());
		
			if(idenunciado > 0){
				consultar();
				clonar();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	public void consultar(){
		listpetvenunciado = new ArrayList<>();
		PetenunciadoBO petenunciadoBO = new PetenunciadoBO();
		try {
			listpetvenunciado = petenunciadoBO.getListpetvenunciadoidpadre(idenunciado);
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
	}
	
	public void grabar(){
		PetenunciadoBO petenunciadoBO = new PetenunciadoBO();
		List<Petenunciado> listpetenunciado = new ArrayList<Petenunciado>();
		try {
			if(idenunciado ==0){
			   listpetenunciado.add(0,petenunciadopregunta);
			   listpetenunciado.add(1,petenunciadorespuesta);
			   petenunciadoBO.grabar(listpetenunciado, 1);	
			   new MessageUtil().showInfoMessage("Exito", "Información registrada");
			   
			}else if(idenunciado >0){
			  for(Petenunciado petenunciadoclone: listpetenunciadoclone){
				  if(petenunciadoclone.getIdenunciado()==petenunciadopregunta.getIdenunciado()){
					 if(!petenunciadoclone.equals(petenunciadopregunta)){
				       listpetenunciado.add(0,petenunciadopregunta);
					}
  				  }else if(petenunciadoclone.getIdenunciado()==petenunciadorespuesta.getIdenunciado()){
					if(!petenunciadoclone.equals(petenunciadorespuesta)){
					  listpetenunciado.add(1,petenunciadorespuesta);
					}
				  }
				}
				
			   petenunciadoBO.modificar(listpetenunciado, 1);	
			   listpetenunciadoclone = new ArrayList<Petenunciado>();
			   new MessageUtil().showInfoMessage("Exito", "Información modificada");
			}
			petenunciadopregunta = new Petenunciado(0, new Setestado(),new Setusuario(), null, null, 0, null, null,null, null);
			petenunciadorespuesta = new Petenunciado(0, new Setestado(),new Setusuario(), null, null, 0, null, null,null, null);
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void eliminar(){
		PetenunciadoBO petenunciadoBO = new PetenunciadoBO();
		List<Petenunciado> listpetenunciado = new ArrayList<Petenunciado>();
		try {
			if(idenunciado > 0){
				listpetenunciado.add(0,petenunciadopregunta);
				listpetenunciado.add(1,petenunciadorespuesta);
				petenunciadoBO.eliminar(listpetenunciado, 2);
				new MessageUtil().showInfoMessage("Exito", "Registro eliminado");
				petenunciadopregunta = new Petenunciado(0, new Setestado(),new Setusuario(), null, null, 0, null, null,null, null);
				petenunciadorespuesta = new Petenunciado(0, new Setestado(),new Setusuario(), null, null, 0, null, null,null, null);
			}	
			paginaRetorno = "preguntas?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void clonar(){
		
		int indice =0;
		Petenunciado petenunciado = new Petenunciado(0, new Setestado(), new Setusuario(), null, null, 0, null, null, null, null);
		try{
			if(!listpetvenunciado.isEmpty()){
			for(Petvenunciado petvenunciado : listpetvenunciado){
			    petenunciado.setIdenunciado(petvenunciado.getIdenunciado());
			    petenunciado.setDescripcion(petvenunciado.getDescripcion());
			    petenunciado.setTipo(petvenunciado.getTipo());
			    petenunciado.setTag(petvenunciado.getTag());
			    if(petvenunciado.getIdpadre()>0){
			       petenunciado.setIdpadre(petvenunciado.getIdpadre());
			    }
			    listpetenunciadoclone.add(indice,petenunciado.clonar());
			 }
		   }
		}
		catch (Exception e){
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	public int getIdenunciado() {
		return idenunciado;
	}

	public void setIdenunciado(int idenunciado) {
		this.idenunciado = idenunciado;
	}

	public Petenunciado getPetenunciadopregunta() {
		return petenunciadopregunta;
	}

	public void setPetenunciadopregunta(Petenunciado petenunciadopregunta) {
		this.petenunciadopregunta = petenunciadopregunta;
	}

	public Petenunciado getPetenunciadorespuesta() {
		return petenunciadorespuesta;
	}

	public void setPetenunciadorespuesta(Petenunciado petenunciadorespuesta) {
		this.petenunciadorespuesta = petenunciadorespuesta;
	}

	public String getPaginaRetorno() {
		return paginaRetorno;
	}

	public void setPaginaRetorno(String paginaRetorno) {
		this.paginaRetorno = paginaRetorno;
	}

	public List<Petvenunciado> getListpetvenunciado() {
		return listpetvenunciado;
	}

	public void setListpetvenunciado(List<Petvenunciado> listpetvenunciado) {
		this.listpetvenunciado = listpetvenunciado;
	}

	public List<Petenunciado> getListpetenunciadoclone() {
		return listpetenunciadoclone;
	}

	public void setListpetenunciadoclone(List<Petenunciado> listpetenunciadoclone) {
		this.listpetenunciadoclone = listpetenunciadoclone;
	}

	
}
