package com.web.util;

import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FacesUtil {
	
	private FacesContext facesContext = FacesContext.getCurrentInstance();
	private HttpServletRequest request = ((HttpServletRequest)facesContext.getExternalContext().getRequest());
	private HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();

	public String getIp() {
		return request.getRemoteAddr();
	}
	
	public String getSid(){
		return request.getSession().getId();
	}
	
	public Object getSessionBean(String beanName){
		//return request.getSession().getAttribute(beanName);
		return getFacesContext().getExternalContext().getSessionMap().get(beanName);
	}
	
	public void setSessionBean(String attributeName, Object objectBean){
		request.getSession().setAttribute(attributeName, objectBean);
	}
	
	public void removeSessionBean(String beanName){
		getFacesContext().getExternalContext().getSessionMap().remove(beanName);
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void redirect(String view) throws Exception{
		if(view != null){
			String url = getFacesContext().getExternalContext().getRequestContextPath()+"/pages"+"/"+view;
			getFacesContext().getExternalContext().redirect(url);
		}
	}
	
	public void logout() {
		getFacesContext().getExternalContext().invalidateSession();
    }
	
	public InputStream getResourceAsStream(String pathrecurso){
		
		InputStream content = facesContext.getExternalContext().getResourceAsStream(pathrecurso);
		
		return content;
	}
	
	public String getParametrosUrl(){
		return request.getQueryString();
	}
	
	public String getContextParam(String paramName) throws Exception {
		
		String paramValue = facesContext.getExternalContext().getInitParameter(paramName);
		
		return paramValue;
	}
	
}
