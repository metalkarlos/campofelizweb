package com.web.cementerio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.web.cementerio.bo.PetguiaBO;
import com.web.cementerio.pojo.annotations.Petguia;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;


@ManagedBean
@ViewScoped
public class GuiaGeneralBean  implements Serializable{

	private static final long serialVersionUID = 1800067820691222840L;

	private String rutaImagenes;
	private LazyDataModel<Petguia> lisPetguia;
	private List<Petguia>listPetguiaprincipal;
	private String descripcionParam;
	
	public GuiaGeneralBean(){
		descripcionParam="buscar";
		cargarRutaImagenes();
		consultarGuiabyPrincipal();
		consultarGuia();
		
	}

	private void cargarRutaImagenes(){
		try {
			rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	public void consultarGuiabyPrincipal(){
		try {
			PetguiaBO petguiaBO = new PetguiaBO();
			listPetguiaprincipal = new ArrayList<Petguia>();
			listPetguiaprincipal = petguiaBO.getlistPetguiaPrincipal(true, 1);
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
		
	}
	
	
	@SuppressWarnings("serial")
	public void consultarGuia(){
		try
		{
			lisPetguia = new LazyDataModel<Petguia>() {
				public List<Petguia> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					List<Petguia> data = new ArrayList<Petguia>();
					PetguiaBO petguiaBO = new PetguiaBO();
					int args[] = {0};
					
					String[] textoBusqueda = null;
					if(descripcionParam != null && descripcionParam.trim().length() > 0 && descripcionParam.trim().compareTo("buscar") != 0 ){
						textoBusqueda = descripcionParam.split(" ");
						first = 0;
					}
					
					data = petguiaBO.lisPetguiaBusquedaByPage(textoBusqueda, pageSize, first, args, 1);
					this.setRowCount(args[0]);
	
			        return data;
				}
				
				@Override
                public void setRowIndex(int rowIndex) {
                    /*
                     * The following is in ancestor (LazyDataModel):
                     * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
                     */
                    if (rowIndex == -1 || getPageSize() == 0) {
                        super.setRowIndex(-1);
                    }
                    else {
                        super.setRowIndex(rowIndex % getPageSize());
                    }      
                }
			};
		}catch(Exception re){
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}

	
	public String getRutaImagenes() {
		return rutaImagenes;
	}

	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}

	

	public LazyDataModel<Petguia> getLisPetguia() {
		return lisPetguia;
	}

	public void setLisPetguia(LazyDataModel<Petguia> lisPetguia) {
		this.lisPetguia = lisPetguia;
	}

	public String getDescripcionParam() {
		return descripcionParam;
	}

	public void setDescripcionParam(String descripcionParam) {
		this.descripcionParam = descripcionParam;
	}

	public List<Petguia> getListPetguiaprincipal() {
		return listPetguiaprincipal;
	}

	public void setListPetguiaprincipal(List<Petguia> listPetguiaprincipal) {
		this.listPetguiaprincipal = listPetguiaprincipal;
	}
}
