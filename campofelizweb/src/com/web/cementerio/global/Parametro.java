package com.web.cementerio.global;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Parametro {
	public static final String FILE_SEPARATOR = "/";//File.separator;
	public static final long DAY_IN_MILLISECONDS = (24*60*60*1000);
	public static final String PROPERTIES_FILE_NAME = "parametros.properties";
	public static final String PROPERTIES_MAIL = "mail.properties";
	public static final long TAMAÑO_IMAGEN = 716800;
}
