package com.web.faces.listeners;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import com.web.cementerio.bean.BreadCrumbBean;
import com.web.cementerio.bean.UsuarioBean;
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
		if(phaseEvent.getPhaseId() == PhaseId.RESTORE_VIEW){
			UsuarioBean usuarioBean = (UsuarioBean) new FacesUtil().getSessionBean("usuarioBean");
			if(usuarioBean != null && usuarioBean.getStreamedContent() != null){
				usuarioBean.setStreamedContent(null);
				new FacesUtil().setSessionBean("usuarioBean", usuarioBean);
			}
			
			FacesUtil facesUtil = new FacesUtil();
			BreadCrumbBean breadCrumbBean = (BreadCrumbBean) facesUtil.getSessionBean("breadCrumbBean");
			
			if(breadCrumbBean == null){
				breadCrumbBean = new BreadCrumbBean();
				facesUtil.setSessionBean("breadCrumbBean", breadCrumbBean);
			}
			
			FacesContext facesContext = phaseEvent.getFacesContext();
			HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();
			String[] urlarray = request.getRequestURI().split("/");
			int x=-1;
			for(int i=0;i<urlarray.length;i++){
				if(urlarray[i].endsWith(".jsf")){
					x=i;
					break;
				}
			}
			String pagina = urlarray[x];
			breadCrumbBean.armarBreadCrumb(pagina);
		}
	}

	public void beforePhase(PhaseEvent phaseEvent) {
		
	}
}
