package com.web.cementerio.global;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.web.util.FacesUtil;

@ManagedBean
@SessionScoped
public class Parametro {
	public static final String FILE_SEPARATOR = "/";//File.separator;
	public static final String MASCOTAS_PATH = FILE_SEPARATOR+"resources"+FILE_SEPARATOR+"images"+FILE_SEPARATOR+"mascotas"+FILE_SEPARATOR;
	public static final String PERSONAS_PATH = FILE_SEPARATOR+"resources"+FILE_SEPARATOR+"images"+FILE_SEPARATOR+"personas"+FILE_SEPARATOR;
	public static final String BLANK_IMAGE_PATH = FILE_SEPARATOR+"resources"+FILE_SEPARATOR+"images"+FILE_SEPARATOR+"miscellaneous"+FILE_SEPARATOR+"blank.jpg";
	public static final String IMAGE_FILE_NAME_PATERN = "##-###-###.jpg";
	public static final long DAY_IN_MILLISECONDS = (24*60*60*1000);
	public static final String WAR_PATH;
	static{
		String war_path = null;
		try{
			war_path = new FacesUtil().getRealPath("");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		WAR_PATH = war_path;
	}
	public static final String DEPLOYMENTS_PATH = WAR_PATH.substring(0, WAR_PATH.lastIndexOf("\\"));
	public static final String PARAMETROS_PROPERTIES_PATH = WAR_PATH+FILE_SEPARATOR+"resources"+FILE_SEPARATOR+"parametros.properties";
	public static final String RUTA_REPORTES = WAR_PATH+FILE_SEPARATOR+"reportes"+FILE_SEPARATOR;
	public static final String RUTA_IMAGENES_MISCELLANEOUS = WAR_PATH+FILE_SEPARATOR+"resources"+FILE_SEPARATOR+"images"+FILE_SEPARATOR+"miscellaneous"+FILE_SEPARATOR;
}
