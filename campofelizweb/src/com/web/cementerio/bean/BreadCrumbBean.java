package com.web.cementerio.bean;

import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

@ManagedBean
@SessionScoped
public class BreadCrumbBean {

	private MenuModel breadCrumb;
	private List<String> opciones = Arrays.asList("quienessomos.jsf",
			"servicios.jsf",
			"servicio.jsf",
			"encuesta.jsf",
			"cotizacion.jsf",
			"cementeriovirtual.jsf",
			"mascotashomenaje.jsf",
			"mascotahomenaje.jsf",
			"noticias.jsf",
			"noticia.jsf",
			"guiageneral.jsf",
			"guia.jsf",
			"preguntas.jsf",
			"contactenos.jsf");
	
	private String[][] ruta = {{"Home;Quienes Somos","home.jsf;#"},
			{"Home;Servicios","home.jsf;#"},
			{"Home;Servicios;Servicio","home.jsf;servicios.jsf;#"},
			{"Home;Encuesta","home.jsf;#"},
			{"Home;Cotización","home.jsf;#"},
			{"Home;Instalaciones","home.jsf;#"},
			{"Home;Homenaje Póstumo","home.jsf;#"},
			{"Home;Homenaje Póstumo;Homenaje Póstumo","home.jsf;mascotashomenaje.jsf;#"},
			{"Home;Noticias","home.jsf;#"},
			{"Home;Noticias;Noticia","home.jsf;noticias.jsf;#"},
			{"Home;Guía General","home.jsf;#"},
			{"Home;Guía General;Cómo Decir Adiós","home.jsf;guiageneral.jsf;#"},
			{"Home;Preguntas Frecuentes","home.jsf;#"},
			{"Home;Contáctenos","home.jsf;contactenos.jsf"}};
	
	public BreadCrumbBean() {
		breadCrumb = new DefaultMenuModel();
	}
	
	public void armarBreadCrumb(String opcion){
		int index = opciones.indexOf(opcion);
		breadCrumb = new DefaultMenuModel();
		
		if(index >= 0){
			String txtnom = ruta[index][0];
			String[] nom = txtnom.split(";");
			
			String txtop = ruta[index][1];
			String[] op = txtop.split(";");

			for(int i = 0 ; i<op.length ; i++){
				MenuItem item = new MenuItem();
				item.setValue(nom[i]);
				item.setUrl(op[i]);
				breadCrumb.addMenuItem(item);
			}
		}
	}
	
	public MenuModel getBreadCrumb() { return breadCrumb; }
}
