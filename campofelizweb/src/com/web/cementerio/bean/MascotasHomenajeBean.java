package com.web.cementerio.bean;




import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;



import com.web.cementerio.bo.PetmascotahomenajeBO;
import com.web.cementerio.pojo.annotations.Petmascotahomenaje;
import com.web.util.FileUtil;
import com.web.util.MessageUtil;

@ManagedBean
@ViewScoped
public class MascotasHomenajeBean  {
private LazyDataModel<Petmascotahomenaje>  listpetmascotahomenaje; 
private int idespecie;
private String nombre;
private String rutaImagenes;
private String descripcionParam;


	public MascotasHomenajeBean(){
		idespecie=0;
		nombre ="";
		rutaImagenes = "";
		descripcionParam = "buscar por nombre de mascota";
		cargarRutaImagenes();
		consultar();
		
		
	}
		
	private void cargarRutaImagenes(){
		try {
			rutaImagenes = new FileUtil().getPropertyValue("rutaImagen");
		} catch (Exception e) {
			e.printStackTrace();
			new MessageUtil().showFatalMessage("Error!", "Ha ocurrido un error inesperado. Comunicar al Webmaster!");
		}
	}
	
	@SuppressWarnings("serial")
	public void consultar(){
		try
		{
			listpetmascotahomenaje = new LazyDataModel<Petmascotahomenaje>() {
				public List<Petmascotahomenaje> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
					List<Petmascotahomenaje> data = new ArrayList<Petmascotahomenaje>();
					PetmascotahomenajeBO petmascotahomenajeBO = new PetmascotahomenajeBO();
					int args[] = {0};
					
					String[] textoBusqueda = null;
					if(descripcionParam != null && descripcionParam.trim().length() > 0 && descripcionParam.trim().compareTo("buscar por nombre de mascota") != 0 ){
						textoBusqueda = descripcionParam.split(" ");
						first = 0;
					}
					
					data = petmascotahomenajeBO.lisPetmascotahomenajeBusquedaByPage(textoBusqueda, pageSize, first, args,1);
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


	public int getIdespecie() {
		return idespecie;
	}

	
	public void setIdespecie(int idespecie) {
		this.idespecie = idespecie;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getRutaImagenes() {
		return rutaImagenes;
	}


	public void setRutaImagenes(String rutaImagenes) {
		this.rutaImagenes = rutaImagenes;
	}

	public String getDescripcionParam() {
		return descripcionParam;
	}

	public void setDescripcionParam(String descripcionParam) {
		this.descripcionParam = descripcionParam;
	}

	public LazyDataModel<Petmascotahomenaje> getListpetmascotahomenaje() {
		return listpetmascotahomenaje;
	}

	public void setListpetmascotahomenaje(
			LazyDataModel<Petmascotahomenaje> listpetmascotahomenaje) {
		this.listpetmascotahomenaje = listpetmascotahomenaje;
	}
	

	
	
	
}
