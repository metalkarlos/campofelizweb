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
		petenunciadopregunta = new Petenunciado(0, new Setestado(),new Setusuario(), 'P', null, 0,0, null, null,null, null);
		petenunciadorespuesta = new Petenunciado(0, new Setestado(),new Setusuario(), 'R', null, 0,0, null, null,null, null);
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
	
	public String  grabar(){
		PetenunciadoBO petenunciadoBO = new PetenunciadoBO();
		List<Petenunciado> listpetenunciado = new ArrayList<Petenunciado>();
		String paginaRetorno = null;
		try {
			if(validarcampos()){
				if(idenunciado ==0){
				   listpetenunciado.add(0,petenunciadopregunta);
				   petenunciadorespuesta.setOrden(petenunciadopregunta.getOrden());
				   listpetenunciado.add(1,petenunciadorespuesta);
				   petenunciadoBO.grabar(listpetenunciado, 1);	
				   //new MessageUtil().showInfoMessage("Exito", "Información registrada");
				   
				}else if(idenunciado >0){
				  for(Petenunciado petenunciadoclone: listpetenunciadoclone){
					  int indice = 0;
					  if(petenunciadoclone.getIdenunciado()==petenunciadopregunta.getIdenunciado()){
						 if(!petenunciadoclone.equals(petenunciadopregunta)){
							if(petenunciadoclone.getOrden()!=petenunciadopregunta.getOrden()){
							  petenunciadorespuesta.setOrden(petenunciadopregunta.getOrden());	
							}
					       listpetenunciado.add(indice,petenunciadopregunta);
						}
	  				  }else if(petenunciadoclone.getIdenunciado()==petenunciadorespuesta.getIdenunciado()){
						if(!petenunciadoclone.equals(petenunciadorespuesta)){
						
						  listpetenunciado.add(indice,petenunciadorespuesta);
						}
					  }
					  indice++;
					}
				   if((listpetenunciado.size()>0) && (!listpetenunciado.isEmpty())){	
					   if (petenunciadoBO.modificar(listpetenunciado, 1)){
						  listpetenunciadoclone = new ArrayList<Petenunciado>();	  
					   }
				   }
				}
				petenunciadopregunta = new Petenunciado(0, new Setestado(),new Setusuario(), 'P', null, 0, 0,null, null,null, null);
				petenunciadorespuesta = new Petenunciado(0, new Setestado(),new Setusuario(), 'R', null, 0,0, null, null,null, null);
				paginaRetorno="../pages/preguntas?faces-redirect=true";	   	 
			}	
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		return paginaRetorno;
	}
	
	public String eliminar(){
		PetenunciadoBO petenunciadoBO = new PetenunciadoBO();
		List<Petenunciado> listpetenunciado = new ArrayList<Petenunciado>();
		try {
			if(idenunciado > 0){
				listpetenunciado.add(0,petenunciadopregunta);
				listpetenunciado.add(1,petenunciadorespuesta);
				petenunciadoBO.eliminar(listpetenunciado, 2);
				new MessageUtil().showInfoMessage("Exito", "Registro eliminado");
				petenunciadopregunta = new Petenunciado(0, new Setestado(),new Setusuario(), 'P', null, 0,0, null, null,null, null);
				petenunciadorespuesta = new Petenunciado(0, new Setestado(),new Setusuario(), 'R', null, 0,0, null, null,null, null);
				listpetenunciadoclone = new ArrayList<Petenunciado>();
			}	
			paginaRetorno = "preguntas?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paginaRetorno;
	}
	
	public boolean validarcampos(){
		boolean ok = true;
	    if(petenunciadopregunta.getDescripcion()== null || petenunciadopregunta.getDescripcion().length() ==0){
	    	ok = false;
	    	new MessageUtil().showInfoMessage("Info", "Es necesario ingresar el contenido de la pregunta");
	    }
	    else if(petenunciadorespuesta.getDescripcion()== null || petenunciadorespuesta.getDescripcion().length() ==0){
	    	ok = false;
	    	new MessageUtil().showInfoMessage("Info", "Es necesario ingresar el contenido de la respuesta");
	    }
	    else if(petenunciadopregunta.getOrden()<=0){
	    	ok = false;
	    	new MessageUtil().showInfoMessage("Info", "Es necesario ingresar el orden de presentación de la pregunta");
	    }
		return ok;
		
	}
	
	public void clonar(){
		
		int indice =0;
		Petenunciado petenunciado = new Petenunciado(0, null, null, null, null, 0,0, null, null, null, null);
		listpetenunciadoclone = new ArrayList<Petenunciado>();
		try{
			if(!listpetvenunciado.isEmpty()){
			for(Petvenunciado petvenunciado : listpetvenunciado){
			    petenunciado.setIdenunciado(petvenunciado.getIdenunciado());
			    petenunciado.setDescripcion(petvenunciado.getDescripcion());
			    petenunciado.setTipo(petvenunciado.getTipo());
			    petenunciado.setTag(petvenunciado.getTag());
			    petenunciado.setOrden(petvenunciado.getOrden());
			    petenunciado.setFecharegistro(petvenunciado.getFecharegistro());
			    if(petvenunciado.getIdpadre()>0){
			       petenunciado.setIdpadre(petvenunciado.getIdpadre());
			    }
			    
			    if(String.valueOf(petvenunciado.getTipo()).equals("P")){
				   petenunciadopregunta = petenunciado;
				}else if(String.valueOf(petvenunciado.getTipo()).equals("R")){
					petenunciadorespuesta= petenunciado;
				 }
			    listpetenunciadoclone.add(indice,petenunciado.clonar());
			    petenunciado = new Petenunciado(0, null, null, null, null, 0,0, null, null, null, null);
			    indice ++;
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
