package com.web.cementerio.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.cementerio.bo.PetnoticiaBO;
import com.web.cementerio.pojo.annotations.Petnoticia;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class NoticiasBean {

	private String rutaImagenes;
	private LazyDataModel<Petnoticia> lisPetnoticia;
	private String tituloParam;
	private String descripcionParam;
	
	public NoticiasBean() {
		tituloParam = "";
		descripcionParam = "";
		cargarRutaImagenes();
		consultarNoticias();
	}
	
	private void cargarRutaImagenes(){
		try {
			rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
			
			consultarNoticias();
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	@SuppressWarnings("serial")
	public void consultarNoticias(){
		try
		{
			lisPetnoticia = new LazyDataModel<Petnoticia>() {
				public List<Petnoticia> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					List<Petnoticia> data = new ArrayList<Petnoticia>();
					PetnoticiaBO petnoticiaBO = new PetnoticiaBO();
					int args[] = {0};
					
					data = petnoticiaBO.lisPetnoticiaByPage(pageSize, first, args, tituloParam, descripcionParam);
					this.setRowCount(args[0]);
	
			        return data;
				}
			};
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Esto es Vergonzoso!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}

	public LazyDataModel<Petnoticia> getLisPetnoticia() {
		return lisPetnoticia;
	}

	public void setLisPetnoticia(LazyDataModel<Petnoticia> lisPetnoticia) {
		this.lisPetnoticia = lisPetnoticia;
	}


}
