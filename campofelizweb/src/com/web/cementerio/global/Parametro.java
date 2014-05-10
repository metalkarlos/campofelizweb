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
	
	//Seccion de rangos de los cargos
	public static final short CARGO_NIVEL_SERVICIO_MIN = 1;
	public static final short CARGO_NIVEL_SERVICIO_MAX = 50;
	public static final short CARGO_NIVEL_DESCUENTO_MIN = 51;
	public static final short CARGO_NIVEL_DESCUENTO_MAX = 100;
	public static final short CARGO_NIVEL_IMPUESTO_MIN = 101;
	public static final short CARGO_NIVEL_IMPUESTO_MAX = 150;
	
	//Seccion de niveles de los cargos
	public static final short CARGO_NIVEL_IVA_SERVICIOS = 101;
	public static final short CARGO_NIVEL_ICE_SERVICIOS = 102;
	
	//Seccion de tipos de menu
	public static final int TIPOMENU_PRINCIPAL = 1;
	public static final int TIPOMENU_CLIENTE = 2;
	
	//Seccion de formas de pago
	public static final int TIPO_FORMA_PAGO_CHEQUE = 1;
	public static final int TIPO_FORMA_PAGO_TARJETA_CREDITO = 2;
	public static final int TIPO_FORMA_PAGO_TARJETA_DEBITO = 3;
	public static final int TIPO_FORMA_PAGO_TRANSFERENCIA = 4;
	public static final int TIPO_FORMA_PAGO_DEPOSITO = 5;
	public static final int TIPO_FORMA_PAGO_EFECTIVO = 6;
	public static final int TIPO_FORMA_PAGO_CREDITO = 7;
	public static final int TIPO_FORMA_PAGO_EXCEDENTE = 8;
	
	//Seccion de tipos de menu
	public static final int TIPO_MENU_PRINCIPAL = 1;
	public static final int TIPO_MENU_CLIENTE = 2;
}
