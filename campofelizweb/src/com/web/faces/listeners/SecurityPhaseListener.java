package com.web.faces.listeners;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

//import com.web.pet.bean.MenuBean;
//import com.web.pet.bean.UsuarioBean;
//import com.web.pet.pojo.annotations.Menu;
//import com.web.pet.pojo.annotations.Sevmenu;
import com.web.util.FacesUtil;

public class SecurityPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3917092198608197271L;

	public SecurityPhaseListener() {
	}
	
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;//PhaseId.ANY_PHASE;
	}
	
	public void afterPhase(PhaseEvent phaseEvent) {
		/*if(phaseEvent.getPhaseId() == PhaseId.RESTORE_VIEW){
			UsuarioBean usuarioBean = (UsuarioBean) new FacesUtil().getSessionBean("usuarioBean");
			FacesContext facesContext = phaseEvent.getFacesContext();
			//HttpServletRequest requestTmp = (HttpServletRequest)facesContext.getExternalContext().getRequest();
			//System.out.println(requestTmp.getRequestURI());
			//Verifica si la sesión ha caducado o si no está autenticado
			if(usuarioBean==null || !usuarioBean.isAutenticado()){
				boolean loginPage = facesContext.getViewRoot().getViewId().equals("/pages/login.xhtml");
				boolean notloggedPage = facesContext.getViewRoot().getViewId().equals("/pages/not_logged.xhtml");
				
				//Si se quiere acceder a páginas no permitidas se guarda la url requerida y se redirecciona a not_logged.xhtml
				if(!loginPage && !notloggedPage){
					//Se arma la url requerida y se guarda en sesión
					HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();
					String[] urlarray = request.getRequestURI().split("/");
					int x=-1;
					for(int i=0;i<urlarray.length;i++){
						if(urlarray[i].endsWith(".jsf")){
							x=i;
							break;
						}
					}
					String urloriginal = urlarray[x];
					String urlrequested = null;
					try {
						urlrequested = URLEncoder.encode(urloriginal, "UTF-8");
						urlrequested += "?faces-redirect=true";
						
						if(request.getQueryString() != null){
							urlrequested += "&"+request.getQueryString();
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					request.getSession().setAttribute("urlrequested", urlrequested);
					//throw new ViewExpiredException();
					//Se redirecciona a not_logged.xhtml
					NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
					navigationHandler.handleNavigation(facesContext, null, "not_logged?faces-redirect=true");
				}
			}else{
				//Si está autenticado seteamos el menu de la página requerida 
				FacesUtil facesUtil = new FacesUtil();
				MenuBean menuBean = (MenuBean)facesUtil.getSessionBean("menuBean");
				
				if(menuBean == null){
					menuBean = new MenuBean();
					facesUtil.setSessionBean("menuBean", menuBean);
				}
				
				if(menuBean != null){
					int ordenmenupadre = 0;
					String iditem = facesContext.getExternalContext().getRequestParameterMap().get("iditem");
					
					if(iditem != null && Integer.parseInt(iditem) >= 0){
						boolean existepagina = false;
						for(Menu menupadre : menuBean.getLisMenu()){
							//ordenmenupadre = menupadre.getOrden();
							
							if(Integer.parseInt(iditem) == menupadre.getIdmenu()){
								existepagina = true;
								break;
							}
							
							for(Sevmenu menuhijo : menupadre.getLisSevOpcionMenu()){
								if(Integer.parseInt(iditem) == menuhijo.getIdmenu()){
									existepagina = true;
									break;
								}
							}
							if(existepagina){
								break;
							}
							ordenmenupadre++;
						}
						if(existepagina){
							menuBean.setActiveIndex(ordenmenupadre);
							menuBean.setActiveIdItem(Integer.parseInt(iditem));
						}
					}
				}
			}
		}*/
	}

	public void beforePhase(PhaseEvent phaseEvent) {
		/*FacesContext ctx = phaseEvent.getFacesContext();
		HttpServletRequest request = (HttpServletRequest)ctx.getExternalContext().getRequest();
		ValueBinding vb = ctx.getApplication().createValueBinding("#{navigationBean}");
		NavigationBean nb = (NavigationBean)vb.getValue(ctx);
		String path = request.getPathInfo();
		if("/controller".equals(path)){
			try{
				String newPageViewId = null;
				if("mascotas".equalsIgnoreCase(nb.getTarget()))
					newPageViewId = "/mascotas.jsf";
				if("agenda".equalsIgnoreCase(nb.getTarget()))
					newPageViewId = "/agenda.jsf";
				if("colores".equalsIgnoreCase(nb.getTarget()))
					newPageViewId = "/colores.jsf";
				
				UIViewRoot newPage = ctx.getApplication().getViewHandler().createView(ctx, newPageViewId);
				ctx.setViewRoot(newPage);
				ctx.renderResponse();
			}catch(Exception e){
				
			}
		}*/
	}
}
